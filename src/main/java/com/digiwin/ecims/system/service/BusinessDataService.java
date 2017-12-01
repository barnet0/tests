package com.digiwin.ecims.system.service;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.ontime.bean.PageBean;

public interface BusinessDataService {

	public <T> PageBean<T> findAllBusinessDataWithLimit(Class<T> clazz, String pojoName, 
			String start, String limit);
	
	public <T> PageBean<T> findAllBusinessDataByConditionWithLimit(Class<T> clazz, String pojoName,
			String condition,
			String start, String limit);
	
	
	public PageBean<AomsordT> findAllOrderWithLimit(String start, String limit);
	
	public PageBean<AomsrefundT> findAllRefundWithLimit(String start, String limit);
	
	public PageBean<AomsitemT> findAllItemWithLimit(String start, String limit);
}
