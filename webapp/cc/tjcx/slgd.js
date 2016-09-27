$(function(){
	sys_config_list('','table_list','',false);
});

function queryData(){
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,1);
	sys_config_list_url("table_list","/cc/ywtj.do?method=querySlgd");
	$('#table_list').datagrid('load',toData); 
}

function exportData(index){
	var formData = $("#query_form").serialize();
	window.open(webcontext+"/cc/ywtj.do?method=exportSlgd&"+formData);
}