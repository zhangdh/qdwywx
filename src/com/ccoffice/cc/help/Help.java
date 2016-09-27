package com.ccoffice.cc.help;

import java.util.List;
import java.util.Map;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;

public class Help {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Help(Map _map){
		this._map = _map;
		this.yhid = _map.get("yhid").toString();
		this.groupId = _map.get("group_id").toString();
		json = new JsonData();
	}	
	
	public Map query(){	
		try{
			StringBuffer strSql = new StringBuffer();
			strSql.append(" select * from  t_cc_wywx_help order by create_time desc ");
			Page page = new Page();
			page.setSql(strSql.toString());
			page.setSqlCount("select count(*) from t_cc_wywx_help ");
			
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("rows")==null?"10":_map.get("rows").toString());
			List _list = Db.getPageData(page);
			json.addListEasyUI("table_list",_list,page);    
		}catch(Exception e){
			Log.error(yhid,"query","","查询数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询数据时异常,"+e.toString());
		}
		json.addResult(true,"我们已接收到您的反馈，感谢您提出的宝贵意见!");
		return json.getJsonDataEasyUIGrid();
	}
	
	public Map queryAll(){	
		try{
			StringBuffer strSql = new StringBuffer();
			strSql.append(" select * from  t_cc_wywx_help order by create_time desc ");
			List _list = Db.getJtN().queryForList(strSql.toString());
			json.addListEasyUI("table_list",_list,null);    
		}catch(Exception e){
			Log.error(yhid,"queryAll","","查询数据时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询数据时异常,"+e.toString());
		}
		return json.getJsonDataEasyUIGrid();
	}
	public Map save(){	
		try{
			StringBuffer strSql = new StringBuffer();
			String guid = _map.get("guid")==null?"":String.valueOf( _map.get("guid"));
			if("".equals(guid)){
				strSql.append("insert into t_cc_wywx_help(guid,yhid,question,answer,create_time)values('").append(Db.getGUID()).append("','").append(yhid)
					  .append("','").append(_map.get("question")).append("','").append(_map.get("answer")).append("',").append(Db.getDateStr()).append(")");
				
			}else{
				strSql.append("update t_cc_wywx_help set question='").append(_map.get("question")).append("',answer='").append(_map.get("answer")).append("' ")
						.append(" where guid = '").append(guid).append("'");
			}
			Db.getJtN().update(strSql.toString());
		}catch(Exception e){
			Log.error(yhid,"save","","保存数据时异常"+e.toString());
			json.addResult(false, "保存数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
	
	public Map saveSuggestion(){	
		try{
			StringBuffer strSql = new StringBuffer();
			String guid =Db.getGUID();
		 
			strSql.append("insert into t_cc_app_suggestion(guid,yhid,suggestion,create_time)values('").append(guid).append("','").append(yhid)
				  .append("','").append(_map.get("suggestion")).append("',").append(Db.getDateStr()).append(")");
			
			 
			Db.getJtN().update(strSql.toString());
		}catch(Exception e){
			Log.error(yhid,"saveSuggestion","","保存数据时异常"+e.toString());
			json.addResult(false, "保存数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
	public Map delete(){	
		try{
			String guid = _map.get("guid")==null?"":String.valueOf( _map.get("guid"));
			if(!"".equals(guid)){
				Db.getJtN().update("delete from t_cc_wywx_help where guid ='"+guid+"'");
			} 
		}catch(Exception e){
			Log.error(yhid,"delete","","删除数据时异常"+e.toString());
			json.addResult(false, "保存数据时异常,"+e.toString());
		}
		return json.getJsonData();
	}
}
