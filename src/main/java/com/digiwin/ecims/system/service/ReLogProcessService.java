package com.digiwin.ecims.system.service;

import java.util.List;
import java.util.Map;

import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

/**
 * 提供給Query資料及業務邏輯的接口
 * @author Sen.Shen
 *
 */
public interface ReLogProcessService {
	public List<Object> getReAomsData(String tableName);
	
	public <T> List<T> getReAomsData(String pojoName, Class<T> clazz);
	
	public TaskScheduleConfig getTaskScheduleConfigInfo(String scheduleType);
	
	public Map<String,Object> getInvoiceIdMappingLogId(String tableName);
	
	public int deleteLogByHql(String hql);
	
	public int deleteLogBySql(String sql);
	
}
