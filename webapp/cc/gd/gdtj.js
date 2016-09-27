var limit = 10;

$(function(){
	sys_config_list(limit,'table_list','',false);
});

function table_list_next_page(pageNumber, pageSize){
	query(pageNumber);
}

function query(query_page){
	$("#type").val("");
	if(query_page == undefined){
		query_page = 1;
	}
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,query_page);
	sys_config_list_url("table_list",'/cc/gdgl.do?method=queryTj');
	$('#table_list').datagrid('load',toData); 
}
function checkRadio(type){
	$("#type").val(type);
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,1);
	sys_config_list_url("table_list",'/cc/gdgl.do?method=queryTj');
	$('#table_list').datagrid('load',toData);
}
function exportTj(){
	var formData = $("#query_form").serialize();
	window.open(webcontext+"/cc/gdgl.do?method=exportTj&"+formData);
}