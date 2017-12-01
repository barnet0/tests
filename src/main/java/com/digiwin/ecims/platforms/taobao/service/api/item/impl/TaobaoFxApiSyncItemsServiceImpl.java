package com.digiwin.ecims.platforms.taobao.service.api.item.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.domain.FenxiaoProduct;
import com.taobao.api.response.FenxiaoProductsGetResponse;

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
import com.digiwin.ecims.platforms.taobao.service.api.item.TaobaoFxApiSyncItemsService;
import com.digiwin.ecims.platforms.taobao.service.translator.item.TaobaoJdpFxItemService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

@Service
public class TaobaoFxApiSyncItemsServiceImpl implements TaobaoFxApiSyncItemsService {

  @Autowired
  private TaskService taskService;
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Autowired
  private TaobaoJdpFxItemService taobaoJdpFxItem;
  
  @Autowired
  private TaobaoApiService taobaoApiService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncItems(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws ApiException, IOException {
    // 參數設定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    // 取得授權
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    // 取得資料總筆數
    FenxiaoProductsGetResponse fxProductsGetResponse = 
        taobaoApiService.taobaoFenxiaoProductsGet(
            appKey, appSecret, accessToken, 
            null, 
            TaobaoCommonTool.API_FX_ITEM_MIN_FIELDS, DateTimeTool.parse(sDate), 
            DateTimeTool.parse(eDate), TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize);
    long totalSize = fxProductsGetResponse.getTotalResults() == null ? 
        0L : fxProductsGetResponse.getTotalResults();

    // 整理搜尋區間，直到區間數目<=最大讀取數目
    // 整理方式，採二分法
    while (totalSize > taskScheduleConfig.getMaxReadRow()) {
      // eDate變為sDate與eDate的中間時間
      eDate = DateTimeTool.format(new Date(
          (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      fxProductsGetResponse = 
          taobaoApiService.taobaoFenxiaoProductsGet(
              appKey, appSecret, accessToken, 
              null, 
              TaobaoCommonTool.API_FX_ITEM_MIN_FIELDS, DateTimeTool.parse(sDate), 
              DateTimeTool.parse(eDate), TaobaoCommonTool.MIN_PAGE_NO, (long)pageSize);
      totalSize = fxProductsGetResponse.getTotalResults() == null ? 
          0L : fxProductsGetResponse.getTotalResults();
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

    // 取得總頁數
    long pageNum = totalSize / pageSize + ((totalSize % pageSize) == 0 ? 0 : 1);
    
    // 針對每一頁的資料做轉化處理
    for (long i = pageNum; i > 0; i--) {
      List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
      FenxiaoProductsGetResponse response =
          taobaoApiService.taobaoFenxiaoProductsGet(
              appKey, appSecret, accessToken, 
              null, 
              TaobaoCommonTool.API_FX_ITEM_FIELDS, DateTimeTool.parse(sDate), 
              DateTimeTool.parse(eDate), (long)i, (long)pageSize);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, 
          TaobaoCommonTool.STORE_TYPE_FX,
          "[" + UseTimeEnum.UPDATE_TIME + "]|taobao.fenxiao.products.get 查询产品列表", 
          JsonUtil.format(response),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());
      
      if (response.getProducts() != null) {
        for (FenxiaoProduct product : response.getProducts()) {
          List<AomsitemT> list =
              taobaoJdpFxItem.parseFenxiaoProductToAomsitemT(product, storeId);
          aomsitemTs.addAll(list);
          
          // 比較區間資料時間，取最大時間
          if (product.getModified().after(lastUpdateTime)) {
            lastUpdateTime = product.getModified();
          }
        }
      }
      // 每頁儲存資料一次
      taskService.newTransaction4Save(aomsitemTs);
    }
    
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateTime));
    
    return true;
  }

}
