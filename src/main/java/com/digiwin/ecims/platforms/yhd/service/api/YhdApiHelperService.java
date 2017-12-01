package com.digiwin.ecims.platforms.yhd.service.api;

import java.util.List;

public interface YhdApiHelperService {

	/**
	 * 获取数据库中一号店商品ID列表
	 * @param storeId 店铺ID
	 * @param isSerialItem 是否返回系列商品
	 * @return
	 */
	public List<String> getItemIdsList(String storeId, boolean isSerialItem);
}
