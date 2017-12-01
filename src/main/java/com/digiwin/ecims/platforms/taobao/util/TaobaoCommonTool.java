package com.digiwin.ecims.platforms.taobao.util;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

/**
 * @author 维杰
 *
 */
public class TaobaoCommonTool {

  public static final String STORE_TYPE = "0";
  public static final String STORE_TYPE_FX = "A";
  
  public static final String STORE_NAME = "TaobaoTb";
  public static final String STORE_NAME_FX = "TaobaoFx";

  public static final String REFUND_TYPE_NOT_FX = "1";
  public static final String REFUND_TYPE_FX = "0";

  public static final String B_SELLER_TYPE_IN_DB = "天猫";
  public static final String C_SELLER_TYPE_IN_DB = "淘宝";
  public static final String FX_SELLER_TYPE_IN_DB = "淘宝分销";

  public static final String PIC_RELATIVE_PATH_DELIMITER = "imgextra";

  public static final Long INVENTORY_FULL_UPDATE_TYPE = 1l;
  public static final Long INVENTORY_INCR_UPDATE_TYPE = 2l;
  
  public static final Long MIN_PAGE_NO = 1L;
  public static final Long MIN_PAGE_SIZE = 1L;
  public static final Long DEFAULT_PAGE_SIZE = 50L;
  
  public static final String SKU_QUANTITY_UPDATE_INNER_DELIMITER = ":";
  public static final String SKU_QUANTITY_UPDATE_OUTER_DELIMITER = ";";

  public static final String API_ORDER_FIELDS =
      "tid,type,status,trade_from,price,created,modified,end_time,trade_memo,"
          + "buyer_message,buyer_memo,seller_memo,buyer_flag,seller_flag,title,seller_nick,"
          + "num,pic_path,adjust_fee,discount_fee,alipay_no,payment,pay_time,buyer_nick,buyer_email,"
          + "buyer_rate,buyer_alipay_no,buyer_obtain_point_fee,point_fee,real_point_fee,total_fee,"
          + "shipping_type,post_fee,receiver_name,receiver_state,receiver_city,receiver_district,"
          + "receiver_address,receiver_zip,receiver_mobile,receiver_phone,consign_time,commission_fee,"
          + "seller_alipay_no,seller_mobile,seller_phone,seller_name,seller_email,seller_rate,"
          + "available_confirm_fee,invoice_title,orders.oid,orders.num_iid,orders.sku_id,"
          + "orders.sku_properties_name,orders.num,orders.title,orders.price,orders.pic_path,"
          + "orders.outer_iid,orders.outer_sku_id,orders.total_fee,orders.discount_fee,orders.adjust_fee,"
          + "orders.payment,orders.status,orders.refund_status,orders.refund_id,orders.shipping_type,"
          + "receiver_town,has_yfx,orders.divide_order_fee,orders.part_mjz_discount,step_trade_status,step_paid_fee";
  
  public static final String API_ORDER_MIN_FIELDS = "tid,modified";

  public static final String API_FX_ORDER_FIELDS =
      /*"id,trade_type,status,supplier_from,total_fee,created,modified,end_time,memo,"
          + "supplier_memo,supplier_flag,supplier_username,alipay_no,distributor_payment,"
          + "pay_type,pay_time,distributor_username,total_fee,shipping,logistics_company_name,"
          + "post_fee,receiver.name,receiver.state,receiver.city,receiver.district,"
          + "receiver.address,receiver.zip,receiver.mobile_phone,receiver.phone,consign_time,"
          + "sub_purchase_orders.id,sub_purchase_orders.item_id,"
          + "sub_purchase_orders.sku_id,sub_purchase_orders.item_outer_id,"
          + "sub_purchase_orders.sku_properties,sub_purchase_orders.num,sub_purchase_orders.title,"
          + "sub_purchase_orders.price,sub_purchase_orders.sc_item_id,sub_purchase_orders.sku_outer_id,"
          + "sub_purchase_orders.total_fee,sub_purchase_orders.tc_discount_fee,"
          + "sub_purchase_orders.tc_adjust_fee,sub_purchase_orders.distributor_payment,"
          + "sub_purchase_orders.status,tc_order_id,logistics_id,sub_purchase_orders.refund_fee,"
          + "sub_purchase_orders.fenxiao_id,sub_purchase_orders.order_200_status,"
          + "sub_purchase_orders.promotion_type,sub_purchase_orders.discount_fee,"
          + "sub_purchase_orders.auction_sku_id,sub_purchase_orders.auction_id,"
          + "sub_purchase_orders.buyer_payment";*/
      "";

  public static final String API_FX_ORDER_MIN_FIELDS = "id";
  
