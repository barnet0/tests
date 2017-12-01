package com.digiwin.ecims.platforms.icbc.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiHelperService;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiSyncRefundsService;
import com.digiwin.ecims.platforms.icbc.util.IcbcCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("icbcRefundUpdateServiceImpl")
public class IcbcRefundUpdateServiceImpl implements OnTimeTaskBusiJob {
  private static final Logger logger = LoggerFactory.getLogger(IcbcRefundUpdateServiceImpl.class);

  private static final String CLASS_NAME = "IcbcRefundUpdateServiceImpl";
  
  @Autowired
  private IcbcApiSyncRefundsService icbcApiSyncRefundsService;
  
  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Autowired
  private IcbcApiHelperService icbcApiHelperService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得工商银行融e购退单数据---", CLASS_NAME);
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            IcbcCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            IcbcCommonTool.API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
            IcbcCommonTool.STORE_TYPE,
            90, 
            icbcApiSyncRefundsService);
    
    logger.info("---更新本地工商银行融e购退单数据---");
    boolean result2 = 
        icbcApiHelperService.updateLocalRefunds(IcbcCommonTool.STORE_TYPE);
    
    logger.info("---定时任务结束--{}--取得工商银行融e购退单数据---", CLASS_NAME);
    
    return result && result2;
  }

}
