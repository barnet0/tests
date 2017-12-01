package com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TotalInfo {
	
	@XmlElement(name = "totalOrderCount")
	private String totalOrderCount;
	
	@XmlElement(name = "needReturnOrderCount")
	private String needReturnOrderCount;
	
	@XmlElement(name = "needExchangeOrderCount")
	private String needExchangeOrderCount;
	
	@XmlElement(name = "pageItemCount")
	private String pageItemCount;
	
	@XmlElement(name = "pageItemMoney")
	private String pageItemMoney;
	
	@XmlElement(name = "currentOrderCount")
	private String currentOrderCount;
	
	@XmlElement(name = "pageTotal")
	private String pageTotal;
	
	@XmlElement(name = "currentPage")
	private String currentPage;
	
	public TotalInfo() {
		
	}

	public String getTotalOrderCount() {
		return totalOrderCount;
	}

	public void setTotalOrderCount(String totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	public String getNeedReturnOrderCount() {
		return needReturnOrderCount;
	}

	public void setNeedReturnOrderCount(String needReturnOrderCount) {
		this.needReturnOrderCount = needReturnOrderCount;
	}

	public String getNeedExchangeOrderCount() {
		return needExchangeOrderCount;
	}

	public void setNeedExchangeOrderCount(String needExchangeOrderCount) {
		this.needExchangeOrderCount = needExchangeOrderCount;
	}

	public String getPageItemCount() {
		return pageItemCount;
	}

	public void setPageItemCount(String pageItemCount) {
		this.pageItemCount = pageItemCount;
	}

	public String getPageItemMoney() {
		return pageItemMoney;
	}

	public void setPageItemMoney(String pageItemMoney) {
		this.pageItemMoney = pageItemMoney;
	}

	public String getCurrentOrderCount() {
		return currentOrderCount;
	}

	public void setCurrentOrderCount(String currentOrderCount) {
		this.currentOrderCount = currentOrderCount;
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