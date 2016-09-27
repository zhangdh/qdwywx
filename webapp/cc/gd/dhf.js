var limit = 10;

$(function(){
	sys_config_list(limit,'table_list','');
	var p = $('#table_list').datagrid('getPager');
	$(p).pagination({ 
		buttons: [{
			iconCls:'icon-save',
			text:'保存回访',
			handler:function(){
				saveDeal();
			}
		}]
	});
	
	sys_post("/cc/gdgl.do?method=queryReturnSelect","",function(json){
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
	formData = formData+"&state=1010306" //修改派单状态
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
	formData = formData+"&state=1010305";
	var toData = getObj(formData,query_page);
	sys_config_list_url("table_list",'/cc/gdgl.do?method=queryTodo');
	$('#table_list').datagrid('load',toData); 
}

function table_list_click(rowIndex,rowData){
	bindForm('show_form',rowData);
}

function openImages(value,type){
	if(value == "点击查看"){
		var host = window.location.host;
		sys_post("/cc/gdgl.do?method=queryImages","service_id="+$("#guid").val()+"&image_type="+type,function(json){
			$.each(json.rows,function(k,v){
 
				$("#images").append("<img style= 'text-align:center' src='http://"+host+"/images/"+v.path+"'><img");
			});
		})
		$('#images').window('open');
	}else{
		return false;
	}
}