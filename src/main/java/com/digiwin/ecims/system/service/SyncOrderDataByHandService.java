package com.digiwin.ecims.system.service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;

public interface SyncOrderDataByHandService {
  
  public static final String SYNC_BY_CREATE_DATE = "syncOrderDataByCreateDate";
  
  public static final String SYNC_BY_MODIFY_DATE = "syncOrderDataByModifyDate";
  
  public static final String SYNC_BY_ID = "syncOrderDataByOrderId";
  
	/**
	 * 根据订单的新建时间从Ec平台同步订单数据
	 * @param startDate 查询开始时间
	 * @param endDate 查询结束时间
	 * @return {isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''}
	 * 
	 */
	public String syncOrderDataByCreateDate(String storeId,String startDate,String endDate);
	
//	/**
//	 * 根据订单的新建时间从Ec平台同步订单数据
//	 * @param storeId
//	 * @param startDate
//	 * @param endDate
//	 * @param mappingCode 原始数据记录与check_service记录的关联值
//	 * @return
//	 */
//	public String syncOrderDataByCreateDate(String storeId,String startDate,String endDate, String mappingCode);
	
	/**
	 * 根据订单的修改时间从Ec平台同步订单数据
	 * @param startDate 查询开始时间
	 * @param endDate 查询结束时间
	 * @return {isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''}
	 * 
	 */
	public String syncOrderDataByModifyDate(String storeId,String startDate,String endDate);
	
//	/**
//	 * 根据订单的修改时间从Ec平台同步订单数据
//	 * @param storeId
//	 * @param startDate
//	 * @param endDate
//	 * @param mappingCode 原始数据记录与check_service记录的关联值
//	 * @return
//	 */
//	public String syncOrderDataByModifyDate(String storeId,String startDate,String endDate, String mappingCode);
	
	/**
	 * 根据订单的orderId从Ec平台同步订单数据
	 * @param orderId 订单id
	 * @return {isSuccess:'0'/'1',resultSize:100,errCode:'',errMsg:''}
	 * 
	 */
	public String syncOrderDataByOrderId(String storeId,String orderId);
	
	
	/**
	 * 此接口暂时可以不实现
	 * 根据条件类型，查询订单
	 * @param conditionType
	 * @param orderId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public String syncOrderDataByCondition(int conditionType,String orderId,String startDate,String endDate);
	
	/**
	 * 通过创建时间在平台查询所有订单，返回订单数量
	 * @param scheduleUpdateCheckType TODO
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findOrderCountFromEcByCreateDate(String scheduleUpdateCheckType,String storeId,String startDate, String endDate);
	
	/**
	 * 通过创建时间在EC查询所有订单，返回订单数量
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findOrderCountFromEcByCreateDate(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT);
	
	/**
	 * 通过修改时间在平台查询所有订单，返回订单数量
	 * @param scheduleUpdateCheckType TODO
	 * @param storeId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long findOrderCountFromEcByModifyDate(String scheduleUpdateCheckType, String storeId, String startDate, String endDate);
	
	/**
	 * 通过修改时间在平台查询所有订单，返回订单数量
	 * @param taskScheduleConfig
	 * @param aomsshopT
	 * @return
	 */
	public Long findOrderCountFromEcByModifyDate(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT);
}
