package com.ccoffice.util.tools;

import java.util.List;
import java.util.Map;

import com.ccoffice.util.cache.Cache;
import com.ccoffice.util.db.Db;

public class SysPara {
	public static String getValue(String csdm){
		if(Cache.isExists("para_"+csdm)){
			return String.valueOf(Cache.getCacheInfo("para_"+csdm));
		}else{
			List paraList = Db.getJtN().queryForList("select para_value from t_sys_para where para_name='"+csdm+"'");
			if(paraList.size()>0){
				return String.valueOf(((Map)paraList.get(0)).get("para_value"));
			}else{
				return "";
			}
		}
		
	}
}
