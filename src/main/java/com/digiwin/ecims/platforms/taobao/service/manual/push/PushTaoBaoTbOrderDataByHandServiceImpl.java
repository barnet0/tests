package com.digiwin.ecims.platforms.taobao.service.manual.push;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.CheckColEnum;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.WorkOperateTypeEnum;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * yhd手动拉取对外接口服务类
 * 
 * @author hopelee
 *
 */
@Service("pushTaoBaoTbOrderDataByHandServiceImpl")
public class PushTaoBaoTbOrderDataByHandServiceImpl implements PushOrderDataByHandService {

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Autowired
  private BasePostService basePostService;
  
  // private String[] scheduleCodes;// [0]=店鋪類型;[1]=推送資料表;[2]=店鋪ID

  private static final Logger logger =
      LoggerFactory.getLogger(PushTaoBaoTbOrderDataByHandServiceImpl.class);

  // private TaskScheduleConfig taskScheduleConfig;

  // private ExecutorService executorService;

  // private SyncResOrderHandBean syncResOrderHandBean;

  private static final String SCHEDULE_TYPE = "TaobaoTb#AomsordT#0";

  @Override
  public String pushOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    // String scheduleType = "TaobaoTb#AomsordT#0";

    // scheduleCodes = scheduleType.split("#");
    //
    // logger.info("開始推送" + scheduleType);
    // taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
    // HashMap<String, String> params = new HashMap<String, String>();
    // params.put("pojo", scheduleCodes[1]);
    // params.put("limit", String.valueOf(taskScheduleConfig.getMaxReadRow()));
    // params.put("storeType", scheduleCodes[2]);// 057
    // params.put("storeId", storeId);
    // params.put("startDate", startDate);
    // params.put("endDate", endDate);
    // params.put("checkCol", "aoms006");// 依照更新時間則填modified
    // long count = taskService.getSelectPojoCount(params);
    // long totalIdCount = count; // modi by mowj 20150805
    // List<AomsordT> dataList = null;
    // try {
    // while (count > 0) {
    // logger.info("本次推送資料數目：" + count);
    // if (count > taskScheduleConfig.getMaxReadRow()) {
    //
    // logger.info("獲取區間資料‧");
    // dataList = taskService.getSelectPojos(params, AomsordT.class);
    // // if (dataList.get(dataList.size() -
    // // 1).getAoms024().equals(params.get("startDate"))) {
    //
    // if (dataList.get(dataList.size() - 1).getAoms006().equals(params.get("startDate"))) {
    // logger.info("本區間資料一秒鐘大於" + taskScheduleConfig.getMaxReadRow());
    // params.put("id", "0");
    // while (count > 0) {
    //
    // dataList = taskService.getSelectPojosById(params, AomsordT.class);
    // doPost(dataList, AomsordT.class);
    // params.put("id", dataList.get(dataList.size() - 1).getId());
    // count = taskService.getSelectPojoCountById(params);
    //
    // }
    // logger.info("本一秒鐘資料處理完畢");
    // params.put("startDate", DateTimeTool.timeAddOneMillisecond(params.get("startDate")));
    //
    // } else {
    // logger.info("本區間資料未在同一秒發生" + taskScheduleConfig.getMaxReadRow() + "筆");
    // doPost(dataList, AomsordT.class);
    // params.put("startDate", dataList.get(dataList.size() - 1).getAoms006());
    // }
    //
    // } else {
    // logger.info("本區間資料未達" + taskScheduleConfig.getMaxReadRow() + "筆");
    // dataList = taskService.getSelectPojos(params, AomsordT.class);
    // doPost(dataList, AomsordT.class);
    // params.put("startDate", dataList.get(dataList.size() - 1).getAoms006());
    // break;
    // }
    //
    // count = taskService.getSelectPojoCount(params);
    // }
    long totalIdCount = 0;
    TaskScheduleConfig taskScheduleConfig = taskService.getTaskScheduleConfigInfo(SCHEDULE_TYPE);
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      AomsshopT aomsshopT = null;
      List<AomsshopT> aomsshopTs = new ArrayList<AomsshopT>();
      if (storeId != null && storeId.length() > 0) {
        aomsshopT = aomsShopService.getStoreByStoreId(storeId);
        aomsshopTs.add(aomsshopT);
      } else {
        aomsshopTs = aomsShopService.getStoreByStoreType(TaobaoCommonTool.STORE_TYPE);
      }
      for (AomsshopT tempShopT : aomsshopTs) {
        long tempCount = basePostService.timeOutExecute(taskScheduleConfig, AomsordT.class,
            WorkOperateTypeEnum.IS_MANUALLY, CheckColEnum.IS_MANUALLY_ORDER_BY_CREATE_DATE,
            startDate, endDate, tempShopT.getAomsshop001());
        totalIdCount += tempCount;
      }

