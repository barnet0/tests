package com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class IcbcOrderListResponse {
	@XmlElement(name = "head")
	private Head head;

	@XmlElement(name = "body")
	private IcbcOrderListResponseBody body;

	public IcbcOrderListResponse() {

	}

	public IcbcOrderListResponse(Head head, IcbcOrderListResponseBody body) {
		this.head = head;
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public IcbcOrderListResponseBody getBody() {
		return body;
	}

	public void setBody(IcbcOrderListResponseBody body) {
		this.body = body;
	}

}
