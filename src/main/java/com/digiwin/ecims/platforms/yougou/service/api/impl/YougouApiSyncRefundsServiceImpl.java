package com.digiwin.ecims.platforms.yougou.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.platforms.yougou.bean.domain.refund.ReturnItem;
import com.digiwin.ecims.platforms.yougou.bean.request.refund.ReturnQualityQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.refund.ReturnQualityQueryResponse;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiService;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiSyncRefundsService;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;
import com.digiwin.ecims.platforms.yougou.util.translator.AomsrefundTTranslator;

@Service
public class YougouApiSyncRefundsServiceImpl implements YougouApiSyncRefundsService {

  private static final Logger logger = LoggerFactory.getLogger(YougouApiSyncRefundsServiceImpl.class);

  @Autowired
  private YougouApiService yougouApiService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncRefunds(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();

    ReturnQualityQueryRequest request = new ReturnQualityQueryRequest();
    request.setPageIndex(YougouCommonTool.MIN_PAGE_NO + "");
    request.setPageSize(YougouCommonTool.DEFAULT_PAGE_SIZE + "");

    ReturnQualityQueryResponse listResponse = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    do {
      request.setApplyStartTime(sDate);
      request.setApplyEndTime(eDate);
      listResponse = yougouApiService.yougouReturnQualityQuery(request, appKey, appSecret);
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
    for (int i = pageNum; i > 0; i--) {
      List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();

      request.setPageIndex(i + "");
      listResponse = yougouApiService.yougouReturnQualityQuery(request, appKey, appSecret);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, YougouCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|yougou.return.quality.query 退换货质检查询",
          JsonUtil.format(listResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      for (ReturnItem returnItem : listResponse.getItems()) {
        List<AomsrefundT> list = new AomsrefundTTranslator(returnItem).doTranslate(storeId);
        aomsrefundTs.addAll(list);
      }

      for (AomsrefundT aomsrefundT : aomsrefundTs) {
        if (DateTimeTool.parse(aomsrefundT.getAoms041()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(aomsrefundT.getAoms041());
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
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
  public Boolean syncRefunds(String startDate, String endDate, String storeId) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long getCreatedCount(String appKey, String appSecret, String accessToken, String startDate,
      String endDate) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
