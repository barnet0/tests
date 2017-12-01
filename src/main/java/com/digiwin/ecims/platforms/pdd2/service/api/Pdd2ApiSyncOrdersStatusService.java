package com.digiwin.ecims.platforms.pdd2.service.api;

import com.digiwin.ecims.core.api.EcImsApiSyncOrdersService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool.OrderStatus;

public interface Pdd2ApiSyncOrdersStatusService extends EcImsApiSyncOrdersService {
	void syncOrdersByStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception;
}
