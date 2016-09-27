var rows = 10;
$(function(){
	queryTodo(1);
	pageDiv("div_grid_page");
});
function queryTodo(page){
	showLoader('正在加载数据');
	sys_ajaxPost("/cc/app/gdgl.do?method=queryTodo&state='1010305','1010306'&page="+page+"&rows="+rows,"",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			bindGrid(json);
		}
		
		hideLoader();
	});
}
function div_grid_page_next(){
	var page = parseInt($("#page").val());
	if(page*rows >= parseInt($("#total").val())){
		$(".preloader").html("已加载完毕");
		$(".preloader").removeClass("preloader");
	}else{
		queryTodo(1+page);
	}
}
function show(guid){
	sys_goUrl("show.html?guid="+guid);
}
function dial(num){
	if(deviceType == "Android"){
		window.androd.bohao(num);
	}
}
function show(guid){
	sys_goUrl("show.html?guid="+guid);
}