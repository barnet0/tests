package com.digiwin.ecims.platforms.aliexpress.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiSyncItemsService;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("aliexpressItemUpdateServiceImpl")
public class AliexpressItemUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger =
      LoggerFactory.getLogger(AliexpressItemUpdateServiceImpl.class);

  private static final String CLASS_NAME = "AliexpressItemUpdateServiceImpl";

  @Autowired
  private AliexpressApiSyncItemsService aliexpressApiSyncItemsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得速卖通商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            AliexpressCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            AliexpressCommonTool.ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
            AliexpressCommonTool.STORE_TYPE, 
            90, 
            aliexpressApiSyncItemsService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得速卖通商品数据---方法");
    return result;
  }

}
