package com.digiwin.ecims.platforms.ccb.bean.request.item.list.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemListGetRequestBody {

  @XmlElement(name = "query")
  private ItemListGetRequestBodyDetail bodyDetail;

  public ItemListGetRequestBodyDetail getBodyDetail() {
    return bodyDetail;
  }

  public void setBodyDetail(ItemListGetRequestBodyDetail bodyDetail) {
    this.bodyDetail = bodyDetail;
  }
  
  
}
