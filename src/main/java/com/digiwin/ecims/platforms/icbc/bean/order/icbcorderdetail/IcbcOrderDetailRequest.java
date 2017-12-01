package com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class IcbcOrderDetailRequest implements BasicRequest{

	@XmlTransient
	private final String method = "icbcb2c.order.detail";
	
	@XmlElement(name = "order_ids")
	private String orderIds;

	public IcbcOrderDetailRequest(String orderIds) {
		this.orderIds = orderIds;
	}

	public IcbcOrderDetailRequest() {

	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
