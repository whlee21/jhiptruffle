# Truffle 

## truffle init

truffle 프로젝트를 초기화한다.

~~~bash
truffle init
~~~

## truffle console test
 
~~~bash
purchaser = web3.eth.accounts[2]
GustavoCrowdsale.deployed().then(inst => { crowdsale = inst })
crowdsale.token().then(addr => { tokenAddress = addr } )
tokenAddress
tokenInstance = SimpleToken.at(tokenAddress)
tokenInstance.transferOwnership(crowdsale.address)
tokenInstance.balanceOf(purchaser).then(balance => balance.toString(10))
GustavoCrowdsale.deployed().then(inst => inst.sendTransaction({ from: purchaser, value: web3.toWei(5, "ether")}))
tokenInstance.balanceOf(purchaser).then(balance => purchaserTokenBalance = balance.toString(10))
web3.fromWei(purchaserTokenBalance, "ether")
~~~
