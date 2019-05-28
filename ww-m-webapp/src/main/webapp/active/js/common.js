$.ajax({
	type:"get",
	url:"/api/activity/first?type=131&len=1",
	async:true,
	success:function(data){
//		var str = "<ul>";
//		for(var i = 0;i<data.rule.length;i++){
//			str += '<li><dl><dt><p>'+data.rule[i].id+'</p></dt><dd><p>'+data.rule[i].cont+'</p></dd></dl></li>'
//		}
//		str +='</ul>' 
//		str += '<div class="bot"></div>';
//		$("#rule").append(str);
		var str1 = "";
		for(var j = 0;j<data.list.length;j++){
			str1 += '<li>'
			str1 += '<div>'+data.list[j].id+'</div>'
			str1 += '<div><img src="'+data.list[j].img+'" /></div>'
			str1 += '<div>'+data.list[j].name+'</div>'
			str1 += '<div><img src="/active/img/mo.png">'+data.list[j].money+'</div>'
			str1 += '<div><img src="/active/img/moo.png">'+data.list[j].addM+'</div>'
			str1 += '</li>'
		}
		$("#list ul.c").append(str1)
		
	},
	error:function(){
		console.log(1)
	}
});






















