package com.digiwin.ecims.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.system.service.ReLogProcessService;

/**
 * 用於Query驗證與業務邏輯 最後返回QueryRes給Controller
 * 
 * @author Sen.Shen
 *
 */
@Service
public class ReLogProcessServiceImpl implements ReLogProcessService {

  private static final Logger logger = LoggerFactory.getLogger(ReLogProcessServiceImpl.class);

  @Autowired
  BaseDAO baseDao;// 使用Hibernate


  @Override
  public List<Object> getReAomsData(String tableName) {
    StringBuffer sbfAomsSQL = new StringBuffer();

    sbfAomsSQL.append("select t from " + tableName + " t where t.id in");
    sbfAomsSQL.append("(select logT.errBillId from LogInfoT logT ");
    sbfAomsSQL.append("where logT.errBillType='" + tableName
        + "' and logT.businessType='ECI-PUSH' and logT.finalStatus='0' and logT.pushLimits>0)");
    return baseDao.executeQueryByHql(sbfAomsSQL.toString());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.mercuryecinf.system.service.ReLogProcessService#getReAomsData(java.lang.String,
   * java.lang.Class)
   */
  @Override
  public <T> List<T> getReAomsData(String pojoName, Class<T> clazz) {
    StringBuffer sbfAomsSQL = new StringBuffer();

    sbfAomsSQL.append("select t from " + pojoName + " t where t.id in");
    sbfAomsSQL.append("(select logT.errBillId from LogInfoT logT ");
    sbfAomsSQL.append("where logT.errBillType='" + pojoName
        + "' and logT.businessType='ECI-PUSH' and logT.finalStatus='0' and logT.pushLimits>0)");

    return baseDao.executeQueryByHql(sbfAomsSQL.toString());
  }



  @Override
  public TaskScheduleConfig getTaskScheduleConfigInfo(String scheduleType) {
    Criterion criterion = Restrictions.eq("scheduleType", scheduleType);

    Criteria criteria =
        baseDao.createCriteria(TaskScheduleConfig.class, baseDao.getSession(), criterion);

    return (TaskScheduleConfig) criteria.list().get(0);
  }

  @Override
  public Map<String, Object> getInvoiceIdMappingLogId(String tableName) {
    StringBuffer sbfAomsSQL = new StringBuffer();

    sbfAomsSQL.append("select logT from LogInfoT logT ");
    sbfAomsSQL.append("where logT.errBillType='" + tableName
        + "' and logT.businessType='ECI-PUSH' and logT.finalStatus='0' and logT.pushLimits>0");

    List<Object> objListArr = baseDao.executeQueryByHql(sbfAomsSQL.toString());

    Map<String, Object> invoiceIdMappingLogIdMap = new HashMap<String, Object>();
    for (Object obj : objListArr) {
      invoiceIdMappingLogIdMap.put(((LogInfoT) obj).getErrBillId().toString(), obj);
    }

    return invoiceIdMappingLogIdMap;

  }

  @Override
  public int deleteLogByHql(String hql) {
    return baseDao.deleteByHQL(hql);
  }

  @Override
  public int deleteLogBySql(String sql) {
    return baseDao.deleteBySQL(sql);
  }
}
