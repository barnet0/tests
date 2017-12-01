package com.digiwin.ecims.test.aliexpress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.digiwin.ecims.platforms.aliexpress.bean.AliExpressClient;
import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderListQueryRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderListQueryResponse;
import com.digiwin.ecims.core.util.DateTimeTool;

public class AeApiTest {

  private String baseUrl;
  private String appKey;
  private String appSecret;
  private String accessToken;
  
  @Before
  public void setUp() throws Exception {
    this.baseUrl = "https://gw.api.alibaba.com/openapi/";
    this.appKey = "41450330";
    this.appSecret = "XXxk5EvSPuZ";
    this.accessToken = "f19b9b8e-d58b-4287-92c5-c858b6fd55ec";
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() throws Exception {
    AliExpressClient client =
        new AliExpressClient(baseUrl, appKey, appSecret);

    FindOrderListQueryRequest request = new FindOrderListQueryRequest();
    request.setPage(1);
    request.setPageSize(100);
    request.setCreateDateStart("2016-09-19 08:36:16");
    request.setCreateDateEnd(DateTimeTool.getTodayBySecond());
    FindOrderListQueryResponse response = client.execute(request, accessToken);
    System.out.println(response.getOrderList().get(0).getGmtCreate());
    
//    FindOrderByIdRequest request = new FindOrderByIdRequest();
//    request.setOrderId(75315672028908L);
//    FindOrderByIdResponse response = client.execute(request, accessToken);
//    System.out.println(JsonUtil.objectToJsonString(response));
    
//    QueryIssueListRequest request = new QueryIssueListRequest();
//    request.setCurrentPage(1);
//    QueryIssueListResponse response = client.execute(request, accessToken);
//    System.out.println(JsonUtil.objectToJsonString(response));
    
//    QueryIssueDetailRequest request = new QueryIssueDetailRequest();
//    request.setIssueId(235l);
//    QueryIssueDetailResponse response = client.execute(request, accessToken);
//    System.out.println(response.getSuccess());
    
//    FindProductInfoListQueryRequest request = new FindProductInfoListQueryRequest();
////    request.setProductStatusType("onSelling");
////    request.setProductStatusType("offline");
////    request.setProductStatusType("auditing");
//    request.setProductStatusType("editingRequired");
//    FindProductInfoListQueryResponse response = client.execute(request, accessToken);
//    System.out.println(response.getProductCount());
    
//    FindAeProductByIdRequest request = new FindAeProductByIdRequest();
//    request.setProductId(2038291942L);
//    FindAeProductByIdResponse response = client.execute(request, accessToken);
//    System.out.println(response.getAeopAeProductSKUs().get(0).getSkuCode());
    
//    EditSingleSkuStockRequest request = new EditSingleSkuStockRequest();
//    request.setProductId(32223893270L);
//    request.setSkuId("14:29;5:201298991");
//    request.setIpmSkuStock(3760L);
//    EditSingleSkuStockResponse response = client.execute(request, accessToken);
//    System.out.println(response.getModifyCount());
    
//    EditMutilpleSkuStocksRequest request = new EditMutilpleSkuStocksRequest();
//    request.setProductId(2038575794l);
//    Map<String, Long> skuStockMap = new HashMap<String, Long>();
//    skuStockMap.put("14:29;5:201298991", -1l);
//    String skuStocks = JsonUtil.objectToJsonString(skuStockMap);
//    request.setSkuStocks(skuStocks);;
//    EditMutilpleSkuStocksResponse response = client.execute(request, accessToken);
//    System.out.println(response);
//    
//    OnlineAeProductRequest request = new OnlineAeProductRequest();
//    request.setProductIds("2038575794");
//    OnlineAeProductResponse response = client.execute(request, accessToken);
//    System.out.println(response.getModifyCount());
    
//    OfflineAeProductRequest request = new OfflineAeProductRequest();
//    request.setProductIds("2038575794");
//    OfflineAeProductResponse response = client.execute(request, accessToken);
//    System.out.println(response.getModifyCount());
    
//    GetChildAttributesResultByPostCateIdAndPathRequest request = new GetChildAttributesResultByPostCateIdAndPathRequest();
//    request.setCateId(40602);
//    GetChildAttributesResultByPostCateIdAndPathResponse response = client.execute(request, accessToken);
//    System.out.println(response.getAttributes().size());
//    response.getAttributes().get(0).getValues().get(0).getAttributes();
    
  }

}
