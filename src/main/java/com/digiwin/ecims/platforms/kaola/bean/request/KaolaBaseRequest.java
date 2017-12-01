package com.digiwin.ecims.platforms.kaola.bean.request;

import java.util.Map;

import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;

/**
 * 
 * @author cjp 2017/5/26
 *
 * @param <T>
 */
public abstract class KaolaBaseRequest<T extends KaolaBaseResponse> {

  protected final Integer TIMSTAMP_LENGTH = 10;
  
  /**
   * 获取API需要参数的key-value图
   * @return API参数的Map
   */
  public abstract Map<String, String> getApiParams();

  /**
   * 获取API的具体名称
   * @return API名称
   */
  public abstract String getMType();

  /**
   * 获取请求类对应的响应类的Class实例
   * @return Class实例
   */
  public abstract Class<T> getResponseClass();
  
}
