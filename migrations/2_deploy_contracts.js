//var ConvertLib = artifacts.require("./ConvertLib.sol");
//var MetaCoin = artifacts.require("./MetaCoin.sol");
//var FuckToken = artifacts.require("./FuckToken.sol");
var GustavoToken = artifacts.require("./GustavoToken.sol");
var GustavoCrowdsale = artifacts.require("./GustavoCrowdsale.sol");
var SimpleToken = artifacts.require("./SimpleToken.sol");
var SimpleCrowdsale = artifacts.require("./SimpleCrowdsale.sol");

module.exports = deployer => {
    const openingTime = web3.eth.getBlock('latest').timestamp + 2; // two secs in the future
    const closingTime = openingTime + 86400 * 20; // 20 days
    //const openingTime = Math.round((new Date(Date.now() - 86400000).getTime())/1000); // Yesterday
    //const closingTime = Math.round((new Date().getTime() + (86400000 * 20))/1000); // Today + 20 days
    const rateGustavo = new web3.BigNumber(1000);
    const rateSimple = new web3.BigNumber(5);

    const walletGustavo = '0xDdD85AAe484510943831c4f5f5f04b0BC41392b1';
    // const wallet = '0x46752fc25d1BFE38c0966677B0a9190587b340b5';
    const walletSimple = '0xB1da708E9c1848938105deb0e7ac0BE7f0c11469';


    return deployer
        .then(() => {
            return deployer.deploy(GustavoToken);
        })
        .then(() => {
            return deployer.deploy(SimpleToken);
        })
        .then(() => {
            return deployer.deploy(
                GustavoCrowdsale,
                openingTime,
                closingTime,
                rateGustavo,
                walletGustavo,
                GustavoToken.address
            );
        })
        .then(() => {
            return deployer.deploy(
                SimpleCrowdsale,
                openingTime,
                closingTime,
                rateSimple,
                walletSimple,
                2000000000000000000, // 2 ETH
                500000000000000000000, // 500 ETH
                SimpleToken.address
            );
        });
};
