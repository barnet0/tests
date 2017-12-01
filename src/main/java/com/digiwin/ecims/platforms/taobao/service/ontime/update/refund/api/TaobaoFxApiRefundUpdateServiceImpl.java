package com.digiwin.ecims.platforms.taobao.service.ontime.update.refund.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.refund.TaobaoFxApiSyncRefundsService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;

/**
 * 使用淘宝TOP API同步分销退单
 * @author 维杰
 *
 */
@Service("taobaoFxApiRefundUpdateServiceImpl")
public class TaobaoFxApiRefundUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(TaobaoFxApiRefundUpdateServiceImpl.class);

  private static final String CLASS_NAME = "TaobaoFxApiRefundUpdateServiceImpl";
  
  @Autowired
  private TaobaoFxApiSyncRefundsService taobaoFxApiSyncRefundsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得淘宝退单数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
          TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
          TaobaoCommonTool.FX_API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
          TaobaoCommonTool.STORE_TYPE_FX,
          90, 
          taobaoFxApiSyncRefundsService);
    
    logger.info("---定时任务结束--{}--取得淘宝退单数据---", CLASS_NAME);
    
    return result;
  }

}
