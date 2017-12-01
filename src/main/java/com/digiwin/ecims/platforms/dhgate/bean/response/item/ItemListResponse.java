package com.digiwin.ecims.platforms.dhgate.bean.response.item;

import java.util.List;

import com.dhgate.open.client.BizStatusResponse;

import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemList;

public class ItemListResponse extends BizStatusResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 610215200008926824L;

	private String pageTotal;
	
	private String total;
	
	private List<ItemList> itemList;

	public String getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(String pageTotal) {
		this.pageTotal = pageTotal;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public List<ItemList> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemList> itemList) {
		this.itemList = itemList;
	}

	public ItemListResponse() {
		super();
	}
	
	
}
