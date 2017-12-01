package com.digiwin.ecims.platforms.yunji.service.api;

import com.digiwin.ecims.core.api.EcImsApiSyncOrdersService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool.OrderStatus;


public interface YunjiApiSyncOrdersService extends EcImsApiSyncOrdersService{
	Boolean syncOrder(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, OrderStatus orderStatus) throws Exception;

}
