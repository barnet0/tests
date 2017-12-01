package com.digiwin.ecims.system.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.system.bean.LogMaintainQueryBean;

public interface LogMaintainDao {
	
	/**
	 * 直接查询所有数据 当数据大时易产生性能问题
	 */
	public <T> List<T> findAll(Class<T> clazz);
	
	/**
	 * 创建一个criteria 一个中介类 Criterion是个接口，其实例可以由Restritions来创建
	 */
	public <T> List<T> findCondition(Class<T> clazz,Map<String,Object>paramMap);
	
	public PageBean<LogInfoT> findAllByCondition(LogMaintainQueryBean queryBean,String start,String limit);
	
	public <T> T get(Class<T> clazz, Serializable id) ;
	
	public int update(String sql);
}
