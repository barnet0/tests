package com.digiwin.ecims.platforms.aliexpress.bean.domain.order;

/**
 * 货币单位
 * 
 * @author 维杰
 *
 */
public class Currency {

  // 否 默认分数 2
  private Integer defaultFractionDigits;

  // 否 币种 USD
  private String currencyCode;

  // 否 币种符号 $
  private String symbol;

  public Integer getDefaultFractionDigits() {
    return defaultFractionDigits;
  }

  public void setDefaultFractionDigits(Integer defaultFractionDigits) {
    this.defaultFractionDigits = defaultFractionDigits;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }


}
