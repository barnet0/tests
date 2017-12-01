package com.digiwin.ecims.platforms.pdd.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.pdd.bean.request.PddBaseRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.PddBaseResponse;
import com.digiwin.ecims.platforms.pdd.util.PddClientUtil;
import com.digiwin.ecims.platforms.pdd.util.PddUtil;


public class PddClient {

  private String serverUrl;
  
  private String uCode;
  
  private String mType;

  private String secret;
  
  private String dType;
  
  /**
   * @return the serverUrl 
   */
  public String getServerUrl() {
    return serverUrl;
  }

  /**
   * @param serverUrl the serverUrl to set
   */
  public void setServerUrl(String serverUrl) {
    this.serverUrl = serverUrl;
  }

  /**
   * @return the uCode 接入码。用于验证请求的有效性,主要用于区分店铺。
   */
  public String getuCode() {
    return uCode;
  }

  /**
   * @param uCode the uCode to set
   */
  public void setuCode(String uCode) {
    this.uCode = uCode;
  }

  /**
   * @return the mType 方法名  
   */
  public String getmType() {
    return mType;
  }

  /**
   * @param mType the mType to set
   */
  public void setmType(String mType) {
    this.mType = mType;
  }

  /**
   * @return the secret
   */
  public String getSecret() {
    return secret;
  }

  /**
   * @param secret the secret to set
   */
  public void setSecret(String secret) {
    this.secret = secret;
  }

  /**
   * @return the dType 返回数据的格式。
   */
  public String getdType() {
    return dType;
  }

  /**
   * @param dType the dType to set XML或者JSON（二选一）必填
   */
  public void setdType(String dType) {
    this.dType = dType;
  }

  public PddClient() {
    super();
  }
  
  public PddClient(String serverUrl, String appKey, String appSecret) {
    this(serverUrl, appKey, appSecret, "JSON");
  }
  
  public PddClient(String serverUrl, String appKey, String appSecret, String format) {
    super();
    this.serverUrl = serverUrl;
    this.uCode = appKey;
    this.secret = appSecret;
    this.dType = format;
  }
  
  public Map<String, String> getProtocolParams() {
    Map<String, String> protocolParams = new HashMap<String, String>();
    protocolParams.put("uCode", getuCode());
    protocolParams.put("mType", getmType());
    protocolParams.put("timeStamp", DateTimeTool.getTodayByTimestamp());

    return protocolParams;
  }
  
  public <T extends PddBaseResponse> T execute(PddBaseRequest<T> request) throws Exception {
    setmType(request.getMType());
    
    Map<String, String> params = new HashMap<String, String>();
    params.putAll(getProtocolParams());
    
    // 生成签名
    params.put("sign", PddUtil.sign(params, getSecret()));
    
    params.put("dType", getdType());
    params.putAll(request.getApiParams());
    
    // 调用 API
    String result = HttpPostUtils.getInstance().send_common_after_url(getServerUrl(), params);
    
    T pddRsp = null;
    JsonNode rootNode = null;
    JsonNode responseNode = null;

  //2017.5.26 临时方式过滤utf8mb4范围的
    result=PddClientUtil.getInstance().filterUtf8mb4(result);
    try {
      rootNode = JsonUtil.DEFAULT_OBJECT_MAPPER.readTree(result);
      Iterator<Entry<String, JsonNode>> rootIter = rootNode.fields();
      while (rootIter.hasNext()) {
        Map.Entry<String, JsonNode> curEntry = rootIter.next();
        if (curEntry.getValue().isObject()) {
          responseNode = curEntry.getValue();
          break;
        }
      }
      pddRsp = JsonUtil.jsonToObject(responseNode.toString(), request.getResponseClass());
      pddRsp.setResponseBody(result);
    } catch (Exception e) {
      if (responseNode != null) {
        Integer resultCode = responseNode.get("result").intValue();
        String cause = responseNode.get("cause").textValue();
        pddRsp = request.getResponseClass().newInstance();
        pddRsp.setResult(resultCode);
        pddRsp.setCause(cause);
        pddRsp.setResponseBody(result);
      }
    }
    
    return pddRsp;
    
  }
}
