var GustavoCrowdsale = artifacts.require("GustavoCrowdsale");
var GustavoToken = artifacts.require("GustavoToken");

contract('GustavoCrowdsale', function(accounts) {
    it('should deploy the token and store the address', function(done) {
        GustavoCrowdsale.deployed().then(async instance => {
            const token = await instance.token.call();
            assert(token, 'Token address couldn\'t be stored');
            done();
       });
    });

    it('five ETH should buy 5000 Gustavo Tokens', function(done) {
        GustavoCrowdsale.deployed().then(async function(instance) {
            const purchaser = accounts[2];
            const tokenAddress = await instance.token.call();
            const token = GustavoToken.at(tokenAddress);
            await token.transferOwnership(instance.address);
            const data = await instance.sendTransaction({ from: purchaser, value: web3.toWei(5, "ether")}, function(error, txhash) {
                console.log(error);
            });
            const tokenAmount = await token.balanceOf(purchaser);
            assert.equal(tokenAmount.toNumber(), 5000000000000000000000 , 'The purchaser didn\'t receive the tokens as per rate');
            done();
       });
    });

});
