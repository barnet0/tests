package com.digiwin.ecims.platforms.dhgate.util.translator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.util.CommonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderContact;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderProduct;
import com.digiwin.ecims.platforms.dhgate.bean.enums.OrderStatus;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;

public class AomsordTTranslator {

  private Object detail;

  private Object itemList;

  private Map<String, Long> outerSkuToSkuMap;
  
  public AomsordTTranslator() {
    super();
  }

  public AomsordTTranslator(Object detail, Object itemList, Map<String, Long> outerSkuToSkuMap) {
    this.detail = detail;
    this.itemList = itemList;
    this.outerSkuToSkuMap = outerSkuToSkuMap;
  }

  public List<AomsordT> doTranslate(String storeId) {
    if (this.detail instanceof OrderGetResponse && this.itemList instanceof List) {
      return parseDhOrderToAomsordT(storeId);
    } else {
      return new ArrayList<AomsordT>();
    }

  }

  private List<AomsordT> parseDhOrderToAomsordT(String storeId) {
    List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
    OrderGetResponse detail = (OrderGetResponse) this.detail;
    @SuppressWarnings("unchecked")
    List<OrderProduct> products = (List<OrderProduct>) this.itemList;

    for (OrderProduct orderProduct : products) {
      AomsordT aomsordT = new AomsordT();
      OrderContact orderContact = detail.getOrderContact();

      aomsordT.setId(CommonUtil.checkNullOrNot(detail.getOrderNo()));
      aomsordT.setAoms003(CommonUtil.checkNullOrNot(OrderStatus.getOrderStatusByCode(detail.getOrderStatus())));
      aomsordT.setAoms006(CommonUtil.checkNullOrNot(detail.getStartedDate()));
      aomsordT.setAoms009(CommonUtil.checkNullOrNot(generateOverseaAddress(orderContact)));
      aomsordT.setAoms010(CommonUtil.checkNullOrNot(detail.getOrderRemark()));
      aomsordT.setAoms020(CommonUtil.checkNullOrNot(detail.getSellerCouponPrice()));
      aomsordT.setAoms022(CommonUtil.checkNullOrNot(detail.getActualPrice()));
      aomsordT.setAoms024(CommonUtil.checkNullOrNot(detail.getPayDate()));

      aomsordT.setAoms025(CommonUtil.checkNullOrNot(orderContact.getBuyerNickName()));
      aomsordT.setAoms026(CommonUtil.checkNullOrNot(orderContact.getEmail()));

//      System.out.println(detail.getShippingType());
      aomsordT.setAoms034(CommonUtil.checkNullOrNot(detail.getShippingType()));
      aomsordT.setAoms035(CommonUtil.checkNullOrNot(detail.getShippingCost()));
      aomsordT.setAoms036(
          CommonUtil.checkNullOrNot(orderContact.getLastName() + orderContact.getFirstName()));

      // 省市区不需要标准化，因为是发往国际快递固定站点，国外的地址不需要再处理。
      aomsordT.setAoms037(CommonUtil.checkNullOrNot(orderContact.getState()));
      aomsordT.setAoms038(CommonUtil.checkNullOrNot(orderContact.getCity()));

      aomsordT.setAoms040(CommonUtil
          .checkNullOrNot(orderContact.getAddressLine1() + orderContact.getAddressLine2()));
      aomsordT.setAoms041(CommonUtil.checkNullOrNot(orderContact.getPostalcode()));
      aomsordT.setAoms043(CommonUtil.checkNullOrNot(orderContact.getTelephone()));
      aomsordT.setAoms045(CommonUtil.checkNullOrNot(detail.getCommissionAmount()));

      aomsordT.setStoreId(storeId);
      aomsordT.setStoreType(DhgateCommonTool.STORE_TYPE);

      aomsordT.setAoms059(CommonUtil.checkNullOrNot(orderProduct.getItemcode()));
      
      aomsordT.setAoms060(CommonUtil.checkNullOrNot(outerSkuToSkuMap.get(orderProduct.getSkuCode())));
      aomsordT.setAoms061(CommonUtil.checkNullOrNot(orderProduct.getItemAttr()));
      aomsordT.setAoms062(CommonUtil.checkNullOrNot(orderProduct.getItemCount()));
      aomsordT.setAoms064(CommonUtil.checkNullOrNot(orderProduct.getItemPrice()));

      long l062 = orderProduct.getItemCount();
      double d064 = orderProduct.getItemPrice();
      aomsordT.setAoms071(CommonUtil.checkNullOrNot(l062 * d064));

      Date now = new Date();
      aomsordT.setAomsstatus("0");
      aomsordT.setAomscrtdt(CommonUtil.checkNullOrNot(now));
      aomsordT.setAomsmoddt(DateTimeTool.formatToMillisecond(now));

      aomsordTs.add(aomsordT);
    }

    return aomsordTs;
  }

  /**
   * 拼接海外联系人地址。格式如下：
   * <p>Contact Name: Amy Sapkota Address Line 1: 7002 Southwark Terrace Address Line 2: City: Hyattsville State: Maryland Country: United States Postal Code: 20782 Phone Number: 1-3016997583</p>
   * @param orderContact
   * @return
   */
  private String generateOverseaAddress(OrderContact orderContact) {
    StringBuilder sbBuilder = new StringBuilder(200);

    sbBuilder.append("Contact Name: ")
      .append(orderContact.getLastName()).append(" ").append(CommonUtil.checkNullOrNot(orderContact.getFirstName()))
      .append(" ").append("Address Line 1: ").append(CommonUtil.checkNullOrNot(orderContact.getAddressLine1()))
      .append(" ").append("Address Line 2: ").append(CommonUtil.checkNullOrNot(orderContact.getAddressLine2()))
      .append(" ").append("City: ").append(CommonUtil.checkNullOrNot(orderContact.getCity()))
      .append(" ").append("State: ").append(CommonUtil.checkNullOrNot(orderContact.getState()))
      .append(" ").append("Country: ").append(CommonUtil.checkNullOrNot(orderContact.getCountry()))
      .append(" ").append("Postal Code: ").append(CommonUtil.checkNullOrNot(orderContact.getPostalcode()))
      .append(" ").append("Phone Number: ").append(CommonUtil.checkNullOrNot(orderContact.getTelephone()));
      
    return sbBuilder.toString();
  }
  
  public static void main(String[] args) {
//    OrderContact orderContact = new OrderContact();
//    orderContact.setAddressLine1("7002 Southwark Terrace");
//    orderContact.setCity("Hyattsville");
//    orderContact.setState("Maryland");
//    orderContact.setCountry("United States");
//    orderContact.setPostalcode("20782");
//    orderContact.setTelephone("1-3016997583");
//    orderContact.setLastName("Amy");
//    orderContact.setFirstName("Sapkota");
//    
//    System.out.println(new AomsordTTranslator().generateOverseaAddress(orderContact));
  }
}
