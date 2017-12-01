package com.digiwin.ecims.platforms.dhgate.bean.domain.order;

import java.util.List;

/**
 * 订单详情
 * @author 维杰
 *
 */
public class OrderDetail {

//  必须	实收金额	示例值:100.00
	private Double actualPrice;
//  必须	佣金金额	示例值:100.00
	private Double commissionAmount;
//	必须	发货时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String deliveryDate;
//	 必须	发货截止时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String deliveryDeadline;
//	 必须	订单补款金额	示例值:100.00
	private Double fillingMoney;
//	 必须	网关手续费	示例值:100.00
	private Double gatewayFee;
//	 必须	产品总计	示例值:100.00
	private Double itemTotalPrice;
//	 必须	收货人基本信息	订单基本信息
	private OrderContact orderContact;
//	 必须	订单处理信息	订单处理信息
	private List<OrderDeliveryInfo> orderDeliveryList;
//	 必须	订单编号	卖家后台登录能看到成交的订单号；示例值：1330312162
	private String orderNo;
//	 可选	订单备注	蓝色要10个，加急，圣诞前要用到等等备注信息
	private String orderRemark;
//	 必须	订单状态	111000,订单取消;101003,等待买家付款;102001,买家已付款，等待平台确认;103001,等待发货;105001,买家申请退款，等待协商结果;105002,退款协议已达成;105003,部分退款后，等待发货;105004,买家取消退款申请;103002,已部分发货;101009,等待买家确认收货;106001,退款/退货协商中，等待协议达成;106002,买家投诉到平台;106003,协议已达成，执行中;102006,人工确认收货;102007,超过预定期限，自动确认收货;102111,交易成功;111111,交易关闭
	private String orderStatus;
//	 必须	订单总额	示例值:100.00
	private Double orderTotalPrice;
//	 必须	付款时间	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String payDate;
//	 必须	订单降价金额	示例值:100.00
	private Double reducePrice;
//	 必须	订单退款金额	示例值:100.00
	private Double refundMoney;
//	 必须	订单涨价金额	示例值:100.00
	private Double risePrice;
//	 必须	seller优惠券	示例值:100.00
	private Double sellerCouponPrice;
//	 必须	运费	示例值:100.00
	private Double shippingCost;
//	 必须	买家选择物流方式	示例值：UPS,D-LINK等
	private String shippingType;
//	 必须	下单日期	日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
	private String startedDate;
	
	public Double getActualPrice() {
		return actualPrice;
	}
	public void setActualPrice(Double actualPrice) {
		this.actualPrice = actualPrice;
	}
	public Double getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(Double commissionAmount) {
		this.commissionAmount = commissionAmount;
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
	public Double getFillingMoney() {
		return fillingMoney;
	}
	public void setFillingMoney(Double fillingMoney) {
		this.fillingMoney = fillingMoney;
	}
	public Double getGatewayFee() {
		return gatewayFee;
	}
	public void setGatewayFee(Double gatewayFee) {
		this.gatewayFee = gatewayFee;
	}
	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}
	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}
	public OrderContact getOrderContact() {
		return orderContact;
	}
	public void setOrderContact(OrderContact orderContact) {
		this.orderContact = orderContact;
	}
	public List<OrderDeliveryInfo> getOrderDeliveryList() {
		return orderDeliveryList;
	}
	public void setOrderDeliveryList(List<OrderDeliveryInfo> orderDeliveryList) {
		this.orderDeliveryList = orderDeliveryList;
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
	public Double getReducePrice() {
		return reducePrice;
	}
	public void setReducePrice(Double reducePrice) {
		this.reducePrice = reducePrice;
	}
	public Double getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(Double refundMoney) {
		this.refundMoney = refundMoney;
	}
	public Double getRisePrice() {
		return risePrice;
	}
	public void setRisePrice(Double risePrice) {
		this.risePrice = risePrice;
	}
	public Double getSellerCouponPrice() {
		return sellerCouponPrice;
	}
	public void setSellerCouponPrice(Double sellerCouponPrice) {
		this.sellerCouponPrice = sellerCouponPrice;
	}
	public Double getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}
	public String getShippingType() {
		return shippingType;
	}
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	
	public OrderDetail() {
		super();
	}
	
	
}
