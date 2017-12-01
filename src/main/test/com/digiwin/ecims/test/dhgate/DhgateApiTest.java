package com.digiwin.ecims.test.dhgate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dhgate.open.client.ClientRequest;
import com.dhgate.open.client.CompositeResponse;
import com.dhgate.open.client.DhgateDefaultClient;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderListGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderListGetResponse;
import com.digiwin.ecims.platforms.dhgate.util.DhgateRequestParamBuilder;

public class DhgateApiTest {

  private static DhgateDefaultClient client =
      new DhgateDefaultClient("http://api.dhgate.com/dop/router");
  private final String accessToken = "PeBHVTPByeEsV12uPjWbPOIVjYs7wVLfZHeXk4Pv";

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() {
     CompositeResponse<OrderListGetResponse> response = null;
     client = client.buildRequestClient(accessToken);
     OrderListGetRequest orderListGetRequest = new OrderListGetRequest();
     orderListGetRequest.setPageNo(1);
     orderListGetRequest.setPageSize(30);
     orderListGetRequest.setQuerytimeType("2");
     orderListGetRequest.setStartDate("2016-06-18 14:28:38");
     orderListGetRequest.setEndDate(DateTimeTool.getTodayBySecond());
    
    
     ClientRequest request = DhgateRequestParamBuilder.addParams(client, accessToken,
     orderListGetRequest);
     response = request.post(OrderListGetResponse.class, "dh.order.list.get", "2.0");
     System.out.println(response.getSuccessResponse().getOrderBaseInfoList().get(0).getStartedDate());
    //
    // CompositeResponse<OrderDisputeCloseListResponse> response = null;
    // client = client.buildRequestClient(accessToken);
    // OrderDisputeCloseListRequest listRequest = new OrderDisputeCloseListRequest();
    // listRequest.setPageNo(1);
    // listRequest.setPageSize(10);
    // listRequest.setStartDate("2015-08-02 00:00:00");
    // listRequest.setEndDate("2015-10-31 23:59:59");
    //
    // ClientRequest request = DhgateRequestParamBuilder.addParams(client, accessToken,
    // listRequest);
    // response = request.post(OrderDisputeCloseListResponse.class, "dh.order.disputeclose.list",
    // "2.0");
    // System.out.println(response.getSuccessResponse().getStatus().getMessage());

    // CompositeResponse<ShippingTypeListResponse> response = null;
    // client = client.buildRequestClient(accessToken);
    // ShippingTypeListRequest listRequest = new ShippingTypeListRequest();
    //
    // ClientRequest request = DhgateRequestParamBuilder.addParams(client, accessToken,
    // listRequest);
    // response = request.post(ShippingTypeListResponse.class, "dh.shipping.typelist", "2.0");
    // System.out.println("id,name");
    // for (ShippingType shippingType : response.getSuccessResponse().getShippingTypeList()) {
    // System.out.println(shippingType.getShippingTypeId() + "," + shippingType.getName());
    // }

//    CompositeResponse<OrderGetResponse> response = null;
//    client = client.buildRequestClient(accessToken);
//    OrderGetRequest orderGetRequest = new OrderGetRequest();
//    orderGetRequest.setOrderNo("235208297");
//    ClientRequest request =
//        DhgateRequestParamBuilder.addParams(client, accessToken, orderGetRequest);
//    response = request.post(OrderGetResponse.class, "dh.order.get", "2.0");
//    System.out.println(JsonUtil.format(response));


    // CompositeResponse<OrderDisputeOpenListResponse> response = null;
    // client = client.buildRequestClient(accessToken);
    // OrderDisputeOpenListRequest listRequest = new OrderDisputeOpenListRequest();
    // listRequest.setPageNo(1);
    // listRequest.setPageSize(10);
    //
    // ClientRequest request = DhgateRequestParamBuilder.addParams(client, accessToken,
    // listRequest);
    // response = request.post(OrderDisputeOpenListResponse.class, "dh.order.disputeopen.list",
    // "2.0");
    // System.out.println(response.getSuccessResponse().getOrderDisputeOpenInfos().size());

    // TestA testA = new TestA();
    // testA.setOrderNo("testOrderNo");
    // testA.setPayDate("testPayDate");
    // testA.getTestC().setName("testName");
    // System.out.println(testA.getOrderNo());
    // System.out.println(testA.getPayDate());
    // TestB testB = new TestB();
    // System.out.println(testB.getOrderNo());
    // System.out.println(testB.getPayDate());
    //
    // BeanUtils.copyProperties(testA, testB);
    // System.out.println(testB.getOrderNo());
    // System.out.println(testB.getPayDate());
    // System.out.println(testB.getTestC().getName());
    //
    // System.out.println(System.currentTimeMillis());
  }

}
