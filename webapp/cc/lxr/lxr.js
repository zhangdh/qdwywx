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
		},{
			iconCls:'icon-ok',
			text:'保存修改',
			handler:function(){
				modi();
			}
		}]
	});
	sys_post("/cc/lxr.do?method=init","",function(json){
		bindSelect(json);
	})
});

function table_list_next_page(pageNumber, pageSize){
	query(pageNumber);
}
function table_list_click(rowIndex,rowData){
	bindForm('show_form',rowData);	 
}
function query(query_page){
	if(query_page == undefined){
		query_page = 1;
	}
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,query_page);
	sys_config_list_url("table_list","/cc/lxr.do?method=queryLxr");
	$('#table_list').datagrid('load',toData); 
}

function add(){
	$('#show_form')[0].reset();
}

function save(){
	if(!Validate.CheckForm($('#show_form')[0])) {
		return false;
	}
	
	var formData = $("#show_form").serialize();
	sys_post("/cc/lxr.do?method=saveLxr",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
		}else{
			alertMes(json);
		}
	})
}
function modi(){
	if(!Validate.CheckForm($('#show_form')[0])) {
		return false;
	}
	var formData = $("#show_form").serialize();
	sys_post("/cc/lxr.do?method=modiLxr",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
		}else{
			alertMes(json);
		}
	})
}
function formatOper(value,row,index){
	return '<a href="#" onclick="deleteLxr('+index+')">删除</a> ';
}
function deleteLxr(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');
	var guid = rowData.guid;
	_Post("/cc/lxr.do?method=delLxr","guid="+guid,function(jsonData){			
		$('#table_list').datagrid('reload');
	});
}