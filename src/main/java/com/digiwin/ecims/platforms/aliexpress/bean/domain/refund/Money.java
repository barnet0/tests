package com.digiwin.ecims.platforms.aliexpress.bean.domain.refund;

/**
 * 货币信息
 * 
 * @author 维杰
 *
 */
public class Money {

  // 否 金额，单位元 12.31
  private Double amount;

  // 否 金额，单位分 1231
  private Long cent;

  // 否 币种 USD
  private String currencyCode;

  // 否 元/分换算比例 100
  private Integer centFactor;

  private Currency currency;

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Long getCent() {
    return cent;
  }

  public void setCent(Long cent) {
    this.cent = cent;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public Integer getCentFactor() {
    return centFactor;
  }

  public void setCentFactor(Integer centFactor) {
    this.centFactor = centFactor;
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }


}
