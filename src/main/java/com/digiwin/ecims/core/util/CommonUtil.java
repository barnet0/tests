package com.digiwin.ecims.core.util;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;

public class CommonUtil {

  // JSON轉換類====Start====;;

  /**
   * 取得POST過來的資料並返回字串
   * 
   * @param servletRequest
   * @return 回傳request的json
   * @throws IOException
   */
  public static String getPostData(ServletRequest servletRequest) throws IOException {
    BufferedReader in =
        new BufferedReader(new InputStreamReader(servletRequest.getInputStream(), "UTF-8"));

//    // Read the request
//    CharArrayWriter data = new CharArrayWriter();
//    char[] buf = new char[8192];
//    int ret;
//    while ((ret = in.read(buf, 0, 8192)) != -1) {
//      data.write(buf, 0, ret);
//    }
//    return data.toString();
    
    
    //获取从聚石塔推送的数据偶见乱码异常，故更改此处  2017-09-13 wjl
    
    char[]  ch = new char[512];
    StringBuffer data =  new StringBuffer();
    while(in.read(ch)!=-1){
    	data.append(new String(ch));
    	ch = null;
    	ch =new char[512];
    }
    
    
    return data.toString().trim();
    // sb为post过来的数据
  }

  
  // JSON轉換類====End====

  // XML類轉換====Start====

  // /**
  // * 讀參數設擋
  // *
  // * @param tableName
  // * 要求的table
  // * @return 該table的所有資料 map
  // */
  // public static Map<String, String> paramsXmlMapping(String tableName) {
  // Document impDoc;
  // FileInputStream fis = null;
  // Map<String, String> mappingMap = new HashMap<String, String>();
  //
  // String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
  // path = path.replace('/', '\\'); // 将/换成\
  // path = path.replace("file:", ""); // 去掉file:
  // path = path.replace("classes\\", ""); // 去掉class\
  // path = path.substring(1); // 去掉第一个\,如 \D:\JavaWeb...
  // path += "classes\\config\\aoms.trans.mapping.xml";
  //
  // Properties prop = System.getProperties();
  //
  // String os = prop.getProperty("os.name");
  // // File xmlFile;
  // String xmlFilePath;
  // if (os.startsWith("win") || os.startsWith("Win")) {
  // // xmlFile=new File(path);
  // xmlFilePath = path;
  // } else {
  // path = "\\" + path;
  // // xmlFile=new File(path.replace("\\", "/"));
  // xmlFilePath = path.replace("\\", "/");
  // }
  //
  // try {
  // fis = new FileInputStream(xmlFilePath);
  // impDoc = new SAXReader().read(fis);
  // Element root = impDoc.getRootElement();
  // for (Object element : root.elements()) {
  // if (((Element) element).attributeValue("value").equals(tableName)) {
  // for (Object e : ((Element) element).elements()) {
  // mappingMap.put(((Element) e).getName(), ((Element) e).attributeValue("value"));
  // // System.out.println(((Element)e).getName()+":"+
  // // ((Element)e).attributeValue("name"));
  // }
  // break;
  // }
  // }
  // } catch (DocumentException e) {
  // e.printStackTrace();
  // } catch (FileNotFoundException e1) {
  // e1.printStackTrace();
  // } finally {
  // try {
  // fis.close();
  // } catch (IOException e) {
  // e.printStackTrace();
  // }
  // }
  // return mappingMap;
  // }

  // XML類轉換====End====

  // Error訊息的準備====Start====

  /**
   * 有定意好ErrorCode的
   * 
   * @param errorCode 代碼
   * @return ResponseError物件
   */
  public static ResponseError responseErrorJson(String errorCode) {
    return responseErrorJson(errorCode, null);
  }

  public static ResponseError responseErrorJson(String errorCode, String exceptionErrorMsg) {
    ResponseError myError;
    if (errorCode == null) {
      myError = new ResponseError("999", "NullPointerException!");
    } else {
      myError = new ResponseError(errorCode.replace("_", ""),
          exceptionErrorMsg == null ? ErrorMessageBox.getErrorMsg(errorCode) : exceptionErrorMsg);
    }
    return myError;
  }

