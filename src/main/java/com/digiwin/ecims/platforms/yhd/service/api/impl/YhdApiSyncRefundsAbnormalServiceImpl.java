package com.digiwin.ecims.platforms.yhd.service.api.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhd.response.order.OrdersRefundAbnormalGetResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncRefundsAbnormalService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.ApiPageParam;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.OrderRefundAbnormalDateType;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsrefundTTranslator;

@Service
public class YhdApiSyncRefundsAbnormalServiceImpl implements YhdApiSyncRefundsAbnormalService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private YhdApiService yhdApiService;
  
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
    OrdersRefundAbnormalGetResponse responseTmp = null;
    int totalSize = 0;
    boolean sizeMoreThanSetting = false;
    // 取得時間區間內總資料筆數
    do {
      responseTmp = yhdApiService.yhdOrdersRefundAbnormalGet(
          appKey, appSecret, accessToken, null, null, null, null, null, 
          sDate, eDate, OrderRefundAbnormalDateType.APPLY_TIME.getValue(), 
          ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue());
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
      // 獲取該頁退貨單列表
      OrdersRefundAbnormalGetResponse refundGetResponse =
          yhdApiService.yhdOrdersRefundAbnormalGet(
              appKey, appSecret, accessToken, null, null, null, null, null, 
              sDate, eDate, OrderRefundAbnormalDateType.APPLY_TIME.getValue(), 
              i, pageSize);

      // add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, YhdCommonTool.STORE_TYPE,
          "yhd.orders.refund.abnormal.get 异常订单退款查询接口", JsonUtil.format(refundGetResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), storeId,
          taskScheduleConfig.getScheduleType());

      // 將退款詳情轉換成中台退貨單POJO
      List<AomsrefundT> list =
          new YhdAomsrefundTTranslator(refundGetResponse.getRefundOrderInfoList(),
              aomsshopT.getAomsshop001()).doTranslateAbNormal();
      for (AomsrefundT aomsrefundT : list) {
        if (DateTimeTool.parse(aomsrefundT.getModified()).after(lastUpdateTime)) {
          lastUpdateTime = DateTimeTool.parse(aomsrefundT.getModified());
        }
      }
      taskService.newTransaction4Save(list);
    }
    
    logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), lastUpdateTime);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateTime));
    
    return true;
  }

  @Override
  public Boolean syncRefundsToBeChecked() throws Exception {
    Date sysDate = new Date();
    List<AomsshopT> aomsShopList = aomsShopService.getStoreByStoreType(YhdCommonTool.STORE_TYPE);
    if (aomsShopList != null && aomsShopList.size() != 0) {
      for (int i = 0; i < aomsShopList.size(); i++) {
        AomsshopT aomsshopT = aomsShopList.get(i);
        /* 處理所有有更新的退貨單 */
        // 從中台DB中取出退款狀態不為8-退款完成, 9-拒絕退款, 10-退款關閉的退款單
        logger.info("目前storeId={}", aomsshopT.getAomsshop001());
        List<AomsrefundT> aomsrefundTs = taskService
            .executeQueryByHql(
                "FROM AomsrefundT t WHERE t.storeType = '" + YhdCommonTool.STORE_TYPE 
                + "' AND t.storeId = '" + aomsshopT.getAomsshop001() 
//                + "' AND t.aoms037 = '待审核'"
                + "' AND t.aoms037 = '0'" // 待审核状态代码为0 add by mowj 20160727
                );

        logger.info("要处理资料量为{}", aomsrefundTs.size());
        // 每筆中台退貨單..
        String modiDateStr = DateTimeTool.format(sysDate);// 若資料有修改過，以此為修改時間
        for (AomsrefundT aomsrefundT : aomsrefundTs) {
          String date = aomsrefundT.getAoms041();
          OrdersRefundAbnormalGetResponse response = 
              yhdApiService.yhdOrdersRefundAbnormalGet(
                  aomsshopT.getAomsshop004(), 
                  aomsshopT.getAomsshop005(), 
                  aomsshopT.getAomsshop006(),
                  aomsrefundT.getId(), 
                  null, null, null, null,
                  date, date, OrderRefundAbnormalDateType.APPLY_TIME.getValue(),
                  ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue());

          boolean success = response.getErrorCount() == 0;
       // 若調用API成功
          if (success) {
            List<AomsrefundT> aomsrefunds =
                new YhdAomsrefundTTranslator(response.getRefundOrderInfoList(),
                    aomsrefundT.getStoreId()).doTranslateAbNormal();
            for (AomsrefundT refund : aomsrefunds) {
              refund.setModified(modiDateStr);
            }

            taskService.newTransaction4Save(aomsrefunds, true);
          }
        }
      }
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
