package com.digiwin.ecims.platforms.aliexpress.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.refund.IssueAPIIssueDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.request.refund.QueryIssueDetailRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.refund.QueryIssueListRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.refund.QueryIssueDetailResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.refund.QueryIssueListResponse;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiSyncRefundsService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.platforms.aliexpress.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class AliexpressApiSyncRefundsServiceImpl implements AliexpressApiSyncRefundsService {

  private static final Logger logger = LoggerFactory.getLogger(AliexpressApiSyncRefundsServiceImpl.class);

  @Autowired
  private AliexpressApiService aliexpressApiService;

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
    // int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    QueryIssueListRequest listRequest = new QueryIssueListRequest();
    listRequest.setCurrentPage(AliexpressCommonTool.MIN_PAGE_NO);

    QueryIssueListResponse listResponse =
        aliexpressApiService.ApiQueryIssueList(listRequest, appKey, appSecret, accessToken);

    int totalSize = listResponse.getTotalItem();
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

    int pageSize = listResponse.getPageSize();

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    // TODO 是否是逆序需要测试后验证
    for (int i = 1; i <= pageNum; i++) {
      listRequest.setCurrentPage(i);
      listResponse =
          aliexpressApiService.ApiQueryIssueList(listRequest, appKey, appSecret, accessToken);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, AliexpressCommonTool.STORE_TYPE,
          "api.queryIssueList -- version: 1 查询纠纷列表信息", JsonUtil.format(listResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      QueryIssueDetailRequest detailRequest = new QueryIssueDetailRequest();
      QueryIssueDetailResponse detailResponse = null;
      for (IssueAPIIssueDTO issueBaseInfo : listResponse.getDataList()) {
        Long issueId = issueBaseInfo.getId();

        detailRequest.setIssueId(issueId);
        detailResponse =
            aliexpressApiService.ApiQueryIssueDetail(detailRequest, appKey, appSecret, accessToken);
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
            AliexpressCommonTool.STORE_TYPE, "api.queryIssueDetail -- version: 1 根据纠纷ID查询纠纷详情信息",
            JsonUtil.format(listResponse),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        List<AomsrefundT> list =
            new AomsrefundTTranslator(detailResponse.getData()).doTranslate(storeId);

        aomsrefundTs.addAll(list);

      }
      taskService.newTransaction4Save(aomsrefundTs);
      aomsrefundTs.clear();
    }
    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
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
