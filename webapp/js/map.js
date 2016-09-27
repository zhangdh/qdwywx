function loaded(){
	var map = new BMap.Map("allmap");  
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  
	map.addControl(new BMap.MapTypeControl());   
	map.setCurrentCity("青岛");          
	map.enableScrollWheelZoom(true);    
}