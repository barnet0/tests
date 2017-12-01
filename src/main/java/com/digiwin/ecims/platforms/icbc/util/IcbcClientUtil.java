package com.digiwin.ecims.platforms.icbc.util;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.icbc.bean.IcbcClient;

public final class IcbcClientUtil {

  private volatile static IcbcClientUtil icbcClientUtil;
  
  private IcbcClientUtil() {
    
  }
  
  public static IcbcClientUtil getInstance() {
    if (icbcClientUtil == null) {
      synchronized (IcbcClientUtil.class) {
        if (icbcClientUtil == null) {
          icbcClientUtil = new IcbcClientUtil();
        }
      }
    }
    return icbcClientUtil;
  }
  
  /**
   * 根据access_token返回对应的JdClient
   */
  private volatile static Map<String, IcbcClient> icbcClientMap = new HashMap<String, IcbcClient>(); 
  
  public IcbcClient getIcbcClient(String serverUrl, String appKey, String appSecret, String accessToken) {
    IcbcClient icbcClient = null;
    if (icbcClientMap.containsKey(accessToken)) {
      icbcClient = icbcClientMap.get(accessToken);
      icbcClient.setUrl(serverUrl);
    } else {
      icbcClient = new IcbcClient(serverUrl, appKey, appSecret);
      icbcClientMap.put(accessToken, icbcClient);
    }
    return icbcClient;
  }
}
