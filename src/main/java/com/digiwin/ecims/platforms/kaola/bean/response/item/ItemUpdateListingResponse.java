package com.digiwin.ecims.platforms.kaola.bean.response.item;

import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

public class ItemUpdateListingResponse extends KaolaBaseResponse {
	
	private String listing_time;


	public String getListing_time() {
		return listing_time;
	}

	public void setListing_time(String listing_time) {
		this.listing_time = listing_time;
	}

}
