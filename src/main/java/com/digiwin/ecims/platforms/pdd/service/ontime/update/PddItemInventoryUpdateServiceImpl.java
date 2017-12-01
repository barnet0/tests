package com.digiwin.ecims.platforms.pdd.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiSyncItemsInventoryService;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;

@Service("pddItemInventoryUpdateServiceImpl")
public class PddItemInventoryUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(PddItemInventoryUpdateServiceImpl.class);

  private static final String CLASS_NAME = "PddItemInventoryUpdateServiceImpl";

  @Autowired
  private PddApiSyncItemsInventoryService pddApiSyncItemsInventoryService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得拼多多库存商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            PddCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            PddCommonTool.ITEM_INVENTORY_UPDATE_SCHEDULE_NAME_PREFIX, 
            PddCommonTool.STORE_TYPE, 
            90, 
            pddApiSyncItemsInventoryService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得拼多多库存商品数据---方法");
    return result;
  }

}
