<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ page import="com.ccoffice.util.cache.Cache"%>  
<%@ page import="com.ccoffice.bean.LoginUser"%> 
<%@ page import="java.util.Date"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/phone/phone.css" rel="stylesheet" type="text/css"> 
<style>
.top_right{
	position:absolute;
	right:5px;
	width:240px;
	height:65px;
	top:30px;
	color:#000079;
	font-weight:bolder;
}
.top_phone{
	position:absolute;
	right:0px;
	float:right;
	width:100px;
	align:center;
	 
}
.userInfo{
	position:absolute;
	left:5px;
	float:left;
	width:500px;
	height:26px;
	align:center;
}
.logo{
	position:absolute;
	background: url(resources/images/logo.png) no-repeat;
	margin-top:2px;
	left:10px;
	width:130px;
	height:58px;
}
.logo_text{
	position:absolute;
	background: url(resources/images/logo_text.png) no-repeat;
	top:10px;
	margin-top:0px;
	left:10px;
	width:750px;
	height:65px;
}
.top_left{
	position: absolute;
	padding:0px;
	top:0;
	left:0;
	width:650px;
	height: 65px;
	float:left;
	z-index:-1;
	background-color:#0EABF4;	 
}
.top_right{
	background: url('resources/images/top-3.jpg');
	position: absolute;
	float:right;
	height:65px;
	width:434px;
	top: 0px;
	right: 0px;
	z-index:-1;
}
.top_center{
	background-color:#0EABF4;	 
	height: 65px;
	padding:0px;
	margin-right: 434px;
	margin-left: 650px;
}
a{text-decoration: none;}
a:visited{color:#000079;}
a:link{color:#000079;} 
a { blr:expression(this.onFocus=this.blur()) }
</style>
<%
	String yhid=(String)request.getAttribute("yhid");
	LoginUser user = (LoginUser)Cache.getLoginInfo(yhid);
	String workNo = String.valueOf(Cache.getUserInfo(yhid,"101"));
%>
<script language="javascript">
	function exit(){
   		if(confirm("确认要退出系统吗？")){
   			parent.document.location.href="<%=request.getContextPath()%>/exit.do";
	 	}
   	}
</script>
<script>
	function btnclick(name){
		SoftPhone.BtnClick(name);
	}

	function onLoad(){
		var now=new Date()
		var week= "";
		if(now.getDay()==0){
			week = "星期日";
		}else if(now.getDay()==1){
			week = "星期一";
		}else if(now.getDay()==2){
			week = "星期二";
		}else if(now.getDay()==3){
			week = "星期三";
		}else if(now.getDay()==4){
			week = "星期四";
		}else if(now.getDay()==5){
			week = "星期五";
		}else if(now.getDay()==6){
			week = "星期五";
		}
   		var t = now.getFullYear()+"年"+(now.getMonth()+1)+"月"+now.getDate()+"日 "+week;
   		document.getElementById("timeInfo").innerHTML = t;
	}
	function inBound(caller,tmpfile1){
		parent.frame_left.openUrl("",caller+"来电","/cc/gd/xzgd.jsp?caller="+caller+"&tempfile1="+tmpfile1);
	}
	function addGd(){
		parent.frame_left.openUrl("","新增工单","/cc/gd/xzgd.jsp");
	}
</script>
</head>
<body onload="onLoad();">
<div style="height:65px;width:100%">
	<div class="top_left">
		<!-- <div id="logo" class="logo"></div> -->
		<div id="logo" class="logo_text"></div>
	</div>
	<div class="top_center"></div>
	<div class="top_right">
		<div style="width:60px; margin-top:15px;float:right;font-family:Microsoft Yahei;font-size:14px;font-weight:800;color:#529ADE'" onclick="exit()">
			<img src="resources/images/exit.png"><span >&nbsp;<a href='#'>退出</a></span>
		</div>
	</div>
</div>
<div style="height:26px;background-color:'#F7F7F7';overflow:hidden;line-height:26px;">
	 <div id="userInfo" class="userInfo">
		<img src="resources/images/user.png" style="margin-top:4px;margin-left:10px;"></img>
		<span style='height:100%;line-height:25px;font-family:Microsoft Yahei;font-size:14px;font-weight:800;color:#529ADE'>当前用户：<%=user.getName()%>&nbsp;&nbsp;</span>
		<span id="timeInfo" style='height:100%;line-height:25px;font-family:Microsoft Yahei;font-size:12px;font-weight:700;color:#529ADE'></span>
	 </div>
	 <div id="softphoneID" class="top_phone">
	 	
	 	<!-- <input type=button class="BtnConnect" id="BtnConnect"  name="BtnConnect" value="  签入"  onclick="btnclick(this,'BtnConnect')"/>
	 	
	 	<input type=button class="JX" id="JX"  name="JX" value="  就绪" disabled onclick="btnclick(this,'JX')"/>
	 	
	 	<INPUT  type=button class="BC"  value="  保持"  id=BC  name=BC   disabled onclick="btnclick(this,'BC')"> 
	 	
	 	<INPUT  type=button class="QBC"  value="  取保持"  id=QBC  name=QBC   disabled onclick="btnclick(this,'QBC')">
	 	
	 	<INPUT  type=button class="JJ"  value="  挂断"  id=JJ  name=JJ   disabled onclick="btnclick(this,'JJ')">
	 	
	 	<INPUT  type=button class="ZJ"  value="  转接"  id=ZJ  name=ZJ   disabled onclick="btnclick(this,'ZJ')">
	 	
	 	<INPUT  type=button class="JT"  value="  接听"  id=JT  name=JT   disabled onclick="btnclick(this,'JT')">
	 	
	 	<INPUT  type=button class="BtnMeet"  value="  会议"  id=BtnMeet  name=BtnMeet   disabled onclick="btnclick(this,'BtnMeet')">
	 	
	 	<INPUT  type=button class="ZWX"  value="  转外线"  id=ZWX  name=ZWX   disabled onclick="btnclick(this,'ZWX')">
	 	
	 	<INPUT  type=button class="BtnIvr"  value="  满意度"  id=BtnIvr  name=BtnIvr   disabled onclick="btnclick(this,'BtnIvr')">
	 	
	 	<INPUT  type=button class="WH"  value="  外呼"  id=WH  name=WH   disabled onclick="openTxl(this,'WH')"> -->
	 	
	 	<INPUT  type=button  class="add" value="  新增工单"     onclick="addGd()">
	 	
	 	
	</div>
	
</div>
<!-- <object id="SoftPhone" classid="clsid:02001C16-9B7B-4954-8289-E83C593DFE24" width="0" height="0" title=""></object> -->

<script language="javascript">	
	<%-- WorkNo = '<%=workNo%>';
	ServerIP= '172.16.99.1'; --%>
	/* if(WorkNo == "" || WorkNo == "null"){
		document.getElementById("softphoneID").style.display = "none";
	} */
</script> 
<script language="javascript" event="test(caller,tmpfile1)" for="SoftPhone">
	//来电事件
	inBound(caller,tmpfile1);
</script>
 
<script language="javascript" event="OnStatChange(a0,a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,l1,l2)" for="SoftPhone">
     	document.getElementById('BtnConnect').disabled = !a0;
        document.getElementById('BtnConnect').value = "  "+l1;
        document.getElementById('JX').disabled = !a1;
        if(a1){
        	document.getElementById('JX').className= "JX1";
        }else{
        	document.getElementById('JX').className= "JX";
        }
        
        document.getElementById('JX').value = "  "+l2;
 		document.getElementById('BC').disabled = !a2;
 		if(a2){
        	document.getElementById('BC').className= "BC1";
        }else{
        	document.getElementById('BC').className= "BC";
        }
 		document.getElementById('QBC').disabled = !a3;
 		if(a3){
        	document.getElementById('QBC').className= "QBC";
        }
 		
 		document.getElementById('JJ').disabled = !a4;
 		if(a4){
        	document.getElementById('JJ').className= "JJ1";
        }else{
        	document.getElementById('JJ').className= "JJ";
        }
  		document.getElementById('ZJ').disabled = !a5;
  		if(a5){
        	document.getElementById('ZJ').className= "ZJ1";
        }else{
        	document.getElementById('ZJ').className= "ZJ";
        }
    	document.getElementById('JT').disabled = !a6;
    	if(a6){
        	document.getElementById('JT').className= "JT1";
        }else{
        	document.getElementById('JT').className= "JT";
        }
 		document.getElementById('BtnMeet').disabled = !a7;
 		if(a7){
        	document.getElementById('BtnMeet').className= "BtnMeet1";
        }else{
        	document.getElementById('BtnMeet').className= "BtnMeet";
        }
    	document.getElementById('ZWX').disabled = !a8;
    	if(a8){
        	document.getElementById('ZWX').className= "ZWX1";
        }else{
        	document.getElementById('ZWX').className= "ZWX";
        }
        document.getElementById('BtnIvr').disabled = !a9;
        if(a9){
        	document.getElementById('BtnIvr').className= "BtnIvr1";
        }else{
        	document.getElementById('BtnIvr').className= "BtnIvr";
        }       
 		//document.getElementById('WH').disabled = !a10;
 		document.getElementById('WH').disabled = false;
 		if(a10){
        	document.getElementById('WH').className= "WH1";
        }else{
        	//document.getElementById('WH').className= "WH";
        	document.getElementById('WH').className= "WH1";
        }
 </script>
 <script>
 	 function btnclick(obj,btnId){
 	 	if(btnId == "BtnConnect"){
    		SoftPhone.StaffMsg(WorkNo,ServerIP,'1','1'); 
    	}
    	SoftPhone.BtnClick(obj.id);
 	 }
 	 
 	 function Waiboclick(){
 	 	var msg=confirm("请输入外呼号码");
 	 	if(msg != ""){
 	 		SoftPhone.JsCallOut(msg);
 	 	}
 	 }
 	 function openTxl(obj,btnId){
		var value = window.showModalDialog('txl/openTxl.jsp', '请选择号码', 'center=yes;dialogWidth=800px;dialogHeight=400px');
		if(btnId=="WH" && value != undefined && value !=""){
			SoftPhone.JsCallOut(value);
		}
	}
 </script>
</body>
</html>
