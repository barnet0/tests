package com.digiwin.ecims.ontime.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;

import com.digiwin.ecims.ontime.bean.OnTimeTaskQueryBean;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.model.OntimeTask;

@SuppressWarnings("unchecked")
public interface OnTimeDAO{
	
	public Session getSession();
	/**
	 * 多筆批次新增
	 * @param objList
	 * @return
	 */
	public boolean saveByCollection(List<Object> objList);
	
	public boolean saveOrUpdateByCollection(List<Object> entityList);
	
	
	/**
	 * 保存实体
	 * @param entity
	 */
	public boolean save(Object entity);
	
	/**
	 * 保存或更新某个实例
	 * 
	 * @param entity
	 */
	public boolean saveOrUpdate(Object entity);
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
	 * merge()方法，会根据对象是否进行了实质性修改，来决定是否执行相应的update/delete/update语句，
	 * 而upate()则不会进行比较，只用给定的对象信息覆盖原有信息
	 * 合并后的entity实例仍然是一个脱管态，而save或saveOrUpdate执行后变为持久态
	 * 
	 * @param entity
	 */
	public boolean update(Object entity);
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
	 * 懒加载 返回所给id的实体类持久化实例，假定该实例存在。该方法可能返回一个代理实例， 这个代理实例在非id的方法被访问的时候根据需要初始化。
	 * 如果查找的实例不存在，抛出异常。
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
	 * 强制Session冲刷。将当前Session中所有维持在内存中的保存、更新和删除持久化状态同步到数据库。
	 * 该方法必须在事务提交和Session关闭之前调用
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
	 * 清除Session中缓存的所有对象，并取消当前Session中所有维持在内存中的保存、更新和删除持久化状态。
	 * 该方法不会关闭已经打开的迭代器或ScrollableResults实例。
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
	public <T> Criteria createCriteria(Class<T> clazz, Session session,
			Criterion... values);


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
	public <T> Criteria createCriteria(Class<T> clazz, Session session,
			String orderBy, boolean isAsc, Criterion... values);

	public<T> int findTotal(Class<T> clazz);
	
    
    /** 
     * 通过hql查找唯一记录
     * @return 
     * @TODO
     */  
   public <T> T findUniqueResultBySQL(String hql);
	
	
	/** 调用存储过程 */
	public void callProcedure(String call);
	
	/** 条件更新数据 */
	public int update(String hql);
	
	/**
	 * 使用原生SQL语句执行更新
	 * @param sql
	 * @return 更新影响的行数
	 */
	public int updateByNativeSql(String sql);
	
	public void executeSQL(final String sql);
	public List executeNativeQuerySql(String sql);
	
	
	public <T> List<T> executeQueryByHql(String hql);
	
	/**
	 * 保存实体
	 * @param entity
	 */
	public Serializable saveObject(Object entity);
	/**
	 * 不分页查找所有任务
	 * @return
	 */
	public PageBean<OntimeTask> findAll();
	/**
	 * 分页，按条件查找任务列表
	 * @param queryBean
	 * @param start
	 * @param limit
	 * @return
	 */
	public PageBean<OntimeTask> findAllByCondition(OnTimeTaskQueryBean queryBean,String start,String limit);
	
}
