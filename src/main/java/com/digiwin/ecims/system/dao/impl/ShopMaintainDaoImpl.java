package com.digiwin.ecims.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.model.SystemParam;
import com.digiwin.ecims.system.bean.EcPlatformInfo;
import com.digiwin.ecims.system.dao.ShopMaintainDao;

@Repository
public class ShopMaintainDaoImpl implements ShopMaintainDao {

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
	public PageBean<AomsshopT> findAllShopDataByCondition(String start, String limit) {
		StringBuffer hql = new StringBuffer("FROM AomsshopT t WHERE 1 = 1 ");
		StringBuffer countHQL = new StringBuffer("SELECT COUNT(*) FROM AomsshopT t WHERE 1 = 1 ");
		
		PageBean<AomsshopT> pageBean = new PageBean<AomsshopT>();
		Query query = this.getSession().createQuery(countHQL.toString());
		int totalCount = Integer.parseInt(query.uniqueResult().toString());
		pageBean.setTotal(totalCount);
		/**
		 * 分页查询结果
		 */
		query = this.getSession().createQuery(hql.toString());
		query.setFirstResult(Integer.parseInt(start));//从第几条开始
		query.setMaxResults(Integer.parseInt(limit));//我要取多少条
		List<AomsshopT> list= query.list();
		pageBean.setRoot(list);
		return pageBean;
	}

	@Override
	public List<EcPlatformInfo> findAllEcPlatformInfo() {
		try {
			List<Object[]> resultsList = this.getSession().createQuery("SELECT DISTINCT aomsshop003,aomsshop011 FROM AomsshopT ORDER BY aomsshop003").list();
			List<EcPlatformInfo> ecPlatformInfos = new ArrayList<EcPlatformInfo>();
			if (resultsList != null && resultsList.size() > 0) {
				for (Object[] objects : resultsList) {
					ecPlatformInfos.add(new EcPlatformInfo((String)objects[0], (String)objects[1]));
				}
			}
			return ecPlatformInfos;
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("find all for AomsshopT.aomsshop003 is failed!");
		}
		return null;
	}

	@Override
	public List<AomsshopT> findShopById(String shopId) {
		try {
			List<AomsshopT> list = null;
			Query query = this.getSession().createQuery("FROM AomsshopT t WHERE 1 = 1 AND t.aomsshop001= :aomsshop001");
			query.setParameter("aomsshop001", shopId);
			
			list = query.list();
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("find shop failed...");
		}
		return null;
	}

	@Override
	public boolean saveOrUpdate(Object entity) {
		try{
			this.getSession().saveOrUpdate(entity);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("saveOrUpdate " + entity.getClass().getName() + " is failed!");
			return false;
		}
		return true;
	}

}
