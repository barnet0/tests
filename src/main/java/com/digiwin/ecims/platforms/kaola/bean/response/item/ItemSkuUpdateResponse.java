package com.digiwin.ecims.platforms.kaola.bean.response.item;

import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

/**
 * 
 * @author cjp
 *
 */
public class ItemSkuUpdateResponse extends KaolaBaseResponse {
	private String modify_time;


	public String getModify_Time() {
		return modify_time;
	}

	public void setModify_Time(String modify_time) {
		this.modify_time = modify_time;
	}
}
