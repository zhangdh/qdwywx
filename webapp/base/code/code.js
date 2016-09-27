var limit = 10;
var toolbar = [{
	text:'新增',
	iconCls:'icon-add',
	handler:function(){
		add();
	}
}];
$(function(){
	sys_config_list(limit,'table_list','');
	sys_post("/base/code.do?method=getModule","",function(json){
		bindSelect(json);
	})
	
	var p = $('#table_list').datagrid('getPager');
	$(p).pagination({ 
		buttons: [{
			iconCls:'icon-add',
			handler:function(){
				addDm();
			}
		}]
	}); 
	
});
function change(value){
	sys_post("/base/code.do?method=getDmType","moduleId="+value,function(json){
		bindSelect(json);
	})
}
function query(query_page){
	if(query_page == undefined){
		query_page = 1;
	}
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,query_page);
	
	sys_config_list_url("table_list",'/base/code.do?method=queryCode');
	
	$('#table_list').datagrid('load',toData); 
}


function table_list_next_page(pageNumber, pageSize){
	query(pageNumber);
}
 
function addPara(){
	$('#show_div').window('open')
}
function save(){
	var formData = $("#show_form").serialize();
	formData = formData+"&dm_type="+$("#dm_type").val();
	sys_post("/base/code.do?method=saveCode",formData,function(json){
		$('#table_list').datagrid('reload');
	})
}
function deleteDm(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');  
	var dm = rowData.dm;
	_Post("/base/code.do?method=delCode","rowsdm="+dm,function(jsonData){			
		$('#table_list').datagrid('reload');
	});
}
function modiDm(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');  
	bindForm("show_form",rowData);
	$('#show_div').window('open');
}
function addDm(){
	if($("#dm_type").val()=="" || $("#dm_type").val() == "null"){
		warnAlert("代码类型不能为空");
		return ;
	}
	resetForm("show_form");
	$('#show_div').window('open');
}
function formatOper(value,row,index){
	return '<a href="#" onclick="deleteDm('+index+')">删除</a> '+'<a href="#" onclick="modiDm('+index+')">修改</a>' ;
}
