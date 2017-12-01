package com.digiwin.ecims.platforms.baidu.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.baidu.bean.domain.enums.QueryTimeType;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.DeliveryGoodsItem;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfo.GetItemInfoRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfo.GetItemInfoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos.GetItemInfosRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos.GetItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.grouping.GroupingRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.grouping.GroupingResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.off.OffRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.off.OffResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos.QueryItemInfosRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos.QueryItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.SkuStock;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.UpdateStockRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.UpdateStockResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.batch.BatchUpdateStockRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.batch.BatchUpdateStockResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder.FindOrderRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder.FindOrderResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getinvoice.GetInvoiceRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getinvoice.GetInvoiceResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant.SendGoodByMerchantRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant.SendGoodByMerchantResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo.FindRefundOrderItemInfoRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo.FindRefundOrderItemInfoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno.GetRefundInfoByNoRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno.GetRefundInfoByNoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.api.MyItemService;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.api.MyOrderService;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.api.MyServiceFactory;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Request;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.RequestHeader;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;
import com.digiwin.ecims.platforms.baidu.service.api.BaiduApiService;
import com.digiwin.ecims.platforms.baidu.util.BaiduCommonTool;
import com.digiwin.ecims.platforms.baidu.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.baidu.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.baidu.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class BaiduApiServiceImpl implements BaiduApiService {

  private static final Logger logger = LoggerFactory.getLogger(BaiduApiServiceImpl.class);

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
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取參數
    String orderId = cmdReq.getParams().get("id");

    Response<GetDetailResponse> orderDetailResponse =
        baiduMallOrderDetailGet(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), Long.parseLong(orderId));

    boolean success =
        orderDetailResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(orderDetailResponse.getHeader().getStatus() + "",
                orderDetailResponse.getHeader().getDesc()),
        success ? new AomsordTTranslator(
            orderDetailResponse.getBody().getData().get(0).getOrderDetail())
                .doTranslate(cmdReq.getStoreid())
            : null);

  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String refundId = cmdReq.getParams().get("id");

    Response<GetRefundInfoByNoResponse> refundResponse =
        baiduMallRefundGet(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), Long.parseLong(refundId));

    boolean success = refundResponse != null
        && refundResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(refundResponse.getHeader().getStatus() + "",
                refundResponse.getHeader().getDesc()),
        success ? new AomsrefundTTranslator(refundResponse.getBody().getData().get(0).getResult())
            .doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = params.get("numid");
    Response<GetItemInfoResponse> itemResponse =
        baiduMallItemGet(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), numId);

    boolean success = itemResponse != null
        && itemResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(itemResponse.getHeader().getStatus() + "",
                itemResponse.getHeader().getDesc()),
        success ? new AomsitemTTranslator(itemResponse.getBody().getData().get(0).getItemInfos())
            .doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = params.get("numid");
    // String num = params.get("num");

    Response<GroupingResponse> groupingResponse =
        baiduMallItemGrounding(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), numId);
    boolean success = groupingResponse != null
        && groupingResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(groupingResponse.getHeader().getStatus() + "",
                groupingResponse.getHeader().getDesc()),
        null);
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = params.get("numid");
    // String num = params.get("num");

    Response<OffResponse> offResponse =
        baiduMallItemOff(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), numId);
    boolean success = offResponse != null
        && offResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(offResponse.getHeader().getStatus() + "",
                offResponse.getHeader().getDesc()),
        null);
  }

  @Override
  public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    Long oid = Long.parseLong(params.get("oid"));
    String expressno = params.get("expressno");
    String expcompno = params.get("expcompno");
    String expcompname = params.get("expcompname");
    if (expcompname == null || expcompname.length() == 0) {
      expcompname = expcompno;
    }

    List<DeliveryGoodsItem> deliveryItems = new ArrayList<DeliveryGoodsItem>();
    DeliveryGoodsItem deliveryItem =
        new DeliveryGoodsItem(oid, expcompname, Long.parseLong(expcompno), expressno);
    deliveryItems.add(deliveryItem);

    Response<SendGoodByMerchantResponse> sendGoodsResponse =
        baiduMallOrderSendGoodsByMerchant(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), deliveryItems);

    boolean success = sendGoodsResponse != null
        && sendGoodsResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(sendGoodsResponse.getBody().getData().get(0).getCode() + "",
                sendGoodsResponse.getBody().getData().get(0).getResult().getMessage()),
        null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String skuId = params.get("skuid");
    String num = params.get("num");

    SkuStock skuStock = new SkuStock();
    skuStock.setSkuId(Long.parseLong(skuId));
    skuStock.setNum(Integer.parseInt(num));

    Response<UpdateStockResponse> updStockResponse =
        baiduMallStockUpdate(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), skuStock);

    boolean success = updStockResponse != null
        && updStockResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(updStockResponse.getHeader().getStatus() + "",
                updStockResponse.getHeader().getDesc()),
        null);
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // String numId = params.get("numid");
    String skuIds = params.get("skuids");
    String outerIds = params.get("outerids");
    String nums = params.get("nums");

    String[] skuIdArray = skuIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
    String[] outerIdArray = outerIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
    String[] numArray = nums.split(CMD_INVENTORY_UPDATE_DELIMITER);

    int skuIdCount = skuIdArray.length;
    int outerIdCount = outerIdArray.length;
    int numCount = numArray.length;

    if (skuIdCount != outerIdCount || skuIdCount != numCount || outerIdCount != numCount) {
      return new CmdRes(cmdReq, false, new ResponseError("039", ErrorMessageBox._039), null);
    }

    List<SkuStock> skuStocks = new ArrayList<SkuStock>();
    for (int i = 0; i < skuIdArray.length; i++) {
      SkuStock tempSkuStock = new SkuStock();
      tempSkuStock.setSkuId(Long.parseLong(skuIdArray[i]));
      tempSkuStock.setNum(Integer.parseInt(numArray[i]));

      skuStocks.add(tempSkuStock);
    }
    Response<BatchUpdateStockResponse> batchUpdStockResponse =
        baiduMallStockUpdateList(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), skuStocks);

    boolean success = batchUpdStockResponse != null
        && batchUpdStockResponse.getHeader().getStatus() == BaiduCommonTool.RESPONSE_SUCCESS_CODE;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(batchUpdStockResponse.getHeader().getStatus() + "",
                batchUpdStockResponse.getHeader().getDesc()),
        null);
  }

  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public Response<FindOrderResponse> baiduMallOrdersFind(AomsshopT aomsshopT,
      List<Integer> orderStatusList, QueryTimeType queryTimeType, String startTime, String endTime,
      int pageNo, int pageSize) throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    return baiduMallOrdersFind(esVerification.getAppKey(),
        esVerification.getAppSecret(), esVerification.getAccessToken(), 
        orderStatusList, queryTimeType, startTime, endTime, pageNo, pageSize);
