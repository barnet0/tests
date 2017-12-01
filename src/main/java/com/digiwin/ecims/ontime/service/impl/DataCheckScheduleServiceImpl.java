package com.digiwin.ecims.ontime.service.impl;

import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.exception.SyncResponseErrorException;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJobForDataCycleRun;
import com.digiwin.ecims.ontime.service.OnTimeTaskCheckBusiJob;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.MySpringContext;

/**
 * 订单补单逻辑.会按淘宝与其他平台区分.
 * 主要流程：获取实现了OnTimeTaskBusiJobForDataCycleRun接口的bean，设置变量后，
 * 调用接口定义的方法：OnTimeTaskBusiJobForDataCycleRun来处理。
 * 
 * 添加新平台补单逻辑方法：
 * 0. 新平台下实现xxxTradeUpdateCheckService,syncxxxOrderDataByHandServiceImpl与puchxxxOrderDataByHandServiceImpl
 * 1. 在StoreType枚举类中添加平台的补单排程名称前缀
 * 2. 在getPreScheduleTypeName中添加对新平台代码的判断
 * 
 * @author 建恩，秉璋，维杰
 * @since 2015.08.28
 */
@Service("dataCheckScheduleServiceImpl")
@Scope("prototype")
public class DataCheckScheduleServiceImpl extends UpdateTask implements OnTimeTaskCheckBusiJob {

  private String scheduleType;

  @Autowired
  private TaskService taskService;

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private OnTimeTaskCheckBusiJob taobaoFxTradeUpdateCheckServiceImpl;

  @Autowired
  private OnTimeTaskCheckBusiJob taobaoTbTradeUpdateCheckServiceImpl;

  private enum StoreType {
//    TAOBAO(""), 
//    JING_DONG("JingdongTradeUpdateCheck#"), 
//    YHD("YhdTradeUpdateCheck#"), 
//    ICBC("IcbcTradeUpdateCheck#"), 
    SUNING("SuningTradeUpdateCheck#"), 
    DANG_DANG("DangdangTradeUpdateCheck#"), 
    ALIEXPRESS("AliexpressTradeUpdateCheck#");

    private String scheduleType;

    private StoreType(String scheduleType) {
      this.scheduleType = scheduleType;
    }

    @Override
    public String toString() {
      return this.scheduleType;
    }
  }

  /**
   * 取得對應的平台
   * 
   * @param storeType
   * @return
   */
  private StoreType getPreScheduleTypeName(String storeType) {
    switch (storeType) {
//      case TaobaoCommonTool.STORE_TYPE:
//        return StoreType.TAOBAO;
//      case JingdongCommonTool.STORE_TYPE:
//        return StoreType.JING_DONG;
//      case YhdCommonTool.STORE_TYPE:
//        return StoreType.YHD;
//      case IcbcCommonTool.STORE_TYPE:
//        return StoreType.ICBC;
      case SuningCommonTool.STORE_TYPE:
        return StoreType.SUNING;
      case DangdangCommonTool.STORE_TYPE:
        return StoreType.DANG_DANG;
      case AliexpressCommonTool.STORE_TYPE:
        return StoreType.ALIEXPRESS;
      default:
        return null;
    }
  }

  /**
   * 更新最後執行時間
   */
  private void updateLastRunTime(Date lastRunTime, String[] inputData) {
    // 檢查該schedule 是否存在 DB, 若無, 則自動新增一筆.
    TaskScheduleConfig tsc = taskService.getTaskScheduleConfigInfo(scheduleType);
    if (StringUtils.isBlank(tsc.getId())) {
      taskService.saveTaskTaskScheduleConfig(tsc);
    }

    // 更新對應的 shopId or platform id 及 runType
    if (StringUtils.isBlank(tsc.getStoreId()) || StringUtils.isBlank(tsc.getRunType())) {
      /*
       * 格式: service#storeType#timeType#reCycleTime ex: taobaoFxTradeUpdateCheckService#0#Hour#1
       * 
       * inputData[0] = serviceName inputData[1] = storeType inputData[2] = timeType inputData[3] =
       * reCycleTime
       */
      String storeType = inputData[1];
      String timeType = inputData[2];
      final int beforeHours = Integer.parseInt(inputData[3]);

      String runType = "RECYCLE-" + beforeHours + timeType;
      taskService.newTransaction4SaveShopIdAndRunType(storeType, storeType, runType);
    }

    // 更新執行時間（lastRunTime）, 一開始進入, 就直接回壓
    taskService.newTransaction4SaveLastRunTime(scheduleType, lastRunTime); // 記錄執行時間, add by xavier
                                                                           // on 20150830
  }

