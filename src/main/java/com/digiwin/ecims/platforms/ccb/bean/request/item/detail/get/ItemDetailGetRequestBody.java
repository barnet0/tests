package com.digiwin.ecims.platforms.ccb.bean.request.item.detail.get;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemDetailGetRequestBody {

  @XmlElement(name = "query")
  private ItemDetailGetRequestBodyDetail bodyDetail;

  public ItemDetailGetRequestBodyDetail getBodyDetail() {
    return bodyDetail;
  }

  public void setBodyDetail(ItemDetailGetRequestBodyDetail bodyDetail) {
    this.bodyDetail = bodyDetail;
  }
  
  
}
