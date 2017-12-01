package com.digiwin.ecims.platforms.ccb.bean.request.item.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "query")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemListGetRequestBodyDetail {

  @XmlElement(name = "start_time")
  private String startTime;
  
  @XmlElement(name = "end_time")
  private String endTime;
  
  @XmlElement(name = "status")
  private String status;
  
  @XmlElement(name = "page_no")
  private Integer pageNo;
  
  @XmlElement(name = "page_size")
  private Integer pageSize;

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
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
