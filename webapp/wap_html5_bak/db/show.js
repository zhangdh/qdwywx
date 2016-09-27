var guid = "";
var type = "";
var sign_status = "";
$(function(){
	guid = sys_getParaFromUrl("guid");
	showTodo();
	 
	$(document).on('click','.create-actions',function (){
	      var buttons1 = [{
					          text: '请选择',
					          label: true
					        },{
					          text: '拍照',
					          bold: true,
					          onClick: function() {
					        	  paizhao();
					          }
					        },{
					          text: '图库',
					          onClick: function() {
					        	  tuku();
					          }
					        }
					      ];
	      var buttons2 = [
	                      {
	                        text: '取消',
	                        bg: 'danger'
	                      }
	                    ];
	  
	      var groups = [buttons1,buttons2];
	      $.actions(groups);
  });
	 
});
function showTodo(){
	sys_ajaxPost("/cc/app/gdgl.do?method=showTodo&guid="+guid,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			bindForm('div_show',json.formMap);
		}
		hideLoader();
	});
}
function dial(){
	if(deviceType == "Android"){
		window.androd.bohao($("#contact_num").html());
	}
}

function saveReservation(){
	var service_id = $("#guid").val();
	showLoader('正在处理');
	sys_ajaxPost("/cc/app/gdgl.do?method=dealTodo&state=1010304&service_id="+service_id+"&reservation="+$("#reservation").val(),"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			document.getElementById("reservation_time").innerHTML = $("#reservation").val();
			$("#popupReservation").popup( "close" );
		}
		hideLoader();
	});
}
function uploadImages(){
	sys_alert('调用壳 window.androd.getSheXiangTou()前');
	window.androd.getSheXiangTou();
	sys_alert('调用壳 window.androd.getSheXiangTou()后');
}

function uploadOfferImages(){
	type = "offer";
}
function uploadSceneImages(){
	type = "scene";
}
function deal(){
	$.popup('.popup-deal');
	$('.popup-overlay').css('z-index',-1);
}
function paizhao(){
	window.androd.getSheXiangTou(guid,type);
}
function tuku(){
	window.androd.getPhoto(guid,type);
}
function dealTodo(){
	var postData = "state=1010305&repairer_content="+$("#repairer_content").val()+"&offer_value="+$("#offer_value").val()+"&service_id="+$("#guid").val();
	sys_ajaxPost("/cc/app/gdgl.do?method=dealTodo",postData,function(json){
		if(!json.login){
			sys_alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			sys_alert(json.msg);
			window.location.href="dwx.html"; 
		}
	});
}
function sign(guid,sign,status){
	$.popup('.popup-sign');
	$('.popup-overlay').css('z-index',-1);
	if($("#sign_status").val() == "1"){
		$("#sign_header").html("签到/签退(您还未签退,请先签退)");
	}
	$("#curtime").html(getDateStr());
	getAddr();
	querySign();
}
function querySign(){
	sys_ajaxPost("/cc/app/gdgl.do?method=querySign&service_id="+$("#guid").val(),"",function(json){
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
	if($("#sign_status").val() == "1"){
		$.alert('您上次还未签退,请先签退后再签到');
		return;
	}
	sys_ajaxPost("/cc/app/gdgl.do?method=saveSign&sign_type=in&service_id="+$("#guid").val()+"&sign_guid="+$("#sign_guid").val(),"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			sys_alert(json.msg);
			$("#sign_guid").val(json.sign_guid);
			$("#sign_status").val("1");
			querySign();
		}
	});
}
function signOut(){
	if($("#sign_status").val() == "0"){
		$.alert('您还未签到,请先签到后再签退');
		return;
	}
	sys_ajaxPost("/cc/app/gdgl.do?method=saveSign&sign_type=out&service_id="+$("#guid").val()+"&sign_guid="+$("#sign_guid").val(),"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			sys_alert(json.msg);
			$("#sign_header").html("签到/签退");
			$("#sign_status").val("0");
			querySign();
		}
	});
}