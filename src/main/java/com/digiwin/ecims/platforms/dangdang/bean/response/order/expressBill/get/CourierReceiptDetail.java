package com.digiwin.ecims.platforms.dangdang.bean.response.order.expressBill.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CourierReceiptDetail {
	
	@XmlElement(name = "receiptTitle")
	private String receiptTitle;

	@XmlElement(name = "orderType")
	private String orderType;

	@XmlElement(name = "shopWarehouse")
	private String shopWarehouse;

	@XmlElement(name = "sendGoodsWarehouse")
	private String sendGoodsWarehouse;

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

	@XmlElement(name = "consigneeAddrState")
	private String consigneeAddrState;

	@XmlElement(name = "consigneeAddrProvince")
	private String consigneeAddrProvince;

	@XmlElement(name = "consigneeAddrCity")
	private String consigneeAddrCity;

	@XmlElement(name = "consigneeAddrArea")
	private String consigneeAddrArea;

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

	@XmlElement(name = "consignerAddr")
	private String consignerAddr;

	@XmlElement(name = "totalBarginPrice")
	private String totalBarginPrice;

	@XmlElement(name = "sendGoodsTime")
	private String sendGoodsTime;
	
	@XmlElement(name = "consignerTel")
	private String consignerTel;

	@XmlElement(name = "productNum")
	private String productNum;

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
	 * @return the orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * @param orderType the orderType to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	 * @return the consigneeAddrState
	 */
	public String getConsigneeAddrState() {
		return consigneeAddrState;
	}

	/**
	 * @param consigneeAddrState the consigneeAddrState to set
	 */
	public void setConsigneeAddrState(String consigneeAddrState) {
		this.consigneeAddrState = consigneeAddrState;
	}

	/**
	 * @return the consigneeAddrProvince
	 */
	public String getConsigneeAddrProvince() {
		return consigneeAddrProvince;
	}

	/**
	 * @param consigneeAddrProvince the consigneeAddrProvince to set
	 */
	public void setConsigneeAddrProvince(String consigneeAddrProvince) {
		this.consigneeAddrProvince = consigneeAddrProvince;
	}

	/**
	 * @return the consigneeAddrCity
	 */
	public String getConsigneeAddrCity() {
		return consigneeAddrCity;
	}

	/**
	 * @param consigneeAddrCity the consigneeAddrCity to set
	 */
	public void setConsigneeAddrCity(String consigneeAddrCity) {
		this.consigneeAddrCity = consigneeAddrCity;
	}

	/**
	 * @return the consigneeAddrArea
	 */
	public String getConsigneeAddrArea() {
		return consigneeAddrArea;
	}

	/**
	 * @param consigneeAddrArea the consigneeAddrArea to set
	 */
	public void setConsigneeAddrArea(String consigneeAddrArea) {
		this.consigneeAddrArea = consigneeAddrArea;
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
	 * @return the productNum
	 */
	public String getProductNum() {
		return productNum;
	}

	/**
	 * @param productNum the productNum to set
	 */
	public void setProductNum(String productNum) {
		this.productNum = productNum;
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
