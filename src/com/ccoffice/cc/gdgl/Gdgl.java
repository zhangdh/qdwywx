package com.ccoffice.cc.gdgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ccoffice.base.excel.ExcelTool;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.push.PushSync;
import com.ccoffice.util.tools.Guid;

public class Gdgl {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Gdgl(Map map){
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
			
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_service_type where higher_guid= '0' ");
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
			
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_service_type where higher_guid= '0' ");
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
	public Map queryReturnSelect(){
		try{
			List _list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10109' order by dm ");
			json.addSelect("return_attitude", _list," ");
			
			_list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10110' order by dm ");
			json.addSelect("return_quality", _list," ");
			
			_list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10111' order by dm ");
			json.addSelect("return_clean", _list," ");
			
			 
			
		}catch(Exception e){
			Log.error(yhid,"queryReturnSelect","","工单管理-待回访-查询select数据时异常"+e.toString());
		}
		return json.getJsonDataSelect();
	}
	public Map queryNextSelect(){
		try{
			String thisValue = _map.get("value")==null?"":String.valueOf(_map.get("value"));
			String nextId = _map.get("nextId")==null?"":String.valueOf(_map.get("nextId"));
			List _list = null;
			if(!"".equals(thisValue) && !"".equals(nextId)){
				if("cx_grid".equals(nextId) || "grid".equals(nextId) || "repairer_grid".equals(nextId) || "cx_repairer_grid".equals(nextId)){
					_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_grid where higher_id='"+thisValue+"'");
				}else if("repairer_yhid".equals(nextId) || "cx_repairer_yhid".equals(nextId)){
					_list = Db.getJtN().queryForList("select yhid dm,name mc from t_sys_yh where grid='"+thisValue+"'");
				}else if("service_type_2nd".equals(nextId)){
					_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_service_type where higher_guid='"+thisValue+"'");
				}
			}
			
			json.addSelect(nextId, _list," ");
			
		}catch(Exception e){
			Log.error(yhid,"querySelect","","工单管理-查询select数据时异常"+e.toString());
		}
		return json.getJsonDataSelect();
	}
	
