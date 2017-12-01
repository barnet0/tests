package com.digiwin.ecims.ontime.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.ontime.enumvar.TaskScheduleConfigRunTypeEnum;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.enums.WorkOperateTypeEnum;

/**
 * 定時任務
 * 
 * @author 06690 Ken Tu 2015/07/06
 */
@Service
public class TaskServiceImpl implements TaskService {

//  TaskService taskService;

  @Autowired
  private BaseDAO baseDAO;

  /**************************** 維護TaskScheduleConfig *****************************/
  @Override
  public void updateLastUpdateTime(String scheduleType, String lastUpdateTime) {

    String hql =
        "UPDATE TaskScheduleConfig SET lastUpdateTime = :lastUpdateTime WHERE scheduleType = :scheduleType";
    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("lastUpdateTime", lastUpdateTime);
    paramMap.put("scheduleType", scheduleType);
    baseDAO.updateHqlByParam(hql, paramMap);
  }

  @Override
  public void updateLastUpdateTime(String scheduleType, String startDate, String lastUpdateTime) {
    // 在同一秒
    if (startDate != null && lastUpdateTime != null && startDate.equals(lastUpdateTime)) {
//      Calendar calendar = Calendar.getInstance();
//      Date date = DateTimeTool.parse(lastUpdateTime);
//      calendar.setTime(date);
//      calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 1);
//      lastUpdateTime = DateTimeTool.format(calendar.getTime());
//      
      lastUpdateTime = DateTimeTool.timeAddOneSecond(lastUpdateTime);
    }

    String hql =
        "UPDATE TaskScheduleConfig SET lastUpdateTime = :lastUpdateTime WHERE scheduleType = :scheduleType";
    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("lastUpdateTime", lastUpdateTime);
    paramMap.put("scheduleType", scheduleType);
    baseDAO.updateHqlByParam(hql, paramMap);
  }

  @Override
  public String getLastUpdateTime(String scheduleType) {
    String sql = "SELECT lastUpdateTime FROM TaskScheduleConfig WHERE scheduleType = :scheduleType";
    Query query = baseDAO.getSession().createQuery(sql);

    query.setParameter("scheduleType", scheduleType);

    return (String) query.uniqueResult();
  }

  @Override
  public TaskScheduleConfig getTaskScheduleConfigInfo(String scheduleType) {

    TaskScheduleConfig taskScheduleConfig;
    String hqlString = "FROM TaskScheduleConfig WHERE scheduleType = :scheduleType";
    Query query = baseDAO.getSession().createQuery(hqlString);
    query.setParameter("scheduleType", scheduleType);
    Object obj = query.uniqueResult();
    if (obj == null) {
      String tString = scheduleType;
      if (tString.toLowerCase().contains("update")) { // add by mowj 20160229 添加对推送排程的处理
        // 更新排程默认设置
        taskScheduleConfig = new TaskScheduleConfig();
        taskScheduleConfig.setScheduleType(scheduleType);
        taskScheduleConfig.setMaxReadRow(1000);
        taskScheduleConfig.setMaxPageSize(20);
        taskScheduleConfig.setRunType(TaskScheduleConfigRunTypeEnum.PULL.getRunType());
      } else {
        // 推送排程默认设置
        taskScheduleConfig = new TaskScheduleConfig();
        taskScheduleConfig.setScheduleType(scheduleType);
        taskScheduleConfig.setMaxPushRow(20);
        taskScheduleConfig.setMaxReadRow(1000);
        taskScheduleConfig.setMaxRunnable(1);
        taskScheduleConfig.setRunType(TaskScheduleConfigRunTypeEnum.PUSH.getRunType());
      }
    } else {
      taskScheduleConfig = (TaskScheduleConfig) obj;
    }

    // 不應在一取出時, 就直接更新 lastRunTime, 要分開處理, edit by xavier on 20150830
    // taskService =
    // MySpringContext.getContext().getBean(TaskService.class);
    // taskService.newTransaction4SaveLastRunTime(taskScheduleConfig);
    return taskScheduleConfig;
  }

  @Override
  public void newTransaction4SaveLastRunTime(String scheduleType, Date lastRunTime) {
    String hql =
        "UPDATE TaskScheduleConfig SET lastRunTime = :lastRunTime WHERE scheduleType = :scheduleType";
    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("lastRunTime",
        DateTimeTool.format(lastRunTime == null ? new Date() : lastRunTime));
    paramMap.put("scheduleType", scheduleType);
    baseDAO.updateHqlByParam(hql, paramMap);
  }

  @Override
  public void newTransaction4SaveShopIdAndRunType(String scheduleType, String shopId,
      String runType) {
    String hql =
        "UPDATE TaskScheduleConfig SET shop_id = :shop_id, run_type = :run_type WHERE scheduleType = :scheduleType";
    HashMap<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("shop_id", shopId);
    paramMap.put("run_type", runType);
    paramMap.put("scheduleType", scheduleType);
    baseDAO.updateHqlByParam(hql, paramMap);
  }

