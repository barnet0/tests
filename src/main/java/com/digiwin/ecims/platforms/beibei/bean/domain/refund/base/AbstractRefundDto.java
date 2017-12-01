package com.digiwin.ecims.platforms.beibei.bean.domain.refund.base;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * 售后单基础类
 * @author zaregoto
 *
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public abstract class AbstractRefundDto {

  private Long id;

  private String oid;

  private String iid;

  private String status;

  private Double price;

  private Integer num;

  private String title;

  private String reason;

  private String desc;

  private String company;

  private String outSid;

  private Double refundFee;

  private Double totalFee;

  private Double pointFee;

  private String createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public String getIid() {
    return iid;
  }

  public void setIid(String iid) {
    this.iid = iid;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getOutSid() {
    return outSid;
  }

  public void setOutSid(String outSid) {
    this.outSid = outSid;
  }

  public Double getRefundFee() {
    return refundFee;
  }

  public void setRefundFee(Double refundFee) {
    this.refundFee = refundFee;
  }

  public Double getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(Double totalFee) {
    this.totalFee = totalFee;
  }

  public Double getPointFee() {
    return pointFee;
  }

  public void setPointFee(Double pointFee) {
    this.pointFee = pointFee;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

}
