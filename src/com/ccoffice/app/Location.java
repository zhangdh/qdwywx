package com.ccoffice.app;

import java.util.List;
import java.util.Map;
import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;
import com.ccoffice.util.log.Log;
import com.ccoffice.util.page.Page;

public class Location {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Location(Map _map){
		this._map = _map;
		this.yhid = _map.get("yhid").toString();
		this.groupId = _map.get("group_id").toString();
		json = new JsonData();
	}	
	
	public Map saveLocation(){	
		try{
			String longitude = _map.get("longitude")==null?"":String.valueOf(_map.get("longitude"));
			String latitude = _map.get("latitude")==null?"":String.valueOf(_map.get("latitude"));
			StringBuffer sqlStr = new StringBuffer();
			if(!"".equals(longitude)&& !"".equals(latitude)){
				sqlStr.append("insert into t_cc_location (guid,yhid,longitude,latitude,create_time)values('").append(Db.getGUID()).append("','").append(yhid)
				  .append("',").append(longitude).append(",").append(latitude).append(",").append(Db.getDateStr()).append(")");
				Db.getJtN().update(sqlStr.toString());
			}
		}catch(Exception e){
			Log.error(yhid,"saveLocation","","保存位置信息时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","保存位置信息时异常,"+e.toString());
		}
		return json.getJsonData();
	}
	
	public Map saveDeviceId(){	
		try{
			String pushDeviceId = _map.get("pushDeviceId")==null?"":String.valueOf(_map.get("pushDeviceId"));
			Cache.setCacheInfo(yhid+"_pushDeviceId", pushDeviceId);
		}catch(Exception e){
			Log.error(yhid,"saveDeviceId","","将推送设备id数据存入缓存时异常"+e.toString());
			json.addData("success","false");
			json.addData("msg","将推送设备id数据存入缓存时,"+e.toString());
		}
		return json.getJsonData();
	}
}
