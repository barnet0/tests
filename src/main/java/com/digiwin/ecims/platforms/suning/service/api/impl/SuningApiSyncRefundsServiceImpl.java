package com.digiwin.ecims.platforms.suning.service.api.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.rejected.BatchrejectedQueryRequest;
import com.suning.api.entity.rejected.BatchrejectedQueryResponse;

import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.platforms.suning.bean.AlreadyProcessStore;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiHelperService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiSyncRefundsService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.ApiInterface;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.SuningPageBean;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.SyncBasicParm;
import com.digiwin.ecims.platforms.suning.util.SuningTranslatorTool;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class SuningApiSyncRefundsServiceImpl implements SuningApiSyncRefundsService {

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private TaskService taskService;

  @Autowired
  private SuningApiService suningApiService;

  @Autowired
  private SuningApiHelperService suningApiHelperService;
  
  @Autowired
  private AomsShopService aomsShopService;
  
  @Autowired
  private ParamSystemService paramSystemService;
  
  
  @Override
  public void syncData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncRefunds(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncRefunds(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncRefundsAndReturnsData(taskScheduleConfig, aomsshopT);
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

  
  public void syncRefundsAndReturnsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    try {

      // 取得 api 認証的 key.
      String storeId = aomsshopT.getAomsshop001();
      String appKey = aomsshopT.getAomsshop004();
      String appSecret = aomsshopT.getAomsshop005();

      // 基本資料
      Date modiDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime()); // 最後一筆更新時間
      int pageSize = taskScheduleConfig.getMaxPageSize();
      final int maxReadRows =
          taskScheduleConfig.getMaxReadRow() == null ? 2000 : taskScheduleConfig.getMaxReadRow(); // 每次可存檔的筆數
      final AlreadyProcessStore alreadyProcessStore = new AlreadyProcessStore(maxReadRows, 0); // 目前處理筆數

      /*
       * 計算時間 因為會直接傳入, 要查詢的日期區間, 且要配合每個 api 的特性去調整.
       * 
       * taskScheduleConfig.getLastUpdateTime(); ==> start time （last time）
       * taskScheduleConfig.getEndDate(); ==> end time (current time)
       */
      Date startDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
      Date endDate = DateTimeTool.parse(taskScheduleConfig.getEndDate());

      /*
       * 1.因為退貨是用訂單創建日期, 去查詢的, 所以若只用 lastUpdateTime 去查的話, 會發生 7/23 下的訂單, 但在 8/1退款,然後退款API 查不到的情況.
       * 2.經討論後, 先由 lastUpdateTime - 30 天, 來當查詢時間 3.設定排程LastUpdateTime 時, 要注意,會多跑 30 天的狀況. EX. 設定
       * 6/1跑, 其實是 會從 5/1 開始跑
       */
      startDate = DateTimeTool.getAfterDays(new Date(), (30 * -1));

      // 計算差幾天
      final double calDiffRealDays = DateTimeTool.getDiffPrecisionDay(startDate, endDate); // 計算startDate,//
                                                                                           // endDate//
                                                                                           // 相差幾天
      final int calDiffDays = (int) Math.ceil(calDiffRealDays); // 表示兩個時間相差, 不到一天, 但以一天計算.

      // 計算 startDate 是否超過, 可查區間
      startDate = suningApiHelperService.calIsOutOfRange(taskScheduleConfig, startDate, calDiffDays,
          SyncBasicParm.REFUNDS_AND_RETURNS);

      // 計算可執行次數
      final int maxProcessDays = SyncBasicParm.REFUNDS_AND_RETURNS.getMaxProcessDays(); // 最大可取區間範圍,
                                                                                        // 也就是資料不能取超過這個天數範圍
      final int diffDays = calDiffDays > maxProcessDays ? maxProcessDays : calDiffDays; // 判斷最大可取天數
      final int getDataDays = SyncBasicParm.REFUNDS_AND_RETURNS.getGetDataDays(); // 一次取幾天的資料
      final int executeTimes = (int) Math.ceil(diffDays / (getDataDays + 0.0));

      // 運算用物件（for 第一次使用）
      String sDate = DateTimeTool.format(startDate);
      String eDate = DateTimeTool.format(DateTimeTool.getAfterDays(startDate, getDataDays));
      final String originalEDate = DateTimeTool.format(endDate); // 用來計算最後一天的時間

      DefaultSuningClient client = new DefaultSuningClient(
          paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API), appKey, appSecret, "json");

      for (int runDay = 1; runDay <= executeTimes; runDay++) {
        // System.out.println(sDate + "_" + eDate + "_diffDays：" + diffDays + "_" + runDay);
        // step1. 先取得總頁數
        BatchrejectedQueryRequest request = new BatchrejectedQueryRequest();
        request.setStartTime(sDate);
        request.setEndTime(eDate);
        request.setPageSize(pageSize);
        request.setPageNo(1);

        // DefaultSuningClient client = new
        // DefaultSuningClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API), appKey,
        // appSecret, "json"); // mark by mowj 20151117 放到循环外面
        BatchrejectedQueryResponse response = client.excute(request);
        // System.out.println("返回json/xml格式数据 :" + response.getBody());

        // 記錄回傳的 json 的原始格式.
        String api = "suning.custom.batchrejected.query 批量获取退货信息";
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, SuningCommonTool.STORE_TYPE,
            api, response.getBody(), SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(),
            aomsshopT.getAomsshop001(), taskScheduleConfig.getScheduleType());

        ResponseError re = SuningCommonTool.getInstance()
            .hasError(ApiInterface.DIGIWIN_INVENTORY_UPDATE, response.getBody()); // 檢查是否有 error
        if (re != null) {
          if ("biz.handler.data-get:no-result".equals(re.getCode())) {
            continue; // 表示查不到資料
          } else {
            throw new Exception(re.getCode() + "," + re.getMsg());
          }
        } else {
          // step2. 依頁次, 處理回傳回來的資料
          // 倒序處理, 怕會漏筆數, 因為若有修改, 則被修改的資料, 會不在本次查詢區間.
          SuningPageBean pb = SuningCommonTool.getInstance().getMaxPage(response.getBody());

          for (int i = pb.getPageTotal(); i > 0; i--) {

            // 檢查是不是己逹到最大處理筆數, 若己逹到, 則不做任何事, 留待下次再處理
            if (alreadyProcessStore.isFull()) {
              break;
            }

            request.setPageNo(i);
            request.setPageSize(pageSize);
            // client = new
            // DefaultSuningClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API), appKey,
            // appSecret, "json"); // 无需重新new一个client
            response = client.excute(request);

            // 記錄回傳的 json 的原始格式.
            loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
                SuningCommonTool.STORE_TYPE, api, response.getBody(),
                SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
                taskScheduleConfig.getScheduleType());

            re = SuningCommonTool.getInstance().hasError(ApiInterface.DIGIWIN_INVENTORY_UPDATE,
                response.getBody()); // 檢查是否有 error
            if (re != null) {
              // re.setExecuteStartDate(sDate);
              // re.setExecuteEndDate(eDate);
              // re.setErrorPage(i);
              // errorPool.add(re);
              // continue;
              throw new Exception(re.getCode() + "," + re.getMsg());
            }

            // 轉換成 DB bean. 也計算最後更新時間.
            List<AomsrefundT> data = SuningTranslatorTool.getInstance()
                .transBatchRejectedQueryToAomsrefundTBean(storeId, response, modiDate);

            // 將資料存到 DB.
            taskService.newTransaction4Save(data); // 新的批次存檔方式
            alreadyProcessStore.addThisTimeProcessRows(data.size()); // 記錄目前所處理的筆數
          }
        }
        // for 第N次使用.
        sDate = eDate;
        int remainDays = diffDays - (runDay * getDataDays);
        eDate = suningApiHelperService.calEndDate(sDate, remainDays, getDataDays, originalEDate);
        if (eDate == null) {
          // 若算不出來, 則表示己超出範圍
          continue;
        }
      }
      // 若都沒有取到任何資料, 則 lastUpdateTime = endDate.
      suningApiHelperService.updateLastUpdateTime(modiDate, taskScheduleConfig);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
