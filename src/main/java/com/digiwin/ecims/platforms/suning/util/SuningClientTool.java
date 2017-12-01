package com.digiwin.ecims.platforms.suning.util;

import com.suning.api.DefaultSuningClient;

public final class SuningClientTool {

  private static volatile SuningClientTool suningClientTool;
  
  private SuningClientTool() {}
  
  public static SuningClientTool getInstance() {
    if (suningClientTool == null) {
      synchronized (SuningClientTool.class) {
        if (suningClientTool == null) {
          suningClientTool = new SuningClientTool();
        }
      }
    }
    return suningClientTool;
  }
  
  public DefaultSuningClient getSuningClient(
      String serverUrl, String appKey, String appSecret, String accessToken) {
    return new DefaultSuningClient(serverUrl, appKey, appSecret, accessToken, "json");
  }
}
