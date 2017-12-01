package com.digiwin.ecims.platforms.beibei.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.beibei.bean.domain.refund.RefundsGetDto;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundsGetResponse;
import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiService;
import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiSyncRefundsService;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool.RefundTimeRange;
import com.digiwin.ecims.platforms.beibei.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;

@Service
public class BeibeiApiSyncRefundsServiceImpl implements BeibeiApiSyncRefundsService {

  private static final Logger logger = LoggerFactory.getLogger(BeibeiApiSyncRefundsServiceImpl.class);
  
  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private BeibeiApiService beibeiApiService;

  @Autowired
  private AomsShopService aomsShopService;
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncRefunds(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
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
    boolean sizeMoreThanSetting = false;
    do {
      totalSize = getCreatedCount(appKey, appSecret, accessToken, sDate, eDate);
      
      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);
    
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

    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OuterRefundsGetResponse response = 
          beibeiApiService.beibeiOuterRefundsGet(
              appKey, appSecret, accessToken,
              RefundTimeRange.CREATE_TIME.getValue(), null, 
              sDate, eDate, 
              i, (long)pageSize);
      
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BeibeiCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|beibei.outer.refunds.get 售后列表",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      for (RefundsGetDto refundGetDto : response.getData()) {
        Long id = refundGetDto.getId();
        OuterRefundDetailGetResponse refundDetailGetResponse = 
              beibeiApiService.beibeiOuterRefundDetailGet(
            appKey, appSecret, accessToken, null, id.toString());
        
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, BeibeiCommonTool.STORE_TYPE,
            "beibei.outer.refund.get 售后详情",
            JsonUtil.formatByMilliSecond(refundDetailGetResponse),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());
        
        List<AomsrefundT> list =
            new AomsrefundTTranslator(refundDetailGetResponse).doTranslate(storeId);
        
        if (DateTimeTool.parse(refundGetDto.getCreateTime()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(refundGetDto.getCreateTime());
        }
        
        aomsrefundTs.addAll(list);
      }
      taskService.newTransaction4Save(aomsrefundTs);
      // 清空列表，为下一页资料作准备
      aomsrefundTs.clear();
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      // 更新updateTime 成為下次的sDate
      logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }
    
    return true;
  }

  @Override
  public Boolean syncRefunds(String startDate, String endDate, String storeId) throws Exception {
    // 参数设定
    Long pageSize = BeibeiCommonTool.DEFAULT_PAGE_SIZE;
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);
    String appKey = esv.getAppKey();
    String appSecret = esv.getAppSecret();
    String accessToken = esv.getAccessToken();
    
    // 取得时间区间内总资料笔数
    long totalSize = 0L;
    totalSize = getCreatedCount(appKey, appSecret, accessToken, startDate, endDate);

    // 區間內有資料， 計算頁數
    long pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
    
    List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
    // 針對每一頁(倒序)的所有資料新增
    for (long i = pageNum; i > 0; i--) {
      OuterRefundsGetResponse response = 
          beibeiApiService.beibeiOuterRefundsGet(
              appKey, appSecret, accessToken,
              RefundTimeRange.CREATE_TIME.getValue(), null, 
              startDate, endDate, 
              i, (long)pageSize);
      
      loginfoOperateService.newTransaction4SaveSource(startDate, endDate, BeibeiCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME + "]|beibei.outer.refunds.get 售后列表",
          JsonUtil.formatByMilliSecond(response),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          null);

      for (RefundsGetDto refundGetDto : response.getData()) {
        Long id = refundGetDto.getId();
        OuterRefundDetailGetResponse refundDetailGetResponse = 
              beibeiApiService.beibeiOuterRefundDetailGet(
            appKey, appSecret, accessToken, null, id.toString());
        
        loginfoOperateService.newTransaction4SaveSource(startDate, endDate, BeibeiCommonTool.STORE_TYPE,
            "beibei.outer.refund.get 售后详情",
            JsonUtil.formatByMilliSecond(refundDetailGetResponse),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            null);
        
        List<AomsrefundT> list =
            new AomsrefundTTranslator(refundDetailGetResponse).doTranslate(storeId);
        
        aomsrefundTs.addAll(list);
      }
      taskService.newTransaction4Save(aomsrefundTs);
      // 清空列表，为下一页资料作准备
      aomsrefundTs.clear();
    }
    return true;
  }

  @Override
  public Long getCreatedCount(String appKey, String appSecret, String accessToken, String startDate,
      String endDate) throws Exception {
    OuterRefundsGetResponse response = 
        beibeiApiService.beibeiOuterRefundsGet(
            appKey, appSecret, accessToken,
            RefundTimeRange.CREATE_TIME.getValue(), null, 
            startDate, endDate, BeibeiCommonTool.MIN_PAGE_NO, BeibeiCommonTool.MIN_PAGE_SIZE);
    if (response == null
        || (response.getSuccess() == Boolean.FALSE)) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "BeibeiApiSyncRefundsServiceImpl#getCreatedCount", 
          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
          new Date(), "获取数据异常", response.getMessage(), "", "");
      return 0L;
    }
    Long count = response.getCount();
    
    return count;
  }
  
//  private Long getIncrementalCount(String appKey, String appSecret, String accessToken, String startDate,
//      String endDate) throws Exception {
//    OuterRefundsGetResponse response = 
//        beibeiApiService.beibeiOuterRefundsGet(
//            appKey, appSecret, accessToken,
//            RefundTimeRange.UPDATE_TIME.getValue(), null, 
//            startDate, endDate, BeibeiCommonTool.MIN_PAGE_NO, BeibeiCommonTool.MIN_PAGE_SIZE);
//    if (response == null
//        || (response.getSuccess() == Boolean.FALSE)) {
//      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
//          "BeibeiApiSyncRefundsServiceImpl#getIncrementalCount", 
//          LogInfoBizTypeEnum.ECI_REQUEST.getValueString(),
//          new Date(), "获取数据异常", response.getMessage(), "", "");
//      return 0L;
//    }
//    Long count = response.getCount();
//    
//    return count;
//  }

}