  @Override
  public boolean timeOutExecute(String... args) throws Exception {

    /*
     * 格式: service#storeType#timeType#reCycleTime ex: taobaoFxTradeUpdateCheckService#0#Hour#1
     * 
     * inputData[0] = serviceName inputData[1] = storeType inputData[2] = timeType inputData[3] =
     * reCycleTime
     */
    String[] inputData = scheduleType.split("#");
    String serviceName = inputData[0];
    String storeType = inputData[1];
    // String timeType = inputData[2]; //目前無使用到
    // final int beforeHours = Integer.parseInt(inputData[3]); //每次執行, 往回取幾個小時的資料

    // 記錄執行時間
    Date lastRunTime = new Date();

    // 取得該平台的所有店鋪
    List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(storeType);

    // 取得對應的平台類型
    StoreType st = this.getPreScheduleTypeName(storeType);

    // 計算時間
    // Date endDate = new Date();
    // String sDate = DateTimeTool.format(DateTimeTool.getBeforeHours(endDate, beforeHours),
    // "yyyy-MM-dd HH:mm:ss");
    // String eDate = DateTimeTool.format(endDate, "yyyy-MM-dd HH:mm:ss");
    // String[] checkDate = { sDate, eDate };


//    if ("0".equals(storeType)) {
//      // taobao 比較特殊, 故獨立操作
//      this.taobaoCheckData(inputData, lastRunTime);
//    } else {
//
//      // 取得對應的 service, taobao 不走這個logic, 直接用 @Autowired 來注入
//      Object obj = MySpringContext.getContext().getBean(serviceName);
//      OnTimeTaskBusiJobForDataCycleRun otjfdcr = (OnTimeTaskBusiJobForDataCycleRun) obj;
//
//      // 查找不到
//      if (obj == null) {
//        return Boolean.FALSE; // 若查無, 直接結束
//      }
//
//      // 執行 reCycle 的logic.
//      this.doReCycleCheckData(otjfdcr, aomsShopList, st, inputData, lastRunTime);
//    } // mark by mowj 20160705

    // 取得對應的 service, taobao 不走這個logic, 直接用 @Autowired 來注入
    Object obj = MySpringContext.getContext().getBean(serviceName);
//    OnTimeTaskBusiJobForDataCycleRun otjfdcr = (OnTimeTaskBusiJobForDataCycleRun) obj;
    OnTimeTaskBusiJobForDataCycleRun checkJob = (OnTimeTaskBusiJobForDataCycleRun) obj;

    // 查找不到
    if (obj == null) {
      return Boolean.FALSE; // 若查無, 直接結束
    }

    // 執行 reCycle 的logic.
    this.doReCycleCheckData(checkJob, aomsShopList, st, inputData, lastRunTime);
    
    return false;
  }

