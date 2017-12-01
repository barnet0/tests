package com.digiwin.ecims.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用來傳 HTTP 的資料
 * 
 * @author Xavier
 *
 */
public final class HttpPostUtils {

  private static final Logger logger = LoggerFactory.getLogger(HttpPostUtils.class); // 20170512 by cjp

  private static HttpPostUtils hpu;

  private static final String CHAR_ENCODING = "UTF-8";

  private static final String DANGDANG_CHAR_ENCODING = "GBK";

  /**
   * 链接超时的时间.
   */
  private static final int CONNECT_TIMEOUT = 60 * 1000;

  /**
   * 读取超时的时间.
   */
  private static final int READ_TIMEOUT = 60 * 1000;


  private HttpPostUtils() {
    // HttpPostUtils.getInstance().doPost(hostUrl, formMap, attachXmlDoc, attachXmlDocName)
  }


  public static HttpPostUtils getInstance() {
    if (hpu == null) {
      hpu = new HttpPostUtils();
    }
    return hpu;
  }

  /**
   * 一般 http 推送
   * 
   * @param hostUrl
   * @param formMap
   * @return
   * @throws Exception
   */
  public String send_common(String hostUrl, Map<String, String> formMap) throws Exception {
    HttpPost httpost = null;
    try {
      httpost = new HttpPost(hostUrl);
      HttpClient httpclient = new DefaultHttpClient();

      List<NameValuePair> params = new ArrayList<NameValuePair>();
      String[] keys = formMap.keySet().toArray(new String[0]);
      for (String key : keys) {
        params.add(new BasicNameValuePair(key, formMap.get(key)));
      }

      httpost.setEntity(new UrlEncodedFormEntity(params, CHAR_ENCODING));
      logger.info("entity:" + new UrlEncodedFormEntity(params, CHAR_ENCODING));
      printHttpContent(httpost);
      
      httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
      httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECT_TIMEOUT);
      
      HttpResponse response = httpclient.execute(httpost);
      
      // response
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        return EntityUtils.toString(response.getEntity(), CHAR_ENCODING);
      } else {
        throw new Exception(
            "OUCH！ has error!, return http code: " + response.getStatusLine().getStatusCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (httpost != null) {
        httpost.releaseConnection();
      }
    }

  }

  /**
   * 将数据以纯字符串形式放在请求Body中请求
   * 
   * @param hostUrl
   * @param json
   * @return
   * @throws Exception
   */
  public String send_common_string(String hostUrl, String json) throws Exception {
    return send_common_string(hostUrl, json, CONNECT_TIMEOUT, READ_TIMEOUT, CHAR_ENCODING);
  }

  /**
   * 将数据以纯字符串形式放在请求Body中请求
   * 
   * @param hostUrl
   * @param json
   * @return
   * @throws Exception
   */
  public String send_common_string(String hostUrl, String json, 
      int connectTimeout, int readTimeout, String charEncoding) 
      throws Exception {

    HttpPost httpost = null;
    try {
      httpost = new HttpPost(hostUrl);
      HttpClient httpclient = new DefaultHttpClient();

      httpost.setEntity(new StringEntity(json, charEncoding));

      httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectTimeout);
      httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeout);

      HttpResponse response = httpclient.execute(httpost);

