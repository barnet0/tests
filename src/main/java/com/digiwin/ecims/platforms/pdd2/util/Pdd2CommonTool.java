package com.digiwin.ecims.platforms.pdd2.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class Pdd2CommonTool {

  public static final String STORE_TYPE = "J";
  public static final String STORE_NAME = "Pdd";
  public static final String STORE_CHN_NAME_IN_DB = "拼多多";
  
  //public static final Integer RESPONSE_SUCCESS_CODE = 1;
  public static final Boolean RESPONSE_SUCCESS_CODE = true;
  public static final Integer RESPONSE_FAILURE_CODE = 0;
  public static final Integer RESPONSE_DEFAULT_CODE = null;
  
  public static final Integer MIN_PAGE_NO = 1;     //mod by cjp 20170510 0->1
  public static final Integer MIN_PAGE_SIZE = 1;
  public static final Integer DEFAULT_PAGE_SIZE = 50;
  public static final Integer MAX_PAGE_SIZE = 100;
  
  public static final String ORDER_UPDATE_BYSTATUS_SCHEDULE_NAME_PREFIX =
      STORE_NAME +"ByStatus"+ OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  public static final String ORDER_UPDATE_BYINCREMENT_SCHEDULE_NAME_PREFIX =
	      STORE_NAME + "ByIncrement"+OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
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
  
//add by cjp 20170509 REF_POST_SCHEDULE_TYPE
	public static final String REF_POST_SCHEDULE_TYPE = STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER
			+ AomsrefundT.BIZ_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
	// add by cjp 20170510 REFUND_UPDATE_SCHEDULE_NAME_PREFIX
	public static final String REFUND_UPDATE_SCHEDULE_NAME_PREFIX = STORE_NAME
			+ OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
	
  public enum OrderStatus {
    PAIED(1),WAIT_TO_SIGN(2),SIGNED(3),ALL(4);
    
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
  
  public enum ItemStatusInt {
	    ONSALE(1), INSTOCK(0);
	    
	    private Integer value;

	    private ItemStatusInt(Integer value) {
	      this.value = value;
	    }

	    public Integer getValue() {
	      return value;
	    }
	  }
  
  public enum RefundStatus {
	  AFTERSALES(1),AFTERSALEING(2),REFUNDING(3),REFUNDED(4),ALL(5);
	    
	    private Integer value;

	    RefundStatus(Integer value) {
	      this.value = value;
	    }

	    public Integer getValue() {
	      return value;
	    }
	  }
  
  public enum LuckyFlag {
	  COMMON(1),LUCKY(2);
	    
	    private Integer value;

	    LuckyFlag(Integer value) {
	      this.value = value;
	    }

	    public Integer getValue() {
	      return value;
	    }
	  }
}
