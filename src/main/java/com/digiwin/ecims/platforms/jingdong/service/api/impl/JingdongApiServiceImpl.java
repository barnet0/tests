package com.digiwin.ecims.platforms.jingdong.service.api.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.mall.http.PriceChange;
import com.jd.open.api.sdk.domain.ware.Sku;
import com.jd.open.api.sdk.domain.ware.Ware;
import com.jd.open.api.sdk.request.afsservice.AfsserviceFreightmessageGetRequest;
import com.jd.open.api.sdk.request.afsservice.AfsserviceReceivetaskGetRequest;
import com.jd.open.api.sdk.request.afsservice.AfsserviceRefundinfoGetRequest;
import com.jd.open.api.sdk.request.afsservice.AfsserviceServicedetailListRequest;
import com.jd.open.api.sdk.request.etms.EtmsWaybillSendRequest;
import com.jd.open.api.sdk.request.etms.EtmsWaybillcodeGetRequest;
import com.jd.open.api.sdk.request.imgzone.ImgzonePictureUploadRequest;
import com.jd.open.api.sdk.request.mall.WarePriceGetRequest;
import com.jd.open.api.sdk.request.order.OrderGetRequest;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.request.order.OrderSopOutstorageRequest;
import com.jd.open.api.sdk.request.refundapply.PopAfsSoaRefundapplyQueryByIdRequest;
import com.jd.open.api.sdk.request.refundapply.PopAfsSoaRefundapplyQueryPageListRequest;
import com.jd.open.api.sdk.request.ware.WareGetRequest;
import com.jd.open.api.sdk.request.ware.WareInfoByInfoRequest;
import com.jd.open.api.sdk.request.ware.WareListRequest;
import com.jd.open.api.sdk.request.ware.WareSkuStockUpdateRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateDelistingRequest;
import com.jd.open.api.sdk.request.ware.WareUpdateListingRequest;
import com.jd.open.api.sdk.response.afsservice.AfsserviceFreightmessageGetResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceReceivetaskGetResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceRefundinfoGetResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceServicedetailListResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceServiceinfoGetResponse;
import com.jd.open.api.sdk.response.etms.EtmsWaybillSendResponse;
import com.jd.open.api.sdk.response.etms.EtmsWaybillcodeGetResponse;
import com.jd.open.api.sdk.response.imgzone.ImgzonePictureUploadResponse;
import com.jd.open.api.sdk.response.mall.WarePriceGetResponse;
import com.jd.open.api.sdk.response.order.OrderGetResponse;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.jd.open.api.sdk.response.order.OrderSopOutstorageResponse;
import com.jd.open.api.sdk.response.refundapply.PopAfsSoaRefundapplyQueryByIdResponse;
import com.jd.open.api.sdk.response.refundapply.PopAfsSoaRefundapplyQueryPageListResponse;
import com.jd.open.api.sdk.response.ware.WareGetResponse;
import com.jd.open.api.sdk.response.ware.WareInfoByInfoSearchResponse;
import com.jd.open.api.sdk.response.ware.WareListResponse;
import com.jd.open.api.sdk.response.ware.WareSkuStockUpdateResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateDelistingResponse;
import com.jd.open.api.sdk.response.ware.WareUpdateListingResponse;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.LoginfoOperateService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.HttpGetUtils;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.platforms.jingdong.bean.picture.PictureExternalUploadReturnValue;
import com.digiwin.ecims.platforms.jingdong.bean.request.afsservice.MyAfsserviceServicedetailListRequest;
import com.digiwin.ecims.platforms.jingdong.bean.response.afsservice.MyAfsserviceServicedetailListResponse;
import com.digiwin.ecims.platforms.jingdong.service.api.JingdongApiService;
import com.digiwin.ecims.platforms.jingdong.util.JingdongClientUtil;
import com.digiwin.ecims.platforms.jingdong.util.JingdongCommonTool;
import com.digiwin.ecims.platforms.jingdong.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.jingdong.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.jingdong.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.TaskService;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.SourceLogBizTypeEnum;

@Service
public class JingdongApiServiceImpl implements JingdongApiService {

  private static final Logger logger = LoggerFactory.getLogger(JingdongApiServiceImpl.class);

  @Autowired
  private ParamSystemService paramSystemService; // add by mowj 20150928

  @Autowired
  private AomsShopService aomsShopService;
  

