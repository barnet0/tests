package com.digiwin.ecims.core.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class HttpGetUtils {

	private static HttpGetUtils _httpGetUtils = new HttpGetUtils();
	
	private HttpGetUtils() {
	}
	
	public static HttpGetUtils getInstance() {
		return _httpGetUtils;
	}
	
	public byte[] sendGet(String hostUrl) throws IOException {
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(hostUrl);
	        HttpClient httpclient = new DefaultHttpClient();
	        
	        HttpResponse httpResponse = httpclient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toByteArray(httpResponse.getEntity());
	        } else {
	        	throw new IOException(ErrorMessageBox.getErrorMsg("_033") + 
	        			". HTTP Status Code:" + httpResponse.getStatusLine().getStatusCode());
	        }
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
		}
	}
	
	public boolean isUrlExists(String hostUrl) throws IOException {
		HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(hostUrl);
	        HttpClient httpclient = new DefaultHttpClient();
	        
	        HttpResponse httpResponse = httpclient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return true;
	        } else {
	        	return false;
	        }
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (httpGet != null) {
				httpGet.releaseConnection();
			}
		}
	}
	
}
