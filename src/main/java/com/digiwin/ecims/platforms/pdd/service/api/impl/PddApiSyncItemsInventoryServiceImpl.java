package com.digiwin.ecims.platforms.pdd.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncItemsInventoryService;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncItemsService;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool.ItemStatus;

@Service
public class PddApiSyncItemsInventoryServiceImpl implements PddApiSyncItemsInventoryService {

  @Autowired
  private PddApiSyncItemsService pddApiSyncItemsService;

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
    return pddApiSyncItemsService.syncItems(taskScheduleConfig, aomsshopT, ItemStatus.INSTOCK);
  }

}
