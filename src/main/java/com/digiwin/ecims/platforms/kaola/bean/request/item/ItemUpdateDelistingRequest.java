package com.digiwin.ecims.platforms.kaola.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemUpdateDelistingResponse;

public class ItemUpdateDelistingRequest extends KaolaBaseRequest<ItemUpdateDelistingResponse> {
    private String key_list;
    
	public String getKey_list() {
		return key_list;
	}

	public void setKey_list(String key_list) {
		this.key_list = key_list;
	}

	@Override
	public Map<String, String> getApiParams() {
		 Map<String, String> apiParams = new HashMap<String, String>();
		    apiParams.put("key_list", getKey_list());
		    return apiParams;
	}

	@Override
	public String getMType() {
		return "kaola.item.update.delisting";
	}

	@Override
	public Class<ItemUpdateDelistingResponse> getResponseClass() {
		return ItemUpdateDelistingResponse.class;
	}


}
