package com.digiwin.ecims.platforms.dangdang.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.HttpPostUtils;
import com.digiwin.ecims.core.util.MD5;
import com.digiwin.ecims.core.util.XmlUtils;
import com.digiwin.ecims.platforms.dangdang.service.impl.DangdangApiServiceImpl;

public class DangdangClient {
	/**
	 * 版本
	 */
	public static final String VERSION = "1.0";
	/**
	 * 請求格式
	 */
	public static final String FORMAT = "xml";

	public static final String SIGN_METHOD = "md5";

	private String url;
	private String appkey;
	private String format;
	private String method;
	private String session;
	private String signMethod;
	private String timestamp;
	private String v;
	private String sign;
	private String requestXml;
	private String appSecret;

	XmlUtils xu = XmlUtils.getInstance();

	/**
	 * constructor
	 * 
	 * @param URL
	 *            接口URL
	 * @param appKey
	 *            app key
	 * @param appSecret
	 *            app secret
	 */
	public DangdangClient(String url, String appkey, String appSecret, String accessToken) {
		super();
		this.url = url;
		this.appkey = appkey;
		this.format = FORMAT;
		this.method = null;
		this.session = accessToken;
		this.signMethod = SIGN_METHOD;
		this.timestamp = DateTimeTool.format(new Date());
		this.v = VERSION;
		this.sign = null;
		this.requestXml = null;
		this.appSecret = appSecret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getSign() {
		// 参考当当API http://open.dangdang.com/index.php?c=documentCenter&f=show&page_id=89
		// 1、系统级参数按照参数名称升序排列
		// 2、连接字符串：连接参数名与参数值,并在首尾加上secret
		// 3、生成签名：32位大写MD5值

		String combinationStrings = "";
		combinationStrings = this.getAppSecret() + "app_key" + getAppkey() + "format" + getFormat() + "method" + getMethod() + "session" + getSession() + "sign_method" + getSignMethod() + "timestamp" + getTimestamp() + "v" + getV() + this.getAppSecret();

		return MD5.encode(combinationStrings);
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	@Override
	public String toString() {
		return "&app_key" + getAppkey() + "&format" + getFormat() + "&method" + getMethod() + "&session" + getSession() + "&sign_method" + getSignMethod() + "&timestamp" + getTimestamp() + "&v" + getV();
	}

	public Map<String, String> toFieldMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("app_key", getAppkey());
		map.put("format", getFormat());
		map.put("method", getMethod());
		map.put("session", getSession());
		map.put("sign_method", getSignMethod());
		map.put("timestamp", getTimestamp());
		map.put("v", "1.0");
		map.put("requestXml", getRequestXml());
		map.put("sign", getSign());
		return map;
	}

	/**
	 * 无应用级参数的API调用，只需提供API方法名称即可
	 * @param method API方法
	 * @return 调用结果
	 * @throws Exception
	 * @author 维杰
	 * @since 2015.09.03
	 */
	public String execute(String method) throws Exception {
		return execute(method, null);
	}
	
	/**
	 * 有应用级参数的API调用，需要提供API方法名与应用级参数组成的Map
	 * @param method 調用API名稱
	 * @param formMap 应用级参数Map
	 * @return
	 * @throws Exception
	 */
	public String execute(String method, Map<String, String> formMap) throws Exception {
		this.setMethod(method);
		this.setSign(getSign());
		Map<String, String> params = this.toFieldMap();
		if (formMap != null && !formMap.isEmpty()) {
			String[] keys = formMap.keySet().toArray(new String[0]);
			for (String key : keys) {
				params.put(key, formMap.get(key));
			}
		}

		return HttpPostUtils.getInstance().send_common(getUrl(), params);
	}

	/**
	 * HTTP请求方式
	 * 
	 * @param method
	 *            調用API名稱
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String execute_common(String method, Object request) throws Exception {
		this.setMethod(method);

		if (null != request) {
			this.setRequestXml(DangdangApiServiceImpl.XML_TITLE + xu.javaBean2Xml(request));
		}
		this.setSign(getSign());

		return HttpPostUtils.getInstance().send_common(getUrl(), toFieldMap());
	}

	/**
	 * HTTP请求方式
	 * 
	 * @param method
	 *            調用API名稱
	 * @param fileName
	 *            file檔名
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String execute(String method, String fileName, Object request) throws Exception {
		this.setMethod(method);

		if (null != request) {
//			this.setRequestXml(DangdangApiServiceImpl.XML_TITLE + xu.javaBean2Xml(request)); // mark by mowj 20160503
		  this.setRequestXml(xu.javaBean2Xml(request, "GBK", false));
		}

		this.setSign(getSign());
		return HttpPostUtils.getInstance().doPostForXmlString(getUrl(), toFieldMap(), fileName, this.getRequestXml(), fileName);
	}
}
