const Web3 = require('web3');
const BigNumber = require('bignumber.js');
const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const Tx = require('ethereumjs-tx');
const ethKeys = require("ethereumjs-keys");

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
const port = process.env.PORT || 8083; // set our port
 
/******************************************分割线************************************************/

//以太坊公链地址
const host = 'https://mainnet.infura.io/NFxh6F3WBNGnQdTG0iVf';

const fromAddress = "0x988f7ED25D55666A634De63595718cC82dB6faA6";

const privateKey = "42b06df89717bcb7d3722f7d627b56d879b1e86137cf10cd1121aae419ef8c5e"; 

const tokenAddress = '0x36d10c6800d569bb8c4fe284a05ffe3b752f972c';
  
/******************************************分割线************************************************/

//web3对象
const web3 = new Web3(new Web3.providers.HttpProvider(host));

const tokenContract = new web3.eth.Contract(contractAbi, tokenAddress);

const router = express.Router();

let  index =1;

router.get('/', function (req, res) {
    res.json({message: 'hooray! welcome to our api!'});
});
   
router.route('/tokenTransfer')
    .post(function (req, resp) {
        console.log(req.body);
        tokenTransfer(
            req.body['fromAddress'],
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
            req.query['fromAddress'],
            req.query['toAddress'],
            req.query['amount'],
			req.body['gas']
            , (respone) => {
            resp.json(respone);
		}); 
    });

router.route('/getAddress')
    .post(function (req, resp) {
        console.log(req.body);
        getAddress(
            (respone) => {
            resp.json(respone);
		});
    })
    .get(function (req, resp) {
         console.log(req.query);
		 getAddress(
            (respone) => {
            resp.json(respone);
		}); 
    });
  
	
app.use('/api', router);

app.listen(port);

console.log('Contract Server on port ' + port);
/**
 * 调用合约的通用的方法
 */
function sendSignedTransaction(gasLimit, data, callback) {
    web3.eth.getTransactionCount(fromAddress)
        .then(nonce => {
        //index++;  	
        let rawTx = {
            nonce: nonce+index,
            gasPrice: web3.utils.toHex(web3.utils.toWei('30', 'gwei')),
            gasLimit: web3.utils.toHex(gasLimit),
            to: contractAddress,
            value: '0x00',
            data: data
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
		//console.log(resp);
		callback(hash);
	})
	.on('receipt', receipt => {
		console.log('receipt----->' + receipt);
	})
	.on('error', console.error);
	});
}
 
function getAddress(callback) {
	 let dk = ethKeys.create();
	 let privateKey = dk.privateKey.toString('hex');
	 let json = ethKeys.dump(password, dk.privateKey, dk.salt, dk.iv, kdf);
	 let address = json.address;
	 let data = {address:address,privateKey:privateKey};
	 let respone = {code: 0, data: data, msg: ''};
	 callback(respone);
}	
/**
 * 代币转账
 * @param fromAddress 出款方地址
 * @param toAddress 收款方地址
 * @param amount 转账金额
 */
function tokenTransfer(fromAddress, toAddress, amount,gas, callback) {
    let gasLimit;
    amount = new BigNumber(amount);
    amount = amount.multipliedBy(new BigNumber('1e18')).toString(10);
    return tokenContract.methods.transfer(toAddress, amount).estimateGas({from: fromAddress})
    .then(gasAmount => {
			gasLimit = parseInt(gasAmount) + 1;
			return web3.eth.getTransactionCount(fromAddress);
	})
	.then((nonce) => {
        let data = tokenContract.methods.transfer(toAddress, amount).encodeABI();
		let rawTx = {
			nonce: web3.utils.toHex(nonce),
			gasPrice: web3.utils.toHex(web3.utils.toWei(gas, 'gwei')),
			gasLimit: web3.utils.toHex(gasLimit),
			to: tokenAddress,
			value: '0x00',
			data: data
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
						var respone = {code: 0, hash: hash, msg: ''};
						callback(respone);
					})
					.on('receipt', receipt => {
						console.log('receipt----->' + receipt);
					})
					.on('error', console.error);
	})
	.catch(err => {
		console.log(err);
		var respone = {code: 1001, hash: '', msg: '转账失败'};
		callback(respone);
	});
}