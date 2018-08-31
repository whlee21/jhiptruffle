pragma solidity ^0.4.24;

import "./SimpleToken.sol";
import "openzeppelin-solidity/contracts/crowdsale/validation/CappedCrowdsale.sol";
import "openzeppelin-solidity/contracts/crowdsale/distribution/RefundableCrowdsale.sol";
import "openzeppelin-solidity/contracts/crowdsale/validation/TimedCrowdsale.sol";
import "openzeppelin-solidity/contracts/crowdsale/emission/MintedCrowdsale.sol";

contract SimpleCrowdsale is CappedCrowdsale, RefundableCrowdsale, MintedCrowdsale {

    // ICO Stage
    // ============
    enum CrowdsaleStage { PreICO, ICO }
    CrowdsaleStage public stage = CrowdsaleStage.PreICO; // By default it's Pre Sale
    // =============

    // Token Distribution
    // =============================
    uint256 public maxTokens = 100000000000000000000; // There will be total 100 Hashnode Tokens
    uint256 public tokensForEcosystem = 20000000000000000000;
    uint256 public tokensForTeam = 10000000000000000000;
    uint256 public tokensForBounty = 10000000000000000000;
    uint256 public totalTokensForSale = 60000000000000000000; // 60 HTs will be sold in Crowdsale
    uint256 public totalTokensForSaleDuringPreICO = 20000000000000000000; // 20 out of 60 HTs will be sold during PreICO
    // ==============================

    // Amount raised in PreICO
    // ==================
    uint256 public totalWeiRaisedDuringPreICO;
    // ===================


    // Events
    event EthTransferred(string text);
    event EthRefunded(string text);


    // Constructor
    // ============
    constructor(uint256 _openingTime, uint256 _closingTime, uint256 _rate, address _wallet, uint256 _goal, uint256 _cap, MintableToken _token)
        CappedCrowdsale(_cap) FinalizableCrowdsale() RefundableCrowdsale(_goal) TimedCrowdsale(_openingTime, _closingTime) Crowdsale(_rate, _wallet, _token) public {
        require(_goal <= _cap);
    }
    // =============

    // Token Deployment
    // =================
    // by whlee21, commented out
    //function createTokenContract() internal returns (MintableToken) {
    //  return new SimpleToken(); // Deploys the ERC20 token. Automatically called when crowdsale contract is deployed
    //}
    // ==================

    // Crowdsale Stage Management
    // =========================================================

    // Change Crowdsale Stage. Available Options: PreICO, ICO
    function setCrowdsaleStage(uint value) public onlyOwner {

        CrowdsaleStage _stage;

        if (uint(CrowdsaleStage.PreICO) == value) {
            _stage = CrowdsaleStage.PreICO;
        } else if (uint(CrowdsaleStage.ICO) == value) {
            _stage = CrowdsaleStage.ICO;
        }

        stage = _stage;

        if (stage == CrowdsaleStage.PreICO) {
            setCurrentRate(5);
        } else if (stage == CrowdsaleStage.ICO) {
            setCurrentRate(2);
        }
    }

    // Change the current rate
    function setCurrentRate(uint256 _rate) private {
        rate = _rate;
    }

    // ================ Stage Management Over =====================

    // Token Purchase
    // =========================
    function () external payable {
        uint256 tokensThatWillBeMintedAfterPurchase = msg.value.mul(rate);
        if ((stage == CrowdsaleStage.PreICO) && (token.totalSupply() + tokensThatWillBeMintedAfterPurchase > totalTokensForSaleDuringPreICO)) {
            msg.sender.transfer(msg.value); // Refund them
            emit EthRefunded("PreICO Limit Hit");
            return;
        }

        buyTokens(msg.sender);

        if (stage == CrowdsaleStage.PreICO) {
            totalWeiRaisedDuringPreICO = totalWeiRaisedDuringPreICO.add(msg.value);
        }
    }

    function forwardFunds() internal {
        if (stage == CrowdsaleStage.PreICO) {
            wallet.transfer(msg.value);
            emit EthTransferred("forwarding funds to wallet");
        } else if (stage == CrowdsaleStage.ICO) {
            emit EthTransferred("forwarding funds to refundable vault");
            super._forwardFunds();
        }
    }
    // ===========================

    // Finish: Mint Extra Tokens as needed before finalizing the Crowdsale.
    // ====================================================================

    function finish(address _teamFund, address _ecosystemFund, address _bountyFund) public onlyOwner {

        require(!isFinalized);
        uint256 alreadyMinted = token.totalSupply();
        require(alreadyMinted < maxTokens);

        uint256 unsoldTokens = totalTokensForSale - alreadyMinted;
        if (unsoldTokens > 0) {
            tokensForEcosystem = tokensForEcosystem + unsoldTokens;
        }

        super._deliverTokens(_teamFund,tokensForTeam);
        super._deliverTokens(_ecosystemFund,tokensForEcosystem);
        super._deliverTokens(_bountyFund,tokensForBounty);
        finalize();
    }
    // ===============================

    // REMOVE THIS FUNCTION ONCE YOU ARE READY FOR PRODUCTION
    // USEFUL FOR TESTING `finish()` FUNCTION
    function hasEnded() public view returns (bool) {
        return true;
    }
}
