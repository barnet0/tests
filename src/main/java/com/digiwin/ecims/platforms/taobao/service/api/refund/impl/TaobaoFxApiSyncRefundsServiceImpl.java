package com.digiwin.ecims.platforms.taobao.service.api.refund.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.RefundDetail;
import com.taobao.api.response.FenxiaoRefundGetResponse;
import com.taobao.api.response.FenxiaoRefundQueryResponse;

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
import com.digiwin.ecims.platforms.taobao.service.api.refund.TaobaoFxApiSyncRefundsService;
import com.digiwin.ecims.platforms.taobao.service.translator.refund.TaobaoJdpFxRefundService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoFxApiSyncRefundsServiceImpl implements TaobaoFxApiSyncRefundsService {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Autowired
  private TaobaoJdpFxRefundService taobaoJdpFxRefund;
  
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
    FenxiaoRefundQueryResponse fxRefundQueryResponse = 
        taobaoApiService.taobaoFenxiaoRefundQuery(
            appKey, appSecret, accessToken, 
            DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
            TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize);
    long totalSize = fxRefundQueryResponse.getTotalResults();

    // 整理搜尋區間，直到區間數目<=最大讀取數目
    // 整理方式，採二分法
    while (totalSize > taskScheduleConfig.getMaxReadRow()) {
      // eDate變為sDate與eDate的中間時間
      eDate = DateTimeTool.format(new Date(
          (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      fxRefundQueryResponse = 
          taobaoApiService.taobaoFenxiaoRefundQuery(
              appKey, appSecret, accessToken, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize);
      totalSize = fxRefundQueryResponse.getTotalResults() == null ? 
          0L : fxRefundQueryResponse.getTotalResults();
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
    // 針對每一頁(正序)的所有資料新增
    for (long i = 1; i <= pageNum; i++) {
      FenxiaoRefundQueryResponse response =
          taobaoApiService.taobaoFenxiaoRefundQuery(
              appKey, appSecret, accessToken, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              (long)i, (long)pageSize);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, TaobaoCommonTool.STORE_TYPE_FX,
          "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.fenxiao.refund.query 批量查询采购退款",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (RefundDetail refundDetail : response.getRefundList()) {
        FenxiaoRefundGetResponse fenxiaoRefundGetResponse = 
            taobaoApiService.taobaoFenxiaoRefundGet(
                appKey, appSecret, accessToken, refundDetail.getSubOrderId());

        loginfoOperateService.newTransaction4SaveSource(
            sDate, eDate, TaobaoCommonTool.STORE_TYPE_FX,
            "taobao.fenxiao.refund.get 查询采购单退款信息",
            JsonUtil.format(fenxiaoRefundGetResponse),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        
        List<AomsrefundT> list = taobaoJdpFxRefund
            .parseResponseToAomsrefundT(fenxiaoRefundGetResponse, storeId);
        aomsrefundTs.addAll(list);

        // 比較區間資料時間，取最大時間
        if (refundDetail.getModified().after(lastUpdateTime)) {
          lastUpdateTime = refundDetail.getModified();
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
      // 清空列表，为下一页资料作准备
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
