package com.digiwin.ecims.platforms.yougou.service.api.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.platforms.yougou.bean.domain.order.OrderIncrement;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderIncrementQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderIncrementQueryResponse;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiService;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiSyncOrdersService;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool.ORDER_STATUS;
import com.digiwin.ecims.platforms.yougou.util.translator.AomsordTTranslator;

@Service
public class YougouApiSyncOrdersServiceImpl implements YougouApiSyncOrdersService {

  private static final Logger logger = LoggerFactory.getLogger(YougouApiSyncOrdersServiceImpl.class);

  @Autowired
  private YougouApiService yougouApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncOrdersByIncremental(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncOrdersByIncremental(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    Date sDateTime = DateTimeTool.parse(sDate);
    Date eDateTime = DateTimeTool.parse(eDate);

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();

    OrderIncrementQueryRequest orderListRequest = new OrderIncrementQueryRequest();
    orderListRequest.setPageIndex(YougouCommonTool.MIN_PAGE_NO);
    orderListRequest.setPageSize(YougouCommonTool.DEFAULT_PAGE_SIZE);

    Calendar calendar = GregorianCalendar.getInstance();
    // 设定同步初始时间
    calendar.setTime(sDateTime);

    while (calendar.getTimeInMillis() <= eDateTime.getTime()) {
      // 设定开始时间与结束时间在一天之内
      sDate = DateTimeTool.format(calendar.getTime());
      calendar.add(GregorianCalendar.DATE, 1);
      if (calendar.getTimeInMillis() >= eDateTime.getTime()) {
        eDate = DateTimeTool.format(eDateTime);
        lastUpdateTime = eDateTime;
      } else {
        eDate = DateTimeTool.format(calendar.getTime());
        lastUpdateTime = calendar.getTime();
      }

      for (ORDER_STATUS ordStatus : YougouCommonTool.ORDER_STATUS.values()) {
        orderListRequest.setOrderStatus(ordStatus.getValue());

        OrderIncrementQueryResponse orderListResponse = null;
        int totalSize = 0;
        boolean sizeMoreThanSetting = false;
        do {
          orderListRequest.setStartModified(sDate);
          orderListRequest.setEndModified(eDate);
          orderListResponse =
              yougouApiService.yougouOrderIncrementQuery(orderListRequest, appKey, appSecret);
          if (orderListResponse == null
              || !orderListResponse.getCode().equals(YougouCommonTool.RESPONSE_SUCCESS_CODE)) {
            return false;
          } else {
            totalSize = orderListResponse.getTotalCount();
          }

          // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
          if (taskScheduleConfig.isReCycle()) {
            // process empty, 主要是為好閱讀
          } else if (totalSize == 0) {
            // 區間內沒有資料
            if (DateTimeTool.parse(eDate)
                .before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
              // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
              taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);

              // Thread.currentThread();
              Thread.sleep(1000);
              // return; // mark by mowj 20160324
              break;
            }
          }

          sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
          if (sizeMoreThanSetting) {
            // eDate變為sDate與eDate的中間時間
            Date tempEdate = new Date(
                (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2);
            calendar.setTime(tempEdate);
            eDate = DateTimeTool.format(tempEdate);
          }

        } while (sizeMoreThanSetting);

        // 計算頁數
        int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

        // OrderGetRequest orderGetRequest = new OrderGetRequest();

        // TODO 需要验证是否是最新的资料在第一页
        for (int i = pageNum; i > 0; i--) {
          List<AomsordT> aomsordTs = new ArrayList<AomsordT>();

          orderListRequest.setPageIndex(i);
          orderListResponse =
              yougouApiService.yougouOrderIncrementQuery(orderListRequest, appKey, appSecret);
          loginfoOperateService.newTransaction4SaveSource(sDate, eDate, YougouCommonTool.STORE_TYPE,
              "[" + UseTimeEnum.UPDATE_TIME + "]|yougou.order.increment.query 查询增量订单列表信息",
              JsonUtil.format(orderListResponse),
              SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
              taskScheduleConfig.getScheduleType());

          for (OrderIncrement order : orderListResponse.getItems()) {
            List<AomsordT> list = new AomsordTTranslator(order).doTranslate(storeId);

            aomsordTs.addAll(list);

            if (DateTimeTool.parse(order.getModifyTime()).after(lastUpdateTime)) {
              lastUpdateTime = DateTimeTool.parse(order.getModifyTime());
            }
          }
          taskService.newTransaction4Save(aomsordTs);
        } // end-for one page
      } // end-for one order status
    } // end-for one day
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
  public Long syncOrdersByIncremental(String startDate, String endDate, String storeId, String scheduleType)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long getIncrementalOrdersCount(String startDate, String endDate, String appKey,
      String appSecret, String accessToken) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean syncOrdersByCreated(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // TODO Auto-generated method stub
    return null;
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
