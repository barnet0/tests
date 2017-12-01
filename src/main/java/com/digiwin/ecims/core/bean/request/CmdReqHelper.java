package com.digiwin.ecims.core.bean.request;

import com.digiwin.ecims.core.service.CmdService;
import com.digiwin.ecims.platforms.taobao.bean.request.EcimsTaobaoWaybillIPrintReqParam;
import com.digiwin.ecims.platforms.taobao.bean.request.EcimsWaybillIGetOrdersParam;
import com.digiwin.ecims.platforms.yunji.bean.domain.logistic.LogisticList;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 自定義的 prase json 的工具. 保留了原本設計的格式.
 *
 * @author Xavier
 */
public final class CmdReqHelper {

    private static CmdReqHelper ch;

    private CmdReqHelper() {
    }

    public static CmdReqHelper getInstance() {
        if (ch == null) {
            ch = new CmdReqHelper();
        }
        return ch;
    }

    /**
     * 分析 JSON 格式
     *
     * @param json
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws Exception
     */
    public CmdReq[] doParse(String json)
        throws JsonProcessingException, IOException, IllegalAccessException,
        InvocationTargetException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode cmdNode = rootNode.get("cmd");
        if (cmdNode.isArray()) {
            CmdReq[] data = new CmdReq[cmdNode.size()];
            for (int i = 0; i < cmdNode.size(); i++) {
                JsonNode jn0 = cmdNode.path(i);
                data[i] = this.doParseSingle(mapper, jn0);
            }
            return data;
        }
        // throw new Exception("parse JSON error!!"); // mark by mowj 20150828 并且添加了新的excetpions
        return null;
    }

    private CmdReq doParseSingle(ObjectMapper mapper, JsonNode rootNode)
        throws IllegalAccessException, InvocationTargetException, JsonParseException,
        JsonMappingException, IOException {
        CmdReq req = new CmdReq();
        // ObjectMapper mapper = new ObjectMapper();
        // JsonNode rootNode = mapper.readTree(json);
        Iterator<Entry<String, JsonNode>> iter = rootNode.fields();
        while (iter.hasNext()) {
            Entry<String, JsonNode> entry = (Entry<String, JsonNode>) iter.next();

            if (entry.getValue().isContainerNode()) {
                // params 的節點.
                JsonNode paramsNode = rootNode.path(entry.getKey());
                Iterator<Entry<String, JsonNode>> paramsIter = paramsNode.fields();

                while (paramsIter.hasNext()) {
                    Entry<String, JsonNode> paramsEntry =
                        (Entry<String, JsonNode>) paramsIter.next();
                    // System.out.println("key --> " + paramsEntry.getKey() + " value-->" +
                    // paramsEntry.getValue());

                    // 以後 params 有新加特殊的欄位, 可以在這邊調整
                    if (paramsEntry.getValue().isArray()) {
                        if ("itemlist".equals(paramsEntry.getKey())) {
                            // 當當 會 用到的特殊欄位
                            // mark by mowj 20160601测试新方法：getSpecialTypedListInParams
                            List<Map<String, String>> m =
                                this.getStringStringMap(mapper, paramsEntry.getValue());
                            //              List<Map<String, String>> m =
                            //                  getSpecialTypedListInParams(mapper, paramsEntry.getValue());
                            BeanUtils.setProperty(req, paramsEntry.getKey(), m);
                        } else if ("prodcodelist".equals(paramsEntry.getKey()) || "suboidlist"
                            .equals(paramsEntry.getKey())) { // suning 會用到的特殊欄位
                            String[] value = this.getArray(mapper, paramsEntry.getValue());
                            BeanUtils.setProperty(req, paramsEntry.getKey(), value);
                        } else if ("orderlist".equals(paramsEntry.getKey())) {
                            // add by mowj 20160601 for 菜鸟物流申请面单号
                            List<EcimsWaybillIGetOrdersParam> m =
                                getSpecialTypedListInParams(mapper, paramsEntry.getValue(),
                                    EcimsWaybillIGetOrdersParam.class);
                            BeanUtils.setProperty(req, paramsEntry.getKey(), m);
                        } else if ("printlist".equals(paramsEntry.getKey())) {
                            // add by mowj 20160601 for 菜鸟物流面单打印前确认
                            List<EcimsTaobaoWaybillIPrintReqParam> m =
                                getSpecialTypedListInParams(mapper, paramsEntry.getValue(),
                                    EcimsTaobaoWaybillIPrintReqParam.class);
                            BeanUtils.setProperty(req, paramsEntry.getKey(), m);
                        } else if ("oidlist".equals(paramsEntry.getKey())) {
                            // add by mowj 20160602 for 菜鸟物流取消面单
                            List<String> m =
                                getSpecialTypedListInParams(mapper, paramsEntry.getValue(),
                                    String.class);
                            BeanUtils.setProperty(req, paramsEntry.getKey(), m);
                        }else if ("logisticlist".equals(paramsEntry.getKey())) {
                            // add by xiaohb 20170704 for 云集平台发货确认
                            List<LogisticList> m =
                                getSpecialTypedListInParams(mapper, paramsEntry.getValue(),
                                	LogisticList.class);
                            BeanUtils.setProperty(req, paramsEntry.getKey(), m);							
						}else {
                            //20160305 add by wucla for 如果是其他数组, 则将数组json字符串写入params
                            req.getParams()
                                .put(paramsEntry.getKey(), paramsEntry.getValue().toString());
                        }
                    } else {
                        if (paramsEntry.getValue().isContainerNode()) {
                            //20160305 add by wucla for 如果还有子结点, 则将子结点字符串写入params
                            req.getParams()
                                .put(paramsEntry.getKey(), paramsEntry.getValue().toString());
                        } else {
                            //params 一般的節點
                            req.getParams().put(paramsEntry.getKey(),
                                paramsEntry.getValue().asText().replaceAll("\"", ""));
                        }
                    }
                }
            } else {
                // 一般的節點
                BeanUtils.setProperty(req, entry.getKey(),
                    entry.getValue().asText().replaceAll("\"", ""));
            }
        }
        return req;
    }

    /**
     * 取得陣列
     *
     * @param mapper
     * @param jNode
     * @return
     * @throws Exception
     */
    private String[] getArray(ObjectMapper mapper, JsonNode jNode)
        throws IOException, JsonParseException, JsonMappingException {
        TypeReference<String[]> typeRef = new TypeReference<String[]>() {
        };
        String[] arr = mapper.readValue(jNode.traverse(), typeRef);
        return arr;
    }

    private List<Map<String, String>> getStringStringMap(ObjectMapper mapper, JsonNode jNode)
        throws IOException, JsonParseException, JsonMappingException {
        TypeReference<List<Map<String, String>>> typeRef =
            new TypeReference<List<Map<String, String>>>() {
            };
        List<Map<String, String>> m = mapper.readValue(jNode.traverse(), typeRef);
        return m;
    }

    public <T> List<T> getSpecialTypedListInParams(ObjectMapper mapper, JsonNode jsonNode,
        Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        //    TypeReference<List<T>> typeRef = new TypeReference<List<T>>() {};
        //    List<T> list = mapper.readValue(jsonNode.traverse(), typeRef);
        List<T> list = mapper.readValue(jsonNode.traverse(), type);
        return list;
    }

    public void validateCmdReq(CmdReq cmdReq) {
        String ecno = cmdReq.getEcno();

        // 參數null判斷..
        if (cmdReq.getParams() == null || (cmdReq.getApi() == null || cmdReq.getApi().equals(""))
            || (ecno == null || ecno.equals(""))) {
            throw new NullPointerException("_031");
        }
        // ecno範圍:
        // 0:淘宝,1:京东,2:一号店,3:工行,4:苏宁,5:当当,6:拍拍,9:其他,A:淘宝分销,B:QQ网购,C:卓越亚马逊
        // else if(Integer.parseInt(ecno)<0 || Integer.parseInt(ecno)>6){ //
        // mark by mowj 20150723
        // else if (!ecno.equals("0") && !ecno.equals("1") && !ecno.equals("2") && !ecno.equals("3") &&
        // !ecno.equals("4") && !ecno.equals("5")
        // && !ecno.equals("6") && !ecno.equals("9") && !ecno.equals("A") && !ecno.equals("B") &&
        // !ecno.equals("C")) // mark by mowj 20160215
        else if (!isEcnoValid(ecno)) {
            throw new IllegalArgumentException("_032");
        }
    }

    public boolean isEcnoValid(String ecno) {
        for (String validEcNo : CmdService.VALID_STORE_TYPES) {
            if (ecno.equals(validEcNo)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        // String json =
        // "{\"cmd\":[{\"ecno\":\"0\",\"storeid\":\"店ID\",\"api\":\"digiwin.order.detail.get\",\"params\":{\"isfx\":\"0\",\"id\":\"1234567\",\"prodcodelist\":[\"aaaa\",\"bbbb\"],\"suboidlist\":[\"11111\",\"22222\"],\"itemlist\":[{\"itemid\":\"12324\",\"count\":\"1\"},{\"itemid\":\"34894\",\"count\":\"2\"}]}},{\"ecno\":\"1\",\"storeid\":\"店ID222\",\"api\":\"digiwin.order.detail.get\",\"params\":{\"isfx\":\"0\",\"id\":\"1234567\",\"prodcodelist\":[\"aaaa\",\"bbbb\"],\"suboidlist\":[\"11111\",\"22222\"],\"itemlist\":[{\"itemid\":\"12324\",\"count\":\"1\"},{\"itemid\":\"34894\",\"count\":\"2\"}]}}]}";
        //
        // System.out.println(json);
        // CmdReqHelper ch = new CmdReqHelper();
        // CmdReq[] req = ch.doParse(json);
        //
        // System.out.println(req[0]);
        //
        // ObjectMapper mapper = new ObjectMapper();
        // JsonNode rootNode = mapper.readTree(json);
        // JsonNode cmdNode = rootNode.get("cmd");
        // if (cmdNode.isArray()) {
        // CmdReqTest[] data = new CmdReqTest[cmdNode.size()];
        // for (int i = 0; i < cmdNode.size(); i++) {
        // JsonNode jn0 = cmdNode.path(i);
        // data[i] = mapper.readValue(jn0.toString(), CmdReqTest.class);
        // }
        //
        // for (CmdReqTest cmdReqTest : data) {
        // Set<String> paramKeys = cmdReqTest.getParams().keySet();
        // for (String key : paramKeys) {
        // Object value = cmdReqTest.getParams().get(key);
        // System.out.println("key:" + key + ", value:" + value);
        // if (value instanceof ArrayList) {
        // ArrayList list = (ArrayList)value;
        // for (Iterator iterator = list.iterator(); iterator.hasNext();) {
        // Object object = (Object) iterator.next();
        // System.out.println(object.getClass());
        // if (object instanceof Map) {
        // Set<String> mapKeys = ((Map)object).keySet();
        // for (String mapKey : mapKeys) {
        // Object mapValue = ((Map)object).get(mapKey);
        // System.out.println("key:" + mapKey + ", value:" + mapValue);
        // }
        // }
        // }
        // }
        // }
        // }
        // }

        String json =
            "{\"cmd\":[{\"ecno\":\"0\",\"storeid\":\"TMZMD\",\"api\":\"digiwin.wlb.waybill.i.get\",\"params\":{\"cpcode\":\"YUNDA\",\"sendprovince\":\"浙江\",\"sendaddress\":\"浙江省义乌市廿三里街道开元北街121\",\"orderlist\":[{\"rcvname\":\"value\",\"ordchannel\":\"value\",\"oidlist\":[12321321,12321322],\"rcvphone\":\"13242422352\",\"rcvprovince\":\"上海\",\"rcvaddress\":\"静安区共和新路4666弄1号\",\"itemlist\":[{\"itemname\":\"测试商品1\",\"count\":1},{\"itemname\":\"测试商品2\",\"count\":10}],\"expressprodtype\":\"STANDARD\"}]}}]}";

        //    json = "{\"cmd\":[{\"ecno\":\"4\",\"storeid\":\"店铺ID\",\"api\":\"digiwin.shipping.send\",\"params\":{\"oid\":\"12345\",\"expressno\":\"33333\",\"expcompno\":\"Y02\",\"prodcodelist\":[\"12324\",\"345567\"],\"suboidlist\":[\"00022019545701\",\"00022019545702\"]}}]}";

        System.out.println(json);

        CmdReqHelper ch = new CmdReqHelper();
        CmdReq[] req = ch.doParse(json);

        // for (CmdReq cmdReq : req) {
        // List<EcimsTaobaoWaybillIGetParamOrderList> orderInfos = cmdReq.getOrderlist();
        // for (EcimsTaobaoWaybillIGetParamOrderList taobaoWayBillIGetOrderInfo : orderInfos) {
        // System.out.println(taobaoWayBillIGetOrderInfo.getRcvAddress());
        // List<String> oidList = taobaoWayBillIGetOrderInfo.getOidList();
        // for (String oid : oidList) {
        // System.out.println(oid);
        // }
        // List<? extends Map<String, String>> itemList = taobaoWayBillIGetOrderInfo.getItemList();
        // for (Map<String, String> map : itemList) {
        // System.out.println(map);
        // }
        // }
        // }

        //    ObjectMapper mapper = new ObjectMapper();
        //    JsonNode rootNode = mapper.readTree(json);
        //    JsonNode cmdNode = rootNode.get("cmd");
        //    if (cmdNode.isArray()) {
        //      CmdReqTest[] data = new CmdReqTest[cmdNode.size()];
        //      for (int i = 0; i < cmdNode.size(); i++) {
        //        JsonNode jn0 = cmdNode.path(i);
        //        data[i] = mapper.readValue(jn0.toString(), CmdReqTest.class);
        //      }
        //      for (CmdReqTest cmdReqTest : data) {
        //        System.out.println(cmdReqTest);
        //        List<? extends LinkedHashMap<String, Object>> orderList = (List)cmdReqTest.getParams().get("orderlist");
        //        for (LinkedHashMap<String, Object> linkedHashMap : orderList) {
        //          System.out.println(((List)linkedHashMap.get("itemlist")).get(0).getClass());
        //        }
        ////        List prodcodelist = (List)cmdReqTest.getParams().get("prodcodelist");
        ////        for (Object object : prodcodelist) {
        ////          System.out.println(object.getClass());
        ////        }
        //
        //      }
        //    }


    }

}
