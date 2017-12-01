package com.digiwin.ecims.platforms.taobao.service.ontime.update.trade.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.order.TaobaoTbApiSyncOrdersService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 使用淘宝TOP API同步淘宝与天猫订单
 * @author 维杰
 *
 */
@Service("taobaoTbApiTradeUpdateServiceImpl")
public class TaobaoTbApiTradeUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(TaobaoTbApiTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "TaobaoTbApiTradeUpdateServiceImpl";
  
  @Autowired
  private TaobaoTbApiSyncOrdersService taobaoTbApiSyncOrdersService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得淘宝订单数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
            TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            TaobaoCommonTool.TB_API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
            TaobaoCommonTool.STORE_TYPE,
            90, 
            taobaoTbApiSyncOrdersService);
    
    logger.info("---定时任务结束--{}--取得淘宝订单数据---", CLASS_NAME);
    
    return result;
  }

}
