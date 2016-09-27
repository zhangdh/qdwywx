<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="org.js" type="text/javascript"></script>
  </head>
  
  <body style="overflow-x:hidden">
   	 <div class="easyui-layout" style="width:100%;height:100%;">
		<div  region="west"  split="true" title="" style="width:200px;">
	 		<ul id="org" ></ul>
			 
		</div>
		<div   region="center"  >
			 <table id="table_list"   border = 0 title="数据列表" style="width:100%;" 
					data-options="pagination:true">
				<thead>
					<tr>
						<th data-options="field:'mc',align:'center'" width="32%">名称</th>
						<th data-options="field:'lb_mc',align:'center'" width="30%">类别</th>
						<th data-options="field:'cjsj',align:'center'" width="30%">创建时间</th>
						<th data-options="field:'orgid',align:'center',formatter:formatOper" width="5%"></th> 
					</tr>
				</thead>
			</table>
			
			<input type=hidden id="sjid" name="sjid">
			<input type=hidden id="sjbmid" name="sjbmid">
			
			<div id="p" class="easyui-panel" title="部门信息" style="width:99%;height:140px">   
			      <form id="show_formBm">
					<input type=hidden id="cur_bmid" name="cur_bmid">
					<table class="show_table">
						<tr>
							<td  class="show_table_label" width="10%">部门名称：</td>
							<td class="show_table_text" width="40%">
								<input type="text"  name="bmmc" id="bmmc" required = "true" showName="部门名称">
							</td>
							<td  class="show_table_label" width="10%">部门电话： </td>
							<td class="show_table_text" width="40%">
								<input type="text"  name="bmdh" id="bmdh">
							</td>
						</tr>
						<tr>
							<td  class="show_table_label">备注：</td>
							<td class="show_table_text" colspan="3">
								<textarea id="bmbz" name="bmbz" style="height:50px;" ></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div> 			 
			<div id="p" class="easyui-panel" title="人员信息" style="width:99%;height:220px">   
			      <form id="show_formRy">
						<input type=hidden id="cur_yhid" name="cur_yhid">
						<table class="show_table">
							<tr>
								<td  class="show_table_label" width="10%">登录名：</td>
								<td class="show_table_text" width="20%" >
									<input type="text"  name="username" id="username" required = "true" showName="登录名名称" >
								</td>
								<td  class="show_table_label" width="10%">密码： </td>
								<td class="show_table_text" width="20%">
									<input type="password"  name="new_password" id="new_password" required = "true" showName="密码名称" value="******" readonly>
								</td>
								<td  class="show_table_label" width="10%">角色： </td>
								<td class="show_table_text" width="20%">
									<select id="js" name="js" required = "true" showName="角色名称">
									</select>
								</td>
							</tr>
							<tr>
								<td  class="show_table_label">姓名：</td>
								<td class="show_table_text">
									<input type="text"  name="name" id="name" required = "true" showName="姓名">
								</td>
								<td  class="show_table_label">联系方式：</td>
								<td class="show_table_text">
									<input type="text"  name="telphone" id="telphone">
								</td>
								<td  class="show_table_label">工号：</td>
								<td class="show_table_text">
									<input type="text"  name="kz101" id="kz101">
								</td>
								<!-- <td  class="show_table_label">手机：</td>
								<td class="show_table_text">
									<input type="text"  name="mobilephone" id="mobilephone">
								</td> -->
							</tr>
							<tr>
								<td  class="show_table_label">分机号：</td>
								<td class="show_table_text">
									<input type="text"  name="kz102" id="kz102">
								</td>
								<td  class="show_table_label">所属点部：</td>
								<td class="show_table_text">
									<select name="grid_higher" id="grid_higher" onchange="getGrid(this.value)">
									</select>
								</td>
								<td  class="show_table_label">所属区域：</td>
								<td class="show_table_text">
									<select name="grid" id="grid" linkage = true >
									</select>
								</td>
							</tr>
							<tr>
								<td  class="show_table_label">联系地址：</td>
								<td class="show_table_text" colspan="5">
									<input type="text"  name="lxdz" id="lxdz">
								</td>
							</tr>
							<tr>
								<td  class="show_table_label">位置：</td>
								<td class="show_table_text" colspan=4> 
									<input type="text"  name="points" id="points">
								</td>
								<td>
									<a onclick="getPosition()" style="color:#0EABF4;font-size:12px">标记位置</a>
								</td>
							</tr>
							<tr>
								<td  class="show_table_label">备注：</td>
								<td class="show_table_text" colspan="5">
									<textarea id="bz" name="bz" style="height:50px;" ></textarea>
								</td>
							</tr>
						</table>
					</form>
			</div> 
		</div>
	</div>
  </body>
</html>
