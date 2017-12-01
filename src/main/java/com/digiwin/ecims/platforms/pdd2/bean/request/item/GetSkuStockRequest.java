package com.digiwin.ecims.platforms.pdd2.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetSkuStockResponse;

/**
 * 获取商品sku库存接口
 * @author cjp
 *
 */
public class GetSkuStockRequest extends Pdd2BaseRequest<GetSkuStockResponse> {

  private String sku_ids;
  
  private String outer_ids;
  
  public String getPddSkuIds() {
    return sku_ids;
  }

  /**
   * 拼多多的SKU_ID 示例：623,631(批量请用半角逗号分开)
   * 注：pddSkuIds和outerSkuIds 只需要填写一个即可
   * @param pddSkuIds
   */
  public void setPddSkuIds(String pddSkuIds) {
    this.sku_ids = pddSkuIds;
  }

  public String getOuterSkuIds() {
    return outer_ids;
  }

  /**
   * 外部系统编码 示例：out623,out631(批量请用半角逗号分开，此处为商品映射填写的编码)
   * 注：pddSkuIds和outerSkuIds 只需要填写一个即可
   * @param outerSkuIds
   */
  public void setOuterSkuIds(String outerSkuIds) {
    this.outer_ids = outerSkuIds;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("sku_ids", getPddSkuIds());
    apiParams.put("outer_ids", getOuterSkuIds());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "pdd.goods.sku.stock.get";
  }

  @Override
  public Class<GetSkuStockResponse> getResponseClass() {
    return GetSkuStockResponse.class;
  }

}