  /**
   * 获取单个退单时用的字段
   */
  public static final String API_REFUND_FIELDS = 
      "address,advance_status,alipay_no,attribute,buyer_nick,company_name,created,cs_status,"
      + "desc,good_return_time,good_status,has_good_return,modified,num,num_iid,oid,"
      + "operation_contraint,order_status,outer_id,payment,price,reason,refund_fee,"
      + "refund_id,refund_phase,refund_remind_timeout,refund_version,seller_nick,"
      + "shipping_type,sid,sku,split_seller_fee,split_taobao_fee,status,tid,title,total_fee";

  /**
   * 获取退单列表时用的字段
   */
  public static final String API_REFUNDS_FIELDS = 
      "attribute,buyer_nick,company_name,created,desc,good_status,has_good_return,"
      + "modified,num,oid,operation_contraint,order_status,outer_id,payment,reason,"
      + "refund_fee,refund_id,refund_phase,refund_version,seller_nick,sid,sku,status,"
      + "tid,title,total_fee";
  
  /**
   * 获取退单列表时用的最小字段
   */
  public static final String API_REFUNDS_MIN_FIELDS = "refund_id,modified";

  // mark by mowj 20160705
//  public static final String API_ITEM_FIELDS =
//      "num_iid,outer_id,title,property_alias,sku.sku_id,sku.outer_id,sku.properties_name,approve_status,"
//          + "modified,detail_url,pic_url,created,list_time,delist_time,sku.quantity,sku.modified";

  // add by mowj 20160705
  public static final String API_ITEM_FIELDS =
      "num_iid,outer_id,title,property_alias,approve_status,"
          + "modified,detail_url,pic_url,created,list_time,delist_time,with_hold_quantity,"
          + "sku.sku_id,sku.properties,sku.quantity,sku.price,"
          + "sku.created,sku.modified,sku.status,sku.properties_name,"
          + "sku.outer_id,sku.barcode";
  
  public static final String API_ITEM_MIN_FIELDS = "num_iid,modified";
  
  public static final String API_FX_ITEM_FIELDS = "skus";
  
  public static final String API_FX_ITEM_MIN_FIELDS = "modified";
  
  public static final String API_TRADE_RATES_FIELDS = 
      "tid,oid,role,nick,result,reply,created,rated_nick,item_title,item_price,content,"
      + "reply,num_iid";

  public static final String API_TRADE_RATES_MIN_FILEDS = "tid";

  public static final String JST_UPDATE_SCHEDULE = "Jst";
  
  public static final String API_UPDATE_SCHEDULE = "Api";
  
  /**
   * 淘宝聚石塔同步排程名称前缀.订单<br>
   * TaobaoTbJstTradeUpdate
   */
  public static final String TB_JST_ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + JST_UPDATE_SCHEDULE + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝聚石塔同步排程名称前缀.退单<br>
   * TaobaoTbJstRefundUpdate
   */
  public static final String TB_JST_REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + JST_UPDATE_SCHEDULE + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝聚石塔同步排程名称前缀.商品<br>
   * TaobaoTbJstItemUpdate
   */
  public static final String TB_JST_ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + JST_UPDATE_SCHEDULE + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝聚石塔同步排程名称前缀.分销订单<br>
   * TaobaoFxJstTradeUpdate
   */
  public static final String FX_JST_ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME_FX + JST_UPDATE_SCHEDULE + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝聚石塔同步排程名称前缀.分销退单<br>
   * TaobaoFxJstRefundUpdate
   */
  public static final String FX_JST_REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME_FX + JST_UPDATE_SCHEDULE + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝TOP API同步排程名称前缀.订单<br>
   * TaobaoTbApiTradeUpdate
   */
  public static final String TB_API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + API_UPDATE_SCHEDULE + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝TOP API同步排程名称前缀.退单<br>
   * TaobaoTbApiRefundUpdate
   */
  public static final String TB_API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + API_UPDATE_SCHEDULE + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝TOP API同步排程名称前缀.销售中的商品<br>
   * TaobaoTbApiOnsaleItemUpdate
   */
  public static final String TB_API_ONSALE_ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + API_UPDATE_SCHEDULE + "Onsale" + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝TOP API同步排程名称前缀.库存中的商品<br>
   * TaobaoTbApiInventoryItemUpdate
   */
  public static final String TB_API_INVENORY_ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + API_UPDATE_SCHEDULE + "Inventory" + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;

  /**
   * 淘宝TOP API同步排程名称前缀.分销订单<br>
   * TaobaoFxApiTradeUpdate
   */
  public static final String FX_API_ORDER_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME_FX + API_UPDATE_SCHEDULE + OntimeCommonUtil.ORDER_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝TOP API同步排程名称前缀.分销退单<br>
   * TaobaoFxApiRefundUpdate
   */
  public static final String FX_API_REFUND_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME_FX + API_UPDATE_SCHEDULE + OntimeCommonUtil.REFUND_UPDATE_SCHEDULE_NAME_SUFFIX;
  
