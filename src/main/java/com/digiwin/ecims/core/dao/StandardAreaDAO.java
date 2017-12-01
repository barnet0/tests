package com.digiwin.ecims.core.dao;

import java.util.List;

import org.hibernate.Session;

public interface StandardAreaDAO {
	
	public Session getSession();
	/**
	 * 多筆批次新增
	 * @param objList
	 * @return
	 */
	public boolean saveByCollection(List<? extends Object> objList);
	
	/**
	 * 直接查询所有数据 当数据大时易产生性能问题
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public <T> List<T> findAll(Class<T> clazz);
}
