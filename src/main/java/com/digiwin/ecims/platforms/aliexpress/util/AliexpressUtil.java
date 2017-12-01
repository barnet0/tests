package com.digiwin.ecims.platforms.aliexpress.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AliexpressUtil {

  public static String sign(String urlPath, Map<String, String> params, String secret)
      throws IOException {
    String[] keys = params.keySet().toArray(new String[0]);
    Arrays.sort(keys);

    StringBuilder query = new StringBuilder();
    query.append(urlPath);
    for (String key : keys) {
      String value = params.get(key);
      if (key.length() > 0 && value != null && value.length() > 0) {
        query.append(key).append(value);
      }
    }

    byte[] bytes;
    bytes = encryptHmacSHA1(query.toString(), secret);

    return byte2hex(bytes);
  }

  private static String getStringFromException(Throwable e) {
    String result = "";
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bos);
    e.printStackTrace(ps);
    try {
      result = bos.toString("UTF-8");
    } catch (IOException ioe) {
    }
    return result;
  }

  public static byte[] encryptMD5(String data) throws IOException {
    return encryptMD5(data.getBytes("UTF-8"));
  }

  public static byte[] encryptMD5(byte[] data) throws IOException {
    byte[] bytes = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      bytes = md.digest(data);
    } catch (GeneralSecurityException gse) {
      String msg = getStringFromException(gse);
      throw new IOException(msg);
    }
    return bytes;
  }

  public static byte[] encryptHmacSHA1(String data, String secret) throws IOException {
    return encryptHmacSHA1(data.getBytes("UTF-8"), secret);
  }

  public static byte[] encryptHmacSHA1(byte[] data, String secret) throws IOException {
    byte[] bytes = null;
    try {
      
      Mac mac = Mac.getInstance("HmacSHA1");
      SecretKeySpec key = new SecretKeySpec(secret.getBytes("UTF-8"), mac.getAlgorithm());
      mac.init(key);
      bytes = mac.doFinal(data);
    } catch (GeneralSecurityException gse) {
      String msg = getStringFromException(gse);
      throw new IOException(msg);
    }
    return bytes;
  }

  public static String byte2hex(byte[] bytes) {
    StringBuilder sign = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(bytes[i] & 0xFF);
      if (hex.length() == 1) {
        sign.append("0");
      }
      sign.append(hex.toUpperCase());
    }
    return sign.toString();
  }
}
