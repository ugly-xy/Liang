$(function() {

	$('.langlist').hover(function() {
		$(this).find('.updiv').show();
	}, function() {
		$('.updiv').hide();
	});
	
})
$(window).load(function() {
	var _boxH = $(".Business-list img");
//	func1();

	function func1() {
		for(var i = 0; i < _boxH.length; i++) {
			_boxH.eq(i).width(_boxH.eq(i).width() / 2);
		}
	}
});

function imganimate(clasname, nums) { //背景元素位移
	var items = document.getElementsByClassName(clasname);
	document.addEventListener('mousemove', function(evt) {
		var x = evt.clientX;
		var y = evt.clientY;
		//		console.log(nums); 
		var winWidth = window.innerWidth;
		var winHeight = window.innerHeight;
		var halfWidth = winWidth / nums;
		var halfHeight = winHeight / nums;
		var rx = x - halfWidth;
		var ry = halfHeight - y;
		var length = items.length;
		var max = 30;
		for(var i = 0; i < length; i++) {
			var dx = (items[i].getBoundingClientRect().width / max) * (rx / -halfWidth);
			var dy = (items[i].getBoundingClientRect().height / max) * (ry / halfHeight);
			items[i].style['transform'] = items[i].style['-webkit-transform'] = 'translate(' + dx + 'px,' + dy + 'px)';
		}
	}, false);
}

$(".popup ul li").click(function() {
	$(this).parent().find(".active").removeClass("active");
	$(this).addClass("active")
});

function showpop1() {
	$(".popup1,.mask-pop").show();
}

function showpop2() {
	$(".popup2,.mask-pop").show();
}

function showpop3() {
	$(".popup3,.mask-pop").show();
	$(".popup4").hide();
}

function showpop4() {
	$(".popup4,.mask-pop").show();
}

function showpop5(fn) {
	$(".layer-con,.mask-pop").show();
	if(fn&&typeof(fn)=='function'){
		hidepop=function hidepop(){
			$(".popup1,.popup2,.popup3,.popup4,.layer-con,.mask-pop").hide();
			fn();
		};
	};
	
}

function showpop6(fn,con){
	showpop5(fn);
    if(con)$('#messagetips').html(con);
	
}

function hidepop() {
	$(".popup1,.popup2,.popup3,.popup4,.layer-con,.mask-pop").hide();
}

function jsCopy() {
	var url1 = $("#code_num").attr("data-url");
	var ele = document.getElementById("code_num");
	ele.value = url1;
	ele.select();
	document.execCommand("Copy");
};
function checkLevel(strid){
	var str = $(strid).val();
	var all =validNumber(str)+validNotNum(str);
	$('.pass2').removeClass("certified");
	if(all<6){
		$('.security-ico div:nth-of-type(1)').removeClass("active2 active3").addClass("active");
		$('.security-ico div:nth-of-type(2)').removeClass("active2 active3");
		$('.security-ico div:nth-of-type(3)').removeClass("active2 active3");
		$('#level').text("하");//低
		$('.pass1').removeClass("certified");
	}else{
		if(all>=6){
			$('.pass1').addClass("certified");
		}
		if(validNumber(str)>0&&validNotNum(str)>0){
			$('.pass2').addClass("certified");
			$('.security-ico div:nth-of-type(1)').addClass("active2").removeClass("active active3");
			$('.security-ico div:nth-of-type(2)').addClass("active2").removeClass("active active3");
			$('.security-ico div:nth-of-type(3)').removeClass("active active2 active3");;
			$('#level').text("중");//中
		}
		if(validNumber(str)>0&&validNotNum(str)>1&&all>8){
			$('.security-ico div:nth-of-type(1)').removeClass("active active2").addClass("active3");
			$('.security-ico div:nth-of-type(2)').removeClass("active2").addClass("active3");
			$('.security-ico div:nth-of-type(3)').removeClass("active2").addClass("active3");
			$('#level').text("상");//高
		}
	}
}
function validNumber(str){
	var l = 0;
	if(str&&str!=''){
		var regexp = /[0-9]/g;	
	    var a = str.match(regexp);
	    if(a&&a!=null){
		  return a.length;
	    }
	}
	
	return l;
}
function validNotNum(str){
	
	var l = 0;
	if(str&&str!=''){
		var regexp = /\D/g;	
	    var a = str.match(regexp);
	    if(a&&a!=null){
		  return a.length;
	    }
	}
	
	return l;
}

function regTestPhone(phone){
	phone+="";
	if(phone&&phone.length>1){
		var reg_zh =/1[3|4|5|7|8]\d{9}/;
		var reg_ko =/^01\d{9}$/;
		var reg_ko2=/^10\d{8}$/;
		var reg_ko3=/^\d{8}$/;
		if(!reg_zh.test(phone)&&!(reg_ko.test(phone)||reg_ko2.test(phone)||reg_ko3.test(phone))){
			return false;
		}else{
			return true;
		}
	}
	return false;
}

function beginCount(val){
	var num =Number(val);
	$('#but_time').html(num+"");
	if((num--)==0){
		$("#smsSend").removeClass("show").addClass("hide");
		$("#getSms").removeClass("hide").addClass("show");
		return;
	}else{
		t=setTimeout("beginCount("+num+")",1000);
	}

}

function reload() {
	location.reload(true);
}

function getAddObj(obj,url,title,des,media){
	if(!obj)return {};
	if(url&&''!=url){
		obj.url=''+url;
	}
	if(title&&''!=title){
		obj.title=''+title;

	}
	if(des&&''!=des){
		obj.description=''+des;
	}
	if(media&&''!=media){
		obj.media=''+media;
	}
}

function goPAGE(){
	if((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
        // window.location.href="移动端url";
        console.log("mobile")
    }
    else {
    	 	// window.location.href="PCurl";
    	  	console.log("pc")
      
    }
}