package com.digiwin.ecims.platforms.dangdang.bean.request.items.status.update;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInfoList {
	
	@XmlElement(name = "ProductInfo")
	private List<ProductInfo> productInfo;

	public List<ProductInfo> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}

		
	
		
}