//    Response<FindOrderResponse> findOrderResponse = baiduMallOrdersFind(esVerification.getAppKey(),
//        esVerification.getAppSecret(), esVerification.getAppKey(), esVerification.getAccessToken(),
//        orderStatusList, queryTimeType, startTime, endTime, pageNo, pageSize);
//
//    return findOrderResponse;
  }

  @Override
  public Response<FindOrderResponse> baiduMallOrdersFind(String appKey, String appSecret,
      String accessToken, List<Integer> orderStatusList, QueryTimeType queryTimeType,
      String startTime, String endTime, int pageNo, int pageSize) throws Exception {
    Response<FindOrderResponse> findOrderResponse = baiduMallOrdersFind(appKey,
        appSecret, appKey, accessToken,
        orderStatusList, queryTimeType, startTime, endTime, pageNo, pageSize);

    return findOrderResponse;
  }

  @Override
  public Response<GetDetailResponse> baiduMallOrderDetailGet(AomsshopT aomsshopT, long orderId)
      throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    return baiduMallOrderDetailGet(
        esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAccessToken(), 
        orderId);
//    Response<GetDetailResponse> getDetailResponse =
//        baiduMallOrderDetailGet(esVerification.getAppKey(), esVerification.getAppSecret(),
//            esVerification.getAppKey(), esVerification.getAccessToken(), orderId);
//
//    return getDetailResponse;
  }

  @Override
  public Response<GetDetailResponse> baiduMallOrderDetailGet(String appKey, String appSecret,
      String accessToken, long orderId) throws Exception {
    Response<GetDetailResponse> getDetailResponse =
        baiduMallOrderDetailGet(appKey, appSecret, appKey, accessToken, orderId);

    return getDetailResponse;
  }

  @Override
  public Response<SendGoodByMerchantResponse> baiduMallOrderSendGoodsByMerchant(AomsshopT aomsshopT,
      List<DeliveryGoodsItem> deliveryItems) throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    Response<SendGoodByMerchantResponse> response =
        baiduMallOrderSendGoodsByMerchant(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), deliveryItems);

    return response;
  }

  @Override
  public Response<FindRefundOrderItemInfoResponse> baiduMallRefundsReceiveGet(AomsshopT aomsshopT,
      List<Integer> refundStatusList, String startTime, String refundNo, String orderNo,
      String mobileNo, int pageNo, int pageSize) throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    return baiduMallRefundsReceiveGet(
        esVerification.getAppKey(), esVerification.getAppSecret(),esVerification.getAccessToken(), 
        refundStatusList, startTime, refundNo, orderNo, mobileNo, pageNo, pageSize);
