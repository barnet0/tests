package com.digiwin.ecims.core.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jd.open.api.sdk.JdException;
import com.suning.api.exception.SuningApiException;
import com.taobao.api.ApiException;

public final class JsonUtil {

  public static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

  /**
   * 从文件读取JSON字符串的mapper
   */
  public static final ObjectMapper FROM_FILE_OBJECT_MAPPER = new ObjectMapper();

  /**
   * 转换成JSON字符串的mapper.Date格式化为yyyy-MM-dd HH:mm:ss
   */
  public static final ObjectMapper TO_STRING_SECOND_OBJECT_MAPPER = new ObjectMapper();

  /**
   * 转换成JSON字符串的mapper.Date格式化为yyyy-MM-dd HH:mm:ss.SSS
   */
  public static final ObjectMapper TO_STRING_MILLI_SECOND_OBJECT_MAPPER = new ObjectMapper();

  // private static final JsonFactory DEFAULT_JSON_FACTORY = DEFAULT_OBJECT_MAPPER.getFactory();
  static {
    /*
     * 反序列化用Mapper
     */
    // to prevent exception when encountering unknown property:
    DEFAULT_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    // to allow coercion of JSON empty String ("") to null Object value:
    DEFAULT_OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    FROM_FILE_OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    FROM_FILE_OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    /*
     * 序列化用Mapper
     */
    TO_STRING_SECOND_OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
    TO_STRING_SECOND_OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimeTool.SECOND_FORMAT));
    TO_STRING_SECOND_OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    TO_STRING_MILLI_SECOND_OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
    TO_STRING_MILLI_SECOND_OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DateTimeTool.MILLI_SECOND_FORMAT));
    TO_STRING_SECOND_OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }

  /**
   * 从文件读取JSON字符串，并转换成对应类
   * 
   * @param file json文件
   * @param clazz 目标类
   * @return 目标类实例
   */
  public static <T> T jsonToObject(File file, Class<T> clazz) {
    try {
      // ObjectMapper mapper = new ObjectMapper();
      // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//
      // 設為false對不到的話jackson也不會報錯
      // 把檔案的json根據class型別轉成該型別的物件
      return FROM_FILE_OBJECT_MAPPER.readValue(file, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 把JSON字符串转换成对应类
   * 
   * @param json JSON字符串
   * @param clazz 目标类
   * @return 目标类实例
   */
  public static <T> T jsonToObject(String json, Class<T> clazz) {
    try {
      return DEFAULT_OBJECT_MAPPER.readValue(json, clazz);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 把JSON字符串转换成自定义的引用类型
   * 
   * @param json JSON字符串
   * @param type 自定义引用类型
   * @return 自定义引用类型类
   */
  public static <T> T jsonToObject(String json, TypeReference<T> type) {
    try {
      return DEFAULT_OBJECT_MAPPER.readValue(json, type);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 把指定实例序列化成JSON字符串。默认Date类型格式化为yyyy-MM-dd HH:mm:ss
   * 
   * @param bean 实例类
   * @return 实例类的JSON字符串
   */
  public static String format(Object bean) {
    return formatBySecond(bean);
  }

  /**
   * 把指定实例序列化成JSON字符串,bean中的Date类型格式化为yyyy-MM-dd HH:mm:ss.SSS
   * 
   * @param bean 实例类
   * @return JSON字符串
   */
  public static String formatBySecond(Object bean) {
    try {
      return TO_STRING_SECOND_OBJECT_MAPPER.writeValueAsString(bean);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 把指定实例序列化成JSON字符串,bean中的Date类型格式化为yyyy-MM-dd HH:mm:ss.SSS
   * 
   * @param bean 实例类
   * @return JSON字符串
   */
  public static String formatByMilliSecond(Object bean) {
    try {
      return TO_STRING_MILLI_SECOND_OBJECT_MAPPER.writeValueAsString(bean);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 获取当前JSON字符串中，指定property名称的JsonNode实例
   * 
   * @param jsonString JSON字符串
   * @param fieldName property名称
   * @return property名称的JsonNode实例。如果不存在，则返回null
   */
  public static JsonNode getJsonNodeByFieldName(String jsonString, String fieldName) {
    JsonNode rootNode;
    try {
      rootNode = DEFAULT_OBJECT_MAPPER.readTree(jsonString);
      return rootNode.get(fieldName);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * 获取当前JSON字符串中，指定property名称的value字符串值
   * 
   * @param jsonString JSON字符串
   * @param fieldName property名称
   * @return
   */
  public static String getJsonNodeTextByFieldName(String jsonString, String fieldName) {
    JsonNode jsonNode = getJsonNodeByFieldName(jsonString, fieldName);
    return jsonNode == null ? null : jsonNode.textValue();
  }

  public static void main(String[] args) throws InterruptedException, ApiException, JdException,
      SuningApiException, JsonProcessingException {
    // System.out.println(test1());
    // System.out.println(test1());
    //
    // t1 test1 = new t1();
    // t2 test2 = new t2();
    // test1.run();
    // Thread.sleep(3000L);
    // test2.run();
//    String json =
//        "{\"ecno\":\"0\",\"storeid\":\"TMZMD\",\"api\":\"digiwin.order.detail.get\",\"params\":{\"isfx\":\"0\",\"id\":\"1990614901040409\"},\"test\":\"test\"}";
//    CmdReq req = JsonUtil.jsonToObject(json, CmdReq.class);
//    System.out.println(req.getApi());
//
//    Map<String, String> treeMap = new TreeMap<String, String>();
//    System.out.println(Boolean.FALSE);

    // String json =
    // "{\"logistics_companies_get_response\":{\"logistics_companies\":{\"logistics_company\":[{\"code\":\"HOAU\",\"id\":1191,\"name\":\"天地华宇\"},{\"code\":\"DTW\",\"id\":512,\"name\":\"大田\"},{\"code\":\"YTO\",\"id\":101,\"name\":\"圆通速递\"},{\"code\":\"YUNDA\",\"id\":102,\"name\":\"韵达快递\"},{\"code\":\"HTKY\",\"id\":502,\"name\":\"百世快递\"},{\"code\":\"SF\",\"id\":505,\"name\":\"顺丰速运\"},{\"code\":\"EMS\",\"id\":2,\"name\":\"EMS\"},{\"code\":\"ZJS\",\"id\":103,\"name\":\"宅急送\"},{\"code\":\"STO\",\"id\":100,\"name\":\"申通快递\"},{\"code\":\"ZTO\",\"id\":500,\"name\":\"中通快递\"},{\"code\":\"POST\",\"id\":1,\"name\":\"中国邮政\"},{\"code\":\"OTHER\",\"id\":-1,\"name\":\"其他\"},{\"code\":\"AIR\",\"id\":507,\"name\":\"亚风\"},{\"code\":\"MGSD\",\"id\":21000007003,\"name\":\"美国速递\"},{\"code\":\"BHWL\",\"id\":21000053037,\"name\":\"保宏物流\"},{\"code\":\"UNIPS\",\"id\":1237,\"name\":\"发网\"},{\"code\":\"YUD\",\"id\":513,\"name\":\"长发\"},{\"code\":\"YC\",\"id\":1139,\"name\":\"远长\"},{\"code\":\"DFH\",\"id\":1137,\"name\":\"东方汇\"},{\"code\":\"CYEXP\",\"id\":511,\"name\":\"长宇\"},{\"code\":\"WND\",\"id\":21000127009,\"name\":\"WnDirect\"},{\"code\":\"GZLT\",\"id\":200427,\"name\":\"飞远配送
    // \"},{\"code\":\"PKGJWL\",\"id\":21000038002,\"name\":\"派易国际物流77\"},{\"code\":\"NEDA\",\"id\":1192,\"name\":\"能达速递\"},{\"code\":\"YCT\",\"id\":1185,\"name\":\"黑猫宅急便\"},{\"code\":\"SURE\",\"id\":201174,\"name\":\"速尔\"},{\"code\":\"DBL\",\"id\":107,\"name\":\"德邦物流\"},{\"code\":\"UC\",\"id\":1207,\"name\":\"优速快递\"},{\"code\":\"LTS\",\"id\":1214,\"name\":\"联昊通\"},{\"code\":\"ESB\",\"id\":200740,\"name\":\"E速宝\"},{\"code\":\"GTO\",\"id\":200143,\"name\":\"国通快递\"},{\"code\":\"LB\",\"id\":1195,\"name\":\"龙邦速递\"},{\"code\":\"POSTB\",\"id\":200734,\"name\":\"邮政快递包裹\"},{\"code\":\"TTKDEX\",\"id\":504,\"name\":\"天天快递\"},{\"code\":\"HZABC\",\"id\":1121,\"name\":\"飞远(爱彼西)配送\"},{\"code\":\"EYB\",\"id\":3,\"name\":\"EMS经济快递\"},{\"code\":\"DBKD\",\"id\":5000000110730,\"name\":\"德邦快递\"},{\"code\":\"CNEX\",\"id\":1056,\"name\":\"佳吉快递\"},{\"code\":\"QFKD\",\"id\":1216,\"name\":\"全峰快递\"},{\"code\":\"GDEMS\",\"id\":1269,\"name\":\"广东EMS\"},{\"code\":\"FEDEX\",\"id\":106,\"name\":\"联邦快递\"},{\"code\":\"QRT\",\"id\":1208,\"name\":\"增益速递\"},{\"code\":\"UAPEX\",\"id\":1259,\"name\":\"全一快递\"},{\"code\":\"XB\",\"id\":1186,\"name\":\"新邦物流\"},{\"code\":\"XFWL\",\"id\":202855,\"name\":\"信丰物流\"},{\"code\":\"FAST\",\"id\":1204,\"name\":\"快捷快递\"},{\"code\":\"SHQ\",\"id\":108,\"name\":\"华强物流\"},{\"code\":\"BEST\",\"id\":105,\"name\":\"百世物流\"}]},\"request_id\":\"13oyxm2m7iho3\"}}";
    // LogisticsCompaniesGetResponse response = TaobaoUtils.parseResponse(json,
    // LogisticsCompaniesGetResponse.class);
    // if (response != null) {
    // for (LogisticsCompany logisticsCompany : response.getLogisticsCompanies()) {
    // treeMap.put(logisticsCompany.getName(), logisticsCompany.getCode());
    // }
    // for (String string : treeMap.keySet()) {
    //// System.out.println(string);
    // System.out.println(treeMap.get(string));
    // }
    // }
    // TaobaoClient taobaoClient = new DefaultTaobaoClient("http://121.199.161.136:30001/tbapi",
    // "12414910", "59d0c0c35a75e5b2cb742e6d174642f3");
    // TradeFullinfoGetRequest request = new TradeFullinfoGetRequest();
    // request.setTid(1990614901040409L);
    // request.setFields("receiver_address");
    // TradeFullinfoGetResponse response = taobaoClient.execute(request,
    // "6102311706f34c0cd4e1cb1f2fbdff3c6879d6b53a77eda2608950843");
    // System.out.println(response.getBody());

    // treeMap.clear();
    // JdClient jdClient = new DefaultJdClient(
    // "https://api.jd.com/routerjson",
    // "15c90523-595b-4ff9-9158-1c5e586d3eb9",
    // "985C9E37363A93F9D74724201AC0398B",
    // "f981f669e7804d379ecf6ac8aeac463f");
    // GetVenderAllDeliveryCompanyRequest jDeliveryCompanyRequest = new
    // GetVenderAllDeliveryCompanyRequest();
    // jDeliveryCompanyRequest.setFields("id,name");
    // GetVenderAllDeliveryCompanyResponse jDeliveryCompanyResponse =
    // jdClient.execute(jDeliveryCompanyRequest);
    // if (jDeliveryCompanyResponse != null) {
    // List<DeliveryCompany> deliveryCompanies = jDeliveryCompanyResponse.getDeliveryList();
    // for (DeliveryCompany deliveryCompany : deliveryCompanies) {
    // treeMap.put(deliveryCompany.getName(), deliveryCompany.getId() + "");
    // }
    // for (String string : treeMap.keySet()) {
    //// System.out.println(string);
    // System.out.println(treeMap.get(string));
    // }
    // }


    // treeMap.clear();
    // DefaultSuningClient suningClient = new DefaultSuningClient(
    // "http://open.suning.com/api/http/sopRequest",
    // "c9077bca850b21185ffac278b957f82b", "ca4779e629b16650da196fae4971d57d");
    // LogisticcompanyQueryRequest request = new LogisticcompanyQueryRequest();
    // request.setPageNo(1);
    // request.setPageSize(50);
    // LogisticcompanyQueryResponse response = suningClient.excute(request);
    // if (response != null && response.getSnbody() != null) {
    //
    // int pageCount = response.getSnhead().getPageTotal();
    // for (int i = pageCount; i > 0; i--) {
    // request.setPageNo(i);
    // response = suningClient.excute(request);
    // if (response != null && response.getSnbody() != null) {
    // List<LogisticCompany> companies = response.getSnbody().getLogisticCompany();
    // for (LogisticCompany logisticCompany : companies) {
    // treeMap.put(logisticCompany.getExpressCompanyName(),
    // logisticCompany.getExpressCompanyCode());
    // }
    // }
    // }
    // for (String string : treeMap.keySet()) {
    // System.out.println(treeMap.get(string));
    // }
    // }
  }
}
