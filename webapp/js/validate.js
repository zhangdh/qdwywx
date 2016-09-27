   /*
	*--------------- 客户端表单通用验证CheckForm(oForm) -----------------
	* 功能:通过正则表达式实现通用验证所有的表单元素.
	* 使用:
	*　<form name="form1" onsubmit="return CheckForm(this)">
	*　　	<input type="text" name="name" check="notBlank" required="true" showName="用户姓名">
	*　　	<input type="submit">
	*　</form>
	* 通过对required的属性设置true或false，断定该项是否必填.
	* showName为alert时候要显示的对象.
	* check的属性设置重点说明如下:
	*		对于不带参数的规则，可直接设置规则名，如notBlank或者notBlank().
	*		对于带参数的规则，需要设置对应的规则参数，如isDate('YYYY-MM-DD').
	*		对于本JS文件未提供的正则类型判定，可自行设置正则表达式验证，如验证三到五位的字符，设置reg('^\\S{3,5}$') .
	*		可给check设置多个规则，各个规则之间使用";"隔开，如"不能含有空格，且字数不能超过10",设置"notBlank;isString('#',10)".
	* 规则函数的设定，有两种方式,说明如下:
	*		1.正则验证，返回数组rt，rt[0]为对应的正则表达式，rt[1]为对应要警告的语句.
	*		2.普通的判断function，返回false或true，函数体中通过this.value和this.showName获取页面数据，自行判定value的合法性.
	*
	*--------------- 客户端表单通用验证CheckForm(oForm) -----------------
	*/
