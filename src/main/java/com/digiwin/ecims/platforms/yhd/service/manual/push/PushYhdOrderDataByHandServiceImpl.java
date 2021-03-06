package com.digiwin.ecims.platforms.yhd.service.manual.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

/**
 * yhd手动拉取对外接口服务类
 * 
 * @author hopelee
 *
 */
@Service("pushYhdOrderDataByHandServiceImpl")
public class PushYhdOrderDataByHandServiceImpl implements PushOrderDataByHandService {

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Autowired
  private BasePostService basePostService;
  
  private static final Logger logger =
      LoggerFactory.getLogger(PushYhdOrderDataByHandServiceImpl.class);

  private static final String SCHEDULE_TYPE = "Yhd#AomsordT#2";

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
        aomsshopTs = aomsShopService.getStoreByStoreType(YhdCommonTool.STORE_TYPE);
      }
      for (AomsshopT tempShopT : aomsshopTs) {
        long tempCount = basePostService.timeOutExecute(taskScheduleConfig, AomsordT.class,
            WorkOperateTypeEnum.IS_MANUALLY, CheckColEnum.IS_MANUALLY_ORDER_BY_CREATE_DATE,
            startDate, endDate, tempShopT.getAomsshop001());
        totalIdCount += tempCount;
      }

      syncResOrderHandBean = new SyncResOrderHandBean(true, totalIdCount + "");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataByCreateDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataByCreateDate",
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
        aomsshopTs = aomsShopService.getStoreByStoreType(YhdCommonTool.STORE_TYPE);
      }
      for (AomsshopT tempShopT : aomsshopTs) {
        long tempCount = basePostService.timeOutExecute(taskScheduleConfig, AomsordT.class,
            WorkOperateTypeEnum.IS_MANUALLY, CheckColEnum.IS_MANUALLY_ORDER_BY_MODIFY_DATE,
            startDate, endDate, tempShopT.getAomsshop001());
        totalIdCount += tempCount;
      }

      syncResOrderHandBean = new SyncResOrderHandBean(true, totalIdCount + "");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataByModifyDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataByModifyDate",
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
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      basePostService.timeOutExecuteById(taskScheduleConfig, orderId, AomsordT.class);
      syncResOrderHandBean = new SyncResOrderHandBean(true, "1");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataById",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataById",
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
  public Long findOrderDataCountFromDBByCreateDate(String storeId, String startDate,
      String endDate) {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("pojo", "AomsordT");
    params.put("checkCol", "aoms006");
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("storeType", YhdCommonTool.STORE_TYPE);
    params.put("storeId", storeId);
    Long count = taskService.getSelectPojoCount(params);
    return count;
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
        aomsshopTs = aomsShopService.getStoreByStoreType(YhdCommonTool.STORE_TYPE);
      }
      for (AomsshopT tempShopT : aomsshopTs) {
        long tempCount = basePostService.timeOutExecute(taskScheduleConfig, AomsordT.class,
            WorkOperateTypeEnum.IS_MANUALLY, CheckColEnum.IS_MANUALLY_ORDER_BY_PAY_DATE, startDate,
            endDate, tempShopT.getAomsshop001());
        totalIdCount += tempCount;
      }

      syncResOrderHandBean = new SyncResOrderHandBean(true, totalIdCount + "");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataByPayDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          "AomsordT", "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushYhdOrderDataByHandServiceImpl#pushOrderDataByPayDate",
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
  public <T> Long findOrderDataCountFromDBByCreateDate(HashMap<String, String> params,
      Class<T> clazz) {
    // TODO Auto-generated method stub
    Long totalSize = taskService.getSelectPojoCount(params);
    // 整理搜尋區間，直到區間數目<=最大讀取數目
    // 整理方式，採二分法
    String startDate = params.get("startDate");
    String endDate = params.get("endDate");
    while (totalSize > Integer
        .parseInt(params.get("readMaxRow") == null ? "8000" : params.get("readMaxRow"))) {
      // 二分法查找小于maxrow的时间的数据
      endDate = DateTimeTool.format(new Date(
          (DateTimeTool.parse(startDate).getTime() + DateTimeTool.parse(endDate).getTime()) / 2));
      params.put("startDate", startDate);
      totalSize = taskService.getSelectPojoCount(params);
    }
    return totalSize;
  }

  @Override
  public Long findOrderDataCountFromDbByModifyDate(String storeId, String startDate,
      String endDate) {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("pojo", "AomsordT");
    params.put("checkCol", "modified");
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("storeType", YhdCommonTool.STORE_TYPE);
    params.put("storeId", storeId);
    Long count = taskService.getSelectPojoCount(params);
    return count;
  }

}
