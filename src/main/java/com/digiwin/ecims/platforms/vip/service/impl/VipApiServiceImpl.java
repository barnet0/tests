package com.digiwin.ecims.platforms.vip.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vip.osp.sdk.context.InvocationContext;
import com.vip.osp.sdk.exception.OspException;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.UseTimeEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;
import com.digiwin.ecims.platforms.vip.bean.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.vip.bean.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.vip.bean.translator.AomsrefundTTranslator;
import com.digiwin.ecims.platforms.vip.service.VipApiHelperService;
import com.digiwin.ecims.platforms.vip.service.VipApiService;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool;
import com.digiwin.ecims.platforms.vip.util.VipCommonTool.VipApiEnum;
import vipapis.common.Warehouse;
import vipapis.delivery.CreateDeliveryResponse;
import vipapis.delivery.Delivery;
import vipapis.delivery.GetPickListResponse;
import vipapis.delivery.GetPoListResponse;
import vipapis.delivery.GetPoSkuListResponse;
import vipapis.delivery.GetPrintDeliveryResponse;
import vipapis.delivery.JitDeliveryServiceHelper.JitDeliveryServiceClient;
import vipapis.delivery.PickDetail;
import vipapis.delivery.PurchaseOrder;
import vipapis.delivery.SimplePick;
import vipapis.product.MultiGetProductSkuResponse;
import vipapis.product.MultiGetProductSpuResponse;
import vipapis.product.ProductSkuInfo;
import vipapis.product.ProductSpuInfo;
import vipapis.product.ProductStatus;
import vipapis.product.VendorProductServiceHelper.VendorProductServiceClient;
import vipapis.stock.GetVendorScheduleFreezeStock;
import vipapis.stock.GetVendorScheduleFreezeStockResult;
import vipapis.stock.StockServiceHelper.StockServiceClient;
import vipapis.stock.UpdateWarehouseInventory;
import vipapis.stock.UpdateWarehouseInventoryResponse;
import vipapis.vreturn.GetReturnDetailResponse;
import vipapis.vreturn.GetReturnInfoResponse;
import vipapis.vreturn.ReturnInfo;
import vipapis.vreturn.VendorReturnServiceHelper.VendorReturnServiceClient;

@Service
public class VipApiServiceImpl implements VipApiService {

  private static final Logger logger = LoggerFactory.getLogger(VipApiServiceImpl.class);

  @Autowired
  private TaskService taskService;

  @Autowired
  private AomsShopService aomsShopService;

  @Autowired
  private ParamSystemService paramSystemService;

  @Autowired
  private LoginfoOperateService loginfoOperateService;

