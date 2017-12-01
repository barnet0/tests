package com.digiwin.ecims.ontime.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.api.EcImsApiSyncOrdersService;
import com.digiwin.ecims.core.api.EcImsApiSyncRefundsService;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.BaseUpdateCheckService;
import com.digiwin.ecims.ontime.service.CheckUpdateService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.CheckColEnum;

@Service
public class BaseUpdateCheckServiceImpl implements BaseUpdateCheckService {

  private static final Logger logger =
      LoggerFactory.getLogger(BaseUpdateCheckServiceImpl.class);

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private CheckUpdateService checkUpdateService;

//  @Autowired
//  private AomsShopService aomsShopService;

//  @Override
//  public boolean timeOutExecuteCheck(String... args) throws Exception {
//    /*
//     * 格式:
//     * 排程名称#平台编号#时间类型#检验时间范围 
//     * service#storeType#timeType#reCycleTime 
//     * ex: taobaoTbTradeUpdateCheck#0#Hour#1，表示淘宝普通订单，检验前一小时至当前时间的订单
//     * 
//     * inputData[0] = serviceName 
//     * inputData[1] = storeType 
//     * inputData[2] = timeType 
//     * inputData[3] = reCycleTime
//     */
//    if (args == null || args.length < 1) {
//      throw new IllegalArgumentException("补单需要排程名称!");
//    }
//    
//    String scheduleType = args[0];
//    String[] inputData = scheduleType.split("#");
////    String serviceName = inputData[0];
//    String storeType = inputData[1];
//    // String timeType = inputData[2]; //目前無使用到
//    // final int beforeHours = Integer.parseInt(inputData[3]); //每次執行, 往回取幾個小時的資料
//
//    // 記錄執行時間
//    Date lastRunTime = new Date();
//    // 取得該平台的所有店鋪
//    List<AomsshopT> aomsShopList = aomsShopService.getShopDataByEcType(storeType);
//
////    // 執行 reCycle 的logic.
////    this.doReCycleCheckData(aomsShopList, inputData, lastRunTime);
//
////  每次執行, 往回取幾個小時的資料
//    final int beforeHours = Integer.parseInt(inputData[3]); 
//
//    String[] checkDate = new String[2];
//    if (aomsShopList != null && aomsShopList.size() != 0) {
//      for (int i = 0; i < aomsShopList.size(); i++) {
//        AomsshopT aomsshopT = aomsShopList.get(i);
//        
////      add by mowj 20151118 调整为取当前补单排程启动的时间
//        checkDate[1] = DateTimeTool.format(lastRunTime);
//        checkDate[0] = DateTimeTool.format(DateTimeTool.getBeforeHours(checkDate[1], beforeHours));
//
//        // 執行 check;
//        executeUpdateCheckForDataCycleRun(aomsshopT, checkDate[0], checkDate[1]);
//      }
//    }
//
//    // 更新 lastRunTime add by xaiver on 20150906
//    this.updateLastRunTime(lastRunTime, inputData);
//
//    return true;
//  }

  
  
  
  
  
  
  
  
  
  
  
  @Override
  public void executeOrderUpdateCheck(EcImsApiSyncOrdersService ecImsApiSyncOrdersService, 
      String scheduleType,  
      AomsshopT aomsshopT, String startDate, String endDate) 
          throws Exception {
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    String storeId = aomsshopT.getAomsshop001();
    String storeType = aomsshopT.getAomsshop003();
    
    Map<String, String> updateCheckParams = 
        setOrderCheckParams(startDate, endDate, storeType, storeId);
    
    long ecCount =
        ecImsApiSyncOrdersService.getCreatedOrdersCount(appKey, appSecret, accessToken, 
            startDate, endDate);

    long dbCount = taskService.getSelectPojoCount(updateCheckParams);
    
    logger.info("ec数量:{} --- db数量:{}", ecCount, dbCount);
    checkUpdateService.put(startDate, endDate, scheduleType,
        loginfoOperateService.newTransaction4SaveCheckServiceRecord(this.getClass().getSimpleName(),
            startDate, endDate, ecCount - dbCount, storeType, storeId, scheduleType));
    
    if (ecCount != dbCount) {
      ecImsApiSyncOrdersService.syncOrdersByCreated(startDate, endDate, storeId, scheduleType);
    }
    
    // 用來 記錄 Check_Service_ReCall_T 的關聯key. add by xavier on 20150909
    checkUpdateService.remove(startDate, endDate, scheduleType);

  }

