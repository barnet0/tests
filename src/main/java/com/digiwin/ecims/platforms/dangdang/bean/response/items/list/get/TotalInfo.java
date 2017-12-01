package com.digiwin.ecims.platforms.dangdang.bean.response.items.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TotalInfo {
	
	@XmlElement(name = "itemsCount")
	private String itemsCount;
	
	@XmlElement(name = "pageSize")
	private String pageSize;
	
	@XmlElement(name = "pageTotal")
	private String pageTotal;
	
	@XmlElement(name = "currentPage")
	private String currentPage;
	
	public TotalInfo() {
		
	}

	public String getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(String itemsCount) {
		this.itemsCount = itemsCount;
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