package com.digiwin.ecims.test.icbc;

import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist.IcbcOrderListResponse;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.test.JUnitTestWithLog4jRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@RunWith(JUnitTestWithLog4jRunner.class)
@ContextConfiguration(locations = "classpath:config/applicationContext-common.xml")
public class IcbcApiTest {

    private String baseUrl;
    private String appKey;
    private String appSecret;
    private String accessToken;

    @Autowired
    private IcbcApiService icbcApiService;
    
    @Before
    public void setUp() throws Exception {
        this.baseUrl = "https://ops.mall.icbc.com.cn//icbcrouter";
        this.appKey = "y6TuOxq2";
        this.appSecret = "APPXA6sut3r9wsrqP1fbXLzjBuSBNNkC";
        this.accessToken = "7wNQaF7nZkVhA9Jsv397kaKBaIy5BLOZ4qNgYpPcTOlFAWQ0k9BUVeVAe30ofZng";
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void test() throws Exception {
        IcbcOrderListResponse listResponse =
            icbcApiService.icbcb2cOrderList(
                appKey, appSecret, accessToken, null, null,
                "2017-01-12 00:00:00",
                "2017-01-14 23:59:59", null);
    }

}
