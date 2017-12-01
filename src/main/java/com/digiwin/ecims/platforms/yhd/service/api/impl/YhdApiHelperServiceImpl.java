package com.digiwin.ecims.platforms.yhd.service.api.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiHelperService;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;

@Service
public class YhdApiHelperServiceImpl implements YhdApiHelperService {

	@Autowired
	private BaseDAO baseDao;
	
	@Override
	public List<String> getItemIdsList(String storeId, boolean isSerialItem) {
		List<String> dataList = null;
		
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT DISTINCT i.id FROM AomsitemT i ");
		sbf.append("WHERE 1=1 ");
		sbf.append("AND i.storeType='").append(YhdCommonTool.STORE_TYPE).append("' ");
		sbf.append("AND i.storeId='").append(storeId).append("' ");
		if (!isSerialItem) {
			sbf.append("AND i.id = aoms004 ");
		} else {
			sbf.append("AND i.id != aoms004 ");
		}
		
		dataList = baseDao.executeQueryByHql(sbf.toString());

		return dataList;
	}

}
