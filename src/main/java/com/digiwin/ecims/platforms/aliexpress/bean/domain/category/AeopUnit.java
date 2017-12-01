package com.digiwin.ecims.platforms.aliexpress.bean.domain.category;

import java.io.Serializable;

public class AeopUnit implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -3512990072797477639L;

  // 否 单位名称
  private String unitName;

  // 否 和标准属性对换比率
  private Double rate;

  public String getUnitName() {
    return unitName;
  }

  public void setUnitName(String unitName) {
    this.unitName = unitName;
  }

  public Double getRate() {
    return rate;
  }

  public void setRate(Double rate) {
    this.rate = rate;
  }

}
