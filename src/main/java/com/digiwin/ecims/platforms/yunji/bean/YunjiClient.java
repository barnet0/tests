package com.digiwin.ecims.platforms.yunji.bean;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.yunji.bean.request.YunjiBaseRequest;
import com.digiwin.ecims.platforms.yunji.bean.request.order.OrderListApiParamJson;
import com.digiwin.ecims.platforms.yunji.bean.response.YunjiBaseResponse;
import com.digiwin.ecims.platforms.yunji.util.YunjiUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class YunjiClient {
	private String serverUrl;

	private String method;

	private String appkey;
	
	private String appSecret;

	private String v;

	private String param;

	private static final Logger logger = LoggerFactory.getLogger(YunjiClient.class);

	

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public YunjiClient() {
		super();
	}

	/*public KaolaClient(String serverUrl, String appKey, String appSecret) {
		this(serverUrl, appKey, appSecret, "JSON");
	}*/

	public YunjiClient(String serverUrl,String appKey,String secret,String version) {
		super();
		this.serverUrl = serverUrl;
		this.appkey = appKey;
		this.v = version;
		this.appSecret = secret;
	}

	public Map<String, String> getProtocolParams() {
		Map<String, String> protocolParams = new HashMap<String, String>();
		protocolParams.put("method", getMethod());
		protocolParams.put("appkey", getAppkey());
		protocolParams.put("v", getV());
		protocolParams.put("param", getParam());
		protocolParams.put("timestamp", DateTimeTool.getToday("yyyy-MM-dd HH:mm:ss")); 

		return protocolParams;
	}

	public <T extends YunjiBaseResponse> T execute(YunjiBaseRequest<T> request) throws Exception {
		setMethod(request.getMType());
		setParam(request.getApiParamsJson());
		//add by test
		logger.info("request object:" + getParam());
		
		Map<String, String> params = new HashMap<String, String>();
		params.putAll(getProtocolParams());

		// 生成签名
		params.put("sign", YunjiUtil.sign(params, getAppSecret()));
		logger.info("param:" + params.toString());
		// 调用 API
		String result = HttpPostUtils.getInstance().send_common(getServerUrl(), params);

		
	//	logger.info("result:" + result);
		T yunjiRsp = null;
		
		JSONObject   jobject =	
				JSON.parseObject(result);
		
		
		
		if("success".equalsIgnoreCase(jobject.getString("desc"))){

			yunjiRsp = JSON.parseObject(JSON.toJSONString( jobject.getJSONObject("data")), request.getResponseClass());
			
			System.out.println("==---> "+jobject.getJSONObject("data"));
			
			yunjiRsp.setResponseBody(JSON.toJSONString( jobject.getJSONObject("data")));
		}else{
			
			yunjiRsp = request.getResponseClass().newInstance();
			
		}
		
		
		
		yunjiRsp.setCode(jobject.getInteger("code"));
		yunjiRsp.setDesc(jobject.getString("desc"));
				
		return yunjiRsp;

	}
}
