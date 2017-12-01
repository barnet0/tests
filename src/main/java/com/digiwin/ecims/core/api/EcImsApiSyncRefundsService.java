package com.digiwin.ecims.core.api;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface EcImsApiSyncRefundsService extends EcImsApiSyncService {

  /**
   * 使用API的方式，同步退款、退货单
   * 
   * @param taskScheduleConfig 同步設定檔
   * @param aomsshopT 商店授權
   * @throws Exception 
   */
  Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) 
      throws Exception;
  
  /**
   * 使用API的方式，同步退款、退货单
   * 
   * @param taskScheduleConfig 同步設定檔
   * @param aomsshopT 商店授權
   * @throws Exception 
   */
  Boolean syncRefunds(String startDate, String endDate, String storeId) 
      throws Exception;
  
  /**
   * 使用API的方式，获取创建退单的数量
   * @param startDate
   * @param endDate
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @return 创建订单的数量
   * @throws Exception
   */
  Long getCreatedCount(String appKey, String appSecret, String accessToken, 
      String startDate, String endDate) throws Exception;
  
}
