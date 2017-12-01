package com.digiwin.ecims.platforms.yougou.util;

import java.util.HashMap;

public class YougouApiParamMap extends HashMap<String, String> {

  /**
   * 
   */
  private static final long serialVersionUID = -400974554060295891L;

  @Override
  public String put(String key, String value) {
    if (key != null && key.length() > 0
        && value != null && value.length() > 0) {
      return super.put(key, value);
    }
    return null;
  }
  

  
}
