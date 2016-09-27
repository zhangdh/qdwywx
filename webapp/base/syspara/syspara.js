var limit = 10;
var toolbar = [{
	text:'新增',
	iconCls:'icon-add',
	handler:function(){
		addPara();
	}
}];
$(function(){
	sys_config_list(limit,'table_list','/base/syspara.do?method=query');
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
	/*$.each(toData,function(k,v){
		alert(k);alert(v);
	});*/
	$('#table_list').datagrid('load',toData); 
}
function addPara(){
	$('#show_div').window('open')
}
function save(){
	var formData = $("#show_form").serialize();
	_Post("/base/syspara.do?method=save",formData,function(jsonData){			
		$('#table_list').datagrid('reload');
	});
}
function row_click(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');  
	bindForm("show_form",rowData);
	$('#show_div').window('open');
}
function formatOper(value,row,index){
	return '<a href="#" onclick="row_click('+index+')">修改</a>';
}
