package com.digiwin.ecims.platforms.suning.service.api;

import com.suning.api.entity.inventory.InventoryModifyResponse;
import com.suning.api.entity.item.ItemQueryResponse;
import com.suning.api.entity.item.ItemdetailQueryResponse;
import com.suning.api.entity.item.ItemsaleQueryResponse;
import com.suning.api.entity.item.ItemsaleQueryResponse.ItemSaleParams;
import com.suning.api.entity.item.ShelvesAddResponse;
import com.suning.api.entity.item.ShelvesMoveResponse;
import com.suning.api.entity.rejected.BatchrejectedOrdQueryResponse;
import com.suning.api.entity.rejected.BatchrejectedQueryResponse;
import com.suning.api.entity.rejected.SinglerejectedGetResponse;
import com.suning.api.entity.transaction.OrdQueryResponse;
import com.suning.api.entity.transaction.OrderGetResponse;
import com.suning.api.entity.transaction.OrderQueryResponse;
import com.suning.api.entity.transaction.OrdercodeQueryResponse;
import com.suning.api.entity.transaction.OrderdeliveryAddResponse;
import com.suning.api.exception.SuningApiException;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.model.AomsshopT;

public interface SuningApiService extends EcImsApiService {

  /*
   * ****************************************
   * *          SuningApiEncapsulate        *
   * ****************************************
   * 苏宁API简易封装
   */
  
  
  /**
   * suning.custom.order.get 单笔订单查询
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.order.get&interTypePageId=5<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderCode
   * @return
   * @throws Exception
   */
  OrderGetResponse suningCustomOrderGet(
      String appKey, String appSecret, String accessToken, 
      String orderCode) throws SuningApiException;
  
  
  /**
   * suning.custom.ord.query 根据订单修改时间批量查询订单信息
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.ord.query&interTypePageId=5<br>
   * 查询修结束时间-查询修改开始时间<=1天,<br>
   * 通过此接口可以查询到时间范围在当前时间往前三个月的时间内
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param startTime
   * @param endTime
   * @param orderLineStatus 订单行项目状态 10待发货，20已发货，30交易成功
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  OrdQueryResponse suningCustomOrdQuery(
      String appKey, String appSecret, String accessToken, 
      String startTime, String endTime, String orderLineStatus, 
      Integer pageNo, Integer pageSize) throws SuningApiException;
  
  /**
   * suning.custom.order.query 批量获取订单（三个月内的订单）
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.order.query&interTypePageId=5<br/>
   * 查询时交易开始时间和交易结束时间间隔不能超过30天.<br>
   * 能够查询到时间范围在当前时间往前三个月的时间内
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param startTime
   * @param endTime
   * @param orderStatus 订单状态 10待发货，20已发货，21部分发货，30交易成功 ，40交易关闭
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  OrderQueryResponse suningCustomOrderQuery(
      String appKey, String appSecret, String accessToken,
      String startTime, String endTime, String orderStatus, 
      Integer pageNo, Integer pageSize) throws SuningApiException;
   
  
  /**
   * suning.custom.orderdelivery.add 订单发货
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.orderdelivery.add&interTypePageId=5
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderCode
   * @param expressNo
   * @param expressCompanyCode
   * @param deliveryTime
   * @param phoneIdentityCode
   * @param productCodes
   * @param orderLineNumbers
   * @return
   * @throws Exception
   */
  OrderdeliveryAddResponse suningCustomOrderDeliveryAdd(
      String appKey, String appSecret, String accessToken,
      String orderCode, String expressNo, String expressCompanyCode, 
      String deliveryTime, String phoneIdentityCode, 
      String[] productCodes, String[] orderLineNumbers) throws SuningApiException;
  
  /**
   * suning.custom.ordercode.query 批量获取订单号
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.ordercode.query&interTypePageId=5<br>
   * 查询时交易开始时间和交易结束时间间隔不能超过30天；
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param startTime
   * @param endTime
   * @param orderStatus
   * @return
   * @throws Exception
   */
  OrdercodeQueryResponse suningCustomOrderCodeQuery(
      String appKey, String appSecret, String accessToken,
      String startTime, String endTime, String orderStatus) throws SuningApiException;
  
