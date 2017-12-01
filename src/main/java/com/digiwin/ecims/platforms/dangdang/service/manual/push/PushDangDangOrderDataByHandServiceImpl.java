package com.digiwin.ecims.platforms.dangdang.service.manual.push;

import java.io.IOException;
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
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.BasePostService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.CheckColEnum;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.WorkOperateTypeEnum;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;

/**
 * 当当手动拉取对外接口服务类
 * 
 * @author hopelee
 *
 */
@Service("pushDangDangOrderDataByHandServiceImpl")
public class PushDangDangOrderDataByHandServiceImpl implements PushOrderDataByHandService {

  private static final Logger logger =
      LoggerFactory.getLogger(PushDangDangOrderDataByHandServiceImpl.class);

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private BasePostService basePostService;
  
  private static final String SCHEDULE_TYPE = DangdangCommonTool.STORE_NAME + "#"
      + AomsordT.class.getSimpleName() + "#" + DangdangCommonTool.STORE_TYPE;

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
        aomsshopTs = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
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
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByCreateDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByCreateDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
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
        aomsshopTs = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
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
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByModifyDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByModifyDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
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
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByOrderId",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByOrderId",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
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
        aomsshopTs = aomsShopService.getStoreByStoreType(DangdangCommonTool.STORE_TYPE);
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
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByPayDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "IOException", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
      e.printStackTrace();
      logger.error("IOException = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "IOException:" + e.getMessage());
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "pushDangDangOrderDataByHandServiceImpl#pushOrderDataByPayDate",
          LogInfoBizTypeEnum.MANUAL_PUSH.getValueString(),
          DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()), "Exception", e.getMessage(),
          taskScheduleConfig.getScheduleType(), "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean(false, "Exception:" + e.getMessage());
    }

    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String pushOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    return null;
  }

  @Override
  public Long findOrderDataCountFromDBByCreateDate(String storeId, String startDate,
      String endDate) {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("pojo", "AomsordT");
    params.put("storeType", DangdangCommonTool.STORE_TYPE);// 057
    params.put("storeId", storeId);
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("checkCol", "aoms006");// 依照更新時間則填modified
    long count = taskService.getSelectPojoCount(params);

    return count;
  }

  @Override
  public <T> Long findOrderDataCountFromDBByCreateDate(HashMap<String, String> params,
      Class<T> clazz) {
    return null;
  }

  @Override
  public Long findOrderDataCountFromDbByModifyDate(String storeId, String startDate,
      String endDate) {
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("pojo", "AomsordT");
    params.put("storeType", DangdangCommonTool.STORE_TYPE);// 057
    params.put("storeId", storeId);
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("checkCol", "modified");// 依照更新時間則填modified
    long count = taskService.getSelectPojoCount(params);

    return count;
  }
}
