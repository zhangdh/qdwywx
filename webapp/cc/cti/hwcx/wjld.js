var limit = 10;
$(function(){
	sys_config_list(limit,'table_list','/cti/hwbb.do?method=queryWjld');
});
function table_list_next_page(pageNumber, pageSize){
	query(pageNumber);
}
function query(query_page){
	if(query_page == undefined){
		query_page = 1;
	}
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,query_page);
	$('#table_list').datagrid('load',toData); 
}