package com.digiwin.ecims.platforms.kaola.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiSyncOrdersSignedService;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;
@Service("kaolaOrderSignedUpdateServiceImpl")
public class KaolaOrderSignedUpdateServiceImpl implements OnTimeTaskBusiJob {
	  private static final Logger logger = LoggerFactory.getLogger(KaolaOrderSignedUpdateServiceImpl.class);

	  private static final String CLASS_NAME = "KaolaOrderSignedUpdateServiceImpl";

	  @Autowired
	  private KaolaApiSyncOrdersSignedService kaolaApiSyncOrdersSignedService;

	  @Autowired
	  private BaseUpdateService baseUpdateService;
	  
	  @Override
	  public boolean timeOutExecute(String... args) throws Exception {
	    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得考拉已签收订单数据---方法");

	    boolean result = baseUpdateService
	        .timeOutExecute(
	            KaolaCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
	            KaolaCommonTool.SIGNED_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
	            KaolaCommonTool.STORE_TYPE, 
	            90, 
	            kaolaApiSyncOrdersSignedService);

	    logger.info("定时任务结束-" + CLASS_NAME + "--取得考拉已签收订单数据---方法");
	    return result;
	  }
}
