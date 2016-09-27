<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="setting.js" type="text/javascript"></script>
  </head>
  
  <body>
	<div id="p" class="easyui-panel" title="密码修改" style="width:98%;">   
		<table class="show_table" width=200 height=50> 
			<tr>
				<td  class="show_table_label" width="10%">新密码：</td>
				<td class="show_table_text" width="19%" >
					<input type="password"  name="new_password" id="new_password" required = "true" showName="新密码" >
				</td>
				<td  class="show_table_label" width="10%">再次输入： </td>
				<td class="show_table_text" width="19%">
					<input type="password"  name="new_password2" id="new_password2" required = "true" showName="再次输入">
				</td>
			</tr>
			<tr>
				<td class="show_table_text" colspan="4" align="center">
					<input type=button value="确认修改" onclick="modiPass()" style="width:60px;height:20px"></input>
				</td>
			</tr>
		</table>
	</div>
	<div id="p_self" class="easyui-panel" title="个人信息" style="width:98%;">   
		<table class="show_table"> 
			<tr>
				<td  class="show_table_label" width="10%">姓名：</td>
				<td class="show_table_text"  width="19%">
					<input type="text"  name="name" id="name" required = "true" showName="姓名">
				</td>
				<td  class="show_table_label"  width="10%">联系方式：</td>
				<td class="show_table_text"  width="19%">
					<input type="text"  name="telphone" id="telphone">
				</td>
			</tr>
			<tr>
				<td  class="show_table_label">联系地址：</td>
				<td class="show_table_text" colspan="3">
					<input type="text"  name="lxdz" id="lxdz">
				</td>
			</tr>
			<tr>
				<td class="show_table_text" colspan="4" align="center">
					<input type=button value="确认修改" onclick="modiSelf()" style="width:60px;height:20px"></input>
				</td>
			</tr>
		</table>
	</div>
  </body>
</html>
