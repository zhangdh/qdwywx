<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="openTxl.js" type="text/javascript"></script>
     <title>请选择或输入号码</title>
  </head>
  
  <body>
   	 <div class="easyui-layout" style="width:100%;height:100%;">
		<div  region="west"  split="true" title="" style="width:150px;">
	 		<ul id="txl" ></ul>
		</div>
		<div   region="center"  >
			 <div id="query_div" class="easyui-panel"   style="width:100%;">
		   		<table class="query_table">
		   		   	<form id="query_form">
		   			<tr>
			    		<td  style="width:180px;height:20px;text-align:center;font-family:Microsoft Yahei;font-size:12px;">请输入号码或搜索条件</td>
			    		<td  style="width:200px;height:20px;line-height:20px;font-size:12px;">
			    			<input name="phone" id="phone" class="easyui-textbox">
			    		</td>
			    		<td>
			    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-ok'" onclick="call()">外拨</a>
			    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="query()">搜索</a>
			    		</td>
		   			</tr>
					</form>		
		    		</table>
	 		</div>
			 <table id="table_list"   border = 0 title="数据列表" style="width:100%;" 
					data-options="pagination:true">
				<thead>
					<tr>
						<th data-options="field:'name',align:'center'" width="26%">名称</th>
						<th data-options="field:'phone1',align:'center'" width="12%">电话1</th>
						<th data-options="field:'phone2',align:'center'" width="12%">电话2</th>
						<th data-options="field:'phone3',align:'center'" width="12%">电话3</th>
						<th data-options="field:'phone4',align:'center'" width="12%">电话4</th>
						<th data-options="field:'phone5',align:'center'" width="12%">电话5</th>
						<th data-options="field:'phone6',align:'center'" width="12%">电话6</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
  </body>
</html>
