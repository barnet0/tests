package com.digiwin.ecims.platforms.dangdang.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.MD5;
import com.digiwin.ecims.core.util.XmlUtils;
import com.digiwin.ecims.platforms.dangdang.bean.AomsitemTTranslator;
import com.digiwin.ecims.platforms.dangdang.bean.AomsordTTranslator;
import com.digiwin.ecims.platforms.dangdang.bean.AomsrefundTTranslator;
import com.digiwin.ecims.platforms.dangdang.bean.request.items.status.update.ItemsStatusUpdateRequest;
import com.digiwin.ecims.platforms.dangdang.bean.request.items.status.update.ProductInfo;
import com.digiwin.ecims.platforms.dangdang.bean.request.items.status.update.ProductInfoList;
import com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send.ItemInfo;
import com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send.OrderGoodsSendRequest;
import com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send.OrderInfo;
import com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send.OrdersList;
import com.digiwin.ecims.platforms.dangdang.bean.request.order.goods.send.SendGoodsList;
import com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update.OrdersPickGoodsUpdateRequest;
import com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update.PickGoodsList;
import com.digiwin.ecims.platforms.dangdang.bean.response.DangdangErrorResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.ResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.item.get.ItemGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.item.stock.update.ItemStockUpdateResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.item.stock.update.ItemStockUpdateResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.items.list.get.ItemListGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.items.status.update.ItemsStatusUpdateResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.items.status.update.ItemsStatusUpdateResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.ToWmsOrderExpressBillGetData;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.ToWmsOrderExpressBillGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.ToWmsOrderExpressBillGetResponseMessage;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.details.get.OrderDetailsGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.expressBill.get.CourierReceiptDetail;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.expressBill.get.OrderExpressBillGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.expressBill.get.OrderExpressBillGetResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.ddsend.OrderGoodsDdSendResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.ddsend.OrderGoodsDdSendResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send.OrderGoodsSendResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send.OrderGoodsSendResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.receipt.details.list.ReceiptDetaisListResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.order.receipt.details.list.ReceiptDetaisListResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get.OrdersListGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.pickgoods.update.OrdersPickGoodsUpdateResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.pickgoods.update.OrdersPickGoodsUpdateResponseErr;
import com.digiwin.ecims.platforms.dangdang.bean.response.orders.refund.list.OrdersRefundListResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.ordersExchangeReturnListGet.OrdersExchangeReturnListGetResponse;
import com.digiwin.ecims.platforms.dangdang.bean.response.shop.carriagetype.get.ShopCarriageTypeGetResponse;
import com.digiwin.ecims.platforms.dangdang.service.DangdangApiService;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangClient;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool;
import com.digiwin.ecims.platforms.dangdang.utils.DangdangCommonTool.DangdangApiEnum;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class DangdangApiServiceImpl implements DangdangApiService {

  public static final String XML_TITLE = "<?xml version=\"1.0\" encoding=\"GBK\" ?>";

  private static final Logger logger = LoggerFactory.getLogger(DangdangApiServiceImpl.class);

  @Autowired
  private BaseDAO baseDAO;

  @Autowired
  private TaskService taskService;

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private ParamSystemService paramSystemService; // add by mowj 20150928

  private XmlUtils xu = XmlUtils.getInstance();

  @Override
  public CmdRes execute(CmdReq cmdReq) throws Exception {
    String api = cmdReq.getApi();

    if (api.equals(EcImsApiEnum.DIGIWIN_ORDER_DETAIL_GET.getApiName())) { // 获取单笔订单详情
      return (digiwinOrderDetailGet(cmdReq));
    } else if (api.equals(EcImsApiEnum.DIGIWIN_REFUND_GET.getApiName())) { // 退货、退款单下载
      return (digiwinRefundGet(cmdReq));
    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DETAIL_GET.getApiName())) {// 单笔铺货详情
      return (digiwinItemDetailGet(cmdReq));
    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_LISTING.getApiName())) { // 商品上架
      return (digiwinItemListing(cmdReq));
    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DELISTING.getApiName())) { // 商品下架
      return (digiwinItemDelisting(cmdReq));
    } else if (api.equals(EcImsApiEnum.DIGIWIN_SHIPPING_SEND.getApiName())) { // 发货
      return (digiwinShippingSend(cmdReq));
    } else if (api.equals(EcImsApiEnum.DIGIWIN_INVENTORY_UPDATE.getApiName())) { // 店铺库存更新
      return (digiwinInventoryUpdate(cmdReq));
    } else if (api.equals(DangdangApiEnum.DIGIWIN_DD_WAYBILL_GET.toString())) { // 打印快递面单 // add by
                                                                                // mowj 20150902
                                                                                // 不再使用
      return (digiwinDdWaybillGet(cmdReq));
    } else if (api.equals(DangdangApiEnum.DIGIWIN_DD_SHIPPING_DDSEND.toString())) { // 当当外单和代发订单的发货
                                                                                    // // add by
                                                                                    // mowj 20150903
      return (digiwinDdShippingDdsend(cmdReq));
    } else if (api.equals("dangdang.shop.carriagetype.get")) { // add by mowj 20150903 查询商家运输方式
      return (dangdangShopCarriagetypeGet(cmdReq));
    } else if (api.equals(DangdangApiEnum.DIGIWIN_DD_RECEIPT_DETAILS_LIST.toString())) {
      return (digiwinDdReceiptDetailsList(cmdReq));
    } else if (api.equals(DangdangApiEnum.DIGIWIN_DD_PICKGOODS_UPDATE.toString())) { // add by mowj
                                                                                     // 20150917
                                                                                     // 配货（目前只支持代发订单，包括推荐物流）
      return (digiwinDdPickGoodsUpdate(cmdReq));
    } else {
      return new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
    }
  }

  @Override
  public CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("o", cmdReq.getParams().get("id").trim());// 订单编号，一次只能查询一张订单详细信息
      String resultClient = client.execute("dangdang.order.details.get", formMap);
      OrderDetailsGetResponse response =
          (OrderDetailsGetResponse) xu.xml2JavaBean(resultClient, OrderDetailsGetResponse.class);

      boolean isSuccess = (null == response.getError());
      cmdRes = new CmdRes(cmdReq, isSuccess,
          isSuccess ? null
              : new ResponseError(response.getError().getOperCode(),
                  response.getError().getOperation()),
          isSuccess ? new AomsordTTranslator().doTrans(response, cmdReq.getStoreid()) : null);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    List<AomsrefundT> resultList = null;// 纪录所有退货、退款数据
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API 退换货
      DangdangClient clientOERLG =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMapOERLG = new HashMap<String, String>();
      formMapOERLG.put("o", cmdReq.getParams().get("id").trim());// 订单编号，一次一个订单信息
      formMapOERLG.put("isNeedReverseReason", "1");// 如果为“1”则表示会返回商品的退换货原因，如果为0或者其他值则表示不返回
      String resultClient =
          clientOERLG.execute("dangdang.orders.exchange.return.list.get", formMapOERLG);
      if (resultClient.contains("errorResponse")) {
        // response返回错误信息
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(resultClient, DangdangErrorResponse.class);
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()), null);
      } else {
        OrdersExchangeReturnListGetResponse oerlgResponse = (OrdersExchangeReturnListGetResponse) xu
            .xml2JavaBean(resultClient, OrdersExchangeReturnListGetResponse.class);
        if (null == resultList) {
          resultList = new ArrayList<AomsrefundT>();
        }
        if (!"0".equals(oerlgResponse.getTotalInfo().getTotalOrderCount())) { // add by mowj
                                                                              // 20150812
          resultList
              .addAll(new AomsrefundTTranslator().doTrans(oerlgResponse, cmdReq.getStoreid()));
        }
      }

      // 调用API 退款
      DangdangClient clientORL =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMapORL = new HashMap<String, String>();
      formMapORL.put("order_id", cmdReq.getParams().get("id").trim());// 订单编号，一次一个订单信息
      String paymentDate = getPaymentDate(cmdReq.getParams().get("id").trim());// 取付款日期。註：有退款，必有付款日期
      if ("".equals(paymentDate)) {
        // 有退款单，但是查无订单付款日期
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError("999", "找不到订单号码"), null);
      } else {
        Calendar calendar = Calendar.getInstance();
        Date endDate = DateTimeTool.parse(paymentDate);
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, 3);// 增加3个月。註：开始时间和结束时间间隔不能超过3个月
        formMapORL.put("osd", paymentDate);// 退款单开始日期。註：因API限制，退款日期為必填参数
        formMapORL.put("oed", DateTimeTool.format(calendar.getTime()));// 退款单结束日期
        String refundListResponse = clientORL.execute("dangdang.orders.refund.list", formMapORL);
        OrdersRefundListResponse orlResponse = (OrdersRefundListResponse) xu
            .xml2JavaBean(refundListResponse, OrdersRefundListResponse.class);
        if ("0".equals(orlResponse.getResultCode())
            && !"0".equals(orlResponse.getRefundInfos().getTotalSize())) {
          // 有资料
          if (null == resultList) {
            resultList = new ArrayList<AomsrefundT>();
          }
          resultList.addAll(new AomsrefundTTranslator().doTransToRefundListBean(orlResponse,
              cmdReq.getStoreid()));
        } else {
          // response返回错误信息
          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError(orlResponse.getResultCode(), orlResponse.getResultMessage()), null);
        }
      }

      if (null != resultList && resultList.size() > 0) {
        // 有资料
        cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, resultList);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("it", cmdReq.getParams().get("numid").trim());// 商品标识符，普通商品or分色分码产品or分色分码产品下的商品信息
      String response = client.execute("dangdang.item.get", formMap);

      if (!response.contains("ItemDetail")) {
        // response返回错误信息
        ResponseErr responseError = (ResponseErr) xu.xml2JavaBean(response, ResponseErr.class);
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
            responseError.getError().getOperCode(), responseError.getError().getOperation()), null);
      } else {
        ItemGetResponse itemGetResponse =
            (ItemGetResponse) xu.xml2JavaBean(response, ItemGetResponse.class);
        cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null,
            new AomsitemTTranslator().doTrans(itemGetResponse, cmdReq.getStoreid()));
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      String result =
          client.execute("dangdang.order.goods.send", "sendGoods", getShippingSendRequest(cmdReq));

      // mark by mowj 20150907
      // if (!response.contains("OrdersList")) {
      // // Response返回錯誤信息
      // ResponseErr responseErr = (ResponseErr) xu.xml2JavaBean(response, ResponseErr.class);
      // result = new CmdRes(cmdReq, Boolean.FALSE, new
      // ResponseError(responseErr.getError().getOperCode(), responseErr.getError().getOperation()),
      // null);
      // } else {
      // OrderGoodsSendResponse orderGoodsSendResponse = (OrderGoodsSendResponse)
      // xu.xml2JavaBean(response, OrderGoodsSendResponse.class);
      // com.mercuryecinf.dangdang.bean.response.order.goods.send.OrderInfo orderInfo =
      // orderGoodsSendResponse.getResult().getOrdersList().getOrderInfos().get(0);
      // if (!"0".equals(orderInfo.getOrderOperCode())) {
      // // 操作成功，但发货失败
      // result = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(orderInfo.getOrderOperCode(),
      // orderInfo.getOrderOperation()), null);
      // } else {
      // // 操作成功，发货成功
      // result = new CmdRes(cmdReq, Boolean.TRUE, null, null);
      // }
      // }
      // add by mowj 20150907
      if (result.contains("errorResponse")) {
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        // API调用失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()), null);

      } else if (result.contains("Error")) {
        OrderGoodsSendResponseErr responseErr =
            (OrderGoodsSendResponseErr) xu.xml2JavaBean(result, OrderGoodsSendResponseErr.class);
        // 操作成功，但发货失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
            responseErr.getResult().getOperCode(), responseErr.getResult().getOperation()), null);
      } else {
        OrderGoodsSendResponse response =
            (OrderGoodsSendResponse) xu.xml2JavaBean(result, OrderGoodsSendResponse.class);
        // 操作成功
        if (response.getResult() != null && response.getResult().getOrdersList() != null
            && response.getResult().getOrdersList().getOrderInfos() != null
            && response.getResult().getOrdersList().getOrderInfos().size() > 0) {
          List<com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send.OrderInfo> orderInfos =
              response.getResult().getOrdersList().getOrderInfos();
          for (com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.send.OrderInfo orderInfo : orderInfos) {
            if (orderInfo.getOrderOperCode() != null) {
              if (orderInfo.getOrderOperCode().equals("0")) {
                // 业务成功
                cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, null);
              } else {
                String orderOperation = orderInfo.getOrderOperation() == null
                    ? DangdangCommonTool.getInstance().getErrorDescMap()
                        .get(DangdangCommonTool.DangdangApiEnum.DIGIWIN_SHIPPING_SEND)
                        .get(orderInfo.getOrderOperCode())
                    : orderInfo.getOrderOperation();
                // 业务失败
                cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                    new ResponseError(orderInfo.getOrderOperCode(), orderOperation), null);
                // 业务失败
              }
            } else {
              // 调用成功，但是返回的XML格式不正确。是特殊情况
              cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                  new ResponseError("_070", ErrorMessageBox._070), null);
            }
          }
        } else {
          // 调用成功，但是返回的XML格式不正确。是特殊情况
          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError("_070", ErrorMessageBox._070), null);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMap = new HashMap<String, String>();
      // formMap.put("oit", cmdReq.getParams().get("numid").trim());// 企业商品标识符 // mark by mowj
      // 20150916
      formMap.put("oit", cmdReq.getParams().get("outerid").trim());// 企业商品标识符 // add by mowj
                                                                   // 20150916
      formMap.put("stk", cmdReq.getParams().get("num").trim());// 商品库存数量
      String result = client.execute("dangdang.item.stock.update", formMap);

      // mark by mowj 20150907
      // ResponseErr responseErr = (ResponseErr) xu.xml2JavaBean(response, ResponseErr.class);
      // if (!"0".equals(responseErr.getResult().getOperCode())) {
      // // Response返回錯誤信息
      // result = new CmdRes(cmdReq, Boolean.FALSE, new
      // ResponseError(responseErr.getResult().getOperCode(),
      // responseErr.getResult().getOperation()), null);
      // } else {
      // result = new CmdRes(cmdReq, Boolean.TRUE, null, null);
      // }
      // add by mowj 20150907
      if (result.contains("errorResponse")) {
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        // API调用失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()), null);

      } else if (result.contains("Error")) {
        ItemStockUpdateResponseErr responseErr =
            (ItemStockUpdateResponseErr) xu.xml2JavaBean(result, ItemStockUpdateResponseErr.class);
        // 操作成功，但发货失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
            responseErr.getError().getOperCode(), responseErr.getError().getOperation()), null);
      } else {
        ItemStockUpdateResponse response =
            (ItemStockUpdateResponse) xu.xml2JavaBean(result, ItemStockUpdateResponse.class);
        // 操作成功
        if (response.getResult() != null) {
          if (response.getResult().getOperCode().equals("0")) {
            // 业务成功
            cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, null);
          } else {
            // 业务失败
            cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
                response.getResult().getOperCode(), response.getResult().getOperation()), null);
          }
        } else {
          // 调用成功，但是返回的XML格式不正确。是特殊情况
          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError("_070", ErrorMessageBox._070), null);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      String result = client.execute("dangdang.items.status.update", "updateMultiItemsStatus",
          getItemsStatusUpdateRequest(cmdReq, DangdangCommonTool.ITEM_LISTING));
      if (result.contains("errorResponse")) {
        // Response返回錯誤信息
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()), null);
      } else if (result.contains("Error")) { // add by mowj 20150907
        ItemsStatusUpdateResponseErr responseErr = (ItemsStatusUpdateResponseErr) xu
            .xml2JavaBean(result, ItemsStatusUpdateResponseErr.class);
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
            responseErr.getError().getOperCode(), responseErr.getError().getOperation()), null);
      } else {
        ItemsStatusUpdateResponse response =
            (ItemsStatusUpdateResponse) xu.xml2JavaBean(result, ItemsStatusUpdateResponse.class);
        if ("0".equals(response.getItemsIDList().getItemIDInfos().get(0).getOperCode())) {
          cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, null);
        } else {
          // 操作成功，但是商品上架失败
          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError(response.getItemsIDList().getItemIDInfos().get(0).getOperCode(),
                  response.getItemsIDList().getItemIDInfos().get(0).getOperation()),
              null);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      String result = client.execute("dangdang.items.status.update", "updateMultiItemsStatus",
          getItemsStatusUpdateRequest(cmdReq, DangdangCommonTool.ITEM_DELISTING));
      if (result.contains("errorResponse")) {
        // Response返回錯誤信息
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()), null);
      } else if (result.contains("Error")) { // add by mowj 20150907
        ItemsStatusUpdateResponseErr responseErr = (ItemsStatusUpdateResponseErr) xu
            .xml2JavaBean(result, ItemsStatusUpdateResponseErr.class);
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
            responseErr.getError().getOperCode(), responseErr.getError().getOperation()), null);
      } else {
        ItemsStatusUpdateResponse response =
            (ItemsStatusUpdateResponse) xu.xml2JavaBean(result, ItemsStatusUpdateResponse.class);
        if ("0".equals(response.getItemsIDList().getItemIDInfos().get(0).getOperCode())) {
          cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, null);
        } else {
          // 操作成功，但是商品下架失败
          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError(response.getItemsIDList().getItemIDInfos().get(0).getOperCode(),
                  response.getItemsIDList().getItemIDInfos().get(0).getOperation()),
              null);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  @Override
  public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

  private CmdRes dangdangShopCarriagetypeGet(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      String result = "";
      result = client.execute("dangdang.shop.carriagetype.get");

      ShopCarriageTypeGetResponse response =
          (ShopCarriageTypeGetResponse) xu.xml2JavaBean(result, ShopCarriageTypeGetResponse.class);
      // System.out.println(result);
      List<ShopCarriageTypeGetResponse> returnValue = new ArrayList<ShopCarriageTypeGetResponse>();
      returnValue.add(response);

      cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, returnValue);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  /**
   * 当当外单和代发订单的发货（水星货到付款用）
   * 
   * @param cmdReq
   * @return
   */
  private CmdRes digiwinDdShippingDdsend(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("o", cmdReq.getParams().get("oid").trim());
      if (cmdReq.getParams().get("type") != null) {
        formMap.put("type", cmdReq.getParams().get("type").trim());
      }
      String result = "";
      result = client.execute("dangdang.order.goods.ddsend", formMap);
      // System.out.println(result);

      if (result.contains("errorResponse")) {
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        // API调用失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()), null);

      } else if (result.contains("Error")) {
        OrderGoodsDdSendResponseErr responseErr = (OrderGoodsDdSendResponseErr) xu
            .xml2JavaBean(result, OrderGoodsDdSendResponseErr.class);
        // 操作成功，但发货失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
            responseErr.getError().getOperCode(), responseErr.getError().getOperation()), null);
      } else {
        OrderGoodsDdSendResponse response =
            (OrderGoodsDdSendResponse) xu.xml2JavaBean(result, OrderGoodsDdSendResponse.class);
        // 操作成功
        if (response.getResult() != null && response.getResult().getOrdersList() != null
            && response.getResult().getOrdersList().getOrderInfo() != null
            && response.getResult().getOrdersList().getOrderInfo().size() > 0) {
          List<com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.ddsend.OrderInfo> orderInfos =
              response.getResult().getOrdersList().getOrderInfo();
          for (com.digiwin.ecims.platforms.dangdang.bean.response.order.goods.ddsend.OrderInfo orderInfo : orderInfos) {
            if (orderInfo.getOrderOperCode() != null) {
              if (orderInfo.getOrderOperCode().equals("0")) {
                // 业务成功
                cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, null);
              } else {
                // 业务失败
                cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                    new ResponseError(orderInfo.getOrderOperCode(), orderInfo.getOrderOperation()),
                    null);
              }

            } else {
              // 调用成功，但是返回的XML格式不正确。是特殊情况
              cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                  new ResponseError("_070", ErrorMessageBox._070), null);
            }
          }
        } else {
          // 调用成功，但是返回的XML格式不正确。是特殊情况
          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError("_070", ErrorMessageBox._070), null);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  /**
   * 打印快递面单（只打印快递面单，包括自发，代发和推荐物流）。
   * 
   * @param cmdReq
   * @return
   * @throws Exception
   * @author 维杰
   * @since 2015.09.03
   *
   */
  @Deprecated
  private CmdRes digiwinDdWaybillGet(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMap = new HashMap<String, String>();
      String oid = cmdReq.getParams().get("oid").trim();
      formMap.put("o", oid);
      String result = "";
      result = client.execute("dangdang.order.expressbill.get", formMap);
      // System.out.println(result);

      List<ToWmsOrderExpressBillGetResponseMessage> toWmsMessageList =
          new ArrayList<ToWmsOrderExpressBillGetResponseMessage>();
      List<ToWmsOrderExpressBillGetData> responseResultBase64edList =
          new ArrayList<ToWmsOrderExpressBillGetData>();

      if (result.contains("errorResponse")) {
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        // // API调用失败
        // cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
        // new ResponseError(errorResponse.getErrorCode(),
        // errorResponse.getErrorMessage()), null);

        toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(oid, result));
        // 先将由ERP转发给WMS的respose转换为json字符串
        String tempToWmsResponseJson =
            JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                false));
        // 再将转换的json字符串使用BASE64编码，得到编码后的字符串
        String base64edTempToWmsResponseJson =
            new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
        // 最后将编码后的字符串添加进cmdRes的returnValue内
        responseResultBase64edList
            .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()),
            responseResultBase64edList);

      } else if (result.contains("Error")) {
        OrderExpressBillGetResponseErr responseErr = (OrderExpressBillGetResponseErr) xu
            .xml2JavaBean(result, OrderExpressBillGetResponseErr.class);
        // 操作成功，但获取面单失败
        // cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new
        // ResponseError(responseErr.getError().getOperCode(),
        // responseErr.getError().getOperation()), null);

        toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(oid, result));
        String tempToWmsResponseJson =
            JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                false));
        String base64edTempToWmsResponseJson =
            new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
        responseResultBase64edList
            .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(responseErr.getError().getOperCode(),
                responseErr.getError().getOperation()),
            responseResultBase64edList);

      } else {
        OrderExpressBillGetResponse response = (OrderExpressBillGetResponse) xu.xml2JavaBean(result,
            OrderExpressBillGetResponse.class);
        // 操作成功
        if (response.getOrderCourierReceiptDetails() != null
            && response.getOrderCourierReceiptDetails().getCourierReceiptDetail() != null
            && response.getOrderCourierReceiptDetails().getCourierReceiptDetail().size() > 0) {
          List<CourierReceiptDetail> crDetailList =
              response.getOrderCourierReceiptDetails().getCourierReceiptDetail();
          for (CourierReceiptDetail courierReceiptDetail : crDetailList) {
            if (courierReceiptDetail.getOperCode() != null
                || courierReceiptDetail.getOrderOperCode() != null) {
              // // 业务失败
              // cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new
              // ResponseError(courierReceiptDetail.getOperCode(),
              // courierReceiptDetail.getOperation()),
              // resultList);

              toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(oid, result));
              String tempToWmsResponseJson =
                  JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                      ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                      false));
              String base64edTempToWmsResponseJson =
                  new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
              responseResultBase64edList
                  .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

              cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                  new ResponseError(courierReceiptDetail.getOperCode(),
                      courierReceiptDetail.getOperation()),
                  responseResultBase64edList);

            } else {
              // 业务成功
              // cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null,
              // response.getOrderCourierReceiptDetails().getCourierReceiptDetail());

              toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(oid, result));
              String tempToWmsResponseJson = JsonUtil.format(
                  new ToWmsOrderExpressBillGetResponse("", toWmsMessageList, false));
              String base64edTempToWmsResponseJson =
                  new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
              responseResultBase64edList
                  .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

              cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, responseResultBase64edList);
            }
          }
        } else {
          // 调用成功，但是返回的XML格式不正确。是特殊情况
          // cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new
          // ResponseError("_070", ErrorMessageBox._070), null);

          toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(oid, result));
          String tempToWmsResponseJson =
              JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                  ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                  false));
          String base64edTempToWmsResponseJson =
              new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
          responseResultBase64edList
              .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError("_070", ErrorMessageBox._070), responseResultBase64edList);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

    return cmdRes;
  }

  /**
   * 取发货Request
   * 
   * @param
   * @return
   */
  private OrderGoodsSendRequest getShippingSendRequest(CmdReq cmdReq) throws Exception {
    OrderGoodsSendRequest orderGoodsSendRequest = null;
    try {
      orderGoodsSendRequest = new OrderGoodsSendRequest();
      OrdersList ordersList = new OrdersList();
      List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
      ordersList.setOrderInfos(orderInfos);
      orderGoodsSendRequest.setOrdersList(ordersList);

      orderGoodsSendRequest.setFunctionID("dangdang.order.goods.send");
      orderGoodsSendRequest.setTime(DateTimeTool.format(new Date()));
      List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();
      List<? extends LinkedHashMap<String, String>> itemlist = cmdReq.getItemlist();
      for (int i = 0; i < itemlist.size(); i++) {
        LinkedHashMap<String, String> itemMap = itemlist.get(i);
        itemInfos.add(new ItemInfo(itemMap.get("itemid").trim(), itemMap.get("count").trim(),
            itemMap.get("proditemid").trim())); // modi by mowj 20151020 添加proditemid参数
      }
      SendGoodsList sendGoodsList = new SendGoodsList();
      sendGoodsList.setItemInfos(itemInfos);
      orderInfos.add(new OrderInfo(cmdReq.getParams().get("oid").trim(),
          cmdReq.getParams().get("expcompno").trim(), cmdReq.getParams().get("expcompphone").trim(),
          cmdReq.getParams().get("expressno").trim(), sendGoodsList));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return orderGoodsSendRequest;
  }

  /**
   * 当当打印面单，给水星WMS用。链路如下：WMS->ERP->中台->ERP->WMS，ERP只做转发
   * 
   * @param cmdReq
   * @return
   * 
   */
  private CmdRes digiwinDdReceiptDetailsList(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMap = new HashMap<String, String>();
      String oid = cmdReq.getParams().get("oid").trim();
      String distributeId = cmdReq.getParams().get("distid").trim();
      formMap.put("o", oid);
      String result = "";
      result = client.execute("dangdang.order.receipt.details.list", formMap);
      // System.out.println(result);

      List<ToWmsOrderExpressBillGetResponseMessage> toWmsMessageList =
          new ArrayList<ToWmsOrderExpressBillGetResponseMessage>();
      List<ToWmsOrderExpressBillGetData> responseResultBase64edList =
          new ArrayList<ToWmsOrderExpressBillGetData>();

      if (result.contains("errorResponse")) {
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        // // API调用失败
        // cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
        // new ResponseError(errorResponse.getErrorCode(),
        // errorResponse.getErrorMessage()), null);

        toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(distributeId, result));
        // 先将由ERP转发给WMS的respose转换为json字符串
        String tempToWmsResponseJson =
            JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                false));
        // 再将转换的json字符串使用BASE64编码，得到编码后的字符串
        String base64edTempToWmsResponseJson =
            new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
        // 最后将编码后的字符串添加进cmdRes的returnValue内
        responseResultBase64edList
            .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()),
            responseResultBase64edList);

      } else if (result.contains("Error")) {
        ReceiptDetaisListResponseErr responseErr = (ReceiptDetaisListResponseErr) xu
            .xml2JavaBean(result, ReceiptDetaisListResponseErr.class);
        // 操作成功，但获取面单失败
        // cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new
        // ResponseError(responseErr.getError().getOperCode(),
        // responseErr.getError().getOperation()), null);

        toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(distributeId, result));
        String tempToWmsResponseJson =
            JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                false));
        String base64edTempToWmsResponseJson =
            new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
        responseResultBase64edList
            .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(responseErr.getError().getOperCode(),
                responseErr.getError().getOperation()),
            responseResultBase64edList);

      } else {
        ReceiptDetaisListResponse response =
            (ReceiptDetaisListResponse) xu.xml2JavaBean(result, ReceiptDetaisListResponse.class);
        // 操作成功
        if (response.getOrderCourierReceiptDetails() != null
            && response.getOrderCourierReceiptDetails().getCourierReceiptDetail() != null
            && response.getOrderCourierReceiptDetails().getCourierReceiptDetail().size() > 0) {
          List<com.digiwin.ecims.platforms.dangdang.bean.response.order.receipt.details.list.CourierReceiptDetail> crDetailList =
              response.getOrderCourierReceiptDetails().getCourierReceiptDetail();
          for (com.digiwin.ecims.platforms.dangdang.bean.response.order.receipt.details.list.CourierReceiptDetail courierReceiptDetail : crDetailList) {
            if (courierReceiptDetail.getOperCode() != null
                || courierReceiptDetail.getOrderOperCode() != null) {
              // // 业务失败
              // cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new
              // ResponseError(courierReceiptDetail.getOperCode(),
              // courierReceiptDetail.getOperation()),
              // resultList);

              toWmsMessageList
                  .add(new ToWmsOrderExpressBillGetResponseMessage(distributeId, result));
              String tempToWmsResponseJson =
                  JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                      ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                      false));
              String base64edTempToWmsResponseJson =
                  new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
              responseResultBase64edList
                  .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

              cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                  new ResponseError(courierReceiptDetail.getOperCode(),
                      courierReceiptDetail.getOperation()),
                  responseResultBase64edList);

            } else {
              // 业务成功
              // cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null,
              // response.getOrderCourierReceiptDetails().getCourierReceiptDetail());

              toWmsMessageList
                  .add(new ToWmsOrderExpressBillGetResponseMessage(distributeId, result));
              String tempToWmsResponseJson = JsonUtil.format(
                  new ToWmsOrderExpressBillGetResponse("", toWmsMessageList, false));
              String base64edTempToWmsResponseJson =
                  new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
              responseResultBase64edList
                  .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

              cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, responseResultBase64edList);
            }
          }
        } else {
          // 调用成功，但是返回的XML格式不正确。是特殊情况
          // cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new
          // ResponseError("_070", ErrorMessageBox._070), null);

          toWmsMessageList.add(new ToWmsOrderExpressBillGetResponseMessage(distributeId, result));
          String tempToWmsResponseJson =
              JsonUtil.format(new ToWmsOrderExpressBillGetResponse(
                  ToWmsOrderExpressBillGetResponse.EXCEPTION_MSG_WHEN_ERROR, toWmsMessageList,
                  false));
          String base64edTempToWmsResponseJson =
              new MD5().complieToBase64WithUrlEncode(tempToWmsResponseJson);
          responseResultBase64edList
              .add(new ToWmsOrderExpressBillGetData(base64edTempToWmsResponseJson));

          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError("_070", ErrorMessageBox._070), responseResultBase64edList);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }

    return cmdRes;
  }

  /**
   * 当当订单配货操作
   * 
   * @param cmdReq
   * @return
   * @author 维杰
   * @throws Exception
   * @since 2015.09.17
   */
  private CmdRes digiwinDdPickGoodsUpdate(CmdReq cmdReq) throws Exception {
    CmdRes cmdRes = null;
    try {
      // 取得授权
      ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              veriInfo.getAppKey(), veriInfo.getAppSecret(), veriInfo.getAccessToken());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("dataXML", getPickGoodsUpdateRequestXmlString(cmdReq));
      String result = "";
      result = client.execute("dangdang.orders.pickgoods.update", formMap);
      // System.out.println(result);

      if (result.contains("errorResponse")) {
        DangdangErrorResponse errorResponse =
            (DangdangErrorResponse) xu.xml2JavaBean(result, DangdangErrorResponse.class);
        // API调用失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
            new ResponseError(errorResponse.getErrorCode(), errorResponse.getErrorMessage()), null);

      } else if (result.contains("Error")) {
        OrdersPickGoodsUpdateResponseErr responseErr = (OrdersPickGoodsUpdateResponseErr) xu
            .xml2JavaBean(result, OrdersPickGoodsUpdateResponseErr.class);
        // 操作成功，但发货失败
        cmdRes = new CmdRes(cmdReq, Boolean.FALSE, new ResponseError(
            responseErr.getError().getOperCode(), responseErr.getError().getOperation()), null);
      } else {
        OrdersPickGoodsUpdateResponse response = (OrdersPickGoodsUpdateResponse) xu
            .xml2JavaBean(result, OrdersPickGoodsUpdateResponse.class);
        // 操作成功
        if (response.getResult() != null && response.getResult().getOrdersList() != null
            && response.getResult().getOrdersList().getOrderInfos() != null
            && response.getResult().getOrdersList().getOrderInfos().size() > 0) {
          List<com.digiwin.ecims.platforms.dangdang.bean.response.orders.pickgoods.update.OrderInfo> orderInfos =
              response.getResult().getOrdersList().getOrderInfos();
          for (com.digiwin.ecims.platforms.dangdang.bean.response.orders.pickgoods.update.OrderInfo orderInfo : orderInfos) {
            if (orderInfo.getOrderOperCode() != null) {
              if (orderInfo.getOrderOperCode().equals("0")) {
                // 业务成功
                cmdRes = new CmdRes(cmdReq, Boolean.TRUE, null, null);
              } else {
                String orderOperation = orderInfo.getOrderOperation() == null
                    ? DangdangCommonTool.getInstance().getErrorDescMap()
                        .get(DangdangCommonTool.DangdangApiEnum.DIGIWIN_DD_PICKGOODS_UPDATE)
                        .get(orderInfo.getOrderOperCode())
                    : orderInfo.getOrderOperation();
                // 业务失败
                cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                    new ResponseError(orderInfo.getOrderOperCode(), orderOperation), null);
              }

            } else {
              // 调用成功，但是返回的XML格式不正确。是特殊情况
              cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
                  new ResponseError("_070", ErrorMessageBox._070), null);
            }
          }
        } else {
          // 调用成功，但是返回的XML格式不正确。是特殊情况
          cmdRes = new CmdRes(cmdReq, Boolean.FALSE,
              new ResponseError("_070", ErrorMessageBox._070), null);
        }
      }

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      throw e;
    }
    return cmdRes;
  }

  /**
   * 用于将当当配货的对象转换成字符串，发送给当当
   * 
   * @param cmdReq
   * @return
   * @throws JAXBException
   * @author 维杰
   * @since 2015.09.17
   */
  private String getPickGoodsUpdateRequestXmlString(CmdReq cmdReq) throws JAXBException {
    OrdersPickGoodsUpdateRequest request = new OrdersPickGoodsUpdateRequest();
    // 设定request内容
    request.setFunctionID(OrdersPickGoodsUpdateRequest.FUNCTION_ID);
    request.setTime(DateTimeTool.format(new Date()));
    com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update.OrdersList ordersList =
        new com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update.OrdersList();
    request.setOrdersList(ordersList);

    // 设定request.OrdersList内容
    com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update.OrderInfo orderInfo =
        new com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update.OrderInfo();
    ordersList.getOrderInfos().add(orderInfo);

    // 设定request.OrdersList.OrderInfo内容
    orderInfo.setOrderID(cmdReq.getParams().get("oid").trim());
    PickGoodsList pickGoodsList = new PickGoodsList();
    orderInfo.setPickGoodsList(pickGoodsList);

    // 设定request.OrdersList.OrderInfo.PickGoodsList内容
    List<? extends LinkedHashMap<String, String>> itemlist = cmdReq.getItemlist();
    for (int i = 0; i < itemlist.size(); i++) {
      LinkedHashMap<String, String> itemMap = itemlist.get(i);
      pickGoodsList.getItemInfos()
          .add(new com.digiwin.ecims.platforms.dangdang.bean.request.orders.pickgoods.update.ItemInfo(
              itemMap.get("count").trim(), itemMap.get("proditemid").trim())); // modi by mowj
                                                                               // 20151020
                                                                               // itemid->proditemid
    }

    String reqString = xu.javaBean2Xml(request);
    reqString = XML_TITLE + reqString;
    return reqString;
  }

  @Override
  public OrdersListGetResponse dangdangOrdersListGet(AomsshopT aomsshopT, String sDate,
      String eDate, String state, int page, int pageSize, UseTimeEnum orderUseTime,
      String scheduleType) throws Exception {
    OrdersListGetResponse olgResponse = null;
    try {
      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
      Map<String, String> formMap = new HashMap<String, String>();
      // 默认0按修改时间查询，1为按创建时间查询
      if (orderUseTime == UseTimeEnum.CREATE_TIME) { // modify by mowj 20151118 by using
                                                          // Enumeration
        formMap.put("osd", sDate);// 下单开始日期
        formMap.put("oed", eDate);// 下单结束日期
      } else {
        formMap.put("lastModifyTime_start", sDate);// 最后修改时间_开始
        formMap.put("lastModifyTime_end", eDate);// 最后修改时间_结束
      }
      formMap.put("os", state);// 订单状态 9999 全部
      formMap.put("p", String.valueOf(page));// 页数，预设值为1
      formMap.put("pageSize", String.valueOf(pageSize));// 每页结果数量，只能选择如下数值：5、10、15、20 可以不填，默认是20
      String response = client.execute("dangdang.orders.list.get", formMap);
      // 资料捞回来后，先存DB
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DangdangCommonTool.STORE_TYPE,
          "[" + orderUseTime + "]|dangdang.orders.list.get 查询订单列表信息", response,
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(), scheduleType);
      olgResponse = (OrdersListGetResponse) xu.xml2JavaBean(response, OrdersListGetResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return olgResponse;
  }

  @Override
  public OrderDetailsGetResponse dangdangOrderDetailsGet(AomsshopT aomsshopT, String orderID,
      String scheduleType) throws Exception {
    OrderDetailsGetResponse odgResponse = null;
    try {
      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("o", orderID);// 订单编号，一次只能查询一张订单详细信息
      String resultClient = client.execute("dangdang.order.details.get", formMap);
      // 资料捞回来后，先存DB
      // add by mowj 20150903
      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", DangdangCommonTool.STORE_TYPE,
          "dangdang.order.details.get 查看订单详细信息", resultClient,
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(), scheduleType);

      odgResponse =
          (OrderDetailsGetResponse) xu.xml2JavaBean(resultClient, OrderDetailsGetResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return odgResponse;
  }

  // @Override
  public OrdersExchangeReturnListGetResponse dangdangOrdersExchangeReturnListGet(
      AomsshopT aomsshopT, String sDate, String eDate, String state, int page, int pageSize,
      String scheduleType) throws Exception {
    OrdersExchangeReturnListGetResponse oerlgResponse = null;
    try {
      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("resd", sDate);// 申请退换货开始日期
      formMap.put("reed", eDate);// 申请退换货结束日期
      formMap.put("applyStatus", state);// 申请单状态
      formMap.put("p", String.valueOf(page));// 页数，预设值为1
      formMap.put("isNeedReverseReason", "1");// 如果为“1”则表示会返回商品的退换货原因，如果为0或者其他值则表示不返回
      // 退换货一次仅10笔，无法使用pageSize
      String listGetResponse = client.execute("dangdang.orders.exchange.return.list.get", formMap);
      // 资料捞回来后，先存DB add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DangdangCommonTool.STORE_TYPE,
          "dangdang.orders.exchange.return.list.get 查询退换货订单列表信息", listGetResponse,
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          scheduleType);
      oerlgResponse = (OrdersExchangeReturnListGetResponse) xu.xml2JavaBean(listGetResponse,
          OrdersExchangeReturnListGetResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return oerlgResponse;
  }

  // @Override
  private OrdersRefundListResponse dangdangOrdersRefundList(AomsshopT aomsshopT, String sDate,
      String eDate, String state, int page, int pageSize, String scheduleType) throws Exception {
    OrdersRefundListResponse orlResponse = null;
    try {
      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("refundApp_Status", state);// 申请单状态 9999 全部
      formMap.put("osd", sDate);// 退款单开始日期
      formMap.put("oed", eDate);// 退款单结束日期
      formMap.put("pageIndex", String.valueOf(page));// 默认值1
      formMap.put("pageSize", String.valueOf(pageSize));// 默认值为30,最大值100
      String refundListResponse = client.execute("dangdang.orders.refund.list", formMap);
      // 资料捞回来后，先存DB add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DangdangCommonTool.STORE_TYPE,
          "dangdang.orders.refund.list 查询商家退款订单列表信息", refundListResponse,
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          scheduleType);
      orlResponse = (OrdersRefundListResponse) xu.xml2JavaBean(refundListResponse,
          OrdersRefundListResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return orlResponse;
  }

  // @Override
  private ItemGetResponse dangdangItemGet(AomsshopT aomsshopT, String itemID, String scheduleType)
      throws Exception {
    ItemGetResponse igResponse = null;
    try {
      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("it", itemID);// 商品标识符，普通商品or分色分码产品or分色分码产品下的商品信息
      String response = client.execute("dangdang.item.get", formMap);
      // 资料捞回来后，先存DB add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", DangdangCommonTool.STORE_TYPE,
          "dangdang.item.get 查看商品详细信息", response, SourceLogBizTypeEnum.AOMSITEMT.getValueString(),
          aomsshopT.getAomsshop001(), scheduleType);
      igResponse = (ItemGetResponse) xu.xml2JavaBean(response, ItemGetResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return igResponse;
  }

  // @Override
  private ItemListGetResponse dangdangItemsListGet(AomsshopT aomsshopT, String sDate, String eDate,
      int page, int pageSize, String scheduleType) throws Exception {
    ItemListGetResponse ilgResponse = null;
    try {
      // 调用API
      DangdangClient client =
          new DangdangClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.DANGDANG_API),
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006());
      Map<String, String> formMap = new HashMap<String, String>();
      formMap.put("mts", sDate);// 可以查询出修改时间之后的商品
      formMap.put("mte", eDate);// 可以查询出修改时间之前的商品
      formMap.put("p", String.valueOf(page));// 页数，预设值为1
      formMap.put("pageSize", String.valueOf(pageSize));// 每页结果数量，只能选择如下数值：5、10、15、20 可以不填，默认是20
      String resultClient = client.execute("dangdang.items.list.get", formMap);
      // 资料捞回来后，先存DB add by mowj 20150824
      // modi by mowj 20150825 增加biztype
      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, DangdangCommonTool.STORE_TYPE,
          "dangdang.items.list.get 查询商品列表信息", resultClient,
          SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
          scheduleType);
      ilgResponse = (ItemListGetResponse) xu.xml2JavaBean(resultClient, ItemListGetResponse.class);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return ilgResponse;
  }

  // =====定时任务=====
  @Override
  public void syncOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    try {
      // 根据修改时间捞取数据
      String sDate = taskScheduleConfig.getLastUpdateTime();
      String eDate = taskScheduleConfig.getEndDate();
      String modiDate = taskScheduleConfig.getLastUpdateTime();// 每一笔更新后的时间。预设先给上次修改时间
      int pageSize = taskScheduleConfig.getMaxPageSize();
      int pageNo = 1;
      // String state = "9999";
      // String dateType = "0";// 修改时间

      // 取得时间区间内总资料笔数
      int totalSize = Integer.parseInt(
          dangdangOrdersListGet(aomsshopT, sDate, eDate, DangdangCommonTool.FULL_ORDER_STATUS,
              pageNo, DangdangCommonTool.MIN_PAGE_SIZE, UseTimeEnum.UPDATE_TIME,
              taskScheduleConfig.getScheduleType()).getTotalInfo().getOrderCount());

      // 区间内没有资料
      if (totalSize == 0) {
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return;
        }
      }

      // 整理搜寻区间，直到区间数目<=最大读取数目
      // 整理方式，采二分法
      while (totalSize > taskScheduleConfig.getMaxReadRow()) {
        // eDate变为sDate与eDate的中间时间
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
        // System.out.println("eDate" + eDate);
        totalSize = Integer.parseInt(
            dangdangOrdersListGet(aomsshopT, sDate, eDate, DangdangCommonTool.FULL_ORDER_STATUS,
                pageNo, DangdangCommonTool.MIN_PAGE_SIZE, UseTimeEnum.UPDATE_TIME,
                taskScheduleConfig.getScheduleType()).getTotalInfo().getOrderCount());
      }

      // 区间内有资料，计算页数
      int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
      for (int i = pageNum; i > 0; i--) {
        // 区间内资料可以一次储存完毕，储存完后离开循环
        List<Object> saveAomsordTs = new ArrayList<Object>();
        OrdersListGetResponse olgResponse =
            dangdangOrdersListGet(aomsshopT, sDate, eDate, DangdangCommonTool.FULL_ORDER_STATUS, i,
                pageSize, UseTimeEnum.UPDATE_TIME, taskScheduleConfig.getScheduleType());
        if (null == olgResponse.getOrdersList()
            || null == olgResponse.getOrdersList().getOrderInfos()) {
          // logger.info("syncOrdersData正在处理第" + i + "页" + "， 总共有" + pageNum + "页" + "OrderInfo为空");
          throw new Exception(
              "syncOrdersData正在处理第" + i + "页" + "， 总共有" + pageNum + "页" + "OrderInfo为空");
        }
        for (com.digiwin.ecims.platforms.dangdang.bean.response.orders.list.get.OrderInfo orderInfo : olgResponse
            .getOrdersList().getOrderInfos()) {
          OrderDetailsGetResponse odgResponse = null;
          // 当当抓取资料时，连线可能会timeout，经决议，作法为连线异常时无限reTry
          int reTryNumber = 10000;
          for (int j = 0; j < reTryNumber;) {
            try {
              odgResponse = dangdangOrderDetailsGet(aomsshopT, orderInfo.getOrderID(),
                  taskScheduleConfig.getScheduleType());
              break;
            } catch (Exception ex) {
              logger.error("当当抓取资料时，连线timeout，正在处理第{}页, 总共有{}页, 订单编号为{}已经reTry{}次", (pageNum - i),
                  pageNum, orderInfo.getOrderID(), reTryNumber);
              // 连线错误时 reTry
              j++;
              continue;
            }
          }

          List<AomsordT> list = new AomsordTTranslator().doTransToAomsordTBean(odgResponse,
              aomsshopT.getAomsshop001(), orderInfo.getOrderTimeStart());
          saveAomsordTs.addAll(list);

          // 比较区间数据时间，取最大时间
          if (DateTimeTool.parse(odgResponse.getLastModifyTime())
              .after(DateTimeTool.parse(modiDate))) {
            modiDate = odgResponse.getLastModifyTime();
          }
        }

        // 每页储存数据一次
        taskService.newTransaction4Save(saveAomsordTs);
      }
      // 开始时间与更新时间为同一秒钟，则modiDate+1秒
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate, modiDate);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public void syncReturnListAndRefundsData(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) throws Exception {
    try {
      digiwinOrdersExchangeReturnListGet(taskScheduleConfig, aomsshopT);// 查询退换货订单列表信息
      digiwinOrdersRefundList(taskScheduleConfig, aomsshopT);// 查询商家退款订单列表信息
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  private void digiwinOrdersExchangeReturnListGet(TaskScheduleConfig taskScheduleConfig,
      AomsshopT aomsshopT) throws Exception {
    try {
      // 根据申请时间捞取数据
      String sDate = taskScheduleConfig.getLastUpdateTime();
      String eDate = taskScheduleConfig.getEndDate();
      // 退换货一次仅10笔，无法使用pageSize
      int pageSize =
          taskScheduleConfig.getMaxPageSize() > 10 ? 10 : taskScheduleConfig.getMaxPageSize();
      String state = "9999";

      // 取得时间区间内总资料笔数
      int totalSize = Integer.parseInt(dangdangOrdersExchangeReturnListGet(aomsshopT, sDate, eDate,
          state, 1, 10, taskScheduleConfig.getScheduleType()).getTotalInfo().getTotalOrderCount());
      if (totalSize == 0) {
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return;
      }
      // 区间内有资料，计算页数
      int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
      // 针对每一页(倒序)的所有数据新增
      for (int i = pageNum; i > 0; i--) {
        // 区间内资料可以一次储存完毕，储存完后离开循环
        OrdersExchangeReturnListGetResponse oerlgResponse = dangdangOrdersExchangeReturnListGet(
            aomsshopT, sDate, eDate, state, i, 10, taskScheduleConfig.getScheduleType());
        List<AomsrefundT> aomsrefundTList = new AomsrefundTTranslator()
            .doTransToReturnListBean(oerlgResponse, aomsshopT.getAomsshop001(), taskScheduleConfig);
        // 每页储存数据一次
        taskService.newTransaction4Save(aomsrefundTList);
      }
      // 区间内数据都更新完成，在更新最后更新时间
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  private void digiwinOrdersRefundList(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    try {
      // 根据申请时间捞取数据
      String sDate = taskScheduleConfig.getLastUpdateTime();
      String eDate = taskScheduleConfig.getEndDate();
      // 退款pageSize,默认值为30,最大值100
      int pageSize =
          taskScheduleConfig.getMaxPageSize() > 100 ? 100 : taskScheduleConfig.getMaxPageSize();
      String state = "9999";

      // 取得时间区间内总资料笔数
      int totalSize = Integer.parseInt(dangdangOrdersRefundList(aomsshopT, sDate, eDate, state, 1,
          30, taskScheduleConfig.getScheduleType()).getRefundInfos().getTotalSize());
      if (totalSize == 0) {
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return;
      }
      // 区间内有资料，计算页数
      int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
      // 针对每一页(倒序)的所有数据新增
      for (int i = pageNum; i > 0; i--) {
        // 区间内资料可以一次储存完毕，储存完后离开循环
        OrdersRefundListResponse orlResponse = dangdangOrdersRefundList(aomsshopT, sDate, eDate,
            state, i, pageSize, taskScheduleConfig.getScheduleType());
        List<AomsrefundT> aomsrefundTList = new AomsrefundTTranslator()
            .doTransToRefundListBean(orlResponse, aomsshopT.getAomsshop001());
        // 每页储存数据一次
        taskService.newTransaction4Save(aomsrefundTList);
      }
      // 区间内数据都更新完成，在更新最后更新时间
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public void syncGoodsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    try {
      // 根据修改时间捞取数据
      String sDate = taskScheduleConfig.getLastUpdateTime();
      String eDate = taskScheduleConfig.getEndDate();
      String modiDate = taskScheduleConfig.getLastUpdateTime();
      int pageSize = taskScheduleConfig.getMaxPageSize();

      // 取得时间区间内总资料笔数
      int totalSize = Integer.parseInt(
          dangdangItemsListGet(aomsshopT, sDate, eDate, 1, 5, taskScheduleConfig.getScheduleType())
              .getTotalInfo().getItemsCount());

      // 整理搜寻区间，直到区间数目<=最大读取数目
      // 整理方式，采二分法
      while (totalSize > taskScheduleConfig.getMaxReadRow()) {
        // eDate变为sDate与eDate的中间时间
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
        // System.out.println("eDate" + eDate);
        totalSize = Integer.parseInt(dangdangItemsListGet(aomsshopT, sDate, eDate, 1, 5,
            taskScheduleConfig.getScheduleType()).getTotalInfo().getItemsCount());
      }

      // 区间内没有资料
      if (totalSize == 0) {
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果区间的 eDate < sysDate 则把lastUpdateTime变为eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return;
        }
      }

      // 计算页数
      int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
      // 针对每一页(倒序)的所有数据新增
      for (int i = pageNum; i > 0; i--) {
        List<Object> aomsitemTs = new ArrayList<Object>();
        // logger.info("syncGoodsData正在处理第" + i + "页");
        ItemListGetResponse ilgResponse = dangdangItemsListGet(aomsshopT, sDate, eDate, i, 20,
            taskScheduleConfig.getScheduleType());

        for (com.digiwin.ecims.platforms.dangdang.bean.response.items.list.get.ItemInfo itemInfo : ilgResponse
            .getItemsList().getItemInfos()) {
          // 取得每个itemInfo信息
          ItemGetResponse igResponse = dangdangItemGet(aomsshopT, itemInfo.getItemID(),
              taskScheduleConfig.getScheduleType());
          List<AomsitemT> list =
              new AomsitemTTranslator().doTrans(igResponse, aomsshopT.getAomsshop001(), eDate);
          aomsitemTs.addAll(list);

          // 比较区间数据时间，取最大时间
          if (DateTimeTool.parse(itemInfo.getUpdateTime()).after(DateTimeTool.parse(modiDate))) {
            modiDate = itemInfo.getUpdateTime();
          }
        }
        // 每页储存数据一次
        taskService.newTransaction4Save(aomsitemTs);
      }
      // 开始时间与更新时间为同一秒钟，则modiDate+1秒
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate, modiDate);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * 取付款时间资料
   *
   * @param storeId
   * @return
   */
  private String getPaymentDate(String id) throws Exception {
    String result = "";
    Object obj = baseDAO
        .findUniqueResultBySQL(" select distinct aoms024 FROM AomsordT WHERE id ='" + id + "'");
    if (null != obj) {
      result = obj.toString();
    }
    return result;
  }

  /**
   * 取商品上、下架Request
   * 
   * @param cmdReq
   * @param itemsStatus 只能放"上架"或"下架"
   * @return
   */
  private ItemsStatusUpdateRequest getItemsStatusUpdateRequest(CmdReq cmdReq, String itemsStatus)
      throws Exception {
    ItemsStatusUpdateRequest dangdangRequest = new ItemsStatusUpdateRequest();
    try {
      ProductInfoList productInfoList = new ProductInfoList();
      List<ProductInfo> productInfo = new ArrayList<ProductInfo>();
      productInfoList.setProductInfo(productInfo);
      dangdangRequest.setProductInfoList(productInfoList);
      dangdangRequest.getProductInfoList().getProductInfo()
          .add(new ProductInfo(cmdReq.getParams().get("numid").trim(), itemsStatus));
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    return dangdangRequest;
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    // TODO Auto-generated method stub
    return null;
  }

}
