package com.digiwin.ecims.platforms.kaola.service.api;

import com.digiwin.ecims.core.api.EcImsApiSyncOrdersService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.OrderStatus;

public interface KaolaApiSyncOrdersService extends EcImsApiSyncOrdersService {


	Boolean syncOrder(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, OrderStatus orderStatus) throws Exception;
}
