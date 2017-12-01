package com.digiwin.ecims.platforms.suning.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suning.api.entity.item.ItemQueryResponse.ItemQuery;
import com.suning.api.entity.item.ItemdetailQueryResponse;
import com.suning.api.entity.item.ItemdetailQueryResponse.ChildItemdetailQuery;
import com.suning.api.entity.item.ItemdetailQueryResponse.ItemdetailQuery;
import com.suning.api.entity.item.ItemsaleQueryResponse.ItemSaleParams;
import com.suning.api.entity.item.ProductParam;
import com.suning.api.entity.rejected.BatchrejectedQueryResponse;
import com.suning.api.entity.rejected.Rejected;
import com.suning.api.entity.rejected.SinglerejectedGetResponse;
import com.suning.api.entity.transaction.OrdQueryResponse;
import com.suning.api.entity.transaction.OrderGetResponse;
import com.suning.api.entity.transaction.OrderQueryResponse;

import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.enums.RegEnums;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;
import com.digiwin.ecims.platforms.suning.bean.OrderCollection;

/**
 * 將回傳回來的資料, 轉換成 DB bean
 * 
 * @author Xavier
 *
 */
public final class SuningTranslatorTool {
  
  private static final Logger logger = LoggerFactory.getLogger(SuningTranslatorTool.class);

  private static SuningTranslatorTool stt;

  private StandardAreaService standardAreaService =
      MySpringContext.getContext().getBean(StandardAreaService.class);

  private SuningTranslatorTool() {}

  public static SuningTranslatorTool getInstance() {
    if (stt == null) {
      stt = new SuningTranslatorTool();
    }
    return stt;
  }

  // public static void main(String[] args) {
  // String kk = "0";
  // if (kk.matches("[0-9]*|[0-9]*.[0-9]*")) {
  // System.out.println(kk);
  // } else {
  // System.out.println("no");
  // }
  // }

  /**
   * suning.custom.ord.query 根据订单修改时间批量查询订单信息
   * 
   * @param storeId 商店碼
   * @param res 回傳的資料
   * @param modiDate 計算最後更新時間
   * @return
   * @throws Exception
   */
  public List<AomsordT> transOrdQueryToAomsordTBean(String storeId, OrderCollection res,
      Date modiDate, String orderLineStatusChangeTime) throws Exception {
    List<AomsordT> data = new ArrayList<AomsordT>();
    List<?> returnList = res.getOrderQuery();
    if (returnList == null) {
      return data; // 查無資料
    }
    for (Object og : returnList) {
      data.addAll(this.doTransToAomsordTBean(storeId, og, modiDate, orderLineStatusChangeTime));
    }
    return data;
  }

  /**
   * suning.custom.order.get 单笔订单查询
   * 
   * @param storeId 商店碼
   * @param res 回傳的資料
   * @return
   * @throws Exception
   */
  public List<AomsordT> transOrderGetToAomsordTBean(String storeId, OrderGetResponse res, 
      String orderLineStatusChangeTime)
      throws Exception {
    OrderGetResponse.OrderGet og = res.getSnbody().getOrderGet();
    if (og == null) {
      return Collections.emptyList();
    }
    return this.doTransToAomsordTBean(storeId, og, null, orderLineStatusChangeTime);
  }

