package com.digiwin.ecims.platforms.pdd2.service.api;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.ItemStatus;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.ItemStatusInt;

public interface Pdd2ApiSyncItemsService {

  /**
   * 使用API的方式，自动同步商品资料
   * @param taskScheduleConfig
   * @param aomsshopT
   * @return
   * @throws Exception
   */
Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, ItemStatusInt itemStatus,
		String goodsName, String outerId) throws Exception;
}
