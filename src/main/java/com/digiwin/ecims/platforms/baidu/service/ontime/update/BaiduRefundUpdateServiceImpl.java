package com.digiwin.ecims.platforms.baidu.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiSyncRefundsService;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("baiduRefundUpdateServiceImpl")
public class BaiduRefundUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger = LoggerFactory.getLogger(BaiduRefundUpdateServiceImpl.class);

  private static final String CLASS_NAME = "BaiduRefundUpdateServiceImpl";

  @Autowired
  private BaiduApiSyncRefundsService baiduApiSyncRefundsService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  
  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得百度Mall退单数据---方法");

    boolean result = baseUpdateService
        .timeOutExecute(
            BaiduCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
            BaiduCommonTool.REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
            BaiduCommonTool.STORE_TYPE,
            90, 
            baiduApiSyncRefundsService);

    logger.info("定时任务结束-" + CLASS_NAME + "--取得百度Mall退单数据---方法");
    return result;
  }

}
