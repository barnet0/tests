package com.digiwin.ecims.platforms.kaola.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncItemsInventoryService;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncItemsService;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.ItemStatus;

/**
 * 下架
 * @author cjp
 *
 */
@Service
public class KaolaApiSyncItemsInventoryServiceImpl implements KaolaApiSyncItemsInventoryService {

  @Autowired
  private KaolaApiSyncItemsService kaolaApiSyncItemsService;

  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncItems(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return syncItemsInventory(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItemsInventory(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return kaolaApiSyncItemsService.syncItems(taskScheduleConfig, aomsshopT, ItemStatus.Inventory);
  }

}
