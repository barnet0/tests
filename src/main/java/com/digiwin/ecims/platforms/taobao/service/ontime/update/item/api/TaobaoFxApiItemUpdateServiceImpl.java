package com.digiwin.ecims.platforms.taobao.service.ontime.update.item.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.item.TaobaoFxApiSyncItemsService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 使用淘宝TOP API同步分销商品
 * 
 * @author 维杰
 */
@Service("syncTaobaoFenxiaoProductServiceImpl")
public class TaobaoFxApiItemUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(TaobaoFxApiItemUpdateServiceImpl.class);

  private static final String CLASS_NAME = "TaobaoFxApiItemUpdateServiceImpl";
  
  @Autowired
  private TaobaoFxApiSyncItemsService taobaoFxApiSyncItemsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得淘宝分销商品数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
          TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
          TaobaoCommonTool.FX_API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX,  
          TaobaoCommonTool.STORE_TYPE_FX,
          90,
          taobaoFxApiSyncItemsService);
    
    logger.info("---定时任务结束--{}--取得淘宝分销商品数据---", CLASS_NAME);
    
    return result;
  }

}
