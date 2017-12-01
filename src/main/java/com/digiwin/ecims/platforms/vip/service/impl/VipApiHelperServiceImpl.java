package com.digiwin.ecims.platforms.vip.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.vip.bean.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.vip.service.VipApiHelperService;
import com.digiwin.ecims.platforms.vip.service.VipApiService;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool.VipJitPickStatusEnum;
import vipapis.delivery.GetPickListResponse;
import vipapis.delivery.GetPoSkuListResponse;
import vipapis.delivery.PickDetail;

@Service
public class VipApiHelperServiceImpl implements VipApiHelperService {

  @Autowired
  private BaseDAO baseDao;

  @Autowired
  private VipApiService vipApiServie;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @SuppressWarnings("unchecked")
  @Override
  public void updateJitOrderStatus(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      String poNo) throws Exception {

    // 从中台取出未送货的拣货单（订单）
    List<AomsordT> ordersToBeUpdated = null;
    String hql = "FROM AomsordT t " + "WHERE 1=1 " + "AND t.storeType = :vipStoreType "
        + "AND t.storeId = :vipStoreId " + "AND t.aoms003 = :vipJitOrderStatus ";
    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("vipStoreType", VipCommonTool.STORE_TYPE);
    paramMap.put("vipStoreId", aomsshopT.getAomsshop001());
    paramMap.put("vipJitOrderStatus", VipJitPickStatusEnum.UNDELIVERIED.getPickStatus());

    ordersToBeUpdated = baseDao.queryHqlByParam(hql, paramMap);

    // 更新订单为平台上的最新状态
    if (ordersToBeUpdated != null && ordersToBeUpdated.size() > 0) {
      // 准备订单ID
      List<String> orderIds = new ArrayList<String>();
      for (AomsordT aomsordT : ordersToBeUpdated) {
        orderIds.add(aomsordT.getId());
      }

      // 开始更新
      for (String orderId : orderIds) {
        List<GetPoSkuListResponse> poSkuListResponses =
            vipApiServie.getPoSkuListResponses(taskScheduleConfig.getScheduleType(),
                taskScheduleConfig.getMaxPageSize(), aomsshopT, poNo, orderId);

        // List<GetPoSkuListResponse> poSkuListResponses = new ArrayList<GetPoSkuListResponse>();
        //
        // int pageSize = taskScheduleConfig.getMaxPageSize();
        // int totalSize = 0;
        //
        // int pageNum = 1;
        // int i = 1;
        //
        // do {
        // // 为了获取供货价使用此API
        // GetPoSkuListResponse poSkuListResponse = jitDeliveryServiceGetPoSkuList(
        // aomsshopT.getAomsshop004(),
        // aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(),
        // aomsshopT.getAomsshop012(), poNo,
        // StringUtils.EMPTY, null,
        // StringUtils.EMPTY,
        // StringUtils.EMPTY, StringUtils.EMPTY,
        // StringUtils.EMPTY, orderId, StringUtils.EMPTY,
        // StringUtils.EMPTY, StringUtils.EMPTY,
        // StringUtils.EMPTY, StringUtils.EMPTY,
        // i, pageSize);
        //
        // loginfoOperateService.newTransaction4SaveSource(
        // "N/A", "N/A", VipCommonTool.STORE_TYPE,
        // "vipapis.delivery.JitDeliveryService.getPoSkuList 获取PO单SKU信息",
        // CommonUtil.objectToJsonString(poSkuListResponse),
        // SourceLogBizTypeEnum.AOMSORDT.getValueString()
        // , aomsshopT.getAomsshop001()
        // , taskScheduleConfig.getScheduleType()
        // );
        // // 第一次请求时计算总页数
        // if (pageNum == i) {
        // totalSize = poSkuListResponse.getTotal();
        // pageNum = totalSize / pageSize + (totalSize % pageSize == 0 ? 0 : 1);
        // }
        //
        // poSkuListResponses.add(poSkuListResponse);
        //
        // i++;
        // } while (totalSize > 0 && i <= pageNum);


        GetPickListResponse pickListResponse = vipApiServie.jitDeliveryServiceGetPickList(
            aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(),
            aomsshopT.getAomsshop012(), poNo, orderId, null, StringUtils.EMPTY, StringUtils.EMPTY,
            StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
            StringUtils.EMPTY, StringUtils.EMPTY, NumberUtils.INTEGER_ZERO,
            VipCommonTool.MIN_PAGE_NO, VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY);
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", VipCommonTool.STORE_TYPE,
            "vipapis.delivery.JitDeliveryService.getPickList 获取指定供应商下拣货单列表信息",
            JsonUtil.format(pickListResponse),
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
            taskScheduleConfig.getScheduleType());

        PickDetail pickDetail = vipApiServie.jitDeliveryServiceGetPickDetail(
            aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(),
            poNo, aomsshopT.getAomsshop012(), orderId, VipCommonTool.MIN_PAGE_NO,
            VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY);
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", VipCommonTool.STORE_TYPE,
            "vipapis.delivery.JitDeliveryService.getPickDetail 获取指定拣货单明细信息",
            JsonUtil.format(pickDetail), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
            aomsshopT.getAomsshop001(), taskScheduleConfig.getScheduleType());

        List<AomsordT> list = new AomsordTTranslator(pickListResponse.getPicks().get(0), pickDetail,
            poSkuListResponses).doTranslate(aomsshopT.getAomsshop001());

        taskService.newTransaction4Save(list);
      }
    }

  }

}
