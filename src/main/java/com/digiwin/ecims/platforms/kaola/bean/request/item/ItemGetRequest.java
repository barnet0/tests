package com.digiwin.ecims.platforms.kaola.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemGetResponse;

/**
 * 根据商品id获取单个商品的详细信息
 * @author cjp 2017/5/26
 *
 */
public class ItemGetRequest extends KaolaBaseRequest<ItemGetResponse> {

	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public Map<String, String> getApiParams() {
		 Map<String, String> apiParams = new HashMap<String, String>();
			
		 apiParams.put("key", getKey());
		 
		 return apiParams;
	}

	@Override
	public String getMType() {
		
		return "kaola.item.get";
	}

	@Override
	public Class<ItemGetResponse> getResponseClass() {
		return ItemGetResponse.class;
	}

}
