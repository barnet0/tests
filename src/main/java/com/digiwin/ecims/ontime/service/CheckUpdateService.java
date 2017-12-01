package com.digiwin.ecims.ontime.service;

import com.digiwin.ecims.core.model.SourceLogT;

/**
 * 用來 記錄 Check_Service_ReCall_T 的關聯key.
 * @author Xavier
 *
 */
public interface CheckUpdateService {

	public boolean initParamToCache();
	public void put(String startDate, String endDate, String scheduleType, String mappingKey) throws Exception;
	public String get(SourceLogT sourceLog) throws Exception;
	public String get(String startDate, String endDate, String scheduleType) throws Exception;
	public void remove(String startDate, String endDate, String scheduleType) throws Exception;
}
