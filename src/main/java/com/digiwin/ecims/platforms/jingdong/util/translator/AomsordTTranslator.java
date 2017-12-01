package com.digiwin.ecims.platforms.jingdong.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jd.open.api.sdk.domain.order.CouponDetail;
import com.jd.open.api.sdk.domain.order.ItemInfo;
import com.jd.open.api.sdk.domain.order.OrderInfo;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.domain.order.UserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.platforms.taobao.service.area.impl.StandardAreaServiceImpl.MunicipalityEnum;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.SequenceBuilder;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.ontime.util.MySpringContext;

public class AomsordTTranslator {

  private static final Map<String, Object> Object = null;

private Object obj;

  private StandardAreaService standardAreaService =
      MySpringContext.getContext().getBean(StandardAreaService.class);

  public AomsordTTranslator(Object orderEntity) {
    super();
    this.obj = orderEntity;
  }

  public List<AomsordT> doTranslate(String storeId,Map<String,String> jdmap) {
    // add by mowj 20150901 修正主键重复的问题（如买一送一会有两个相同的商品，但是数量不一样）
    if (obj instanceof OrderSearchInfo) {
      return parseOrderSearchInfoToAomsordT((OrderSearchInfo) obj, storeId,jdmap);
    } else if (obj instanceof OrderInfo) {
      return parseOrderInfoToAomsordT((OrderInfo) obj, storeId,jdmap);
    } else {
      return new ArrayList<AomsordT>();
    }
  }

  /**
   * 多筆訂單下載
   * 
   * @param orderInfo
   * @param item
   * @param storeId
   * @return
   * @author 维杰
   * @since 2015.09.01
   */
  private List<AomsordT> parseOrderSearchInfoToAomsordT(OrderSearchInfo orderInfo, String storeId,Map<String,String> jdmap) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    Map<String, AomsordT> aomsordTMap = new HashMap<String, AomsordT>();

    for (ItemInfo item : orderInfo.getItemInfoList()) {
      String aomsordTKey = orderInfo.getOrderId() + JingdongCommonTool.STORE_TYPE
          + (item.getSkuId() == null ? item.getWareId() : item.getSkuId());

      // id + StoreType + skuid会有重复，对于重复的商品，以下栏位需特殊加总
      // 062=sum(item.getItemTotal()) 商品数量加总
      // 069=sum(coupon的skuid与当前item相同，且Orderid与主订单号相同 ，且优惠信息为30时，相加)
      // 071=sum(item.getItemTotal()*item.getJdPrice()) - aoms069 重复商品的总价-重复商品的总的优惠金额，所以要在循环外面做
      int aoms062 = Integer.parseInt(item.getItemTotal());
      double aoms064 = Double.parseDouble(item.getJdPrice());
      double aoms069 = 0.0;
      if (item.getSkuId() != null && !item.getSkuId().trim().equals("")) {
        for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
          if (item.getSkuId().equals(cDetail.getSkuId())
              && cDetail.getOrderId().equals(orderInfo.getOrderId())
              && cDetail.getCouponType().split("-")[0].equals("30")) {
            aoms069 += Double.parseDouble(cDetail.getCouponPrice());
          }
        }
      }
      double aoms071 = aoms062 * Double.parseDouble(item.getJdPrice()) - aoms069;

      AomsordT aomsordT = null;

