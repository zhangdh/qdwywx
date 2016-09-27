$(function(){	
	getOrgTree();
	
	sys_config_list(10,'table_list','');
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
			iconCls:'icon-tip',
			text:'保存修改',
			handler:function(){
				saveModi();
			}
		}]
	});
	sys_config_list_url("table_list",'/txl.do?method=getTxlList&sjid=0');
	$('#table_list').datagrid('load'); 
});

function getOrgTree(){
	$('#txl').tree({ 
		data: [{
			text: '嘉伟速修',
			"id": 0,
			"txlid":0,
			"iconCls":'',
			"lbdm":'1040101'
		}],
		onClick:function(node){
			nodeClick(node);
		}
	}); 
	
	sys_post("/txl.do?method=getTxlTree","",function(json){
		$.each(json.txlData,function(k,v){
			var sjid = v.sj_id;
			var icon = "";
			if(v.lb_dm == '1040102'){
				icon = "icon-man";
			}else{
				icon = "icon-room";
			}
			var parentNode = $('#txl').tree('find', sjid);
			$('#txl').tree('append', {
				parent: parentNode.target,
				data: [{
					"id": v.guid,
					"text": v.name,
					"sjid":sj_id,
					"lbdm":v.lb_dm,
					"iconCls":icon
				}]
			});
		});
		$('#txl').tree('collapseAll');
	})
}
function query(){
	var queryData = $("#query_form").serialize();
	sys_config_list_url("table_list",'/txl.do?method=getTxlList&'+queryData);
	$('#table_list').datagrid('load'); 
}
function nodeClick(node){
	//alert(node.text);alert(node.sjid);alert(node.lbdm);alert(node.id);alert(node.orgid);
	sys_config_list_url("table_list",'/txl.do?method=getTxlList&sjid='+node.id);
	$('#table_list').datagrid('load'); 
	if(node.lbdm == "1040101"){
		$("#sj_id").val(node.id);
	}
	if(node.id != "1" && node.id != "0"){
		sys_post("/txl.do?method=showTxl","id="+node.id,function(json){
			bindForm("show_formTxl",json.form_show);
		})
	}
}

function add(){
	$("#show_formTxl")[0].reset();
}

function save(){
	if(!Validate.CheckForm($('#show_formTxl')[0])) {
		return false;
	}
	var formData = $("#show_formTxl").serialize();
	formData = formData+"&sj_id="+$("#sj_id").val();
	sys_post("/txl.do?method=saveTxl",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
			getOrgTree();
			add();
		}else{
			alertMes(json);
		}
	})
}
function saveModi(){	
	if(!Validate.CheckForm($('#show_formTxl')[0])) {
		return false;
	}
	var formData = $("#show_formTxl").serialize();
	sys_post("/txl.do?method=modiTxl",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
			getOrgTree();
		}else{
			alertMes(json);
		}
	})
}

function table_list_click(rowIndex,rowData){
	bindForm("show_formTxl",rowData);
}

function formatOper(value,row,index){
	return '<a href="#" onclick="deleteTxl('+index+')">删除</a> ';
}
function deleteTxl(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');
	var id = rowData.guid;
	_Post("/txl.do?method=delTxl","id="+id,function(jsonData){			
		$('#table_list').datagrid('reload');
		getOrgTree();
	});
}
function upload(){
	alert('12');
	$('#show_upload').window('open');
}