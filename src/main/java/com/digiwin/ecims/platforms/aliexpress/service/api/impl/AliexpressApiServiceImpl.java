package com.digiwin.ecims.platforms.aliexpress.service.api.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.aliexpress.bean.AliExpressClient;
import com.digiwin.ecims.platforms.aliexpress.bean.domain.category.AeopAttributeDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
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
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;
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
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressApiService;
import com.digiwin.ecims.platforms.aliexpress.service.api.AliexpressCacheService;
import com.digiwin.ecims.platforms.aliexpress.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.aliexpress.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.aliexpress.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class AliexpressApiServiceImpl implements AliexpressApiService {

  private static final Logger logger = LoggerFactory.getLogger(AliexpressApiServiceImpl.class);

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private ParamSystemService paramSystemService;

  @Autowired
  private AliexpressCacheService aliexpressCacheService;
  
  @Override
  public CmdRes execute(CmdReq cmdReq) throws Exception {
    String api = cmdReq.getApi();

//    if (api.equals(EcImsApiEnum.DIGIWIN_ORDER_DETAIL_GET.toString())) { // 獲取單筆訂單詳情
//      return digiwinOrderDetailGet(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_REFUND_GET.toString())) { // 退款單下載
//      return digiwinRefundGet(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DETAIL_GET.toString())) { // 單筆鋪貨下載
//      return digiwinItemDetailGet(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_LISTING.toString())) { // 商品上架
//      return digiwinItemListing(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DELISTING.toString())) { // 商品下架
//      return digiwinItemDelisting(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_UPDATE.toString())) { // 訂單發貨
//      return digiwinItemUpdate(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_SHIPPING_SEND.toString())) { // 訂單發貨
//      return digiwinShippingSend(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_INVENTORY_UPDATE.toString())) { // 店鋪庫存初始化
//      return digiwinInventoryUpdate(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_INVENTORY_BATCH_UPDATE.toString())) { // 批量修改库存
//      return digiwinInventoryBatchUpdate(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_PICTURE_EXTERNAL_UPLOAD.toString())) {
//      return digiwinPictureExternalUpload(cmdReq);
//    } else {
//      return new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
//    }
    
    CmdRes cmdRes = null;
    switch (EcImsApiEnum.parse(api)) {
      case DIGIWIN_ORDER_DETAIL_GET:
        cmdRes = digiwinOrderDetailGet(cmdReq);
        break;
      case DIGIWIN_REFUND_GET:
        cmdRes = digiwinRefundGet(cmdReq);
        break;
      case DIGIWIN_ITEM_DETAIL_GET:
        cmdRes = digiwinItemDetailGet(cmdReq);
        break;
      case DIGIWIN_ITEM_LISTING:
        cmdRes = digiwinItemListing(cmdReq);
        break;
      case DIGIWIN_ITEM_DELISTING:
        cmdRes = digiwinItemDelisting(cmdReq);
        break;
      case DIGIWIN_ITEM_UPDATE:
        cmdRes = digiwinItemUpdate(cmdReq);
        break;
      case DIGIWIN_INVENTORY_UPDATE:
        cmdRes = digiwinInventoryUpdate(cmdReq);
        break;
      case DIGIWIN_INVENTORY_BATCH_UPDATE:
        cmdRes = digiwinInventoryBatchUpdate(cmdReq);
        break;
      case DIGIWIN_SHIPPING_SEND:
        cmdRes = digiwinShippingSend(cmdReq);
        break;
      default:
        cmdRes = new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
        break;
    }
  
    return cmdRes;
  }

  @Override
  public CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    String orderId = cmdReq.getParams().get("id");

    FindOrderByIdRequest request = new FindOrderByIdRequest();
    request.setOrderId(Long.parseLong(orderId));

    FindOrderByIdResponse response = ApiFindOrderById(request, appKey, appSecret, accessToken);

    boolean success = response != null;

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(), success
        ? new AomsordTTranslator(response).doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    String refundId = cmdReq.getParams().get("id");

    QueryIssueDetailRequest request = new QueryIssueDetailRequest();
    request.setIssueId(Long.parseLong(refundId));

    QueryIssueDetailResponse response =
        ApiQueryIssueDetail(request, appKey, appSecret, accessToken);

    boolean success = response != null && response.getSuccess();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(), success
        ? new AomsrefundTTranslator(response.getData()).doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();

    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    String numId = params.get("numid");
    FindAeProductByIdRequest request = new FindAeProductByIdRequest();
    request.setProductId(Long.parseLong(numId));

    FindAeProductByIdResponse response =
        ApiFindAeProductById(request, appKey, appSecret, accessToken);

    GetChildAttributesResultByPostCateIdAndPathRequest childAttrResultRequest = new GetChildAttributesResultByPostCateIdAndPathRequest();
    GetChildAttributesResultByPostCateIdAndPathResponse childAttrResultResponse = null;
    List<AeopAttributeDTO> attributeDTOs = null;
    int categoryId = response.getCategoryId();
    if (aliexpressCacheService.isKeyExist(categoryId + "")) {
      attributeDTOs = (List<AeopAttributeDTO>) aliexpressCacheService.get(categoryId + "");
    } else {
      childAttrResultRequest.setCateId(categoryId);
      childAttrResultResponse = ApiGetChildAttributesResultByPostCateIdAndPath(
          childAttrResultRequest, appKey, appSecret, accessToken);
      attributeDTOs = childAttrResultResponse.getAttributes();
      aliexpressCacheService.put(categoryId + "", attributeDTOs);
    }
    
    boolean success = response != null;

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(), success
        ? new AomsitemTTranslator(response, attributeDTOs).doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();

    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    String numId = params.get("numid");

    OnlineAeProductRequest request = new OnlineAeProductRequest();
    request.setProductIds(numId);

    OnlineAeProductResponse response = ApiOnlineAeProduct(request, appKey, appSecret, accessToken);

    boolean success = response != null && response.getSuccess();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(), null);
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();

    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    String numId = params.get("numid");

    OfflineAeProductRequest request = new OfflineAeProductRequest();
    request.setProductIds(numId);

    OfflineAeProductResponse response =
        ApiOfflineAeProduct(request, appKey, appSecret, accessToken);

    boolean success = response != null && response.getSuccess();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(), null);
  }

  @Override
  public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();

    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    String oid = params.get("oid");
    String expressno = params.get("expressno");
    String expcompno = params.get("expcompno");
    String expcompname = params.get("expcompname");
    String expstate = params.get("expstate");

    SellerShipmentRequest request = new SellerShipmentRequest();
    request.setServiceName(expcompno);
    request.setLogisticsNo(expressno);
    request.setOutRef(oid);
    switch (expstate) {
      case "1":
        request.setSendType("all");
        break;
      case "2":
        request.setSendType("part");
        break;
      default:
        break;
    }
    SellerShipmentResponse response = ApiSellerShipment(request, appKey, appSecret, accessToken);

    boolean success = response != null && response.getSuccess();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(), null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();

    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();

    String numId = params.get("numid");
    String skuId = params.get("skuid");
    String num = params.get("num");

    EditSingleSkuStockRequest request = new EditSingleSkuStockRequest();
    request.setProductId(Long.parseLong(numId));
    request.setSkuId(skuId);
    request.setIpmSkuStock(Long.parseLong(num));

    EditSingleSkuStockResponse response =
        ApiEditSingleSkuStock(request, appKey, appSecret, accessToken);

    boolean success = response != null && response.getSuccess();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(), null);
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    // TODO
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String appKey = esVerification.getAppKey();
    String appSecret = esVerification.getAppSecret();
    String accessToken = esVerification.getAccessToken();
    return null;
  }

  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public FindOrderListQueryResponse ApiFindOrderListQuery(FindOrderListQueryRequest request,
      String appKey, String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public FindOrderByIdResponse ApiFindOrderById(FindOrderByIdRequest request, String appKey,
      String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public QueryIssueListResponse ApiQueryIssueList(QueryIssueListRequest request, String appKey,
      String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public QueryIssueDetailResponse ApiQueryIssueDetail(QueryIssueDetailRequest request,
      String appKey, String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public FindProductInfoListQueryResponse ApiFindProductInfoListQuery(
      FindProductInfoListQueryRequest request, String appKey, String appSecret, String accessToken)
          throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public FindAeProductByIdResponse ApiFindAeProductById(FindAeProductByIdRequest request,
      String appKey, String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public OnlineAeProductResponse ApiOnlineAeProduct(OnlineAeProductRequest request, String appKey,
      String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public OfflineAeProductResponse ApiOfflineAeProduct(OfflineAeProductRequest request,
      String appKey, String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public SellerShipmentResponse ApiSellerShipment(SellerShipmentRequest request, String appKey,
      String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public EditSingleSkuStockResponse ApiEditSingleSkuStock(EditSingleSkuStockRequest request,
      String appKey, String appSecret, String accessToken) throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }

  @Override
  public EditMutilpleSkuStocksResponse ApiEditMutilpleSkuStocks(
      EditMutilpleSkuStocksRequest request, String appKey, String appSecret, String accessToken)
          throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }
  
  @Override
  public GetChildAttributesResultByPostCateIdAndPathResponse ApiGetChildAttributesResultByPostCateIdAndPath(
      GetChildAttributesResultByPostCateIdAndPathRequest request,
      String appKey, String appSecret, String accessToken)
          throws Exception {
    return doApiCall(request, appKey, appSecret, accessToken);
  }


  public <T extends AliExpressBaseResponse> T doApiCall(AliExpressBaseRequest<T> request,
      String appKey, String appSecret, String accessToken) throws Exception {
    AliExpressClient client = new AliExpressClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.ALIEXPRESS_API), appKey, appSecret);

    T response = client.execute(request, accessToken);

    return response;
  }
}