  /**
   * suning.custom.batchrejected.query 批量获取退货信息
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.batchrejected.query&interTypePageId=6<br>
   * 通过此接口可批量获取三个月内的退货信息。开始时间与结束时间间隔不超过30天；
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param startTime
   * @param endTime
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  BatchrejectedQueryResponse suningCustomBatchRejectedQuery(
      String appKey, String appSecret, String accessToken,
      String startTime, String endTime, 
      Integer pageNo, Integer pageSize) throws SuningApiException;
  
  /**
   * suning.custom.batchrejectedOrd.query 批量获取退货订单号
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.batchrejectedOrd.query&interTypePageId=6<br>
   * 通过此接口可批量获取三个月内的退货订单号.开始时间与结束时间间隔不超过30天
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param startTime
   * @param endTime
   * @return
   * @throws Exception
   */
  BatchrejectedOrdQueryResponse suningCustomBatchRejectedOrdQuery(
      String appKey, String appSecret, String accessToken,
      String startTime, String endTime) throws SuningApiException;
  
  /**
   * suning.custom.singlerejected.get 单笔查询退货信息
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.singlerejected.get&interTypePageId=6<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderCode
   * @return
   * @throws Exception
   */
  SinglerejectedGetResponse suningCustomSingleRejectedGet(
      String appKey, String appSecret, String accessToken,
      String orderCode) throws SuningApiException;
  
  /**
   * suning.custom.item.query 获取我的商品库信息
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.item.query&interTypePageId=1<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param categoryCode
   * @param brandCode
   * @param status
   * @param startTime
   * @param endTime
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  ItemQueryResponse suningCustomItemQuery(
      String appKey, String appSecret, String accessToken,
      String categoryCode, String brandCode, String status,
      String startTime, String endTime,
      Integer pageNo, Integer pageSize) throws SuningApiException;
  
  /**
   * suning.custom.itemdetail.query 获取我的商品详情信息
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.itemdetail.query&interTypePageId=1<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productCode
   * @return
   * @throws Exception
   */
  ItemdetailQueryResponse suningCustomItemDetailQuery(
      String appKey, String appSecret, String accessToken,
      String productCode) throws SuningApiException;
  
  /**
   * suning.custom.itemsale.query 商品销售情况查询
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.itemsale.query&interTypePageId=1<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productName
   * @param productCode
   * @param categoryCode
   * @param priceUpper
   * @param priceLimit
   * @param saleStatus
   * @param pageNo
   * @param pageSize
   * @param cmTitle
   * @return
   * @throws Exception
   */
  ItemsaleQueryResponse suningCustomItemSaleQuery(
      String appKey, String appSecret, String accessToken,
      String productName, String productCode, String categoryCode,
      String priceUpper, String priceLimit, String saleStatus, 
      Integer pageNo, Integer pageSize, String cmTitle) throws SuningApiException;
  
  /**
   * suning.custom.shelves.add 商品上架
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.shelves.add&interTypePageId=1<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productCode
   * @return
   * @throws SuningApiException
   */
  ShelvesAddResponse suningCustomShelvesAdd(
      String appKey, String appSecret, String accessToken,
      String productCode) throws SuningApiException;
  
  /**
   * suning.custom.shelves.move 商品下架
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.shelves.move&interTypePageId=1<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productCode
   * @return
   * @throws SuningApiException
   */
  ShelvesMoveResponse suningCustomShelvesMove(
      String appKey, String appSecret, String accessToken,
      String productCode) throws SuningApiException;
  
  /**
   * suning.custom.inventory.modify 库存修改(单个)
   * http://open.suning.com/ospos/apipage/toApiMethodDetailMenu.do?interCode=suning.custom.inventory.modify&interTypePageId=3<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productCode
   * @param itemCode
   * @param invAddrId
   * @param destInvNum
   * @param invType
   * @return
   * @throws SuningApiException
   */
  InventoryModifyResponse suningCustomInventoryModify(
      String appKey, String appSecret, String accessToken,
      String productCode, String itemCode, String invAddrId,
      String destInvNum, String invType) throws SuningApiException;
  
  /**
   * 取得 商品销售情况<br/>
   * http://open.suning.com/api/view/api.htm?mid=100000194&btype=platform&tid=100000
   * 
   * @param aomsshopT
   * @param productCode
   * @throws Exception
   */
  public ItemSaleParams getItemSaleQuery(AomsshopT aomsshopT, String productCode,
      String scheduleType) throws Exception;

}
