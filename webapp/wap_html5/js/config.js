//是否是安卓
var deviceType = "Android";
if((/android/gi).test(navigator.appVersion)){
	deviceType = "Android";
}else if((/iPad|iphone/gi).test(navigator.userAgent)){
	deviceType = "iOS";
}else{
	deviceType = "PC";
}

//window.localStorage.setItem("deviceType",deviceType);
//var com_ccoffice_token = window.localStorage.com_ccoffice_token;
var sys_ctx = window.location.protocol+"//"+window.location.host+"/qdwy";//电脑端