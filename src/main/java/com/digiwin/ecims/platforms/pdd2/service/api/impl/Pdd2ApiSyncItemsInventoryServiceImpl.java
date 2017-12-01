package com.digiwin.ecims.platforms.pdd2.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncItemsInventoryService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncItemsService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.ItemStatusInt;

@Service
public class Pdd2ApiSyncItemsInventoryServiceImpl implements Pdd2ApiSyncItemsInventoryService {

  @Autowired
  private Pdd2ApiSyncItemsService pddApiSyncItemsService;

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
    return pddApiSyncItemsService.syncItems(taskScheduleConfig, aomsshopT, ItemStatusInt.INSTOCK,null,null);
  }

}
