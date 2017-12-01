package com.digiwin.ecims.platforms.pdd.service.ontime.updatecheck;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.service.BaseUpdateCheckService;
import com.digiwin.ecims.ontime.service.OnTimeTaskCheckBusiJob;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncOrdersService;

@Service("pddTradeUpdateCheckServiceImpl")
public class PddTradeUpdateCheckServiceImpl implements OnTimeTaskCheckBusiJob {

  private static final Logger logger =
      LoggerFactory.getLogger(PddTradeUpdateCheckServiceImpl.class);

  @Autowired
  private PddApiSyncOrdersService pddApiSyncOrdersService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Autowired
  private BaseUpdateCheckService baseCheckUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    return timeOutExecuteCheck(args);
  }

  @Override
  public boolean timeOutExecuteCheck(String... args) throws Exception {
    /*
     * 格式:
     * 排程名称#平台编号#时间类型#检验时间范围 
     * service#storeType#timeType#reCycleTime 
     * ex: taobaoTbTradeUpdateCheck#0#Hour#1，表示淘宝普通订单，检验前一小时至当前时间的订单
     * 
     * inputData[0] = serviceName 
     * inputData[1] = storeType 
     * inputData[2] = timeType 
     * inputData[3] = reCycleTime
     */
    if (args == null || args.length < 1) {
      throw new IllegalArgumentException("补单需要排程名称!");
    }
    
    String scheduleType = args[0];
    String[] inputData = scheduleType.split("#");
//    String serviceName = inputData[0];
    String storeType = inputData[1];
    // String timeType = inputData[2]; //目前無使用到
    // final int beforeHours = Integer.parseInt(inputData[3]); //每次執行, 往回取幾個小時的資料

    // 記錄執行時間
    Date lastRunTime = new Date();
    // 取得該平台的所有店鋪
    List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(storeType);

//    // 執行 reCycle 的logic.
//    this.doReCycleCheckData(aomsShopList, inputData, lastRunTime);

//  每次執行, 往回取幾個小時的資料
    final int beforeHours = Integer.parseInt(inputData[3]); 

    String[] checkDate = new String[2];
    if (aomsShopList != null && aomsShopList.size() != 0) {
      logger.info("开始--拼多多订单补单---排程类型{}", scheduleType);
      for (int i = 0; i < aomsShopList.size(); i++) {
        AomsshopT aomsshopT = aomsShopList.get(i);
        
        logger.info("当前店铺编号{},排程类型{}", aomsshopT.getAomsshop001(), scheduleType);
        
//      add by mowj 20151118 调整为取当前补单排程启动的时间
        checkDate[1] = DateTimeTool.format(lastRunTime);
        checkDate[0] = DateTimeTool.format(DateTimeTool.getBeforeHours(checkDate[1], beforeHours));

        logger.info("时间范围:{}~{}", checkDate[0], checkDate[1]);
        
        baseCheckUpdateService.saveTaskScheduleConfigIfNotExist(scheduleType);
        // 執行 check;
        baseCheckUpdateService.executeOrderUpdateCheck(
            pddApiSyncOrdersService, scheduleType, aomsshopT, checkDate[0], checkDate[1]);

      }
      // 更新 lastRunTime add by xaiver on 20150906
      baseCheckUpdateService.updateLastRunTime(lastRunTime, scheduleType);
      
      logger.info("结束--拼多多订单补单---排程类型{},上次运行时间{}", scheduleType, DateTimeTool.format(lastRunTime));
      return true;
    } else {
      logger.info("拼多多订单补单失败---没有找到相应的店铺,请确认排程类型{}设定是否正确", scheduleType);
      
      return false;
    }
  }
}
