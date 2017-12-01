package com.digiwin.ecims.platforms.feiniu.service.api;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.refund.MyFeiniuRefundGetDetailInfoResponse;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.returngoods.MyFeiniuOrderReverseDetailGetResponse;
import com.feiniu.open.api.sdk.response.item.*;
import com.feiniu.open.api.sdk.response.order.*;
import com.feiniu.open.api.sdk.response.refund.RefundsRreceiveNeedGetListResponse;
import com.feiniu.open.api.sdk.response.returngoods.OrderReverseGetResponse;

/**
 * Created by zaregoto on 2017/1/25.
 */
public interface FeiniuApiService extends EcImsApiService {

  /*
   * ****************************************
   * *           FeiniuApiEncapsulate       *
   * ****************************************
   * 飞牛网API简易封装
   */

    /**
     * fn.trades.sold.get 查询订单列表POST
     * http://open.feiniu.com/apiDetails.do?apiId=25
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param orderType 订单状态， 1：待付款/未付款 2：待发货/待发货 3：待收货/已发货 4：交易成功/已完成 5：已取消/已取消 6：已退款/退款完成 7：退货中/未发货用户申请退款 8：退货中/退货中 9：交易关闭/已取消（逾期未付款）10:申请报关，11:报关通过，12:申请通关，13:通关通过
     * @param dateStart
     * @param dateEnd
     * @param dateType 查询时间类型，1-订单生成时间、2-支付时间、3-发货时间、4-收货时间、5-取消时间、6-交易成功时间，7-最新更新时间
     * @param currPage
     * @param pageCount
     * @return
     * @throws Exception
     */
  TradeSoldGetResponse fnTradesSoldGet(
      String appKey, String appSecret, String accessToken,
      String orderType, String dateStart, String dateEnd, String dateType,
      Long currPage, Long pageCount) throws Exception;

    /**
     * fn.trade.fullinfo.get 查询单笔订单详情POST
     * http://open.feiniu.com/apiDetails.do?apiId=26
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param ogsSeq 子订单流水号（包裹编号）
     * @return
     * @throws Exception
     */
  TradeFullinfoGetResponse fnTradeFullInfoGet(
      String appKey, String appSecret, String accessToken,
      String ogsSeq) throws Exception;

    /**
     * fn.trades.sold.getOrderDetail 通过订单号查询订单详情
     * http://open.feiniu.com/apiDetails.do?apiId=27
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param ogNo 订单编号
     * @return
     * @throws Exception
     */
    TradeSoldGetOrderDetailResponse fnTradesSoldGetOrderDetail(
        String appKey, String appSecret, String accessToken,
        String ogNo) throws Exception;

    /**
     * fn.order.deliverByOgNo 订单发货POST(根据订单号)
     * http://open.feiniu.com/apiDetails.do?apiId=30
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param expressId 快递公司ID
     * @param expressNo 运单号
     * @param ogNo 订单编号
     * @return
     * @throws Exception
     */
    OrderDeliverByOgNoResponse fnOrderDeliverByOgNo(
        String appKey, String appSecret, String accessToken,
        String expressId, String expressNo, String ogNo) throws Exception;

    /**
     * fn.order.delivergoods 订单发货POST(根据包裹号)
     * http://open.feiniu.com/apiDetails.do?apiId=31
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param ogsSeq 子订单流水号（包裹编号）
     * @param expressNo 运单号
     * @param expressId 快递公司ID
     * @return
     * @throws Exception
     */
    OrderDelivergoodsResponse fnOrderDeliverGoods(
        String appKey, String appSecret, String accessToken,
        String ogsSeq, String expressNo, String expressId) throws Exception;

    /**
     * fn.refunds.receiveNeed.getList 查询所有需要退款的列表POST
     * http://open.feiniu.com/apiDetails.do?apiId=42
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param currPage 当前页
     * @param pageCount 每页条数
     * @param rssSeq 退货子单流水号
     * @param receiveName 收货人姓名
     * @param receivePhone 收货人手机号
     * @param submitDateStart 开始时间
     * @param submitDateEnd 结束时间
     * @param skuId 商品sku ID
     * @param name 商品名称
     * @param dateType 查询日期类型。1：退单时间 2：最新更新时间
     * @return
     * @throws Exception
     */
    RefundsRreceiveNeedGetListResponse fnRefundsReceiveNeedGetList(
        String appKey, String appSecret, String accessToken,
        Long currPage, Long pageCount,
        String rssSeq, String receiveName, String receivePhone,
        String submitDateStart, String submitDateEnd, String skuId, String name,
        String dateType) throws Exception;

