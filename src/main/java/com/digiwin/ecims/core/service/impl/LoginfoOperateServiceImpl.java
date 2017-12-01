package com.digiwin.ecims.core.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.CheckServiceRecordT;
import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.model.SourceLogT;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.ontime.service.CheckUpdateService;
import com.digiwin.ecims.system.bean.ParamBean;
import com.digiwin.ecims.system.bean.SyncResOrderHandBean;

/**
 * @author zaregoto
 *
 */
@Service
public class LoginfoOperateServiceImpl implements LoginfoOperateService {

  @Autowired
  BaseDAO baseDao;

  @Autowired
  private CheckUpdateService checkUpdateService;

  private static final Logger logger = LoggerFactory.getLogger(LoginfoOperateServiceImpl.class);

  /**
   * 保存log
   * 
   * @return
   * @throws Exception
   */
  public boolean saveLog(HttpServletRequest request, Exception ex) throws Exception {
    boolean saveStatus = false;
    if (ex != null) {
      List<Object> logInfos = (List<Object>) request.getAttribute("logObj");
      // add by mowj 20150811
      for (Object logInfoObject : logInfos) {
        if (logInfoObject instanceof LogInfoT) {
          LogInfoT logInfo = (LogInfoT) logInfoObject;
          logInfo.setResSize(BigInteger.valueOf(0));
          logInfo.setIsSuccess(false);
          logInfo.setErrMsg(ex.getMessage());
          logInfo.setResCode("999");
          logInfo.setResMsg(ex.getMessage());

          baseDao.save(logInfo);
        }
      }

      // baseDao.save(sysLogInfo(request,ex)); // mark by mowj 20150810
    } else {
      if (request.getAttribute("reqType").toString().equals("cmd")) {
        try {
          baseDao.saveByCollection(cmdLogInfoList(request));
          saveStatus = true;
        } catch (Exception e) {
          e.printStackTrace();
          throw e;
        }
      }
    }

    baseDao.flush();
    return saveStatus;
  }

  /**
   * 準備error時要存的logInfo物件
   * 
   * @param request
   * @param ex
   * @return
   */
  private Object sysLogInfo(HttpServletRequest request, Exception ex) {
    LogInfoT logInfo = (LogInfoT) request.getAttribute("logObj");
    logInfo.setResSize(BigInteger.valueOf(0));
    logInfo.setIsSuccess(false);
    logInfo.setErrMsg(ex.getMessage());
    logInfo.setResCode("999");
    logInfo.setResMsg(ex.getMessage());
    return logInfo;
  }

