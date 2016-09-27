var limit = 10;
$(function(){
	sys_config_list(limit,'table_list','/cti/record.do?method=list');
});
function table_list_next_page(pageNumber, pageSize){
	query(pageNumber);
}
function query(query_page){
	if(query_page == undefined){
		query_page = 1;
	}
	var formData = $("#query_form").serialize();
	var toData = getObj(formData,query_page);
	/*$.each(toData,function(k,v){
		alert(k);alert(v);
	});*/
	$('#table_list').datagrid('load',toData); 
}


function row_click(index){
	$('#table_list').datagrid('selectRow',index);// 关键在这里  
    var rowData = $('#table_list').datagrid('getSelected');  
	bindForm("show_form",rowData);
	$('#show_div').window('open');
}
function formatOper(value,row,index){
	return '<a href="#" onclick=play("'+value+'")>播放</a>&nbsp;&nbsp;<a href="#" onclick=download("'+value+'")>下载</a>';
}
function play(path){
	window.open("../../../play.jsp?url="+path.split(":")[1],"","height=100, width=500,toolbar=no, menubar=no, scrollbars=no, left=500,top=400,resizable=no,location=no, status=no,titlebar=no");
}
function download(path){
	var hostIp = window.location.host;
	window.open("http://"+hostIp+path.split(":")[1]);
}
