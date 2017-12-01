package com.digiwin.ecims.core.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.core.model.SourceLogT;
import com.digiwin.ecims.system.bean.ParamBean;

public interface LoginfoOperateService {

  /**
   * 保存log
   * 
   * @return
   */
  public boolean saveLog(HttpServletRequest request, Exception ex) throws Exception;

  /**
   * 新增事务保存日志 独立于其他事务提交
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
      Date lastUpdateTime, String resErrMsg, String errMsg, String billType, String billId);

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
      int pushLimits, Date reqTime, String errMsg, String errBillType, String errBillId);

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
  public void newTransaction4SaveLog(LogInfoT logInfoT);

  public boolean newTransaction4SaveLogByCollection(List<LogInfoT> logInfoList);

  public boolean newTransaction4UpdateLogByCollection(List<LogInfoT> logInfoList);

  boolean newTransaction4SaveOrUpdateLogByCollection(List<LogInfoT> logInfoList);

  /**
   * 将平台的原始数据存入到DB作为记录
   * 
   * @param startDate 请求平台资料的开始日期
   * @param endDate 请求平台资料的结束日期
   * @param storeType 平台类型（0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C: 卓越亚马逊 ）
   * @param method 调用的平台方法
   * @param source 平台原始数据
   */
  public void newTransaction4SaveSource(String startDate, String endDate, String storeType,
      String method, String source);

  /**
   * 将平台的原始数据存入到DB作为记录
   * 
   * @param startDate 请求平台资料的开始日期
   * @param endDate 请求平台资料的结束日期
   * @param storeType 平台类型（0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C: 卓越亚马逊 ）
   * @param method 调用的平台方法
   * @param source 平台原始数据
   * @param businessType 业务类型
   * @param shopId 電商編號
   * @author 维杰
   * @since 2015.08.25
   */
  public void newTransaction4SaveSource(String startDate, String endDate, String storeType,
      String method, String source, String businessType, String shopId, String scheduleType);

  /**
   * 将平台的原始数据存入到DB作为记录
   * 
   * @param startDate 请求平台资料的开始日期
   * @param endDate 请求平台资料的结束日期
   * @param storeType 平台类型（0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C: 卓越亚马逊 ）
   * @param method 调用的平台方法
   * @param response 平台原始数据类实例
   * @param businessType 业务类型
   * @param shopId 電商編號
   * @author 维杰
   * @since 2016.08.09
   */
  public void newTransaction4SaveSource(String startDate, String endDate, String storeType,
      String method, Object response, String businessType, String shopId, String scheduleType);
  
  /**
   * 将平台的原始数据存入到DB作为记录
   * 
   * @param sourceLog SourceLog实体
   */
  public void newTransaction4SaveSource(SourceLogT sourceLog);

  /**
   * 
   * @param sourceLogList
   * @return
   */
  public boolean newTransaction4SaveSourceByCollection(List<SourceLogT> sourceLogList);

  /**
   * 
   * @param sourceLogList
   * @return
   */
  public boolean newTransaction4UpdateSourceByCollection(List<SourceLogT> sourceLogList);

  /**
   * logSave for sys-param
   * 
   * @param bean
   * @param processStatus
   * @param resultMsg
   * @param e
   */
  public void saveSysParamActionLog(String ip, ParamBean bean, boolean processStatus,
      String resultMsg, Exception e);

  /**
   * logSave for manual-sync-push
   * 
   * @param bean
   * @param resultMsg
   * @param resTime
   * @throws Exception
   */
  public void saveManualSyncPushLog(String ip, ParamBean bean, String resultMsg, Date resTime)
      throws Exception;

  /**
   * CheckService運行紀錄
   * 
   * @param service
   * @param startDate
   * @param endDate
   * @param storeType
   * @param shopId
   * @param scheduleType
   * @return
   */
  public String newTransaction4SaveCheckServiceRecord(String service, String startDate,
      String endDate, Long results, String storeType, String shopId, String scheduleType);
  
  
  /**
   * 查询操作日志错误记录是否存在。
   * @param bizType 日志类型。目前支持ECI-PUSH与ECI-REQUEST
   * @param errBillType 错误日志记录的业务类型。有:AomsordT,AomsrefundT与AomsitemT
   * @param errBillId 错误日志记录的业务单号。
   * @return 如果有符合条件的，则返回对象；否则，返回NULL
   */
  public LogInfoT isLogInfoExist(String bizType, String errBillType, String errBillId);
}
