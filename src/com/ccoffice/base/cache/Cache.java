package com.ccoffice.base.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccoffice.util.json.JsonData;
import com.ccoffice.util.log.Log;

public class Cache {
	Map _map = null;
	String yhid = "";
	String groupId = "";
	JsonData json = null;
	public  Cache(Map _map){
		json = new JsonData();
		this._map = _map;		
		this.yhid = _map.get("yhid").toString();
		this.groupId = _map.get("group_id").toString();
	}
	
	public Map getCache(){	
		try{
			List<Object> _list = com.ccoffice.util.cache.Cache.getKeys();
			List<Object> reList = new ArrayList();
			for(int i=0;i<_list.size();i++){
				String key = String.valueOf(_list.get(i));
				String value = String.valueOf(com.ccoffice.util.cache.Cache.getCacheInfo(key));
				Map kMap = new HashMap();
				kMap.put("dm",key);
				kMap.put("mc",value);
				reList.add(kMap);
			}
			json.addListEasyUI("table_list", reList, null);
		}catch(Exception e){
			Log.error(yhid,"getCache","","得到缓存时异常"+e.toString());
			json.addData("result","false");
			json.addData("msg","得到缓存时异常");
		}
		return json.getJsonDataEasyUIGrid();
	}
}
