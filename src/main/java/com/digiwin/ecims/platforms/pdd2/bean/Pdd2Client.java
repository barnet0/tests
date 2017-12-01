package com.digiwin.ecims.platforms.pdd2.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.pdd2.bean.response.Pdd2BaseResponse;
import com.digiwin.ecims.platforms.pdd.util.PddClientUtil;
import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2ClientUtil;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2Util;

public class Pdd2Client {

  private String serverUrl;
  
  private String mall_id;
  
  private String type;

  private String secret;
  
  private String data_type;
  
  private static final Logger logger = LoggerFactory.getLogger(Pdd2Client.class);
  
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
   * @return the mall_id 接入码。用于验证请求的有效性,主要用于区分店铺。
   */
  public String getmall_id() {
    return mall_id;
  }

  /**
   * @param mall_id the mall_id to set
   */
  public void setmall_id(String mall_id) {
    this.mall_id = mall_id;
  }

  /**
   * @return the type 方法名  
   */
  public String gettype() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void settype(String type) {
    this.type = type;
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
   * @return the data_type 返回数据的格式。
   */
  public String getdata_type() {
    return data_type;
  }

  /**
   * @param data_type the data_type to set XML或者JSON（二选一）必填
   */
  public void setdata_type(String data_type) {
    this.data_type = data_type;
  }

  public Pdd2Client() {
    super();
  }
  
  public Pdd2Client(String serverUrl, String appKey, String appSecret) {
    this(serverUrl, appKey, appSecret, "JSON");
  }
  
  public Pdd2Client(String serverUrl, String appKey, String appSecret, String format) {
    super();
    this.serverUrl = serverUrl;
    this.mall_id = appKey;
    this.secret = appSecret;
    this.data_type = format;
  }
  
  public Map<String, String> getProtocolParams() {
    Map<String, String> protocolParams = new HashMap<String, String>();
    protocolParams.put("mall_id", getmall_id());
    protocolParams.put("type", gettype());
    protocolParams.put("timestamp", DateTimeTool.getTodayByTimestamp());

    return protocolParams;
  }
  
  public <T extends Pdd2BaseResponse> T execute(Pdd2BaseRequest<T> request) throws Exception {
    settype(request.getMType());
    
    Map<String, String> params = new HashMap<String, String>();
    params.putAll(getProtocolParams());
    
    
    
    params.put("data_type", getdata_type());
    params.putAll(request.getApiParams());
    
    
 // 生成签名
    params.put("sign", Pdd2Util.sign(params, getSecret()));
    // 调用 API
    String result = HttpPostUtils.getInstance().send_common_after_url(getServerUrl(), params);
    
    T pddRsp = null;
    JsonNode rootNode = null;
    JsonNode responseNode = null;
  
    //2017.5.26 临时方式过滤utf8mb4范围的
    result=PddClientUtil.getInstance().filterUtf8mb4(result);
    //logger.info("result:{}",result);
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
        Integer resultCode = responseNode.get("error_code").intValue();
        String cause = responseNode.get("error_msg").textValue();
        pddRsp = request.getResponseClass().newInstance();
        pddRsp.setError_code(resultCode);
        pddRsp.setError_msg(cause);
        pddRsp.setResponseBody(result);
      }
    }
    
    return pddRsp;
    
  }
  
}
