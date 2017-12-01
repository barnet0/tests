package com.digiwin.ecims.platforms.pdd.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class PddCommonTool {

  public static final String STORE_TYPE = "J";
  public static final String STORE_NAME = "Pdd";
  public static final String STORE_CHN_NAME_IN_DB = "拼多多";
  
  public static final Integer RESPONSE_SUCCESS_CODE = 1;
  public static final Integer RESPONSE_FAILURE_CODE = 0;
  
  public static final Integer MIN_PAGE_NO = 0;
  public static final Integer MIN_PAGE_SIZE = 1;
  public static final Integer DEFAULT_PAGE_SIZE = 50;
  public static final Integer MAX_PAGE_SIZE = 100;
  
  public static final String ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String ITEM_ONSALE_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + "Onsale" + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String ITEM_INVENTORY_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + "Inventory" + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;

  
  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 25;
  
  public static final String SKU_SPEC_DELIMITER = " ";
  
  public enum OrderStatus {
    PAIED(1),WAIT_TO_PAY(0),PROBLEM_ISSUE(-1);
    
    private Integer value;

    OrderStatus(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }
  }
  
  public enum ItemStatus {
    ONSALE("Onsale"), INSTOCK("InStock");
    
    private String type;

    private ItemStatus(String type) {
      this.type = type;
    }

    public String getType() {
      return type;
    }
  }
}