  /**
   * 
   * @param storeId
   * @param og
   * @param modiDate 传入的最后修改时间，会被修改
   * @return
   * @throws ReflectiveOperationException
   */
  private List<AomsordT> doTransToAomsordTBean(String storeId, Object og, Date modiDate, 
      String orderLineStatusChangeTime)
      throws ReflectiveOperationException {

    List<AomsordT> data = new ArrayList<AomsordT>();
    SuningCommonTool sct = SuningCommonTool.getInstance();
    SuningCommonTool row = SuningCommonTool.getInstance();

    List<?> details = null;
    if (og instanceof OrderQueryResponse.OrderQuery) {
      details = ((OrderQueryResponse.OrderQuery) og).getOrderDetail();
    } else if (og instanceof OrdQueryResponse.OrderQuery) {
      details = ((OrdQueryResponse.OrderQuery) og).getOrderDetail();
    } else if (og instanceof OrderGetResponse.OrderGet) { // 單筆的
      details = ((OrderGetResponse.OrderGet) og).getOrderDetail();
    }

    double aoms020 = 0; // 整单优惠金额
    double aoms022 = 0; // 支付金额
    double aoms035 = 0; // 物流费用
    double aomsord078 = 0; // 平台优惠
    double aomsord035 = 0; // 物流费用

    for (Object oodRow : details) {

      final double transportFee; // 下方計算, 會用到
      if (row.getBeanValue(oodRow, "transportFee").matches(RegEnums.FEE.getRegExpString())) {
        // 需要将全部单身orderQuery.transportFee相加
        transportFee = Double.parseDouble(row.getBeanValue(oodRow, "transportFee"));
        aoms035 += transportFee;
      } else {
        transportFee = 0;
      }

      AomsordT at = new AomsordT();
      at.setId(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "orderCode"))); // aomsord001
      at.setAoms003(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "orderTotalStatus")));
      // 苏宁的订单渠道有出现过：MOBILE|01|01|4.0.0|20000C|IPHONE 6 PLUS (A1522/A1524),MOBILE|01|01|4.0.0|20000
      // modify by mowj 20160503. 目前栏位长度已经从40->100
      at.setAoms004(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "orderchannel")));
      at.setAoms006(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "orderSaleTime")));
      
   // orderQuery.orderDetail.orderLineStatusChangeTime（仅在调用suning.custom.ord.query时才会有返回值）
//      at.setModified(
//          CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "orderLineStatusChangeTime")));
      at.setModified(CommonUtil.checkNullOrNot(orderLineStatusChangeTime));
      
      // mark by mowj 20160822
//      at.setAoms010(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "orderRemark")));
      at.setAoms010(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "buyerOrdRemark")));
//      at.setAoms012(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "memoInformation")));
      at.setAoms012(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "sellerOrdRemark")));

      /*
       * 調整logic aomsord023 支付方式 = orderQuery.orderDetail.paymentList.banktypecode 取得順序為：
       * 4148-易付宝支付、1001-门店付款、5002-礼品卡支付、6901-券或云钻支付
       * 
       * add by xavier on 20150827
       */
      at.setAoms023(CommonUtil.checkNullOrNot(
          this.getPaymentCodeForAomsord023(row.getBeanListValue(oodRow, "paymentList"))));

      at.setAoms024(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "orderSaleTime"))); // 苏宁没有订单支付时间，使用订单创建时间代替
      at.setAoms025(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "userName")));
      // logger.info("userName in order {} is {}",CommonUtil.checkNullOrNot(sct.getBeanValue(og,
      // "orderCode")), CommonUtil.checkNullOrNot(sct.getBeanValue(og, "userName"))); // for test

      // 20150825 aomsord030 = sum(orderQuery.orderDetail.paymentList.payamount),
      // 取banktypecode='8012'时的值 add by xavier
      at.setAoms030(CommonUtil.checkNullOrNot(
          this.getOrderPaymentData(row.getBeanListValue(oodRow, "paymentList"), "8012")));

      // 20150825 aomsord024 = orderQuery.orderDetail.expresscompanycode add by xavier.
      at.setAoms034(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "expresscompanycode")));

      at.setAoms036(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "customerName")));

      // modi by mowj 20150726 省市区淘宝标准化
      AreaResponse standardArea =
          standardAreaService.getStandardAreaNameByKeyWord(sct.getBeanValue(og, "provinceName"),
              sct.getBeanValue(og, "cityName"), sct.getBeanValue(og, "districtName"));
      if (standardArea != null) {
        String standardProvince = standardArea.getProvince();
        String standardCity = standardArea.getCity();
        String standardDistrict = standardArea.getDistrict();

        at.setAoms037(CommonUtil.checkNullOrNot(standardProvince));// aomsord037
        at.setAoms038(CommonUtil.checkNullOrNot(standardCity));// aomsord038
        at.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));// aomsord039

      } else {
        at.setAoms037(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "provinceName")));
        at.setAoms038(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "cityName")));
        at.setAoms039(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "districtName")));
      }
      at.setAoms040(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "customerAddress")));
      at.setAoms042(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "mobNum")));
      at.setAoms053(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "invoiceHead")));
      at.setAoms054(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "invoice")));
      
      at.setStoreId(CommonUtil.checkNullOrNot(storeId));
      at.setStoreType(SuningCommonTool.STORE_TYPE);
      
      at.setAoms059(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "productCode")));
