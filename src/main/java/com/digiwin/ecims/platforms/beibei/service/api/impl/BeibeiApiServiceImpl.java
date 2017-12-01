package com.digiwin.ecims.platforms.beibei.service.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.platforms.beibei.bean.BeibeiClient;
import com.digiwin.ecims.platforms.beibei.bean.request.item.OuterItemGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.item.OuterItemQtyUpdateRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.item.OuterItemWarehouseGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.logistics.OuterTradeLogisticsShipRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.order.OuterTradeOrderDetailGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.order.OuterTradeOrderGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.refund.OuterRefundDetailGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.request.refund.OuterRefundsGetRequest;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemQtyUpdateResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemWarehouseGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.logistics.OuterTradeLogisticsShipResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundsGetResponse;
import com.digiwin.ecims.platforms.beibei.service.api.BeibeiApiService;
import com.digiwin.ecims.platforms.beibei.util.BeibeiClientUtil;
import com.digiwin.ecims.platforms.beibei.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.beibei.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.beibei.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class BeibeiApiServiceImpl implements BeibeiApiService {

  @Autowired
  private ParamSystemService paramSystemService;

  @Autowired
  private AomsShopService aomsShopService;
  
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
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得参数
    String orderId = cmdReq.getParams().get("id");

    OuterTradeOrderDetailGetResponse response = beibeiOuterTradeOrderDetailGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), orderId);

    boolean success = response.getSuccess();
    
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(success + "", response.getMessage()),
        success ? new AomsordTTranslator(response).doTranslate(cmdReq.getStoreid()) : null);
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得参数
    // 物流公司ID
    String logisticsId = cmdReq.getParams().get("expcompno");
    // 运单号
    String waybill = cmdReq.getParams().get("expressno");
    // 订单号
    String orderId = cmdReq.getParams().get("oid"); 

    OuterTradeLogisticsShipResponse response = beibeiOuterTradeLogisticsShip(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        orderId, logisticsId, waybill);

    boolean success = response.getSuccess();
    
    return new CmdRes(cmdReq, success,
        success ? 
            null : new ResponseError(response.getSuccess() + "", response.getMessage()), 
        null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  
    // 取得参数
    String numId = cmdReq.getParams().get("numid");
    // 商品在ERP料号
    String outerId = cmdReq.getParams().get("outerid");
    // 数量
    String quantity = cmdReq.getParams().get("num");
  
    OuterItemQtyUpdateResponse response = beibeiOuterItemQtyUpdate(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        numId, outerId, quantity); 
  
    boolean success = response.getSuccess();
    
    return new CmdRes(cmdReq, success,
        success ? 
            null : new ResponseError(response.getSuccess() + "", response.getMessage()), 
        null);
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得参数-退款单号
    String refundId = cmdReq.getParams().get("id");

    OuterRefundDetailGetResponse response = beibeiOuterRefundDetailGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), null, refundId);

    boolean success = response.getSuccess();
    
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getSuccess() + "", response.getMessage()),
        success ? new AomsrefundTTranslator(response).doTranslate(cmdReq.getStoreid()) : null);
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
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得API所需参数
    // 此处需要ERP传入ERP的料号，并在拼多多平台上进行映射维护
    String numid = cmdReq.getParams().get("numid");

    OuterItemGetResponse response = beibeiOuterItemGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), numid); 
    
    boolean success = response.getSuccess();
    
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getSuccess() + "", response.getMessage()),
            success ? new AomsitemTTranslator(response).doTranslate(cmdReq.getStoreid()) : null);
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
  public OuterTradeOrderGetResponse beibeiOuterTradeOrderGet(
      String appKey, String appSecret, String accessToken, 
      String status, String timeRange, 
      String startTime, String endTime,
      Long pageNo, Long pageSize) throws Exception {

    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterTradeOrderGetRequest request = new OuterTradeOrderGetRequest();
    if (StringTool.isNotEmpty(status)) {
      request.setStatus(status);
    }
    request.setTimeRange(timeRange);
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    if (StringTool.isNotEmpty(pageNo)) {
      request.setPageNo(pageNo);
    }
    if (StringTool.isNotEmpty(pageSize)) {
      request.setPageSize(pageSize);
    }
    
    OuterTradeOrderGetResponse response = client.execute(request, accessToken);
        
    return response;
  }

  @Override
  public OuterTradeOrderDetailGetResponse beibeiOuterTradeOrderDetailGet(
      String appKey, String appSecret, String accessToken, 
      String oid) throws Exception {
    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterTradeOrderDetailGetRequest request = new OuterTradeOrderDetailGetRequest();
    request.setOid(oid);
    
    OuterTradeOrderDetailGetResponse response = client.execute(request, accessToken);
    
    return response;
  }

  @Override
  public OuterRefundsGetResponse beibeiOuterRefundsGet(
      String appKey, String appSecret, String accessToken, 
      String timeRange, String status, 
      String startTime, String endTime,
      Long pageNo, Long pageSize) throws Exception {
    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterRefundsGetRequest request = new OuterRefundsGetRequest();
    request.setTimeRange(timeRange);
    if (StringTool.isNotEmpty(status)) {
      request.setStatus(status);
    }
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    if (pageNo != null) {
      request.setPageNo(pageNo);
    }
    if (pageSize != null) {
      request.setPageSize(pageSize);
    }
    
    OuterRefundsGetResponse response = client.execute(request, accessToken);
    
    return response;
  }

  @Override
  public OuterRefundDetailGetResponse beibeiOuterRefundDetailGet(
      String appKey, String appSecret, String accessToken, 
      String oid, String refundId) throws Exception {
    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterRefundDetailGetRequest request = new OuterRefundDetailGetRequest();
    if (StringTool.isNotEmpty(oid)) {
      request.setOid(oid);
    }
    if (StringTool.isNotEmpty(refundId)) {
      request.setRefundId(refundId);
    }
    
    OuterRefundDetailGetResponse response = client.execute(request, accessToken);
    
    return response;
  }

  @Override
  public OuterItemWarehouseGetResponse beibeiOuterItemWarehouseGet(
      String appKey, String appSecret, String accessToken, 
      Long pageNo, Long pageSize) throws Exception {
    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterItemWarehouseGetRequest request = new OuterItemWarehouseGetRequest();
    if (pageNo != null) {
      request.setPageNo(pageNo);
    }
    if (pageSize != null) {
      request.setPageSize(pageSize);
    }
    
    OuterItemWarehouseGetResponse response = client.execute(request, accessToken);
    
    return response;
  }

  @Override
  public OuterItemGetResponse beibeiOuterItemGet(String appKey, String appSecret,
      String accessToken, String iid) throws Exception {
    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterItemGetRequest request = new OuterItemGetRequest();
    request.setIid(iid);
    
    OuterItemGetResponse response = client.execute(request, accessToken);
    
    return response;
  }

  @Override
  public OuterTradeLogisticsShipResponse beibeiOuterTradeLogisticsShip(
      String appKey, String appSecret, String accessToken, 
      String oid, String company, String outSid) throws Exception {
    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterTradeLogisticsShipRequest request = new OuterTradeLogisticsShipRequest();
    request.setOid(oid);
    request.setCompany(company);
    request.setOutSid(outSid);
    
    OuterTradeLogisticsShipResponse response = client.execute(request, accessToken);
    
    return response;
  }

  @Override
  public OuterItemQtyUpdateResponse beibeiOuterItemQtyUpdate(
      String appKey, String appSecret, String accessToken, 
      String iid, String outerId, String qty) throws Exception {
    BeibeiClient client = BeibeiClientUtil.getInstance().getBeibeiClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.BEIBEI_API), appKey, appSecret);
    OuterItemQtyUpdateRequest request = new OuterItemQtyUpdateRequest();
    request.setIid(iid);
    request.setOuterId(outerId);
    request.setQty(qty);
    
    OuterItemQtyUpdateResponse response = client.execute(request, accessToken);
    
    return response;
  }
  
  
}
