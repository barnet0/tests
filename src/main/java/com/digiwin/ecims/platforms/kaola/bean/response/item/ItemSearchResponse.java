package com.digiwin.ecims.platforms.kaola.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_edit_list;
import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

/**
 * 根据状态查询商品信息
 * @author cjp 2017/5/26
 *
 */
public class ItemSearchResponse extends KaolaBaseResponse {

	/*private Integer total_count;
	private List<ItemEdit> item_edit_list;

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	public List<ItemEdit> getItemEditList() {
		return item_edit_list;
	}

	public void setItemEditList(List<ItemEdit> itemEditList) {
		this.item_edit_list = itemEditList;
	}*/

	private List<Item_edit_list> item_edit_list ;

	private int total_count;

	public void setItem_edit_list(List<Item_edit_list> item_edit_list){
	this.item_edit_list = item_edit_list;
	}
	public List<Item_edit_list> getItem_edit_list(){
	return this.item_edit_list;
	}
	public void setTotal_count(int total_count){
	this.total_count = total_count;
	}
	public int getTotal_count(){
	return this.total_count;
	}
}
