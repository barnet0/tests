package com.digiwin.ecims.platforms.jingdong.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

public class JingdongCommonTool {

  public static final String STORE_TYPE = "1";
  public static final String STORE_NAME = "Jingdong";
  public static final String STORE_CHN_NAME_IN_DB = "京东";

  public static final String ORDER_STATUS =
      "WAIT_SELLER_STOCK_OUT,WAIT_GOODS_RECEIVE_CONFIRM,FINISHED_L,TRADE_CANCELED,LOCKED";
  public static final String ORDER_FIELDS =
      "order_id,order_source,customs,customs_model,vender_id,pay_type,"
          + "order_total_price,order_seller_price,order_payment,freight_price,seller_discount,"
          + "order_state,order_state_remark,delivery_type,invoice_info,order_remark,"
          + "order_start_time,order_end_time,modified,consignee_info,"
          + "item_info_list,coupon_detail_list,vender_remark,balance_used,payment_confirm_time,"
          + "waybill,logistics_id,vat_invoice_info,parent_order_id,pin,return_order,order_type,"
          + "store_order";
  public static final String WARE_FIELDS = "skus";

  public static final String CREATE_TIME = "1";
  public static final String UPDATE_TIME = "0";

  public static final String ORDER_SEARCH_DESCEND_SORT = "1";
  public static final String ORDER_SEARCH_ASCEND_SORT = "0";

  public static final Integer MIN_PAGE_SIZE = 1;
  public static final Integer MIN_PAGE_NO = 1;
  public static final Integer DEFAULT_PAGE_SIZE = 50;
  
  public static final String RESPONSE_SUCCESS_CODE = "0";
  public static final String RESPONSE_EXPRESS_SUCCESS_CODE = "100";

  public static final String SAL_PLAT_JINGDONG_CODE = "0010001";

  public static final int UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 50;
  
  // 30 s
  public static final int JD_API_CONNECT_TIMEOUT = 30 * 1000;
  // 300 s
  public static final int JD_API_READ_TIMEOUT = 5 * 60 * 1000;
  
  public static final String API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + "RefundOnlyUpdate";
  
  public static final String API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  public static final String API_RETURN_UPDATE_SCHEDULE_NAME_PREFIX = 
      STORE_NAME + "RefundBothUpdate";
  
  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;

  public static final String REF_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  public enum SyncBasicParm {
    ORDER(30); // 訂單
    private int maxProcessDays; // 每次排程調用, 最大可處理的天數

    SyncBasicParm(int maxProcessDays) {
      this.maxProcessDays = maxProcessDays;
    }

    public int getMaxProcessDays() {
      return maxProcessDays;
    }
  }

}
