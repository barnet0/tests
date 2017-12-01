package com.digiwin.ecims.platforms.icbc.service.api.impl;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.core.util.XmlUtils;
import com.digiwin.ecims.platforms.icbc.bean.IcbcClient;
import com.digiwin.ecims.platforms.icbc.bean.base.Product;
import com.digiwin.ecims.platforms.icbc.bean.base.Products;
import com.digiwin.ecims.platforms.icbc.bean.item.IcbcStorageModifyRequest;
import com.digiwin.ecims.platforms.icbc.bean.item.IcbcStorageModifyResponse;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail.IcbcProductDetailRequest;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail.IcbcProductDetailResponse;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductlist.IcbcProductListRequest;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductlist.IcbcProductListResponse;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail.IcbcOrderDetailRequest;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail.IcbcOrderDetailResponse;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist.IcbcOrderListRequest;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist.IcbcOrderListResponse;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcordersendmess.IcbcOrderSendmessRequest;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcordersendmess.IcbcOrderSendmessResponse;
import com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery.IcbcRefundQueryRequest;
import com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery.IcbcRefundQueryResponse;
import com.digiwin.ecims.platforms.icbc.bean.translator.IcbcAomsitemTTranslator;
import com.digiwin.ecims.platforms.icbc.bean.translator.IcbcAomsordTTranslator;
import com.digiwin.ecims.platforms.icbc.bean.translator.IcbcAomsrefundTTranslator;
import com.digiwin.ecims.platforms.icbc.service.api.IcbcApiService;
import com.digiwin.ecims.platforms.icbc.util.IcbcClientUtil;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class IcbcApiServiceImpl implements IcbcApiService {

  public static final String XML_TITLE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private ParamSystemService paramSystemService; // add by mowj 20150928

  @Override
  public CmdRes execute(CmdReq cmdReq) throws Exception {
    String api = cmdReq.getApi();

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
      case DIGIWIN_INVENTORY_UPDATE:
        cmdRes = digiwinInventoryUpdate(cmdReq);
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
    // 取得授權資料
    ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    String orderIds = cmdReq.getParams().get("id"); // 訂單號

    // 調用API
    IcbcClient client = new IcbcClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API),
        veriInfo.getAppKey(), veriInfo.getAppSecret());
    IcbcOrderDetailRequest request = new IcbcOrderDetailRequest(orderIds);
    String result = client.execute(request, veriInfo.getAccessToken());
    IcbcOrderDetailResponse response =
        (IcbcOrderDetailResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcOrderDetailResponse.class);

    boolean success = response.getHead().getReturnMsg().equals("OK");
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getReturnCode(),
                response.getHead().getReturnMsg()),
        success ? new IcbcAomsordTTranslator(response, cmdReq.getStoreid()).doTranslate() : null);
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    // 取得授權資料
    ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    String orderIds = cmdReq.getParams().get("id"); // 訂單號

    // 調API
    IcbcClient client = new IcbcClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API),
        veriInfo.getAppKey(), veriInfo.getAppSecret());
    IcbcRefundQueryRequest request = new IcbcRefundQueryRequest(orderIds);
    String result = client.execute(request, veriInfo.getAccessToken());
    IcbcRefundQueryResponse response =
        (IcbcRefundQueryResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcRefundQueryResponse.class);

    boolean success = response.getHead().getReturnMsg().equals("OK");
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getReturnCode(),
                response.getHead().getReturnMsg()),
        success ? new IcbcAomsrefundTTranslator(response, cmdReq.getStoreid()).doTranslate()
            : null);
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    // 取得授權資料
    ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    String product_ids = cmdReq.getParams().get("numid"); // 商品ID

    // 調用API
    IcbcClient client = new IcbcClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API),
        veriInfo.getAppKey(), veriInfo.getAppSecret());
    IcbcProductDetailRequest request = new IcbcProductDetailRequest(product_ids);
    String result = client.execute(request, veriInfo.getAccessToken());
    IcbcProductDetailResponse response =
        (IcbcProductDetailResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcProductDetailResponse.class);

    boolean success = response.getHead().getReturnMsg().equals("OK");
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getHead().getReturnCode(),
                response.getHead().getReturnMsg()),
        success ? new IcbcAomsitemTTranslator(response, cmdReq.getStoreid()).doTranslate() : null);
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
    // 取得授權資料
    ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    String order_id = cmdReq.getParams().get("oid");// 訂單ID
    String logistics_company = cmdReq.getParams().get("expcompno");// 物流公司編碼
    String shipping_code = cmdReq.getParams().get("expressno");// 運單號
    String shipping_time = DateTimeTool.format(new Date());// 發貨時間

    // 調用API
    IcbcClient client = new IcbcClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API),
        veriInfo.getAppKey(), veriInfo.getAppSecret());
    IcbcOrderSendmessRequest request =
        new IcbcOrderSendmessRequest(order_id, logistics_company, shipping_code, shipping_time);
    String result = client.execute(request, veriInfo.getAccessToken());
    IcbcOrderSendmessResponse response =
        (IcbcOrderSendmessResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcOrderSendmessResponse.class);

    boolean success = response.getHead().getReturnMsg().equals("OK");
    return new CmdRes(cmdReq, success, success ? null
        : new ResponseError(response.getHead().getReturnCode(), response.getHead().getReturnMsg()),
        null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    // 取得授權資料
    ESVerification veriInfo = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    String product_id = cmdReq.getParams().get("numid");// 商品ID
    String product_sku_id = cmdReq.getParams().get("skuid");// skuId
    String product_code = cmdReq.getParams().get("outerid");// 商品在ERP的編碼
    int storage = Integer.parseInt(cmdReq.getParams().get("num"));// 數量

    // 調用API
    IcbcClient client = new IcbcClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API),
        veriInfo.getAppKey(), veriInfo.getAppSecret());
    IcbcStorageModifyRequest request = new IcbcStorageModifyRequest(new Products(
        Arrays.asList(new Product(product_id, product_sku_id, product_code, storage))));
    String result = client.execute(request, veriInfo.getAccessToken());
    IcbcStorageModifyResponse response =
        (IcbcStorageModifyResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcStorageModifyResponse.class);

    boolean success = response.getHead().getReturnCode().equals("00");// 該API調用成功時
                                                                      // 沒有returnMsg...
    return new CmdRes(cmdReq, success, success ? null
        : new ResponseError(response.getHead().getReturnCode(), response.getHead().getReturnMsg()),
        null);
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
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public IcbcOrderListResponse icbcb2cOrderList(String appKey, String appSecret, String accessToken,
      String createStartTime, String createEndTime, String modifyTimeFrom, String modifyTimeTo,
      Integer orderStatus) throws Exception {
    IcbcClient client = IcbcClientUtil.getInstance().getIcbcClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API), appKey, appSecret, accessToken);
    IcbcOrderListRequest request = new IcbcOrderListRequest();
    if (StringTool.isNotEmpty(createStartTime)) {
      request.setCreateStartTime(createStartTime);
    }
    if (StringTool.isNotEmpty(createEndTime)) {
      request.setCreateEndTime(createEndTime);
    }
    if (StringTool.isNotEmpty(modifyTimeFrom)) {
      request.setModifyTimeFrom(modifyTimeFrom);
    }
    if (StringTool.isNotEmpty(modifyTimeTo)) {
      request.setModifyTimeTo(modifyTimeTo);
    }
    if (StringTool.isNotEmpty(orderStatus)) {
      request.setOrderStatus(orderStatus);
    }
    
    String result = client.execute(request, accessToken);

    IcbcOrderListResponse response =
        (IcbcOrderListResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcOrderListResponse.class);

    return response;
  }

  @Override
  public IcbcOrderDetailResponse icbcb2cOrderDetail(String appKey, String appSecret,
      String accessToken, String orderIds) throws Exception {
    IcbcClient client = IcbcClientUtil.getInstance().getIcbcClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API), appKey, appSecret, accessToken);
    IcbcOrderDetailRequest request = new IcbcOrderDetailRequest(orderIds);
    String result = client.execute(request, accessToken);
    IcbcOrderDetailResponse response =
        (IcbcOrderDetailResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcOrderDetailResponse.class);
    
    return response;
  }

  @Override
  public IcbcRefundQueryResponse icbcb2cRefundQuery(String appKey, String appSecret,
      String accessToken, String createStartTime, String createEndTime, String refundStatus,
      String orderId) throws Exception {
    IcbcClient client = IcbcClientUtil.getInstance().getIcbcClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API), appKey, appSecret, accessToken);
    IcbcRefundQueryRequest request = new IcbcRefundQueryRequest(createStartTime, createEndTime);
    if (StringTool.isNotEmpty(orderId)) {
      request.setOrderId(orderId);
    }
    
    String result = client.execute(request, accessToken);

    IcbcRefundQueryResponse response =
        (IcbcRefundQueryResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcRefundQueryResponse.class);
    
    return response;
  }

  @Override
  public IcbcProductListResponse icbcb2cProductList(String appKey, String appSecret,
      String accessToken, String createStartTime, String createEndTime, String modifyTimeFrom,
      String modifyTimeTo, String putTimeFrom, String putTimeTo, Integer productStatus)
      throws Exception {
    // TODO Auto-generated method stub
    IcbcClient client = IcbcClientUtil.getInstance().getIcbcClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API), appKey, appSecret, accessToken);
    IcbcProductListRequest request = new IcbcProductListRequest();
    if (StringTool.isNotEmpty(createStartTime)) {
      request.setCreateStartTime(createStartTime);
    }
    if (StringTool.isNotEmpty(createEndTime)) {
      request.setCreateEndTime(createEndTime);
    }
    if (StringTool.isNotEmpty(modifyTimeFrom)) {
      request.setModifyTimeFrom(modifyTimeFrom);
    }
    if (StringTool.isNotEmpty(modifyTimeTo)) {
      request.setModifyTimeTo(modifyTimeTo);
    }
    if (StringTool.isNotEmpty(putTimeFrom)) {
      request.setPutTimeFrom(putTimeFrom);
    }
    if (StringTool.isNotEmpty(putTimeTo)) {
      request.setPutTimeTo(putTimeTo);
    }
    if (StringTool.isNotEmpty(productStatus)) {
      request.setProductStatus(productStatus);
    }
    
    String result = client.execute(request, accessToken);

    IcbcProductListResponse response =
        (IcbcProductListResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcProductListResponse.class);
    
    return response;
  }

  @Override
  public IcbcProductDetailResponse icbcb2cProductDetail(String appKey, String appSecret,
      String accessToken, String productIds) throws Exception {
    IcbcClient client = IcbcClientUtil.getInstance().getIcbcClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API), appKey, appSecret, accessToken);
    IcbcProductDetailRequest request = new IcbcProductDetailRequest(productIds);
    
    String result = client.execute(request, accessToken);
    
    IcbcProductDetailResponse response =
        (IcbcProductDetailResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcProductDetailResponse.class);
    
    return response;
  }

  @Override
  public IcbcOrderSendmessResponse icbcb2cOrderSendmess(String appKey, String appSecret,
      String accessToken, String orderId, String logisticsCompany, String shippingCode,
      String shippingTime, String shippingUser, String notes, Products products) throws Exception {
    IcbcClient client = IcbcClientUtil.getInstance().getIcbcClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.ICBC_API), appKey, appSecret, accessToken);
    IcbcOrderSendmessRequest request =
        new IcbcOrderSendmessRequest(orderId, logisticsCompany, shippingCode, shippingTime);
    if (StringTool.isNotEmpty(shippingUser)) {
      request.setShippingUser(shippingUser);
    }
    if (StringTool.isNotEmpty(notes)) {
      request.setNotes(notes);
    }
    if (products != null) {
      request.setProducts(products);
    }
    
    String result = client.execute(request, accessToken);
    
    IcbcOrderSendmessResponse response =
        (IcbcOrderSendmessResponse) XmlUtils.getInstance().xml2JavaBean(result, IcbcOrderSendmessResponse.class);
    
    return response;
  }
}
