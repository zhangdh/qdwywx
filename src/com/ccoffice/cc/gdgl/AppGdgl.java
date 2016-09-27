package com.ccoffice.cc.gdgl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ccoffice.base.excel.ExcelTool;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.push.PushSync;
import com.ccoffice.util.tools.Guid;
import com.ccoffice.util.tools.SysPara;

public class AppGdgl {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  AppGdgl(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}
	
	public Map querySelect(){
		try{
			List _list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10106' order by dm ");
			json.addSelect("type", _list," ");
			
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_grid where type= '1010801' ");
			json.addSelect("repairer_station", _list," ");
			json.addSelect("station", _list,"");
			
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_service_type where higher_guid= '0' ");
			json.addSelect("service_type", _list," ");
			
			//_list = Db.getJtN().queryForList("select  dm, mc from t_dm where dm_type= '10103' order by dm ");
			//json.addSelect("state", _list," ");
			
		}catch(Exception e){
			Log.error(yhid,"querySelect","","工单管理-查询select数据时异常"+e.toString());
		}
		return json.getJsonDataSelect();
	}
	
	public Map queryQuerySelect(){
		try{
			List _list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10102' order by dm ");
			json.addSelect("cx_call_type", _list," ");
			json.addSelect("call_type", _list,"");
			
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_grid where type= '1010801' ");
			json.addSelect("cx_repairer_station", _list," ");
			json.addSelect("repairer_station", _list,"");
			
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_service_type where higher_guid= '0' ");
			json.addSelect("cx_service_type", _list," ");
			json.addSelect("service_type", _list,"");
			
			_list = Db.getJtN().queryForList("select  dm, mc from t_dm where dm_type= '10103' order by dm ");
			json.addSelect("cx_state", _list," ");
			json.addSelect("state", _list,"");
			
			_list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10109' order by dm ");
			json.addSelect("return_attitude", _list," ");
			
			_list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10110' order by dm ");
			json.addSelect("return_quality", _list," ");
			
			_list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10111' order by dm ");
			json.addSelect("return_clean", _list," ");
		}catch(Exception e){
			Log.error(yhid,"querySelect","","工单管理-查询select数据时异常"+e.toString());
		}
		return json.getJsonDataSelect();
	}
	//查询待办工单
	public Map queryTodo(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("cx_caller_name")==null?"":String.valueOf(_map.get("cx_caller_name"));
			if(!"".equals(temp)){
				sqlWhere.append(" and caller_name like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_call_num")==null?"":String.valueOf(_map.get("cx_call_num"));
			if(!"".equals(temp)){
				sqlWhere.append(" and call_num like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_service_number")==null?"":String.valueOf(_map.get("cx_service_number"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_number = '").append(temp).append("'");
			}
			temp = _map.get("state")==null?"":String.valueOf(_map.get("state"));
			if(temp.indexOf(",")>-1){
				sqlWhere.append(" and state in (").append(temp).append(")");
			}else if(!"".equals(temp)){
				sqlWhere.append(" and state = '").append(temp).append("'");
			}
			temp = _map.get("cx_create_time_start")==null?"":String.valueOf(_map.get("cx_create_time_start"));
			if(!"".equals(temp)){
				sqlWhere.append(" and create_time > '").append(temp).append("'");
			}
			temp = _map.get("cx_create_time_end")==null?"":String.valueOf(_map.get("cx_create_time_end"));
			if(!"".equals(temp)){
				sqlWhere.append(" and create_time < '").append(temp).append(" 23:59:59'");
			}
			
			StringBuffer strSql = new StringBuffer();
			strSql.append("select guid,service_number,caller_name,contact_num,repair_address,service_content,repairer_content,")
				  .append(" to_char(create_time,'YYYY-MM-DD HH24:MI:SS') create_time ,")
				  .append(" (select name from t_sys_yh where yhid = a.yhid ) slr ,")
				  .append("(select name from t_cc_wywx_service_type where guid = a.service_type) service_type_name,sign_guid,sign_start, ")
				  .append("supervision,(case when supervision ='1' then '正在督办'  else '未督办' end ) supervision_name,to_char(reservation_time,'YYYY-MM-DD HH24:MI:SS') reservation_time, ")
				  .append("(case when sign_start is not null and sign_end is null then '1' else '0' end) sign_status ")
				  .append("from  t_cc_wywx_service a where repairer_yhid = '").append(yhid).append("'  ").append(sqlWhere).append(" order by reservation_time asc,create_time desc ");
			Page page = new Page();
			page.setSql(strSql.toString());
			page.setSqlCount("select count(*) from t_cc_wywx_service where repairer_yhid = '"+yhid+"'  "+sqlWhere.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list,page);   
		}catch(Exception e){
			Log.error(yhid,"queryTodo","","查询待办工单数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	//查询正在督办
	public Map queryDb(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("state")==null?"":String.valueOf(_map.get("state"));
			if(temp.indexOf(",")>-1){
				sqlWhere.append(" and state in (").append(temp).append(")");
			}else if(!"".equals(temp)){
				sqlWhere.append(" and state = '").append(temp).append("'");
			}
			
			sqlWhere.append(" and supervision = '1' ");
			StringBuffer strSql = new StringBuffer();
			strSql.append("select guid,service_number,caller_name,contact_num,repair_address,service_content,repairer_content,")
				  .append(" to_char(create_time,'YYYY-MM-DD HH24:MI:SS') create_time,to_char(supervision_time ,'YYYY-MM-DD HH24:MI:SS') supervision_time,supervision_content,")
				  .append("(select name from t_cc_wywx_service_type where guid = a.service_type) service_type_name,'正在督办' supervision_name, ")
				  .append("(select mc from t_dm where dm = a.state )state_name ,to_char(reservation_time,'YYYY-MM-DD HH24:MI:SS') reservation_time, ")
				  .append("(case when sign_start is not null and sign_end is null then '1' else '0' end) sign_status,sign_guid ")
				  .append("from  t_cc_wywx_service a where repairer_yhid = '").append(yhid).append("'  ").append(sqlWhere).append(" order by create_time desc ");
			Page page = new Page();
			page.setSql(strSql.toString());
			page.setSqlCount("select count(*) from t_cc_wywx_service where repairer_yhid = '"+yhid+"'  "+sqlWhere.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list,page);   
		}catch(Exception e){
			Log.error(yhid,"queryTodo","","查询待办工单数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	//处理工单
	public Map dealTodo(){
		try{
			StringBuffer strSql = new StringBuffer();
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			String state = _map.get("state")==null?"":String.valueOf(_map.get("state"));
			String do_type = _map.get("do_type")==null?"":String.valueOf(_map.get("do_type"));
			String back_content = _map.get("back_content")==null?"":String.valueOf(_map.get("back_content"));
			
			if("1010302".equals(state)){
				//维修人员回退工单
				Db.getJtN().update("update t_cc_wywx_service set state = '1010302',back_content='"+back_content+"' where guid ='"+service_id+"'");
				
				Db.getJtN().update("update t_cc_wywx_service_repair set state = '1010702',state_time="+Db.getDateStr()+" where service_id='"+service_id+"' and repairer_id='"+yhid+"'");
			}else if("1010304".equals(state)){
				//维修工预约
				strSql.append("update t_cc_wywx_service set state = '1010304',reservation_time=to_timestamp('").append(_map.get("reservation"))
					  .append("','YYYY-MM-DD HH24:MI'),remind=0 where guid ='").append(service_id).append("'");
				Db.getJtN().update(strSql.toString());
				strSql.delete(0,strSql.length());
				strSql.append("insert into t_cc_wywx_service_reservation(guid,service_id,yhid,reservation_time,create_time)values('")
					  .append(Db.getGUID()).append("','").append(service_id).append("','").append(yhid).append("',to_timestamp('").append(_map.get("reservation"))
					  .append("','YYYY-MM-DD HH24:MI'),").append(Db.getDateStr()).append(")");
				Db.getJtN().update(strSql.toString());
				
				Db.getJtN().update("update t_cc_wywx_service_repair set state = '1010704',state_time="+Db.getDateStr()+" where service_id='"+service_id+"' and repairer_id='"+yhid+"'");
				
				
			}else if("1010305".equals(state)){
				strSql.append("update t_cc_wywx_service set state = '1010305',repairer_content='").append(_map.get("repairer_content")).append("',repairer_time=")
				      .append(Db.getDateStr()).append(",offer_value=").append(_map.get("offer_value")).append(" where guid ='").append(service_id).append("'");
				Db.getJtN().update(strSql.toString());
				strSql.delete(0,strSql.length());
				
				Db.getJtN().update("update t_cc_wywx_service_repair set state = '1010703',state_time="+Db.getDateStr()+" where service_id='"+service_id+"' and repairer_id='"+yhid+"'");
			}
			
			json.addResult(true,"操作成功");
			
		}catch(Exception e){
			Log.error(yhid,"dealTodo","","处理待处理工单数据时异常"+e.toString());
			json.addResult(false,"操作失败:"+e.toString());
		}
		return json.getJsonDataResult();
	}
	
	//展现待办
	public Map showTodo(){
		try{
			StringBuffer strSql = new StringBuffer();
			String service_id = _map.get("guid")==null?"":String.valueOf(_map.get("guid"));
			strSql.append("select guid,service_number,caller_name,contact_num,repair_address,service_content,")
			  .append(" to_char(create_time,'YYYY-MM-DD HH24:MI:SS') create_time ,")
			  .append(" (select name from t_sys_yh where yhid = a.yhid ) slr ,")
			  .append("(select name from t_cc_wywx_service_type where guid = a.service_type) service_type_name,sign_guid,sign_start, ")
			  .append("(case when supervision ='1' then '正在督办'  else '正常' end ) supervision_name,to_char(reservation_time,'YYYY-MM-DD HH24:MI:SS') reservation_time, ")
			  .append("supervision_content,(case when sign_start is not null and sign_end is null then '1' else '0' end) sign_status,")
			  .append("(select mc from t_dm where dm = a.state )state_name,to_char(repairer_time,'YYYY-MM-DD HH24:MI:SS') repairer_time,repairer_content,offer_value  ")
			  .append("repairer_content,offer_value,(case when offer_images is not null  and offer_images <> '' then '点击查看' else '无' end) offer_images_mc,")
			  .append("(case when images is not null and images <>'' then '点击查看' else '无' end)images_mc,return_time,(select mc from t_dm where dm = return_attitude ) return_attitude_mc,")
			  .append("(select mc from t_dm where dm = return_quality ) return_quality_mc,(select mc from t_dm where dm = return_clean ) return_clean_mc ")
			  .append("from  t_cc_wywx_service a where guid='").append(service_id).append("'");
			
			Map _map = Db.getJtN().queryForMap(strSql.toString());
			json.addForm(_map);
		}catch(Exception e){
			Log.error(yhid,"showTodo","","显示待处理工单数据时异常"+e.toString());
			json.addResult(false,"操作失败:"+e.toString());
		}
		return json.getJsonDataMap();
	}
	public Map saveReservation(){
		try{
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			String reservation_time = _map.get("reservation_time")==null?"":String.valueOf(_map.get("reservation_time"));
			String reservation_content = _map.get("reservation_content")==null?"":String.valueOf(_map.get("reservation_content"));
			if(!"".equals(service_id)){
				StringBuffer sqlStr = new StringBuffer();
				sqlStr.append("insert into t_cc_wywx_service_reservation(guid,service_id,yhid,reservation_time,reservation_content,create_time)values('")
					  .append(Db.getGUID()).append("','").append(service_id).append("','").append(yhid).append("',cast('").append(reservation_time).append("' as timestamp),'")
					  .append(reservation_content).append("',").append(Db.getDateStr()).append(")");
				Db.getJtN().update(sqlStr.toString());
			}
			json.addResult(true,"处理成功");
		}catch(Exception e){
			Log.error(yhid,"saveReservation","","app工单管理-saveReservation时异常"+e.toString());
			json.addResult(false,"操作失败:"+e.toString());
		}
		return json.getJsonDataResult();
	}
	public Map saveSign(){
		try{
			
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			String sign_guid = _map.get("sign_guid")==null?"":String.valueOf(_map.get("sign_guid"));
			String sign_type = _map.get("sign_type")==null?"":String.valueOf(_map.get("sign_type"));
			StringBuffer sqlStr = new StringBuffer();
			if("in".equals(sign_type)){
				//签到
				String guid = Db.getGUID();
				sqlStr.append("insert into t_cc_wywx_sign (guid,service_id,yhid,sign_start,sign_start_points,sign_start_address,create_time)values('").append(guid)
					  .append("','").append(service_id).append("','").append(yhid).append("',").append(Db.getDateStr()).append(",'")
					  .append(_map.get("points")).append("','").append(_map.get("address")).append("',").append(Db.getDateStr()).append(")");
				Db.getJtN().update(sqlStr.toString());
				sqlStr.delete(0,sqlStr.length());
				sqlStr.append(" update t_cc_wywx_service set sign_guid='").append(guid).append("' ,sign_end=null ,sign_end_points = null,sign_end_address=null,sign_start=")
				.append(Db.getDateStr()).append(",sign_start_points='").append(_map.get("points")).append("',sign_start_address='").append(_map.get("address")).append("'")
				.append(" where guid = '").append(service_id).append("'");
				Db.getJtN().update(sqlStr.toString());
				json.addData("sign_guid",guid);
				json.addResult(true,"签到成功");
			}else if("out".equals(sign_type)){
				//签退
				sqlStr.append("update t_cc_wywx_sign set sign_end = ").append(Db.getDateStr()).append(",sign_end_points='")
				.append(_map.get("points")).append("',sign_end_address='").append(_map.get("address")).append("' where guid = '").append(sign_guid).append("'");
				Db.getJtN().update(sqlStr.toString());
				sqlStr.delete(0,sqlStr.length());
				sqlStr.append(" update t_cc_wywx_service set sign_end=").append(Db.getDateStr()).append(",sign_end_points='")
				.append(_map.get("points")).append("',sign_end_address='").append(_map.get("address")).append("' where guid = '").append(service_id).append("'");
				Db.getJtN().update(sqlStr.toString());
				json.addResult(true,"签退成功");
			}
		}catch(Exception e){
			Log.error(yhid,"saveSign","","app工单管理-saveSign时异常"+e.toString());
			json.addResult(false,"操作失败:"+e.toString());
		}
		return json.getJsonDataResult();
	}
	
	public Map uploadImages(HttpServletRequest request,HttpServletResponse response){
		try{
			System.out.println("-------------上传图片start----------------------");
			Map reMap = new HashMap();
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	        List<MultipartFile> files = multipartRequest.getFiles("file");
	        //处理一次处理多个文件的情况
	        String images_path = SysPara.getValue("images_path");
	        String upload_type = _map.get("upload_type")==null?"":String.valueOf(_map.get("upload_type"));
	        String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
	        StringBuffer sqlStr = new StringBuffer();
	        System.out.println("文件个数:"+files.size());
	        System.out.println("upload_type:"+upload_type);
	        System.out.println("service_id:"+service_id);
	        for(int key =0;key<files.size();key++){
	        	MultipartFile file = files.get(key);
	        	//判断文件内容不为空
	        	if(!file.isEmpty()){
	        		 //将文件保存在本地
	        		 String originalName = file.getOriginalFilename();
	        		 String  suffix = originalName.substring(originalName.lastIndexOf("."), originalName.length());
	        		 String outFilePath = images_path+"/"+Db.getGUID()+suffix;
	        		 if("offer".equals(upload_type)){
	        			 //报价图片
	        			 outFilePath = images_path+"/offer/"+Db.getGUID()+suffix;
	        			 Db.getJtN().update("update t_cc_wywx_service set offer_images=guid where guid = '"+service_id+"'");
	        			 sqlStr.append("insert into t_cc_wywx_offer_images(guid,service_id,offer_yhid,images_name,images_path,create_time)values('")
	        			 		.append(Db.getGUID()).append("','").append(service_id).append("','").append(yhid).append("','").append(originalName)
	        			 		.append("','").append(outFilePath).append("',").append(Db.getDateStr()).append(")");
	        			 
	        		 }else{
	        			 //场景图片
	        			 outFilePath = images_path+"/scene/"+Db.getGUID()+suffix;
	        			 Db.getJtN().update("update t_cc_wywx_service set images=guid where guid = '"+service_id+"'");
	        			 
	        			 sqlStr.append("insert into t_cc_wywx_service_images(guid,service_id,yhid,images_name,images_path,create_time)values('")
	        			 		.append(Db.getGUID()).append("','").append(service_id).append("','").append(yhid).append("','").append(originalName)
	        			 		.append("','").append(outFilePath).append("',").append(Db.getDateStr()).append(")");
	        		 }
	        		 Db.getJtN().update(sqlStr.toString());
	        		 File outFile = new File(outFilePath); 
	        		 file.transferTo(outFile); 
	        	}
	        }
	        System.out.println("-------------上传图片end----------------------");
		}catch(Exception e){
			 e.printStackTrace();
			 Log.error(yhid,"uploadImages","","上传图片异常:"+e.toString());
			 json.addResult(false,"上传图片异常:"+e.toString());
		}
		return json.getJsonDataResult();
	}
	
	public Map getImages(){
		try{
			
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
		    String upload_type = _map.get("upload_type")==null?"":String.valueOf(_map.get("upload_type"));
		    List _list = null;
		    if("offer".equals(upload_type)){
	 			 //报价图片
		    	 _list = Db.getJtN().queryForList("select guid,images_name,images_path from t_cc_wywx_offer_images where service_id='"+service_id+"'");
		    }else if("scene".equals(upload_type)){
		    	//场景图片
		    	_list = Db.getJtN().queryForList("select guid,images_name,images_path from t_cc_wywx_service_images where service_id='"+service_id+"'"); 
		    }
		    json.addListEasyUI("images", _list,null);
		}catch(Exception e){
			e.printStackTrace();
			Log.error(yhid,"getImages","","查询上传图片记录时异常"+e.toString());
			json.addResult(false,"操作失败:"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map queryReservation(){
		try{
			List _list  = null;
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			if(!"".equals(service_id)){
		    	//场景图片
		    	_list = Db.getJtN().queryForList("select to_char(reservation_time,'YYYY-MM-DD HH24:MI') reservation_time  from t_cc_wywx_service_reservation where service_id='"+service_id+"' and yhid = '"+yhid+"' order by create_time desc "); 
		    }
		    json.addListEasyUI("reservation", _list,null);
		}catch(Exception e){
			e.printStackTrace();
			Log.error(yhid,"queryReservation","","查询预约数据时异常"+e.toString());
			json.addResult(false,"操作失败:"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map querySign(){
		try{
			List _list  = null;
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			if(!"".equals(service_id)){
		    	//场景图片
		    	_list = Db.getJtN().queryForList("select to_char(sign_start,'MM-DD HH24:MI') sign_start ,to_char(sign_end,'MM-DD HH24:MI') sign_end from t_cc_wywx_sign where service_id='"+service_id+"' and yhid = '"+yhid+"' order by create_time desc "); 
		    }
		    json.addListEasyUI("sign", _list,null);
		}catch(Exception e){
			e.printStackTrace();
			Log.error(yhid,"querySign","","查询签到数据时异常"+e.toString());
			json.addResult(false,"操作失败:"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
}
