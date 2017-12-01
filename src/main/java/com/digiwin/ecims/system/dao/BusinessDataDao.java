package com.digiwin.ecims.system.dao;

import java.util.List;

public interface BusinessDataDao {

	public <T> List<T> findAllBusinessData(Class<T> clazz, String pojoName);
	
	public <T> List<T> findAllBusinessDataByLimit(Class<T> clazz, String pojoName, String start, String limit);
	
	public <T> List<T> findAllBusinessDataByCondition(Class<T> clazz, String pojoName, String condition);
	
	public <T> List<T> findAllBusinessDataByConditionWithLimit(Class<T> clazz, String pojoName, String condition, String start, String limit);
	
	public <T> long findAllBusinessDataCount(Class<T> clazz, String pojoName);
	
	public <T> long findAllBusinessDataCountByCondition(Class<T> clazz, String pojoName, String condition);
	
}