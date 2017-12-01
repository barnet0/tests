package com.digiwin.ecims.platforms.dangdang.bean.response.shop.carriagetype.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class ShopCarriageTypeGetResponse {
	
	@XmlElement(name = "functionID")
	private String functionID;

	@XmlElement(name = "time")
	private String time;

	@XmlElement(name = "result")
	private String result;

	@XmlElement(name = "resultCode")
	private String resultCode;

	@XmlElement(name = "resultMessage")
	private String resultMessage;

	@XmlElement(name = "shopId")
	private String shopId;

	@XmlElement(name = "storeCarriageTypeDefault")
	private StoreCarriageTypeDefault storeCarriageTypeDefault;

	@XmlElement(name = "storeCarriageTypeList")
	private StoreCarriageTypeList storeCarriageTypeList;

	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	public String getFunctionID() {
		return this.functionID;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime() {
		return this.time;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return this.result;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultMessage() {
		return this.resultMessage;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopId() {
		return this.shopId;
	}

	public void setStoreCarriageTypeDefault(StoreCarriageTypeDefault storeCarriageTypeDefault) {
		this.storeCarriageTypeDefault = storeCarriageTypeDefault;
	}

	public StoreCarriageTypeDefault getStoreCarriageTypeDefault() {
		return this.storeCarriageTypeDefault;
	}

	public void setStoreCarriageTypeList(StoreCarriageTypeList storeCarriageTypeList) {
		this.storeCarriageTypeList = storeCarriageTypeList;
	}

	public StoreCarriageTypeList getStoreCarriageTypeList() {
		return this.storeCarriageTypeList;
	}
}
