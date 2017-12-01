package com.digiwin.ecims.platforms.yhd.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yhd.YhdClient;
import com.yhd.object.ErrDetailInfoList;
import com.yhd.object.logistics.Warehouse;
import com.yhd.object.product.SerialChildProd;
import com.yhd.object.refund.RefundDetail;
import com.yhd.request.logistics.LogisticsOrderShipmentsUpdateRequest;
import com.yhd.request.logistics.LogisticsWarehouseInfoGetRequest;
import com.yhd.request.order.OrderDetailGetRequest;
import com.yhd.request.order.OrdersDetailGetRequest;
import com.yhd.request.order.OrdersGetRequest;
import com.yhd.request.order.OrdersRefundAbnormalGetRequest;
import com.yhd.request.product.GeneralProductsSearchRequest;
import com.yhd.request.product.ProductShelvesdownUpdateRequest;
import com.yhd.request.product.ProductShelvesupUpdateRequest;
import com.yhd.request.product.ProductStockUpdateRequest;
import com.yhd.request.product.ProductsPriceGetRequest;
import com.yhd.request.product.ProductsStockGetRequest;
import com.yhd.request.product.ProductsStockUpdateRequest;
import com.yhd.request.product.SerialChildproductsGetRequest;
import com.yhd.request.product.SerialProductAttributeGetRequest;
import com.yhd.request.product.SerialProductGetRequest;
import com.yhd.request.product.SerialProductsSearchRequest;
import com.yhd.request.product.SerialProductsStockUpdateRequest;
import com.yhd.request.refund.RefundDetailGetRequest;
import com.yhd.request.refund.RefundGetRequest;
import com.yhd.response.logistics.LogisticsOrderShipmentsUpdateResponse;
import com.yhd.response.logistics.LogisticsWarehouseInfoGetResponse;
import com.yhd.response.order.OrderDetailGetResponse;
import com.yhd.response.order.OrdersDetailGetResponse;
import com.yhd.response.order.OrdersGetResponse;
import com.yhd.response.order.OrdersRefundAbnormalGetResponse;
import com.yhd.response.product.GeneralProductsSearchResponse;
import com.yhd.response.product.ProductShelvesdownUpdateResponse;
import com.yhd.response.product.ProductShelvesupUpdateResponse;
import com.yhd.response.product.ProductStockUpdateResponse;
import com.yhd.response.product.ProductsPriceGetResponse;
import com.yhd.response.product.ProductsStockGetResponse;
import com.yhd.response.product.ProductsStockUpdateResponse;
import com.yhd.response.product.SerialChildproductsGetResponse;
import com.yhd.response.product.SerialProductAttributeGetResponse;
import com.yhd.response.product.SerialProductGetResponse;
import com.yhd.response.product.SerialProductsSearchResponse;
import com.yhd.response.product.SerialProductsStockUpdateResponse;
import com.yhd.response.refund.RefundDetailGetResponse;
import com.yhd.response.refund.RefundGetResponse;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.platforms.yhd.service.api.YhdApiService;
import com.digiwin.ecims.platforms.yhd.util.YhdClientUtil;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.ApiPageParam;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.InventoryUpdateType;
import com.digiwin.ecims.platforms.yhd.util.YhdCommonTool.OrderRefundAbnormalDateType;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsitemTTranslator;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsordTTranslator;
import com.digiwin.ecims.platforms.yhd.util.translator.YhdAomsrefundTTranslator;

/**
 * 
 * @author Sen.Shen
 *
 */
@Service
public class YhdApiServiceImpl implements YhdApiService {

  @Autowired
  private TaskService taskService;

  @Autowired
  private ParamSystemService paramSystemService;

  @Autowired
  private AomsShopService aomsShopService;

  @Override
  public CmdRes execute(CmdReq cmdReq) throws Exception {
    String api = cmdReq.getApi();

//    if (api.equals(EcImsApiEnum.DIGIWIN_ORDER_DETAIL_GET.toString())) { // 獲取單筆訂單詳情
//      return digiwinOrderDetailGet(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_REFUND_GET.toString())) { // 退款單下載
//      return digiwinRefundGet(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DETAIL_GET.toString())) { // 获取单笔铺货资料
//      return digiwinItemDetailGet(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_LISTING.toString())) { // 商品上架
//      return digiwinItemListing(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_ITEM_DELISTING.toString())) { // 商品下架
//      return digiwinItemDelisting(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_SHIPPING_SEND.toString())) { // 訂單發貨
//      return digiwinShippingSend(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_INVENTORY_UPDATE.toString())) { // 店鋪庫存初始化
//      return digiwinInventoryUpdate(cmdReq);
//    } else if (api.equals(EcImsApiEnum.DIGIWIN_INVENTORY_BATCH_UPDATE.toString())) { // 批量库存更新
//      return digiwinInventoryBatchUpdate(cmdReq);
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

    // 取得參數
    String orderCode = cmdReq.getParams().get("id");

    OrderDetailGetResponse response = yhdOrderDetailGet(
        esVerification.getAppKey(), esVerification.getAppSecret(), 
        esVerification.getAccessToken(), orderCode);

    boolean success = (response.getOrderInfo() != null);
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
                response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
        success ? new YhdAomsordTTranslator(response.getOrderInfo(),
            cmdReq.getStoreid()).doTranslate() : null);
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    String refundCode = cmdReq.getParams().get("id");

    // 調用API
//    YhdClient yhd = new YhdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API),
//        // authData.getAomsshop004(), authData.getAomsshop005()
//        esVerification.getAppKey(), esVerification.getAppSecret());
//    RefundDetailGetRequest request = new RefundDetailGetRequest();
//    request.setRefundCode(refundCode);
//    RefundDetailGetResponse response = yhd.excute(request,
//        // authData.getAomsshop006()
//        esVerification.getAccessToken());
    
    RefundDetailGetResponse response = yhdRefundDetailGet(
        esVerification.getAppKey(), esVerification.getAppSecret(), 
        esVerification.getAccessToken(), refundCode);

