<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <script src="gdcx.js" type="text/javascript"></script>
  </head>
  
  <body style="overflow-x:hidden">
      <div id="query_div" class="easyui-panel"   style="width:100%;">
   		<table class="query_table">
   		<form id="query_form">
   			<tr>
   				<td  class="show_table_label">工单状态：</td>
				<td >
					<select id="cx_state" name="cx_state" style="width:120px;height:20px;"></select>
				</td>
				<td class="show_table_label" >来电类别：</td>
				<td >
					<select id="cx_call_type" name="cx_call_type" style="width:120px;height:20px;"></select>
				</td>
				<td class="query_label">派单点部</td>
				<td>
					<select id="cx_repairer_station" name="cx_repairer_station" style="width:120px;height:20px;" onchange="getNext(this,'cx_repairer_grid')"></select>
				</td>
				<td  class="show_table_label">派单片区：</td>
				<td >
					<select id="cx_repairer_grid" name="cx_repairer_grid" style="width:120px;height:20px;" onchange="getNext(this,'cx_repairer_yhid')"></select>
				</td>
				<td  class="show_table_label">派单人员：</td>
				<td >
					<select id="cx_repairer_yhid" name="cx_repairer_yhid" style="width:120px;height:20px;" ></select>
				</td>
			</tr>
			<tr>
				<td  class="show_table_label">报修类别：</td>
				<td >
					<select id="cx_service_type" name="cx_service_type" style="width:120px;height:20px;"></select>
				</td>
	    		<td class="query_label">来电人名称： </td>
	    		<td ><input name="cx_caller_name" style="width:120px;height:20px"></td>
	    		<td class="query_label">来电号码：</td>
	    		<td ><input name="cx_call_num" id="cx_call_num"  style="width:120px;height:20px"></td>
	    		<td class="show_table_label" >工单编号：</td>
				<td>
					<input type="text"  name="cx_service_number" id="cx_service_number" style="width:120px;height:20px;">
				</td>
	    		<td class="query_label">
					是否督办<input type="checkbox"  value ="1" name="cx_supervison" id="cx_supervison">
				</td>
	    		<td >
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
				<th data-options="field:'service_number',align:'center'" width="8%">工单编号</th>
				<th data-options="field:'caller_name',align:'center'" width="8%">来电姓名</th>
				<th data-options="field:'contact_num',align:'center'" width="8%">联系号码</th>
				<th data-options="field:'service_type_name',align:'center'" width="8%">报修类别</th>
				<th data-options="field:'repairer_station_name',align:'center'" width="8%">派工点部</th>
				<th data-options="field:'repairer_yhid_name',align:'center'" width="8%">派工人员</th>
				<th data-options="field:'supervision_name',align:'center'" width="8%">督办情况</th>
				<th data-options="field:'state_name',align:'center'" width="8%">当前状态</th>
				<th data-options="field:'service_content',align:'center'" width="25%">来电内容</th>
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
						<td  class="show_table_label" style="width:8%">报修大类：</td>
						<td class="show_table_text" style="width:17%">
							<select id="service_type" name="service_type"  onchange="getNext(this,'service_type_2nd')"></select>
						</td>
					</tr>
					<tr>
						<td class="show_table_label" >工单编号：</td>
						<td class="show_table_text" >
							<input type="text"  name="service_number" id="service_number">
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
						<td class="show_table_label">来电类别：</td>
						<td style="height:20px;font-family:Microsoft Yahei;font-size:12px;" colspan=7>
							<input type="radio"  name="call_type" value="1010201" >报修
							<input type="radio"  name="call_type" value="1010202" >督办
							<input type="radio"  name="call_type" value="1010203" >咨询
							<input type="radio"  name="call_type" value="1010204" >其它
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
							<textarea style="height:40px;" id="comment " name="comment"></textarea>
						</td>	
					</tr>
					<tr>
						<td class="show_table_label">督办状态：</td>
						<td class="show_table_text" >
							<input type="text"  name="supervision_name" id="supervision_name" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px '>
						</td>
						<td class="show_table_label"  rowspan=2>督办内容：</td>
						<td class="show_table_text"   rowspan=2 colspan=5>
							<textarea id="supervision_content" name="supervision_content"></textarea>
						</td>
					</tr>
					<tr>
						<td class="show_table_label">督办时间：</td>
						<td class="show_table_text" >
							<input type="text"  name="supervision_time" id="supervision_time" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px '>
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
							 <input type="text" onclick = "openVideos();"  name="video_name" id="video_name" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;'>
						</td>
					</tr>
					
					<tr>
						<td class="show_table_label" >回复时间：</td>
						<td class="show_table_text" >
							<input type="text"  name="repairer_time" id="repairer_time" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px '>
						</td>
						<td  class="show_table_label">回复内容：</td>
						<td class="show_table_text" colspan=5 >
							 <input type="text"  name="repairer_content" id="repairer_content" >
						</td>
					</tr>
					<tr>
						<td  class="show_table_label">回访时间：</td>
						<td class="show_table_text">
							<input type="text"  name="return_time" id="return_time" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px '>
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
					<tr>
						<td class="show_table_label" >督办情况：</td>
						<td class="show_table_text" >
							<input type="text"   style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;' value="点击查看" />
						</td>
						<td class="show_table_label" >预约情况：</td>
						<td class="show_table_text" >
							<input type="text"  onclick = "openReservation();" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;' value="点击查看" />
						</td>
						<td  class="show_table_label">签到情况：</td>
						<td class="show_table_text" >
							 <input type="text"   onclick = "openSign();" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;' value="点击查看" />
						</td>
						<td  class="show_table_label">关联视频：</td>
						<td class="show_table_text" >
							 <input type="text"   onclick = "openVideo();" style='border-left:0px;border-top:0px;border-right:0px;border-bottom:1px;color:#FF0000;' value="点击关联" />
						</td>
					</tr>
				</table>
			</form>
 		 </div>
 	</div>
 	<div id="images" class="easyui-window" title="图片浏览" data-options="closed:true" style="width:1000px;height:450px" >
	
 	</div>
 	<div id="reservation" class="easyui-window" data-options="closed:true" style="width:500px;height:350px" title="预约列表">
	 	<table id="table_listreservation"   border = 0  style="width:99%;" data-options="pagination:true">
			<thead>
				<tr>
					<th data-options="field:'reservation_time',align:'center'" width="31%">预约时间</th>
					<th data-options="field:'reservation_yhid_name',align:'center'" width="31%">预约人员</th>
					<th data-options="field:'create_time',align:'center'" width="31%">创建时间</th>
				</tr>
			</thead>
		</table>
	</div> 	
	
	<div id="sign" class="easyui-window" data-options="closed:true" style="width:500px;height:350px" title="签到列表">
	 	<table id="table_listsign"   border = 0  style="width:99%;" data-options="pagination:true">
			<thead>
				<tr>
					<th data-options="field:'sign_start',align:'center'" width="31%">签到时间</th>
					<th data-options="field:'sign_end',align:'center'" width="31%">签退时间</th>
					<th data-options="field:'yh_name',align:'center'" width="31%">上门人员</th>
				</tr>
			</thead>
		</table>
	</div> 	 
	<div id="videolist" class="easyui-window" data-options="closed:true" style="width:500px;height:350px" title="关联视频列表">
	 	<table id="table_listvideo"   border = 0  style="width:99%;" data-options="pagination:true">
			<thead>
				<tr>
					<th data-options="field:'video_path',align:'center'" width="44%">视频路径</th>
					<th data-options="field:'create_time',align:'center'" width="44%">关联时间</th>
				</tr>
			</thead>
		</table>
	</div> 	 
		
	<div  id="video" class="easyui-window" data-options="closed:true" style="width:500px;height:250px" title="视频关联">
		<form id="form_videos">
		<table id = "table_video">
			<tr name = "table_video_tr" id="table_video_tr">
				<td class="show_table_label" style="width:80px">视频路径:</td>
				<td>
					 <input type="text"  name="video_path" style="width:350px">
				</td>
				<td>
					<img src = "../../resources/images/edit_add.png" onclick="addTr();"></img>
					<img src = "../../resources/images/edit_remove.png" onclick="removeTr(this);"></img>
				</td>
			</tr>
			<tr>
				<td colspan="6" align="center">
					<input type=button value="保存关联" onclick="saveVideo();" style="width:60px;height:25px;line-height:20px;"></input>
				</td>
			</tr>
		</table>
		</form>
	</div> 	
  </body>
</html>
