var limit = 10;
var isContinue = true;
$(function(){
	sys_config_list(limit,'table_list','');
	sys_config_list(limit,'table_listreservation','',false);
	sys_config_list(limit,'table_listsign','',false);
	sys_config_list(limit,'table_listvideo','',false);
	sys_post("/cc/gdgl.do?method=queryQuerySelect","",function(json){
		bindSelect(json);
	})
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
	sys_config_list_url("table_list",'/cc/gdgl.do?method=queryGdcx');
	$('#table_list').datagrid('load',toData); 
}

function table_list_click(rowIndex,rowData){
	bindForm('show_form',rowData);
	 
}
function openImages(value,type){
	if(value == "点击查看"){
		$("#images").html("");
		var host = window.location.host;
		sys_post("/cc/gdgl.do?method=queryImages","service_id="+$("#guid").val()+"&image_type="+type,function(json){
			$.each(json.rows,function(k,v){
				$("#images").append("<img style= 'text-align:center' src='http://"+host+"/images/"+v.path.split("images")[1]+"'><img>");
			});
		})
		$('#images').window('open');
	}else{
		return false;
	}
}
function openReservation(){
	$('#reservation').window('open');
	var formData = "service_id="+$("#guid").val();
	var toData = getObj(formData,1);
	sys_config_list_url("table_listreservation",'/cc/gdgl.do?method=queryReservation');
	$('#table_listreservation').datagrid('load',toData); 
}
function openSign(){
	$('#sign').window('open');
	var formData = "service_id="+$("#guid").val();
	var toData = getObj(formData,1);
	sys_config_list_url("table_listsign",'/cc/gdgl.do?method=querySign');
	$('#table_listsign').datagrid('load',toData); 
}
function openVideo(){
	isContinue = true;
	$('#video').window('open');
}
function addTr(){
	$("#table_video").prepend("<tr>"+$("#table_video_tr").html()+"</tr>");
}
function removeTr(obj){
	$(obj.parentNode.parentNode).remove();
}
function openVideos(){
	$('#videolist').window('open');
	var formData = "service_id="+$("#guid").val();
	var toData = getObj(formData,1);
	sys_config_list_url("table_listvideo",'/cc/gdgl.do?method=queryVideos');
	$('#table_listvideo').datagrid('load',toData); 
}
function saveVideo(){
	if($("#guid").val() == ""){
		alert("请选择关联工单");
		return;
	}
	$("input[name='video_path']").each(function(index,element){
		if($(this).val() == ""){
			alert("关联视频路径不允许有空值");
			isContinue = false;
		}
	})
	if(!isContinue){
		return false;
	}
	var videos = $("#form_videos").serialize();
	videos = videos+"&service_id="+$("#guid").val();
	sys_post("/cc/gdgl.do?method=saveVideos",videos,function(json){
		alertMes(json);
	})
	 
}
