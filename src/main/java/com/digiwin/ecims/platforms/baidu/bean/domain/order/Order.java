package com.digiwin.ecims.platforms.baidu.bean.domain.order;

import java.util.List;

/**
 * 订单基础信息
 * @author 维杰
 *
 */
public class Order {
  private Long orderNo;

  private Integer type;

  private String status;
  
  private String productFee;

  private String promotionFee;
  
  private String merchantCouponFee;
  
  private String platformCouponFee;
  
  private String paymentFee;
  
  private Integer payMethod;
  
  private Boolean needInvoice;
  
  private String createTime;
  
  private String updateTime;
  
  private String paySuccessTime;
  
  private String sendGoodsTime;
  
  private String receiveGoodsTime;
  
  private String successTime;
  
  private Long userId;
  
  private String userMessage;
  
  private Long shopId;

  private String shopName;

  private Long merchantId;

  private String merchantName;
  
  private Coupon merchantCoupon;
  
  private Coupon platformCoupon;
  
  private List<Promotion> promotionList;
  
  private String memo;
  
  private List<OrderItem> orderItemList;
  
  private InvoiceInfo invoice;

  private DeliveryInfo delivery;
  
  private List<PromotionGiftInfo> orderPromotionGiftList;
  
  private Long deliverCompanyId;

  private String receiverAddress;

  private String userMobileNo;

  private Integer appealId;

  private String receiverName;

  private String deliveryFee;

  private String receiverMobileNo;

  private String saleChannel;

  private Boolean canAppeal;

  private String deliveryNo;
  
  private String deliverCompanyName;
  
  private Long paymentId;

  private String cancelTime;

  private Integer paymentStatus;

  private String userEmail;
  
  private Integer refundStatus;
  
  private String userName;
  
  public Long getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Long orderNo) {
    this.orderNo = orderNo;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getProductFee() {
    return productFee;
  }

  public void setProductFee(String productFee) {
    this.productFee = productFee;
  }

  public String getPromotionFee() {
    return promotionFee;
  }

  public void setPromotionFee(String promotionFee) {
    this.promotionFee = promotionFee;
  }

  public String getMerchantCouponFee() {
    return merchantCouponFee;
  }

  public void setMerchantCouponFee(String merchantCouponFee) {
    this.merchantCouponFee = merchantCouponFee;
  }

  public String getPlatformCouponFee() {
    return platformCouponFee;
  }

  public void setPlatformCouponFee(String platformCouponFee) {
    this.platformCouponFee = platformCouponFee;
  }

  public String getPaymentFee() {
    return paymentFee;
  }

  public void setPaymentFee(String paymentFee) {
    this.paymentFee = paymentFee;
  }

  public Integer getPayMethod() {
    return payMethod;
  }

  public void setPayMethod(Integer payMethod) {
    this.payMethod = payMethod;
  }

  public Boolean getNeedInvoice() {
    return needInvoice;
  }

  public void setNeedInvoice(Boolean needInvoice) {
    this.needInvoice = needInvoice;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public String getPaySuccessTime() {
    return paySuccessTime;
  }

  public void setPaySuccessTime(String paySuccessTime) {
    this.paySuccessTime = paySuccessTime;
  }

  public String getSendGoodsTime() {
    return sendGoodsTime;
  }

  public void setSendGoodsTime(String sendGoodsTime) {
    this.sendGoodsTime = sendGoodsTime;
  }

  public String getReceiveGoodsTime() {
    return receiveGoodsTime;
  }

  public void setReceiveGoodsTime(String receiveGoodsTime) {
    this.receiveGoodsTime = receiveGoodsTime;
  }

  public String getSuccessTime() {
    return successTime;
  }

  public void setSuccessTime(String successTime) {
    this.successTime = successTime;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUserMessage() {
    return userMessage;
  }

  public void setUserMessage(String userMessage) {
    this.userMessage = userMessage;
  }

  public Long getShopId() {
    return shopId;
  }

  public void setShopId(Long shopId) {
    this.shopId = shopId;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public Long getMerchantId() {
    return merchantId;
  }

  public void setMerchantId(Long merchantId) {
    this.merchantId = merchantId;
  }

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public Coupon getMerchantCoupon() {
    return merchantCoupon;
  }

  public void setMerchantCoupon(Coupon merchantCoupon) {
    this.merchantCoupon = merchantCoupon;
  }

  public Coupon getPlatformCoupon() {
    return platformCoupon;
  }

  public void setPlatformCoupon(Coupon platformCoupon) {
    this.platformCoupon = platformCoupon;
  }

  public List<Promotion> getPromotionList() {
    return promotionList;
  }

  public void setPromotionList(List<Promotion> promotionList) {
    this.promotionList = promotionList;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }

  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }

  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }

  public InvoiceInfo getInvoice() {
    return invoice;
  }

  public void setInvoice(InvoiceInfo invoice) {
    this.invoice = invoice;
  }

  public DeliveryInfo getDelivery() {
    return delivery;
  }

  public void setDelivery(DeliveryInfo delivery) {
    this.delivery = delivery;
  }

  public List<PromotionGiftInfo> getOrderPromotionGiftList() {
    return orderPromotionGiftList;
  }

  public void setOrderPromotionGiftList(List<PromotionGiftInfo> orderPromotionGiftList) {
    this.orderPromotionGiftList = orderPromotionGiftList;
  }

  public Long getDeliverCompanyId() {
    return deliverCompanyId;
  }

  public void setDeliverCompanyId(Long deliverCompanyId) {
    this.deliverCompanyId = deliverCompanyId;
  }

  public String getReceiverAddress() {
    return receiverAddress;
  }

  public void setReceiverAddress(String receiverAddress) {
    this.receiverAddress = receiverAddress;
  }

  public String getUserMobileNo() {
    return userMobileNo;
  }

  public void setUserMobileNo(String userMobileNo) {
    this.userMobileNo = userMobileNo;
  }

  public Integer getAppealId() {
    return appealId;
  }

  public void setAppealId(Integer appealId) {
    this.appealId = appealId;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getDeliveryFee() {
    return deliveryFee;
  }

  public void setDeliveryFee(String deliveryFee) {
    this.deliveryFee = deliveryFee;
  }

  public String getReceiverMobileNo() {
    return receiverMobileNo;
  }

  public void setReceiverMobileNo(String receiverMobileNo) {
    this.receiverMobileNo = receiverMobileNo;
  }

  public String getSaleChannel() {
    return saleChannel;
  }

  public void setSaleChannel(String saleChannel) {
    this.saleChannel = saleChannel;
  }

  public Boolean getCanAppeal() {
    return canAppeal;
  }

  public void setCanAppeal(Boolean canAppeal) {
    this.canAppeal = canAppeal;
  }

  public String getDeliveryNo() {
    return deliveryNo;
  }

  public void setDeliveryNo(String deliveryNo) {
    this.deliveryNo = deliveryNo;
  }

  public String getDeliverCompanyName() {
    return deliverCompanyName;
  }

  public void setDeliverCompanyName(String deliverCompanyName) {
    this.deliverCompanyName = deliverCompanyName;
  }

  public Long getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(Long paymentId) {
    this.paymentId = paymentId;
  }

  public String getCancelTime() {
    return cancelTime;
  }

  public void setCancelTime(String cancelTime) {
    this.cancelTime = cancelTime;
  }

  public Integer getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(Integer paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public Integer getRefundStatus() {
    return refundStatus;
  }

  public void setRefundStatus(Integer refundStatus) {
    this.refundStatus = refundStatus;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  
}
