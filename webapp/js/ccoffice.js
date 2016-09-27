function sys_config_list(limit,tableId,listUrl,ifPage){
	if(ifPage == undefined){
		ifPage = true;
	}
	if(listUrl != ""){
		listUrl = webcontext+listUrl;
	}
	$("#"+tableId).datagrid({
		method:'post',
		url:listUrl,
		pageSize:limit,
		pageList: [5,10,11,12,13,14,15,16,17,18,19,20],
		rownumbers:true,
		pagePosition:'top',
		singleSelect:true,
		checkbox:true,
		pagination:ifPage,
		onBeforeLoad:function(paras){

		},
		onClickRow:function(rowIndex,rowData){
			try{
				if(typeof(eval(tableId+"_click"))=="function"){
					eval(tableId+"_click(rowIndex,rowData)");
				}
			}catch(e){
			}
		},
		onDblClickRow:function(rowIndex,rowData){
			try{
				if(typeof(eval(tableId+"_dblclick"))=="function"){
					eval(tableId+"_dblclick(rowIndex,rowData)");
				}
			}catch(e){
			}
		},
		onClickCell:function(rowIndex, field, value){
			try{
				if(typeof(eval(tableId+"_clickcell"))=="function"){
					eval(tableId+"_clickcell(rowIndex, field, value)");
				}
			}catch(e){
			}
		},
		onDblClickCell:function(rowIndex, field, value){
			try{
				if(typeof(eval(tableId+"_dbclickcell"))=="function"){
					eval(tableId+"_dbclickcell(rowIndex, field, value)");
				}
			}catch(e){
			}
		},
		onLoadError:function(){
			alert('加载数据失败');
		}
	});
	var p = $('#'+tableId).datagrid('getPager');
	if(p){
		$(p).pagination({  
			 
        	
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
	    }); 
	}
}
function sys_config_list_url(table_list,value){
	$('#'+table_list).datagrid('options').url = webcontext+value;
}
function resetForm(formId){
	$("#"+formId)[0].reset(); 	
}
function warnAlert(str){
	$.messager.alert('警告提示',str,'warning');
}
function infoAlert(str){
	$.messager.alert('操作提示',str,'info');
}

function alertMes(json){
  var message="";
  if(json.result===false){
		message=json.msg;
		warnAlert(message);
		return false;
  }else{	
  		    
  		if(json.msg == undefined || json.msg == ""){
  			message="处理成功";
  		}else{
  			message=json.msg;
  		}
		infoAlert(message);
		return true;
	}
}

function validateConfig(str){
	var strs = str.split(",");
	for (var i = 0; i < strs.length - 1; i++) {
        $("#"+strs[i]).validatebox(); 
    }
}

//转换json数据格式
function convertData(datar){
	var dataJson;
	var tableId;
	var allCount;
	var gridData = datar.gridData;
	$.each(gridData,function(i,d){
		//只转换第一条
		allCount = d.page.page_allCount;
		tableId = i;
		dataJson = d.data;
	});
	var dataString = "";
	$.each(dataJson,function(k,v){
		
		dataString = dataString +"{";
		$.each(v,function(k1,v1){
			dataString = dataString+"'"+k1+"':'"+v1+"',";
		});
		dataString = dataString.substring(0,dataString.length-1);
		
		dataString = dataString +"},";
	});
	dataString = dataString.substring(0,dataString.length-1);
	dataString = "{'total':"+allCount+",'rows':"+"["+dataString+"],'tableId':'"+tableId+"'}";
	var dataObj =eval("("+dataString+")");
	return dataObj;  
}
function sys_post(_url,_data,callback){
	if (callback===undefined){
		$.ajax({type:"POST", url:webcontext+_url, data:_data, dataType:"json", 
		  success:function (msg) {
			ajaxAlert(msg);
		},error:function(xhr, ajaxOptions, thrownError){
            ajaxAlert(thrownError);
		}});
	}else{
		$.ajax({type:"POST", url:webcontext+_url, data:_data, dataType:"json", success:callback,
		  error:function(xhr, ajaxOptions, thrownError){
			ajaxAlert(thrownError);
		}});
	}
}

function bindSelect(jsonData){
	$.each(jsonData,function(k,v){
		k=k.toLowerCase();
		$("#"+k).empty();
		$.each(v,function(z,m){
			$("#"+k).append("<option value='"+m['dm']+"'>"+m['mc']+"</option>"); 
		})
		if($('#'+k).attr("linkage")){
			$("#"+k).attr("style","");
			$("#"+k).css("width","100%");
		}
		
	});
}


