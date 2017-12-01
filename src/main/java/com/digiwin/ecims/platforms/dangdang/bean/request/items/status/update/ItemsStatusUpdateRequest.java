package com.digiwin.ecims.platforms.dangdang.bean.request.items.status.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "request")
public class ItemsStatusUpdateRequest {
	
	@XmlElement(name = "ProductInfoList")
	private ProductInfoList productInfoList;

	public ProductInfoList getProductInfoList() {
		return productInfoList;
	}

	public void setProductInfoList(ProductInfoList productInfoList) {
		this.productInfoList = productInfoList;
	}

	
		
}