    // 如果不是普通退货退款，则查找是否为异常订单退款（物品破损但买家不想退货，则卖家按适当比例赔偿给买家一些钱）
    // 由于异常订单退款API中，startTime与endTime，和dateType为必填项，且跨度只有三个月。
    // 所以这里往前推了三个月一次。
    if (response.getErrorCount() != 0) {
      Date date = new Date();
      String sDate = DateTimeTool.format(DateTimeTool.getAfterMonths(date, -3));
      String eDate = DateTimeTool.format(date);

      // OrdersRefundAbnormalGetResponse abnormalResponse = yhdOrdersRefundAbnormalGet(authData,
      // sDate, eDate, 1, 1, refundCode);
      OrdersRefundAbnormalGetResponse abnormalResponse =
          yhdOrdersRefundAbnormalGet(
              esVerification.getAppKey(), esVerification.getAppSecret(), 
              esVerification.getAccessToken(), 
              refundCode, null, null, null, null, 
              sDate, eDate, OrderRefundAbnormalDateType.APPLY_TIME.getValue(), 
              ApiPageParam.MIN_PAGE_NO.getValue(), ApiPageParam.MIN_PAGE_SIZE.getValue());

      boolean success = abnormalResponse.getErrorCount() == 0;
      return new CmdRes(cmdReq, success,
          success ? null
              : new ResponseError(
                  abnormalResponse.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
                  abnormalResponse.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
          success ? new YhdAomsrefundTTranslator(abnormalResponse.getRefundOrderInfoList(),
              // authData.getAomsshop001()
              cmdReq.getStoreid()).doTranslateAbNormal() : null);

    } else {
      boolean success = (response.getRefundInfoMsg() != null);
      return new CmdRes(cmdReq, success, success ? null
          : new ResponseError(response.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
              response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
          success ? new YhdAomsrefundTTranslator(response.getRefundInfoMsg(),
              // authData.getAomsshop001()
              cmdReq.getStoreid()).doTranslate() : null);
    }
  }

  /*
   * 單品鋪貨
   */
  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numid = cmdReq.getParams().get("numid");

    List<AomsitemT> aomsitemTList = null;

    // 單品鋪貨普通產品
    // GeneralProductsSearchResponse generalRes = getGeneralItemsGet(shopInfo, numid);
    GeneralProductsSearchResponse generalRes = yhdGeneralProductsSearch(
        esVerification.getAppKey(), esVerification.getAppSecret(), 
        esVerification.getAccessToken(), null, null, null, null, null, 
        numid, null, null, null, null, null, null, null, null);

    if (generalRes.getErrorCount() != 0) {// 有錯
      // 系列產品鋪貨
      // SerialProductsSearchResponse serialRes = getSerialItemsGet(shopInfo, numid);
      SerialProductsSearchResponse serialRes = yhdSerialProductsSearch(
          esVerification.getAppKey(), esVerification.getAppSecret(), 
          esVerification.getAccessToken(), null, null, null, null, null, 
          numid, null, null, null, null, null, null, null, null);
      if (serialRes.getErrorCount() != 0) {// 有錯
        return new CmdRes(cmdReq, false,
            new ResponseError(serialRes.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
                generalRes.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
            null);
      } else {
        aomsitemTList = new ArrayList<AomsitemT>();

        // SerialProductGetResponse serialProductRes = getSerialChildProd(shopInfo,
        // Long.valueOf(numid));
        SerialProductGetResponse serialProductRes =
            yhdSerialProductGet(
                esVerification.getAppKey(), esVerification.getAppSecret(), 
                esVerification.getAccessToken(), Long.valueOf(numid), null, null, null);

        // SerialProductAttributeGetResponse serialProductAttr =
        // yhdSerialProductAttributeGet(shopInfo, Long.valueOf(numid));
        SerialProductAttributeGetResponse serialProductAttr =
            yhdSerialProductAttributeGet(
                esVerification.getAppKey(), esVerification.getAppSecret(), 
                esVerification.getAccessToken(), Long.valueOf(numid), null);

        // 商品价格 add by mowj 20150819
        String serialChildProdIdString = "";
        for (SerialChildProd scProd : serialProductRes.getSerialChildProdList()
            .getSerialChildProd()) {
          serialChildProdIdString += scProd.getProductId() + ",";
        }
        serialChildProdIdString =
            serialChildProdIdString.substring(0, serialChildProdIdString.length() - 1);
        // ProductsPriceGetResponse productsPriceGetResponse = yhdProductsPriceGet(shopInfo,
        // serialChildProdIdString, "");
        ProductsPriceGetResponse productsPriceGetResponse =
            yhdProductsPriceGet(
                esVerification.getAppKey(), esVerification.getAppSecret(), 
                esVerification.getAccessToken(), serialChildProdIdString, "");

        // add by mowj 20150831 添加商品库存
        // ProductsStockGetResponse productsStockGetResponse = yhdProductsStockGet(shopInfo,
        // serialChildProdIdString, "", 0l);
        ProductsStockGetResponse productsStockGetResponse =
            yhdProductsStockGet(
                esVerification.getAppKey(), esVerification.getAppSecret(), 
                esVerification.getAccessToken(), serialChildProdIdString, "", null);

        if (serialRes.getSerialProductList() != null) {

          aomsitemTList =
              new YhdAomsitemTTranslator(serialRes.getSerialProductList().getSerialProduct().get(0),
                  // shopInfo.getAomsshop001()
                  cmdReq.getStoreid()).doTranslate(serialProductRes, serialProductAttr,
                      productsPriceGetResponse,
                      productsStockGetResponse
          );
        }
     // add by mowj 20151223
     // 由于一号店铺货API的原因，需要在下完此指令后保存或更新到数据库
        taskService.newTransaction4Save(aomsitemTList); 

        return new CmdRes(cmdReq, true, null, aomsitemTList);
      }
    } else {
      // 商品价格（包括普通商品、系列子产品） add by mowj 20150819
      // ProductsPriceGetResponse productsPriceGetResponse = yhdProductsPriceGet(shopInfo, numid,
      // "");
      ProductsPriceGetResponse productsPriceGetResponse =
          yhdProductsPriceGet(
              esVerification.getAppKey(), esVerification.getAppSecret(), 
              esVerification.getAccessToken(), numid, "");

      // 商品库存 add by mowj 20150831
      // ProductsStockGetResponse productsStockGetResponse = yhdProductsStockGet(shopInfo, numid,
      // "", 0L);
      ProductsStockGetResponse productsStockGetResponse =
          yhdProductsStockGet(
              esVerification.getAppKey(), esVerification.getAppSecret(), 
              esVerification.getAccessToken(), numid, "", null);

      aomsitemTList = new ArrayList<AomsitemT>();
      if (generalRes.getProductList() != null) {

        aomsitemTList
            .add(new YhdAomsitemTTranslator(generalRes.getProductList().getProduct().get(0),
                // shopInfo.getAomsshop001()
                cmdReq.getStoreid()).doTranslate(productsPriceGetResponse,
                    productsStockGetResponse));
      }
      // add by mowj 20151223 
      // 由于一号店铺货API的原因，需要在下完此指令后保存或更新到数据库
      taskService.newTransaction4Save(aomsitemTList);

      return new CmdRes(cmdReq, true, null, aomsitemTList);
    }
  }

  @Override
  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    String orderCode = cmdReq.getParams().get("oid");// 訂單號
    Long deliverySupplierId = Long.parseLong(cmdReq.getParams().get("expcompno"));// 快遞公司ID
    String expressNbr = cmdReq.getParams().get("expressno");// 運單號

    // 調用API
//    YhdClient yhd = new YhdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API),
//        // authData.getAomsshop004(), authData.getAomsshop005()
//        esVerification.getAppKey(), esVerification.getAppSecret());
//    LogisticsOrderShipmentsUpdateRequest request = new LogisticsOrderShipmentsUpdateRequest();
//    request.setOrderCode(orderCode);
//    request.setDeliverySupplierId(deliverySupplierId);
//    request.setExpressNbr(expressNbr);
//    LogisticsOrderShipmentsUpdateResponse response = yhd.excute(request,
//        // authData.getAomsshop006()
//        esVerification.getAccessToken());

    LogisticsOrderShipmentsUpdateResponse response = yhdLogisticsOrderShipmentsUpdate(
        esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAccessToken(), 
        orderCode, deliverySupplierId, expressNbr, null, null);
    
    boolean success = response.getErrorCount() == 0;

    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
                response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
        null);

  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    // 取得授權
    // AomsshopT authData = getAuthorizationByStoreId(cmdReq.getStoreid());
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    // Long numId = Long.parseLong(cmdReq.getParams().get("numid"));// 商品ID
    // 由于：更新产品库存失败(产品必须为普通产品或子品)，所以这里一号店的productId取skuid
    Long productId = Long.parseLong(cmdReq.getParams().get("skuid"));// 商品SKUID add by mowj 20150909
    String outerId = cmdReq.getParams().get("outerid");// 產品料號
    int virtualStockNum = Integer.parseInt(cmdReq.getParams().get("num"));// 數量

