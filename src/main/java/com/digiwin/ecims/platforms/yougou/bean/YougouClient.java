package com.digiwin.ecims.platforms.yougou.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.base.YougouBaseResponse;
import com.digiwin.ecims.platforms.yougou.util.YougouUtil;

public class YougouClient {

  private String serverUrl;

  // 必填 优购分配给商家应用的APP_KEY
  private String appKey;

  private String appSecret;
  // 必填 API接口名称
  private String method;

  // 必填 时间戳，格式为yyyy-MM-dd HH:mm:ss允许与优购系统时间误差正负6分钟，例如：2012-03-25 20:00:00
  private String timeStamp;

  // 必填 API输入参数签名结果
  private String sign;

  // 可选，指定签名方式。系统默认md5，支持方式有：md5、sha-1
  private String signMethod = "md5";

  // 可选，指定API版本。系统默认1.0，支持版本有：1.0
  private String appVersion = "1.0";

  // 可选，指定响应格式。系统默认xml，支持响应格式有：xml、json
  private String format = "json";

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

  protected String getAppVersion() {
    return appVersion;
  }

  protected void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  protected String getFormat() {
    return format;
  }

  protected void setFormat(String format) {
    this.format = format;
  }

  public YougouClient() {
    super();
  }

  public YougouClient(String serverUrl, String appKey, String appSecret) {
    this(serverUrl, appKey, appSecret, "json", "md5");
  }

  public YougouClient(String serverUrl, String appKey, String appSecret, String format,
      String signMethod) {
    super();
    this.serverUrl = serverUrl;
    this.appKey = appKey;
    this.appSecret = appSecret;
    this.format = format;
    this.signMethod = signMethod;
    this.timeStamp = DateTimeTool.format(new Date());
  }

  public Map<String, String> getProtocolParams() {
    Map<String, String> protocolParams = new HashMap<String, String>();
    protocolParams.put("method", getMethod());
    protocolParams.put("timestamp", getTimeStamp());
    protocolParams.put("app_key", getAppKey());
    protocolParams.put("sign_method", getSignMethod());
    protocolParams.put("app_version", getAppVersion());
    protocolParams.put("format", getFormat());

    return protocolParams;
  }

  public <T extends YougouBaseResponse> T execute(YougouBaseRequest<T> request) throws Exception {
    setMethod(request.getApiMethodName());
    
    Map<String, String> params = new HashMap<String, String>();
    params.putAll(this.getProtocolParams());
    params.putAll(request.getApiParams());
    // 生成签名
    String sign = "";
    if (getSignMethod().toLowerCase().equals("md5")) {
        sign = YougouUtil.sign(params, this.appSecret, true);
    } else {
        sign = YougouUtil.sign(params, this.appSecret, false);
    }
    setSign(sign);
    params.put("sign", getSign());
    
    // 调用API
    String result = HttpPostUtils.getInstance().send_common_after_url(getServerUrl(), params);

//    System.out.println(result);
    
    ObjectMapper mapper = new ObjectMapper();
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    T yougouRsp = null;
    JsonNode rootNode = null;
    JsonNode responseNode = null;
    try {
        rootNode = mapper.readTree(result);
        Iterator<Entry<String, JsonNode>> rootIter = rootNode.fields();
        while (rootIter.hasNext()) {
            Map.Entry<String, JsonNode> curEntry = rootIter.next();
            if (curEntry.getValue().isObject()) {
                responseNode = curEntry.getValue();
                break;
            }
        }
        yougouRsp = mapper.readValue(responseNode.toString(), request.getResponseClass());
    } catch (RuntimeException e) {
      if (responseNode != null) {
        String code = responseNode.get("code").textValue();
        String message = responseNode.get("message").textValue();
        yougouRsp = request.getResponseClass().newInstance();
        yougouRsp.setCode(code);
        yougouRsp.setMessage(message);
      }
    }
    return yougouRsp;
  }
  
}
