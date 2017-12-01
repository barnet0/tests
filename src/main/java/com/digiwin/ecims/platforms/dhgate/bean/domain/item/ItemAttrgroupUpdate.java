package com.digiwin.ecims.platforms.dhgate.bean.domain.item;

import java.util.List;

/**
 * 兼容性属性列表
 * @author 维杰
 *
 */
public class ItemAttrgroupUpdate {

//	必须	产品兼容性属性值列表	产品兼容性属性值列表
	private List<ItemAttrgroupDetail> itemAttrgroupDetailList;

	public List<ItemAttrgroupDetail> getItemAttrgroupDetailList() {
		return itemAttrgroupDetailList;
	}

	public void setItemAttrgroupDetailList(List<ItemAttrgroupDetail> itemAttrgroupDetailList) {
		this.itemAttrgroupDetailList = itemAttrgroupDetailList;
	}

	public ItemAttrgroupUpdate() {
		super();
	}
	
	
}
