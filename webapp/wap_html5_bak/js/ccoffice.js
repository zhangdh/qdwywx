function sys_alert(msg){
	if(deviceType == "Android"){
		window.androd.showMsgTip(msg);
	}else if(deviceType == "PC"){
		alert(msg);
	}
}
function sys_goUrl(url){
	window.location.href=url;
}
function sys_getCookies(cookie_name){
	var allcookies = document.cookie;
	var cookie_pos = allcookies.indexOf(cookie_name);
	// 如果找到了索引，就代表cookie存在，
	// 反之，就说明不存在。
	if (cookie_pos != -1){
	// 把cookie_pos放在值的开始，只要给值加1即可。
		cookie_pos += cookie_name.length + 1;
		var cookie_end = allcookies.indexOf(";", cookie_pos);

		if (cookie_end == -1){
			cookie_end = allcookies.length;
		}

		var value = allcookies.substring(cookie_pos, cookie_end);
	}
	return value;
}
function sys_getParaFromUrl(key){
	var url = window.location.search;
	var value = "";
	if(url.indexOf("?")>=0){
		var paraStr = url.substr(url.indexOf("?")+1,url.length);
		var paraArray = paraStr.split("&");
		for(var i=0;i<paraArray.length;i++){
			if(paraArray[i].split("=")[0] == key){
				value =  paraArray[i].split("=")[1];
				break;
			}
		}
	}
	return value;
}
function sys_ajaxPost(_url,_data,callback){
	if (callback===undefined){
		$.ajax({type:"POST", url:sys_ctx+_url, data:_data, dataType:"json", timeout:6000,
		  success:function (msg) {
			ajaxAlert(msg);
		},error:function(xhr, ajaxOptions, thrownError){
			  if(xhr.status==0){
				hideLoader();
				$.alert('请求超时,请检查自己的网络情况');
			  }
		}});
	}else{
		$.ajax({type:"POST", url:sys_ctx+_url, data:_data, dataType:"json", success:callback,timeout:6000,
		  error:function(xhr, ajaxOptions, thrownError){
			  //alert('error');
			  if(xhr.status==0){
				hideLoader();
			 　	$.alert('请求超时,请检查自己的网络情况');
			  }
			  
			
		}});
	}
}
function bindGrid(json){
	if(json.page == 1 || json.page == undefined){
		$("#div_grid").empty("");
	}
	if(json.total != undefined){
		$("#total").val(json.total);
	}
	if(json.page != undefined){
		$("#page").val(json.page);
	}
	var rowsData = json.rows;
	var templetInfo=$("#div_grid_templet").html();
	for (i=0;i<rowsData.length;i=i+1){
		var rowJson=rowsData[i];
		var newInfo=templetInfo;
		for(var j in rowJson){
			var jValue=eval("rowJson."+j);
			jValue=filterTimestamp(jValue);
			var re=eval("/{"+j+"}/ig");
			if(jValue == null || jValue == "null"){
				jValue = "";
			}
			newInfo=newInfo.replace(re,jValue);
		}
		$("#div_grid").append(newInfo);
	}
	if(rowsData.length == 0){
		$(".preloader").html("已加载完毕");
		$(".preloader").removeClass("preloader");
	}else{
		if(getPageHeight("div_grid")){
			$(".preloader").html("已加载完毕");
			$(".preloader").removeClass("preloader");
		}
	}
}
function bindGridOfTemplate(gridId,templateId,json){
	$("#"+gridId).empty("");
	var rowsData = json.rows;
	var templetInfo=$("#"+templateId).html();
	for (i=0;i<rowsData.length;i=i+1){
		var rowJson=rowsData[i];
		var newInfo=templetInfo;
		for(var j in rowJson){
			var jValue=eval("rowJson."+j);
			jValue=filterTimestamp(jValue);
			var re=eval("/{"+j+"}/ig");
			if(jValue == null || jValue=='null'){
				jValue = "";
			}
			newInfo=newInfo.replace(re,jValue);
			
		}
		var reRow=eval("/{row}/ig");
		newInfo=newInfo.replace(reRow,i+1);
		
		$("#"+gridId).append(newInfo);
	}
	if(rowsData.length == 0){
		$(".preloader").html("已加载完毕");
		$(".preloader").removeClass("preloader");
	}else{
		if(getPageHeight("div_grid")){
			$(".preloader").html("已加载完毕");
			$(".preloader").removeClass("preloader");
		}
	}
}
function bindForm(divId,json){
	$.each(json,function(k,v){
		//alert(k);alert(v);
		var eles = document.getElementById(k);
		if(eles != null){
			switch(eles.tagName){
				case "INPUT":
					$("#"+divId+" #"+k).val(v);
					break;
				case 'TEXTAREA':
					$("#"+divId+" #"+k).val(v);
					break;
				case 'DIV': 
				case 'SPAN': 
				case 'TD':
				case 'LI':
					eles.innerHTML = v;
					break;
			}
		}
		
	});
}
function showLoader(text) {  
    //显示加载器.for jQuery Mobile 1.2.0  
    /*$.mobile.loading('show', {  
        text: text, //加载器中显示的文字  
        textVisible: true, //是否显示文字  
        theme: 'a',        //加载器主题样式a-e  
        textonly: false,   //是否只显示文字  
        html: ""           //要显示的html内容，如图片等  
    }); */
	 $.showPreloader(text);
}  
  
//隐藏加载器.for jQuery Mobile 1.2.0  
function hideLoader()  
{  
    //隐藏加载器  
    //$.mobile.loading('hide');
	 $.hidePreloader();
}  

function putData(key,value){
	
}
function filterTimestamp(str){
	var v=new String(str);
	if (v.match(/(\d\d:\d\d:\d\d)\.\d{1,3}/)) {
		var v2=v.replace(/(\d\d:\d\d:\d\d)\.\d{1,3}/g, "$1");
		return v2;
	}
	return v;
}
function alertMsg(json){
	var msg = json.msg;
	if(msg != ""){
		sys_alert(msg);
	}
}
function getDateStr(){
	var myDate = new Date();
	return myDate.getFullYear()+"-"+(parseInt(myDate.getMonth())+1)+"-"+myDate.getDate()+" "+myDate.getHours()+":"+myDate.getMinutes();
}

function getPageHeight(divId){
	var clientHeight = document.body.clientHeight;
	var height = document.getElementById(divId).offsetHeight;
	return height<clientHeight;
}
function pageDiv(divId){
	var scroller = document.getElementById(divId);
	$("#"+divId).on("scroll",function(){
		var scrollTop = scroller.scrollTop;
		var scrollHeight = scroller.scrollHeight;
		var height = scroller.offsetHeight;
		if(scrollTop + height - scrollHeight>-50){
			eval(divId+"_next()");
		}
	})
}
function logout(page){
	$.confirm('是否确认退出?',function () {
		sys_goUrl(page);
    });
}
function getAddr(){
	if(deviceType == "Android"){
		window.androd.getAddr();
	}
}
function getAppVersion(){
	var app_version = "1.0";
	if(deviceType == "Android"){
		app_version =  window.androd.getVerNo();
	}
	return app_version;
}
function  callBackAddr(x,y,addr){
	$("#points").html(x+","+y);
	$("#address").html(addr);
}
