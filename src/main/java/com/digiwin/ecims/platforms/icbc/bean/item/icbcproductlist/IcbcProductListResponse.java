package com.digiwin.ecims.platforms.icbc.bean.item.icbcproductlist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class IcbcProductListResponse {
	@XmlElement(name = "head")
	private Head head;

	@XmlElement(name = "body")
	private IcbcProductListResponseBody body;

	public IcbcProductListResponse() {

	}

	public IcbcProductListResponse(Head head, IcbcProductListResponseBody body) {
		this.head = head;
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public IcbcProductListResponseBody getBody() {
		return body;
	}

	public void setBody(IcbcProductListResponseBody body) {
		this.body = body;
	}

}