  /**
   * 準備cmd要存的logInfo list 物件
   * 
   * @param request
   * @return
   * @throws Exception
   */
  private List<Object> cmdLogInfoList(HttpServletRequest request) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    List<CmdRes> cmdResList = objectMapper.readValue(request.getAttribute("res").toString(),
        TypeFactory.defaultInstance().constructCollectionType(List.class, CmdRes.class));
    List<Object> objList = (List<Object>) request.getAttribute("logObj");
    for (int i = 0; i < objList.size(); i++) {
      ((LogInfoT) objList.get(i)).setResTime(new Date());
      ((LogInfoT) objList.get(i)).setResSize(BigInteger.valueOf(cmdResList.size()));
      ((LogInfoT) objList.get(i))
          .setIsSuccess(cmdResList.get(i) == null ? false : cmdResList.get(i).isSuccess());

      if (cmdResList.get(i).getError() != null) {
        ((LogInfoT) objList.get(i)).setResCode(cmdResList.get(i).getError().getCode());
        ((LogInfoT) objList.get(i)).setResMsg(cmdResList.get(i).getError().getMsg());
        ((LogInfoT) objList.get(i)).setFinalStatus("0");
      } else {
        ((LogInfoT) objList.get(i)).setFinalStatus("1");
      }
      ((LogInfoT) objList.get(i)).setRemark("api-" + cmdResList.get(i).getApi());
    }
    return objList;
  }

  /**
   * 新增事务保存日志 可根据自己需求重载
   * 
   * @param ip
   * @param methodName
   * @param bizType
   * @param lastUpdateTime
   * @param errMsg
   * @param billType
   * @param billId
   */

  public void newTransaction4SaveLog(String ip, String methodName, String bizType,
      Date lastUpdateTime, String resErrMsg, String errMsg, String billType, String billId) {
    LogInfoT logInfoT = new LogInfoT();
    logInfoT.setIpAddress(ip);
    logInfoT.setCallMethod(methodName);
    logInfoT.setBusinessType(bizType);
    logInfoT.setReqType("taskSchedule");
    logInfoT.setResSize(BigInteger.valueOf(1));
    logInfoT.setIsSuccess(false);
    logInfoT.setReqTime(lastUpdateTime);
    logInfoT.setResTime(new Date());
    logInfoT.setResCode("fail");
    logInfoT.setResMsg(resErrMsg);
    logInfoT.setErrMsg(errMsg);
    logInfoT.setPushLimits(5);
    logInfoT.setFinalStatus("0");
    logInfoT.setErrBillType(billType);
    logInfoT.setErrBillId(billId);
    baseDao.save(logInfoT);
  }

  /**
   * 新增事务保存日志 可根据自己需求重载 此方法一用16个字段
   * 
   * @param ip 请求程序的ip
   * @param methodName 调用方法或路径
   * @param bizType 业务类型：ECI-PUSH ec接口定时想oms推送数据，ECI-REQUEST,ec接口定时请求电商平台数据，OMS-REQUEST OMS请求数据
   * @param reqType taskSchedule 定时任务，query查询，cmd 命令
   * @param reqParam 请求参数
   * @param resSize 响应数据量
   * @param resTime 响应时间
   * @param resErrCode 响应错误代码
   * @param resErrMsg 响应错误代码对应的错误消息
   * @param errBillType 错误的单据类型
   * @param pushLimits 推送的数据最大数量
   * @param reqTime 请求时间
   * @param errMsg exception的错误信息
   * @param errBillType 错误的单据类型
   * @param errBillId 错误的单据id
   */
  public void newTransaction4SaveLog(String ip, String methodName, String bizType, String reqType,
      String reqParam, BigInteger resSize, Date resTime, String resErrCode, String resErrMsg,
      int pushLimits, Date reqTime, String errMsg, String errBillType, String errBillId) {
    LogInfoT logInfoT = new LogInfoT();
    logInfoT.setIpAddress(ip);
    logInfoT.setCallMethod(methodName);
    logInfoT.setBusinessType(bizType);
    logInfoT.setReqType(reqType);
    logInfoT.setReqParam(reqParam);
    logInfoT.setResSize(resSize);
    logInfoT.setIsSuccess(false);
    logInfoT.setReqTime(reqTime);
    logInfoT.setResTime(resTime);
    logInfoT.setResCode(resErrCode);
    logInfoT.setResMsg(resErrMsg);
    logInfoT.setErrMsg(errMsg);
    logInfoT.setPushLimits(pushLimits);
    logInfoT.setFinalStatus("0");
    logInfoT.setErrBillType(errBillType);
    logInfoT.setErrBillId(errBillId);
    baseDao.save(logInfoT);
  }

  /**
   * 新增事务保存日志 可根据自己需求重载 此方法一用16个字段
   * 
   * @param ip 请求程序的ip
   * @param methodName 调用方法或路径
   * @param bizType 业务类型：ECI-PUSH ec接口定时想oms推送数据，ECI-REQUEST,ec接口定时请求电商平台数据，OMS-REQUEST OMS请求数据
   * @param reqType taskSchedule 定时任务，query查询，cmd 命令
   * @param reqParam 请求参数
   * @param resSize 响应数据量
   * @param resTime 响应时间
   * @param resErrCode 响应错误代码
   * @param resErrMsg 响应错误代码对应的错误消息
   * @param errBillType 错误的单据类型
   * @param pushLimits 推送的数据最大数量
   * @param reqTime 请求时间
   * @param errMsg exception的错误信息
   * @param errBillType 错误的单据类型
   * @param errBillId 错误的单据id
   */
  public void newTransaction4SaveLog(LogInfoT logInfoT) {
    baseDao.save(logInfoT);
  }

  @Override
  public boolean newTransaction4UpdateLogByCollection(List<LogInfoT> logInfoList) {
    // TODO Auto-generated method stub
    return baseDao.updateByCollection(logInfoList);
  }

  @Override
  public boolean newTransaction4SaveLogByCollection(List<LogInfoT> logInfoList) {
    // TODO Auto-generated method stub
    return baseDao.saveByCollection(logInfoList);
  }

  @Override
  public boolean newTransaction4SaveOrUpdateLogByCollection(List<LogInfoT> logInfoList) {
    return baseDao.saveOrUpdateByCollection(logInfoList);
  }
  
  @Override
  public void newTransaction4SaveSource(String startDate, String endDate, String storeType,
      String method, String source) {
    // TODO Auto-generated method stub
    // mark by mowj 20150825
    // SourceLogT sourceLog = new SourceLogT();
    // sourceLog.setStartDate(startDate);
    // sourceLog.setEndDate(endDate);
    // sourceLog.setStoreType(storeType);
    // sourceLog.setMethod(method);
    // sourceLog.setSource(source);
    // try {
    // baseDao.save(sourceLog);
    // }catch(Exception e){
    // logger.error(e.getMessage());
    // }
    // mark by mowj 20150825

    // add by mowj 20150825
    newTransaction4SaveSource(startDate, endDate, storeType, method, source, "", "", "");

  }

  @Override
  public void newTransaction4SaveSource(SourceLogT sourceLog) {
    // TODO Auto-generated method stub
    try {
      // 查找是否有 mappingCode add by xavier on 20150909
      this.addMappingCode(sourceLog);
      baseDao.save(sourceLog);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }

  }

  @Override
  public boolean newTransaction4SaveSourceByCollection(List<SourceLogT> sourceLogList) {
    try {
      // 查找是否有 mappingCode add by xavier on 20150909
      this.addMappingCode(sourceLogList);
      return baseDao.saveByCollection(sourceLogList);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return false;
    }

  }

  @Override
  public boolean newTransaction4UpdateSourceByCollection(List<SourceLogT> sourceLogList) {
    try {
      // 查找是否有 mappingCode add by xavier on 20150909
      this.addMappingCode(sourceLogList);
      return baseDao.saveByCollection(sourceLogList);
    } catch (Exception e) {
      logger.error(e.getMessage());
      return false;
    }
  }

  @Override
  public void saveSysParamActionLog(String ip, ParamBean bean, boolean processStatus,
      String resultMsg, Exception e) {
    LogInfoT loginfoT = new LogInfoT(ip, bean.getCallMethod(), bean.getProcessType(), "sysparam",
        bean.getParam(), bean.getNowDate());

    if (processStatus) {// success
      loginfoT.setResSize(BigInteger.valueOf(1));
      loginfoT.setIsSuccess(Boolean.TRUE);
      loginfoT.setFinalStatus("1");
    } else {// faild
      loginfoT.setResSize(BigInteger.valueOf(0));
      loginfoT.setIsSuccess(Boolean.FALSE);
      loginfoT.setFinalStatus("0");
      loginfoT.setErrMsg(e.getMessage());
      loginfoT.setResCode("999");
      loginfoT.setResMsg(e.getMessage());
    }

    loginfoT.setResTime(new Date());
    loginfoT.setRemark(resultMsg);

    // newTransaction4SaveLog(loginfoT); // mark by mowj 20150901
    baseDao.save(loginfoT); // add by mowj 20150901
  }

  @Override
  public void saveManualSyncPushLog(String ip, ParamBean bean, String resultMsg, Date resTime)
      throws Exception {
    LogInfoT loginfoT = new LogInfoT();
    loginfoT.setIpAddress(ip);
    loginfoT.setCallMethod(bean.getCallMethod());
    loginfoT.setBusinessType(bean.getProcessType());
    loginfoT.setReqType("manual");
    loginfoT.setReqParam(JsonUtil.format(bean));
    loginfoT.setReqTime(bean.getNowDate());
    loginfoT.setResTime(resTime);
    loginfoT.setRemark(resultMsg);

    SyncResOrderHandBean returnBean =
        (SyncResOrderHandBean) JsonUtil.jsonToObject(resultMsg, SyncResOrderHandBean.class);

    loginfoT.setResSize(BigInteger.valueOf(Long.valueOf(returnBean.getResultSize())));

    if (returnBean.getIsSuccess().equals("0")) {// success
      loginfoT.setIsSuccess(Boolean.TRUE);
      loginfoT.setFinalStatus("1");
    } else {// faild
      loginfoT.setIsSuccess(Boolean.FALSE);
      loginfoT.setFinalStatus("0");
      loginfoT.setResCode(returnBean.getErrCode());
      loginfoT.setResMsg(returnBean.getErrMsg());
    }

    newTransaction4SaveLog(loginfoT);
  }

  @Override
  public String newTransaction4SaveCheckServiceRecord(String service, String startDate,
      String endDate, Long results, String storeType, String storeId, String scheduleType) {
    String mappingCode = service + "-" + startDate + "-" + endDate;
    CheckServiceRecordT checkServiceRecordT = new CheckServiceRecordT();
    checkServiceRecordT.setService(service);
    checkServiceRecordT.setStartDate(startDate);
    checkServiceRecordT.setResults(results);
    checkServiceRecordT.setEndDate(endDate);
    checkServiceRecordT.setStoreType(storeType);
    checkServiceRecordT.setStoreId(storeId);
    checkServiceRecordT.setScheduleType(scheduleType);
    checkServiceRecordT.setMappingCode(mappingCode);
    try {
      baseDao.save(checkServiceRecordT);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return mappingCode;
  }

  @Override
  public void newTransaction4SaveSource(String startDate, String endDate, String storeType,
      String method, String source, String businessType, String shopId, String scheduleType) {
    // TODO Auto-generated method stub
    SourceLogT sourceLog = new SourceLogT();
    sourceLog.setStartDate(startDate);
    sourceLog.setEndDate(endDate);
    sourceLog.setStoreType(storeType);
    sourceLog.setMethod(method);
    sourceLog.setSource(source);
    sourceLog.setBusinessType(businessType);
    sourceLog.setShopId(shopId);
    sourceLog.setScheduleType(scheduleType);
    try {
      // 查找是否有 mappingCode add by xavier on 20150909
      this.addMappingCode(sourceLog);
      baseDao.save(sourceLog);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.core.service.LoginfoOperateService#newTransaction4SaveSource(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Object, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public void newTransaction4SaveSource(String startDate, String endDate, String storeType,
      String method, Object response, String businessType, String shopId, String scheduleType) {
    this.newTransaction4SaveSource(startDate, endDate, storeType, method, JsonUtil.format(response), businessType, shopId, scheduleType);
  }

  @Override
  public LogInfoT isLogInfoExist(String bizType, String errBillType, String errBillId) {
    StringBuilder strBld = 
        new StringBuilder();
    strBld.append("FROM LogInfoT l WHERE l.businessType=:bizType ");
    strBld.append(" AND ").append("errBillType=:errBillType ");
    strBld.append(" AND ").append("errBillId=:errBillId ");
    
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("bizType", bizType);
    paramMap.put("errBillType", errBillType);
    paramMap.put("errBillId", errBillId);
    
    LogInfoT logInfoT = null;
    List queryResult = baseDao.queryHqlByParam(strBld.toString(), paramMap);
    if (!queryResult.isEmpty()) {
      logInfoT = (LogInfoT)queryResult.get(0);
    }
    
    return logInfoT;
  }

  /**
   * 查找是否有 mappingCode
   * 
   * @param data
   * @author Xavier
   */
  private void addMappingCode(List<SourceLogT> data) throws Exception {
    for (SourceLogT row : data) {
      this.addMappingCode(row);
    }
  }

  /**
   * 查找是否有 mappingCode
   * 
   * @param sourceLog
   * @author Xavier
   */
  private void addMappingCode(SourceLogT sourceLog) throws Exception {
    String mappingCode;
    try {
      mappingCode = this.checkUpdateService.get(sourceLog);
      if (StringUtils.isNotBlank(mappingCode)) {
        // FIXME 未完成...
        sourceLog.setMappingCode(mappingCode);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

}
