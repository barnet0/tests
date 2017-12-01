package com.digiwin.ecims.platforms.dhgate.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhgate.open.client.CompositeResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderDisputeCloseInfo;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderDisputeOpenInfo;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDisputeCloseListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDisputeOpenListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeCloseListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeOpenListResponse;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiService;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiSyncRefundsService;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.platforms.dhgate.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class DhgateApiSyncRefundsServiceImpl implements DhgateApiSyncRefundsService {

  private static final Logger logger = LoggerFactory.getLogger(DhgateApiSyncRefundsServiceImpl.class);

  @Autowired
  private DhgateApiService dhgateApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncRefunds(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // 先获取纠纷开启订单列表。因为纠纷开启表示产生纠纷，需要先下载。
    // 但是此接口没有时间作为入参限制，所以不需要更新排程的时间
    syncOrderDisputeOpenList(taskScheduleConfig, aomsshopT);
    // 再获取纠纷关闭订单列表。
    syncOrderDisputeCloseList(taskScheduleConfig, aomsshopT);
    
    return true;

  }

  /**
   * 获取卖家纠纷关闭订单
   * 
   * @param taskScheduleConfig
   * @param aomsshopT
   * @throws Exception
   */
  private void syncOrderDisputeCloseList(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    OrderDisputeCloseListRequest orderDisputeCloseListRequest = new OrderDisputeCloseListRequest();
    orderDisputeCloseListRequest.setPageNo(DhgateCommonTool.MIN_PAGE_NO);
    orderDisputeCloseListRequest.setPageSize(DhgateCommonTool.DEFAULT_PAGE_SIZE);

    CompositeResponse<OrderDisputeCloseListResponse> listResponse = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    do {
      orderDisputeCloseListRequest.setStartDate(sDate);
      orderDisputeCloseListRequest.setEndDate(eDate);

      listResponse = dhgateApiService.dhOrderDisputeCloseList(orderDisputeCloseListRequest,
          aomsshopT.getAomsshop006());
      if (listResponse == null || !listResponse.isSuccessful()
          || listResponse.getSuccessResponse().getStatus() == null) {
        return;
      } else {
        totalSize = listResponse.getSuccessResponse().getCount();
      }

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀
      } else if (totalSize == 0) {
        // 區間內沒有資料
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return;
        }
      }

      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // TODO 需要验证是否是最新的资料在第一页
    for (int i = pageNum; i > 0; i--) {
      List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();

      orderDisputeCloseListRequest.setPageNo(i);

      listResponse = dhgateApiService.dhOrderDisputeCloseList(orderDisputeCloseListRequest,
          aomsshopT.getAomsshop006());
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DhgateCommonTool.STORE_TYPE,
          "dh.order.disputeclose.list$2.0 获取seller纠纷关闭订单列表",
          JsonUtil.format(listResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      List<OrderDisputeCloseInfo> orderDisputeCloseInfos =
          listResponse.getSuccessResponse().getOrderDisputeCloseInfos();

      for (OrderDisputeCloseInfo disputeInfo : orderDisputeCloseInfos) {
        List<AomsrefundT> list =
            new AomsrefundTTranslator(disputeInfo).doTranslate(aomsshopT.getAomsshop001());

        aomsrefundTs.addAll(list);

        if (DateTimeTool.parse(disputeInfo.getDisputeCloseDate()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(disputeInfo.getDisputeCloseDate());
        }
      } // end for one order

      taskService.newTransaction4Save(aomsrefundTs);
    } // end for one page

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
  }

  /**
   * 获取卖家纠纷开启订单
   * 
   * @param taskScheduleConfig
   * @param aomsshopT
   * @throws Exception
   */
  private void syncOrderDisputeOpenList(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    OrderDisputeOpenListRequest orderDisputeOpenListRequest = new OrderDisputeOpenListRequest();
    orderDisputeOpenListRequest.setPageNo(DhgateCommonTool.MIN_PAGE_NO);
    orderDisputeOpenListRequest.setPageSize(DhgateCommonTool.DEFAULT_PAGE_SIZE);

    CompositeResponse<OrderDisputeOpenListResponse> listResponse = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    listResponse = dhgateApiService.dhOrderDisputeOpenList(orderDisputeOpenListRequest,
        aomsshopT.getAomsshop006());
    if (listResponse == null || !listResponse.isSuccessful()
        || listResponse.getSuccessResponse().getStatus() == null) {
      return;
    } else {
      totalSize = listResponse.getSuccessResponse().getCount();
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀
    } else if (totalSize == 0) {
      // 區間內沒有資料
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return;
      }
    }

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // TODO 需要验证是否是最新的资料在第一页
    for (int i = pageNum; i > 0; i--) {
      List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();

      orderDisputeOpenListRequest.setPageNo(i);

      listResponse = dhgateApiService.dhOrderDisputeOpenList(orderDisputeOpenListRequest,
          aomsshopT.getAomsshop006());
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DhgateCommonTool.STORE_TYPE,
          "dh.order.disputeopen.list$2.0 获取seller纠纷开启订单列表",
          JsonUtil.format(listResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      List<OrderDisputeOpenInfo> orderDisputeOpenInfos =
          listResponse.getSuccessResponse().getOrderDisputeOpenInfos();

      for (OrderDisputeOpenInfo disputeInfo : orderDisputeOpenInfos) {
        List<AomsrefundT> list =
            new AomsrefundTTranslator(disputeInfo).doTranslate(aomsshopT.getAomsshop001());

        aomsrefundTs.addAll(list);

        if (DateTimeTool.parse(disputeInfo.getDisputeOpenDate()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(disputeInfo.getDisputeOpenDate());
        }
      } // end for one order

      taskService.newTransaction4Save(aomsrefundTs);
    } // end for one page

    // // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    // if (taskScheduleConfig.isReCycle()) {
    // // process empty, 主要是為好閱讀
    //
    // } else {
    // taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
    // DateTimeTool.format(lastUpdateTime));
    // }
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
