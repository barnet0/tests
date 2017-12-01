package com.digiwin.ecims.ontime.service;

import com.digiwin.ecims.core.model.AomsshopT;

public interface OnTimeTaskBusiJobForDataCycleRun extends OnTimeTaskBusiJob {

	public void setCheckDate(String[] reCycleDate);
	public void setScheduleUpdateCheckType(String scheduleUpdateCheckType);
	public void executeUpdateCheckForDataCycleRun(AomsshopT aomsshopT) throws Exception;
}