  @Override
  public void saveTaskTaskScheduleConfig(TaskScheduleConfig taskScheduleConfig) {
    baseDAO.saveOrUpdate(taskScheduleConfig);
  }

  /**************************** 維護TaskScheduleConfig *****************************/


  /**************************** 維護TaskSchedulePostConfig *****************************/
  /*
   * 推送资料的查询。 由于单头与单身是一张表，所以在通过时间获取资料时，需要有防拆单处理。
   * 以订单来说，就是防止在通过时间查询订单时，订单在中台的时间正好被分割开，导致进行多次发货，产生额外的费用。
   * 
   * 防拆单处理的机制： 步骤1. 先通过时间与平台过滤出需要的单头ID，进行升序排序，再加上笔数的限制； 步骤2. 然后根据获取到的ID再去关联原表，取出整单
   * 
   * !!!!!机制修正： 排序需要放在第二步fixed by mowj 20150927 步骤1.先通过时间与平台过滤出需要的单头ID，再加上笔数的限制
   * 步骤2.然后根据获取到的ID再去关联原表，取出整单，进行升序排序
   * 
   * 其中，过滤出单头ID的过程又有以下步骤： 如果，一个订单有两个单身，在从电商平台同步到中台时， 一个出错无法存入，另一个正确可以存入，那正确的那个也是会存入数据库的。
   * 但是，在推送的时候，会有过滤。 如果在日志里存在这条订单的错误数据，那么，这条订单是 不推送的
   */

  // @Override
  // @Deprecated
  // public List<String> getSelectPojosIdNormally(HashMap<String, String> params) {
  // // v1.2
  // StringBuilder strBld = new StringBuilder();
  // // hibernate不支持没有配置过而在FROM后出现子查询的情况
  //// strBld.append(" FROM ");
  //// strBld.append(params.get("pojo"));
  //// strBld.append(" AS t1 JOIN ");
  //// strBld.append(" ( ");
  // // 步骤1. 先通过时间与平台过滤出需要的单头ID，并进行升序排序
  // strBld.append(" SELECT DISTINCT a.id AS id FROM ");
  // strBld.append(params.get("pojo"));
  // strBld.append(" a WHERE a.");
  // strBld.append(params.get("checkCol"));
  // strBld.append(" >= '");
  // strBld.append(params.get("startDate"));
  // strBld.append("' AND a.");
  // strBld.append(params.get("checkCol"));
  // strBld.append(" < '");
  // strBld.append(params.get("endDate"));
  // strBld.append("' AND a.storeType = '");
  // strBld.append(params.get("storeType"));
  // strBld.append("' ");
  // if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
  // strBld.append(" AND a.storeId='").append(params.get("storeId")).append("'");
  // }
  // // 过滤掉有错的，不完整的资料
  // strBld.append(" AND NOT EXISTS ");
  // strBld.append(" ( ");
  // strBld.append(" SELECT 1 FROM LogInfoT t ");
  // strBld.append(" WHERE 1=1 ");
  // strBld.append(" AND t.businessType = '" + LogInfoBizTypeEnum.ECI_REQUEST.getValueString() + "'
  // ");
  // strBld.append(" AND t.final_status = '0'");
  // strBld.append(" AND t.errBillType = '" + params.get("pojo") + "' ");
  // strBld.append(" AND t.errBillId = a.id ");
  // strBld.append(" ) ");
  // // 在IN中的id不会按照我们要的顺序排列，只会按照主键的顺序排列
  // // 这会造成推送的lastUpdateTime一直不会被更新
  // // 所以排序要写在最外层
  // // mark by mowj 20150927
  //// strBld.append(" ORDER BY a.");
  //// strBld.append(params.get("checkCol"));
  //// strBld.append(" ASC ");
  // // hibernate不支持LIMIT关键字
  //// strBld.append(" LIMIT 0, ");
  //// strBld.append(params.get("limit"));
  //// strBld.append(" ) AS t2 ");
  //// System.out.println(strBld.toString());
  //
  // Query query = baseDAO.getSession().createQuery(strBld.toString());
  // // 限制取回的笔数
  // query.setMaxResults(Integer.valueOf(params.get("limit")));
  // // 获取需要推送资料的ID列表（订单号、退单号，以及商品平台编号）
  // @SuppressWarnings("unchecked")
  // List<String> idList = query.list();
  //
  // return idList;
  // }
  //
  // @Override
  // @Deprecated
  // public <T> List<T> getSelectPojos(HashMap<String, String> params, List<String> idList, Class<T>
  // clazz) {
  // StringBuilder strBld = new StringBuilder();
  // // 按ID列表关联原表，取出数据
  // strBld.append("FROM ");
  // strBld.append(params.get("pojo"));
  // /* idList的长度受MySQL的参数：max_allowed_packet限制。
  // * 现行设定为32M.
  // * 以淘宝的订单推送HQL来说，一个订单号有16个字符，即16Byte。再加上订单号之间的逗号，总共需要
  // * 16*2000+1999+2=34001Byte
  // * 再加上FROM和WHERE等关键字，算它50个字符，所以最终需要
  // * 34001+50=34051Byte=33.25KByte，远远小于参数的限制。
  // * 所以实现的SQL不会有长度限制
  // */
  // strBld.append(" o WHERE o.id IN (:idList) ");
  // strBld.append(" ORDER BY o.");
  // strBld.append(params.get("checkCol"));
  // strBld.append(" ASC ");
  //
  // Query query = baseDAO.getSession().createQuery(strBld.toString());
  // query.setParameterList("idList", idList);
  //
  // @SuppressWarnings("unchecked")
  // List<T> list = query.list();
  //
  // strBld.delete(0, strBld.length());
  //
  // return list;
  // }

