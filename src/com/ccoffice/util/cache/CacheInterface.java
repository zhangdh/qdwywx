package com.ccoffice.util.cache;

import java.util.List;

public interface CacheInterface {
	public  void setCacheInfo(String key,Object obj,int timeToIdleSeconds,int timeToLiveSeconds);
	
	public  Object getCacheInfo(String key);
	
	public  void removeCacheInfo(String key);
	
	public  boolean isExists(String key);
	
	public List getKeys();
}
