package com.digiwin.ecims.platforms.yunji.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiSyncRefundsAcceptService;
import com.digiwin.ecims.platforms.yunji.util.YunjiCommonTool;

@Service("yunjiRefundAcceptRefundUpdateServiceImpl")
public class YunjiRefundAcceptRefundUpdateServiceImpl implements OnTimeTaskBusiJob{
	  private static final Logger logger = LoggerFactory.getLogger(YunjiRefundAcceptRefundUpdateServiceImpl.class);

	  private static final String CLASS_NAME = "YunjiRefundAcceptRefundUpdateServiceImpl";

	  @Autowired
	  private YunjiApiSyncRefundsAcceptService yunjiApiSyncRefundsAcceptService;

	  @Autowired
	  private BaseUpdateService baseUpdateService;
	  
	  @Override
	  public boolean timeOutExecute(String... args) throws Exception {
	    logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得云集货售后数据---方法");

	    boolean result = baseUpdateService
	        .timeOutExecute(
	            YunjiCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
	            YunjiCommonTool.ACCEPT_REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
	            YunjiCommonTool.STORE_TYPE, 
	            90, 
	            yunjiApiSyncRefundsAcceptService);

	    logger.info("定时任务结束-" + CLASS_NAME + "--取得云集货售后数据---方法");
	    return result;
	  }
}
