<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="dcl.js" type="text/javascript"></script>
  </head>
  
  <body style="overflow-x:hidden">
      <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		<form id="query_form">
			<tr>
				<td class="query_label">
					本人<input type = "checkbox" value="1" name = "cx_self" id = "cx_self" checked>
				</td>
	    		<td class="query_label">来电人名称： </td>
	    		<td class="query_text"><input name="cx_caller_name" style="width:120px"></td>
	    		<td class="query_label">来电号码：</td>
	    		<td class="query_text"><input name="cx_call_num" id="cx_call_num"  style="width:120px"></td>
	    		<td class="query_label" >工单编号：</td>
				<td class="query_text">
					<input type="text"  name="cx_service_number" id="cx_service_number" style="width:120px">
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
	    		<td  >
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="query()">搜索</a>
	    			<a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="reset('query_form')">清空</a>
	    		</td>
	    	</tr>
	    </form>	
    	</table>	
	 </div>
	 <table id="table_list"   border = 0 title="工单数据列表" style="width:99%;" data-options="pagination:true">
		<thead>
			<tr>
				<th data-options="field:'service_number',align:'center'" width="10%">工单编号</th>
				<th data-options="field:'call_type_name',align:'center'" width="10%">工单类别</th>
				<th data-options="field:'service_type_name',align:'center'" width="10%">报修类别</th>
				<th data-options="field:'service_type_2nd_name',align:'center'" width="10%">报修小类</th>
				<th data-options="field:'repairer_station_name',align:'center'" width="10%">派工点部</th>
				<th data-options="field:'repairer_yhid_name',align:'center'" width="10%">派工人员</th>
				<th data-options="field:'state_name',align:'center'" width="10%">当前状态</th>
				<th data-options="field:'supervision_name',align:'center'" width="10%">督办情况</th>
				<th data-options="field:'reservation_time',align:'center'" width="10%">预约情况</th>
				<th data-options="field:'create_time',align:'center'" width="10%">创建时间</th>
				
			</tr>
		</thead>
	</table>
	
	<div id="show_div" class="easyui-panel" title="工单数据" data-options="modal:true" style="width:99%;" >
 		 <div>
			 <form id="show_form">
			 	<input type=hidden id="guid" name="guid">
				<table class="show_table">
					<tr>
						<td  class="show_table_label" style="width:10%">联系人姓名：</td>
						<td class="show_table_text" style="width:22%">
							<input type="text"  name="caller_name" id="caller_name">
						</td>
						<td  class="show_table_label" style="width:10%">来电号码：</td>
						<td class="show_table_text" style="width:22%">
							<input type="text"  name="call_num" id="call_num">
						</td>
						<td  class="show_table_label" style="width:10%">联系号码：</td>
						<td class="show_table_text" style="width:22%">
							<input type="text"  name="contact_num" id="contact_num">
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">派单点部：</td>
						<td class="show_table_text">
							<select type="text"  name="repairer_station" id="repairer_station"  onchange="getNext(this,'repairer_grid')"></select>
						</td>
						<td  class="show_table_label">派单片区：</td>
						<td class="show_table_text">
							<select id="repairer_grid" name="repairer_grid" linkage = true onchange="getNext(this,'repairer_yhid')">
								<option value=""></option>
							</select>
						</td>
						<td  class="show_table_label">派单人员：</td>
						<td class="show_table_text">
							<select id="repairer_yhid" name="repairer_yhid" linkage = true >
								<option value=""></option>
							</select>
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">报修类别：</td>
						<td class="show_table_text">
							<select id="service_type" name="service_type"  onchange="getNext(this,'service_type_2nd')"></select>
						</td>
						<td  class="show_table_label">报修小类：</td>
						<td class="show_table_text">
							<select id="service_type_2nd" name="service_type_2nd"  linkage = true ></select>
						</td>
						<td  class="show_table_label">当前状态：</td>
						<td class="show_table_text">
							<input type="text"  name="state_name" id="state_name" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px '>
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">报修地址：</td>
						<td class="show_table_text" colspan=4> 
							<input type="text"  name="repair_address" id="repair_address">
						</td>
						<td>
							<a onclick="getRepairer()" style="color:#0EABF4;font-size:12px">标记位置</a>
						</td>
					</tr>
					<tr>
						<td class="show_table_label">报修内容：</td>
						<td class="show_table_text" colspan=5>
							<textarea style="height:40px;" id="service_content" name="service_content"></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="show_table_label">备注</td>
						<td class="show_table_text" colspan=5>
							<textarea style="height:40px;" id="comment" name="comment"></textarea>
						</td>	
					</tr>
					<tr name="supervision_tr" >
						<td class="show_table_label">督办内容：</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="supervision_content" name="supervision_content"></textarea>
						</td>
					</tr>
				</table>
			</form>
 		 </div>
 	</div> 	
  </body>
</html>
