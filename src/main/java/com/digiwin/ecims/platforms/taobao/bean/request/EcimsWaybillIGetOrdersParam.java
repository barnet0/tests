package com.digiwin.ecims.platforms.taobao.bean.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 中台对接菜鸟物流API——申请菜鸟物流面单号
 * 请求参数params.orderlist之java映射
 * @author 维杰
 *
 */
public class EcimsWaybillIGetOrdersParam {

  @JsonProperty("rcvname")
  private String rcvName;
  
  @JsonProperty("ordchannel")
  private String ordChannel;
  
  @JsonProperty("oid")
  private String oid;
  
  @JsonProperty("suboidlist")
  private List<String> subOidList;
  
  @JsonProperty("rcvphone")
  private String rcvPhone;
  
  @JsonProperty("rcvmobile")
  private String rcvMobile;
  
  @JsonProperty("rcvprovince")
  private String rcvProvince;
  
  @JsonProperty("rcvaddress")
  private String rcvAddress;
  
  @JsonProperty("packageid")
  private String packageId;
  
  @JsonProperty("itemlist")
  private List<? extends Map<String, String>> itemList;
  
  @JsonProperty("expressprodtype")
  private String expressProdType;

  public String getRcvName() {
    return rcvName;
  }

  public void setRcvName(String rcvName) {
    this.rcvName = rcvName;
  }

  public String getOrdChannel() {
    return ordChannel;
  }

  public void setOrdChannel(String ordChannel) {
    this.ordChannel = ordChannel;
  }

  public String getOid() {
    return oid;
  }

  public void setOid(String oid) {
    this.oid = oid;
  }

  public List<String> getSubOidList() {
    return subOidList;
  }

  public void setSubOidList(List<String> subOidList) {
    this.subOidList = subOidList;
  }

  public String getRcvPhone() {
    return rcvPhone;
  }

  public void setRcvPhone(String rcvPhone) {
    this.rcvPhone = rcvPhone;
  }

  /**
   * @return the rcvMobile
   */
  public String getRcvMobile() {
    return rcvMobile;
  }

  /**
   * @param rcvMobile the rcvMobile to set
   */
  public void setRcvMobile(String rcvMobile) {
    this.rcvMobile = rcvMobile;
  }

  public String getRcvProvince() {
    return rcvProvince;
  }

  public void setRcvProvince(String rcvProvince) {
    this.rcvProvince = rcvProvince;
  }

  public String getRcvAddress() {
    return rcvAddress;
  }

  public void setRcvAddress(String rcvAddress) {
    this.rcvAddress = rcvAddress;
  }

  public String getPackageId() {
    return packageId;
  }

  public void setPackageId(String packageId) {
    this.packageId = packageId;
  }

  public List<? extends Map<String, String>> getItemList() {
    return itemList;
  }

  public void setItemList(List<? extends Map<String, String>> itemList) {
    this.itemList = itemList;
  }

  public String getExpressProdType() {
    return expressProdType;
  }

  public void setExpressProdType(String expressProdType) {
    this.expressProdType = expressProdType;
  }

  
}
