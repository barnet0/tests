package com.digiwin.ecims.platforms.kaola.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncOrdersService;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncOrdersSignedService;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool.OrderStatus;
@Service
public class KaolaApiSyncOrdersSignedServiceImpl implements KaolaApiSyncOrdersSignedService{
	  @Autowired
	  private KaolaApiSyncOrdersService kaolaApiSyncOrdersService;

	  @Override
	  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
	      throws Exception {
		  syncOrders(taskScheduleConfig, aomsshopT);
	  }


	  public Boolean syncOrders(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
	      throws Exception {
	    return syncOrdersSigned(taskScheduleConfig, aomsshopT);
	  }

	  public Boolean syncOrdersSigned(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
	      throws Exception {
	    return kaolaApiSyncOrdersService.syncOrder(taskScheduleConfig, aomsshopT, OrderStatus.SIGNED);
	  }

	@Override
	public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getIncrementalOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCreatedOrdersCount(String appKey, String appSecret, String accessToken, String startDate,
			String endDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	  

}