  @Autowired
  private VipApiHelperService vipApiHelperService;

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
    } else if (api.equals(VipApiEnum.DIGIWIN_VIP_JIT_SHIPPING_SEND.toString())) { // JIT模式发货
      return digiwinVipJitShippingSend(cmdReq);
    } else {
      return new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
    }
  }

  @Override
  public CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception {
    String storeId = cmdReq.getStoreid();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(storeId);
    AomsshopT aomsshopT = aomsShopService.getStoreByStoreId(storeId);

    String orderId = cmdReq.getParams().get("id");
    String vendorId = aomsshopT.getAomsshop012();

    boolean success = false;
    // TODO isExport参数先填0，测试之后调整
    GetPickListResponse pickListResponse = jitDeliveryServiceGetPickList(esVerification.getAppKey(),
        esVerification.getAppSecret(), esVerification.getAccessToken(), vendorId, StringUtils.EMPTY,
        orderId, null, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
        NumberUtils.INTEGER_ZERO, VipCommonTool.MIN_PAGE_NO, VipCommonTool.MIN_PAGE_SIZE,
        StringUtils.EMPTY);

    if (pickListResponse != null && pickListResponse.getPicks().size() > 0) {
      String poNo = pickListResponse.getPicks().get(0).getPo_no();
      PickDetail pickDetail = jitDeliveryServiceGetPickDetail(esVerification.getAppKey(),
          esVerification.getAppSecret(), esVerification.getAccessToken(), poNo, vendorId, orderId,
          VipCommonTool.MIN_PAGE_NO, VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY);

      // 由于API调整，需要从此API才能获取商品的供货价
      List<GetPoSkuListResponse> poSkuListResponses =
          getPoSkuListResponses("", VipCommonTool.DEFAULT_PAGE_SIZE, aomsshopT, poNo, orderId);
      if (pickDetail != null && poSkuListResponses != null) {
        success = true;

        // List<AomsordT> list = new AomsordTTranslator(pickListResponse.getPicks().get(0),
        // pickDetail, poSkuListResponses).doTranslate(cmdReq.getStoreid());
        // taskService.newTransaction4Save(list);

        return new CmdRes(cmdReq, success, null,
            new AomsordTTranslator(pickListResponse.getPicks().get(0), pickDetail,
                poSkuListResponses).doTranslate(cmdReq.getStoreid()));
      }
    } else {

    }

    return new CmdRes(cmdReq, success, new ResponseError(), null);
  }

  /**
   * 获取PO单的SKU列表。（这里为了获取供货价而使用）
   * 
   * @param scheduleType
   * @param pageSize
   * @param aomsshopT
   * @param poNo
   * @param pickNo
   * @return
   * @throws NumberFormatException
   * @throws OspException
   */
  @Override
  public List<GetPoSkuListResponse> getPoSkuListResponses(String scheduleType, int pageSize,
      AomsshopT aomsshopT, String poNo, String pickNo) throws NumberFormatException, OspException {
    List<GetPoSkuListResponse> poSkuLists = new ArrayList<GetPoSkuListResponse>();
    int totalSizePoSku = 0;
    int poSkuPageNum = 0;
    int j = 1;
    do {
      GetPoSkuListResponse poSkuListResponse = jitDeliveryServiceGetPoSkuList(
          aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(),
          aomsshopT.getAomsshop012(), poNo, StringUtils.EMPTY, null, StringUtils.EMPTY,
          StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, pickNo, StringUtils.EMPTY,
          StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, j, pageSize);

      loginfoOperateService.newTransaction4SaveSource("N/A", "N/A", VipCommonTool.STORE_TYPE,
          "vipapis.delivery.JitDeliveryService.getPoSkuList 获取PO单SKU信息",
          JsonUtil.format(poSkuListResponse),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(), scheduleType);
      if (poSkuListResponse != null) {
        totalSizePoSku = poSkuListResponse.getTotal();

        poSkuPageNum = (totalSizePoSku / pageSize) + (totalSizePoSku % pageSize == 0 ? 0 : 1);

        poSkuLists.add(poSkuListResponse);
      } else {
        throw new NullPointerException("获取PO单SKU信息出错.当前po单号为" + poNo);
      }
      j++;
    } while (totalSizePoSku > 0 && j <= poSkuPageNum);

    return poSkuLists;
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
    /*
     * String storeId = cmdReq.getStoreid(); ESVerification esVerification =
     * aomsShopService.getAuthorizationByStoreId(storeId); AomsshopT aomsshopT =
     * aomsShopService.getAomsshopTByStoreId(storeId, VipCommonTool.STORE_TYPE);
     * 
     * String refundId = cmdReq.getParams().get("id"); String vendorId = aomsshopT.getAomsshop012();
     * 
     * GetReturnInfoResponse returnInfoResponse = vendorReturnServiceGetReturnInfo(
     * esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAccessToken(),
     * vendorId, null, refundId, StringUtils.EMPTY, StringUtils.EMPTY, VipCommonTool.MIN_PAGE_NO,
     * VipCommonTool.MIN_PAGE_SIZE);
     * 
     * boolean success = false; if (returnInfoResponse != null &&
     * returnInfoResponse.getReturnInfos().size() > 0) { GetReturnDetailResponse
     * returnDetailResponse = vendorReturnServiceGetReturnDetail( esVerification.getAppKey(),
     * esVerification.getAppSecret(), esVerification.getAccessToken(), vendorId, null, refundId,
     * StringUtils.EMPTY, StringUtils.EMPTY, VipCommonTool.MIN_PAGE_NO,
     * VipCommonTool.MIN_PAGE_SIZE);
     * 
     * if (returnDetailResponse != null && returnInfoResponse.getReturnInfos().size() > 0) { success
     * = true; return new CmdRes(cmdReq, success, null, new
     * AomsrefundTTranslator(returnInfoResponse.getReturnInfos().get(0),
     * returnDetailResponse).doTranslate(cmdReq.getStoreid())); } }
     * 
     * return new CmdRes(cmdReq, success, new ResponseError(), null);
     */
  }


  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
    /*
     * String storeId = cmdReq.getStoreid(); ESVerification esVerification =
     * aomsShopService.getAuthorizationByStoreId(storeId); AomsshopT aomsshopT =
     * aomsShopService.getAomsshopTByStoreId(storeId, VipCommonTool.STORE_TYPE);
     * 
     * String numId = cmdReq.getParams().get("numid"); String vendorId = aomsshopT.getAomsshop012();
     * 
     * MultiGetProductSpuResponse spuResponse = vendorProductServiceMultiGetProductSpuInfo(
     * esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAccessToken(),
     * vendorId, 0, 0, numId, null, VipCommonTool.MIN_PAGE_NO, VipCommonTool.DEFAULT_PAGE_SIZE);
     * 
     * boolean success = false; if (spuResponse != null && spuResponse.getProducts().size() > 0) {
     * MultiGetProductSkuResponse skuResponse = vendorProductServiceMultiGetProductSkuInfo(
     * esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAccessToken(),
     * vendorId, StringUtils.EMPTY, 0, 0, numId, VipCommonTool.MIN_PAGE_NO,
     * VipCommonTool.DEFAULT_PAGE_SIZE); // 锁定库存数量 List<String> nums = new ArrayList<String>(); for
     * (ProductSkuInfo skuInfo : skuResponse.getProducts()) {
     * List<GetVendorScheduleFreezeStockResult> results = stockServiceGetVendorScheduleFreezeStock(
     * esVerification.getAppKey(), esVerification.getAppSecret(), esVerification.getAccessToken(),
     * vendorId, StringUtils.EMPTY, skuInfo.getBarcode(), StringUtils.EMPTY, StringUtils.EMPTY,
     * StringUtils.EMPTY, VipCommonTool.MIN_PAGE_NO, VipCommonTool.DEFAULT_PAGE_SIZE); if
     * (results.size() > 0) { nums.add(results.get(0).getFreeze_num()); } }
     * 
     * if (skuResponse != null) { success = true; return new CmdRes(cmdReq, success, null, new
     * AomsitemTTranslator<String>(spuResponse.getProducts().get(0), skuResponse,
     * nums).doTranslate(storeId)); } }
     * 
     * return new CmdRes(cmdReq, success, new ResponseError(), null);
     */
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
    throw new UnsupportedOperationException("_034");
  }

  @Override
  public CmdRes digiwinVipJitShippingSend(CmdReq cmdReq) throws Exception {
    Map<String, String> params = cmdReq.getParams();
    String storeId = cmdReq.getStoreid();
    ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    AomsshopT aomsshopT = aomsShopService.getStoreByStoreId(storeId);

    String pickNo = params.get("pickno");
    String deliveryNo = params.get("expressno");
    String arrivalTime = params.get("arrivaltime");
    String carrierName = params.get("expcompno");

    List<? extends LinkedHashMap<String, String>> itemList = cmdReq.getItemlist();

    String now = DateTimeTool.getTodayBySecond();

    // 1.创建出仓单
    /*
     * 通过传入的拣货单号，查询对应的拣货单关联的PO单，及PO单下SKU信息，以获取 PO单号与送货仓库
     */

    // TODO isExport参数先填0，测试之后调整
    GetPickListResponse pickListResponse = jitDeliveryServiceGetPickList(esVerification.getAppKey(),
        esVerification.getAppSecret(), esVerification.getAccessToken(), aomsshopT.getAomsshop012(),
        StringUtils.EMPTY, pickNo, null, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
        StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
        StringUtils.EMPTY, NumberUtils.INTEGER_ZERO, VipCommonTool.MIN_PAGE_NO,
        VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY);
    if (pickListResponse != null && pickListResponse.getPicks().size() > 0) {
      String poNo = pickListResponse.getPicks().get(0).getPo_no();
      PickDetail pickDetail =
          jitDeliveryServiceGetPickDetail(esVerification.getAppKey(), esVerification.getAppSecret(),
              esVerification.getAccessToken(), poNo, aomsshopT.getAomsshop012(), pickNo,
              VipCommonTool.MIN_PAGE_NO, VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY);
      String warehouse = pickDetail.getWarehouse();

      CreateDeliveryResponse createDeliveryResponse =
          jitDeliveryServiceCreateDelivery(esVerification.getAppKey(),
              esVerification.getAppSecret(), esVerification.getAccessToken(),
              aomsshopT.getAomsshop012(), poNo, deliveryNo, Warehouse.valueOf(warehouse),
              StringUtils.EMPTY, arrivalTime, StringUtils.EMPTY, carrierName, StringUtils.EMPTY,
              StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, VipCommonTool.MIN_PAGE_NO,
              VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY, StringUtils.EMPTY);
      loginfoOperateService.newTransaction4SaveSource(now, now, VipCommonTool.STORE_TYPE,
          "vipapis.delivery.JitDeliveryService.createDelivery 创建出仓单",
          JsonUtil.format(createDeliveryResponse),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
          "digiwin.vip.jit.shipping.send 唯品会JIT模式发货");
      if (createDeliveryResponse != null) {
        // 出库单Id
        String deliveryId = createDeliveryResponse.getDelivery_id();
        // 入库编号
        String storageNo = createDeliveryResponse.getStorage_no();

        // 2.导入商品明细
        List<Delivery> deliveries = new ArrayList<Delivery>();
        for (LinkedHashMap<String, String> itemMap : itemList) {
          Delivery delivery = new Delivery();
          delivery.setVendor_type(itemMap.get("vtype").trim());
          delivery.setBarcode(itemMap.get("barcode").trim());
          delivery.setBox_no(itemMap.get("boxno").trim());
          delivery.setPick_no(pickNo);
          delivery.setAmount(Integer.parseInt(itemMap.get("count").trim()));

          deliveries.add(delivery);
        }

        // 返回导入结果
        String importResult = jitDeliveryServiceImportDeliveryDetail(esVerification.getAppKey(),
            esVerification.getAppSecret(), esVerification.getAccessToken(),
            aomsshopT.getAomsshop012(), poNo, storageNo, deliveryId, StringUtils.EMPTY, deliveries);

        loginfoOperateService.newTransaction4SaveSource(now, now, VipCommonTool.STORE_TYPE,
            "vipapis.delivery.JitDeliveryService.importDeliveryDetail 导入出仓明细", importResult,
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
            "digiwin.vip.jit.shipping.send 唯品会JIT模式发货");

        // 3.确认出仓
        String result = jitDeliveryServiceConfirmDelivery(esVerification.getAppKey(),
            esVerification.getAppSecret(), esVerification.getAccessToken(),
            aomsshopT.getAomsshop012(), storageNo, poNo, StringUtils.EMPTY);

        loginfoOperateService.newTransaction4SaveSource(now, now, VipCommonTool.STORE_TYPE,
            "vipapis.delivery.JitDeliveryService.confirmDelivery 确认某一条出仓单", result,
            SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
            "digiwin.vip.jit.shipping.send 唯品会JIT模式发货");

        String returnCode = "";
        boolean success = false;
        if (result.equals("1")) {
          success = true;
        } else {
          returnCode = JsonUtil.getJsonNodeTextByFieldName(result, "returnCode");
          success = returnCode.equals("0");
        }
        return new CmdRes(cmdReq, success, !success ? new ResponseError(returnCode,
            JsonUtil.getJsonNodeTextByFieldName(result, "returnMessage")) : null, null);
      }
    }

    return new CmdRes(cmdReq, false, new ResponseError(), null);
  }

  @Override
  public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
    // Map<String, String> params = cmdReq.getParams();
    // String storeId = cmdReq.getStoreid();
    // ESVerification esVerification =
    // aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    // AomsshopT aomsshopT = aomsShopService.getAomsshopTByStoreId(storeId,
    // VipCommonTool.STORE_TYPE);
    //
    //// String numId = params.get("numid");
    // String skuId = params.get("skuid");
    //// String outerId = params.get("outerid");
    // String num = params.get("num");
    // String vendorId = aomsshopT.getAomsshop012();
    //
    // UpdateWarehouseInventoryResponse updateRespnose =
    // stockServiceUpdateWarehouseInventory(esVerification, skuId, num, vendorId);
    //
    // boolean success = updateRespnose != null && updateRespnose.getFail_data() == null;
    // return new CmdRes(cmdReq, success, !success ?
    // new ResponseError(
    // // TODO 错误代码暂时先写流水号
    // updateRespnose.getFail_data().get(0).getTransaction_id(),
    // updateRespnose.getFail_data().get(0).getMessage()
    // ) : null, null);

  }

  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
    // // 取得授權
    // String storeId = cmdReq.getStoreid();
    // ESVerification esVerification =
    // aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    // AomsshopT aomsshopT = aomsShopService.getAomsshopTByStoreId(storeId,
    // VipCommonTool.STORE_TYPE);
    //
    // String numId = cmdReq.getParams().get("numid");
    // String skuIds = cmdReq.getParams().get("skuids");
    // String outerIds = cmdReq.getParams().get("outerids");
    // String nums = cmdReq.getParams().get("nums");
    //
    // String[] skuIdArray = skuIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
    // String[] outerIdArray = outerIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
    // String[] numArray = nums.split(CMD_INVENTORY_UPDATE_DELIMITER);
    //
    // int skuIdCount = skuIdArray.length;
    // int outerIdCount = outerIdArray.length;
    // int numCount = numArray.length;
    //
    // if (skuIdCount != outerIdCount
    // || skuIdCount != numCount
    // || outerIdCount != numCount) {
    // return new CmdRes(cmdReq, false, new ResponseError("039", ErrorMessageBox._039), null);
    // }
    //
    // String vendorId = aomsshopT.getAomsshop012();
    // UpdateWarehouseInventoryResponse updateResponses =
    // stockServiceUpdateWarehouseInventory(esVerification, skuIds, nums, vendorId);
    //
    // boolean success = true;
    // UpdateWarehouseInventoryResult failResult = null;
    // for (UpdateWarehouseInventoryResult fail : updateResponses.getFail_data()) {
    // // TODO 需要测试后知道错误信息的样子，暂时先写0
    // if (!fail.getMessage().equals("1")) {
    // success = false;
    // failResult = fail;
    // break;
    // }
    // }
    //
    // return new CmdRes(cmdReq, success, success ?
    // null : new ResponseError(
    // // TODO 错误代码暂时先写流水号
    // failResult.getTransaction_id(),
    // failResult.getMessage()
    // ), null);
  }

  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    throw new UnsupportedOperationException("_034");
  }

  private List<SimplePick> jitDeliveryServiceCreatePick(String appKey, String appSecret,
      String accessToken, String poNo, String vendorId, String coMode)
          throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    List<SimplePick> picks = client.createPick(poNo, Integer.parseInt(vendorId), coMode);

    return picks;
  }

  @Override
  public GetPickListResponse jitDeliveryServiceGetPickList(String appKey, String appSecret,
      String accessToken, String vendorId, String poNo, String pickNo, Warehouse warehouse,
      String coMode, String orderCate, String stCreateTime, String etCreateTime,
      String stSellTimeFrom, String etSellTimeFrom, String stSellTimeTo, String etSellTimeTo,
      int isExport, int page, int limit, String storeSn)
          throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    GetPickListResponse pickListResponse = client.getPickList(Integer.parseInt(vendorId), poNo,
        pickNo, warehouse, coMode, orderCate, stCreateTime, etCreateTime, stSellTimeFrom,
        etSellTimeFrom, stSellTimeTo, etSellTimeTo, isExport, page, limit, storeSn);

    return pickListResponse;
  }

  @Override
  public PickDetail jitDeliveryServiceGetPickDetail(String appKey, String appSecret,
      String accessToken, String poNo, String vendorId, String pickNo, int page, int limit,
      String coMode) throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    PickDetail pickDetail =
        client.getPickDetail(poNo, Integer.parseInt(vendorId), pickNo, page, limit, coMode);

    return pickDetail;
  }

  // @Override
  // public CmdRes digiwinVipJitCreateDelivery(CmdReq cmdReq) throws Exception {
  // String storeId = cmdReq.getStoreid();
  // ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  // AomsshopT aomsshopT = aomsShopService.getAomsshopTByStoreId(storeId, VipCommonTool.STORE_TYPE);
  //
  // String poid = cmdReq.getParams().get("poid");
  // String expressNo = cmdReq.getParams().get("expressno");
  // String expcompNo = cmdReq.getParams().get("expcompno");
  // String warehouseId = cmdReq.getParams().get("warehouseid");
  // String arrivaltime = cmdReq.getParams().get("arrivaltime");
  //
  // String vendorId = aomsshopT.getAomsshop012();
  //
  // CreateDeliveryResponse createDeliveryResponse =
  // jitDeliveryServiceCreateDelivery(
  // esVerification.getAppKey(),
  // esVerification.getAppSecret(),
  // esVerification.getAccessToken(),
  // vendorId,
  // poid,
  // expressNo,
  // Warehouse.valueOf(warehouseId),
  // StringUtils.EMPTY, arrivaltime, StringUtils.EMPTY,
  // expcompNo, StringUtils.EMPTY,
  // StringUtils.EMPTY, StringUtils.EMPTY,
  // StringUtils.EMPTY,
  // VipCommonTool.MIN_PAGE_NO,
  // VipCommonTool.DEFAULT_PAGE_SIZE,
  // StringUtils.EMPTY, StringUtils.EMPTY);
  // boolean success = createDeliveryResponse != null;
  // List<CreateDeliveryResponse> returnValue = new ArrayList<CreateDeliveryResponse>();
  // if (success) {
  // returnValue.add(createDeliveryResponse);
  // }
  //
  // return new CmdRes(cmdReq, success, success ?
  // null : new ResponseError(), success ? returnValue : null);
  // }

  private CreateDeliveryResponse jitDeliveryServiceCreateDelivery(String appKey, String appSecret,
      String accessToken, String vendorId, String poNo, String deliveryNo, Warehouse warehouse,
      String deliveryTime, String arrivalTime, String raceTime, String carrierName, String tel,
      String driver, String driverTel, String plateNumber, int page, int limit,
      String deliveryMethod, String storeSn) throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    CreateDeliveryResponse createdelResponse = client.createDelivery(Integer.parseInt(vendorId),
        poNo, deliveryNo, warehouse, deliveryTime, arrivalTime, raceTime, carrierName, tel, driver,
        driverTel, plateNumber, page, limit, deliveryMethod, storeSn);

    return createdelResponse;
  }

  // @Override
  // public CmdRes digiwinVipJitImportDeliveryDetail(CmdReq cmdReq) throws Exception {
  // String storeId = cmdReq.getStoreid();
  // ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  // AomsshopT aomsshopT = aomsShopService.getAomsshopTByStoreId(storeId, VipCommonTool.STORE_TYPE);
  // String vendorId = aomsshopT.getAomsshop012();
  //
  // String poId = cmdReq.getParams().get("poid");
  // String storageNo = cmdReq.getParams().get("storageno");
  // String expressNo = cmdReq.getParams().get("expressno");
  // List<? extends LinkedHashMap<String, String>> itemList = cmdReq.getItemlist();
  //
  // List<Delivery> deliveries = new ArrayList<Delivery>();
  // for (Map<String, String> map : itemList) {
  // Delivery delivery = new Delivery();
  // delivery.setVendor_type(VipCommonTool.VENDOR_TYPE);
  // delivery.setBarcode(map.get("barcode"));
  // delivery.setBox_no(map.get("boxno"));
  // delivery.setPick_no(map.get("pickno"));
  // delivery.setAmount(Integer.parseInt(map.get("count")));
  //
  // deliveries.add(delivery);
  // }
  //
  // String result = jitDeliveryServiceImportDeliveryDetail(
  // esVerification.getAppKey(),
  // esVerification.getAppSecret(),
  // esVerification.getAccessToken(),
  // vendorId,
  // poId,
  // storageNo,
  // expressNo,
  // StringUtils.EMPTY,
  // deliveries);
  // boolean success = result != null;
  // List<ImportDeliveryReturnValue> returnValue = new ArrayList<ImportDeliveryReturnValue>();
  // if (success) {
  // returnValue.add(new ImportDeliveryReturnValue(result));
  // }
  //
  // return new CmdRes(cmdReq, success, success ?
  // null : new ResponseError(), success ? returnValue : null);
  // }

  public class ImportDeliveryReturnValue {
    private String storageNo;

    public ImportDeliveryReturnValue() {
      super();
    }

    public ImportDeliveryReturnValue(String storageNo) {
      super();
      this.storageNo = storageNo;
    }

    /**
     * @return the storageNo
     */
    public String getStorageNo() {
      return storageNo;
    }

    /**
     * @param storageNo the storageNo to set
     */
    public void setStorageNo(String storageNo) {
      this.storageNo = storageNo;
    }

  }

  private String jitDeliveryServiceImportDeliveryDetail(String appKey, String appSecret,
      String accessToken, String vendorId, String poNo, String storageNo, String deliveryNo,
      String storeSn, List<Delivery> deliveries) throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    String result = client.importDeliveryDetail(Integer.parseInt(vendorId), poNo, storageNo,
        deliveryNo, storeSn, deliveries);

    return result;
  }

  // @Override
  // public CmdRes digiwinVipJitConfirmDelivery(CmdReq cmdReq) throws Exception {
  // String storeId = cmdReq.getStoreid();
  // ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  // AomsshopT aomsshopT = aomsShopService.getAomsshopTByStoreId(storeId, VipCommonTool.STORE_TYPE);
  //
  // String poid = cmdReq.getParams().get("poid");
  // String storageNo = cmdReq.getParams().get("storageno");
  // String vendorId = aomsshopT.getAomsshop012();
  //
  // String resultCode = jitDeliveryServiceConfirmDelivery(
  // esVerification.getAppKey(),
  // esVerification.getAppSecret(),
  // esVerification.getAccessToken(),
  // vendorId,
  // storageNo,
  // poid,
  // StringUtils.EMPTY);
  // String[] results = resultCode.split(";");
  // boolean success = StringUtils.equals(resultCode, "0");
  //
  // return new CmdRes(cmdReq, success, success ?
  // null : new ResponseError(results[0], results[1]), null);
  // }

  private String jitDeliveryServiceConfirmDelivery(String appKey, String appSecret,
      String accessToken, String vendorId, String storageNo, String poNo, String storeSn)
          throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    String result = client.confirmDelivery(Integer.parseInt(vendorId), storageNo, poNo, storeSn);

    return result;
  }

  // @Override
  // public CmdRes digiwinVipJitGetPrintDelivery(CmdReq cmdReq) throws Exception {
  // String storeId = cmdReq.getStoreid();
  // ESVerification esVerification = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  // AomsshopT aomsshopT = aomsShopService.getAomsshopTByStoreId(storeId, VipCommonTool.STORE_TYPE);
  //
  // String vendorId = aomsshopT.getAomsshop012();
  // String poId = cmdReq.getParams().get("poid");
  // String storageNo = cmdReq.getParams().get("storageno");
  //
  // GetPrintDeliveryResponse printDeliveryResponse =
  // jitDeliveryServiceGetPrintDelivery(
  // esVerification.getAppKey(),
  // esVerification.getAppSecret(),
  // esVerification.getAccessToken(),
  // vendorId,
  // storageNo,
  // poId,
  // StringUtils.EMPTY);
  // boolean success = printDeliveryResponse != null;
  //
  // // TODO
  //
  // return null;
  // }

  private GetPrintDeliveryResponse jitDeliveryServiceGetPrintDelivery(String appKey,
      String appSecret, String accessToken, String vendorId, String storageNo, String poNo,
      String boxNo) throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    GetPrintDeliveryResponse printDeliveryResponse =
        client.getPrintDelivery(Integer.parseInt(vendorId), storageNo, poNo, boxNo);

    return printDeliveryResponse;
  }

  @Override
  public void syncOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      String dateType) throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void syncJitOrdersData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT)
      throws Exception {
    // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    // String lastRunTime = taskScheduleConfig.getLastRunTime();
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String vendorId = aomsshopT.getAomsshop012();

    // 取得時間區間內總資料筆數
    int totalSize = 0;
    GetPoListResponse poListResponse = null;
    boolean sizeMoreThanSetting = false;
    do {
      poListResponse = jitDeliveryServiceGetPoList(aomsshopT.getAomsshop004(),
          aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), StringUtils.EMPTY,
          StringUtils.EMPTY, null, StringUtils.EMPTY, StringUtils.EMPTY, vendorId, sDate, eDate,
          StringUtils.EMPTY, StringUtils.EMPTY, VipCommonTool.MIN_PAGE_NO, pageSize);
      if (poListResponse != null) {
        totalSize = poListResponse.getTotal();
      }
      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀
      } else if (totalSize == 0) {
        // 區間內沒有資料
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return;
        }
      }
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // FIXME for test
    // pageNum = 1;

    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<AomsordT> aomsordTs = new ArrayList<AomsordT>();
      // 获取PO单列表
      poListResponse = jitDeliveryServiceGetPoList(aomsshopT.getAomsshop004(),
          aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), StringUtils.EMPTY,
          StringUtils.EMPTY, null, StringUtils.EMPTY,
          // "2100000042", // FIXME for test
          StringUtils.EMPTY, vendorId, sDate, eDate,
          // StringUtils.EMPTY, StringUtils.EMPTY, // FIXME for test
          StringUtils.EMPTY, StringUtils.EMPTY, i, pageSize);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
          "[SALE_ST_TIME]|vipapis.delivery.JitDeliveryService.getPoList 根据供应商获取PO列表",
          JsonUtil.format(poListResponse),
          SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      List<PurchaseOrder> purchaseOrders = poListResponse.getPurchase_order_list();
      for (PurchaseOrder purchaseOrder : purchaseOrders) {
        String poNo = purchaseOrder.getPo_no();
        // 没有未拣货数量时，更新中台的拣货单信息
        if (purchaseOrder.getNot_pick().equals("0")) {
          vipApiHelperService.updateJitOrderStatus(taskScheduleConfig, aomsshopT, poNo);
          continue;
        }
        List<SimplePick> picks =
            jitDeliveryServiceCreatePick(aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(),
                aomsshopT.getAomsshop006(), purchaseOrder.getPo_no(), vendorId, StringUtils.EMPTY);

        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
            "vipapis.delivery.JitDeliveryService.createPick 创建拣货单信息",
            JsonUtil.format(picks), SourceLogBizTypeEnum.AOMSORDT.getValueString(),
            aomsshopT.getAomsshop001(), taskScheduleConfig.getScheduleType());

        // TODO 不知道创建拣货单会返回多少，先写成循环取，再看实际测试结果调整
        for (SimplePick simplePick : picks) {
          String pickNo = simplePick.getPick_no();

          List<GetPoSkuListResponse> poSkuLists =
              getPoSkuListResponses("", pageSize, aomsshopT, purchaseOrder.getPo_no(), pickNo);
              // 为了获取供货价使用此API

          // TODO isExport参数先填0，测试之后调整
          GetPickListResponse pickListResponse = jitDeliveryServiceGetPickList(
              aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(),
              vendorId, StringUtils.EMPTY, pickNo, null, StringUtils.EMPTY, StringUtils.EMPTY,
              StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY,
              StringUtils.EMPTY, StringUtils.EMPTY, NumberUtils.INTEGER_ZERO,
              VipCommonTool.MIN_PAGE_NO, VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY);
          loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
              "vipapis.delivery.JitDeliveryService.getPickList 获取指定供应商下拣货单列表信息",
              JsonUtil.format(pickListResponse),
              SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
              taskScheduleConfig.getScheduleType());

          // TODO 由于给了拣货单号，正常情况下返回的拣货单列表也应该只有一个，所以这里先写取第一个.如果测试结果不同，再调整
          if (pickListResponse != null && pickListResponse.getPicks().size() > 0) {

            PickDetail pickDetail = jitDeliveryServiceGetPickDetail(aomsshopT.getAomsshop004(),
                aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), poNo, vendorId, pickNo,
                VipCommonTool.MIN_PAGE_NO, VipCommonTool.MIN_PAGE_SIZE, StringUtils.EMPTY);
            loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
                "vipapis.delivery.JitDeliveryService.getPickDetail 获取指定拣货单明细信息",
                JsonUtil.format(pickDetail),
                SourceLogBizTypeEnum.AOMSORDT.getValueString(), aomsshopT.getAomsshop001(),
                taskScheduleConfig.getScheduleType());

            List<AomsordT> list =
                new AomsordTTranslator(pickListResponse.getPicks().get(0), pickDetail, poSkuLists)
                    .doTranslate(aomsshopT.getAomsshop001());
            aomsordTs.addAll(list);

            // 比較區間資料時間，取最大時間
            // 唯品会JIT以sell_st_time，即最新的档期开始销售时间为准，这样可以只获取当前档期的
            Date currentSellStTime = DateTimeTool.parse(purchaseOrder.getSell_st_time());
            if (currentSellStTime.after(lastUpdateTime)) {
              lastUpdateTime = currentSellStTime;
            }

            taskService.newTransaction4Save(aomsordTs);
          } // end-if
        } // end-simplepicks
      } // end-po
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      // 更新lastUpdateTime 成為下次的sDate
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }

  }

  @Override
  public void syncReturnsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      String dateType) throws Exception {
    // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String vendorId = aomsshopT.getAomsshop012();

    // 取得時間區間內總資料筆數
    int totalSize = 0;
    GetReturnInfoResponse returnInfoResponse = null;
    boolean sizeMoreThanSetting = false;
    do {
      returnInfoResponse = vendorReturnServiceGetReturnInfo(aomsshopT.getAomsshop004(),
          aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), vendorId, null, StringUtils.EMPTY,
          sDate, eDate, VipCommonTool.MIN_PAGE_NO, pageSize);
      if (returnInfoResponse != null) {
        totalSize = returnInfoResponse.getTotal();
      }
      sizeMoreThanSetting = totalSize > taskScheduleConfig.getMaxReadRow();
      // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
      if (taskScheduleConfig.isReCycle()) {
        // process empty, 主要是為好閱讀
      } else if (totalSize == 0) {
        // 區間內沒有資料
        if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
          // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
          taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
          return;
        }
      }
      if (sizeMoreThanSetting) {
        // eDate變為sDate與eDate的中間時間
        eDate = DateTimeTool.format(new Date(
            (DateTimeTool.parse(sDate).getTime() + DateTimeTool.parse(eDate).getTime()) / 2));
      }
    } while (sizeMoreThanSetting);

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<AomsrefundT> aomsrefundTs = new ArrayList<AomsrefundT>();
      // 获取退供单列表
      returnInfoResponse = vendorReturnServiceGetReturnInfo(aomsshopT.getAomsshop004(),
          aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), vendorId, null, StringUtils.EMPTY,
          sDate, eDate, i, pageSize);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
          "[" + UseTimeEnum.CREATE_TIME
              + "]|vipapis.vreturn.VendorReturnService.getReturnInfo 查询退供单信息",
          JsonUtil.format(returnInfoResponse),
          SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
          taskScheduleConfig.getScheduleType());

      List<ReturnInfo> returnInfos = returnInfoResponse.getReturnInfos();
      for (ReturnInfo returnInfo : returnInfos) {

        GetReturnDetailResponse returnDetailResponse = vendorReturnServiceGetReturnDetail(
            aomsshopT.getAomsshop004(), aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(),
            vendorId, returnInfo.getWarehouse(), returnInfo.getReturn_sn(), StringUtils.EMPTY,
            StringUtils.EMPTY, VipCommonTool.MIN_PAGE_NO, pageSize);

        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
            "vipapis.vreturn.VendorReturnService.getReturnDetail 查询退供单出仓明细",
            JsonUtil.format(returnInfoResponse),
            SourceLogBizTypeEnum.AOMSREFUNDT.getValueString(), aomsshopT.getAomsshop001(),
            taskScheduleConfig.getScheduleType());

        List<AomsrefundT> list = new AomsrefundTTranslator(returnInfo, returnDetailResponse)
            .doTranslate(aomsshopT.getAomsshop001());
        aomsrefundTs.addAll(list);

        // 比較區間資料時間，取最大時間
        Date currentOutTime = null;
        for (AomsrefundT aomsrefundT : list) {
          Date tempOutTime = DateTimeTool.parse(aomsrefundT.getAoms041());
          if (currentOutTime == null) {
            currentOutTime = tempOutTime;
            continue;
          } else {
            if (tempOutTime.after(currentOutTime)) {
              currentOutTime = tempOutTime;
            }
          }
        }
        if (currentOutTime.after(lastUpdateTime)) {
          lastUpdateTime = currentOutTime;
        }
      }
      taskService.newTransaction4Save(aomsrefundTs);
    }

    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀

    } else {
      // 更新updateTime 成為下次的sDate
      taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
          DateTimeTool.format(lastUpdateTime));
    }

  }

  @Override
  public void syncGoodsData(TaskScheduleConfig taskScheduleConfig, AomsshopT aomsshopT,
      String dateType) throws Exception {
    // 参数设定
    Date lastUpdateTime = DateTimeTool.parse(taskScheduleConfig.getLastUpdateTime());
    String sDate = taskScheduleConfig.getLastUpdateTime();
    String eDate = taskScheduleConfig.getEndDate();
    int pageSize = taskScheduleConfig.getMaxPageSize();

    String vendorId = aomsshopT.getAomsshop012();

    // 取得時間區間內總資料筆數
    int totalSize = 0;
    MultiGetProductSpuResponse spuResponse = null;
    spuResponse = vendorProductServiceMultiGetProductSpuInfo(aomsshopT.getAomsshop004(),
        aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), vendorId, 0, 0, StringUtils.EMPTY,
        null, VipCommonTool.MIN_PAGE_NO, pageSize);
    if (spuResponse != null) {
      totalSize = spuResponse.getTotal();
    }
    // 如果是 reCycle schedule 的, 則不執行更新[lastUpdateTime]logic, 以免影響現有的 check 排程.
    if (taskScheduleConfig.isReCycle()) {
      // process empty, 主要是為好閱讀
    } else if (totalSize == 0) {
      // 區間內沒有資料
      if (DateTimeTool.parse(eDate).before(DateTimeTool.parse(taskScheduleConfig.getSysDate()))) {
        // 如果區間的 eDate < sysDate 則把lastUpdateTime變為eDate
        taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), eDate);
        return;
      }
    }

    // 計算頁數
    int pageNum = (totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);
    // 锁定库存数量
    List<String> nums = new ArrayList<String>();
    // 針對每一頁(倒序)的所有資料新增
    for (int i = pageNum; i > 0; i--) {
      List<AomsitemT> aomsitemTs = new ArrayList<AomsitemT>();
      // 获取商品SPU列表
      spuResponse = vendorProductServiceMultiGetProductSpuInfo(aomsshopT.getAomsshop004(),
          aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), vendorId, 0, 0, StringUtils.EMPTY,
          null, i, pageSize);

      loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
          "vipapis.product.VendorProductService.multiGetProductSpuInfo 多维度获取商品SPU信息",
          JsonUtil.format(spuResponse), SourceLogBizTypeEnum.AOMSITEMT.getValueString(),
          aomsshopT.getAomsshop001(), taskScheduleConfig.getScheduleType());

      List<ProductSpuInfo> spuInfos = spuResponse.getProducts();
      for (ProductSpuInfo spuInfo : spuInfos) {
        MultiGetProductSkuResponse skuResponse =
            vendorProductServiceMultiGetProductSkuInfo(aomsshopT.getAomsshop004(),
                aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), vendorId, StringUtils.EMPTY,
                0, 0, spuInfo.getSn(), VipCommonTool.MIN_PAGE_NO, pageSize);

        loginfoOperateService.newTransaction4SaveSource(sDate, eDate, VipCommonTool.STORE_TYPE,
            "vipapis.product.VendorProductService.multiGetProductSkuInfo 多维度获取商品SKU信息",
            JsonUtil.format(spuResponse),
            SourceLogBizTypeEnum.AOMSITEMT.getValueString(), aomsshopT.getAomsshop001(),
            taskScheduleConfig.getScheduleType());
        if (skuResponse != null && skuResponse.getProducts().size() > 0) {
          for (ProductSkuInfo skuInfo : skuResponse.getProducts()) {
            List<GetVendorScheduleFreezeStockResult> results =
                stockServiceGetVendorScheduleFreezeStock(aomsshopT.getAomsshop004(),
                    aomsshopT.getAomsshop005(), aomsshopT.getAomsshop006(), vendorId,
                    StringUtils.EMPTY, skuInfo.getBarcode(), StringUtils.EMPTY, StringUtils.EMPTY,
                    StringUtils.EMPTY, VipCommonTool.MIN_PAGE_NO, VipCommonTool.DEFAULT_PAGE_SIZE);
            if (results.size() > 0) {
              nums.add(results.get(0).getFreeze_num());
            }
          }
        }

        List<AomsitemT> list = new AomsitemTTranslator<String>(spuInfo, skuResponse, nums)
            .doTranslate(aomsshopT.getAomsshop001());
        aomsitemTs.addAll(list);

        nums.clear();
      }
      taskService.newTransaction4Save(aomsitemTs);
    }
    // 更新updateTime 成為下次的sDate
    taskService.updateLastUpdateTime(taskScheduleConfig.getScheduleType(), sDate,
        DateTimeTool.format(lastUpdateTime));
  }

  /**
   * 根据供应商获取PO列表：http://vop.vip.com/apicenter/method?serviceName=vipapis.delivery.JitDeliveryService
   * -1.0.0&methodName=getPoLis
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param stCreateTime
   * @param etCreateTime
   * @param warehouse
   * @param poNo
   * @param coMode
   * @param vendorId
   * @param stSellStTime
   * @param etSellTtTime
   * @param stSellEtTime
   * @param etSellEtTime
   * @param page
   * @param limit
   * @return
   * @throws OspException
   */
  private GetPoListResponse jitDeliveryServiceGetPoList(String appKey, String appSecret,
      String accessToken, String stCreateTime, String etCreateTime, Warehouse warehouse,
      String poNo, String coMode, String vendorId, String stSellStTime, String etSellTtTime,
      String stSellEtTime, String etSellEtTime, int page, int limit)
          throws NumberFormatException, OspException {

    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);
    GetPoListResponse poListResponse = client.getPoList(stCreateTime, etCreateTime, warehouse, poNo,
        coMode, vendorId, stSellStTime, etSellTtTime, stSellEtTime, etSellEtTime, page, limit);

    return poListResponse;
  }

  /**
   * 获取PO单SKU信息：http://vop.vip.com/apicenter/method?serviceName=vipapis.delivery.JitDeliveryService-
   * 1.0.0&methodName=getPoSkuList
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param vendorId
   * @param poNo
   * @param sellSite
   * @param warehouse
   * @param orderStatus
   * @param stAduityTime
   * @param etAduityTime
   * @param orderId
   * @param pickNo
   * @param deliveryNo
   * @param stPickTime
   * @param etPickTime
   * @param stDeliveryTime
   * @param etDeliveryTime
   * @param page
   * @param limit
   * @return
   * @throws OspException
   */
  private GetPoSkuListResponse jitDeliveryServiceGetPoSkuList(String appKey, String appSecret,
      String accessToken, String vendorId, String poNo, String sellSite, Warehouse warehouse,
      String orderStatus, String stAduityTime, String etAduityTime, String orderId, String pickNo,
      String deliveryNo, String stPickTime, String etPickTime, String stDeliveryTime,
      String etDeliveryTime, int page, int limit) throws NumberFormatException, OspException {
    JitDeliveryServiceClient client = new JitDeliveryServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);
    GetPoSkuListResponse skuListResponse = client.getPoSkuList(Integer.parseInt(vendorId), poNo,
        sellSite, warehouse, orderStatus, stAduityTime, etAduityTime, orderId, pickNo, deliveryNo,
        stPickTime, etPickTime, stDeliveryTime, etDeliveryTime, page, limit);

    return skuListResponse;
  }

  /**
   * 查询退供单信息:http://vop.vip.com/apicenter/method?serviceName=vipapis.vreturn.VendorReturnService-1.0
   * .0&methodName=getReturnInfo
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param vendorId
   * @param warehouse
   * @param returnSn
   * @param stCreateTime
   * @param edCreateTime
   * @param page
   * @param limit
   * @return
   * @throws OspException
   */
  private GetReturnInfoResponse vendorReturnServiceGetReturnInfo(String appKey, String appSecret,
      String accessToken, String vendorId, Warehouse warehouse, String returnSn,
      String stCreateTime, String edCreateTime, int page, int limit)
          throws NumberFormatException, OspException {
    VendorReturnServiceClient client = new VendorReturnServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    GetReturnInfoResponse returnInfoResponse = client.getReturnInfo(Integer.parseInt(vendorId),
        warehouse, returnSn, stCreateTime, edCreateTime, page, limit);

    return returnInfoResponse;
  }

  /**
   * 查询退供单出仓明细：http://vop.vip.com/apicenter/method?serviceName=vipapis.vreturn.VendorReturnService-1
   * .0.0&methodName=getReturnDetail
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param vendorId
   * @param warehouse
   * @param returnSn
   * @param outTimeStart
   * @param outTimeEnd
   * @param page
   * @param limit
   * @return
   * @throws OspException
   */
  private GetReturnDetailResponse vendorReturnServiceGetReturnDetail(String appKey,
      String appSecret, String accessToken, String vendorId, Warehouse warehouse, String returnSn,
      String outTimeStart, String outTimeEnd, int page, int limit)
          throws NumberFormatException, OspException {
    VendorReturnServiceClient client = new VendorReturnServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    GetReturnDetailResponse returnDetailResponse = client.getReturnDetail(
        Integer.parseInt(vendorId), warehouse, returnSn, outTimeStart, outTimeEnd, page, limit);

    return returnDetailResponse;
  }

  /**
   * 多维度获取商品SPU信息：http://vop.vip.com/apicenter/method?serviceName=vipapis.product.
   * VendorProductService-1.0.0&methodName=multiGetProductSpuInfo
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param vendorId
   * @param brandId
   * @param categoryId
   * @param sn
   * @param status
   * @param page
   * @param limit
   * @return
   * @throws OspException
   */
  private MultiGetProductSpuResponse vendorProductServiceMultiGetProductSpuInfo(String appKey,
      String appSecret, String accessToken, String vendorId, int brandId, int categoryId, String sn,
      ProductStatus status, int page, int limit) throws NumberFormatException, OspException {
    VendorProductServiceClient client = new VendorProductServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    MultiGetProductSpuResponse spuResponse = client.multiGetProductSpuInfo(
        Integer.parseInt(vendorId), brandId, categoryId, sn, status, page, limit);

    return spuResponse;
  }

  /**
   * 多维度获取商品SKU信息：http://vop.vip.com/apicenter/method?serviceName=vipapis.product.
   * VendorProductService-1.0.0&methodName=multiGetProductSkuInfo
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param vendorId
   * @param barCode
   * @param brandId
   * @param categoryId
   * @param sn
   * @param page
   * @param limit
   * @return
   * @throws OspException
   */
  private MultiGetProductSkuResponse vendorProductServiceMultiGetProductSkuInfo(String appKey,
      String appSecret, String accessToken, String vendorId, String barCode, int brandId,
      int categoryId, String sn, int page, int limit) throws NumberFormatException, OspException {
    VendorProductServiceClient client = new VendorProductServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    MultiGetProductSkuResponse skuResponse = client.multiGetProductSkuInfo(
        Integer.parseInt(vendorId), barCode, brandId, categoryId, sn, page, limit);

    return skuResponse;
  }

  /**
   * 获取供应商档期门店锁定库存:http://vop.vip.com/apicenter/method?serviceName=vipapis.stock.StockService-1.0.0&
   * methodName=getVendorScheduleFreezeStock
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param vendorId
   * @param warehouseId
   * @param barCode
   * @param scheduleId
   * @param startDate
   * @param endDate
   * @param page
   * @param limit
   * @return
   * @throws NumberFormatException
   * @throws OspException
   */
  private List<GetVendorScheduleFreezeStockResult> stockServiceGetVendorScheduleFreezeStock(
      String appKey, String appSecret, String accessToken, String vendorId, String warehouseId,
      String barCode, String scheduleId, String startDate, String endDate, int page, int limit)
          throws NumberFormatException, OspException {
    StockServiceClient client = new StockServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(appKey);
    context.setAppSecret(appSecret);
    context.setAccessToken(accessToken);

    GetVendorScheduleFreezeStock scheduleFreezeStock = new GetVendorScheduleFreezeStock();
    scheduleFreezeStock.setVendor_warehouse_id(warehouseId);
    scheduleFreezeStock.setBarcode(barCode);
    scheduleFreezeStock.setSchedule_id(scheduleId);
    scheduleFreezeStock.setStart_date(startDate);
    scheduleFreezeStock.setEnd_date(endDate);
    scheduleFreezeStock.setPage(page);
    scheduleFreezeStock.setLimit(limit);

    List<GetVendorScheduleFreezeStockResult> results =
        client.getVendorScheduleFreezeStock(Integer.parseInt(vendorId), scheduleFreezeStock);

    return results;
  }

  /**
   * 供应商商品库存信息更新：http://vop.vip.com/apicenter/method?serviceName=vipapis.stock.StockService-1.0.0&
   * methodName=updateWarehouseInventory
   * 
   * @param esVerification
   * @param barCode 商品条码（可以多个，以逗号分隔）
   * @param num 商品库存数量（可以多个，以逗号分隔）
   * @param vendorId 供应商ID
   * @return
   * @throws NumberFormatException
   * @throws OspException
   */
  private UpdateWarehouseInventoryResponse stockServiceUpdateWarehouseInventory(
      ESVerification esVerification, String barCode, String num, String vendorId)
          throws NumberFormatException, OspException {
    StockServiceClient client = new StockServiceClient();
    InvocationContext context = InvocationContext.Factory.getInstance();
    context.setAppURL(paramSystemService.getEcApiUrl(EcApiUrlEnum.VIP_API));
    context.setAppKey(esVerification.getAppKey());
    context.setAppSecret(esVerification.getAppSecret());
    context.setAccessToken(esVerification.getAccessToken());

    List<UpdateWarehouseInventory> updateList = new ArrayList<UpdateWarehouseInventory>();
    // TODO 需要等SDK更新后增加参数的设定
    String[] barCodes = barCode.split(CMD_INVENTORY_UPDATE_DELIMITER);
    String[] nums = num.split(CMD_INVENTORY_UPDATE_DELIMITER);

    for (int i = 0; i < barCodes.length; i++) {
      UpdateWarehouseInventory updateWarehouseInventory = new UpdateWarehouseInventory();
      updateWarehouseInventory.setBarcode(barCodes[i]);
      updateWarehouseInventory.setInventory_qty(Integer.parseInt(nums[i]));
      updateWarehouseInventory.setInventory_type(VipCommonTool.JIT_INVENTORY_TYPE);
      updateWarehouseInventory.setTransaction_id(String.valueOf(System.currentTimeMillis()));

      updateList.add(updateWarehouseInventory);
    }

    UpdateWarehouseInventoryResponse updateRespnoses =
        client.updateWarehouseInventory(Integer.parseInt(vendorId), updateList);

    return updateRespnoses;
  }

}
