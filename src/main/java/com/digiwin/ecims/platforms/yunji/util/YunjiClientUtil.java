package com.digiwin.ecims.platforms.yunji.util;

import com.digiwin.ecims.platforms.yunji.bean.YunjiClient;

public final class YunjiClientUtil {
	  private static volatile YunjiClientUtil yunjiClientUtil;
	  
	  private YunjiClientUtil() {
	  }
	  
	  public static YunjiClientUtil getInstance() {
	    if (yunjiClientUtil == null) {
	      synchronized (YunjiClientUtil.class) {
	        if (yunjiClientUtil == null) {
	          yunjiClientUtil = new YunjiClientUtil();
	        }
	      }
	    }
	    return yunjiClientUtil;
	  }
	  
	  private static volatile YunjiClient yunjiClient;
	  
	  public YunjiClient getYunjiClient(String serverUrl, String appKey, String appSecret,String version) {
	    if (yunjiClient == null) {
	      synchronized (YunjiClientUtil.class) {
	        if (yunjiClient == null) {
	          yunjiClient = new YunjiClient(serverUrl, appKey, appSecret,version);
	        }
	      }
	    } else {
	      if (serverUrl.equals(yunjiClient.getServerUrl()) && 
	          appKey.equals(yunjiClient.getAppkey()) && 
	          appSecret.equals(yunjiClient.getAppSecret())&&
	          version.equals(yunjiClient.getV())) {
	        
	      } else {
	        synchronized (YunjiClientUtil.class) {
	          yunjiClient.setServerUrl(serverUrl);
	          yunjiClient.setAppkey(appKey);
	          yunjiClient.setAppSecret(appSecret);
	          yunjiClient.setV(version);
	          
	        }
	      }
	      
	    }
	    return yunjiClient;
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
