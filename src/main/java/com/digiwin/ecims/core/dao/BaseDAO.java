package com.digiwin.ecims.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

@SuppressWarnings("unchecked")
public interface BaseDAO {

  public Session getSession();

  /**
   * 多筆批次新增
   * 
   * @param objList
   * @return
   */
  public <T> boolean saveByCollection(List<T> objList);

  public <T> boolean updateByCollection(List<T> objList);

  public <T> boolean saveOrUpdateByCollection(List<T> entityList);

  public int deleteByHQL(String hql);

  public int deleteBySQL(String sql);

  /**
   * 保存实体
   * 
   * @param entity
   */
  public <T> boolean save(T entity);

  /**
   * 保存或更新某个实例
   * 
   * @param entity
   */
  public <T> boolean saveOrUpdate(T entity);

  /**
   * 保存或更新多筆批次新增 如果错误，添加日志
   * 
   * @param objList
   * @return
   */
  public <T> boolean saveOrUpdateByCollectionWithLog(List<T> entityList);

  /**
   * 删除某个实例
   * 
   * @param entity
   */
  public void delete(Object entity);

  /**
   * 依据id删除实体
   * 
   * @param <T>
   * @param clazz
   * @param id
   */
  public <T> boolean deleteById(Class clazz, Serializable id);

  /**
   * merge()方法，会根据对象是否进行了实质性修改，来决定是否执行相应的update/delete/update语句， 而upate()则不会进行比较，只用给定的对象信息覆盖原有信息
   * 合并后的entity实例仍然是一个脱管态，而save或saveOrUpdate执行后变为持久态
   * 
   * @param entity
   */
  public <T> boolean update(T entity);

  /**
   * 返回所给id的实体类持久化实例，如果实例不存在则返回null。 该方法不会返回没有初始化的实例。
   * 
   * @param <T>
   * @param clazz
   * @param id
   * @return
   */
  public <T> T get(Class<T> clazz, Serializable id);

  /**
   * 懒加载 返回所给id的实体类持久化实例，假定该实例存在。该方法可能返回一个代理实例， 这个代理实例在非id的方法被访问的时候根据需要初始化。 如果查找的实例不存在，抛出异常。
   * 
   * @param <T>
   * @param clazz
   * @param id
   * @return
   */
  public <T> T load(Class<T> clazz, Serializable id);

  /**
   * 直接查询所有数据 当数据大时易产生性能问题
   * 
   * @param <T>
   * @param clazz
   * @return
   */
  public <T> List<T> findAll(Class<T> clazz);

  /**
   * 强制Session冲刷。将当前Session中所有维持在内存中的保存、更新和删除持久化状态同步到数据库。 该方法必须在事务提交和Session关闭之前调用
   * 。建议只在相同的事务内后续操作依赖于之前操作对数据库的改变时使用，一般情况建议依赖于事务提交时的自动冲刷即可，无需手动调用此方法。
   */
  public void flush();

  /**
   * 从Session的缓存中移除该实例。该实例所有的更改将不会被同步到数据库。
   * 
   * @param entity
   */
  public void evict(Object entity);

  /**
   * 清除Session中缓存的所有对象，并取消当前Session中所有维持在内存中的保存、更新和删除持久化状态。 该方法不会关闭已经打开的迭代器或ScrollableResults实例。
   */
  public void clear();

  /**
   * 创建一个criteria 一个中介类 Criterion是个接口，其实例可以由Restritions来创建
   * 
   * @param <T>
   * @param clazz
   * @param session
   * @param values
   * @return
   */
  public <T> Criteria createCriteria(Class<T> clazz, Session session, Criterion... values);


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
      Criterion... values);

  public <T> int findTotal(Class<T> clazz);


  /**
   * 通过hql查找唯一记录
   * 
   * @return
   * @TODO
   */
  public <T> T findUniqueResultBySQL(String hql);


  /** 调用存储过程 */
  public void callProcedure(String call);

  /** 条件更新数据 */
  public int update(String hql);

  /**
   * 通过SQL参数进行HQL的查询操作
   * 
   * @param hql 要进行查询的HQL语句。
   * @param paramMap 参数Map
   * @return 查询结果列表
   */
  public List queryHqlByParam(String hql, Map<String, Object> paramMap);

  /**
   * 通过SQL参数进行DML操作
   * 
   * @param hql 要进行DML操作的HQL语句，包括INSERT,UPDATE与DELETE
   * @param paramMap 参数Map
   * @return
   */
  public int updateHqlByParam(String hql, Map<String, Object> paramMap);

  public void executeSQL(final String sql);

  public List executeNativeSQL(String sql);


  public <T> List<T> executeQueryByHql(String hql);

  /**
   * 保存实体
   * 
   * @param entity
   */
  public Serializable saveObject(Object entity);

  public List<Object[]> selectByHqlLimit(String hql, int i);

  public List<Object> testDb(String hql) throws Exception;
}
