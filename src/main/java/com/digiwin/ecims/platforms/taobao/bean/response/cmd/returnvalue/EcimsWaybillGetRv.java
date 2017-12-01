package com.digiwin.ecims.platforms.taobao.bean.response.cmd.returnvalue;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 中台对接菜鸟物流API——申请菜鸟物流面单号
 * 响应参数returnValue之java映射
 * @author 维杰
 *
 */
public class EcimsWaybillGetRv {

  @JsonProperty("waybillcode")
  private String waybillCode;
  
  @JsonProperty("shortaddress")
  private String shortAddress;
  
  @JsonProperty("packcentcode")
  private String packCentCode;
  
  @JsonProperty("packcentname")
  private String packCentName;
  
  @JsonProperty("rcvname")
  private String rcvName;
  
  @JsonProperty("rcvphone")
  private String rcvPhone;
  
  @JsonProperty("rcvmobile")
  private String rcvMobile;
  
  @JsonProperty("oid")
  private String oid;
  
  @JsonProperty("suboidlist")
  private List<String> subOidList;
  
  @JsonProperty("itemlist")
  private List<? extends Map<String, String>> itemList;

  public String getWaybillCode() {
    return waybillCode;
  }

  public void setWaybillCode(String waybillCode) {
    this.waybillCode = waybillCode;
  }

  public String getShortAddress() {
    return shortAddress;
  }

  public void setShortAddress(String shortAddress) {
    this.shortAddress = shortAddress;
  }

  public String getPackCentCode() {
    return packCentCode;
  }

  public void setPackCentCode(String packCentCode) {
    this.packCentCode = packCentCode;
  }

  public String getPackCentName() {
    return packCentName;
  }

  public void setPackCentName(String packCentName) {
    this.packCentName = packCentName;
  }

  public String getRcvName() {
    return rcvName;
  }

  public void setRcvName(String rcvName) {
    this.rcvName = rcvName;
  }

  public String getRcvPhone() {
    return rcvPhone;
  }

  public void setRcvPhone(String rcvPhone) {
    this.rcvPhone = rcvPhone;
  }

  public String getRcvMobile() {
    return rcvMobile;
  }

  public void setRcvMobile(String rcvMobile) {
    this.rcvMobile = rcvMobile;
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

  public List<? extends Map<String, String>> getItemList() {
    return itemList;
  }

  public void setItemList(List<? extends Map<String, String>> itemList) {
    this.itemList = itemList;
  }

}
