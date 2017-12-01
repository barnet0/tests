package com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class IcbcProductDetailResponse {

	@XmlElement(name = "head")
	private Head head;

	@XmlElement(name = "body")
	private IcbcProductDetailResponseBody body;

	public IcbcProductDetailResponse() {

	}

	public IcbcProductDetailResponse(Head head,
			IcbcProductDetailResponseBody body) {
		this.head = head;
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public IcbcProductDetailResponseBody getBody() {
		return body;
	}

	public void setBody(IcbcProductDetailResponseBody body) {
		this.body = body;
	}

}
