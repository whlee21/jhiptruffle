var GustavoCrowdsale = artifacts.require("GustavoCrowdsale");
var GustavoToken = artifacts.require("GustavoToken");

contract('GustavoCrowdsale', function(accounts) {
    it('should deploy the token and store the address', function(done){
        GustavoCrowdsale.deployed().then(async function(instance) {
            const token = await instance.token.call();
            assert(token, 'Token address couldn\'t be stored');
            done();
       });
    });

    it('five ETH should buy 5000 Gustavo Tokens in PreICO', function(done){
        GustavoCrowdsale.deployed().then(async function(instance) {
            const data = await instance.sendTransaction({ from: accounts[2], value: web3.toWei(5, "ether")});
            const tokenAddress = await instance.token.call();
            const simpleToken = GustavoToken.at(tokenAddress);
            const tokenAmount = await simpleToken.balanceOf(accounts[2]);
            assert.equal(tokenAmount.toNumber(), 5000000000000000000000, 'The sender didn\'t receive the tokens as per PreICO rate');
            done();
       });
    });

    it('should transfer the ETH to wallet immediately in Pre ICO', function(done){
        GustavoCrowdsale.deployed().then(async function(instance) {
            let balanceOfBeneficiary = await web3.eth.getBalance(accounts[9]);
            balanceOfBeneficiary = Number(balanceOfBeneficiary.toString(10));

            await instance.sendTransaction({ from: accounts[1], value: web3.toWei(2, "ether")});

            let newBalanceOfBeneficiary = await web3.eth.getBalance(accounts[9]);
            newBalanceOfBeneficiary = Number(newBalanceOfBeneficiary.toString(10));

            assert.equal(newBalanceOfBeneficiary, balanceOfBeneficiary + 2000000000000000000, 'ETH couldn\'t be transferred to the beneficiary');
            done();
       });
    });

    it('should set variable `totalWeiRaisedDuringPreICO` correctly', function(done){
        GustavoCrowdsale.deployed().then(async function(instance) {
            var amount = await instance.totalWeiRaisedDuringPreICO.call();
            assert.equal(amount.toNumber(), web3.toWei(3, "ether"), 'Total ETH raised in PreICO was not calculated correctly');
            done();
       });
    });

    it('one ETH should buy 2 Gustavo Tokens in ICO', function(done){
        GustavoCrowdsale.deployed().then(async function(instance) {
            const data = await instance.sendTransaction({ from: accounts[2], value: web3.toWei(1.5, "ether")});
            const tokenAddress = await instance.token.call();
            const simpleToken = GustavoToken.at(tokenAddress);
            const tokenAmount = await simpleToken.balanceOf(accounts[2]);
            assert.equal(tokenAmount.toNumber(), 3000000000000000000, 'The sender didn\'t receive the tokens as per ICO rate');
            done();
       });
    });

});
