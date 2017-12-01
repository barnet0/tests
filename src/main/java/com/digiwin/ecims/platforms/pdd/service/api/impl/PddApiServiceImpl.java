package com.digiwin.ecims.platforms.pdd.service.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.pdd.bean.PddClient;
import com.digiwin.ecims.platforms.pdd.bean.request.SendGoodsRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.item.GetGoodsRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.item.SynSkuStockRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.pdd.bean.request.order.OrderSearchRequest;
import com.digiwin.ecims.platforms.pdd.bean.response.SendGoodsResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetGoodsResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.item.SynSkuStockResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.pdd.service.api.PddApiService;
import com.digiwin.ecims.platforms.pdd.util.PddClientUtil;
import com.digiwin.ecims.platforms.pdd.util.PddCommonTool;
import com.digiwin.ecims.platforms.pdd.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.pdd.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.pdd2.bean.response.refund.RefundGetResponse;
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class PddApiServiceImpl implements PddApiService {

  private static final Logger logger = LoggerFactory.getLogger(PddApiServiceImpl.class);

  @Autowired
  private ParamSystemService paramSystemService; // add by mowj 20150928

  @Autowired
  private AomsShopService aomsShopService;
  
  @Autowired 
  private Pdd2ApiService pdd2ApiService;  //2070509 add by cjp
  
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

    OrderGetResponse response = pddOrderGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), orderId);

    boolean success = response.getResult() == PddCommonTool.RESPONSE_SUCCESS_CODE;
    
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getResult() + "", response.getCause()),
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

    SendGoodsResponse response = pddSndGoods(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        orderId, logisticsId, System.currentTimeMillis() + "", waybill);

    boolean success = response.getResult() == PddCommonTool.RESPONSE_SUCCESS_CODE;
    
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getResult() + "", response.getCause()), null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
 // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  
    // 取得参数
    // 商品sku id
    String skuId = cmdReq.getParams().get("skuid");
    // 商品在ERP料号
    String outerId = cmdReq.getParams().get("outerid");
    // 数量
    String quantity = cmdReq.getParams().get("num");
  
    SynSkuStockResponse response = pddSynSkuStock(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        skuId, outerId, Long.parseLong(quantity)); 
  
    boolean success = response.getResult() == PddCommonTool.RESPONSE_SUCCESS_CODE;
    
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getResult() + "", response.getCause()), null);
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
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

    GetGoodsResponse response = pddGetGoods(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),  
        null, numid, null, 
        PddCommonTool.MIN_PAGE_NO, PddCommonTool.MIN_PAGE_SIZE);
    
    boolean success = response.getResult() == PddCommonTool.RESPONSE_SUCCESS_CODE;
    
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getResult() + "", response.getCause()),
            success ? new AomsitemTTranslator(response)
                .doTranslate(cmdReq.getStoreid(), null) : null);
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
  public OrderSearchResponse pddOrderSearch(String appKey, String appSecret, String accessToken,
      Integer orderStatus, String beginTime, String endTime, Integer page, Integer pageSize)
      throws Exception {
    PddClient client = PddClientUtil.getInstance().getPddClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD_API), appKey, appSecret);
    OrderSearchRequest request = new OrderSearchRequest();
    request.setOrderStatus(orderStatus);
    if (StringTool.isNotEmpty(beginTime)) {
      request.setBeginTime(beginTime);
    }
    if (StringTool.isNotEmpty(endTime)) {
      request.setEndTime(endTime);
    }
    request.setPage(page);
    request.setPageSize(pageSize);
    
    OrderSearchResponse response = client.execute(request);
    
    return response;
  }

  @Override
  public OrderGetResponse pddOrderGet(String appKey, String appSecret, String accessToken,
      String orderSN) throws Exception {
    PddClient client = PddClientUtil.getInstance().getPddClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD_API), appKey, appSecret);
    OrderGetRequest request = new OrderGetRequest();
    request.setOrderSN(orderSN);
    
    OrderGetResponse response = client.execute(request);
    
    return response;
  }

  @Override
  public SendGoodsResponse pddSndGoods(String appKey, String appSecret, String accessToken,
      String orderSN, String sndStyle, String consignTime, String billID) throws Exception {
    PddClient client = PddClientUtil.getInstance().getPddClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD_API), appKey, appSecret);
    SendGoodsRequest request = new SendGoodsRequest();
    request.setOrderSN(orderSN);
    request.setSndStyle(sndStyle);
    request.setBillID(billID);
    request.setConsignTime(System.currentTimeMillis() + "");
    
    SendGoodsResponse response = client.execute(request);
    
    return response;
  }

  @Override
  public SynSkuStockResponse pddSynSkuStock(String appKey, String appSecret, String accessToken,
      String pddSkuId, String outerSkuIds, Long quantity) throws Exception {
    PddClient client = PddClientUtil.getInstance().getPddClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD_API), appKey, appSecret);
    SynSkuStockRequest request = new SynSkuStockRequest();
    if (StringTool.isNotEmpty(pddSkuId)) {
      request.setPddSkuId(pddSkuId);
    }
    if (StringTool.isNotEmpty(outerSkuIds)) {
      request.setOuterSkuIds(outerSkuIds);
    }
    request.setQuantity(quantity);
    
    SynSkuStockResponse response = client.execute(request);
    
    return response;
  }

  @Override
  public GetGoodsResponse pddGetGoods(String appKey, String appSecret, String accessToken,
      String goodsType, String outerID, String goodsName, Integer page, Integer pageSize)
      throws Exception {
    PddClient client = PddClientUtil.getInstance().getPddClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD_API), appKey, appSecret);
    GetGoodsRequest request = new GetGoodsRequest();
    if (StringTool.isNotEmpty(goodsType)) {
      request.setGoodsType(goodsType);
    }
    if (StringTool.isNotEmpty(outerID)) {
      request.setOuterID(outerID);
    }
    if (StringTool.isNotEmpty(goodsName)) {
      request.setGoodsName(goodsName);
    }
    request.setPage(page);
    request.setPageSize(pageSize);
    
    GetGoodsResponse response = client.execute(request);
    
    return response;
  }
  /**
   * add by cjp 20170510
   */
  @Override
  public RefundGetResponse pddRefundGet(String appKey, String appSecret, String accessToken,
	      String afterSalesType, String afterSalesStatus, String startUpdatedAt, String endUpdatedAt,
	      Integer page, Integer pageSize) throws Exception {
	  return pdd2ApiService.pddRefundGet(appKey, appSecret, accessToken, afterSalesType, afterSalesStatus, startUpdatedAt, endUpdatedAt, page, pageSize);
	  
  }
}
