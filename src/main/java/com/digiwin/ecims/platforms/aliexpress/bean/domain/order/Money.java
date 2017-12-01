package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 货币信息
 * 
 * @author 维杰
 *
 */
public class Money {

  // 金额.如：100
  private String amount;

  // 分.如：10000
  private Long cent;

  // 币种USD/RUB.如：USD
  private String currencyCode;

  private Currency currency;

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
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

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }


}
