package com.digiwin.ecims.platforms.aliexpress.bean.domain.category;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AeAttrValueDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -2077916371734613810L;

  // 否 属性值id
  private Integer id;

  // 否 属性值名称
  private Map<String, String> names;

  // 否 下一层属性
  private List<AeopAttributeDTO2> attributes;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Map<String, String> getNames() {
    return names;
  }

  public void setNames(Map<String, String> names) {
    this.names = names;
  }

  public List<AeopAttributeDTO2> getAttributes() {
    return attributes;
  }

  public void setAttributes(List<AeopAttributeDTO2> attributes) {
    this.attributes = attributes;
  }

}
