var project_id  = "";
var questionnaire_id = "";
$(function(){
	sys_config_list(100,'table_list_project','/cc/md/wjdc.do?method=queryProject',false);
	sys_config_list(100,'table_list_questionnaire','',false);
});

function table_list_project_click(index,rowData){
	project_id = rowData.id;
	var toData = getObj("project_id="+rowData.id,1);
	sys_config_list_url("table_list_questionnaire","/cc/md/wjdc.do?method=queryQuestionnaire");
	$('#table_list_questionnaire').datagrid('load',toData); 
}

function table_list_questionnaire_click(index,rowData){
	var questionnaire_id = rowData.id;
	window.open(webcontext+"/cc/md/wjdc.do?method=openQuestionnaire&project_id="+project_id+"&questionnaire_id="+questionnaire_id, '',
  	'width='+window.screen.Width-20+',height='+window.screen.Height-20+',top=0,left=0,resizable=yes,menubar=no,scrollbars=yes');
}