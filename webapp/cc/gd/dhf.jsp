<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="dhf.js" type="text/javascript"></script>
     <title>已回复待回访</title>
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
				<th data-options="field:'repairer_time',align:'center'" width="10%">回复时间</th>
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
						<td  class="show_table_label" style="width:8%">联系人姓名：</td>
						<td class="show_table_text" style="width:17%">
							<input type="text"  name="caller_name" id="caller_name">
						</td>
						<td  class="show_table_label" style="width:8%">来电号码：</td>
						<td class="show_table_text" style="width:17%">
							<input type="text"  name="call_num" id="call_num">
						</td>
						<td  class="show_table_label" style="width:8%">联系号码：</td>
						<td class="show_table_text" style="width:17%">
							<input type="text"  name="contact_num" id="contact_num">
						</td>
						<td  class="show_table_label" style="width:8%">报修类别：</td>
						<td class="show_table_text" style="width:17%">
							<select id="service_type" name="service_type"  onchange="getNext(this,'service_type_2nd')"></select>
						</td>
					</tr>
					<tr>
						<td  class="show_table_label" style="width:8%">报修小类：</td>
						<td class="show_table_text" style="width:17%">
							<select id="service_type_2nd" name="service_type_2nd"  linkage = true></select>
						</td>
						<td  class="show_table_label">派单点部：</td>
						<td class="show_table_text">
							<select type="text"  name="repairer_station" id="repairer_station"  onchange="getNext(this,'repairer_grid')"></select>
						</td>
						<td  class="show_table_label">派单片区：</td>
						<td class="show_table_text">
							<select id="repairer_grid" name="repairer_grid" linkage = true onchange="getNext(this,'repairer_yhid')"></select>
						</td>
						<td  class="show_table_label">派单人员：</td>
						<td class="show_table_text">
							<select id="repairer_yhid" name="repairer_yhid" linkage = true ></select>
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">报修地址：</td>
						<td class="show_table_text" colspan=7> 
							<input type="text"  name="repair_address" id="repair_address">
						</td>
					</tr>
					<tr>
						<td class="show_table_label">来电内容：</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="service_content" name="service_content"></textarea>
						</td>
					</tr>
					
					<tr>
						<td class="show_table_label">备注</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="comment" name="comment"></textarea>
						</td>	
					</tr>
					<tr>
						<td class="show_table_label" >报价：</td>
						<td class="show_table_text" >
							<input type="text"  name="offer_value" id="offer_value" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px '>
						</td>
						<td  class="show_table_label">报价图片：</td>
						<td class="show_table_text">
							 <input type="text"  onclick = "openImages(this.value,'offer');" name="offer_images_name" id="offer_images_name" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;'>
						</td>
						<td  class="show_table_label">现场图片：</td>
						<td class="show_table_text">
							 <input type="text"  onclick = "openImages(this.value,'repair');" name="images_name" id="images_name" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;'>
						</td>
						<td  class="show_table_label">现场视频：</td>
						<td class="show_table_text">
							 <input type="text"  name="video_name" id="video_name" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;'>
						</td>
					</tr>
					<tr>
						<td class="show_table_label">回复内容</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="repairer_content" name="repairer_content"></textarea>
						</td>
					</tr>
					<tr>
						<td class="show_table_label" >回复时间：</td>
						<td class="show_table_text" >
							<input type="text"  name="repairer_time" id="repairer_time" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px '>
						</td>
						<td  class="show_table_label">服务态度：</td>
						<td class="show_table_text">
							 <select id="return_attitude" name="return_attitude">
							 </select>
						</td>
						<td  class="show_table_label">服务质量：</td>
						<td class="show_table_text">
							 <select id="return_quality" name="return_quality">
							 </select>
						</td>
						<td  class="show_table_label">事后清理：</td>
						<td class="show_table_text">
							 <select id="return_clean" name="return_clean"></select>
						</td>
					</tr>
					<tr>
						<td class="show_table_label">回访内容</td>
						<td class="show_table_text" colspan=7>
							<textarea style="height:40px;" id="return_content" name="return_content"></textarea>
						</td>	
					</tr>
				</table>
			</form>
 		 </div>
 	</div>
 	<div id="images" class="easyui-window" title="图片浏览" data-options="closed:true" style="width:1000px;height:450px" >
	
 	</div> 	
  </body>
</html>
