<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	<script src="wjdc.js" type="text/javascript"></script>
  </head>
  <body>
    <table id="table_list_project"   border = 0  style="width:100%;" 
					data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'mc',align:'center'" width="33%">任务名称</th>
				<th data-options="field:'cjsj',align:'center'" width="33%">创建时间</th>
				<th data-options="field:'zt_dm_mc',align:'center'" width="33%">当前状态</th>  
			</tr>
		</thead>
	</table>
	<table id="table_list_questionnaire"   border = 0 title="问卷列表" style="width:100%;" 
					data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'mc',align:'center'" width="33%">问卷名称</th>
				<th data-options="field:'cjsj',align:'center'" width="33%">创建时间</th>
				<th data-options="field:'zt_dm_mc',align:'center'" width="33%">当前状态</th> 
			</tr>
		</thead>
	</table>
  </body>
</html>
