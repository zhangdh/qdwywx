package com.ccoffice.base.syspara;

import java.util.List;
import java.util.Map;

import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;

public class SysPara {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  SysPara(Map map){
		this._map = map;
		this.yhid = map.get("yhid").toString();
		this.groupId = map.get("group_id").toString();
		json = new JsonData();
	}
	
	public Map query(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			String temp = "";
			temp = _map.get("cx_name")==null?"":String.valueOf(_map.get("cx_name"));
			if(!"".equals(temp)){
				sqlWhere.append(" and para_name like '%").append(temp).append("%'");
			}
			temp = _map.get("cx_value")==null?"":String.valueOf(_map.get("cx_value"));
			if(!"".equals(temp)){
				sqlWhere.append(" and para_value like '%").append(temp).append("%'");
			}
			Page page = new Page();
			page.setSql("select * from  t_sys_para where zt_dm = 1 "+sqlWhere.toString());
			page.setSqlCount("select count(*) from t_sys_para where zt_dm = 1  "+sqlWhere.toString());
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list,page);   
		}catch(Exception e){
			Log.error(yhid,"query","com.ccoffice.base.syspara.SysPara","查询系统参数数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
	
	public Map save(){
		try{
			StringBuffer sqlStr = new StringBuffer();
			String para_id = _map.get("para_id")==null?"":String.valueOf(_map.get("para_id"));
			if(!"".equals(para_id)){
				sqlStr.append("update t_sys_para set para_name = '").append(String.valueOf(_map.get("para_name"))).append("',")
				      .append("para_value = '").append(String.valueOf(_map.get("para_value"))).append("',")
				      .append("para_comment='").append(String.valueOf(_map.get("para_comment"))).append("' ")
				      .append(" where para_id = '").append(para_id).append("'");
			}
			Db.getJtN().update(sqlStr.toString());
		}catch(Exception e){
			Log.error(yhid,"save","com.ccoffice.base.syspara.SysPara","保存系统参数数据时异常"+e.toString());
		}
		return json.getJsonDataMap();
	}
}
