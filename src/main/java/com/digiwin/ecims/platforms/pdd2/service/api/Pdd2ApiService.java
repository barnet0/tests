package com.digiwin.ecims.platforms.pdd2.service.api;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.platforms.pdd2.bean.response.SendGoodsResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetGoodsInfoResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetGoodsResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.GetSkuStockResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.item.SynSkuStockResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.CheckOrdersAfterSaleResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderIncrementSearchResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderInfoGetResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.pdd2.bean.response.refund.RefundGetResponse;

public interface Pdd2ApiService extends EcImsApiService {

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
   * @param page 用于查询要查询的页码 默认0，页码从0开始
   * @param pageSize 用于查询返回每页的订单数量，默认100
   * @return
   * @throws Exception
   */
  OrderSearchResponse pddOrderSearch(
      String appKey, String appSecret, String accessToken, 
      Integer orderStatus, 
      Integer page, Integer pageSize) throws Exception;
  
  /**
   * 增量订单列表查询接口
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param isLuckyFlag 1:非抽奖订单 2：抽奖订单
   * @param OrderStatus 订单状态，1：待发货，2：已发货待签收，3：已签收 5：全部（目前只支持抓取状态为1的订单，即：已付款待发货订单）
   * @param refund_status 售后状态  1：无售后或售后关闭，2：售后处理中，3：退款中，4：退款成功 5：全部
   * @param beginTime 标准时间戳,用于根据订单下单时间过滤待发货订单
   * @param endTime 标准时间戳,用于根据订单下单时间过滤待发货订单
   * @param page 用于查询要查询的页码 默认0，页码从0开始
   * @param pageSize 用于查询返回每页的订单数量，默认100
   * @return
   * @throws Exception
   */
  public OrderIncrementSearchResponse pddOrderIncrementSearch(String appKey, String appSecret, String accessToken,
	      Integer isLuckyFlag,Integer OrderStatus,Integer refund_status,
		String beginTime, String endTime, Integer page, Integer pageSize) throws Exception;
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
  OrderInfoGetResponse pddOrderGet(
      String appKey, String appSecret, String accessToken,
      String orderSN) throws Exception;
  
  
  /**
   * 发货通知拼多多接口
   * http://mms.yangkeduo.com/assets/commonapi.html#h2_6
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderSN
   * @param logistics_id （快递公司编号）
   * @param tracking_number
   * @return
   * @throws Exception
   */
  SendGoodsResponse pddSndGoods(
      String appKey, String appSecret, String accessToken, 
      String orderSN, String logistics_id,String tracking_number) throws Exception;
  
  
  /**
   * 同量修改拼多多商品库存
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
   * @param outerID 商品外部编码  非必填
   * @param isOnsale 上下架状态 上架：1 下架：0 非必填
   * @param goodsName 商品名称模糊查询 非必填
   * @param page 非必填
   * @param pageSize 非必填
   * @return
   * @throws Exception
   */
  GetGoodsResponse pddGetGoods(
      String appKey, String appSecret, String accessToken, 
        String outerID, Integer isOnsale,String goodsName, 
      Integer page, Integer pageSize) throws Exception;
  /**
   * 获得商品详情档案
   * http://mms.yangkeduo.com/assets/commonapi.html#h2_12
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param goodsId
   * @return
   * @throws Exception
   */
  GetGoodsInfoResponse pddGetGoodsInfo(
      String appKey, String appSecret, String accessToken, 
      String goodsId) throws Exception;
  
  /**
   * 获取商品sku库存接口
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param pddSkuId
   * @param outerSkuIds
   * @return
   * @throws Exception
   */
  GetSkuStockResponse pddGetSkuStock(String appKey, String appSecret, String accessToken,
		  String pddSkuId, String outerSkuIds) throws Exception;
  /**
   * 校验售后单
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param order_sns
   * @return
   * @throws Exception
   */
  CheckOrdersAfterSaleResponse pddCheckOrdersAfterSale(String appKey, String appSecret, String accessToken,
		  String order_sns) throws Exception;
  
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
			String afterSalesStatus, String startUpdatedAt, String endUpdatedAt ,
			Integer page, Integer pageSize) throws Exception;
}