      if (null == aomsordTMap.get(aomsordTKey)) {
        aomsordT = new AomsordT();
        aomsordT.setAoms062(CommonUtil.checkNullOrNot(aoms062));// aomsord062
        aomsordT.setAoms064(CommonUtil.checkNullOrNot(aoms064));
        aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071));// aomsord071

      } else {
        aomsordT = aomsordTMap.get(aomsordTKey);
        aomsordT.setAoms062(
            CommonUtil.checkNullOrNot(Integer.parseInt(aomsordT.getAoms062()) + aoms062));// aomsord062
        aomsordT.setAoms064(
            CommonUtil.checkNullOrNot((Double.parseDouble(aomsordT.getAoms064()) + aoms064)
                / Double.parseDouble(aomsordT.getAoms062())));
        aomsordT.setAoms071(
            CommonUtil.checkNullOrNot(Double.parseDouble(aomsordT.getAoms071()) + aoms071));// aomsord071
        continue;
      }
      aomsordT.setAoms069(CommonUtil.checkNullOrNot(aoms069));// aomsord069

      aomsordT.setId(CommonUtil.checkNullOrNot(orderInfo.getOrderId())); // aomsord001
      aomsordT.setAoms002("0"); // aomsord002
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderInfo.getOrderState()));// aomsord003
      aomsordT.setAoms004(CommonUtil.checkNullOrNot(orderInfo.getOrderSource()));// aomsord004
      aomsordT.setAoms005(CommonUtil.checkNullOrNot(orderInfo.getOrderSellerPrice()));// aomsord005
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderInfo.getOrderStartTime()));// aomsord006
      aomsordT.setModified(CommonUtil.checkNullOrNot(orderInfo.getModified()));// aomsord007
      aomsordT.setAoms008(CommonUtil.checkNullOrNot(orderInfo.getOrderEndTime()));// aomsord008
      aomsordT.setAoms010(CommonUtil.checkNullOrNot(orderInfo.getOrderRemark()));// aomsord010
      aomsordT.setAoms011(CommonUtil.checkNullOrNot(orderInfo.getOrderRemark()));// aomsord011
      aomsordT.setAoms012(CommonUtil.checkNullOrNot(orderInfo.getVenderRemark()));// aomsord012
      Double sum20 = 0.0;
      for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
        if (cDetail.getOrderId().equals(orderInfo.getOrderId())
            && !cDetail.getCouponType().split("-")[0].equals("39")
            && !cDetail.getCouponType().split("-")[0].equals("41")
            && !cDetail.getCouponType().split("-")[0].equals("52")
            && !cDetail.getCouponType().split("-")[0].equals("30")) {
          sum20 += Double.parseDouble(cDetail.getCouponPrice());
        }
      }
      // add by mowj 20150902 for debug error!
      // if (String.valueOf(sum20).length() > 15) {
      // logger.error("sum20: " + String.valueOf(sum20) + " is too long for aomsord020 in order " +
      // aomsordT.getId());
      // throw new RuntimeException("sum20: " + String.valueOf(sum20) + " is too long for aomsord020
      // in order " + aomsordT.getId());
      // }

      // modi by mowj 20150902
      aomsordT.setAoms020(CommonUtil.checkNullOrNot(sum20)); 

      // 支付金额（平台支付商家）
      // orderInfo.order_order_seller_price+order_info.freight_price
      Double aoms022 = Double.valueOf(orderInfo.getOrderSellerPrice())
          + Double.valueOf(orderInfo.getFreightPrice());
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(aoms022));

      aomsordT.setAoms023(CommonUtil.checkNullOrNot(orderInfo.getPayType()));
      
      //临时处理京东退货无忧  发票
      if("1".equals(orderInfo.getPayType().split("-")[0])|| !"不需要开具发票".equals(orderInfo.getInvoiceInfo()) ){
    	  
    	 Map<String,Object> map=   getJdtuiHuoWuYou(orderInfo.getOrderId(),jdmap);
    	  
    	 if(map.containsKey("tuiHuoWuYou")){
    		 aomsordT.setAomsordundefined011(CommonUtil.checkNullOrNot(map.get("tuiHuoWuYou")));
    	 }
    		
        if(map.containsKey("invoiceCode")){
        	
        	aomsordT.setAomsordundefined012(CommonUtil.checkNullOrNot(map.get("invoiceCode")));
        }
        	
    	
      }
      
      
      aomsordT.setAoms024(CommonUtil
          .checkNullOrNot(aoms024Format(orderInfo.getPaymentConfirmTime(), aomsordT.getAoms006())));// aomsord024
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderInfo.getPin()));

      Double sum30 = 0.0;
      for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
        if (cDetail.getOrderId().equals(orderInfo.getOrderId())
            && cDetail.getCouponType().split("-")[0].equals("39")) {
          sum30 += Double.parseDouble(cDetail.getCouponPrice());
        }
      }

      aomsordT.setAoms030(CommonUtil.checkNullOrNot(sum30));// aomsord030
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderInfo.getFreightPrice()));// aomsord035
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getFullname()));// aomsord036
      // modi by mowj 20150726 省市区淘宝标准化
      setProvinceCityDistrict(aomsordT, orderInfo.getConsigneeInfo());
      
      aomsordT.setAoms040(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getFullAddress()));// aomsord040
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getMobile()));// aomsord042
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getTelephone()));// aomsord043

      if (orderInfo.getInvoiceInfo().indexOf(";") > 0) {
        String[] invoiceInfo = orderInfo.getInvoiceInfo().split(";");
        String invoiceTitle = invoiceInfo[1].split(":")[1];
        String invoiceMemo = invoiceInfo[2].split(":")[1];
        String invoiceType = invoiceInfo[0].split(":")[1];
        aomsordT.setAoms053(CommonUtil.checkNullOrNot(invoiceTitle));// aomsord053
        aomsordT.setAoms054(CommonUtil.checkNullOrNot(invoiceMemo));// aomsord054
        aomsordT.setAoms055(CommonUtil.checkNullOrNot(invoiceType));// aomsord055
      } else {
        aomsordT.setAoms053(CommonUtil.checkNullOrNot(null));// aomsord053
        aomsordT.setAoms054(CommonUtil.checkNullOrNot(orderInfo.getInvoiceInfo()));// aomsord054
        aomsordT.setAoms055(CommonUtil.checkNullOrNot(null));// aomsord055
      }

      aomsordT.setStoreId(CommonUtil.checkNullOrNot(storeId));// aomsord056
      aomsordT.setStoreType(JingdongCommonTool.STORE_TYPE);// aomsord057
      aomsordT.setAoms058(CommonUtil.checkNullOrNot(SequenceBuilder.getSequence()));// aomsord058
      aomsordT.setAoms059(CommonUtil.checkNullOrNot(item.getWareId()));// aomsord059
      aomsordT.setAoms060(item.getSkuId() == null ? aomsordT.getAoms059() : item.getSkuId());// aomsord060
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(item.getSkuName()));// aomsord061
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(item.getItemTotal()));// aomsord062
      aomsordT.setAoms063(CommonUtil.checkNullOrNot(item.getSkuName()));// aomsord063
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(item.getJdPrice()));// aomsord064
      aomsordT.setAoms066(CommonUtil.checkNullOrNot(item.getProductNo()));// aomsord066
      aomsordT.setAoms067(CommonUtil.checkNullOrNot(item.getOuterSkuId()));// aomsord067

      // mark by mowj 20150901
      // Double aoms071 = Double.parseDouble(aomsordT.getAoms064()) *
      // Double.parseDouble(aomsordT.getAoms062()) - Double.parseDouble(aomsordT.getAoms069());
      // aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071));// aomsord071

      Double sum78 = 0.0;
      for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
        if (cDetail.getOrderId().equals(orderInfo.getOrderId())
            && (cDetail.getCouponType().split("-")[0].equals("41")
                || cDetail.getCouponType().split("-")[0].equals("52"))) {
          sum78 += Double.parseDouble(cDetail.getCouponPrice());
        }
      }

      aomsordT.setAoms078(CommonUtil.checkNullOrNot(sum78));// aomsord078
      // add on 20150903 余额支付金额
      aomsordT.setAomsundefined001(CommonUtil.checkNullOrNot(orderInfo.getBalanceUsed()));
      // add on 20161018 京仓订单
      aomsordT.setAomsundefined008(CommonUtil.checkNullOrNot(orderInfo.getStoreOrder()));

      Date now = DateTimeTool.getTodayDate();
      aomsordT.setAomscrtdt(DateTimeTool.format(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsordTMap.put(aomsordTKey, aomsordT);
    }

    if (null != aomsordTMap) {
      for (Map.Entry<String, AomsordT> aomsordT : aomsordTMap.entrySet()) {
        aomsordTs.add(aomsordT.getValue());
      }
    }
    return aomsordTs;
  }

  /**
   * For 單筆調用的 API 做 parse
   * 
   * @param orderInfo
   * @param storeId
   * @return
   * @author 维杰
   * @since 2015.09.01
   */
  private List<AomsordT> parseOrderInfoToAomsordT(OrderInfo orderInfo, String storeId,Map<String,String> jdmap) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    Map<String, AomsordT> aomsordTMap = new HashMap<String, AomsordT>();

    for (ItemInfo item : orderInfo.getItemInfoList()) {
      String aomsordTKey = orderInfo.getOrderId() + JingdongCommonTool.STORE_TYPE
          + (item.getSkuId() == null ? item.getWareId() : item.getSkuId());

      // id + StoreType + skuid会有重复，对于重复的商品，以下栏位需特殊加总
      // 062=sum(item.getItemTotal()) 商品数量加总
      // 069=sum(coupon的skuid与当前item相同，且Orderid与主订单号相同 ，且优惠信息为30时，相加)
      // 071=sum(item.getItemTotal()*item.getJdPrice()) - aoms069 重复商品的总价-重复商品的总的优惠金额，所以要在循环外面做
      int aoms062 = Integer.parseInt(item.getItemTotal());
      double aoms064 = Double.parseDouble(item.getJdPrice());
      double aoms069 = 0.0;
      if (item.getSkuId() != null && !item.getSkuId().trim().equals("")) {
        for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
          if (item.getSkuId().equals(cDetail.getSkuId())
              && cDetail.getOrderId().equals(orderInfo.getOrderId())
              && cDetail.getCouponType().split("-")[0].equals("30")) {
            aoms069 += Double.parseDouble(cDetail.getCouponPrice());
          }
        }
      }
      double aoms071 = aoms062 * Double.parseDouble(item.getJdPrice()) - aoms069;

      AomsordT aomsordT = null;

      if (null == aomsordTMap.get(aomsordTKey)) {
        aomsordT = new AomsordT();
        aomsordT.setAoms062(CommonUtil.checkNullOrNot(aoms062));// aomsord062
        aomsordT.setAoms064(CommonUtil.checkNullOrNot(aoms064));
        aomsordT.setAoms071(CommonUtil.checkNullOrNot(aoms071));// aomsord071

      } else {
        aomsordT = aomsordTMap.get(aomsordTKey);
        aomsordT.setAoms062(
            CommonUtil.checkNullOrNot(Integer.parseInt(aomsordT.getAoms062()) + aoms062));// aomsord062
        aomsordT.setAoms064(
            CommonUtil.checkNullOrNot((Double.parseDouble(aomsordT.getAoms064()) + aoms064)
                / Double.parseDouble(aomsordT.getAoms062())));
        aomsordT.setAoms071(
            CommonUtil.checkNullOrNot(Double.parseDouble(aomsordT.getAoms071()) + aoms071));// aomsord071
        continue;
      }
      aomsordT.setAoms069(CommonUtil.checkNullOrNot(aoms069));// aomsord069

      aomsordT.setId(CommonUtil.checkNullOrNot(orderInfo.getOrderId())); // aomsord001
      aomsordT.setAoms002("0"); // aomsord002
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderInfo.getOrderState()));// aomsord003
      aomsordT.setAoms005(CommonUtil.checkNullOrNot(orderInfo.getOrderSellerPrice()));// aomsord005
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderInfo.getOrderStartTime()));// aomsord006
      aomsordT.setModified(CommonUtil.checkNullOrNot(orderInfo.getModified()));// aomsord007
      aomsordT.setAoms008(CommonUtil.checkNullOrNot(orderInfo.getOrderEndTime()));// aomsord008
      aomsordT.setAoms010(CommonUtil.checkNullOrNot(orderInfo.getOrderRemark()));// aomsord010
      aomsordT.setAoms011(CommonUtil.checkNullOrNot(orderInfo.getOrderRemark()));// aomsord011
      aomsordT.setAoms012(CommonUtil.checkNullOrNot(orderInfo.getVenderRemark()));// aomsord012
      Double sum20 = 0.0;
      for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
        if (cDetail.getOrderId().equals(orderInfo.getOrderId())
            && !cDetail.getCouponType().split("-")[0].equals("39")
            && !cDetail.getCouponType().split("-")[0].equals("41")
            && !cDetail.getCouponType().split("-")[0].equals("52")
            && !cDetail.getCouponType().split("-")[0].equals("30")) {
          sum20 += Double.parseDouble(cDetail.getCouponPrice());
        }
      }
      // modi by mowj 20150902
      aomsordT.setAoms020(CommonUtil.checkNullOrNot(sum20));

      // 支付金额（平台支付商家）
      // orderInfo.order_order_seller_price+order_info.freight_price
      Double aoms022 = Double.valueOf(orderInfo.getOrderSellerPrice())
          + Double.valueOf(orderInfo.getFreightPrice());
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(aoms022));// aomsord022

      aomsordT.setAoms023(CommonUtil.checkNullOrNot(orderInfo.getPayType()));// aomsord023
      
      //退货无忧
      if("1".equals(orderInfo.getPayType().split("-")[0])|| !"不需要开具发票".equals(orderInfo.getInvoiceInfo()) ){
    	  
    	 Map<String,Object> map=   getJdtuiHuoWuYou(orderInfo.getOrderId(),jdmap);
    	  
    	 if(map.containsKey("tuiHuoWuYou")){
    		 aomsordT.setAomsordundefined011(CommonUtil.checkNullOrNot(map.get("tuiHuoWuYou")));
    	 }
    		
        if(map.containsKey("invoiceCode")){
        	
        	aomsordT.setAomsordundefined012(CommonUtil.checkNullOrNot(map.get("invoiceCode")));
        }
        	
    	
      }
      
      aomsordT.setAoms024(CommonUtil
          .checkNullOrNot(aoms024Format(orderInfo.getPaymentConfirmTime(), aomsordT.getAoms006())));// aomsord024
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderInfo.getPin()));// aomsord025

      Double sum30 = 0.0;
      for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
        if (cDetail.getOrderId().equals(orderInfo.getOrderId())
            && cDetail.getCouponType().split("-")[0].equals("39")) {
          sum30 += Double.parseDouble(cDetail.getCouponPrice());
        }
      }

      aomsordT.setAoms030(CommonUtil.checkNullOrNot(sum30));// aomsord030
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderInfo.getFreightPrice()));// aomsord035
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getFullname()));// aomsord036
      // modi by mowj 20150726 省市区淘宝标准化
      setProvinceCityDistrict(aomsordT, orderInfo.getConsigneeInfo());
      
      aomsordT.setAoms040(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getFullAddress()));// aomsord040
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getMobile()));// aomsord042
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(orderInfo.getConsigneeInfo().getTelephone()));// aomsord043

      if (orderInfo.getInvoiceInfo().indexOf(";") > 0) {
        String[] invoiceInfo = orderInfo.getInvoiceInfo().split(";");
        String invoiceTitle = invoiceInfo[1].split(":")[1];
        String invoiceMemo = invoiceInfo[2].split(":")[1];
        String invoiceType = invoiceInfo[0].split(":")[1];
        aomsordT.setAoms053(CommonUtil.checkNullOrNot(invoiceTitle));// aomsord053
        aomsordT.setAoms054(CommonUtil.checkNullOrNot(invoiceMemo));// aomsord054
        aomsordT.setAoms055(CommonUtil.checkNullOrNot(invoiceType));// aomsord055
      } else {
        aomsordT.setAoms053(CommonUtil.checkNullOrNot(null));// aomsord053
        aomsordT.setAoms054(CommonUtil.checkNullOrNot(orderInfo.getInvoiceInfo()));// aomsord054
        aomsordT.setAoms055(CommonUtil.checkNullOrNot(null));// aomsord055
      }

      aomsordT.setStoreId(CommonUtil.checkNullOrNot(storeId));// aomsord056
      aomsordT.setStoreType(JingdongCommonTool.STORE_TYPE);// aomsord057
      aomsordT.setAoms058(CommonUtil.checkNullOrNot(SequenceBuilder.getSequence()));// aomsord058
      aomsordT.setAoms059(CommonUtil.checkNullOrNot(item.getWareId()));// aomsord059
      aomsordT.setAoms060(item.getSkuId() == null ? aomsordT.getAoms059() : item.getSkuId());// aomsord060
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(item.getSkuName()));// aomsord061
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(item.getItemTotal()));// aomsord062
      aomsordT.setAoms063(CommonUtil.checkNullOrNot(item.getSkuName()));// aomsord063
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(item.getJdPrice()));// aomsord064
      aomsordT.setAoms066(CommonUtil.checkNullOrNot(item.getProductNo()));// aomsord066
      aomsordT.setAoms067(CommonUtil.checkNullOrNot(item.getOuterSkuId()));// aomsord067

      Double sum78 = 0.0;
      for (CouponDetail cDetail : orderInfo.getCouponDetailList()) {
        if (cDetail.getOrderId().equals(orderInfo.getOrderId())
            && (cDetail.getCouponType().split("-")[0].equals("41")
                || cDetail.getCouponType().split("-")[0].equals("52"))) {
          sum78 += Double.parseDouble(cDetail.getCouponPrice());
        }
      }

      aomsordT.setAoms078(CommonUtil.checkNullOrNot(sum78));// aomsord078

      // add on 20150903 余额支付金额
      aomsordT.setAomsundefined001(CommonUtil.checkNullOrNot(orderInfo.getBalanceUsed()));

      // add on 20161018 京仓订单
      aomsordT.setAomsundefined008(CommonUtil.checkNullOrNot(orderInfo.getStoreOrder()));

      Date now = DateTimeTool.getTodayDate();
      aomsordT.setAomscrtdt(DateTimeTool.format(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsordTMap.put(aomsordTKey, aomsordT);
    }

    if (null != aomsordTMap) {
      for (Map.Entry<String, AomsordT> aomsordT : aomsordTMap.entrySet()) {
        aomsordTs.add(aomsordT.getValue());
      }
    }
    return aomsordTs;
  }

  /**
   * aoms024 欄位內容校驗替換
   * 
   * @param paymentConTime
   * @param aoms006
   * @return
   */
  public String aoms024Format(String paymentConTime, String aoms006) {
    //String newPaymentConTime = null;
	String newPaymentConTime = "";
    if (paymentConTime != null) {

      if (paymentConTime.equals("0001-01-01 00:00:00")) {

        // 非空，且格式位內定義的話以 aoms006 替代
        newPaymentConTime = aoms006;
      } else {

        // 非空，且格式為正確日期
        newPaymentConTime = paymentConTime;
      }

    }
    return newPaymentConTime;
  }

  /**
   * 京东对直辖市的省市区的值类似以下：上海长宁区内环以内 但是对非直辖市的省市区的值类似如下：山东济南市天桥区 所以需要特别处理，将上海长宁区内环以内->上海市上海市长宁区
   * 
   * @param aomsordT
   * @param orderInfo
   * @author 维杰
   * @since 2015.07.25
   */
  private void setProvinceCityDistrict(AomsordT aomsordT, UserInfo userInfo) {
    // add by mowj 20160329 发现京东返回的JSON中，省市区有可能为空。当为空时，不进行标准化处理
    if (userInfo.getProvince().length() == 0 && userInfo.getCity().length() == 0
        && userInfo.getCounty().length() == 0) {
      return;
    }
    String provinceString = userInfo.getProvince();
    // 如果是直辖市，则只获取province,province,city
    if (MunicipalityEnum.isProvinceInMunicipality(provinceString)) {
      AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(
          userInfo.getProvince(), userInfo.getProvince(), userInfo.getCity());
      // System.out.println(standardArea);
      if (standardArea != null) {
        String standardProvince = standardArea.getProvince();
        String standardCity = standardArea.getCity();
        String standardDistrict = standardArea.getDistrict();

        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));// aomsord037
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));// aomsord038
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));// aomsord039

      } else {
        aomsordT.setAoms037(CommonUtil.checkNullOrNot(userInfo.getProvince()));// aomsord037
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(userInfo.getProvince()));// aomsord038
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(userInfo.getCity()));// aomsord039
      }

    } else {
      // 否则全部取
      AreaResponse standardArea = standardAreaService.getStandardAreaNameByKeyWord(
          userInfo.getProvince(), userInfo.getCity(), userInfo.getCounty());
      // System.out.println(standardArea);
      if (standardArea != null) {
        String standardProvince = standardArea.getProvince();
        String standardCity = standardArea.getCity();
        String standardDistrict = standardArea.getDistrict();

        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));// aomsord037
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));// aomsord038
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));// aomsord039

      } else {
        aomsordT.setAoms037(CommonUtil.checkNullOrNot(userInfo.getProvince()));// aomsord037
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(userInfo.getCity()));// aomsord038
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(userInfo.getCounty()));// aomsord039
      }
    }
  }
  
  /***
   * 
   * @param map
   * 临时处理退货无忧字段
   * http 方式调用根据订单ID查询订单
   *  jingdong.pop.order.get
   *  http://jos.jd.com/api/detail.htm?apiName=jingdong.pop.order.get&id=1565
   */
  private static Map<String,Object>   getJdtuiHuoWuYou(String order_id,Map<String,String> tokenmap){
	  Map<String,Object> result=new HashMap<String,Object>();
	    try {
	    	Map<String,String> parammap=new HashMap<String,String>();
	    	parammap.put("order_id", order_id);
			parammap.put("optional_fields", "orderId,tuiHuoWuYou,vatInfo,invoiceInfo,invoiceCode");
			String url="https://api.jd.com/routerjson?360buy_param_json="+JSON.toJSONString(parammap)+"&access_token="+tokenmap.get("accessToken")+"&app_key="+tokenmap.get("appKey")+"&method=jingdong.pop.order.get&v=2.0";
			Map<String,Object> tmap1=new HashMap<String,Object>();
			JSONObject jsonmap=HttpPostUtils.getInstance().httpsRequest(url, "GET","");
				if(jsonmap.containsKey("jingdong_pop_order_get_responce")){
					Map<String,Object> tmap= (Map<String, Object>) jsonmap.get("jingdong_pop_order_get_responce");
					if(tmap.containsKey("orderDetailInfo")){
						tmap1=(Map<String, Object>) tmap.get("orderDetailInfo");	
						if(tmap1.containsKey("orderInfo")&& tmap1.get("orderInfo")!=null){
							result=(Map<String, Object>) tmap1.get("orderInfo");
						}
					}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			  return result;
		}
	  //  System.out.println(result);
	   return result;
  }

}
