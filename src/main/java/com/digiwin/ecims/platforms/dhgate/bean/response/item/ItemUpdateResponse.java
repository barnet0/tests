package com.digiwin.ecims.platforms.dhgate.bean.response.item;

import com.dhgate.open.client.BizStatusResponse;

public class ItemUpdateResponse extends BizStatusResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1229152710367468443L;

	private String itemCode;
	
	private String itemId;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public ItemUpdateResponse() {
		super();
	}
	
	
}
