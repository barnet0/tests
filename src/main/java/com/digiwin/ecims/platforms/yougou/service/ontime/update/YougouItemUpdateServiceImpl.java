package com.digiwin.ecims.platforms.yougou.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiSyncItemsService;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;

@Service("yougouItemUpdateServiceImpl")
public class YougouItemUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(YougouItemUpdateServiceImpl.class);

  private static final String CLASS_NAME = "YougouItemUpdateServiceImpl";

  @Autowired
  private YougouApiSyncItemsService yougouApiSyncItemsService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得优购网商品数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            YougouCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            YougouCommonTool.ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
            YougouCommonTool.STORE_TYPE, 
            90, 
            yougouApiSyncItemsService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得优购网商品数据---方法");
    return result;
  }

}
