function modiPass(){
	if($("#new_password")=="" || $("#new_password2").val() == ""){
		alert("请输入密码");
		return　false;
	}
	if($("#new_password").val() != $("#new_password2").val()){
		alert("两次输入密码不一致，请重新输入");
		return　false;
	}
	sys_post("/base/org.do?method=modiPassword","new_password="+$("#new_password").val(),function(json){
		alertMes(json);
	})
}

function modiSelf(){
	var postData = "name="+$("#name").val()+"&telphone="+$("#telphone").val()+"&lxdz="+$("#lxdz").val();
	sys_post("/base/org.do?method=modiSelf",postData,function(json){
		alertMes(json);
	})
}