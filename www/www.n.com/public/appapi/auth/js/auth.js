$(function(){

	

	/*点击同意/不同意*/

	$(".agreement .agree_area").click(function(){
		if($(this).hasClass("agree")){
			$(this).removeClass("agree").css("background","none");
			$(this).children("img").hide();
			$(".autharea input").attr("disabled", true);
		}else{
			$(this).addClass("agree").css({"background":"#40BB0A","border-color":"#40BB0A"});
			$(this).children("img").show();
			$(".autharea input").attr("disabled", false);
		}
		
	});

	/*点击提交审核*/

	var is_submit=0;

	$(".autharea input").click(function(){
		
		if(is_submit==1){
			return;
		}
		var realname=$("#realname").val();
		var phone=$("#phone").val();
		var cardno=$("#cardno").val();
		//var reg_realName=/^(?=.*\d.*\b)/;
		var reg_realName=/^[\u4e00-\u9fa5]+$/;
		var reg_realName2=/^[\uAC00-\uD7A3]+$/; //韩国名称验证
		var reg_phone = /^1[3|4|5|6|7|8|9][0-9]{9}$/; //验证规则
		var reg_phone2 = /^[0|1][0|1|6|7|8|9]\d{9}$/; //韩国手机号验证
		var reg_identity=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
		var reg_identity2=/^(\d{6}-)(\d{7})?$/;//韩国身份号验证
	
		if((reg_realName.test(realname)==false && reg_realName2.test(realname)==false)  || realname.length>6){
			layer.msg(LangT('请输入您的名字'));
			return;
		}else if(reg_phone.test(phone)==false && reg_phone2.test(phone)==false){
			layer.msg(LangT("请输入11位手机号码<br />如:01012345678"));
			return;
		}else if(cardno.length<12 || 13<cardno.length){
			layer.msg(LangT('请输入12位或13号身份证件号码<br />如:1234561234567'));
			return;
		}
	
		is_submit=1;
		$.ajax({
			url: '/index.php?g=Appapi&m=Auth&a=auth_save',
			type: 'POST',
			dataType: 'json',
			data: {uid:uid,realname: realname,phone:phone,cardno:cardno,language:language_type},
			success:function(data){

				var code=data.code;
				if(code!=0){
					layer.msg(data.msg);
					return;
				}else{
					//layer.msg("认证成功");
					layer.msg(LangT('提交成功'), {time:1000},function(){
						location.href="/index.php?g=Appapi&m=Auth&a=success&uid="+uid+"&language="+language_type;
					});


				}
			},
			error:function(e){
				console.log(e);
			}
		});
		
		

	});
});