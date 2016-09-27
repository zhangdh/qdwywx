$(function(){
	var name = window.localStorage.getItem("name");
	var grid = window.localStorage.getItem("grid");
	var grid_higher = window.localStorage.getItem("grid_higher");
	if(grid == "null"){
		grid = "";
	}
	if(grid_higher == "null"){
		grid_higher = "";
	}
	$("#name").html(name);
	$("#station").html(grid_higher+" > "+grid);  
	if(deviceType == "Android"){
		//获取当前客户端版本
		var app_version = getAppVersion();
		$("#verison").html(app_version);
	}
});
function openPassword(){
	$.popup('.popup-password');
	$('.popup-overlay').css('z-index',-1);
}
function savePassword(){
	if($("#new_password").val() == ""){
		$.alert("新密码不可为空");
		return false;
	}
	if($("#new_password").val() != $("#new_password2").val()){
		$.alert("二次密码输入不一致");
		return false;
	}
	sys_ajaxPost("/base/org.do?method=modiPassword&new_password="+$("#new_password").val(),"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else if(json.result){
			alertMsg(json);
			$.closeModal(".popup-password");
		}else{
			alertMsg(json);
		}
	});
}

function openInfo(){
	$.popup('.popup-info');
	$('.popup-overlay').css('z-index',-1);
}
function saveInfo(){
	if($("#name").val() == ""){
		$.alert("姓名不可为空");
		return false;
	}
	var postData = "name="+$("#name").val()+"&telephone="+$("#telephone").val()+"&lxdz="+$("#lxdz").val();
	sys_ajaxPost("/base/org.do?method=modiSelf",postData,function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else if(json.result){
			alertMsg(json);
			$.closeModal(".popup-info");
		}else{
			alertMsg(json);
		}
	});
}
function openFeedback(){
	$.alert("功能正在建设中... ...");
}
function openAbout(){
	$.popup('.popup-about');
	$('.popup-overlay').css('z-index',-1);
}