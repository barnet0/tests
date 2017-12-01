package com.digiwin.ecims.test.suning;

import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.transaction.OrderQueryRequest;
import com.suning.api.entity.transaction.OrderQueryResponse;
import com.suning.api.exception.SuningApiException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuningCustomOrderGetJunitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuningCustomOrderGetJunitTest.class);

    private String url = "";

    private String appKey = "";

    private String appSecret = "";

    @Before public void setUp() throws Exception {
        url = "http://open.suning.com/api/http/sopRequest";
        appKey = "c9077bca850b21185ffac278b957f82b";
        appSecret = "ca4779e629b16650da196fae4971d57d";
    }

    @After public void tearDown() throws Exception {
    }

    @Test public void test() throws Exception {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setEndTime("2016-12-21 16:46:20");
        request.setOrderStatus("10");
        request.setPageNo(1);
        request.setPageSize(5);
        request.setStartTime("2016-12-20 15:46:20");
        //api入参校验逻辑开关，当测试稳定之后建议设置为 false 或者删除该行
        request.setCheckParam(true);
        DefaultSuningClient client = new DefaultSuningClient(url, appKey, appSecret, "json");
        try {
            OrderQueryResponse response = client.excute(request);
            System.out.println("返回json/xml格式数据 :" + response.getBody());
        } catch (SuningApiException e) {
            e.printStackTrace();
        }
    }
}
