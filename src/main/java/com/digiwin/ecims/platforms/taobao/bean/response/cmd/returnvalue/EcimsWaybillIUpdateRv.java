package com.digiwin.ecims.platforms.taobao.bean.response.cmd.returnvalue;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 中台菜鸟物流系列接口——更新面单信息接口
 * returnValue返回值
 * @author 维杰
 */
public class EcimsWaybillIUpdateRv {

  @JsonProperty("cpcode")
  private String cpCode;
  
  @JsonProperty("waybillcode")
  private String waybillCode;
  
  @JsonProperty("expressprodtype")
  private String expressProdType;
  
  @JsonProperty("ordchannel")
  private String ordChannel;
  
  @JsonProperty("rcvname")
  private String rcvName;
  
  @JsonProperty("rcvphone")
  private String rcvPhone;
  
  @JsonProperty("rcvprovince")
  private String rcvProvince;
  
  @JsonProperty("rcvaddress")
  private String rcvAddress;
  
  @JsonProperty("oid")
  private String oid;
  
  @JsonProperty("suboidlist")
  private List<String> subOidList;
  
  @JsonProperty("itemlist")
  private List<? extends Map<String, String>> itemList;

  public String getCpCode() {
    return cpCode;
  }

  public void setCpCode(String cpCode) {
    this.cpCode = cpCode;
  }

  public String getWaybillCode() {
    return waybillCode;
  }

  public void setWaybillCode(String waybillCode) {
    this.waybillCode = waybillCode;
  }

  public String getExpressProdType() {
    return expressProdType;
  }

  public void setExpressProdType(String expressProdType) {
    this.expressProdType = expressProdType;
  }

  public String getOrdChannel() {
    return ordChannel;
  }

  public void setOrdChannel(String ordChannel) {
    this.ordChannel = ordChannel;
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
