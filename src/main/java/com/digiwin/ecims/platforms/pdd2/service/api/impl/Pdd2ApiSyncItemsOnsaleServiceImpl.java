package com.digiwin.ecims.platforms.pdd2.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncItemsOnsaleService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncItemsService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.ItemStatusInt;

@Service
public class Pdd2ApiSyncItemsOnsaleServiceImpl implements Pdd2ApiSyncItemsOnsaleService {

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
    return syncItemsOnsale(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItemsOnsale(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return pddApiSyncItemsService.syncItems(taskScheduleConfig, aomsshopT, ItemStatusInt.ONSALE,null,null);
  }

}
