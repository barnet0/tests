package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.dangdang.bean.response.Error;
import com.digiwin.ecims.platforms.dangdang.bean.response.item.stock.update.Result;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class OrderDetailsGetResponse {
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "orderID")
	private String orderID;
	
	@XmlElement(name = "orderState")
	private String orderState;
	
	@XmlElement(name = "message")
	private String message;
	
	@XmlElement(name = "remark")
	private String remark;
	
	@XmlElement(name = "label")
	private String label;
	
	@XmlElement(name = "parentOrderID")
	private String parentOrderID;
	
	@XmlElement(name = "outerOrderID")
	private String outerOrderID;
	
	@XmlElement(name = "lastModifyTime")
	private String lastModifyTime;
	
	@XmlElement(name = "giftCardCharge")
	private String giftCardCharge;
	
	@XmlElement(name = "sendGiftCardCharge")
	private String sendGiftCardCharge;
	
	@XmlElement(name = "lackGiftCardCharge")
	private String lackGiftCardCharge;
	
	@XmlElement(name = "isCourierReceiptDetail")
	private String isCourierReceiptDetail;
	
	@XmlElement(name = "paymentDate")
	private String paymentDate;
	
	@XmlElement(name = "orderMode")
	private String orderMode;
	
	@XmlElement(name = "sendDate")
	private String sendDate;
	
	@XmlElement(name = "isPresale")
	private String isPresale;
	
	@XmlElement(name = "OrderOperateList")
	private OrderOperateList orderOperateList;
	
	@XmlElement(name = "buyerInfo")
	private BuyerInfo buyerInfo;
	
	@XmlElement(name = "sendGoodsInfo")
	private SendGoodsInfo sendGoodsInfo;
	
	@XmlElement(name = "ItemsList")
	private ItemsList itemsList;
	
	@XmlElement(name = "PromoList")
	private PromoList promoList;
	
	@XmlElement(name = "receiptInfo")
	private ReceiptInfo receiptInfo;
	
	@XmlElement(name = "originalOrderId")
	private String originalOrderId;
	
	@XmlElement(name = "einvoiceAddress")
	private String einvoiceAddress;
	
	@XmlElement(name = "warehouseID")
	private String warehouseID;
	

	@XmlElement(name = "code")
	private String code;
	
	@XmlElement(name = "msg")
	private String msg;

	@XmlElement(name = "Error")
	private Error error;
	
	@XmlElement(name = "Result")
	private Result result;
	
	
	public String getFunctionID() {
		return functionID;
	}

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getParentOrderID() {
		return parentOrderID;
	}

	public void setParentOrderID(String parentOrderID) {
		this.parentOrderID = parentOrderID;
	}

	public String getOuterOrderID() {
		return outerOrderID;
	}

	public void setOuterOrderID(String outerOrderID) {
		this.outerOrderID = outerOrderID;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getGiftCardCharge() {
		return giftCardCharge;
	}

	public void setGiftCardCharge(String giftCardCharge) {
		this.giftCardCharge = giftCardCharge;
	}

	public String getSendGiftCardCharge() {
		return sendGiftCardCharge;
	}

	public void setSendGiftCardCharge(String sendGiftCardCharge) {
		this.sendGiftCardCharge = sendGiftCardCharge;
	}

	public String getLackGiftCardCharge() {
		return lackGiftCardCharge;
	}

	public void setLackGiftCardCharge(String lackGiftCardCharge) {
		this.lackGiftCardCharge = lackGiftCardCharge;
	}

	public String getIsCourierReceiptDetail() {
		return isCourierReceiptDetail;
	}

	public void setIsCourierReceiptDetail(String isCourierReceiptDetail) {
		this.isCourierReceiptDetail = isCourierReceiptDetail;
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

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getIsPresale() {
		return isPresale;
	}

	public void setIsPresale(String isPresale) {
		this.isPresale = isPresale;
	}

	public OrderOperateList getOrderOperateList() {
		return orderOperateList;
	}

	public void setOrderOperateList(OrderOperateList orderOperateList) {
		this.orderOperateList = orderOperateList;
	}

	public BuyerInfo getBuyerInfo() {
		return buyerInfo;
	}

	public void setBuyerInfo(BuyerInfo buyerInfo) {
		this.buyerInfo = buyerInfo;
	}

	public SendGoodsInfo getSendGoodsInfo() {
		return sendGoodsInfo;
	}

	public void setSendGoodsInfo(SendGoodsInfo sendGoodsInfo) {
		this.sendGoodsInfo = sendGoodsInfo;
	}

	public ItemsList getItemsList() {
		return itemsList;
	}

	public void setItemsList(ItemsList itemsList) {
		this.itemsList = itemsList;
	}

	public PromoList getPromoList() {
		return promoList;
	}

	public void setPromoList(PromoList promoList) {
		this.promoList = promoList;
	}

	public ReceiptInfo getReceiptInfo() {
		return receiptInfo;
	}

	public void setReceiptInfo(ReceiptInfo receiptInfo) {
		this.receiptInfo = receiptInfo;
	}

	public String getOriginalOrderId() {
		return originalOrderId;
	}

	public void setOriginalOrderId(String originalOrderId) {
		this.originalOrderId = originalOrderId;
	}

	public String getEinvoiceAddress() {
		return einvoiceAddress;
	}

	public void setEinvoiceAddress(String einvoiceAddress) {
		this.einvoiceAddress = einvoiceAddress;
	}

	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
		
}