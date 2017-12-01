package com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class IcbcRefundQueryRequest implements BasicRequest{

	@XmlTransient
	private final String method = "icbcb2c.refund.query";
	
	@XmlElement(name = "create_start_time")
	private String createStartTime;

	@XmlElement(name = "create_end_time")
	private String createEndTime;

	@XmlElement(name = "refund_status")
	private String refundStatus;

	@XmlElement(name = "order_id")
	private String orderId;

	public IcbcRefundQueryRequest() {

	}

	public IcbcRefundQueryRequest(String createStartTime, String createEndTime,
			String refundStatus, String orderId) {
		this.createStartTime = createStartTime;
		this.createEndTime = createEndTime;
		this.refundStatus = refundStatus;
		this.orderId = orderId;
	}

	public IcbcRefundQueryRequest(String orderIds) {
		this.orderId = orderIds;
	}

	public IcbcRefundQueryRequest(String s, String e) {
		this.createStartTime = s;
		this.createEndTime = e;
	}

	public String getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(String createStartTime) {
		this.createStartTime = createStartTime;
	}

	public String getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(String createEndTime) {
		this.createEndTime = createEndTime;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