//    Response<FindRefundOrderItemInfoResponse> refundInfoResponse =
//        baiduMallRefundsReceiveGet(esVerification.getAppKey(), esVerification.getAppSecret(),
//            esVerification.getAppKey(), esVerification.getAccessToken(), refundStatusList,
//            startTime, refundNo, orderNo, mobileNo, pageNo, pageSize);
//
//    return refundInfoResponse;
  }

  @Override
  public Response<FindRefundOrderItemInfoResponse> baiduMallRefundsReceiveGet(String appKey,
      String appSecret, String accessToken, List<Integer> refundStatusList, String startTime,
      String refundNo, String orderNo, String mobileNo, int pageNo, int pageSize) throws Exception {
    Response<FindRefundOrderItemInfoResponse> refundInfoResponse =
        baiduMallRefundsReceiveGet(appKey, appSecret,
            appKey, accessToken, refundStatusList,
            startTime, refundNo, orderNo, mobileNo, pageNo, pageSize);

    return refundInfoResponse;
  }

  @Override
  public Response<GetRefundInfoByNoResponse> baiduMallRefundGet(AomsshopT aomsshopT,
      String refundNo) throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    return baiduMallRefundGet(esVerification.getAppKey(), esVerification.getAppSecret(),
        esVerification.getAccessToken(), refundNo);
