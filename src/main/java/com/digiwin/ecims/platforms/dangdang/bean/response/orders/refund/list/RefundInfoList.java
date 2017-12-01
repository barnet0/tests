package com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RefundInfoList {
	
	@XmlElement(name = "refundInfo")
	private List<RefundInfo> refundInfo;
	
	public RefundInfoList() {
		
	}

	public List<RefundInfo> getRefundInfo() {
		return refundInfo;
	}

	public void setRefundInfo(List<RefundInfo> refundInfo) {
		this.refundInfo = refundInfo;
	}

	
		
}