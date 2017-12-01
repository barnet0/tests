package com.digiwin.ecims.platforms.ccb.bean.domain.order;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrderProductItemList {

  @XmlElement(name = "product_items")
  private List<OrderProductItem> productItems;

  public List<OrderProductItem> getProductItems() {
    return productItems;
  }

  public void setProductItems(List<OrderProductItem> productItems) {
    this.productItems = productItems;
  }
  
  
}
