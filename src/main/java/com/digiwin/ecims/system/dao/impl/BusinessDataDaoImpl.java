package com.digiwin.ecims.system.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digiwin.ecims.system.dao.BusinessDataDao;

@Repository
public class BusinessDataDaoImpl implements BusinessDataDao {

	private static final Logger logger = LoggerFactory.getLogger(ShopMaintainDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession() {
		//获取当前session
		return sessionFactory.getCurrentSession();
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public <T> List<T> findAllBusinessData(Class<T> clazz, String pojoName) {
		return findAllBusinessDataByConditionWithLimit(clazz, pojoName, null, null, null);
	}

	@Override
	public <T> List<T> findAllBusinessDataByLimit(Class<T> clazz, String pojoName, String start, String limit) {
		return findAllBusinessDataByConditionWithLimit(clazz, pojoName, null, start, limit);
	}

	@Override
	public <T> List<T> findAllBusinessDataByCondition(Class<T> clazz, String pojoName, String condition) {
		return findAllBusinessDataByConditionWithLimit(clazz, pojoName, condition, null, null);
	}

	@Override
	public <T> List<T> findAllBusinessDataByConditionWithLimit(Class<T> clazz, String pojoName, String condition,
			String start, String limit) {
		StringBuffer hql = new StringBuffer();
		hql.append("FROM ");
		hql.append(pojoName).append(" ");
		hql.append(" WHERE 1=1 ");
		if (condition != null && condition.length() > 0) {
			hql.append(condition);
		}
		
		Query query = this.getSession().createQuery(hql.toString());
		if (start != null && limit != null) {
			query.setFirstResult(Integer.parseInt(start));
			query.setMaxResults(Integer.parseInt(limit));
		}
		List<T> resultList = query.list();
		return resultList;
	}

	@Override
	public <T> long findAllBusinessDataCount(Class<T> clazz, String pojoName) {
		return findAllBusinessDataCountByCondition(clazz, pojoName, null);
	}
	
	@Override
	public <T> long findAllBusinessDataCountByCondition(Class<T> clazz, String pojoName, String condition) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(*) FROM ");
		hql.append(pojoName).append(" ");
		hql.append(" WHERE 1=1 ");
		if (condition != null && condition.length() > 0) {
			hql.append(condition);
		}
		Query query = this.getSession().createQuery(hql.toString());
		long result = (long) query.uniqueResult();
		
		return result;
	}

}
