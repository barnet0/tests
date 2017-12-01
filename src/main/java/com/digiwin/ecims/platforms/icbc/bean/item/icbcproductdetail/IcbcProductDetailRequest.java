package com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class IcbcProductDetailRequest implements BasicRequest{
	
	@XmlTransient
	private final String method = "icbcb2c.product.detail";

	@XmlElement(name = "product_ids")
	private String productIds;

	public IcbcProductDetailRequest(String productIds) {
		this.productIds = productIds;
	}

	public IcbcProductDetailRequest() {

	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	@Override
	public String getMethod() {
		return method;
	}

}
