package com.digiwin.ecims.core.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;

/**
 * The persistent class for the aomsitem_t database table.
 * 
 */
@Entity
@Table(name = "log_info_t")
@NamedQuery(name = "LogInfoT.findAll", query = "SELECT a FROM LogInfoT a")
/*
 * 新增@DynamicUpdate(true)注解标记，如果没有发生变化的字段，在更新时，就不会进行update， 
 * 如果不加，就更新所有字段
 */
@DynamicUpdate(true)
public class LogInfoT implements Serializable, Cloneable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "log_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private BigInteger logId;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "call_method")
  private String callMethod;

  @Column(name = "business_type")
  private String businessType;

  @Column(name = "req_type")
  private String reqType;

  @Column(name = "req_param")
  private String reqParam;

  @Column(name = "res_size")
  private BigInteger resSize;

  @Column(name = "res_code")
  private String resCode;

  @Column(name = "res_msg")
  private String resMsg;

  @Column(name = "is_success")
  private Boolean isSuccess;

  @Column(name = "err_msg")
  private String errMsg;

  @Column(name = "req_time")
  private Date reqTime = new Date();

  @Column(name = "res_time")
  private Date resTime = new Date();

  @Column(name = "err_bill_type")
  private String errBillType;

  @Column(name = "err_bill_id")
  private String errBillId;

  @Column(name = "push_limits")
  private Integer pushLimits;

  @Column(name = "final_status")
  private String finalStatus;

  @Column(name = "remark")
  private String remark;

  /*
   * 此字段只针对数据拉取 新增字段商店类型，包括0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
   */
  @Column(name = "err_store_type")
  private String errStoreType;

  @Column(name = "store_id")
  private String errStoreId;

  public String getErrStoreId() {
    return errStoreId;
  }

  public void setErrStoreId(String errStoreId) {
    this.errStoreId = errStoreId;
  }

  public String getErrStoreType() {
    return errStoreType;
  }

  public void setErrStoreType(String errStoreType) {
    this.errStoreType = errStoreType;
  }

  /*
   * 新增字段单据状态，此字段只针对数据拉取
   */
  @Column(name = "err_bill_status")
  private String errBillStatus;


  public String getErrBillStatus() {
    return errBillStatus;
  }

  // 存储第三个联合主键
  @Column(name = "other_key")
  private String otherKey;


  public String getOtherKey() {
    return otherKey;
  }

  public void setOtherKey(String otherKey) {
    this.otherKey = otherKey;
  }

  public void setErrBillStatus(String errBillStatus) {
    this.errBillStatus = errBillStatus;
  }

  // 2015-07-18 add by sen.shen dgw05710
  @Transient
  private String reqTimeTrasient;

  @Transient
  private String resTimeTrasient;

  public LogInfoT() {

  }

  public LogInfoT(String ipAddress, String callMethod, String businessType, String reqType,
      String reqParam, Date reqTime) {
    this.ipAddress = ipAddress;
    this.callMethod = callMethod;
    this.businessType = businessType;
    this.reqType = reqType;
    this.reqParam = reqParam;
    this.reqTime = reqTime;
  }

  public LogInfoT(String ipAddress, String callMethod, String businessType, String reqType,
      String reqParam, Date reqTime, String storeId) {
    this.ipAddress = ipAddress;
    this.callMethod = callMethod;
    this.businessType = businessType;
    this.reqType = reqType;
    this.reqParam = reqParam;
    this.reqTime = reqTime;
    this.errStoreId = storeId;
  }

  public LogInfoT(String ipAddress, String callMethod, String businessType, String reqType,
      String reqParam, Date reqTime, String storeType, String storeId) {
    this.ipAddress = ipAddress;
    this.callMethod = callMethod;
    this.businessType = businessType;
    this.reqType = reqType;
    this.reqParam = reqParam;
    this.reqTime = reqTime;
    this.errStoreType = storeType;
    this.errStoreId = storeId;
  }

  /**
   * 此构造函数只针对数据拉取的日志记录
   * 
   * @param ipAddress 拉取地址
   * @param callMethod 调用方法
   * @param reqTime 请求时间
   * @param resErrMsg 错误信息
   * @param errMsg 异常信息
   * @param errBillId 单据id
   * @param errBillType 单据类型
   * @param errStoreType 电商编码
   * @param errBillStatus 单据状态
   * @param otherKey 第三个主键
   */
  public LogInfoT(String ipAddress, String callMethod, Date reqTime, String resErrMsg,
      String errMsg, String errBillId, String errBillType, String errStoreType,
      String errBillStatus, String otherKey, String errStoreId) {
    this.ipAddress = ipAddress;
    this.callMethod = callMethod;
    this.businessType = LogInfoBizTypeEnum.ECI_REQUEST.getValueString();
    this.reqType = "taskSchedule";
    this.errStoreType = errStoreType;
    this.errBillStatus = errBillStatus;
    this.resSize = BigInteger.valueOf(1);
    this.reqTime = reqTime;
    this.isSuccess = false;
    this.finalStatus = "0";
    this.resMsg = resErrMsg;
    this.resCode = "fail";
    this.errMsg = errMsg;
    this.pushLimits = 5;
    this.resTime = new Date();
    this.errBillId = errBillId;
    this.errBillType = errBillType;
    this.otherKey = otherKey;
    this.errStoreId = errStoreId;
  }

  public BigInteger getLogId() {
    return logId;
  }

  public void setLogId(BigInteger logId) {
    this.logId = logId;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public String getCallMethod() {
    return callMethod;
  }

  public void setCallMethod(String callMethod) {
    this.callMethod = callMethod;
  }

  public String getBusinessType() {
    return businessType;
  }

  public void setBusinessType(String businessType) {
    this.businessType = businessType;
  }

  public String getReqType() {
    return reqType;
  }

  public void setReqType(String reqType) {
    this.reqType = reqType;
  }

  public String getReqParam() {
    return reqParam;
  }

  public void setReqParam(String reqParam) {
    this.reqParam = reqParam;
  }

  public BigInteger getResSize() {
    return resSize;
  }

  public void setResSize(BigInteger resSize) {
    this.resSize = resSize;
  }

  public String getResCode() {
    return resCode;
  }

  public void setResCode(String resCode) {
    this.resCode = resCode;
  }

  public String getResMsg() {
    return resMsg;
  }

  public void setResMsg(String resMsg) {
    this.resMsg = resMsg;
  }

  public Boolean getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(Boolean isSuccess) {
    this.isSuccess = isSuccess;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public Date getReqTime() {
    return reqTime;
  }

  public void setReqTime(Date reqTime) {
    this.reqTime = reqTime;
  }

  public Date getResTime() {
    return resTime;
  }

  public void setResTime(Date resTime) {
    this.resTime = resTime;
  }

  public String getErrBillType() {
    return errBillType;
  }

  public void setErrBillType(String errBillType) {
    this.errBillType = errBillType;
  }

  public String getErrBillId() {
    return errBillId;
  }

  public void setErrBillId(String errBillId) {
    this.errBillId = errBillId;
  }

  public Integer getPushLimits() {
    return pushLimits;
  }

  public void setPushLimits(Integer pushLimits) {
    this.pushLimits = pushLimits;
  }

  public String getFinalStatus() {
    return finalStatus;
  }

  public void setFinalStatus(String finalStatus) {
    this.finalStatus = finalStatus;
  }

  public String getReqTimeTrasient() {
    return reqTimeTrasient;
  }

  public void setReqTimeTrasient(String reqTimeTrasient) {
    this.reqTimeTrasient = reqTimeTrasient;
  }

  public String getResTimeTrasient() {
    return resTimeTrasient;
  }

  public void setResTimeTrasient(String resTimeTrasient) {
    this.resTimeTrasient = resTimeTrasient;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }



}
