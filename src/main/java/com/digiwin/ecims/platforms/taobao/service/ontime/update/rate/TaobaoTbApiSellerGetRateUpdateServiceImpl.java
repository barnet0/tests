package com.digiwin.ecims.platforms.taobao.service.ontime.update.rate;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.rate.TaobaoTbApiSyncSellerGetRatesService;
import com.digiwin.ecims.platforms.taobao.service.api.rate.TaobaoTbApiSyncSellerGiveRatesService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zaregoto on 2017/1/19.
 */
@Service("taobaoTbApiSellerGetRateUpdateServiceImpl")
public class TaobaoTbApiSellerGetRateUpdateServiceImpl implements OnTimeTaskBusiJob {

    private static final Logger logger =
        LoggerFactory.getLogger(TaobaoTbApiSellerGetRateUpdateServiceImpl.class);

    private static final String CLASS_NAME = "TaobaoTbApiSellerGetRateUpdateServiceImpl";

    @Autowired
    private TaobaoTbApiSyncSellerGetRatesService taobaoTbApiSyncSellerGetRatesService;

    @Autowired
    private BaseUpdateService baseUpdateService;

    @Override public boolean timeOutExecute(String... args) throws Exception {
        logger.info("---定时任务开始--{}--取得卖家得到评价数据---", CLASS_NAME);

        boolean result =
            baseUpdateService
                .timeOutExecute(
                    TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
                    TaobaoCommonTool.TB_API_SELLER_GET_RATE_UPDATE_SCHEDULE_NAME_PREFIX,
                    TaobaoCommonTool.STORE_TYPE,
                    180,
                    taobaoTbApiSyncSellerGetRatesService);

        logger.info("---定时任务结束--{}--取得卖家得到评价数据---", CLASS_NAME);

        return result;
    }
}
