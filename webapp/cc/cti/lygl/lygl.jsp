<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="lygl.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   <form id="query_form">
   			<tr>
	    		<td class="query_label">主叫</td>
	    		<td class="query_text"><input name="caller" class="easyui-textbox"></td>
				<td class="query_label">被叫</td>
	    		<td class="query_text"><input name="called" class="easyui-textbox"></td>
	    		<td class="query_label">工号</td>
	    		<td class="query_text"><input name="gh" class="easyui-textbox"></td>
	    		<td class="query_label">查询时间</td>
	    		<td  class="query_label">
   					<input name="begintime_start" class="easyui-datebox" style="width:120px">
   				</td>
   				<td class="query_label">
   					至
   				</td>
   				<td class="query_label">
   					<input name="begintime_end" class="easyui-datebox" style="width:120px">
   				</td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="query()">搜索</a>
	    		</td>
   			</tr>
			</form>		
    		</table>
    	  
	</div>
	
	 <table id="table_list"   border = 0 title="数据列表" style="width:100%;" data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'caller',align:'center'" width="15%">主叫</th>
				<th data-options="field:'called',align:'center'" width="15%">被叫</th>
				<th data-options="field:'begintime',align:'center'" width="15%">开始时间</th>
				<th data-options="field:'endtime',align:'center'" width="15%">结束时间</th>
				<th data-options="field:'gh',align:'center'" width="15%">工号</th>
				<th data-options="field:'calltype',align:'center'" width="15%">类型</th>
				<th data-options="field:'path',align:'center', formatter:formatOper" width="8%"></th> 
			</tr>
		</thead>
	</table>
  </body>
</html>
