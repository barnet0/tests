package com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RefundInfo {
	
	@XmlElement(name = "orderId")
	private String orderId;
	
	@XmlElement(name = "isAgree")
	private String isAgree;
	
	@XmlElement(name = "shopId")
	private String shopId;
	
	@XmlElement(name = "refundSource")
	private String refundSource;
	
	@XmlElement(name = "totalAmount")
	private String totalAmount;
	
	@XmlElement(name = "refundAmount")
	private String refundAmount;
	
	@XmlElement(name = "refundDate")
	private String refundDate;
	
	@XmlElement(name = "creationDate")
	private String creationDate;
	
	@XmlElement(name = "lastModifiedDate")
	private String lastModifiedDate;
	
	@XmlElement(name = "remark")
	private String remark;
	
	public RefundInfo() {
		
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getRefundSource() {
		return refundSource;
	}

	public void setRefundSource(String refundSource) {
		this.refundSource = refundSource;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
		
}