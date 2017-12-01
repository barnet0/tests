package com.digiwin.ecims.platforms.pdd.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd.bean.request.PddBaseRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetSkuStockResponse;

public class GetSkuStockRequest extends PddBaseRequest<GetSkuStockResponse> {

  private String pddSkuIds;
  
  private String outerSkuIds;
  
  public String getPddSkuIds() {
    return pddSkuIds;
  }

  /**
   * 拼多多的SKU_ID 示例：623,631(批量请用半角逗号分开)
   * 注：pddSkuIds和outerSkuIds 只需要填写一个即可
   * @param pddSkuIds
   */
  public void setPddSkuIds(String pddSkuIds) {
    this.pddSkuIds = pddSkuIds;
  }

  public String getOuterSkuIds() {
    return outerSkuIds;
  }

  /**
   * 外部系统编码 示例：out623,out631(批量请用半角逗号分开，此处为商品映射填写的编码)
   * 注：pddSkuIds和outerSkuIds 只需要填写一个即可
   * @param outerSkuIds
   */
  public void setOuterSkuIds(String outerSkuIds) {
    this.outerSkuIds = outerSkuIds;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("pddSkuIds", getPddSkuIds());
    apiParams.put("outerSkuIds", getOuterSkuIds());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "mGetSkuStock";
  }

  @Override
  public Class<GetSkuStockResponse> getResponseClass() {
    return GetSkuStockResponse.class;
  }

}
