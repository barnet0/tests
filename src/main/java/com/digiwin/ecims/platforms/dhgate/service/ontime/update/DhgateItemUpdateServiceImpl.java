package com.digiwin.ecims.platforms.dhgate.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiSyncItemsService;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("dhgateItemUpdateServiceImpl")
public class DhgateItemUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(DhgateItemUpdateServiceImpl.class);

  private static final String CLASS_NAME = "DhgateItemUpdateServiceImpl";

  @Autowired
  private DhgateApiSyncItemsService dhgateApiSyncItemsService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得敦煌网商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            DhgateCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            DhgateCommonTool.ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
            DhgateCommonTool.STORE_TYPE, 
            90, 
            dhgateApiSyncItemsService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得敦煌网商品数据---方法");
    return result;
  }

}
