package com.digiwin.ecims.platforms.pdd.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.pdd.bean.domain.order.OrderItem;
import com.digiwin.ecims.platforms.pdd.bean.response.PddBaseResponse;

public class OrderGetResponse extends PddBaseResponse {

  private String orderSN;
  
  private String payTime;
  
  private String buyerName;
  
  private String buyerID;
  
  private String createdTime;
  
  private String country;
  
  private String province;
  
  private String city;
  
  private String town;
  
  private String address;
  
  private String zip;
  
  private String email;
  
  private String phone;
  
  private Double orderAmount;
  
  private Double goodsAmount;
  
  private Double discountAmount;
  
  private Double sellerDiscount;
  
  private Double platformDiscount;
  
  private Double postage;
  
  private String payType;
  
  private String payNo;
  
  private String logisticsName;
  
  private String trackingNumber;
  
  private String shippingTime;
  
  private String idNum;
  
  private String idName;
  
  private String customerRemark;
  
  private String invoiceTitle;
  
  private String sellMemo;
  
  private List<OrderItem> itemList;

  /**
   * @return the orderSN
   */
  public String getOrderSN() {
    return orderSN;
  }

  /**
   * @param orderSN the orderSN to set
   */
  public void setOrderSN(String orderSN) {
    this.orderSN = orderSN;
  }

  /**
   * @return the payTime
   */
  public String getPayTime() {
    return payTime;
  }

  /**
   * @param payTime the payTime to set
   */
  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }

  /**
   * @return the buyerName
   */
  public String getBuyerName() {
    return buyerName;
  }

  /**
   * @param buyerName the buyerName to set
   */
  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }

  /**
   * @return the buyerID
   */
  public String getBuyerID() {
    return buyerID;
  }

  /**
   * @param buyerID the buyerID to set
   */
  public void setBuyerID(String buyerID) {
    this.buyerID = buyerID;
  }

  /**
   * @return the createdTime
   */
  public String getCreatedTime() {
    return createdTime;
  }

  /**
   * @param createdTime the createdTime to set
   */
  public void setCreatedTime(String createdTime) {
    this.createdTime = createdTime;
  }

  /**
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * @param country the country to set
   */
  public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @return the province
   */
  public String getProvince() {
    return province;
  }

  /**
   * @param province the province to set
   */
  public void setProvince(String province) {
    this.province = province;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the town
   */
  public String getTown() {
    return town;
  }

  /**
   * @param town the town to set
   */
  public void setTown(String town) {
    this.town = town;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the zip
   */
  public String getZip() {
    return zip;
  }

  /**
   * @param zip the zip to set
   */
  public void setZip(String zip) {
    this.zip = zip;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the orderAmount
   */
  public Double getOrderAmount() {
    return orderAmount;
  }

  /**
   * @param orderAmount the orderAmount to set
   */
  public void setOrderAmount(Double orderAmount) {
    this.orderAmount = orderAmount;
  }

  /**
   * @return the goodsAmount
   */
  public Double getGoodsAmount() {
    return goodsAmount;
  }

  /**
   * @param goodsAmount the goodsAmount to set
   */
  public void setGoodsAmount(Double goodsAmount) {
    this.goodsAmount = goodsAmount;
  }

  /**
   * @return the discountAmount
   */
  public Double getDiscountAmount() {
    return discountAmount;
  }

  /**
   * @param discountAmount the discountAmount to set
   */
  public void setDiscountAmount(Double discountAmount) {
    this.discountAmount = discountAmount;
  }

  public Double getSellerDiscount() {
    return sellerDiscount;
  }

  public void setSellerDiscount(Double sellerDiscount) {
    this.sellerDiscount = sellerDiscount;
  }

  public Double getPlatformDiscount() {
    return platformDiscount;
  }

  public void setPlatformDiscount(Double platformDiscount) {
    this.platformDiscount = platformDiscount;
  }

  /**
   * @return the postage
   */
  public Double getPostage() {
    return postage;
  }

  /**
   * @param postage the postage to set
   */
  public void setPostage(Double postage) {
    this.postage = postage;
  }

  /**
   * @return the payType
   */
  public String getPayType() {
    return payType;
  }

  /**
   * @param payType the payType to set
   */
  public void setPayType(String payType) {
    this.payType = payType;
  }

  /**
   * @return the payNo
   */
  public String getPayNo() {
    return payNo;
  }

  /**
   * @param payNo the payNo to set
   */
  public void setPayNo(String payNo) {
    this.payNo = payNo;
  }

  /**
   * @return the logisticsName
   */
  public String getLogisticsName() {
    return logisticsName;
  }

  /**
   * @param logisticsName the logisticsName to set
   */
  public void setLogisticsName(String logisticsName) {
    this.logisticsName = logisticsName;
  }

  /**
   * @return the trackingNumber
   */
  public String getTrackingNumber() {
    return trackingNumber;
  }

  /**
   * @param trackingNumber the trackingNumber to set
   */
  public void setTrackingNumber(String trackingNumber) {
    this.trackingNumber = trackingNumber;
  }

  /**
   * @return the shippingTime
   */
  public String getShippingTime() {
    return shippingTime;
  }

  /**
   * @param shippingTime the shippingTime to set
   */
  public void setShippingTime(String shippingTime) {
    this.shippingTime = shippingTime;
  }

  /**
   * @return the idNum
   */
  public String getIdNum() {
    return idNum;
  }

  /**
   * @param idNum the idNum to set
   */
  public void setIdNum(String idNum) {
    this.idNum = idNum;
  }

  /**
   * @return the idName
   */
  public String getIdName() {
    return idName;
  }

  /**
   * @param idName the idName to set
   */
  public void setIdName(String idName) {
    this.idName = idName;
  }

  /**
   * @return the customerRemark
   */
  public String getCustomerRemark() {
    return customerRemark;
  }

  /**
   * @param customerRemark the customerRemark to set
   */
  public void setCustomerRemark(String customerRemark) {
    this.customerRemark = customerRemark;
  }

  /**
   * @return the invoiceTitle
   */
  public String getInvoiceTitle() {
    return invoiceTitle;
  }

  /**
   * @param invoiceTitle the invoiceTitle to set
   */
  public void setInvoiceTitle(String invoiceTitle) {
    this.invoiceTitle = invoiceTitle;
  }

  /**
   * @return the sellMemo
   */
  public String getSellMemo() {
    return sellMemo;
  }

  /**
   * @param sellMemo the sellMemo to set
   */
  public void setSellMemo(String sellMemo) {
    this.sellMemo = sellMemo;
  }

  /**
   * @return the itemList
   */
  public List<OrderItem> getItemList() {
    return itemList;
  }

  /**
   * @param itemList the itemList to set
   */
  public void setItemList(List<OrderItem> itemList) {
    this.itemList = itemList;
  }
  
  
}
