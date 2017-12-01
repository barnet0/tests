package com.digiwin.ecims.system.service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

/**
 * 退款用
 * @author Xavier
 *
 */
public interface SyncRefundDataByHandService {
	
	/**
	 * 通过创建时间在EC查询所有订单，返回订单数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findRefundDataFromECByCreateDate(String storeId,String startDate,String endDate);
	
	/**
	 * 通过创建时间在EC查询所有订单，返回订单数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findRefundDataFromECByCreateDate(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT);

}