//    modi by mowj 20150727 修改060栏位（SKUID）为商品编号，即和059栏位相同
      at.setAoms060(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "productCode")));
      at.setAoms058(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "orderLineNumber"))); // 子訂單號

      // FXIME 未完成.....若回傳回來的資料, 不是數值格式的處理.
      double aoms062 = 0;
      if (row.getBeanValue(oodRow, "saleNum").matches(RegEnums.FEE.getRegExpString())) {
        aoms062 = Double.parseDouble(row.getBeanValue(oodRow, "saleNum"));
      }

      // add by mowj 20150730 start
      // 遇到满100减50这种优惠时，它返回的unitprice不正确。
      // 正确的商品单价算法是：（子订单付款金额+优惠单金额）/ 子订单商品数量
      double realUnitPrice = 0;
      double realTotalPrice = 0;
      if (row.getBeanValue(oodRow, "payAmount").matches(RegEnums.FEE.getRegExpString())
          && row.getBeanValue(oodRow, "vouchertotalMoney").matches(RegEnums.FEE.getRegExpString())
          && row.getBeanValue(oodRow, "coupontotalMoney").matches(RegEnums.FEE.getRegExpString()) 
          && row.getBeanValue(oodRow, "saleNum").matches(RegEnums.FEE.getRegExpString())) {
        realTotalPrice = Double.parseDouble(row.getBeanValue(oodRow, "payAmount"))
            + Double.parseDouble(row.getBeanValue(oodRow, "vouchertotalMoney"))
            + Double.parseDouble(row.getBeanValue(oodRow, "coupontotalMoney"));

        realUnitPrice = realTotalPrice / aoms062;
      }

      at.setAoms062(CommonUtil.checkNullOrNot(aoms062));

      // 20150824 logic 調整 add by xavier
      // aomsord071 = orderQuery.orderDetail.payAmount + orderQuery.orderDetail.voucherTotalMoney
      // + orderQuery.orderDetail.coupontotalMoney - orderQuery.orderDetail.transportFee
      final double aomsord071 = Double.parseDouble(row.getBeanValue(oodRow, "payAmount"))
          + Double.parseDouble(row.getBeanValue(oodRow, "vouchertotalMoney"))
          + Double.parseDouble(row.getBeanValue(oodRow, "coupontotalMoney")) - transportFee;
      at.setAoms071(CommonUtil.checkNullOrNot(aomsord071));
      // at.setAoms071(CommonUtil.checkNullOrNot(aoms062 * realUnitPrice)); // mark by mowj 20150727
      // at.setAoms071(CommonUtil.checkNullOrNot(row.getPayAmount())); // add by mowj 20150727 modi
      // by mowj 20150730
      at.setAoms072(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "orderLineStatus")));
      // 20150824 logic 調整 add by xavier
      // aomsord064 = (orderQuery.orderDetail.payAmount + orderQuery.orderDetail.voucherTotalMoney
      // + orderQuery.orderDetail.coupontotalMoney - orderQuery.orderDetail.transportFee) /
      // orderQuery.orderDetail.saleNum
      double aomsord064 = aomsord071 / Double.parseDouble(row.getBeanValue(oodRow, "saleNum"));
      at.setAoms064(CommonUtil.checkNullOrNot(aomsord064));
      // at.setAoms064(CommonUtil.checkNullOrNot(realUnitPrice)); // add by mowj 20150730

      at.setAoms067(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "itemCode")));// 苏宁的aomsord067用orderQuery.orderDetail.itemCode~相当于ERP中的料号~
      at.setAoms069(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "vouchertotalMoney")));
      // mark by mowj 20161025
