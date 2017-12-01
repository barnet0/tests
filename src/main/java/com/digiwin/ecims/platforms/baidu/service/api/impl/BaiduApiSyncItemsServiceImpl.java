package com.digiwin.ecims.platforms.baidu.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.baidu.bean.domain.item.ItemInfo;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos.GetItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos.QueryItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiService;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiSyncItemsService;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.platforms.baidu.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class BaiduApiSyncItemsServiceImpl implements BaiduApiSyncItemsService {

  private static final Logger logger = LoggerFactory.getLogger(BaiduApiSyncItemsServiceImpl.class);

  @Autowired
  private BaiduApiService baiduApiService;

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
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    // 取得区间内资料总笔数
    Response<QueryItemInfosResponse> queryItemResponse = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;

    // TODO API没有时间作为传入参数控制
    queryItemResponse =
        baiduApiService.baiduMallItemQuery(aomsshopT, null, BaiduCommonTool.MIN_PAGE_NO, pageSize);

    if (queryItemResponse == null
        || queryItemResponse.getHeader().getStatus() != BaiduCommonTool.RESPONSE_SUCCESS_CODE) {
      return false;
    } else {
      totalSize = queryItemResponse.getBody().getData().get(0).getTotalRecords();
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

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);


    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();

      queryItemResponse = baiduApiService.baiduMallItemQuery(aomsshopT, null, i, pageSize);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BaiduCommonTool.STORE_TYPE,
          "baidumall.item.query 获取商品列表", JsonUtil.format(queryItemResponse),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      List<Long> itemIds = queryItemResponse.getBody().getData().get(0).getItemIds();

      Response<GetItemInfosResponse> itemInfosResponse =
          baiduApiService.baiduMallItemGetList(aomsshopT, itemIds, null);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BaiduCommonTool.STORE_TYPE,
          "baidumall.item.get.list 批量获取商品信息", JsonUtil.format(itemInfosResponse),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      List<ItemInfo> itemAllInfos = itemInfosResponse.getBody().getData().get(0).getItemInfos();

      List<AomsitemT> list =
          new AomsitemTTranslator(itemAllInfos).doTranslate(aomsshopT.getAomsshop001());
      aomsitemTs.addAll(list);

      for (ItemInfo itemInfo : itemAllInfos) {
        if (DateTimeTool.parse(itemInfo.getUpdateTime()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(itemInfo.getUpdateTime());
        }
      }

      taskService.newTransaction4Save(aomsitemTs);
    } // end for one page

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    
    return true;
  }

}
