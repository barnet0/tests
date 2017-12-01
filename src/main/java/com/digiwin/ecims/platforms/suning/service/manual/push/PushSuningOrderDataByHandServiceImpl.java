package com.digiwin.ecims.platforms.suning.service.manual.push;

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
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.CheckColEnum;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.WorkOperateTypeEnum;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;

/**
 * suning手动拉取对外接口服务类
 * 
 * @author Xavier
 *
 */
@Service("pushSuningOrderDataByHandServiceImpl")
public class PushSuningOrderDataByHandServiceImpl implements PushOrderDataByHandService {

  private static final Logger logger =
      LoggerFactory.getLogger(PushSuningOrderDataByHandServiceImpl.class);

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private BasePostService basePostService;
  
  private static final String SCHEDULE_TYPE = SuningCommonTool.STORE_NAME + "#"
      + AomsordT.class.getSimpleName() + "#" + SuningCommonTool.STORE_TYPE;

  @Override
  public String pushOrderDataByCreateDate(String storeId, String startDate, String endDate) {
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
        aomsshopTs = aomsShopService.getStoreByStoreType(SuningCommonTool.STORE_TYPE);
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
          "pushSuningOrderDataByHandServiceImpl#pushOrderDataByCreateDate",
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
        aomsshopTs = aomsShopService.getStoreByStoreType(SuningCommonTool.STORE_TYPE);
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
          "pushSuningOrderDataByHandServiceImpl#pushOrderDataByModifyDate",
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
    TaskScheduleConfig taskScheduleConfig = taskService.getTaskScheduleConfigInfo(SCHEDULE_TYPE);
    SyncResOrderHandBean res = null;
    try {
      basePostService.timeOutExecuteById(taskScheduleConfig, orderId, AomsordT.class);
      res = new SyncResOrderHandBean(true, "1");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushSuningOrderDataByHandServiceImpl#pushOrderDataById",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      res = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
    }

    return JsonUtil.format(res);
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
        aomsshopTs = aomsShopService.getStoreByStoreType(SuningCommonTool.STORE_TYPE);
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
          "pushSuningOrderDataByHandServiceImpl#pushOrderDataByPayDate",
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
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("pojo", AomsordT.class.getSimpleName());
    params.put("checkCol", "aoms006");
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("storeType", SuningCommonTool.STORE_TYPE);
    params.put("storeId", storeId);
    Long count = taskService.getSelectPojoCount(params);
    return count;
  }

  @Override
  public <T> Long findOrderDataCountFromDBByCreateDate(HashMap<String, String> params,
      Class<T> clazz) {

    List<T> dataList;
    try {
      dataList = taskService.getSelectPojos(params, clazz, WorkOperateTypeEnum.IS_MANUALLY);
      return new Long(dataList.size());
    } catch (NoSuchFieldException | SecurityException e) {
      // loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
      // "pushSuningOrderDataByHandServiceImpl#findOrderDataCountFromDBByCreateDate",
      // LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
      // DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime(), "yyyy-MM-dd HH:mm:ss"),
      // "Exception", e.getMessage(),
      // "AomsordT", "");
      e.printStackTrace();
      // logger.error("Exception = {}", e.getMessage());
      // res = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
      return 0l;
    }

  }

  @Override
  public Long findOrderDataCountFromDbByModifyDate(String storeId, String startDate,
      String endDate) {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("pojo", AomsordT.class.getSimpleName());
    params.put("checkCol", "modified");
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("storeType", SuningCommonTool.STORE_TYPE);
    params.put("storeId", storeId);
    Long count = taskService.getSelectPojoCount(params);
    return count;
  }

}
