package com.digiwin.ecims.platforms.pdd2.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.SynSkuStockResponse;
/**
 * 全量更新商品库存
 * @author cjp
 *
 */
public class SynSkuStockRequest extends Pdd2BaseRequest<SynSkuStockResponse> {

  private String sku_id;
  
  private String outer_id;
  
  private Long quantity;
  
  public String getSkuId() {
    return sku_id;
  }

  public void setSkuId(String pddSkuId) {
    this.sku_id = pddSkuId;
  }

  public String getOuterIds() {
    return outer_id;
  }

  public void setOuterIds(String outerSkuIds) {
    this.outer_id = outerSkuIds;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("sku_id", getSkuId());
    apiParams.put("outer_id", getOuterIds());
    apiParams.put("quantity", getQuantity() + "");

    return apiParams;
  }

  @Override
  public String getMType() {
    return "pdd.goods.sku.stock.update";
  }

  @Override
  public Class<SynSkuStockResponse> getResponseClass() {
    return SynSkuStockResponse.class;
  }


}
