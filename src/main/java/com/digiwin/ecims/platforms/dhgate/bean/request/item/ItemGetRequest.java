package com.digiwin.ecims.platforms.dhgate.bean.request.item;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemGetResponse;

/**
 * 卖家获取产品详情接口请求参数
 * 
 * @author 维杰
 *
 */
public class ItemGetRequest extends DhgateBaseRequest<ItemGetResponse> {

  // 必须 产品编码信息 产品编码字符串信息,示例值：211198221
  private String itemCode;
  // 可选 产品所属语言站点 默认为英文站点,参数值为：EN英文站点,RU俄语站点,ES西班牙语站点;示例值：EN
  private String siteId;

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getSiteId() {
    return siteId;
  }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  public ItemGetRequest() {
    super();
  }

  @Override
  public Class<ItemGetResponse> getResponseClass() {
    return ItemGetResponse.class;
  }


}