function _Post(_url,_data,callback){
	if (callback===undefined){
		$.ajax({type:"POST", url:webcontext+_url, data:_data, dataType:"json",charset:'UTF-8', 
		  success:function (msg) {
			ajaxAlert(msg);
		},error:function(xhr, ajaxOptions, thrownError){
            ajaxAlert(thrownError);
		}});
	}else{
		$.ajax({type:"POST", url:webcontext+_url, data:_data, dataType:"json", success:callback,charset:'UTF-8', 
		  error:function(xhr, ajaxOptions, thrownError){
			ajaxAlert(thrownError);
		}});
	}
}
function bindForm(formId,jsonData){
	$.each(jsonData,function(k,v){
		k=k.toLowerCase();
		if($('#'+k).attr("type")=="radio"){
			$("input[name="+k+"][value="+v+"]").attr("checked",true);
		}else if ($('#'+k).attr("linkage")){
			$("#"+k).empty();
			if(jsonData[k+'_name'] != null){
				$("#"+k).append("<option value='"+v+"'>"+jsonData[k+'_name']+"</option>");
				$("#"+k).attr("style","");
			}
		}else{
			$('#'+formId+' #'+k).val(v);
		}
	});
}
function loadForm(formId,jsonData){
	$('#'+formId)[0].reset();
	var formData;
	for(var key in jsonData.formData){
		formData = jsonData.formData;
		break;
	}
	if (jsonData.result===false){
		window.alert(jsonData.msg);
		return;
	}
	$.each(formData,function(k,v){
		k=k.toLowerCase();
		if($('#'+k).attr("type")=="radio"){
			$("input[name="+k+"][value="+v+"]").attr("checked",true);
		}else{
			$('#'+k).val(v);
		}
	});
}
//提示成功与否，一般在提交操作使用

