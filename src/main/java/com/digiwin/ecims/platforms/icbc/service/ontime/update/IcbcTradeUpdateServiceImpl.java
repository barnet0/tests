package com.digiwin.ecims.platforms.icbc.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiSyncOrdersService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("icbcTradeUpdateServiceImpl")
public class IcbcTradeUpdateServiceImpl implements OnTimeTaskBusiJob {
  private static final Logger logger = LoggerFactory.getLogger(IcbcTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "IcbcTradeUpdateServiceImpl";
  
  @Autowired
  private IcbcApiSyncOrdersService icbcApiSyncOrdersService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得工商银行融e购订单数据---", CLASS_NAME);
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            IcbcCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            IcbcCommonTool.API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
            IcbcCommonTool.STORE_TYPE,
            90, 
            icbcApiSyncOrdersService);
    
    logger.info("---定时任务结束--{}--取得工商银行融e购订单数据---", CLASS_NAME);
    
    return result;
  }
}
