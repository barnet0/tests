package com.digiwin.ecims.platforms.yhd.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncOrdersService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

@Service("yhdTradeUpdateServiceImpl")
public class YhdTradeUpdateServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(YhdTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "YhdTradeUpdateServiceImpl";
  
  @Autowired
  private YhdApiSyncOrdersService yhdApiSyncOrdersService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得一号店订单数据---", CLASS_NAME);
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            YhdCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            YhdCommonTool.API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
            YhdCommonTool.STORE_TYPE,
            15, 
            yhdApiSyncOrdersService);
    
    logger.info("---定时任务结束--{}--取得一号店订单数据---", CLASS_NAME);
    
    return result;
  }
}
