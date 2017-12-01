package com.digiwin.ecims.platforms.pdd.service.api;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.platforms.pdd.bean.response.SendGoodsResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.item.GetGoodsResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.item.SynSkuStockResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.pdd.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.refund.RefundGetResponse;

public interface PddApiService extends EcImsApiService {

  /*
   * ****************************************
   * *            PddApiEncapsulate         *
   * ****************************************
   * 拼多多API简易封装
   */
  
  /**
   * 订单列表查询接口
   * http://mms.yangkeduo.com/assets/commonapi.html#h2_4
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderStatus 状态有3种。1，表示已付款；0，表示未付款；-1表示问题单（目前只支持抓取状态为1的订单，即：已付款待发货订单）
   * @param beginTime 标准时间戳,用于根据订单下单时间过滤待发货订单
   * @param endTime 标准时间戳,用于根据订单下单时间过滤待发货订单
   * @param page 用于查询要查询的页码 默认0，页码从0开始
   * @param pageSize 用于查询返回每页的订单数量，默认100
   * @return
   * @throws Exception
   */
  OrderSearchResponse pddOrderSearch(
      String appKey, String appSecret, String accessToken, 
      Integer orderStatus, String beginTime, String endTime, 
      Integer page, Integer pageSize) throws Exception;
  
  /**
   * 订单详细信息接口
   * http://mms.yangkeduo.com/assets/commonapi.html#h2_5
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderSN
   * @return
   * @throws Exception
   */
  OrderGetResponse pddOrderGet(
      String appKey, String appSecret, String accessToken,
      String orderSN) throws Exception;
  
  
  /**
   * 发货通知拼多多接口
   * http://mms.yangkeduo.com/assets/commonapi.html#h2_6
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderSN
   * @param sndStyle （中文 例：申通 圆通等）
   * @param consignTime 标准时间戳
   * @param billID
   * @return
   * @throws Exception
   */
  SendGoodsResponse pddSndGoods(
      String appKey, String appSecret, String accessToken, 
      String orderSN, String sndStyle, String consignTime, String billID) throws Exception;
  
  
  /**
   * 修改拼多多商品库存
   * http://mms.yangkeduo.com/assets/commonapi.html#h2_8
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param pddSkuId
   * @param outerSkuIds
   * @param quantity
   * @return
   * @throws Exception
   */
  SynSkuStockResponse pddSynSkuStock(
      String appKey, String appSecret, String accessToken,
      String pddSkuId, String outerSkuIds, Long quantity) throws Exception;
  
  /**
   * 获得商品档案
   * http://mms.yangkeduo.com/assets/commonapi.html#h2_12
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param goodsType 该值为“Onsale”或者“InStock”，分别是代表“在售”和“下架”。
   * @param outerID
   * @param goodsName
   * @param page
   * @param pageSize
   * @return
   * @throws Exception
   */
  GetGoodsResponse pddGetGoods(
      String appKey, String appSecret, String accessToken, 
      String goodsType, String outerID, String goodsName, 
      Integer page, Integer pageSize) throws Exception;
  
  /**
 	 * add by cjp
 	 * 
 	 * @param appKey
 	 * @param appSecret
 	 * @param accessToken
 	 * @param afterSalesType
 	 * @param afterSalesStatus
 	 * @param startUpdatedAt
 	 * @param endUpdatedAt
 	 * @return
 	 * @throws Exception
 	 */
 	RefundGetResponse pddRefundGet(String appKey, String appSecret, String accessToken, String afterSalesType,
 			String afterSalesStatus, String startUpdatedAt, String endUpdatedAt ,Integer page, Integer pageSize) throws Exception;
}
