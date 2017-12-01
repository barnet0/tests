package com.digiwin.ecims.platforms.ccb.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.ccb.bean.domain.order.detail.get.OrderInfo;
import com.digiwin.ecims.platforms.ccb.bean.domain.order.list.get.OrderBriefInfo;
import com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get.OrderDetailGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get.OrderDetailGetRequestBody;
import com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get.OrderDetailGetRequestBodyDetail;
import com.digiwin.ecims.platforms.ccb.bean.request.order.list.get.OrderListGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.order.list.get.OrderListGetRequestBody;
import com.digiwin.ecims.platforms.ccb.bean.request.order.list.get.OrderListGetRequestBodyDetail;
import com.digiwin.ecims.platforms.ccb.bean.response.order.detail.get.OrderDetailGetResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.order.list.get.OrderListGetResponse;
import com.digiwin.ecims.platforms.ccb.service.api.CcbApiService;
import com.digiwin.ecims.platforms.ccb.service.api.CcbApiSyncOrdersService;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.platforms.ccb.util.translator.AomsordTTranslator;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class CcbApiSyncOrdersServiceImpl implements CcbApiSyncOrdersService {

  private static final Logger logger = LoggerFactory.getLogger(CcbApiSyncOrdersServiceImpl.class);

  @Autowired
  private CcbApiService ccbApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // TODO Auto-generated method stub
  
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    throw new UnsupportedOperationException("not implemented!");
  }

  @Override
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    throw new UnsupportedOperationException("not implemented!");
  }

  @Override
  public Long getIncrementalOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    throw new UnsupportedOperationException("not implemented!");
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();

    OrderListGetRequest listRequest = new OrderListGetRequest();
    OrderListGetRequestBody body = new OrderListGetRequestBody();
    OrderListGetRequestBodyDetail detail = new OrderListGetRequestBodyDetail();
    listRequest.setBody(body);
    body.setBodyDetail(detail);

    detail.setPageNo(CcbCommonTool.MIN_PAGE_NO);
    detail.setPageSize(CcbCommonTool.DEFAULT_PAGE_SIZE);

    OrderListGetResponse listResponse = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    do {
      detail.setStartCreated(sDate);
      detail.setEndCreated(eDate);
      listResponse = ccbApiService.CcbOrderListGet(listRequest, appKey);
      if (listResponse == null
          || !listResponse.getHead().getRetCode().equals(CcbCommonTool.RESPONSE_SUCCESS_CODE)) {
        return false;
      } else {
        totalSize = Integer.parseInt(listResponse.getBody().getTotalResultsCount());
      }

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀
      } else if (totalSize == 0) {
        // 區間內沒有資料
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return true;
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
    for (int i = pageNum; i > 0; i++) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();

      detail.setPageNo(i);
      listResponse = ccbApiService.CcbOrderListGet(listRequest, appKey);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, CcbCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|T0008 同步批量订单查询",
          JsonUtil.format(listResponse), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
          storeId, taskScheduleConfig.getScheduleType());

      List<OrderBriefInfo> orderBriInfoList =
          listResponse.getBody().getOrders().getOrderBriefInfos();
      OrderDetailGetRequest detailGetRequest = new OrderDetailGetRequest();
      OrderDetailGetRequestBody detailBody = new OrderDetailGetRequestBody();
      OrderDetailGetRequestBodyDetail detailBodyDetail = new OrderDetailGetRequestBodyDetail();
      detailGetRequest.setBody(detailBody);
      detailBody.setBodyDetail(detailBodyDetail);

      OrderDetailGetResponse detailGetResponse = null;
      for (OrderBriefInfo orderBriefInfo : orderBriInfoList) {
        String orderId = orderBriefInfo.getOrderId();

        detailBodyDetail.setOrderId(orderId);
        detailGetResponse = ccbApiService.CcbOrderDetailGet(detailGetRequest, appKey);
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, CcbCommonTool.STORE_TYPE,
            "T0007 单笔订单查询", JsonUtil.format(detailGetResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        OrderInfo orderInfo = detailGetResponse.getBody().getOrderItems().getOrderInfos().get(0);
        List<AomsordT> list = new AomsordTTranslator(orderInfo).doTranslate(storeId);

        aomsordTs.addAll(list);

        if (DateTimeTool.parse(orderInfo.getOrderTime()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(orderInfo.getOrderTime());
        }
      }
      taskService.newTransaction4Save(aomsordTs);
    } // end-for one page

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    
    return true;
  }

  @Override
  public Long syncOrdersByCreated(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long getCreatedOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean syncOrderByOrderId(String storeId, String orderId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