    // 調用API
//    YhdClient yhd = new YhdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API),
//        // authData.getAomsshop004(), authData.getAomsshop005()
//        esVerification.getAppKey(), esVerification.getAppSecret());
//    ProductStockUpdateRequest request = new ProductStockUpdateRequest();
//    request.setProductId(productId);
//    request.setOuterId(outerId);
//    request.setVirtualStockNum(virtualStockNum);
//    request.setUpdateType(1);// 預設全量更新
//    ProductStockUpdateResponse response = yhd.excute(request,
//        // authData.getAomsshop006()
//        esVerification.getAccessToken());

    ProductStockUpdateResponse response = yhdProductStockUpdate(
        esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAccessToken(),
        productId, outerId, virtualStockNum, null, InventoryUpdateType.FULL.getValue());

    boolean success = (response.getErrInfoList() == null);
    updateGoodsAfterCmd(cmdReq, success); // add by mowj 20151223
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
                response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
        null);

  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    // 取得授權
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    Long productId = Long.parseLong(cmdReq.getParams().get("numid"));// 商品ID

    // 調用API
//    YhdClient yhd = new YhdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API),
//        // authData.getAomsshop004(), authData.getAomsshop005()
//        esVerification.getAppKey(), esVerification.getAppSecret());
//    ProductShelvesupUpdateRequest request = new ProductShelvesupUpdateRequest();
//    request.setProductId(productId);
//    ProductShelvesupUpdateResponse response = yhd.excute(request,
//        // authData.getAomsshop006()
//        esVerification.getAccessToken());

    ProductShelvesupUpdateResponse response = yhdProductShelvesupUpdate(
        esVerification.getAppKey(), esVerification.getAppSecret(), 
        esVerification.getAccessToken(), productId, null);
    
    boolean success = (response.getErrInfoList() == null);
    updateGoodsAfterCmd(cmdReq, success); // add by mowj 20151223
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
                response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
        null);
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
    // 取得授權
    // AomsshopT authData = getAuthorizationByStoreId(cmdReq.getStoreid());
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得參數
    Long productId = Long.parseLong(cmdReq.getParams().get("numid"));// 商品ID

    // 調用API
