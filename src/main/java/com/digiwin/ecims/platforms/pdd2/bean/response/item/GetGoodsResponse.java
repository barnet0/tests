package com.digiwin.ecims.platforms.pdd2.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;
import com.digiwin.ecims.platforms.pdd2.bean.domain.item.Item;

public class GetGoodsResponse extends Pdd2BaseResponse {

	private Integer total_count;

	private List<Item> goods_list;

	/**
	 * @return the total_count
	 */
	public Integer getTotal_count() {
		return total_count;
	}

	/**
	 * @param total_count
	 *            the total_count to set
	 */
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	/**
	 * @return the goods_list
	 */
	public List<Item> getGoods_list() {
		return goods_list;
	}

	/**
	 * @param goods_list
	 *            the goods_list to set
	 */
	public void setGoods_list(List<Item> goods_list) {
		this.goods_list = goods_list;
	}

}
