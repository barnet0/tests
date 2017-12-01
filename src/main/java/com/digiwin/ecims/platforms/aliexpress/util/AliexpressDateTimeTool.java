package com.digiwin.ecims.platforms.aliexpress.util;

import java.util.Date;

import com.digiwin.ecims.core.util.DateTimeTool;

public class AliexpressDateTimeTool {

  public static final String REQUEST_DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

  public static final String RESPONSE_DATE_FORMAT = "yyyyMMddHHmmssSSSZ";

  public static Date parseToAeRequestDate(String date) {
    return DateTimeTool.parse(date, REQUEST_DATE_FORMAT);
  }

  public static Date parseToAeResponseDate(String date) {
    return DateTimeTool.parse(date, RESPONSE_DATE_FORMAT);
  }

  public static String formatToAeReqDateString(Date date) {
    return DateTimeTool.format(date, REQUEST_DATE_FORMAT);
  }

  public static String formatToAeResDateString(Date date) {
    return DateTimeTool.format(date, RESPONSE_DATE_FORMAT);
  }

  public static String turnDateStringToAeReqDateString(String dateString) {
    Date date = DateTimeTool.parse(dateString);
    return DateTimeTool.format(date, REQUEST_DATE_FORMAT);
  }

  public static String turnDateStringToAeResDateString(String dateString) {
    Date date = DateTimeTool.parse(dateString);
    return DateTimeTool.format(date, RESPONSE_DATE_FORMAT);
  }

  public static String turnAeReqDateStringToDateString(String aeReqDateString) {
    Date date = parseToAeRequestDate(aeReqDateString);
    return DateTimeTool.format(date, DateTimeTool.SECOND_FORMAT);
  }

  public static String turnAeResDateStringToDateString(String aeResDateString) {
    if (aeResDateString != null) {
      Date date = parseToAeResponseDate(aeResDateString);
      return DateTimeTool.format(date, DateTimeTool.SECOND_FORMAT);
    } else {
      return "";
    }
  }

  public static String getRequestMiddleDateString(String sDate, String eDate) {
    Date startDate = parseToAeRequestDate(sDate);
    Date endDate = parseToAeRequestDate(eDate);
    Date middleDate = new Date((startDate.getTime() + endDate.getTime()) / 2);
    return formatToAeReqDateString(middleDate);
  }

  public static boolean after(String aeDateString, Date date) {
    return parseToAeResponseDate(aeDateString).after(date);
  }
}