  /**
   * 淘宝TOP API同步排程名称前缀.分销商品<br>
   * TaobaoFxApiItemUpdate
   */
  public static final String FX_API_ITEM_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME_FX + API_UPDATE_SCHEDULE + OntimeCommonUtil.ITEM_UPDATE_SCHEDULE_NAME_SUFFIX;

  /**
   * 淘宝TOP API同步排程名称前缀.卖家给出的评价<br>
   * TaobaoTbApiTradeUpdate
   */
  public static final String TB_API_SELLER_GIVE_RATE_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + API_UPDATE_SCHEDULE + "SellerGiveRateUpdate";

  /**
   * 淘宝TOP API同步排程名称前缀.卖家收到的评价<br>
   * TaobaoTbApiTradeUpdate
   */
  public static final String TB_API_SELLER_GET_RATE_UPDATE_SCHEDULE_NAME_PREFIX =
      STORE_NAME + API_UPDATE_SCHEDULE + "SellerGetRateUpdate";


  /**
   * 淘宝推送排程名称前缀.订单
   */
  public static final String ORD_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  /**
   * 淘宝推送排程名称前缀.退单
   */
  public static final String REF_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  /**
   * 淘宝推送排程名称前缀.商品
   */
  public static final String ITE_POST_SCHEDULE_TYPE =
      STORE_NAME + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE;
  
  /**
   * 淘宝推送排程名称前缀.分销订单
   */
  public static final String FX_ORD_POST_SCHEDULE_TYPE =
      STORE_NAME_FX + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsordT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE_FX;
  
  /**
   * 淘宝推送排程名称前缀.分销退单
   */
  public static final String FX_REF_POST_SCHEDULE_TYPE =
      STORE_NAME_FX + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsrefundT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE_FX;
  
  /**
   * 淘宝推送排程名称前缀.分销商品
   */
  public static final String FX_ITE_POST_SCHEDULE_TYPE =
      STORE_NAME_FX + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + AomsitemT.BIZ_NAME
          + OntimeCommonUtil.SCHEDULE_NAME_DELIMITER + STORE_TYPE_FX;

  public static final Integer UPDATE_SCHEDULE_NAME_INIT_CAPACITY = 50;
  
  /**
   * 当请求数量无法正常返回时，返回-1
   */
  public static final Long NO_COUNT_RETURNED = -1L;
  
  public enum FxTradesGetTimeType {
    CREATE_TIME("trade_time_type"),
    UPDATE_TIME("update_time_type");
    
    private String type;
    private FxTradesGetTimeType(String type) {
      this.type = type;
    }
    
    public String getType() {
      return type;
    }
    
  }
  
  public enum TaobaoTradeRateResult {
    
    GOOD("1", "good"),
    NEUTRAL("0", "neutral"),
    BAD("-1", "bad");
    
    private String ecimsValue;
    private String taobaoValue;
    
    private TaobaoTradeRateResult(String ecimsValue, String taobaoValue) {
      this.ecimsValue = ecimsValue;
      this.taobaoValue = taobaoValue;
    }

    public String getEcimsValue() {
      return ecimsValue;
    }

    public String getTaobaoValue() {
      return taobaoValue;
    }
    
  }
  
  public enum TaobaoTradeRateRole {
    SELLER("seller"), 
    BUYER("buyer");
    
    private String role;

    private TaobaoTradeRateRole(String role) {
      this.role = role;
    }

    public String getRole() {
      return role;
    }
    
  }
  
  public enum TaobaoTradeRateType {
    GET("get"),
    GIVE("give");
    
    private String rateType;

    private TaobaoTradeRateType(String rateType) {
      this.rateType = rateType;
    }

    public String getRateType() {
      return rateType;
    }
    
  }
  
  public enum FxProductStatus {

    LISTING("up"),
    DELISTING("down"),
    DELETE("delete");
    
    private String status;
    private FxProductStatus(String status) {
      this.status = status;
    }
    public String getStatus() {
      return status;
    }
  }
  
//  public static void main(String[] args) {
//    String s = "shipping_type,cs_status,advance_status,split_taobao_fee,split_seller_fee,refund_id,tid,oid,alipay_no,total_fee,buyer_nick,seller_nick,created,modified,order_status,status,good_status,has_good_return,refund_fee,payment,reason,desc,title,price,num,good_return_time,company_name,sid,address,refund_remind_timeout,num_iid,refund_phase,refund_version,sku,attribute,outer_id,operation_contraint";
//    java.util.Map<String, String> map = new java.util.TreeMap<String, String>();
//    String[] ss = s.split(",");
//    for (String string : ss) {
//      map.put(string, string);
//    }
//    java.lang.StringBuffer sb = new StringBuffer();
//    for (String string : map.keySet()) {
//      sb.append(string).append(",");
//      
//    }
//    
//    System.out.println(sb.substring(0, sb.length() - 1));
//    
//  }
  
}
