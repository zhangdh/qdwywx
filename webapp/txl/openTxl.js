$(function(){	
	initTree();
	sys_config_list(20,'table_list','');
	var p = $('#table_list').datagrid('getPager');
	sys_config_list_url("table_list",'/txl.do?method=getTxlList&sjid=0');
	$('#table_list').datagrid('load'); 
});
function initTree(){
	$('#txl').tree({ 
		data: [{
			text: '嘉伟速修',
			"id": 0,
			"txlid":0,
			"iconCls":''
		}],
		onClick:function(node){
			nodeClick(node);
		}
	}); 	
}
function getOrgTree(nodeid){
	sys_post("/txl.do?method=getTxlTree","sj_id="+nodeid,function(json){
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
					"sjid":v.sj_id,
					"lbdm":v.lb_dm,
					"iconCls":icon
				}]
			});
		});
		$('#txl').tree('collapseAll');
	})
}
function query(){
	var queryData = "cx_name="+$("#phone").val();
	sys_config_list_url("table_list",'/txl.do?method=getTxlList&'+encodeURI(queryData));
	$('#table_list').datagrid('load'); 
}
function nodeClick(node){
	sys_config_list_url("table_list",'/txl.do?method=getTxlList&sjid='+node.id);
	$('#table_list').datagrid('load'); 
	
	if(node.id=="0" || node.id=="1" ){
		if($('#txl').tree('getChildren',node.target).length == 0){
			getOrgTree(node.id);
		}
	}
}
function call(){
	var num = $("#phone").val();
	window.returnValue = num; 
    window.close();
}
function table_list_dblclick(rowIndex,rowData){
	//window.returnValue = rowData.phone; 
   // window.close();
}
function table_list_dbclickcell(rowIndex, field, value){
	if(field != "name" && value !=""){
		window.returnValue = value;
		window.close();
	}
}
function keyDown(){
	if(event.keyCode == 13){
		call();
	} 
}
