package com.digiwin.ecims.system.service;

import java.util.HashMap;

public interface PushOrderDataByHandService {

	/**
	 * 根据订单的新建时间从中间库查询出的订单推送到oms系统
	 * @param startDate 查询开始时间
	 * @param endDate 查询结束时间
	 * @return {isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''}
	 * 
	 */
	public String pushOrderDataByCreateDate(String storeId,String startDate,String endDate);
	
	/**
	 * 根据订单的修改时间从中间库查询出的订单推送到oms系统
	 * @param startDate 查询开始时间
	 * @param endDate 查询结束时间
	 * @return {isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''}
	 * 
	 */
	public String pushOrderDataByModifyDate(String storeId,String startDate,String endDate);
	
	/**
	 * 根据订单的orderId从中间库查询出的订单推送到oms系统
	 * @param orderId 订单id
	 * @return {isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''}
	 * 
	 */
	public String pushOrderDataByOrderId(String storeId,String orderId);
	
	/**
	 * 根据订单的支付时间从中间库查询出的订单推送到OMS系统
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @return {isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''}
	 */
	public String pushOrderDataByPayDate(String storeId, String startDate, String endDate);
	
	/**
	 * 此接口暂时可以不实现
	 * 根据条件类型，推送中间库查询出的订单
	 * @param conditionType
	 * @param orderId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String pushOrderDataByCondition(int conditionType,String orderId,String startDate,String endDate);
	
	
	/**
	 * 根据创建时间在中间库查询当前区间的订单数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findOrderDataCountFromDBByCreateDate(String storeId,String startDate,String endDate);
	
	/**
	 * 根据创建时间在中间库查询当前区间的订单数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public <T> Long findOrderDataCountFromDBByCreateDate(HashMap<String, String> params, Class<T> clazz);
	
	/**
	 * 根据修改时间在中间库查询当前区间的订单数量
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findOrderDataCountFromDbByModifyDate(String storeId, String startDate, String endDate);
}
