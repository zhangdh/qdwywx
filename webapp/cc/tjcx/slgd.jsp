<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="slgd.js" type="text/javascript"></script>
  </head>
  
  <body>
      <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   	<form id="query_form">
   			<tr>
   				<td class="query_label" >查询时间：</td>
	    		<td  class="query_label">
   					<input name="cx_time_start" class="easyui-datebox" style="width:120px">
   				</td>
   				<td class="query_label">
   					至
   				</td>
   				<td class="query_label">
   					<input name="cx_time_end" class="easyui-datebox" style="width:120px">
   				</td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="queryData()">搜索</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-undo'" onclick="reset('query_form')">清空</a>
	    		</td>
   			</tr>
			</form>		
    	</table>
	 </div>
	 <table id="table_list"   border = 0 title="工单数据列表" style="width:99%;" data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'station_name',align:'center'" width="20%">点部</th>
				<th data-options="field:'count',align:'center'" width="20%">派单总数</th>
				<th data-options="field:'dcl',align:'center'" width="20%">待处理</th>
				<th data-options="field:'yht',align:'center'" width="20%">已回退</th>
				<th data-options="field:'yhf',align:'center'" width="18%">已回复</th>
			</tr>
		</thead>
	</table>
  </body>
</html>
