package com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.digiwin.ecims.platforms.icbc.bean.base.RefundList;

@XmlAccessorType(XmlAccessType.FIELD)
public class IcbcRefundQueryResponseBody {

	@XmlElement(name = "refund_list")
	private RefundList refundList;

	public IcbcRefundQueryResponseBody() {

	}

	public IcbcRefundQueryResponseBody(RefundList refundList) {
		this.refundList = refundList;
	}

	public RefundList getRefundList() {
		return refundList;
	}

	public void setRefundList(RefundList refundList) {
		this.refundList = refundList;
	}

}
