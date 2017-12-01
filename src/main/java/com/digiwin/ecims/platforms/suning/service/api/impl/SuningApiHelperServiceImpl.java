package com.digiwin.ecims.platforms.suning.service.api.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiHelperService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.SyncBasicParm;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

@Service
public class SuningApiHelperServiceImpl implements SuningApiHelperService {

  private Logger logger = LoggerFactory.getLogger(SuningApiHelperServiceImpl.class);
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;
  
  @Override
  public Date calIsOutOfRange(TaskScheduleConfig taskScheduleConfig, Date startDate,
      double diffDays, SyncBasicParm type) {
    final int maxProcessDays = type.getMaxProcessDays(); // 最大可取區間範圍,// 也就是資料不能取超過這個天數範圍
    if (diffDays > maxProcessDays) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "SuningSyncAPIServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()),
          "超過可查詢區間, 最大只能查  " + maxProcessDays + "天己內的資料,  本次查詢起始時間："
              + DateTimeTool.format(startDate),
          "RangeException ： 超過可查詢區間, 最大只能查  " + maxProcessDays + "天己內的資料,  本次查詢起始時間："
              + DateTimeTool.format(startDate),
          "SuningSyncAPIServiceImpl", "");
      return DateTimeTool.getAfterDays(new Date(), (maxProcessDays * -1));
    } else {
      return startDate;
    }
  }

  @Override
  public String calEndDate(String sDate, int remainDays, int getDataDays, String originalEDate) {
    if (remainDays > getDataDays) {
      // 計算下一天日期, 天數正常.
      return DateTimeTool.format(DateTimeTool.getAfterDays(DateTimeTool.parse(sDate), getDataDays));
    } else if (remainDays > 0) {
      // 計算下一天日期, 天數不足, 不能使用到未來日期
      // return DateTimeTool.format(
      // DateTimeTool.getAfterDays(DateTimeTool.parse(sDate, "yyyy-MM-dd HH:mm:ss"), remainDays)
      // , "yyyy-MM-dd HH:mm:ss"
      // );
      return originalEDate;
    } else {
      return null;
    }
  }

  @Override
  public void updateLastUpdateTime(Date modiDate, TaskScheduleConfig taskScheduleConfig) {
 // 如果是 reCycle schedule 的, 則不執行下方logic, 以免影響現有的 check 排程. add by xavier on 20150829
    if (taskScheduleConfig.isReCycle()) {
      return;
    }

    /*
     * 計算時間 因為會直接傳入, 要查詢的日期區間, 且要配合每個 api 的特性去調整.
     * 
     * taskScheduleConfig.getLastUpdateTime(); ==> start time （last time）
     * taskScheduleConfig.getEndDate(); ==> end time (current time)
     * 
     * 若都沒有取到任何資料, 則 lastUpdateTime = endDate.
     */
    Date inputEndDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());

    // 若都沒有取到任何資料, 則 lastUpdateTime = endDate.
    if (modiDate.equals(inputEndDate)) {
      // FIXME 若沒有取得到任何資料, 則不更新 lastUpdateTime...未完成
      logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), taskScheduleConfig.getEndDate());
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(),
          taskScheduleConfig.getEndDate());
    } else {
      logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), modiDate);
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(),
          DateTimeTool.format(modiDate));
    }
  }

  @Override
  public int calAlreadyProcessRow(int maxReadRows, int alreadyReadRows, int thisTimeProcessRows) {
    alreadyReadRows += thisTimeProcessRows;
    return alreadyReadRows >= maxReadRows ? maxReadRows : alreadyReadRows;
  }

}
