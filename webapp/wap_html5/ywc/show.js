var guid = "";
$(function(){
	guid = sys_getParaFromUrl("guid");
	showTodo();
});
function showTodo(){
	sys_ajaxPost("/cc/app/gdgl.do?method=showTodo&guid="+guid,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			bindForm('div_show',json.formMap);
			$(".preloader").html("已加载完毕");
			$(".preloader").removeClass("preloader");
		}
		hideLoader();
	});
}
function dial(){
	if(deviceType == "Android"){
		window.androd.bohao($("#contact_num").html());
	}
}

function openImages(type){
	sys_ajaxPost("/cc/app/gdgl.do?method=getImages&service_id="+$("#guid").val()+"&upload_type="+type,"",function(json){
		if(json.result){
			var rows = json.rows;
			var imagesArray=new Array()
			var hostIp = window.location.host;
			$.each(rows,function(k,v){
				imagesArray[k] = "http://"+hostIp+"/"+v.images_path.replace("D:/ccoffice_files/images","images");
			});
			var myPhotoBrowserPopup = $.photoBrowser({
			      photos : imagesArray,
			      type: 'popup'
			});
			myPhotoBrowserPopup.open();
		}else{
			alertMsg(json);
		}
	});
}