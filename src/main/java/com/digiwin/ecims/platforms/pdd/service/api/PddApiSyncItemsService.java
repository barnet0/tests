package com.digiwin.ecims.platforms.pdd.service.api;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool.ItemStatus;

public interface PddApiSyncItemsService {

  /**
   * 使用API的方式，自动同步商品资料
   * @param taskScheduleConfig
   * @param aomsshopT
   * @return
   * @throws Exception
   */
  Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, ItemStatus itemStatus)
      throws Exception;
}
