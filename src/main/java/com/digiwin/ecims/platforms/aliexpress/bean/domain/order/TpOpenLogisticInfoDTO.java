package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 物流信息
 * 
 * @author 维杰
 *
 */
public class TpOpenLogisticInfoDTO {

  // 否 妥投时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtReceived;

  // 否 发货时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtSend;

  // 否 是否可获取物流追踪信息Y/N N
  private Boolean haveTrackingInfo;

  // 否 物流追踪号
  private String logisticsNo;

  // 否 发货物流服务key
  private String logisticsServiceName;

  // 否 物流公司类型
  private String logisticsTypeCode;

  // 否 初始值 default、 已经妥投 received、没有妥投 not_received、疑似妥投 suspected_received default
  private String receiveStatus;

  // 否 未妥投原因，如国家不匹配
  private String recvStatusDesc;

  // 否 物流订单id
  private Long shipOrderId;

  public String getGmtReceived() {
    return gmtReceived;
  }

  public void setGmtReceived(String gmtReceived) {
    this.gmtReceived = gmtReceived;
  }

  public String getGmtSend() {
    return gmtSend;
  }

  public void setGmtSend(String gmtSend) {
    this.gmtSend = gmtSend;
  }

  public Boolean getHaveTrackingInfo() {
    return haveTrackingInfo;
  }

  public void setHaveTrackingInfo(Boolean haveTrackingInfo) {
    this.haveTrackingInfo = haveTrackingInfo;
  }

  public String getLogisticsNo() {
    return logisticsNo;
  }

  public void setLogisticsNo(String logisticsNo) {
    this.logisticsNo = logisticsNo;
  }

  public String getLogisticsServiceName() {
    return logisticsServiceName;
  }

  public void setLogisticsServiceName(String logisticsServiceName) {
    this.logisticsServiceName = logisticsServiceName;
  }

  public String getLogisticsTypeCode() {
    return logisticsTypeCode;
  }

  public void setLogisticsTypeCode(String logisticsTypeCode) {
    this.logisticsTypeCode = logisticsTypeCode;
  }

  public String getReceiveStatus() {
    return receiveStatus;
  }

  public void setReceiveStatus(String receiveStatus) {
    this.receiveStatus = receiveStatus;
  }

  public String getRecvStatusDesc() {
    return recvStatusDesc;
  }

  public void setRecvStatusDesc(String recvStatusDesc) {
    this.recvStatusDesc = recvStatusDesc;
  }

  public Long getShipOrderId() {
    return shipOrderId;
  }

  public void setShipOrderId(Long shipOrderId) {
    this.shipOrderId = shipOrderId;
  }


}
