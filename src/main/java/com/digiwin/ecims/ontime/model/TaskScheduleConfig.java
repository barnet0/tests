package com.digiwin.ecims.ontime.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import com.digiwin.ecims.core.util.DateTimeTool;

/**
 * 
 * @author 06690 Ken Tu 2015/07/06
 */

@Entity
@Table(name = "task_schedule_config")
/*
 * 新增@DynamicUpdate(true)注解标记，如果没有发生变化的字段，在更新时，就不会进行update， 如果不加，就更新所有字段
 */
@DynamicUpdate(true)
public class TaskScheduleConfig {
  @Id
  @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  String id;
  @Column(name = "schedule_type")
  String scheduleType;
  @Column(name = "lastUpdateTime")
  String lastUpdateTime;
  @Column(name = "maxPushRow")
  Integer maxPushRow;
  @Column(name = "maxReadRow")
  Integer maxReadRow;
  @Column(name = "maxPageSize")
  Integer maxPageSize;
  @Column(name = "plant")
  String plant;
  @Column(name = "service")
  String service;
  @Column(name = "user")
  String user;
  @Column(name = "postUrl")
  String postUrl;
  @Column(name = "postIp")
  String postIp;
  @Column(name = "maxRunnable")
  Integer maxRunnable;
  @Column(name = "lastRunTime")
  String lastRunTime;

  // add by xavier on 20150827
  @Column(name = "shop_id")
  String shopId;
  @Column(name = "run_type")
  String runType;

  // add by mowj on 20160520
  @Column(name = "entId")
  String entId;
  @Column(name = "companyId")
  String companyId;
  
  // add by mowj 20160719
  @Column(name = "storeId")
  String storeId;
  
  @Transient
  String endDate;
  @Transient
  String sysDate;

  // 用來判斷, 是不是 reCycle schedule 用的 add by xavier on 20150829
  @Transient
  boolean isReCycle;

  public TaskScheduleConfig() {}

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getScheduleType() {
    return scheduleType;
  }

  public void setScheduleType(String scheduleType) {
    this.scheduleType = scheduleType;
  }

  public String getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public String getPlant() {
    return plant;
  }

  public void setPlant(String plant) {
    this.plant = plant;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPostUrl() {
    return postUrl;
  }

  public void setPostUrl(String postUrl) {
    this.postUrl = postUrl;
  }

  public String getPostIp() {
    return postIp;
  }

  public void setPostIp(String postIp) {
    this.postIp = postIp;
  }

  public Integer getMaxRunnable() {
    return maxRunnable;
  }

  public void setMaxRunnable(Integer maxRunnable) {
    this.maxRunnable = maxRunnable;
  }

  public Integer getMaxPushRow() {
    return maxPushRow;
  }

  public void setMaxPushRow(Integer maxPushRow) {
    this.maxPushRow = maxPushRow;
  }

  public Integer getMaxReadRow() {
    return maxReadRow;
  }

  public void setMaxReadRow(Integer maxReadRow) {
    this.maxReadRow = maxReadRow;
  }

  public Integer getMaxPageSize() {
    return maxPageSize;
  }

  public void setMaxPageSize(Integer maxPageSize) {
    this.maxPageSize = maxPageSize;
  }

  public void setEndDateByDate(Date currentDate, int date) {
    Date updateDate = DateTimeTool.getAfterDays(DateTimeTool.parse(lastUpdateTime), date);
    if (updateDate.after(currentDate)) {
      endDate = DateTimeTool.format(currentDate);
    } else {
      endDate = DateTimeTool.format(updateDate);
    }
    setSysDate(DateTimeTool.format(currentDate));// 設定sysDate作區間內沒有資料時的判斷用
  }

  public void setEndDate(Date currentDate) {
    endDate = DateTimeTool.format(currentDate, "yyyy-MM-dd HH:mm:ss");
    setSysDate(endDate);// 設定sysDate作區間內沒有資料時的判斷用
  }

  public void setEndDateByMonth(Date currentDate, int month) {
    Date updateDate = DateTimeTool.getAfterMonths(DateTimeTool.parse(lastUpdateTime), month);
    if (updateDate.after(currentDate)) {
      endDate = DateTimeTool.format(currentDate);
    } else {
      endDate = DateTimeTool.format(updateDate);
    }
    setSysDate(DateTimeTool.format(currentDate));// 設定sysDate作區間內沒有資料時的判斷用
  }

  public String getEndDate() {
    return endDate;
  }

  public String getSysDate() {
    return sysDate;
  }

  private void setSysDate(String sysDate) {
    this.sysDate = sysDate;
  }

  public String getLastRunTime() {
    return lastRunTime;
  }

  public void setLastRunTime(String lastRunTime) {
    this.lastRunTime = lastRunTime;
  }

  public String getStoreId() {
    return shopId;
  }

  public void setStoreId(String storeId) {
    this.shopId = storeId;
    // add by mowj 20160719
    this.storeId = storeId;
  }

  public String getRunType() {
    return runType;
  }

  public void setRunType(String runType) {
    this.runType = runType;
  }

  public String getEntId() {
    return entId;
  }

  public void setEntId(String entId) {
    this.entId = entId;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public boolean isReCycle() {
    return isReCycle;
  }

  public void setReCycle(boolean isReCycle) {
    this.isReCycle = isReCycle;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
}
