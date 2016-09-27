<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script src="help.js" type="text/javascript"></script>
  </head>
  <body>
	<table id="table_list"   border = 0 title="数据列表" style="width:99%;" >
		<thead>
			<tr>
				<th data-options="field:'question',align:'center'" width="20%">主题</th>
				<th data-options="field:'answer',align:'center'" width="74%">内容</th>
				<th data-options="field:'dm_type',align:'center',formatter:formatOper" width="5%"></th> 
			</tr>
		</thead>
	</table>
	
 	<div id="show_div" class="easyui-panel" title="数据" data-options="modal:true" style="width:99%;">
 		 <div>
			 <form id="show_form">
				<input type=hidden id="guid" name="guid">
				<table class="show_table">
					<tr>
						<td  class="show_table_label" style="width:8%">主题：</td>
						<td class="show_table_text" style="width:91%">
							<input  type="text"  name="question" id="question" required = "true" showName="主题" >
						</td>
					</tr>
					<tr>
						<td class="show_table_label">内容：</td>
						<td class="show_table_text" >
							<textarea style="height:80px;" id="answer" name="answer" required = "true" showName="内容"></textarea>
						</td>
					</tr>
				</table>
			</form>
 		 </div>
 	</div>
  </body>
</html>
