package com.digiwin.ecims.platforms.yougou.service.api.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.platforms.yougou.bean.YougouClient;
import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.inventory.InventoryQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.inventory.InventoryUpdateRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderCompletedUpdateRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderIncrementQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.order.OrderQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.request.refund.ReturnQualityQueryRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.base.YougouBaseResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.inventory.InventoryQueryResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.inventory.InventoryUpdateResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderCompletedUpdateResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderIncrementQueryResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderQueryResponse;
import com.digiwin.ecims.platforms.yougou.bean.response.refund.ReturnQualityQueryResponse;
import com.digiwin.ecims.platforms.yougou.service.api.YougouApiService;
import com.digiwin.ecims.platforms.yougou.util.YougouCommonTool;
import com.digiwin.ecims.platforms.yougou.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.yougou.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.yougou.util.translator.AomsrefundTTranslator;

@Service
public class YougouApiServiceImpl implements YougouApiService {

  private static final Logger logger = LoggerFactory.getLogger(YougouApiServiceImpl.class);

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private ParamSystemService paramSystemService;

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
    String orderId = cmdReq.getParams().get("id");

    OrderGetRequest request = new OrderGetRequest();
    request.setOrderSubNo(orderId);
    OrderGetResponse response =
        yougouOrderGet(request, esVerification.getAppKey(), esVerification.getAppSecret());

    boolean success =
        response != null && response.getCode().equals(YougouCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getMessage()), success
            ? new AomsordTTranslator(response.getItem()).doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String refundId = cmdReq.getParams().get("id");

    ReturnQualityQueryRequest request = new ReturnQualityQueryRequest();
    request.setApplyNo(refundId);
    ReturnQualityQueryResponse response = yougouReturnQualityQuery(request,
        esVerification.getAppKey(), esVerification.getAppSecret());

    boolean success =
        response != null && response.getCode().equals(YougouCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getMessage()),
        success
            ? new AomsrefundTTranslator(response.getItems().get(0)).doTranslate(cmdReq.getStoreid())
            : null);
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = params.get("numid");
    InventoryQueryRequest request = new InventoryQueryRequest();
    request.setProductNo(numId);
    request.setPageIndex(YougouCommonTool.MIN_PAGE_NO);
    request.setPageSize(YougouCommonTool.DEFAULT_PAGE_SIZE);
    
    InventoryQueryResponse response = yougouInventoryQuery(request, esVerification.getAppKey(), 
        esVerification.getAppSecret());
    
    boolean success =
        response != null && response.getCode().equals(YougouCommonTool.RESPONSE_SUCCESS_CODE);
    
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getMessage()),
        success
            ? new AomsitemTTranslator(response.getItems()).doTranslate(cmdReq.getStoreid())
            : null);
  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdreq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String oid = params.get("oid");
    String expressno = params.get("expressno");
    String expcompno = params.get("expcompno");

    OrderCompletedUpdateRequest request = new OrderCompletedUpdateRequest();
    request.setOrderSubNos(oid);
    request.setExpressCodes(expressno);
    request.setLogisticsCompanyCodes(expcompno);
    request.setShipTimes(DateTimeTool.getTodayBySecond());
    OrderCompletedUpdateResponse response = yougouOrderCompletedUpdate(request,
        esVerification.getAppKey(), esVerification.getAppSecret());

    boolean success =
        response != null && response.getCode().equals(YougouCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getMessage()), null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();

    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = params.get("numid"); // 商品在電商平台的ID
    String skuId = params.get("skuid");
    String outerId = params.get("outerid"); // 產品在ERP的料號
    String num = params.get("num"); // 數量

    InventoryUpdateRequest request = new InventoryUpdateRequest();
    request.setThirdPartyCode(outerId);
    request.setQuantity(Integer.parseInt(num));
    InventoryUpdateResponse response =
        yougouInventoryUpdate(request, esVerification.getAppKey(), esVerification.getAppSecret());

    boolean success =
        response != null && response.getCode().equals(YougouCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getMessage()), null);
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public OrderGetResponse yougouOrderGet(OrderGetRequest request, String appKey, String appSecret)
      throws Exception {
    return doApiCall(request, appKey, appSecret);
  }

  @Override
  public OrderQueryResponse yougouOrderQuery(OrderQueryRequest request, String appKey,
      String appSecret) throws Exception {
    return doApiCall(request, appKey, appSecret);
  }

  @Override
  public OrderIncrementQueryResponse yougouOrderIncrementQuery(OrderIncrementQueryRequest request,
      String appKey, String appSecret) throws Exception {
    return doApiCall(request, appKey, appSecret);
  }

  @Override
  public InventoryQueryResponse yougouInventoryQuery(InventoryQueryRequest request, String appKey,
      String appSecret) throws Exception {
    return doApiCall(request, appKey, appSecret);
  }

  @Override
  public ReturnQualityQueryResponse yougouReturnQualityQuery(ReturnQualityQueryRequest request,
      String appKey, String appSecret) throws Exception {
    return doApiCall(request, appKey, appSecret);
  }

  @Override
  public OrderCompletedUpdateResponse yougouOrderCompletedUpdate(
      OrderCompletedUpdateRequest request, String appKey, String appSecret) throws Exception {
    return doApiCall(request, appKey, appSecret);
  }

  @Override
  public InventoryUpdateResponse yougouInventoryUpdate(InventoryUpdateRequest request,
      String appKey, String appSecret) throws Exception {
    return doApiCall(request, appKey, appSecret);
  }

  private <T extends YougouBaseResponse> T doApiCall(YougouBaseRequest<T> request, String appKey,
      String appSecret) throws Exception {
    YougouClient client = new YougouClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.YOUGOU_API),
        appKey, appSecret);

    T response = client.execute(request);

    return response;
  }
}
