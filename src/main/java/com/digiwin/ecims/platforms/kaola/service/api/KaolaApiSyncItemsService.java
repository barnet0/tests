package com.digiwin.ecims.platforms.kaola.service.api;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.ItemStatus;

public interface KaolaApiSyncItemsService {

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
