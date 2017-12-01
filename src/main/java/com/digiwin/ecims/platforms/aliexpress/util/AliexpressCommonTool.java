package com.digiwin.ecims.platforms.aliexpress.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class AliexpressCommonTool {

  public static final String STORE_TYPE = "H";
  public static final String STORE_NAME = "Aliexpress";
  public static final String STORE_CHN_NAME_IN_DB = "速卖通";

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
  public static final int MAX_PAGE_SIZE = 50;
  public static final int DEFAULT_PAGE_SIZE = MAX_PAGE_SIZE;

  public static final String VALUE_DELIMITER = ";";
  
  public static final int COLOR_PID = 14;
  public static final int SIZE_PID = 5;
  
  
  // public enum OrderStatusEnum {
  //// 等待买家付款
  // PLACE_ORDER_SUCCESS("PLACE_ORDER_SUCCESS"),
  //// 买家申请取消
  // IN_CANCEL("IN_CANCEL"),
  //// 等待您发货
  // WAIT_SELLER_SEND_GOODS("WAIT_SELLER_SEND_GOODS"),
  //// 部分发货
  // SELLER_PART_SEND_GOODS("SELLER_PART_SEND_GOODS"),
  //// 等待买家收货
  // WAIT_BUYER_ACCEPT_GOODS("WAIT_BUYER_ACCEPT_GOODS"),
  //// 买卖家达成一致，资金处理中
  // FUND_PROCESSING("FUND_PROCESSING"),
  //// 含纠纷中的订单
  // IN_ISSUE("IN_ISSUE"),
  //// 冻结中的订单
  // IN_FROZEN("IN_FROZEN"),
  //// 等待您确认金额
  // WAIT_SELLER_EXAMINE_MONEY("WAIT_SELLER_EXAMINE_MONEY"),
  //// 订单处于风控24小时中，从买家在线支付完成后开始，持续24小时
  // RISK_CONTROL("RISK_CONTROL"),
  //// 已结束的订单，需单独查询。
  // FINISH("FINISH");
  //
  // private String orderStatus;
  //
  // private OrderStatusEnum(String orderStatus) {
  // this.orderStatus = orderStatus;
  // }
  //
  // public String getOrderStatus() {
  // return orderStatus;
  // }
  // }
  public enum OrderStatusEnum {
    // 已结束的订单，需单独查询。且量比较少，先用它来更新排程的lastUpdateTime
    FINISH("FINISH"),
    // 其他状态
    OTHER("");

    private String orderStatus;

    private OrderStatusEnum(String orderStatus) {
      this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
      return orderStatus;
    }
  }
  
  public enum ItemStatusTypeEnum {
//    上架
    ON_SELLING("onSelling"),
//    下架
    OFFLINE("offline"),
//    审核中
    AUDITING("auditing"),
//    审核不通过
    EDITING_REQUIRED("editingRequired");
    
    private String status;
    private ItemStatusTypeEnum(String status) {
      this.status = status;
    }
    public String getStatus() {
      return status;
    }
    
    
  }
}
