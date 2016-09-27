<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="txl.js" type="text/javascript"></script>
  </head>
  
  <body>
   	 <div class="easyui-layout" style="width:100%;height:100%;">
		<div  region="west"  split="true" title="" style="width:200px;">
	 		<ul id="txl" ></ul>
			 
		</div>
		<div   region="center"  >
			 <div id="query_div" class="easyui-panel"   style="width:100%;">
		   		<table class="query_table">
		   		   	<form id="query_form">
		   			<tr>
			    		<td class="query_label">名称</td>
			    		<td class="query_text"><input name="cx_name" class="easyui-textbox"></td>
			    		<td>
			    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="query()">搜索</a>
			    		</td>
		   			</tr>
					</form>		
		    		</table>
	 		</div>
			 <table id="table_list"   border = 0 title="数据列表" style="width:100%;" 
					data-options="pagination:true">
				<thead>
					<tr>
						<th data-options="field:'name',align:'center'" width="24%">名称</th>
						<th data-options="field:'phone1',align:'center'" width="11%">电话1</th>
						<th data-options="field:'phone2',align:'center'" width="11%">电话2</th>
						<th data-options="field:'phone3',align:'center'" width="11%">电话3</th>
						<th data-options="field:'phone4',align:'center'" width="11%">电话4</th>
						<th data-options="field:'phone5',align:'center'" width="11%">电话5</th>
						<th data-options="field:'phone6',align:'center'" width="11%">电话6</th>
						<th data-options="field:'orgid',align:'center',formatter:formatOper" width="8%"></th> 
					</tr>
				</thead>
			</table>
			
			<input type=hidden id="sj_id" name="sj_id" value="0">
			<div id="p" class="easyui-panel" title="通讯录信息" style="width:99%;height:160px">   
			      <form id="show_formTxl">
					<input type=hidden id="guid" name="guid">
					<table class="show_table">
						<tr>
							<td  class="show_table_label" width="10%">名称：</td>
							<td class="show_table_text" width="15%">
								<input type="text"  name="name" id="name" required = "true" showName="名称">
							</td>
							<td  class="show_table_label" width="10%">电话1： </td>
							<td class="show_table_text" width="15%">
								<input type="text"  name="phone1" id="phone1">
							</td>
							<td  class="show_table_label" width="10%">电话2： </td>
							<td class="show_table_text" width="15%">
								<input type="text"  name="phone2" id="phone2">
							</td>
							<td  class="show_table_label" width="10%">电话3： </td>
							<td class="show_table_text" width="15%">
								<input type="text"  name="phone3" id="phone3">
							</td>
						</tr>
						<tr>
							<td  class="show_table_label" width="10%">电话4： </td>
							<td class="show_table_text" width="15%">
								<input type="text"  name="phone4" id="phone4">
							</td>
							<td  class="show_table_label" width="10%">电话5： </td>
							<td class="show_table_text" width="15%">
								<input type="text"  name="phone5" id="phone5">
							</td>
							<td  class="show_table_label" width="10%">电话6： </td>
							<td class="show_table_text" width="15%">
								<input type="text"  name="phone6" id="phone6">
							</td> 
							<td  class="show_table_label" width="10%">性质： </td>
							<td class="show_table_text" width="15%">
								<select id="lb_dm" name="lb_dm">
									<option value="1040101">科室</option>
									<option value="1040102">医生</option>
								</select>
							</td>
						</tr>
						<tr>
							<td  class="show_table_label">备注：</td>
							<td class="show_table_text" colspan="7">
								<textarea id="bz" name="bz" style="height:50px;" ></textarea>
							</td>
						</tr>
					</table>
				</form>
			</div> 			 
		</div>
	</div>
	<%-- <div id="show_upload" class="easyui-window" title="上传信息" data-options="closed:true,modal:true" style="width:650px;height:290px;">
		<form action="${webcontext}/txl.do?method=upload" method="post" enctype="multipart/form-data" >  
		    <table align="center" border="1">  
		        <tr>  
		            <td>选择文件</td>  <td><input type="file" name="uploadFileCtrl" /></td>  
		        </tr>  
		        <tr><td colspan="2"><input type="submit" value="确认" /></td></tr>  
		    </table>  
		</form>  
 	</div> --%>
  </body>
</html>
