package com.digiwin.ecims.platforms.pdd.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetGoodsResponse;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiService;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncItemsService;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool.ItemStatus;
import com.digiwin.ecims.platforms.pdd.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class PddApiSyncItemsServiceImpl implements PddApiSyncItemsService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private PddApiService pddApiService;

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      ItemStatus itemStatus) throws Exception {
 // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();
    
    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    totalSize = pddApiService.pddGetGoods(
        appKey, appSecret, accessToken, itemStatus.getType(), null, null, 
        PddCommonTool.MIN_PAGE_NO, PddCommonTool.MIN_PAGE_SIZE).getTotalCount(); 
    
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

    List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum - 1; i >= 0; i--) {
      GetGoodsResponse response = pddApiService.pddGetGoods(
          appKey, appSecret, accessToken, 
          itemStatus.getType(), null, null, (int)i, pageSize);

      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", 
          PddCommonTool.STORE_TYPE,
          "[" + itemStatus + "]|mGetGoods 获得商品档案",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      List<AomsitemT> list =
          new AomsitemTTranslator(response).doTranslate(storeId, itemStatus);
      aomsitemTs.addAll(list);
      
      taskService.newTransaction4Save(aomsitemTs);
      // 清空列表，为下一页资料作准备
      aomsitemTs.clear();
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      // 更新updateTime 成為下次的sDate
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
    }
    
    return true;
  }

}
