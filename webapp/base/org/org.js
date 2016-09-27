$(function(){	
	getOrgTree();
	sys_config_list(20,'table_list','');
	var p = $('#table_list').datagrid('getPager');
	$(p).pagination({ 
		buttons: [{
			iconCls:'icon-add',
			text:'新增部门',
			handler:function(){
				addBm();
			}
		},{
			iconCls:'icon-add',
			text:'新增人员',
			handler:function(){
				addRy();
			}
		},{
			iconCls:'icon-save',
			text:'保存部门',
			handler:function(){
				saveBm();
			}
		},{
			iconCls:'icon-save',
			text:'保存人员',
			handler:function(){
				saveRy();
			}
		}]
	});
	
	sys_post("/base/org.do?method=getJsList","",function(json){
		bindSelect(json);
	});
});
function getGrid(gridId){
	sys_post("/base/org.do?method=getGrid&gridId="+gridId,"",function(json){
		bindSelect(json);
	});
}
function getOrgTree(){
	$('#org').tree({ 
		data: [{
			text: '组织架构',
			"id": 0,
			"orgid":0,
			"lbdm":2,
			"iconCls":''
		}],
		onClick:function(node){
			nodeClick(node);
		}
	}); 
	
	sys_post("/base/org.do?method=getOrgTree","",function(json){
		$.each(json.orgData,function(k,v){
			var sjid = v.sjid;
			var icon = "";
			if(v.lbdm == 1){
				icon = "icon-man";
			}
			var parentNode = $('#org').tree('find', sjid);
			$('#org').tree('append', {
				parent: parentNode.target,
				data: [{
					"id": v.id,
					"text": v.orgname,
					"orgid":v.orgid,
					"sjid":sjid,
					"lbdm":v.lbdm,
					"iconCls":icon
				}]
			});
		});
		$('#org').tree('collapseAll');
	})
}
function nodeClick(node){
	//alert(node.text);alert(node.sjid);alert(node.lbdm);alert(node.id);alert(node.orgid);
	sys_config_list_url("table_list",'/base/org.do?method=getOrgList&sjid='+node.id);
	$('#table_list').datagrid('load'); 
	if(node.lbdm == "2"){
		//部门
		$("#sjid").val(node.id);
		$("#sjbmid").val(node.orgid);
	}else if(node.lbdm == "1"){
		//人员
		$("#sjid").val('');
		$("#sjbmid").val('');
	}
}

function addBm(){
	$("#show_formBm")[0].reset();
}

function addRy(){
	$("#show_formRy")[0].reset();
}

function saveBm(){
	if(!Validate.CheckForm($('#show_formBm')[0])) {
		return false;
	}
	var formData = $("#show_formBm").serialize();
	formData = formData+"&sjid="+$("#sjid").val()+"&sjbmid="+$("#sjbmid").val();
	sys_post("/base/org.do?method=saveBm",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
			getOrgTree();
		}else{
			alertMes(json);
		}
	})
}

function saveRy(){
	if($("#sjid").val==""){
		alert("请选择上级部门");
		return false;
	}
	if($("#sjbmid").val==""){
		alert("请选择上级部门");
		return false;
	}
	
	if(!Validate.CheckForm($('#show_formRy')[0]))return;
	var formData = $("#show_formRy").serialize();
	formData = formData+"&sjid="+$("#sjid").val()+"&sjbmid="+$("#sjbmid").val();
	sys_post("/base/org.do?method=saveYh",formData,function(json){
		if(json.result){
			$('#table_list').datagrid('reload');
			getOrgTree();
		}else{
			alertMes(json);
		}
	});
}
function table_list_click(rowIndex,rowData){
	if(rowData.lbdm == 1){
		sys_post("/base/org.do?method=showYh","id="+rowData.orgid,function(json){
			bindForm("show_formRy",json.formMap);
		});
	}else {
		sys_post("/base/org.do?method=showBm","id="+rowData.orgid,function(json){
			bindForm("show_formBm",json.formMap);
		});
	}
}

function formatOper(value,row,index){
	return '<a href="#" onclick="deleteOrg('+index+')">删除</a> ';
}
function deleteOrg(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');  
	var orgid = rowData.orgid;
	var id = rowData.id;
	_Post("/base/org.do?method=delOrg","orgid="+orgid+"&id="+id,function(jsonData){			
		$('#table_list').datagrid('reload');
		getOrgTree();
	});
}
function getPosition(){
	var position = $('#points').val();
	var returnValue='';
	var getTimestamp=new Date().getTime();
	if(position==null){
		position='';
	}
	returnValue = window.showModalDialog(webcontext+'/base/zone/mapMarker.jsp'+"?timestamp="+getTimestamp,position,"dialogWidth=1000px;dialogHeight=650px;scroll:no;center:yes");
	if(returnValue!=''){
		$('#points').val(returnValue);
	}
}