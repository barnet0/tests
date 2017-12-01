package com.digiwin.ecims.platforms.taobao.service.ontime.update.rate;

import com.digiwin.ecims.ontime.service.BaseUpdateService;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.platforms.taobao.service.api.rate.TaobaoTbApiSyncSellerGiveRatesService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zaregoto on 2017/1/19.
 */
@Service("taobaoTbApiSellerGiveRateUpdateServiceImpl")
public class TaobaoTbApiSellerGiveRateUpdateServiceImpl implements OnTimeTaskBusiJob {

    private static final Logger logger =
        LoggerFactory.getLogger(TaobaoTbApiSellerGiveRateUpdateServiceImpl.class);

    private static final String CLASS_NAME = "TaobaoTbApiSellerGiveRateUpdateServiceImpl";

    @Autowired
    private TaobaoTbApiSyncSellerGiveRatesService taobaoTbApiSyncSellerGiveRatesService;

    @Autowired
    private BaseUpdateService baseUpdateService;

    @Override public boolean timeOutExecute(String... args) throws Exception {
        logger.info("---定时任务开始--{}--取得卖家给出评价数据---", CLASS_NAME);

        boolean result =
            baseUpdateService
                .timeOutExecute(
                    TaobaoCommonTool.UPDATE_SCHEDULE_NAME_INIT_CAPACITY,
                    TaobaoCommonTool.TB_API_SELLER_GIVE_RATE_UPDATE_SCHEDULE_NAME_PREFIX,
                    TaobaoCommonTool.STORE_TYPE,
                    180,
                    taobaoTbApiSyncSellerGiveRatesService);

        logger.info("---定时任务结束--{}--取得卖家给出评价数据---", CLASS_NAME);

        return result;
    }
}
