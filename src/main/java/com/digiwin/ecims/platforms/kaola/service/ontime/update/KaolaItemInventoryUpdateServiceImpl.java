package com.digiwin.ecims.platforms.kaola.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncItemsInventoryService;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;

@Service("kaolaItemInventoryUpdateServiceImpl")
public class KaolaItemInventoryUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(KaolaItemInventoryUpdateServiceImpl.class);

  private static final String CLASS_NAME = "kaolaItemInventoryUpdateServiceImpl";

  @Autowired
  private KaolaApiSyncItemsInventoryService kaolaApiSyncItemsInventoryService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得网易考拉库存商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            KaolaCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            KaolaCommonTool.ITEM_INVENTORY_UPDATE_SCHEDULE_NAME_PREFIX, 
            KaolaCommonTool.STORE_TYPE, 
            90, 
            kaolaApiSyncItemsInventoryService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得网易考拉库存商品数据---方法");
    return result;
  }

}
