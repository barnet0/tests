package com.digiwin.ecims.platforms.baidu.bean.merchant.client.model;

import java.util.List;

public class ResponseHeader {
  private String desc;

  private List<Failure> failures;

  private int oprs;

  private int succ;

  private int oprtime;

  private int quota;

  private int rquota;

  private int status;

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<Failure> getFailures() {
    return failures;
  }

  public void setFailures(List<Failure> failures) {
    this.failures = failures;
  }

  public int getOprs() {
    return oprs;
  }

  public void setOprs(int oprs) {
    this.oprs = oprs;
  }

  public int getSucc() {
    return succ;
  }

  public void setSucc(int succ) {
    this.succ = succ;
  }

  public int getOprtime() {
    return oprtime;
  }

  public void setOprtime(int oprtime) {
    this.oprtime = oprtime;
  }

  public int getQuota() {
    return quota;
  }

  public void setQuota(int quota) {
    this.quota = quota;
  }

  public int getRquota() {
    return rquota;
  }

  public void setRquota(int rquota) {
    this.rquota = rquota;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }


}

