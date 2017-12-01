package com.digiwin.ecims.platforms.yougou.bean.request.base;

import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.response.base.YougouBaseResponse;


/**
 * 优购网API调用基础请求类
 * @author 维杰
 *
 */
public abstract class YougouBaseRequest<T extends YougouBaseResponse> {

  /**
   * 获取API需要参数的key-value图
   * @return API参数的Map
   */
  public abstract Map<String, String> getApiParams();

  /**
   * 获取API的具体名称
   * @return API名称
   */
  public abstract String getApiMethodName();

  /**
   * 获取请求类对应的响应类的Class实例
   * @return Class实例
   */
  public abstract Class<T> getResponseClass();
  
  
}
