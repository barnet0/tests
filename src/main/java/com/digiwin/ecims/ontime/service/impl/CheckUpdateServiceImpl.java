package com.digiwin.ecims.ontime.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.SourceLogT;
import com.digiwin.ecims.ontime.service.CheckUpdateService;

/**
 * 用來 記錄 Check_Service_ReCall_T 的關聯key.
 * @author Xavier
 *
 */
@Service
public class CheckUpdateServiceImpl implements CheckUpdateService {
	
	//缓存名稱
	private final String CACHE_NAME = "checkcache";
	
	/**
	 * ehcache 缓存插件
	 */
	@Autowired
	private EhCacheCacheManager cacheManager;
	
	public boolean initParamToCache() {
		try {
			Cache paramCache = cacheManager.getCache(this.CACHE_NAME);
			if(paramCache != null){
				paramCache.clear();
			}
			return Boolean.TRUE;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Boolean.FALSE;
	}
	
	public void put(String startDate, String endDate, String scheduleType, String mappingKey) throws Exception {
		try {
			String key = scheduleType + startDate + endDate;
			this.put(key, mappingKey);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String get(SourceLogT sourceLog) throws Exception {
		try {
			String key = sourceLog.getScheduleType() + sourceLog.getStartDate() + sourceLog.getEndDate();
			return this.get(key);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public String get(String startDate, String endDate, String scheduleType) throws Exception {
		try {
			String key = scheduleType + startDate + endDate;
			return this.get(key);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void remove(String startDate, String endDate, String scheduleType) throws Exception {
		try {
			String key = scheduleType + startDate + endDate;
			this.remove(key);
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private void put(String key, String value) throws Exception{
		Cache paramCache = cacheManager.getCache(this.CACHE_NAME);
		paramCache.put(key, value);
	}
	
	private String get(String key) throws Exception {
		Cache paramCache = cacheManager.getCache(this.CACHE_NAME);
		ValueWrapper value = paramCache.get(key);
		return value == null ? null : (String)value.get();
	}

	private void remove(String key) throws Exception {
		Cache paramCache = cacheManager.getCache(this.CACHE_NAME);
		paramCache.evict(key);
	}
}
