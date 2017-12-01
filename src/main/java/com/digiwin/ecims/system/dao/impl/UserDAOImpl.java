package com.digiwin.ecims.system.dao.impl;
/**
 * @author sux
 * @time 2011-1-11
 */
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.digiwin.ecims.core.util.ConditionValidate;
import com.digiwin.ecims.system.bean.PageBean;
import com.digiwin.ecims.system.dao.UserDAO;
import com.digiwin.ecims.system.model.User;
@Repository
public class UserDAOImpl implements UserDAO{
	public static final Log log = LogFactory.getLog(UserDAOImpl.class);
	
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
	
	@SuppressWarnings("unchecked")
	public List<User> findByUsernameAndPassword(String username, String password) {
		log.info("start findByUsernaemAndPassword");
		String hql = "FROM User u WHERE u.userName = :username AND u.userPwd = :userPwd ";
		Query query = this.getSession().createQuery(hql);
		query.setParameter("username", username);
		query.setParameter("userPwd", password);
		return query.list();
	}

	
	public PageBean findAll(String start, String limit) {
		log.info("find all user");
		String hql = "FROM User";
		PageBean pageBean = new PageBean();
		Query query = this.getSession().createQuery(hql);
		query.setFirstResult(Integer.parseInt(start));
		query.setMaxResults(Integer.parseInt(limit));
		pageBean.setRoot(query.list());
		List list = this.getSession().createQuery(hql).list();
		pageBean.setTotalProperty(list==null?0:list.size());
		return pageBean;
	}

	
	public PageBean findByCondition(User userBean, String start, String limit) {
		StringBuffer sb = new StringBuffer("FROM User u WHERE 1 = 1 ");
		if(!ConditionValidate.isEmpty(userBean.getUserRealName())){
			sb.append(" AND u.userRealName = '"+userBean.getUserRealName()+"'");
		}
		if(!ConditionValidate.isEmpty(userBean.getUserName())){
			sb.append(" AND u.userName = '"+userBean.getUserName()+"'");
		}
		PageBean pageBean = new PageBean();
		Query query = this.getSession().createQuery(sb.toString());
		query.setFirstResult(Integer.parseInt(start));
		query.setMaxResults(Integer.parseInt(limit));
		pageBean.setRoot(query.list());
		List list = this.getSession().createQuery(sb.toString()).list();
		pageBean.setTotalProperty(list==null?0:list.size());
		return pageBean;
	}

	
	public boolean deleteByIds(String[] userIds) {
		boolean flag = true;
		for(String userId : userIds){
			Object obj = this.getSession().get(User.class, Integer.parseInt(userId));
			try{
				this.getSession().delete(obj);
				flag = true;
			}catch(Exception ex){
				ex.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	
	public boolean saveOrUpdate(Object user) {
		try{
			this.getSession().saveOrUpdate(user);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	
	public User findById(int userId) {
		return (User)this.getSession().get(User.class, userId);
	}

}