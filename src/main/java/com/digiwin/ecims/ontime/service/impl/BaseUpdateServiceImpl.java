package com.digiwin.ecims.ontime.service.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.api.EcImsApiSyncService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.enumvar.TaskScheduleConfigRunTypeEnum;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

@Service
public class BaseUpdateServiceImpl extends UpdateTask implements BaseUpdateService {

  private static final Logger logger = LoggerFactory.getLogger(BaseUpdateServiceImpl.class);

  private static final String CLASS_NAME = "BaseUpdateServiceImpl";

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;

  @Override
  public Boolean timeOutExecute(Integer scheduleTypeInitLen, String scheduleTypeNamePrefix,
      String storeType, Integer endDatePeriod, EcImsApiSyncService apiSyncService) throws Exception {
    Date sysDate = new Date();
    TaskScheduleConfig taskScheduleConfig = null;

    StringBuffer scheduleTypeBuffer = new StringBuffer(scheduleTypeInitLen);
    scheduleTypeBuffer.append(scheduleTypeNamePrefix);
    int prefixLength = scheduleTypeNamePrefix.length();

    String scheduleType = null;
    try {
      List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(storeType);
      if (aomsShopList != null && aomsShopList.size() != 0) {
        for (int i = 0; i < aomsShopList.size(); i++) {
          AomsshopT aomsshopT = aomsShopList.get(i);
          scheduleTypeBuffer.append(OntimeCommonUtil.SCHEDULE_NAME_DELIMITER)
              .append(aomsshopT.getAomsshop001()).append(OntimeCommonUtil.SCHEDULE_NAME_DELIMITER)
              .append(aomsshopT.getAomsshop003());
          /*
           * 获取定时任务的schedule配置， 此配置内存放了该批次的定时任务的最后更新时间，每笔最大更新条数
           */
          scheduleType = scheduleTypeBuffer.toString();
          taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
          // taskScheduleConfig不存在就產一個新的
          // 如果没有设置更新时间，默认从很久以前开始同步
          if (taskScheduleConfig.getLastUpdateTime() == null
              || "".equals(taskScheduleConfig.getLastUpdateTime())) {
            taskScheduleConfig.setLastUpdateTime(getInitDateTime());
            taskScheduleConfig.setMaxReadRow(2000);
            taskScheduleConfig.setMaxPageSize(50);
            taskScheduleConfig.setStoreId(aomsshopT.getAomsshop001());
            taskScheduleConfig.setRunType(TaskScheduleConfigRunTypeEnum.PULL.getRunType());

            taskService.saveTaskTaskScheduleConfig(taskScheduleConfig);
          }
          taskService.newTransaction4SaveLastRunTime(scheduleType, null); // 記錄執行時間
          logger.info("当前排程:{}, 店铺ID:{}, 上次同步时间{}", 
              scheduleType, aomsshopT.getAomsshop001(), taskScheduleConfig.getLastUpdateTime());
          if (endDatePeriod != null) {
            taskScheduleConfig.setEndDateByDate(sysDate, endDatePeriod);
          }
          
          apiSyncService.syncData(taskScheduleConfig, aomsshopT);

          if (scheduleTypeBuffer.length() > 0) {
            scheduleTypeBuffer.delete(prefixLength, scheduleTypeBuffer.length());
          }
        }
      }
    } catch (DataException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), CLASS_NAME,
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "保存数据异常",
          e.getSQLException().getMessage(), scheduleType, "");
      e.printStackTrace();
      logger.error("DataException = {}", e.getSQLException().getMessage());
      throw e;
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(), CLASS_NAME,
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "获取数据异常",
          "Exception:" + e.getLocalizedMessage(), scheduleType, "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getLocalizedMessage());
      throw e;
    }

    return true;
  }

}
