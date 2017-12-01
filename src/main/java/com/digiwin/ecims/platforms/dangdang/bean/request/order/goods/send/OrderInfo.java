package com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInfo {
	
	@XmlElement(name = "orderID")
	private String orderID;
	
	@XmlElement(name = "logisticsName")
	private String logisticsName;
	
	@XmlElement(name = "logisticsTel")
	private String logisticsTel;
	
	@XmlElement(name = "logisticsOrderID")
	private String logisticsOrderID;
	
	@XmlElement(name = "SendGoodsList")
	private SendGoodsList sendGoodsList;
	
	public OrderInfo() {
		
	}
	
	public OrderInfo(String orderID, String logisticsName, String logisticsTel, String logisticsOrderID, SendGoodsList sendGoodsList) {
		this.orderID = orderID;
		this.logisticsName = logisticsName;
		this.logisticsTel = logisticsTel;
		this.logisticsOrderID = logisticsOrderID;
		this.sendGoodsList = sendGoodsList;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getLogisticsTel() {
		return logisticsTel;
	}

	public void setLogisticsTel(String logisticsTel) {
		this.logisticsTel = logisticsTel;
	}

	public String getLogisticsOrderID() {
		return logisticsOrderID;
	}

	public void setLogisticsOrderID(String logisticsOrderID) {
		this.logisticsOrderID = logisticsOrderID;
	}

	public SendGoodsList getSendGoodsList() {
		return sendGoodsList;
	}

	public void setSendGoodsList(SendGoodsList sendGoodsList) {
		this.sendGoodsList = sendGoodsList;
	}

		
}