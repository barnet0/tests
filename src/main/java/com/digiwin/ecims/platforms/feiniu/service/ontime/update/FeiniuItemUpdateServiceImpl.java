package com.digiwin.ecims.platforms.feiniu.service.ontime.update;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiSyncItemsService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zaregoto on 2017/1/25.
 */
@Service("feiniuItemUpdateServiceImpl") public class FeiniuItemUpdateServiceImpl
    implements OnTimeTaskBusiJob {

    private static final Logger logger = LoggerFactory.getLogger(FeiniuItemUpdateServiceImpl.class);

    private static final String CLASS_NAME = FeiniuItemUpdateServiceImpl.class.getSimpleName();

    @Autowired private FeiniuApiSyncItemsService feiniuApiSyncItemsService;

    @Autowired private BaseUpdateService baseUpdateService;

    @Override public boolean timeOutExecute(String... args) throws Exception {
        logger.info("定时任务开始--进入-" + CLASS_NAME + "--取得贝贝网商品数据---方法");

        boolean result = baseUpdateService
            .timeOutExecute(
                FeiniuCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
                FeiniuCommonTool.ITEM_UPDATE_SCHEDULE_NAME_PREFIX,
                FeiniuCommonTool.STORE_TYPE,
                90,
                feiniuApiSyncItemsService);

        logger.info("定时任务结束-" + CLASS_NAME + "--取得贝贝网商品数据---方法");

        return result;
    }
}
