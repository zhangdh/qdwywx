<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="/common/common.jsp"%>
<head>
<script>
	var login = '${login}';
	if('${login}' == 'false'){
		alert('${msg}');
	}
</script>
<script>
	function login(){
		var iname = document.getElementById("username").value;//$("#username").val();
		var ipassword = $("#password").val();
		if (iname == "") {
			alert("请输入用户名");
			$("#username").focus();
			return false;
		}
		if (ipassword == "") {
			alert("请输入密码");
			$("#password").focus();
			return false;
		}
	    document.formid.submit();
	}

	function reset(){
		
	}
</script>

<style>
	body {
		font-size: 12px;
		font-family: "宋体";
		margin-left: 0px;
		margin-top: 0px;
		margin-right: 0px;
		margin-bottom: 0px;
		background-color: white;
		overflow: hidden;
	}
	.button{
		vertical-align:middle ;
		width:60px;
		height:25px;
		text-align:center; 
		line-height:25px; 
		margin:0px; 
	}
	.login_text{
		padding: 15px 0 10px 0;
		color: #fff;
		font-size: 18px;
		font-weight: bold;
	}
	.text{
		width:265px;
		height:40px;
		border:none;
		line-height:40px;
		font-size:18px;
	}
</style>
</head>
  
<body>
<form id="formid" name="formid" action="${webcontext}/login.do" method="post">	
	<table	width="100%" border="0" cellspacing="0" cellpadding="0" align="center" style="vertical-align:middle;">
		<tr></tr>
		<tr>
			<td width="1440" height = "66" align="center"  style="background-image:url('resources/images/backgroud_top.png')">
				<table>
					<tr>
						<td width="440" align="left">
							<img src="resources/images/logo_text.png" width="385" height="36" />
						</td>
						<td width="600">
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td	width="1440" height = "600" align="center"  style="background-image:url('resources/images/background_side.png');background-repeat:repeat-x; ">
				<div style="height:600px;background:url('resources/images/background_left.png') 0px -50px no-repeat;">
				<div style="top:100px;left:350px;position:relative;">
				<table width="364" height = "310"  border="0" >
					<tr>
						<td  style="background-image:url('resources/images/login_bg.png')" >
							<table border="0" width="364" height = "320">
								<tr>
									<td  width="55" height="90" ></td>
									<td></td>
								</tr>
								<tr>
									<td  height="40"></td>
									<td>
										<input class="text" type="text" name="username" id="username"   onKeyDown="if(event.keyCode == 13) login();" value="${username}" placeholder="请输入用户名">
									</td>
								</tr>
								<tr>
									<td height="3"></td>
									<td></td>
								</tr>
								<tr>
									<td height="40"></td>
									<td>
										<input class="text" type="password" name="password" id="password"   onKeyDown="if(event.keyCode == 13) login();" placeholder="请输入密码">
									</td>
								</tr>
								<tr>
									<td colspan="2" height="120" >
										&nbsp;
										<img style="cursor:hand" width="140" heigth="44" src="resources/images/login.jpg" onClick="login();" />
										&nbsp;
										<img style="cursor:hand" width="140" heigth="44" src="resources/images/reset.jpg" onClick="reset();"/>
									</td>
								</tr>
								<tr>
									<td colspan="2"></td>
								</tr>
							</table>
						</td>
						</tr>
				</table>
				</div>
				</div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
