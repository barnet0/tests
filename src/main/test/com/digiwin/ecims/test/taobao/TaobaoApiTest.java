package com.digiwin.ecims.test.taobao;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;

import com.digiwin.ecims.core.api.EcImsApiService.EcImsApiEnum;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.platforms.taobao.bean.jst.JstParam;
import com.digiwin.ecims.platforms.taobao.bean.jst.PostBean;
import com.digiwin.ecims.platforms.taobao.bean.response.cainiao.waybillget.CainiaoWaybillGetPrintData;

public class TaobaoApiTest {

    public static void main(String[] args) {
        //    PostBean postBean = new PostBean();
        //    postBean.setApi("taobao.tb.trade.getList");
        //
        //    JstParam jstParam = new JstParam();
        //    jstParam.setStartTime("2016-01-01 00:00:00");
        //    jstParam.setEndTime(DateTimeTool.format(new Date()));
        //    jstParam.setSequence("ASC");
        //    jstParam.setScheduleType("testScheduleType");
        //    jstParam.setLimit(100);
        //    jstParam.setComparisonCol("jdpModified");
        //
        //    postBean.setParams(jstParam);
        //
        //    System.out.println(JsonUtil.format(postBean));
        //
        //    NewPostBean newPostBean = JsonUtil.jsonToObject(JsonUtil.format(postBean), NewPostBean.class);
        //    System.out.println(newPostBean.getParams().getLimit());

        //    System.out.println(EcImsApiEnum.DIGIWIN_ORDER_DETAIL_GET);

        //    String string = "{\"data\":{\"recipient\":{\"address\":{\"city\":\"北京市\",\"detail\":\"花家地社区卫生服务站\",\"district\":\"朝阳区\",\"province\":\"北京\",\"town\":\"望京街道\"},\"mobile\":\"1326443654\",\"name\":\"Bar\",\"phone\":\"057123222\"},\"routingInfo\":{\"consolidation\":{\"name\":\"集包地\",\"code\":\"123\"},\"origin\":{\"code\":\"POSTB\",\"name\":\"发件网点名称\"},\"sortation\":{\"name\":\"杭州\"}},\"sender\":{\"address\":{\"city\":\"北京市\",\"detail\":\"花家地社区卫生服务站\",\"district\":\"朝阳区\",\"province\":\"北京\",\"town\":\"望京街道\"},\"mobile\":\"1326443654\",\"name\":\"Bar\",\"phone\":\"057123222\"},\"shippingOption\":{\"code\":\"COD\",\"services\":{\"SVC-COD\":{\"value\":\"200\"}},\"title\":\"代收货款\"},\"waybillCode\":\"9890000160004\"},\"signature\":\"RSA: hqUkugCY2FFXJK10fRcUE0TsvDppxXuBdOEnPVntF3u4H2yaYiPsPXqjdI1C5oXs60vVCfqd8YC6vZx57TnN9/u0VYM/TiFmMtxix9ouPQ2p7G64UyX9BrnRQQCp5ETqJogSzIpxVlz5rx3hy19C+VQrqWgQEw5JqOkoBFXhMi8=\",\"templateURL\":\"http: //cloudprint.cainiao.com/cloudprint/template/getStandardTemplate.json?template_id=1001\"}";
        //
        //    CainiaoWaybillGetPrintData data = JsonUtil.jsonToObject(string, CainiaoWaybillGetPrintData.class);
        //    System.out.println(data.getData().getShippingOption().getServices());

        //    String appKey = "12364339";
        //    String appSecret = "046c481f480462907718bd1cce7f2403";
        //    String accessToken = "6100d1365b14a9f369117178293b09ac4e23011879c6a0292449256";
        //
        //    Date date = new Date();
        //    TaobaoClient client = new DefaultTaobaoClient("http://114.55.12.230:30001/tbapi", appKey, appSecret);
        //    TradesSoldGetRequest request = new TradesSoldGetRequest();
        //    request.setFields("tid,receiver_name,receiver_address,modified");
        //    request.setStartCreated(DateTimeTool.getBeforeDays(date, 90));
        //    request.setStartCreated(date);

        //    TradeFullinfoGetRequest request = new TradeFullinfoGetRequest();
        //    request.setTid(2889943510114875L);
        //    request.setFields("tid,receiver_name,receiver_address,created,modified");
        //    try {
        ////      TradesSoldGetResponse response = client.execute(request, accessToken);
        //      TradeFullinfoGetResponse response = client.execute(request, accessToken);
        //      System.out.println(response.getBody());
        //    } catch (ApiException e) {
        //      // TODO Auto-generated catch block
        //      e.printStackTrace();
        //    }

        String appKey = "23064056";
        String appSecret = "903690d55255c2842293901302b910b7";
        String accessToken = "6200c28cb6b38818ff559a60855b11ad4a01ZZ6422ea39e92042735";
        TaobaoClient client =
            new DefaultTaobaoClient("http://121.41.171.119:30001/tbapi",
                appKey, appSecret);
        TraderatesGetRequest request = new TraderatesGetRequest();
        request.setStartDate(DateTimeTool.parse("2016-10-20 00:00:00"));
        request.setEndDate(DateTimeTool.parse("2017-01-19 00:00:00"));
        request.setFields("tid");
        request.setRateType("give");
        request.setRole("seller");
        try {
            TraderatesGetResponse response = client.execute(request, accessToken);
            System.out.println(response.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }

        //    TradesSoldGetRequest request = new TradesSoldGetRequest();
        //    request.setFields("tid");
        //    request.setUseHasNext(false);
        //    request.setPageNo(1L);
        //    request.setPageSize(1L);
        //    request.setStartCreated(DateTimeTool.parse("2016-10-10 00:00:00"));
        //    request.setEndCreated(DateTimeTool.parse("2016-11-10 16:00:00"));
        //    TradesSoldGetResponse response = null;
        //
        //    Map<String, String> accessTokenMap = new LinkedHashMap<>();
        //    accessTokenMap.put("MISC-1", "6200024c369d314861bf016429b4c4aaceca1658c201704170712287");
        //    accessTokenMap.put("MISC-3", "6200c28cb6b38818ff559a60855b11ad4a01ZZ6422ea39e92042735");
        //    accessTokenMap.put("MISC-10", "6200d2822a08de4e6485c25f95bff45fd473ZZbc5072879688024484");
        //    accessTokenMap.put("MISC-12", "62028167f0155639ffaa4d0dfhb275598f94947b3cc69dc675786499");
        //    accessTokenMap.put("MISC-18", "6102315fe692aaca2b207991eb880d898f1791a1ad3f30c2660710392");
        //    accessTokenMap.put("MISC-19", "61025232b346732c0a12e4239736cc21ee45fbf3349e2902746783448");
        //    accessTokenMap.put("MISC-20", "610162911295fe9bb06885bbf9e40382b25c21b377f09e52746793196");
        //
        //    Set<String> storeIds = accessTokenMap.keySet();
        //
        //    for (String storeId : storeIds) {
        //      String accessToken = accessTokenMap.get(storeId);
        //      try {
        //        response = client.execute(request, accessToken);
        //        if (response != null) {
        //          System.out.println(storeId + " " + response.getTotalResults());
        //        }
        //      } catch (ApiException e) {
        //        // TODO Auto-generated catch block
        //        e.printStackTrace();
        //      }
        //    }

    }
}
