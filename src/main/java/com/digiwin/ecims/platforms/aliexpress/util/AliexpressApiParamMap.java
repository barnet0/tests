package com.digiwin.ecims.platforms.aliexpress.util;

import java.util.HashMap;

public class AliexpressApiParamMap extends HashMap<String, String> {

  /**
   * 
   */
  private static final long serialVersionUID = 7395257694909895011L;

  @Override
  public String put(String key, String value) {
    if (key != null && key.length() > 0 
        && value != null && value.length() > 0) {
      return super.put(key, value);
    }
    return null;
  }

  
}