  private Map<String, String> setOrderCheckParams(String sDate, String eDate, String storeType, String storeId) {
    Map<String, String> updateCheckParams = new HashMap<String, String>();
    // 設定起始時間
    updateCheckParams.put("startDate", sDate);
    // 設定結束時間
    updateCheckParams.put("endDate", eDate);
    updateCheckParams.put("pojo", AomsordT.BIZ_NAME);
    // 設定比对时间栏位：创建时间
    updateCheckParams.put("checkCol", CheckColEnum.IS_MANUALLY_ORDER_BY_CREATE_DATE.toString());
    updateCheckParams.put("storeType", storeType);

    updateCheckParams.put("storeId", storeId);
    
    return updateCheckParams;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.ontime.service.BaseUpdateCheckService#executeRefundUpdateCheck(com.digiwin.ecims.core.api.EcImsApiSyncRefundsService, java.lang.String, com.digiwin.ecims.core.model.AomsshopT, java.lang.String, java.lang.String)
   */
  @Override
  public void executeRefundUpdateCheck(EcImsApiSyncRefundsService ecImsApiSyncRefundsService,
      String scheduleType, AomsshopT aomsshopT, String startDate, String endDate) throws Exception {
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    String storeId = aomsshopT.getAomsshop001();
    String storeType = aomsshopT.getAomsshop003();
    
    Map<String, String> updateCheckParams = 
        setRefundCheckParams(startDate, endDate, storeType, storeId);
    
    long ecCount =
        ecImsApiSyncRefundsService.getCreatedCount(appKey, appSecret, accessToken, 
            startDate, endDate);

    long dbCount = taskService.getSelectPojoCount(updateCheckParams);
    logger.info("ec数量:{} --- db数量:{}", ecCount, dbCount);
    checkUpdateService.put(startDate, endDate, scheduleType,
        loginfoOperateService.newTransaction4SaveCheckServiceRecord(this.getClass().getSimpleName(),
            startDate, endDate, ecCount - dbCount, storeType, storeId, scheduleType));
    
    if (ecCount != dbCount) {
      ecImsApiSyncRefundsService.syncRefunds(startDate, endDate, storeId);
    }
    
    // 用來 記錄 Check_Service_ReCall_T 的關聯key. add by xavier on 20150909
    checkUpdateService.remove(startDate, endDate, scheduleType);
    
  }

  private Map<String, String> setRefundCheckParams(String startDate, String endDate,
      String storeType, String storeId) {
    Map<String, String> updateCheckParams = new HashMap<String, String>();
    // 設定起始時間
    updateCheckParams.put("startDate", startDate);
    // 設定結束時間
    updateCheckParams.put("endDate", endDate);
    updateCheckParams.put("pojo", AomsrefundT.BIZ_NAME);
    // 設定比对时间栏位：创建时间
    updateCheckParams.put("checkCol", CheckColEnum.IS_MANUALLY_REFUND_BY_CREATE_DATE.toString());
    updateCheckParams.put("storeType", storeType);
    updateCheckParams.put("storeId", storeId);

    return updateCheckParams;
  }

  /**
   * 更新最後執行時間
   */
  @Override
  public void updateLastRunTime(Date lastRunTime, String scheduleType) {
    //  記錄執行時間, add by xavier on 20150830
    taskService.newTransaction4SaveLastRunTime(scheduleType, lastRunTime); 
  }

  @Override
  public void saveTaskScheduleConfigIfNotExist (String scheduleType){
 // 檢查該schedule 是否存在 DB, 若無, 則自動新增一筆.
    TaskScheduleConfig tsc = taskService.getTaskScheduleConfigInfo(scheduleType);
    if (StringTool.isEmpty(tsc.getId())) {
      taskService.saveTaskTaskScheduleConfig(tsc);
    }

  }
}