      syncResOrderHandBean = new SyncResOrderHandBean(true, totalIdCount + "");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushTaoBaoTbOrderDataByHandServiceImpl#pushOrderDataByCreateDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
    }

    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String pushOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    long totalIdCount = 0;
    TaskScheduleConfig taskScheduleConfig = taskService.getTaskScheduleConfigInfo(SCHEDULE_TYPE);
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      AomsshopT aomsshopT = null;
      List<AomsshopT> aomsshopTs = new ArrayList<AomsshopT>();
      if (storeId != null && storeId.length() > 0) {
        aomsshopT = aomsShopService.getStoreByStoreId(storeId);
        aomsshopTs.add(aomsshopT);
      } else {
        aomsshopTs = aomsShopService.getStoreByStoreType(TaobaoCommonTool.STORE_TYPE);
      }
      for (AomsshopT tempShopT : aomsshopTs) {
        long tempCount = basePostService.timeOutExecute(taskScheduleConfig, AomsordT.class,
            WorkOperateTypeEnum.IS_MANUALLY, CheckColEnum.IS_MANUALLY_ORDER_BY_MODIFY_DATE,
            startDate, endDate, tempShopT.getAomsshop001());
        totalIdCount += tempCount;
      }

      syncResOrderHandBean = new SyncResOrderHandBean(true, totalIdCount + "");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushTaoBaoTbOrderDataByHandServiceImpl#pushOrderDataByModifyDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
    }
    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String pushOrderDataByOrderId(String storeId, String orderId) {
    // String scheduleType = "TaobaoTb#AomsordT#0";
    //
    // logger.info("開始推送" + scheduleType);
    // taskScheduleConfig = taskService.getTaskScheduleConfigInfo(scheduleType);
    // HashMap<String, String> params = new HashMap<String, String>();
    // params.put("pojo", "AomsordT");
    // params.put("storeId", storeId);
    // params.put("id", orderId);
    //
    // List<AomsordT> dataList = taskService.getSelectPojoById(params, AomsordT.class);
    // try {
    // doPost(dataList, AomsordT.class);
    TaskScheduleConfig taskScheduleConfig = taskService.getTaskScheduleConfigInfo(SCHEDULE_TYPE);
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      basePostService.timeOutExecuteById(taskScheduleConfig, orderId, AomsordT.class);
      syncResOrderHandBean = new SyncResOrderHandBean(true, "1");
    } catch (NullPointerException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushTaoBaoFxOrderDataByHandServiceImpl#pushOrderDataById",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "NullPointerException",
          "查無資料。", "AomsordT", "");
      e.printStackTrace();
      logger.error("NullPointerException = {}", e.getMessage());
      syncResOrderHandBean =
          new SyncResOrderHandBean(false, "NullPointerException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushTaoBaoTbOrderDataByHandServiceImpl#pushOrderDataById",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
    }

    return JsonUtil.format(syncResOrderHandBean);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mercuryecinf.system.service.PushOrderDataByHandService#pushOrderDataByPayDate(java.lang.
   * String, java.lang.String, java.lang.String)
   */
  @Override
  public String pushOrderDataByPayDate(String storeId, String startDate, String endDate) {
    long totalIdCount = 0;
    TaskScheduleConfig taskScheduleConfig = taskService.getTaskScheduleConfigInfo(SCHEDULE_TYPE);
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      AomsshopT aomsshopT = null;
      List<AomsshopT> aomsshopTs = new ArrayList<AomsshopT>();
      if (storeId != null && storeId.length() > 0) {
        aomsshopT = aomsShopService.getStoreByStoreId(storeId);
        aomsshopTs.add(aomsshopT);
      } else {
        aomsshopTs = aomsShopService.getStoreByStoreType(TaobaoCommonTool.STORE_TYPE);
      }
      for (AomsshopT tempShopT : aomsshopTs) {
        long tempCount = basePostService.timeOutExecute(taskScheduleConfig, AomsordT.class,
            WorkOperateTypeEnum.IS_MANUALLY, CheckColEnum.IS_MANUALLY_ORDER_BY_PAY_DATE, startDate,
            endDate, tempShopT.getAomsshop001());
        totalIdCount += tempCount;
      }

      syncResOrderHandBean = new SyncResOrderHandBean(true, totalIdCount + "");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushTaoBaoTbOrderDataByHandServiceImpl#pushOrderDataByPayDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
    }
    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String pushOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderDataCountFromDBByCreateDate(String storeId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <T> Long findOrderDataCountFromDBByCreateDate(HashMap<String, String> params,
      Class<T> clazz) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderDataCountFromDbByModifyDate(String storeId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  // private <T> void doPost(List<T> dataList, Class<T> Clazz) {
  // List<PostRunnable<T>> runnables = createRunnable(dataList, Clazz);
  // logger.info("排定執行續");
  //
  // executorService = Executors.newFixedThreadPool(runnables.size());
  // for (PostRunnable<T> postRunnable : runnables) {
  // executorService.execute(postRunnable);
  // }
  //
  // executorService.shutdown();
  // while (!executorService.isTerminated()) {
  // try {
  // Thread.sleep(500);
  // } catch (InterruptedException e) {
  // e.printStackTrace();
  // }
  // }
  // }
  //
  // private <T> List<PostRunnable<T>> createRunnable(List<T> dataList, Class<T> clazz) {
  // List<PostRunnable<T>> postRunnables = new ArrayList<PostRunnable<T>>();
  // int maxRunnable = taskScheduleConfig.getMaxRunnable();
  // int dateSize = dataList.size();
  // int runnableDataSize = dateSize % maxRunnable == 0 ? dateSize / maxRunnable : dateSize /
  // maxRunnable + 1;
  // for (int i = 0; i < maxRunnable; i++) {
  // PostRunnable<T> postRunnable = new PostRunnable<T>();
  // postRunnable.addPostDatalLists(dataList
  // .subList(i * runnableDataSize, (i + 1) * runnableDataSize > dateSize ? dateSize : (i + 1) *
  // runnableDataSize));
  // postRunnable.setClazz(clazz);
  // postRunnable.setTaskSchedulePostConfig(taskScheduleConfig);
  // postRunnables.add(postRunnable);
  // }
  // return postRunnables;
  //
  // }
}
