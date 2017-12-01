package com.digiwin.ecims.platforms.yhd.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhd.object.refund.Refund;
import com.yhd.response.refund.RefundDetailGetResponse;
import com.yhd.response.refund.RefundGetResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncRefundsService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.ApiPageParam;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.RefundDateType;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsrefundTTranslator;

@Service
public class YhdApiSyncRefundsServiceImpl implements YhdApiSyncRefundsService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private YhdApiService yhdApiService;

  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncRefunds(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
 // 參數設定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    
    // 取得時間區間內總資料筆數
    RefundGetResponse responseTmp = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    // 取得時間區間內總資料筆數
    do {
      responseTmp = yhdApiService.yhdRefundGet(
          appKey, appSecret, accessToken, null, null, null, sDate, eDate, 
          ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue(), 
          RefundDateType.UPDATE_TIME.getValue(), null);
      // add by mowj 20151204
      if (responseTmp.getErrorCount() > 0 || responseTmp.getErrInfoList() != null) {
        // mark by mowj 20160728
//        ErrDetailInfo errorDetailInfo = responseTmp.getErrInfoList().getErrDetailInfo().get(0);
//        String errorCode = errorDetailInfo.getErrorCode();
//        String errorDescription = errorDetailInfo.getErrorDes();
//        throw new GenericBusinessException(errorCode, errorDescription, errorDescription);
      } else {
        totalSize = responseTmp.getTotalCount();
      }

      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程. add by xavier on
      // 20150829
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

    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
      // 獲取該頁退貨單列表
      RefundGetResponse refundGetResponse = yhdApiService.yhdRefundGet(
          appKey, appSecret, accessToken, null, null, null, sDate, eDate, 
          i, pageSize, 
          RefundDateType.UPDATE_TIME.getValue(), null);

      // add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, YhdCommonTool.STORE_TYPE,
          "yhd.refund.get 获取退货列表", JsonUtil.format(refundGetResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      // 對每個退貨單號..
      for (Refund refund : refundGetResponse.getRefundList().getRefund()) {
        // 取得退貨詳情
        RefundDetailGetResponse refundDetail = yhdApiService.yhdRefundDetailGet(
            appKey, appSecret, accessToken, refund.getRefundCode());

        // add by mowj 20150824
        // modi by mowj 20150825 增加biztype
        loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
            "yhd.refund.detail.get 获取单个退货详情", JsonUtil.format(refundDetail),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
            taskScheduleConfig.getScheduleType());

        // 將訂單詳情轉換成中台退貨單POJO
        List<AomsrefundT> list = new YhdAomsrefundTTranslator(refundDetail.getRefundInfoMsg(),
            refund, storeId).doTranslate();
        aomsrefundTs.addAll(list);
        for (AomsrefundT aomsrefundT : list) {
          if (DateTimeTool.parse(aomsrefundT.getModified()).after(lastUpdateTime)) {
            lastUpdateTime = DateTimeTool.parse(aomsrefundT.getModified());
          }
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
    }

    logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateTime));
    
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
