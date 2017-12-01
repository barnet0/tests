package com.digiwin.ecims.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.EcPlatformInfo;
import com.digiwin.ecims.system.dao.ShopMaintainDao;
import com.digiwin.ecims.system.service.ShopMaintainService;

@Service
public class ShopMaintainServiceImpl implements ShopMaintainService {

	@Autowired
	private ShopMaintainDao shopMaintainDao;
	
	@Override
	public PageBean<AomsshopT> findAllShopDataByCondition(String start, String limit){
		PageBean pageBean  = shopMaintainDao.findAllShopDataByCondition(start, limit);
		return pageBean;
	}

	@Override
	public List<EcPlatformInfo> findAllEcPlatformInfo() {
		return shopMaintainDao.findAllEcPlatformInfo();
	}

	@Override
	public boolean shopExist(String shopId) {
		List<AomsshopT> list = shopMaintainDao.findShopById(shopId);
		if (list != null && list.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public AomsshopT saveOrUpdateAomsshopT(AomsshopT aomsshopT) {
		if (aomsshopT != null) {
			AomsshopT shopInDb = null;
			shopInDb = new AomsshopT();
			shopInDb.setAomsshop001(aomsshopT.getAomsshop001());
			shopInDb.setAomsshop002(aomsshopT.getAomsshop002());
			shopInDb.setAomsshop003(aomsshopT.getAomsshop003());
			shopInDb.setAomsshop004(aomsshopT.getAomsshop004());
			shopInDb.setAomsshop005(aomsshopT.getAomsshop005());
			shopInDb.setAomsshop006(aomsshopT.getAomsshop006());
			shopInDb.setAomsshop007(aomsshopT.getAomsshop007());
			shopInDb.setAomsshop008(aomsshopT.getAomsshop008());
			shopInDb.setAomsshop009(aomsshopT.getAomsshop009());
			shopInDb.setAomsshop010(aomsshopT.getAomsshop010());
			shopInDb.setAomsshop011(aomsshopT.getAomsshop011());
			String date = DateTimeTool.format(new Date());
			shopInDb.setAomsshopcrtdt(date);
			shopInDb.setAomsshopmoddt(date);
			
			boolean isSuccess = shopMaintainDao.saveOrUpdate(shopInDb);
			if (isSuccess) {
				return shopInDb;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public AomsshopT findShopById(String shopId) {
		if (StringUtils.isNotBlank(shopId) && StringUtils.isNotEmpty(shopId)) {
			List<AomsshopT> resultList = shopMaintainDao.findShopById(shopId);
			if (resultList != null && resultList.size() == 1) {
				return resultList.get(resultList.size() - 1);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
}
