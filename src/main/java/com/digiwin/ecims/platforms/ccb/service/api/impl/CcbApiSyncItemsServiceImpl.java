package com.digiwin.ecims.platforms.ccb.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.ccb.bean.domain.item.list.get.ItemList;
import com.digiwin.ecims.platforms.ccb.bean.request.item.list.get.ItemListGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.item.list.get.ItemListGetRequestBody;
import com.digiwin.ecims.platforms.ccb.bean.request.item.list.get.ItemListGetRequestBodyDetail;
import com.digiwin.ecims.platforms.ccb.bean.response.item.list.get.ItemListGetResponse;
import com.digiwin.ecims.platforms.ccb.service.api.CcbApiService;
import com.digiwin.ecims.platforms.ccb.service.api.CcbApiSyncItemsService;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool.CcbSyncParamEnum;
import com.digiwin.ecims.platforms.ccb.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class CcbApiSyncItemsServiceImpl implements CcbApiSyncItemsService {

  private static final Logger logger = LoggerFactory.getLogger(CcbApiSyncItemsServiceImpl.class);

  @Autowired
  private CcbApiService ccbApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncItems(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
 // Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    // int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();

    ItemListGetRequest listRequest = new ItemListGetRequest();
    ItemListGetRequestBody body = new ItemListGetRequestBody();
    ItemListGetRequestBodyDetail detail = new ItemListGetRequestBodyDetail();
    listRequest.setBody(body);
    body.setBodyDetail(detail);

    detail.setPageNo(CcbCommonTool.MIN_PAGE_NO);
    detail.setPageSize(CcbCommonTool.DEFAULT_PAGE_SIZE);

    ItemListGetResponse listResponse = null;
    String flag = "0";
    detail.setStartTime(sDate);
    detail.setEndTime(eDate);
    listResponse = ccbApiService.CcbItemListGet(listRequest, appKey);
    if (listResponse == null
        || !listResponse.getHead().getRetCode().equals(CcbCommonTool.RESPONSE_SUCCESS_CODE)) {
      return false;
    } else {
      flag = listResponse.getFlag();
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀
    } else if (flag.equals(CcbSyncParamEnum.ITEM_SYNC_NO_MORE.getValue())) {
      // 區間內沒有資料
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return true;
      }
    }

    int i = CcbCommonTool.MIN_PAGE_NO;
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    do {
      detail.setPageNo(i);
      listResponse = ccbApiService.CcbItemListGet(listRequest, appKey);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, CcbCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|T0011 同步批量商品信息查询",
          JsonUtil.format(listResponse),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());
      flag = listResponse.getFlag();

      ItemList itemList = listResponse.getItemList();
      List<AomsitemT> list = new AomsitemTTranslator(itemList).doTranslate(storeId);

      aomsitemTs.addAll(list);
      taskService.newTransaction4Save(aomsitemTs);

      aomsitemTs.clear();
      i++;
    } while (flag.equals(CcbSyncParamEnum.ITEM_SYNC_HAS_NEXT.getValue()));

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
    }
    
    return true;
  }

}
