package com.digiwin.ecims.platforms.yhd.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

/**
 * 
 * @author 维杰
 * @since 2015.08.28
 */
public class YhdCommonTool {

  public static final String STORE_TYPE = "2";
  public static final String STORE_NAME = "Yhd";
  public static final String STORE_CHN_NAME_IN_DB = "一号店";

  public static final String ORDER_STATUS =
      "ORDER_WAIT_PAY,ORDER_PAYED,ORDER_WAIT_SEND,ORDER_ON_SENDING,ORDER_RECEIVED,ORDER_FINISH,ORDER_CANCEL";

  /**
   * 列表参数的内容分隔符
   */
  public static final String LIST_DELIMITER = ",";
  public static final Integer MAX_ORDER_CODE_LENGTH = 50;
  public static final Integer MAX_PRODUCT_CODE_LENGTH = 100;

  public static final String STOCK_UPDATE_INNER_DELIMITER = ":";
  public static final String STOCK_UPDATE_OUTER_DELIMITER = LIST_DELIMITER;

  public static final Integer DEFAULT_WARE_HOUSE_CODE = 1;

  /**
   * 订单日期类型
   * @author 维杰
   *
   */
  public enum OrderDateType {
    CREATE_TIME(1), PAY_TIME(2), SEND_TIME(3), RECEIVE_TIME(4), UPDATE_TIME(5);

    private Integer dateType;

    OrderDateType(Integer dateType) {
      this.dateType = dateType;
    }

    public Integer getDateType() {
      return dateType;
    }
  }

  /**
   * 异常订单退款日期类型
   * @author 维杰
   *
   */
  public enum OrderRefundAbnormalDateType {
    APPLY_TIME(1), APPROVE_TIME(2);
    
    private Integer value;

    OrderRefundAbnormalDateType(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }
  }
  
  public enum RefundDateType {
    APPLY_TIME(1), UPDATE_TIME(2);
    
    private Integer value;

    RefundDateType(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }
  }
  
  public enum ApiPageParam {
    MIN_PAGE_SIZE(1), MAX_PAGE_SIZE(100), DEFAULT_PAGE_SIZE(50),
    MIN_PAGE_NO(1);

    private Integer value;

    ApiPageParam(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }
  }

  public enum RefundOrderStatus {
    CANCELLED(34, "已取消"), REFUSED(4, "已拒绝"), TO_BE_CHECKED(0, "待审核"), REFUNDED(27, "已退款");

    private String description;
    private Integer code;

    RefundOrderStatus(Integer code, String description) {
      this.code = code;
      this.description = description;
    }

    public String getDescription() {
      return description;
    }

    public Integer getCode() {
      return code;
    }

    @Override
    public String toString() {
      return this.code + ":" + this.description;
    }
  }

  public enum InventoryUpdateType {
    FULL(1), INCREMENT(2);

    private int value;

    InventoryUpdateType(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  
  public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 30;
  
  public static final String API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String API_REFUND_ABNORMAL_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + "RefundAbnormalUpdate";
  
  public static final String API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String API_SERIAL_ITEM_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + "Serial" + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

  public static final String REF_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
}
