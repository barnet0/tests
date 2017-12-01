package com.digiwin.ecims.platforms.taobao.service.api.refund.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Refund;
import com.taobao.api.response.RefundGetResponse;
import com.taobao.api.response.RefundsReceiveGetResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.api.refund.TaobaoTbApiSyncRefundsService;
import com.digiwin.ecims.platforms.taobao.service.translator.refund.TaobaoJdpTbRefundService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoTbApiSyncRefundsServiceImpl implements TaobaoTbApiSyncRefundsService {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Autowired
  private TaobaoJdpTbRefundService taobaoJdpTbRefund;
  
  @Autowired
  private TaobaoApiService taobaoApiService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncRefunds(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws ApiException, IOException {
    // 參數設定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();
    
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    // 取得時間區間內總資料筆數
    RefundsReceiveGetResponse refundsResponse = 
        taobaoApiService.taobaoRefundsReceiveGet(
            appKey, appSecret, accessToken, 
            TaobaoCommonTool.API_REFUNDS_MIN_FIELDS, 
            DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
            TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize, Boolean.FALSE);
    long totalSize = refundsResponse.getTotalResults();

    // 整理搜尋區間，直到區間數目<=最大讀取數目
    // 整理方式，採二分法
    while (totalSize > taskScheduleConfig.getMaxReadRow()) {
      // eDate變為sDate與eDate的中間時間
      eDate = DateTimeTool.format(new Date(
          (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      refundsResponse = 
          taobaoApiService.taobaoRefundsReceiveGet(
              appKey, appSecret, accessToken, 
              TaobaoCommonTool.API_REFUNDS_MIN_FIELDS, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize, Boolean.FALSE);
      totalSize = refundsResponse.getTotalResults() == null ? 
          0L : refundsResponse.getTotalResults();
    }
    
    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else if (totalSize == 0L) {
      // 区间内没有资料 
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return true;
      }
    }

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      RefundsReceiveGetResponse response =
          taobaoApiService.taobaoRefundsReceiveGet(
              appKey, appSecret, accessToken, 
              TaobaoCommonTool.API_REFUNDS_MIN_FIELDS, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              (long)i, (long)pageSize, Boolean.FALSE);

      loginfoOperateService.newTransaction4SaveSource(
          sDate, eDate, 
          TaobaoCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.refunds.receive.get 查询卖家收到的退款列表",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (Refund refund : response.getRefunds()) {
        RefundGetResponse refundGetResponse = 
            taobaoApiService.taobaoRefundGet(
                appKey, appSecret, accessToken, 
                TaobaoCommonTool.API_REFUND_FIELDS, refund.getRefundId());

        loginfoOperateService.newTransaction4SaveSource(
            "N/A", "N/A", 
            TaobaoCommonTool.STORE_TYPE,
            "taobao.refund.get 查询单笔退款详情",
            JsonUtil.format(refundGetResponse),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        if (refundGetResponse.isSuccess()) {
          List<AomsrefundT> list = taobaoJdpTbRefund.parseResponseToAomsrefundT(refundGetResponse, storeId);
          aomsrefundTs.addAll(list);
          
          // 比較區間資料時間，取最大時間
          if (refund.getModified().after(lastUpdateTime)) {
            lastUpdateTime = refund.getModified();
          }
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
      // 为下一次循环清空列表
      aomsrefundTs.clear();
    }

 // 更新updateTime 成為下次的sDate
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateTime));
    
    return true;
  }

  @Override
  public Boolean syncRefunds(String startDate, String endDate, String storeId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long getCreatedCount(String appKey, String appSecret, String accessToken, String startDate,
      String endDate) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
