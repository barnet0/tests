package com.digiwin.ecims.platforms.jingdong.service.ontime.update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiSyncReturnsService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;

@Service("jingdongRefundBothUpdateServiceImpl")
public class JingdongReturnUpdateServiceImpl implements OnTimeTaskBusiJob {

  private static final Logger logger =
      LoggerFactory.getLogger(JingdongReturnUpdateServiceImpl.class);

  private static final String CLASS_NAME = "JingdongReturnUpdateServiceImpl";
  
  @Autowired
  private JingdongApiSyncReturnsService jingdongApiSyncReturnsService;

  @Autowired
  private BaseUpdateService baseUpdateService;

  @Override
  public boolean timeOutExecute(String... args) throws Exception {
    logger.info("---定时任务开始--{}--取得京东服务单数据---", CLASS_NAME);

    boolean result = 
        baseUpdateService
        .timeOutExecute(
            JingdongCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            JingdongCommonTool.API_RETURN_UPDATE_SCHEDULE_NAME_PREFIX,  
            JingdongCommonTool.STORE_TYPE,
            30, 
            jingdongApiSyncReturnsService);
    
    logger.info("---定时任务结束--{}--取得京东服务单数据---", CLASS_NAME);
    
    return result;
  }
}
