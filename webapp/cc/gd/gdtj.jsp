<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="gdtj.js" type="text/javascript"></script>
  </head>
  
  <body>
      <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		   	<form id="query_form">
   		   		<input type=hidden id="type" name="type" >
   			<tr>
   				<td class="query_label">
	   				<input type = radio name="tjlx" value="zt_dm" checked>当前状态
	   			</td>
	   			<td class="query_label">
	   				<input type = radio name="tjlx" value="nrlb">内容类别
	   			</td>
	   			<td class="query_label">
	   				<input type = radio name="tjlx" value="xzfl">性质分类
   				</td>
   				<td  class="query_label">
   					<input name="cx_sjq" class="easyui-datebox" style="width:140px">
   				</td>
   				<td class="query_label">
   					至
   				</td>
   				<td class="query_label">
   					<input name="cx_sjz" class="easyui-datebox" style="width:140px">
   				</td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="query()">搜索</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="checkRadio('Day')">日报</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="checkRadio('Week')">周报</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="checkRadio('Month')">月报</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="checkRadio('Year')">年报</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="exportTj()">导出</a>
	    		</td>
   			</tr>
			</form>		
    		</table>
	 </div>
	 <table id="table_list"   border = 0 title="数据列表" style="width:100%;" 
					data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'tjlx',align:'center'" width="33%">统计类别</th>
				<th data-options="field:'count',align:'center'" width="33%">数量</th>
				<th data-options="field:'percentage',align:'center'" width="32%">占比</th> 
			</tr>
		</thead>
	</table>
	
  </body>
</html>
