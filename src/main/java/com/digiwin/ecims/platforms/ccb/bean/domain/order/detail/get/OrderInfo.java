package com.digiwin.ecims.platforms.ccb.bean.domain.order.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.ccb.bean.domain.order.OrderInvoiceInfo;
import com.digiwin.ecims.platforms.ccb.bean.domain.order.OrderProductItemList;
import com.digiwin.ecims.platforms.ccb.bean.domain.order.OrderShippingInfo;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfo {

  @XmlElement(name = "order_id")
  private String orderId;

  @XmlElement(name = "order_memo")
  private String orderMemo;

  @XmlElement(name = "status")
  private String status;

  @XmlElement(name = "buyer_email")
  private String buyerEmail;

  @XmlElement(name = "buyer_name")
  private String buyerName;
  
  @XmlElement(name = "buyer_id")
  private String buyerId;

  @XmlElement(name = "order_time")
  private String orderTime;

  @XmlElement(name = "payment_time")
  private String paymentTime;

  @XmlElement(name = "order_prod_amt")
  private String orderProdAmt;

  @XmlElement(name = "order_pay_amt")
  private String orderPayAmt;

  @XmlElement(name = "order_coupon")
  private String orderCoupon;

  @XmlElement(name = "merchant_discount")
  private String merchantDiscount;

  @XmlElement(name = "delivery_fee")
  private String deliveryFee;

  @XmlElement(name = "shipping_info")
  private OrderShippingInfo shippingInfo;

  @XmlElement(name = "product_items")
  private OrderProductItemList productItems;

  @XmlElement(name = "invoice_info")
  private OrderInvoiceInfo invoiceInfo;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderMemo() {
    return orderMemo;
  }

  public void setOrderMemo(String orderMemo) {
    this.orderMemo = orderMemo;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBuyerEmail() {
    return buyerEmail;
  }

  public void setBuyerEmail(String buyerEmail) {
    this.buyerEmail = buyerEmail;
  }

  public String getBuyerName() {
    return buyerName;
  }

  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }

  public String getBuyerId() {
    return buyerId;
  }

  public void setBuyerId(String buyerId) {
    this.buyerId = buyerId;
  }

  public String getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(String orderTime) {
    this.orderTime = orderTime;
  }

  public String getPaymentTime() {
    return paymentTime;
  }

  public void setPaymentTime(String paymentTime) {
    this.paymentTime = paymentTime;
  }

  public String getOrderProdAmt() {
    return orderProdAmt;
  }

  public void setOrderProdAmt(String orderProdAmt) {
    this.orderProdAmt = orderProdAmt;
  }

  public String getOrderPayAmt() {
    return orderPayAmt;
  }

  public void setOrderPayAmt(String orderPayAmt) {
    this.orderPayAmt = orderPayAmt;
  }

  public String getOrderCoupon() {
    return orderCoupon;
  }

  public void setOrderCoupon(String orderCoupon) {
    this.orderCoupon = orderCoupon;
  }

  public String getMerchantDiscount() {
    return merchantDiscount;
  }

  public void setMerchantDiscount(String merchantDiscount) {
    this.merchantDiscount = merchantDiscount;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public OrderShippingInfo getShippingInfo() {
    return shippingInfo;
  }

  public void setShippingInfo(OrderShippingInfo shippingInfo) {
    this.shippingInfo = shippingInfo;
  }

  public OrderProductItemList getProductItems() {
    return productItems;
  }

  public void setProductItems(OrderProductItemList productItems) {
    this.productItems = productItems;
  }

  public OrderInvoiceInfo getInvoiceInfo() {
    return invoiceInfo;
  }

  public void setInvoiceInfo(OrderInvoiceInfo invoiceInfo) {
    this.invoiceInfo = invoiceInfo;
  }
  
  
}
