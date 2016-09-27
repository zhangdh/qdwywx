var limit = 5;
$(function(){
	sys_config_list(limit,'table_listlxr','',false);
	sys_config_list(limit,'table_listfwqq','');
	var p = $('#table_listfwqq').datagrid('getPager');
	$(p).pagination({ 
		buttons: [{
			iconCls:'icon-add',
			text:'新增工单',
			handler:function(){
				addGd();
			}
		},{
			iconCls:'icon-save',
			text:'保存工单',
			handler:function(){
				save('1010301');
			}
		},{
			iconCls:'icon-save',
			text:'保存派单',
			id:'btn_saveRepairer',
			handler:function(){
				save('1010303');
			}
		}]
	});

	$("#cx_call_num").val(caller);
	$("#call_num").val(caller);
	$("#contact_num").val(caller);
	$("#record").val(record);
	if(caller != ""){
		setTimeout("queryLxr(1)",1000);
	}
	
	sys_post("/cc/gdgl.do?method=querySelect","",function(json){
		bindSelect(json);
	})
});

function table_listfwqq_next_page(pageNumber, pageSize){
	query(pageNumber);
}


function table_listlxr_click(rowIndex,rowData){
	$("#show_formfwqq")[0].reset();
	bindForm("show_formfwqq",rowData);
	var formData = "cx_caller_id="+rowData.guid;
	var toData = getObj(formData,1);
	sys_config_list_url("table_listfwqq",'/cc/gdgl.do?method=query');
	$('#table_listfwqq').datagrid('load',toData); 	 
}
function table_listfwqq_click(rowIndex,rowData){
	bindForm("show_formfwqq",rowData);
}

function query(query_page){
	if(query_page == undefined){
		query_page = 1;
	}
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,query_page);
	sys_config_list_url("table_list",'/cc/gdgl.do?method=query');
	$('#table_listfwqq').datagrid('load',toData); 
}

function addGd(){
	$('#show_formfwqq')[0].reset();
}
function queryLxr(query_page){
	if($("#cx_name").val() == "" && $("#cx_call_num").val() == ""){
		alert("请输入查询条件");
		return false;
	}
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,query_page);
	sys_config_list_url("table_listlxr",'/cc/lxr.do?method=queryLxr');
	$('#table_listlxr').datagrid('load',toData); 
}
function save(state){
	if(state == "1010303"){
		//保存工单同时派单
		if(!Validate.CheckForm($('#show_formfwqq')[0])) {
			return false;
		}
		
		if($("#repairer_station").val() == "" ||  $("#repairer_grid").val()=="" || $("#repairer_yhid").val()==""){
			alert("请指定派单人员");
			return ;
		}
		
	}
	if($("#service_guid").val() == "" && $("input:radio[name='call_type']:checked").val()=="1010202"){
		alert("请在工单列表中选择督办工单");
		return ;
	}
	
	var formData = $("#show_formfwqq").serialize();
	formData = formData+"&state="+state;
	sys_post("/cc/gdgl.do?method=saveGd",formData,function(json){
		if(json.result){
			$('#table_listfwqq').datagrid('reload');
		}
		alertMes(json);
	})
}
function radioChange(obj){
	if(obj.value=="1010201"){
		//报修
		$("#btn_saveRepairer").show();
	   $("#show_formfwqq tr[name='repair_tr']").each(function(){
		   $(this).show();
	   });
	   $("#show_formfwqq tr[name='supervision_tr']").each(function(){
		   $(this).hide();
	   });
	}else if(obj.value=="1010202"){
		//督办
		
		$("#btn_saveRepairer").hide();
		$("#show_formfwqq tr[name='repair_tr']").each(function(){
			   $(this).show();
		   });
		$("#show_formfwqq tr[name='supervision_tr']").each(function(){
		   $(this).show();
		});
	}else if(obj.value=="1010203"){
		//咨询
		$("#btn_saveRepairer").hide();
		$("#show_formfwqq tr[name='repair_tr']").each(function(){
		   $(this).hide();
		});
		$("#show_formfwqq tr[name='supervision_tr']").each(function(){
			   $(this).hide();
		});
	}else if(obj.value=="1010204"){
		//其它
		$("#btn_saveRepairer").hide();
		$("#show_formfwqq tr[name='repair_tr']").each(function(){
		   $(this).hide();
		});
		$("#show_formfwqq tr[name='supervision_tr']").each(function(){
			   $(this).hide();
		});
	}
}
//获取点部信息
function getPosition(){
	var returnValue = '';
	sys_post("/cc/gdgl.do?method=queryDbInfo","",function(json){
		returnValue = window.showModalDialog('../../multiPoint.jsp',json.dbinfo,"dialogWidth=1000px;dialogHeight=650px;scroll:no;center:yes;minimize:yes;maximize:yes;location:no");
	    if(returnValue!=null){
	    	$('#repairer_station').val(returnValue.split(',')[0]);
	    	$('#repairer_grid').val(returnValue.split(',')[0]);
	    }
		
	});
	
	
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