//    YhdClient yhd = new YhdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API),
//        // authData.getAomsshop004(), authData.getAomsshop005()
//        esVerification.getAppKey(), esVerification.getAppSecret());
//    ProductShelvesdownUpdateRequest request = new ProductShelvesdownUpdateRequest();
//    request.setProductId(productId);
//    ProductShelvesdownUpdateResponse response = yhd.excute(request,
//        // authData.getAomsshop006()
//        esVerification.getAccessToken());

    ProductShelvesdownUpdateResponse response = yhdProductShelvesdownUpdate(
        esVerification.getAppKey(), esVerification.getAppSecret(), 
        esVerification.getAccessToken(), productId, null);
    
    boolean success = (response.getErrInfoList() == null);
    updateGoodsAfterCmd(cmdReq, success); // add by mowj 20151223
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getErrInfoList().getErrDetailInfo().get(0).getErrorCode(),
                response.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()),
        null);
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

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String numId = cmdReq.getParams().get("numid");
    String skuIds = cmdReq.getParams().get("skuids");
    String outerIds = cmdReq.getParams().get("outerids");
    String nums = cmdReq.getParams().get("nums");

    /*
     * mark原因见TaobaoApiServiceImpl类中的同名方法 if (skuIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) <= 0 ||
     * outerIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) <= 0 ||
     * nums.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) <= 0 ||
     * skuIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) == skuIds.length() - 1 ||
     * outerIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) == outerIds.length() - 1 ||
     * nums.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) == nums.length() - 1) { return new
     * CmdRes(cmdReq, false, new ResponseError("039", ErrorMessageBox._039), null); }
     */
    String[] skuIdArray = skuIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
    String[] outerIdArray = outerIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
    String[] numArray = nums.split(CMD_INVENTORY_UPDATE_DELIMITER);

    int skuIdCount = skuIdArray.length;
    int outerIdCount = outerIdArray.length;
    int numCount = numArray.length;

    if (skuIdCount != outerIdCount || skuIdCount != numCount || outerIdCount != numCount) {
      return new CmdRes(cmdReq, false, new ResponseError("039", ErrorMessageBox._039), null);
    }

    StringBuilder listStringBuilder = new StringBuilder();
    String productStockList = "";
    String outerStockList = "";

    // 获取店铺的默认仓库
    LogisticsWarehouseInfoGetResponse warehouseInfoGetResponse =
        yhdLogisticsWarehouseInfoGet(esVerification);
    if (warehouseInfoGetResponse == null || warehouseInfoGetResponse.getErrorCount() != 0) {
      return new CmdRes(cmdReq, false, new ResponseError("035", ErrorMessageBox._035), null);
    }
    Long defaultWareId = null;

    for (Warehouse warehouse : warehouseInfoGetResponse.getWarehouseList().getWarehouse()) {
      if (warehouse.getIsDefaultWarehouse() == YhdCommonTool.DEFAULT_WARE_HOUSE_CODE) {
        defaultWareId = warehouse.getWarehouseId();
      }
    }

    // 准备productStockList
    for (int i = 0; i < skuIdCount; i++) {
      listStringBuilder.append(skuIdArray[i]).append(YhdCommonTool.STOCK_UPDATE_INNER_DELIMITER)
          .append(defaultWareId).append(YhdCommonTool.STOCK_UPDATE_INNER_DELIMITER)
          .append(numArray[i]);
      if (i + 1 == skuIdCount) {

      } else {
        listStringBuilder.append(YhdCommonTool.STOCK_UPDATE_OUTER_DELIMITER);
      }
    }
    productStockList = listStringBuilder.toString();

    // 清除StringBuffer
    listStringBuilder.delete(0, listStringBuilder.length());

    // 准备outerStockList
    for (int i = 0; i < skuIdCount; i++) {
      listStringBuilder.append(outerIdArray[i]).append(YhdCommonTool.STOCK_UPDATE_INNER_DELIMITER)
          .append(defaultWareId).append(YhdCommonTool.STOCK_UPDATE_INNER_DELIMITER)
          .append(numArray[i]);
      if (i + 1 == skuIdCount) {

      } else {
        listStringBuilder.append(YhdCommonTool.STOCK_UPDATE_OUTER_DELIMITER);
      }
    }
    outerStockList = listStringBuilder.toString();

    listStringBuilder.delete(0, listStringBuilder.length());

    Long productId = numId.equals("") ? null : Long.parseLong(numId);

    ProductsStockUpdateResponse productsStockUpdateResponse =
        yhdProductsStockUpdate(
            esVerification.getAppKey(), esVerification.getAppSecret(), 
            esVerification.getAccessToken(), InventoryUpdateType.FULL.getValue(), productStockList, outerStockList);

    // mark by mowj 20160726 合并else条件
//    if (productsStockUpdateResponse.getErrInfoList() != null) {
//      SerialProductsStockUpdateResponse serialProductsStockUpdateResponse =
//          yhdSerialProductsStockUpdate(
//              esVerification.getAppKey(), esVerification.getAppSecret(), 
//              esVerification.getAccessToken(), productId, null, InventoryUpdateType.FULL.getValue(), 
//              productStockList, outerStockList);
//      if (serialProductsStockUpdateResponse.getErrInfoList() != null) {
//        return new CmdRes(cmdReq, false, new ResponseError(
//            productsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0).getErrorCode()
//                + "+"
//                + serialProductsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0)
//                    .getErrorCode(),
//            productsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()
//                + "+" + serialProductsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0)
//                    .getErrorDes()),
//            null);
//      } else {
//        updateGoodsAfterCmd(cmdReq, true); // add by mowj 20151223
//        return new CmdRes(cmdReq, true, null, null);
//      }
//    } else {
//      updateGoodsAfterCmd(cmdReq, true); // add by mowj 20151223
//      return new CmdRes(cmdReq, true, null, null);
//    }
    if (productsStockUpdateResponse.getErrInfoList() != null) {
      SerialProductsStockUpdateResponse serialProductsStockUpdateResponse =
          yhdSerialProductsStockUpdate(
              esVerification.getAppKey(), esVerification.getAppSecret(), 
              esVerification.getAccessToken(), productId, null, InventoryUpdateType.FULL.getValue(), 
              productStockList, outerStockList);
      if (serialProductsStockUpdateResponse.getErrInfoList() != null) {
        return new CmdRes(cmdReq, false, new ResponseError(
            productsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0).getErrorCode()
                + "+"
                + serialProductsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0)
                    .getErrorCode(),
            productsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0).getErrorDes()
                + "+" + serialProductsStockUpdateResponse.getErrInfoList().getErrDetailInfo().get(0)
                    .getErrorDes()),
            null);
      }
    }
    updateGoodsAfterCmd(cmdReq, true); // add by mowj 20151223
    return new CmdRes(cmdReq, true, null, null);
  }

  @Override
  public OrdersGetResponse yhdOrdersGet(String appKey, String appSecret, String accessToken,
      String orderStatusList, Integer dateType, String startTime, String endTime, Integer curPage,
      Integer pageRows) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    OrdersGetRequest request = new OrdersGetRequest();
