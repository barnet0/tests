package com.digiwin.ecims.platforms.icbc.bean.encrypt;

public abstract class Encrypt {
	
	/**
	 * 签名
	 * @param app_key 应用key
	 * @param auth_code 授权码
	 * @param req_data 请求报文
	 * @param app_secret 应用秘钥
	 * @return
	 * @throws Exception
	 */
	public abstract String sign(String app_key,String auth_code,String req_data,String app_secret) throws Exception;
	
}
