package com.digiwin.ecims.platforms.dangdang.bean.response.order.receipt.details.list;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CourierReceiptDetail {
	
	@XmlElement(name = "receiptTitle")
	private String receiptTitle;

	@XmlElement(name = "shopWarehouse")
	private String shopWarehouse;

	@XmlElement(name = "sendGoodsWarehouse")
	private String sendGoodsWarehouse;

	@XmlElement(name = "rejectWarehouse")
	private String rejectWarehouse;
	
	@XmlElement(name = "expressCompany")
	private String expressCompany;

	@XmlElement(name = "orderID")
	private String orderID;

	@XmlElement(name = "orderCreateTime")
	private String orderCreateTime;

	@XmlElement(name = "consigneeName")
	private String consigneeName;

	@XmlElement(name = "consigneeAddr")
	private String consigneeAddr;

	@XmlElement(name = "consigneeAddr_State")
	private String consigneeAddr_State;

	@XmlElement(name = "consigneeAddr_Province")
	private String consigneeAddr_Province;

	@XmlElement(name = "consigneeAddr_City")
	private String consigneeAddr_City;

	@XmlElement(name = "consigneeAddr_Area")
	private String consigneeAddr_Area;

	@XmlElement(name = "consigneePostcode")
	private String consigneePostcode;

	@XmlElement(name = "consigneeTel")
	private String consigneeTel;

	@XmlElement(name = "consigneeMobileTel")
	private String consigneeMobileTel;

	@XmlElement(name = "shopName")
	private String shopName;

	@XmlElement(name = "shopID")
	private String shopID;

	@XmlElement(name = "consignerName")
	private String consignerName;

	@XmlElement(name = "consignerTel")
	private String consignerTel;
	
	@XmlElement(name = "consignerAddr")
	private String consignerAddr;

	@XmlElement(name = "totalBarginPrice")
	private String totalBarginPrice;

	@XmlElement(name = "sendGoodsTime")
	private String sendGoodsTime;

	@XmlElement(name = "orderOperCode")
	private String orderOperCode;

	@XmlElement(name = "payType")
	private String payType;

	@XmlElement(name = "operCode")
	private String operCode;
	
	@XmlElement(name = "operation")
	private String operation;

	/**
	 * @return the receiptTitle
	 */
	public String getReceiptTitle() {
		return receiptTitle;
	}

	/**
	 * @param receiptTitle the receiptTitle to set
	 */
	public void setReceiptTitle(String receiptTitle) {
		this.receiptTitle = receiptTitle;
	}

	/**
	 * @return the shopWarehouse
	 */
	public String getShopWarehouse() {
		return shopWarehouse;
	}

	/**
	 * @param shopWarehouse the shopWarehouse to set
	 */
	public void setShopWarehouse(String shopWarehouse) {
		this.shopWarehouse = shopWarehouse;
	}

	/**
	 * @return the sendGoodsWarehouse
	 */
	public String getSendGoodsWarehouse() {
		return sendGoodsWarehouse;
	}

	/**
	 * @param sendGoodsWarehouse the sendGoodsWarehouse to set
	 */
	public void setSendGoodsWarehouse(String sendGoodsWarehouse) {
		this.sendGoodsWarehouse = sendGoodsWarehouse;
	}

	/**
	 * @return the rejectWarehouse
	 */
	public String getRejectWarehouse() {
		return rejectWarehouse;
	}

	/**
	 * @param rejectWarehouse the rejectWarehouse to set
	 */
	public void setRejectWarehouse(String rejectWarehouse) {
		this.rejectWarehouse = rejectWarehouse;
	}

	/**
	 * @return the expressCompany
	 */
	public String getExpressCompany() {
		return expressCompany;
	}

	/**
	 * @param expressCompany the expressCompany to set
	 */
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	/**
	 * @return the orderID
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * @param orderID the orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * @return the orderCreateTime
	 */
	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	/**
	 * @param orderCreateTime the orderCreateTime to set
	 */
	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	/**
	 * @return the consigneeName
	 */
	public String getConsigneeName() {
		return consigneeName;
	}

	/**
	 * @param consigneeName the consigneeName to set
	 */
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	/**
	 * @return the consigneeAddr
	 */
	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	/**
	 * @param consigneeAddr the consigneeAddr to set
	 */
	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	/**
	 * @return the consigneeAddr_State
	 */
	public String getConsigneeAddr_State() {
		return consigneeAddr_State;
	}

	/**
	 * @param consigneeAddr_State the consigneeAddr_State to set
	 */
	public void setConsigneeAddr_State(String consigneeAddr_State) {
		this.consigneeAddr_State = consigneeAddr_State;
	}

	/**
	 * @return the consigneeAddr_Province
	 */
	public String getConsigneeAddr_Province() {
		return consigneeAddr_Province;
	}

	/**
	 * @param consigneeAddr_Province the consigneeAddr_Province to set
	 */
	public void setConsigneeAddr_Province(String consigneeAddr_Province) {
		this.consigneeAddr_Province = consigneeAddr_Province;
	}

	/**
	 * @return the consigneeAddr_City
	 */
	public String getConsigneeAddr_City() {
		return consigneeAddr_City;
	}

	/**
	 * @param consigneeAddr_City the consigneeAddr_City to set
	 */
	public void setConsigneeAddr_City(String consigneeAddr_City) {
		this.consigneeAddr_City = consigneeAddr_City;
	}

	/**
	 * @return the consigneeAddr_Area
	 */
	public String getConsigneeAddr_Area() {
		return consigneeAddr_Area;
	}

	/**
	 * @param consigneeAddr_Area the consigneeAddr_Area to set
	 */
	public void setConsigneeAddr_Area(String consigneeAddr_Area) {
		this.consigneeAddr_Area = consigneeAddr_Area;
	}

	/**
	 * @return the consigneePostcode
	 */
	public String getConsigneePostcode() {
		return consigneePostcode;
	}

	/**
	 * @param consigneePostcode the consigneePostcode to set
	 */
	public void setConsigneePostcode(String consigneePostcode) {
		this.consigneePostcode = consigneePostcode;
	}

	/**
	 * @return the consigneeTel
	 */
	public String getConsigneeTel() {
		return consigneeTel;
	}

	/**
	 * @param consigneeTel the consigneeTel to set
	 */
	public void setConsigneeTel(String consigneeTel) {
		this.consigneeTel = consigneeTel;
	}

	/**
	 * @return the consigneeMobileTel
	 */
	public String getConsigneeMobileTel() {
		return consigneeMobileTel;
	}

	/**
	 * @param consigneeMobileTel the consigneeMobileTel to set
	 */
	public void setConsigneeMobileTel(String consigneeMobileTel) {
		this.consigneeMobileTel = consigneeMobileTel;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * @return the shopID
	 */
	public String getShopID() {
		return shopID;
	}

	/**
	 * @param shopID the shopID to set
	 */
	public void setShopID(String shopID) {
		this.shopID = shopID;
	}

	/**
	 * @return the consignerName
	 */
	public String getConsignerName() {
		return consignerName;
	}

	/**
	 * @param consignerName the consignerName to set
	 */
	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
	}

	/**
	 * @return the consignerTel
	 */
	public String getConsignerTel() {
		return consignerTel;
	}

	/**
	 * @param consignerTel the consignerTel to set
	 */
	public void setConsignerTel(String consignerTel) {
		this.consignerTel = consignerTel;
	}

	/**
	 * @return the consignerAddr
	 */
	public String getConsignerAddr() {
		return consignerAddr;
	}

	/**
	 * @param consignerAddr the consignerAddr to set
	 */
	public void setConsignerAddr(String consignerAddr) {
		this.consignerAddr = consignerAddr;
	}

	/**
	 * @return the totalBarginPrice
	 */
	public String getTotalBarginPrice() {
		return totalBarginPrice;
	}

	/**
	 * @param totalBarginPrice the totalBarginPrice to set
	 */
	public void setTotalBarginPrice(String totalBarginPrice) {
		this.totalBarginPrice = totalBarginPrice;
	}

	/**
	 * @return the sendGoodsTime
	 */
	public String getSendGoodsTime() {
		return sendGoodsTime;
	}

	/**
	 * @param sendGoodsTime the sendGoodsTime to set
	 */
	public void setSendGoodsTime(String sendGoodsTime) {
		this.sendGoodsTime = sendGoodsTime;
	}

	/**
	 * @return the orderOperCode
	 */
	public String getOrderOperCode() {
		return orderOperCode;
	}

	/**
	 * @param orderOperCode the orderOperCode to set
	 */
	public void setOrderOperCode(String orderOperCode) {
		this.orderOperCode = orderOperCode;
	}

	/**
	 * @return the payType
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(String payType) {
		this.payType = payType;
	}

	/**
	 * @return the operCode
	 */
	public String getOperCode() {
		return operCode;
	}

	/**
	 * @param operCode the operCode to set
	 */
	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
}
