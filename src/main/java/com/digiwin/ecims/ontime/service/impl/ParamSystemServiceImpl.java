package com.digiwin.ecims.ontime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.ontime.model.SystemParam;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.EcApiValidListEnum;
import com.digiwin.ecims.system.util.SystemCommonUtil;

/**
 * 系统参数的服务实现类
 * 
 * @author hopelee
 *
 */
@Service
public class ParamSystemServiceImpl implements ParamSystemService {

  @Autowired
  BaseDAO baseDAO;

  /**
   * ehcache 缓存插件
   */
  @Autowired
  private EhCacheCacheManager cacheManager;

  public List<SystemParam> findAllSysParam() {
    return baseDAO.findAll(SystemParam.class);
  }

  /**
   * 初始化系统参数
   * 
   * @return
   */
  public boolean initParamToCache() {
    List<SystemParam> sysParamList = this.findAllSysParam();
    try {
      if (sysParamList != null && sysParamList.size() > 0) {
        Cache objcache = cacheManager.getCache("objcache");
        if (objcache != null) {
          objcache.clear();
        }
        for (int i = 0; i < sysParamList.size(); i++) {
          SystemParam sysParam = sysParamList.get(i);
          if (sysParam.getStatus().equals("1")) {
            objcache.put(sysParam.getpKey(), sysParam.getpVal());
          }
        }
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw ex;
    }
  }

  /**
   * 通过key获取系统参数
   * 
   * @param key
   * @return
   */
  public Object getSysParamByKey(String key) {
    Cache sample = cacheManager.getCache("objcache");
    if (sample.get(key) != null) {
      return sample.get(key).get();
    } else {
      return null;
    }
  }

  @Override
  public String getEcApiUrl(EcApiUrlEnum urlEnum) {
    return (String) getSysParamByKey(urlEnum.toString());
  }

  @Override
  public boolean isApiInValidList(String apiName, EcApiValidListEnum ecApiValidListEnum) {
    String apiValidList = (String) getSysParamByKey(ecApiValidListEnum.getKeyName());
    for (String api : apiValidList.split(SystemCommonUtil.PARAM_VALUE_DELIMITER)) {
      if (apiName.equals(api)) {
        return true;
      }
    }
    return false;
  }
  
}
