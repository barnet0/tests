package com.digiwin.ecims.platforms.dangdang.service;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get.OrderDetailsGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get.OrdersListGetResponse;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.system.enums.UseTimeEnum;

public interface DangdangApiService extends EcImsApiService {

	/**
	 * 多笔订单下载(同步用)
	 * 
	 * @param aomsshopT
	 *            授权资料
	 * @param sDate
	 *            更新起始时间
	 * @param eDate
	 *            更新截止时间
	 * @param state
	 *            订单状态
	 * @param page
	 *            页码
	 * @param pageSize
	 *            每页显示资料笔数
	 * @param dateType
	 *            默认0按修改时间查询，1为按创建时间查询
	 * @return OrdersListGetResponse response結果
	 * @throws Exception
	 */
	OrdersListGetResponse dangdangOrdersListGet(
			AomsshopT aomsshopT, String sDate, String eDate, String state, int page, int pageSize, UseTimeEnum dateType
			, String scheduleType
	) throws Exception;

	/**
	 * 单笔订单下载(同步用)
	 * 
	 * @param aomsshopT
	 *            授权资料
	 * @param orderID
	 *            订单编号
	 * @return OrderDetailsGetResponse response結果
	 * @throws Exception
	 */
	OrderDetailsGetResponse dangdangOrderDetailsGet(AomsshopT aomsshopT, String orderID, String scheduleType) throws Exception;

	// =======定时任务========
	/**
	 * 同步订单资料
	 * 
	 * @param taskScheduleConfig
	 * 
	 * @param aomsshopT
	 * 
	 * @return
	 * @throws Exception
	 */
	void syncOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception;

	/**
	 * 同步铺货资料
	 * 
	 * @param taskScheduleConfig
	 * 
	 * @param aomsshopT
	 * 
	 * @return
	 * @throws Exception
	 */
	void syncGoodsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception;

	/**
	 * 同步退换货订单列表汉退款订单列表
	 * 
	 * @param taskScheduleConfig
	 * 
	 * @param aomsshopT
	 * 
	 * @return
	 * @throws Exception
	 */
	void syncReturnListAndRefundsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT) throws Exception;

}