  @Override
  public <T> List<T> getSelectPojos(Map<String, String> params, Class<T> clazz,
      WorkOperateTypeEnum workOpEnum) throws NoSuchFieldException, SecurityException {
    // v1.0
    // StringBuilder strBld = new StringBuilder("from ");
    // strBld.append(params.get("pojo"));
    // strBld.append(" a where a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" >= '");
    // strBld.append(params.get("startDate"));
    // strBld.append("' and a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" < '");
    // strBld.append(params.get("endDate"));
    // strBld.append("' and a.storeType = '");
    // strBld.append(params.get("storeType"));
    // strBld.append("' ");
    // if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
    // strBld.append(" and a.storeId='").append(params.get("storeId")).append("'");
    // }
    //
    // strBld.append(" and not exists(select 1 from LogInfoT t where a.id = t.errBillId ");
    // strBld.append(" and t.errBillType = 'AomsordT'");
    // strBld.append(" and t.final_status = '0'");
    // strBld.append(" and t.businessType = 'ECI-REQUEST')");
    //
    // strBld.append(" order by a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" ASC");
    //
    // Query query = baseDAO.getSession().createQuery(strBld.toString());
    //
    // query.setMaxResults(Integer.valueOf(params.get("limit")));
    //
    // List<T> list = query.list();
    //
    // return list;

    // v1.1
    // StringBuilder strBld = new StringBuilder("select distinct a.id as id from ");
    // strBld.append(params.get("pojo"));
    // strBld.append(" a where a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" >= '");
    // strBld.append(params.get("startDate"));
    // strBld.append("' and a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" < '");
    // strBld.append(params.get("endDate"));
    // strBld.append("' and a.storeType = '");
    // strBld.append(params.get("storeType"));
    // strBld.append("' ");
    // if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
    // strBld.append(" and a.storeId='").append(params.get("storeId")).append("'");
    // }
    //
    // strBld.append(" and not exists(select 1 from LogInfoT t where a.id = t.errBillId ");
    // strBld.append(" and t.errBillType = 'AomsordT'");
    // strBld.append(" and t.final_status = '0'");
    // strBld.append(" and t.businessType = 'ECI-REQUEST')");
    //
    // strBld.append(" order by a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" ASC");
    //
    // System.out.println(strBld.toString());
    // Query query = baseDAO.getSession().createQuery(strBld.toString());
    //
    // query.setMaxResults(Integer.valueOf(params.get("limit")));
    //
    // List<String> idList = query.list();
    //
    // strBld = new StringBuilder("from ");
    // strBld.append(params.get("pojo"));
    //
    // strBld.append(" o where o.id in ('");
    // strBld.append(idList.get(0));
    // strBld.append("','");
    // strBld.append(idList.get(1));
    // strBld.append("','");
    // strBld.append(idList.get(2));
    // strBld.append("','");
    // strBld.append(idList.get(3));
    // strBld.append("','");
    // strBld.append(idList.get(4));
    // strBld.append("')");
    //
    // query = baseDAO.getSession().createQuery(strBld.toString());
    //
    // query.setMaxResults(Integer.valueOf(params.get("limit")));
    //
    // List<T> list = query.list();
    //
    // return list;

    // v1.2
    // StringBuilder strBld = new StringBuilder();
    // // hibernate不支持没有配置过而在FROM后出现子查询的情况
    //// strBld.append(" FROM ");
    //// strBld.append(params.get("pojo"));
    //// strBld.append(" AS t1 JOIN ");
    //// strBld.append(" ( ");
    // // 步骤1. 先通过时间与平台过滤出需要的单头ID，并进行升序排序
    // strBld.append(" SELECT DISTINCT a.id AS id FROM ");
    // strBld.append(params.get("pojo"));
    // strBld.append(" a WHERE a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" >= '");
    // strBld.append(params.get("startDate"));
    // strBld.append("' AND a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" < '");
    // strBld.append(params.get("endDate"));
    // strBld.append("' AND a.storeType = '");
    // strBld.append(params.get("storeType"));
    // strBld.append("' ");
    // if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
    // strBld.append(" AND a.storeId='").append(params.get("storeId")).append("'");
    // }
    // // 过滤掉有错的，不完整的资料
    // strBld.append(" AND NOT EXISTS ");
    // strBld.append(" ( ");
    // strBld.append(" SELECT 1 FROM LogInfoT t ");
    // strBld.append(" WHERE 1=1 ");
    // strBld.append(" AND t.businessType = '" + LogInfoBizTypeEnum.ECI_REQUEST.getValueString() +
    // "' ");
    // strBld.append(" AND t.final_status = '0'");
    // strBld.append(" AND t.errBillType = '" + params.get("pojo") + "' ");
    // strBld.append(" AND t.errBillId = a.id ");
    // strBld.append(" ) ");
    // // 在IN中的id不会按照我们要的顺序排列，只会按照主键的顺序排列
    // // 这会造成推送的lastUpdateTime一直不会被更新
    // // 所以排序要写在最外层
    // // mark by mowj 20150927
    //// strBld.append(" ORDER BY a.");
    //// strBld.append(params.get("checkCol"));
    //// strBld.append(" ASC ");
    // // hibernate不支持LIMIT关键字
    //// strBld.append(" LIMIT 0, ");
    //// strBld.append(params.get("limit"));
    //// strBld.append(" ) AS t2 ");
    //// System.out.println(strBld.toString());
    //
    // Query query = baseDAO.getSession().createQuery(strBld.toString());
    // // 限制取回的笔数
    // query.setMaxResults(Integer.valueOf(params.get("limit")));
    // // 获取需要推送资料的ID列表（订单号、退单号，以及商品平台编号）
    // @SuppressWarnings("unchecked")
    // List<String> idList = query.list();
    //
    // strBld.delete(0, strBld.length());
    // // 按ID列表关联原表，取出数据
    // strBld.append("FROM ");
    // strBld.append(params.get("pojo"));
    // /* idList的长度受MySQL的参数：max_allowed_packet限制。
    // * 现行设定为32M.
    // * 以淘宝的订单推送HQL来说，一个订单号有16个字符，即16Byte。再加上订单号之间的逗号，总共需要
    // * 16*2000+1999+2=34001Byte
    // * 再加上FROM和WHERE等关键字，算它50个字符，所以最终需要
    // * 34001+50=34051Byte=33.25KByte，远远小于参数的限制。
    // * 所以实现的SQL不会有长度限制
    // */
    // strBld.append(" o WHERE o.id IN (:idList) ");
    // strBld.append(" ORDER BY o.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" ASC ");
    //
    // query = baseDAO.getSession().createQuery(strBld.toString());
    // query.setParameterList("idList", idList);
    //
    // @SuppressWarnings("unchecked")
    // List<T> list = query.list();
    //
    // strBld.delete(0, strBld.length());
    //
    // return list;

    // add by mowj 20151105 start
    // List<String> idList = getSelectPojosIdNormally(params);
    // return getSelectPojos(params, idList, clazz);
    // add by mowj 20151105 end

    /*
     * 使用原生SQL有以下几点不方便 1. 无法动态绑定类和字段名，需要预先处理。比如Model的类型和属性名需要预先判断； 2.
     * 返回值的List<T>中，T的类型为Array，无法转换成对应Model 3. 由于params用到的地方比较多，而且目前不是统一管理的，修改起来会比较麻烦
     * 所以考虑后，还是决定使用HQL来实现
     * 
     */
    /*
     * -- IN的写法 -- Duration / Fetch -- 1. 0.078 0.235 -- 2. 0.078 0.219 -- 3. 0.078 0.219 SELECT
     *
     * FROM mercuryecinf.aomsord_t AS `t1` WHERE t1.aomsord001 IN (SELECT t2.id FROM (SELECT
     * DISTINCT a.aomsord001 AS `id` FROM mercuryecinf.aomsord_t a WHERE a.aomsordmoddt >=
     * '2015-09-05 05:30:00' AND a.aomsordmoddt < '2015-09-16 17:12:14.332' AND a.aomsord057 = '0'
     * AND NOT EXISTS( SELECT 1 FROM mercuryecinf.log_info_t t WHERE 1 = 1 AND t.business_type =
     * 'ECI-REQUEST' AND t.final_status = '0' AND t.err_bill_type = 'AomsordT' AND t.err_bill_id =
     * a.aomsord001) ORDER BY a.aomsordmoddt ASC -- 这个排序一定要,否则InnoDB会按主键去排序,导致时间上有间隔,遗漏订单 LIMIT 0 ,
     * 2000) AS `t2`) ORDER BY t1.aomsordmoddt ASC;
     */

    /*
     * -- 多表查询的写法(使用逗号) -- Duration / Fetch -- 1. 0.047 0.234 -- 2. 0.063 0.250 -- 3. 0.062 0.219
     * SELECT
     *
     * FROM mercuryecinf.aomsord_t AS `t1`, (SELECT DISTINCT a.aomsord001 AS `id` FROM
     * mercuryecinf.aomsord_t a WHERE a.aomsordmoddt >= '2015-09-05 05:30:00' AND a.aomsordmoddt <
     * '2015-09-16 17:12:14.332' AND a.aomsord057 = '0' AND NOT EXISTS( SELECT 1 FROM
     * mercuryecinf.log_info_t t WHERE 1 = 1 AND t.business_type = 'ECI-REQUEST' AND t.final_status
     * = '0' AND t.err_bill_type = 'AomsordT' AND t.err_bill_id = a.aomsord001) ORDER BY
     * a.aomsordmoddt ASC -- 这个排序一定要,否则InnoDB会按主键去排序,导致时间上有间隔,遗漏订单 LIMIT 0 , 2000) AS `t2` WHERE
     * t1.aomsord001 = t2.id; ORDER BY t1.aomsordmoddt ASC
     */

    /*
     * -- 多表查询的写法(使用JOIN关键字)，结果与使用逗号的写法相同 -- Duration / Fetch -- 1. 0.062 0.219 -- 2. 0.063 0.219 --
     * 3. 0.062 0.219 SELECT
     *
     * FROM mercuryecinf.aomsord_t AS `t1` JOIN (SELECT DISTINCT a.aomsord001 AS `id` FROM
     * mercuryecinf.aomsord_t a WHERE a.aomsordmoddt >= '2015-09-05 05:30:00' AND a.aomsordmoddt <
     * '2015-09-16 17:12:14.332' AND a.aomsord057 = '0' AND NOT EXISTS( SELECT 1 FROM
     * mercuryecinf.log_info_t t WHERE 1 = 1 AND t.business_type = 'ECI-REQUEST' AND t.final_status
     * = '0' AND t.err_bill_type = 'AomsordT' AND t.err_bill_id = a.aomsord001) ORDER BY
     * a.aomsordmoddt ASC -- 这个排序一定要,否则InnoDB会按主键去排序,导致时间上有间隔,遗漏订单 LIMIT 0 , 2000) AS `t2` ON
     * t1.aomsord001 = t2.id ORDER BY t1.aomsordmoddt ASC;
     */

    /*
     * -- EXISTS的写法 -- Duration / Fetch 返回的笔数没有被LIMIT限制！不能使用
     * 
     * SELECT
     *
     * FROM mercuryecinf.aomsord_t AS `t1` WHERE EXISTS( SELECT DISTINCT a.aomsord001 AS `id` FROM
     * mercuryecinf.aomsord_t a WHERE 1 = 1 AND t1.aomsord001 = a.aomsord001 AND a.aomsordmoddt >=
     * '2015-09-05 05:30:00' AND a.aomsordmoddt < '2015-09-16 17:12:14.332' AND a.aomsord057 = '0'
     * AND NOT EXISTS( SELECT 1 FROM mercuryecinf.log_info_t t WHERE 1 = 1 AND t.business_type =
     * 'ECI-REQUEST' AND t.final_status = '0' AND t.err_bill_type = 'AomsordT' AND t.err_bill_id =
     * a.aomsord001) ORDER BY a.aomsordmoddt ASC LIMIT 0 , 2000);
     */

    // v1.3
    // add by mowj 20151106
    // 借助反射准备业务表的表名和字段名称
    String dataTable = clazz.getAnnotation(Table.class).name();
    String idColName = clazz.getDeclaredField("id").getAnnotation(Column.class).name();
    String checkColName =
        clazz.getDeclaredField(params.get("checkCol")).getAnnotation(Column.class).name();
    String storeTypeColName =
        clazz.getDeclaredField("storeType").getAnnotation(Column.class).name();
    String storeIdColName = clazz.getDeclaredField("storeId").getAnnotation(Column.class).name();
    // 借助反射准备日志表的表名和字段名称
    Class<LogInfoT> loginfoClazz = LogInfoT.class;
    String logTable = loginfoClazz.getAnnotation(Table.class).name();
    String logBizTypeColName =
        loginfoClazz.getDeclaredField("businessType").getAnnotation(Column.class).name();
    String logFinalStatusColName =
        loginfoClazz.getDeclaredField("finalStatus").getAnnotation(Column.class).name();
    String logErrBillType =
        loginfoClazz.getDeclaredField("errBillType").getAnnotation(Column.class).name();
    String logErrBillId =
        loginfoClazz.getDeclaredField("errBillId").getAnnotation(Column.class).name();

    StringBuilder strBld = new StringBuilder();
    strBld.append(" SELECT * ");
    strBld.append(" FROM ");
    strBld.append(dataTable);
    strBld.append(" t1 JOIN ");
    strBld.append("  ( ");
    strBld.append("   SELECT DISTINCT ");
    strBld.append("    a.").append(idColName).append(" ID ");
    strBld.append("   FROM ").append(dataTable).append(" a ");
    strBld.append("   WHERE 1=1 ");
    strBld.append("    AND a.").append(checkColName).append(" >= :startDate");
    strBld.append("    AND a.").append(checkColName).append(" < :endDate");
    strBld.append("    AND a.").append(storeTypeColName).append(" = :storeType");
    if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
      strBld.append("    AND a.").append(storeIdColName).append(" ='").append(params.get("storeId"))
          .append("' ");
    }
    // 过滤掉有错的，不完整的资料
    if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
      strBld.append("    AND NOT EXISTS ");
      strBld.append("    ( ");
      strBld.append("     SELECT 1 ");
      strBld.append("     FROM ").append(logTable).append(" t ");
      strBld.append("     WHERE 1=1 ");

