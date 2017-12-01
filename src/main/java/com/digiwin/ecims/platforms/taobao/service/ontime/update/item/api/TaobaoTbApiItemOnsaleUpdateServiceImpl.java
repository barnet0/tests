package com.digiwin.ecims.platforms.taobao.service.ontime.update.item.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.item.TaobaoTbApiSyncItemsOnsaleService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 使用淘宝TOP API同步销售中的商品
 * 
 * @author 维杰
 *
 */
@Service("taobaoTbApiItemOnsaleUpdateServiceImpl")
public class TaobaoTbApiItemOnsaleUpdateServiceImpl implements OnTimeTaskBusiJob {
  
  private static final Logger logger = LoggerFactory.getLogger(TaobaoTbApiItemOnsaleUpdateServiceImpl.class);

  private static final String CLASS_NAME = "TaobaoTbApiItemOnsaleUpdateServiceImpl";
  
  @Autowired
  private TaobaoTbApiSyncItemsOnsaleService taobaoTbApiSyncItemsOnsaleService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得淘宝销售中商品数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
          TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
          TaobaoCommonTool.TB_API_ONSALE_ITEM_UPDATE_SCHEDULE_NAME_PREFIX, 
          TaobaoCommonTool.STORE_TYPE,
          90,
          taobaoTbApiSyncItemsOnsaleService);
    
    logger.info("---定时任务结束--{}--取得淘宝销售中商品数据---", CLASS_NAME);
    
    return result;
  }

}
