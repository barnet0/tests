package com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SendGoodsInfo {
	
	@XmlElement(name = "dangdangAccountID")
	private String dangdangAccountID;
	
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
	
	@XmlElement(name = "consigneeAddr_StateCode")
	private String consigneeAddr_StateCode;
	
	@XmlElement(name = "consigneeAddr_ProvinceCode")
	private String consigneeAddr_ProvinceCode;
	
	@XmlElement(name = "consigneeAddr_CityCode")
	private String consigneeAddr_CityCode;
	
	@XmlElement(name = "consigneeAddr_AreaCode")
	private String consigneeAddr_AreaCode;
	
	@XmlElement(name = "consigneePostcode")
	private String consigneePostcode;
	
	@XmlElement(name = "consigneeTel")
	private String consigneeTel;
	
	@XmlElement(name = "consigneeMobileTel")
	private String consigneeMobileTel;
	
	@XmlElement(name = "sendGoodsMode")
	private String sendGoodsMode;
	
	@XmlElement(name = "sendCompany")
	private String sendCompany;
	
	@XmlElement(name = "sendOrderID")
	private String sendOrderID;
	
	@XmlElement(name = "DangdangWarehouseAddr")
	private String DangdangWarehouseAddr;
	
	public SendGoodsInfo() {
		
	}

	public String getDangdangAccountID() {
		return dangdangAccountID;
	}

	public void setDangdangAccountID(String dangdangAccountID) {
		this.dangdangAccountID = dangdangAccountID;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeAddr() {
		return consigneeAddr;
	}

	public void setConsigneeAddr(String consigneeAddr) {
		this.consigneeAddr = consigneeAddr;
	}

	public String getConsigneeAddr_State() {
		return consigneeAddr_State;
	}

	public void setConsigneeAddr_State(String consigneeAddr_State) {
		this.consigneeAddr_State = consigneeAddr_State;
	}

	public String getConsigneeAddr_Province() {
		return consigneeAddr_Province;
	}

	public void setConsigneeAddr_Province(String consigneeAddr_Province) {
		this.consigneeAddr_Province = consigneeAddr_Province;
	}

	public String getConsigneeAddr_City() {
		return consigneeAddr_City;
	}

	public void setConsigneeAddr_City(String consigneeAddr_City) {
		this.consigneeAddr_City = consigneeAddr_City;
	}

	public String getConsigneeAddr_Area() {
		return consigneeAddr_Area;
	}

	public void setConsigneeAddr_Area(String consigneeAddr_Area) {
		this.consigneeAddr_Area = consigneeAddr_Area;
	}

	public String getConsigneeAddr_StateCode() {
		return consigneeAddr_StateCode;
	}

	public void setConsigneeAddr_StateCode(String consigneeAddr_StateCode) {
		this.consigneeAddr_StateCode = consigneeAddr_StateCode;
	}

	public String getConsigneeAddr_ProvinceCode() {
		return consigneeAddr_ProvinceCode;
	}

	public void setConsigneeAddr_ProvinceCode(String consigneeAddr_ProvinceCode) {
		this.consigneeAddr_ProvinceCode = consigneeAddr_ProvinceCode;
	}

	public String getConsigneeAddr_CityCode() {
		return consigneeAddr_CityCode;
	}

	public void setConsigneeAddr_CityCode(String consigneeAddr_CityCode) {
		this.consigneeAddr_CityCode = consigneeAddr_CityCode;
	}

	public String getConsigneeAddr_AreaCode() {
		return consigneeAddr_AreaCode;
	}

	public void setConsigneeAddr_AreaCode(String consigneeAddr_AreaCode) {
		this.consigneeAddr_AreaCode = consigneeAddr_AreaCode;
	}

	public String getConsigneePostcode() {
		return consigneePostcode;
	}

	public void setConsigneePostcode(String consigneePostcode) {
		this.consigneePostcode = consigneePostcode;
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

	public String getSendGoodsMode() {
		return sendGoodsMode;
	}

	public void setSendGoodsMode(String sendGoodsMode) {
		this.sendGoodsMode = sendGoodsMode;
	}

	public String getSendCompany() {
		return sendCompany;
	}

	public void setSendCompany(String sendCompany) {
		this.sendCompany = sendCompany;
	}

	public String getSendOrderID() {
		return sendOrderID;
	}

	public void setSendOrderID(String sendOrderID) {
		this.sendOrderID = sendOrderID;
	}

	public String getDangdangWarehouseAddr() {
		return DangdangWarehouseAddr;
	}

	public void setDangdangWarehouseAddr(String dangdangWarehouseAddr) {
		DangdangWarehouseAddr = dangdangWarehouseAddr;
	}

		
}