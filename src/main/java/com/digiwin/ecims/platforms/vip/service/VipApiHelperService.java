package com.digiwin.ecims.platforms.vip.service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface VipApiHelperService {

	public void updateJitOrderStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT, String poNo) throws Exception;
}
