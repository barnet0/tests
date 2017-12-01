package com.digiwin.ecims.core.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digiwin.ecims.core.dao.StandardAreaDAO;
import com.digiwin.ecims.ontime.dao.impl.OnTimeDAOImpl;

@SuppressWarnings("unchecked")
@Repository
public class StandardAreaDAOImpl implements StandardAreaDAO {

	public static final Log log = LogFactory.getLog(StandardAreaDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	@Override
	public Session getSession() {
		//获取当前session
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 保存实体
	 * @param entity
	 */
	public boolean save(Object entity){
		try{
			this.getSession().save(entity);
		}catch(Exception e){
			e.printStackTrace();
			log.info("save "+entity.getClass().getName()+" is failed!");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean saveByCollection(List<? extends Object> objList) {
		try{
			for(Object o:objList){
				save(o);
			}
			//getSession().flush();
		}catch(Exception e){
			e.printStackTrace();
			log.info("save " + objList.get(0).getClass().getName() + " is failed!");
			return false;
		}
		return true;
	}

	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		try{
			return this.getSession().createQuery("From StandardArea sa").list();
		}catch(Exception e){
			e.printStackTrace();
			log.info("find all for " + clazz.getClass().getName() + " is failed!");
		}
		return null;
	}

}
