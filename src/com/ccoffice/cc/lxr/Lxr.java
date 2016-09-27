package com.ccoffice.cc.lxr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ccoffice.base.excel.ExcelTool;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.tools.Guid;

public class Lxr {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Lxr(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}
	public Map init(){
		try{
			List _list = Db.getJtN().queryForList("select dm,mc from t_dm where dm_type= '10106' order by dm ");
			json.addSelect("cx_type", _list," ");
			json.addSelect("type", _list,"");
			
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_grid where type= '1010801' ");
			json.addSelect("cx_station", _list," ");
			json.addSelect("station", _list,"");
			
		}catch(Exception e){
			Log.error(yhid,"init","","市民信箱管理-查询select数据时异常"+e.toString());
		}
		return json.getJsonDataSelect();
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
			temp = _map.get("cx_contact_num")==null?"":String.valueOf(_map.get("cx_contact_num"));
			if(!"".equals(temp)){
				sqlWhere.append(" and contact_num  = '").append(temp).append("' ");
			}
			temp = _map.get("cx_type")==null?"":String.valueOf(_map.get("cx_type"));
			if(!"".equals(temp)){
				sqlWhere.append(" and type  = '").append(temp).append("' ");
			}
			temp = _map.get("cx_station")==null?"":String.valueOf(_map.get("cx_station"));
			if(!"".equals(temp)){
				sqlWhere.append(" and station  = '").append(temp).append("' ");
			}
			
			temp = _map.get("cx_grid")==null?"":String.valueOf(_map.get("cx_grid"));
			if(!"".equals(temp)){
				sqlWhere.append(" and grid  = '").append(temp).append("' ");
			}
			sqlStr.append("select guid caller_id,a.*,(select mc from t_dm where dm = type) type_name, (select name from t_cc_wywx_grid where guid = grid) grid_name from t_cc_wywx_caller a where flag = 1 ").append(sqlWhere);
			List _list = Db.getJtN().queryForList(sqlStr.toString());
			json.addListEasyUI("table_listlxr",_list,null);    
		}catch(Exception e){
			Log.error(yhid,"queryLxr","","查询联系人数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map saveLxr(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("insert into t_cc_wywx_caller(guid,yhid,name,type,call_num,contact_num,address,station,grid,longitude,latitude,comment,create_time,flag)")
				  .append("values('").append(Guid.get()).append("','").append(yhid).append("','").append(_map.get("name")).append("','").append(_map.get("type"))
				  .append("','").append(_map.get("call_num")).append("','").append(_map.get("contact_num")).append("','").append(_map.get("address")).append("','")
				  .append(_map.get("station")).append("','").append(_map.get("grid")).append("','").append(_map.get("longitude")).append("','")
				  .append(_map.get("latitude")).append("','").append(_map.get("comment")).append("',").append(Db.getDateStr()).append(",1)");
			Db.getJtN().update(sqlStr.toString());
		}catch(Exception e){
			Log.error(yhid,"saveLxr","","保存数据时异常"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map modiLxr(){
		try{
			 StringBuffer sqlStr = new StringBuffer();
			 String guid = _map.get("guid")==null?"":String.valueOf(_map.get("guid"));
			 if(!"".equals(guid)){
				 sqlStr.append("update t_cc_wywx_caller set name='").append(_map.get("name")).append("',type='").append(_map.get("type")).append("',call_num='").append(_map.get("call_num"))
				 		.append("',contact_num='").append(_map.get("contact_num")).append("',address='").append(_map.get("address")).append("',station='").append(_map.get("station"))
				 		.append("',grid='").append(_map.get("grid")).append("',longitude='").append(_map.get("longitude")).append("',latitude='")
				 		.append(_map.get("latitude")).append("',comment='").append(_map.get("comment")).append("' where guid = '").append(guid).append("'");
				 Db.getJtN().update(sqlStr.toString());
			 }
		}catch(Exception e){
			Log.error(yhid,"saveGd","","修改联系人数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
	
	public Map delLxr(){
		try{
			String guid = _map.get("guid")==null?"":String.valueOf(_map.get("guid"));
			if(!"".equals(guid)){
				Db.getJtN().update("update t_cc_wywx_caller set flag = 0 where guid ='"+guid+"'");
			}
		}catch(Exception e){
			Log.error(yhid,"delLxr","","删除数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
}
