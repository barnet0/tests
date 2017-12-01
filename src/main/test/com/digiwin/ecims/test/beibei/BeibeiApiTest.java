package com.digiwin.ecims.test.beibei;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.suning.api.client.Client;

import com.digiwin.ecims.platforms.beibei.bean.BeibeiClient;
import com.digiwin.ecims.platforms.beibei.bean.request.item.OuterItemGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.item.OuterItemWarehouseGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.order.OuterTradeOrderDetailGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.order.OuterTradeOrderGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.refund.OuterRefundDetailGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.refund.OuterRefundsGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemWarehouseGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundsGetResponse;
import com.digiwin.ecims.platforms.pdd.bean.PddClient;

public class BeibeiApiTest {

  BeibeiClient beibeiClient;
  
  String sessionKey;
  
  @Before
  public void setUp() throws Exception {
    beibeiClient = new BeibeiClient(
        "http://api.open.beibei.com/outer_api/out_gateway/route.html", 
        "expa", "b2c212ef5c8222339261ebd5906b1791");
    sessionKey = "2bdae29c56825000578886ef8d9e1";
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() {
//    outerTradeOrderGetTest();
//    outerTradeOrderDetailGetTest();
//    refundsGetTest();
//    refundDetailGetTest();
//    itemWarehouseGet();
    itemGetTest();
//    fail("Not yet implemented");
  }

  private void outerTradeOrderGetTest() {
    OuterTradeOrderGetRequest request = new OuterTradeOrderGetRequest();
    request.setTimeRange("modified_time");
    request.setStartTime("2016-10-01 00:00:00");
    request.setEndTime("2016-10-09 00:00:00");
    request.setPageNo(1L);
    request.setPageSize(5L);
    
    try {
      OuterTradeOrderGetResponse response = beibeiClient.execute(request, sessionKey);
      System.out.println(response.getResponseBody());
      System.out.println(response.getSuccess());
      System.out.println(response.getData().get(0).getOid());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void outerTradeOrderDetailGetTest() {
    OuterTradeOrderDetailGetRequest request = new OuterTradeOrderDetailGetRequest();
    request.setOid("328252200173675215");
    
    try {
      OuterTradeOrderDetailGetResponse response = beibeiClient.execute(request, sessionKey);
      System.out.println(response.getResponseBody());
      System.out.println(response.getSuccess());
      System.out.println(response.getData().getCity());
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void refundsGetTest() {
    OuterRefundsGetRequest request = new OuterRefundsGetRequest();
    request.setTimeRange("modified_time");
    request.setStartTime("2016-10-01 00:00:00");
    request.setEndTime("2016-10-10 00:00:00");
    try {
      OuterRefundsGetResponse response = beibeiClient.execute(request, sessionKey);
      System.out.println(response.getResponseBody());
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  private void refundDetailGetTest() {
    OuterRefundDetailGetRequest request = new OuterRefundDetailGetRequest();
    request.setRefundId("7421946");
    
    try {
      OuterRefundDetailGetResponse response = beibeiClient.execute(request, sessionKey);
      System.out.println(response.getResponseBody());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private void itemWarehouseGet() {
    OuterItemWarehouseGetRequest request = new OuterItemWarehouseGetRequest();
    request.setPageNo(1L);
    request.setPageSize(10L);
    
    try {
      OuterItemWarehouseGetResponse response = beibeiClient.execute(request, sessionKey);
      System.out.println(response.getResponseBody());
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
  
  private void itemGetTest() {
    OuterItemGetRequest request = new OuterItemGetRequest();
    request.setIid("1605107");
    
    try {
      OuterItemGetResponse response = beibeiClient.execute(request, sessionKey);
      System.out.println(response.getResponseBody());
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
  
  
}
