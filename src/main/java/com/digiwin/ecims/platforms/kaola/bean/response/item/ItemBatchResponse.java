package com.digiwin.ecims.platforms.kaola.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.kaola.bean.domain.itembak.ItemEdit;
import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

/**
 * 批量商品的详细信息
 * @author cjp 2017/5/26
 *
 */
public class ItemBatchResponse extends KaolaBaseResponse {
	private List<ItemEdit> item_edit_list;

	public List<ItemEdit> getItem_edit_list() {
		return item_edit_list;
	}

	public void setItem_edit_list(List<ItemEdit> item_edit_list) {
		this.item_edit_list = item_edit_list;
	}

}
