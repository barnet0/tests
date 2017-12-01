package com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.order.DeliveryGoodsItem;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class SendGoodByMerchantRequest extends BaiduBaseRequest {

  private List<DeliveryGoodsItem> deliveryItems;
  
  public List<DeliveryGoodsItem> getDeliveryItems() {
    return deliveryItems;
  }

  public void setDeliveryItems(List<DeliveryGoodsItem> deliveryItems) {
    this.deliveryItems = deliveryItems;
  }

  @Override
  public String getUrlPath() {
    return "OrderService/sendGoodByMerchant";
  }

}
