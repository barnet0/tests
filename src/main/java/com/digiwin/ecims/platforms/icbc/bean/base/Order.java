package com.digiwin.ecims.platforms.icbc.bean.base;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

	@XmlElement(name = "order_id")
	private String orderId;

	@XmlElement(name = "order_create_time")
	private String orderCreateTime;

	@XmlElement(name = "order_modify_time")
	private String orderModifyTime;

	@XmlElement(name = "order_status")
	private String orderStatus;

	@XmlElement(name = "order_buyer_remark")
	private String orderBuyerRemark;

	@XmlElement(name = "order_seller_remark")
	private String orderSellerRemark;

	@XmlElement(name = "order_buyer_id")
	private String orderBuyerId;

	@XmlElement(name = "order_buyer_username")
	private String orderBuyerUsername;

	@XmlElement(name = "order_buyer_name")
	private String orderBuyerName;

	@XmlElement(name = "order_amount")
	private BigDecimal orderAmount;

	@XmlElement(name = "order_credit_amount")
	private BigDecimal orderCreditAmount;

	@XmlElement(name = "credit_liquid_amount")
	private BigDecimal creditLiquidAmount;

	@XmlElement(name = "order_other_discount")
	private BigDecimal orderOtherDiscount;

	@XmlElement(name = "order_channel")
	private Integer orderChannel;

	@XmlElement(name = "order_coupon_amount")
	private BigDecimal orderCouponAmount;

	@XmlElement(name = "discounts")
	private Discounts discounts;

	@XmlElement(name = "products")
	private Products products;

	@XmlElement(name = "invoice")
	private Invoice invoice;

	@XmlElement(name = "payment")
	private Payment payment;

	@XmlElement(name = "consignee")
	private Consignee consignee;

	public Order() {

	}

	// icbcorderlist用到
	public Order(String orderId, String orderCreateTime,
			String orderModifyTime, String orderStatus) {
		this.orderId = orderId;
		this.orderCreateTime = orderCreateTime;
		this.orderModifyTime = orderModifyTime;
		this.orderStatus = orderStatus;
	}

	// icbcorderdetail用到
	public Order(String orderId, String orderModifyTime, String orderStatus,
			String orderBuyerRemark, String orderSellerRemark,
			String orderBuyerId, String orderBuyerUsername,
			String orderBuyerName, String orderCreateTime,
			BigDecimal orderAmount, BigDecimal orderCreditAmount,
			BigDecimal creditLiquidAmount, BigDecimal orderOtherDiscount,
			Integer orderChannel, BigDecimal orderCouponAmount,
			Discounts discounts, Products products, Invoice invoice,
			Payment payment, Consignee consignee) {
		this.orderId = orderId;
		this.orderModifyTime = orderModifyTime;
		this.orderStatus = orderStatus;
		this.orderBuyerRemark = orderBuyerRemark;
		this.orderSellerRemark = orderSellerRemark;
		this.orderBuyerId = orderBuyerId;
		this.orderBuyerUsername = orderBuyerUsername;
		this.orderBuyerName = orderBuyerName;
		this.orderCreateTime = orderCreateTime;
		this.orderAmount = orderAmount;
		this.orderCreditAmount = orderCreditAmount;
		this.creditLiquidAmount = creditLiquidAmount;
		this.orderOtherDiscount = orderOtherDiscount;
		this.orderChannel = orderChannel;
		this.orderCouponAmount = orderCouponAmount;
		this.discounts = discounts;
		this.products = products;
		this.invoice = invoice;
		this.payment = payment;
		this.consignee = consignee;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public String getOrderModifyTime() {
		return orderModifyTime;
	}

	public void setOrderModifyTime(String orderModifyTime) {
		this.orderModifyTime = orderModifyTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderBuyerRemark() {
		return orderBuyerRemark;
	}

	public void setOrderBuyerRemark(String orderBuyerRemark) {
		this.orderBuyerRemark = orderBuyerRemark;
	}

	public String getOrderSellerRemark() {
		return orderSellerRemark;
	}

	public void setOrderSellerRemark(String orderSellerRemark) {
		this.orderSellerRemark = orderSellerRemark;
	}

	public String getOrderBuyerId() {
		return orderBuyerId;
	}

	public void setOrderBuyerId(String orderBuyerId) {
		this.orderBuyerId = orderBuyerId;
	}

	public String getOrderBuyerUsername() {
		return orderBuyerUsername;
	}

	public void setOrderBuyerUsername(String orderBuyerUsername) {
		this.orderBuyerUsername = orderBuyerUsername;
	}

	public String getOrderBuyerName() {
		return orderBuyerName;
	}

	public void setOrderBuyerName(String orderBuyerName) {
		this.orderBuyerName = orderBuyerName;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getOrderCreditAmount() {
		return orderCreditAmount;
	}

	public void setOrderCreditAmount(BigDecimal orderCreditAmount) {
		this.orderCreditAmount = orderCreditAmount;
	}

	public BigDecimal getCreditLiquidAmount() {
		return creditLiquidAmount;
	}

	public void setCreditLiquidAmount(BigDecimal creditLiquidAmount) {
		this.creditLiquidAmount = creditLiquidAmount;
	}

	public BigDecimal getOrderOtherDiscount() {
		return orderOtherDiscount;
	}

	public void setOrderOtherDiscount(BigDecimal orderOtherDiscount) {
		this.orderOtherDiscount = orderOtherDiscount;
	}

	public Integer getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(Integer orderChannel) {
		this.orderChannel = orderChannel;
	}

	public BigDecimal getOrderCouponAmount() {
		return orderCouponAmount;
	}

	public void setOrderCouponAmount(BigDecimal orderCouponAmount) {
		this.orderCouponAmount = orderCouponAmount;
	}

	public Discounts getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Discounts discounts) {
		this.discounts = discounts;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Consignee getConsignee() {
		return consignee;
	}

	public void setConsignee(Consignee consignee) {
		this.consignee = consignee;
	}

}
