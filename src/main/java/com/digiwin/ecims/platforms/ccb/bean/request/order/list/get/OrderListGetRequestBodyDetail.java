package com.digiwin.ecims.platforms.ccb.bean.request.order.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderListGetRequestBodyDetail {

  @XmlElement(name = "start_created")
  private String startCreated;
  
  @XmlElement(name = "end_created")
  private String endCreated;
  
  @XmlElement(name = "status")
  private String status;
  
  @XmlElement(name = "page_no")
  private Integer pageNo;
  
  @XmlElement(name = "page_size")
  private Integer pageSize;

  public String getStartCreated() {
    return startCreated;
  }

  public void setStartCreated(String startCreated) {
    this.startCreated = startCreated;
  }

  public String getEndCreated() {
    return endCreated;
  }

  public void setEndCreated(String endCreated) {
    this.endCreated = endCreated;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }
  
  
}
