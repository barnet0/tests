package com.digiwin.ecims.platforms.ccb.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class CcbCommonTool {

  public static final String STORE_TYPE = "I";
  public static final String STORE_NAME = "Ccb";
  public static final String STORE_CHN_NAME_IN_DB = "建设银行";


  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
//  public static final String REF_POST_SCHEDULE_TYPE =
//      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
//          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
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
  public static final int MAX_PAGE_SIZE = 100;

  public static final String RESPONSE_SUCCESS_CODE = "000000";
  public static final String RESPONSE_FAILURE_CODE = "000001";
  
  public enum ItemInventoryUpdateTypeEnum {
    FULLY(0), INCREMENTAL(1);

    private int type;

    private ItemInventoryUpdateTypeEnum(int type) {
      this.type = type;
    }

    public int getType() {
      return type;
    }

  }

  public enum OrderStatusEnum {

    // (等待买家付款)
    WAIT_BUYER_PAY("WAIT_BUYER_PAY"),
    // (等待卖家发货,即:买家已付款)
    WAIT_SELLER_SEND_GOODS("WAIT_SELLER_SEND_GOODS"),
    // (等待买家确认收货,即:卖家已发货)
    WAIT_BUYER_CONFIRM_GOODS("WAIT_BUYER_CONFIRM_GOODS"),
    // (交易成功)
    TRADE_FINISHED("TRADE_FINISHED"),
    // (交易关闭)
    TRADE_CLOSED("TRADE_CLOSED"),
    // (交易取消)
    TRADE_CANCELLED("TRADE_CANCELLED"),
    // (订单删除)
    ORDER_DELETED("ORDER_DELETED"),
    // (等待卖家退款,未发货)
    WAIT_SELLER_REFUND_SEND_GOODS("WAIT_SELLER_REFUND_SEND_GOODS"),
    // (等待卖家退款,已发货)
    WAIT_SELLER_REFUND_CONFIRM_GOODS("WAIT_SELLER_REFUND_CONFIRM_GOODS"),
    // (部分付款)
    PART_PAY("PART_PAY"),
    // (部分退款)
    PART_REFUND("PART_REFUND"),
    // (部分确认付款)
    PART_AFFIRM_PAY("PART_AFFIRM_PAY");

    private String orderStatus;

    private OrderStatusEnum(String orderStatus) {
      this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
      return orderStatus;
    }

  }

  public enum ItemStatusEnum {
    // 未审核
    UNAPPROVED("01"),
    // 审核中
    APPROVING("02"),
    // 已删除
    DELETED("03"),
    // 待上架
    TO_BE_SHELVED("04"),
    // 已上架
    ON_SHELF("05"),
    // 审核未通过
    NOT_APPROVED("06");

    private String status;

    private ItemStatusEnum(String status) {
      this.status = status;
    }

    public String getStatus() {
      return status;
    }

  }
  
  public enum CcbSyncParamEnum {
    
    ITEM_SYNC_HAS_NEXT("0"),
    ITEM_SYNC_NO_MORE("1");
    
    private String value;
    private CcbSyncParamEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }
    
    
  }
}
