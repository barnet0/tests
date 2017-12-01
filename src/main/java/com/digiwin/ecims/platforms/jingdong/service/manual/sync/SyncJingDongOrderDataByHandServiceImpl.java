package com.digiwin.ecims.platforms.jingdong.service.manual.sync;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiSyncOrdersService;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;

/**
 * 京东手动拉取对外接口服务类
 * 
 * @author hopelee
 *
 */
@Service("syncJingDongOrderDataByHandServiceImpl")
public class SyncJingDongOrderDataByHandServiceImpl implements SyncOrderDataByHandService {
  private static final Logger logger =
      LoggerFactory.getLogger(SyncJingDongOrderDataByHandServiceImpl.class);

  private static final String CLASS_NAME = "SyncJingDongOrderDataByHandServiceImpl";

  @Autowired
  private JingdongApiSyncOrdersService jingdongApiSyncOrdersService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  /**
   * 透過創建時間調用批量訂單的API
   */
  @Override
  public String syncOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    long totalSize = 0l;
    try {
      totalSize = jingdongApiSyncOrdersService.syncOrdersByCreated(startDate, endDate, storeId, null);
    } catch (JdException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "JdException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("JdException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("JdException：京东API错误");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "IOException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("IOException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("IOException" + "JSON转换错误");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "获取数据异常", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("Exception" + "其他错误");
    }
    syncResOrderHandBean = new SyncResOrderHandBean(true, totalSize + "");
    return JsonUtil.format(syncResOrderHandBean);
  }

  /**
   * 透過修改時間調用批量訂單的API
   */
  @Override
  public String syncOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    long totalSize = 0l;
    try {
      totalSize = jingdongApiSyncOrdersService.syncOrdersByIncremental(startDate, endDate, storeId, null);
    } catch (JdException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "JdException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("JdException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("JdException：京东API错误");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "IOException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("IOException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("IOException" + "JSON转换错误");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "获取数据异常", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("Exception" + "其他错误");
    }
    syncResOrderHandBean = new SyncResOrderHandBean(true, totalSize + "");
    return JsonUtil.format(syncResOrderHandBean);
  }

  /**
   * 透過ID調用單筆API
   */
  @Override
  public String syncOrderDataByOrderId(String storeId, String orderId) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      jingdongApiSyncOrdersService.syncOrderByOrderId(storeId, orderId);
    } catch (JdException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_ID, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "JdException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("JdException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("JdException" + "京东API错误");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_ID, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "IOException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("IOException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("IOException" + "JSON转换错误");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME + "#" + SYNC_BY_ID, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "获取数据异常", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("Exception" + "其他错误");
    }
    syncResOrderHandBean = new SyncResOrderHandBean(true, "1");
    return JsonUtil.format(syncResOrderHandBean);
  }

  /**
   * 透過API計算以創建時間為主的資料筆數(單頭)
   */
  @Override
  public Long findOrderCountFromEcByCreateDate(String scheduleUpdateCheckType, String storeId,
      String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderCountFromEcByCreateDate(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String syncOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderCountFromEcByModifyDate(String scheduleUpdateCheckType, String storeId,
      String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long findOrderCountFromEcByModifyDate(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) {
    // TODO Auto-generated method stub
    return null;
  }

}
