package com.digiwin.ecims.platforms.pdd2.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncItemsOnsaleService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;

@Service("pdd2ItemOnsaleUpdateServiceImpl")
public class Pdd2ItemOnsaleUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(Pdd2ItemOnsaleUpdateServiceImpl.class);

  private static final String CLASS_NAME = "Pdd2ItemOnsaleUpdateServiceImpl";

  @Autowired
  private Pdd2ApiSyncItemsOnsaleService pddApiSyncItemsOnsaleService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得新版拼多多在售商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            Pdd2CommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            Pdd2CommonTool.ITEM_ONSALE_UPDATE_SCHEDULE_NAME_PREFIX, 
            Pdd2CommonTool.STORE_TYPE, 
            90, 
            pddApiSyncItemsOnsaleService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得新版拼多多在售商品数据---方法");
    return result;
  }

}