//    request.setOrderStatusList(YhdCommonTool.ORDER_STATUS);
    request.setOrderStatusList(orderStatusList);
    if (StringTool.isNotEmpty(dateType)) {
      request.setDateType(dateType);
    }
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    if (StringTool.isNotEmpty(curPage)) {
      request.setCurPage(curPage);
    }
    if (StringTool.isNotEmpty(pageRows)) {
      request.setPageRows(pageRows);
    }
    
    OrdersGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public OrdersDetailGetResponse yhdOrdersDetailGet(String appKey, String appSecret, String accessToken,
      String orderCodeList) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    OrdersDetailGetRequest request = new OrdersDetailGetRequest();
    request.setOrderCodeList(orderCodeList);
    
    OrdersDetailGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public OrderDetailGetResponse yhdOrderDetailGet(String appKey, String appSecret,
      String accessToken, String orderCode) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    OrderDetailGetRequest request = new OrderDetailGetRequest();
    request.setOrderCode(orderCode);

    OrderDetailGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public RefundGetResponse yhdRefundGet(String appKey, String appSecret, String accessToken,
      String orderCode, Long productId, Integer refundStatus, 
      String startTime, String endTime, Integer curPage, Integer pageRows, 
      Integer dateType, Integer operateType) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    RefundGetRequest request = new RefundGetRequest();
    if (StringTool.isNotEmpty(orderCode)) {
      request.setOrderCode(orderCode);
    }
    if (StringTool.isNotEmpty(productId)) {
      request.setProductId(productId);
    }
    if (StringTool.isNotEmpty(refundStatus)) {
      request.setRefundStatus(refundStatus);
    }
    request.setStartTime(startTime);
    request.setEndTime(endTime);
    if (StringTool.isNotEmpty(curPage)) {
      request.setCurPage(curPage);
    }
    if (StringTool.isNotEmpty(pageRows)) {
      request.setPageRows(pageRows);
    }
//    request.setDateType(2);
    request.setDateType(dateType);
    if (StringTool.isNotEmpty(operateType)) {
      request.setOperateType(operateType);
    }
    
    RefundGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public RefundDetailGetResponse yhdRefundDetailGet(String appKey, String appSecret, String accessToken,
      String refundCode) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    RefundDetailGetRequest request = new RefundDetailGetRequest();
    request.setRefundCode(refundCode);
    
    RefundDetailGetResponse response = client.excute(request);

    return response;
  }

  @Override
  public OrdersRefundAbnormalGetResponse yhdOrdersRefundAbnormalGet(String appKey, String appSecret, String accessToken,
      String refundOrderCode, String orderCode, String refundCode, String refundStatus,
      String receiverPhone, 
      String startTime, String endTime,
      Integer dateType, 
      Integer curPage, Integer pageRows) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    OrdersRefundAbnormalGetRequest request = new OrdersRefundAbnormalGetRequest();
    if (StringTool.isNotEmpty(refundOrderCode)) {
      request.setRefundOrderCode(refundOrderCode);
    }
    if (StringTool.isNotEmpty(orderCode)) {
      request.setOrderCode(orderCode);
    }
    if (StringTool.isNotEmpty(refundCode)) {
      request.setRefundCode(refundCode);
    }
    if (StringTool.isNotEmpty(refundStatus)) {
      request.setRefundStatus(refundStatus);
    }
    if (StringTool.isNotEmpty(receiverPhone)) {
      request.setReceiverPhone(receiverPhone);
    }
    request.setStartTime(startTime);
    request.setEndTime(endTime);
//    request.setDateType(1);
    request.setDateType(dateType);
    if (StringTool.isNotEmpty(curPage)) {
      request.setCurPage(curPage);
    }
    if (StringTool.isNotEmpty(pageRows)) {
      request.setPageRows(pageRows);
    }
    
    OrdersRefundAbnormalGetResponse response = client.excute(request);

    return response;
  }

  @Override
  public GeneralProductsSearchResponse yhdGeneralProductsSearch(String appKey, String appSecret, String accessToken,
      Integer canShow, Integer canSale, Integer curPage, Integer pageRows,
      String productCname, String productIdList, String outerIdList, 
      Integer verifyFlg, Long categoryId, Integer categoryType, Long brandId,
      String productCodeList, String updateStartTime, String updateEndTime) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    GeneralProductsSearchRequest request = new GeneralProductsSearchRequest();
    
