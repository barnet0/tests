package com.digiwin.ecims.system.service;

import java.util.List;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.EcPlatformInfo;

public interface ShopMaintainService {
	
	/**
	 * 返回所有店铺基础资料
	 * @param limit 
	 * @param start 
	 * @return
	 */
	public PageBean<AomsshopT> findAllShopDataByCondition(String start, String limit);
	
	/**
	 * 返回所有的电商平台信息
	 * @return
	 */
	public List<EcPlatformInfo> findAllEcPlatformInfo();
	
	/**
	 * 依据店铺编号判断是否已经在数据库中存在此店铺
	 * @param shopId
	 * @return
	 */
	public boolean shopExist(String shopId);
	
	public AomsshopT saveOrUpdateAomsshopT(AomsshopT aomsshopT);
	
	public AomsshopT findShopById(String shopId);
}
