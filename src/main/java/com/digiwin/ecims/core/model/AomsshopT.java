package com.digiwin.ecims.core.model;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the aomsshop_t database table.
 * 
 */
@Entity
@Table(name = "aomsshop_t")
@NamedQuery(name = "AomsshopT.findAll", query = "SELECT a FROM AomsshopT a")
/*
 * 新增@DynamicUpdate(true)注解标记，如果没有发生变化的字段，在更新时，就不会进行update， 如果不加，就更新所有字段
 */
@DynamicUpdate(true)
public class AomsshopT implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "aomsshop001")
  private String aomsshop001;// 店铺编号

  @Column(name = "aomsshop002")
  private String aomsshop002;// 店铺名称

  @Column(name = "aomsshop003")
  private String aomsshop003;// 平台类型

  @Column(name = "aomsshop004")
  private String aomsshop004;// appkey

  @Column(name = "aomsshop005")
  private String aomsshop005;// appsecret

  @Column(name = "aomsshop006")
  private String aomsshop006;// access_token

  @Column(name = "aomsshop007")
  private String aomsshop007;// refresh_token

  @Column(name = "aomsshop008")
  private String aomsshop008;// raw_json

  @Column(name = "aomsshop009")
  private String aomsshop009;// 店鋪在平台的名稱

  @Column(name = "aomsshopcrtdt", updatable = false)
  private String aomsshopcrtdt;// 创建时间

  @Column(name = "aomsshopmoddt")
  private String aomsshopmoddt;// 修改时间

  @Column(name = "aomsshop010")
  private String aomsshop010; // 平台app回调地址

  @Column(name = "aomsshop011")
  private String aomsshop011; // 平台名称

  @Column(name = "aomsshop012")
  private String aomsshop012; // 平台分配的店铺识别ID

  @Column(name = "aomsshop013")
  private String aomsshop013; // 是否启用。1表示启用，0表示禁用
  
  public AomsshopT() {}

  public String getAomsshop001() {
    return this.aomsshop001;
  }

  public void setAomsshop001(String aomsshop001) {
    this.aomsshop001 = aomsshop001;
  }

  public String getAomsshop002() {
    return this.aomsshop002;
  }

  public void setAomsshop002(String aomsshop002) {
    this.aomsshop002 = aomsshop002;
  }

  public String getAomsshop003() {
    return this.aomsshop003;
  }

  public void setAomsshop003(String aomsshop003) {
    this.aomsshop003 = aomsshop003;
  }

  public String getAomsshop004() {
    return this.aomsshop004;
  }

  public void setAomsshop004(String aomsshop004) {
    this.aomsshop004 = aomsshop004;
  }

  public String getAomsshop005() {
    return this.aomsshop005;
  }

  public void setAomsshop005(String aomsshop005) {
    this.aomsshop005 = aomsshop005;
  }

  public String getAomsshop006() {
    return this.aomsshop006;
  }

  public void setAomsshop006(String aomsshop006) {
    this.aomsshop006 = aomsshop006;
  }

  public String getAomsshop007() {
    return this.aomsshop007;
  }

  public void setAomsshop007(String aomsshop007) {
    this.aomsshop007 = aomsshop007;
  }

  public String getAomsshop008() {
    return this.aomsshop008;
  }

  public void setAomsshop008(String aomsshop008) {
    this.aomsshop008 = aomsshop008;
  }

  public String getAomsshopcrtdt() {
    return this.aomsshopcrtdt;
  }

  public void setAomsshopcrtdt(String aomsshopcrtdt) {
    this.aomsshopcrtdt = aomsshopcrtdt;
  }

  public String getAomsshopmoddt() {
    return this.aomsshopmoddt;
  }

  public void setAomsshopmoddt(String aomsshopmoddt) {
    this.aomsshopmoddt = aomsshopmoddt;
  }

  public String getAomsshop009() {
    return aomsshop009;
  }

  public void setAomsshop009(String aomsshop009) {
    this.aomsshop009 = aomsshop009;
  }

  public String getAomsshop010() {
    return aomsshop010;
  }

  public void setAomsshop010(String aomsshop010) {
    this.aomsshop010 = aomsshop010;
  }

  public String getAomsshop011() {
    return aomsshop011;
  }

  public void setAomsshop011(String aomsshop011) {
    this.aomsshop011 = aomsshop011;
  }

  /**
   * @return the aomsshop012
   */
  public String getAomsshop012() {
    return aomsshop012;
  }

  /**
   * @param aomsshop012 the aomsshop012 to set
   */
  public void setAomsshop012(String aomsshop012) {
    this.aomsshop012 = aomsshop012;
  }

  public String getAomsshop013() {
    return aomsshop013;
  }

  public void setAomsshop013(String aomsshop013) {
    this.aomsshop013 = aomsshop013;
  }

}
