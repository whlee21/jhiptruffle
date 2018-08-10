var ConvertLib = artifacts.require("./ConvertLib.sol");
var MetaCoin = artifacts.require("./MetaCoin.sol");
var FuckToken = artifacts.require("./FuckToken.sol");
var MyBasicToken = artifacts.require("./MyBasicToken.sol");
var SimpleToken = artifacts.require("./SimpleToken.sol");

module.exports = function(deployer) {
    deployer.deploy(ConvertLib);
    deployer.link(ConvertLib, MetaCoin);
    deployer.deploy(MetaCoin);
    deployer.deploy(FuckToken);
    deployer.deploy(MyBasicToken);
    deployer.deploy(SimpleToken);
};
