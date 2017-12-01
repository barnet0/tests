package com.digiwin.ecims.platforms.yougou.bean.response.base;

/**
 * 
 * @author 维杰
 *
 */
public abstract class YougouBaseResponse {

  // 业务结果代码(200=成功|500=系统异常|XXX=业务异常)
  protected String code;

  // 业务结果消息(异常消息|true|false)
  protected String message;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public YougouBaseResponse() {
    super();
  }

  public YougouBaseResponse(String code, String message) {
    super();
    this.code = code;
    this.message = message;
  }


}
