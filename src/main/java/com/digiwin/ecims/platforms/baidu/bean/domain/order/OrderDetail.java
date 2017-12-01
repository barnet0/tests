package com.digiwin.ecims.platforms.baidu.bean.domain.order;

public class OrderDetail extends Order {

  private String saleChannel;

  private String adChannel;

  private Integer cpsRatioStatus;

  public String getSaleChannel() {
    return saleChannel;
  }

  public void setSaleChannel(String saleChannel) {
    this.saleChannel = saleChannel;
  }

  public String getAdChannel() {
    return adChannel;
  }

  public void setAdChannel(String adChannel) {
    this.adChannel = adChannel;
  }

  public Integer getCpsRatioStatus() {
    return cpsRatioStatus;
  }

  public void setCpsRatioStatus(Integer cpsRatioStatus) {
    this.cpsRatioStatus = cpsRatioStatus;
  }
  
  
}