///////////////////////////////////////////////////////////////////////////////////
 Validate = {
	value:"",
	showName:"",
	setColor : true,//错误项是否显示颜色
	color : "red",
　　//主函数
　　CheckForm:function(oForm)
　　{
　　　　var els = oForm.elements;
　　　　//遍历所有表元素
　　　　for(var i=0;i<els.length;i++)
　　　　{
			if(this.setColor)		
				els[i].style.borderColor="";

			//var required = els[i].required;
			var required = "";
			if(els[i].id ){
				required = this.isRequired(els[i]);
			}
			var check = els[i].check;//原有值		
			if(required!="undefined"&&(required==""||required=="true"||required=="yes"||required==true)&&required!=false){
				//必填项
				els[i].check = "required";
				if(!this.validate(els[i])){
					els[i].check = check; 		
					return false;
				}
			}
			//如果有值，则根据正则表达式验证
			els[i].check = check; 
			if(this.GetValue(els[i])!=""){
				if(!this.validate(els[i])){
					return false;
				}
			}
　　　　}
		return true;
　　},
	validate:function(ele){
		if(!ele.check){
			return true;
		}
		//取得表单的值,用通用取值函数			
		var sVal = this.GetValue(ele);
		this.value = sVal;
		this.showName="";
		if($(ele).attr('showName')!=null&&$(ele).attr('showName')!="undefined"){
			this.showName = "“"+$(ele).attr('showName')+"”";
		}

		var checkArr = ele.check.split(";");
		for (var i=0;i<checkArr.length ;i++ )
		{	
			if(checkArr[i]=="") continue;
			//取得验证的规则函数
			var sReg = "";
			if(ele.check){
				try{
	　　　　　　　　		sReg = this.executeFunc(checkArr[i]);
				}catch(e){
					alert(e+"使用错误:类型'"+checkArr[i]+"'对应的验证规则函数未定义！");
					return false;
					//sReg = ele.check;
				}
			}
			if(sReg==null||sReg=="undefined"){
				continue;
			}
			if(sReg==false||sReg=="false"){
				this.GoBack(ele) 
		　　　　return false;	
			}
			if(sReg[0]==""){
				continue;
			}

			//判定是否符合正则表达式
			if(!this.regValidate(this.value,sReg[0])){
		　　　　//验证不通过,弹出提示warning
				var msg = this.showName;
				if(ele.check){
					try{
						msg += sReg[1];
					}catch(e){
						msg += "格式不正确！";
					}
				}
				alert(msg);
				//该表单元素取得焦点,用通用返回函数
				this.GoBack(ele) 
		　　　　return false;
			}
		}
		return true;
	},
　　//通用取值函数分三类进行取值
　　//文本输入框,直接取值el.value
　　//单多选,遍历所有选项取得被选中的个数返回结果"00"表示选中两个
　　//单多下拉菜单,遍历所有选项取得被选中的个数返回结果"0"表示选中一个
　　GetValue:function (el)
　　{
　　　　//取得表单元素的类型
　　　　var sType = el.type;
　　　　switch(sType)
　　　　{
　　　　　　case "text":
　　　　　　case "hidden":
　　　　　　case "password":
　　　　　　case "file":
　　　　　　case "textarea": return el.value;
　　　　　　case "checkbox":
　　　　　　case "radio": return GetValueChoose(el);
　　　　　　case "select-one":
　　　　　　case "select-multiple": return GetValueSel(el);
　　　　}
　　　　//取得radio,checkbox的选中数,用"0"来表示选中的个数,我们写正则的时候就可以通过0{1,}来表示选中个数
　　　　function GetValueChoose(el)
　　　　{
　　　　　　var sValue = "";
　　　　　　//取得第一个元素的name,搜索这个元素组
　　　　　　var tmpels = document.getElementsByName(el.name);
　　　　　　for(var i=0;i<tmpels.length;i++)
　　　　　　{
　　　　　　　　if(tmpels[i].checked)
　　　　　　　　{
　　　　　　　　　　sValue += "0";
　　　　　　　　}
　　　　　　}
　　　　　　return sValue;
　　　　}
　　　　//取得select的选中数,用"0"来表示选中的个数,我们写正则的时候就可以通过0{1,}来表示选中个数
　　　　function GetValueSel(el)
　　　　{
　　　　　　var sValue = "";
　　　　　　for(var i=0;i<el.options.length;i++)
　　　　　　{
　　　　　　　　//单选下拉框提示选项设置为value=""
　　　　　　　　if(el.options[i].selected && el.options[i].value!="")
　　　　　　　　{
　　　　　　　　　　sValue += "0";
　　　　　　　　}
　　　　　　}
　　　　　　return sValue;
　　　　}
　　},
　　//通用返回函数,验证没通过返回的效果.分三类进行取值
　　//文本输入框,光标定位在文本输入框的末尾
　　//单多选,第一选项取得焦点
　　//单多下拉菜单,取得焦点
　　GoBack:function (el)
　　{
　　　　//取得表单元素的类型
　　　　var sType = el.type;
　　　　switch(sType)
　　　　{
　　　　　　case "text":
　　　　　　case "hidden":
　　　　　　case "password":
　　　　　　case "file":
　　　　　　case "textarea":
				try{
					el.focus();var rng = el.createTextRange(); rng.collapse(false); rng.select();
					if(this.setColor){
						el.style.borderColor=this.color;
					}
				}catch(e){}
				break;
　　　　　　case "checkbox":
　　　　　　case "radio": 
				try{
					var els = document.getElementsByName(el.name);els[0].focus();	
				}catch(e){}
				break;
　　　　　　case "select-one":
　　　　　　case "select-multiple":
				try{
					el.focus();	
				}catch(e){}					
　　　　}
　　},

	executeFunc:function (name){
		if(name.indexOf("(")==-1){
			return eval("this."+name+"()");
		}else{
			return eval("this."+name);
		}
	},
	//去除空格
	trim:function (value){
		return value.replace(/(^\s*)|(\s*$)/g, "");
	},
	//判定某个值与表达式是否相符
	regValidate:function (value,sReg){
		//字符串->正则表达式,不区分大小写
		var reg = new RegExp(sReg ,"i");
		if(reg.test(value)){
			return true;
		}else{
			return false;
		}
	},
	//判定某个数值与某个验证函数是否相符
	checkValue:function (value,func){
		this.value = value;
		var rt = "";
		try{
			rt = this.executeFunc(func);
		}catch(e){
			alert(e+"使用错误:函数"+func+"未定义！");
			return false;
			//sReg = ele.check;
		}
		
		if(rt==null||rt=="undefined"){
				return true;
		}
		if(rt==false||rt=="false"){
	　　　　return false;	
		}
		//验证表达式
		var check = this.regValidate(value,rt[0]);
		if(!check&&this.showName!=""){
			alert(this.showName+rt[1]);
		}
		return check;
	},
	//检查某个元素与某个验证函数是否相符,需要传入元素的ID
	checkElement:function (id,func,showName){
		var obj = document.getElementById(id);
		if(obj==null||obj=="undefined"){
			//alert("对应id:"+id+"的元素不存在！");
			return false;
		}
		if(this.setColor){
			obj.style.borderColor="";
		}
		if(showName!=null){
			this.showName = "“"+showName+"”";
		}else{
			this.showName = "";
		}

		var value = obj.value;
		var check = this.checkValue(value,func);
		if(!check){
			this.GoBack(obj);
		}
		return check;
	},
	//检查某个对象(checkbox/select-multiple)至少选择个数,需要传入元素的NAME
	checkSelectByName:function (name,num,showName){
		var obj = document.getElementsByName(name);
		if(obj.length==0){
			//alert("对应name:"+name+"的元素不存在！");
			return false;
		}
		if(showName!=null){
			this.showName = "“"+showName+"”";
		}else{
			this.showName = "";
		}

		var check = this.checkValue(this.GetValue(obj[0]),"select("+num+")");
		if(!check){
			this.GoBack(obj[0]);
		}
		
		return check;
	},
	//获取某个元素的值
	elementValue:function (id){
		var obj = document.getElementById(id);
		if(obj==null||obj=="undefined"){
			return "";
		}
		return obj.value;
	},
//////////////////////////////////验证规则定义////////////////////////////////
	
	reg:function (sReg){
		var rt = new Array();
		rt[0] = sReg;
		rt[1] = "格式不匹配正则表达式:"+sReg;
		return rt;
	},
	notBlank:function (){
		var rt = new Array();
		rt[0] = "^\\S+$";
		rt[1] = "不能为空,且不能含有空格!"; 
		return rt;
	},	
	required:function (){
		var rt = new Array();
		rt[0] = "^\\S";
		rt[1] = "不能为空!"
		return rt;
	},
	isDate:function (fmt){
		var regex = new Array("-",".","/");
		var regex0 = "";
		for (var i=0;i<regex.length ;i++ ){
			if(fmt.indexOf(regex[i])!=-1){
				regex0 = regex[i];
				break;
			}
		}
		var y = 0;
		var m = 0;
		var d = 0;
		var number = new Array(0,0,0);
		var ch = '';
		var index = 0;
		for (var i=0;i<fmt.length ;i++ )
		{
			if(ch==''||fmt.charAt(i)==ch){
				number[index] = number[index]+1;
			}else if(fmt.charAt(i)!=regex0){
				index++;
				number[index] = number[index]+1;
			}
			ch = fmt.charAt(i);
			if(fmt.charAt(i)=='Y'||fmt.charAt(i)=='y'){
				y++;
			}
			if(fmt.charAt(i)=='M'||fmt.charAt(i)=='m'){
				m++;
			}
			if(fmt.charAt(i)=='D'||fmt.charAt(i)=='d'){
				d++;
			}
		}
		
		var rt = new Array();
		var sreg = "";
		for(i in number){
			if(number[i]!=0){
				if(sreg!=""){
					sreg += regex0;	
				}
				sreg += "\\d{"+number[i]+"}"; 
			}
		}
		sreg = "^"+sreg+"$";
		rt[0] = sreg;
		rt[1] = "格式应为"+fmt;
		return rt;
	},	
	/**
	 * 字符串判定
	 * 如min设为"'#'",表示字数不能大于max
	 * 如max设为"'#'",表示字数不能小于min
	 */
	isString:function (min,max){
		var rt = new Array();
		if(min==null&&max==null){
			rt[0] = "";
			rt[1] = "";

			return rt;
		}
		if(max==null)
			max=min;
		if(min=="#"&&max=="#"){
			//任意字符
			rt[0] = "[\\S|\\s]";
			rt[1] = "";
			return rt;
		}
		if(min=="#"){
			rt[0] = "^[\\S|\\s]{0,"+max+"}$"; 
			rt[1] = "字数不能大于"+max;
			return rt;
		}
		if(max=="#"){
			rt[0] = "^[\\S|\\s]{"+min+",}$";
			rt[1] = "字数不能小于"+min;
			return rt;
		}
		rt[0] = "^[\\S|\\s]{"+min+","+max+"}$"; 
		if(min==max){
			rt[1] = "字数应为"+min+"个！";
		}else{
			rt[1] = "字数介于"+min+"和"+max+"之间！";
		}
		return rt;
	},
	strStartsWith:function (str){
		if(this.value.indexOf(str)!=0){
			alert(this.showName+"必须以字符‘"+str+"’开头！");
			return false;
		}
	},
	isContains:function (str){
		if(this.value.indexOf(str)==-1){
			alert(this.showName+"必须包含字符‘"+str+"’！");
			return false;
		}
	},	
	strEndsWith:function (str){
		if(this.value.lastIndexOf(str)+str.length!=this.value.length){
			alert(this.showName+"必须以字符‘"+str+"’结束！");
			return false;
		}
	},
	//判断email
	isEmail:function (){
		var rt = new Array();
		rt[0] = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		rt[1] = "应为正确的EMAIL格式！";
		
		return rt;
	},	
	//只能输入中文
	onlyZh:function (){
		var rt = new Array();
		rt[0] = "^[\u0391-\uFFE5]+$";
		rt[1] = "应输入中文！";
		return rt;
	},	
	//只可输入英文
	onlyEn:function (){
		var rt = new Array();
		rt[0] = "^[A-Za-z]+$";
		rt[1] = "只能输入英文！";
		return rt;
	},	
	enOrNum:function (){
		var rt = new Array();
		rt[0] = "^[A-Za-z0-9]+$";
		rt[1] = "只能输入英文和数字,且不能有空格！";
		
		return rt;
	},
	/**
	 * 整数的判定
	 * @param type
	 *		为空		任意整数
	 *		'0+'	非负整数
	 *		'+'		正整数
	 *		'-0'	非正整数
	 *		'-' 	负整数
	 */
	isInt:function (type){
		var rt = new Array();
		if(type=="0+"){
			rt[0] = "^\\d+$";
			rt[1] = "应输入非负整数!";
		}else if(type=="+"){
			rt[0] = "^\\d*[1-9]\\d*$";
			rt[1] = "应输入正整数!";
		}else if(type=="-0"){
			rt[0] = "^((-\\d+)|(0+))$";
			rt[1] = "应输入非正整数!";
		}else if(type=="-"){
			rt[0] = "^-\\d*[1-9]\\d*$";
			rt[1] = "应输入负整数!";
		}else{
			rt[0] = "^-?\\d+$";
			rt[1] = "应输入整数值！";
		}
		return rt;
	},
	/**
	 * 浮点数的判定
	 * @param type
	 *		为空		任意浮点数
	 *		'0+'	非负浮点数
	 *		'+'		正浮点数
	 *		'-0'	非正浮点数
	 *		'-'		负浮点数
	 */
	isFloat:function (type){
		var rt = new Array();
		if(type=="0+"){
			rt[0] = "^\\d+(\.\\d+)?$";
			rt[1] = "应输入非负浮点数!";
		}else if(type=="+"){
			rt[0] = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";
			rt[1] = "应输入正浮点数!";
		}else if(type=="-0"){
			rt[0] = "^((-\\d+(\.\\d+)?)|(0+(\\.0+)?))$";
			rt[1] = "应输入非正浮点数!";
		}else if(type=="-"){
			rt[0] = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";
			rt[1] = "应输入负浮点数!";
		}else{
			rt[0] = "^(-?\\d+)(\\.\\d+)?$";
			rt[1] = "应输入浮点数值！";
		}
		return rt;
	},	
	/**
	 * 数字大小判定
	 * 如min设为"'#'",表示不能大于max
	 * 如max设为"'#'",表示不能小于min
	 */
	setNumber:function (min,max){
		
		if(!this.regValidate(this.value,"^(-?\\d+)(\\.\\d+)?$")){
			alert(this.showName+"应输入数字！");
			return false;
		}
		if(min==null&&max==null){
			//任意数字,不判定范围
			return isFloat();
		}
		if(max==null)
			max=min;

		if(min=="#"&&max=="#"){
			//任意数字,不判定范围
			return isFloat();
		}
		if(min=="#"){
			if(this.value>max){
				alert(this.showName+"不能大于"+max);	
				return false;
			}
		}
		if(max=="#"){
			if(this.value<min){
				alert(this.showName+"不能小于"+min);	
				return false;
			}
		}
		if(this.value<min||this.value>max){
			if(min==max){
				alert(this.showName+"的值应为"+min);	
			}else{
				alert(this.showName+"应介于"+min+"和"+max+"之间！");	
			}
			return false;
		}
		return true;
	},
	isPhone:function (){
		var rt = new Array();
		rt[0] = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$";
		rt[1] = "应输入正确的电话号码格式！";
		return rt;
	},
	isMobile:function (){
		var rt = new Array();
		rt[0] = "^((\\(\\d{2,3}\\))|(\\d{3}\\-))?1\\d{10}$";
		rt[1] = "应输入正确的手机号码格式！";
		return rt;
	},
	isUrl:function (){
		var rt = new Array();
		rt[0] = "^http[s]?:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\\?%\\-&_~`@[\\]\\':+!]*([^<>\"\"])*$";
		rt[1] = "应输入正确的URL（必须以http(s)://开头）！";
		return rt;
	},
	isZip:function (){
		var rt = new Array();
		rt[0] = "^[0-9]\\d{5}$";
		rt[1] = "应输入正确的编码格式！";
		return rt;
	},
	//目标至少需要选择(通常用于select-multiple/checkbox)
	select:function (num){
		var rt = new Array();
		rt[0] = "^0{"+num+",}$";
		rt[1] = "应至少选择"+num+"项！";
		return rt;
	},
	//验证是否是金额格式9,384,859.30
	isMoneyFmt: function() {
		var rt = new Array();
		rt[0] = "^[1-9][0-9]{0,2}(,[0-9]{3})*(\\.[0-9]{1,2})*$";
		rt[1] = "应输入正确的金额格式！[例如9,384,859.30]";
		return rt;
	},
	//IE10required不支持调整
	isRequired:function(el) {
		var required;
        if (document.querySelector) {
            required = $(el).attr("required");
            if (required === undefined || required === false) {
                return undefined;
            }
            return "true";
        } else {
            // IE6, IE7
            var outer = $(el).get(0).outerHTML, part = outer.slice(0, outer.search(/\/?['"]?>(?![^<]*<['"])/));
            return /\srequired\b/i.test(part)? "true": undefined;    
        }
	}
}
