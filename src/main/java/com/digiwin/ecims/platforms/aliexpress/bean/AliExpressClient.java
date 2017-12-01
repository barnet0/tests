package com.digiwin.ecims.platforms.aliexpress.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.error.ErrorResponse;
import com.digiwin.ecims.platforms.aliexpress.util.AliexpressUtil;
import com.digiwin.ecims.core.util.HttpPostUtils;


/**
 * 速卖通API客户端
 * @author 维杰
 *
 */
public class AliExpressClient {

  private String serverUrl;

  // 创建app时，速卖通开放平台会为每个app生成一个appKey和与之对应的secretKey
  private String appKey;

  private String appSecret;
  // 必填 API接口名称
  private String method;

  // 必填 时间戳，格式为时间转换为毫秒的值
  private String timeStamp;

  // 必填 API输入参数签名结果
  private String sign;

  // 可选，指定签名方式。系统默认md5，支持方式有：md5、sha-1
  private String signMethod = "sha1";

  protected String getServerUrl() {
    return serverUrl;
  }

  protected void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  protected String getAppKey() {
    return appKey;
  }

  protected void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  protected String getAppSecret() {
    return appSecret;
  }

  protected void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  protected String getMethod() {
    return method;
  }

  protected void setMethod(String method) {
    this.method = method;
  }

  protected String getTimeStamp() {
    return timeStamp;
  }

  protected void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  protected String getSign() {
    return sign;
  }

  protected void setSign(String sign) {
    this.sign = sign;
  }

  protected String getSignMethod() {
    return signMethod;
  }

  protected void setSignMethod(String signMethod) {
    this.signMethod = signMethod;
  }

  public AliExpressClient() {
    
  }

  public AliExpressClient(String serverUrl, String appKey, String appSecret) {
    super();
    this.serverUrl = serverUrl;
    this.appKey = appKey;
    this.appSecret = appSecret;
  }
  
  private <T extends AliExpressBaseResponse> String getUrlPath(AliExpressBaseRequest<T> request) {
    return request.getApiUrl() + "/" + this.getAppKey();
  }
  
  public <T extends AliExpressBaseResponse> T execute(AliExpressBaseRequest<T> request, String accessToken) throws Exception {
    Map<String, String> params = new HashMap<String, String>();
    params.putAll(request.getApiParams());
    params.put("access_token", accessToken);
//    params.put("_aop_timestamp", System.currentTimeMillis() + "");
    String urlPath = getUrlPath(request);
    // 生成签名
    String sign = "";
    sign = AliexpressUtil.sign(urlPath, params, this.appSecret);
    setSign(sign);
    params.put("_aop_signature", getSign());
    
    // 调用API
    String result = HttpPostUtils.getInstance().send_common_after_url(this.serverUrl + urlPath, params);
//    System.out.println(result);
    
    ObjectMapper mapper = new ObjectMapper();
//    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
    T rsp = null;
    JsonNode rootNode = null;
    try {
        rootNode = mapper.readTree(result);
        rsp = mapper.readValue(rootNode.toString(), request.getResponseClass());
    } catch (RuntimeException e) {
      String code = rootNode.get("error_code").textValue();
      String message = rootNode.get("error_message").textValue();
      String exception = rootNode.get("exception").textValue();
      
      rsp = request.getResponseClass().newInstance();
      ErrorResponse errRsp = new ErrorResponse(code, message, exception);
      rsp.setErrorResponse(errRsp);
      
    }
    return rsp;
  }
  
}
