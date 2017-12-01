package com.digiwin.ecims.platforms.aliexpress.service.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderByIdRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderByIdResponse;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiHelperService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool.OrderStatusEnum;
import com.digiwin.ecims.platforms.aliexpress.util.translator.AomsordTTranslator;
import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class AliexpressApiHelperServiceImpl implements AliexpressApiHelperService {

  @Autowired
  private BaseDAO baseDao;

  @Autowired
  private AliexpressApiService aliexpressApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  // @Override
  // public void updateOrderRefundStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
  // {
  // // 从中台取出未完成的订单，查看是否产生纠纷
  // List<AomsordT> ordersMayBeInIssue = null;
  // String hql = "FROM AomsordT t " + "WHERE 1=1 " + "AND t.storeType = :aeStoreType "
  // + "AND t.storeId = :aeStoreId " + "AND t.aoms003 != :aeOrderStatus ";
  // HashMap<String, Object> paramMap = new HashMap<String, Object>();
  // paramMap.put("aeStoreType", AliexpressCommonTool.STORE_TYPE);
  // paramMap.put("aeStoreId", aomsshopT.getAomsshop001());
  // paramMap.put("aeOrderStatus", OrderStatusEnum.FINISH.getOrderStatus());
  //
  // ordersMayBeInIssue = baseDao.queryHqlByParam(hql, paramMap);
  //
  // if (ordersMayBeInIssue != null && !ordersMayBeInIssue.isEmpty()) {
  // for (AomsordT aomsordT : ordersMayBeInIssue) {
  // String orderId = aomsordT.getId();
  //
  // }
  // }
  //
  // }

  @Override
  public void updateOrderStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // 从中台取出未完成的订单，并更新
    List<AomsordT> ordersToBeUpdated = null;
    String hql = "FROM AomsordT t " + "WHERE 1=1 " + "AND t.storeType = :aeStoreType "
        + "AND t.storeId = :aeStoreId " + "AND t.aoms003 != :aeOrderStatus ";
    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("aeStoreType", AliexpressCommonTool.STORE_TYPE);
    paramMap.put("aeStoreId", aomsshopT.getAomsshop001());
    paramMap.put("aeOrderStatus", OrderStatusEnum.FINISH.getOrderStatus());

    ordersToBeUpdated = baseDao.queryHqlByParam(hql, paramMap);

    if (ordersToBeUpdated != null && !ordersToBeUpdated.isEmpty()) {
      String storeId = aomsshopT.getAomsshop001();
      String appKey = aomsshopT.getAomsshop004();
      String appSecret = aomsshopT.getAomsshop005();
      String accessToken = aomsshopT.getAomsshop006();

      FindOrderByIdRequest request = new FindOrderByIdRequest();
      FindOrderByIdResponse response = null;

      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
      AomsordTTranslator translator = new AomsordTTranslator();
      int mod = 50;
      int i = 0;

      for (AomsordT aomsordT : ordersToBeUpdated) {
        String orderId = aomsordT.getId();

        request.setOrderId(Long.parseLong(orderId));
        response = aliexpressApiService.ApiFindOrderById(request, appKey, appSecret, accessToken);
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A",
            AliexpressCommonTool.STORE_TYPE,
            "[BY_ORDER_ID]|api.findOrderById -- version: 1 交易订单详情查询",
            JsonUtil.format(response), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
            storeId, taskScheduleConfig.getScheduleType());

        if (response != null) {
          translator.setDetail(response);
          aomsordTs.addAll(translator.doTranslate(storeId));

          i++;
        }
        // 批量保存
        if (i > 0 && i % mod == 0) {
          taskService.newTransaction4Save(aomsordTs);

          aomsordTs.clear();
        }

        Thread.sleep(1000); // add by mowj 20160517 由于速卖通的API调用频率有限制，但是又不清楚限制的具体内容，所以在一笔查询之后让线程停止1秒。
      }
    }

  }
}
