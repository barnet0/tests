package com.digiwin.ecims.platforms.icbc.service.api.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.icbc.bean.base.Refund;
import com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery.IcbcRefundQueryResponse;
import com.digiwin.ecims.platforms.icbc.bean.translator.IcbcAomsrefundTTranslator;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiSyncRefundsService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class IcbcApiSyncRefundsServiceImpl implements IcbcApiSyncRefundsService {

  @Autowired
  private IcbcApiService icbcApiService;

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
    // 參數設定
    Date modiDate = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();

    String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();

    IcbcRefundQueryResponse response =
        icbcApiService.icbcb2cRefundQuery(appKey, appSecret, accessToken, sDate, eDate, null, null);

    // add by mowj 20150824
    // modi by mowj 20150825 增加biztype
    loginfoOperateService.newTransaction4SaveSource(sDate, eDate, 
        IcbcCommonTool.STORE_TYPE,
        "icbcb2c.refund.query 退款申请查询", response, SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(),
        storeId, taskScheduleConfig.getScheduleType());

    // 若區間內沒有資料，將結束時間設為下一次的起始時間，結束
    if (response == null || response.getBody() == null
        || response.getBody().getRefundList() == null) {
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
      return false;
    }

    int totalSize = response.getBody().getRefundList().getRefundList().size();// 資料總筆數
    int maxSizePerPage = taskScheduleConfig.getMaxPageSize(); // 每一頁最大資料量
    int totalPage = (totalSize / maxSizePerPage) + (totalSize % maxSizePerPage == 0 ? 0 : 1);// 總頁數
    int finalPageIndex = totalPage - 1; // 最後一頁的索引值

    // 因為批量取訂單資料是一次取全部，不用倒序新增
    // 針對每一頁的資料..
    for (int i = 0; i < totalPage; i++) {
      // 取出所有退貨單
      int startIndex = i * maxSizePerPage;// 起始索引值
      int length =
          (i == finalPageIndex) ? (totalSize - maxSizePerPage * finalPageIndex) : maxSizePerPage;// 長度
      List<Refund> refunds = response.getBody().getRefundList().getRefundList().subList(startIndex,
          startIndex + length);

      // 取得退貨單最晚確認時間 (!!!退貨單沒有更新時間 只有確認時間)
      for (int j = 0; j < refunds.size(); j++) {
        Date refundConfirmTs = DateTimeTool.parse(refunds.get(j).getRefundTs());
        if (refundConfirmTs.after(modiDate)) {
          modiDate = refundConfirmTs;
        }
      }

      // 轉換成中台退貨單POJO
      List<AomsrefundT> aomsrefundTs =
          new IcbcAomsrefundTTranslator(refunds, storeId)
              .refundToAomsrefundT(refunds);

      // 存檔
      taskService.newTransaction4Save(aomsrefundTs);
    }

    // 將最晚的退貨確認時間當作下一次的起始時間
    logger.info("更新{}的lastUpdateTime为{}...", taskScheduleConfig.getScheduleType(), modiDate);
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(modiDate));
    
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
