var isNavHover=false;

var t = n = 0;
var count = 0;

function g(obj){return document.getElementById(obj);}
function getCookie(name){
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");if(arr=document.cookie.match(reg)){return unescape(decodeURI(arr[2]));}else{return "";}
}

function isMobile(){
	var ua=navigator.userAgent.toLowerCase();
	if (ua.indexOf('iphone') != -1)return "iphone";
	if (ua.indexOf('ipod') != -1)return "ipod";
	if (ua.indexOf('ipad') != -1) return "ipad";
	if (ua.indexOf('android') != -1) return "android";
	return false;
}


