package com.digiwin.ecims.platforms.beibei.bean.domain.order;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.digiwin.ecims.platforms.beibei.bean.domain.order.base.AbstractOrderItemDto;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class OrderDetailGetItemDto extends AbstractOrderItemDto {

  private String shipCityCode;

  private String declareCityCode;

  private Double forexPrice;

  private Double forexOriginPrice;

  private Double forexSubtotal;

  public String getShipCityCode() {
    return shipCityCode;
  }

  public void setShipCityCode(String shipCityCode) {
    this.shipCityCode = shipCityCode;
  }

  public String getDeclareCityCode() {
    return declareCityCode;
  }

  public void setDeclareCityCode(String declareCityCode) {
    this.declareCityCode = declareCityCode;
  }

  public Double getForexPrice() {
    return forexPrice;
  }

  public void setForexPrice(Double forexPrice) {
    this.forexPrice = forexPrice;
  }

  public Double getForexOriginPrice() {
    return forexOriginPrice;
  }

  public void setForexOriginPrice(Double forexOriginPrice) {
    this.forexOriginPrice = forexOriginPrice;
  }

  public Double getForexSubtotal() {
    return forexSubtotal;
  }

  public void setForexSubtotal(Double forexSubtotal) {
    this.forexSubtotal = forexSubtotal;
  }

  public OrderDetailGetItemDto() {
  }
  
}
