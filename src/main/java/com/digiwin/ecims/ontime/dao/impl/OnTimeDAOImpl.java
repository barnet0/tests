package com.digiwin.ecims.ontime.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.digiwin.ecims.ontime.bean.OnTimeTaskQueryBean;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.dao.OnTimeDAO;
import com.digiwin.ecims.ontime.model.OntimeTask;
import com.digiwin.ecims.ontime.util.StringTool;

@SuppressWarnings("unchecked")
@Repository
public class OnTimeDAOImpl implements OnTimeDAO {
  public static final Log log = LogFactory.getLog(OnTimeDAOImpl.class);

  @Autowired
  private SessionFactory sessionFactory;

  private Session session;

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Session getSession() {
    // 获取当前session
    return sessionFactory.getCurrentSession();
  }

  public void setSession(Session session) {
    this.session = session;
  }

  private String likeStr(String str) {
    return "'%" + str + "%'";
  }

  /**
   * 多筆批次新增
   * 
   * @param objList
   * @return
   */
  @Override
  public boolean saveByCollection(List<Object> entityList) {
    // TODO Auto-generated method stub
    try {
      for (Object o : entityList) {
        save(o);
      }
      // getSession().flush();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("save " + entityList.get(0).getClass().getName() + " is failed!");
      return false;
    }
    return true;
  }

