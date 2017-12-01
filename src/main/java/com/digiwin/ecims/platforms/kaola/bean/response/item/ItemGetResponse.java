package com.digiwin.ecims.platforms.kaola.bean.response.item;


import java.util.List;

import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_category_list;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_edit_list;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_image_list;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_postage;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_property_list;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Item_text_property_list;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Raw_item_edit;
import com.digiwin.ecims.platforms.kaola.bean.domain.item.Sku_list;
import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

/**
 * 单个商品的详细信息
 * @author cjp 2017/5/26 
 *
 */
public class ItemGetResponse extends KaolaBaseResponse {
	private String hide_add_to_cart;

	private List<Item_category_list> item_category_list ;

	private List<Item_image_list> item_image_list ;

	private Item_postage item_postage;

	private List<Item_property_list> item_property_list ;

	private List<Item_text_property_list> item_text_property_list ;

	private String key;

	private Raw_item_edit raw_item_edit;

	private List<Sku_list> sku_list ;

	public void setHide_add_to_cart(String hide_add_to_cart){
	this.hide_add_to_cart = hide_add_to_cart;
	}
	public String getHide_add_to_cart(){
	return this.hide_add_to_cart;
	}
	public void setItem_category_list(List<Item_category_list> item_category_list){
	this.item_category_list = item_category_list;
	}
	public List<Item_category_list> getItem_category_list(){
	return this.item_category_list;
	}
	public void setItem_image_list(List<Item_image_list> item_image_list){
	this.item_image_list = item_image_list;
	}
	public List<Item_image_list> getItem_image_list(){
	return this.item_image_list;
	}
	public void setItem_postage(Item_postage item_postage){
	this.item_postage = item_postage;
	}
	public Item_postage getItem_postage(){
	return this.item_postage;
	}
	public void setItem_property_list(List<Item_property_list> item_property_list){
	this.item_property_list = item_property_list;
	}
	public List<Item_property_list> getItem_property_list(){
	return this.item_property_list;
	}
	public void setItem_text_property_list(List<Item_text_property_list> item_text_property_list){
	this.item_text_property_list = item_text_property_list;
	}
	public List<Item_text_property_list> getItem_text_property_list(){
	return this.item_text_property_list;
	}
	public void setKey(String key){
	this.key = key;
	}
	public String getKey(){
	return this.key;
	}
	public void setRaw_item_edit(Raw_item_edit raw_item_edit){
	this.raw_item_edit = raw_item_edit;
	}
	public Raw_item_edit getRaw_item_edit(){
	return this.raw_item_edit;
	}
	public void setSku_list(List<Sku_list> sku_list){
	this.sku_list = sku_list;
	}
	public List<Sku_list> getSku_list(){
	return this.sku_list;
	}	

}
