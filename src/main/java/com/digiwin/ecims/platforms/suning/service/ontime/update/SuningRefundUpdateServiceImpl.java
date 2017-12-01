package com.digiwin.ecims.platforms.suning.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiSyncRefundsService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;

@Service("suningRefundUpdateServiceImpl")
public class SuningRefundUpdateServiceImpl implements OnTimeTaskBusiJob {
  
  private static final Logger logger = 
      LoggerFactory.getLogger(SuningRefundUpdateServiceImpl.class);
  
  private static final String CLASS_NAME = "SuningRefundUpdateServiceImpl";
  
  @Autowired
  private SuningApiSyncRefundsService suningApiSyncRefundsService;

  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得苏宁退款退货数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
            SuningCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            SuningCommonTool.API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX,  
            SuningCommonTool.STORE_TYPE,
            90, 
            suningApiSyncRefundsService);
    
    logger.info("---定时任务结束--{}--取得苏宁退款退货数据---", CLASS_NAME);
    
    return result;
    
  }
}