  /**
   * 沒定意好ErrorCode的
   * 
   * @param errorCode 自己定的errorCode
   * @param errorMsg 自己定的errorMsg
   * @return ResponseError物件
   */
  public static ResponseError noDefinitionErrorResponse(String errorCode, String errorMsg) {
    ResponseError myError = new ResponseError(errorCode, errorMsg);
    return myError;
  }

  // Error訊息的準備====End=====

  // 物件類型轉換處理====Start====

  /**
   * 透過字串轉換成這類型的物件
   * 
   * @param className 類別名稱
   * @return 該類型物件
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws ClassNotFoundException
   */
  public Object instanceObject(String className)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    Object obj = Class.forName(className).newInstance();
    return obj;
  }

  /**
   * 確認該字串是否為數字
   * 
   * @param str 要判別的字串
   * @return 是數值==true else false
   */
  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    return pattern.matcher(str).matches();
  }

  /**
   * 店鋪ID Mapping
   * 
   * @param objList資料庫取出的aomsshop物建清單
   * @return 回傳出map
   */
  public static Map<String, String> aomsshopToMap(List<Object> objList) {
    Map<String, String> resultMap = new HashMap<String, String>();
    if (objList == null || objList.size() == 0) {
      return null;
    }

    for (Object obj : objList) {
      resultMap.put(((AomsshopT) obj).getAomsshop009(), ((AomsshopT) obj).getAomsshop001());
    }
    return resultMap;
  }

  // 物件類型轉換處理====End====

  public static String checkNullOrNot(Object object) {
    DecimalFormat dFormat = new DecimalFormat("###########.00");
    if (object == null) {
      return "";
    } else {
      if (object instanceof String) {
        return (String) object;
      } else if (object instanceof Date) {
        return DateTime.toString((Date) object);
      } else if (object instanceof Double) {
        return dFormat.format(((Double) object));
      } else {
        return String.valueOf(object);
      }
    }
  }


  public static <T> String getAomsmoddt(Object obj, Class<T> clazz)
      throws UnsupportedOperationException {
    if (clazz.equals(AomsordT.class)) {
      return ((AomsordT) obj).getAomsmoddt();
    } else if (clazz.equals(AomsrefundT.class)) {
      return ((AomsrefundT) obj).getAomsmoddt();
    } else if (clazz.equals(AomsitemT.class)) {
      return ((AomsitemT) obj).getAomsmoddt();
    } else {
      throw new UnsupportedOperationException(
          "Unknown class type! Only AomsordT,AomsrefundT and AomsitemT supported ");
    }
  }

  public static <T> String getId(Object obj, Class<T> clazz) throws UnsupportedOperationException {
    if (clazz.equals(AomsordT.class)) {
      return ((AomsordT) obj).getId();
    } else if (clazz.equals(AomsrefundT.class)) {
      return ((AomsrefundT) obj).getId();
    } else if (clazz.equals(AomsitemT.class)) {
      return ((AomsitemT) obj).getId();
    } else {
      throw new UnsupportedOperationException(
          "Unknown class type! Only AomsordT,AomsrefundT and AomsitemT supported ");
    }
  }

  public static <T> String getOrderCreateTime(Object obj, Class<T> clazz)
      throws UnsupportedOperationException {
    if (clazz.equals(AomsordT.class)) {
      return ((AomsordT) obj).getAoms006();
    } else {
      throw new UnsupportedOperationException("Unknown class type! Only AomsordT supported ");
    }
  }

  public static <T> String getOrderModifiedTime(Object obj, Class<T> clazz)
      throws UnsupportedOperationException {
    if (clazz.equals(AomsordT.class)) {
      return ((AomsordT) obj).getModified();
    } else {
      throw new UnsupportedOperationException("Unknown class type! Only AomsordT supported ");
    }
  }
  
  public static <T> String getJdpModified(Object obj, Class<T> clazz) {
    if (clazz.equals(AomsordT.class)) {
      return ((AomsordT) obj).getJdpModified();
    } else if (clazz.equals(AomsrefundT.class)) {
      return ((AomsrefundT) obj).getJdpModified();
    } else if (clazz.equals(AomsitemT.class)) {
      return ((AomsitemT) obj).getJdpModified();
    } else {
      throw new UnsupportedOperationException(
          "Unknown class type! Only AomsordT,AomsrefundT and AomsitemT supported ");
    }
  }
}
