package com.digiwin.ecims.core.api;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface EcImsApiSyncOrdersService extends EcImsApiSyncService {

  /**
   * 使用API的方式，自动同步增量订单
   * @param taskScheduleConfig
   * @param aomsshopT
   * @throws Exception 
   */
  Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) 
      throws Exception;
  
  /**
   * 使用API的方式，手动同步增量订单，并返回同步订单数量
   * @param startDate
   * @param endDate
   * @param storeId
   * @param scheduleType 如果不为null，则是补单排程；否则 为手动操作
   * @return 同步的订单的数量
   * @throws Exception
   */
  Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception;
  
  /**
   * 使用API的方式，获取增量订单的数量
   * @param startDate
   * @param endDate
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @return 增量订单的数量
   * @throws Exception
   */
  Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws Exception;

  /**
   * 使用API的方式，自动同步创建订单
   * @param taskScheduleConfig
   * @param aomsshopT
   * @throws Exception
   */
  Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) 
      throws Exception;
  
  /**
   * 使用API的方式，手动同步创建的订单，并返回同步订单数量
   * @param startDate
   * @param endDate
   * @param storeId
   * @param scheduleType 如果不为null，则是补单排程；否则 为手动操作
   * @return 同步订单的数量
   * @throws Exception
   */
  Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception;
  
  /**
   * 使用API的方式，获取创建订单的数量
   * @param startDate
   * @param endDate
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @return 创建订单的数量
   * @throws Exception
   */
  Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws Exception;
  
  /**
   * 使用API的方式，手动同步单个订单
   * @param storeId
   * @param orderId
   * @throws Exception
   */
  Boolean syncOrderByOrderId(String storeId, String orderId) 
      throws Exception;
}
