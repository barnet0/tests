package com.digiwin.ecims.platforms.feiniu.service.ontime.update;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiSyncOrdersService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zaregoto on 2017/1/25.
 */
@Service("feiniuTradeUpdateServiceImpl") public class FeiniuTradeUpdateServiceImpl
    implements OnTimeTaskBusiJob {

    private static final Logger logger =
        LoggerFactory.getLogger(FeiniuTradeUpdateServiceImpl.class);

    private static final String CLASS_NAME = FeiniuTradeUpdateServiceImpl.class.getSimpleName();

    @Autowired private FeiniuApiSyncOrdersService feiniuApiSyncOrdersService;

    @Autowired private BaseUpdateService baseUpdateService;

    @Override public boolean timeOutExecute(String... args) throws Exception {
        logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得飞牛网订单数据---方法");

        boolean result = baseUpdateService
            .timeOutExecute(FeiniuCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
                FeiniuCommonTool.ORDER_UPDATE_SCHEDULE_NAME_PREFIX, FeiniuCommonTool.STORE_TYPE, 90,
                feiniuApiSyncOrdersService);

        logger.info("定时任务结束-" + CLASS_NAME + "--取得飞牛网订单数据---方法");

        return result;
    }
}