//      at.setAoms078(CommonUtil.checkNullOrNot(sct.getBeanValue(og, "payTotalAmount"))); 

      // 20150825 aoms092 == orderQuery.orderDetail.transportFee add by xavier
      at.setAoms092(CommonUtil.checkNullOrNot(row.getBeanValue(oodRow, "transportFee")));

      // aomsord035 物流费用 = sum(aomsord092)
      if (row.getBeanValue(oodRow, "transportFee").matches(RegEnums.FEE.getRegExpString())) {
        aomsord035 = Double.parseDouble(row.getBeanValue(oodRow, "transportFee"));
      }

      // add by mowj 20150730
      double voucherTotalMoney = 0;
      if (row.getBeanValue(oodRow, "vouchertotalMoney").matches(RegEnums.FEE.getRegExpString())) {
        voucherTotalMoney = Double.parseDouble(row.getBeanValue(oodRow, "vouchertotalMoney"));
      }
      // at.setAoms091(CommonUtil.checkNullOrNot(couponTotalMoney + voucherTotalMoney)); // modi by
      // mowj 20150730

      /*
       * aomsord091 =
       * orderQuery.orderDetail.vouchertotalMoney+orderQuery.orderDetail.paymentList.payamount
       * 取banktypecode = 5998-店铺优惠券、 10006-店铺易券。add by mowj 20151230
       * 将banktypecode=10006的店铺易券作为店铺优惠，转移到091栏位 add by Xavier on 20150825
       */
      final double aomsord091 = voucherTotalMoney
          + this.getOrderPaymentData(row.getBeanListValue(oodRow, "paymentList"), 
              "5998", "10006");
      at.setAoms091(CommonUtil.checkNullOrNot(aomsord091));

      /**
       * aomsord094 = orderQuery.orderDetail.paymentList.payamount 
       * 取banktypecode = 6998-联合0元购券、7998-0元购券、 9994-优惠券、 
       * 9995-优惠券、 10001-云券、 10002-限品类云券、 
       * 10003-店铺云券、 10004-易券、 10005-限品类易券、
       * //10006-店铺易券、 modify by mowj 20151230 将banktypecode=10006的店铺易券作为店铺优惠，转移到091栏位作为店铺优惠分摊 
       * 10009-无敌券。
       * 
       * add by Xavier on 20150825
       */
      double aomsord094 =
          this.getOrderPaymentData(row.getBeanListValue(oodRow, "paymentList"), 
              "6998", "7998", "9994", 
              "9995", "10001", "10002", 
              "10003", "10004", "10005", 
              /* "10006", */ "10009");
      at.setAoms094(CommonUtil.checkNullOrNot(aomsord094));

      // 調整計算 logic, aomsord020 = sum(aomsord091)
      aoms020 += aomsord091;

      // 平台优惠 = sum(aomsord094);
      aomsord078 += aomsord094;

      // double payAmount = aoms062 * realUnitPrice; // modi by mowj 20150730

      // aomsord090 分摊之后的实付金额 = sord071 - aomsord091 edit by xavier on 20150825
      double payAmount = aomsord071 - aomsord091;
      at.setAoms090(CommonUtil.checkNullOrNot(payAmount));

      // aomsord022 = sum(aomsord071) - aomsord20 + aomsord035, edit by xavier 20150828
      aoms022 += aomsord071;

      // logger.error("Suning order: " + og.getOrderCode() + "|CoupontotalMoney:" +
      // row.getCoupontotalMoney() + "|VouchertotalMoney:" + row.getVouchertotalMoney());

      // 系統欄位
      Date now = DateTimeTool.getTodayDate();
      at.setAomscrtdt(DateTimeTool.format(now));
      at.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      data.add(at);

      // 取得最後筆更新時間
      if (modiDate != null
          && StringUtils.isNotBlank(row.getBeanValue(oodRow, "orderLineStatusChangeTime"))) {
        Date newDate = DateTimeTool.parse(row.getBeanValue(oodRow, "orderLineStatusChangeTime"));
        if (newDate.after(modiDate)) {
          modiDate.setTime(newDate.getTime());
        }
      }
    }


    for (AomsordT atRow : data) {
//    add by mowj 20150730 需要将单身的coupuonTotalMoney相加
      atRow.setAoms020(CommonUtil.checkNullOrNot(aoms020)); 
      // modi by mowj 20150730 单头支付金额，即平台支付给商家的金额=商品总金额-单身商品总优惠（此处为0）-整单优惠金额+运费金额
      // aomsord022 = sum(aomsord071) - aomsord20 + aomsord035, edit by xavier 20150828
      atRow.setAoms022(CommonUtil.checkNullOrNot(aoms022 - 0 - aoms020 + aoms035));
//    需要将全部单身orderQuery.transportFee相加
      atRow.setAoms035(CommonUtil.checkNullOrNot(aoms035)); 
//    平台优惠 = sum(aomsord094);
      atRow.setAoms078(CommonUtil.checkNullOrNot(aomsord078));
//    aomsord035 物流费用 = sum(aomsord092)
      atRow.setAoms035(CommonUtil.checkNullOrNot(aomsord035));
    }

    return data;
  }

  /**
   * 取得支付方式 aomsord023 支付方式 = orderQuery.orderDetail.paymentList.banktypecode 取得順序為：
   * 4148-易付宝支付、1001-门店付款、5002-礼品卡支付、6901-券或云钻支付
   * 
   * @param data
   * @return
   * @throws NoSuchMethodException 
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   * @throws Exception
   */
  private String getPaymentCodeForAomsord023(List<?> data) 
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException  {
    SuningCommonTool tool = SuningCommonTool.getInstance();
    for (Object op : data) {
      String value = tool.getBeanValue(op, "banktypecode");
      if ("4148".equals(value)) {
        return value;
      } else if ("1001".equals(value)) {
        return value;
      } else if ("5002".equals(value)) {
        return value;
      } else if ("6901".equals(value)) {
        return value;
      }
    }
    return null;
  }

  /**
   * 計算 paymentList 的料
   * 
   * @param data
   * @param 計算類別
   * @return
   * @throws NoSuchMethodException 
   * @throws InvocationTargetException 
   * @throws IllegalAccessException 
   * @throws Exception
   */
  private double getOrderPaymentData(List<?> data, String... sumBankTypeCode) 
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    double total = 0;
    SuningCommonTool tool = SuningCommonTool.getInstance();
    for (Object op : data) {
      String banktypecode = tool.getBeanValue(op, "banktypecode");
      for (String code : sumBankTypeCode) {
        if (code.equals(banktypecode)) {
          String payamount = tool.getBeanValue(op, "payamount");
          if (payamount.matches(RegEnums.FEE.getRegExpString())) {
            total += Double.parseDouble(payamount);
          }
        }
      }
    }
    return total;
  }

  /**
   * suning.custom.batchrejected.query 批量获取退货信息轉換
   * 
   * @param res
   * @return
   */
  public List<AomsrefundT> transBatchRejectedQueryToAomsrefundTBean(String storeId,
      BatchrejectedQueryResponse res, Date modiDate) {
    List<Rejected> details = res.getSnbody().getRejects();
    if (details == null) {
      return new ArrayList<AomsrefundT>(); // 查無資料
    }
    return this.doTransToAomsrefundTBean(storeId, details, modiDate);
  }

  /**
   * suning.custom.singlerejected.get 单笔查询退货信息轉換
   * 
   * @param res
   * @return
   */
  public List<AomsrefundT> transSinglerejectedGetToAomsrefundTBean(String storeId,
      SinglerejectedGetResponse res) {
    List<Rejected> details = res.getSnbody().getRejects();
    if (details == null) {
      return Collections.emptyList();
    }
    return this.doTransToAomsrefundTBean(storeId, details, null);
  }

  /**
   * 轉換单笔查询退货信息
   * 
   * @param res
   * @return
   */
  private List<AomsrefundT> doTransToAomsrefundTBean(String storeId, List<Rejected> details,
      Date modiDate) {
    List<AomsrefundT> data = new ArrayList<AomsrefundT>();

    for (Rejected row : details) {
      AomsrefundT aft = new AomsrefundT();
      String id = row.getOrderCode() + "#"
          + row.getApplyTime().replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
      aft.setId(id);

      // 20150825 refundtype(返回值为1时，填"FALSE",返回值为其他时，填“TRUE”), add by xavier.
      aft.setAoms009("1".equals(row.getRefundtype()) ? Boolean.FALSE + "" : Boolean.TRUE + "");
      aft.setModified(row.getApplyTime());
      aft.setAoms017(CommonUtil.checkNullOrNot(row.getCustomer()));
      aft.setAoms018(CommonUtil.checkNullOrNot(row.getExpressCompanyCode()));
      aft.setAoms023((StringUtils.isBlank(row.getProductCode()))
          ? CommonUtil.checkNullOrNot(row.getOrderCode())
          : CommonUtil.checkNullOrNot(row.getProductCode()));
      aft.setAoms029(CommonUtil.checkNullOrNot(row.getDealMoney()));
      aft.setAoms030(CommonUtil.checkNullOrNot(row.getReturnMoney()));
      aft.setAoms035(CommonUtil.checkNullOrNot(row.getExpressNo())); 
      aft.setAoms037(CommonUtil.checkNullOrNot(row.getStatusDesc()));
      aft.setAoms038(CommonUtil.checkNullOrNot(row.getOrderCode()));
      aft.setAoms041(CommonUtil.checkNullOrNot(row.getApplyTime()));
      aft.setAoms043(CommonUtil.checkNullOrNot(row.getReturnReason()));

      aft.setStoreId(CommonUtil.checkNullOrNot(storeId));
      aft.setStoreType(SuningCommonTool.STORE_TYPE);
      
      // 系統欄位
      Date now = DateTimeTool.getTodayDate();
      aft.setAomscrtdt(DateTimeTool.format(now));
      aft.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      data.add(aft);

      // 取得最後筆更新時間
      if (modiDate != null && StringUtils.isNotBlank(row.getApplyTime())) {
        Date newDate = DateTimeTool.parse(row.getApplyTime());
        if (newDate.after(modiDate)) {
          modiDate.setTime(newDate.getTime());
        }
      }
    }
    return data;
  }

  /**
   * suning.custom.itemdetail.query 获取我的商品详情信息
   * 
   * @param storeId
   * @param res
   * @return
   */
  public List<AomsitemT> doTransToAomsitemTBean(String storeId, ItemdetailQueryResponse res,
      ItemSaleParams itemSale) {
    ItemdetailQuery detailRow = res.getSnbody().getItemdetailQuery();
    if (detailRow == null) {
      return Collections.emptyList();
    }
    return this.doTransToAomsitemTBean(storeId, null, detailRow, itemSale, null, null);
  }

  /**
   * suning.custom.item.query 获取我的商品库信息 suning.custom.itemdetail.query 获取我的商品详情信息
   * 
   * @param storeId
   * @param headRow
   * @param detailRow
   * @param modiDate 排程推算最後更新時間
   * @param scheduleRunTime 排程執行時間
   * @return
   */
  public List<AomsitemT> doTransToAomsitemTBean(String storeId, ItemQuery headRow,
      ItemdetailQuery detailRow, ItemSaleParams itemSale, Date modiDate, Date scheduleRunTime) {
    List<AomsitemT> data = new ArrayList<AomsitemT>();
    if (detailRow == null) {
      return new ArrayList<AomsitemT>(); // 查無資料
    }
    List<ChildItemdetailQuery> childItems = detailRow.getChildItemdetailQueries();

    if (childItems != null && childItems.size() > 0) {
      for (ChildItemdetailQuery row : childItems) {
        AomsitemT ast = new AomsitemT();
        ast.setId(CommonUtil.checkNullOrNot(detailRow.getProductCode()));
        ast.setAoms002(CommonUtil.checkNullOrNot(detailRow.getItemCode()));
        ast.setAoms003(CommonUtil.checkNullOrNot(detailRow.getProductName()));
        ast.setAoms004(CommonUtil.checkNullOrNot(row.getProductCode()));
        ast.setAoms005(CommonUtil.checkNullOrNot(row.getItemCode()));
        ast.setAoms006(CommonUtil.checkNullOrNot(detailRow.getProductName()));

        if (itemSale != null) { // 有可能儲有商品情況查不到的狀況
          ast.setAoms007("1".equals(itemSale.getSaleStatus()) ? "onsale" : "instock");
        }

        for (ProductParam par : row.getProductParams()) {
          if ("G00001".equals(par.getParCode())) {
            ast.setAoms008(CommonUtil.checkNullOrNot(par.getParValue()));
          } else if ("G00002".equals(par.getParCode())) {
            ast.setAoms009(CommonUtil.checkNullOrNot(par.getParValue()));
          }
        }
        ast.setStoreId(CommonUtil.checkNullOrNot(storeId));
        ast.setStoreType(SuningCommonTool.STORE_TYPE);

        // 若資料為空, 則放入, 排程實際執行時間
        if (headRow == null) {
          ast.setModified(DateTimeTool.format(scheduleRunTime));
        } else {
          ast.setModified(StringUtils.isBlank(headRow.getEditTime())
              ? DateTimeTool.format(scheduleRunTime)
              : headRow.getEditTime());
        }
        ast.setAoms014(CommonUtil.checkNullOrNot(row.getSupplierImg1Url()));
        ast.setAoms018(CommonUtil.checkNullOrNot(row.getInvQty()));
        ast.setAoms024(CommonUtil.checkNullOrNot(row.getPrice()));
        // ast.setAoms028(null); //FIXME aomsitem028 状态, DB bean 查無此欄位.

        // 系統欄位
        Date now = DateTimeTool.getTodayDate();
        ast.setAomscrtdt(DateTimeTool.format(now));
        ast.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

        data.add(ast);
      }
    } else {
      AomsitemT ast = new AomsitemT();
      ast.setId(CommonUtil.checkNullOrNot(detailRow.getProductCode()));
      ast.setAoms002(CommonUtil.checkNullOrNot(detailRow.getItemCode()));
      ast.setAoms003(CommonUtil.checkNullOrNot(detailRow.getProductName()));
      ast.setAoms004(CommonUtil.checkNullOrNot(detailRow.getProductCode()));
      ast.setAoms005(CommonUtil.checkNullOrNot(detailRow.getItemCode()));
      ast.setAoms006(CommonUtil.checkNullOrNot(detailRow.getProductName()));

      if (itemSale != null) { // 有可能儲有商品情況查不到的狀況
        ast.setAoms007("1".equals(itemSale.getSaleStatus()) ? "onsale" : "instock");
      }

      ast.setAoms008(CommonUtil.checkNullOrNot(detailRow.getProductName()));
      ast.setAoms009(CommonUtil.checkNullOrNot(detailRow.getProductName()));
      
      ast.setStoreId(storeId);
      ast.setStoreType(SuningCommonTool.STORE_TYPE);
      
      ast.setAoms014(CommonUtil.checkNullOrNot(detailRow.getSupplierImg1Url()));
      ast.setAoms018(CommonUtil.checkNullOrNot(detailRow.getInvQty())); 
      ast.setAoms024(CommonUtil.checkNullOrNot(detailRow.getPrice()));
      // ast.setAoms028(null); //FIXME aomsitem028 状态, DB bean 查無此欄位.

      // 若資料為空, 則放入, 排程實際執行時間
      if (headRow == null) {
        ast.setModified(DateTimeTool.format(scheduleRunTime));
      } else {
        ast.setModified(StringUtils.isBlank(headRow.getEditTime())
            ? DateTimeTool.format(scheduleRunTime) : headRow.getEditTime());
      }

      // 系統欄位
      Date now = DateTimeTool.getTodayDate();
      ast.setAomscrtdt(DateTimeTool.format(now));
      ast.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      data.add(ast);
    }

    // 取得最後筆更新時間
    // System.out.print(headRow.getEditTime() + ",");
    if (headRow != null) {
      if (modiDate != null && StringUtils.isNotBlank(headRow.getEditTime())) {
        Date newDate = DateTimeTool.parse(headRow.getEditTime());
        if (newDate.after(modiDate)) {
          modiDate.setTime(newDate.getTime());
        }
      }
    }

    return data;
  }
}