function convertDataFormat(dataString){
	var returnData="";
	var dataArray = dataString.split("&");
	for(var i=0;i<dataArray.length;i++){
		var curdata = dataArray[i].split("=");
		returnData = returnData+curdata[0]+":'"+curdata[1]+"',";
	}
	//returnData = returnData.substring(0,returnData.length-1);
	return returnData;
}
function _loadSelect(jsonData){
	var selectData = jsonData.selectData;
	$.each(selectData,function(k,v){
		k=k.toLowerCase();
		$("#"+k).empty();
		$.each(v,function(z,m){
			$("#"+k).append("<option value='"+m['dm']+"'>"+m['mc']+"</option>"); 
		})
	});
}
function _loadSelect1(jsonData){
	var selectData = jsonData.selectData;
	$.each(selectData,function(k,v){
		k=k.toLowerCase();
		$("#"+k).empty();
		$.each(v,function(z,m){
			if(m['mc'] != '请选择'){
				$("#"+k).append("<option value='"+m['dm']+"'>"+m['mc']+"</option>");
			}
		})
	});
}
function mediateDiv(divId){
	var w = $("#"+divId).css("width");
	var h = $("#"+divId).css("height");
	var wid = document.body.cliventWidth;
	var hei = document.body.clientHeight;
	if(wid>w){
		var left = (wid-w)/2+"px";
		alert($("#"+divId).css("width"));
		$("#"+divId).css({"left":"138px"});
	}
	if(hei>h){
		var top = (hei-h)/2+"px";
		$("#"+divId).css("top",top);
	}
}
function ajaxAlert(err){

}
function bindPage(jsonData){
	var pageData;
	var  tableId;
	$.each(jsonData,function(i,d){
		pageData = d.page;
		tableId = i;
	});
	var prefix="";//选择器前缀
	if (tableId===undefined){tableId="";}//页面中只有一个列表，不需要输入tableId
	else{prefix="#"+tableId+"div ";tableId="_"+tableId;}
	$(prefix+"span[navid=page_cur]").text(pageData.page_cur);
	$(prefix+"span[navid=page_allPage]").text(pageData.page_allPage);
	$(prefix+"span[navid=page_allCount]").text(pageData.page_allCount);
	var pageNum=parseInt(pageData.page_cur);
	var page_pre=pageNum-1;
	var page_next=pageNum+1;	
	$(prefix+"span[navid=page_pre]").unbind();
	$(prefix+"span[navid=page_pre]").click(function(){
		if (page_pre===0){window.alert("已到达第一页");}		
		else{eval("clickData"+tableId+"("+page_pre+")");}
	});
	$(prefix+"span[navid=page_next]").unbind();
	$(prefix+"span[navid=page_next]").click(function(){
		if (page_next>parseInt(pageData.page_allPage)) {window.alert("已是最后一页");return false;}
		eval("clickData"+tableId+"("+page_next+")");return false;
		
	});
	$(prefix+"span[navid=page_frist]").unbind();
	$(prefix+"span[navid=page_frist]").click(function(){
		eval("clickData"+tableId+"(1)");
	});
	$(prefix+"span[navid=page_enter]").unbind();
	$(prefix+"span[navid=page_enter]").click(function(){
		var _goto=$(prefix+"input[navid=page_goto]").val();
		if (isNaN(_goto)){window.alert("请输入有效的页码");return false;}
		if ((_goto>parseInt(pageData.page_allPage))||(_goto<1)) {window.alert("请输入有效的页码");return false;}
		eval("clickData"+tableId+"("+_goto+")");
	});
	$(prefix+"span[navid=page_last]").unbind();
	$(prefix+"span[navid=page_last]").click(function(){
		eval("clickData"+tableId+"("+pageData.page_allPage+")");
	});
}
function prompt(str){
	if($("#dialog-message").length <= 0){
		$("body").append("<div id='dialog-prompt' style='height:100;width:230;left:120;top:120;' align='center' title='信息提示'></div>");  
	}
	$("#dialog-message").append("<br/><br/>"+str);
	$("#dialog-message").dialog({ });
	setTimeout(function(){$("#dialog-message").dialog('close');$("#dialog-message").empty()},1000);	
	return true;
}
function getObj(datas,query_page){
	var dataStr = "{";
	if(datas == ""){
		dataStr+="'page':'"+query_page+"'}";
	}else{
		var dataArray = datas.split("&");
		for(var i=0;i<dataArray.length;i++){
			dataStr +=dataArray[i].split("=")[0]+":'"+decodeURI(dataArray[i].split("=")[1])+"',";
		}
		dataStr = dataStr.substring(0,dataStr.length-1);
		dataStr = dataStr +"}";
	}
	
	//dataStr +="page_goto:'"+query_page+"'}";
	return eval("("+dataStr+")");
}
function solrbind(jsonData){
	var tableId = "solrlist";
	$("#"+tableId+" tbody tr:not(:first)").remove();
	var tableData=jsonData.gridData.solrlist.data;
	var trInfo=$("#"+tableId+" tbody tr:first").html();
	var keepRow=$("#"+tableId+" tbody tr").length-1;//页面中保留行数
	var i;
	for (i=0;i<tableData.length;i=i+1){
		var rowJson=tableData[i];
		var newTr=trInfo;
		for(var j in rowJson){
			var jValue=eval("rowJson."+j);
			var re=eval("/{"+j+"}/ig");
			newTr=newTr.replace(re,jValue);
		}
		newTr=newTr.replace(/{.*}/ig,"");//清空没有数据的单元格
		$("#"+tableId+" tbody tr:eq("+i+")").after("<tr >"+newTr+"</tr>");
		if (i<keepRow)	{$("#"+tableId+" tbody tr:last").remove();}
	}
}
function bindPageSolr(jsonData){
	jsonData = jsonData.gridData; 
	var pageData;
	var  tableId;
	$.each(jsonData,function(i,d){
		pageData = d.page;
		tableId = i;
	});
	var prefix="";//选择器前缀
	if (tableId===undefined){tableId="";}//页面中只有一个列表，不需要输入tableId
	else{prefix="#"+tableId+"div ";tableId="_"+tableId;}
	$(prefix+"span[navid=page_cur]").text(pageData.page_cur);
	$(prefix+"span[navid=page_allPage]").text(pageData.page_allPage);
	$(prefix+"span[navid=page_allCount]").text(pageData.page_allCount);
	var pageNum=parseInt(pageData.page_cur);
	var page_pre=pageNum-1;
	var page_next=pageNum+1;	
	$(prefix+"span[navid=page_pre]").unbind();
	$(prefix+"span[navid=page_pre]").click(function(){
		if (page_pre===0){window.alert("已到达第一页");}		
		else{eval("clickData"+tableId+"("+page_pre+")");}
	});
	$(prefix+"span[navid=page_next]").unbind();
	$(prefix+"span[navid=page_next]").click(function(){
		if (page_next>parseInt(pageData.page_allPage)) {window.alert("已是最后一页");return false;}
		eval("clickData"+tableId+"("+page_next+")");return false;
		
	});
	$(prefix+"span[navid=page_frist]").unbind();
	$(prefix+"span[navid=page_frist]").click(function(){
		eval("clickData"+tableId+"(1)");
	});
	$(prefix+"span[navid=page_enter]").unbind();
	$(prefix+"span[navid=page_enter]").click(function(){
		var _goto=$(prefix+"input[navid=page_goto]").val();
		if (isNaN(_goto)){window.alert("请输入有效的页码");return false;}
		if ((_goto>parseInt(pageData.page_allPage))||(_goto<1)) {window.alert("请输入有效的页码");return false;}
		eval("clickData"+tableId+"("+_goto+")");
	});
	$(prefix+"span[navid=page_last]").unbind();
	$(prefix+"span[navid=page_last]").click(function(){
		eval("clickData"+tableId+"("+pageData.page_allPage+")");
	});
}

function getNext(obj,nextId){
	sys_post("/cc/gdgl.do?method=queryNextSelect","value="+obj.value+"&nextId="+nextId,function(json){
		bindSelect(json);
	})
}