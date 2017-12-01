package com.digiwin.ecims.platforms.aliexpress.bean.domain.item;

/**
 * 商品类目信息
 * 
 * @author 维杰
 *
 */
public class AeopAeProductPropertys {

  // 否 属性名ID。从类目属性接口getAttributesResultByCateId获取普通类目属性，不可填入sku属性。 自定义属性名时,该项不填. 200000043
  private Integer attrNameId;

  // 否 自定义属性名属性名。 自定义属性名时,该项必填. size
  private String attrName;

  // 否 属性值ID。从类目属性接口getAttributesResultByCateId获取普通类目属性，不可填入sku属性。自定义属性值时,该项不填。 581
  private Integer attrValueId;

  // 否 自定义属性值。自定义属性名时,该项必填。 当自定义属性值内容为区间情况时，建议格式2 - 5 kg。(注意，数字'-'单位三者间是要加空格的！) 2 - 5 kg
  private String attrValue;

  public Integer getAttrNameId() {
    return attrNameId;
  }

  public void setAttrNameId(Integer attrNameId) {
    this.attrNameId = attrNameId;
  }

  public String getAttrName() {
    return attrName;
  }

  public void setAttrName(String attrName) {
    this.attrName = attrName;
  }

  public Integer getAttrValueId() {
    return attrValueId;
  }

  public void setAttrValueId(Integer attrValueId) {
    this.attrValueId = attrValueId;
  }

  public String getAttrValue() {
    return attrValue;
  }

  public void setAttrValue(String attrValue) {
    this.attrValue = attrValue;
  }


}
