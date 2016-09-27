var limit = 10;
$(function(){	
	sys_config_list(limit,'table_list','');
	var p = $('#table_list').datagrid('getPager');
	$(p).pagination({ 
		buttons: [{
			iconCls:'icon-add',
			text:'新增',
			handler:function(){
				add();
			}
		},{
			iconCls:'icon-save',
			text:'保存',
			handler:function(){
				save();
			}
		}]
	});
	query(1);
});
function save(){
	if(!Validate.CheckForm($('#show_form')[0])) {
		return false;
	}
	var formData = $("#show_form").serialize();
	sys_post("/cc/app/help.do?method=save",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
		}
		alertMes(json);
	})
}
function add(){
	$('#show_form')[0].reset();
}
function query(query_page){
	if(query_page == undefined){
		query_page = 1;
	}
	var toData = getObj("",query_page);
	sys_config_list_url('table_list','/cc/app/help.do?method=query');
	$('#table_list').datagrid('load',toData); 
}
function del(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');
	var guid = rowData.guid;
	sys_post("/cc/app/help.do?method=delete","guid="+guid,function(jsonData){			
		$('#table_list').datagrid('reload');
	});
}
function formatOper(value,row,index){
	return '<a href="#" onclick="del('+index+')">删除</a> ';
}
function table_list_next_page(pageNumber, pageSize){
	query(pageNumber);
}
function table_list_click(rowIndex,rowData){
	bindForm('show_form',rowData);	 
}