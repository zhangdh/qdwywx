var limit = 10;

$(function(){
	sys_config_list(limit,'table_list','');
	var p = $('#table_list').datagrid('getPager');
	$(p).pagination({ 
		buttons: [{
			iconCls:'icon-save',
			text:'保存派单',
			handler:function(){
				saveDeal();
			}
		}]
	});
	
	sys_post("/cc/gdgl.do?method=querySelect","",function(json){
		bindSelect(json);
	})
});

function saveDeal(){
	if(!Validate.CheckForm($('#show_form')[0])) {
		return false;
	}
	if($("#repairer_station").val() == "" ||  $("#repairer_grid").val()=="" || $("#repairer_yhid").val()==""){
		alert("请指定派单人员");
		return ;
	}
	
	var formData = $("#show_form").serialize();
	formData = formData+"&state=1010303" //修改派单状态
	sys_post("/cc/gdgl.do?method=dealTodo",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
		}
		alertMes(json);
	})
}
function table_list_next_page(pageNumber, pageSize){
	query(pageNumber);
}

function query(query_page){
	if(query_page == undefined){
		query_page = 1;
	}
	var formData = $("#query_form").serialize();
	formData = formData+"&state=1010302";
	var toData = getObj(formData,query_page);
	sys_config_list_url("table_list",'/cc/gdgl.do?method=queryTodo');
	$('#table_list').datagrid('load',toData); 
}

function table_list_click(rowIndex,rowData){
	bindForm('show_form',rowData);
	 
}