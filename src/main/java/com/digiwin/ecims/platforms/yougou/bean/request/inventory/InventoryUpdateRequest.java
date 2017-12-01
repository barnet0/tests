package com.digiwin.ecims.platforms.yougou.bean.request.inventory;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.inventory.InventoryUpdateResponse;

/**
 * @author 维杰
 *
 */
public class InventoryUpdateRequest extends YougouBaseRequest<InventoryUpdateResponse> {

  // 商家货品条码
  private String thirdPartyCode;

  // 可选，库存更新类型。有效取值范围：0=全局(默认即覆盖更新)、1=增量(即基础上更新)
  // 0=全局、1=增量
  private String updateType;

  // 更新数量，数值为负数表示扣减操作，数值为正数表示增加操作
  private Integer quantity;

  public String getThirdPartyCode() {
    return thirdPartyCode;
  }

  public void setThirdPartyCode(String thirdPartyCode) {
    this.thirdPartyCode = thirdPartyCode;
  }

  public String getUpdateType() {
    return updateType;
  }

  public void setUpdateType(String updateType) {
    this.updateType = updateType;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getThirdPartyCode() != null) {
      apiParams.put("third_party_code", getThirdPartyCode());
    }
    if (getUpdateType() != null) {
      apiParams.put("update_type", getUpdateType());
    }
    if (getQuantity() != null) {
      apiParams.put("quantity", getQuantity() + "");
    }

    return apiParams;
  }
  
  @Override
  public String getApiMethodName() {
    return "yougou.inventory.update";
  }

  @Override
  public Class<InventoryUpdateResponse> getResponseClass() {
    return InventoryUpdateResponse.class;
  }

}
