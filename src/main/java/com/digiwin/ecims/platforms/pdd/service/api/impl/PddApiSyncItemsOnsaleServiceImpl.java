package com.digiwin.ecims.platforms.pdd.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncItemsOnsaleService;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncItemsService;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool.ItemStatus;

@Service
public class PddApiSyncItemsOnsaleServiceImpl implements PddApiSyncItemsOnsaleService {

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
    return syncItemsOnsale(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItemsOnsale(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return pddApiSyncItemsService.syncItems(taskScheduleConfig, aomsshopT, ItemStatus.ONSALE);
  }

}
