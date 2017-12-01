package com.digiwin.ecims.system.service;

import java.util.HashMap;

public interface PushRefundDataByHandService {
	
	/**
	 * 根据创建时间在中间库查询当前区间的退款数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findRefundDataCountFromDBByCreateDate(String storeId,String startDate,String endDate);
	
	/**
	 * 根据创建时间在中间库查询当前区间的退款数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public <T> Long findRefundDataCountFromDBByCreateDate(HashMap<String, String> params, Class<T> clazz);

}