  /**
   * 執行 reCycle 的logic.
   * 
   * @param checkJob
   * @param aomsShopList
   * @param st
   * @param beforeHours
   * @throws Exception
   */
  private void doReCycleCheckData(OnTimeTaskBusiJobForDataCycleRun checkJob,
      List<AomsshopT> aomsShopList, StoreType st, String[] inputData, Date lastRunTime)
          throws Exception {

    /*
     * 格式: service#storeType#timeType#reCycleTime ex: taobaoFxTradeUpdateCheckService#0#Hour#1
     * 
     * inputData[0] = serviceName inputData[1] = storeType inputData[2] = timeType inputData[3] =
     * reCycleTime
     */
    // String serviceName = inputData[0];
    // String storeType = inputData[1];
    // String timeType = inputData[2]; //目前無使用到
    final int beforeHours = Integer.parseInt(inputData[3]); // 每次執行, 往回取幾個小時的資料

    String[] checkDate = new String[2];
    if (aomsShopList != null && aomsShopList.size() != 0) {
      for (int i = 0; i < aomsShopList.size(); i++) {

        checkDate[1] = DateTimeTool.format(lastRunTime); // add by mowj 20151118 调整为取当前补单排程启动的时间
        checkDate[0] = DateTimeTool.format(DateTimeTool.getBeforeHours(checkDate[1], beforeHours));

        // 執行 check;
        checkJob.setCheckDate(checkDate);
        checkJob.setScheduleUpdateCheckType(scheduleType);
        checkJob.executeUpdateCheckForDataCycleRun(aomsShopList.get(i));

      }
    }

    // 更新 lastRunTime add by xaiver on 20150906
    this.updateLastRunTime(lastRunTime, inputData);
  }

  /**
   * 執行 reCycle 的logic.
   * 
   * @param beforeHours
   * @throws SyncResponseErrorException
   * @throws Exception
   */
  private void taobaoCheckData(String[] inputData, Date lastRunTime)
      throws SyncResponseErrorException, Exception {

    /*
     * 格式: service#storeType#timeType#reCycleTime ex: taobaoFxTradeUpdateCheckService#0#Hour#1
     * 
     * inputData[0] = serviceName inputData[1] = storeType inputData[2] = timeType inputData[3] =
     * reCycleTime
     */
    // String serviceName = inputData[0];
    // String storeType = inputData[1];
    // String timeType = inputData[2]; //目前無使用到
    final int beforeHours = Integer.parseInt(inputData[3]); // 每次執行, 往回取幾個小時的資料

    String[] checkDate = new String[2]; // 一般用
    // checkDate[1] =
    // taskService.getTaskScheduleConfigInfo("TaobaoTbTradeUpdateCheck").getLastUpdateTime();
    checkDate[1] = DateTimeTool.format(lastRunTime);
    checkDate[0] = DateTimeTool
        .format(DateTimeTool.getBeforeHours(DateTimeTool.parse(checkDate[1]), beforeHours));
//    taobaoTbTradeUpdateCheckServiceImpl.setCheckDate(checkDate);
//    taobaoTbTradeUpdateCheckServiceImpl.executeUpdateCheckForDataCycleRun(null);

    String[] checkDateForFX = new String[2]; // for 分銷用
    checkDateForFX[1] =
        taskService.getTaskScheduleConfigInfo("TaobaoFxTradeUpdateCheck").getLastUpdateTime();
    checkDateForFX[0] = DateTimeTool.format(
        DateTimeTool.getBeforeHours(DateTimeTool.parse(checkDate[1]), beforeHours));
//    taobaoFxTradeUpdateCheckServiceImpl.setCheckDate(checkDateForFX);
//    taobaoFxTradeUpdateCheckServiceImpl.executeUpdateCheckForDataCycleRun(null);

    // 更新 lastRunTime add by xaiver on 20150906
    this.updateLastRunTime(lastRunTime, inputData);
  }


  public void setScheduleType(String scheduleType) {
    /*
     * 格式: service#storeType#timeType#reCycleTime ex: taobaoFxTradeUpdateCheckService#0#Hour#1
     * 
     * inputData[0] = serviceName inputData[1] = storeType inputData[2] = timeType inputData[3] =
     * reCycleTime
     */
    this.scheduleType = scheduleType;
  }

  @Override
  public boolean timeOutExecuteCheck(String... args) throws Exception {
    // TODO Auto-generated method stub
    return false;
  }
}
