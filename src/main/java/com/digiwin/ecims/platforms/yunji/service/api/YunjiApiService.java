package com.digiwin.ecims.platforms.yunji.service.api;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderDetailResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderListResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundDetailResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundListResponse;

public interface  YunjiApiService extends EcImsApiService{
	/*
	   * ****************************************
	   * *            YunjiApiEncapsulate         *
	   * ****************************************
	   * 云集API简易封装
	   */



			
	/**
	 * 订单信息列表接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param status:订单状态(40:待发货 50:已发货  )
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @param queryType
	 * @return
	 * @throws Exception
	 */
	OrderListResponse yunjiOrderList(
			  String appKey, String appSecret,String version,String status, String startTime, String endTime, 
		      Integer pageNo, Integer pageSize,Integer queryType) throws Exception;

	/**
	 * 订单信息详情接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param version
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	OrderDetailResponse yunjiOrderDetail(String appKey, String appSecret, String version, String orderId) throws Exception;

	/**
	 * 订单售后列表接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param status:订单退款状态(0:无退款;1:申请退款;3:已退款;4:已关闭;5:确认收货) 多个状态获取逗号隔开
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @param pageSize
	 * @param queryType
	 * @return
	 * @throws Exception
	 */	
	OrderRefundListResponse yunjiOrderRefundList(
			String appKey, String appSecret,String version,String status, String startTime, String endTime, 
		      Integer pageNo, Integer pageSize,Integer queryType)throws Exception;
	
	
	/**
	 * 售后信息详情接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param version
	 * @param refundId
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	OrderRefundDetailResponse yunjiOrderRefundDetail(String appKey, String appSecret, String version,String refundId, String orderId) throws Exception;

}