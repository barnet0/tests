package com.digiwin.ecims.platforms.dhgate.bean.request.item;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemDownshelfListResponse;

/**
 * 批量下架产品信息请求参数
 * 
 * @author 维杰
 *
 */
public class ItemDownshelfListRequest extends DhgateBaseRequest<ItemDownshelfListResponse> {

  // 必须 产品编码字符串信息 产品编码字符串信息，多个产品编码用英文半角逗号分隔;示例值：211198221,211198222
  private String itemCodes;
  // 可选 产品站点编号 产品站点编号，EN:英文站，RU：俄文站；默认值:EN;示例值：EN
  private String siteId;

  public String getItemCodes() {
    return itemCodes;
  }

  public void setItemCodes(String itemCodes) {
    this.itemCodes = itemCodes;
  }

  public String getSiteId() {
    return siteId;
  }

  public void setSiteId(String siteId) {
    this.siteId = siteId;
  }

  public ItemDownshelfListRequest() {
    super();
  }

  @Override
  public Class<ItemDownshelfListResponse> getResponseClass() {
    return ItemDownshelfListResponse.class;
  }
}
