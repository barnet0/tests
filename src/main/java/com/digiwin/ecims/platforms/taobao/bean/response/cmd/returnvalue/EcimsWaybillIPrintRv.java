package com.digiwin.ecims.platforms.taobao.bean.response.cmd.returnvalue;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 中台对接菜鸟物流API——打印菜鸟物流面单确认
 * 响应参数returnValue之java映射
 * @author 维杰
 *
 */
public class EcimsWaybillIPrintRv {

  @JsonProperty("noticeid")
  private String noticeId;
  
  @JsonProperty("noticemsg")
  private String noticeMsg;
  
  @JsonProperty("printcnt")
  private String printCnt;
  
  @JsonProperty("waybillcode")
  private String waybillCode;

  public String getNoticeId() {
    return noticeId;
  }

  public void setNoticeId(String noticeId) {
    this.noticeId = noticeId;
  }

  public String getNoticeMsg() {
    return noticeMsg;
  }

  public void setNoticeMsg(String noticeMsg) {
    this.noticeMsg = noticeMsg;
  }

  public String getPrintCnt() {
    return printCnt;
  }

  public void setPrintCnt(String printCnt) {
    this.printCnt = printCnt;
  }

  public String getWaybillCode() {
    return waybillCode;
  }

  public void setWaybillCode(String waybillCode) {
    this.waybillCode = waybillCode;
  }
  
}
