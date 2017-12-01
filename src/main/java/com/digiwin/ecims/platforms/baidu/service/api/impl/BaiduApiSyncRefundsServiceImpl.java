package com.digiwin.ecims.platforms.baidu.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.baidu.bean.domain.refund.RefundInfoEntity;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo.FindRefundOrderItemInfoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno.GetRefundInfoByNoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiService;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiSyncRefundsService;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.platforms.baidu.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class BaiduApiSyncRefundsServiceImpl implements BaiduApiSyncRefundsService {

  private static final Logger logger = LoggerFactory.getLogger(BaiduApiSyncRefundsServiceImpl.class);
  
  @Autowired
  private BaiduApiService baiduApiService;

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

    // 取得区间内资料总笔数
    Response<FindRefundOrderItemInfoResponse> listResponse = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    listResponse = baiduApiService.baiduMallRefundsReceiveGet(aomsshopT, null, sDate, null, null,
        null, BaiduCommonTool.MIN_PAGE_NO, pageSize);
    if (listResponse == null
        || listResponse.getHeader().getStatus() != BaiduCommonTool.RESPONSE_SUCCESS_CODE) {
      return false;
    } else {
      totalSize = listResponse.getBody().getData().get(0).getTotalCount();
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

    for (int i = pageNum; i > 0; i--) {
      List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();

      listResponse = baiduApiService.baiduMallRefundsReceiveGet(aomsshopT, null, sDate, null, null,
          null, i, pageSize);
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BaiduCommonTool.STORE_TYPE,
          "baidumall.refunds.receive.get 获取卖家的退款单列表", JsonUtil.format(listResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      Response<GetRefundInfoByNoResponse> detailResponse = null;
      for (RefundInfoEntity refundInfo : listResponse.getBody().getData().get(0)
          .getRefundOrderItemInfoEntityList()) {
        String refundNo = refundInfo.getRefundNo();

        detailResponse = baiduApiService.baiduMallRefundGet(aomsshopT, refundNo);
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BaiduCommonTool.STORE_TYPE,
            "baidumall.refund.get 获取单个退款单详情", JsonUtil.format(detailResponse),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
            taskScheduleConfig.getScheduleType());

        List<AomsrefundT> list =
            new AomsrefundTTranslator(detailResponse.getBody().getData().get(0).getResult())
                .doTranslate(aomsshopT.getAomsshop001());

        aomsrefundTs.addAll(list);

        String createTime = detailResponse.getBody().getData().get(0).getResult()
            .getRefundInfoEntity().getCreateTime();
        if (DateTimeTool.parse(createTime).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(createTime);
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
    } // end-for one page

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
