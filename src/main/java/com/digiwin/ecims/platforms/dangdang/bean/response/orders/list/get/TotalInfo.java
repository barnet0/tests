package com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TotalInfo {
	
	@XmlElement(name = "sendGoodsOrderCount")
	private String sendGoodsOrderCount;

	@XmlElement(name = "needExchangeOrderCount")
	private String needExchangeOrderCount;

	@XmlElement(name = "orderCount")
	private String orderCount;

	@XmlElement(name = "totalOrderMoney")
	private String totalOrderMoney;

	@XmlElement(name = "pageSize")
	private String pageSize;

	@XmlElement(name = "pageTotal")
	private String pageTotal;

	@XmlElement(name = "currentPage")
	private String currentPage;
	
	public TotalInfo() {
		
	}

	public String getSendGoodsOrderCount() {
		return sendGoodsOrderCount;
	}

	public void setSendGoodsOrderCount(String sendGoodsOrderCount) {
		this.sendGoodsOrderCount = sendGoodsOrderCount;
	}

	public String getNeedExchangeOrderCount() {
		return needExchangeOrderCount;
	}

	public void setNeedExchangeOrderCount(String needExchangeOrderCount) {
		this.needExchangeOrderCount = needExchangeOrderCount;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getTotalOrderMoney() {
		return totalOrderMoney;
	}

	public void setTotalOrderMoney(String totalOrderMoney) {
		this.totalOrderMoney = totalOrderMoney;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(String pageTotal) {
		this.pageTotal = pageTotal;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	
		
}