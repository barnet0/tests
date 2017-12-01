package com.digiwin.ecims.platforms.beibei.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiSyncItemsService;
import com.digiwin.ecims.platforms.beibei.util.BeibeiCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("beibeiItemUpdateServiceImpl")
public class BeibeiItemUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(BeibeiItemUpdateServiceImpl.class);

  private static final String CLASS_NAME = "BeibeiItemUpdateServiceImpl";

  @Autowired
  private BeibeiApiSyncItemsService beibeiApiSyncItemsService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得贝贝网商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            BeibeiCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            BeibeiCommonTool.ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
            BeibeiCommonTool.STORE_TYPE, 
            90, 
            beibeiApiSyncItemsService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得贝贝网商品数据---方法");
    return result;
  }

}
