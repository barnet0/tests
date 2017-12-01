package com.digiwin.ecims.platforms.beibei.bean.request;

import java.util.Map;

import com.digiwin.ecims.platforms.beibei.bean.response.BeibeiBaseResponse;

public abstract class BeibeiBaseRequest<T extends BeibeiBaseResponse> {

  /**
   * 获取API需要参数的key-value图
   * 
   * @return API参数的Map
   */
  public abstract Map<String, String> getApiParams();

  /**
   * 获取API的具体名称
   * 
   * @return API名称
   */
  public abstract String getMethod();

  /**
   * 获取请求类对应的响应类的Class实例
   * 
   * @return Class实例
   */
  public abstract Class<T> getResponseClass();

}
