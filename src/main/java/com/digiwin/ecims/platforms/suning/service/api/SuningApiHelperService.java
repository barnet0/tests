package com.digiwin.ecims.platforms.suning.service.api;

import java.util.Date;

import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.SyncBasicParm;

public interface SuningApiHelperService {
  
  /**
   * 計算 startDate 是否超過, 可查區間
   * 
   * @param startDate
   * @param diffDays
   * @param type
   * @return
   */
  Date calIsOutOfRange(TaskScheduleConfig taskScheduleConfig, Date startDate,
      double diffDays, SyncBasicParm type);
  
  /**
   * 計算最後天的日期
   * 
   * @param sDate
   * @param remainDays
   * @param getDataDays
   * @return
   */
  String calEndDate(String sDate, int remainDays, int getDataDays, String originalEDate);
  
  /**
   * 若都沒有取到任何資料, 則 lastUpdateTime = endDate.
   * 
   * @param inputEndDate
   * @param modiDate
   * @param taskScheduleConfig
   */
  void updateLastUpdateTime(Date modiDate, TaskScheduleConfig taskScheduleConfig);
  
  int calAlreadyProcessRow(int maxReadRows, int alreadyReadRows, int thisTimeProcessRows);
  
}
