package com.digiwin.ecims.platforms.ccb.service.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.ccb.bean.CcbClient;
import com.digiwin.ecims.platforms.ccb.bean.request.base.BaseRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.delivery.send.DeliverySendRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.delivery.send.DeliverySendRequestBody;
import com.digiwin.ecims.platforms.ccb.bean.request.delivery.send.DeliverySendRequestBodyDetail;
import com.digiwin.ecims.platforms.ccb.bean.request.item.detail.get.ItemDetailGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.item.detail.get.ItemDetailGetRequestBody;
import com.digiwin.ecims.platforms.ccb.bean.request.item.detail.get.ItemDetailGetRequestBodyDetail;
import com.digiwin.ecims.platforms.ccb.bean.request.item.inventory.update.ItemInventoryUpdateRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.item.inventory.update.ItemInventoryUpdateRequestBody;
import com.digiwin.ecims.platforms.ccb.bean.request.item.inventory.update.ItemInventoryUpdateRequestBodyDetail;
import com.digiwin.ecims.platforms.ccb.bean.request.item.list.get.ItemListGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get.OrderDetailGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get.OrderDetailGetRequestBody;
import com.digiwin.ecims.platforms.ccb.bean.request.order.detail.get.OrderDetailGetRequestBodyDetail;
import com.digiwin.ecims.platforms.ccb.bean.request.order.list.get.OrderListGetRequest;
import com.digiwin.ecims.platforms.ccb.bean.response.base.BaseResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.delivery.send.DeliverySendResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.item.detail.get.ItemDetailGetResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.item.inventory.update.ItemInventoryUpdateResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.item.list.get.ItemListGetResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.order.detail.get.OrderDetailGetResponse;
import com.digiwin.ecims.platforms.ccb.bean.response.order.list.get.OrderListGetResponse;
import com.digiwin.ecims.platforms.ccb.service.api.CcbApiService;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool;
import com.digiwin.ecims.platforms.ccb.util.CcbCommonTool.ItemInventoryUpdateTypeEnum;
import com.digiwin.ecims.platforms.ccb.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.ccb.util.translator.AomsordTTranslator;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.XmlUtils;
import com.digiwin.ecims.ontime.service.ParamSystemService;

@Service
public class CcbApiServiceImpl implements CcbApiService {

  private static final Logger logger = LoggerFactory.getLogger(CcbApiServiceImpl.class);

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private ParamSystemService paramSystemService;

  @Override
  public CmdRes execute(CmdReq cmdReq) throws Exception {
    String api = cmdReq.getApi();

    if (api.equals(EcImsApiEnum.DIGIWIN_ORDER_DETAIL_GET.toString())) { // 獲取單筆訂單詳情
      return digiwinOrderDetailGet(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_REFUND_GET.toString())) { // 退款單下載
      return digiwinRefundGet(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DETAIL_GET.toString())) { // 單筆鋪貨下載
      return digiwinItemDetailGet(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_LISTING.toString())) { // 商品上架
      return digiwinItemListing(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DELISTING.toString())) { // 商品下架
      return digiwinItemDelisting(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_UPDATE.toString())) { // 訂單發貨
      return digiwinItemUpdate(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_SHIPPING_SEND.toString())) { // 訂單發貨
      return digiwinShippingSend(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_INVENTORY_UPDATE.toString())) { // 店鋪庫存初始化
      return digiwinInventoryUpdate(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_INVENTORY_BATCH_UPDATE.toString())) { // 批量修改库存
      return digiwinInventoryBatchUpdate(cmdReq);
    } else if (api.equals(EcImsApiEnum.DIGIWIN_PICTURE_EXTERNAL_UPLOAD.toString())) {
      return digiwinPictureExternalUpload(cmdReq);
    } else {
      return new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
    }
  }

  @Override
  public CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    String orderId = cmdReq.getParams().get("id");

    OrderDetailGetRequest request = new OrderDetailGetRequest();
    OrderDetailGetRequestBody body = new OrderDetailGetRequestBody();
    OrderDetailGetRequestBodyDetail bodyDetail = new OrderDetailGetRequestBodyDetail();
    bodyDetail.setOrderId(orderId);

