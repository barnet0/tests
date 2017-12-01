package com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class RefundInfos {
	
	@XmlElement(name = "shopId")
	private String shopId;
	
	@XmlElement(name = "totalSize")
	private String totalSize;
	
	@XmlElement(name = "pageIndex")
	private String pageIndex;

	@XmlElement(name = "pageSize")
	private String pageSize;
	
	@XmlElement(name = "refundInfoList")
	private RefundInfoList refundInfoList;
	
	public RefundInfos() {
		
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public RefundInfoList getRefundInfoList() {
		return refundInfoList;
	}

	public void setRefundInfoList(RefundInfoList refundInfoList) {
		this.refundInfoList = refundInfoList;
	}
		
}