    /**
     * fn.refund.getDetailInfo 查询单笔退款详情POST
     * http://open.feiniu.com/apiDetails.do?apiId=44
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param rssSeq 退货子单流水号
     * @return
     * @throws Exception
     */
    MyFeiniuRefundGetDetailInfoResponse fnRefundGetDetailInfo(
        String appKey, String appSecret, String accessToken,
        String rssSeq) throws Exception;

    /**
     * fn.order.reverse.get 获取退货订单列表接口POST
     * http://open.feiniu.com/apiDetails.do?apiId=47
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param dateType 时间类型：1.申请时间、2.完成时间
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param returnStatus 退货单子单状态(1,待审核 2退货申请未通过 3待买家寄回 4待卖家退款 5退款审核未通过
     *                     6退款中 7已退款 8退款完成,9用户取消，10申请仲裁，11仲裁成功)
     * @param skuId 商品skuId
     * @param currPage 当前页
     * @param pageCount 每页条数
     * @param isDesc 是否降序
     * @return
     * @throws Exception
     */
    OrderReverseGetResponse fnOrderReverseGet(
        String appKey, String appSecret, String accessToken,
        String dateType, String startDate, String endDate,
        String returnStatus, String skuId,
        Long currPage, Long pageCount,
        String isDesc) throws Exception;

    /**
     * fn.order.reverse.detail.get 获取退单详情接口POST
     * http://open.feiniu.com/apiDetails.do?apiId=48
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param rssSeq
     * @return
     * @throws Exception
     */
    MyFeiniuOrderReverseDetailGetResponse fnOrderReverseDetailGet(
        String appKey, String appSecret, String accessToken,
        String rssSeq) throws Exception;

    /**
     * fn.item.inventory.get 获取当前会话用户的商品列表
     * http://open.feiniu.com/apiDetails.do?apiId=10
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param status
     * @param title
     * @param merchantCodeStr
     * @param goodsId
     * @param curPage
     * @param pageRows
     * @return
     * @throws Exception
     */
    ItemInventoryGetResponse fnItemInventoryGet(
        String appKey, String appSecret, String accessToken,
        String status, String title,
        String merchantCodeStr, String goodsId,
        Long curPage, Long pageRows) throws Exception;

    /**
     * fn.item.get 查询一个商品POST
     * http://open.feiniu.com/apiDetails.do?apiId=8
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param goodsId
     * @return
     * @throws Exception
     */
    ItemGetResponse fnItemGet(
        String appKey, String appSecret, String accessToken,
        String fields, String goodsId) throws Exception;

    /**
     * fn.item.sku.update 商品SKU(价格，数量等)更新POST
     * http://open.feiniu.com/apiDetails.do?apiId=16
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param skuId 商品sku ID
     * @param price 价格
     * @param stock 库存
     * @param warehouseCode 仓库编码
     * @return
     * @throws Exception
     */
    ItemSkuUpdateResponse fnItemSkuUpdate(
        String appKey, String appSecret, String accessToken,
        String skuId, String price, String stock,
        String warehouseCode) throws Exception;

    /**
     * fn.item.sku.batchUpdate 批量更新商品库存
     * http://open.feiniu.com/apiDetails.do?apiId=17
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param skuIds Sku ID，最多20个
     * @param stocks 库存 ，最多20个
     * @param warehouseCodes 仓库编码，最多20个
     * @return
     * @throws Exception
     */
    ItemSkuBatchUpdateResponse fnItemSkuBatchUpdate(
        String appKey, String appSecret, String accessToken,
        String skuIds, String stocks, String warehouseCodes) throws Exception;

    /**
     * fn.item.update.listing 商品上架POST
     * http://open.feiniu.com/apiDetails.do?apiId=18
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param goodsId 商品ID
     * @return
     * @throws Exception
     */
    ItemUpdateListingResponse fnItemUpdateListing(
        String appKey, String appSecret, String accessToken,
        String goodsId) throws Exception;


    /**
     * fn.item.update.dellisting 商品下架POST
     * http://open.feiniu.com/apiDetails.do?apiId=19
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param goodsId 商品ID
     * @return
     * @throws Exception
     */
    ItemUpdateDellistingResponse fnItemUpdateDellisting(
        String appKey, String appSecret, String accessToken,
        String goodsId) throws Exception;

}

