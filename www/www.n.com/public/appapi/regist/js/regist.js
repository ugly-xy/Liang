$(function(){
	$("#phone").on("keyup",function(){
		var len=$("#phone").val().length;
		var codeVal=$("#code").val();
		if(len==11&&codeVal=="获取验证码"){
			$("#code").removeAttr("disabled");
			$("#code").css("background","#ffd63c");
		}
	});
	/*获取验证码倒计时*/
	$(".code").on("click",function(){
		var phone=$("#phone").val();
		 var count = 60;
         var countdown = setInterval(CountDown, 1000);
        $.ajax({
        	type:"get",
        	data:{mobile:phone},
        	url:"./index.php?g=Appapi&m=Regist&a=getCode",
        	async:true,
        	dataType:"json",
        	success:function(data){
        		if(data.type==1){
        			layer.msg("验证码发送成功，请注意查收");
        		}else if(data.type==2){
        			layer.msg("验证码为123456");
        		}else{
        			layer.msg("验证码发送失败");
        		}
        	},
        	error:function(data){
        		layer.msg("验证码发送失败");
        	}
        });
		
		function CountDown() {
			$("#code").attr("disabled", true);
			$("#code").val(""+count+"s");
			$("#code").css("background","#aaa");
			if (count == 0) {
				$("#code").val("获取验证码").removeAttr("disabled");//将input变为可用
				clearInterval(countdown);
			}
			count--;
    	}

	});
	
		$("#submitBtn").on("click",function(){
			var phone=$("#phone").val();
			var phoneCode=$("#phoneCode").val();
			var firstPass=$("#firstPass").val();
			var ConfirmPass=$("#ConfirmPass").val();
			var one_uid=$("#one_uid").val();
			
			var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if(!phoneReg.test(phone)) {
			   layer.msg("请输入有效的手机号码");//layer是一个弹窗插件
			    return false;
			}
			
			if(phoneCode==""||phoneCode.length!=6){
				layer.msg("请确认您的验证码");
			    return false;
			}
			if(firstPass==""||ConfirmPass==""||firstPass!=ConfirmPass){
				layer.msg("请确认您输入的密码");
			    return false;
			}
			if(firstPass.length<4){
				layer.msg("密码长度必须多余4位");
			    return false;
			}
			
			if(one_uid==""){
				layer.msg("没有推荐人，无法注册");
			    return false;
			}
			
			if(!isNaN(firstPass)){
				layer.msg("密码不能是纯数字");
			    return false;
			}
			
			$.ajax({
				type:"post",
				url:"./index.php?g=Appapi&m=Regist&a=regist_post",
				async:true,
				data:{phone:phone,phoneCode:phoneCode,pass:firstPass,one_uid:one_uid},
				dataType:"json",
				success:function(data){
					if(data.code==0){
						layer.msg("此号码已经注册");
						return;
					}
					
					if(data.code==1){
						layer.msg("注册成功");
						var ua = navigator.userAgent.toLowerCase();	
						if (/iphone|ipad|ipod/.test(ua)) {
							setTimeout("location.href='"+data.iosUrl+"'",2000);		
						} else if (/android/.test(ua)) {
							 setTimeout("location.href='"+data.androidUrl+"'",2000);	
							   
						}
						return;
					}
					if(data.code==2){
						layer.msg("手机号码不正确");
						return;
					}
					
					if(data.code==3){
						layer.msg("验证码错误");
						return;
					}
					
					if(data.code==4){
						layer.msg("手机号码格式不对");
						return;
					}
				},
				error:function(){
					layer.msg("注册失败");
				}
				
			});
			
		});
});
