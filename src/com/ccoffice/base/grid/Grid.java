package com.ccoffice.base.grid;
import java.util.List;
import java.util.Map;

import com.ccoffice.bean.LoginUser;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;
import com.ccoffice.util.tools.Guid;

public class Grid{
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Grid(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}	
	
	public Map init(){
		try{
			List _list = null;
			_list = Db.getJtN().queryForList("select guid dm,name mc from t_cc_wywx_grid where type = '1010801' ");
			json.addSelect("higher_id", _list," ");
			
			_list = Db.getJtN().queryForList("select bmid dm,bmmc mc from t_sys_bm order by xh ");
			json.addSelect("org_id", _list," ");
			
		}catch(Exception e){
			Log.error(yhid,"init","com.ccoffice.base.grid.init","查询菜单数据时异常"+e.toString());
		}
		return json.getJsonDataSelect();
	}
	
	public Map saveGrid(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			String guid = _map.get("guid")==null?"":String.valueOf(_map.get("guid"));
			if("".equals(guid)){
				sqlStr.append("insert into t_cc_wywx_grid(guid,yhid,name,address,points,higher_id,org_id,type,comment,create_time)values('")
				  .append(Db.getGUID()).append("','").append(yhid).append("','").append(_map.get("name")).append("','").append(_map.get("address")).append("','")
				  .append(_map.get("points")).append("','").append(("").equals(_map.get("higher_id"))?"0":_map.get("higher_id")).append("','").append(_map.get("org_id"))
				  .append("','").append(_map.get("type")).append("','").append(_map.get("comment")).append("',").append(Db.getDateStr()).append(")");
			}else{
				sqlStr.append("update t_cc_wywx_grid set name='").append(_map.get("name")).append("',address='").append(_map.get("address")).append("',points='").append(_map.get("points")).append("',")
					  .append(" org_id='").append(_map.get("org_id")).append("',")
					  .append("comment='").append(_map.get("comment")).append("' where guid = '").append(guid).append("'");
			}
			Db.getJtN().update(sqlStr.toString());
		}catch(Exception e){
			Log.error(yhid,"GetMenu","com.ccoffice.base.grid.saveGrid","查询菜单数据时异常"+e.toString());
			json.addResult(false,"保存失败:"+e.toString());
		}
		return json.getJsonDataMap();
	}
	
	public Map getGridTree(){
		try{
			String sql = "select guid,name,higher_id,address,points,type,comment,org_id from t_cc_wywx_grid  order by create_time";
			List _list = Db.getJtN().queryForList(sql);
			json.addData("gridData", _list);
		}catch(Exception e){
			Log.error(yhid,"getGridTree","com.ccoffice.base.grid.getGridTree","查询菜单数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
	public Map delGrid(){
		try{
			String guid = _map.get("guid")==null?"":String.valueOf(_map.get("guid"));
			if(!"".equals(guid)){
				Db.getJtN().update("delete from t_cc_wywx_grid where guid = '"+guid+"'");
			}
		}catch(Exception e){
			Log.error(yhid,"delGrid","com.ccoffice.base.grid.delGrid","删除网格数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
	public Map getGridList(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlCount = new StringBuffer();
			String higher_id = _map.get("higher_id")==null?"":String.valueOf(_map.get("higher_id"));
			sqlStr.append("select guid,name,type,address,comment,points,higher_id,(select mc from t_dm where dm = a.type) type_name,to_char(create_time, 'YYYY-MM-DD HH24:MI:SS') create_time from t_cc_wywx_grid  a where higher_id = '").append(higher_id).append("'");
			sqlCount.append("select count(*) from ").append("t_cc_wywx_grid where higher_id = '").append(higher_id).append("'");
			Page page = new Page();
			page.setSql(sqlStr.toString());
			page.setSqlCount(sqlCount.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list", _list, page);
		}catch(Exception e){
			Log.error(yhid,"GetMenu","com.ccoffice.base.grid.getGridList","查询菜单数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
}
