import { Component, OnInit } from '@angular/core';
import { Web3Service } from 'app/util/web3.service';

// import metacoin_artifacts = require('../../../../../../build/contracts/MetaCoin.json');
// import erc20token_artifacts = require('../../../../../../build/contracts/FuckToken.json');
import metacoin_artifacts = require('../../../../../../build/contracts/MetaCoin.json');
import erc20token_artifacts = require('../../../../../../build/contracts/SimpleToken.json');

@Component({
    selector: 'jhi-meta-sender',
    templateUrl: './meta-sender.component.html',
    styleUrls: ['./meta-sender.component.css']
})
export class MetaSenderComponent implements OnInit {
    accounts: string[];
    MetaCoin: any;
    ERC20Token: any;

    model = {
        amount: 5,
        receiver: '',
        balance: 0,
        account: ''
    };

    ethereumModel = {
        amount: 0,
        receiver: '',
        balance: '',
        account: ''
    };

    ERC20Model = {
        amount: 0,
        receiver: '',
        balance: 0,
        account: ''
    };

    status = '';

    constructor(private web3Service: Web3Service) {
        console.log('Constructor: ' + web3Service);
    }

    ngOnInit(): void {
        console.log('OnInit: ' + this.web3Service);
        console.log(this);
        this.watchAccount();
        console.log('metacoin_artifacts:' + metacoin_artifacts);
        this.web3Service.artifactsToContract(metacoin_artifacts).then(MetaCoinAbstraction => {
            this.MetaCoin = MetaCoinAbstraction;
        });

        console.log('erc20token_artifacts:' + erc20token_artifacts);
        this.web3Service.artifactsToContract(erc20token_artifacts).then(ERC20Abstraction => {
            this.ERC20Token = ERC20Abstraction;
        });
    }

    watchAccount() {
        this.web3Service.accountsObservable.subscribe(accounts => {
            this.accounts = accounts;
            this.model.account = accounts[0];
            this.refreshBalance();
        });
    }

    setStatus(status) {
        this.status = status;
    }

    async sendCoin() {
        // if (!this.MetaCoin) {
        if (!this.ERC20Token) {
            this.setStatus('Metacoin is not loaded, unable to send transaction');
            return;
        }

        const amount = this.model.amount;
        const receiver = this.model.receiver;

        console.log('Sending tokens ' + amount + ' to ' + receiver);

        this.setStatus('Initiating transaction... (please wait)');
        try {
            // const deployedMetaCoin = await this.MetaCoin.deployed();
            const deployedERC20Token = await this.ERC20Token.deployed();
            // const transaction = await deployedMetaCoin.sendCoin.sendTransaction(receiver, amount, { from: this.model.account });
            // const transaction = await deployedERC20Token.transferFrom(this.model.account, receiver, amount);
            const transaction = await deployedERC20Token.transferFrom('0xf78b19283bd210128b77ab7930ef4d8dbc3b5f93', receiver, amount);

            if (!transaction) {
                this.setStatus('Transaction failed!');
            } else {
                this.setStatus('Transaction complete!');
            }
            location.reload();
        } catch (e) {
            console.log(e);
            this.setStatus('Error sending coin; see log.');
        }
    }

    async refreshBalance() {
        // console.log('Refreshing balance');
        const that = this;

        // console.log('whlee21 0', this.model.account);
        try {
            this.web3Service.getEthBalance(this.model.account, function(data) {
                that.ethereumModel.balance = data;
            });
            // console.log(
            //     'whlee21 1',
            //     this.ethereumModel.account,
            //     this.ethereumModel.amount,
            //     this.ethereumModel.balance,
            //     this.ethereumModel.receiver
            // );
            this.ERC20Model.balance = await this.web3Service.getERC20Balance(this.model.account);

            // console.log('whlee21 2');
            const deployedERC20Coin = await this.ERC20Token.deployed();
            // console.log('whlee21 deployedERC20Coin', deployedERC20Coin);
            const ERC20TokenBalance = await deployedERC20Coin.balanceOf(this.model.account);
            // console.log('Found ERC20 token balance: ' + ERC20TokenBalance);
            this.ERC20Model.balance = ERC20TokenBalance;

            // const deployedMetaCoin = await this.MetaCoin.deployed();
            // console.log('whlee21 deployedMetaCoin', deployedMetaCoin);
            // const metaCoinBalance = await deployedMetaCoin.methods.getBalance(this.model.account).call();
            // const metaCoinBalance = await deployedMetaCoin.getBalance(this.model.account);
            // console.log('Found balance: ' + metaCoinBalance);
            // this.model.balance = metaCoinBalance;
        } catch (e) {
            console.log(e);
            this.setStatus('Error getting balance; see log.');
        }
    }

    clickAddress(e) {
        this.model.account = e.target.value;
        this.refreshBalance();
    }

    setAmount(e) {
        console.log('Setting amount: ' + e.target.value);
        this.model.amount = e.target.value;
    }

    setReceiver(e) {
        console.log('Setting receiver: ' + e.target.value);
        this.model.receiver = e.target.value;
    }
}
