package com.digiwin.ecims.platforms.pdd2.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncOrdersService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncOrdersStatusService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;

@Service("pdd2TradeUpdateByStatusServiceImpl")
public class Pdd2TradeUpdateByStatusServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(Pdd2TradeUpdateByStatusServiceImpl.class);

  private static final String CLASS_NAME = "Pdd2TradeUpdateByStatusServiceImpl";

  @Autowired
  private Pdd2ApiSyncOrdersStatusService pdd2ApiSyncOrdersStatusService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得新版拼多多订单数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            Pdd2CommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            Pdd2CommonTool.ORDER_UPDATE_BYSTATUS_SCHEDULE_NAME_PREFIX, 
            Pdd2CommonTool.STORE_TYPE, 
            90, 
            pdd2ApiSyncOrdersStatusService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得新版拼多多订单数据---方法");
    return result;
  }

}
