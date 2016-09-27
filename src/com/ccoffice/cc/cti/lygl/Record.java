package com.ccoffice.cc.cti.lygl;

import java.util.List;
import java.util.Map;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;

public class Record {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Record(Map _map){
		this._map = _map;
		this.yhid = _map.get("yhid").toString();
		this.groupId = _map.get("group_id").toString();
		json = new JsonData();
	}	
	
	public Map list(){	
		try{
			String caller = _map.get("caller")==null?"":String.valueOf(_map.get("caller"));
			String called = _map.get("called")==null?"":String.valueOf(_map.get("called"));	
			String begintime_start = _map.get("begintime_start")==null?"":String.valueOf(_map.get("begintime_start"));
			String begintime_end = _map.get("begintime_end")==null?"":String.valueOf(_map.get("begintime_end"));
			String gh= _map.get("gh")==null?"":String.valueOf(_map.get("gh"));
			StringBuffer sqlStr = new StringBuffer();
			StringBuffer sqlWhere = new StringBuffer();
			sqlStr.append("select date_format(begintime,'%Y-%c-%d %h:%i:%s') begintime, date_format(endtime,'%Y-%c-%d %h:%i:%s') endtime")
					.append(",(case when calltype = 0 then '呼入' when calltype= 1 then '呼出' else '' end ) calltype,called,caller,gh,REPLACE(path,'\\\\','/') path from t_cti_record a where status = 1 ");
			if(!"".equals(caller)){
				sqlWhere.append(" and caller like '%"+caller+"%'");
			}
			if(!"".equals(called)){
				sqlWhere.append(" and called like '%"+called+"%'");
			}
			if(!"".equals(gh)){
				sqlWhere.append(" and gh like '%"+gh+"%'");
			}
			if(!"".equals(begintime_start)){
				sqlWhere.append(" and begintime > '"+begintime_start+"'");
			}
			if(!"".equals(begintime_end)){
				sqlWhere.append(" and begintime < '"+begintime_end+" 23:59:59'");
			}
			sqlStr.append(sqlWhere);
			Page page = new Page();
			page.setSql(sqlStr.toString()+" order by begintime desc ");
			page.setSqlCount("select count(*) from t_cti_record where status = 1 "+sqlWhere);
			page.setNext_page(_map.get("page")==null?"1":_map.get("page").toString());
			page.setPage_size(_map.get("limit")==null?"10":_map.get("limit").toString());
			List _list = Db.getPageDataExt(page);
			json.addListEasyUI("table_list", _list, page);
		}catch(Exception e){
			Log.error(yhid,"list","","查询录音列表时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","查询录音列表,"+e.toString());
		}
		return json.getJsonData();
	}
	
	public Map queryRecordPath(){
		try{
			String lsh = _map.get("lsh")==null?"":String.valueOf(_map.get("lsh"));
			if(!"".equals(lsh)){
				List _list = Db.getJtN().queryForList("select path from t_cti_record where xlh = '"+lsh+"'");
				if(_list.size()>0){
					String path = ((Map)_list.get(0)).get("path").toString();
					json.addData("lypath",path);
				}
			}else{
				json.addData("lypath","");
			}
		}catch(Exception e){
			Log.error(yhid,"queryRecordPath","","根据流水号查询录音路径异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","根据流水号查询录音路径列表,"+e.toString());
		}
		return json.getJsonData();
	}
}
