package com.digiwin.ecims.core.util;

import java.util.Date;
import java.util.HashMap;

public class EcImsApiHashMap<K,V> extends HashMap<String, String> {

  /**
   * 
   */
  static final long serialVersionUID = -2161344178069169685L;

  public String put(String key, Object value) {
    String strValue;
    if (value == null) {
      strValue = null;
    } else {
      if (value instanceof String) {
        strValue = (String)value;
      } else if (value instanceof Boolean) {
        strValue = Boolean.toString((boolean)value);
      } else if (value instanceof Date) {
        strValue = DateTimeTool.format((Date)value);
      } else {
        strValue = value + "";
      }
    }
    return put(key, strValue);
  }
  
  @Override
  public String put(String key, String value) {
    if (StringTool.isNotEmpty(value)) {
      return super.put(key, value);
    } else {
      return null;
    }
  }
}