      // response
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        return EntityUtils.toString(response.getEntity(), charEncoding);
      } else {
        throw new Exception(
            "OUCH！ has error!, return http code: " + response.getStatusLine().getStatusCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (httpost != null) {
        httpost.releaseConnection();
      }
    }

  }
  
  /**
   * 将请求的数据以key=value&key=value&...&key=value的形式，用POST添加在URL后进行请求。 value会被URLEncode。
   * 
   * @param hostUrl
   * @param formMap
   * @return
   * @throws Exception
   */
  public String send_common_after_url(String hostUrl, Map<String, String> formMap)
      throws Exception {
    HttpPost httpost = null;
    try {
      HttpClient httpclient = new DefaultHttpClient();

      StringBuilder sBuilder = new StringBuilder(hostUrl);
      sBuilder.append("?");
      for (String key : formMap.keySet()) {
        if (formMap.get(key) != null && formMap.get(key).length() > 0)
          sBuilder.append(key).append("=")
              .append(URLEncoder.encode(formMap.get(key), CHAR_ENCODING)).append("&");
       /* String str=formMap.get(key);
        System.out.println(key+":"+str);
        System.out.println(key+":"+URLEncoder.encode(formMap.get(key), CHAR_ENCODING));*/
      }
      sBuilder = sBuilder.deleteCharAt(sBuilder.length() - 1);
      String postUrl = sBuilder.toString();
      logger.info(postUrl);  //20170512 by cjp
      httpost = new HttpPost(postUrl);

      httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
      httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECT_TIMEOUT);

      HttpResponse response = httpclient.execute(httpost);

      // response
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
    	  String result = EntityUtils.toString(response.getEntity(), CHAR_ENCODING);
    	   logger.info("result:"+result);  //20170512 by cjp //部署到正式区的时候注释，否则会造成日志很大
          return result;
      } else {
        throw new Exception(
            "OUCH！ has error! return http code: " + response.getStatusLine().getStatusCode() + ","
                + EntityUtils.toString(response.getEntity(), CHAR_ENCODING));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (httpost != null) {
        httpost.releaseConnection();
      }
    }
  }

  /**
   * 使用ByteArray的方式发送Http-Post请求到聚石塔
   * 
   * @param params
   * @param api
   * @param url
   * @return
   * @throws ClientProtocolException
   * @throws IOException
   */
  public String send_common_bytearray(String postData, String api, String url)
      throws ClientProtocolException, IOException {
    // 设置超时
    HttpClient httpClient = new DefaultHttpClient();
    httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
    httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECT_TIMEOUT);

    // Post方式传送xml
    HttpPost httpPost = new HttpPost(url);
    httpPost.setEntity(new ByteArrayEntity(postData.getBytes(CHAR_ENCODING)));

    // 执行Post
    HttpResponse response = httpClient.execute(httpPost);

    // 檢查HTTP POST 結果: 若不為200，丟例外
    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
      throw new ClientProtocolException(CommonUtil.responseErrorJson("_033").getMsg());
    }

    // 回傳執行結果
    HttpEntity entity = response.getEntity();
    InputStream inputStream = entity.getContent();
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String result = reader.readLine();
    return result;
  }

  /**
   * 1.可將字串的xml 轉成文件, 並透過 POST 的方式, 傳送到對方主機上<br/>
   * 
   * @param hostUrl 對方主機
   * @param formMap 自帶表單參數
   * @param fileField 上傳檔案欄位名稱
   * @param attachXmlDoc 要當成附件的 xml
   * @param attachXmlDocName 要當成附件的 xml, 可有可不有.
   * @return 主機回傳結果
   * @throws Exception
   */
  public String doPostForXmlString(String hostUrl, Map<String, String> formMap, String fileField,
      String attachXmlDoc, String attachXmlDocName) throws Exception {
    HttpPost httpPost = null;
    try {
      HttpClient httpclient = new DefaultHttpClient();
      httpPost = new HttpPost(hostUrl);

      // 涉及到附件上传, 需要使用 MultipartEntity
      MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null,
          Charset.forName(DANGDANG_CHAR_ENCODING));
      String[] keys = formMap.keySet().toArray(new String[0]);
      for (String key : keys) {
        entity.addPart(key,
            new StringBody(formMap.get(key), Charset.forName(DANGDANG_CHAR_ENCODING)));
      }

      // 加上附件串流
      InputStreamBody is = new InputStreamBody(
          new ByteArrayInputStream(attachXmlDoc.getBytes(DANGDANG_CHAR_ENCODING)),
          attachXmlDocName);
      entity.addPart(fileField, is);

      httpPost.setEntity(entity);

      httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);
      httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, CONNECT_TIMEOUT);

      HttpResponse response = httpclient.execute(httpPost);
      // 处理响应
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        // 正常返回, 解析返回数据
        return EntityUtils.toString(response.getEntity(), DANGDANG_CHAR_ENCODING);
      } else {
        throw new Exception(
            "OUCH！ has error!, return http code: " + response.getStatusLine().getStatusCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (httpPost != null) {
        httpPost.releaseConnection();
      }
    }

  }

  /**
   * 使用SSL方式请求指定URL，并用POST传递参数。 Content-Type=application/x-www-form-urlencoded
   * 
   * @param hostUrl 指定URL
   * @param formMap 表单参数
   * @param trustStorePath truststore文件路径
   * @param password 公钥
   * @return
   * @throws Exception
   */
  public String send_commonWithSSL(String hostUrl, Map<String, String> formMap,
      String trustStorePath, final String password) throws Exception {

    HttpPost httpost = null;
    try {

      // 設定傳送參數
      httpost = new HttpPost(hostUrl);
      List<NameValuePair> params = new ArrayList<NameValuePair>();
      String[] keys = formMap.keySet().toArray(new String[0]);
      for (String key : keys) {
        params.add(new BasicNameValuePair(key, formMap.get(key)));
      }

      httpost.setEntity(new UrlEncodedFormEntity(params, CHAR_ENCODING));

      // SSL 設定
      HttpClient httpclient = new DefaultHttpClient(); // 取得連線物件
      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
      FileInputStream instream = new FileInputStream(
          HttpPostUtils.class.getClassLoader().getResource(trustStorePath).getFile());
      // 加载keyStore d:\\tomcat.keystore
      trustStore.load(instream, password.toCharArray());

      // create ssl Socket
      SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
      Scheme sch = new Scheme("https", 443, socketFactory); // 設定 SSL port.

      // 設定 SSL
      httpclient.getConnectionManager().getSchemeRegistry().register(sch);

      // 取得回傳結果
      HttpResponse response = httpclient.execute(httpost);

      // response
      if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        return EntityUtils.toString(response.getEntity(), CHAR_ENCODING);
      } else {
        throw new Exception(
            "OUCH！ has error!, return http code: " + response.getStatusLine().getStatusCode());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (httpost != null) {
        httpost.releaseConnection();
      }
    }

  }
  
  private static void printHttpContent(HttpPost httpPost) {
    
  }
  
  // public static void main(String[] args) {
	
  // DefaultHttpClient httpclient = new DefaultHttpClient();
  //
  // try {
  //
  // KeyStore trustStore = KeyStore.getInstance(KeyStore .getDefaultType());
  // FileInputStream instream = new FileInputStream(
  // HttpPostUtils.class.getClassLoader().getResource("ssl/icbc.truststore").getFile()
  // );
  //
  // try {
  // // 加载keyStore d:\\tomcat.keystore
  // trustStore.load(instream, "ebankpass".toCharArray());
  // } catch (CertificateException e) {
  // e.printStackTrace();
  // } finally {
  // try {
  // instream.close();
  // } catch (Exception ignore) {
  // }
  // }
  //
  // // 穿件Socket工厂,将trustStore注入
  // SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
  //
  // // 创建Scheme
  // Scheme sch = new Scheme("https", 443, socketFactory);
  //
  // // 注册Scheme
  // httpclient.getConnectionManager().getSchemeRegistry().register(sch);
  //
  // // 创建http请求(get方式)
  // HttpGet httpget = new HttpGet("https://ops.mall.icbc.com.cn/icbcrouter");
  // System.out.println("executing request" + httpget.getRequestLine());
  //
  // HttpResponse response = httpclient.execute(httpget);
  // HttpEntity entity = response.getEntity();
  // System.out.println("----------------------------------------");
  // System.out.println(response.getStatusLine());
  //
  // if (entity != null) {
  // System.out.println("Response content length: " + entity.getContentLength());
  // String ss = EntityUtils.toString(entity);
  // System.out.println(ss);
  // EntityUtils.consume(entity);
  // }
  // } catch (Exception e) {
  // e.printStackTrace();
  // } finally {
  // httpclient.getConnectionManager().shutdown();
  // }
 //  }
  
	/**
	 * yang add 1012 用于http请求 get or post
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public  JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
	
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSON.parseObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	

}
