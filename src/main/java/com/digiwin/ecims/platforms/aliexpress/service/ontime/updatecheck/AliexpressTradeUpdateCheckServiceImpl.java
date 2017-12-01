package com.digiwin.ecims.platforms.aliexpress.service.ontime.updatecheck;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.service.CheckUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJobForDataCycleRun;
import com.digiwin.ecims.ontime.util.InetAddressTool;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.system.service.PushOrderDataByHandService;
import com.digiwin.ecims.system.service.SyncOrderDataByHandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

@Service("aliexpressTradeUpdateCheckServiceImpl")
@Scope("prototype")
public class AliexpressTradeUpdateCheckServiceImpl extends UpdateTask
    implements OnTimeTaskBusiJobForDataCycleRun {

  private static final Logger logger =
      LoggerFactory.getLogger(AliexpressTradeUpdateCheckServiceImpl.class);

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private CheckUpdateService checkUpdateService;

  private String scheduleUpdateCheckType = "";

  private String[] reCycleDate;

  /*
   * 校验实现类
   */
  @Resource(name = "syncAliexpressOrderDataByHandServiceImpl")
  private SyncOrderDataByHandService syncAliexpressOrderDataByHandService;

  @Resource(name = "pushAliexpressOrderDataByHandServiceImpl")
  private PushOrderDataByHandService pushAliexpressOrderDataByHandService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-AliexpressTradeUpdateCheckServiceImpl--速卖通校验数据---方法");
    try {

      List<AomsshopT> aomsShopList =
          aomsShopService.getStoreByStoreType(AliexpressCommonTool.STORE_TYPE);
      if (aomsShopList != null && aomsShopList.size() != 0) {
        for (int i = 0; i < aomsShopList.size(); i++) {
          executeUpdateCheckForDataCycleRun(aomsShopList.get(i));
        }
      }
    } catch (ParseException e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "AliexpressTradeUpdateCheckServiceImpl",
          LogInfoBizTypeEnum.ECI_REQUEST_CHECK.getValueString(), DateTimeTool.parse(reCycleDate[0]),
          "json数据转换异常", e.getMessage(), scheduleUpdateCheckType, "");
      e.printStackTrace();
      logger.error("ParseException = {}", e.getMessage());
      throw e;
    } catch (Exception e) {
      loginfoOperateService.newTransaction4SaveLog(InetAddressTool.getLocalIpv4(),
          "AliexpressTradeUpdateCheckServiceImpl",
          LogInfoBizTypeEnum.ECI_REQUEST_CHECK.getValueString(), DateTimeTool.parse(reCycleDate[0]),
          "获取数据异常", e.getMessage(), scheduleUpdateCheckType, "");
      e.printStackTrace();
      logger.error("Exception = {}", e.getMessage());
      throw e;
    }
    logger.info("定时任务结束-AliexpressTradeUpdateCheckServiceImpl--速卖通校验数据---方法");
    return false;
  }

  @Override
  public void setCheckDate(String[] reCycleDate) {
    this.reCycleDate = reCycleDate;
  }

  @Override
  public void setScheduleUpdateCheckType(String scheduleUpdateCheckType) {
    this.scheduleUpdateCheckType = scheduleUpdateCheckType;
  }

  @Override
  public void executeUpdateCheckForDataCycleRun(AomsshopT aomsshopT) throws Exception {
    String startDate = "";
    String endDate = "";
    if (reCycleDate != null) {
      // 取得執行區間
      startDate = this.reCycleDate[0];
      endDate = this.reCycleDate[1];
    }
    // 平台只提供按创建时间查询订单
    executeCheckLogic(aomsshopT, startDate, endDate, UseTimeEnum.CREATE_TIME);
  }

  private void executeCheckLogic(AomsshopT aomsshopT, String startDate, String endDate,
      UseTimeEnum orderUseTime) throws Exception {
    String storeId = aomsshopT.getAomsshop001();
    long ecCount = 0L;
    long dbCount = 0L;

    // 依據創建時間取出電商資料總笔數
    ecCount = syncAliexpressOrderDataByHandService.findOrderCountFromEcByCreateDate(
        this.scheduleUpdateCheckType, aomsshopT.getAomsshop001(), startDate, endDate);
    // 依據創建時間取出中台資料總笔數
    dbCount = pushAliexpressOrderDataByHandService
        .findOrderDataCountFromDBByCreateDate(aomsshopT.getAomsshop001(), startDate, endDate);

    // 用來 記錄 Check_Service_ReCall_T 的關聯key. add by xavier on 20150909
    checkUpdateService.put(startDate, endDate, scheduleUpdateCheckType,
        loginfoOperateService.newTransaction4SaveCheckServiceRecord(
            this.getClass().getSimpleName() + "|" + orderUseTime, startDate, endDate,
            ecCount - dbCount, AliexpressCommonTool.STORE_TYPE, storeId, scheduleUpdateCheckType));
    if (ecCount != dbCount) {
      syncAliexpressOrderDataByHandService.syncOrderDataByCreateDate(aomsshopT.getAomsshop001(),
          startDate, endDate);

    }
    // 用來 記錄 Check_Service_ReCall_T 的關聯key. add by xavier on 20150909
    checkUpdateService.remove(startDate, endDate, scheduleUpdateCheckType);
  }
}
