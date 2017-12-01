package com.digiwin.ecims.platforms.dhgate.service.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhgate.open.client.BizStatusResponse;
import com.dhgate.open.client.ClientRequest;
import com.dhgate.open.client.CompositeResponse;
import com.dhgate.open.client.DhgateDefaultClient;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemSku;
import com.digiwin.ecims.platforms.dhgate.bean.domain.item.ItemSkuUpdate;
import com.digiwin.ecims.platforms.dhgate.bean.domain.order.OrderProduct;
import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemDownshelfListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemSkuListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemUpdateRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.item.ItemUpshelfListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDeliverySaveRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDisputeCloseListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderDisputeOpenListRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderListGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.request.order.OrderProductGetRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemDownshelfListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemSkuListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemUpdateResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.item.ItemUpshelfListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDeliverySaveResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeCloseListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeOpenListResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderListGetResponse;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderProductGetResponse;
import com.digiwin.ecims.platforms.dhgate.service.api.DhgateApiService;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool;
import com.digiwin.ecims.platforms.dhgate.util.DhgateCommonTool.DhgateApiEnum;
import com.digiwin.ecims.platforms.dhgate.util.DhgateRequestParamBuilder;
import com.digiwin.ecims.platforms.dhgate.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.dhgate.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.dhgate.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class DhgateApiServiceImpl implements DhgateApiService {

  private static final Logger logger = LoggerFactory.getLogger(DhgateApiServiceImpl.class);

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

    OrderGetRequest orderGetRequest = new OrderGetRequest();
    orderGetRequest.setOrderNo(orderId);
    CompositeResponse<OrderGetResponse> orderGetResponse =
        dhOrderGet(orderGetRequest, esVerification.getAccessToken());

    OrderProductGetRequest orderProductGetRequest = new OrderProductGetRequest();
    orderProductGetRequest.setOrderNo(orderId);
    CompositeResponse<OrderProductGetResponse> orderProductGetResponse =
        dhOrderProductGet(orderProductGetRequest, esVerification.getAccessToken());

    Map<String, Long> outerSkuToSkuMap = new HashMap<String, Long>();
    // TODO 商品SKUID获取
    // 根据订单中商品的itemId与outerId，通过dh.item.sku.list接口获取商品在平台的SKUID
    // 这么做的原因是因为dh.order.product.get无法获取商品在平台的SKUID，而OMS这边会拿这个值关联铺货资料表
    for (OrderProduct orderProduct : orderProductGetResponse.getSuccessResponse().getOrderProductList()) {
      // 平台商品编号
      String itemCode = orderProduct.getItemcode();
      // 平台商品SKU编号
      Long itemSkuId = 0l;
      // 商家外部SKU编号（料号）
      String outerSkuId = orderProduct.getSkuCode();
      
      ItemSkuListRequest itemSkuListRequest = new ItemSkuListRequest();
      itemSkuListRequest.setItemCode(itemCode);
      CompositeResponse<ItemSkuListResponse> itemSkuListResponse = 
          dhItemSkuList(itemSkuListRequest, esVerification.getAccessToken());
      if (itemSkuListResponse != null && itemSkuListResponse.getSuccessResponse().getStatus() != null) {
        for (ItemSku itemSku : itemSkuListResponse.getSuccessResponse().getItemSkuList()) {
          if (itemSku.getItemCode() == Long.parseLong(itemCode)) {
            itemSkuId = itemSku.getSkuId();
            break;
          }
        }
        outerSkuToSkuMap.put(outerSkuId, itemSkuId);
      }
    }
    
    boolean orderGetSuccess = orderGetResponse.getSuccessResponse().getStatus().getCode()
        .equals(DhgateCommonTool.BIZ_SUCCESS_CODE);
    boolean orderProductGetSuccess = orderProductGetResponse.getSuccessResponse().getStatus()
        .getCode().equals(DhgateCommonTool.BIZ_SUCCESS_CODE);
    boolean success = orderGetSuccess && orderProductGetSuccess;
    String errCode = "";
    String errMsg = "";

    String orderGetErrCode = orderGetResponse.getSuccessResponse().getStatus().getCode();
    String orderGetErrMsg = orderGetResponse.getSuccessResponse().getStatus().getMessage();
    String orderProductGetErrCode =
        orderProductGetResponse.getSuccessResponse().getStatus().getCode();
    String orderProductGetErrMsg =
        orderProductGetResponse.getSuccessResponse().getStatus().getMessage();

    if (!orderGetSuccess && orderProductGetSuccess) {
      errCode = orderGetErrCode;
      errMsg = orderGetErrMsg;
    } else if (orderGetSuccess && !orderProductGetSuccess) {
      errCode = orderProductGetErrCode;
      errMsg = orderProductGetErrMsg;
    } else if (!orderGetSuccess && !orderProductGetSuccess) {
      errCode = orderGetErrCode + "," + orderProductGetErrCode;
      errMsg = orderGetErrMsg + "," + orderProductGetErrMsg;
    } else {

    }

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(errCode, errMsg),
        success ? new AomsordTTranslator(orderGetResponse.getSuccessResponse(),
            orderProductGetResponse.getSuccessResponse().getOrderProductList(), outerSkuToSkuMap)
                .doTranslate(cmdReq.getStoreid())
            : null);
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    // 取參數
    String refundId = cmdReq.getParams().get("id");

    OrderDisputeOpenListRequest orderDisputeOpenListRequest = new OrderDisputeOpenListRequest();
    orderDisputeOpenListRequest.setOrderNo(refundId);
    orderDisputeOpenListRequest.setPageNo(1);
    orderDisputeOpenListRequest.setPageSize(10);

    CompositeResponse<OrderDisputeOpenListResponse> orderDisputeOpenListResponse =
        dhOrderDisputeOpenList(orderDisputeOpenListRequest, esVerification.getAccessToken());

    boolean success = orderDisputeOpenListResponse.getSuccessResponse().getStatus().getCode()
        .equals(DhgateCommonTool.BIZ_SUCCESS_CODE);

    String errCode = orderDisputeOpenListResponse.getSuccessResponse().getStatus().getCode();
    String errMsg = orderDisputeOpenListResponse.getSuccessResponse().getStatus().getMessage();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(errCode, errMsg),
        success ? new AomsrefundTTranslator(
            orderDisputeOpenListResponse.getSuccessResponse().getOrderDisputeOpenInfos().get(0))
                .doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    // 取參數
    String numIid = cmdReq.getParams().get("numid");

    ItemGetRequest itemGetRequest = new ItemGetRequest();
    itemGetRequest.setItemCode(numIid);

    CompositeResponse<ItemGetResponse> itemGetResponse =
        dhItemGet(itemGetRequest, esVerification.getAccessToken());

    boolean success = itemGetResponse.getSuccessResponse().getStatus().getCode()
        .equals(DhgateCommonTool.BIZ_SUCCESS_CODE);

    String errCode = itemGetResponse.getSuccessResponse().getStatus().getCode();
    String errMsg = itemGetResponse.getSuccessResponse().getStatus().getMessage();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(errCode, errMsg),
        success ? new AomsitemTTranslator(itemGetResponse.getSuccessResponse())
            .doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得API所需參數
    Long numIid = Long.parseLong(cmdReq.getParams().get("numid"));

    ItemUpshelfListRequest itemUpshelfListRequest = new ItemUpshelfListRequest();
    itemUpshelfListRequest.setItemCodes(numIid.toString());

    CompositeResponse<ItemUpshelfListResponse> itemUpshelfListResponse =
        dhItemUpshelfList(itemUpshelfListRequest, esVerification.getAccessToken());

    boolean success = itemUpshelfListResponse.getSuccessResponse().getStatus().getCode()
        .equals(DhgateCommonTool.BIZ_SUCCESS_CODE);

    String errCode = itemUpshelfListResponse.getSuccessResponse().getStatus().getCode();
    String errMsg = itemUpshelfListResponse.getSuccessResponse().getStatus().getMessage();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(errCode, errMsg), null);
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得API所需參數
    Long numIid = Long.parseLong(cmdReq.getParams().get("numid"));

    ItemDownshelfListRequest itemDownshelfListRequest = new ItemDownshelfListRequest();
    itemDownshelfListRequest.setItemCodes(numIid.toString());

    CompositeResponse<ItemDownshelfListResponse> itemDownshelfListResponse =
        dhItemDownshelfList(itemDownshelfListRequest, esVerification.getAccessToken());

    boolean success = itemDownshelfListResponse.getSuccessResponse().getStatus().getCode()
        .equals(DhgateCommonTool.BIZ_SUCCESS_CODE);

    String errCode = itemDownshelfListResponse.getSuccessResponse().getStatus().getCode();
    String errMsg = itemDownshelfListResponse.getSuccessResponse().getStatus().getMessage();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(errCode, errMsg), null);
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
    String expstate = params.get("expstate");

    OrderDeliverySaveRequest orderDeliverySaveRequest = new OrderDeliverySaveRequest();
    orderDeliverySaveRequest.setDeliveryNo(expressno);
    orderDeliverySaveRequest.setDeliveryState(expstate);
    orderDeliverySaveRequest.setOrderNo(oid);
    orderDeliverySaveRequest.setShippingType(expcompno);

    CompositeResponse<OrderDeliverySaveResponse> orderDeliverySaveResponse =
        dhOrderDeliverySave(orderDeliverySaveRequest, esVerification.getAccessToken());

    boolean success = orderDeliverySaveResponse.getSuccessResponse().getStatus().getCode()
        .equals(DhgateCommonTool.BIZ_SUCCESS_CODE);

    String errCode = orderDeliverySaveResponse.getSuccessResponse().getStatus().getCode();
    String errMsg = orderDeliverySaveResponse.getSuccessResponse().getStatus().getMessage();

    return new CmdRes(cmdReq, success, success ? null : new ResponseError(errCode, errMsg), null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();

    // 先获取商品详情，将商品详情的property赋值给ItemUpdateRequest的property，最后再修改库存量后提交请求
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = params.get("numid"); // 商品在電商平台的ID
    String skuId = params.get("skuid");
    String outerId = params.get("outerid"); // 產品在ERP的料號
    String num = params.get("num"); // 數量

    ItemGetRequest itemGetRequest = new ItemGetRequest();
    itemGetRequest.setItemCode(numId);
    CompositeResponse<ItemGetResponse> itemGetResponse =
        dhItemGet(itemGetRequest, esVerification.getAccessToken());

    if (itemGetResponse.getSuccessResponse().getStatus().getCode()
        .equals(DhgateCommonTool.BIZ_SUCCESS_CODE)) {
      ItemUpdateRequest itemUpdateRequest = new ItemUpdateRequest();
      // TODO 需要在测试后验证数据是否正确
      BeanUtils.copyProperties(itemGetResponse.getSuccessResponse(), itemUpdateRequest);

      List<ItemSkuUpdate> itemSkus = itemUpdateRequest.getItemSkuList();
      for (ItemSkuUpdate itemSkuUpdate : itemSkus) {
        if (itemSkuUpdate.getSkuId() == Long.parseLong(skuId)
            && itemSkuUpdate.getSkuCode().equals(outerId)) {
          itemSkuUpdate.setInventory(Integer.parseInt(num));
          break;
        }
      }

      CompositeResponse<ItemUpdateResponse> itemUpdateResponse =
          dhItemUpdate(itemUpdateRequest, esVerification.getAccessToken());

      boolean success = itemUpdateResponse.getSuccessResponse().getStatus().getCode()
          .equals(DhgateCommonTool.BIZ_SUCCESS_CODE);

      String errCode = itemUpdateResponse.getSuccessResponse().getStatus().getCode();
      String errMsg = itemUpdateResponse.getSuccessResponse().getStatus().getMessage();

      return new CmdRes(cmdReq, success, success ? null : new ResponseError(errCode, errMsg), null);
    }

    return new CmdRes(cmdReq, false, new ResponseError(itemGetResponse.getErrorResponse().getCode(),
        itemGetResponse.getErrorResponse().getMessage()), null);
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  /**
   * 获取seller订单列表接口:http://developer.dhgate.com/api/0498bacf77fa457087ea90593512c125.html
   * 
   * @param startDate
   * @param endDate
   * @param pageNo
   * @param pageSize
   * @param queryTimeType
   * @param accessToken
   * @return
   * @throws Exception
   */
  @Override
  public CompositeResponse<OrderListGetResponse> dhOrderListGet(
      OrderListGetRequest orderListGetRequest, String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ORDER_LIST_GET, accessToken, orderListGetRequest);
  }

  /**
   * 获取订单详情接口:http://developer.dhgate.com/api/80fdf081b6774105b542866035edddfa.html
   * 
   * @param orderNo 卖家后台登录能看到成交的订单号；示例值：1330312162
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<OrderGetResponse> dhOrderGet(OrderGetRequest orderGetRequest,
      String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ORDER_GET, accessToken, orderGetRequest);
  }

  /**
   * 获取订单产品信息接口:http://developer.dhgate.com/api/0a8c5876b5964fa2942646f658ae91bc.html
   * 
   * @param orderNo 卖家后台登录能看到成交的订单号；示例值：1330312162
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<OrderProductGetResponse> dhOrderProductGet(
      OrderProductGetRequest orderProductGetRequest, String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ORDER_PRODUCT_GET, accessToken, orderProductGetRequest);
  }

  /**
   * 获取seller纠纷关闭订单列表接口:http://developer.dhgate.com/api/5735298ea83749019c3d231113418f19.html
   * 
   * @param orderNo 卖家后台登录能看到成交的订单号；示例值：1330312162
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<OrderDisputeCloseListResponse> dhOrderDisputeCloseList(
      OrderDisputeCloseListRequest orderDisputeCloseListRequest, String accessToken)
          throws Exception {
    return doApiCall(DhgateApiEnum.DH_ORDER_DISPUTECLOSE_LIST, accessToken,
        orderDisputeCloseListRequest);
  }

  /**
   * 获取seller纠纷开启订单列表接口:http://developer.dhgate.com/api/213633247a704dcca4b6a1d4d859a5fb.html
   * 
   * @param orderNo 卖家后台登录能看到成交的订单号；示例值：1330312162
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<OrderDisputeOpenListResponse> dhOrderDisputeOpenList(
      OrderDisputeOpenListRequest orderDisputeOpenListRequest, String accessToken)
          throws Exception {
    return doApiCall(DhgateApiEnum.DH_ORDER_DISPUTEOPEN_LIST, accessToken,
        orderDisputeOpenListRequest);
  }

  /**
   * 上传运单号接口:http://developer.dhgate.com/api/19ea94eade564f07ba5fa708115cf186.html
   * 
   * @param deliveryNo
   * @param deliveryState
   * @param orderNo
   * @param shippingType
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<OrderDeliverySaveResponse> dhOrderDeliverySave(
      OrderDeliverySaveRequest orderDeliverySaveRequest, String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ORDER_DELIVERY_SAVE, accessToken, orderDeliverySaveRequest);
  }

  /**
   * 卖家获取产品列表接口:http://developer.dhgate.com/api/4b28263d643e4002a43ef669a61c6cf7.html
   * 
   * @param operateDateStart
   * @param pageNo
   * @param pageSize
   * @param state
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<ItemListResponse> dhItemList(ItemListRequest itemListRequest,
      String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ITEM_LIST, accessToken, itemListRequest);
  }


  /**
   * 卖家获取产品详情接口:http://developer.dhgate.com/api/d9e1898ab51f4cad9126df75730d108e.html
   * 
   * @param itemCode 产品编码字符串信息,示例值：211198221
   * @param accessToekn
   * @return
   */
  @Override
  public CompositeResponse<ItemGetResponse> dhItemGet(ItemGetRequest itemGetRequest,
      String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ITEM_GET, accessToken, itemGetRequest);
  }

  /**
   * 更新产品信息接口：http://developer.dhgate.com/api/fc470dcd97034c5bb4ed1428f253e28a.html
   * 
   * @param itemDetail 通过dh.item.get得到的参数，或者使用dh.item.add使用的产品参数
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<ItemUpdateResponse> dhItemUpdate(ItemUpdateRequest itemUpdateRequest,
      String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ITEM_UPDATE, accessToken, itemUpdateRequest);
  }

  /**
   * 批量下架产品：http://developer.dhgate.com/api/6bb4ae8dae2844d79b7c79da5170e1be.html
   * 
   * @param itemCodes 产品编码字符串信息，多个产品编码用英文半角逗号分隔;示例值：211198221,211198222
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<ItemUpshelfListResponse> dhItemUpshelfList(
      ItemUpshelfListRequest itemUpshelfListRequest, String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ITEM_UPSHELF_LIST, accessToken, itemUpshelfListRequest);
  }

  /**
   * 批量上架产品：http://developer.dhgate.com/api/00d882a48ee4428e8c7437dd1a39e258.html
   * 
   * @param itemCodes 产品编码字符串信息，多个产品编码用英文半角逗号分隔;示例值：211198221,211198222
   * @param accessToken
   * @return
   */
  @Override
  public CompositeResponse<ItemDownshelfListResponse> dhItemDownshelfList(
      ItemDownshelfListRequest itemDownshelfListRequest, String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ITEM_DOWNSHELF_LIST, accessToken, itemDownshelfListRequest);
  }

  @Override
  public CompositeResponse<ItemSkuListResponse> dhItemSkuList(ItemSkuListRequest itemSkuListRequest,
      String accessToken) throws Exception {
    return doApiCall(DhgateApiEnum.DH_ITEM_SKU_LIST, accessToken, itemSkuListRequest);
  }
  
  /**
   * 敦煌网调用API通用方法
   * 
   * @param apiEnum API枚举
   * @param accessToken API访问令牌
   * @param bizRequest API应用级参数实例
   * @return API响应信息
   * @author 维杰
   */
  private <T extends BizStatusResponse> CompositeResponse<T> doApiCall(DhgateApiEnum apiEnum,
      String accessToken, DhgateBaseRequest<T> bizRequest) throws Exception {
    // 获取API调用地址
    DhgateDefaultClient client =
        new DhgateDefaultClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DHGATE_API));
    // 声明响应实例
    CompositeResponse<T> response = null;
    // 为API添加上参数
    ClientRequest request = DhgateRequestParamBuilder.addParams(client, accessToken, bizRequest);
    // 调用API得到响应
    response =
        request.post(bizRequest.getResponseClass(), apiEnum.getApiName(), apiEnum.getVersion());

    return response;
  }

 
}
