package com.digiwin.ecims.platforms.taobao.service.ontime.update.trade.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.order.TaobaoFxApiSyncOrdersService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 使用淘宝TOP API同步分销订单
 * @author 维杰
 *
 */
@Service("taobaoFxApiTradeUpdateServiceImpl")
public class TaobaoFxApiTradeUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(TaobaoFxApiTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "TaobaoFxApiTradeUpdateServiceImpl";
  
  @Autowired
  private TaobaoFxApiSyncOrdersService taobaoFxApiSyncOrdersService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得淘宝分销订单数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
          TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
          TaobaoCommonTool.FX_API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
          TaobaoCommonTool.STORE_TYPE_FX,
          90, 
          taobaoFxApiSyncOrdersService);
    
    logger.info("---定时任务结束--{}--取得淘宝分销订单数据---", CLASS_NAME);
    
    return result;
  }

}
