package com.digiwin.ecims.ontime.service;

import com.digiwin.ecims.core.api.EcImsApiSyncService;

/**
 * 同步资料到电商接口排程的基础服务
 * @author 维杰
 *
 */
public interface BaseUpdateService {

  /**
   * 
   * @param scheduleTypeInitLen 排程类型名称初始长度(stringbuffer初始化用)
   * @param scheduleTypeNamePrefix 排程类型名称前缀.如：TaobaoTbTradeUpdate
   * @param storeType 平台类型
   * @param endDatePeriod 同步资料的结束日期距离开始日期的间隔。如果计算完结束日期会大于此时此刻，则结束日期设置成此时此刻。单位：天
   * @param apiSyncService 
   * @return
   * @throws Exception
   */
  Boolean timeOutExecute(
      Integer scheduleTypeInitLen, 
      String scheduleTypeNamePrefix,
      String storeType, 
      Integer endDatePeriod, EcImsApiSyncService apiSyncService) throws Exception;

}
