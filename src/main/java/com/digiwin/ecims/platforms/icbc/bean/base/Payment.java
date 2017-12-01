package com.digiwin.ecims.platforms.icbc.bean.base;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Payment {

	@XmlElement(name = "order_pay_time")
	private String orderPayTime;

	@XmlElement(name = "order_pay_amount")
	private BigDecimal orderPayAmount;

	@XmlElement(name = "order_pay_sys")
	private String orderPaySys;

	@XmlElement(name = "order_discount_amount")
	private BigDecimal orderDiscountAmount;

	@XmlElement(name = "order_freight")
	private BigDecimal orderFreight;

	@XmlElement(name = "pay_serial")
	private String paySerial;

	public Payment() {

	}

	// icbc.order.detail用到
	public Payment(String orderPayTime, BigDecimal orderPayAmount,
			String orderPaySys, BigDecimal orderDiscountAmount,
			BigDecimal orderFreight, String paySerial) {
		super();
		this.orderPayTime = orderPayTime;
		this.orderPayAmount = orderPayAmount;
		this.orderPaySys = orderPaySys;
		this.orderDiscountAmount = orderDiscountAmount;
		this.orderFreight = orderFreight;
		this.paySerial = paySerial;
	}

	public String getOrderPayTime() {
		return orderPayTime;
	}

	public void setOrderPayTime(String orderPayTime) {
		this.orderPayTime = orderPayTime;
	}

	public BigDecimal getOrderPayAmount() {
		return orderPayAmount;
	}

	public void setOrderPayAmount(BigDecimal orderPayAmount) {
		this.orderPayAmount = orderPayAmount;
	}

	public String getOrderPaySys() {
		return orderPaySys;
	}

	public void setOrderPaySys(String orderPaySys) {
		this.orderPaySys = orderPaySys;
	}

	public BigDecimal getOrderDiscountAmount() {
		return orderDiscountAmount;
	}

	public void setOrderDiscountAmount(BigDecimal orderDiscountAmount) {
		this.orderDiscountAmount = orderDiscountAmount;
	}

	public BigDecimal getOrderFreight() {
		return orderFreight;
	}

	public void setOrderFreight(BigDecimal orderFreight) {
		this.orderFreight = orderFreight;
	}

	public String getPaySerial() {
		return paySerial;
	}

	public void setPaySerial(String paySerial) {
		this.paySerial = paySerial;
	}

}
