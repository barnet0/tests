package com.digiwin.ecims.platforms.dangdang.utils;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.util.OntimeCommonUtil;

/**
 * @author 维杰
 * @since 2015.09.25
 *
 */
public class DangdangCommonTool {

  private static DangdangCommonTool dct = new DangdangCommonTool();

  public enum DangdangApiEnum {
    DIGIWIN_ORDER_DETAIL_GET("digiwin.order.detail.get"), 
    DIGIWIN_ITEM_DETAIL_GET("digiwin.item.detail.get"), 
    DIGIWIN_REFUND_GET("digiwin.refund.get"), 
    DIGIWIN_ITEM_LISTING("digiwin.item.listing"), 
    DIGIWIN_ITEM_DELISTING("digiwin.item.delisting"), 
    DIGIWIN_SHIPPING_SEND("digiwin.shipping.send"), 
    DIGIWIN_INVENTORY_UPDATE("digiwin.inventory.update"), 
    DIGIWIN_DD_WAYBILL_GET("digiwin.dd.waybill.get"), 
    DIGIWIN_DD_SHIPPING_DDSEND("digiwin.dd.shipping.ddsend"),
                                // DANGDANG_SHOP_CARRIAGETYPE_GET("dangdang.shop.carriagetype.get"),
    DIGIWIN_DD_RECEIPT_DETAILS_LIST("digiwin.dd.receipt.details.list"), 
    DIGIWIN_DD_PICKGOODS_UPDATE("digiwin.dd.pickgoods.update"), 
    DIGIWIN_SYSTEM("digiwin.system");

    private String apiName;

    DangdangApiEnum(String apiName) {
      this.apiName = apiName;
    }

    @Override
    public String toString() {
      return this.apiName;
    }
  }

  public static final String STORE_TYPE = "5";
  public static final String STORE_NAME = "Dangdang";
  public static final String STORE_CHN_NAME_IN_DB = "当当";
  
  public static final String FULL_ORDER_STATUS = "9999";
  public static final int MIN_PAGE_SIZE = 5;
  public static final int MAX_PAGE_SIZE = 20;

  public static final String ITEM_LISTING = "上架";
  public static final String ITEM_DELISTING = "下架";

  /**
   * 记录错误信息
   */
  private final Map<DangdangApiEnum, Map<String, String>> errorDescMap;

  /**
   * @return the errorDescMap
   */
  public Map<DangdangApiEnum, Map<String, String>> getErrorDescMap() {
    return errorDescMap;
  }

  private DangdangCommonTool() {
    this.errorDescMap = new HashMap<DangdangApiEnum, Map<String, String>>();

    /*
     * 订单
     */
    Map<String, String> orderDetailGet = new HashMap<String, String>();
    orderDetailGet.put("35", "订单编号错误");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_ORDER_DETAIL_GET, orderDetailGet);

    /*
     * 铺货
     */
    Map<String, String> itemGet = new HashMap<String, String>();
    // itemGet.put("", "");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_ITEM_DETAIL_GET, orderDetailGet);

