package com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class IcbcRefundQueryResponse {

	@XmlElement(name = "head")
	private Head head;

	@XmlElement(name = "body")
	private IcbcRefundQueryResponseBody body;

	public IcbcRefundQueryResponse() {

	}

	public IcbcRefundQueryResponse(Head head, IcbcRefundQueryResponseBody body) {
		this.head = head;
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public IcbcRefundQueryResponseBody getBody() {
		return body;
	}

	public void setBody(IcbcRefundQueryResponseBody body) {
		this.body = body;
	}
}
