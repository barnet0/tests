package com.digiwin.ecims.platforms.pdd.service.api;

import com.digiwin.ecims.core.api.EcImsApiSyncItemsService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface PddApiSyncItemsOnsaleService extends EcImsApiSyncItemsService {

  Boolean syncItemsOnsale(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception;
}
