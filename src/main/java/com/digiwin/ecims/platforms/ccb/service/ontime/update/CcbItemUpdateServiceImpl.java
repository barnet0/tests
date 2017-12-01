package com.digiwin.ecims.platforms.ccb.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.ccb.service.api.CcbApiSyncItemsService;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("ccbItemUpdateServiceImpl")
public class CcbItemUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(CcbTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "CcbItemUpdateServiceImpl";

  @Autowired
  private CcbApiSyncItemsService ccbApiSyncItemsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得建设银行商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            CcbCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            CcbCommonTool.ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
            CcbCommonTool.STORE_TYPE, 
            30, 
            ccbApiSyncItemsService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得建设银行商品数据---方法");
    return result;
  }

}