	public Map queryImages(){
		try{
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			String image_type = _map.get("image_type")==null?"":String.valueOf(_map.get("image_type"));
			List _list = null;
			if("offer".equals(image_type) && !"".equals(service_id)){
				_list = Db.getJtN().queryForList(" select images_path path from t_cc_wywx_offer_images where service_id='"+service_id+"'");
			}else if("repair".equals(image_type) && !"".equals(service_id)){
				_list = Db.getJtN().queryForList(" select images_path path from t_cc_wywx_service_images where service_id='"+service_id+"'");
			}
			json.addListEasyUI("", _list,null);
		}catch(Exception e){
			Log.error(yhid,"querySelect","","工单管理-queryImages时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
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
			
			temp = _map.get("cx_service_type")==null?"":String.valueOf(_map.get("cx_service_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_type = '").append(temp).append("'");
			}
			temp = _map.get("cx_service_type_2nd")==null?"":String.valueOf(_map.get("cx_service_type_2nd"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_type_2nd = '").append(temp).append("'");
			}
			temp = _map.get("state")==null?"":String.valueOf(_map.get("state"));
			if(!"".equals(temp)){
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
			temp = _map.get("cx_self")==null?"":String.valueOf(_map.get("cx_self"));
			if("1".equals(temp)){
				sqlWhere.append(" and yhid = '").append(yhid).append("'");
			}
			
			StringBuffer strSql = new StringBuffer();
			strSql.append("select guid service_guid,a.*,to_char(create_time,'YYYY-MM-DD HH24:MI:SS')create_time ,")
				  .append("(select name from t_cc_service_type where guid = a.service_type) service_type_name, ")
				  .append("(select name from t_cc_service_type where guid = a.service_type_2nd) service_type_2nd_name, ")
				  .append("(select name from t_cc_wywx_grid where guid = a.repairer_station) repairer_station_name ,")
				  .append("(select name from t_cc_wywx_grid where guid = a.repairer_grid) repairer_grid_name ,")
				  .append("(select name from t_sys_yh where yhid = a.repairer_yhid) repairer_yhid_name ,")
				  .append("(select name from t_sys_yh where yhid = a.repairer_yhid) repairer_yhid_name ,")
				  .append("to_char(return_time,'YYYY-MM-DD HH24:MI:SS')return_time ,(select mc from t_dm where dm = a.call_type) call_type_name,")
				  .append("to_char(repairer_time,'YYYY-MM-DD HH24:MI:SS') repairer_time ,")
				  .append("(case when supervision ='1' then '正在督办'  else '未督办' end ) supervision_name ,'待处理' state_name, ")
				  .append("(case when offer_images !='' then '点击查看'  else '无' end ) offer_images_name , ")
				  .append("(case when images !='' then '点击查看'  else '无' end ) images_name , ")
				  .append("(case when video !='' then '点击查看'  else '无' end ) video_name , ")
				  .append("(select mc from t_dm where dm = a.state)state_name ").append("from  t_cc_wywx_service a where 1=1 ").append(sqlWhere);
			Page page = new Page();
			page.setSql(strSql.toString());
			page.setSqlCount("select count(*) from t_cc_wywx_service where 1=1  "+sqlWhere.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list,page);   
		}catch(Exception e){
			Log.error(yhid,"query","","查询工单数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	//处理工单
	public Map dealTodo(){
		try{
			StringBuffer strSql = new StringBuffer();
			String state = _map.get("state")==null?"":String.valueOf(_map.get("state"));
			if("1010303".equals(state)){
				//处理待办工单，1010303代表派单操作
				strSql.append("update t_cc_wywx_service set repairer_station='").append(_map.get("repairer_station")).append("',repairer_grid='")
				  .append(_map.get("repairer_grid")).append("',repairer_yhid='").append(_map.get("repairer_yhid")).append("',repair_address='")
				  .append(_map.get("repair_address")).append("',service_type='")
				  .append(_map.get("service_type")).append("',service_type_2nd='").append(_map.get("service_type_2nd")).append("',state='")
				  .append(_map.get("state")).append("',service_content='").append(_map.get("service_content")).append("',comment='")
				  .append(_map.get("comment")).append("' where guid = '").append(_map.get("guid")).append("'");
				Db.getJtN().update(strSql.toString());
				
				//派单时保存维修信息
				strSql.delete(0,strSql.length());
				strSql.append("insert into t_cc_wywx_service_repair(guid,service_id,repairer_station,repairer_grid,repairer_id,state,state_time,create_time) values('")
					  .append(Db.getGUID()).append("','").append(_map.get("guid")).append("','").append(_map.get("repairer_station")).append("','")
					  .append(_map.get("repairer_grid")).append("','").append(_map.get("repairer_yhid")).append("','1010701',").append(Db.getDateStr())
					  .append(",").append(Db.getDateStr()).append(")");
				Db.getJtN().update(strSql.toString());
				
				//多线程 推送消息代码
				Map hashMap = new HashMap();
				hashMap.put("push_title","您有一条新的派单信息，请注意查收");
				hashMap.put("push_content",_map.get("service_content"));
				hashMap.put("yhid",_map.get("repairer_yhid"));
				hashMap.put("guid",_map.get("guid"));
				hashMap.put("type","2080001"); //派单提醒类型
				new PushSync(hashMap).run();
				
			}else if("1010306".equals(state)){
				//处理待办工单，1010304 待办回访后办结。保存回访信息
				strSql.append("update t_cc_wywx_service set return_time=").append(Db.getDateStr()).append(",return_content='").append(_map.get("return_content"))
					  .append("',return_attitude='").append(_map.get("return_attitude")).append("',return_quality='").append(_map.get("return_quality"))
					  .append("',return_clean='").append(_map.get("return_clean")).append("',state='").append(state).append("' where guid = '").append(_map.get("guid")).append("'");
				Db.getJtN().update(strSql.toString());
				
				//派单时保存维修信息
				strSql.delete(0,strSql.length());
				strSql.append("update t_cc_wywx_service_repair set return_time=").append(Db.getDateStr()).append(",return_attitude='")
				      .append(_map.get("return_attitude")).append("',return_quality='").append(_map.get("return_quality"))
				      .append("',return_clean='").append(_map.get("return_clean")).append("' where service_id = '").append(_map.get("guid")).append("'");
				Db.getJtN().update(strSql.toString());
			}
			
			
		}catch(Exception e){
			Log.error(yhid,"dealTodo","","处理待处理工单数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
		
	public Map query(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("cx_caller_name")==null?"":String.valueOf(_map.get("cx_caller_name"));
			if(!"".equals(temp)){
				sqlWhere.append(" and caller_name like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_contact_num")==null?"":String.valueOf(_map.get("cx_contact_num"));
			if(!"".equals(temp)){
				sqlWhere.append(" and contact_num like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_call_type")==null?"":String.valueOf(_map.get("cx_call_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and call_type = '").append(temp).append("'");
			}
			temp = _map.get("cx_repairer_station")==null?"":String.valueOf(_map.get("cx_repairer_station"));
			if(!"".equals(temp)){
				sqlWhere.append(" and repairer_station = '").append(temp).append("'");
			}
			temp = _map.get("cx_repairer_grid")==null?"":String.valueOf(_map.get("cx_repairer_grid"));
			if(!"".equals(temp)){
				sqlWhere.append(" and repairer_grid = '").append(temp).append("'");
			}
			temp = _map.get("cx_repairer_yhid")==null?"":String.valueOf(_map.get("cx_repairer_yhid"));
			if(!"".equals(temp)){
				sqlWhere.append(" and repairer_yhid = '").append(temp).append("'");
			}
			
			temp = _map.get("cx_state")==null?"":String.valueOf(_map.get("cx_state"));
			if(!"".equals(temp)){
				sqlWhere.append(" and state = '").append(temp).append("'");
			}
			temp = _map.get("cx_call_type")==null?"":String.valueOf(_map.get("cx_call_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and call_type = '").append(temp).append("'");
			}
			temp = _map.get("cx_service_number")==null?"":String.valueOf(_map.get("cx_service_number"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_number = '").append(temp).append("'");
			}
			
			temp = _map.get("cx_service_type")==null?"":String.valueOf(_map.get("cx_service_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_type = '").append(temp).append("'");
			}
			temp = _map.get("cx_service_type_2nd")==null?"":String.valueOf(_map.get("cx_service_type_2nd"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_type_2nd = '").append(temp).append("'");
			}
			temp = _map.get("cx_caller_id")==null?"":String.valueOf(_map.get("cx_caller_id"));
			if(!"".equals(temp)){
				sqlWhere.append(" and caller_guid = '").append(temp).append("'");
			}
			temp = _map.get("cx_create_time_start")==null?"":String.valueOf(_map.get("cx_create_time_start"));
			if(!"".equals(temp)){
				sqlWhere.append(" and create_time > '").append(temp).append("'");
			}
			temp = _map.get("cx_create_time_end")==null?"":String.valueOf(_map.get("cx_create_time_end"));
			if(!"".equals(temp)){
				sqlWhere.append(" and create_time < '").append(temp).append(" 23:59:59'");
			}
			temp = _map.get("cx_supervison")==null?"":String.valueOf(_map.get("cx_supervison"));
			if("1".equals(temp)){
				sqlWhere.append(" and supervision = '1' ");
			}
			StringBuffer strSql = new StringBuffer();
			strSql.append("select guid service_guid,a.*,to_char(create_time,'YYYY-MM-DD HH24:MI:SS')create_time ,")
				  .append(" to_char(reservation_time,'YYYY-MM-DD HH24:MI:SS')reservation_time ,")
				  .append("(select mc from t_dm where dm = a.call_type) call_type_name,")
				  .append("(select name from t_cc_service_type where guid = a.service_type) service_type_name, ")
				  .append("(select name from t_cc_service_type where guid = a.service_type_2nd) service_type_2nd_name, ")
				  .append("(select name from t_cc_wywx_grid where guid = a.repairer_station) repairer_station_name ,")
				  .append("(select name from t_cc_wywx_grid where guid = a.repairer_grid) repairer_grid_name ,")
				  .append("(select name from t_sys_yh where yhid = a.repairer_yhid) repairer_yhid_name ,")
				  .append("(case when supervision ='1' then '正在督办'  else '未督办' end ) supervision_name ,")
				  .append("(select mc from t_dm where dm = a.state)state_name ").append("from  t_cc_wywx_service a where 1 = 1").append(sqlWhere);
			Page page = new Page();
			page.setSql(strSql.toString());
			page.setSqlCount("select count(*) from t_cc_wywx_service where 1 = 1  "+sqlWhere.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list,page);   
		}catch(Exception e){
			Log.error(yhid,"query","","查询工单数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map queryGdcx(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("cx_caller_name")==null?"":String.valueOf(_map.get("cx_caller_name"));
			if(!"".equals(temp)){
				sqlWhere.append(" and caller_name like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_contact_num")==null?"":String.valueOf(_map.get("cx_contact_num"));
			if(!"".equals(temp)){
				sqlWhere.append(" and contact_num like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_call_type")==null?"":String.valueOf(_map.get("cx_call_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and call_type = '").append(temp).append("'");
			}
			temp = _map.get("cx_repairer_station")==null?"":String.valueOf(_map.get("cx_repairer_station"));
			if(!"".equals(temp)){
				sqlWhere.append(" and repairer_station = '").append(temp).append("'");
			}
			temp = _map.get("cx_repairer_grid")==null?"":String.valueOf(_map.get("cx_repairer_grid"));
			if(!"".equals(temp)){
				sqlWhere.append(" and repairer_grid = '").append(temp).append("'");
			}
			temp = _map.get("cx_repairer_yhid")==null?"":String.valueOf(_map.get("cx_repairer_yhid"));
			if(!"".equals(temp)){
				sqlWhere.append(" and repairer_yhid = '").append(temp).append("'");
			}
			
			temp = _map.get("cx_state")==null?"":String.valueOf(_map.get("cx_state"));
			if(!"".equals(temp)){
				sqlWhere.append(" and state = '").append(temp).append("'");
			}
			temp = _map.get("cx_call_type")==null?"":String.valueOf(_map.get("cx_call_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and call_type = '").append(temp).append("'");
			}
			temp = _map.get("cx_service_number")==null?"":String.valueOf(_map.get("cx_service_number"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_number = '").append(temp).append("'");
			}
			
			temp = _map.get("cx_service_type")==null?"":String.valueOf(_map.get("cx_service_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_type = '").append(temp).append("'");
			}
			temp = _map.get("cx_service_type_2nd")==null?"":String.valueOf(_map.get("cx_service_type_2nd"));
			if(!"".equals(temp)){
				sqlWhere.append(" and service_type_2nd = '").append(temp).append("'");
			}
			temp = _map.get("cx_caller_id")==null?"":String.valueOf(_map.get("cx_caller_id"));
			if(!"".equals(temp)){
				sqlWhere.append(" and caller_guid = '").append(temp).append("'");
			}
			temp = _map.get("cx_create_time_start")==null?"":String.valueOf(_map.get("cx_create_time_start"));
			if(!"".equals(temp)){
				sqlWhere.append(" and create_time > '").append(temp).append("'");
			}
			temp = _map.get("cx_create_time_end")==null?"":String.valueOf(_map.get("cx_create_time_end"));
			if(!"".equals(temp)){
				sqlWhere.append(" and create_time < '").append(temp).append(" 23:59:59'");
			}
			temp = _map.get("cx_supervison")==null?"":String.valueOf(_map.get("cx_supervison"));
			if("1".equals(temp)){
				sqlWhere.append(" and supervision = '1' ");
			}
			StringBuffer strSql = new StringBuffer();
			strSql.append("select guid service_guid,a.*,to_char(create_time,'YYYY-MM-DD HH24:MI:SS') create_time ,(select mc from t_dm where dm = a.call_type) call_type_name,")
				   .append("to_char(supervision_time,'YYYY-MM-DD HH24:MI:SS')supervision_time ,")
				   .append("to_char(return_time,'YYYY-MM-DD HH24:MI:SS')return_time ,")
				   .append("to_char(repairer_time,'YYYY-MM-DD HH24:MI:SS')repairer_time ,")
				  .append("(select name from t_cc_service_type where guid = a.service_type) service_type_name, ")
				  .append("(select name from t_cc_service_type where guid = a.service_type_2nd) service_type_2nd_name, ")
				  .append("(select name from t_cc_wywx_grid where guid = a.repairer_station) repairer_station_name ,")
				  .append("(select name from t_cc_wywx_grid where guid = a.repairer_grid) repairer_grid_name ,")
				  .append("(select name from t_sys_yh where yhid = a.repairer_yhid) repairer_yhid_name ,")
				  .append("(case when supervision ='1' then '正在督办'  else '未督办' end )supervision_name ,")
				  .append("(case when offer_images !='' then '点击查看'  else '无' end ) offer_images_name , ")
				  .append("(case when images !='' then '点击查看'  else '无' end ) images_name , ")
				  .append("(case when video !='' then '点击查看'  else '无' end ) video_name , ")
				  .append("(select mc from t_dm where dm = a.state)state_name ").append("from  t_cc_wywx_service a where 1 = 1").append(sqlWhere);
			Page page = new Page();
			page.setSql(strSql.toString());
			page.setSqlCount("select count(*) from t_cc_wywx_service where 1 = 1  "+sqlWhere.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list,page);   
		}catch(Exception e){
			Log.error(yhid,"queryGdcx","","查询工单数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map saveGd(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlStrLxr = new StringBuffer();
			String service_id = _map.get("service_guid")==null?"":String.valueOf(_map.get("service_guid"));
			String caller_id = _map.get("caller_id")==null?"":String.valueOf(_map.get("caller_id"));
			String call_type = _map.get("call_type")==null?"":String.valueOf(_map.get("call_type"));
			
			if("".equals(caller_id)){
				caller_id = Guid.get();
				sqlStrLxr.append("insert into t_cc_wywx_caller (guid,yhid,name,type,call_num,contact_num,address,station,grid,longitude,latitude,comment,create_time,flag)values('")
						.append(caller_id).append("','").append(yhid).append("','").append(_map.get("name")).append("','").append((_map.get("type"))).append("','")
						.append(_map.get("call_num")).append("','").append(_map.get("contact_num")).append("','").append(_map.get("address")).append("','")
						.append(_map.get("station")).append("','").append(_map.get("grid")).append("','").append(_map.get("longitude")).append("','")
						.append(_map.get("latitude")).append("','").append(_map.get("comment")).append("',").append(Db.getDateStr()).append(",1)");
				Db.getJtN().update(sqlStrLxr.toString());
			}
			if("1010201".equals(call_type)){
				//报修工单
				String guid = Db.getGUID();
				sqlStr.append("insert into t_cc_wywx_service(guid,yhid,caller_guid,service_number,caller_name,call_num,contact_num,call_type,repair_address,sources,service_type,service_type_2nd,")
				  .append("service_content,comment,repairer_station,repairer_grid,repairer_yhid,repairer_content,state,create_time)values('")
				  .append(guid).append("','").append(yhid).append("','").append(caller_id).append("','")
				  .append(guid.substring(0,15)).append("','").append(_map.get("name")).append("','")
				  .append(_map.get("call_num")).append("','").append(_map.get("contact_num")).append("','").append(_map.get("call_type")).append("','")
				  .append(_map.get("repair_address")).append("','1010101','").append(_map.get("service_type")).append("','").append(_map.get("service_type_2nd"))
				  .append("','").append(_map.get("service_content")).append("','").append(_map.get("comment")).append("','")
				  .append(_map.get("repairer_station")).append("','").append(_map.get("repairer_grid")).append("','").append(_map.get("repairer_yhid"))
				  .append("','").append(_map.get("repairer_content")).append("','").append(_map.get("state")).append("',").append(Db.getDateStr()).append(")");
				Db.getJtN().update(sqlStr.toString());
				if("1010303".equals(_map.get("state"))){
					//派单时保存维修信息
					sqlStr.delete(0,sqlStr.length());
					sqlStr.append("insert into t_cc_wywx_service_repair(guid,service_id,repairer_station,repairer_grid,repairer_id,state,state_time,create_time) values('")
					     .append(Db.getGUID()).append("','").append(guid).append("','").append(_map.get("repairer_station")).append("','")
					     .append(_map.get("repairer_grid")).append("','").append(_map.get("repairer_yhid")).append("','1010701',").append(Db.getDateStr())
					     .append(",").append(Db.getDateStr()).append(")");
					Db.getJtN().update(sqlStr.toString());
					
					//多线程 推送消息代码
					Map hashMap = new HashMap();
					hashMap.put("push_title","您有一条新的派单信息，请注意查收");
					hashMap.put("push_content",_map.get("service_content"));
					hashMap.put("yhid",_map.get("repairer_yhid"));
					hashMap.put("guid",guid);
					hashMap.put("type","2080001"); //派单提醒类型
					new PushSync(hashMap).run();
				}
			}else if("1010202".equals(call_type)){
				//假如是督办工单，就进行特殊处理，更新service表中的督办字段
				if(!"".equals(service_id)){
					StringBuffer updateSql = new StringBuffer();
					updateSql.append("update t_cc_wywx_service set supervision = 1,supervision_time = ").append(Db.getDateStr())
							.append(",supervision_content='").append(_map.get("supervision_content")).append("' where guid = '").append(service_id).append("'");
					Db.getJtN().update(updateSql.toString());
					updateSql.delete(0, updateSql.length());
					updateSql.append("insert into t_cc_wywx_service_supervision(guid,service_id,caller_id,supervision_time,supervision_yhid,supervision_content) values('")
							 .append(Db.getGUID()).append("','").append(service_id).append("','").append(caller_id).append("',").append(Db.getDateStr())
							 .append(",'").append(yhid).append("','").append(_map.get("supervision_content")).append("')");
					Db.getJtN().update(updateSql.toString());
				}
			}else{
				sqlStr.append("insert into t_cc_wywx_service(guid,yhid,caller_guid,service_number,caller_name,call_num,contact_num,call_type,")
				  .append("service_content,comment,state,create_time)values('")
				  .append(Db.getGUID()).append("','").append(yhid).append("','").append(caller_id).append("','")
				  .append(String.valueOf(_map.get("service_number"))).append("','").append(_map.get("caller_name")).append("','")
				  .append(_map.get("call_num")).append("','").append(_map.get("contact_num")).append("','").append(_map.get("call_type")).append("','")
				  .append(_map.get("service_content")).append("','").append(_map.get("comment")).append("','")
				  .append(_map.get("state")).append("',").append(Db.getDateStr()).append(")");
	 
				Db.getJtN().update(sqlStr.toString());
			}
			
			json.addResult(true, "处理成功");
		}catch(Exception e){
			e.printStackTrace();
			json.addResult(false, e.toString());
			Log.error(yhid,"saveGd","","保存工单数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
	
	public Map queryReservation(){
		try{
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			List _list = null;
			if(!"".equals(service_id)){
				_list = Db.getJtN().queryForList(" select to_char(reservation_time,'YYYY-MM-DD HH24:MI:SS')reservation_time, (select name from t_sys_yh where yhid = a.yhid) reservation_yhid_name,to_char(create_time,'YYYY-MM-DD HH24:MI:SS')create_time  from t_cc_wywx_service_reservation a where service_id='"+service_id+"'");
			}
			json.addListEasyUI("table_listreservation", _list,null);
		}catch(Exception e){
			Log.error(yhid,"queryReservation","","工单管理-queryReservation时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	public Map querySign(){
		try{
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			List _list = null;
			if(!"".equals(service_id)){
				_list = Db.getJtN().queryForList(" select to_char(sign_start,'YYYY-MM-DD HH24:MI:SS')sign_start, to_char(sign_end,'YYYY-MM-DD HH24:MI:SS')sign_end,(select name from t_sys_yh where yhid = a.yhid) yh_name  from t_cc_wywx_sign a where service_id='"+service_id+"'");
			}
			json.addListEasyUI("table_listsign", _list,null);
		}catch(Exception e){
			Log.error(yhid,"querySign","","工单管理-querySign时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map exportTj(HttpServletRequest request,HttpServletResponse response){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String tjlx = _map.get("tjlx")==null?"":String.valueOf(_map.get("tjlx"));
			String type = _map.get("type")==null?"":String.valueOf(_map.get("type"));
			String cx_sjq = _map.get("cx_sjq")==null?"":String.valueOf(_map.get("cx_sjq"));
			String cx_sjz = _map.get("cx_sjz")==null?"":String.valueOf(_map.get("cx_sjz"));
			sqlWhere.append(" where yxbz = 1 ");
			if("Day".equals(type)){
				sqlWhere.append(" and cjsj > curdate() ");
			}else if("Week".equals(type)){
				sqlWhere.append(" and cjsj > date_add(curdate(), interval - ( 5 + dayofweek(curdate())) day) ");
			}else if("Month".equals(type)){
				sqlWhere.append(" and cjsj > DATE_ADD(curdate(),interval -day(curdate())+1 day) ");
			}else if("Year".equals(type)){
				sqlWhere.append(" and YEAR(cjsj) = YEAR( NOW( ) )  ");
			}else{
				if(!"".equals(cx_sjq)){
					sqlWhere.append(" and cjsj >'").append(cx_sjq).append("' ");
				}
				
				if(!"".equals(cx_sjz)){
					sqlWhere.append(" and cjsj <'").append(cx_sjz).append(" 23:59:59' ");
				}
			}
			
			if("zt_dm".equals(tjlx)){
				sqlStr.append(" select (select mc from t_dm where dm = zt_dm) tjlx,count(*) count from t_cc_fwqq ").append(sqlWhere).append(" group by zt_dm order by zt_dm ");
			}else if("nrlb".equals(tjlx)){
				sqlStr.append(" select (select mc from t_dm where dm = nrlb) tjlx,count(*) count from t_cc_fwqq ").append(sqlWhere).append(" group by nrlb order by nrlb ");
			}else if("xzfl".equals(tjlx)){
				sqlStr.append(" select (select mc from t_dm where dm = xzfl) tjlx,count(*) count from t_cc_fwqq ").append(sqlWhere).append(" group by xzfl order by xzfl ");
			}
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			
			int sumCount = 0;
			for(int i = 0;i<_list.size();i++){
				Map _map = (Map)_list.get(i);
				sumCount = sumCount +Integer.parseInt(_map.get("count").toString()); 
			}
			
			Map sumMap  = new HashMap();
			sumMap.put("tjlx","合计");
			sumMap.put("count",sumCount);
			sumMap.put("percentage","100%");
			_list.add(sumMap);
			for(int i=0;i<_list.size();i++){
				Map _map = (Map)_list.get(i);
				int count = Integer.parseInt(_map.get("count").toString());
				if(sumCount>0){
					String percentage = String.valueOf(Math.round((count/(double)sumCount)*10000)/100.0)+"%"; 
					_map.put("percentage", percentage);
				}else{
					_map.put("percentage", "100%");
				}
			}
			
			String templateFileName = "exportTemplate/fwqqTjExport.xls";
			ExcelTool tool = new ExcelTool(templateFileName,"服务请求统计数据导出");
			tool.addLists("fwqq", _list);
			tool.exportExcel(response, request);
			
		}catch(Exception e){
			Log.error(yhid,"exportTj","","导出统计数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
	
	public Map queryTj(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String tjlx = _map.get("tjlx")==null?"":String.valueOf(_map.get("tjlx"));
			String type = _map.get("type")==null?"":String.valueOf(_map.get("type"));
			String cx_sjq = _map.get("cx_sjq")==null?"":String.valueOf(_map.get("cx_sjq"));
			String cx_sjz = _map.get("cx_sjz")==null?"":String.valueOf(_map.get("cx_sjz"));
			sqlWhere.append(" where yxbz = 1 ");
			if("Day".equals(type)){
				sqlWhere.append(" and cjsj > curdate() ");
			}else if("Week".equals(type)){
				sqlWhere.append(" and cjsj > date_add(curdate(), interval - ( 5 + dayofweek(curdate())) day) ");
			}else if("Month".equals(type)){
				sqlWhere.append(" and cjsj > DATE_ADD(curdate(),interval -day(curdate())+1 day) ");
			}else if("Year".equals(type)){
				sqlWhere.append(" and YEAR(cjsj) = YEAR( NOW( ) )  ");
			}else{
				if(!"".equals(cx_sjq)){
					sqlWhere.append(" and cjsj >'").append(cx_sjq).append("' ");
				}
				
				if(!"".equals(cx_sjz)){
					sqlWhere.append(" and cjsj <'").append(cx_sjz).append(" 23:59:59' ");
				}
			}
			
			if("zt_dm".equals(tjlx)){
				sqlStr.append(" select (select mc from t_dm where dm = zt_dm) tjlx,count(*) count from t_cc_fwqq ").append(sqlWhere).append(" group by zt_dm order by zt_dm ");
			}else if("nrlb".equals(tjlx)){
				sqlStr.append(" select (select mc from t_dm where dm = nrlb) tjlx,count(*) count from t_cc_fwqq ").append(sqlWhere).append(" group by nrlb order by nrlb ");
			}else if("xzfl".equals(tjlx)){
				sqlStr.append(" select (select mc from t_dm where dm = xzfl) tjlx,count(*) count from t_cc_fwqq ").append(sqlWhere).append(" group by xzfl order by xzfl ");
			}
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			
			int sumCount = 0;
			for(int i = 0;i<_list.size();i++){
				Map _map = (Map)_list.get(i);
				sumCount = sumCount +Integer.parseInt(_map.get("count").toString()); 
			}
			
			Map sumMap  = new HashMap();
			sumMap.put("tjlx","合计");
			sumMap.put("count",sumCount);
			sumMap.put("percentage","100%");
			_list.add(sumMap);
			for(int i=0;i<_list.size();i++){
				Map _map = (Map)_list.get(i);
				int count = Integer.parseInt(_map.get("count").toString());
				if(sumCount>0){
					String percentage = String.valueOf(Math.round((count/(double)sumCount)*10000)/100.0)+"%"; 
					_map.put("percentage", percentage);
				}else{
					_map.put("percentage", "100%");
				}
			}
			
			json.addListEasyUI("table_list", _list,null);
		}catch(Exception e){
			Log.error(yhid,"queryTj","","查询统计数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map exportCx(HttpServletRequest request,HttpServletResponse response){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("cx_xm")==null?"":String.valueOf(_map.get("cx_xm"));
			if(!"".equals(temp)){
				sqlWhere.append(" and xm like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_ldhm")==null?"":String.valueOf(_map.get("cx_ldhm"));
			if(!"".equals(temp)){
				sqlWhere.append(" and ldhm like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_lxhm")==null?"":String.valueOf(_map.get("cx_lxhm"));
			if(!"".equals(temp)){
				sqlWhere.append(" and lxhm like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_nrlb")==null?"":String.valueOf(_map.get("cx_nrlb"));
			if(!"".equals(temp)){
				sqlWhere.append(" and nrlb = '").append(temp).append("'");
			}
			
			temp = _map.get("cx_zt_dm")==null?"":String.valueOf(_map.get("cx_zt_dm"));
			if(!"".equals(temp)){
				sqlWhere.append(" and zt_dm = '").append(temp).append("'");
			}
			sqlStr.append("select a.*,(select mc from t_dm where dm = a.nrlb) nrlb_mc,(select mc from t_dm where dm = a.zt_dm) zt_dm_mc from  t_cc_fwqq a where yxbz = 1 "+sqlWhere.toString()+" order by cjsj ");
			 
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			
			String templateFileName = "exportTemplate/fwqqCxExport.xls";
			ExcelTool tool = new ExcelTool(templateFileName,"服务请求查询数据导出");
			tool.addLists("fwqq", _list);
			tool.exportExcel(response, request);
			
		}catch(Exception e){
			Log.error(yhid,"exportCx","","导出查询数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
	
	public Map saveVideos(){
		try{
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			String video_path = _map.get("video_path")==null?"":String.valueOf(_map.get("video_path"));
			String[] videos = video_path.split("~");
			if(videos.length>0 && !"".equals(service_id)){
				Db.getJtN().update("update t_cc_wywx_service set video = '"+service_id+"' where guid = '"+service_id+"'");
			}
			for(int i=0;i<videos.length;i++){
				Db.getJtN().update("insert into t_cc_wywx_service_video(guid,service_id,yhid,video_path,create_time)values('"+Db.getGUID()+"','"+service_id+"','"+yhid+"','"+videos[i]+"',"+Db.getDateStr()+")");
			}
			json.addResult(true, "关联成功");
		}catch(Exception e){
			Log.error(yhid,"saveVideos","","工单管理-saveVideos时异常"+e.toString());
			json.addResult(false, "关联关系保存失败:"+e.toString());
		}
		return json.getJsonDataResult();
	}
	public Map queryVideos(){
		try{
			String service_id = _map.get("service_id")==null?"":String.valueOf(_map.get("service_id"));
			List _list = null;
			if(!"".equals(service_id)){
				_list = Db.getJtN().queryForList(" select to_char(create_time,'YYYY-MM-DD HH24:MI:SS')create_time, video_path  from t_cc_wywx_service_video a where service_id='"+service_id+"'");
			}
			json.addListEasyUI("table_listvideo", _list,null);
		}catch(Exception e){
			Log.error(yhid,"queryVideos","","工单管理-queryVideos时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	public Map queryLxr(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlCount = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("cx_name")==null?"":String.valueOf(_map.get("cx_name"));
			if(!"".equals(temp)){
				sqlWhere.append(" and name = '").append(temp).append("'");
			}
			temp = _map.get("cx_call_num")==null?"":String.valueOf(_map.get("cx_call_num"));
			if(!"".equals(temp)){
				sqlWhere.append(" and call_num  = '").append(temp).append("' ");
			}
			sqlStr.append("select guid caller_id,a.*,(select mc from t_dm where dm = type) type_name, (select name from t_cc_wywx_grid where guid = grid) grid_name from t_cc_wywx_caller a where flag = 1 ").append(sqlWhere);
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			json.addListEasyUI("table_listlxr",_list,null);    
		}catch(Exception e){
			Log.error(yhid,"queryLxr","","查询联系人数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}

	public Map queryDbInfo() {
		try {
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("select guid,name,points point,address from t_cc_wywx_grid a where a.higher_id = '0'");
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			json.addSelect("dbinfo", _list, "");
		} catch (Exception e) {
			Log.error(yhid, "queryDbInfo", "", "查询点部信息异常" + e.toString());
		}
		return json.getSelectData();
	}

	public Map queryWgInfo() {
		try {
			String dbid = "";
			dbid = _map.get("dbid") == null ? "" : String.valueOf(_map.get("dbid"));
			if ("".equals(dbid)) {
				json.addResult(false, "根据点部取网格数据失败");
			} else {
				StringBuffer sqlStr = new StringBuffer();
				sqlStr.append("select guid,name,points point,address from t_cc_wywx_grid ");
				sqlStr.append(" where higher_id = '").append(dbid).append("' ");

			   List _list = Db.getJtN().queryForList(sqlStr.toString());
				json.addSelect("wginfo", _list, "");
				// 查询人
				sqlStr.setLength(0);
				/*sqlStr.append("select a.yhid,a.name,a.points point,a.telephone,b.guid ");
				sqlStr.append(" from t_sys_yh a ,t_cc_wywx_grid b where a.grid = b.guid ");
				sqlStr.append(" and b.higher_id= '").append(dbid).append("' ");*/
				sqlStr.append("select a.yhid,a.name,a.points,a.telephone,grid, ")
					.append("(select name from t_cc_wywx_grid where guid = a.grid ) grid_name ")
					.append("from  t_sys_yh a where grid_higher='").append(dbid).append("'");

				List _list_user = Db.getJtN().queryForList(sqlStr.toString());
				json.addSelect("userinfo", _list_user, "");
			}
		} catch (Exception e) {
			Log.error(yhid, "queryWgInfo", "", "查询网格数据时异常" + e.toString());
		}
		return json.getSelectData();
	}
}
