<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="${webcontext}/base/zone/zone.js" type="text/javascript"></script>
  </head>
  
  <body>
   	 <div class="easyui-layout" style="width:100%;height:100%;">
		<div  region="west"  split="true" title="" style="width:160px;">
	 		<ul id="grid" ></ul>
			 
		</div>
		<div  region="center"  >
			<table id="table_list"   border = 0 title="区域列表" style="width:100%;"  data-options="pagination:true">
				<thead>
					<tr>
						<th data-options="field:'name',align:'center'" width="32%">名称</th>
						<th data-options="field:'type_name',align:'center'" width="30%">类别</th>
						<th data-options="field:'create_time',align:'center'" width="30%">创建时间</th>
						<th data-options="field:'guid',align:'center',formatter:formatOper" width="5%"></th> 
					</tr>
				</thead>
			</table>
					<div id="p" class="easyui-panel" title="点部信息" style="width:99%;height:200px">   
			      <form id="show_formGrid">
					<input type=hidden id="guid" name="guid">
					<table class="show_table">
						<tr>
							<td  class="show_table_label" width="10%">网格名称：</td>
							<td class="show_table_text" width="12%">
								<input type="text"  name="name" id=name required = "true" showName="网格名称">
							</td>
							
							<td  class="show_table_label" width="10%">网格级别： </td>
							<td class="show_table_text" width="12%">
								<select id="type" name="type">
									<option value="1010801">点部</option>
									<option value="1010802">区域</option>
								</select>
							</td>
							
							<td  class="show_table_label" width="10%">上级网格： </td>
							<td class="show_table_text" width="12%">
								<select id="higher_id" name="higher_id"></select>
							</td>
							<td  class="show_table_label" width="10%">关联部门： </td>
							<td class="show_table_text" width="12%">
								<select id="org_id" name="org_id"></select>
							</td>
						</tr>
						<tr>
							<td  class="show_table_label">地址： </td>
							<td  class="show_table_text" colspan="7">
								<input type="text"  name="address" id="address">
							</td>
						</tr>
						<tr>
						  	<td  class="show_table_label">经纬度标记：</td>
							<td class="show_table_text" colspan="6">
								<input type="text"  name="points" id="points" readonly="true">
							</td>
							<td  class="show_table_text">
								<a onclick="getPosition()" style="color:#0EABF4;font-size:12px">标记位置</a>
							</td>
						</tr>
						<tr>

							<td  class="show_table_label">备注：</td>
							<td class="show_table_text" colspan="7">
								<textarea id="comment" name="comment" style="height:50px;" ></textarea>
							</td>
						</tr>
					</table>
				</form>
		</div> 	
		</div>
	</div>
  </body>
</html>