  /**
   * service 區份調用 api 區塊
   */
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
      case DIGIWIN_PICTURE_EXTERNAL_UPLOAD:
        cmdRes = digiwinPictureExternalUpload(cmdReq);
        break;
      case DIGIWIN_JD_EXPRESSNO_GET:
        cmdRes = digiwinJdExpressnoGet(cmdReq);
        break;
      case DIGIWIN_JD_WAYBILL_SEND:
        cmdRes = digiwinJdWaybillSend(cmdReq);
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
    
    Map<String,String> jdmap=new HashMap<String,String>();
    jdmap.put("appKey", esv.getAppKey());
    jdmap.put("accessToken", esv.getAppSecret());

    OrderGetResponse response = jingdongOrderGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),  
        orderId, JingdongCommonTool.ORDER_FIELDS, null);

    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getEnDesc()),
        success ? new AomsordTTranslator(response.getOrderDetailInfo().getOrderInfo())
            .doTranslate(cmdReq.getStoreid(),jdmap) : null);
  }

  @Override
  public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
    if (cmdReq.getParams().get("type").equals(REFUND_TYPE_NORMAL)) {
      return jingdongPopAfsSoaRefundapplyQueryById(cmdReq);
    } else if (cmdReq.getParams().get("type").equals(REFUND_TYPE_JDAFSERVICE)) {
      return jingdongAfsserviceInfoGet(cmdReq);
    } else {
      return new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
    }
  }

  @Override
  public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得API所需参数
    String wareId = cmdReq.getParams().get("numid");

    WareGetResponse response = jingdongWareGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),  
        wareId, null);
     
    //addbycs at 20170419
    //取得每个ware单品的商品价格 ,根据sku资讯，再将价格信息存入一个list
    Ware wareGoods = response.getWare();
    List<HashMap<String, String>> priceList = new ArrayList<HashMap<String, String>>();
    if(wareGoods.getSkus() != null){
    	  	
    	for(Sku sku : wareGoods.getSkus()){
    		String skuId = "J_"+ sku.getSkuId().toString();
    		
    		HashMap<String, String> map = new HashMap<String,String>();
    		
    		map.put("skuId", sku.getSkuId().toString());	
    		
    	    //根据skuid获取每个sku商品的价格
    		WarePriceGetResponse warePriceGetResponse = jingdongWarePriceGet(
    				  esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
    				  skuId);
    		
    		List<PriceChange> priceChange = warePriceGetResponse.getPriceChanges();
    		
    		map.put("price", priceChange.get(0).getPrice());
    		map.put("marketPrice", priceChange.get(0).getMarketPrice());
    		
    		
    		priceList.add(map);
    		
    	}
    }

    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getEnDesc()),
        success ? new AomsitemTTranslator(response.getWare(), response.getWare().getSkus(),priceList)
            .doTranslator(cmdReq.getStoreid()) : null);    //modifybycs 增加函数传入参数
  }

  @Override
  public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  
    // 取得参数
    String wareId = cmdReq.getParams().get("numid");
  
    WareUpdateListingResponse response = jingdongWareUpdateListing(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        wareId, System.currentTimeMillis() + StringTool.EMPTY);
  
    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getEnDesc()), // 上架只有成功或失敗
        null);
  }

  @Override
  public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
  
    // 取得参数
    String wareId = cmdReq.getParams().get("numid");
  
    WareUpdateDelistingResponse response = jingdongWareUpdateDelisting(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        wareId, System.currentTimeMillis() + StringTool.EMPTY);
  
    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getEnDesc()), // 下架只有成功或失敗
        null);
  }

  @Override
  public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
    // TODO Auto-generated method stub
    return null;
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
  
    WareSkuStockUpdateResponse response = jingdongSkuStockUpdate(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        skuId, outerId, quantity, 
        null, null);
  
    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getEnDesc()), null);
  }

  //  // 用途 ：不明
  //  @Override
  //  public String saveAomsord(TaskScheduleConfig taskScheduleConfig, Date modiDate,
  //      List<Object> saveAomsordTs) {
  //    String modifiedDateTime = DateTimeTool.format(modiDate);
  //    baseDAO.saveOrUpdateByCollectionWithLog(saveAomsordTs);
  //    return modifiedDateTime;
  //  }
  
  @Override
  public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
    // TODO Auto-generated method stub
    return null;
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

    OrderSopOutstorageResponse response = jingdongOrderSopOutstorage(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        logisticsId, waybill, orderId, null);

    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getEnDesc()), null);// 只區分成功或不成功
  }

  /*
   * 图片上传：https://jos.jd.com/api/detail.htm?apiName=jingdong.imgzone.picture.upload&id=164
   */
  @Override
  public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    String extPicUrl = cmdReq.getParams().get("extpicurl");
    String picCateId = cmdReq.getParams().get("picateid");
    String picName = cmdReq.getParams().get("picname");

    if (extPicUrl == null || picCateId == null || picName == null) {
      return new CmdRes(cmdReq, false,
          new ResponseError("031", ErrorMessageBox.getErrorMsg("_031")), null);
    }
    // 先下载到本地
    byte[] imageData = HttpGetUtils.getInstance().sendGet(extPicUrl);

    if (imageData != null) {
      // 再上传到京东
      JdClient client =
          new DefaultJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API),
              // authorizationData.get("accessToken"),
              // authorizationData.get("appKey"),
              // authorizationData.get("appSecret")
              esv.getAccessToken(), esv.getAppKey(),
              esv.getAppSecret());
      ImgzonePictureUploadRequest request = new ImgzonePictureUploadRequest();
      request.setImageData(imageData);
      // 0为默认分类
      if (!picCateId.equals("0")) {
        request.setPictureCateId(Long.parseLong(picCateId));
      }
      if (picName.length() > 0) {
        request.setPictureName(picName);
      }
      ImgzonePictureUploadResponse response = client.execute(request);
    //response.getCode().equals(JingdongCommonTool.RESPONSE_EXPRESS_SUCCESS_CODE)
    //response.getEnDesc()
      if (response != null && response.getCode() != null) {
        boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
        List<PictureExternalUploadReturnValue> returnValues = null;
        if (success) {
          returnValues = new ArrayList<PictureExternalUploadReturnValue>();
          returnValues.add(new PictureExternalUploadReturnValue(response.getPictureId(),
              response.getPictureUrl()));
        }

        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getCode(), response.getDesc()),
            returnValues);
      } else {
        return new CmdRes(cmdReq, false,
            new ResponseError("035", ErrorMessageBox.getErrorMsg("_035")), null);
      }
    } else {
      return new CmdRes(cmdReq, false,
          new ResponseError("037", ErrorMessageBox.getErrorMsg("_037")), null);
    }
  }

  /**
   * 獲取運單號http://jos.jd.com/api/detail.htm?apiName=jingdong.etms.waybillcode.get&id=246
   */
  @Override
  public CmdRes digiwinJdExpressnoGet(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得参数
    // 运单号数量
    String preNum = cmdReq.getParams().get("cnt");
    // 商家编码
    String customerCode = cmdReq.getParams().get("custcode");

    // 調用API
    EtmsWaybillcodeGetResponse response = jingdongEtmsWaybillcodeGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        preNum, customerCode, null);
    boolean success = response.getResultInfo().getCode()
        .equals(JingdongCommonTool.RESPONSE_EXPRESS_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getResultInfo().getCode(),
                response.getEnDesc()),
        success ? response.getResultInfo().getDeliveryIdList() : null);
  }

  /**
   * 提交運單信息http://jos.jd.com/api/detail.htm?apiName=jingdong.etms.waybill.send&id=245
   */
  @Override
  public CmdRes digiwinJdWaybillSend(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    // Map<String, String> authorizationData = getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得参数
    // 运单号
    String deliveryId = cmdReq.getParams().get("deliveryid");
    // 商家编码
    String customerCode = cmdReq.getParams().get("sellercode");
    // 商家订单号
    String erpOid = cmdReq.getParams().get("erpoid");
    // 京东订单号
    String jdOid = cmdReq.getParams().get("oid");
    // 寄件人姓名
    String senderName = cmdReq.getParams().get("sendername");
    // 寄件人地址
    String senderAddress = cmdReq.getParams().get("senderaddress");
    // 寄件人电话
    String senderTel = cmdReq.getParams().get("senderphone");
    // 寄件人手机
    String senderMobile = cmdReq.getParams().get("sendermobile");
    // 收件人名称
    String receiverName = cmdReq.getParams().get("receivername");
    // 收件人地址
    String receiverAddress = cmdReq.getParams().get("receiveraddress");
    // 收件人电话
    String receiverTel = cmdReq.getParams().get("receiverphone");
    // 收件人手机
    String receiverMobile = cmdReq.getParams().get("receivermobile");
    // 代收金額
    double collectionMoney = Double.parseDouble(cmdReq.getParams().get("collectionmoney").trim()); 
    // 是否代收
    int collectionValue = Integer.parseInt(cmdReq.getParams().get("collectionvalue").trim()); 
    // 包裹数 
    int packageCount = Integer.parseInt(cmdReq.getParams().get("packagecnt").trim());
    // 重量
    double weight = Double.parseDouble(cmdReq.getParams().get("weight").trim());
    // 体积
    double vloumn = Double.parseDouble(cmdReq.getParams().get("volumn").trim());

    // 检查寄件人电话&手机，与收件人电话&手机至少有一个不为空
    if (StringTool.isEmpty(senderTel)
        && StringTool.isEmpty(senderMobile)) {
      throw new IllegalArgumentException("寄件人电话(senderphone)&手机(sendermobile)至少有一个不为空!");
    }
    if (StringTool.isEmpty(receiverTel)
        && StringTool.isEmpty(receiverMobile)) {
      throw new IllegalArgumentException("收件人电话(receiverphone)&手机(receivermobile)至少有一个不为空!");
    }

    EtmsWaybillSendResponse response = jingdongEtmsWaybillSend(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        deliveryId, JingdongCommonTool.SAL_PLAT_JINGDONG_CODE, customerCode, erpOid, jdOid, 
        null, null, null, senderName, senderAddress, senderTel, senderMobile, null, 
        receiverName, receiverAddress, null, null, null, null, null, null, null, null, 
        null, null, null, receiverTel, receiverMobile, null, packageCount, weight, 
        null, null, null, vloumn, null, collectionValue, collectionMoney, null, null, 
        null, null, null, null, null, null, null, null, null, null, null, null, null, null);

    if (!response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE)) {
      return new CmdRes(cmdReq, false, new ResponseError(response.getCode(), response.getEnDesc()),
          null);
    } else {
      boolean resultSuccess = response.getResultInfo().getCode()
          .equals(JingdongCommonTool.RESPONSE_EXPRESS_SUCCESS_CODE);
      return new CmdRes(cmdReq, resultSuccess,
          resultSuccess ? null
              : new ResponseError(response.getResultInfo().getCode(),
                  response.getResultInfo().getMessage()),
          null);
    }
  }

  private CmdRes jingdongPopAfsSoaRefundapplyQueryById(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得参数
    // 退款单号
    Long refundId = Long.parseLong(cmdReq.getParams().get("id"));

    PopAfsSoaRefundapplyQueryByIdResponse response = jingdongRefundApplyQueryById(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        refundId);

    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null
            : new ResponseError(response.getCode(), response.getEnDesc()),
        success ? new AomsrefundTTranslator(response, cmdReq.getStoreid()).doTranslate() : null);
  }

  private CmdRes jingdongAfsserviceInfoGet(CmdReq cmdReq) throws Exception {
    // 取得授权
    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

    // 取得参数
    // 服务单号
    Long afsserviceId = Long.parseLong(cmdReq.getParams().get("id"));

    AfsserviceServiceinfoGetResponse response = jingdongAfsserviceInfoGet(
        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
        afsserviceId);

    boolean success = response.getCode().equals(JingdongCommonTool.RESPONSE_SUCCESS_CODE);
    return new CmdRes(cmdReq, success,
        success ? null : new ResponseError(response.getCode(), response.getEnDesc()),
        success ? new AomsrefundTTranslator(response, cmdReq.getStoreid()).doTranslate(response)
            : null);
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongOrderSearch(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public OrderSearchResponse jingdongOrderSearch(String appKey, String appSecret,
      String accessToken, String startDate, String endDate, String orderState, Integer page,
      Integer pageSize, String optionalFields, String sortType, String dateType)
          throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    OrderSearchRequest request = new OrderSearchRequest();
    if (StringTool.isNotEmpty(startDate)) {
      request.setStartDate(startDate);
    }
    if (StringTool.isNotEmpty(endDate)) {
      request.setEndDate(endDate);
    }
    request.setOrderState(orderState);
    request.setPage(page + StringTool.EMPTY);
    request.setPageSize(pageSize + StringTool.EMPTY);
    if (StringTool.isNotEmpty(optionalFields)) {
      request.setOptionalFields(optionalFields);
    }
    if (StringTool.isNotEmpty(sortType)) {
      request.setSortType(sortType);
    }
    if (StringTool.isNotEmpty(dateType)) {
      request.setDateType(dateType);
    }
    
    OrderSearchResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongOrderGet(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public OrderGetResponse jingdongOrderGet(String appKey, String appSecret, String accessToken,
      String orderId, String optionalFields, String orderState) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    OrderGetRequest request = new OrderGetRequest();
    request.setOrderId(orderId);
    request.setOptionalFields(optionalFields);
    if (StringTool.isNotEmpty(orderState)) {
      request.setOrderState(orderState);
    }
    
    OrderGetResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongOrderSopOutstorage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public OrderSopOutstorageResponse jingdongOrderSopOutstorage(String appKey, String appSecret,
      String accessToken, String logisticsId, String waybill, String orderId, String tradeNo)
          throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    OrderSopOutstorageRequest request = new OrderSopOutstorageRequest();
    request.setLogisticsId(logisticsId);
    if (StringTool.isNotEmpty(waybill)) {
      request.setWaybill(waybill);
    }
    request.setOrderId(orderId);
    if (StringTool.isNotEmpty(tradeNo)) {
      request.setTradeNo(tradeNo);
    }
    
    OrderSopOutstorageResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongRefundApplyQueryPageList(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
   */
  @Override
  public PopAfsSoaRefundapplyQueryPageListResponse jingdongRefundApplyQueryPageList(String appKey,
      String appSecret, String accessToken, String ids, Long status, String orderId, String buyerId,
      String buyerName, String applyTimeStart, String applyTimeEnd, String checkTimeStart,
      String checkTimeEnd, Integer pageIndex, Integer pageSize) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    PopAfsSoaRefundapplyQueryPageListRequest request = new PopAfsSoaRefundapplyQueryPageListRequest();
    if (StringTool.isNotEmpty(ids)) {
      request.setIds(ids);
    }
    if (status != null) {
      request.setStatus(status);
    }
    if (StringTool.isNotEmpty(orderId)) {
      request.setOrderId(orderId);
    }
    if (StringTool.isNotEmpty(buyerId)) {
      request.setBuyerId(buyerId);
    }
    if (StringTool.isNotEmpty(buyerName)) {
      request.setBuyerName(buyerName);
    }
    if (StringTool.isNotEmpty(applyTimeStart)) {
      request.setApplyTimeStart(applyTimeStart);
    }
    if (StringTool.isNotEmpty(applyTimeEnd)) {
      request.setApplyTimeEnd(applyTimeEnd);
    }
    // 审核时间
    if (StringTool.isNotEmpty(checkTimeStart)) {
      request.setCheckTimeStart(checkTimeStart);
    }
    if (StringTool.isNotEmpty(checkTimeEnd)) {
      request.setCheckTimeEnd(checkTimeEnd);
    }
    request.setPageIndex(pageIndex);
    request.setPageSize(pageSize);
    
    PopAfsSoaRefundapplyQueryPageListResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongRefundApplyQueryById(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public PopAfsSoaRefundapplyQueryByIdResponse jingdongRefundApplyQueryById(String appKey,
      String appSecret, String accessToken, Long id) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    PopAfsSoaRefundapplyQueryByIdRequest request = new PopAfsSoaRefundapplyQueryByIdRequest();
    request.setId(id);
    
    PopAfsSoaRefundapplyQueryByIdResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongAfsserviceReceivetaskGet(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public AfsserviceReceivetaskGetResponse jingdongAfsserviceReceivetaskGet(String appKey,
      String appSecret, String accessToken, Integer afsserviceId, Integer pageNumber, Integer pageSize,
      String customerPin, Long orderId, Date afsApplyTimeBegin, Date afsApplyTimeEnd)
          throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    AfsserviceReceivetaskGetRequest request = new AfsserviceReceivetaskGetRequest();
    if (afsserviceId != null) {
      request.setAfsServiceId(afsserviceId);
    }
    request.setPageNumber(pageNumber);
    request.setPageSize(pageSize);
    if (StringTool.isNotEmpty(customerPin)) {
      request.setCustomerPin(customerPin);
    }
    if (StringTool.isNotEmpty(orderId)) {
      request.setOrderId(orderId);
    }
    if (StringTool.isNotEmpty(afsApplyTimeBegin)) {
      request.setAfsApplyTimeBegin(afsApplyTimeBegin);
    }
    if (StringTool.isNotEmpty(afsApplyTimeEnd)) {
      request.setAfsApplyTimeEnd(afsApplyTimeEnd);
    }

    AfsserviceReceivetaskGetResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongAfsserviceServicedetailList(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
   */
  @Override
  public AfsserviceServicedetailListResponse jingdongAfsserviceServicedetailList(String appKey,
      String appSecret, String accessToken, Integer afsServiceId) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    AfsserviceServicedetailListRequest request = new AfsserviceServicedetailListRequest();
    request.setAfsServiceId(afsServiceId);
    
    AfsserviceServicedetailListResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#myJingdongAfsserviceServicedetailList(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
   */
  @Override
  public MyAfsserviceServicedetailListResponse myJingdongAfsserviceServicedetailList(String appKey,
      String appSecret, String accessToken, Long afsServiceId) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    MyAfsserviceServicedetailListRequest request = new MyAfsserviceServicedetailListRequest();
    request.setAfsServiceId(afsServiceId);
    
    MyAfsserviceServicedetailListResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jindongAfsserviceFreightMessageGet(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
   */
  @Override
  public AfsserviceFreightmessageGetResponse jindongAfsserviceFreightMessageGet(String appKey,
      String appSecret, String accessToken, Integer afsserviceId) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    AfsserviceFreightmessageGetRequest request = new AfsserviceFreightmessageGetRequest();
    request.setAfsServiceId(afsserviceId);
    
    AfsserviceFreightmessageGetResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongAfsserviceRefundInfoGet(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
   */
  @Override
  public AfsserviceRefundinfoGetResponse jingdongAfsserviceRefundInfoGet(String appKey,
      String appSecret, String accessToken, Integer afsserviceId) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    AfsserviceRefundinfoGetRequest request = new AfsserviceRefundinfoGetRequest();
    request.setAfsServiceId(afsserviceId);
    
    AfsserviceRefundinfoGetResponse response = client.execute(request); 
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongAfsserviceInfoGet(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
   */
  @Override
  public AfsserviceServiceinfoGetResponse jingdongAfsserviceInfoGet(String appKey, String appSecret,
      String accessToken, Long afsserviceId) throws JdException, IOException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongWareUpdateListing(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public WareUpdateListingResponse jingdongWareUpdateListing(String appKey, String appSecret,
      String accessToken, String wareId, String tradeNo) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    WareUpdateListingRequest request = new WareUpdateListingRequest();
    request.setWareId(wareId);
    if (StringTool.isNotEmpty(tradeNo)) {
      request.setTradeNo(tradeNo);
    }
    
    WareUpdateListingResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongWareUpdateDelisting(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public WareUpdateDelistingResponse jingdongWareUpdateDelisting(String appKey, String appSecret,
      String accessToken, String wareId, String tradeNo) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    WareUpdateDelistingRequest request = new WareUpdateDelistingRequest();
    request.setWareId(wareId);
    if (StringTool.isNotEmpty(tradeNo)) {
      request.setTradeNo(tradeNo);
    }
    
    WareUpdateDelistingResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongWareGet(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public WareGetResponse jingdongWareGet(String appKey, String appSecret, String accessToken,
      String wareId, String fields) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    WareGetRequest request = new WareGetRequest();
    request.setWareId(wareId);
    if (StringTool.isNotEmpty(fields)) {
      request.setFields(fields);
    }
    
    WareGetResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongWaresListGet(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public WareListResponse jingdongWaresListGet(String appKey, String appSecret, String accessToken,
      String wareIds, String fields) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    WareListRequest request = new WareListRequest();
    request.setWareIds(wareIds);
    if (StringTool.isNotEmpty(fields)) {
      request.setFields(fields);
    }    
    WareListResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongWaresSearch(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public WareInfoByInfoSearchResponse jingdongWaresSearch(String appKey, String appSecret,
      String accessToken, String cid, String startPrice, String endPrice, Integer page,
      Integer pageSize, String title, String orderBy, String startTime, String endTime,
      String startModified, String endModified, String wareStatus, String fields,
      String parentShopCategoryId, String shopCategoryId, String itemNum)
          throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    WareInfoByInfoRequest request = new WareInfoByInfoRequest();
    if (StringTool.isNotEmpty(cid)) {
      request.setCid(cid);
    }
    if (StringTool.isNotEmpty(startPrice)) {
      request.setStartPrice(startPrice);
    }
    if (StringTool.isNotEmpty(endPrice)) {
      request.setEndPrice(endPrice);
    }
    request.setPage(page + StringTool.EMPTY);
    request.setPageSize(pageSize + StringTool.EMPTY);
    if (StringTool.isNotEmpty(title)) {
      request.setTitle(title);
    }
    if (StringTool.isNotEmpty(orderBy)) {
      request.setOrderBy(orderBy);
    }
    if (StringTool.isNotEmpty(startTime)) {
      request.setStartTime(startTime);
    }
    if (StringTool.isNotEmpty(endTime)) {
      request.setEndTime(endTime);
    }
    if (StringTool.isNotEmpty(startModified)) {
      request.setStartModified(startModified);
    }
    if (StringTool.isNotEmpty(endModified)) {
      request.setEndModified(endModified);
    }
    if (StringTool.isNotEmpty(wareStatus)) {
      request.setWareStatus(wareStatus);
    }
    if (StringTool.isNotEmpty(fields)) {
      request.setFields(fields);
    }
    if (StringTool.isNotEmpty(parentShopCategoryId)) {
      request.setParentShopCategoryId(parentShopCategoryId);
    }
    if (StringTool.isNotEmpty(shopCategoryId)) {
      request.setShopCategoryId(shopCategoryId);
    }
    if (StringTool.isNotEmpty(itemNum)) {
      request.setItemNum(itemNum);
    }

    WareInfoByInfoSearchResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongSkuStockUpdate(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public WareSkuStockUpdateResponse jingdongSkuStockUpdate(String appKey, String appSecret,
      String accessToken, String skuId, String outerId, String quantity, String tradeNo,
      String storeId) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    WareSkuStockUpdateRequest request = new WareSkuStockUpdateRequest();
    if (StringTool.isNotEmpty(skuId)) {
      request.setSkuId(skuId);
    }
    if (StringTool.isNotEmpty(outerId)) {
      request.setOuterId(outerId);
    }
    request.setQuantity(quantity);
    if (StringTool.isNotEmpty(tradeNo)) {
      request.setTradeNo(tradeNo);
    }
    if (StringTool.isNotEmpty(storeId)) {
      request.setStoreId(storeId);
    }
    
    WareSkuStockUpdateResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongEtmsWaybillcodeGet(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
   */
  @Override
  public EtmsWaybillcodeGetResponse jingdongEtmsWaybillcodeGet(String appKey, String appSecret,
      String accessToken, String preNum, String customerCode, Integer orderType)
          throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    EtmsWaybillcodeGetRequest request = new EtmsWaybillcodeGetRequest();
    request.setPreNum(preNum);
    request.setCustomerCode(customerCode);
    if (orderType != null) {
      request.setOrderType(orderType);
    }
    
    EtmsWaybillcodeGetResponse response = client.execute(request);
    
    return response;
  }

  /* (non-Javadoc)
   * @see com.digiwin.ecims.platforms.jingdong.service.JingdongApiService#jingdongEtmsWaybillSend(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.Double, java.lang.String, java.lang.Double, java.lang.Double, java.lang.Integer, java.lang.Double, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
   */
  @Override
  public EtmsWaybillSendResponse jingdongEtmsWaybillSend(String appKey, String appSecret,
      String accessToken, String deliveryId, String salPlat, String customerCode, String orderId,
      String thrOrderId, Integer selfPrintWayBill, String pickMethod, String packageRequired,
      String senderName, String senderAddress, String senderTel, String senderMobile,
      String senderPostcode, String receiverName, String receiverAddress, String receiverProvince,
      String receiverCity, String receiverCounty, String receiverTown, Integer receiverProinceId,
      Integer receiverCityId, Integer receiverCountyId, Integer receiverTownId, Integer siteType,
      Integer siteId, String siteName, String receiverTel, String receiverMobile,
      String receiverPostcode, Integer packageCount, Double weight, Double volumnLong,
      Double volumnWidth, Double volumnHeight, Double volumn, String description,
      Integer collectionValue, Double collectionMoney, Integer guaranteeValue,
      Double guaranteeValueAmount, Integer signReturn, Integer aging, Integer transType,
      String remark, Integer goodsType, Integer orderType, String shopCode, String orderSendTime,
      String warehouseCode, String extendField1, String extendField2, String extendField3,
      Integer extendField4, Integer extendField5) throws JdException, IOException {
    JdClient client = JingdongClientUtil.getInstance()
        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
            appKey, appSecret, accessToken);
    EtmsWaybillSendRequest request = new EtmsWaybillSendRequest();
    request.setDeliveryId(deliveryId);
    request.setSalePlat(salPlat);
    request.setCustomerCode(customerCode);
    request.setOrderId(orderId);
    if (StringTool.isNotEmpty(thrOrderId)) {
      request.setThrOrderId(thrOrderId);
    }
    if (StringTool.isNotEmpty(selfPrintWayBill)) {
      request.setSelfPrintWayBill(selfPrintWayBill);
    }
    if (StringTool.isNotEmpty(pickMethod)) {
      request.setPickMethod(pickMethod);
    }
    if (StringTool.isNotEmpty(packageRequired)) {
      request.setPackageRequired(packageRequired);
    }
    request.setSenderName(senderName);
    request.setSenderAddress(senderAddress);
    if (StringTool.isNotEmpty(senderTel)) {
      request.setSenderTel(senderTel);
    }
    if (StringTool.isNotEmpty(senderMobile)) {
      request.setSenderMobile(senderMobile);
    }
    if (StringTool.isNotEmpty(senderPostcode)) {
      request.setSenderPostcode(senderPostcode);
    }
    request.setReceiveName(receiverName);
    request.setReceiveAddress(receiverAddress);
    if (StringTool.isNotEmpty(receiverProvince)) {
      request.setProvince(receiverProvince);
    }
    if (StringTool.isNotEmpty(receiverCity)) {
      request.setCity(receiverCity);
    }
    if (StringTool.isNotEmpty(receiverCounty)) {
      request.setCounty(receiverCounty);
    }
    if (StringTool.isNotEmpty(receiverTown)) {
      request.setTown(receiverTown);
    }
    if (StringTool.isNotEmpty(receiverProinceId)) {
      request.setProvinceId(receiverProinceId);
    }
    if (StringTool.isNotEmpty(receiverCityId)) {
      request.setCityId(receiverCityId);
    }
    if (StringTool.isNotEmpty(receiverCountyId)) {
      request.setCountyId(receiverCountyId);
    }
    if (StringTool.isNotEmpty(receiverTownId)) {
      request.setTownId(receiverTownId);
    }
    if (StringTool.isNotEmpty(siteType)) {
      request.setSiteType(siteType);
    }
    if (StringTool.isNotEmpty(siteId)) {
      request.setSiteId(siteId);
    }
    if (StringTool.isNotEmpty(siteName)) {
      request.setSiteName(siteName);
    }
    if (StringTool.isNotEmpty(receiverTel)) {
      request.setReceiveTel(receiverTel);
    }
    if (StringTool.isNotEmpty(receiverMobile)) {
      request.setReceiveMobile(receiverMobile);
    }
    if (StringTool.isNotEmpty(receiverPostcode)) {
      request.setPostcode(receiverPostcode);
    }
    request.setPackageCount(packageCount);
    request.setWeight(weight);
    if (StringTool.isNotEmpty(volumnLong)) {
      request.setVloumLong(volumnLong);
    }
    if (StringTool.isNotEmpty(volumnWidth)) {
      request.setVloumWidth(volumnWidth);
    }
    if (StringTool.isNotEmpty(volumnHeight)) {
      request.setVloumHeight(volumnHeight);
    }
    request.setVloumn(volumn);
    if (StringTool.isNotEmpty(description)) {
      request.setDescription(description);
    }
    if (StringTool.isNotEmpty(collectionValue)) {
      request.setCollectionValue(collectionValue);
    }
    if (StringTool.isNotEmpty(collectionMoney)) {
      request.setCollectionMoney(collectionMoney);
    }
    if (StringTool.isNotEmpty(guaranteeValue)) {
      request.setGuaranteeValue(guaranteeValue);
    }
    if (StringTool.isNotEmpty(guaranteeValueAmount)) {
      request.setGuaranteeValueAmount(guaranteeValueAmount);
    }
    if (StringTool.isNotEmpty(signReturn)) {
      request.setSignReturn(signReturn);
    }
    if (StringTool.isNotEmpty(aging)) {
      request.setAging(aging);
    }
    if (StringTool.isNotEmpty(transType)) {
      request.setTransType(transType);
    }
    if (StringTool.isNotEmpty(remark)) {
      request.setRemark(remark);
    }
    if (StringTool.isNotEmpty(goodsType)) {
      request.setGoodsType(goodsType);
    }
    if (StringTool.isNotEmpty(orderType)) {
      request.setOrderType(orderType);
    }
    if (StringTool.isNotEmpty(shopCode)) {
      request.setShopCode(shopCode);
    }
    if (StringTool.isNotEmpty(orderSendTime)) {
      request.setOrderSendTime(orderSendTime);
    }
    if (StringTool.isNotEmpty(warehouseCode)) {
      request.setWarehouseCode(warehouseCode);
    }
    if (StringTool.isNotEmpty(extendField1)) {
      request.setExtendField1(extendField1);
    }
    if (StringTool.isNotEmpty(extendField2)) {
      request.setExtendField2(extendField2);
    }
    if (StringTool.isNotEmpty(extendField3)) {
      request.setExtendField3(extendField3);
    }
    if (StringTool.isNotEmpty(extendField4)) {
      request.setExtendField4(extendField4);
    }
    if (StringTool.isNotEmpty(extendField5)) {
      request.setExtendField5(extendField5);
    }
    
    EtmsWaybillSendResponse response = client.execute(request);
    
    return response;
  }
  
  //addby cs at 20170411
  @Override
  public WarePriceGetResponse jingdongWarePriceGet(String appKey, String appSecret, String accessToken, String skuId)
		throws JdException, IOException {
	 	JdClient client = JingdongClientUtil.getInstance()
		        .getJdClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.JINGDONG_API), 
		            appKey, appSecret, accessToken);
	 		WarePriceGetRequest request = new WarePriceGetRequest();
	 		request.setSkuId(skuId);
		        
		    WarePriceGetResponse response = client.execute(request);
		    
		    return response;
	}

}
