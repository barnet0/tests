package com.digiwin.ecims.core.api;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface EcImsApiSyncItemsService extends EcImsApiSyncService {

  /**
   * 使用API的方式，自动同步商品资料
   * @param taskScheduleConfig
   * @param aomsshopT
   * @return
   * @throws Exception
   */
  Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception;
}
