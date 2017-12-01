package com.digiwin.ecims.platforms.jingdong.util;

import java.util.HashMap;
import java.util.Map;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;

/**
 * 京东客户端获取工具。单例
 * @author 维杰
 *
 */
public final class JingdongClientUtil {

  private volatile static JingdongClientUtil jingdongClientUtil;
  
  private JingdongClientUtil() {}
  
  public static JingdongClientUtil getInstance() {
    if (jingdongClientUtil == null) {
      synchronized (JingdongClientUtil.class) {
        if (jingdongClientUtil == null) {
          jingdongClientUtil = new JingdongClientUtil();
        }
      }
    }
    return jingdongClientUtil;
  }
  /*
   * 由于京东客户端的设计在创建时会绑定access_token，所以这里不适合使用单例。
   * 只适合使用工厂模式。
   */
  
//  private volatile static JdClient jdClient;
//  
//  public JdClient getJdClient(String serverUrl, String appKey, String appSecret, String accessToken) {
//    if (jdClient == null) {
//      synchronized (JingdongClientUtil.class) {
//       if (jdClient == null) {
//         jdClient = new DefaultJdClient(serverUrl, accessToken, appKey, appSecret);
//       }
//      }
//    }
//    return jdClient;
//  }
  
  /**
   * 根据access_token返回对应的JdClient
   */
  private volatile static Map<String, JdClient> jdClientMap = new HashMap<String, JdClient>(); 
  
  public JdClient getJdClient(String serverUrl, String appKey, String appSecret, String accessToken) {
    JdClient jdClient = null;
    if (jdClientMap.containsKey(accessToken)) {
      jdClient = jdClientMap.get(accessToken);
    } else {
      jdClient = new DefaultJdClient(serverUrl, accessToken, appKey, appSecret, 
          JingdongCommonTool.JD_API_CONNECT_TIMEOUT, JingdongCommonTool.JD_API_READ_TIMEOUT);
      jdClientMap.put(accessToken, jdClient);
    }
    return jdClient;
  }
}
