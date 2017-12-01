package com.digiwin.ecims.platforms.taobao.service.api.item.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsSellerListGetResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.api.item.TaobaoTbApiSyncItemsInventoryService;
import com.digiwin.ecims.platforms.taobao.service.translator.item.TaobaoJdpTbItemService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoTbApiSyncItemsInventoryServiceImpl implements TaobaoTbApiSyncItemsInventoryService {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Autowired
  private TaobaoJdpTbItemService taobaoJdpTbItem;
  
  @Autowired
  private TaobaoApiService taobaoApiService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncTaobaoItemsInventory(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    return syncTaobaoItemsInventory(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncTaobaoItemsInventory(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
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
    
    final String orderBy = "modified:desc";

    // 取得時間區間內總資料筆數
    ItemsInventoryGetResponse itemsInventoryGetResponse = 
        taobaoApiService.taobaoItemsInventoryGet(
            appKey, appSecret, accessToken, 
            TaobaoCommonTool.API_ITEM_MIN_FIELDS, 
            orderBy, 
            DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
            TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize);
    long totalSize = itemsInventoryGetResponse.getTotalResults();

 // 整理搜尋區間，直到區間數目<=最大讀取數目
    // 整理方式，採二分法
    while (totalSize > taskScheduleConfig.getMaxReadRow()) {
      // eDate變為sDate與eDate的中間時間
      eDate = DateTimeTool.format(new Date(
          (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      itemsInventoryGetResponse = 
          taobaoApiService.taobaoItemsInventoryGet(
              appKey, appSecret, accessToken, 
              TaobaoCommonTool.API_ITEM_MIN_FIELDS, 
              orderBy, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate), 
              TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize);
      totalSize = itemsInventoryGetResponse.getTotalResults() == null ? 
          0L : itemsInventoryGetResponse.getTotalResults();
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

    List<String> itemIds = new ArrayList<String>();
    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      ItemsInventoryGetResponse  response =
          taobaoApiService.taobaoItemsInventoryGet(
              appKey, appSecret, accessToken, 
              TaobaoCommonTool.API_ITEM_MIN_FIELDS, 
              orderBy, 
              DateTimeTool.parse(sDate), DateTimeTool.parse(eDate),  
              (long)i, (long)pageSize);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, TaobaoCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.items.inventory.get 获取当前会话用户库存中的商品列表",
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (Item item : response.getItems()) {
        itemIds.add(item.getNumIid() + "");
      }
    }
    
    if (itemIds.size() > 0) {
      StringBuffer numIidBuffer = new StringBuffer();
      for (int i = 0; i < itemIds.size(); i++) {
        numIidBuffer.append(itemIds.get(i) + ",");
        if (((i + 1) % 20 != 0) && (i + 1) < itemIds.size()) {
          continue;
        } else {
          // System.out.println("20筆：" + numiidBuffer.toString());
          String numIids = numIidBuffer.substring(0, numIidBuffer.length() - 1).toString();

          ItemsSellerListGetResponse itemsListResponse = taobaoApiService.taobaoItemsSellerListGet(
              appKey, appSecret, accessToken, TaobaoCommonTool.API_ITEM_FIELDS, numIids);

          loginfoOperateService.newTransaction4SaveSource(
              sDate, eDate, TaobaoCommonTool.STORE_TYPE,
              "taobao.items.seller.list.get 批量获取商品详细信息",
              JsonUtil.format(itemsListResponse),
              SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
              taskScheduleConfig.getScheduleType());
          
          for (Item item : itemsListResponse.getItems()) {
            List<AomsitemT> list = taobaoJdpTbItem.parseItemToAomsitemT(item, storeId);
            aomsitemTs.addAll(list);

            // 比較區間資料時間，取最大時間
            if (item.getModified().after(lastUpdateTime)) {
              lastUpdateTime = item.getModified();
            }
          }
          taskService.newTransaction4Save(aomsitemTs);
          // 清空列表，为下一页资料作准备
          aomsitemTs.clear();

          // numiidBuffer = new StringBuffer(); // mark by mowj 20150810
          numIidBuffer.setLength(0); // add by mowj 20150810 效率比上方的高.清空StringBuffer
        }
      }
    }

 // 更新updateTime 成為下次的sDate
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateTime));

    return true;
  }

}
