function XYV(formId,cbf,cfg){
	this.ver="2.0.0";
	this.form=null;
	this.vfset=[];/*function和ajax类型验证*/
	this.extErrSet=[];/*附加err内容*/
	this.showedNameMap=[];/*已显示提示 表单对象 名称*/
	this.errArray=[];/*不能通过验证的错误提示数组{name,position}*/
	this.e=function(en){
		return this.form.elements[en];
	};
	/*克隆对象*/
	this.clone=function(obj){
		var objClone={};
		for(var key in obj){
			objClone[key] = obj[key];
		}
		return objClone; 
	};

	/**
	* XYV.cfg 属性
	* short		短路判断					默认 true
	* trim		自动去首位空白字符			默认 true
	* autoCheck 是否在onblur时自动验证	默认 false
	* tlct		div所在位置 [top,right,buttom]	默认 right
	* eventNames	激活验证事件名称		默认 blur 
	*/
	this.initCfg=function(nc){
		this.cfg={};
		this.cfg.short = (nc!=null&&nc.short!=null)?nc.short:true;/*是否短路校验*/
		this.cfg.trim = (nc!=null&&nc.trim!=null?nc.trim:true);/*是否自动除去首位空格*/
		this.cfg.autoCheck = (nc!=null&&nc.autoCheck!=null?nc.autoCheck:true);/*是否自动验证单个表单项*/
		this.cfg.tlct = (nc!=null&&nc.tlct!=null?nc.tlct:"right");/*默认右侧显示提示信息*/
		this.cfg.sct = (nc!=null&&nc.sct!=null?nc.sct:true);/*验证通过后是否显示验证通过提示(showCorrecctTip)*/
		this.cfg.cssPair = (nc!=null&&nc.cssPair!=null?nc.cssPair:{});/*默认信息提示样式(全局变量)*/
		this.cfg.marginL = (nc!=null&&nc.marginL!=null?nc.marginL:10);//*右侧显示时 距离表单域的距离*/
		this.cfg.marginT = (nc!=null&&nc.marginT!=null?nc.marginT:4);/*下侧显示时 距离表单域的距离*/
		//this.cfg.marginB = (nc!=null&&nc.marginB!=null?nc.marginB:5);/*上侧显示时 距离表单域的距离*/
		this.cfg.aen=[];/*自动验证单项的触发事件,多个之间用英文半角逗号隔开*/
		if(nc==null || nc.aen==null){
			this.cfg.aen.push("blur");
		}else{
			this.cfg.aen.concat(nc.aen);
		}
	};
	/**计算元素绝对坐标*/
	this.calcPosition=function(obj){
		var pt={x:0,y:0}, oTmp=obj;
		do{
			pt.x += oTmp.offsetLeft;
			pt.y += oTmp.offsetTop;
			oTmp = oTmp.offsetParent;
			if(!oTmp){break;}
		}while(oTmp.tagName.toUpperCase()!="BODY" && oTmp.tagName.toUpperCase()!="HTML");
		return pt;
	};

	/*
	* 添加事件
	* 参数
	*	eventName 验证时机: change,blur,focus,keyup,keydown,keypress,click,dblclick,select
	*/
	this.addEvent=function(name,eventName,func){
		var el=((typeof name).toLowerCase()=="string")?this.e(name):name,obj=null;
		obj=(el&&el.length&&el.length>1)?el[0]:el;
		if(obj){
			if(func==undefined){
				func=function(event){
					this.form.xyv.validateElement(this);
				}
			}
			/*this.addEvent(e,eventName,func);*/
			if(obj.addEventListener){
				obj.addEventListener(eventName,func, false);
			}else if(obj.attachEvent){
				obj.attachEvent("on"+eventName,function(){func.call(obj);});
			}else{
				obj["on"+eventName]=function(){func.call(obj)};
			}
		}
		return this;
	};
	this.ae=this.addEvent;

	/*
	* 处理验证属性
	*/
	this.parseRule=function(e,rule){
		if(rule!=null){
			//局部变量覆盖全局变量
            if(!rule.tip)rule.tip=null;
            if(!rule.cssPair){
				rule.cssPair=this.cfg.cssPair;
			}else{
				for(var tmp_0 in this.cfg.cssPair){
					if(rule.cssPair[tmp_0]==undefined){
						rule.cssPair[tmp_0]=this.cfg.cssPair[tmp_0];
					}
				}
			}
            if(!rule.type)rule.type="err";
            if(!rule.aen)rule.aen=this.cfg.aen.join(",");/*attach event name*/
			if(!rule.tlct)rule.tlct=this.cfg.tlct;
			if(!rule.marginL)rule.marginL=this.cfg.marginL;
			if(!rule.marginT)rule.marginT=this.cfg.marginT;
			//if(!rule.marginB)rule.marginB=this.cfg.marginB;
			switch(rule.tlct){
			case "top":
			case "right":
			case "buttom":
				break;
			default:
				rule.tlct=this.cfg.tlct;break;
			}
            switch(rule.cmd){
			case "req":/*必须填写字段*/
			case "required":
                break;
			case "alpha":/*字母*/
			case "alphabetic":
				break;
			case "int":/*整数*/
			case "Integer":
                if(!rule.maxv)rule.maxv=null;
                if(!rule.minv)rule.minv=null;
				break;
			case "email":/*电子邮件地址*/
				break;
			case "CN_mobile":/*中国手机*/
				break;
			case "CN_tel":/*中国固话*/
				break;
			case "len":/*长度*/
			case "length":
                if(!rule.minl)rule.minl=null;
                if(!rule.maxl)rule.maxl=null;
				break;
			case "dec":
			case "decimal":/*小数*/
                if(!rule.scalar)rule.scalar=null;
                if(!rule.maxv)rule.maxv=null;
                if(!rule.minv)rule.minv=null;
				break;
			case "date":/*日期*/
                if(!rule.begin)rule.begin=null;
                if(!rule.end)rule.end=null;
				break;
			case "regexp":/*正则表达式*/
                if(!rule.regexp)rule.regexp=null;
                if(!rule.reverse)rule.reverse=false;
				break;
			case "checked":/*checkbox*/
                if(!rule.minChkNum)rule.minChkNum=null;
				break;
			case "selected":/*radio*/
                if(!rule.minChkNum)rule.minChkNum=1;
				break;
			case "function":/*function*/
                if(!rule.fun)rule.fun=function(){return true;};
				this.vfset[e.name]={e:e,tip:rule.tip,tlct:rule.tlct,vf:false};
				break;
			}
        }
		return rule;
	};

	/*
	* 添加验证规则
	*/
	this.add=function(name, valtObj){
		var el=this.e(name),e=null;
		if(el){
			if(el.tagName!=undefined){
				switch(el.tagName.toLowerCase()){
				case "select":
				case "input":
				case "textarea":
					e=el;
					break;
				default:
					e=el;
					break;
				}
			}else{
				if(el.length!=undefined){
					e = el[el.length-1];
					switch(e.tagName.toLowerCase()){
					case "select":
					case "input":
					case "textarea":
						break;
					default:
						e=el;
						break;
					}
				}else{
					e=el;
				}
			}
		}
		if(e){
			var vs;
			if(!e.validateSet){
				e.validateSet=[];
				this.addEvent(e, 'focus',function(){
					this.form.xyv.hide(this.getAttribute("name"));
				});
			}
			e.validateSet.push(this.parseRule(e,valtObj));
			if(this.cfg.autoCheck==true && e.validateSet.length==1){
				var aenArr=valtObj.aen.split(",");
				for(var i=0;i<aenArr.length;i++){
					this.addEvent(e, aenArr[i]);
				}
			}
		}
		return this;
	};
	
	/**显示默认提示信息*/
	this.showTip=function(arg0, arg1){
		if(arg0 && arg1 && arg1.tip){
			var obj=this.e(arg0);
			if(obj){
				if(!arg1.type){arg1.type="info";}
				this.show(obj, this.parseRule(obj, arg1));
			}
		}
	};

	/**显示消息窗口*/
	this.show=function(obj, vo){//e, vo
		var divID="xyv_"+obj.form.id+"__yt_"+obj.name;
		var divC=document.getElementById(divID);
		if(vo.tip!=null){
			var pt=this.calcPosition(obj);
			if(divC!=undefined){
				document.getElementsByTagName("BODY")[0].removeChild(divC);
				divC=null;
			}
			divC=document.createElement("DIV");
			var cssArr={display:"block",position:"absolute",width:"auto",cursor:"pointer","z-index":"99",width:"auto",height:"24px","line-height":"24px",padding:"0 5px",_top:"2px"};
			switch(vo.type){
			case "err":
				cssArr["color"]="#e00";
				cssArr["border"]="1px solid #EED97C";
				divC.className="tip_err";
				break;
			case "correcct":
				cssArr["color"]="#309100";
				cssArr["border"]="0px solid #fff";
				divC.className="tip_correcct";
				break;
			case "info":
			default:
				cssArr["color"]="#666";
				cssArr["border"]="1px solid #ddd";
				divC.className="tip_info";
				break;
			}
			if(vo.cssPair){
				for(var tmp_0 in vo.cssPair)
					cssArr[tmp_0]=vo.cssPair[tmp_0];
			}
			var cssText0=[];
			for(var tmp_1 in cssArr){
				cssText0.push(tmp_1+":"+cssArr[tmp_1]);
			}
			divC.style.cssText=cssText0.join(";");
			divC.setAttribute("id", divID);
			switch(vo.tlct){
			case "right":/*右侧显示窗口***********************************************/
				divC.innerHTML=vo.tip;
				document.getElementsByTagName("BODY")[0].appendChild(divC);
				var _fix_h=(obj.offsetHeight-divC.offsetHeight)/2;
				divC.style.top=pt.y+_fix_h+"px";
				divC.style.left=(pt.x+parseInt(obj.offsetWidth)+vo.marginL)+"px";
				/**修正位置*/
				break;
			case "top":/*上部显示窗口***********************************************/
				divC.innerHTML=vo.tip;
				document.getElementsByTagName("BODY")[0].appendChild(divC);
				divC.style.top=(pt.y-obj.offsetHeight-vo.marginT)+"px";
				divC.style.left=(pt.x+parseInt(obj.offsetWidth*0.7))+"px";
				/**修正位置*/
				var _h_c=Math.abs(divC.offsetHeight-49);
				if(_h_c>10 && (divC.offsetTop-_h_c>0)){
					divC.style.top=(divC.offsetTop-_h_c)+"px";
				}
				break;
			case "buttom":/*下部显示窗口***********************************************/
				divC.innerHTML=vo.tip;
				document.getElementsByTagName("BODY")[0].appendChild(divC);
				divC.style.top=(pt.y+obj.offsetHeight+vo.marginT)+"px";
				divC.style.left=(pt.x+parseInt(obj.offsetWidth*0.7))+"px";
				/**修正位置*/
				//div2.style.marginTop="-"+(div1.offsetHeight+6)+"px";
				/*var _h_c=Math.abs(divC.offsetHeight-49);
				if(_h_c>10 && (divC.offsetTop-_h_c>0)){
					divC.style.top=(divC.offsetTop-_h_c)+"px";
				}*/
				break;
			}
			this.addEvent(divC,'click',function(){
				document.getElementsByTagName("BODY")[0].removeChild(this);
			});
			this.showedNameMap[obj.name]=true;
		}
	};

    /**隐藏消息窗口*/
	this.hide=function(name){
		var obj=this.e(name);
		var div=document.getElementById("xyv_"+this.form.id+"__yt_"+name);
		if(div!=undefined){
			div.parentNode.removeChild(div);
		}
		this.showedNameMap[obj.name]=false;
	};
    /**隐藏全部消息窗口*/
	this.hideAll=function(){
		for(var name in this.showedNameMap){
			this.hide(name);
		}
	};
    /*处理时间*/
	this.parseDate=function(s){
		var arr1=s.split(" "),arr2=[];if(arr1.length==0)return undefined;
		if(arr1.length>0){
			var aa=arr1[0].split("\-");
			if(aa.length!=3)return undefined;
			arr2.push(parseInt(aa[0]));
			arr2.push(parseInt(aa[1].indexOf("0")==0?aa[1].substring(1):aa[1])-1);
			arr2.push(parseInt(aa[2].indexOf("0")==0?aa[2].substring(1):aa[2]));
		}
		if(arr1.length>1){
			var aa=arr1[1].split(":");
			if(aa.length!=3)return undefined;
			arr2.push(parseInt(aa[0].indexOf("0")==0?aa[0].substring(1):aa[0]));
			arr2.push(parseInt(aa[1].indexOf("0")==0?aa[1].substring(1):aa[1]));
			arr2.push(parseInt(aa[2].indexOf("0")==0?aa[2].substring(1):aa[2]));
		}
		for(var i=0;i<arr2.length;i++)
			if(isNaN(arr2[i]))return undefined;
		var date=new Date(0);
		if(arr2.length>2){
			date.setFullYear(arr2[0]);date.setMonth(arr2[1]);date.setDate(arr2[2]);
		}
		if(arr2.length>5){
			date.setHours(arr2[3]);date.setMinutes(arr2[4]);date.setSeconds(arr2[5]);
		}else{date.setHours(0);}
		return date;
	};
    /*测试必须填写*/
	this.testRequired=function(obj, vo){
		var val=obj.value.replace(/^\s+|\s+$/g,"");/*去空格*/
		if(eval(val.length)==0){
			if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须填写或选择!";
			this.show(obj, vo);
			return false;
		}
		return true;
	};
	this.testInteger=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(/^[\-]?[\d]{1,}$/.test(val)==false){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须是非零开始的数字!";
				this.show(obj, vo);
				return false;
			}
			if(vo.minv!=undefined && !(parseInt(val)>parseInt(vo.minv)) ){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须大于 "+vo.minv+" !";
				this.show(obj, vo);
				return false;
			}
			if(vo.maxv!=undefined && !(parseInt(val)<parseInt(vo.maxv)) ){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须小于 "+vo.maxv+" !";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testAlphabetic=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(/^[a-zA-Z]{1,}$/.test(val)==false){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须全部是由字母组成!";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testEmail=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(/^[\w-.]+@(([a-zA-Z0-9]{1}[a-zA-Z0-9\-]{0,61})?[a-zA-Z0-9]{1}\.)+[a-zA-Z]{1}[a-zA-Z]+$/.test(val)==false){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须是合法的电邮地址!";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testCNMobile=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(/^1[3458]{1}[\d]{9}$/.test(val)==false){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须是合法的手机号码!";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testCNTel=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(/^(0[0-9]{2,3}\-)?[2-9]{1}[\d]{6,7}(\-[\d]{1,})?$/.test(val)==false){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须是合法的固定电话!";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testLength=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(vo.maxl!=undefined && eval(val.length)>vo.maxl){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":内容文字长度不能大于 "+vo.maxl+" !";
				this.show(obj, vo);
				return false;
			}
		}
		if(vo.minl!=undefined && eval(val.length)<vo.minl){
			if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":内容文字长度不能小于 "+vo.minl+" !";
			this.show(obj, vo);
			return false;
		}
		return true;
	};
	this.testDecimal=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(val.indexOf(".")==-1){obj.value=obj.value+".0";val=obj.value;}
			if(!(/^[\-]?0\.[\d]{1,}$/.test(val) || (/^[\-]?[1-9]{1}[\d]{0,}\.[\d]{1,}$/.test(val))) ){/*判断是否是合法小数*/
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":不是一个合法小数!";
				this.show(obj, vo);
				return false;
			}
			if(vo.scalar==undefined)vo.scalar=2;
			if((val.length-val.indexOf(".")-1)>vo.scalar){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须是一个有 "+vo.scalar+"位小数的数字!";
				this.show(obj, vo);
				return false;
			}
			if(vo.minv!=undefined && !(parseFloat(val)>vo.minv) ){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须大于 "+vo.minv+" !";
				this.show(obj, vo);
				return false;
			}
			if(vo.maxv!=undefined && !(parseFloat(val)<vo.maxv) ){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须小于 "+vo.maxv+" !";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testDate=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			var arr=val.split(" ");
			if(new RegExp("^(?:(?:([0-9]{4}(-|\/)(?:(?:0?[1,3-9]|1[0-2])(-|\/)(?:29|30)|((?:0?[13578]|1[02])(-|\/)31)))|([0-9]{4}(-|\/)(?:0?[1-9]|1[0-2])(-|\/)(?:0?[1-9]|1\\d|2[0-8]))|(((?:(\\d\\d(?:0[48]|[2468][048]|[13579][26]))|(?:0[48]00|[2468][048]00|[13579][26]00))(-|\/)0?2(-|\/)29))))$").test(arr[0])==false){/*判断是否是合法日期*/
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":不是一个合法日期!";
				this.show(obj, vo);
				return false;
			}
			if(arr.length>1 && (/^([0-1]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]{1}$/.test(arr[1])==false)){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":不是一个合法日期!";
				this.show(obj, vo);
				return false;
			}
			var objDate=this.parseDate(val);
			if(vo.begin!=undefined && !(objDate.getTime()>=this.parseDate(vo.begin).getTime()) ){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须晚于 "+vo.begin+" !";
				this.show(obj, vo);
				return false;
			}
			if(vo.end!=undefined && !(this.parseDate(vo.end).getTime()>=objDate.getTime()) ){
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":必须早于 "+vo.end+" !";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testRegExp=function(obj, vo){
		var val=obj.value;
		if(eval(val.length)>0){
			if(new RegExp(vo.regexp).test(val)==(!vo.reverse)){/*判断是否符合正则表达式*/
				if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":内容不匹配给定的正则表达式!";
				this.show(obj, vo);
				return false;
			}
		}
		return true;
	};
	this.testChecked=function(obj, vo){
		var ostc=obj.form.elements[obj.name],chkn=0, flag=true;
		if(vo.minChkNum==undefined)vo.minChkNum=1;
		if(ostc.length){
			for(var c=0;c<ostc.length;c++){
				if(ostc[c].checked){
					++chkn;if(chkn<vo.minChkNum){continue;}else{break;}
				}
			}
		}else{
			if(vo.minChkNum>1){
				flag=false;
			}else{
				flag=ostc.checked;
				if(flag)chkn++;
			}
		}
		if(chkn<vo.minChkNum){
			flag = false;
		}
		if(flag==false){
			if(!vo.tip||vo.tip.length==0)vo.tip=obj.name+":至少选择"+vo.minChkNum+"项!";
			this.show(obj, vo);
		}
		return flag;
	};
	this.testFunction=function(obj, vo){
		var val=obj.value,fl=true;
		if(eval(val.length)>0){
			if((typeof vo.fun).toLowerCase()=="function"){
				vo.fun.call(this, obj);
			}
		}
		return true;
	};
	this.setVF=function(obj, fl, err){
		var vfo=this.vfset[obj.name];
		if(vfo!=undefined){
			vfo.vf=fl;
			if(err!=undefined)vfo.tip=err;
		}
	};
	this.getVF=function(name){
		return this.vfset[name];
	};
	this.addErr=function(name, msg){
		this.extErrSet[name]=msg;
	};
	/*验证单个对象*/
	this.validateElement=function(e){
		var vs=null, vflag=true;
        if(e.validateSet){
            vs=e.validateSet;
        }
		if(vs==undefined || vs==null)return true;/*此对象没有验证规则*/
		if(this.cfg.trim)if(e.tagName.toLowerCase()=="input"||e.tagName.toLowerCase()=="textarea")e.value=e.value.replace(/^\s+|\s+$/g,"");
		for(var j=0;j<vs.length;j++){
			var vo=vs[j];
			switch(vo.cmd){
			case "req":/*必须填写字段*/
			case "required":
				vflag=this.testRequired(e, vo);
				break;
			case "int":/*整数*/
			case "Integer":
				vflag=this.testInteger(e, vo);
				break;
			case "alpha":/*字母*/
			case "alphabetic":
				vflag=this.testAlphabetic(e, vo);
				break;
			case "email":/*电子邮件地址*/
				vflag=this.testEmail(e, vo);
				break;
			case "mobile":/*中国手机*/
			case "CN_mobile":
				vflag=this.testCNMobile(e, vo);
				break;
			case "tel":/*中国固话*/
			case "CN_tel":
				vflag=this.testCNTel(e, vo);
				break;
			case "len":/*长度*/
			case "length":
				vflag=this.testLength(e, vo);
				break;
			case "dec":
			case "decimal":/*小数*/
				vflag=this.testDecimal(e,vo);
				break;
			case "date":/*日期*/
				vflag=this.testDate(e, vo);
				break;
			case "regexp":/*正则表达式*/
				vflag=this.testRegExp(e, vo);
				break;
			case "checked":/*checkbox*/
				vflag=this.testChecked(e, vo);
				break;
			case "selected":/*radio*/
				vflag=this.testChecked(e, vo);
				break;
			case "function":/*function*/
				vflag=this.testFunction(e, vo);
				break;
			}
            if(vflag==false)break;
		}
		if(vflag==true){
			this.hide(e.getAttribute("name"));
			if(e.tagName.toLowerCase()=="input" && (e.getAttribute("type").toLowerCase()=="radio"||e.getAttribute("type").toLowerCase()=="checkbox")){
				;
			}else if(this.cfg.sct && e.value.length>0){
				var rule0=this.clone(e.validateSet[0]);
				rule0.type="correcct";
				rule0.tip="";
				this.show(e, rule0);
			}
			var tmpA=[];
			for(var k=0;k<this.errArray.length;k++){
				if(this.errArray[k].name==e.getAttribute("name")){
					;
				}else{
					tmpA.push(this.errArray[k]);
				}
			}
			this.errArray=tmpA;
		}else{
			var pstn=this.calcPosition(e);
			this.errArray.push({name:e.getAttribute("name"),position:pstn});
			if(this.errArray.length==1){
				window.scrollTo(0, pstn.y<10?pstn.y:(pstn.y-10));
			}
		}
		return vflag;
	}
	/*处理表单onsubmit事件 验证所有规则*/
	this.validate=function(){
		this.xyv.param={map:[],str:"",po:"",bpo:function(){
			var ab={};
			for(var i=0;i<this.map.length;i++){
				var cc=this.map[i];
				ab[cc.n]=cc.v;
			}
			return ab;
        }};
		this.xyv.errArray=[];/*不能通过验证的错误提示数组{name,position}*/
		var el=this.elements,vf=true,tvf=true;
		for(var i=0;i<el.length;i++){
			var e=el[i];
			vf=this.xyv.validateElement(e);
			if(vf && e.name && e.name.length>0){
				var t=e.getAttribute("type"),n=true;
				if(t){
					if(t=="checkbox"||t=="radio"){
						if(!e.checked){n=false;}
					}
				}
				if(n){
					this.xyv.param.map.push({n:e.name,v:e.value});
					var str=this.xyv.param.str, str2=this.xyv.param.po;
					str+=((str.length>0?"&":"")+e.name+"="+encodeURIComponent(e.value));
					this.xyv.param.str=str;
				}
			}
			if(!vf){
				tvf=false;
				if(this.xyv.cfg.short){
					break;
				}
			}
		}
		if(tvf){
			if(this.xyv.vfset.length>0){//如果有 function 和 ajax类型的验证 则校验
				for(var i=0;i<this.xyv.vfset.length;i++){
					if(this.xyv.vfset[i].vf==false){
						this.xyv.show({e:this.xyv.vfset[i].e,msg:this.xyv.vfset[i].tip,tlct:this.xyv.vfset[i].tlct,mt:vo.type});
						return false;
					}
				}
			}
			return cbf==undefined?true:cbf.call(this.xyv);
		}else{
			return false;
		}
	}
	this.form=document.forms[formId];
	if(this.form==undefined){
		alert("找不到表单!");
	}else{
		this.initCfg(cfg);
		this.form.xyv=this;
		this.form.onsubmit=null;
		this.form.onsubmit=this.validate;
	}
}