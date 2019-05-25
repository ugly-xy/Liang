const fs = require('fs');
const Web3 = require('web3');
const request = require('request');
const schedule = require('node-schedule');

//const host = 'http://127.0.0.1/api/public?service=Token.Transfer';
const host = 'http://lebo.yunbaozb.com/api/public?service=Token.Transfer';
const provider = new Web3.providers.HttpProvider("https://rinkeby.infura.io/v3/2abf85f69cad46989d58465590ebf37e");
const web3 = new Web3(provider); 

const rule     = new schedule.RecurrenceRule();
const times    = [1,6,11,16,21,26,31,36,41,46,51,56];
rule.second  = times;
  
/* let sourceCode = fs.readFileSync('/data/wwwroot/lebo.com/nodejs/ym.sol','utf8').toString();
let compiledCode = solc.compile(sourceCode); */
const abi = [ { constant: true,
    inputs: [],
    name: 'name',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'view',
    type: 'function' },
  { constant: false,
    inputs: [],
    name: 'stop',
    outputs: [],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'function' },
  { constant: false,
    inputs: [ [Object], [Object] ],
    name: 'approve',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'function' },
  { constant: true,
    inputs: [],
    name: 'totalSupply',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'view',
    type: 'function' },
  { constant: false,
    inputs: [ [Object], [Object], [Object] ],
    name: 'transferFrom',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'function' },
  { constant: true,
    inputs: [],
    name: 'decimals',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'view',
    type: 'function' },
  { constant: false,
    inputs: [ [Object] ],
    name: 'burn',
    outputs: [],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'function' },
  { constant: true,
    inputs: [ [Object] ],
    name: 'balanceOf',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'view',
    type: 'function' },
  { constant: true,
    inputs: [],
    name: 'stopped',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'view',
    type: 'function' },
  { constant: true,
    inputs: [],
    name: 'symbol',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'view',
    type: 'function' },
  { constant: false,
    inputs: [ [Object], [Object] ],
    name: 'transfer',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'function' },
  { constant: false,
    inputs: [],
    name: 'start',
    outputs: [],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'function' },
  { constant: false,
    inputs: [ [Object] ],
    name: 'setName',
    outputs: [],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'function' },
  { constant: true,
    inputs: [ [Object], [Object] ],
    name: 'allowance',
    outputs: [ [Object] ],
    payable: false,
    stateMutability: 'view',
    type: 'function' },
  { inputs: [ [Object] ],
    payable: false,
    stateMutability: 'nonpayable',
    type: 'constructor' },
  { anonymous: false,
    inputs: [ [Object], [Object], [Object] ],
    name: 'Transfer',
    type: 'event' },
  { anonymous: false,
    inputs: [ [Object], [Object], [Object] ],
    name: 'Approval',
    type: 'event' } ];
 
var contract = new web3.eth.Contract(abi,'0xFc246c9f9F29e04a73769ee1709AA1D9340d1095');

var sha3 = web3.utils.sha3("Transfer(address,address,uint256)"); 

console.log('monitor---------------'); 

function scheduleGetTrans(){
   
    schedule.scheduleJob(rule, function(){
        //console.log('scheduleGetTrans--------------->' + new Date());
        contract.getPastEvents(
            "allEvents",
              { fromBlock: "latest", toBlock: "latest", topics:[sha3]},
              (errors, events) => {
                  if (!errors) {
                       
                      events.forEach(function(event,index){
                            console.log(event+'---'+index);
                            var transactionHash = event.transactionHash;
                            var blockNumber = event.blockNumber;
                            var from =web3.eth.abi.decodeParameter('address',event.raw.topics[1]);
                            var to =web3.eth.abi.decodeParameter('address',event.raw.topics[2]);
                            var value =web3.eth.abi.decodeParameter('uint256',event.raw.data);
                            
                            console.log('txid--------------->'+transactionHash);
                            console.log('blockNumber--------------->'+blockNumber);
                            console.log('from--------------->'+from);
                            console.log('to--------------->'+to);  
                            console.log('value--------------->'+value);
                            
                            var callbackUrl =   host +'&txhash='+transactionHash +'&block_number='+blockNumber +'&from='+from +"&to="+to +"&value="+value;
                            
                            console.log('callbackUrl--------------->'+callbackUrl);
                            
                            request(callbackUrl, function (error, response, body) {
                              if (!error && response.statusCode == 200) {
                                  console.log(body);
                              }else{
                                 console.log(error);
                              }
                            });
                      }) 

                  }else{
                      console.log(errors);
                  }
              }
        );
    }); 
}

scheduleGetTrans();
 


  

 
 

  