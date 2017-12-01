package com.digiwin.ecims.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.model.SystemParam;
import com.digiwin.ecims.system.bean.SysParamMaintainQueryBean;
import com.digiwin.ecims.system.controller.SysParamMaintainController;
import com.digiwin.ecims.system.dao.SysParamMaintainDao;
import com.digiwin.ecims.system.service.SysParamMaintainService;

@Service
public class SysParamMaintainServiceImpl implements SysParamMaintainService {

	private static final Logger logger = LoggerFactory.getLogger(SysParamMaintainServiceImpl.class);
	
	@Autowired
	SysParamMaintainDao sysParamDao;
	
	@Autowired
	BaseDAO baseDao;
	
	@Override
	public List<SystemParam> findAll() {
		// TODO Auto-generated method stub
		return sysParamDao.findAll(SystemParam.class);
	}

	@Override
	public List<SystemParam> findCondition(String pKey, String status) {		
		// TODO Auto-generated method stub		
		Map<String,Object> paramMap=new HashMap<String,Object>();;
		paramMap.put("pKey",pKey);
		paramMap.put("status",status);
		return sysParamDao.findCondition(SystemParam.class, paramMap);
	}

	@Override
	public PageBean<SystemParam> findSysParamInfo(SysParamMaintainQueryBean queryBean,String start, String limit) {
		PageBean pageBean  = sysParamDao.findAllByCondition(queryBean, start, limit);
		return pageBean;
	}
	
	@Override
	public SystemParam findOneRecord(Long sysParamMaintainId){
		SystemParam logInfoOnlyRecord = sysParamDao.get(SystemParam.class, sysParamMaintainId);
		return logInfoOnlyRecord;
	}

	@Override
	public int executeSql(String sql) throws Exception {
		return sysParamDao.doQueryBySQL(sql);
	}

	@Override
	public String testDb(String hql) throws Exception {
		logger.info("hql is {}", hql); 
		List<Object> result = baseDao.testDb(hql);
		String aString = "";
		for (Object object : result) {
			aString += object.toString() + " ";
		}
		return aString; 
	}
	
}
