package com.digiwin.ecims.platforms.icbc.bean.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.digiwin.ecims.platforms.icbc.bean.base.Head;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class IcbcStorageModifyResponse {
	@XmlElement(name = "head")
	private Head head;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}
}
