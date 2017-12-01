package com.digiwin.ecims.system.dao;

import java.util.List;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.EcPlatformInfo;

public interface ShopMaintainDao {

	/**
	 * 查询所有店铺基础资料
	 * @param limit 
	 * @param start 
	 * @return
	 */
	public PageBean<AomsshopT> findAllShopDataByCondition(String start, String limit);
	
	/**
	 * 查询所有的电商平台信息
	 * @return
	 */
	public List<EcPlatformInfo> findAllEcPlatformInfo();
	
	public List<AomsshopT> findShopById(String shopId);
	
	public boolean saveOrUpdate(Object entity);
}
