package com.digiwin.ecims.platforms.yhd.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.util.UpdateTask;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiSyncRefundsAbnormalService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

@Service("yhdRefundAbnormalUpdateServiceImpl")
public class YhdRefundAbnormalUpdateServiceImpl extends UpdateTask implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(YhdRefundAbnormalUpdateServiceImpl.class);

  private static final String CLASS_NAME = "YhdRefundAbnormalUpdateServiceImpl";
  
  @Autowired
  private YhdApiSyncRefundsAbnormalService yhdApiSyncRefundsAbnormalService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得一号店异常退款订单数据---", CLASS_NAME);
    
    boolean resultSync = 
        baseUpdateService
        .timeOutExecute(
            YhdCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            YhdCommonTool.API_REFUND_ABNORMAL_UPDATE_SCHEDULE_NAME_PREFIX, 
            YhdCommonTool.STORE_TYPE,
            15, 
            yhdApiSyncRefundsAbnormalService);
    
    boolean resultLocal = yhdApiSyncRefundsAbnormalService.syncRefundsToBeChecked(); 
    
    logger.info("---定时任务结束--{}--取得一号店异常退款订单数据---", CLASS_NAME);
    
    return resultSync && resultLocal;
  }
}
