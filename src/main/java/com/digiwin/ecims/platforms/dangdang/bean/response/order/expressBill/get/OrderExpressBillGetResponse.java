package com.digiwin.ecims.platforms.dangdang.bean.response.order.expressBill.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class OrderExpressBillGetResponse {
	
	@XmlElement(name = "functionID")
	private String functionID;
	
	@XmlElement(name = "time")
	private String time;
	
	@XmlElement(name = "orderCourierReceiptDetails")
	private OrderCourierReceiptDetails orderCourierReceiptDetails;

	/**
	 * @return the functionID
	 */
	public String getFunctionID() {
		return functionID;
	}

	/**
	 * @param functionID the functionID to set
	 */
	public void setFunctionID(String functionID) {
		this.functionID = functionID;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the orderCourierReceiptDetails
	 */
	public OrderCourierReceiptDetails getOrderCourierReceiptDetails() {
		return orderCourierReceiptDetails;
	}

	/**
	 * @param orderCourierReceiptDetails the orderCourierReceiptDetails to set
	 */
	public void setOrderCourierReceiptDetails(
			OrderCourierReceiptDetails orderCourierReceiptDetails) {
		this.orderCourierReceiptDetails = orderCourierReceiptDetails;
	}
	
	
}
