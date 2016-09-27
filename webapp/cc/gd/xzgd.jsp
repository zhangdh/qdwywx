<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%
	String caller = request.getParameter("caller")==null?"":request.getParameter("caller");
	String record = request.getParameter("record")==null?"":request.getParameter("record");	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="xzgd.js" type="text/javascript"></script>
     <script type="text/javascript">
     	var caller = '<%=caller%>';
     	var record = '<%=record%>';
     </script>
  </head>
  <body style="overflow-x:hidden">
      <div id="query_div" class="easyui-panel"   style="width:99%;">
   		<table class="query_table">
   		   	<form id="query_form">
   			<tr>
   				<td class="query_label">姓名 </td>
	    		<td class="query_text"><input name="cx_name" id="cx_name"  ></td>
	    		<td class="query_label">来电号码</td>
	    		<td class="query_text"><input name="cx_call_num" id="cx_call_num"></td>
	    		<td>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" onclick="queryLxr(1)">搜索</a>
	    		</td>
   			</tr>
			</form>		
    		</table>
	 </div>
	 <table id="table_listlxr"   border = 0 title="联系人列表" style="width:99%;" data-options="singleSelect:true,collapsible:true">
		<thead>
			<tr>
				<th data-options="field:'name',align:'center'" width="15%">姓名</th>
				<th data-options="field:'contact_num',align:'center'" width="15%">联系方式</th>
				<th data-options="field:'type_name',align:'center'" width="15%">类别</th>
				<th data-options="field:'grid_name',align:'center'" width="15%">所属片区</th>
				<th data-options="field:'address',align:'center'" width="38%">来电人地址</th>
			</tr>
		</thead>
	 </table>
	 <table id="table_listfwqq"   border = 0 title="工单数据列表" style="width:99%;" data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'service_number',align:'center'" width="10%">工单编号</th>
				<th data-options="field:'call_type_name',align:'center'" width="10%">工单类别</th>
				<th data-options="field:'service_type_name',align:'center'" width="10%">报修类别</th>
				<th data-options="field:'service_type_2nd_name',align:'center'" width="10%">报修小类</th>
				<th data-options="field:'repairer_station_name',align:'center'" width="10%">派工点部</th>
				<th data-options="field:'repairer_yhid_name',align:'center'" width="10%">派工人员</th>
				<th data-options="field:'state_name',align:'center'" width="9%">当前状态</th>
				<th data-options="field:'supervision_name',align:'center'" width="9%">督办情况</th>
				<th data-options="field:'reservation_time',align:'center'" width="9%">预约情况</th>
				<th data-options="field:'create_time',align:'center'" width="10%">创建时间</th>
				
			</tr>
		</thead>
	</table>

	<div id="show_divfwqq" class="easyui-panel" title="工单数据" data-options="modal:true" style="width:99%;" >
 		 <div>
			 <form id="show_formfwqq">
				<input type=hidden id="service_guid" name="service_guid">
				<input type=hidden id="caller_id" name="caller_id">
				<input type=hidden id="record" name="record">
				<table class="show_table">
					<tr>
						<td  class="show_table_label" style="width:8%">联系人姓名：</td>
						<td class="show_table_text" style="width:17%">
							<input type="text"  name="name" id="name" required = true>
						</td>
						<td  class="show_table_label" style="width:8%">来电号码：</td>
						<td class="show_table_text" style="width:17%">
							<input type="text"  name="call_num" id="call_num">
						</td>
						<td  class="show_table_label" style="width:8%">联系号码：</td>
						<td class="show_table_text" style="width:16%">
							<input type="text"  name="contact_num" id="contact_num" required = true>
							
						</td>
						<td  class="show_table_label" style="width:8%">类型：</td>
						<td class="show_table_text" style="width:16%">
							<select id="type" name="type"></select>
						</td>
					</tr>
					<tr>
						 
						<td  class="show_table_label">所属点部：</td>
						<td class="show_table_text">
							<select type="text"  name="station" id="station"  onchange="getNext(this,'grid')"></select>
						</td>
							<td  class="show_table_label">所属片区：</td>
						<td class="show_table_text">
							<select id="grid" name="grid" linkage = true ></select>
						</td>
						<td  class="show_table_label">联系地址：</td>
						<td class="show_table_text" colspan=3> 
							<input type="text"  name="address" id="address">
						</td>
					

					</tr>
				
					<tr>
						<td class="show_table_label" style="width:10%">来电类别：</td>
						<td style="height:20px;font-family:Microsoft Yahei;font-size:12px;" colspan=7>
							<input type="radio"  name="call_type" value="1010201" checked onClick="radioChange(this)">报修
							<input type="radio"  name="call_type" value="1010202" onClick="radioChange(this)">督办
							<input type="radio"  name="call_type" value="1010203" onClick="radioChange(this)">咨询
							<input type="radio"  name="call_type" value="1010204" onClick="radioChange(this)">其它
						</td>
					</tr>
						
					<tr name="supervision_tr" style="display:none">
						<td class="show_table_label">督办内容：</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="supervision_content" name="supervision_content"></textarea>
						</td>
					</tr>
					
					<tr name="repair_tr">
						<!-- <td class="show_table_label" >工单编号：</td>
						<td class="show_table_text" >
							<input type="text"  name="service_number" id="service_number" readOnly  placeholder="系统自动生成"> 
						</td> -->
						<td  class="show_table_label">报修大类：</td>
						<td class="show_table_text">
							<select id="service_type" name="service_type"  onchange="getNext(this,'service_type_2nd')"></select>
						</td>
						<td  class="show_table_label">报修地址：</td>
						<td class="show_table_text" colspan=4> 
							<input type="text"  name="repair_address" id="repair_address" required = true>
						</td>
						<td>
							<a onclick="getRepairer()" style="color:#0EABF4;font-size:12px">标记位置</a>
						</td>
					</tr>
					<tr name="repair_tr">
						<td  class="show_table_label">报修小类：</td>
						<td class="show_table_text">
							<select id="service_type_2nd" name="service_type_2nd"  linkage = true></select>
						</td>
						<td  class="show_table_label">派单点部：</td>
						<td class="show_table_text">
							<select type="text"  name="repairer_station" id="repairer_station"  onchange="getNext(this,'repairer_grid')" required = true></select>
						</td>
						<td  class="show_table_label">派单片区：</td>
						<td class="show_table_text">
							<select id="repairer_grid" name="repairer_grid" linkage = true onchange="getNext(this,'repairer_yhid')" required = true></select>
						</td>
						<td  class="show_table_label">派单人员：</td>
						<td class="show_table_text">
							<select id="repairer_yhid" name="repairer_yhid" linkage = true required = true></select>
						</td>
					</tr>
					<tr>
						<td class="show_table_label">报修内容：</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="service_content" name="service_content" required = true></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="show_table_label">备注</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="comment" name="comment"></textarea>
						</td>	
					</tr> 
				</table>
			</form>
 		 </div>
 	</div>
  </body>
</html>
