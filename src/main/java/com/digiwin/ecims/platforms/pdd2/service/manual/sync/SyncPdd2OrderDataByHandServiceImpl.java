package com.digiwin.ecims.platforms.pdd2.service.manual.sync;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncOrdersService;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;

@Service("syncPdd2OrderDataByHandServiceImpl")
public class SyncPdd2OrderDataByHandServiceImpl implements SyncOrderDataByHandService {

  private static final Logger logger =
      LoggerFactory.getLogger(SyncPdd2OrderDataByHandServiceImpl.class);
  
  private static final String CLASS_NAME = "SyncPdd2OrderDataByHandServiceImpl";

  @Autowired
  private Pdd2ApiSyncOrdersService pddApiSyncOrdersService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public String syncOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    long totalSize = 0l;
    try {
      totalSize = pddApiSyncOrdersService.syncOrdersByCreated(startDate, endDate, storeId, null);
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

  @Override
  public String syncOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String syncOrderDataByOrderId(String storeId, String orderId) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      pddApiSyncOrdersService.syncOrderByOrderId(storeId, orderId);
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
    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String syncOrderDataByCondition(int conditionType, String orderId, String startDate,
      String endDate) {
    // TODO Auto-generated method stub
    return null;
  }

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
