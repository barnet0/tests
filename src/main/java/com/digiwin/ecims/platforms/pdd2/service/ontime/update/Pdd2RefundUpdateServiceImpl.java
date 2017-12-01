package com.digiwin.ecims.platforms.pdd2.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncItemsOnsaleService;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiSyncRefundsService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
/**
 * 退货排程入口
 * @author cjp
 *
 */
@Service("pdd2RefundUpdateServiceImpl")
public class Pdd2RefundUpdateServiceImpl implements OnTimeTaskBusiJob {
	  private static final Logger logger = LoggerFactory.getLogger(Pdd2RefundUpdateServiceImpl.class);

	  private static final String CLASS_NAME = "Pdd2RefundUpdateServiceImpl";

	  @Autowired
	  private Pdd2ApiSyncRefundsService pdd2ApiSyncRefundsService; 

	  @Autowired
	  private BaseUpdateService baseUpdateService;
	@Override
	public boolean timeOutExecute(String... args) throws Exception {
		logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得新版拼多多退货数据---方法");

	    boolean result = baseUpdateService
	        .timeOutExecute(
	            Pdd2CommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY, 
	            Pdd2CommonTool.REFUND_UPDATE_SCHEDULE_NAME_PREFIX, 
	            Pdd2CommonTool.STORE_TYPE, 
	            90, 
	            pdd2ApiSyncRefundsService); 
	    logger.info("定时任务结束-" + CLASS_NAME + "--取得新版拼多多退货数据---方法");
	    return result;
	}

}
