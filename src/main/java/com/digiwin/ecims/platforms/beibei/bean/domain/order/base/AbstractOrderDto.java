package com.digiwin.ecims.platforms.beibei.bean.domain.order.base;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/**
 * 订单基础类
 * @author zaregoto
 *
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public abstract class AbstractOrderDto {

  private Long oid;

  private String province;

  private String city;

  private String county;

  private String address;

  private String eventId;

  private Integer itemNum;

  private String status;

  private Double totalFee;

  private Double shippingFee;

  private Double payment;

  private String invoiceType;

  private String invoiceName;

  private String sellerRemark;

  private String remark;

  private String receiverName;

  private String receiverPhone;

  private String receiverAddress;

  private String company;

  private String outSid;

  private String createTime;

  private String payTime;

  private String shipTime;

  private String endTime;

  private String modifiedTime;

  private Double taxFee;

  private Double tariffFee;

  private Double addedvalueFee;

  private Double consumpFee;

  public Long getOid() {
    return oid;
  }

  public void setOid(Long oid) {
    this.oid = oid;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public Integer getItemNum() {
    return itemNum;
  }

  public void setItemNum(Integer itemNum) {
    this.itemNum = itemNum;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Double getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(Double totalFee) {
    this.totalFee = totalFee;
  }

  public Double getShippingFee() {
    return shippingFee;
  }

  public void setShippingFee(Double shippingFee) {
    this.shippingFee = shippingFee;
  }

  public Double getPayment() {
    return payment;
  }

  public void setPayment(Double payment) {
    this.payment = payment;
  }

  public String getInvoiceType() {
    return invoiceType;
  }

  public void setInvoiceType(String invoiceType) {
    this.invoiceType = invoiceType;
  }

  public String getInvoiceName() {
    return invoiceName;
  }

  public void setInvoiceName(String invoiceName) {
    this.invoiceName = invoiceName;
  }

  public String getSellerRemark() {
    return sellerRemark;
  }

  public void setSellerRemark(String sellerRemark) {
    this.sellerRemark = sellerRemark;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getReceiverName() {
    return receiverName;
  }

  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }

  public String getReceiverPhone() {
    return receiverPhone;
  }

  public void setReceiverPhone(String receiverPhone) {
    this.receiverPhone = receiverPhone;
  }

  public String getReceiverAddress() {
    return receiverAddress;
  }

  public void setReceiverAddress(String receiverAddress) {
    this.receiverAddress = receiverAddress;
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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getPayTime() {
    return payTime;
  }

  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }

  public String getShipTime() {
    return shipTime;
  }

  public void setShipTime(String shipTime) {
    this.shipTime = shipTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getModifiedTime() {
    return modifiedTime;
  }

  public void setModifiedTime(String modifiedTime) {
    this.modifiedTime = modifiedTime;
  }

  public Double getTaxFee() {
    return taxFee;
  }

  public void setTaxFee(Double taxFee) {
    this.taxFee = taxFee;
  }

  public Double getTariffFee() {
    return tariffFee;
  }

  public void setTariffFee(Double tariffFee) {
    this.tariffFee = tariffFee;
  }

  public Double getAddedvalueFee() {
    return addedvalueFee;
  }

  public void setAddedvalueFee(Double addedvalueFee) {
    this.addedvalueFee = addedvalueFee;
  }

  public Double getConsumpFee() {
    return consumpFee;
  }

  public void setConsumpFee(Double consumpFee) {
    this.consumpFee = consumpFee;
  }

  public AbstractOrderDto() {
  }
  
  public abstract List<? extends AbstractOrderItemDto> getItems();
}
