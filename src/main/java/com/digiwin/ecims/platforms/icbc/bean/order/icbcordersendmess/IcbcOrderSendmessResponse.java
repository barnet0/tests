package com.digiwin.ecims.platforms.icbc.bean.order.icbcordersendmess;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class IcbcOrderSendmessResponse {
	@XmlElement(name = "head")
	private Head head;

	public IcbcOrderSendmessResponse() {

	}

	public IcbcOrderSendmessResponse(Head head) {
		this.head = head;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

}
