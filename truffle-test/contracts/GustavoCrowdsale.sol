pragma solidity ^0.4.24;

import "./GustavoToken.sol";
import "openzeppelin-solidity/contracts/crowdsale/validation/TimedCrowdsale.sol";
import "openzeppelin-solidity/contracts/crowdsale/emission/MintedCrowdsale.sol";


contract GustavoCrowdsale is TimedCrowdsale, MintedCrowdsale {
    uint public initialBalance = 1 ether;

    constructor
    (
        uint256 _openingTime,
        uint256 _closingTime,
        uint256 _rate,
        address _wallet,
        MintableToken _token
    )
    public
    Crowdsale(_rate, _wallet, _token)
    TimedCrowdsale(_openingTime, _closingTime)
    {
    }

    //function () external payable {
    //}
}