<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="code.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   	<form id="query_form">
   			<tr>
   				<td class="query_label"> 功能模块 </td>
	    		<td class="query_text">
	    			<select id="module" name="module" style="width:150px" onchange="change(this.value)"></select>
	    			
	    		</td>
	    		<td class="query_label">代码类型</td>
	    		<td class="query_text">
	    			<select id="dm_type" name="dm_type" style="width:150px"></select>
	    		</td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="query(1)">搜索</a>
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
				<th data-options="field:'dm',align:'center'" width="44%">代码</th>
				<th data-options="field:'mc',align:'center'" width="50%">代码名称</th>
				<th data-options="field:'dm_type',align:'center',formatter:formatOper" width="5%"></th> 
			</tr>
		</thead>
	</table>
	
 	<div id="show_div" class="easyui-window" title="新增代码" data-options="modal:true,closed:true,iconCls:'icon-add'" style="width:450px;height:120px;padding:5px">
 		 <div>
			 <form id="show_form">
				<input type=hidden id="dm" name="dm">
				<table class="show_table">
					<tr>
						<td  class="show_table_label">代码名称：</td>
						<td class="show_table_text">
							<input  type="text"  name="mc" id="mc" >
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