    /*
     * 退单
     */
    Map<String, String> refundGet = new HashMap<String, String>();
    // 退货
    refundGet.put("35", "订单编号错误");
    refundGet.put("46", "订单退换货类型错误");
    refundGet.put("47", "订单处理状态错误");
    refundGet.put("48", "订单处理结果错误");
    refundGet.put("49", "时间格式错误");
    refundGet.put("50", "页数错误");
    refundGet.put("1300", "申请单状态错误");
    // 退款
    refundGet.put("35", "订单编号错误");
    refundGet.put("49", "时间格式错误");
    refundGet.put("50", "页码错误");
    refundGet.put("51", "审核状态错误");
    refundGet.put("115", "开始时间不能大于结束时间");
    refundGet.put("116", "开始时间和结束时间间隔不能超过3个月");
    refundGet.put("151", "不填参数不能为空");
    refundGet.put("656", "每页结果数量错误");
    refundGet.put("1756", "操作失败");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_REFUND_GET, refundGet);

    /*
     * 上下架
     */
    Map<String, String> itemList = new HashMap<String, String>();
    itemList.put("11", "商品状态错误");
    itemList.put("17", "商品标识符错误");
    itemList.put("84", "更新状态错误。只能填写“上架”或“下架”");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_ITEM_LISTING, itemList);
    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_ITEM_DELISTING, itemList);

    /*
     * 自发货
     */
    Map<String, String> shippingSend = new HashMap<String, String>();
    shippingSend.put("35", "订单编号错误");
    shippingSend.put("39", "发货数量全为0");
    shippingSend.put("40", "物流公司、电话或物流单号出错");
    shippingSend.put("42", "发货商品数量错误");
    shippingSend.put("51", "订单状态错误");
    shippingSend.put("410", "gshopID为空");
    shippingSend.put("411", "gshopID不存在");
    shippingSend.put("605", "订单状态决定该订单不能发货");
    shippingSend.put("665", "当前有取消申请未处理，请先到当当商家后台审核。");
    shippingSend.put("1203", "计算分摊失败");
    shippingSend.put("1204", "配货完成失败");
    shippingSend.put("1205", "发货商品编号或者促销编号错误");
    shippingSend.put("1207", "订单编号全部错误");
    shippingSend.put("1208", "发货操作失败");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_SHIPPING_SEND, shippingSend);

    /*
     * 库存更新
     */
    Map<String, String> inventoryUpdate = new HashMap<String, String>();

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_INVENTORY_UPDATE, inventoryUpdate);

    /*
     * 打印快递面单
     */
    Map<String, String> ddWayBillGet = new HashMap<String, String>();
    ddWayBillGet.put("35", "订单编号错误");
    ddWayBillGet.put("40", "推荐物流的面单，快递公司或快递单号不能为空");
    ddWayBillGet.put("51", "订单不能在配货前打单");
    ddWayBillGet.put("51_1", "订单不能在发货后打单");
    ddWayBillGet.put("410", "gshopID为空");
    ddWayBillGet.put("500", "更新订单isShippingStatus失败");
    ddWayBillGet.put("1206", "订单不属于该商家");
    ddWayBillGet.put("1207", "订单编号全部错误");
    ddWayBillGet.put("1701", "商家编号不合法");
    ddWayBillGet.put("2207", "参数不合法：orderId超过100个");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_DD_WAYBILL_GET, ddWayBillGet);

    /*
     * 当当代发
     */
    Map<String, String> ddSend = new HashMap<String, String>();
    ddSend.put("35", "订单状态决定该订单不能发货");
    ddSend.put("45", "订单编号数量过多 (目前支持50以内)");
    ddSend.put("51", "订单状态错误");
    ddSend.put("410", "gshopID为空");
    ddSend.put("665", "当前有取消申请未处理，请先到当当商家后台审核。 ");
    ddSend.put("720", "运输方式参数不正确");
    ddSend.put("1207", "订单编号全部错误");
    ddSend.put("1208", "发货操作失败");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_DD_SHIPPING_DDSEND, ddSend);

    /*
     * 查询订单的快递面单信息
     */
    Map<String, String> receiptDetailsList = new HashMap<String, String>();
    receiptDetailsList.put("35", "订单编号错误");
    receiptDetailsList.put("51", "订单状态错误");
    receiptDetailsList.put("232", "操作失败");
    receiptDetailsList.put("410", "gshopID为空");
    receiptDetailsList.put("640", "顾客所在市没有对应库房");
    receiptDetailsList.put("1207", "订单编号全部错误");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_DD_RECEIPT_DETAILS_LIST, receiptDetailsList);

    /*
     * 配货
     */
    Map<String, String> pickGoodsUpdate = new HashMap<String, String>();
    pickGoodsUpdate.put("35", "订单编号错误");
    pickGoodsUpdate.put("39", "发货数量全为0");
    pickGoodsUpdate.put("40", "物流公司、电话或物流单号出错");
    pickGoodsUpdate.put("42", "发货商品数量错误");
    pickGoodsUpdate.put("51", "订单状态错误 ");
    pickGoodsUpdate.put("410", "gshopID为空");
    pickGoodsUpdate.put("411", "gshopID不存在");
    pickGoodsUpdate.put("605", "订单状态决定该订单不能发货");
    pickGoodsUpdate.put("665", "当前有取消申请未处理，请先到当当商家后台审核");
    pickGoodsUpdate.put("1203", "计算分摊失败");
    pickGoodsUpdate.put("1204", "配货完成失败");
    pickGoodsUpdate.put("1205", "发货商品编号或者促销编号错误");
    pickGoodsUpdate.put("1207", "订单编号全部错误");
    pickGoodsUpdate.put("1208", "发货操作失败");

    this.errorDescMap.put(DangdangApiEnum.DIGIWIN_DD_PICKGOODS_UPDATE, pickGoodsUpdate);
  }

  public static DangdangCommonTool getInstance() {
    return DangdangCommonTool.dct;
  }

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
}
