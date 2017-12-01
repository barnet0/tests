package com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class IcbcOrderDetailResponse {

	@XmlElement(name = "head")
	private Head head;

	@XmlElement(name = "body")
	private IcbcOrderDetailResponseBody body;

	public IcbcOrderDetailResponse() {

	}

	public IcbcOrderDetailResponse(Head head, IcbcOrderDetailResponseBody body) {
		this.head = head;
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public IcbcOrderDetailResponseBody getBody() {
		return body;
	}

	public void setBody(IcbcOrderDetailResponseBody body) {
		this.body = body;
	}

}
