<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="hwysc.js" type="text/javascript"></script>
  </head>
  <body>
    <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   <form id="query_form">
   			<tr>
	    		<td class="query_label">查询时间</td>
	    		<td  class="query_label">
   					<input name="cx_begintime_start" class="easyui-datebox" style="width:120px">
   				</td>
   				<td class="query_label">
   					至
   				</td>
   				<td class="query_label">
   					<input name="cx_begintime_end" class="easyui-datebox" style="width:120px">
   				</td>
   				<td class="query_label">统计步长</td>
	    		<td  class="query_label">
	    			<select id="cx_tjbc" name="cx_tjbc" style="width:120px">
						<option value=""></option>
	    				<option value="day">天</option>
	    				<option value="month">月</option>
	    			</select>
   				</td>
   				<td class="query_label">统计类型</td>
	    		<td  class="query_label">
	    			<select id="cx_calltype" name="cx_calltype" style="width:120px">
						<option value=""></option>
	    				<option value="0">呼入</option>
	    				<option value="1">呼出</option>
	    			</select>
   				</td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="query()">搜索</a>
	    		</td>
   			</tr>
			</form>		
    		</table>
    	  
	</div>
	
	 <table id="table_list"   border = 0 title="数据列表" style="width:100%;">
		<thead>
			<tr>
				<th data-options="field:'gh',align:'center'" width="33%">工号</th>
				<th data-options="field:'sjd',align:'center'" width="33%">时间段</th>
				<th data-options="field:'sl',align:'center'" width="32%">数量(单位:分钟)</th>
			</tr>
		</thead>
	</table>
  </body>
</html>
