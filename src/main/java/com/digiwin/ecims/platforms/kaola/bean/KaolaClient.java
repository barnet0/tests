package com.digiwin.ecims.platforms.kaola.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.kaola.bean.response.KaolaBaseResponse;
import com.digiwin.ecims.platforms.kaola.bean.request.KaolaBaseRequest;
import com.digiwin.ecims.platforms.kaola.util.KaolaClientUtil;
import com.digiwin.ecims.platforms.kaola.util.KaolaUtil;

/**
 * 
 * @author cjp 2017/5/18 
 *
 */
public class KaolaClient {

	private String serverUrl;

	private String method;

	private String secret;

	private String app_key;

	private String access_token;

	private static final Logger logger = LoggerFactory.getLogger(KaolaClient.class);

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * @param serverUrl
	 *            the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 * @return the app_key 接入码。用于验证请求的有效性,主要用于区分店铺。
	 */
	public String getapp_key() {
		return app_key;
	}

	/**
	 * @param app_key
	 *            the app_key to set
	 */
	public void setapp_key(String app_key) {
		this.app_key = app_key;
	}

	/**
	 * @return access_tocke
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * 
	 * @param access_token
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	/**
	 * @return the type 方法名
	 */
	public String getmethod() {
		return method;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setmethod(String method) {
		this.method = method;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret
	 *            the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public KaolaClient() {
		super();
	}

	/*public KaolaClient(String serverUrl, String appKey, String appSecret) {
		this(serverUrl, appKey, appSecret, "JSON");
	}*/

	public KaolaClient(String serverUrl, String appKey, String appSecret, String access_token) {
		super();
		this.serverUrl = serverUrl;
		this.app_key = appKey;
		this.secret = appSecret;
		this.access_token = access_token;
	}

	public Map<String, String> getProtocolParams() {
		Map<String, String> protocolParams = new HashMap<String, String>();
		protocolParams.put("method", getmethod());
		protocolParams.put("app_key", getapp_key());
		protocolParams.put("access_token", getAccess_token());
		protocolParams.put("timestamp", DateTimeTool.getToday("yyyy-MM-dd HH:mm:ss")); //2017/6/2 by cjp

		return protocolParams;
	}

	public <T extends KaolaBaseResponse> T execute(KaolaBaseRequest<T> request) throws Exception {
		setmethod(request.getMType());

		Map<String, String> params = new HashMap<String, String>();
		params.putAll(getProtocolParams());

		params.putAll(request.getApiParams());

		// 生成签名
		params.put("sign", KaolaUtil.sign(params, getSecret()));
		// 调用 API
		String result = HttpPostUtils.getInstance().send_common_after_url(getServerUrl(), params);

		T kaolaRsp = null;
		JsonNode rootNode = null;
		JsonNode responseNode = null;
		
		ObjectMapper mapper=new ObjectMapper();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		try {
			//rootNode = JsonUtil.DEFAULT_OBJECT_MAPPER.readTree(result);
			rootNode=mapper.readTree(result);
			Iterator<Entry<String, JsonNode>> rootIter = rootNode.fields();
			while (rootIter.hasNext()) {
				Map.Entry<String, JsonNode> curEntry = rootIter.next();
				if (curEntry.getValue().isObject()) {
					responseNode = curEntry.getValue();
					break;
				}
			}
			logger.info("responseNode:" + responseNode.toString() );
			kaolaRsp = JsonUtil.jsonToObject(responseNode.toString(), request.getResponseClass());
			kaolaRsp.setResponseBody(result);
		} catch (Exception e) {
			logger.error(e.getMessage());
			if (responseNode != null) {
				String resultCode = responseNode.get("code").textValue();
				String msg = responseNode.get("msg").textValue();
				kaolaRsp = request.getResponseClass().newInstance();
				kaolaRsp.setCode(resultCode);
				kaolaRsp.setMsg(msg);
				kaolaRsp.setResponseBody(result);
			}
		}

		return kaolaRsp;

	}

}
