package com.digiwin.ecims.platforms.jingdong.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiSyncRefundsService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("jingdongRefundOnlyUpdateServiceImpl")
public class JingdongRefundUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger =
      LoggerFactory.getLogger(JingdongRefundUpdateServiceImpl.class);

  private static final String CLASS_NAME = "JingdongRefundUpdateServiceImpl";
  
  @Autowired
  private JingdongApiSyncRefundsService jingdongApiSyncRefundsService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得京东退款数据---", CLASS_NAME);
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            JingdongCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            JingdongCommonTool.API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
            JingdongCommonTool.STORE_TYPE,
            30, 
            jingdongApiSyncRefundsService);
    
    logger.info("---定时任务结束--{}--取得京东退款数据---", CLASS_NAME);
    
    return result;
  }
}