//    Response<GetRefundInfoByNoResponse> refundInfoResponse =
//        baiduMallRefundGet(esVerification.getAppKey(), esVerification.getAppSecret(),
//            esVerification.getAppKey(), esVerification.getAccessToken(), Long.parseLong(refundNo));
//
//    return refundInfoResponse;
  }

  @Override
  public Response<GetRefundInfoByNoResponse> baiduMallRefundGet(String appKey, String appSecret,
      String accessToken, String refundNo) throws Exception {
    Response<GetRefundInfoByNoResponse> refundInfoResponse =
        baiduMallRefundGet(appKey, appSecret, appKey, accessToken, Long.parseLong(refundNo));

    return refundInfoResponse;
  }

  @Override
  public Response<GetItemInfoResponse> baiduMallItemGet(AomsshopT aomsshopT, String itemId,
      List<String> fields) throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    Response<GetItemInfoResponse> itemInfoResponse =
        baiduMallItemGet(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), itemId);

    return itemInfoResponse;
  }

  @Override
  public Response<GetItemInfosResponse> baiduMallItemGetList(AomsshopT aomsshopT,
      List<Long> itemIds, List<String> fields) throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    Response<GetItemInfosResponse> itemInfosResponse =
        baiduMallItemGetList(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), itemIds, fields);

    return itemInfosResponse;
  }

  @Override
  public Response<QueryItemInfosResponse> baiduMallItemQuery(AomsshopT aomsshopT,
      List<Integer> categoryIds, int pageNum, int pageSize) throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    Response<QueryItemInfosResponse> queryItemInfosResponse = baiduMallItemQuery(
        esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAppKey(),
        esVerification.getAccessToken(), categoryIds, pageNum, pageSize);

    return queryItemInfosResponse;
  }

  @Override
  public Response<GetInvoiceResponse> baiduMallOrderInvoiceGet(AomsshopT aomsshopT, Long orderNo)
      throws Exception {
    ESVerification esVerification =
        aomsShopService.getAuthorizationByStoreId(aomsshopT.getAomsshop001());
    Response<GetInvoiceResponse> response =
        baiduMallOrderInvoiceGet(esVerification.getAppKey(), esVerification.getAppSecret(),
            esVerification.getAppKey(), esVerification.getAccessToken(), orderNo);

    return response;
  }

  /**
   * 获取订单列表：http://dev2.baidu.com/docs.do?product=6#page=%E8%8E%B7%E5%8F%96%E8
   * %AE%A2%E5%8D%95%E5%88%97%E8%A1%A8
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param orderStatusList 订单状态列表
   * @param queryTimeType 查询时间类型
   * @param startTime 查询开始时间
   * @param endTime 查询结束时间
   * @param pageNo 页码
   * @param pageSize 单页大小
   * @return 订单列表响应
   * @throws Exception
   */
  private Response<FindOrderResponse> baiduMallOrdersFind(String userName, String password,
      String target, String token, List<Integer> orderStatusList, QueryTimeType queryTimeType,
      String startTime, String endTime, int pageNo, int pageSize) throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<FindOrderRequest> request = new Request<FindOrderRequest>();
    FindOrderRequest body = new FindOrderRequest();
    body.setOrderStatus(orderStatusList);
    body.setQueryTimeType(queryTimeType.getCode());
    body.setStartTime(startTime);
    body.setEndTime(endTime);
    body.setPageNo(pageNo);
    body.setPageSize(pageSize);

    request.setHeader(header);
    request.setBody(body);

    Response<FindOrderResponse> response = orderService.findOrder(request);

    return response;
  }

  /**
   * 获取单个订单详情：http://dev2.baidu.com/docs.do?product=6#page=%E8%8E%B7%E5%8F%96%
   * E8%AE%A2%E5%8D%95%E8%AF%A6%E6%83%85
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param orderId 订单号
   * @return 订单详情响应
   * @throws Exception
   */
  private Response<GetDetailResponse> baiduMallOrderDetailGet(String userName, String password,
      String target, String token, long orderId) throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<GetDetailRequest> request = new Request<GetDetailRequest>();
    GetDetailRequest body = new GetDetailRequest();
    body.setOrderNo(orderId);

    request.setHeader(header);
    request.setBody(body);

    Response<GetDetailResponse> response = orderService.getDetail(request);

    return response;
  }

  /**
   * 商家发货：http://dev2.baidu.com/docs.do?product=6#page=%E5%95%86%E5%AE%B6%E5% 8F%91%E8%B4%A7
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param deliveryItems
   * @return
   * @throws Exception
   */
  private Response<SendGoodByMerchantResponse> baiduMallOrderSendGoodsByMerchant(String userName,
      String password, String target, String token, List<DeliveryGoodsItem> deliveryItems)
          throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<SendGoodByMerchantRequest> request = new Request<SendGoodByMerchantRequest>();
    SendGoodByMerchantRequest body = new SendGoodByMerchantRequest();
    body.setDeliveryItems(deliveryItems);

    request.setHeader(header);
    request.setBody(body);

    Response<SendGoodByMerchantResponse> response = orderService.sendGoodByMerchant(request);

    return response;
  }

  private Response<GetInvoiceResponse> baiduMallOrderInvoiceGet(String userName, String password,
      String target, String token, Long orderNo) throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<GetInvoiceRequest> request = new Request<GetInvoiceRequest>();
    GetInvoiceRequest body = new GetInvoiceRequest();
    body.setOrderNo(orderNo);

    request.setHeader(header);
    request.setBody(body);

    Response<GetInvoiceResponse> response = orderService.getInvoice(request);

    return response;
  }

  // http://dev2.baidu.com/docs.do?product=6#page=%E8%8E%B7%E5%8F%96%E5%8D%96%E5%AE%B6%E7%9A%84%E9%80%80%E6%AC%BE%E5%8D%95%E5%88%97%E8%A1%A8
  private Response<FindRefundOrderItemInfoResponse> baiduMallRefundsReceiveGet(String userName,
      String password, String target, String token, List<Integer> refundStatusList,
      String startTime, String refundNo, String orderNo, String mobileNo, int pageNo, int pageSize)
          throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<FindRefundOrderItemInfoRequest> request = new Request<FindRefundOrderItemInfoRequest>();
    FindRefundOrderItemInfoRequest body = new FindRefundOrderItemInfoRequest();

    if (mobileNo != null) {
      body.setMobileNumber(mobileNo);
    }
    if (orderNo != null) {
      body.setOrderNo(Long.parseLong(orderNo));
    }
    body.setPageNo(pageNo);
    body.setPageSize(pageSize);
    if (refundNo != null) {
      body.setRefundNo(Long.parseLong(refundNo));
    }
    if (refundStatusList != null && !refundStatusList.isEmpty()) {
      body.setRefundStatusList(refundStatusList);
    }
    if (startTime != null) {
      body.setStartTime(startTime);
    }

    request.setHeader(header);
    request.setBody(body);

    Response<FindRefundOrderItemInfoResponse> response =
        orderService.findRefundOrderItemInfo(request);

    return response;
  }

  /**
   * 获取单个退款单详情：http://dev2.baidu.com/docs.do?product=6#page=%E8%8E%B7%E5%8F%96
   * %E5%8D%95%E4%B8%AA%E9%80%80%E6%AC%BE%E5%8D%95%E7%9A%84%E8%AF%A6%E6%83%85
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param refundNo 退款单号
   * @return
   * @throws Exception
   */
  private Response<GetRefundInfoByNoResponse> baiduMallRefundGet(String userName, String password,
      String target, String token, Long refundNo) throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<GetRefundInfoByNoRequest> request = new Request<GetRefundInfoByNoRequest>();
    GetRefundInfoByNoRequest body = new GetRefundInfoByNoRequest();

    body.setRefundNo(refundNo);

    request.setHeader(header);
    request.setBody(body);

    // TODO 需要测试可行性
    Response<GetRefundInfoByNoResponse> response = orderService.getRefundInfoByNo(request);

    return response;
  }

  /**
   * 获取单个商品信息：http://dev2.baidu.com/docs.do?product=6#page=%E8%8E%B7%E5%8F%96%
   * E5%95%86%E5%93%81%E4%BF%A1%E6%81%AF
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param itemId 商品ID
   * @param fields 需要返回的字段列表，可为null，如果给null，默认返回全部字段
   * @return 商品信息响应
   * @throws Exception
   */
  private Response<GetItemInfoResponse> baiduMallItemGet(String userName, String password,
      String target, String token, String itemId) throws Exception {
    MyItemService itemService =
        MyServiceFactory.createItemService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<GetItemInfoRequest> request = new Request<GetItemInfoRequest>();
    GetItemInfoRequest body = new GetItemInfoRequest();
    body.setItemId(Long.parseLong(itemId));

    request.setHeader(header);
    request.setBody(body);

    Response<GetItemInfoResponse> response = itemService.getItemInfo(request);

    return response;
  }

  /**
   * 批量获取商品信息：http://dev2.baidu.com/docs.do?product=6#page=%E6%89%B9%E9%87%8F%
   * E8%8E%B7%E5%8F%96%E5%95%86%E5%93%81%E4%BF%A1%E6%81%AF
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param itemId 商品ID
   * @param fields 需要返回的字段列表，可为null，如果给null，默认返回全部字段
   * @return 商品列表信息响应
   * @throws Exception
   */
  private Response<GetItemInfosResponse> baiduMallItemGetList(String userName, String password,
      String target, String token, List<Long> itemIds, List<String> fields) throws Exception {
    MyItemService itemService =
        MyServiceFactory.createItemService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<GetItemInfosRequest> request = new Request<GetItemInfosRequest>();
    GetItemInfosRequest body = new GetItemInfosRequest();
    body.setItemIds(itemIds);
    if (fields != null) {
      body.setFields(fields);
    }

    request.setHeader(header);
    request.setBody(body);

    Response<GetItemInfosResponse> response = itemService.getItemInfos(request);

    return response;
  }

  /**
   * 获取商品列表：http://dev2.baidu.com/docs.do?product=6#page=%E8%8E%B7%E5%8F%96%E5
   * %95%86%E5%93%81%E5%88%97%E8%A1%A8
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param categoryIds 类目ID列表。如果为null，则不使用类目进行过滤
   * @param pageNum 页码
   * @param pageSize 单页数量
   * @return 商品ID列表响应信息
   * @throws Exception
   */
  private Response<QueryItemInfosResponse> baiduMallItemQuery(String userName, String password,
      String target, String token, List<Integer> categoryIds, int pageNum, int pageSize)
          throws Exception {
    MyItemService itemService =
        MyServiceFactory.createItemService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<QueryItemInfosRequest> request = new Request<QueryItemInfosRequest>();
    QueryItemInfosRequest body = new QueryItemInfosRequest();
    if (categoryIds != null) {
      body.setCategoryIds(categoryIds);
    }
    body.setPageIndex(pageNum);
    body.setPageSize(pageSize);

    request.setHeader(header);
    request.setBody(body);

    Response<QueryItemInfosResponse> response = itemService.queryItemInfos(request);

    return response;
  }

  /**
   * 商品上架：http://dev2.baidu.com/docs.do?product=6#page=%E5%95%86%E5%93%81%E4% B8%8A%E6%9E%B6
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param itemId 商品ID
   * @return 上架操作响应
   * @throws Exception
   */
  private Response<GroupingResponse> baiduMallItemGrounding(String userName, String password,
      String target, String token, String itemId) throws Exception {
    MyItemService itemService =
        MyServiceFactory.createItemService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<GroupingRequest> request = new Request<GroupingRequest>();
    GroupingRequest body = new GroupingRequest();
    body.setItemId(Long.parseLong(itemId));

    request.setHeader(header);
    request.setBody(body);

    Response<GroupingResponse> response = itemService.grouping(request);

    return response;
  }

  /**
   * 商品下架：http://dev2.baidu.com/docs.do?product=6#page=%E5%95%86%E5%93%81%E4% B8%8B%E6%9E%B6
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param itemId 商品ID
   * @return 下架操作响应
   * @throws Exception
   */
  private Response<OffResponse> baiduMallItemOff(String userName, String password, String target,
      String token, String itemId) throws Exception {
    MyItemService itemService =
        MyServiceFactory.createItemService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<OffRequest> request = new Request<OffRequest>();

    OffRequest body = new OffRequest();
    body.setItemId(Long.parseLong(itemId));

    request.setHeader(header);
    request.setBody(body);

    Response<OffResponse> response = itemService.off(request);

    return response;
  }

  /**
   * 更新库存：http://dev2.baidu.com/docs.do?product=6#page=%E6%9B%B4%E6%96%B0%E5% BA%93%E5%AD%98
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param skuStock 商品库存信息
   * @return 更新库存操作响应
   * @throws Exception
   */
  private Response<UpdateStockResponse> baiduMallStockUpdate(String userName, String password,
      String target, String token, SkuStock skuStock) throws Exception {
    MyItemService itemService =
        MyServiceFactory.createItemService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<UpdateStockRequest> request = new Request<UpdateStockRequest>();
    UpdateStockRequest body = new UpdateStockRequest();
    body.setSkuStock(skuStock);

    request.setHeader(header);
    request.setBody(body);

    Response<UpdateStockResponse> response = itemService.updateStock(request);

    return response;
  }

  /**
   * 批量更新库存：http://dev2.baidu.com/docs.do?product=6#page=%E6%89%B9%E9%87%8F%E6
   * %9B%B4%E6%96%B0%E5%BA%93%E5%AD%98
   * 
   * @param userName 登录百度Mall的用户名
   * @param password 登录百度Mall的密码
   * @param target 与userName相同
   * @param token 商家授权
   * @param skuStocks 商品库存信息列表
   * @return 批量更新库存操作响应
   * @throws NumberFormatException
   * @throws ClientProtocolException
   * @throws IOException
   */
  private Response<BatchUpdateStockResponse> baiduMallStockUpdateList(String userName,
      String password, String target, String token, List<SkuStock> skuStocks) throws Exception {
    MyItemService itemService =
        MyServiceFactory.createItemService(paramSystemService.getEcApiUrl(EcApiUrlEnum.BAIDU_API));

    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername(userName);
    header.setPassword(password);
    header.setTarget(target);
    header.setToken(token);

    Request<BatchUpdateStockRequest> request = new Request<BatchUpdateStockRequest>();
    BatchUpdateStockRequest body = new BatchUpdateStockRequest();
    body.setSkuStocks(skuStocks);

    request.setHeader(header);
    request.setBody(body);

    Response<BatchUpdateStockResponse> response = itemService.batchUpdateStock(request);

    return response;
  }

  public static void main(String[] args) throws Exception {
    MyOrderService orderService =
        MyServiceFactory.createOrderService("https://api.baidu.com/json/mall/v1/");
  
    // 设置Request头部信息
    RequestHeader header = new RequestHeader();
    header.setUsername("mercury2015");
    header.setPassword("SDbaidu201401");
    header.setTarget("mercury2015");
    header.setToken("9a6960fd621155ec09b1b6e55df36b58");
  
    Request<FindOrderRequest> request = new Request<FindOrderRequest>();
    FindOrderRequest body = new FindOrderRequest();
  
    body.setPageNo(1);
    body.setPageSize(10);
  
    request.setHeader(header);
    request.setBody(body);
  
    // TODO 需要测试可行性
    Response<FindOrderResponse> response = orderService.findOrder(request);
  
  
    System.out.println(response.getBody().getData().get(0).getOrderList().get(0).getCreateTime());
  }
}
