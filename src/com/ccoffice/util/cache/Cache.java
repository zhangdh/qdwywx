package com.ccoffice.util.cache;

import java.util.List;
import java.util.Map;

import com.ccoffice.bean.LoginUser;



public class Cache {
	private static CacheInterface cache = new CacheImple();
	public Cache(){
	
	}
	public static void setCacheInfo(String key,Object obj){
		cache.setCacheInfo(key, obj,0,0);
	}
	public static Object getCacheInfo(String key){
		return cache.getCacheInfo(key);
	}
	public static void removeCacheInfo(String key)
	{
		cache.removeCacheInfo(key);
	}
	public static boolean isExists(String key){
		return cache.isExists(key);
	}
	public static void setUserInfo(String yhid,String key,Object obj){
		cache.setCacheInfo(yhid+"_"+key, obj,0,0);
	}
	public static Object getUserInfo(String yhid,String key){
		return cache.getCacheInfo(yhid+"_"+key);
	}
	public static void removeUserInfo(String yhid,String key){
		cache.removeCacheInfo(yhid+"_"+key);
	}
	public static void setLoginInfo(String yhid,Object obj){
		cache.setCacheInfo(yhid, obj,0,0);
	}
	public static LoginUser getLoginInfo(String yhid){
		LoginUser user = (LoginUser)cache.getCacheInfo(yhid);
		return user;
	}
	public static void removeLoginInfo(String token){
		String yhid = String.valueOf(cache.getCacheInfo(token));
		removeCacheInfo(yhid);
		removeCacheInfo(token);
	}
	public static List getKeys(){
		return cache.getKeys();
	}
}
