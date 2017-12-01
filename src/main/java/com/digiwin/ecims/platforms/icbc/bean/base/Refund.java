package com.digiwin.ecims.platforms.icbc.bean.base;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Refund {

	@XmlElement(name = "order_id")
	private String orderId;

	@XmlElement(name = "refund_id")
	private String refundId;

	@XmlElement(name = "refund_ts")
	private String refundTs;

	@XmlElement(name = "refund_status")
	private String refundStatus;

	@XmlElement(name = "refund_total_amount")
	private BigDecimal refundTotalAmount;

	@XmlElement(name = "refund_freight")
	private BigDecimal refundFreight;

	@XmlElement(name = "refund_cash")
	private BigDecimal refundCash;

	@XmlElement(name = "refund_score")
	private Integer refundScore;

	@XmlElement(name = "refund_coupon")
	private BigDecimal refundCoupon;

	@XmlElement(name = "refund_confirm_ts")
	private String refundConfirmTs;

	@XmlElement(name = "refund_prod_flag")
	private String refundProdFlag;

	@XmlElement(name = "products")
	private Products products;

	public Refund() {

	}

	// icbcb2c.refund.query
	public Refund(String orderId, String refundId, String refundTs,
			String refundStatus, BigDecimal refundTotalAmount,
			BigDecimal refundFreight, BigDecimal refundCash,
			Integer refundScore, BigDecimal refundCoupon,
			String refundConfirmTs, String refundProdFlag, Products products) {
		super();
		this.orderId = orderId;
		this.refundId = refundId;
		this.refundTs = refundTs;
		this.refundStatus = refundStatus;
		this.refundTotalAmount = refundTotalAmount;
		this.refundFreight = refundFreight;
		this.refundCash = refundCash;
		this.refundScore = refundScore;
		this.refundCoupon = refundCoupon;
		this.refundConfirmTs = refundConfirmTs;
		this.refundProdFlag = refundProdFlag;
		this.products = products;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundTs() {
		return refundTs;
	}

	public void setRefundTs(String refundTs) {
		this.refundTs = refundTs;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public BigDecimal getRefundTotalAmount() {
		return refundTotalAmount;
	}

	public void setRefundTotalAmount(BigDecimal refundTotalAmount) {
		this.refundTotalAmount = refundTotalAmount;
	}

	public BigDecimal getRefundFreight() {
		return refundFreight;
	}

	public void setRefundFreight(BigDecimal refundFreight) {
		this.refundFreight = refundFreight;
	}

	public BigDecimal getRefundCash() {
		return refundCash;
	}

	public void setRefundCash(BigDecimal refundCash) {
		this.refundCash = refundCash;
	}

	public Integer getRefundScore() {
		return refundScore;
	}

	public void setRefundScore(Integer refundScore) {
		this.refundScore = refundScore;
	}

	public BigDecimal getRefundCoupon() {
		return refundCoupon;
	}

	public void setRefundCoupon(BigDecimal refundCoupon) {
		this.refundCoupon = refundCoupon;
	}

	public String getRefundConfirmTs() {
		return refundConfirmTs;
	}

	public void setRefundConfirmTs(String refundConfirmTs) {
		this.refundConfirmTs = refundConfirmTs;
	}

	public String getRefundProdFlag() {
		return refundProdFlag;
	}

	public void setRefundProdFlag(String refundProdFlag) {
		this.refundProdFlag = refundProdFlag;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

}
