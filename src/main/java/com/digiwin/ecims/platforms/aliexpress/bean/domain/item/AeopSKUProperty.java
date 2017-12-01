package com.digiwin.ecims.platforms.aliexpress.bean.domain.item;

/**
 * SKU属性对象
 * @author 维杰
 *
 */
public class AeopSKUProperty {

  private Integer skuPropertyId;
  
  private Integer propertyValueId;
  
  private String propertyValueDefinitionName;
  
  private String skuImage;

  public Integer getSkuPropertyId() {
    return skuPropertyId;
  }

  public void setSkuPropertyId(Integer skuPropertyId) {
    this.skuPropertyId = skuPropertyId;
  }

  public Integer getPropertyValueId() {
    return propertyValueId;
  }

  public void setPropertyValueId(Integer propertyValueId) {
    this.propertyValueId = propertyValueId;
  }

  public String getPropertyValueDefinitionName() {
    return propertyValueDefinitionName;
  }

  public void setPropertyValueDefinitionName(String propertyValueDefinitionName) {
    this.propertyValueDefinitionName = propertyValueDefinitionName;
  }

  public String getSkuImage() {
    return skuImage;
  }

  public void setSkuImage(String skuImage) {
    this.skuImage = skuImage;
  }
  
  
}
