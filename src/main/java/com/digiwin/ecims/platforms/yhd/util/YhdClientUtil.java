package com.digiwin.ecims.platforms.yhd.util;

import com.yhd.YhdClient;

public final class YhdClientUtil {

  private volatile static YhdClientUtil yhdClientUtil;
  
  private YhdClientUtil() {}
  
  public static YhdClientUtil getInstance() {
    if (yhdClientUtil == null) {
      synchronized (YhdClientUtil.class) {
        if (yhdClientUtil == null) {
          yhdClientUtil = new YhdClientUtil();
        }
      }
    }
    return yhdClientUtil;
  }
  
  /**
   * 这里使用工厂模式获取请求客户端.<br/>
   * 不使用伪单例模式的原因，是因为一号店SDK中客户端的设计有缺点，导致多次调用时，应用级参数会不断累加，影响之后的请求。
   * @param serverUrl
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @return
   */
  public YhdClient getYhdClient(String serverUrl, String appKey, String appSecret, String accessToken) {
    return new YhdClient(serverUrl, appKey, appSecret, accessToken, "json");
  }
}
