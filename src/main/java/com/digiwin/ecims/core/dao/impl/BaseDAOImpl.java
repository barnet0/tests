package com.digiwin.ecims.core.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.LogInfoT;

@SuppressWarnings("unchecked")
@Repository
public class BaseDAOImpl implements BaseDAO {
  private static final Logger log = LoggerFactory.getLogger(BaseDAOImpl.class);

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

  /**
   * 多筆批次新增
   * 
   * @param objList
   * @return
   */
  @Override
  public <T> boolean saveByCollection(List<T> entityList) {
    try {
      for (T o : entityList) {
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
   * 多筆批次新增
   * 
   * @param objList
   * @return
   */
  @Override
  public <T> boolean updateByCollection(List<T> entityList) {
    try {
      for (T o : entityList) {
        update(o);
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
  public <T> boolean saveOrUpdateByCollection(List<T> entityList) {
    long beginTime = System.currentTimeMillis();
    log.info("save begin---" + beginTime);
    log.info("save count--" + (entityList == null ? 0 : entityList.size()));
    try {
      for (T o : entityList) {
        saveOrUpdate(o);
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.info("save " + entityList.get(0).getClass().getName() + " is failed!");
      return false;
    }
    log.info("save end---" + (System.currentTimeMillis() - beginTime));
    return true;
  }

  /**
   * 保存或更新多筆批次新增
   * 
   * @param objList
   * @return
   */
  public <T> boolean saveOrUpdateByCollection2(List<T> entityList) {
    try {
      for (T o : entityList) {
     //   this.getSession().saveOrUpdate(o);
    	  //170915 修改  抛出异常 换merge
    	  this.getSession().merge(o);
      }
      this.flush();
    } catch (Exception e) {
      this.clear();
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * 保存或更新多筆批次新增 增加日志保存功能 增加防拆单功能 增加防止sessoion缓存主键冲突逻辑
   * 
   * @param objList
   * @return
   */
  @Override
  public <T> boolean saveOrUpdateByCollectionWithLog(List<T> entityList) {
    long beginTime = System.currentTimeMillis();
    log.info("save begin---" + beginTime);
    log.info("save count--" + (entityList == null ? 0 : entityList.size()));

    /*
     * 先整批flush提交一次，如果有异常，则返回false，然后进行单个提交，因为绝大多数是成功的，没必要全部都进行单个提交
     */
    boolean isAllSuccess = saveOrUpdateByCollection2(entityList);
    if (!isAllSuccess) {
      Date beingTime = new Date();
      T obj = null;
      HashMap<String, Object> errMap = null;
      for (int i = 0; i < entityList.size(); i++) {
        obj = entityList.get(i);
        String key = null;
        String billType = null;
        String storeType = null;
        String billStatus = null;
        String otherKey = null;
        String storeId = null;
        if (obj instanceof AomsordT) {
          AomsordT aomsordT = (AomsordT) obj;
          key = aomsordT.getId();
          // 联合主键：storetype+Aoms060（storeid），AomsordT的id+storetype+Aoms060为联合主键
          storeType = aomsordT.getStoreType();
          otherKey = aomsordT.getAoms060();
          billStatus = aomsordT.getModified();
          billType = "AomsordT";
          storeId = aomsordT.getStoreId();
        } else if (obj instanceof AomsrefundT) {
          AomsrefundT aomsrefundT = (AomsrefundT) obj;
          // 联合主键:id+storetype
          key = aomsrefundT.getId();
          storeType = aomsrefundT.getStoreType();
          billStatus = aomsrefundT.getModified();
          billType = "AomsrefundT";
          storeId = aomsrefundT.getStoreId();
        } else {
          AomsitemT aomsitemT = (AomsitemT) obj;
          key = aomsitemT.getId();
          // 联合主键:id+storetype+sku
          storeType = aomsitemT.getStoreType();
          otherKey = aomsitemT.getAoms004();
          billStatus = aomsitemT.getModified();
          billType = "AomsitemT";
          storeId = aomsitemT.getStoreId();
        }
        try {
          // saveOrUpdate(obj);
          this.getSession().saveOrUpdate(obj);
          // this.flush(); // 修正 flush() 出錯, 造成兩個資料表都有資料, 且造成資料無法推送的問題 add by xavier on 20151110
        } catch (Exception e) {
          e.printStackTrace();
          this.getSession().clear();
          if (errMap == null) {
            errMap = new HashMap<String, Object>();
          }
          LogInfoT loginfo = new LogInfoT("127.0.0.1", billType + "#" + storeType, beingTime,
              "save " + billType + " failed--此条记录保存失败是整个订单保存失败的主要原因", e.getMessage(), key, billType,
              storeType, billStatus, otherKey, storeId);
          // 判断map中是否存在相同主键的value
          if (errMap.get(key) == null) {
            // 如果不存在，创建一个list，加入
            errMap.put(key, loginfo);
          }

          log.info("save " + entityList.get(0).getClass().getName() + " billType = " + billType
              + " unionkey=" + key + "#" + billType + "#" + otherKey + " is failed!");
        } finally {
          this.flush();
        }
      }
      if (errMap != null && errMap.size() > 0) {
        List<Object> listObj = new ArrayList<Object>(errMap.size());
        listObj.addAll(errMap.values());
        log.info("save loginfo begin---");
        saveByCollection(listObj);
        log.info("save loginfo end--" + listObj.size() + " size");
      }
    }
    // this.clear();
    log.info("save end---cost:" + (System.currentTimeMillis() - beginTime) + " millis");
    return true;
  }


  /**
   * 保存实体
   * 
   * @param entity
   */
  public <T> boolean save(T entity) {
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
  public <T> boolean saveOrUpdate(T entity) {
    try {
      this.getSession().saveOrUpdate(entity);
    } catch (Exception e) {
      e.printStackTrace();
      log.error("saveOrUpdate " + entity.getClass().getName() + " is failed!");
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
      return this.getSession().createQuery("From " + clazz.getSimpleName() + " t").list();
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

  @Override
  public List queryHqlByParam(String hql, Map<String, Object> paramMap) {
    try {
      Query query = setHqlQueryParams(hql, paramMap);

      return query.list();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("delete stmt :  " + hql + " is failed!");
      throw e;
    }
  }

  @Override
  public int updateHqlByParam(String hql, Map<String, Object> paramMap) {
    try {
      Query query = this.getSession().createQuery(hql);

      if (paramMap != null && paramMap.size() > 0) {
        Iterator<String> iteStrKey = paramMap.keySet().iterator();
        while (iteStrKey.hasNext()) {
          String key = iteStrKey.next();
          Object obj = paramMap.get(key);
          query.setParameter(key, obj);
        }
      }

      return query.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("stmt :  " + hql + " is failed!");
      throw e;

    }
  }

  /**
   * 设定HQL声明的参数，并返回一个以HQL为参数的org.hibernate.Query实例
   * 
   * @param hql 含有参数的HQL语句
   * @param paramMap 参数Map
   * @return org.hibernate.Query实例
   */
  private Query setHqlQueryParams(String hql, Map<String, Object> paramMap) {
    Query query = this.getSession().createQuery(hql);

    if (paramMap != null && paramMap.size() > 0) {
      Iterator<String> iteStrKey = paramMap.keySet().iterator();
      while (iteStrKey.hasNext()) {
        String key = iteStrKey.next();
        Object obj = paramMap.get(key);
        query.setParameter(key, obj);
      }
    }

    return query;
  }

  public void executeSQL(final String sql) {
    getSession().doWork(new Work() {
      @Override
      public void execute(java.sql.Connection connection) throws SQLException {
        // connection.createStatement().execute(sql);
        // finally 里关闭资源
      }
    });
  }

  public List executeNativeSQL(String sql) {
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
      log.info("save {} is failed!", entity.getClass().getName());
      return null;
    }
  }

  @Override
  public int deleteByHQL(String hql) {
    try {
      Query query = this.getSession().createQuery(hql);
      return query.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("delete stmt : {} is failed!", hql);
      return -1;
    }
  }

  @Override
  public int deleteBySQL(String sql) {
    try {
      Query query = this.getSession().createSQLQuery(sql);
      return query.executeUpdate();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("delete stmt : {} is failed!", sql);
      return -1;
    }
  }

  public List<Object[]> selectByHqlLimit(String hql, int i) {
    try {
      Query query = this.getSession().createQuery(hql);
      if (i >= 0) {
        query.setFirstResult(0);
        query.setMaxResults(i);
      }
      return query.list();
    } catch (Exception e) {
      e.printStackTrace();
      log.info("select stmt: {} is failed!", hql);
      return null;
    }
  }

  @Override
  public List<Object> testDb(String hql) throws Exception {
    log.info("hql is {}", hql);
    return this.getSession().createQuery(hql).list();
  }

}

