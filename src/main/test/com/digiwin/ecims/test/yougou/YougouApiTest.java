package com.digiwin.ecims.test.yougou;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.yougou.bean.YougouClient;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderGetResponse;

public class YougouApiTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() {
    YougouClient client = new YougouClient("http://open.yougou.com/mms/api.sc",
        "785bb098_14ce4159748__7fe9", "955e8a03904b37500fa312a422f3712f");
    // OrderQueryRequest request = new OrderQueryRequest();
    // request.setOrderStatus("2");
    // request.setPageIndex(1);
    // request.setPageSize(10);
    //
    // OrderQueryResponse response;
    // try {
    // response = client.execute(request);
    // System.out.println(response.getCode());
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    OrderGetRequest request = new OrderGetRequest();
    request.setOrderSubNo("A660413102176_1");
    OrderGetResponse response;
    try {
      response = client.execute(request);
      System.out.println(JsonUtil.format(response));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // OrderIncrementQueryRequest request = new OrderIncrementQueryRequest();
    // request.setStartModified("2016-01-01 00:00:00");
    // request.setEndModified("2016-03-24 00:00:00");
    // request.setOrderStatus("2");
    // request.setPageIndex(1);
    // request.setPageSize(10);
    // OrderIncrementQueryResponse response;
    // ReturnQualityQueryRequest request = new ReturnQualityQueryRequest();
    // request.setPageIndex("1");
    // request.setPageSize("20");
    // ReturnQualityQueryResponse response;
    // try {
    // response = client.execute(request);
    // System.out.println(response.getCode());
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // InventoryQueryRequest request = new InventoryQueryRequest();
    // request.setPageIndex(1);
    // request.setPageSize(10);
    // request.setProductNo("100192202001");
    // InventoryQueryResponse response;
    // try {
    // response = client.execute(request);
    // System.out.println(response.getCode());
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }


    // LogisticsCompanyGetRequest request = new LogisticsCompanyGetRequest();
    // LogisticsCompanyGetResponse response = null;
    // try {
    // response = client.execute(request);
    // for (LogisticsItem logItem : response.getItems()) {
    // System.out.println(logItem.getLogisticsCompanyCode() + "," +
    // logItem.getLogisticsCompanyName());
    // }
    // } catch (Exception e) {
    // // TODO: handle exception
    // }


    // Calendar calendar = Calendar.getInstance();
    //
    // calendar.setTime(new Date());
    //
    // calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 90);
    //
    // System.out.println(calendar.getTime());
  }

}