//    request.setProductIdList(numId);
    if (StringTool.isNotEmpty(canShow)) {
      request.setCanShow(canShow);
    }
    if (StringTool.isNotEmpty(canSale)) {
      request.setCanSale(canSale);
    }
    if (StringTool.isNotEmpty(curPage)) {
      request.setCurPage(curPage);
    }
    if (StringTool.isNotEmpty(pageRows)) {
      request.setPageRows(pageRows);
    }
    if (StringTool.isNotEmpty(productCname)) {
      request.setProductCname(productCname);
    }
    if (StringTool.isNotEmpty(productIdList)) {
      request.setProductIdList(productIdList);
    }
    if (StringTool.isNotEmpty(outerIdList)) {
      request.setOuterIdList(outerIdList);
    }
    if (StringTool.isNotEmpty(verifyFlg)) {
      request.setVerifyFlg(verifyFlg);
    }
    if (StringTool.isNotEmpty(categoryId)) {
      request.setCategoryId(categoryId);
    }
    if (StringTool.isNotEmpty(categoryType)) {
      request.setCategoryType(categoryType);
    }
    if (StringTool.isNotEmpty(brandId)) {
      request.setBrandId(brandId);
    }
    if (StringTool.isNotEmpty(productCodeList)) {
      request.setProductCodeList(productCodeList);
    }
    if (StringTool.isNotEmpty(updateStartTime)) {
      request.setUpdateStartTime(updateStartTime);
    }
    if (StringTool.isNotEmpty(updateEndTime)) {
      request.setUpdateEndTime(updateEndTime);
    }
    
    GeneralProductsSearchResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public SerialProductsSearchResponse yhdSerialProductsSearch(String appKey, String appSecret, String accessToken, 
      Integer canShow, Integer canSale, Integer curPage, Integer pageRows,
      String productCname, String productIdList, String outerIdList, 
      Integer verifyFlg, Long categoryId, Integer categoryType, Long brandId,
      String productCodeList, String updateStartTime, String updateEndTime) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    SerialProductsSearchRequest request = new SerialProductsSearchRequest();
    
    if (StringTool.isNotEmpty(canShow)) {
      request.setCanShow(canShow);
    }
    if (StringTool.isNotEmpty(canSale)) {
      request.setCanSale(canSale);
    }
    if (curPage != null) {
      request.setCurPage(curPage);
    }
    if (pageRows != null) {
      request.setPageRows(pageRows);
    }
    if (StringTool.isNotEmpty(productCname)) {
      request.setProductCname(productCname);
    }
    if (StringTool.isNotEmpty(productIdList)) {
      request.setProductIdList(productIdList);
    }
    if (StringTool.isNotEmpty(outerIdList)) {
      request.setOuterIdList(outerIdList);
    }
    if (StringTool.isNotEmpty(verifyFlg)) {
      request.setVerifyFlg(verifyFlg);
    }
    if (StringTool.isNotEmpty(categoryId)) {
      request.setCategoryId(categoryId);
    }
    if (StringTool.isNotEmpty(categoryType)) {
      request.setCategoryType(categoryType);
    }
    if (StringTool.isNotEmpty(brandId)) {
      request.setBrandId(brandId);
    }
    if (StringTool.isNotEmpty(productCodeList)) {
      request.setProductCodeList(productCodeList);
    }
    if (StringTool.isNotEmpty(updateStartTime)) {
      request.setUpdateStartTime(updateStartTime);
    }
    if (StringTool.isNotEmpty(updateEndTime)) {
      request.setUpdateEndTime(updateEndTime);
    }
    
    SerialProductsSearchResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public SerialProductGetResponse yhdSerialProductGet(String appKey, String appSecret, String accessToken,
      Long productId, String outerId, Long brandId, String productCode) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    SerialProductGetRequest request = new SerialProductGetRequest();
    if (StringTool.isNotEmpty(productId)) {
      request.setProductId(productId);
    }
    if (StringTool.isNotEmpty(outerId)) {
      request.setOuterId(outerId);
    }
    if (StringTool.isNotEmpty(brandId)) {
      request.setBrandId(brandId);
    }
    if (StringTool.isNotEmpty(productCode)) {
      request.setProductCode(productCode);
    }
    
    SerialProductGetResponse response = client.excute(request);

    return response;
  }

  @Override
  public SerialProductAttributeGetResponse yhdSerialProductAttributeGet(String appKey, String appSecret, String accessToken,
      Long productId, String outerId) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    SerialProductAttributeGetRequest request = new SerialProductAttributeGetRequest();
    if (StringTool.isNotEmpty(productId)) {
      request.setProductId(productId);
    }
    if (StringTool.isNotEmpty(outerId)) {
      request.setOuterId(outerId);
    }
    SerialProductAttributeGetResponse attributeGetResponse = client.excute(request);
    ErrDetailInfoList errDetailInfoList = attributeGetResponse.getErrInfoList();
  
    if (errDetailInfoList != null && errDetailInfoList.getErrDetailInfo() != null
        && errDetailInfoList.getErrDetailInfo().get(0) != null) {
      if ("yhd.visit.error.min_pre_visit_over"
          .equals(attributeGetResponse.getErrInfoList().getErrDetailInfo().get(0).getErrorCode())) {
        try {
          Thread.sleep(1100);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        return yhdSerialProductAttributeGet(appKey, appSecret, accessToken, productId, outerId);
      } else {
        return attributeGetResponse;
      }
    } else {
      return attributeGetResponse;
    }
  }

  @Override
  public ProductsPriceGetResponse yhdProductsPriceGet(String appKey, String appSecret,
      String accessToken, String productIdList, String outerIdList) {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    ProductsPriceGetRequest request = new ProductsPriceGetRequest();
    if (StringTool.isNotEmpty(productIdList)) {
      request.setProductIdList(productIdList);
    }
    if (StringTool.isNotEmpty(outerIdList)) {
      request.setOuterIdList(outerIdList);
    }
    
    ProductsPriceGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public ProductsStockGetResponse yhdProductsStockGet(String appKey, String appSecret,
      String accessToken, String productIdList, String outerIdList, Long wareHouseId) {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    ProductsStockGetRequest request = new ProductsStockGetRequest();
    if (StringTool.isNotEmpty(productIdList)) {
      request.setProductIdList(productIdList);
    }
    if (StringTool.isNotEmpty(outerIdList)) {
      request.setOuterIdList(outerIdList);
    }
    if (StringTool.isNotEmpty(wareHouseId)) {
      request.setWarehouseId(wareHouseId);
    }
    
    ProductsStockGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public ProductStockUpdateResponse yhdProductStockUpdate(String appKey, String appSecret,
      String accessToken, Long productId, String outerId, Integer virtualStockNum, Long warehouseId,
      Integer updateType) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    ProductStockUpdateRequest request = new ProductStockUpdateRequest();
    if (StringTool.isNotEmpty(productId)) {
      request.setProductId(productId);
    }
    if (StringTool.isNotEmpty(outerId)) {
      request.setOuterId(outerId);
    }
    request.setVirtualStockNum(virtualStockNum);
    if (StringTool.isNotEmpty(warehouseId)) {
      request.setWarehouseId(warehouseId);
    }
    if (StringTool.isNotEmpty(updateType)) {
      request.setUpdateType(updateType);
    }
    
    ProductStockUpdateResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public ProductsStockUpdateResponse yhdProductsStockUpdate(String appKey, String appSecret, String accessToken,
      Integer updateType, String productStockList, String outerStockList) {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    ProductsStockUpdateRequest request = new ProductsStockUpdateRequest();
    
//    request.setUpdateType(YhdCommonTool.StockUpdateType.FULL.getStockUpdateType());
    if (StringTool.isNotEmpty(updateType)) {
      request.setUpdateType(updateType);
    }
    if (StringTool.isNotEmpty(productStockList)) {
      request.setProductStockList(productStockList);
    }
    if (StringTool.isNotEmpty(outerStockList)) {
      request.setOuterStockList(outerStockList);
    }

    ProductsStockUpdateResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public SerialProductsStockUpdateResponse yhdSerialProductsStockUpdate(
      String appKey, String appSecret, String accessToken, 
      Long productId, String outerId, Integer updateType, String productStockList, String outerStockList) 
          throws Exception{
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    SerialProductsStockUpdateRequest request = new SerialProductsStockUpdateRequest();
    if (StringTool.isNotEmpty(productId) && productId != 0L) {
      request.setProductId(productId);
    }
    if (StringTool.isNotEmpty(outerId)) {
      request.setOuterId(outerId);
    }
//    request.setUpdateType(YhdCommonTool.StockUpdateType.FULL.getStockUpdateType());
    if (StringTool.isNotEmpty(updateType)) {
      request.setUpdateType(updateType);
    }
    if (StringTool.isNotEmpty(productStockList)) {
      request.setProductStockList(productStockList);
    }
    if (StringTool.isNotEmpty(outerStockList)) {
      request.setOuterStockList(outerStockList);
    }

    SerialProductsStockUpdateResponse response = client.excute(request);
    
    return response;
  }

  private LogisticsWarehouseInfoGetResponse yhdLogisticsWarehouseInfoGet(
      ESVerification esVerification) {
    YhdClient yhdClient = new YhdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API),
        esVerification.getAppKey(), esVerification.getAppSecret());
    LogisticsWarehouseInfoGetRequest request = new LogisticsWarehouseInfoGetRequest();
    LogisticsWarehouseInfoGetResponse response =
        yhdClient.excute(request, esVerification.getAccessToken());

    return response;
  }

  @Override
  public SerialChildproductsGetResponse yhdSerialChildProductsGet(String appKey, String appSecret,
      String accessToken, String productIdList, String outerIdList, String productCodeList) 
          throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    SerialChildproductsGetRequest request = new SerialChildproductsGetRequest();
//    request.setProductIdList(productIdList);
    if (StringTool.isNotEmpty(productIdList)) {
      request.setProductIdList(productIdList);
    }
    if (StringTool.isNotEmpty(outerIdList)) {
      request.setOuterIdList(outerIdList);
    }
    if (StringTool.isNotEmpty(productCodeList)) {
      request.setProductCodeList(productCodeList);
    }
    
    SerialChildproductsGetResponse response = client.excute(request);
    
    return response;
  }

  @Override
  public LogisticsOrderShipmentsUpdateResponse yhdLogisticsOrderShipmentsUpdate(String appKey,
      String appSecret, String accessToken, String orderCode, Long deliverySupplierId,
      String expressNbr, String cartonList, String deliveryItemNumberList) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    LogisticsOrderShipmentsUpdateRequest request = new LogisticsOrderShipmentsUpdateRequest();
    request.setOrderCode(orderCode);
    request.setDeliverySupplierId(deliverySupplierId);
    request.setExpressNbr(expressNbr);
    if (StringTool.isNotEmpty(cartonList)) {
      request.setCartonList(cartonList);
    }
    if (StringTool.isNotEmpty(deliveryItemNumberList)) {
      request.setDeliveryItemNumberList(deliveryItemNumberList);
    }
    
    LogisticsOrderShipmentsUpdateResponse response = client.excute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.yhd.service.api.YhdApiService#yhdProductShelvesupUpdate(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
   */
  @Override
  public ProductShelvesupUpdateResponse yhdProductShelvesupUpdate(String appKey, String appSecret,
      String accessToken, Long productId, String outerId) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    ProductShelvesupUpdateRequest request = new ProductShelvesupUpdateRequest();
    if (StringTool.isNotEmpty(productId)) {
      request.setProductId(productId);
    }
    if (StringTool.isNotEmpty(outerId)) {
      request.setOuterId(outerId);
    }
    
    ProductShelvesupUpdateResponse response = client.excute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.yhd.service.api.YhdApiService#yhdProductShelvesdownUpdate(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
   */
  @Override
  public ProductShelvesdownUpdateResponse yhdProductShelvesdownUpdate(String appKey,
      String appSecret, String accessToken, Long productId, String outerId) throws Exception {
    YhdClient client = YhdClientUtil.getInstance().getYhdClient(
        paramSystemService.getEcApiUrl(EcApiUrlEnum.YHD_API), appKey, appSecret, accessToken);
    ProductShelvesdownUpdateRequest request = new ProductShelvesdownUpdateRequest();
    if (StringTool.isNotEmpty(productId)) {
      request.setProductId(productId);
    }
    if (StringTool.isNotEmpty(outerId)) {
      request.setOuterId(outerId);
    }
    
    ProductShelvesdownUpdateResponse response = client.excute(request);
    
    return response;
  }

  /**
   * 從退貨詳情中取得最接近現在的時間
   * 
   * @param refundDetail 退貨詳情
   * @return
   */
  private Date findMaxDate(RefundDetailGetResponse refundDetail) {
    RefundDetail detail = refundDetail.getRefundInfoMsg().getRefundDetail();
    List<Date> dates = new ArrayList<Date>();
    dates.add((detail.getApplyDate() == null || detail.getApplyDate().equals("")) ? null
        : DateTimeTool.parse(detail.getApplyDate()));
    dates.add((detail.getApproveDate() == null || detail.getApproveDate().equals("")) ? null
        : DateTimeTool.parse(detail.getApproveDate()));
    dates.add((detail.getSendBackDate() == null || detail.getSendBackDate().equals("")) ? null
        : DateTimeTool.parse(detail.getSendBackDate()));
    dates.add((detail.getRejectDate() == null || detail.getRejectDate().equals("")) ? null
        : DateTimeTool.parse(detail.getRejectDate()));
    dates.add((detail.getCancelTime() == null || detail.getCancelTime().equals("")) ? null
        : DateTimeTool.parse(detail.getCancelTime()));

    Date maxDate = dates.get(0);// 用申請時間當作起始時間
    for (int i = 1; i < dates.size(); i++) {
      if (dates.get(i) != null && dates.get(i).after(maxDate)) {
        maxDate = dates.get(i);
      }
    }

    return maxDate;
  }

  // /*
  // * add by mowj 20151222
  // * mark by mowj 20151223 不使用。
  // */
  // @Override
  // public void syncSerialGoodsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
  // throws JSONException, Exception {
  // /*
  // * 水星的系列商品为主要的部分，所以需要及时更新。
  // * 然而一号店的API虽然支持按更新时间查询，但是返回的结果却有点不同。
  // * 举例，5分钟前某一商品被下架了，或者库存有异动，但是5分钟后去查，此商品并不在返回结果中。
  // * 所以，此段的逻辑需要改为如下才能满足需求：
  // * 1. 排程启动时，调用API更新中台已有的铺货资料信息；(已经完成)
  // * 2. 一号店单笔下载铺货指令使用之后，需要另外存到中台的DB
  // */
  // // 取得區間內總資料筆數
  // int pageSize = taskScheduleConfig.getMaxPageSize();
  // String endDate = taskScheduleConfig.getEndDate();
  //
  // List<String> itemIdsList = yhdApiHelperService.getItemIdsList(aomsshopT.getAomsshop001(),
  // true);
  //
  // // 如果数据库中没有数据，则进行初始化
  // if (itemIdsList.size() == 0) {
  // syncInitSerialGoodsData(taskScheduleConfig, aomsshopT);
  // return;
  // }
  // // 如果有，则多个ID为一组，使用逗号分隔
  // List<String> itemIdsStringList = new ArrayList<String>();
  // int itemIdStringMaxLength = pageSize <= YhdCommonTool.MAX_PRODUCT_CODE_LENGTH ? pageSize :
  // YhdCommonTool.MAX_PRODUCT_CODE_LENGTH;
  // StringBuffer tempSbuffer = new StringBuffer();
  //
  // for (int i = 0; i < itemIdsList.size(); i++) {
  // String itemId = itemIdsList.get(i);
  // if ((i + 1) % itemIdStringMaxLength == 0) {
  // tempSbuffer.append(itemId);
  //
  // itemIdsStringList.add(tempSbuffer.toString());
  // tempSbuffer.setLength(0);
  //
  // } else {
  // tempSbuffer.append(itemId).append(YhdCommonTool.LIST_DELIMITER);
  // }
  // }
  // if (itemIdsList.size() % itemIdStringMaxLength != 0) {
  // String lastItemIdList = tempSbuffer.toString();
  // itemIdsStringList.add(lastItemIdList.substring(0, lastItemIdList.length() - 1));
  // }
  //
  // int totalSize = yhdSerialProductsSearch(aomsshopT, "", "", "", 1, 1).getTotalCount();
  // if (totalSize == 0) {
  // taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), endDate);
  // return;
  // }
  //
  // for (int i = 0; i < itemIdsStringList.size(); i++) {
  // String itemIdsString = itemIdsStringList.get(i);
  //
  // List<Object> aomsitemTs = new ArrayList<Object>();
  // SerialProductsSearchResponse response = yhdSerialProductsSearch(aomsshopT, itemIdsString, "",
  // "", i, pageSize);
  //
  // loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
  // "yhd.serial.products.search 查询系列产品信息", CommonUtil.objectToJsonString(response),
  // SourceLogBizTypeEnum.AOMSITEMT.getValueString()
  // , aomsshopT.getAomsshop001()
  // , taskScheduleConfig.getScheduleType()
  // );
  //
  // // 針對每一筆系列產品..
  // for (SerialProduct serialProduct : response.getSerialProductList().getSerialProduct()) {
  // // 取得該系列產品的所有子品
  // SerialProductGetResponse childProds = yhdSerialProductGet(aomsshopT,
  // serialProduct.getProductId());
  //
  // loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
  // "yhd.serial.product.get 获取单个系列产品的子品信息", CommonUtil.objectToJsonString(childProds),
  // SourceLogBizTypeEnum.AOMSITEMT.getValueString()
  // , aomsshopT.getAomsshop001()
  // , taskScheduleConfig.getScheduleType()
  // );
  //
  // // 取得所有子品中的屬性
  // SerialProductAttributeGetResponse childProdsAttr = yhdSerialProductAttributeGet(aomsshopT,
  // serialProduct.getProductId());
  //
  // loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
  // "yhd.serial.product.attribute.get 获取商品系列属性", CommonUtil.objectToJsonString(childProdsAttr),
  // SourceLogBizTypeEnum.AOMSITEMT.getValueString()
  // , aomsshopT.getAomsshop001()
  // , taskScheduleConfig.getScheduleType()
  // );
  //
  // // 商品价格
  // String serialChildProdIdString = "";
  // for (SerialChildProd scProd : childProds.getSerialChildProdList().getSerialChildProd()) {
  // serialChildProdIdString += scProd.getProductId() + YhdCommonTool.LIST_DELIMITER;
  // }
  // serialChildProdIdString = serialChildProdIdString.substring(0, serialChildProdIdString.length()
  // - 1); // modi by mowj 20150824 length()-2->length()-1
  //
  // ProductsPriceGetResponse productsPriceGetResponse = yhdProductsPriceGet(aomsshopT,
  // serialChildProdIdString, "");
  //
  // loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
  // "yhd.products.price.get 批量获取产品价格信息", CommonUtil.objectToJsonString(productsPriceGetResponse),
  // SourceLogBizTypeEnum.AOMSITEMT.getValueString()
  // , aomsshopT.getAomsshop001()
  // , taskScheduleConfig.getScheduleType()
  // );
  //
  // // 商品库存
  // ProductsStockGetResponse productsStockGetResponse = yhdProductsStockGet(aomsshopT,
  // serialChildProdIdString, "", 0l);
  // loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", YhdCommonTool.STORE_TYPE,
  // "yhd.products.stock.get 批量获取产品库存信息", CommonUtil.objectToJsonString(productsStockGetResponse),
  // SourceLogBizTypeEnum.AOMSITEMT.getValueString()
  // , aomsshopT.getAomsshop001()
  // , taskScheduleConfig.getScheduleType()
  // );
  //
  // List<AomsitemT> list = new YhdAomsitemTTranslator(serialProduct,
  // aomsshopT.getAomsshop001()).doTranslate(
  // childProds, childProdsAttr,
  // productsPriceGetResponse,
  // productsStockGetResponse);
  // aomsitemTs.addAll(list);
  // }
  // taskService.newTransaction4Save(aomsitemTs);
  // }
  // taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), endDate);
  // }

  /**
   * 在铺货相关的指令执行成功后，获取对应铺货的最新信息
   * 
   * @param cmdReq
   * @param isCmdSuccess
   * @author 维杰
   * @throws Exception 
   */
  private void updateGoodsAfterCmd(CmdReq cmdReq, boolean isCmdSuccess) throws Exception {
    /*
     * add by mowj 20151223 由于一号店铺货API原因，只能在OMS每次调用商品相关接口之后重新获取最新的资料，保持及时
     */
    if (isCmdSuccess) {
      CmdReq cmdReqItem = new CmdReq();
      cmdReqItem.setEcno(YhdCommonTool.STORE_TYPE);
      cmdReqItem.setApi(EcImsApiService.EcImsApiEnum.DIGIWIN_ITEM_DETAIL_GET.toString());
      cmdReqItem.setStoreid(cmdReq.getStoreid());
      HashMap<String, String> params = new HashMap<String, String>();
      params.put("numid", cmdReq.getParams().get("numid"));
      params.put("isfx", "0");
      cmdReqItem.setParams(params);

      CmdRes cmdResItem = digiwinItemDetailGet(cmdReqItem);
      if (cmdResItem.getReturnValue() != null) {
        taskService.newTransaction4Save(cmdResItem.getReturnValue());
      }
      cmdResItem = null;
    }
  }
}
