package com.digiwin.ecims.platforms.taobao.service.ontime.update.refund.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.refund.TaobaoTbApiSyncRefundsService;
import com.digiwin.ecims.platforms.taobao.service.ontime.update.trade.api.TaobaoTbApiTradeUpdateServiceImpl;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 使用淘宝TOP API同步淘宝与天猫退单
 * @author 维杰
 *
 */
@Service("taobaoTbApiRefundUpdateServiceImpl")
public class TaobaoTbApiRefundUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(TaobaoTbApiTradeUpdateServiceImpl.class);

  private static final String CLASS_NAME = "TaobaoTbApiRefundUpdateServiceImpl";
  
  @Autowired
  private TaobaoTbApiSyncRefundsService taobaoTbApiSyncRefundsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得淘宝退单数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
          TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
          TaobaoCommonTool.TB_API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
          TaobaoCommonTool.STORE_TYPE,
          90,
          taobaoTbApiSyncRefundsService);
    
    logger.info("---定时任务结束--{}--取得淘宝退单数据---", CLASS_NAME);
    
    return result;
  }

}
