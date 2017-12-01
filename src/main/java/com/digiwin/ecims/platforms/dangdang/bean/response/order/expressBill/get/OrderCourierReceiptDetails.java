package com.digiwin.ecims.platforms.dangdang.bean.response.order.expressBill.get;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderCourierReceiptDetails {
	
	@XmlElement(name = "courierReceiptDetail")
	private List<CourierReceiptDetail> CourierReceiptDetail;

	public void setCourierReceiptDetail(
			List<CourierReceiptDetail> courierReceiptDetail) {
		this.CourierReceiptDetail = courierReceiptDetail;
	}

	public List<CourierReceiptDetail> getCourierReceiptDetail() {
		return this.CourierReceiptDetail;
	}
}
