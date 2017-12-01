package com.digiwin.ecims.platforms.pdd2.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetGoodsInfoResponse;

public class GetGoodsInfoRequest extends Pdd2BaseRequest<GetGoodsInfoResponse> {

	private String goods_id;

	/**
	 * @return the goods_id
	 */
	public String getGoods_id() {
		return goods_id;
	}

	/**
	 * @param goods_id
	 *            the goods_id to set
	 */
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

	@Override
	public Map<String, String> getApiParams() {
		Map<String, String> apiParams = new HashMap<String, String>();
		apiParams.put("goods_id", getGoods_id());

		return apiParams;
	}

	@Override
	public String getMType() {

		return "pdd.goods.information.get";
	}

	@Override
	public Class<GetGoodsInfoResponse> getResponseClass() {

		return GetGoodsInfoResponse.class;
	}

}
