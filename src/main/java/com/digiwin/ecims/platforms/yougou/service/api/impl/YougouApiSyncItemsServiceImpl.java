package com.digiwin.ecims.platforms.yougou.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.yougou.bean.request.inventory.InventoryQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.inventory.InventoryQueryResponse;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiService;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiSyncItemsService;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;
import com.digiwin.ecims.platforms.yougou.util.translator.AomsitemTTranslator;

@Service
public class YougouApiSyncItemsServiceImpl implements YougouApiSyncItemsService {

  private static final Logger logger = LoggerFactory.getLogger(YougouApiSyncItemsServiceImpl.class);

  @Autowired
  private YougouApiService yougouApiService;

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

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();

    // 取得区间内资料总笔数
    InventoryQueryRequest request = new InventoryQueryRequest();
    request.setPageIndex(YougouCommonTool.MIN_PAGE_NO);
    request.setPageSize(pageSize);
    int totalSize = 0;

    InventoryQueryResponse listResponse = null;
    // API没有时间作为传入参数控制
    listResponse = yougouApiService.yougouInventoryQuery(request, appKey, appSecret);

    if (listResponse == null
        || !listResponse.getCode().equals(YougouCommonTool.RESPONSE_SUCCESS_CODE)) {
      return false;
    } else {
      totalSize = listResponse.getTotalCount();
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

      request.setPageIndex(i);

      listResponse = yougouApiService.yougouInventoryQuery(request, appKey, appSecret);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, YougouCommonTool.STORE_TYPE,
          "yougou.inventory.query 查询商家库存信息", JsonUtil.format(listResponse),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      List<AomsitemT> list = new AomsitemTTranslator(listResponse.getItems()).doTranslate(storeId);

      aomsitemTs.addAll(list);

      taskService.newTransaction4Save(aomsitemTs);
    } // end for one page

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
    }
    
    return true;
  }

}
