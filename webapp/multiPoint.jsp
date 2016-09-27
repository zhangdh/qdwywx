<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CQtzvv0B4S94vbVmeKu3KAKQPxEGGHlo"></script>
	<!--加载鼠标绘制工具-->
	<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
	<!--加载检索信息窗口-->
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />
	
	<script type="text/javascript">
		//百度地图API功能
		var dbinfo = window.dialogArguments ;
		var map ;
		function loaded(){
			map = new BMap.Map('map', {enableMapClick: false});
		    map.centerAndZoom('青岛', 16);
		    map.enableScrollWheelZoom();  
		    loadDb();
		}	    	    
					
	function loadDb(){
	var markerArr =dbinfo; //从数据库取到的点部信息
 	for (var i = 0; i < markerArr.length; i++) {
		var marker = new Array(); 	//存放点部的marker
		(function(){  //使用闭包
			var p0 = markerArr[i].point.split(",")[0]; //  
			var p1 = markerArr[i].point.split(",")[1];
			var point = new BMap.Point(p0, p1); 
			var info= new BMap.InfoWindow(markerArr[i].name + '</br>地址：' + markerArr[i].address+'</br>编号:'+markerArr[i].guid);
			var myIcon = new BMap.Icon("resources/map/db.png", new BMap.Size(40,59));
			marker[i] = new BMap.Marker(point,{icon:myIcon}); 
			marker[i].setLabel(markerArr[i].guid);
			marker[i].addEventListener('mouseover', function(){
			 this.openInfoWindow(info);
			});	
			marker[i].addEventListener('mouseout', function(){
			 this.closeInfoWindow() ;
			});	
			//点部点击事件
			marker[i].addEventListener('click', function(){
			   //从数据库中取该点部的网格 和 人员
			     map.clearOverlays();  //先删除之前的标记，然后再增加新的
				 var  polylineArr = new Array();		
				 var  userMarker = new Array();
				 //var  markpoints = new Array();
			     var dbid = this.getLabel();
				 sys_post("/cc/gdgl.do?method=queryWgInfo&dbid="+dbid,"",function(json){
				   var wgArr = json.wginfo; //网格的数组,多个网格
				   var userArr = json.userinfo; // 点部的人
    			   for(var n=0;n < wgArr.length; n++){
    				       var  markpoints = new Array();
    					   var  plpoints=wgArr[n].point;  //一个多边形对应的点
                           var  wgid = wgArr[n].guid;
    					   var polylinepoints=plpoints.split(';');
    					   for(var j=0;j<polylinepoints.length;j++){
    						   markpoints[j] = new BMap.Point(polylinepoints[j].split(',')[0],polylinepoints[j].split(',')[1]);
    						   };
    					   
    					   polylineArr[n] = new BMap.Polygon(
    				                markpoints, {strokeColor:"red",fillColor:'', strokeWeight:3, strokeOpacity:0.6});   //创建折线
    		    		   map.addOverlay(polylineArr[n]); 
    				 };
  				    for(var q=0;q<userArr.length;q++){
  						 (function(){
  						 
							if(userArr[q].points != null){
  						 		var p0 = userArr[q].points.split(",")[0];  
	  						    var p1 = userArr[q].points.split(",")[1];
	  						    var wgid = userArr[q].guid;
	  						    var grid_name = userArr[q].grid_name;
						    	var yhid = userArr[q].yhid;
						    	var name = userArr[q].name;
	  						    var userpoint = new BMap.Point(p0, p1); 
	  							var info= new BMap.InfoWindow('姓名:'+userArr[q].name + '<br/>电话:' + userArr[q].telephone+'<br/>负责区域:'+ userArr[q].grid_name);
	
	  							var myIcon = new BMap.Icon("resources/map/worker.png", new BMap.Size(40,59));
	  							userMarker[q] = new BMap.Marker(userpoint,{icon:myIcon}); 
	  							userMarker[q].addEventListener('mouseover', function(){
	  								 this.openInfoWindow(info);
	  							    });	
	  							userMarker[q].addEventListener('click',function(){
	  							  getSelectData(dbid,wgid,grid_name,yhid,name);
	  						    });
	  							map.addOverlay(userMarker[q]); 
	  						}
  						 })();
  	  				};
				});
			
			});	
			map.addOverlay(marker[i]); 
			marker[i].disableMassClear(false);
		 })();
	 	}
	}
	//搜索
    function doGetPosition(){
	 var key =  document.getElementById('key').value;
	 var gc = new BMap.Geocoder();
	 var local = new BMap.LocalSearch(map, {
		renderOptions:{map: map,autoViewport:false}
	});
	
	local.search(key);
	local.setMarkersSetCallback(function(pois){
	    for(var i=0;i<pois.length;i++){
	         pois[i].marker.addEventListener("click", function(e){
		          gc.getLocation(e.point, function(rs){
			      var addComp = rs.addressComponents;
			      var address =  addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
			      $('#key').val(address);
		      });
	         });  
	     }
		});
	}
	//重置
	function doReset(){
	   map.clearOverlays(); 
	   $('#key').val();
	}
	function getSelectData(dbid,wgid,grid_name,yhid,name){
	   var selectPosition = $('#key').val();
	   alert(selectPosition);
	   var reval = dbid+','+wgid+','+grid_name+','+yhid+','+name;	   
	   window.returnValue=reval;
	   window.close(); 
	 }
	</script>
	
</head>
<body onload="loaded()">
	<div id="allmap" style="overflow:hidden;zoom:1;position:relative;width:1000px;height:650px">	
	    <div style="position:absolute;top:20px;left:40%;z-index:9999;height:20px;">
		<input type="text" name="key" id="key" style="width:300px;height:30px;line-height:30px;"/>
		<input type="button" value="查找" onclick="doGetPosition()"/>
		<input type="button" value="重置" onclick="doReset()"/></div>
		<div id="map" style="width:100%;height:100%;"></div>
	</div>
</body>
</html>