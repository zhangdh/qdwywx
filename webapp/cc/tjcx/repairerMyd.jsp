<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="repairerMyd.js" type="text/javascript"></script>
  </head>
  
  <body>
      <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   	<form id="query_form">
   			<tr>
   				<td class="query_label" >选择点部：</td>
	    		<td  class="query_label">
   					<select name="cx_repairer_station" id="cx_repairer_station" style="width:120px"></select>
   				</td>
   				<td class="query_label" >查询时间：</td>
	    		<td  class="query_label">
   					<input name="cx_create_time_start" class="easyui-datebox" style="width:120px">
   				</td>
   				<td class="query_label">
   					至
   				</td>
   				<td class="query_label">
   					<input name="cx_create_time_end" class="easyui-datebox" style="width:120px">
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
				<th data-options="field:'repairer_name',align:'center'" width="10%" rowspan="2">点部</th>
				<th data-options="field:'count',align:'center'" width="8%" rowspan="2">派单数</th>
				<th data-options="field:'yhf',align:'center'" width="8%" rowspan="2">已回复</th>
				
				<th colspan="3">态度满意度</th>
				<th colspan="3">质量满意度</th>
				<th colspan="3">清洁满意度</th>
			</tr>
			<tr>
				<th data-options="field:'attitude_hmy',align:'center'" width="8%">很满意</th>
				<th data-options="field:'attitude_my',align:'center'" width="8%">满意</th>
				<th data-options="field:'attitude_bmy',align:'center'" width="8%">不满意</th>
				<th data-options="field:'quality_hmy',align:'center'" width="8%">很满意</th>
				<th data-options="field:'quality_my',align:'center'" width="8%">满意</th>
				<th data-options="field:'quality_bmy',align:'center'" width="8%">不满意</th>
				<th data-options="field:'clean_hmy',align:'center'" width="8%">很满意</th>
				<th data-options="field:'clean_my',align:'center'" width="8%">满意</th>
				<th data-options="field:'clean_bmy',align:'center'" width="8%">不满意</th>
			</tr>
		</thead>
	</table>
  </body>
</html>
