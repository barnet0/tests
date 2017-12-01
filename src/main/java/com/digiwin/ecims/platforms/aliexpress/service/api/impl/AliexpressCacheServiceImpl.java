package com.digiwin.ecims.platforms.aliexpress.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressCacheService;

@Service
public class AliexpressCacheServiceImpl implements AliexpressCacheService {

  @Autowired
  private EhCacheCacheManager cacheManager;

  @Override
  public boolean isKeyExist(String key) {
    Cache cache = getCache();
    if (cache.get(key) != null) {
      return true;
    }
    return false;
  }

  @Override
  public void put(String key, Object value) {
    Cache cache = getCache();
    cache.put(key, value);
  }

  @Override
  public Object get(String key) {
    if (isKeyExist(key)) {
      return getCache().get(key).get();
    }
    return null;
  }

  private Cache getCache() {
    return cacheManager.getCache("othercache");
  }
}
