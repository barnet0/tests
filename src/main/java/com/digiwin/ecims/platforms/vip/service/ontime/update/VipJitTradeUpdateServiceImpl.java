package com.digiwin.ecims.platforms.vip.service.ontime.update;

import java.util.Date;
import java.util.List;

import org.hibernate.exception.DataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.enumvar.TaskScheduleConfigRunTypeEnum;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.platforms.vip.service.VipApiService;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool;

@Service("vipJitTradeUpdateServiceImpl")
public class VipJitTradeUpdateServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(VipJitTradeUpdateServiceImpl.class);

  @Autowired
  private VipApiService vipApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-VipJitTradeUpdateServiceImpl--取得唯品会JIT交易数据---方法");
    Date sysDate = new Date();
    TaskScheduleConfig taskScheduleConfig = null;

    StringBuffer scheduleTypeBuffer =
        new StringBuffer(VipCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY);
    scheduleTypeBuffer.append(VipCommonTool.JIT_ORDER_UPDATE_SCHEDULE_NAME_PREFIX);
    int prefixLength = VipCommonTool.JIT_ORDER_UPDATE_SCHEDULE_NAME_PREFIX.length();

    String scheduleType = null;
    try {
      List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(VipCommonTool.STORE_TYPE);
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
          logger.info("当前排程:{}, 店铺ID:{}", scheduleType, aomsshopT.getAomsshop001());
          logger.info("获取同步前的上次同步时间{}", taskScheduleConfig.getLastUpdateTime());
          taskScheduleConfig.setEndDateByMonth(sysDate, 3);
          vipApiService.syncJitOrdersData(taskScheduleConfig, aomsshopT);
          if (scheduleTypeBuffer.length() > 0) {
            scheduleTypeBuffer.delete(prefixLength + 1, scheduleTypeBuffer.length());
          }
        }
      }
    } catch (DataException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "VipJitTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "保存数据异常",
          e.getSQLException().getMessage(), scheduleType, "");
      e.printStackTrace();
      logger.error("DataException = {}", e.getSQLException().getMessage());
      throw e;
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "VipJitTradeUpdateServiceImpl", LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "获取数据异常",
          "Exception:" + e.getLocalizedMessage(), scheduleType, "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getLocalizedMessage());
      throw e;
    }
    logger.info("定时任务结束-VipJitTradeUpdateServiceImpl--取得唯品会JIT交易数据---方法");
    return false;
  }

}
