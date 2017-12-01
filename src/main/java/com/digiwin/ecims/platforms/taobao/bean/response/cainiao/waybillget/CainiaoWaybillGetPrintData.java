package com.digiwin.ecims.platforms.taobao.bean.response.cainiao.waybillget;

public class CainiaoWaybillGetPrintData {

  private CainiaoWaybillGetPrintDataBody data;

  private String signature;

  private String templateURL;

  /**
   * @return the data
   */
  public CainiaoWaybillGetPrintDataBody getData() {
    return data;
  }

  /**
   * @param data the data to set
   */
  public void setData(CainiaoWaybillGetPrintDataBody data) {
    this.data = data;
  }

  /**
   * @return the signature
   */
  public String getSignature() {
    return signature;
  }

  /**
   * @param signature the signature to set
   */
  public void setSignature(String signature) {
    this.signature = signature;
  }

  /**
   * @return the templateURL
   */
  public String getTemplateURL() {
    return templateURL;
  }

  /**
   * @param templateURL the templateURL to set
   */
  public void setTemplateURL(String templateURL) {
    this.templateURL = templateURL;
  }


}
