package com.digiwin.ecims.platforms.dhgate.util;

import org.apache.commons.beanutils.BeanMap;

import com.dhgate.open.client.ClientRequest;
import com.dhgate.open.client.DhgateDefaultClient;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;

/**
 * 敦煌网API应用级参数设定工具类。<br>
 * 通过建立对应应用级参数的bean,并加以java的反射机制进行参数的设定，以后平台有调整只要修改bean定义即可
 * @author 维杰
 *
 */
public class DhgateRequestParamBuilder {

	/**
	 * 
	 * @param client
	 * @param accessToken
	 * @param request
	 * @return
	 */
	public static  ClientRequest addParams(DhgateDefaultClient client, String accessToken, 
			DhgateBaseRequest request) {
		client.buildRequestClient(accessToken);
		
		BeanMap beanMap = new BeanMap(request);
		ClientRequest clientRequest = null;
		for (Object propertyName : beanMap.keySet()) {
			Object value = beanMap.get(propertyName);
			if (!propertyName.equals("class") && value != null) {
				clientRequest = client.addParam((String) propertyName, value.toString());
			}
		}
		return clientRequest;
		
	}
}
