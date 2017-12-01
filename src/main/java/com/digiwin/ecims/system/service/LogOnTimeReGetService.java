package com.digiwin.ecims.system.service;

import java.util.List;

import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

public interface LogOnTimeReGetService extends OnTimeTaskBusiJob {
	
	/**
	 * 執行單筆的拉取
	 * @param data
	 * @param startProcessRow
	 * @return
	 */
	public boolean executeReTry(List<LogInfoT> data, int startProcessRow);
	
	/**
	 * 取得單筆資料
	 * @param hql
	 * @return
	 */
	public Object getUniqueData(String hql);
	
	public<T> void saveReGetDoDBOperate(List<T> rows);
}
