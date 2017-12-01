package com.digiwin.ecims.platforms.kaola.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncItemsOnsaleService;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncItemsService;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.ItemStatus;

/**
 * 在售
 * @author cjp
 *
 */
@Service
public class KaolaApiSyncItemsOnsaleServiceImpl implements KaolaApiSyncItemsOnsaleService {

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
    return syncItemsOnsale(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItemsOnsale(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return kaolaApiSyncItemsService.syncItems(taskScheduleConfig, aomsshopT, ItemStatus.ONSALE);
  }

}
