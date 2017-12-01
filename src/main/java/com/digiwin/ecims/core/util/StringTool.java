package com.digiwin.ecims.core.util;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class StringTool {

  public static final String EMPTY = StringUtils.EMPTY;
  
  // 0100888888880099 => 0100888888880100
  public static String getNextCardCode(String oldCode) {
    String newCode = "" + (Long.parseLong(oldCode) + 1);
    while (newCode.length() < 16) {
      newCode = "0" + newCode;
    }
    return newCode;
  }

  /**
   * 分隔字符 , 注意：如果前后有分隔符，String.split会多出来一个。该方法自动去掉前后分隔符再调用 String.split 注意：特殊字符 $ % 等，需要使用 转义 $, 改为
   * \\$ aibo zeng 2009-06-09
   * 
   * @param str
   * @param ch
   * @return
   */
  public static String[] split(String str, char ch) {
    if (str == null) {
      return null;
    }
    if (str.charAt(0) == ch) {
      str = str.substring(1);
    }
    if (str.charAt(str.length() - 1) == ch) {
      str = str.substring(0, str.length() - 1);
    }
    return str.split(ch + "");
  }


  /**
   * @param args
   */
  public static void main(String[] args) {
    // System.out.println(getNextCardCode("0000888888880099"));
    /*
     * String[] strArg = StringTool.split("$1$WX0102$0000000000000001$25036671", '$');
     * System.out.println(strArg.length); for(int i=0;i<strArg.length;i++){
     * System.out.println(strArg[i]); }
     */ String[] strArg = "1$WX0102$0000000000000001$25036671".split("\\$");
    System.out.println(strArg.length);
    for (int i = 0; i < strArg.length; i++) {
      System.out.println(strArg[i]);
    }

  }

  /**
   * 判断字符串是否为null或空字符串,在模糊查询的时候很有意义
   */
  public static boolean isEmpty(String str) {
    return str == null || str.length() == 0 || str.trim().length() == 0;
  }

  /**
   * 判断字符串是否为null或空字符串,在模糊查询的时候很有意义
   */
  public static boolean isNotEmpty(String str) {
    return (str != null && !"".equals(str.trim()));
  }

  public static boolean isNotEmpty(Long o) {
    return (o != null);
  }

  public static boolean isNotEmpty(Integer o) {
    return (o != null);
  }

  public static boolean isNotEmpty(Date o) {
    return (o != null);
  }

  public static boolean isNotEmpty(BigDecimal o) {
    return (o != null);
  }

  public static boolean isNotEmpty(Object o) {
    return (o != null);
  }
}
