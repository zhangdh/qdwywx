<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="syspara.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   	<form id="query_form">
   			<tr>
   				<td class="query_label"> 参数名称 </td>
	    		<td class="query_text"><input name="cx_name" class="easyui-textbox"></td>
	    		<td class="query_label">参数值</td>
	    		<td class="query_text"><input name="cx_value" class="easyui-textbox"></td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="query()">搜索</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-undo'" onclick="reset('query_form')">清空</a>
	    		</td>
   			</tr>
			</form>		
    		</table>
    	  
	</div>
	
	<table id="table_list"   border = 0 title="数据列表" style="width:100%;" 
					data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'para_name',align:'center'" width="20%">参数名称</th>
				<th data-options="field:'para_value',align:'center'" width="20%">参数值</th>
				<th data-options="field:'para_comment',align:'center'" width="55%">说明</th>
				<th data-options="field:'para_id',align:'center',formatter:formatOper" width="4%"></th> 
			</tr>
		</thead>
	</table>
	
 	<div id="show_div" class="easyui-window" title="新增系统参数" data-options="modal:true,closed:true,iconCls:'icon-add'" style="width:450px;height:200px;">
 		 <div>
			 <form id="show_form">
				<input type=hidden id="para_id" name="para_id">
				<table class="show_table">
					<tr>
						<td  class="show_table_label" width="50%">参数名称：</td>
						<td class="show_table_text">
							<input type="text"  name="para_name" id="para_name">
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">参数值： </td>
						<td class="show_table_text">
							<input type="text"  name="para_value" id="para_value" class="easyui-validatebox" missingMessage="xxx必须填写" >
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">参数说明：</td>
						<td class="show_table_text">
							<textarea id="para_comment" name="para_comment" style="height:50px;" ></textarea>
						</td>
					</tr>
				</table>
			</form>
 		 </div>
 		 <div class="show_div_buttom" >
         	<a class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="save()">保存</a>
    	</div>
 	</div>
  </body>
</html>
