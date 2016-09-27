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
	formData = formData+"&state=1010301";
	var toData = getObj(formData,query_page);
	sys_config_list_url("table_list",'/cc/gdgl.do?method=queryTodo');
	$('#table_list').datagrid('load',toData); 
}

function table_list_click(rowIndex,rowData){
	bindForm('show_form',rowData);	 
}
function getRepairer(){
	var returnValue = '';
	sys_post("/cc/gdgl.do?method=queryDbInfo","",function(json){
		returnValue = window.showModalDialog('../..//multiPoint.jsp',json.dbinfo,"dialogWidth=1000px;dialogHeight=650px;");
	    if(returnValue!=null){
	    	var results= returnValue.split(',');
	    	var dbid = results[0];
	    	var wgid = results[1];
	    	var grid_name = results[2];
	    	var yhid = results[3];
	    	var name = results[4];
	    	var key = results[5];
	    	$('#repairer_station').val(dbid);
	    	$('#repairer_grid').empty();
	    	$('#repairer_grid').append("<option value = '"+wgid+"'>"+grid_name+'</option>');
	    	$('#repairer_yhid').empty();
	    	$('#repairer_yhid').append("<option value = '"+yhid+"'>"+name+'</option>');
	    	if(key != ""){
	    		$('#repair_address').val(key);
	    	}
	    }
		
	});
}