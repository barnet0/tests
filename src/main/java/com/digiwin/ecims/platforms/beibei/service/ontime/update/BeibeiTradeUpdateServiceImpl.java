package com.digiwin.ecims.platforms.beibei.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiSyncOrdersService;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("beibeiTradeUpdateServiceImpl")
public class BeibeiTradeUpdateServiceImpl implements OnTimeTaskBusiJob {
  
  private static final Logger logger = LoggerFactory.getLogger(BeibeiTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "BeibeiTradeUpdateServiceImpl";

  @Autowired
  private BeibeiApiSyncOrdersService beibeiApiSyncOrdersService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得贝贝网订单数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            BeibeiCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            BeibeiCommonTool.ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
            BeibeiCommonTool.STORE_TYPE, 
            30, 
            beibeiApiSyncOrdersService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得贝贝网订单数据---方法");
    return result;
  }

}
