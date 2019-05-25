var    request  = require('request'),
       schedule = require('node-schedule');

//每5秒执行一次	
   
var rule     = new schedule.RecurrenceRule();
var times    = [1,6,11,16,21,26,31,36,41,46,51,56];
rule.second  = times;

//每天的凌晨0点0分30秒触发 ：'30 1 1 * * *'
var exeTime = '30 0 0 * * *';

//统计用户资产的url
var autoTransferUrl = "http://www.tencentcdn.com/api/public?service=Token.autoTransfer";

function autoTransfer()
{
	console.log('----------------autoTransfer-----------------');
	request(autoTransferUrl, function (error, response, body) {
	  if (!error && response.statusCode == 200) {
		   console.log(body);
	  }else{
		   console.log('autoTransfer----------------->'+error);
	  }
	});
}

function scheduleAutoTransfer(){
	 
	console.log('----------------scheduleAutoTransfer-----------------'); 
    schedule.scheduleJob(exeTime, function(){
        console.log('scheduleAutoTransfer----------------->' + new Date());
		autoTransfer();
    }); 
}

scheduleAutoTransfer();