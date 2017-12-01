package com.digiwin.ecims.platforms.kaola.bean.response.item;

import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

public class ItemUpdateDelistingResponse extends KaolaBaseResponse {
	
	private String delisting_time;


	public String getDeListing_time() {
		return delisting_time;
	}

	public void setDeListing_time(String delisting_time) {
		this.delisting_time = delisting_time;
	}
}
