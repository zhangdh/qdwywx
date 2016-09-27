var service_id = "";
var sign_guid = "";
var sign_status = "";
var rows = 10;
$(function(){
	queryTodo(1);
	pageDiv("div_grid_page");
});
function div_grid_page_next(){
	var page = parseInt($("#page").val());
	if(page*rows >= parseInt($("#total").val())){
		$(".preloader").html("已加载完毕");
		$(".preloader").removeClass("preloader");
	}else{
		queryTodo(1+page);
	}
}
function queryTodo(page){
	showLoader('正在加载数据');
	sys_ajaxPost("/cc/app/gdgl.do?method=queryTodo&state=1010304&page="+page+"&rows="+rows,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			bindGrid(json);
		}
		
		hideLoader();
	});
}
function reservation(guid){
	service_id = guid ;
	$.popup('.popup-reservation');
	$('.popup-overlay').css('z-index',-1);
	$("#reservation").datetimePicker([]);
	sys_ajaxPost("/cc/app/gdgl.do?method=queryReservation&service_id="+service_id,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else if(json.result){
			bindGridOfTemplate("reservation_list","reservation_template",json);
		}else{
			alertMsg(json);
		}
	});
}

function back(guid){
	service_id = guid ;
	$.popup('.popup-back');
	$('.popup-overlay').css('z-index',-1);
}
function saveBack(){
	if($("#back_content").val() == ""){
		sys_alert("回退内容需要填写");
		return false;
	}
	showLoader('正在处理');
	sys_ajaxPost("/cc/app/gdgl.do?method=dealTodo&state=1010302&service_id="+service_id+"&back_content="+$("#back_content").val(),"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			queryTodo(1);
			alertMsg(json);
			$.closeModal(".popup-back");
		}
		
		hideLoader();
	});
}

function saveReservation(){
	//showLoader('正在处理');
	if($("#reservation").val() == ""){
		$.alert("请选择预约时间");
		return false;
	}
	sys_ajaxPost("/cc/app/gdgl.do?method=dealTodo&state=1010304&service_id="+service_id+"&reservation="+$("#reservation").val(),"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else if(json.result){
			queryTodo(1);
			$.closeModal(".popup-reservation");
			sys_alert(json.msg);
		}else{
			alertMsg(json);
		}
		
		//hideLoader();
	});
}
function back(guid){
	service_id = guid ;
	$.popup('.popup-back');
	$('.popup-overlay').css('z-index',-1);
}

function sign(guid,sign,status){
	service_id = guid ;
	sign_guid = sign;
	sign_status = status;
	$.popup('.popup-sign');
	$('.popup-overlay').css('z-index',-1);
	if(sign_status == "1"){
		$("#sign_header").html("签到/签退(您还未签退,请先签退)");
	}
	$("#curtime").html(getDateStr());
	getAddr();
	querySign();
}
function querySign(){
	sys_ajaxPost("/cc/app/gdgl.do?method=querySign&service_id="+service_id,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else if(json.result){
			bindGridOfTemplate("sign_list","sign_template",json);
		}else{
			alertMsg(json);
		}
	});
}
function signIn(){
	if(sign_status == "1"){
		$.alert('您上次还未签退,请先签退后再签到');
		return;
	}
	sys_ajaxPost("/cc/app/gdgl.do?method=saveSign&sign_type=in&service_id="+service_id+"&sign_guid="+sign_guid,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			sys_alert(json.msg);
			sign_guid = json.sign_guid;
			sign_status = "1";
			querySign();
		}
	});
}
function signOut(){
	if(sign_status == "0"){
		$.alert('您还未签到,请先签到后再签退');
		return;
	}
	sys_ajaxPost("/cc/app/gdgl.do?method=saveSign&sign_type=out&service_id="+service_id+"&sign_guid="+sign_guid,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			sys_alert(json.msg);
			$("#sign_header").html("签到/签退");
			sign_status = "0";
			querySign();
		}
	});
}
function show(guid){
	sys_goUrl("show.html?guid="+guid);
}
function dial(num){
	if(deviceType == "Android"){
		window.androd.bohao(num);
	}
}