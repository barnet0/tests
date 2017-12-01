package com.digiwin.ecims.test.pdd;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.pdd.bean.PddClient;
import com.digiwin.ecims.platforms.pdd.bean.request.item.GetGoodsRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.item.GetSkuStockRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.order.CheckOrdersAfterSaleRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.order.OrderSearchRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetGoodsResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetSkuStockResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.CheckOrdersAfterSaleResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderSearchResponse;

public class PddApiTest {

  PddClient pddClient;
  
  @Before
  public void setUp() throws Exception {
    pddClient = new PddClient(
        "http://mms.yangkeduo.com/api/common", 
        "1472", "4RG8T0I433LJQV4O0K7548KARK3MVLO0");
//        "2", "123456");
  }

  @After
  public void teardown() throws Exception {}
  
  @Test
  public void test() throws Exception {
//    fail("Not yet implemented");
//    OrderSearchRequest request = new OrderSearchRequest();
//    request.setBeginTime("2016-12-21 23:59:59");
//    request.setEndTime("2016-12-21 00:00:00");
//    request.setOrderStatus(1);
//    request.setPage(0);
//    request.setPageSize(100);
//    OrderSearchResponse response = pddClient.execute(request);
//    System.out.println(response.getOrderCount());
    
    OrderGetRequest request = new OrderGetRequest();
    request.setOrderSN("161221-11332383360");
    OrderGetResponse response = pddClient.execute(request);
    System.out.println(response.getCause());
//    
//    GetSkuStockRequest request = new GetSkuStockRequest();
//    request.setPddSkuIds("307144");
//    GetSkuStockResponse response = pddClient.execute(request);
//    System.out.println(response.getCause());
    
//    CheckOrdersAfterSaleRequest request = new CheckOrdersAfterSaleRequest();
//    request.setOrderSNs("160720-75851658594");
//    CheckOrdersAfterSaleResponse response = pddClient.execute(request);
//    System.out.println();
    
//    GetGoodsRequest request = new GetGoodsRequest();
////    request.setGoodsType("InStock");
//    request.setOuterID("10887928");
////    request.setGoodsName("【巴基斯坦进口】【水星家纺】巴洛克进口巴基斯坦提花毛巾 纯棉成人加厚");
//    request.setPage(0);
//    request.setPageSize(100);
//    GetGoodsResponse response = pddClient.execute(request);
//    System.out.println(response.getResponseBody());
  }
    
}
