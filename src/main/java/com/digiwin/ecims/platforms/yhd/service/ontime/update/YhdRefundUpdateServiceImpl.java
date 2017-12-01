package com.digiwin.ecims.platforms.yhd.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncRefundsService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

@Service("yhdRefundUpdateServiceImpl")
public class YhdRefundUpdateServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(YhdRefundUpdateServiceImpl.class);

  private static final String CLASS_NAME = "YhdRefundUpdateServiceImpl";
  
  @Autowired
  private YhdApiSyncRefundsService yhdApiSyncRefundsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得一号店退单数据---", CLASS_NAME);
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            YhdCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            YhdCommonTool.API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
            YhdCommonTool.STORE_TYPE,
            15, 
            yhdApiSyncRefundsService);
    
    logger.info("---定时任务结束--{}--取得一号店退单数据---", CLASS_NAME);
    
    return result;
  }
}
