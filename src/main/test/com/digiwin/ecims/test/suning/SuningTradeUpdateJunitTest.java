package com.digiwin.ecims.test.suning;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.test.JUnitTestWithLog4jRunner;
import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.CheckUpdateService;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiSyncOrdersService;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;

@RunWith(JUnitTestWithLog4jRunner.class)
@ContextConfiguration(locations = {
    "classpath:config/applicationContext-*.xml"})
public class SuningTradeUpdateJunitTest {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(SuningTradeUpdateJunitTest.class);

  @Autowired
  private SuningApiSyncOrdersService suningApiSyncOrdersService;

  @Autowired
  private BaseUpdateService baseUpdateService;
  //  
  @Before
  public void setUp() throws Exception {
    ApplicationContext ctx = MySpringContext.getContext();
    ParamSystemService initSystemParam = ctx.getBean(ParamSystemService.class);
    
    boolean isSuccess = initSystemParam.initParamToCache();
    if(!isSuccess){
      LOGGER.error("systemParam:系统参数加载失败");
    }
    LOGGER.info("systemParam:系统参数加载完毕。。。。。");
    
    LOGGER.info("systemParam:标准省市区参数开始加载。。。。。");
    StandardAreaService initAreaData = ctx.getBean(StandardAreaService.class);
    isSuccess = initAreaData.saveAreaToCache();
    if (!isSuccess) {
      LOGGER.error("systemParam:标准省市区参数加载失败");
    }
    LOGGER.info("systemParam:标准省市区参数加载完毕。。。。。");
    
    //清空停機前所記載的資訊. add by xavier on 20150909
    LOGGER.info("checkUpdateParam:準備清空。。。。。");
    CheckUpdateService checkUpdateParam = ctx.getBean(CheckUpdateService.class);
    if(!checkUpdateParam.initParamToCache()) {
      LOGGER.error("checkUpdateParam:清空失败");
    }
    LOGGER.info("checkUpdateParam:準備清空完畢。。。。。");
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() throws Exception {
//    fail("Not yet implemented");
    
    boolean result = 
        baseUpdateService
        .timeOutExecute(
            SuningCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
            SuningCommonTool.API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX, 
            SuningCommonTool.STORE_TYPE,
            90, 
            suningApiSyncOrdersService);
    
    
    Assert.assertEquals(true, result);
  }

}
