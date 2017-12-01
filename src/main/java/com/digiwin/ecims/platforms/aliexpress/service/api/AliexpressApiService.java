package com.digiwin.ecims.platforms.aliexpress.service.api;

import com.digiwin.ecims.platforms.aliexpress.bean.request.category.GetChildAttributesResultByPostCateIdAndPathRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.delivery.SellerShipmentRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.EditMutilpleSkuStocksRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.EditSingleSkuStockRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.FindAeProductByIdRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.FindProductInfoListQueryRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.OfflineAeProductRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.item.OnlineAeProductRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderByIdRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.order.FindOrderListQueryRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.refund.QueryIssueDetailRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.request.refund.QueryIssueListRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.category.GetChildAttributesResultByPostCateIdAndPathResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.delivery.SellerShipmentResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.EditMutilpleSkuStocksResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.EditSingleSkuStockResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.FindAeProductByIdResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.FindProductInfoListQueryResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.OfflineAeProductResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.OnlineAeProductResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderByIdResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderListQueryResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.refund.QueryIssueDetailResponse;
import com.digiwin.ecims.platforms.aliexpress.bean.response.refund.QueryIssueListResponse;
import com.digiwin.ecims.core.api.EcImsApiService;

public interface AliexpressApiService extends EcImsApiService {

  public FindOrderListQueryResponse ApiFindOrderListQuery(FindOrderListQueryRequest request,
      String appKey, String appSecret, String accessToken) throws Exception;

  public FindOrderByIdResponse ApiFindOrderById(FindOrderByIdRequest request, String appKey,
      String appSecret, String accessToken) throws Exception;

  public QueryIssueListResponse ApiQueryIssueList(QueryIssueListRequest request, String appKey,
      String appSecret, String accessToken) throws Exception;

  public QueryIssueDetailResponse ApiQueryIssueDetail(QueryIssueDetailRequest request,
      String appKey, String appSecret, String accessToken) throws Exception;

  public FindProductInfoListQueryResponse ApiFindProductInfoListQuery(
      FindProductInfoListQueryRequest request, String appKey, String appSecret, String accessToken)
          throws Exception;

  public FindAeProductByIdResponse ApiFindAeProductById(FindAeProductByIdRequest request,
      String appKey, String appSecret, String accessToken) throws Exception;

  public OnlineAeProductResponse ApiOnlineAeProduct(OnlineAeProductRequest request, String appKey,
      String appSecret, String accessToken) throws Exception;

  public OfflineAeProductResponse ApiOfflineAeProduct(OfflineAeProductRequest request,
      String appKey, String appSecret, String accessToken) throws Exception;

  public SellerShipmentResponse ApiSellerShipment(SellerShipmentRequest request, String appKey,
      String appSecret, String accessToken) throws Exception;

  public EditSingleSkuStockResponse ApiEditSingleSkuStock(EditSingleSkuStockRequest request,
      String appKey, String appSecret, String accessToken) throws Exception;

  public EditMutilpleSkuStocksResponse ApiEditMutilpleSkuStocks(
      EditMutilpleSkuStocksRequest request, String appKey, String appSecret, String accessToken) throws Exception;

  public GetChildAttributesResultByPostCateIdAndPathResponse ApiGetChildAttributesResultByPostCateIdAndPath(
      GetChildAttributesResultByPostCateIdAndPathRequest request, String appKey, String appSecret,
      String accessToken) throws Exception;

}
