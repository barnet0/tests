package com.digiwin.ecims.test.baidu;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies.DeliveryCompany;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies.FindDeliveryCompaniesRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findDeliveryCompanies.FindDeliveryCompaniesResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.api.MyOrderService;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.api.MyServiceFactory;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Request;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.RequestHeader;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;

public class BaiduApiTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() throws Exception {
    int pageSize = 50;
    int i = 1;
    Response<FindDeliveryCompaniesResponse> response = a(i, pageSize);
    int totalSize = response.getBody().getData().get(0).getTotalCount();
    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    System.out.println("\t id \t name");
    for (int j = 1; j < pageNum + 1; j++) {
      response = a(j, pageSize);
      
      for (DeliveryCompany deliveryCompany : response.getBody().getData().get(0).getDeliveryCompanyList()) {
        System.out.println("\t " + deliveryCompany.getCompanyId() + " \t " + deliveryCompany.getCompanyName());  
      }
      
      
    }
    
  }
  
  private Response a(int pageNum, int pageSize) throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService("https://api.baidu.com/json/mall/v1/");
 // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername("mercury2015");
    header.setPassword("SDbaidu201401");
    header.setTarget("mercury2015");
    header.setToken("9a6960fd621155ec09b1b6e55df36b58");

    Request<FindDeliveryCompaniesRequest> request = new Request<FindDeliveryCompaniesRequest>();
    FindDeliveryCompaniesRequest body = new FindDeliveryCompaniesRequest();
    body.setPageNo(pageNum);
    body.setPageSize(pageSize);

    request.setHeader(header);
    request.setBody(body);

    Response<FindDeliveryCompaniesResponse> response = orderService.findDeliveryCompanies(request);

    return response;
  }

}
