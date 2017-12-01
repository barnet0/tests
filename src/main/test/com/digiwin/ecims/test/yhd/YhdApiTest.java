package com.digiwin.ecims.test.yhd;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.yhd.YhdClient;
import com.yhd.request.product.SerialProductsSearchRequest;
import com.yhd.response.product.SerialProductsSearchResponse;

import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.platforms.yhd.util.YhdClientUtil;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.ApiPageParam;

public class YhdApiTest {

  private String URL;
  private String appKey;
  private String appSecret;
  private String accessToken;
  
  @Before
  public void setUp() throws Exception {
    URL = "http://openapi.yhd.com/app/api/rest/router";
    appKey = "10220015071400003437";
    appSecret = "85c12e4a2cb35be322d5f0a042e0c1cc";
    accessToken = "7ff8f0fdfac5b01d487b2d11748e83b2";
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void test() throws Exception {
//    fail("Not yet implemented");
    int pageSize = 50;
    
    int totalSize = yhdSerialProductsSearch(appKey, appSecret, accessToken, 
        null, null, 
        ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue(),
        null, null, null, null, null, null, null, null, null, null).getTotalCount();
    
    if (totalSize == 0) {
      fail("size is 0");
    }

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // 針對每一頁(倒序)的所有資料新增系列商品
    for (int i = pageNum; i > 0; i--) {
      SerialProductsSearchResponse response =
          yhdSerialProductsSearch(appKey, appSecret, accessToken, 
              null, null, 
              i, pageSize,
              null, null, null, null, null, null, null, null, null, null);
      if (response.getErrorCount() > 0) {
        System.err.println("当前页" + i + ",页面大小" + pageSize);
      } else {
        System.out.println("当前页" + i + ",页面大小" + pageSize + ", 当前页数量" + response.getSerialProductList().getSerialProduct().size());
      }
    }

  }
    
    public SerialProductsSearchResponse yhdSerialProductsSearch(String appKey, String appSecret, String accessToken, 
        Integer canShow, Integer canSale, Integer curPage, Integer pageRows,
        String productCname, String productIdList, String outerIdList, 
        Integer verifyFlg, Long categoryId, Integer categoryType, Long brandId,
        String productCodeList, String updateStartTime, String updateEndTime) throws Exception {
      YhdClient client = YhdClientUtil.getInstance().getYhdClient(
          URL, appKey, appSecret, accessToken);
//      YhdClient client = new YhdClient(URL, appKey, appSecret);
      SerialProductsSearchRequest request = new SerialProductsSearchRequest();
      
      if (StringTool.isNotEmpty(canShow)) {
        request.setCanShow(canShow);
      }
      if (StringTool.isNotEmpty(canSale)) {
        request.setCanSale(canSale);
      }
      request.setCurPage(curPage);
      request.setPageRows(pageRows);
      if (StringTool.isNotEmpty(productCname)) {
        request.setProductCname(productCname);
      }
      if (StringTool.isNotEmpty(productIdList)) {
        request.setProductIdList(productIdList);
      }
      if (StringTool.isNotEmpty(outerIdList)) {
        request.setOuterIdList(outerIdList);
      }
      if (StringTool.isNotEmpty(verifyFlg)) {
        request.setVerifyFlg(verifyFlg);
      }
      if (StringTool.isNotEmpty(categoryId)) {
        request.setCategoryId(categoryId);
      }
      if (StringTool.isNotEmpty(categoryType)) {
        request.setCategoryType(categoryType);
      }
      if (StringTool.isNotEmpty(brandId)) {
        request.setBrandId(brandId);
      }
      if (StringTool.isNotEmpty(productCodeList)) {
        request.setProductCodeList(productCodeList);
      }
      if (StringTool.isNotEmpty(updateStartTime)) {
        request.setUpdateStartTime(updateStartTime);
      }
      if (StringTool.isNotEmpty(updateEndTime)) {
        request.setUpdateEndTime(updateEndTime);
      }
      
      SerialProductsSearchResponse response = client.excute(request);
//      SerialProductsSearchResponse response = client.excute(request, accessToken);
      
      return response;
    }

}
