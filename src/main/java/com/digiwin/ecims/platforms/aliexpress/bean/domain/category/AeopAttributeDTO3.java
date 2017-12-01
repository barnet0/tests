package com.digiwin.ecims.platforms.aliexpress.bean.domain.category;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AeopAttributeDTO3 implements Serializable {

/**
   * 
   */
  private static final long serialVersionUID = -7160152065263513982L;

  //  否   发布属性展现样式
  private String attributeShowTypeValue;
  
//  否   sku属性是否可自定义名称
  private Boolean customizedName;
  
//  否   sku属性是否可自定义图片
  private Boolean customizedPic;
  
//  否   属性id
  private Integer id;
  
//  否   文本输入框型属性输入格式（文本|数字）
  private String inputType; 
  
//  否   发布属性是否关键
  private Boolean keyAttribute;
  
//  否   属性名称
  private Map<String, String> names;
  
//  否   发布属性是否必填
  private Boolean required;
  
//  否   发布属性是否是sku
  private Boolean sku;
  
//  否   sku属性展现样式（色卡|普通）
  private String skuStyleValue;
  
//  否   sku维度（1维~3维）
  private Integer spec;
  
//  否   发布属性单位
  private List<AeopUnit> units;
  
//  否   发布属性值
  private List<AeAttrValueDTO3> values;
  
  private Boolean visible;

  public String getAttributeShowTypeValue() {
    return attributeShowTypeValue;
  }

  public void setAttributeShowTypeValue(String attributeShowTypeValue) {
    this.attributeShowTypeValue = attributeShowTypeValue;
  }

  public Boolean getCustomizedName() {
    return customizedName;
  }

  public void setCustomizedName(Boolean customizedName) {
    this.customizedName = customizedName;
  }

  public Boolean getCustomizedPic() {
    return customizedPic;
  }

  public void setCustomizedPic(Boolean customizedPic) {
    this.customizedPic = customizedPic;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getInputType() {
    return inputType;
  }

  public void setInputType(String inputType) {
    this.inputType = inputType;
  }

  public Boolean getKeyAttribute() {
    return keyAttribute;
  }

  public void setKeyAttribute(Boolean keyAttribute) {
    this.keyAttribute = keyAttribute;
  }

  public Map<String, String> getNames() {
    return names;
  }

  public void setNames(Map<String, String> names) {
    this.names = names;
  }

  public Boolean getRequired() {
    return required;
  }

  public void setRequired(Boolean required) {
    this.required = required;
  }

  public Boolean getSku() {
    return sku;
  }

  public void setSku(Boolean sku) {
    this.sku = sku;
  }

  public String getSkuStyleValue() {
    return skuStyleValue;
  }

  public void setSkuStyleValue(String skuStyleValue) {
    this.skuStyleValue = skuStyleValue;
  }

  public Integer getSpec() {
    return spec;
  }

  public void setSpec(Integer spec) {
    this.spec = spec;
  }

  public List<AeopUnit> getUnits() {
    return units;
  }

  public void setUnits(List<AeopUnit> units) {
    this.units = units;
  }

  public List<AeAttrValueDTO3> getValues() {
    return values;
  }

  public void setValues(List<AeAttrValueDTO3> values) {
    this.values = values;
  }

  public Boolean getVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }
  
}
