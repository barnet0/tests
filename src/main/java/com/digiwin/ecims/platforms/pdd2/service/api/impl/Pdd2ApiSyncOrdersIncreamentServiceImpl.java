package com.digiwin.ecims.platforms.pdd2.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncOrdersIncreamentService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncOrdersService;

@Service
public class Pdd2ApiSyncOrdersIncreamentServiceImpl implements Pdd2ApiSyncOrdersIncreamentService {

	@Autowired
	 private Pdd2ApiSyncOrdersService pdd2ApiSyncOrdersService;
	
	@Override
	public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
			throws Exception {
		return pdd2ApiSyncOrdersService.syncOrdersByIncremental(taskScheduleConfig, aomsshopT);

	}

	@Override
	public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {
		return pdd2ApiSyncOrdersService.syncOrdersByIncremental(startDate, endDate, storeId, scheduleType);

	}

	@Override
	public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		return pdd2ApiSyncOrdersService.syncOrdersByCreated(taskScheduleConfig, aomsshopT);
		
	}

	@Override
	public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {
		return pdd2ApiSyncOrdersService.syncOrdersByCreated(startDate, endDate, storeId, scheduleType);
		
	}

	@Override
	public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {
		return pdd2ApiSyncOrdersService.getCreatedOrdersCount(appKey, appSecret, accessToken, startDate, endDate);
		
	}

	@Override
	public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		pdd2ApiSyncOrdersService.syncOrdersByIncremental(taskScheduleConfig, aomsshopT);

	}

}
