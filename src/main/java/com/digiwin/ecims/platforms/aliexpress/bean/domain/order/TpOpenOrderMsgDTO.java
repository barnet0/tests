package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 留言信息
 * 
 * @author 维杰
 *
 */
public class TpOpenOrderMsgDTO {

  // 否 订单号
  private Integer businessOrderId;

  // 否 留言内容
  private String content;

  // 否 创建时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtCreate;

  // 否 修改时间 标准时间格式：yyyyMMddHHmmssSSSZ，例如：20120801154220368+0800
  private String gmtModified;

  // 否 订单留言ID
  private Integer id;

  // 否 信息发送放 buyer/seller buyer
  private String poster;

  // 否 接收者adminSeq
  private Integer receiverAdminSeq;

  // 否 接收者firstName
  private String receiverFirstName;

  // 否 接收者lastName
  private String receiverLastName;

  // 否 接收者loginId
  private String receiverLoginId;

  // 否 接收者memberSeq
  private Integer receiverSeq;

  // 否 发送者adminSeq
  private Integer senderAdminSeq;

  // 否 发送者firstName
  private String senderFirstName;

  // 否 发送者lastName
  private String senderLastName;

  // 否 发送者loginId
  private String senderLoginId;

  // 否 发送者memberSeq
  private Integer senderSeq;

  // 否 留言状态 create/viewed create
  private String status;

  public Integer getBusinessOrderId() {
    return businessOrderId;
  }

  public void setBusinessOrderId(Integer businessOrderId) {
    this.businessOrderId = businessOrderId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(String gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public String getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(String gmtModified) {
    this.gmtModified = gmtModified;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public Integer getReceiverAdminSeq() {
    return receiverAdminSeq;
  }

  public void setReceiverAdminSeq(Integer receiverAdminSeq) {
    this.receiverAdminSeq = receiverAdminSeq;
  }

  public String getReceiverFirstName() {
    return receiverFirstName;
  }

  public void setReceiverFirstName(String receiverFirstName) {
    this.receiverFirstName = receiverFirstName;
  }

  public String getReceiverLastName() {
    return receiverLastName;
  }

  public void setReceiverLastName(String receiverLastName) {
    this.receiverLastName = receiverLastName;
  }

  public String getReceiverLoginId() {
    return receiverLoginId;
  }

  public void setReceiverLoginId(String receiverLoginId) {
    this.receiverLoginId = receiverLoginId;
  }

  public Integer getReceiverSeq() {
    return receiverSeq;
  }

  public void setReceiverSeq(Integer receiverSeq) {
    this.receiverSeq = receiverSeq;
  }

  public Integer getSenderAdminSeq() {
    return senderAdminSeq;
  }

  public void setSenderAdminSeq(Integer senderAdminSeq) {
    this.senderAdminSeq = senderAdminSeq;
  }

  public String getSenderFirstName() {
    return senderFirstName;
  }

  public void setSenderFirstName(String senderFirstName) {
    this.senderFirstName = senderFirstName;
  }

  public String getSenderLastName() {
    return senderLastName;
  }

  public void setSenderLastName(String senderLastName) {
    this.senderLastName = senderLastName;
  }

  public String getSenderLoginId() {
    return senderLoginId;
  }

  public void setSenderLoginId(String senderLoginId) {
    this.senderLoginId = senderLoginId;
  }

  public Integer getSenderSeq() {
    return senderSeq;
  }

  public void setSenderSeq(Integer senderSeq) {
    this.senderSeq = senderSeq;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }


}
