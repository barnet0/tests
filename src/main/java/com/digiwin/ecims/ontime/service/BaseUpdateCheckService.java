package com.digiwin.ecims.ontime.service;

import java.util.Date;

import com.digiwin.ecims.core.api.EcImsApiSyncOrdersService;
import com.digiwin.ecims.core.api.EcImsApiSyncRefundsService;
import com.digiwin.ecims.core.model.AomsshopT;

/**
 * 资料检查（补单）服务基础接口
 * @author 维杰
 *
 */
public interface BaseUpdateCheckService {

  void executeOrderUpdateCheck(
      EcImsApiSyncOrdersService ecImsApiSyncOrdersService,
      String scheduleType, AomsshopT aomsshopT, 
      String startDate, String endDate) 
          throws Exception;

  void executeRefundUpdateCheck(
      EcImsApiSyncRefundsService ecImsApiSyncRefundsService,
      String scheduleType, AomsshopT aomsshopT, 
      String startDate, String endDate) 
          throws Exception;

  void saveTaskScheduleConfigIfNotExist(String scheduleType);
  
  void updateLastRunTime(Date lastRunTime, String scheduleType);

}
