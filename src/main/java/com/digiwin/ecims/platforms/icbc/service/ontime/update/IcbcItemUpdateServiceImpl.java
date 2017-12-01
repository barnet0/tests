package com.digiwin.ecims.platforms.icbc.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiSyncItemsService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("icbcItemUpdateServiceImpl")
public class IcbcItemUpdateServiceImpl implements OnTimeTaskBusiJob {
  private static final Logger logger = LoggerFactory.getLogger(IcbcItemUpdateServiceImpl.class);

  private static final String CLASS_NAME = "IcbcItemUpdateServiceImpl";
  
  @Autowired
  private IcbcApiSyncItemsService icbcApiSyncItemsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得工商银行融e购商品数据---", CLASS_NAME);
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            IcbcCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            IcbcCommonTool.API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
            IcbcCommonTool.STORE_TYPE,
            90, 
            icbcApiSyncItemsService);
    
    logger.info("---定时任务结束--{}--取得工商银行融e购商品数据---", CLASS_NAME);
    
    return result;
  }
}
