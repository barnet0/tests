package com.digiwin.ecims.platforms.pdd2.util;

import com.digiwin.ecims.platforms.pdd2.bean.Pdd2Client;

public final class Pdd2ClientUtil {

  private static volatile Pdd2ClientUtil pddClientUtil;
  
  private Pdd2ClientUtil() {
  }
  
  public static Pdd2ClientUtil getInstance() {
    if (pddClientUtil == null) {
      synchronized (Pdd2ClientUtil.class) {
        if (pddClientUtil == null) {
          pddClientUtil = new Pdd2ClientUtil();
        }
      }
    }
    return pddClientUtil;
  }
  
  private static volatile Pdd2Client pddClient;
  
  public Pdd2Client getPdd2Client(String serverUrl, String appKey, String appSecret) {
    if (pddClient == null) {
      synchronized (Pdd2ClientUtil.class) {
        if (pddClient == null) {
          pddClient = new Pdd2Client(serverUrl, appKey, appSecret);
        }
      }
    } else {
      if (serverUrl.equals(pddClient.getServerUrl()) && 
          appKey.equals(pddClient.getmall_id()) && 
          appSecret.equals(pddClient.getSecret())) {
        
      } else {
        synchronized (Pdd2ClientUtil.class) {
          pddClient.setServerUrl(serverUrl);
          pddClient.setmall_id(appKey);
          pddClient.setSecret(appSecret);
        }
      }
      
    }
    return pddClient;
  }
  
}
