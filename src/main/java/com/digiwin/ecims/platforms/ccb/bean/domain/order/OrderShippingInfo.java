package com.digiwin.ecims.platforms.ccb.bean.domain.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderShippingInfo {

  @XmlElement(name = "consignee_name")
  private String consigneName;

  @XmlElement(name = "consignee_province")
  private String consigneeProvince;

  @XmlElement(name = "consignee_city")
  private String consigneeCity;

  @XmlElement(name = "consignee_county")
  private String consigneeCounty;

  @XmlElement(name = "consignee_address")
  private String consigneeAddress;

  @XmlElement(name = "consignee_zip")
  private String consigneeZip;

  @XmlElement(name = "consignee_mobile")
  private String consigneeMobile;

  @XmlElement(name = "consignee_phone")
  private String consigneePhone;

  public String getConsigneName() {
    return consigneName;
  }

  public void setConsigneName(String consigneName) {
    this.consigneName = consigneName;
  }

  public String getConsigneeProvince() {
    return consigneeProvince;
  }

  public void setConsigneeProvince(String consigneeProvince) {
    this.consigneeProvince = consigneeProvince;
  }

  public String getConsigneeCity() {
    return consigneeCity;
  }

  public void setConsigneeCity(String consigneeCity) {
    this.consigneeCity = consigneeCity;
  }

  public String getConsigneeCounty() {
    return consigneeCounty;
  }

  public void setConsigneeCounty(String consigneeCounty) {
    this.consigneeCounty = consigneeCounty;
  }

  public String getConsigneeAddress() {
    return consigneeAddress;
  }

  public void setConsigneeAddress(String consigneeAddress) {
    this.consigneeAddress = consigneeAddress;
  }

  public String getConsigneeZip() {
    return consigneeZip;
  }

  public void setConsigneeZip(String consigneeZip) {
    this.consigneeZip = consigneeZip;
  }

  public String getConsigneeMobile() {
    return consigneeMobile;
  }

  public void setConsigneeMobile(String consigneeMobile) {
    this.consigneeMobile = consigneeMobile;
  }

  public String getConsigneePhone() {
    return consigneePhone;
  }

  public void setConsigneePhone(String consigneePhone) {
    this.consigneePhone = consigneePhone;
  }
  
  
}
