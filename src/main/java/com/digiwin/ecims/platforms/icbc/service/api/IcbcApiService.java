package com.digiwin.ecims.platforms.icbc.service.api;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.platforms.icbc.bean.base.Products;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductdetail.IcbcProductDetailResponse;
import com.digiwin.ecims.platforms.icbc.bean.item.icbcproductlist.IcbcProductListResponse;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderdetail.IcbcOrderDetailResponse;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcorderlist.IcbcOrderListResponse;
import com.digiwin.ecims.platforms.icbc.bean.order.icbcordersendmess.IcbcOrderSendmessResponse;
import com.digiwin.ecims.platforms.icbc.bean.refund.icbcrefundquery.IcbcRefundQueryResponse;
import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.system.enums.UseTimeEnum;

/**
 * @author 维杰
 * @since 2015.07.22
 */
public interface IcbcApiService extends EcImsApiService {

  /*
   * ****************************************
   * *           IcbcApiEncapsulate         *
   * ****************************************
   * 工商银行API简易封装
   */
  
  /**
   * 订单列表查询接口(调用频率: 2分/次)
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param createStartTime
   * @param createEndTime
   * @param modifyTimeFrom
   * @param modifyTimeTo
   * @param orderStatus
   * @return
   * @throws Exception
   */
  IcbcOrderListResponse icbcb2cOrderList(
      String appKey, String appSecret, String accessToken, 
      String createStartTime, String createEndTime,
      String modifyTimeFrom, String modifyTimeTo, 
      Integer orderStatus) throws Exception;
  
//  
//  /**
//   * 取時間區間內訂單列表(調用頻率: 2分/次)
//   * 
//   * @param aomsshopT 商店授權
//   * @param sDate 時間起始值
//   * @param eDate 時間結束值
//   * @param orderUseTime 時間類型 1-創建時間; 2-修改時間
//   * @return response
//   * @throws Exception
//   */
//  public IcbcOrderListResponse getOrderList(AomsshopT aomsshopT, String sDate, String eDate,
//      UseTimeEnum orderUseTime, String scheduleType) throws Exception;


  /**
   * 订单详情查询接口.<br/>
   * 订单查询只返回创建时间在6个月内订单。不支持历史订单查询.<br/>
   * 建议单次请求订单数最大为20.
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderIds
   * @return
   * @throws Exception
   */
  IcbcOrderDetailResponse icbcb2cOrderDetail(
      String appKey, String appSecret, String accessToken, 
      String orderIds) throws Exception;
  
//  /**
//   * 取訂單詳情(建議單次請求商品數量最大為20)
//   * 
//   * @param aomsshopT 商店授權
//   * @param orderIds 訂單ID列表(字串長度不可超過512...否則會出錯)
//   * @return response
//   * @throws Exception
//   */
//  public IcbcOrderDetailResponse getOrdersDetail(AomsshopT aomsshopT, String orderIds,
//      String scheduleType) throws Exception;

  /**
   * 退款申请查询接口
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param createStartTime
   * @param createEndTime
   * @param refundStatus
   * @param orderId
   * @return
   * @throws Exception
   */
  IcbcRefundQueryResponse icbcb2cRefundQuery(
      String appKey, String appSecret, String accessToken, 
      String createStartTime, String createEndTime, 
      String refundStatus, String orderId) throws Exception;
  
  /**
   * 商品列表查询接口
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param createStartTime
   * @param createEndTime
   * @param modifyTimeFrom
   * @param modifyTimeTo
   * @param putTimeFrom
   * @param putTimeTo
   * @param productStatus
   * @return
   * @throws Exception
   */
  IcbcProductListResponse icbcb2cProductList(
      String appKey, String appSecret, String accessToken,
      String createStartTime, String createEndTime, 
      String modifyTimeFrom, String modifyTimeTo, 
      String putTimeFrom, String putTimeTo, 
      Integer productStatus) throws Exception;
  
  /**
   * 商品详情查询接口.<br/>
   * 建议单次请求商品数最大为20.
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productIds
   * @return
   * @throws Exception
   */
  IcbcProductDetailResponse icbcb2cProductDetail(
      String appKey, String appSecret, String accessToken, 
      String productIds) throws Exception;
  
  
  /**
   * 订单发货通知接口
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderId
   * @param logisticsCompany
   * @param shippingCode
   * @param shippingTime
   * @param shippingUser
   * @param notes
   * @param products
   * @return
   * @throws Exception
   */
  IcbcOrderSendmessResponse icbcb2cOrderSendmess(
      String appKey, String appSecret, String accessToken, 
      String orderId, String logisticsCompany, String shippingCode, 
      String shippingTime, String shippingUser, String notes, Products products) throws Exception;
  
}
