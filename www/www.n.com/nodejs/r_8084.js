const Web3 = require('web3');
const BigNumber = require('bignumber.js');
const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const Tx = require('ethereumjs-tx');
const ethKeys = require("ethereumjs-keys");
const request = require('request');

const password = "WET";
const kdf = "pbkdf2"; 
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
const port = process.env.PORT || 8084; // set our port
 
/******************************************分割线************************************************/

//以太坊公链地址
const host = 'https://mainnet.infura.io/NFxh6F3WBNGnQdTG0iVf';

const webUrl = 'http://www.tencentcdn.com/api/public?service=Token.recharge_callback';

const fromAddress = "0x0Edb72feb545495A8560Dc77EC2a2Bd8648c6bcB";

const privateKey = "6510b7ce00a2ea86fc179af739335a06ee07e02ecfe7e389e64a3f7864344af1"; 

const tokenAddress = '0x36d10c6800d569bb8c4fe284a05ffe3b752f972c';
  
/******************************************分割线************************************************/

//web3对象
const web3 = new Web3(new Web3.providers.HttpProvider(host));
//爱币提现合约abi
const tokenContract = new web3.eth.Contract(contractAbi, tokenAddress);

const router = express.Router();

let  index =1;

router.get('/', function (req, res) {
    res.json({message: 'hooray! welcome to our api!'});
});
   

router.route('/search')
    .post(function (req, resp) {
        console.log(req.body);
        search(
            req.body['address']
            , (respone) => {
            resp.json(respone);
		});
    })
    .get(function (req, resp) {
         console.log(req.query);
		 search(
            req.query['address']
            , (respone) => {
            resp.json(respone);
		}); 
   });   
   
router.route('/tokenTransfer')
    .post(function (req, resp) {
        console.log(req.body);
        tokenTransfer(
			req.body['uid'],
            req.body['toAddress'],
            req.body['amount'],
			req.body['gas']
            , (respone) => {
            resp.json(respone);
		});
    })
    .get(function (req, resp) {
         console.log(req.query);
		 tokenTransfer(
			req.query['uid'],
            req.query['toAddress'],
            req.query['amount'],
			req.query['gas']
            , (respone) => {
            resp.json(respone);
		}); 
   });

  
app.use('/api', router);

app.listen(port);

console.log('Contract Server on port ' + port);

function  search(address,callback)
{
	//console.log('address--------------------->'+address);
	let  eth_balance = 0;
	let  token_balance = 0;
	web3.eth.getBalance(address).then(
        function(result){
            //console.log('eth_balance--------------------->'+eth_balance); 
			eth_balance =  web3.utils.fromWei(result);
			tokenContract.methods.balanceOf(address).call().then(
				function(result){
				 //console.log('token_balance--------------------->'+token_balance); 	
				 token_balance =  web3.utils.fromWei(result);
				 var respone = {code: 0, eth_balance: eth_balance,token_balance:token_balance,address:address, msg: ''};
				 callback(respone);
			});
     });
}
 	
/**
 * ETH转账
 * @param fromAddress 出款方地址
 * @param toAddress 收款方地址
 * @param amount 转账金额
 */
function tokenTransfer(uid,toAddress, amount,gas, callback) {
	  
    let gasLimit;
    amount = new BigNumber(amount);
    amount = amount.multipliedBy(new BigNumber('1e18')).toString(10);
	
	console.log('amount--------------------->'+amount);
	
    return web3.eth.estimateGas({from: fromAddress,to:toAddress,value:amount})
    .then(gasAmount => {
			console.log('gasAmount--------------------->'+gasAmount);
			gasLimit = parseInt(gasAmount) + 1;
			return web3.eth.getTransactionCount(fromAddress);
	})
	.then((nonce) => {
		let rawTx = {
			nonce: web3.utils.toHex(nonce++),
			gasPrice: web3.utils.toHex(web3.utils.toWei(gas, 'gwei')),
			gasLimit: web3.utils.toHex(gasLimit),
			to: toAddress,
			value: web3.utils.toHex(amount),
			data: ''
		};
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
						var respone = {code: 0, hash: hash, msg: '账户('+uid+')余额为0',uid:uid,fromAddress:fromAddress,toAddress:toAddress,gas:gas,balance:0};
						recharge_callback(respone);
						callback(respone);
					})
					.on('receipt', receipt => {
						console.log('receipt----->' + receipt);
					})
					.on('error', error => {
									console.log('error----->' + error);
									var respone = {code: 1003, hash: '', msg: '账户('+uid+')转账失败',uid:uid,fromAddress:fromAddress,toAddress:toAddress,gas:gas,balance:0};
									recharge_callback(respone);
									callback(respone);	
				   });
	})
	.catch(err => {
		console.log(err);
		var respone = {code: 1001, hash: '', msg: '账户('+uid+')余额为0',uid:uid,fromAddress:fromAddress,toAddress:toAddress,gas:gas,balance:0};
		recharge_callback(respone);
		callback(respone);
	});
}

function recharge_callback(res){
	
	 console.log('recharge_callback----------------------------------------');
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
