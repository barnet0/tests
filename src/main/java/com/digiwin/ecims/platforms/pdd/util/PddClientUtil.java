package com.digiwin.ecims.platforms.pdd.util;

import com.digiwin.ecims.platforms.pdd.bean.PddClient;

public final class PddClientUtil {

  private static volatile PddClientUtil pddClientUtil;
  
  private PddClientUtil() {
  }
  
  public static PddClientUtil getInstance() {
    if (pddClientUtil == null) {
      synchronized (PddClientUtil.class) {
        if (pddClientUtil == null) {
          pddClientUtil = new PddClientUtil();
        }
      }
    }
    return pddClientUtil;
  }
  
  private static volatile PddClient pddClient;
  
  public PddClient getPddClient(String serverUrl, String appKey, String appSecret) {
    if (pddClient == null) {
      synchronized (PddClientUtil.class) {
        if (pddClient == null) {
          pddClient = new PddClient(serverUrl, appKey, appSecret);
        }
      }
    } else {
      if (serverUrl.equals(pddClient.getServerUrl()) && 
          appKey.equals(pddClient.getuCode()) && 
          appSecret.equals(pddClient.getSecret())) {
        
      } else {
        synchronized (PddClientUtil.class) {
          pddClient.setServerUrl(serverUrl);
          pddClient.setuCode(appKey);
          pddClient.setSecret(appSecret);
        }
      }
      
    }
    return pddClient;
  }
  /**
   * 2017.5.24 
   * @param theString
   * @return
   */
  public String decodeUnicode(String asciicode) {      
	  String[] asciis = asciicode.split("\\\\u");
      String nativeValue = asciis[0];
      try {
          for (int i = 1; i < asciis.length; i++) {
              String code = asciis[i];
              nativeValue += (char) Integer.parseInt(code.substring(0, 4), 16);
              if (code.length() > 4) {
                  nativeValue += code.substring(4, code.length());
              }
          }
      } catch (NumberFormatException e) {
          return asciicode;
      }
      return nativeValue;
  }
  
  /**
   * 过滤utf8mb4
   * @param str
   * @return
   */
  public String filterUtf8mb4(String str) {
      final int LAST_BMP = 0xFFFF;
      StringBuilder sb = new StringBuilder(str.length());
      for (int i = 0; i < str.length(); i++) {
          int codePoint = str.codePointAt(i);
          if (codePoint < LAST_BMP) {
              sb.appendCodePoint(codePoint);
          } else {
              i++;
          }
      }
      return sb.toString();
  }
}
