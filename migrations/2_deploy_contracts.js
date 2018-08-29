//var ConvertLib = artifacts.require("./ConvertLib.sol");
//var MetaCoin = artifacts.require("./MetaCoin.sol");
//var FuckToken = artifacts.require("./FuckToken.sol");
var SimpleToken = artifacts.require("./SimpleToken.sol");
var SimpleCrowdsale = artifacts.require("./SimpleCrowdsale.sol");
//var GustavoCrowdsale = artifacts.require("./GustavoCrowdsale.sol");

module.exports = function(deployer) {
    const openingTime = web3.eth.getBlock('latest').timestamp + 2; // two secs in the future
    const closingTime = openingTime + 86400 * 20; // 20 days
    const rate = new web3.BigNumber(1000);
    //const wallet = '0x46752fc25d1BFE38c0966677B0a9190587b340b5';
    const wallet = '0xB1da708E9c1848938105deb0e7ac0BE7f0c11469';


    return deployer
        .then(() => {
            return deployer.deploy(SimpleToken);
        })
        .then(() => {
            return deployer.deploy(
                SimpleCrowdsale,
                //GustavoCrowdsale,
                openingTime,
                closingTime,
                rate,
                wallet,
                2000000000000000000, // 2 ETH
                500000000000000000000, // 500 ETH
                SimpleToken.address
            );
        });
};
