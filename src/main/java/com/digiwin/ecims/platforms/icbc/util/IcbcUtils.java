package com.digiwin.ecims.platforms.icbc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class IcbcUtils {
  /**
   * 用SHA-256的HMAC散列算法產生簽名，再用sun.misc.Base64Encoder進行編碼
   *
   * @param appkey app key
   * @param authCode access token
   * @param reqData 加密所需的字串組合
   * @param appSecret 金鑰(用app secret)
   * @return 簽名
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeyException
   */
  public static String getSign(String appkey, String authCode, String reqData, String appSecret)
      throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
    final String KEY_MAC = "HmacSHA256";
    String data = "app_key=" + appkey + "&auth_code=" + authCode + "&req_data=" + reqData;

    SecretKey sk = new SecretKeySpec(appSecret.getBytes("UTF-8"), KEY_MAC);
    Mac mac = Mac.getInstance(sk.getAlgorithm());
    mac.init(sk);
    byte[] bytes = mac.doFinal(data.getBytes("UTF-8"));
    BASE64Encoder be = new BASE64Encoder();
    return URLEncoder.encode(be.encode(bytes), "UTF-8");
  }
}
