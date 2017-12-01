package com.digiwin.ecims.platforms.aliexpress.bean.domain.category;

import java.io.Serializable;
import java.util.Map;

public class AeAttrValueDTO3 implements Serializable {

/**
   * 
   */
  private static final long serialVersionUID = -4130596457892364711L;

  //  否   属性值id
  private String id;
  
//  否   属性值名称
  private Map<String, String> names;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, String> getNames() {
    return names;
  }

  public void setNames(Map<String, String> names) {
    this.names = names;
  }
  
  
}
