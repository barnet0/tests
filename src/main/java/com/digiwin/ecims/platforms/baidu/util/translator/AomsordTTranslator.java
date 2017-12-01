package com.digiwin.ecims.platforms.baidu.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.enums.OrderStatus;
import com.digiwin.ecims.platforms.baidu.bean.domain.enums.PayMethod;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.DeliveryInfo;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.InvoiceInfo;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.OrderDetail;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.OrderItem;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;

public class AomsordTTranslator {

  public Object detailResponse; // 单头+单身

  public void setDetailResponse(Object detailResponse) {
    this.detailResponse = detailResponse;
  }

  public AomsordTTranslator() {
  }

  public AomsordTTranslator(Object detailResponse) {
    this.detailResponse = detailResponse;
  }

  public List<AomsordT> doTranslate(String storeId) {
    return parseBaiduOrderToAomsordT(storeId);
  }

  private List<AomsordT> parseBaiduOrderToAomsordT(String storeId) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    OrderDetail order = (OrderDetail) this.detailResponse;
    
    for (OrderItem orderItem : order.getOrderItemList()) {
      AomsordT aomsordT = new AomsordT();

      aomsordT.setId(CommonUtil.checkNullOrNot(order.getOrderNo()));
      aomsordT.setAoms002(CommonUtil.checkNullOrNot(order.getType()));
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(OrderStatus.getOrderStatusByValue(Integer.parseInt(order.getStatus()))));
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(order.getCreateTime()));
      aomsordT.setModified(CommonUtil.checkNullOrNot(order.getUpdateTime()));
      aomsordT.setAoms010(CommonUtil.checkNullOrNot(order.getUserMessage()));
      aomsordT.setAoms012(CommonUtil.checkNullOrNot(order.getMemo()));

      double d020 = 0;
      double d022 = 0;
      // 优惠活动金额
      double promotionFee = Double.parseDouble(order.getPromotionFee());
      // 商家优惠券金额
      double merchantCouponFee = Double.parseDouble(order.getMerchantCouponFee());
      // 买家实付金额
      double paymentFee = Double.parseDouble(order.getPaymentFee());
      // 平台优惠券金额
      double platformCouponFee = Double.parseDouble(order.getPlatformCouponFee());

      d020 = promotionFee + merchantCouponFee;
      d022 = paymentFee + platformCouponFee;

      aomsordT.setAoms020(CommonUtil.checkNullOrNot(d020));
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(d022));
      aomsordT.setAoms023(CommonUtil.checkNullOrNot(PayMethod.getPayMethodByValue(order.getPayMethod()).getName()));
      // TODO 和水星讨论支付时间使用什么字段
      aomsordT.setAoms024(CommonUtil.checkNullOrNot(order.getPaySuccessTime()));
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(order.getUserId()));

      DeliveryInfo deliveryInfo = order.getDelivery();
      aomsordT.setAoms034(CommonUtil.checkNullOrNot(deliveryInfo.getDeliverCompanyName()));
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(deliveryInfo.getDeliverFee()));
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(deliveryInfo.getReceiverName()));

      // TODO 标准省市区化
      aomsordT.setAoms037(CommonUtil.checkNullOrNot(deliveryInfo.getProvince()));
      aomsordT.setAoms038(CommonUtil.checkNullOrNot(deliveryInfo.getCity()));
      aomsordT.setAoms039(CommonUtil.checkNullOrNot(deliveryInfo.getDistrict()));

      aomsordT.setAoms040(CommonUtil.checkNullOrNot(deliveryInfo.getReceiverAddress()));
      aomsordT.setAoms041(CommonUtil.checkNullOrNot(deliveryInfo.getReceiverZipCode()));
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(deliveryInfo.getReceiverMobile()));
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(deliveryInfo.getReceiverPhone()));

      InvoiceInfo invoiceInfo = order.getInvoice();
      if (invoiceInfo != null) {
        aomsordT.setAoms053(CommonUtil.checkNullOrNot(invoiceInfo.getTitle()));
        aomsordT.setAoms054(CommonUtil.checkNullOrNot(invoiceInfo.getContent()));
      }

      aomsordT.setStoreId(storeId);
      aomsordT.setStoreType(BaiduCommonTool.STORE_TYPE);

      aomsordT.setAoms058(CommonUtil.checkNullOrNot(orderItem.getOrderNo()));
      aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderItem.getItemId()));
      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderItem.getSkuId()));
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderItem.getSkuProperties()));
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderItem.getSkuNum()));
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderItem.getSkuPrice()));
      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderItem.getSkuOuterId()));

      double d071 = 0;
      int skuNum = orderItem.getSkuNum();
      double skuPrice = Double.parseDouble(orderItem.getSkuPrice());
      d071 = skuNum * skuPrice;
      aomsordT.setAoms071(CommonUtil.checkNullOrNot(d071));

      aomsordT.setAoms078(CommonUtil.checkNullOrNot(platformCouponFee));
      aomsordT.setAoms090(CommonUtil.checkNullOrNot(orderItem.getPaymentFee()));
      // TODO 需要测试后验证
      aomsordT.setAoms091(CommonUtil.checkNullOrNot(orderItem.getPromotionFee()));
      aomsordT.setAoms092(CommonUtil.checkNullOrNot(orderItem.getDeliveryFee()));

      Date now = new Date();
      aomsordT.setAomsstatus("0");
      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsordTs.add(aomsordT);
    }

    return aomsordTs;
  }

}
