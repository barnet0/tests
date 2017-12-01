package com.digiwin.ecims.platforms.jingdong.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.response.afsservice.AfsServiceMessage;
import com.jd.open.api.sdk.response.afsservice.AfsserviceReceivetaskGetResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.jingdong.bean.response.afsservice.MyAfsserviceServicedetailListResponse;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiSyncReturnsService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.platforms.jingdong.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class JingdongApiSyncReturnsServiceImpl implements JingdongApiSyncReturnsService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private JingdongApiService jingdongApiService;
  
  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncJingdongAfss(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    throw new UnsupportedOperationException("not implemented!");
  }

  @Override
  public Boolean syncJingdongAfss(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws JdException, IOException {
    // 设定参数
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    Date lastUpdateDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    int pageSize = taskScheduleConfig.getMaxPageSize();
    int pageNo = 1;

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 取得時間區間內總資料筆數
    int totalSize = 0;
    /*
     * modi by mowj 20151123.京东服务单API十分不稳定，会出现在翻页的过程中不返回数据的情况。 
     * 而且，还有可能在第一页的时候就出现这种情况,所以要在这里做防呆处理.
     *
     **/
    while (pageNo < 100) {
      AfsserviceReceivetaskGetResponse response = 
          jingdongApiService.jingdongAfsserviceReceivetaskGet(
              appKey, appSecret, accessToken, 
              null, pageNo, JingdongCommonTool.MIN_PAGE_SIZE, null, null, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate));
      if (response != null && response.getPublicResultObject() != null
          && response.getPublicResultObject().getResultCode() != null
          && response.getPublicResultObject().getResultCode() == 100
          && response.getPublicResultObject().getWaitReceiveAfsService() != null) {
        totalSize = response.getPublicResultObject().getWaitReceiveAfsService().getTotalCount();
        break;
      } else {
        if (pageNo >= 1) {
          pageNo++;
        }
      }
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      if (totalSize == 0) {
        return false; // 區間內沒有資料, 故也不執行.
      }
    } else if (totalSize == 0) { // 區間內沒有資料
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return false;
      }
    }

    while (totalSize > taskScheduleConfig.getMaxReadRow()) {
      // eDate變為sDate與eDate的中間時間
      eDate = DateTimeTool.format(new Date(
          (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));

      AfsserviceReceivetaskGetResponse responseT =
          jingdongApiService.jingdongAfsserviceReceivetaskGet(
              appKey, appSecret, accessToken, 
              null, pageNo, JingdongCommonTool.MIN_PAGE_SIZE, null, null, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate));
      if (responseT != null && responseT.getPublicResultObject() != null
          && responseT.getPublicResultObject().getResultCode() != null
          && responseT.getPublicResultObject().getResultCode() == 100
          && responseT.getPublicResultObject().getWaitReceiveAfsService() != null) {
        totalSize = responseT.getPublicResultObject().getWaitReceiveAfsService().getTotalCount();
      }
    }

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    for (int i = pageNum; i > 0; i--) {
      AfsserviceReceivetaskGetResponse res =
          jingdongApiService.jingdongAfsserviceReceivetaskGet(
              appKey, appSecret, accessToken, 
              null, i, pageSize, null, null, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate));

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, 
          JingdongCommonTool.STORE_TYPE,
          "jingdong.afsservice.receivetask.get 获取待收货服务单信息",
          JsonUtil.formatByMilliSecond(res),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());
      
      // add by mowj 20151114 京东自主售后API在翻页过程中出现无资料返回的情况。
      // 设定pagesize=1，并且判断resultcode来避免程序出错，以减少服务单的丢失数量
      if (res.getPublicResultObject().getResultCode() != 100) {
        continue;
      }
      // 沒有一頭多身 都是單筆單筆
      for (AfsServiceMessage message : res.getPublicResultObject().getWaitReceiveAfsService()
          .getResult()) {

        // 獲取該服務單詳情
        // mark by mowj 20160612
//        AfsserviceServicedetailListResponse serviceDetailList =
//            jingdongAfsserviceServiceDetailList(aomsshopT, message.getAfsServiceId());
        
        // add by mowj 20160612
        MyAfsserviceServicedetailListResponse serviceDetailList =
            jingdongApiService.myJingdongAfsserviceServicedetailList(
                appKey, appSecret, accessToken, 
                (long)message.getAfsServiceId());

        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, 
            JingdongCommonTool.STORE_TYPE,
            "jingdong.afsservice.servicedetail.list 获取服务单详情",
            JsonUtil.formatByMilliSecond(res),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        AomsrefundT aomsrefundT = new AomsrefundTTranslator(message, storeId)
            .doTranslate(serviceDetailList);
        aomsrefundTs.add(aomsrefundT);

        if (message.getAfsApplyTime().after(lastUpdateDate)) {
          lastUpdateDate = message.getAfsApplyTime();
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
      // 清空列表，为下一页资料作准备
      aomsrefundTs.clear();
    }

 // 重新取得區間內資料筆數
    logger.info("更新{}的lastUpdateDate为{}...", taskScheduleConfig.getScheduleType(), lastUpdateDate);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateDate));
    
    return true;
  }

  @Override
  public Boolean syncRefunds(String startDate, String endDate, String storeId) throws Exception {
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    int pageSize = JingdongCommonTool.MIN_PAGE_SIZE;
    
    String appKey = esv.getAppKey();
    String appSecret = esv.getAppSecret();
    String accessToken = esv.getAccessToken();
    
    // 取得時間區間內總資料筆數
    int totalSize = 0;
    int pageNo = 1;
    /*
     * modi by mowj 20151123.京东服务单API十分不稳定，会出现在翻页的过程中不返回数据的情况。 
     * 而且，还有可能在第一页的时候就出现这种情况,所以要在这里做防呆处理.
     *
     **/
    while (pageNo < 100) {
      AfsserviceReceivetaskGetResponse response = 
          jingdongApiService.jingdongAfsserviceReceivetaskGet(
              appKey, appSecret, accessToken, 
              null, pageNo, JingdongCommonTool.MIN_PAGE_SIZE, null, null, 
              DateTimeTool.parse(startDate), DateTimeTool.parse(endDate));
      if (response != null && response.getPublicResultObject() != null
          && response.getPublicResultObject().getResultCode() != null
          && response.getPublicResultObject().getResultCode() == 100
          && response.getPublicResultObject().getWaitReceiveAfsService() != null) {
        totalSize = response.getPublicResultObject().getWaitReceiveAfsService().getTotalCount();
        break;
      } else {
        if (pageNo >= 1) {
          pageNo++;
        }
      }
    }

    if (totalSize == 0) {
      return false; // 區間內沒有資料, 故也不執行.
    }

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    for (int i = pageNum; i > 0; i--) {
      AfsserviceReceivetaskGetResponse res =
          jingdongApiService.jingdongAfsserviceReceivetaskGet(
              appKey, appSecret, accessToken, 
              null, i, pageSize, null, null, 
              DateTimeTool.parse(startDate), DateTimeTool.parse(endDate));

      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, 
          JingdongCommonTool.STORE_TYPE,
          "jingdong.afsservice.receivetask.get 获取待收货服务单信息",
          JsonUtil.formatByMilliSecond(res),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          null);
      
      // add by mowj 20151114 京东自主售后API在翻页过程中出现无资料返回的情况。
      // 设定pagesize=1，并且判断resultcode来避免程序出错，以减少服务单的丢失数量
      if (res.getPublicResultObject().getResultCode() != 100) {
        continue;
      }
      // 沒有一頭多身 都是單筆單筆
      for (AfsServiceMessage message : res.getPublicResultObject().getWaitReceiveAfsService()
          .getResult()) {

        // add by mowj 20160612
        MyAfsserviceServicedetailListResponse serviceDetailList =
            jingdongApiService.myJingdongAfsserviceServicedetailList(
                appKey, appSecret, accessToken, 
                (long)message.getAfsServiceId());

        loginfoOperateService.newTransaction4SaveSource(startDate, endDate, 
            JingdongCommonTool.STORE_TYPE,
            "jingdong.afsservice.servicedetail.list 获取服务单详情",
            JsonUtil.formatByMilliSecond(res),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            null);

        AomsrefundT aomsrefundT = new AomsrefundTTranslator(message, storeId)
            .doTranslate(serviceDetailList);
        aomsrefundTs.add(aomsrefundT);

      }
      taskService.newTransaction4Save(aomsrefundTs);
      // 清空列表，为下一页资料作准备
      aomsrefundTs.clear();
    }

    return true;
  }

  @Override
  public Long getCreatedCount(String appKey, String appSecret, String accessToken, String startDate,
      String endDate) throws Exception {
    long totalSize = 0L;
    AfsserviceReceivetaskGetResponse response = 
        jingdongApiService.jingdongAfsserviceReceivetaskGet(
            appKey, appSecret, accessToken, 
            null, JingdongCommonTool.MIN_PAGE_NO, JingdongCommonTool.MIN_PAGE_SIZE, null, null, 
            DateTimeTool.parse(startDate), DateTimeTool.parse(endDate));
    if (response != null && response.getPublicResultObject() != null
        && response.getPublicResultObject().getResultCode() != null
        && response.getPublicResultObject().getResultCode() == 100
        && response.getPublicResultObject().getWaitReceiveAfsService() != null) {
      totalSize = response.getPublicResultObject().getWaitReceiveAfsService().getTotalCount();
    }
    return totalSize;
  }
  
}
