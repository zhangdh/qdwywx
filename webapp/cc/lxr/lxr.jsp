<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="lxr.js" type="text/javascript"></script>
  </head>
  
  <body>
      <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   	<form id="query_form">
   			<tr>
   				<td class="query_label">姓名 </td>
	    		<td class="query_text"><input name="cx_name" id="cx_name" class="easyui-textbox"></td>
	    		<td class="query_label">号码</td>
	    		<td class="query_text"><input name="cx_contact_num" id="cx_contact_num" class="easyui-textbox"></td>
	    		<td class="query_label">类别</td>
	    		<td >
	    			<select name="cx_type" id="cx_type" style="width:120px;height:20px;"></select>
	    		</td>
	    		<td  class="show_table_label">所属点部：</td>
				<td class="show_table_text">
					<select type="text"  name="cx_station" id="cx_station"  style="width:120px;height:20px;" onchange="getNext(this,'cx_grid')"></select>
				</td>
					<td  class="show_table_label">所属片区：</td>
				<td class="show_table_text">
					<select id="cx_grid" name="cx_grid" linkage = true  style="width:120px;height:20px;"></select>
				</td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="query()">搜索</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-undo'" onclick="reset('query_form')">清空</a>
	    		</td>
   			</tr>
			</form>		
    		</table>
	 </div>
	 <table id="table_list"   border = 0 title="市民列表" style="width:99%;" data-options="singleSelect:true,collapsible:true">
		<thead>
			<tr>
				<th data-options="field:'name',align:'center'" width="15%">姓名</th>
				<th data-options="field:'contact_num',align:'center'" width="15%">联系方式</th>
				<th data-options="field:'type_name',align:'center'" width="15%">类别</th>
				<th data-options="field:'grid_name',align:'center'" width="15%">所属片区</th>
				<th data-options="field:'address',align:'center'" width="30%">来电人地址</th>
				<th data-options="field:'guid',align:'center',formatter:formatOper" width="8%"></th> 
			</tr>
		</thead>
	 </table>
	
	<div id="show_div" class="easyui-panel" title="市民信息" data-options="modal:true" style="width:100%;">
 		 <div>
			 <form id="show_form">
				<input type=hidden id="guid" name="guid">
				<table class="show_table">
					<tr>
						<td  class="show_table_label" style="width:10%">联系人姓名：</td>
						<td class="show_table_text" style="width:22%">
							<input type="text"  name="name" id="name">
						</td>
						<td  class="show_table_label" style="width:10%">来电号码：</td>
						<td class="show_table_text" style="width:22%">
							<input type="text"  name="call_num" id="call_num">
						</td>
						<td  class="show_table_label" style="width:10%">联系号码：</td>
						<td class="show_table_text" style="width:22%">
							<input type="text"  name="contact_num" id="contact_num">
						</td>
					</tr>
					<tr>
						<td  class="show_table_label" style="width:10%">类型：</td>
						<td class="show_table_text" style="width:22%">
							<select id="type" name="type"></select>
						</td>
						<td  class="show_table_label">所属点部：</td>
						<td class="show_table_text">
							<select type="text"  name="station" id="station"  onchange="getNext(this,'grid')"></select>
						</td>
							<td  class="show_table_label">所属片区：</td>
						<td class="show_table_text">
							<select id="grid" name="grid" linkage = true ></select>
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">联系地址：</td>
						<td class="show_table_text" colspan=5> 
							<input type="text"  name="address" id="address">
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">备注：</td>
						<td class="show_table_text" colspan=5> 
							<input type="text"  name="comment" id="comment">
						</td>
					</tr>
				</table>
			</form>
 		 </div>
 	</div>
  </body>
</html>
