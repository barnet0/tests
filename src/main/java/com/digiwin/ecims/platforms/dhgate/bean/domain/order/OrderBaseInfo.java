package com.digiwin.ecims.platforms.dhgate.bean.domain.order;

/**
 * 订单基础信息
 * @author 维杰
 *
 */
public class OrderBaseInfo {

//	必须	买家确认收货时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String buyerConfirmDate;
//	必须	买家ID	示例值：ff808081416839d5014168e43ab30033
	private String buyerId;
//	必须	买家别名	买家昵称；示例值：zhangsan
	private String buyerNickName;
//	必须	交易取消时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String cancelDate;
//	必须	收货国家	示例值：United States
	private String country;
//	必须	发货时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String deliveryDate;
//	必须	发货截止时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String deliveryDeadline;
//	必须	入账时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String inAccountDate;
//	必须	是否需要特别注意的订单（如高风险订单、售后纠纷订单等）	值为false或null表示订单正常，true表示此订单需要特别注意一下，例如，防止高风险订单直接发货
	private Boolean isWarn;
//	必须	订单号	卖家后台登录能看到成交的订单号；示例值：1330312162
	private String orderNo;
//	可选	订单备注	蓝色要10个，加急，圣诞前要用到等等备注信息
	private String orderRemark;
//	必须	订单状态	111000,订单取消;101003,等待买家付款;102001,买家已付款，等待平台确认;103001,等待发货;105001,买家申请退款，等待协商结果;105002,退款协议已达成;105003,部分退款后，等待发货;105004,买家取消退款申请;103002,已部分发货;101009,等待买家确认收货;106001,退款/退货协商中，等待协议达成;106002,买家投诉到平台;106003,协议已达成，执行中;102006,人工确认收货;102007,超过预定期限，自动确认收货;102111,交易成功;111111,交易关闭
	private String orderStatus;
//	必须	订单总金额	示例值：100.00
	private Double orderTotalPrice;
//	必须	付款时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String payDate;
//	必须	下单日期	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String startedDate;
//	必须	警告原因	示例值：高风险订单、订单发起售后纠纷、信用卡拒付等
	private String warnReason;
	
	public String getBuyerConfirmDate() {
		return buyerConfirmDate;
	}
	public void setBuyerConfirmDate(String buyerConfirmDate) {
		this.buyerConfirmDate = buyerConfirmDate;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerNickName() {
		return buyerNickName;
	}
	public void setBuyerNickName(String buyerNickName) {
		this.buyerNickName = buyerNickName;
	}
	public String getCancelDate() {
		return cancelDate;
	}
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryDeadline() {
		return deliveryDeadline;
	}
	public void setDeliveryDeadline(String deliveryDeadline) {
		this.deliveryDeadline = deliveryDeadline;
	}
	public String getInAccountDate() {
		return inAccountDate;
	}
	public void setInAccountDate(String inAccountDate) {
		this.inAccountDate = inAccountDate;
	}
	public Boolean isWarn() {
		return isWarn;
	}
	public void setWarn(Boolean isWarn) {
		this.isWarn = isWarn;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(Double orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getWarnReason() {
		return warnReason;
	}
	public void setWarnReason(String warnReason) {
		this.warnReason = warnReason;
	}
	
	public OrderBaseInfo() {
		super();
	}
	
	
}
