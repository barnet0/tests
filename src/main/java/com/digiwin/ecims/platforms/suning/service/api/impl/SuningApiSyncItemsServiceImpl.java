package com.digiwin.ecims.platforms.suning.service.api.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.item.ItemQueryRequest;
import com.suning.api.entity.item.ItemQueryResponse;
import com.suning.api.entity.item.ItemQueryResponse.ItemQuery;
import com.suning.api.entity.item.ItemdetailQueryRequest;
import com.suning.api.entity.item.ItemdetailQueryResponse;
import com.suning.api.entity.item.ItemdetailQueryResponse.ItemdetailQuery;
import com.suning.api.entity.item.ItemsaleQueryResponse.ItemSaleParams;

import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.suning.bean.AlreadyProcessStore;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiHelperService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiSyncItemsService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import com.digiwin.ecims.platforms.suning.util.SuningTranslatorTool;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.ApiInterface;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.SuningPageBean;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool.SyncBasicParm;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class SuningApiSyncItemsServiceImpl implements SuningApiSyncItemsService {

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
    // TODO Auto-generated method stub
    syncItems(taskScheduleConfig, aomsshopT);
  }

  @Override
  public Boolean syncItems(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    syncGoodsData(taskScheduleConfig, aomsshopT);
    return true;
  }

  /**
   * 取得商品 detail info. <br/>
   * suning.custom.itemdetail.query 获取我的商品详情信息
   * 
   * @param esv
   * @param headRow
   * @return
   * @throws Exception
   */
  private ItemdetailQuery getGoodDataDetail(String appKey, String appSecret, ItemQuery headRow,
      String shopId, String scheduleType) throws Exception {
    ItemdetailQueryRequest request = new ItemdetailQueryRequest();
    request.setProductCode(headRow.getProductCode());
    DefaultSuningClient client = new DefaultSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API), appKey, appSecret, "json");
    ItemdetailQueryResponse response = client.excute(request);
    // System.out.println("返回json/xml格式数据 :" + response.getBody());

    // 記錄回傳的 json 的原始格式.
    String api = "suning.custom.itemdetail.query 获取我的商品详情信息";
    loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", SuningCommonTool.STORE_TYPE, api,
        response.getBody(), SourceLogBizTypeEnum.AOMSITEMT.getValueString(), shopId, scheduleType);

    ResponseError re = SuningCommonTool.getInstance().hasError(ApiInterface.DIGIWIN_ITEM_DETAIL_GET,
        response.getBody()); // 檢查是否有 error
    if (re != null) {
      // throw new Exception(re.getCode() + "_" + re.getMsg() + "_productCode:" +
      // headRow.getProductCode());
      LogInfoT log = new LogInfoT();
      // 基本運作參數
      log.setErrStoreType(SuningCommonTool.STORE_TYPE);
      log.setFinalStatus("0");
      log.setPushLimits(5);
      log.setErrBillId(headRow.getProductCode());
      // log.setOtherKey("");

      // 基本運作參數
      log.setIpAddress(InetAddressTool.getLocalIpv4());
      log.setCallMethod("getGoodDataDetail");
      log.setBusinessType(LogInfoBizTypeEnum.ECI_REQUEST.getValueString());
      log.setResCode(re.getCode());
      log.setResMsg(re.getMsg());
      log.setErrMsg(re.getMsg());
      log.setErrBillType("AomsitemT");

      loginfoOperateService.newTransaction4SaveLog(log);

      return null;
    } else {
      return response.getSnbody().getItemdetailQuery();
    }
  }
  
  public void syncGoodsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
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
      Date scheduleRunTime = endDate; // 排程啟動時間
      final double calDiffRealDays = DateTimeTool.getDiffPrecisionDay(startDate, endDate); // 計算startDate,//
                                                                                           // endDate//
                                                                                           // 相差幾天
      final int calDiffDays = (int) Math.ceil(calDiffRealDays); // 表示兩個時間相差, 不到一天, 但以一天計算.

      // 計算 startDate 是否超過, 可查區間
      startDate =
          suningApiHelperService.calIsOutOfRange(taskScheduleConfig, startDate, calDiffDays, SyncBasicParm.GOODS);

      // 計算可執行次數
      final int maxProcessDays = SyncBasicParm.GOODS.getMaxProcessDays(); // 最大可取區間範圍,
                                                                          // 也就是資料不能取超過這個天數範圍
      final int diffDays = calDiffDays > maxProcessDays ? maxProcessDays : calDiffDays; // 判斷最大可取天數
      final int getDataDays = SyncBasicParm.GOODS.getGetDataDays(); // 一次取幾天的資料
      final int executeTimes = (int) Math.ceil(diffDays / (getDataDays + 0.0));

      // 運算用物件（for 第一次使用）
      String sDate = DateTimeTool.format(startDate);
      String eDate = DateTimeTool.format(DateTimeTool.getAfterDays(startDate, getDataDays));
      final String originalEDate = DateTimeTool.format(endDate); // 用來計算最後一天的時間
      // -----------------------------------------------

      DefaultSuningClient client = new DefaultSuningClient(
          paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API), appKey, appSecret, "json");

      for (int runDay = 1; runDay <= executeTimes; runDay++) {
        // System.out.println(sDate + "_" + eDate + "_diffDays：" + diffDays + "_" + runDay);

        // step1. 先取得總頁數
        ItemQueryRequest request = new ItemQueryRequest();
        // request.setBrandCode("R3301");
        // request.setCategoryCode("R3301002");
        // request.setStatus("1");
        request.setStartTime(sDate);
        request.setEndTime(eDate);
        request.setPageNo(1);
        request.setPageSize(pageSize);
        // DefaultSuningClient client = new
        // DefaultSuningClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API), appKey,
        // appSecret, "json"); // mark by mowj 20151117
        ItemQueryResponse response = client.excute(request);
        // System.out.println("返回json/xml格式数据 :" + response.getBody());

        // 記錄回傳的 json 的原始格式.
        String api = "suning.custom.item.query 获取我的商品库信息";
        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, SuningCommonTool.STORE_TYPE,
            api, response.getBody(), SourceLogBizTypeEnum.AOMSITEMT.getValueString(),
            aomsshopT.getAomsshop001(), taskScheduleConfig.getScheduleType());

        ResponseError re = SuningCommonTool.getInstance()
            .hasError(ApiInterface.DIGIWIN_ITEM_DETAIL_GET, response.getBody()); // 檢查是否有 error
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

          /*
           * 因為suning api 取回時, 會做ASC（正序排序）, 故最後一頁為最新的資料, 所以不用再從最後一頁取資料(怕漏單) edit by xavier on
           * 20150906
           */
          // for (int i = pb.getPageTotal(); i > 0; i--) {
          for (int i = 1; i <= pb.getPageTotal(); i++) {
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

            loginfoOperateService.newTransaction4SaveSource(sDate, eDate,
                SuningCommonTool.STORE_TYPE, api, response.getBody(),
                SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
                taskScheduleConfig.getScheduleType());

            re = SuningCommonTool.getInstance().hasError(ApiInterface.DIGIWIN_ITEM_DETAIL_GET,
                response.getBody()); // 檢查是否有 error
            if (re != null) {
              // re.setExecuteStartDate(sDate);
              // re.setExecuteEndDate(eDate);
              // re.setErrorPage(i);
              // errorPool.add(re);
              // continue;
              throw new Exception(re.getCode() + "," + re.getMsg());
            }

            // 轉換回傳回來的 JSON.
            ItemQueryResponse reSc = response;
            List<ItemQuery> returnList = reSc.getSnbody().getItemQueries();

            // 取得商品細節資料
            for (ItemQuery headRow : returnList) {
              // FIXME 因目前logic 限, 這邊採單筆, 單筆存到DB, 會有效能上的問題...
              ItemdetailQuery detailRow = this.getGoodDataDetail(appKey, appSecret, headRow,
                  aomsshopT.getAomsshop001(), taskScheduleConfig.getScheduleType());
              if (detailRow == null) {
                continue; // 表示, 查單身資料失敗, 並寫一份資料到 loginfoT 裡, 交由錯誤自動拉取的機制處理
              }

              // 取得 商品销售情况
              ItemSaleParams itemSale = suningApiService.getItemSaleQuery(aomsshopT,
                  headRow.getProductCode(), taskScheduleConfig.getScheduleType());

              // 轉換成 DB bean. 也計算最後更新時間.
              List<AomsitemT> transData = SuningTranslatorTool.getInstance().doTransToAomsitemTBean(
                  storeId, headRow, detailRow, itemSale, modiDate, scheduleRunTime);

              // 將資料存到 DB.
              taskService.newTransaction4Save(transData); // 新的批次存檔方式
              alreadyProcessStore.addThisTimeProcessRows(transData.size()); // 記錄目前所處理的筆數
            }
          }
        }

        // for 第N次使用.
        sDate = eDate; // 原本的最後一天.
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
    // System.out.println("done!");
    
  }
}
