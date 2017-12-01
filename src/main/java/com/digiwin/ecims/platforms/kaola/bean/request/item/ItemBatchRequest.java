package com.digiwin.ecims.platforms.kaola.bean.request.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemBatchResponse;

/**
 * 根据商品id list获取批量商品的详细信息
 * @author cjp 2017/5/26
 *
 */
public class ItemBatchRequest extends KaolaBaseRequest<ItemBatchResponse>  {

	private List<String>key_list=new ArrayList<String>();
	
	public List<String> getKey_list() {
		return key_list;
	}

	public void setKey_list(List<String> key_list) {
		this.key_list = key_list;
	}

	@Override
	public Map<String, String> getApiParams() {
		 Map<String, String> apiParams = new HashMap<String, String>();
		
		 apiParams.put("key_list", getKey_list().toString());
		
		return apiParams;
	}

	@Override
	public String getMType() {
		return "kaola.item.batch.get";
	}

	@Override
	public Class<ItemBatchResponse> getResponseClass() {
	
		return ItemBatchResponse.class;
	}

}