      strBld.append("      AND t.").append(logBizTypeColName).append(" = :businessType");
      strBld.append("      AND t.").append(logFinalStatusColName).append(" = '0'");
      strBld.append("      AND t.").append(logErrBillType).append(" = :errBillType");
      strBld.append("      AND t.").append(logErrBillId).append(" = a.").append(idColName);
      strBld.append("    ) ");
    }
    strBld.append(" ORDER BY a.").append(checkColName).append(" ASC ");
    strBld.append(" LIMIT 0, ").append(params.get("limit"));
    strBld.append(" ) t2 ");
    strBld.append(" WHERE t1.").append(idColName).append(" = t2.ID");
    strBld.append("     ORDER BY ").append("t1.").append(checkColName).append(" ASC ");

    SQLQuery query = baseDAO.getSession().createSQLQuery(strBld.toString());
    query.addEntity(clazz);
    query.setString("startDate", params.get("startDate"));
    query.setString("endDate", params.get("endDate"));
    query.setString("storeType", params.get("storeType"));
    if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
      query.setString("businessType", LogInfoBizTypeEnum.ECI_REQUEST.getValueString());
      query.setString("errBillType", params.get("pojo"));
    }

    @SuppressWarnings("unchecked")
    List<T> list = query.list();

    return list;
  }

  // @Override
  // @Deprecated
  // public List<String> getSelectPojosIdWhenDataMoreThanSettingInOneSecond(
  // HashMap<String, String> params) {
  // StringBuilder strBld = new StringBuilder();
  // strBld.append("SELECT DISTINCT a.id FROM ");
  // strBld.append(params.get("pojo"));
  // strBld.append(" a WHERE a.");
  // strBld.append(params.get("checkCol"));
  // strBld.append(" ='");
  // strBld.append(params.get("startDate"));
  // strBld.append("' AND a.storeType='");
  // strBld.append(params.get("storeType"));
  // strBld.append("' AND a.id > '");
  // strBld.append(params.get("id"));
  // strBld.append("' ");
  // if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
  // strBld.append(" AND a.storeId='").append(params.get("storeId")).append("' ");
  // }
  // // 过滤掉有错的，不完整的资料
  // strBld.append(" AND NOT EXISTS ");
  // strBld.append(" ( ");
  // strBld.append(" SELECT 1 FROM LogInfoT t ");
  // strBld.append(" WHERE 1=1 ");
  // strBld.append(" AND t.businessType = '" + LogInfoBizTypeEnum.ECI_REQUEST.getValueString() + "'
  // ");
  // strBld.append(" AND t.final_status = '0'");
  // strBld.append(" AND t.errBillType = '" + params.get("pojo") + "' ");
  // strBld.append(" AND t.errBillId = a.id ");
  // strBld.append(" ) ");
  //
  // Query query = baseDAO.getSession().createQuery(strBld.toString());
  // // 限制取回的笔数
  // query.setMaxResults(Integer.valueOf(params.get("limit")));
  //
  // @SuppressWarnings("unchecked")
  // List<String> list = query.list();
  //
  // return list;
  // }
  //
  // @Override
  // @Deprecated
  // public <T> List<T> getSelectPojosById(HashMap<String, String> params,
  // List<String> idList, Class<T> clazz) {
  // StringBuilder strBld = new StringBuilder();
  // // 按ID列表关联原表，取出数据
  // strBld.append("FROM ");
  // strBld.append(params.get("pojo"));
  // /* idList的长度受MySQL的参数：max_allowed_packet限制。
  // * 现行设定为32M.
  // * 以淘宝的订单推送HQL来说，一个订单号有16个字符，即16Byte。再加上订单号之间的逗号，总共需要
  // * 16*2000+1999+2=34001Byte
  // * 再加上FROM和WHERE等关键字，算它50个字符，所以最终需要
  // * 34001+50=34051Byte=33.25KByte，远远小于参数的限制。
  // * 所以实现的SQL不会有长度限制
  // */
  // strBld.append(" o WHERE o.id IN (:idList) ");
  // strBld.append(" ORDER BY o.");
  // strBld.append(params.get("checkCol"));
  // strBld.append(" ASC ");
  //
  // Query query = baseDAO.getSession().createQuery(strBld.toString());
  // query.setParameterList("idList", idList);
  //
  // @SuppressWarnings("unchecked")
  // List<T> list = query.list();
  //
  // strBld.delete(0, strBld.length());
  //
  // return list;
  // }

  @Override
  public <T> List<T> getSelectPojosById(Map<String, String> params, Class<T> clazz,
      WorkOperateTypeEnum workOpEnum) throws NoSuchFieldException, SecurityException {
    // mark by mowj 20151010 start
    // StringBuilder strBld = new StringBuilder("FROM ");
    // strBld.append(params.get("pojo"));
    // strBld.append(" a WHERE a.");
    // strBld.append(params.get("checkCol"));
    // strBld.append(" ='");
    // strBld.append(params.get("startDate"));
    // strBld.append("' AND a.storeType='");
    // strBld.append(params.get("storeType"));
    // strBld.append("' AND a.id > '");
    // strBld.append(params.get("id"));
    // strBld.append("' ");
    // if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
    // strBld.append(" AND a.storeId='").append(params.get("storeId")).append("' ");
    // }
    // strBld.append("ORDER BY a.id");
    //
    // Query query = baseDAO.getSession().createQuery(strBld.toString());
    //
    // query.setMaxResults(Integer.valueOf(params.get("limit")));
    //
    // List<T> list = query.list();
    //
    // return list;
    // mark by mowj 20151010 end

    // add by mowj 20151010 start
    // List<String> idList = getSelectPojosIdWhenDataMoreThanSettingInOneSecond(params);
    // return getSelectPojosById(params, idList, clazz);
    // add by mowj 20151010 end


    // v1.3
    // add by mowj 20151106
    // 借助反射准备业务表的表名和字段名称
    String dataTable = clazz.getAnnotation(Table.class).name();
    String idColName = clazz.getDeclaredField("id").getAnnotation(Column.class).name();
    String checkColName =
        clazz.getDeclaredField(params.get("checkCol")).getAnnotation(Column.class).name();
    String storeTypeColName =
        clazz.getDeclaredField("storeType").getAnnotation(Column.class).name();
    String storeIdColName = clazz.getDeclaredField("storeId").getAnnotation(Column.class).name();
    // 借助反射准备日志表的表名和字段名称
    Class<LogInfoT> loginfoClazz = LogInfoT.class;
    String logTable = loginfoClazz.getAnnotation(Table.class).name();
    String logBizTypeColName =
        loginfoClazz.getDeclaredField("businessType").getAnnotation(Column.class).name();
    String logFinalStatusColName =
        loginfoClazz.getDeclaredField("finalStatus").getAnnotation(Column.class).name();
    String logErrBillType =
        loginfoClazz.getDeclaredField("errBillType").getAnnotation(Column.class).name();
    String logErrBillId =
        loginfoClazz.getDeclaredField("errBillId").getAnnotation(Column.class).name();

    StringBuilder strBld = new StringBuilder();
    strBld.append(" SELECT * ");
    strBld.append(" FROM ");
    strBld.append(dataTable);
    strBld.append(" t1 JOIN ");
    strBld.append("  ( ");
    strBld.append("   SELECT DISTINCT ");
    strBld.append("    a.").append(idColName).append(" ID ");
    strBld.append("   FROM ").append(dataTable).append(" a ");
    strBld.append("   WHERE 1=1 ");
    strBld.append("    AND a.").append(checkColName).append(" = :startDate");
    strBld.append("    AND a.").append(storeTypeColName).append(" = :storeType");
    // 这里一定要加上ID
    strBld.append("    AND a.").append(idColName).append(" > :id ");
    if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
      strBld.append("    AND a.").append(storeIdColName).append(" ='").append(params.get("storeId"))
          .append("' ");
    }
    // 过滤掉有错的，不完整的资料
    if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
      strBld.append("    AND NOT EXISTS ");
      strBld.append("    ( ");
      strBld.append("     SELECT 1 ");
      strBld.append("     FROM ").append(logTable).append(" t ");
      strBld.append("     WHERE 1=1 ");

      strBld.append("      AND t.").append(logBizTypeColName).append(" = :businessType");
      strBld.append("      AND t.").append(logFinalStatusColName).append(" = '0'");
      strBld.append("      AND t.").append(logErrBillType).append(" = :errBillType");
      strBld.append("      AND t.").append(logErrBillId).append(" = a.").append(idColName);
      strBld.append("    ) ");
    }
    strBld.append(" ORDER BY a.").append(checkColName).append(" ASC ");
    strBld.append(" LIMIT 0, ").append(params.get("limit"));
    strBld.append(" ) t2 ");
    strBld.append(" WHERE t1.").append(idColName).append(" = t2.ID");
    strBld.append("     ORDER BY ").append("t1.").append(checkColName).append(" ASC ");

    SQLQuery query = baseDAO.getSession().createSQLQuery(strBld.toString());
    query.addEntity(clazz);
    query.setString("startDate", params.get("startDate"));
    query.setString("storeType", params.get("storeType"));
    query.setString("id", params.get("id"));
    if (WorkOperateTypeEnum.IS_SCHEDULE == workOpEnum) {
      query.setString("businessType", LogInfoBizTypeEnum.ECI_REQUEST.getValueString());
      query.setString("errBillType", params.get("pojo"));
    }

    @SuppressWarnings("unchecked")
    List<T> list = query.list();

    return list;
  }


  @Override
  public <T> List<T> getSelectPojoById(Map<String, String> params, Class<T> clazz) {
    // String hql = "FROM " + params.get("pojo") + " a WHERE a.id = '" + params.get("id") + "' AND
    // a.storeId='"
    // + params.get("storeId") + "'"; // mark by mowj 20151022

    // add by mowj 20151022
    StringBuilder hqlSb = new StringBuilder();
    hqlSb.append("FROM ");
    hqlSb.append(params.get("pojo")).append(" a ");
    hqlSb.append(" WHERE a.id = :id");
    if (params.get("storeid") != null) {
      hqlSb.append(" AND a.storeId='").append(params.get("storeId")).append("' ");
    }

    Query query = baseDAO.getSession().createQuery(hqlSb.toString());
    query.setParameter("id", params.get("id"));

    @SuppressWarnings("unchecked")
    List<T> list = query.list();

    return list;
  }

  @Override
  public long getSelectPojoCount(Map<String, String> params) {
    StringBuilder strBld = new StringBuilder("SELECT COUNT(DISTINCT a.id) AS count FROM ");
    strBld.append(params.get("pojo"));
    strBld.append("  a WHERE a.");
    strBld.append(params.get("checkCol"));
    strBld.append(" >= '");
    strBld.append(params.get("startDate"));
    strBld.append("' AND a.");
    strBld.append(params.get("checkCol"));
    strBld.append(" < '");
    strBld.append(params.get("endDate"));
    strBld.append("' AND a.storeType = '");
    strBld.append(params.get("storeType"));
    strBld.append("' ");
    if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
      strBld.append(" AND a.storeId='").append(params.get("storeId")).append("'");
    }
    Query query = baseDAO.getSession().createQuery(strBld.toString());

    return (long) query.uniqueResult();
  }

  @Override
  public long getSelectPojoCountById(Map<String, String> params) {
    StringBuilder strBld = new StringBuilder("SELECT COUNT(DISTINCT a.id) AS count FROM ");
    strBld.append(params.get("pojo"));
    strBld.append("  a WHERE a.");
    strBld.append(params.get("checkCol"));
    strBld.append(" >= '");
    strBld.append(params.get("startDate"));
    strBld.append("' AND a.");
    strBld.append(params.get("checkCol"));
    strBld.append(" < '");
    strBld.append(params.get("endDate"));
    strBld.append("' AND a.storeType = '");
    strBld.append(params.get("storeType"));
    strBld.append("' AND a.id > '");
    strBld.append(params.get("id"));
    strBld.append("' ");
    if (params.get("storeId") != null && !"".equals(params.get("storeId"))) {
      strBld.append(" AND a.storeId='").append(params.get("storeId")).append("'");
    }

    Query query = baseDAO.getSession().createQuery(strBld.toString());

    // return (int) query.uniqueResult(); // mark by mowj 20151010
    return (long) query.uniqueResult(); // add by mowj 20151010
  }

  /**************************** post資料給水星 *****************************/

  @Override
  public <T> String newTransaction4Save(Date modiDate, List<T> objList) {
    baseDAO.clear();
    String modifiedDateTime = DateTimeTool.format(modiDate);
    baseDAO.saveOrUpdateByCollectionWithLog(objList);
    return modifiedDateTime;
  }

  @Override
  public <T> void newTransaction4Save(List<T> objList) {
    baseDAO.clear();
    baseDAO.saveOrUpdateByCollectionWithLog(objList);
  }

  @Override
  public <T> void newTransaction4Save(List<T> objList, boolean isClean) {
    if (isClean) {
      this.baseDAO.getSession().clear();
    }

    baseDAO.saveOrUpdateByCollectionWithLog(objList);
  }

  /**
   * 按hql查询
   * 
   * @param hql
   * @return
   */
  @Override
  public <T> List<T> executeQueryByHql(String hql) {
    return baseDAO.executeQueryByHql(hql);
  }
}
