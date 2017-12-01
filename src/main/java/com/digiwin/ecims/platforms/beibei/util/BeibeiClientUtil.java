package com.digiwin.ecims.platforms.beibei.util;

import com.digiwin.ecims.platforms.beibei.bean.BeibeiClient;
import com.digiwin.ecims.core.util.StringTool;

public final class BeibeiClientUtil {

  private static volatile BeibeiClientUtil beibeiClientUtil;
  
  private BeibeiClientUtil() {
    
  }
  
  public static BeibeiClientUtil getInstance() {
    if (beibeiClientUtil == null) {
      synchronized (BeibeiClientUtil.class) {
        if (beibeiClientUtil == null) {
          beibeiClientUtil = new BeibeiClientUtil();
        }
      }
    }
    return beibeiClientUtil;
  }
  
  private static volatile BeibeiClient beibeiClient;
  
  public BeibeiClient getBeibeiClient(String serverUrl, String appKey, String appSecret) {
    if (beibeiClient == null) {
      synchronized (BeibeiClientUtil.class) {
       if (beibeiClient == null) {
         beibeiClient = new BeibeiClient(serverUrl, appKey, appSecret);
       }
      }
    } else {
      if (serverUrl.equals(beibeiClient.getServerUrl()) && 
          appKey.equals(beibeiClient.getAppId()) && 
          appSecret.equals(beibeiClient.getAppSecret())) {
        
      } else {
        synchronized (BeibeiClientUtil.class) {
          beibeiClient.setServerUrl(serverUrl);
          beibeiClient.setAppId(appKey);
          beibeiClient.setAppSecret(appSecret);
        }  
      }
      
    }
    return beibeiClient;
  }
}
