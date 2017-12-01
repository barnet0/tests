package com.digiwin.ecims.platforms.taobao.bean.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 中台对接菜鸟物流API——打印菜鸟物流面单确认
 * 请求参数params.printlist之java映射
 * @author 维杰
 *
 */
public class EcimsTaobaoWaybillIPrintReqParam {

  @JsonProperty("waybillcode")
  private String waybillCode;
  
  @JsonProperty("shortaddress")
  private String shortAddress;
  
  @JsonProperty("sendprovince")
  private String sendProvince;
  
  @JsonProperty("sendcity")
  private String sendCity;
  
  @JsonProperty("sendarea")
  private String sendArea;
  
  @JsonProperty("sendaddress")
  private String sendAddress;
  
  @JsonProperty("rcvname")
  private String rcvName;
  
  @JsonProperty("rcvphone")
  private String rcvPhone;
  
  @JsonProperty("rcvprovince")
  private String rcvProvince;
  
  @JsonProperty("rcvaddress")
  private String rcvAddress;

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

  public String getSendProvince() {
    return sendProvince;
  }

  public void setSendProvince(String sendProvince) {
    this.sendProvince = sendProvince;
  }

  public String getSendCity() {
    return sendCity;
  }

  public void setSendCity(String sendCity) {
    this.sendCity = sendCity;
  }

  public String getSendArea() {
    return sendArea;
  }

  public void setSendArea(String sendArea) {
    this.sendArea = sendArea;
  }

  public String getSendAddress() {
    return sendAddress;
  }

  public void setSendAddress(String sendAddress) {
    this.sendAddress = sendAddress;
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

  
}