    body.setBodyDetail(bodyDetail);
    request.setBody(body);

    OrderDetailGetResponse response = CcbOrderDetailGet(request, esVerification.getAppKey());
    boolean success = response != null
        && response.getHead().getRetCode().equals(CcbCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getRetCode(), response.getHead().getRetMsg()),
        success ? new AomsordTTranslator(response.getBody().getOrderItems().getOrderInfos().get(0))
            .doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = params.get("numid");
    ItemDetailGetRequest request = new ItemDetailGetRequest();
    ItemDetailGetRequestBody body = new ItemDetailGetRequestBody();
    ItemDetailGetRequestBodyDetail detail = new ItemDetailGetRequestBodyDetail();
    detail.setSkuId(numId);
    body.setBodyDetail(detail);
    request.setBody(body);

    ItemDetailGetResponse response = CcbItemDetailGet(request, esVerification.getAppKey());

    boolean success = response != null
        && response.getHead().getRetCode().equals(CcbCommonTool.RESPONSE_SUCCESS_CODE);

    // TODO 需要测试后知道建行返回的商品详情数据如何，文档中不明确
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getRetCode(), response.getHead().getRetMsg()),
        success
            ? new AomsitemTTranslator(response.getBody().getItem()).doTranslate(cmdReq.getStoreid())
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
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String oid = params.get("oid");
    String expressno = params.get("expressno");
    String expcompno = params.get("expcompno");
    // String expcompname = params.get("expcompname");

    DeliverySendRequest request = new DeliverySendRequest();
    DeliverySendRequestBody body = new DeliverySendRequestBody();
    DeliverySendRequestBodyDetail detail = new DeliverySendRequestBodyDetail();
    detail.setOrderId(oid);
    detail.setCompanyCode(expcompno);
    detail.setOutSid(expressno);

    List<DeliverySendRequestBodyDetail> details = new ArrayList<>();
    details.add(detail);

    body.setDeliveries(details);
    request.setBody(body);

    DeliverySendResponse response = CcbDeliverySend(request, esVerification.getAppKey());

    boolean success = response != null
        && response.getHead().getRetCode().equals(CcbCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getRetCode(), response.getHead().getRetMsg()),
        null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String skuId = params.get("skuid");
    String num = params.get("num");
    String outerId = params.get("outerid");

    ItemInventoryUpdateRequest request = new ItemInventoryUpdateRequest();
    ItemInventoryUpdateRequestBody body = new ItemInventoryUpdateRequestBody();
    ItemInventoryUpdateRequestBodyDetail detail = new ItemInventoryUpdateRequestBodyDetail();
    detail.setSkuId(skuId);
    if (StringUtils.isNotEmpty(outerId)) {
      detail.setProId(outerId);
    }
    detail.setQuantity(Integer.parseInt(num));
    detail.setType(ItemInventoryUpdateTypeEnum.FULLY.getType());

    List<ItemInventoryUpdateRequestBodyDetail> details =
        new ArrayList<ItemInventoryUpdateRequestBodyDetail>();
    details.add(detail);

    body.setBodyDetails(details);
    request.setBody(body);

    ItemInventoryUpdateResponse response =
        CcbItemInventoryUpdate(request, esVerification.getAppKey());

    boolean success = response != null
        && response.getHead().getRetCode().equals(CcbCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getRetCode(), response.getHead().getRetMsg()),
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

    ItemInventoryUpdateRequest request = new ItemInventoryUpdateRequest();
    ItemInventoryUpdateRequestBody body = new ItemInventoryUpdateRequestBody();
    List<ItemInventoryUpdateRequestBodyDetail> details =
        new ArrayList<ItemInventoryUpdateRequestBodyDetail>();
    for (int i = 0; i < skuIdArray.length; i++) {
      ItemInventoryUpdateRequestBodyDetail detail = new ItemInventoryUpdateRequestBodyDetail();
      detail.setSkuId(skuIdArray[i]);
      if (StringUtils.isNotEmpty(outerIdArray[i])) {
        detail.setProId(outerIdArray[i]);
      }
      detail.setQuantity(Integer.parseInt(numArray[i]));
      detail.setType(ItemInventoryUpdateTypeEnum.FULLY.getType());

      details.add(detail);
    }

    body.setBodyDetails(details);
    request.setBody(body);

    ItemInventoryUpdateResponse response =
        CcbItemInventoryUpdate(request, esVerification.getAppKey());

    boolean success = response != null
        && response.getHead().getRetCode().equals(CcbCommonTool.RESPONSE_SUCCESS_CODE);

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getRetCode(), response.getHead().getRetMsg()),
        null);
  }

  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public OrderDetailGetResponse CcbOrderDetailGet(OrderDetailGetRequest request, String custId)
      throws Exception {
    return doApiCall(request, custId);
  }

  @Override
  public OrderListGetResponse CcbOrderListGet(OrderListGetRequest request, String custId)
      throws Exception {
    return doApiCall(request, custId);
  }

  @Override
  public ItemDetailGetResponse CcbItemDetailGet(ItemDetailGetRequest request, String custId)
      throws Exception {
    return doApiCall(request, custId);
  }

  @Override
  public ItemListGetResponse CcbItemListGet(ItemListGetRequest request, String custId)
      throws Exception {
    return doApiCall(request, custId);
  }

  @Override
  public DeliverySendResponse CcbDeliverySend(DeliverySendRequest request, String custId)
      throws Exception {
    return doApiCall(request, custId);
  }

  @Override
  public ItemInventoryUpdateResponse CcbItemInventoryUpdate(ItemInventoryUpdateRequest request,
      String custId) throws Exception {
    return doApiCall(request, custId);
  }

  @SuppressWarnings("unchecked")
  public <T extends BaseResponse> T doApiCall(BaseRequest<T> request, String custId)
      throws Exception {
    request.getHead().setTranCode(request.getTranCode());

    XmlUtils xu = XmlUtils.getInstance();
    CcbClient client = new CcbClient();
    String result = client.execute(request.getTranCode(), request.getHead().getCustId(),
        request.getHead().getTranSid(), xu.javaBean2Xml(request));

    // result =
    // "<response><head><tran_sid>1</tran_sid><cust_id>2</cust_id><ret_code>3</ret_code><ret_msg>4</ret_msg></head><body><order_items><order_info/><order_info/></order_items></body></response>";

    T response = null;
    if (result != null) {
      try {
        response = (T) xu.xml2JavaBean(result, request.getResponseClass());
      } catch (Exception e) {
        e.printStackTrace();
        throw e;
      }
    }
    return response;
  }

  public static void main(String[] args) throws Exception {
    OrderDetailGetRequest request = new OrderDetailGetRequest();
    OrderDetailGetRequestBody body = new OrderDetailGetRequestBody();
    OrderDetailGetRequestBodyDetail bodyDetail = new OrderDetailGetRequestBodyDetail();
    bodyDetail.setOrderId("123test");
    body.setBodyDetail(bodyDetail);
    request.setBody(body);

    // XmlUtils xu = XmlUtils.getInstance();
    // System.out.println(xu.javaBean2Xml(request));

    // OrderDetailGetResponse response = doApiCall(request, "123");
    // XmlUtils xu = XmlUtils.getInstance();
    // System.out.println(xu.javaBean2Xml(response));
    // System.out.println(response.getHead().getTranSid());

    // OrderDetailGetResponse response = new OrderDetailGetResponse();
    // ResponseHead responseHead = new ResponseHead("1","2","3","4");
    // OrderDetailGetResponseBody responseBody = new OrderDetailGetResponseBody();
    // OrderInfos orderItems = new OrderInfos();
    //
    // List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
    // orderInfos.add(new OrderInfo());
    // orderInfos.add(new OrderInfo());
    //
    // orderItems.setOrderInfos(orderInfos);
    // responseBody.setOrderItems(orderItems);
    // response.setHead(responseHead);
    // response.setBody(responseBody);
    //
    // System.out.println(xu.javaBean2Xml(response));
  }
}
