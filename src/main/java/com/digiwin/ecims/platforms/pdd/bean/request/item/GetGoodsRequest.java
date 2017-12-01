package com.digiwin.ecims.platforms.pdd.bean.request.item;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd.bean.request.PddBaseRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetGoodsResponse;

public class GetGoodsRequest extends PddBaseRequest<GetGoodsResponse> {

//  商品类型    该值为“Onsale”或者“InStock”，分别是代表“在售”和“下架”。
  private String goodsType;
  
//  拼多多skuID外部编码    示例：erp_12131
  private String outerID;
  
//  拼多多商品名称 示例：洗衣液
  private String goodsName;
  
  private Integer page;
  
  private Integer pageSize;
  
  public String getGoodsType() {
    return goodsType;
  }

  public void setGoodsType(String goodsType) {
    this.goodsType = goodsType;
  }

  public String getOuterID() {
    return outerID;
  }

  public void setOuterID(String outerID) {
    this.outerID = outerID;
  }

  public String getGoodsName() {
    return goodsName;
  }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("goodsType", getGoodsType());
    apiParams.put("outerID", getOuterID());
    apiParams.put("goodsName", getGoodsName());
    apiParams.put("page", getPage() + "");
    apiParams.put("pageSize", getPageSize() + "");

    return apiParams;
  }

  @Override
  public String getMType() {
    return "mGetGoods";
  }

  @Override
  public Class<GetGoodsResponse> getResponseClass() {
    return GetGoodsResponse.class;
  }

}
