package com.digiwin.ecims.platforms.aliexpress.service.api;

public interface AliexpressCacheService {

  public boolean isKeyExist(String key);
  
  public void put(String key, Object value);
  
  public Object get(String key);
}
