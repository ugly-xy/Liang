const    process = require('child_process');
         schedule = require('node-schedule');
 
let cmd1 = 'pm2 restart monitor'; 
let cmd2 = 'pm2 restart c_8081'; 
let cmd3 = 'pm2 restart c_8082'; 
let cmd4 = 'pm2 restart c_8083'; 

//每天的凌晨3点0分0秒触发 ：'30 1 1 * * *'
let exeTime = '0 0 3 * * *';
  
function startMonitor()
{
	console.log('----------------StartMonitor-----------------');
	process.exec(cmd1,function (error, stdout, stderr) {
        if (error !== null) {
			console.log('error-------------------->' + error);
        }else{
			console.log('stdout-------------------->' + stdout);
		}
	});
	process.exec(cmd2,function (error, stdout, stderr) {
        if (error !== null) {
			console.log('error-------------------->' + error);
        }else{
			console.log('stdout-------------------->' + stdout);
		}
	});
	process.exec(cmd3,function (error, stdout, stderr) {
        if (error !== null) {
			console.log('error-------------------->' + error);
        }else{
			console.log('stdout-------------------->' + stdout);
		}
	});
	process.exec(cmd4,function (error, stdout, stderr) {
        if (error !== null) {
			console.log('error-------------------->' + error);
        }else{
			console.log('stdout-------------------->' + stdout);
		}
	});
}

function scheduleStartMonitor(){
	 
	console.log('----------------scheduleStartMonitor-----------------'); 
    schedule.scheduleJob(exeTime, function(){
        console.log('scheduleStartMonitor----------------->' + new Date());
		startMonitor();
    }); 
}

scheduleStartMonitor();
 
 


 
