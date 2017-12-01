package com.digiwin.ecims.system.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digiwin.ecims.core.dao.impl.BaseDAOImpl;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.util.StringTool;
import com.digiwin.ecims.system.bean.LogMaintainQueryBean;
import com.digiwin.ecims.system.dao.LogMaintainDao;

@Repository
public class LogMaintainDaoImpl implements LogMaintainDao{

public static final Log log = LogFactory.getLog(BaseDAOImpl.class);
	
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
	
	private String likeStr(String str) {
		return "'%" + str + "%'";
	}
	
	@Override
	public <T> List<T> findAll(Class<T> clazz) {
		try{
			return this.getSession().createQuery("From "+clazz.getSimpleName()+" t").list();
		}catch(Exception e){
			e.printStackTrace();
			log.info("find all for " + clazz.getClass().getName() + " is failed!");
		}
		return null;
	}

	@Override
	public <T> List<T> findCondition (Class<T> clazz,Map<String, Object> paramMap) {
//		Criteria criteria = session.createCriteria(clazz);
//		criteria.add(Restrictions.like("reqTime", paramMap.get("reqTime")+"%"));
//		criteria.add(Restrictions.eq("businessType",paramMap.get("businessType")));
//		List logs = criteria.list();
		return null;
	}

	@Override
	public PageBean<LogInfoT> findAllByCondition(
			LogMaintainQueryBean queryBean, String start, String limit) {
		StringBuffer hql = new StringBuffer("FROM LogInfoT t WHERE 1 = 1 ");
		
		StringBuffer countHQL = new StringBuffer("SELECT COUNT(*) FROM  LogInfoT t WHERE 1 = 1 ");
		
		if(queryBean != null){
			StringBuffer sqlCondition = new StringBuffer();
			if (StringTool.isNotEmpty(queryBean.getReqTime())) {
				sqlCondition.append(" and  t.reqTime like ").append(likeStr(queryBean.getReqTime()));
			}
			if (StringTool.isNotEmpty(queryBean.getBusinessType())) {
				sqlCondition.append(" and  t.businessType like ").append(likeStr(queryBean.getBusinessType()));
			}
			if (StringTool.isNotEmpty(queryBean.getIsSuccess())) {
				sqlCondition.append(" and  t.isSuccess like ").append(likeStr(queryBean.getIsSuccess()));
			}
			//exceptionType 异常类型，0系统数据异常，1，业务数据异常
			if (StringTool.isNotEmpty(queryBean.getExceptionType())) {
				if("0".equals(queryBean.getExceptionType())){
					sqlCondition.append(" and  (t.errBillId is null or t.errBillId = '')");
				}else{
					sqlCondition.append(" and  t.errBillId is not null and t.errBillId != ''");
				}
			}
			
			countHQL.append(sqlCondition);
			sqlCondition.append(" order by t.reqTime DESC ");
			hql.append(sqlCondition);
		}
		PageBean<LogInfoT> pageBean = new PageBean<LogInfoT>();
		Query query = this.getSession().createQuery(countHQL.toString());
		int totalCount = Integer.parseInt(query.uniqueResult().toString());
		pageBean.setTotal(totalCount);
		/**
		 * 分页查询结果
		 */
		query = this.getSession().createQuery(hql.toString());
		query.setFirstResult(Integer.parseInt(start));//从第几条开始
		query.setMaxResults(Integer.parseInt(limit));//我要取多少条
		List<LogInfoT> list= query.list();
		pageBean.setRoot(list);
		return pageBean;
	}
	
	/**
	 * 返回所给id的实体类持久化实例，如果实例不存在则返回null。 该方法不会返回没有初始化的实例。
	 * 
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> clazz, Serializable id) {
		try{
			return (T) getSession().get(clazz, id);
		}catch(Exception e){
			e.printStackTrace();
			log.info("get by id for " + clazz.getClass().getName() + " is failed!");
		}
		return null;
	}
	
	@Override
	public int update(String sql) {
		 Query query = this.getSession().createSQLQuery(sql);
		 return query.executeUpdate();
	}

}
