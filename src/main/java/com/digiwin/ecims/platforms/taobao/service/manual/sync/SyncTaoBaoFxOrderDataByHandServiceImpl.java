package com.digiwin.ecims.platforms.taobao.service.manual.sync;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;
import com.digiwin.ecims.platforms.taobao.service.api.order.TaobaoFxApiSyncOrdersService;

/**
 * 淘宝分销手动拉取对外接口服务类
 * 
 * @author hopelee
 *
 */
@Service("syncTaoBaoFxOrderDataByHandServiceImpl")
public class SyncTaoBaoFxOrderDataByHandServiceImpl extends UpdateTask
    implements SyncOrderDataByHandService {
  
  private static final Logger logger =
      LoggerFactory.getLogger(SyncTaoBaoFxOrderDataByHandServiceImpl.class);
  
  private static final String CLASS_NAME = "SyncTaoBaoFxOrderDataByHandServiceImpl";

  @Autowired
  private TaobaoFxApiSyncOrdersService taobaoFxApiSyncOrdersService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Override
  public String syncOrderDataByCreateDate(String storeId, String startDate, String endDate) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    long totalSize = 0L;
    try {
      totalSize = taobaoFxApiSyncOrdersService.syncOrdersByCreated(startDate, endDate, storeId, null);
    } catch (ApiException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ApiException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("ApiException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("ApiException：淘寶API錯誤。");
    } catch (ClientProtocolException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ClientProtocolException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("ClientProtocolException：連線錯誤。");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ParseException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("IOException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("IOException" + "JSON轉換錯誤。");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_CREATE_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "获取数据异常", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("Exception" + "其他錯誤");
    }
    syncResOrderHandBean = new SyncResOrderHandBean(true, totalSize + "");
    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String syncOrderDataByModifyDate(String storeId, String startDate, String endDate) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    long totalSize = 0L;
    try {
      totalSize = taobaoFxApiSyncOrdersService.syncOrdersByIncremental(startDate, endDate, storeId, null);
    } catch (ApiException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "syncTaoBaoTbOrderDataByHandServiceImpl#syncOrderDataByModifyDate", LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ApiException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("ApiException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("ApiException" + "淘寶API錯誤。");
    } catch (ClientProtocolException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ClientProtocolException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("ClientProtocolException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("ClientProtocolException" + "連線錯誤。");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ParseException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("ParseException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("ParseException" + "JSON轉換錯誤。");
    } catch (NullPointerException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "NullPointerException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("NullPointerException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("NullPointerException" + "查無資料。");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "获取数据异常", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("Exception" + "其他錯誤");
    }
    syncResOrderHandBean = new SyncResOrderHandBean(true, totalSize + "");
    return JsonUtil.format(syncResOrderHandBean);
  }

  @Override
  public String syncOrderDataByOrderId(String storeId, String orderId) {
    SyncResOrderHandBean syncResOrderHandBean = null;
    try {
      taobaoFxApiSyncOrdersService.syncOrderByOrderId(storeId, orderId);
    } catch (ApiException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ApiException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("ApiException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("ApiException" + "淘寶資料轉換錯誤。");
    } catch (ClientProtocolException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "ClientProtocolException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("ClientProtocolException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("ClientProtocolException" + "連線錯誤。");
    } catch (IOException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "IOException", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("IOException = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("IOException" + "JSON轉換錯誤。");
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          CLASS_NAME+ "#" + SYNC_BY_MODIFY_DATE, LogInfoBizTypeEnum.MANUAL_SYNC.getValueString(),
          new Date(), "获取数据异常", e.getMessage(), "", "");
      e.printStackTrace();
      logger.error("Exception = " + e.getMessage());
      syncResOrderHandBean = new SyncResOrderHandBean("Exception" + "其他錯誤。");
    }
    syncResOrderHandBean = new SyncResOrderHandBean(true, "1");
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
