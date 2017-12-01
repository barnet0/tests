package com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfo {
	
	@XmlElement(name = "orderID")
	private String orderID;

	@XmlElement(name = "consigneeName")
	private String consigneeName;

	@XmlElement(name = "consigneeTel")
	private String consigneeTel;
	
	@XmlElement(name = "consigneeMobileTel")
	private String consigneeMobileTel;

	@XmlElement(name = "consigneeAddr")
	private String consigneeAddr;
	
	@XmlElement(name = "DangdangWarehouseAddr")
	private String dangdangWarehouseAddr;

	@XmlElement(name = "sendGoodsMode")
	private String sendGoodsMode;

	@XmlElement(name = "orderMoney")
	private String orderMoney;

	@XmlElement(name = "orderTimeStart")
	private String orderTimeStart;

	@XmlElement(name = "lastModifyTime")
	private String lastModifyTime;

	@XmlElement(name = "orderState")
	private String orderState;

	@XmlElement(name = "orderStatus")
	private String orderStatus;

	@XmlElement(name = "isCourierReceiptDetail")
	private String isCourierReceiptDetail;

	@XmlElement(name = "outerOrderID")
	private String outerOrderID;
	
	@XmlElement(name = "remark")
	private String remark;
	
	@XmlElement(name = "label")
	private String label;
	
	@XmlElement(name = "paymentDate")
	private String paymentDate;

	@XmlElement(name = "orderMode")
	private String orderMode;

	@XmlElement(name = "isPresale")
	private String isPresale;
	
	@XmlElement(name = "sendDate")
	private String sendDate;

	@XmlElement(name = "originalOrderId")
	private String originalOrderId;
	
	public OrderInfo() {
		
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeTel() {
		return consigneeTel;
	}

	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}

	public String getConsigneeMobileTel() {
		return consigneeMobileTel;
	}

	public void setConsigneeMobileTel(String consigneeMobileTel) {
		this.consigneeMobileTel = consigneeMobileTel;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public String getDangdangWarehouseAddr() {
		return dangdangWarehouseAddr;
	}

	public void setDangdangWarehouseAddr(String dangdangWarehouseAddr) {
		this.dangdangWarehouseAddr = dangdangWarehouseAddr;
	}

	public String getSendGoodsMode() {
		return sendGoodsMode;
	}

	public void setSendGoodsMode(String sendGoodsMode) {
		this.sendGoodsMode = sendGoodsMode;
	}

	public String getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getOrderTimeStart() {
		return orderTimeStart;
	}

	public void setOrderTimeStart(String orderTimeStart) {
		this.orderTimeStart = orderTimeStart;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getIsCourierReceiptDetail() {
		return isCourierReceiptDetail;
	}

	public void setIsCourierReceiptDetail(String isCourierReceiptDetail) {
		this.isCourierReceiptDetail = isCourierReceiptDetail;
	}

	public String getOuterOrderID() {
		return outerOrderID;
	}

	public void setOuterOrderID(String outerOrderID) {
		this.outerOrderID = outerOrderID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getOrderMode() {
		return orderMode;
	}

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	public String getIsPresale() {
		return isPresale;
	}

	public void setIsPresale(String isPresale) {
		this.isPresale = isPresale;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getOriginalOrderId() {
		return originalOrderId;
	}

	public void setOriginalOrderId(String originalOrderId) {
		this.originalOrderId = originalOrderId;
	}

	
		
}