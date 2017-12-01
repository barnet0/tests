package com.digiwin.ecims.platforms.aliexpress.bean.request.base;

import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public abstract class AliExpressBaseRequest<T extends AliExpressBaseResponse> {

  protected static final String API_NAMESPACE = "aliexpress.open";
  
  protected static final String API_PROTOCOL = "param2";
  
  protected static final String API_VERSION = "1";
  
  public String getApiUrl() {
    return getProtocol() + "/" + getVersion() + "/" + getNameSpace() + "/" + getApiName();
  }

  protected static final String getProtocol() {
    return AliExpressBaseRequest.API_PROTOCOL;
  }
  
  protected static final String getVersion() {
    return AliExpressBaseRequest.API_VERSION;
  };
  
  protected final String getNameSpace() {
    return AliExpressBaseRequest.API_NAMESPACE;
  };
  
  protected abstract String getApiName();
  
  public abstract Map<String, String> getApiParams();
  
  public abstract Class<T> getResponseClass();
}
