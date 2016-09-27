var limit = 20;
$(function(){
	sys_config_list(limit,'table_list','',false);
	sys_config_list_url("table_list",'/base/cache.do?method=getCache');
	$('#table_list').datagrid('load',toData); 
});
