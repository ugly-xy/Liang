$(document).ready(function(){
	//列表页移上去变色
	$(".trover").bind({
		mouseover: function(){
			$(this).css({"background-color":"#F5F5F5"});
		},
		mouseout: function(){
			$(this).css({"background-color":"#fff"});
		}
	});
	$(".listbox").find("a").removeClass().addClass("blue");
});

function ShowLayer(id){	
	var layer=document.getElementById("pop_info"+id);
	var mask=document.getElementById("pop_mask");
	$('#pop_info'+id).show(); 
	$('#pop_mask').show();
	mask.style.width=document.body.clientWidth+"px";
	layer.style.left=(document.body.clientWidth-500)/2 +"px";
	//判断当前是什么浏览器
	if ($.browser.msie) {
		if ($.browser.version == 9){
			mask.style.height = $(document).height() + "px";
			layer.style.top=(document.documentElement.scrollTop+(document.documentElement.clientHeight-$("#pop_info"+id).height())/2)+"px";
		}else{
			mask.style.height = $("body").height() + "px";			
			layer.style.top=(document.documentElement.scrollTop+(document.documentElement.clientHeight-$("#pop_info"+id).height())/2)+"px";
		}
	}else
	{
		mask.style.height = $(document).height() + "px";
		layer.style.top = (document.documentElement.scrollTop+document.body.scrollTop+(document.documentElement.clientHeight-$("#pop_info"+id).height())/2)+"px";
	}

}
function HideLayer(id){
	$('#pop_info'+id).hide();
	$('#pop_mask').hide();
}
