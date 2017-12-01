package com.digiwin.ecims.system.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.model.SystemParam;
import com.digiwin.ecims.system.bean.SysParamMaintainQueryBean;


public interface SysParamMaintainService {
	public List<SystemParam> findAll();
	
	public List<SystemParam> findCondition(String id,String status);
	
	public PageBean<SystemParam> findSysParamInfo(SysParamMaintainQueryBean queryBean,String start,String limit);
	
	public SystemParam findOneRecord(Long sysParamMaintainId);
	
	public int executeSql(String sql)throws Exception;
	
	public String testDb(String hql) throws Exception;
}
