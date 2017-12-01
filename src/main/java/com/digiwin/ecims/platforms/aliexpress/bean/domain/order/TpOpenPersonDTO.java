package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 个人基本信息
 * 
 * @author 维杰
 *
 */
public class TpOpenPersonDTO {

  // 否 国家
  private String country;

  // 否 firstName
  private String firstName;

  // 否 lastName
  private String lastName;

  // 否 登录ID
  private String loginId;

  // 否 邮箱
  @JsonProperty("Email")
  private String email;

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
