package com.digiwin.ecims.platforms.yhd.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncItemsService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

@Service("yhdItemUpdateServiceImpl")
public class YhdItemUpdateServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(YhdItemUpdateServiceImpl.class);

  private static final String CLASS_NAME = "YhdItemUpdateServiceImpl";
  
  @Autowired
  private YhdApiSyncItemsService yhdApiSyncItemsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得一号店普通商品数据---", CLASS_NAME);
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            YhdCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            YhdCommonTool.API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
            YhdCommonTool.STORE_TYPE,
            15, 
            yhdApiSyncItemsService);
    
    logger.info("---定时任务结束--{}--取得一号店普通商品数据---", CLASS_NAME);
    
    return result;
  }
}
