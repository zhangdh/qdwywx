$(function(){
	queryTj();
});
function queryTj(){
	sys_ajaxPost("/cc/ywtj.do?method=queryTj","",function(json){
		if(!json.login){
			alert("状态验证失败，请重新登录");
			sys_goUrl("../login.html")
		}else{
			bindForm('div_show',json.formMap);
		}
		hideLoader();
	});
}