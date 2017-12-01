package com.digiwin.ecims.platforms.ccb.bean.domain.order;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderInvoiceInfo {

  @XmlElement(name = "invoice_type")
  private String invoiceType;

  @XmlElement(name = "is_invoice")
  private String isInvoice;
  
  @XmlElement(name = "invoice_title")
  private String invoiceTitle;

  @XmlElement(name = "tax_payer_id")
  private String taxPayerId;

  @XmlElement(name = "register_address")
  private String registerAddress;

  @XmlElement(name = "register_phone")
  private String registerPhone;

  @XmlElement(name = "bank_name")
  private String bankName;

  @XmlElement(name = "bank_acount")
  private String bankAcount;

  public String getInvoiceType() {
    return invoiceType;
  }

  public void setInvoiceType(String invoiceType) {
    this.invoiceType = invoiceType;
  }

  public String getIsInvoice() {
    return isInvoice;
  }

  public void setIsInvoice(String isInvoice) {
    this.isInvoice = isInvoice;
  }

  public String getInvoiceTitle() {
    return invoiceTitle;
  }

  public void setInvoiceTitle(String invoiceTitle) {
    this.invoiceTitle = invoiceTitle;
  }

  public String getTaxPayerId() {
    return taxPayerId;
  }

  public void setTaxPayerId(String taxPayerId) {
    this.taxPayerId = taxPayerId;
  }

  public String getRegisterAddress() {
    return registerAddress;
  }

  public void setRegisterAddress(String registerAddress) {
    this.registerAddress = registerAddress;
  }

  public String getRegisterPhone() {
    return registerPhone;
  }

  public void setRegisterPhone(String registerPhone) {
    this.registerPhone = registerPhone;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getBankAcount() {
    return bankAcount;
  }

  public void setBankAcount(String bankAcount) {
    this.bankAcount = bankAcount;
  }
  
}
