package com.digiwin.ecims.platforms.icbc.bean.base;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RefundList {

	@XmlElement(name = "refund")
	private List<Refund> refundList;

	public RefundList() {

	}

	public RefundList(List<Refund> refundList) {
		this.refundList = refundList;
	}

	public List<Refund> getRefundList() {
		return refundList;
	}

	public void setRefundList(List<Refund> refundList) {
		this.refundList = refundList;
	}

}
