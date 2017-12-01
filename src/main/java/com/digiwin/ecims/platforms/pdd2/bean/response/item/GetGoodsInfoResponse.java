package com.digiwin.ecims.platforms.pdd2.bean.response.item;

import com.digiwin.ecims.platforms.pdd2.bean.domain.item.GoodsInfo;
import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;

public class GetGoodsInfoResponse extends Pdd2BaseResponse {
	private GoodsInfo goods_info;

	/**
	 * @return the goods_info
	 */
	public GoodsInfo getGoods_info() {
		return goods_info;
	}

	/**
	 * @param goods_info
	 *            the goods_info to set
	 */
	public void setGoods_info(GoodsInfo goods_info) {
		this.goods_info = goods_info;
	}

}
