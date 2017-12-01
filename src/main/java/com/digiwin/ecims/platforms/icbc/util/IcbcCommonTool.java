package com.digiwin.ecims.platforms.icbc.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class IcbcCommonTool {

  public static final String STORE_TYPE = "3";
  public static final String STORE_NAME = "Icbc";
  public static final String STORE_CHN_NAME_IN_DB = "工商银行";
  
  public static final String API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

  public static final String REF_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  public static final int MAX_PAGE_SIZE = 50;
  
  public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 30;
  
  public enum RefundStatus {
    SUCCESS("8"),
    REFUSED("9"),
    CLOSED("10");

    private String status;

    private RefundStatus(String status) {
      this.status = status;
    }

    public String getStatus() {
      return status;
    }
    
  }
}
