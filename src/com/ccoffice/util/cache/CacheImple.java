package com.ccoffice.util.cache;

import java.net.URL;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheImple implements CacheInterface{
	private Cache cache;
	private CacheManager manager;
	private URL url;
	private Element element;

	public CacheImple(){
		cache = null;
		manager = null;
		url = null;
		element = null;
	}
	public void init()
	{
		if (manager == null){
			url = getClass().getResource("/ehcache.xml");
			manager = new CacheManager(url);
		}
		cache = manager.getCache("syscache");
	}
	public void setCacheInfo(String key, Object obj,int timeToIdleSeconds,int timeToLiveSeconds) {
		if (cache == null){
			init();
		}
		Element e = new Element((new StringBuilder(String.valueOf(key))).toString(), obj);
		cache.put(e);
	}
	public Object getCacheInfo(String key) {
		if (cache == null){
			return null;
		}
		element = cache.get((new StringBuilder(String.valueOf(key))).toString());
		if (element == null){
			return null;
		}else{
			return element.getObjectValue();
		}
	}
	public void removeCacheInfo(String key)
	{
		if (cache == null){
			init();
		}
		cache.remove((new StringBuilder(String.valueOf(key)).toString()));
	}
	public boolean isExists(String key) {
		boolean isExist = false;
		try{
			isExist = cache.isKeyInCache(key);
		}catch(Exception e){
			isExist = false;
		}
		return isExist;
	}
	@Override
	public List getKeys() {
		// TODO Auto-generated method stub
		return cache.getKeys();
	}
	
}
