$(function(){	
	//getZoneTree();
	
	sys_config_list(20,'table_list','');
	var p = $('#table_list').datagrid('getPager');
	$(p).pagination({ 
		buttons: [{
			iconCls:'icon-add',
			text:'新增网格',
			handler:function(){
				addGrid();
			}
		},{
			iconCls:'icon-save',
			text:'保存网格',
			handler:function(){
				saveGrid();
			}
		},{
			iconCls:'icon-save',
			text:'修改网格',
			handler:function(){
				saveGrid();
			}
		}]
	});
	
	sys_post("/base/grid.do?method=init","",function(json){
		bindSelect(json);
	});
	getGridTree();
});
function addGrid(){
	$("#show_formGrid")[0].reset();
}
function saveGrid(){
	if(!Validate.CheckForm($('#show_formGrid')[0]))return;
	var formData = $("#show_formGrid").serialize();
	sys_post("/base/grid.do?method=saveGrid",formData,function(json){
		alert(json.msg);
		$('#table_list').datagrid('load'); 
		getGridTree();
	});
}
function getGridTree(){
	$('#grid').tree({ 
		data: [{
			"text": '区域划分',
			"id": 0
		}],
		onClick:function(node){
			nodeClick(node);
		}
	}); 
	
	sys_post("/base/grid.do?method=getGridTree","",function(json){
		$.each(json.gridData,function(k,v){
			var higher_id = v.higher_id;
			var parentNode = $('#grid').tree('find', higher_id);
			if(parentNode != null){
				$('#grid').tree('append', {
					parent: parentNode.target,
					data: [{
						"id": v.guid,
						"text": v.name,
						"higher_id":higher_id,
						"name":v.name,
						"guid":v.guid,
						"comment":v.comment,
						"org_id":v.org_id,
						"address":v.address,
						"points":v.points
					}]
				});
			}
		});
		$('#grid').tree('collapseAll');
	})
}

function nodeClick(node){
	//alert(node.text);alert(node.sjid);alert(node.lbdm);alert(node.id);alert(node.orgid);
	sys_config_list_url("table_list",'/base/grid.do?method=getGridList&higher_id='+node.id);
	$('#table_list').datagrid('load'); 
	bindForm("show_formGrid",node);
}


function table_list_click(rowIndex,rowData){
	bindForm("show_formGrid",rowData);
}

function formatOper(value,row,index){
	return '<a href="#" onclick="deleteGrid('+index+')">删除</a> ';
}
function deleteGrid(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');  
	var orgid = rowData.orgid;
	var guid = rowData.guid;
	_Post("/base/grid.do?method=delGrid","guid="+guid,function(jsonData){			
		$('#table_list').datagrid('reload');
		getGridTree();
	});
}
/*--------------------add by zn-------------------*/
function getPosition(){
	var position = $('#points').val();
	var returnValue='';
	var getTimestamp=new Date().getTime();
	if(position==null){
		position='';
	}
	if($("#type").val() == "1010801"){
		returnValue = window.showModalDialog(webcontext+'/base/zone/mapMarker.jsp'+"?timestamp="+getTimestamp,position,"dialogWidth=1000px;dialogHeight=650px;scroll:no;center:yes");
	}else{
		returnValue = window.showModalDialog(webcontext+'/base/zone/mapPolyline.jsp'+"?timestamp="+getTimestamp,position,"dialogWidth=1000px;dialogHeight=650px;scroll:no;center:yes");
	}
	if(returnValue!=undefined||returnValue!=''){
		$('#points').val(returnValue);
	}
	
	

}