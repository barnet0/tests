package com.digiwin.ecims.platforms.jingdong.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.refundapply.RefundApplySoaService.RefundApplyVo;
import com.jd.open.api.sdk.response.refundapply.PopAfsSoaRefundapplyQueryPageListResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiSyncRefundsService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.platforms.jingdong.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class JingdongApiSyncRefundsServiceImpl implements JingdongApiSyncRefundsService {

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
    syncRefunds(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws JdException, IOException {
    // 参数设定
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    Date lastUpdateDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 取的状态改为商家审核的状态，比原来的“京东财务审核通过”能更早看到退款单
    long status = 1L;
    
    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    boolean sizeMoreThanSetting = false;
    do {
      totalSize = jingdongApiService.jingdongRefundApplyQueryPageList(
          appKey, appSecret, accessToken, 
          null, status, null, null, null, null, null, sDate, eDate, 
          JingdongCommonTool.MIN_PAGE_NO, JingdongCommonTool.MIN_PAGE_SIZE).getQueryResult().getTotalCount();
      
      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);
    
    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程. add by xavier on
    // 20150829
    if (taskScheduleConfig.isReCycle()) {
      if (totalSize == 0) {
        return true; // 區間內沒有資料, 故也不執行.
      }
    } else if (totalSize == 0) {
      // 區間內沒有資料
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return true;
      }
    }

    // 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    for (long i = pageNum; i > 0; i--) {
      PopAfsSoaRefundapplyQueryPageListResponse response =
          jingdongApiService.jingdongRefundApplyQueryPageList(
              appKey, appSecret, accessToken, 
              null, status, null, null, null, null, null, sDate, eDate, 
              (int)i, pageSize);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, JingdongCommonTool.STORE_TYPE,
          "jingdong.pop.afs.soa.refundapply.queryPageList 退款审核单列表查询",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      List<AomsrefundT> list =
          new AomsrefundTTranslator(response, storeId).doTranslate();
      aomsrefundTs.addAll(list);

      for (RefundApplyVo refund : response.getQueryResult().getResult()) {
        if (DateTimeTool.parse(refund.getApplyTime()).after(lastUpdateDate)) {
          lastUpdateDate = DateTimeTool.parse(refund.getApplyTime());
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
      // 清空列表，为下一页资料作准备
      aomsrefundTs.clear();
    }

    logger.info("更新{}的lastUpdateDate为{}...", taskScheduleConfig.getScheduleType(), lastUpdateDate);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateDate));
    
    return true;
  }

  @Override
  public Boolean syncRefunds(String startDate, String endDate, String storeId) throws Exception {
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    int pageSize = JingdongCommonTool.DEFAULT_PAGE_SIZE;
    
    String appKey = esv.getAppKey();
    String appSecret = esv.getAppSecret();
    String accessToken = esv.getAccessToken();
    
    // 取的状态改为商家审核的状态，比原来的“京东财务审核通过”能更早看到退款单
    long status = 1L;
    
    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    totalSize = jingdongApiService.jingdongRefundApplyQueryPageList(
        appKey, appSecret, accessToken, 
        null, status, null, null, null, null, null, startDate, endDate, 
        JingdongCommonTool.MIN_PAGE_NO, JingdongCommonTool.MIN_PAGE_SIZE).getQueryResult().getTotalCount();
    
    if (totalSize == 0) {
      return false; // 區間內沒有資料, 故也不執行.
    }

    // 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    for (long i = pageNum; i > 0; i--) {
      PopAfsSoaRefundapplyQueryPageListResponse response =
          jingdongApiService.jingdongRefundApplyQueryPageList(
              appKey, appSecret, accessToken, 
              null, status, null, null, null, null, null, startDate, endDate, 
              (int)i, pageSize);

      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, 
          JingdongCommonTool.STORE_TYPE,
          "jingdong.pop.afs.soa.refundapply.queryPageList 退款审核单列表查询",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          null);

      List<AomsrefundT> list =
          new AomsrefundTTranslator(response, storeId).doTranslate();
      aomsrefundTs.addAll(list);

      taskService.newTransaction4Save(aomsrefundTs);
      // 清空列表，为下一页资料作准备
      aomsrefundTs.clear();
    }

    return true;
  }

  @Override
  public Long getCreatedCount(String appKey, String appSecret, String accessToken, String startDate,
      String endDate) throws Exception {
    // 取的状态改为商家审核的状态，比原来的“京东财务审核通过”能更早看到退款单
    long status = 1L;
    
    long totalSize = jingdongApiService.jingdongRefundApplyQueryPageList(
        appKey, appSecret, accessToken, 
        null, status, null, null, null, null, null, startDate, endDate, 
        JingdongCommonTool.MIN_PAGE_NO, JingdongCommonTool.MIN_PAGE_SIZE)
        .getQueryResult().getTotalCount();
    
    return totalSize;
  }

}
