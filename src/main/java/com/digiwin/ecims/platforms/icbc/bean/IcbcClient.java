package com.digiwin.ecims.platforms.icbc.bean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.XmlUtils;
import com.digiwin.ecims.platforms.icbc.bean.base.BasicRequest;
import com.digiwin.ecims.platforms.icbc.bean.encrypt.EncryptFactory;
import com.digiwin.ecims.platforms.icbc.service.api.impl.IcbcApiServiceImpl;

public class IcbcClient {
  /**
   * 版本
   */
  public static final String VERSION = "1.0";
  /**
   * 請求格式
   */
  public static final String FORMAT = "xml";

  private String url;
  private String sign;
  private String timestamp;
  private String version;
  private String appkey;
  private String method;
  private String format;
  private String reqSid;
  private String authCode;
  private String reqData;

  private String appSecret;

  /**
   * constructor
   * 
   * @param URL 接口URL
   * @param appKey app key
   * @param appSecret app secret
   */
  public IcbcClient(String URL, String appKey, String appSecret) {
    super();
    this.url = URL;
    this.sign = null;
    this.timestamp = DateTimeTool.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSSSSS");
    this.version = VERSION;
    this.appkey = appKey;
    this.method = null;
    this.format = FORMAT;
    this.reqSid = DateTimeTool.format(new Date(), "yyyyMMddHHmmssSSSS");
    this.authCode = null;
    this.reqData = null;
    this.appSecret = appSecret;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getSign() {
    return sign;
  }

  public void setSign(String sign) {
    this.sign = sign;
  }

  public String getTimestamp() {
    String str = timestamp.replace(" ", "+");
    str = str.replace(":", "%3A");
    return str;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getAppkey() {
    return appkey;
  }

  public void setAppkey(String appkey) {
    this.appkey = appkey;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getReqSid() {
    return reqSid;
  }

  public void setReqSid(String reqSid) {
    this.reqSid = reqSid;
  }

  public String getAuthCode() {
    return authCode;
  }

  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }

  public String getReqData() {
    return reqData;
  }

  public void setReqData(String reqData) {
    this.reqData = reqData;
  }

  public String getAppSecret() {
    return appSecret;
  }

  public void setAppSecret(String appSecret) {
    this.appSecret = appSecret;
  }

  @Override
  public String toString() {
    return "sign=" + getSign() + "&timestamp=" + getTimestamp() + "&version=" + getVersion()
        + "&app_key=" + getAppkey() + "&method=" + getMethod() + "&format=" + getFormat()
        + "&req_sid=" + getReqSid() + "&auth_code=" + getAuthCode() + "&req_data=" + getReqData();
  }

  public Map<String, String> toFieldMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("sign", getSign());
    map.put("timestamp", getTimestamp());
    map.put("version", getVersion());
    map.put("app_key", getAppkey());
    map.put("method", getMethod());
    map.put("format", getFormat());
    map.put("req_sid", getReqSid());
    map.put("auth_code", getAuthCode());
    map.put("req_data", getReqData());
    return map;
  }

  public String execute(Object request, String accessToken) throws Exception {
    XmlUtils xmlUtils = XmlUtils.getInstance();
    this.setReqData(IcbcApiServiceImpl.XML_TITLE + xmlUtils.javaBean2Xml(request));
    this.setAuthCode(accessToken);
    this.setSign(EncryptFactory.getEncrypt("HMACSHA256").sign(this.getAppkey(), this.getAuthCode(),
        this.getReqData(), this.getAppSecret()));// 可能有錯
    this.setMethod(((BasicRequest) request).getMethod());

    // System.out.println("SIGN: " + this.getSign());
    // System.out.println("Medhod: " + this.getMethod());

    // return HttpPostUtils.getInstance().send_common(getUrl(), this.toFieldMap());

    // ICBC 需經過SSL 加密, 才能下常執行 edit by xavier on 20150826
    return HttpPostUtils.getInstance().send_commonWithSSL(getUrl(), this.toFieldMap(),
        "ssl/icbc.truststore", "ebankpass");
  }
}
