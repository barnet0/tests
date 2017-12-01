package com.digiwin.ecims.platforms.yougou.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class YougouCommonTool {

  public static final String STORE_TYPE = "G";
  public static final String STORE_NAME = "Yougou";
  public static final String STORE_CHN_NAME_IN_DB = "优购";

  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  public static final String REF_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

  public static final String ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;

  public static final int UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 30;

  public static final int MIN_PAGE_NO = 1;
  public static final int MIN_PAGE_SIZE = 1;
  public static final int DEFAULT_PAGE_SIZE = 50;
  public static final int MAX_PAGE_SIZE = DEFAULT_PAGE_SIZE;

  public static final String RESPONSE_SUCCESS_CODE = "200";
  public static final String SYSTEM_ERROR_CODE = "500";

  public enum ORDER_STATUS {
    WAIT_SELLER_SEND_GOODS("1"), FINISHED("2"), APPLY_FOR_INTERCEPT("3"), ABNORMAL("4");

    private String value;

    private ORDER_STATUS(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }


  }
}
