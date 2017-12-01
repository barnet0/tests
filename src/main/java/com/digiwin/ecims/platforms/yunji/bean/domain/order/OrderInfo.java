package com.digiwin.ecims.platforms.yunji.bean.domain.order;

import java.util.List;

public class OrderInfo {
	private String orderId;
	private String tradeOrderId;
	private String payId;
	private String payTime;
	private int status;
	private String receiverProvince;
	private String receiverCity;
	private String receiverArea;
	private String receiverAddress;
	private String receiverMobile;
	private String receiverPhone;
	private String receiverName;
	private String receiverZipCode;
	private String buyerComment;
	private int idCardType;
	private String cardNumber;
	private int totalPrice;
	private int itemPrice;
	private int taxPrice;
	private int logisticsPrice;
	private String payEntName;
	private String payEntNo;
	private String hasInvoice;
	private String invoiceHead;
	private String invoiceContent;
	private String createTime;
	private String modifyTime;
	private List<OrderDetailInfo> orderDetail;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTradeOrderId() {
		return tradeOrderId;
	}
	public void setTradeOrderId(String tradeOrderId) {
		this.tradeOrderId = tradeOrderId;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getReceiverProvince() {
		return receiverProvince;
	}
	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverArea() {
		return receiverArea;
	}
	public void setReceiverArea(String receiverArea) {
		this.receiverArea = receiverArea;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverZipCode() {
		return receiverZipCode;
	}
	public void setReceiverZipCode(String receiverZipCode) {
		this.receiverZipCode = receiverZipCode;
	}
	public String getBuyerComment() {
		return buyerComment;
	}
	public void setBuyerComment(String buyerComment) {
		this.buyerComment = buyerComment;
	}
	public int getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(int idCardType) {
		this.idCardType = idCardType;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(int itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(int taxPrice) {
		this.taxPrice = taxPrice;
	}
	public int getLogisticsPrice() {
		return logisticsPrice;
	}
	public void setLogisticsPrice(int logisticsPrice) {
		this.logisticsPrice = logisticsPrice;
	}
	public String getPayEntName() {
		return payEntName;
	}
	public void setPayEntName(String payEntName) {
		this.payEntName = payEntName;
	}
	public String getPayEntNo() {
		return payEntNo;
	}
	public void setPayEntNo(String payEntNo) {
		this.payEntNo = payEntNo;
	}
	public String getHasInvoice() {
		return hasInvoice;
	}
	public void setHasInvoice(String hasInvoice) {
		this.hasInvoice = hasInvoice;
	}
	public String getInvoiceHead() {
		return invoiceHead;
	}
	public void setInvoiceHead(String invoiceHead) {
		this.invoiceHead = invoiceHead;
	}
	public String getInvoiceContent() {
		return invoiceContent;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
	public List<OrderDetailInfo> getOrderDetail() {
		return orderDetail;
	}
	public void setOrderDetail(List<OrderDetailInfo> orderDetail) {
		this.orderDetail = orderDetail;
	}
}
