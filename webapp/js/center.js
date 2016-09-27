$(function(){
	_Post("/base/menu.do?method=GetMenu","",function(jsonData){
		var  pmenu = new Array();
		var menStr = "";
		zNodes = jsonData.menuStr;
		var obj = eval("("+zNodes+")");
		$.each(obj,function(k,v){
			if(v.sjid == "0"){
				pmenu.push(k);
			}
		});      
		for (var i = 0; i < pmenu.length; i++) {
			var pid = obj[pmenu[i]].menu_id;
			var pname = obj[pmenu[i]].text;
            var menStr = '<div style="overflow:auto;" id="'+pid+'"> ';
       		$.each(obj,function(k,v){
				if(v.sjid == pid){
					menStr = menStr+"<div style='width:100%;height:25px;line-height:20px;' onMouseOver='mouseover(this)' onMouseOut='mouseout(this)' onclick=openUrl(this,'"+v.text+"','"+v.url+"')>"
									+"<img style='float:left;width:16px;height:16px;display:block;margin-top:4px;margin-left:15px;' src='"+webcontext+"/resources/menu/"+v.icon+"'>"
									+"<span style='height:100%;line-height:25px;font-family:Microsoft Yahei;font-size:11px;color:#222222'>"
									+"&nbsp;&nbsp;"+v.text+"</span></div>";
									+"<span style='width:100%;height:2px;background:#000; background-image:url(images/dot.png);' />"
				}
			});
			menStr = menStr+'</div>';
			$('#menus').accordion('add',{
                title: pname,
                selected: false,
                content:menStr
            });
   		}
	});
});

function mouseover(obj){
	 $(obj).css("background-color","#FFEC8B");
}
function mouseout(obj){
	$(obj).css("background-color","");
}

function openUrl(obj,title,url){
	if ($('#tabs').tabs('exists', title)){
		$('#tabs').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0" src="'+webcontext+url+'" style="width:100%;height:100%;"></iframe>';
		$('#tabs').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
	}
}