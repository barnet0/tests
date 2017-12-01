package com.digiwin.ecims.platforms.suning.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suning.api.DefaultSuningClient;
import com.suning.api.entity.inventory.InventoryModifyRequest;
import com.suning.api.entity.inventory.InventoryModifyResponse;
import com.suning.api.entity.item.ItemQueryRequest;
import com.suning.api.entity.item.ItemQueryResponse;
import com.suning.api.entity.item.ItemdetailQueryRequest;
import com.suning.api.entity.item.ItemdetailQueryResponse;
import com.suning.api.entity.item.ItemsaleQueryRequest;
import com.suning.api.entity.item.ItemsaleQueryResponse;
import com.suning.api.entity.item.ItemsaleQueryResponse.ItemSaleParams;
import com.suning.api.entity.item.ShelvesAddRequest;
import com.suning.api.entity.item.ShelvesAddResponse;
import com.suning.api.entity.item.ShelvesMoveRequest;
import com.suning.api.entity.item.ShelvesMoveResponse;
import com.suning.api.entity.rejected.BatchrejectedOrdQueryRequest;
import com.suning.api.entity.rejected.BatchrejectedOrdQueryResponse;
import com.suning.api.entity.rejected.BatchrejectedQueryRequest;
import com.suning.api.entity.rejected.BatchrejectedQueryResponse;
import com.suning.api.entity.rejected.SinglerejectedGetRequest;
import com.suning.api.entity.rejected.SinglerejectedGetResponse;
import com.suning.api.entity.transaction.OrdQueryRequest;
import com.suning.api.entity.transaction.OrdQueryResponse;
import com.suning.api.entity.transaction.OrderGetRequest;
import com.suning.api.entity.transaction.OrderGetResponse;
import com.suning.api.entity.transaction.OrderQueryRequest;
import com.suning.api.entity.transaction.OrderQueryResponse;
import com.suning.api.entity.transaction.OrdercodeQueryRequest;
import com.suning.api.entity.transaction.OrdercodeQueryResponse;
import com.suning.api.entity.transaction.OrderdeliveryAddRequest;
import com.suning.api.entity.transaction.OrderdeliveryAddRequest.OrderLineNumbers;
import com.suning.api.entity.transaction.OrderdeliveryAddRequest.SendDetail;
import com.suning.api.entity.transaction.OrderdeliveryAddResponse;
import com.suning.api.exception.SuningApiException;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.suning.service.api.SuningApiService;
import com.digiwin.ecims.platforms.suning.util.SuningClientTool;
import com.digiwin.ecims.platforms.suning.util.SuningCommonTool;
import com.digiwin.ecims.platforms.suning.util.SuningTranslatorTool;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class SuningApiServiceImpl implements SuningApiService {

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private ParamSystemService paramSystemService;

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
    // 取得相關參數
    String storeId = cmdReq.getStoreid(); // 取得商店碼
    String orderId = cmdReq.getParams().get("id"); // 取得訂單號

    // 取得 api 認証的 key.
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    OrderGetResponse response = suningCustomOrderGet(
        esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, orderId);

    // 檢查是否有 Error
    ResponseError re = SuningCommonTool.getInstance()
        .hasError(SuningCommonTool.ApiInterface.DIGIWIN_ORDER_DETAIL_GET, response.getBody());

    if (re == null) {
      return new CmdRes(cmdReq, Boolean.TRUE, re,
          SuningTranslatorTool.getInstance()
          .transOrderGetToAomsordTBean(storeId, response, StringTool.EMPTY)
      );
    } else {
      return new CmdRes(cmdReq, Boolean.FALSE, re, null);
    }
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    // 取得相關參數
    String storeId = cmdReq.getStoreid(); // 取得商店碼
    String orderId = cmdReq.getParams().get("id"); // 取得訂單號(苏宁使用订单号查询退货单)

    // 取得 api 認証的 key.
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    SinglerejectedGetResponse response = suningCustomSingleRejectedGet(
        esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, orderId);

    ResponseError re = SuningCommonTool.getInstance()
        .hasError(SuningCommonTool.ApiInterface.DIGIWIN_REFUND_GET, response.getBody());

    if (re == null) {
      SinglerejectedGetResponse reSc = response;
      return new CmdRes(cmdReq, Boolean.TRUE, re, SuningTranslatorTool.getInstance()
          .transSinglerejectedGetToAomsrefundTBean(storeId, reSc) // 將資料轉換成 AomsrefundT.
      );
    } else {
      return new CmdRes(cmdReq, Boolean.FALSE, re, null);
    }
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    // 取得相關參數
    String storeId = cmdReq.getStoreid(); // 取得商店碼
    String productCode = cmdReq.getParams().get("numid");// 商品編號

    // 取得 api 認証的 key.
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    ItemdetailQueryResponse response = suningCustomItemDetailQuery(
        esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, productCode);
    // System.out.println("返回json/xml格式数据 :" + response.getBody());

    ResponseError re = SuningCommonTool.getInstance()
        .hasError(SuningCommonTool.ApiInterface.DIGIWIN_ITEM_DETAIL_GET, response.getBody()); 

    if (re == null) {
      // 取得 商品销售情况
//      ItemSaleParams itemSale = this.getItemSaleQuery(esv, productCode, storeId,
//          "Mercury_Manually_Suning_digiwinItemDetailGet");
      ItemsaleQueryResponse itemsaleQueryResponse = suningCustomItemSaleQuery(
          esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, 
          StringTool.EMPTY, productCode, StringTool.EMPTY, 
          StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY, 
          null, null, StringTool.EMPTY);
      ItemSaleParams itemSale = itemsaleQueryResponse.getSnbody().getItemSaleParams().get(0);

      return new CmdRes(cmdReq, Boolean.TRUE, re,
          SuningTranslatorTool.getInstance()
          .doTransToAomsitemTBean(storeId, response, itemSale) // 將資料轉換成AomsrefundT.
      );
    } else {
      return new CmdRes(cmdReq, Boolean.FALSE, re, null);
    }
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
 // 取得相關參數
    String storeId = cmdReq.getStoreid(); // 取得商店碼
    String expressCompanyCode = cmdReq.getParams().get("expcompno");// 物流公司ID
    String expressNo = cmdReq.getParams().get("expressno");// 運單號
    String orderCode = cmdReq.getParams().get("oid");// 訂單號

    // 取得 api 認証的 key.
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    OrderdeliveryAddResponse response = suningCustomOrderDeliveryAdd(
        esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, 
        orderCode, expressNo, expressCompanyCode, DateTimeTool.format(new Date()), 
        StringTool.EMPTY, cmdReq.getProdcodelist(), cmdReq.getSuboidlist());
    // System.out.println("返回json/xml格式数据 :" + response.getBody());

    ResponseError re = SuningCommonTool.getInstance()
        .hasError(SuningCommonTool.ApiInterface.DIGIWIN_SHIPPING_SEND, response.getBody());
    
    return new CmdRes(cmdReq, re == null, re, null);
    
  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    // 取得相關參數
    String storeId = cmdReq.getStoreid(); // 取得商店碼
    String numid = cmdReq.getParams().get("numid"); // 商品在电商平台的ID //product id
    // String num = cmdReq.getParams().get("num"); //上架数量（此参数只会用于淘宝上架，其他平台不会check）

    // 取得 api 認証的 key.
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    ShelvesAddResponse response = suningCustomShelvesAdd(
        esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, numid);
    // System.out.println("返回json/xml格式数据 :" + response.getBody());

    // 檢查是否有 Error
    ResponseError re = SuningCommonTool.getInstance()
        .hasError(SuningCommonTool.ApiInterface.DIGIWIN_ITEM_LISTING, response.getBody());
    
    return new CmdRes(cmdReq, re == null, re, null);
    
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
 // 取得相關參數
    String storeId = cmdReq.getStoreid(); // 取得商店碼
    String numid = cmdReq.getParams().get("numid"); // 商品在电商平台的ID //product id

    // 取得 api 認証的 key.
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    ShelvesMoveResponse response = suningCustomShelvesMove(
        esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, numid);
    // System.out.println("返回json/xml格式数据 :" + response.getBody());

    // 檢查是否有 Error
    ResponseError re = SuningCommonTool.getInstance()
        .hasError(SuningCommonTool.ApiInterface.DIGIWIN_ITEM_DELISTING, response.getBody());
    
    return new CmdRes(cmdReq, re == null, re, null);
    
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    // 取得相關參數
    String storeId = cmdReq.getStoreid(); // 取得商店碼
    // String productCode = cmdReq.getParams().get("numid");// 商品編號
    String productCode = cmdReq.getParams().get("skuid");// 商品規格編號
    String outerid = cmdReq.getParams().get("outerid");// 商家商品編碼
    String destInvNum = cmdReq.getParams().get("num");// 數量

    // 取得 api 認証的 key.
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(storeId);

    InventoryModifyResponse response = suningCustomInventoryModify(
        esv.getAppKey(), esv.getAppSecret(), StringTool.EMPTY, 
        productCode, outerid, StringTool.EMPTY, destInvNum, StringTool.EMPTY);
    // System.out.println("返回json/xml格式数据 :" + response.getBody());

    ResponseError re = SuningCommonTool.getInstance()
        .hasError(SuningCommonTool.ApiInterface.DIGIWIN_INVENTORY_UPDATE, response.getBody());
    
    return new CmdRes(cmdReq, re == null, re, null);
    
  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    // TODO Auto-generated method stub
    return null;
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

  /**
   * 取得 商品销售情况
   * 
   * @param aomsshopT
   * @param productCode
   * @throws Exception
   */
  public ItemSaleParams getItemSaleQuery(AomsshopT aomsshopT, String productCode,
      String scheduleType) throws Exception {
    // 取得 api 認証的 key.
    // String storeId = aomsshopT.getAomsshop001();
    String appKey = aomsshopT.getAomsshop004();
    String appSecret = aomsshopT.getAomsshop005();
    String accessToken = aomsshopT.getAomsshop006();
    String shopId = aomsshopT.getAomsshop001();
    ESVerification esv = new ESVerification(appKey, appSecret, accessToken);

    return this.getItemSaleQuery(esv, productCode, shopId, scheduleType);
  }

  /**
   * 取得 商品销售情况
   * 
   * @param productCode
   */
  private ItemSaleParams getItemSaleQuery(ESVerification esv, String productCode, String shopId,
      String scheduleType) throws Exception {
    try {
      ItemsaleQueryRequest request = new ItemsaleQueryRequest();
      request.setProductCode(productCode);

      DefaultSuningClient client =
          new DefaultSuningClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
              esv.getAppKey(), esv.getAppSecret(), "json");
      ItemsaleQueryResponse response = client.excute(request);
      // System.out.println("返回json/xml格式数据 :" + response.getBody());

      String api = "suning.custom.itemsale.query 商品销售情况查询";
      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", SuningCommonTool.STORE_TYPE,
          api, response.getBody(), SourceLogBizTypeEnum.AOMSITEMT.getValueString(), shopId,
          scheduleType);

      ResponseError re = SuningCommonTool.getInstance()
          .hasError(SuningCommonTool.ApiInterface.DIGIWIN_ITEM_DETAIL_GET, response.getBody()); // 檢查是否有
                                                                                                // error

      // 檢查是否有出錯
      if (re == null) {
        return response.getSnbody().getItemSaleParams().get(0);
      } else {
        if ("biz.handler.data-get:no-result".equals(re.getCode())) {
          return null; // 表示查不到資料
        } else {
          throw new Exception(re.getCode() + "," + re.getMsg());
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @Override
  public OrderGetResponse suningCustomOrderGet(String appKey, String appSecret, String accessToken,
      String orderCode) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    OrderGetRequest request = new OrderGetRequest();
    request.setOrderCode(orderCode);
    
    OrderGetResponse response = client.excute(request); 
    
    return response;
  }

  @Override
  public OrdQueryResponse suningCustomOrdQuery(String appKey, String appSecret, String accessToken,
      String startTime, String endTime, String orderLineStatus, Integer pageNo, Integer pageSize)
      throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    OrdQueryRequest request = new OrdQueryRequest();
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    request.setOrderLineStatus(orderLineStatus);
    request.setPageNo(pageNo);
    request.setPageSize(pageSize);
    
    OrdQueryResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public OrderQueryResponse suningCustomOrderQuery(String appKey, String appSecret,
      String accessToken, String startTime, String endTime, String orderStatus, Integer pageNo,
      Integer pageSize) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    OrderQueryRequest request = new OrderQueryRequest();
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    if (StringTool.isNotEmpty(orderStatus)) {
      request.setOrderStatus(orderStatus);
    }
    request.setPageNo(pageNo);
    request.setPageSize(pageSize);

    OrderQueryResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public OrderdeliveryAddResponse suningCustomOrderDeliveryAdd(String appKey, String appSecret,
      String accessToken, String orderCode, String expressNo, String expressCompanyCode,
      String deliveryTime, String phoneIdentityCode, 
      String[] productCodes, String[] orderLineNumbers)
      throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    
    OrderdeliveryAddRequest request = new OrderdeliveryAddRequest();
    request.setOrderCode(orderCode);
    request.setExpressNo(expressNo);
    request.setExpressCompanyCode(expressCompanyCode);
	  Date dt=new Date();
	  dt.setMinutes(dt.getMinutes()-3);
	
    //request.setDeliveryTime(DateTimeTool.format(new Date()));
	  request.setDeliveryTime(DateTimeTool.format(dt));
	  
    if (productCodes != null) {
      SendDetail sendDetail = new SendDetail();
      List<String> productCode = new ArrayList<String>();
      for (String pc : productCodes) {
        productCode.add(pc);
      }
      sendDetail.setProductCode(productCode);
      request.setSendDetail(sendDetail);
    }

    if (orderLineNumbers != null) {
      OrderLineNumbers ordLineNos = new OrderLineNumbers();
      List<String> orderLineNumber = new ArrayList<String>();
      for (String sb : orderLineNumbers) {
        orderLineNumber.add(sb);
      }
      ordLineNos.setOrderLineNumber(orderLineNumber);
      request.setOrderLineNumbers(ordLineNos);
    }
    
    OrderdeliveryAddResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public OrdercodeQueryResponse suningCustomOrderCodeQuery(String appKey, String appSecret,
      String accessToken, String startTime, String endTime, String orderStatus)
      throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    OrdercodeQueryRequest request = new OrdercodeQueryRequest();
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    if (StringTool.isNotEmpty(orderStatus)) {
      request.setOrderStatus(orderStatus);
    }
    
    OrdercodeQueryResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public BatchrejectedQueryResponse suningCustomBatchRejectedQuery(String appKey, String appSecret,
      String accessToken, String startTime, String endTime, Integer pageNo, Integer pageSize)
      throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    BatchrejectedQueryRequest request = new BatchrejectedQueryRequest();
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    request.setPageNo(pageNo);
    request.setPageSize(pageSize);
    
    BatchrejectedQueryResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public BatchrejectedOrdQueryResponse suningCustomBatchRejectedOrdQuery(String appKey,
      String appSecret, String accessToken, String startTime, String endTime)
      throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    BatchrejectedOrdQueryRequest request = new BatchrejectedOrdQueryRequest();
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    
    BatchrejectedOrdQueryResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public SinglerejectedGetResponse suningCustomSingleRejectedGet(String appKey, String appSecret,
      String accessToken, String orderCode) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    SinglerejectedGetRequest request = new SinglerejectedGetRequest();
    request.setOrderCode(orderCode);
    
    SinglerejectedGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public ItemQueryResponse suningCustomItemQuery(String appKey, String appSecret,
      String accessToken, String categoryCode, String brandCode, String status, String startTime,
      String endTime, Integer pageNo, Integer pageSize) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    ItemQueryRequest request = new ItemQueryRequest();
    if (StringTool.isNotEmpty(categoryCode)) {
      request.setCategoryCode(categoryCode);
    }
    if (StringTool.isNotEmpty(brandCode)) {
      request.setBrandCode(brandCode);
    }
    if (StringTool.isNotEmpty(status)) {
      request.setStatus(status);
    }
    if (StringTool.isNotEmpty(startTime)) {
      request.setStartTime(startTime);
    }
    if (StringTool.isNotEmpty(endTime)) {
      request.setEndTime(endTime);
    }
    request.setPageNo(pageNo);
    request.setPageSize(pageSize);
    
    ItemQueryResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public ItemdetailQueryResponse suningCustomItemDetailQuery(String appKey, String appSecret,
      String accessToken, String productCode) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    ItemdetailQueryRequest request = new ItemdetailQueryRequest();
    request.setProductCode(productCode);
    
    ItemdetailQueryResponse reponse = client.excute(request);
    
    return reponse;
  }

  @Override
  public ItemsaleQueryResponse suningCustomItemSaleQuery(String appKey, String appSecret,
      String accessToken, String productName, String productCode, String categoryCode,
      String priceUpper, String priceLimit, String saleStatus, Integer pageNo, Integer pageSize,
      String cmTitle) throws SuningApiException {
    // TODO Auto-generated method stub
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    ItemsaleQueryRequest request = new ItemsaleQueryRequest();
    if (StringTool.isNotEmpty(productName)) {
      request.setProductName(productName);
    }
    if (StringTool.isNotEmpty(productCode)) {
      request.setProductCode(productCode);
    }
    if (StringTool.isNotEmpty(categoryCode)) {
      request.setCategoryCode(categoryCode);
    }
    if (StringTool.isNotEmpty(priceUpper)) {
      request.setPriceUpper(priceUpper);
    }
    if (StringTool.isNotEmpty(priceLimit)) {
      request.setPriceLimit(priceLimit);
    }
    if (StringTool.isNotEmpty(saleStatus)) {
      request.setSaleStatus(saleStatus);
    }
    if (StringTool.isNotEmpty(pageNo)) {
      request.setPageNo(pageNo);
    }
    if (StringTool.isNotEmpty(pageSize)) {
      request.setPageSize(pageSize);
    }
    if (StringTool.isNotEmpty(cmTitle)) {
      request.setCmTitle(cmTitle);
    }
    
    ItemsaleQueryResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public ShelvesAddResponse suningCustomShelvesAdd(String appKey, String appSecret,
      String accessToken, String productCode) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    ShelvesAddRequest request = new ShelvesAddRequest();
    request.setProductCode(productCode);
    
    ShelvesAddResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public ShelvesMoveResponse suningCustomShelvesMove(String appKey, String appSecret,
      String accessToken, String productCode) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    ShelvesMoveRequest request = new ShelvesMoveRequest();
    request.setProductCode(productCode);
    
    ShelvesMoveResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public InventoryModifyResponse suningCustomInventoryModify(String appKey, String appSecret,
      String accessToken, String productCode, String itemCode, String invAddrId, String destInvNum,
      String invType) throws SuningApiException {
    DefaultSuningClient client = SuningClientTool.getInstance().getSuningClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
        appKey, appSecret, accessToken);
    
    InventoryModifyRequest request = new InventoryModifyRequest();
    if (StringTool.isNotEmpty(productCode)) {
      request.setProductCode(productCode);
    }
    if (StringTool.isNotEmpty(itemCode)) {
      request.setItemCode(itemCode);
    }
    if (StringTool.isNotEmpty(invAddrId)) {
      request.setInvAddrId(invAddrId);
    }
    if (StringTool.isNotEmpty(destInvNum)) {
      request.setDestInvNum(destInvNum);
    }
    if (StringTool.isNotEmpty(invType)) {
      request.setInvType(invType);
    }
    
    InventoryModifyResponse response = client.excute(request);    
    return response;
  }

//  @Deprecated
//  private SuningResponse callSuningApi(ESVerification esv, SuningRequest req) throws Exception {
//    // FIXME 未完成.....
//    DefaultSuningClient client =
//        new DefaultSuningClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.SUNING_API),
//            esv.getAppKey(), esv.getAppSecret(), "json");
//    SuningResponse response = client.excute(req);
//    return response;
//  }


  public static void main(String args[]) throws Exception {
    CmdReq cmdReq = new CmdReq();
    cmdReq.setStoreid("DZ0023");

    // 商品上架--ok
    // cmdReq.setApi("digiwin.item.listing");
    // cmdReq.getParams().put("numid", "12345678");

    // 商品下架--ok
    // cmdReq.setApi("digiwin.item.delisting");
    // cmdReq.getParams().put("numid", "12345678");

    // 訂單發貨 --ok
    cmdReq.setApi("digiwin.shipping.send");
    cmdReq.getParams().put("expcompno", "Y02");
    cmdReq.getParams().put("expressno", "3100491124146");
    cmdReq.getParams().put("oid", "3017678576");

    // 库存修改(单个) --ok
    // cmdReq.setApi("digiwin.inventory.update");
    // cmdReq.getParams().put("numid", "12345657");// 商品編號
    // cmdReq.getParams().put("num", "1");// 數量

    // 单笔订单查询 --ok
    // cmdReq.setApi("digiwin.order.detail.get");
    // cmdReq.getParams().put("oid", "3016601811");

    // 单笔查询退货信息--ok
    cmdReq.setApi("digiwin.refund.get");
    cmdReq.getParams().put("oid", "10018908362");

    // 單筆鋪貨詳情
    // cmdReq.setApi("digiwin.item.detail.get");
    // cmdReq.getParams().put("numid", "105492557");

    SuningApiServiceImpl run = new SuningApiServiceImpl();
    CmdRes res = run.execute(cmdReq);
    System.out.println(res.getApi());
  }

}
