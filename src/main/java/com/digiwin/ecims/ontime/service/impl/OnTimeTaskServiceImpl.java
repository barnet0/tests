package com.digiwin.ecims.ontime.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.exception.GenericBusinessException;
import com.digiwin.ecims.ontime.bean.OnTimeTaskQueryBean;
import com.digiwin.ecims.ontime.bean.PageBean;
import com.digiwin.ecims.ontime.dao.OnTimeDAO;
import com.digiwin.ecims.ontime.model.LoginUser;
import com.digiwin.ecims.ontime.model.OntimeTask;
import com.digiwin.ecims.ontime.service.OnTimeTaskService;

@Service
public class OnTimeTaskServiceImpl implements OnTimeTaskService {
  @Autowired
  OnTimeDAO onTimeDao;

  @Override
  public List<OntimeTask> findAllTask(LoginUser loginUser) throws GenericBusinessException {
    return onTimeDao.findAll(OntimeTask.class);
  }

  @Override
  public boolean activeTask(LoginUser loginUser, long taskId) throws GenericBusinessException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean inactiveTask(LoginUser loginUser, long taskId) throws GenericBusinessException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<OntimeTask> findAllActiveTaskByGroup(LoginUser loginUser, String group)
      throws GenericBusinessException {
    List<OntimeTask> tasks = new ArrayList<OntimeTask>();
    if (group == null || group.length() <= 0) {
      return tasks;
    }
    String sql =
        "select t from OntimeTask t where t.dataSource = :pGroup and t.status ='1' order by t.priorityType ";
    tasks = onTimeDao.getSession().createQuery(sql).setParameter("pGroup", group).list();
    return tasks;
  }

  @Override
  public List<OntimeTask> findAllActiveTaskByGroupAndIP(LoginUser loginUser, String group,
      String ip) throws GenericBusinessException {
    List<OntimeTask> tasks = new ArrayList<OntimeTask>();
    if (group == null || group.length() <= 0) {
      return tasks;
    }
    // String sql = "select t from OntimeTask t where t.dataSource = :pGroup and t.runIp = :pIp and
    // t.status ='1' order by t.priorityType ";
    // tasks = getEm().createQuery(sql).setParameter("pGroup", group)
    // .setParameter("pIp", ip).getResultList();
    String sql = "select t from OntimeTask t where t.dataSource = '" + group + "' and t.runIp = '"
        + ip + "' and t.status ='1' order by t.priorityType ";
    tasks = onTimeDao.getSession().createQuery(sql).list();
    return tasks;
  }

  /**
   * 测试代码
   * 
   * @param loginUser
   * @param group
   * @return
   * @throws GenericBusinessException
   */
  public List<OntimeTask> findAllActiveTaskByGroup4Test(LoginUser loginUser, String group)
      throws GenericBusinessException {
    List<OntimeTask> tasks = new ArrayList<OntimeTask>();
    /*
     * OntimeTask task = new OntimeTask(); task.setId(1); task.setApplicationId(0L);
     * task.setCode("test"); task.setCronVal("0 0/2 * * * ?");// 第六位是'?'
     * task.setDataSource(TaskGroupTypeEnum.Platform.getCode());
     * task.setExceName("testOnTimeTask");// @service的名称
     * task.setExceType(TaskExceTypeEnum.SpringService.getCode()); task.setName("test");
     * tasks.add(task);
     * 
     * OntimeTask task2 = new OntimeTask(); task2.setId(2); task2.setApplicationId(0L);
     * task2.setCode("test2"); task2.setCronVal("0 0/1 * * * ?");// 第六位是'?'
     * task2.setDataSource(TaskGroupTypeEnum.Platform.getCode());
     * task2.setExceName("testOnTimeTask2");// @service的名称
     * task2.setExceType(TaskExceTypeEnum.SpringService.getCode()); task2.setName("test2");
     * tasks.add(task2);
     * 
     * OntimeTask taskSql = new OntimeTask(); taskSql.setId(3); taskSql.setApplicationId(0L);
     * taskSql.setCode("testSql"); taskSql.setCronVal("0 0/2 * * * ?");// 第六位是'?'
     * taskSql.setDataSource(TaskGroupTypeEnum.Platform.getCode()); taskSql.setExceName(
     * "insert into t_sys_operate_log(id,operate_type,module_id,module_name,application_id) values(seq_sys_operate_log_id.nextval,'1','1','ontimejob',3)"
     * );// sql语句 taskSql.setExceType(TaskExceTypeEnum.SQL.getCode()); taskSql.setName("testSql");
     * tasks.add(taskSql);
     */
    return tasks;
  }

  /**
   * 查询 所有
   * 
   * @param loginUser
   * @param taskId
   * @return
   * @throws GenericBusinessException
   */
  public PageBean<OntimeTask> findAllOntimeTask(LoginUser user) throws GenericBusinessException {
    PageBean<OntimeTask> pageBean = onTimeDao.findAll();
    return pageBean;
  }

  /**
   * 分页查询
   * 
   * @param loginUser
   * @param taskId
   * @return
   * @throws GenericBusinessException
   */
  public PageBean<OntimeTask> findOntimeTask(LoginUser user, OnTimeTaskQueryBean queryBean,
      String start, String limit) throws GenericBusinessException {
    PageBean pageBean = onTimeDao.findAllByCondition(queryBean, start, limit);
    return pageBean;
  }

  public OntimeTask findontimeTask(LoginUser user, Long ontimeTaskId)
      throws GenericBusinessException {
    OntimeTask ontimeTask = onTimeDao.get(OntimeTask.class, ontimeTaskId);

    return ontimeTask;
  }

  public OntimeTask saveOntimeTask(LoginUser user, OntimeTask ontimeTaskVO)
      throws GenericBusinessException {
    OntimeTask db = null;
    if (ontimeTaskVO.getId() <= 0) {
      // db = (OntimeTask) onTimeDao.save(user,ontimeTaskVO);
      Serializable id = onTimeDao.save(ontimeTaskVO);
      // ontimeTaskVO.setId(id);
      db = ontimeTaskVO;
    } else {
      db = onTimeDao.get(OntimeTask.class, ontimeTaskVO.getId());
      db.setCode(ontimeTaskVO.getCode());
      db.setCronVal(ontimeTaskVO.getCronVal());
      db.setDataSource(ontimeTaskVO.getDataSource());
      db.setExceName(ontimeTaskVO.getExceName());
      db.setExceType(ontimeTaskVO.getExceType());
      db.setName(ontimeTaskVO.getName());
      db.setRunIp(ontimeTaskVO.getRunIp());
      db.setStatus(ontimeTaskVO.getStatus());
      db.setRemark(ontimeTaskVO.getRemark());
      // onTimeDao.update(user, db);
      onTimeDao.update(db);
    }
    return db;
  }

  @SuppressWarnings("unchecked")
  @Override
  public OntimeTask getOntimeTaskByCode(String ontimeTaskCode) throws GenericBusinessException {
    String sql = "select f from OntimeTask f where f.code = :ontimeTaskCode";
    Query query =
        onTimeDao.getSession().createQuery(sql).setParameter("ontimeTaskCode", ontimeTaskCode);
    List<OntimeTask> ontimeTasks = query.list();
    if (ontimeTasks == null || ontimeTasks.isEmpty()) {
      return null;
    }
    return ontimeTasks.get(0);
  }

  @Override
  public boolean deleteByPK(LoginUser user, Class entityClass, long pkId)
      throws GenericBusinessException {
    onTimeDao.deleteById(entityClass, pkId);
    return true;
  }
}
