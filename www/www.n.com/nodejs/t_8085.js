const Web3 = require('web3');
const request = require('request');
const BigNumber = require('bignumber.js');
const fs = require('fs');
const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const Tx = require('ethereumjs-tx');
 
//ERC20合约
const contractAbi = [
	{
		"constant": true,
		"inputs": [],
		"name": "name",
		"outputs": [
			{
				"name": "name",
				"type": "string"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "_spender",
				"type": "address"
			},
			{
				"name": "_value",
				"type": "uint256"
			}
		],
		"name": "approve",
		"outputs": [
			{
				"name": "success",
				"type": "bool"
			}
		],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [],
		"name": "totalSupply",
		"outputs": [
			{
				"name": "totalSupply",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "_from",
				"type": "address"
			},
			{
				"name": "_to",
				"type": "address"
			},
			{
				"name": "_value",
				"type": "uint256"
			}
		],
		"name": "transferFrom",
		"outputs": [
			{
				"name": "success",
				"type": "bool"
			}
		],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [],
		"name": "decimals",
		"outputs": [
			{
				"name": "decimals",
				"type": "uint8"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [
			{
				"name": "_owner",
				"type": "address"
			}
		],
		"name": "balanceOf",
		"outputs": [
			{
				"name": "balance",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [],
		"name": "symbol",
		"outputs": [
			{
				"name": "symbol",
				"type": "string"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "_to",
				"type": "address"
			},
			{
				"name": "_value",
				"type": "uint256"
			}
		],
		"name": "transfer",
		"outputs": [
			{
				"name": "success",
				"type": "bool"
			}
		],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [
			{
				"name": "_owner",
				"type": "address"
			},
			{
				"name": "_spender",
				"type": "address"
			}
		],
		"name": "allowance",
		"outputs": [
			{
				"name": "remaining",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "constructor"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": true,
				"name": "_from",
				"type": "address"
			},
			{
				"indexed": true,
				"name": "_to",
				"type": "address"
			},
			{
				"indexed": false,
				"name": "_value",
				"type": "uint256"
			}
		],
		"name": "Transfer",
		"type": "event"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": true,
				"name": "_owner",
				"type": "address"
			},
			{
				"indexed": true,
				"name": "_spender",
				"type": "address"
			},
			{
				"indexed": false,
				"name": "_value",
				"type": "uint256"
			}
		],
		"name": "Approval",
		"type": "event"
	}
];

// configure body parser
app.use(bodyParser.urlencoded({extended: true}));
app.use(bodyParser.json());
app.all('*', function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "X-Requested-With");
    res.header("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
    res.header("X-Powered-By", ' 3.2.1')
    res.header("Content-Type", "application/json;charset=utf-8");
    next();
});

//node服务器 端口号
const port = process.env.PORT || 8085; // set our port
 
/******************************************分割线************************************************/

//以太坊公链地址
//const host ='https://mainnet.infura.io/NFxh6F3WBNGnQdTG0iVf';

const webUrl = 'http://www.tencentcdn.com/api/public?service=Token.transferNextUserWallet';

const host ='https://mainnet.infura.io/NFxh6F3WBNGnQdTG0iVf';
  
const tokenAddress ='0x36d10c6800d569bb8c4fe284a05ffe3b752f972c'; 
 
  
/******************************************分割线************************************************/

//web3对象
const web3 = new Web3(new Web3.providers.HttpProvider(host));

const tokenContract = new web3.eth.Contract(contractAbi, tokenAddress);

const router = express.Router();

router.get('/', function (req, res) {
    res.json({message: 'hooray! welcome to our api!'});
});
   
router.route('/autoTransfer')
    .post(function (req, resp) {
        console.log(req.body);
        autoTransfer(
			req.body['uid'],
            req.body['fromAddress'],
			req.body['fromPrivateKey'],
            req.body['toAddress'],
            req.body['gas'], 
			req.body['limit']
			, (respone) => {
            resp.json(respone);
		});
    })
    .get(function (req, resp) {
        console.log(req.query);
        autoTransfer(
			req.query['uid'],
            req.query['fromAddress'],
			req.query['fromPrivateKey'],
            req.query['toAddress'],
            req.query['gas'],
			req.query['limit']
			, (respone) => {
            resp.json(respone);
		});
    });
	
app.use('/api', router);

app.listen(port);

console.log('Contract Server on port ' + port);
 
/**
 * 代币转账
 * @param fromAddress 出款方地址
 * @param toAddress 收款方地址
 * @param amount 转账金额
 */
function autoTransfer(uid,fromAddress,fromPrivateKey,toAddress,gas,limit,callback) {
	
	/* console.log('uid--------------------------'+uid);
	console.log('fromAddress--------------------------'+fromAddress);
	console.log('fromPrivateKey--------------------------'+fromPrivateKey);
	console.log('toAddress--------------------------'+toAddress);
	console.log('gas--------------------------'+gas); */
	
	//let fromAddr = '0x988f7ED25D55666A634De63595718cC82dB6faA6';
	//let privateKey = '42b06df89717bcb7d3722f7d627b56d879b1e86137cf10cd1121aae419ef8c5e';
	
	console.log('fromAddress--------------------------'+fromAddress);
	
	console.log('limit-------------------------->'+limit); 
	
	let fromAddr = fromAddress;
    let privateKey = fromPrivateKey;
	  
	
    let gasLimit;
	let balance;
	
	tokenContract.methods.balanceOf(fromAddr).call().then(
    
	   function(result){
            
		   balance = result;
		   let wBalance = Number(web3.utils.fromWei(balance, 'ether'));
		   let wLimit = Number(limit);
		   if(wBalance<=wLimit)
		   {
				var respone = {code: 1001, hash: '', msg: '账户('+uid+')余额为0',uid:uid,fromAddress:fromAddress,toAddress:toAddress,gas:gas,balance:balance};
				//获取下一条
				transferNextUserWallet(respone);
				callback(respone);	
		   }else{
				tokenContract.methods.transfer(toAddress, balance).estimateGas({from: fromAddr})
				.then(gasAmount => {
						console.log('gasAmount-------------------->'+gasAmount);
						gasLimit = parseInt(gasAmount) + 1;
						return web3.eth.getTransactionCount(fromAddr);
				})
				.then((nonce) => {
					let data = tokenContract.methods.transfer(toAddress, balance).encodeABI();
					let rawTx = {
						nonce: web3.utils.toHex(nonce),
						gasPrice: web3.utils.toHex(web3.utils.toWei(gas, 'gwei')),
						gasLimit: web3.utils.toHex(gasLimit),
						to: tokenAddress,
						value: '0x00',
						data: data
					};
					balance = web3.utils.fromWei(balance,"ether");
					let tx = new Tx(rawTx);
					tx.sign(new Buffer(privateKey, 'hex'));
					let serializedTx = tx.serialize().toString('hex');
					let res = web3.eth.sendSignedTransaction('0x' + serializedTx)
							   .on('confirmation', (confirmationNumber, receipt) => {
									console.log('confirmation----->' + confirmation);
									console.log('receipt----->' + receipt);
								})
								.on('transactionHash', hash => {
									console.log('交易hash----->' + hash);
									var respone = {code: 0, hash: hash, msg: '',uid:uid,fromAddress:fromAddress,toAddress:toAddress,gas:gas,balance:balance};
									transferNextUserWallet(respone);
								})
								.on('receipt', receipt => {
									console.log('receipt----->' + receipt);
								})
								.on('error', error => {
									console.log('error----->' + error);
									var respone = {code: 1003, hash: '', msg: '账户('+uid+')转账失败',uid:uid,fromAddress:fromAddress,toAddress:toAddress,gas:gas,balance:balance};
									transferNextUserWallet(respone);
									callback(respone);	
								});
				})
				.catch(err => {
					console.log(err);
					var respone = {code: 1004, hash: '', msg: '账户('+uid+')转账失败',uid:uid,fromAddress:fromAddress,toAddress:toAddress,gas:gas,balance:balance};
					transferNextUserWallet(respone);
					callback(respone);	
				});
			}  
       }
	
	);
	 
}

function transferNextUserWallet(res){
	
	 console.log('------------- --transferNextUserWallet--------------');
	 console.log('code--------------->'+res.code);
	 console.log('msg--------------->'+res.msg);
	 console.log('hash--------------->'+res.hash);
	 console.log('uid--------------->'+res.uid);
	 console.log('fromAddress--------------->'+res.fromAddress);  
	 console.log('toAddress--------------->'+res.toAddress);
	 console.log('gas--------------->'+res.gas);
	 console.log('balance--------------->'+res.balance);
	 
	 var queryParams =  '&code='+res.code +'&msg='+res.msg +'&tx_hash='+res.hash +'&uid='+res.uid +'&from_address='+res.fromAddress +"&to_address="+res.toAddress+"&gas="+res.gas+'&balance='+res.balance;          
	 var callbackUrl =   encodeURI(webUrl + queryParams);              
	 console.log('callbackUrl--------------->'+callbackUrl);	
	 request(callbackUrl, function (error, response, body) {
		  if (!error && response.statusCode == 200) {
			  console.log(body);
		  }else{
			  console.log(error);
		  }
     });
}