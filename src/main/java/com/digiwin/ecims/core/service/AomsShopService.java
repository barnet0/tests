package com.digiwin.ecims.core.service;

import java.util.List;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;

/**
 * 电商铺货信息服务接口
 * 
 * @author hopelee
 *
 */
public interface AomsShopService {
  /**
   * 通过平台类型获得店铺信息
   * 
   * @return
   */
  public List<AomsshopT> getStoreByStoreType(String storeType);

  /**
   * 通过店铺编号取得店铺信息
   * 
   * @param storeId
   * @return
   */
  public AomsshopT getStoreByStoreId(String storeId);

  
  /**
   * 存放電商所授權的 api 使用碼
   * 
   * @param storeId
   * @return
   */
  public ESVerification getAuthorizationByStoreId(String storeId);

}
