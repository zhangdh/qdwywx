<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=CQtzvv0B4S94vbVmeKu3KAKQPxEGGHlo"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />
<link rel="stylesheet" href="${webcontext}/base/zone/map.css" />
<div id="map"></div>
<div id="map_btn">
<input type="button" value="重新选择" onclick="clearAll()"/>
<input type="button" value="获取坐标" onclick="getWgPositon()"/>
</div>
<script type="text/javascript">
	// 百度地图API功能
	var obj = window.dialogArguments ;
    var map = new BMap.Map('map',{enableMapClick:false});
    map.centerAndZoom('青岛', 15);
    map.enableScrollWheelZoom(); 
    //把已经有的画出来
    if(obj!=''&&obj.length>1){
        // 已经有坐标了
        var oldpionts =new Array();
        var oldmarkpoints = new Array();
        oldpoints=obj.split(';');
		for(var t=0;t<oldpoints.length;t++){
		  oldmarkpoints[t] = new BMap.Point(oldpoints[t].split(',')[0],oldpoints[t].split(',')[1]);
		 };
     	var oldpolygon = new BMap.Polygon(
     			oldmarkpoints, {strokeColor:"red",fillColor:'', strokeWeight:3, strokeOpacity:0.6});  
     	map.addOverlay(oldpolygon);  
      } 
    var polylinePoints="";
    var overlays = [];
	var overlaycomplete = function(e){
        overlays.push(e.overlay);
    };
    var styleOptions = {
        strokeColor:"red",    //边线颜色。
        fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
        strokeWeight: 3,       //边线的宽度，以像素为单位。
        strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
        fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
        strokeStyle: 'solid' //边线的样式，solid或dashed。
    }
    //实例化鼠标绘制工具
    var drawingManager = new BMapLib.DrawingManager(map, {
        isOpen: true, //是否开启绘制模式
        enableDrawingTool: false, //是否显示工具栏
        drawingToolOptions: {
            anchor: BMAP_ANCHOR_TOP_RIGHT, 
            offset: new BMap.Size(5, 5)
        },
        polylineOptions: styleOptions //多边形的样式
    });  
	 //添加鼠标绘制工具监听事件，用于获取绘制结果
	drawingManager.setDrawingMode(BMAP_DRAWING_POLYLINE);
    drawingManager.addEventListener('overlaycomplete', overlaycomplete);
	map.addEventListener("click",function(e){
		polylinePoints = polylinePoints+e.point.lng+','+e.point.lat+';'; //获取点的集合
	});
    function clearAll() {
    	polylinePoints="";
		map.clearOverlays();   //清除所有覆盖物
	    //实例化鼠标绘制工具
	    var drawingManager = new BMapLib.DrawingManager(map, {
	        isOpen: true, //是否开启绘制模式
	        enableDrawingTool: false, //是否显示工具栏
	        drawingToolOptions: {
	            anchor: BMAP_ANCHOR_TOP_RIGHT, 
	            offset: new BMap.Size(5, 5)
	        },
	        polylineOptions: styleOptions //多边形的样式
	    });  
	    drawingManager.setDrawingMode(BMAP_DRAWING_POLYLINE);
	    drawingManager.addEventListener('overlaycomplete', overlaycomplete);
    }
	function getWgPositon(){

		  if(polylinePoints!=''){
			  window.returnValue=polylinePoints;
			  }else{
			  window.returnValue='';	  
	      }
		  window.close(); 
		}

</script>