package com.digiwin.ecims.platforms.kaola.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

/**
 * 
 * @author cjp 2017/5/18 mod
 *
 */
public class KaolaCommonTool {

  public static final String STORE_TYPE = "E";
  public static final String STORE_NAME = "Kaola";
  public static final String STORE_CHN_NAME_IN_DB = "网易考拉";
  
  public static final Integer RESPONSE_SUCCESS_CODE = 1;
  public static final Integer RESPONSE_FAILURE_CODE = 0;
  
  public static final Integer MIN_PAGE_NO = 1;     
  public static final Integer MIN_PAGE_SIZE = 1;
  public static final Integer DEFAULT_PAGE_SIZE = 20;
  public static final Integer MAX_PAGE_SIZE = 100;
  
//  public static final String ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
//      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  // 已取消订单更新排程的设定前缀名
  public static final String CANCELLED_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
		  STORE_NAME + "Cancelled" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  

  // 已支付订单更新排程的设定前缀名
  public static final String DELIVERED_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
		  STORE_NAME + "Delivered" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  

  // 已发货订单更新排程的设定前缀名
  public static final String PAIED_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
		  STORE_NAME + "Paied" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  

  // 已签收订单更新排程的设定前缀名
  public static final String SIGNED_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
		  STORE_NAME + "Signed" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  // 取消确认订单更新排程的设定前缀名
  public static final String WAITTOCANCEL_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
		  STORE_NAME + "WaitToCancel" + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
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
    PAIED(1),DELIVERED(2),SIGNED(3),WAIT_TO_CANCEL(5),CANCELED(6);
    
    private Integer value;

    OrderStatus(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }
  }
  
  public enum OrderDataType {
	    PAYT(1),DELIVERT(2),SIGNT(3),WAIT_TO_DELIVERT(4),WAIT_TO_CANCELT(5);
	    
	    private Integer value;

	    OrderDataType(Integer value) {
	      this.value = value;
	    }

	    public Integer getValue() {
	      return value;
	    }
	  }
  
  public enum ItemStatus {
    ONSALE(5),Inventory(6);
    
    private Integer value;

    private ItemStatus(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }
  }
}
