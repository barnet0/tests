package com.digiwin.ecims.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.dao.BusinessDataDao;
import com.digiwin.ecims.system.service.BusinessDataService;

@Service
public class BusinessDataServiceImpl implements BusinessDataService {

	@Autowired
	private BusinessDataDao businessDataDao;
	
	@Override
	public <T> PageBean<T> findAllBusinessDataWithLimit(Class<T> clazz, String pojoName, String start, String limit) {
		return findAllBusinessDataByConditionWithLimit(clazz, pojoName, null, start, limit);
	}
	
	@Override
	public <T> PageBean<T> findAllBusinessDataByConditionWithLimit(Class<T> clazz, String pojoName, String condition,
			String start, String limit) {
		List<T> dataList = businessDataDao.findAllBusinessDataByConditionWithLimit(clazz, pojoName, condition, start, limit);
		long count = businessDataDao.findAllBusinessDataCountByCondition(clazz, pojoName, condition);
		
		PageBean<T> pageBean = new PageBean<T>();
		pageBean.setTotal((int) count);
		pageBean.setRoot(dataList);
		pageBean.setLimit(Integer.parseInt(limit));
		
		return pageBean;
	}

	@Override
	public PageBean<AomsordT> findAllOrderWithLimit(String start, String limit) {
		Class<AomsordT> clazz = AomsordT.class;
		return findAllBusinessDataWithLimit(clazz, clazz.getSimpleName(), start, limit);
	}

	@Override
	public PageBean<AomsrefundT> findAllRefundWithLimit(String start, String limit) {
		Class<AomsrefundT> clazz = AomsrefundT.class;
		return findAllBusinessDataWithLimit(clazz, clazz.getSimpleName(), start, limit);
	}

	@Override
	public PageBean<AomsitemT> findAllItemWithLimit(String start, String limit) {
		Class<AomsitemT> clazz = AomsitemT.class;
		return findAllBusinessDataWithLimit(clazz, clazz.getSimpleName(), start, limit);
	}


}
