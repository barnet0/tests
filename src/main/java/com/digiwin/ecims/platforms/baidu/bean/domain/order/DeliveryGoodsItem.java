package com.digiwin.ecims.platforms.baidu.bean.domain.order;

public class DeliveryGoodsItem {

  private Long orderNo;

  private String deliveryCompany;

  private Long deliveryCompanyId;

  private String deliveryNo;

  public Long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Long orderNo) {
    this.orderNo = orderNo;
  }

  public String getDeliveryCompany() {
    return deliveryCompany;
  }

  public void setDeliveryCompany(String deliveryCompany) {
    this.deliveryCompany = deliveryCompany;
  }

  public Long getDeliveryCompanyId() {
    return deliveryCompanyId;
  }

  public void setDeliveryCompanyId(Long deliveryCompanyId) {
    this.deliveryCompanyId = deliveryCompanyId;
  }

  public String getDeliveryNo() {
    return deliveryNo;
  }

  public void setDeliveryNo(String deliveryNo) {
    this.deliveryNo = deliveryNo;
  }

  public DeliveryGoodsItem() {
  }

  public DeliveryGoodsItem(Long orderNo, String deliveryCompany, Long deliveryCompanyId,
      String deliveryNo) {
    super();
    this.orderNo = orderNo;
    this.deliveryCompany = deliveryCompany;
    this.deliveryCompanyId = deliveryCompanyId;
    this.deliveryNo = deliveryNo;
  }


}
