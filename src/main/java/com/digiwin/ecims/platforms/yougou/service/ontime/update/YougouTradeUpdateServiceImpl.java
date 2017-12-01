package com.digiwin.ecims.platforms.yougou.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiSyncOrdersService;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;

@Service("yougouTradeUpdateServiceImpl")
public class YougouTradeUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(YougouTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "YougouTradeUpdateServiceImpl";

  @Autowired
  private YougouApiSyncOrdersService yougouApiSyncOrdersService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得优购网订单数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            YougouCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            YougouCommonTool.ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
            YougouCommonTool.STORE_TYPE, 
            90, 
            yougouApiSyncOrdersService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得优购网订单数据---方法");
    return result;
  }

}
