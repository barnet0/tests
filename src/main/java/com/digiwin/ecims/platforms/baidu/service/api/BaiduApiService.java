package com.digiwin.ecims.platforms.baidu.service.api;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.domain.enums.QueryTimeType;
import com.digiwin.ecims.platforms.baidu.bean.domain.order.DeliveryGoodsItem;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfo.GetItemInfoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos.GetItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos.QueryItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.findorder.FindOrderResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getdetail.GetDetailResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.getinvoice.GetInvoiceResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.order.sendgoodbymerchant.SendGoodByMerchantResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.findrefundorderiteminfo.FindRefundOrderItemInfoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.refund.getrefundinfobyno.GetRefundInfoByNoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;
import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.model.AomsshopT;

public interface BaiduApiService extends EcImsApiService {

  Response<FindOrderResponse> baiduMallOrdersFind(AomsshopT aomsshopT,
      List<Integer> orderStatusList, QueryTimeType queryTimeType, String startTime, String endTime,
      int pageNo, int pageSize) throws Exception;

  Response<FindOrderResponse> baiduMallOrdersFind(
      String appKey, String appSecret, String accessToken,
      List<Integer> orderStatusList, QueryTimeType queryTimeType, String startTime, String endTime,
      int pageNo, int pageSize) throws Exception;
  
  Response<GetDetailResponse> baiduMallOrderDetailGet(AomsshopT aomsshopT, long orderId)
      throws Exception;
  
  Response<GetDetailResponse> baiduMallOrderDetailGet(
      String appKey, String appSecret, String accessToken, long orderId) 
          throws Exception;

  Response<FindRefundOrderItemInfoResponse> baiduMallRefundsReceiveGet(AomsshopT aomsshopT,
      List<Integer> refundStatusList, String startTime, String refundNo, String orderNo,
      String mobileNo, int pageNo, int pageSize) throws Exception;
  
  Response<FindRefundOrderItemInfoResponse> baiduMallRefundsReceiveGet(
      String appKey, String appSecret, String accessToken,
      List<Integer> refundStatusList, String startTime, String refundNo, String orderNo,
      String mobileNo, int pageNo, int pageSize) throws Exception;

  Response<GetRefundInfoByNoResponse> baiduMallRefundGet(AomsshopT aomsshopT, String refundNo)
      throws Exception;
  
  Response<GetRefundInfoByNoResponse> baiduMallRefundGet(
      String appKey, String appSecret, String accessToken, String refundNo) 
          throws Exception;

  Response<GetItemInfoResponse> baiduMallItemGet(AomsshopT aomsshopT, String itemId,
      List<String> fields) throws Exception;

  Response<GetItemInfosResponse> baiduMallItemGetList(AomsshopT aomsshopT, List<Long> itemIds,
      List<String> fields) throws Exception;

  Response<QueryItemInfosResponse> baiduMallItemQuery(AomsshopT aomsshopT,
      List<Integer> categoryIds, int pageNum, int pageSize) throws Exception;

  Response<SendGoodByMerchantResponse> baiduMallOrderSendGoodsByMerchant(AomsshopT aomsshopT,
      List<DeliveryGoodsItem> deliveryItems) throws Exception;

  Response<GetInvoiceResponse> baiduMallOrderInvoiceGet(AomsshopT aomsshopT, Long orderNo)
      throws Exception;

}
