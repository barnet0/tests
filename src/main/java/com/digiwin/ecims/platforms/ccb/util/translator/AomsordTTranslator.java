package com.digiwin.ecims.platforms.ccb.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.digiwin.ecims.platforms.ccb.bean.domain.order.OrderInvoiceInfo;
import com.digiwin.ecims.platforms.ccb.bean.domain.order.OrderProductItem;
import com.digiwin.ecims.platforms.ccb.bean.domain.order.OrderShippingInfo;
import com.digiwin.ecims.platforms.ccb.bean.domain.order.detail.get.OrderInfo;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.util.MySpringContext;

public class AomsordTTranslator {

  private Object entity;

  private StandardAreaService standardAreaService =
      MySpringContext.getContext().getBean(StandardAreaService.class);

  public AomsordTTranslator(Object entity) {
    this.entity = entity;
  }

  public List<AomsordT> doTranslate(String storeId) {
    if (entity instanceof OrderInfo) {
      return parseCcbOrderToAomsordT(storeId);
    } else {
      return new ArrayList<AomsordT>();
    }
  }

  private List<AomsordT> parseCcbOrderToAomsordT(String storeId) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    OrderInfo orderInfo = (OrderInfo) this.entity;

    for (OrderProductItem orderProdItem : orderInfo.getProductItems().getProductItems()) {
      AomsordT aomsordT = new AomsordT();

      aomsordT.setId(CommonUtil.checkNullOrNot(orderInfo.getOrderId()));
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(orderInfo.getStatus()));
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(orderInfo.getOrderTime()));
      aomsordT.setAoms009(CommonUtil.checkNullOrNot(orderInfo.getOrderMemo()));
      aomsordT.setAoms020(CommonUtil.checkNullOrNot(orderInfo.getMerchantDiscount()));

      double merchantDiscount = Double.parseDouble(orderInfo.getMerchantDiscount());
      double orderProdAmt = Double.parseDouble(orderInfo.getOrderProdAmt());
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(orderProdAmt - merchantDiscount));

      aomsordT.setAoms024(CommonUtil.checkNullOrNot(orderInfo.getPaymentTime()));
      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderInfo.getBuyerId()));
      aomsordT.setAoms026(CommonUtil.checkNullOrNot(orderInfo.getBuyerEmail()));

      aomsordT.setAoms035(CommonUtil.checkNullOrNot(orderInfo.getDeliveryFee()));
      OrderShippingInfo shippingInfo = orderInfo.getShippingInfo();
      aomsordT.setAoms036(CommonUtil.checkNullOrNot(shippingInfo.getConsigneName()));

      // 省市区淘宝标准化
      AreaResponse standardArea =
          standardAreaService.getStandardAreaNameByKeyWord(shippingInfo.getConsigneeProvince(),
              shippingInfo.getConsigneeCity(), shippingInfo.getConsigneeCounty());
      if (standardArea != null) {
        String standardProvince = standardArea.getProvince();
        String standardCity = standardArea.getCity();
        String standardDistrict = standardArea.getDistrict();

        aomsordT.setAoms037(CommonUtil.checkNullOrNot(standardProvince));// aomsord037
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(standardCity));// aomsord038
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(standardDistrict));// aomsord039

      } else {
        aomsordT.setAoms037(CommonUtil.checkNullOrNot(shippingInfo.getConsigneeProvince()));
        aomsordT.setAoms038(CommonUtil.checkNullOrNot(shippingInfo.getConsigneeCity())); // 修正District->City
                                                                                         // by mowj
                                                                                         // 20160317
        aomsordT.setAoms039(CommonUtil.checkNullOrNot(shippingInfo.getConsigneeCounty()));
      }

      aomsordT.setAoms040(CommonUtil.checkNullOrNot(shippingInfo.getConsigneeAddress()));
      aomsordT.setAoms041(CommonUtil.checkNullOrNot(shippingInfo.getConsigneeZip()));
      aomsordT.setAoms042(CommonUtil.checkNullOrNot(shippingInfo.getConsigneeMobile()));
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(shippingInfo.getConsigneePhone()));
      OrderInvoiceInfo invoiceInfo = orderInfo.getInvoiceInfo();
      aomsordT.setAoms053(CommonUtil.checkNullOrNot(invoiceInfo.getInvoiceTitle()));
      aomsordT.setAoms055(CommonUtil.checkNullOrNot(invoiceInfo.getInvoiceType()));

      aomsordT.setStoreId(storeId);
      aomsordT.setStoreType(CcbCommonTool.STORE_TYPE);

      aomsordT.setAoms060(CommonUtil.checkNullOrNot(orderProdItem.getSkuId()));
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderProdItem.getProdDesc()));
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderProdItem.getProdBuyAmt()));
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderProdItem.getProdPrice()));
      aomsordT.setAoms067(CommonUtil.checkNullOrNot(orderProdItem.getProId()));

      double prodDiscount = Double.parseDouble(orderProdItem.getProdDiscount());
      int prodBuyAmt = Integer.parseInt(orderProdItem.getProdBuyAmt());
      aomsordT.setAoms069(CommonUtil.checkNullOrNot(prodDiscount * prodBuyAmt));

      double prodPrice = Double.parseDouble(orderProdItem.getProdPrice());
      aomsordT.setAoms071(CommonUtil.checkNullOrNot(prodPrice * prodBuyAmt));

      aomsordT.setAoms078(CommonUtil.checkNullOrNot(orderInfo.getOrderCoupon()));

      Date now = new Date();
      aomsordT.setAomsstatus("0");
      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsordTs.add(aomsordT);
    }

    return aomsordTs;
  }
}
