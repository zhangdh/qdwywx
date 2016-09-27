<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
  <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CQtzvv0B4S94vbVmeKu3KAKQPxEGGHlo"></script>
   <script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
   <link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
   <link rel="stylesheet" href="${webcontext}/base/zone/map.css" />
<div id="map"></div>
<div id="map_btn">
<input type="button" value="重新选择" onclick="clearAll()"/>
<input type="button" value="获取坐标" onclick="getDbPositon()"/>
</div>
<script type="text/javascript">
	// 百度地图API功能
	var obj = window.dialogArguments ; //传过来的坐标
    var map = new BMap.Map('map',{enableMapClick:false});
    map.enableScrollWheelZoom();  
    map.centerAndZoom('青岛', 15);
    if(obj!=''){
        // 已经有坐标了
        var a =new Array();
        a=obj.split(',');
     	var point = new BMap.Point(a[0],a[1]); 
     	var marker = new BMap.Marker(point); 
     	map.addOverlay(marker);  
      }
    var p_lng;
    var p_lat;
	map.addEventListener("click",function(e){
		  map.clearOverlays(); 
          p_lng = e.point.lng;
          p_lat = e.point.lat;
        var point = new BMap.Point(e.point.lng,e.point.lat); 
     	var marker = new BMap.Marker(point);    
     	map.addOverlay(marker);  
	});

    function clearAll() {
		map.clearOverlays();   //清除所有覆盖物
    }
	function getDbPositon(){
	  var reval = p_lng+','+p_lat;	   
	  window.returnValue=reval;
	  window.close(); 
	}
</script>