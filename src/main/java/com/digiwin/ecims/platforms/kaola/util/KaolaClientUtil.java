package com.digiwin.ecims.platforms.kaola.util;

import com.digiwin.ecims.platforms.kaola.bean.KaolaClient;

/**
 * 
 * @author cjp 2017/5/18
 *
 */
public final class KaolaClientUtil {

  private static volatile KaolaClientUtil kaolaClientUtil;
  
  private KaolaClientUtil() {
  }
  
  public static KaolaClientUtil getInstance() {
    if (kaolaClientUtil == null) {
      synchronized (KaolaClientUtil.class) {
        if (kaolaClientUtil == null) {
          kaolaClientUtil = new KaolaClientUtil();
        }
      }
    }
    return kaolaClientUtil;
  }
  
  private static volatile KaolaClient kaolaClient;
  
  public KaolaClient getKaolaClient(String serverUrl, String appKey, String appSecret,String accessToken) {
    if (kaolaClient == null) {
      synchronized (KaolaClientUtil.class) {
        if (kaolaClient == null) {
          kaolaClient = new KaolaClient(serverUrl, appKey, appSecret,accessToken);
        }
      }
    } else {
      if (serverUrl.equals(kaolaClient.getServerUrl()) && 
          appKey.equals(kaolaClient.getapp_key()) && 
          appSecret.equals(kaolaClient.getSecret())&&
          accessToken.equals(kaolaClient.getAccess_token())) {
        
      } else {
        synchronized (KaolaClientUtil.class) {
          kaolaClient.setServerUrl(serverUrl);
          kaolaClient.setapp_key(appKey);
          kaolaClient.setSecret(appSecret);
          kaolaClient.setAccess_token(accessToken);
          
        }
      }
      
    }
    return kaolaClient;
  }
  
	public String decodeUnicode(String theString) {      
		   
	    char aChar;      
	   
	     int len = theString.length();      
	   
	    StringBuffer outBuffer = new StringBuffer(len);      
	   
	    for (int x = 0; x < len;) {      
	   
	     aChar = theString.charAt(x++);      
	   
	     if (aChar == '\\') {      
	   
	      aChar = theString.charAt(x++);      
	   
	      if (aChar == 'u') {      
	   
	       // Read the xxxx      
	   
	       int value = 0;      
	   
	       for (int i = 0; i < 4; i++) {      
	   
	        aChar = theString.charAt(x++);      
	   
	        switch (aChar) {      
	   
	        case '0':      
	   
	        case '1':      
	   
	        case '2':      
	   
	        case '3':      
	   
	       case '4':      
	   
	        case '5':      
	   
	         case '6':      
	          case '7':      
	          case '8':      
	          case '9':      
	           value = (value << 4) + aChar - '0';      
	           break;      
	          case 'a':      
	          case 'b':      
	          case 'c':      
	          case 'd':      
	          case 'e':      
	          case 'f':      
	           value = (value << 4) + 10 + aChar - 'a';      
	          break;      
	          case 'A':      
	          case 'B':      
	          case 'C':      
	          case 'D':      
	          case 'E':      
	          case 'F':      
	           value = (value << 4) + 10 + aChar - 'A';      
	           break;      
	          default:      
	           throw new IllegalArgumentException(      
	             "Malformed   \\uxxxx   encoding.");      
	          }      
	   
	        }      
	         outBuffer.append((char) value);      
	        } else {      
	         if (aChar == 't')      
	          aChar = '\t';      
	         else if (aChar == 'r')      
	          aChar = '\r';      
	   
	         else if (aChar == 'n')      
	   
	          aChar = '\n';      
	   
	         else if (aChar == 'f')      
	   
	          aChar = '\f';      
	   
	         outBuffer.append(aChar);      
	   
	        }      
	   
	       } else     
	   
	       outBuffer.append(aChar);      
	   
	      }      
	   
	      return outBuffer.toString();      
	   
	}
}

