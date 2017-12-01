package com.digiwin.ecims.platforms.feiniu.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

/**
 * Created by zaregoto on 2017/1/25.
 */
public class FeiniuCommonTool {

    public static final String STORE_TYPE = "L";
    public static final String STORE_NAME = "Feiniu";
    public static final String STORE_CHN_NAME_IN_DB = "飞牛";

    public static final Long MIN_PAGE_NO = 1L;
    public static final Long MIN_PAGE_SIZE = 1L;
    public static final Long DEFAULT_PAGE_SIZE = 40L;
    public static final Long MAX_PAGE_SIZE = 100L;

    public static final String ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
        STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
    public static final String REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
        STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
    public static final String RETURN_UPDATE_SCHEDULE_NAME_PREFIX =
        STORE_NAME + OntimeCommonUtil.RETURN_UPDATE_SCHEDULE_NAME_SUFFIX;
    public static final String ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
        STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;

    public static final String ORD_POST_SCHEDULE_TYPE =
        STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
            + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
    public static final String REF_POST_SCHEDULE_TYPE =
        STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
            + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;;
    public static final String ITE_POST_SCHEDULE_TYPE =
        STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
            + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

    public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 25;

    // 30 s
    public static final int FN_API_CONNECT_TIMEOUT = 30 * 1000;
    // 300 s
    public static final int FN_API_READ_TIMEOUT = 5 * 60 * 1000;

    public static final String RESPONSE_SUCCESS_CODE = "100";

    public static final String RESPONSE_SUCCESS_MSG = "success";

    public static final String ITEM_FIELDS = "createTime,updateTime";
    /**
     * 当请求数量无法正常返回时，返回-1
     */
    public static final Long NO_COUNT_RETURNED = -1L;

    public static final String PROPS_DELIMITER = ";";
}
