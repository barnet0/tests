package com.digiwin.ecims.platforms.beibei.service.api;

import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemQtyUpdateResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.item.OuterItemWarehouseGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.logistics.OuterTradeLogisticsShipResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.order.OuterTradeOrderGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundDetailGetResponse;
import com.digiwin.ecims.platforms.beibei.bean.response.refund.OuterRefundsGetResponse;
import com.digiwin.ecims.core.api.EcImsApiService;

public interface BeibeiApiService extends EcImsApiService {

  /*
   * ****************************************
   * *            BeibeiApiEncapsulate      *
   * ****************************************
   * 贝贝网API简易封装
   */
  
  /**
   * beibei.outer.trade.order.get （订单列表）<br>
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.trade.order.get<br>
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param status
   * @param timeRange
   * @param startTime
   * @param endTime
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  OuterTradeOrderGetResponse beibeiOuterTradeOrderGet(
      String appKey, String appSecret, String accessToken,
      String status, String timeRange,
      String startTime, String endTime,
      Long pageNo, Long pageSize) throws Exception;
  
  /**
   * beibei.outer.trade.order.detail.get （订单详情）
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.trade.order.detail.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param oid
   * @return
   * @throws Exception
   */
  OuterTradeOrderDetailGetResponse beibeiOuterTradeOrderDetailGet(
      String appKey, String appSecret, String accessToken,
      String oid) throws Exception;
  
  
  /**
   * beibei.outer.refunds.get （售后列表）
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.refunds.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param timeRange
   * @param status
   * @param startTime
   * @param endTime
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  OuterRefundsGetResponse beibeiOuterRefundsGet(
      String appKey, String appSecret, String accessToken,
      String timeRange, String status,
      String startTime, String endTime,
      Long pageNo, Long pageSize) throws Exception;
  
  /**
   * beibei.outer.refund.get 售后详情
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.refund.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param oid
   * @param refundId
   * @return
   * @throws Exception
   */
  OuterRefundDetailGetResponse beibeiOuterRefundDetailGet(
      String appKey, String appSecret, String accessToken,
      String oid, String refundId) throws Exception;
  
  /**
   * beibei.outer.item.warehouse.get （商品库商品获取）
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.item.warehouse.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param pageNo
   * @param pageSize
   * @return
   * @throws Exception
   */
  OuterItemWarehouseGetResponse beibeiOuterItemWarehouseGet(
      String appKey, String appSecret, String accessToken,
      Long pageNo, Long pageSize) throws Exception;
  
  /**
   * beibei.outer.item.get （商品详情）
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.item.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param iid
   * @return
   * @throws Exception
   */
  OuterItemGetResponse beibeiOuterItemGet(
      String appKey, String appSecret, String accessToken,
      String iid) throws Exception;
  
  /**
   * beibei.outer.trade.logistics.ship （订单发货）
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.trade.logistics.ship
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param oid
   * @param company
   * @param outSid
   * @return
   * @throws Exception
   */
  OuterTradeLogisticsShipResponse beibeiOuterTradeLogisticsShip(
      String appKey, String appSecret, String accessToken,
      String oid, String company, String outSid) throws Exception;
  
  
  /**
   * beibei.outer.item.qty.update （库存更新）
   * http://open.beibei.com/document/doc_api_detail.html?api=beibei.outer.item.qty.update
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param iid
   * @param outerId
   * @param qty
   * @return
   * @throws Exception
   */
  OuterItemQtyUpdateResponse beibeiOuterItemQtyUpdate(
      String appKey, String appSecret, String accessToken,
      String iid, String outerId, String qty) throws Exception;
}
