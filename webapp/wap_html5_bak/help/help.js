var service_id = "";
var rows = 10;
$(function(){
	query(1);
	pageDiv("div_grid_page");
});
function query(page){
	showLoader('正在加载数据');
	sys_ajaxPost("/cc/app/help.do?method=query&page="+page+"&rows="+rows,"",function(json){
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