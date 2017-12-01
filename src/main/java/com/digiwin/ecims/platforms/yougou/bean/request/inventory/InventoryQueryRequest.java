package com.digiwin.ecims.platforms.yougou.bean.request.inventory;

import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.inventory.InventoryQueryResponse;
import com.digiwin.ecims.platforms.yougou.util.YougouApiParamMap;

public class InventoryQueryRequest extends YougouBaseRequest<InventoryQueryResponse> {

  private String brandNo;
  
  private String catNo;
  
  private String years;
  
  private String commodityStatus;
  
  private String orderDistributionSide;
  
  private String thirdPartyCode;
  
  private Integer pageIndex;
  
  private Integer pageSize;
  
  private String productNo;
  
  private String styleNo;
  
  public String getBrandNo() {
    return brandNo;
  }

  public void setBrandNo(String brandNo) {
    this.brandNo = brandNo;
  }

  public String getCatNo() {
    return catNo;
  }

  public void setCatNo(String catNo) {
    this.catNo = catNo;
  }

  public String getYears() {
    return years;
  }

  public void setYears(String years) {
    this.years = years;
  }

  public String getCommodityStatus() {
    return commodityStatus;
  }

  public void setCommodityStatus(String commodityStatus) {
    this.commodityStatus = commodityStatus;
  }

  public String getOrderDistributionSide() {
    return orderDistributionSide;
  }

  public void setOrderDistributionSide(String orderDistributionSide) {
    this.orderDistributionSide = orderDistributionSide;
  }

  public String getThirdPartyCode() {
    return thirdPartyCode;
  }

  public void setThirdPartyCode(String thirdPartyCode) {
    this.thirdPartyCode = thirdPartyCode;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getStyleNo() {
    return styleNo;
  }

  public void setStyleNo(String styleNo) {
    this.styleNo = styleNo;
  }

  @Override
  public Map<String, String> getApiParams() {
    YougouApiParamMap apiParamMap = new YougouApiParamMap();
    apiParamMap.put("brand_no", getBrandNo());
    apiParamMap.put("cat_no", getCatNo());
    apiParamMap.put("years", getYears());
    apiParamMap.put("commondity_status", getCommodityStatus());
    apiParamMap.put("order_distribution_side", getOrderDistributionSide());
    apiParamMap.put("third_party_code", getThirdPartyCode());
    apiParamMap.put("page_index", getPageIndex() + "");
    apiParamMap.put("page_size", getPageSize() + "");
    apiParamMap.put("product_no", getProductNo());
    apiParamMap.put("style_no", getStyleNo());
    
    return apiParamMap;
  }

  @Override
  public String getApiMethodName() {
    return "yougou.inventory.query";
  }

  @Override
  public Class<InventoryQueryResponse> getResponseClass() {
    return InventoryQueryResponse.class;
  }

}
