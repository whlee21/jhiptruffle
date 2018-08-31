var GustavoToken = artifacts.require("./GustavoToken.sol");
var GustavoCrowdsale = artifacts.require("./GustavoCrowdsale.sol");

module.exports = deployer => {
    const openingTime = web3.eth.getBlock('latest').timestamp + 2; // two secs in the future
    const closingTime = openingTime + 86400 * 20; // 20 days
    const rate = new web3.BigNumber(1000);
    //const wallet = '0xDdD85AAe484510943831c4f5f5f04b0BC41392b1'; // accounts[9]
    //const wallet = '0x5aeda56215b167893e80b4fe645ba6d5bab767de'; // accounts[9]
    const wallet = '0x46752fc25d1BFE38c0966677B0a9190587b340b5';

    return deployer
        .then(() => {
            return deployer.deploy(GustavoToken);
        })
        .then(() => {
            return deployer.deploy(
                GustavoCrowdsale,
                openingTime,
                closingTime,
                rate,
                wallet,
                GustavoToken.address
            );
        });
};
