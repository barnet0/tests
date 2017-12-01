package com.digiwin.ecims.platforms.ccb.service.manual.sync;

import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;

@Service("syncCcbOrderDataByHandServiceImpl")
public class SyncCcbOrderDataByHandServiceImpl implements SyncOrderDataByHandService {

	@Override
	public String syncOrderDataByCreateDate(String storeId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String syncOrderDataByModifyDate(String storeId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String syncOrderDataByOrderId(String storeId, String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String syncOrderDataByCondition(int conditionType, String orderId, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findOrderCountFromEcByCreateDate(String scheduleUpdateCheckType, String storeId, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findOrderCountFromEcByCreateDate(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findOrderCountFromEcByModifyDate(String scheduleUpdateCheckType, String storeId, String startDate,
			String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findOrderCountFromEcByModifyDate(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) {
		// TODO Auto-generated method stub
		return null;
	}

}