  /**
   * 保存或更新多筆批次新增
   * 
   * @param objList
   * @return
   */
  @Override
  public boolean saveOrUpdateByCollection(List<Object> entityList) {
    // TODO Auto-generated method stub
    try {
      for (Object o : entityList) {
        saveOrUpdate(o);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.info("save " + entityList.get(0).getClass().getName() + " is failed!");
      return false;
    }
    return true;
  }

  /**
   * 保存实体
   * 
   * @param entity
   */
  public boolean save(Object entity) {
    try {
      this.getSession().save(entity);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("save " + entity.getClass().getName() + " is failed!");
      return false;
    }
    return true;
  }

  /**
   * 保存或更新某个实例
   * 
   * @param entity
   */
  public boolean saveOrUpdate(Object entity) {
    try {
      this.getSession().saveOrUpdate(entity);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("saveOrUpdate " + entity.getClass().getName() + " is failed!");
      return false;
    }
    return true;
  }

  /**
   * 删除某个实例
   * 
   * @param entity
   */
  public void delete(Object entity) {
    try {
      this.getSession().delete(entity);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("delete " + entity.getClass().getName() + " is failed!");
    }
  }

  /**
   * 依据id删除实体
   * 
   * @param <T>
   * @param clazz
   * @param id
   */
  public <T> boolean deleteById(Class clazz, Serializable id) {
    try {
      this.delete(this.get(clazz, id));
    } catch (Exception e) {
      e.printStackTrace();
      log.info("delete by id for " + clazz.getName() + " is failed!");
      return false;
    }
    return true;
  }

  /**
   * merge()方法，会根据对象是否进行了实质性修改，来决定是否执行相应的update/delete/update语句， 而upate()则不会进行比较，只用给定的对象信息覆盖原有信息
   * 合并后的entity实例仍然是一个脱管态，而save或saveOrUpdate执行后变为持久态
   * 
   * @param entity
   */
  public boolean update(Object entity) {
    try {
      this.getSession().merge(entity);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("update " + entity.getClass().getName() + " is failed!");
      return false;
    }
    return true;
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
    try {
      return (T) getSession().get(clazz, id);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("get by id for " + clazz.getClass().getName() + " is failed!");
    }
    return null;
  }

  /**
   * 懒加载 返回所给id的实体类持久化实例，假定该实例存在。该方法可能返回一个代理实例， 这个代理实例在非id的方法被访问的时候根据需要初始化。 如果查找的实例不存在，抛出异常。
   * 
   * @param <T>
   * @param clazz
   * @param id
   * @return
   */
  public <T> T load(Class<T> clazz, Serializable id) {
    return (T) getSession().load(clazz, id);
  }

  /**
   * 直接查询所有数据 当数据大时易产生性能问题
   * 
   * @param <T>
   * @param clazz
   * @return
   */
  public <T> List<T> findAll(Class<T> clazz) {
    try {
      return this.getSession().createQuery("From TestUse tu").list();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("find all for " + clazz.getClass().getName() + " is failed!");
    }
    return null;
  }

  /**
   * 强制Session冲刷。将当前Session中所有维持在内存中的保存、更新和删除持久化状态同步到数据库。 该方法必须在事务提交和Session关闭之前调用
   * 。建议只在相同的事务内后续操作依赖于之前操作对数据库的改变时使用，一般情况建议依赖于事务提交时的自动冲刷即可，无需手动调用此方法。
   */
  public void flush() {
    this.getSession().flush();
  }

  /**
   * 从Session的缓存中移除该实例。该实例所有的更改将不会被同步到数据库。
   * 
   * @param entity
   */
  public void evict(Object entity) {
    this.getSession().evict(entity);
  }

  /**
   * 清除Session中缓存的所有对象，并取消当前Session中所有维持在内存中的保存、更新和删除持久化状态。 该方法不会关闭已经打开的迭代器或ScrollableResults实例。
   */
  public void clear() {
    this.getSession().clear();
  }


  /**
   * 创建一个criteria 一个中介类 Criterion是个接口，其实例可以由Restritions来创建
   * 
   * @param <T>
   * @param clazz
   * @param session
   * @param values
   * @return
   */
  public <T> Criteria createCriteria(Class<T> clazz, Session session, Criterion... values) {
    Criteria criteria = session.createCriteria(clazz);
    for (Criterion value : values) {
      criteria.add(value);
    }
    return criteria;
  }

  /**
   * 创建带排序条件的criteria
   * 
   * @param <T>
   * @param clazz
   * @param session
   * @param orderBy
   * @param isAsc
   * @param values
   * @return
   */
  public <T> Criteria createCriteria(Class<T> clazz, Session session, String orderBy, boolean isAsc,
      Criterion... values) {
    Assert.hasText(orderBy);
    Criteria criteria = this.createCriteria(clazz, session, values);
    if (isAsc) {
      criteria.addOrder(Order.asc(orderBy));
    } else {
      criteria.addOrder(Order.desc(orderBy));
    }
    return criteria;
  }

  public <T> int findTotal(Class<T> clazz) {
    List<T> list = this.findAll(clazz);
    return list.size();
  }

  /**
   * @return
   * @TODO
   */
  public <T> T findUniqueResultBySQL(String hql) {
    Query query = this.getSession().createQuery(hql);
    Object o = query.uniqueResult();
    return (T) o;
  }



  /** 调用存储过程 */
  public void callProcedure(String call) {
    SQLQuery query = this.getSession().createSQLQuery(call);
    query.executeUpdate();
  }

  @Override
  public int update(String hql) {
    Query query = this.getSession().createQuery(hql);
    return query.executeUpdate();
  }

  public int updateByNativeSql(String sql) {
    SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
    return sqlQuery.executeUpdate();
  }

  public void executeSQL(final String sql) {
    getSession().doWork(new Work() {
      @Override
      public void execute(java.sql.Connection connection) throws SQLException {
        // TODO Auto-generated method stub
        // connection.createStatement().execute(sql);
        // finally 里关闭资源
      }
    });
  }

  public List executeNativeQuerySql(String sql) {
    return getSession().createSQLQuery(sql).list();
  }

  public <T> List<T> executeQueryByHql(String hql) {
    return getSession().createQuery(hql).list();
  }

  /**
   * 保存实体
   * 
   * @param entity
   */
  public Serializable saveObject(Object entity) {
    try {
      return this.getSession().save(entity);
    } catch (Exception e) {
      e.printStackTrace();
      log.info("save " + entity.getClass().getName() + " is failed!");
      return null;
    }
  }

  /**
   * 不分页查找所有任务
   * 
   * @return
   */
  public PageBean findAll() {
    PageBean pageBean = new PageBean();
    StringBuffer hql = new StringBuffer("FROM OntimeTask t");

    StringBuffer countHQL = new StringBuffer("SELECT COUNT(*) FROM  OntimeTask t");
    Query query = this.getSession().createQuery(countHQL.toString());
    int totalCount = Integer.parseInt(query.uniqueResult().toString());
    pageBean.setTotal(totalCount);
    /**
     * 分页查询结果
     */
    query = this.getSession().createQuery(hql.toString());
    List list = query.list();
    pageBean.setRoot(list);
    return pageBean;
  }

  /**
   * 分页，按条件查找任务列表
   * 
   * @param queryBean
   * @param start
   * @param limit
   * @return
   */
  public PageBean<OntimeTask> findAllByCondition(OnTimeTaskQueryBean queryBean, String start,
      String limit) {
    StringBuffer hql = new StringBuffer("FROM OntimeTask t WHERE 1 = 1 ");

    StringBuffer countHQL = new StringBuffer("SELECT COUNT(*) FROM  OntimeTask t WHERE 1 = 1 ");
    if (queryBean != null) {
      StringBuffer sqlCondition = new StringBuffer();
      if (StringTool.isNotEmpty(queryBean.getCode())) {
        sqlCondition.append(" and  t.code like ").append(likeStr(queryBean.getCode()));
      }
      if (StringTool.isNotEmpty(queryBean.getName())) {
        sqlCondition.append(" and  t.name like ").append(likeStr(queryBean.getName()));
      }
      if (StringTool.isNotEmpty(queryBean.getStatus())) {
        sqlCondition.append(" and  t.status like ").append(likeStr(queryBean.getStatus()));
      }
      countHQL.append(sqlCondition);
      hql.append(sqlCondition);
    }
    hql.append(" ORDER BY 1 "); // add by mowj 20160531 添加默认排序规则
    PageBean<OntimeTask> pageBean = new PageBean<OntimeTask>();
    Query query = this.getSession().createQuery(countHQL.toString());
    int totalCount = Integer.parseInt(query.uniqueResult().toString());
    pageBean.setTotal(totalCount);
    /**
     * 分页查询结果
     */
    query = this.getSession().createQuery(hql.toString());
    query.setFirstResult(Integer.parseInt(start));// 从第几条开始
    query.setMaxResults(Integer.parseInt(limit));// 我要取多少条
    List<OntimeTask> list = query.list();
    pageBean.setRoot(list);
    return pageBean;
  }
}

