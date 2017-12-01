package com.digiwin.ecims.platforms.taobao.bean.api;

import com.taobao.api.ApiException;
import com.taobao.api.AutoRetryClusterTaobaoClient;

public class MyAutoRetryClusterTaobaoClient extends AutoRetryClusterTaobaoClient {

  public MyAutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret)
      throws ApiException {
    super(serverUrl, appKey, appSecret);
    // TODO Auto-generated constructor stub
  }

  public MyAutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret,
      String format, int connectTimeout, int readTimeout, String signMethod) throws ApiException {
    super(serverUrl, appKey, appSecret, format, connectTimeout, readTimeout, signMethod);
    // TODO Auto-generated constructor stub
  }

  public MyAutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret,
      String format, int connectTimeout, int readTimeout) throws ApiException {
    super(serverUrl, appKey, appSecret, format, connectTimeout, readTimeout);
    // TODO Auto-generated constructor stub
  }

  public MyAutoRetryClusterTaobaoClient(String serverUrl, String appKey, String appSecret,
      String format) throws ApiException {
    super(serverUrl, appKey, appSecret, format);
    // TODO Auto-generated constructor stub
  }

  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }
}
