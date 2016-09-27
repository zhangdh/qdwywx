$(function(){
 	getMenu();
 	getJs();
});

function getMenu(){
	_Post("/base/authority.do?method=GetMenu","",function(jsonData){
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
			var menuStr = '<tr><td class="show_table_label" style="width:160px"><input name="menu"  type=checkbox value="'
							+pid+'" id="'+pid+'" level="p" sjid="0" onClick  ="changeBox(this)"> '+pname+'</td><td class="show_table_label">';
			
       		$.each(obj,function(k,v){
				if(v.sjid == pid){
					menuStr = menuStr + '<input type=checkbox level="c" name="menu" value="'+v.menu_id+'" id="'+v.menu_id+'" sjid="'+pid+'" onClick= "changeBox(this)">'+v.text;
				}
			});
			menuStr = menuStr+"</td>"
			menuStr = menuStr+'</tr>';
			$("#menu_id").append(menuStr);
   		}
   		
   		$("#menu_id").append('<tr><td style="text-align:center" colspan=2 ><input type=button value="保存授权" onclick="saveAuthority()" ></td></tr>')
	});
}

function getJs(){
	_Post("/base/authority.do?method=GetJs","",function(jsonData){
		$.each(jsonData.rows,function(k,v){
			$("#js").append('<input type=radio name="js" onClick="changeRadio(this)" value="'+v.jsid+'">&nbsp;&nbsp;&nbsp;'+v.jsmc);
		});
	});
}

function changeBox(obj){
	if(obj.level=="p"){
		$("[sjid="+obj.id+"]").attr("checked",$(obj).is(":checked"));
	}else if(obj.level=="c"){
		if($(obj).is(":checked")){
			$("#"+obj.sjid).attr("checked",true);
		}
	} 
}

function changeRadio(obj){
	var jsid = $('input:radio[name=js]:checked').val();
	_Post("/base/authority.do?method=GetAuthority","js_id="+jsid,function(jsonData){
		$.each(jsonData.rows,function(k,v){
			$("#"+v.menu_id).attr("checked",true);
		});
	}); 
}

function saveAuthority(){
	var jsid = $('input:radio[name=js]:checked').val();
	var array= new Array();
	$("input[name='menu']").each(function(){
	    if ($(this).is(":checked")) {
	    	 
	        array.push(this.id); 
	    }
	});
	if(jsid != "" && array.length > 0 ){
		_Post("/base/authority.do?method=saveAuthority","js_id="+jsid+"&menus="+array.join(","),function(jsonData){
			 alertMes(jsonData);
		});
	}
	
}