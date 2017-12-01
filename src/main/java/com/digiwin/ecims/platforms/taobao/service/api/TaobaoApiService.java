package com.digiwin.ecims.platforms.taobao.service.api;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Area;
import com.taobao.api.response.*;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author zaregoto
 */
public interface TaobaoApiService extends EcImsApiService {

  /*
   * ****************************************
   * *        TaobaoApiEncapsulate          *
   * ****************************************
   * 淘宝API简易封装
   */

    /**
     * taobao.trades.sold.get (查询卖家已卖出的交易数据（根据创建时间）)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.SJyC8p&apiId=46<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param startCreated
     * @param endCreated
     * @param pageNo
     * @param pageSize
     * @param useHasNext
     * @return
     * @throws ApiException
     * @throws IOException
     */
    TradesSoldGetResponse taobaoTradesSoldGet(String appKey, String appSecret, String accessToken,
        String fields, Date startCreated, Date endCreated, Long pageNo, Long pageSize,
        Boolean useHasNext) throws ApiException, IOException;


    /**
     * taobao.trades.sold.increment.get (查询卖家已卖出的增量交易数据（根据修改时间）)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.xvWN9i&apiId=128<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param startModified
     * @param endModified
     * @param pageNo
     * @param pageSize
     * @param useHasNext
     * @return
     * @throws ApiException
     * @throws IOException
     */
    TradesSoldIncrementGetResponse taobaoTradesSoldIncrementGet(String appKey, String appSecret,
        String accessToken, String fields, Date startModified, Date endModified, Long pageNo,
        Long pageSize, Boolean useHasNext) throws ApiException, IOException;


    /**
     * taobao.trades.sold.incrementv.get (查询卖家已卖出的增量交易数据（根据入库时间）)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.xvWN9i&apiId=21470<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param startCreate
     * @param endCreate
     * @param pageNo
     * @param pageSize
     * @param useHasNext
     * @return
     * @throws ApiException
     * @throws IOException
     */
    TradesSoldIncrementvGetResponse taobaoTradesSoldIncrementvGet(String appKey, String appSecret,
        String accessToken, String fields, Date startCreate, Date endCreate, Long pageNo,
        Long pageSize, Boolean useHasNext) throws ApiException, IOException;

    /**
     * taobao.trade.fullinfo.get (获取单笔交易的详细信息)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.Ths9HN&apiId=54<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param tid
     * @return
     * @throws ApiException
     * @throws IOException
     */
    TradeFullinfoGetResponse taobaoTradeFullinfoGet(String appKey, String appSecret,
        String accessToken, String fields, Long tid) throws ApiException, IOException;

    /**
     * taobao.refunds.receive.get (查询卖家收到的退款列表)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.Yj5AoM&apiId=52<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param startModified
     * @param endModified
     * @param pageNo
     * @param pageSize
     * @param useHasNext
     * @return
     * @throws ApiException
     * @throws IOException
     */
    RefundsReceiveGetResponse taobaoRefundsReceiveGet(String appKey, String appSecret,
        String accessToken, String fields, Date startModified, Date endModified, Long pageNo,
        Long pageSize, Boolean useHasNext) throws ApiException, IOException;

    /**
     * taobao.refund.get (单笔退款详情)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.z8g5hZ&apiId=53<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param refundId
     * @return
     * @throws ApiException
     */
    RefundGetResponse taobaoRefundGet(String appKey, String appSecret, String accessToken,
        String fields, Long refundId) throws ApiException, IOException;

    /**
     * taobao.items.onsale.get (获取当前会话用户出售中的商品列表)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.F1Jyjd&apiId=18<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param orderBy       排序方式。格式为column:asc/desc 。<br>
     *                      column可选值:list_time(上架时间),delist_time(下架时间),num(商品数量)，modified(最近修改时间)，sold_quantity（商品销量）;
     *                      默认上架时间降序(即最新上架排在前面)。如按照上架时间降序排序方式为list_time:desc
     * @param startModified
     * @param endModified
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    ItemsOnsaleGetResponse taobaoItemsOnsaleGet(String appKey, String appSecret, String accessToken,
        String fields, String orderBy, Date startModified, Date endModified, Long pageNo,
        Long pageSize) throws ApiException, IOException;

    /**
     * taobao.items.inventory.get (得到当前会话用户库存中的商品列表)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.3kTxrq&apiId=162<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param orderBy       排序方式。格式为column:asc/desc 。<br>
     *                      column可选值:list_time(上架时间),delist_time(下架时间),num(商品数量)，modified(最近修改时间)，sold_quantity（商品销量）;
     *                      默认上架时间降序(即最新上架排在前面)。如按照上架时间降序排序方式为list_time:desc
     * @param startModified
     * @param endModified
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    ItemsInventoryGetResponse taobaoItemsInventoryGet(String appKey, String appSecret,
        String accessToken, String fields, String orderBy, Date startModified, Date endModified,
        Long pageNo, Long pageSize) throws ApiException, IOException;

    /**
     * taobao.item.seller.get (获取单个商品详细信息)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.MvnTKw&apiId=24625<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param numIid
     * @return
     */
    ItemSellerGetResponse taobaoItemSellerGet(String appKey, String appSecret, String accessToken,
        String fields, Long numIid) throws ApiException, IOException;

    /**
     * taobao.items.seller.list.get (批量获取商品详细信息)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.ofaFNW&apiId=24626<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param numIids     商品编码列表，以","分隔。最多20个
     * @return
     * @throws ApiException
     */
    ItemsSellerListGetResponse taobaoItemsSellerListGet(String appKey, String appSecret,
        String accessToken, String fields, String numIids) throws ApiException, IOException;

    /**
     * taobao.item.quantity.update (宝贝/SKU库存修改)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.C3ZmMM&apiId=10591<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param numIid
     * @param skuId
     * @param outerId
     * @param quantity
     * @param updateType
     * @return
     * @throws ApiException
     */
    ItemQuantityUpdateResponse taobaoItemQuantityUpdate(String appKey, String appSecret,
        String accessToken, Long numIid, Long skuId, String outerId, Long quantity, Long updateType)
        throws ApiException, IOException;

    /**
     * taobao.skus.quantity.update (SKU库存修改)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.Fyadtv&apiId=21169<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param numIid
     * @param updateType
     * @param skuidQuantities
     * @return
     * @throws ApiException
     */
    SkusQuantityUpdateResponse taobaoSkusQuantityUpdate(String appKey, String appSecret,
        String accessToken, Long numIid, Long updateType, String skuidQuantities)
        throws ApiException, IOException;

    /**
     * taobao.item.update.listing (一口价商品上架)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.JACIcV&apiId=32<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param numIid
     * @param num
     * @return
     * @throws ApiException
     */
    ItemUpdateListingResponse taobaoItemUpdateListing(String appKey, String appSecret,
        String accessToken, Long numIid, Long num) throws ApiException, IOException;

    /**
     * taobao.item.update.delisting (商品下架)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.jNl2Ek&apiId=31<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param numIid
     * @return
     * @throws ApiException
     */
    ItemUpdateDelistingResponse taobaoItemUpdateDelisting(String appKey, String appSecret,
        String accessToken, Long numIid) throws ApiException, IOException;

    /**
     * taobao.fenxiao.orders.get获取单笔采购单详情<br>
     * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.1998342952.1.1EfFJx&path=cid:15-apiId:180<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param status
     * @param startCreated    采购单查询的起始时间
     * @param endCreated      采购单查询的结束时间。采购单查询的起始时间与结束时间跨度不能超过7天
     * @param timeType
     * @param pageNo
     * @param pageSize
     * @param purchaseOrderId
     * @param fields
     * @return
     * @throws ApiException
     * @throws IOException
     */
    FenxiaoOrdersGetResponse taobaoFenxiaoOrdersGet(String appKey, String appSecret,
        String accessToken, String status, Date startCreated, Date endCreated, String timeType,
        Long pageNo, Long pageSize, Long purchaseOrderId, String fields)
        throws ApiException, IOException;

    /**
     * taobao.fenxiao.refund.query (批量查询采购退款)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.7yXIv1&apiId=23000<br>
     * 返回结果为修改时间的升序排列
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param startDate   代销采购退款单最早修改时间
     * @param endDate     代销采购退款最迟修改时间。与start_date的最大时间间隔不能超过30天
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     */
    FenxiaoRefundQueryResponse taobaoFenxiaoRefundQuery(String appKey, String appSecret,
        String accessToken, Date startDate, Date endDate, Long pageNo, Long pageSize)
        throws ApiException, IOException;

    /**
     * taobao.fenxiao.refund.get (查询采购单退款信息)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.HV2Qqi&apiId=21873<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param subOrderId
     * @return
     * @throws ApiException
     */
    FenxiaoRefundGetResponse taobaoFenxiaoRefundGet(String appKey, String appSecret,
        String accessToken, Long subOrderId) throws ApiException, IOException;

    /**
     * taobao.fenxiao.products.get (查询产品列表)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.KY8AtX&apiId=328<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param pids
     * @param fields
     * @param startModified
     * @param endModified
     * @param pageNo
     * @param pageSize
     * @return
     * @throws ApiException
     * @throws ParseException
     */
    FenxiaoProductsGetResponse taobaoFenxiaoProductsGet(String appKey, String appSecret,
        String accessToken, String pids, String fields, Date startModified, Date endModified,
        Long pageNo, Long pageSize) throws ApiException, IOException;

    /**
     * 使用API获取淘宝标准省市区地址
     *
     * @return 区域List
     * @throws ApiException
     */
    List<Area> taobaoLogisticsAreasGet() throws ApiException;

    /**
     * taobao.item.update (更新商品信息)<br>
     * http://open.taobao.com/docs/api.htm?spm=a219a.7629065.0.0.IGHGlI&apiId=21<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param numIid
     * @param picPath     图片路径
     * @return
     * @throws ApiException
     * @throws IOException
     */
    ItemUpdateResponse taobaoItemUpdate(String appKey, String appSecret, String accessToken,
        Long numIid, String picPath) throws ApiException, IOException;

    /**
     * taobao.fenxiao.product.update (更新产品)<br>
     * http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.0rfvKn&apiId=326<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param pid
     * @param quantity
     * @param status
     * @param skuIds
     * @param skuOuterIds
     * @param skuQuantitys
     * @return
     * @throws ApiException
     * @throws IOException
     */
    FenxiaoProductUpdateResponse taobaoFenxiaoProductUpdate(String appKey, String appSecret,
        String accessToken, Long pid, Long quantity, String status, String skuIds,
        String skuOuterIds, String skuQuantitys) throws ApiException, IOException;


    /**
     * taobao.logistics.offline.send (自己联系物流（线下物流）发货)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?apiId=10690<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param tid
     * @param outSid
     * @param companyCode
     * @param subTids
     * @param isSplit
     * @return
     * @throws ApiException
     * @throws IOException
     */
    LogisticsOfflineSendResponse taobaoLogisticsOfflineSend(String appKey, String appSecret,
        String accessToken, Long tid, String outSid, String companyCode, String subTids,
        boolean isSplit) throws ApiException, IOException;

    /**
     * taobao.logistics.online.send (在线订单发货处理（支持货到付款）)<br>
     * http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.l4OZwU&apiId=10687<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param tid
     * @param isSplit
     * @param outSid
     * @param companyCode
     * @param subTids     需要拆单发货的子订单集合1,2,3
     * @return
     * @throws ApiException
     */
    LogisticsOnlineSendResponse taobaoLogisticsOnlineSend(String appKey, String appSecret,
        String accessToken, Long tid, boolean isSplit, String outSid, String companyCode,
        String subTids) throws ApiException, IOException;


    /**
     * taobao.traderates.get (搜索评价信息)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.PWRCRV&apiId=55<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param fields
     * @param rateType
     * @param role
     * @param result
     * @param pageNo
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param tid
     * @param usehasNext
     * @param numIid
     * @return
     * @throws ApiException
     * @throws IOException
     */
    TraderatesGetResponse taobaoTraderatesGet(String appKey, String appSecret, String accessToken,
        String fields, String rateType, String role, String result, Long pageNo, Long pageSize,
        Date startDate, Date endDate, Long tid, Boolean useHasNext, Long numIid)
        throws ApiException, IOException;


    /**
     * taobao.logistics.trace.search (物流流转信息查询)<br>
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.QP1Bry&apiId=10463<br>
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param tid
     * @param sellerNick
     * @param isSplit
     * @param subTids
     * @return
     * @throws ApiException
     * @throws IOException
     */
    LogisticsTraceSearchResponse taobaoLogisticsTraceSearch(String appKey, String appSecret,
        String accessToken, Long tid, String sellerNick, Long isSplit, String subTids)
        throws ApiException, IOException;

    /**
     * taobao.traderate.add (新增单个评价)<br>
     * http://open.taobao.com/docs/api.htm?spm=a219a.7629065.0.0.ACkXyf&apiId=56<br>
     * 注：在评价之前需要对订单成功的时间进行判定（end_time）,如果超过15天，不能再通过该接口进行评价.<br>
     * 默认角色为卖家.
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param tid
     * @param oid
     * @param result
     * @param content
     * @return
     * @throws ApiException
     * @throws IOException
     */
    TraderateAddResponse taobaoTradeRateAdd(String appKey, String appSecret, String accessToken,
        Long tid, Long oid, String result, String content) throws ApiException, IOException;

    /**
     * taobao.traderate.list.add (针对父子订单新增批量评价)<br>
     * http://open.taobao.com/docs/api.htm?spm=a219a.7395905.0.0.eBL4OI&apiId=57<br>
     * 注：在评价之前需要对订单成功的时间进行判定（end_time）,如果超过15天，不用再通过该接口进行评价
     * 默认角色为卖家.
     *
     * @param appKey
     * @param appSecret
     * @param accessToken
     * @param tid
     * @param result
     * @param content
     * @return
     * @throws ApiException
     * @throws IOException
     */
    TraderateListAddResponse taobaoTradeRateListAdd(String appKey, String appSecret,
        String accessToken, Long tid, String result, String content)
        throws ApiException, IOException;



  /*
   * ****************************************
   * *         EcImsApiEncapsulate          *
   * ****************************************
   * 电商接口API简易封装
   */

    /**
     * 取省市區資料 http://open.taobao.com/apidoc/api.htm?path=cid:7-apiId:59
     *
     * @param cmdReq request
     * @return CmdRes response
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ApiException
     */
    CmdRes digiwinLogisticsAreasGet(CmdReq cmdReq)
        throws ClientProtocolException, IOException, ApiException;

    /**
     * 获取菜鸟物流面单号 http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.jEiZyn&apiId=23869
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     */
    CmdRes digiwinWlbWaybillIGet(CmdReq cmdReq) throws ApiException, NumberFormatException;

    /**
     * 更新菜鸟物流面单信息
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7395905.0.0.g6mYMu&apiId=23871
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     */
    CmdRes digiwinWlbWaybillIUpdate(CmdReq cmdReq) throws ApiException;

    /**
     * 取消已经获取的菜鸟物流面单号
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.JCvrHY&apiId=23874
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     */
    CmdRes digiwinWlbWaybillICancel(CmdReq cmdReq) throws ApiException;


    /**
     * 打印菜鸟面单之前确认打印接口
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7395905.0.0.sxnUhk&apiId=23872
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     */
    CmdRes digiwinWlbWaybillIPrint(CmdReq cmdReq) throws ApiException;


    /**
     * 在线订单发货
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.4muPv1&apiId=10687
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     */
    CmdRes digiwinOnlineShippingSend(CmdReq cmdReq) throws ApiException, NumberFormatException;


    /**
     * 物流流转信息查询
     * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7629065.0.0.QP1Bry&apiId=10463
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     * @throws NumberFormatException
     * @throws IOException
     */
    CmdRes digiwinLogisticsTraceSearch(CmdReq cmdReq)
        throws ApiException, NumberFormatException, IOException;

    /**
     * 新增单个评价
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     * @throws NumberFormatException
     * @throws IOException
     */
    CmdRes digiwinRateAdd(CmdReq cmdReq) throws ApiException, NumberFormatException, IOException;

    /**
     * 新增父子订单批量评价
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     * @throws NumberFormatException
     * @throws IOException
     */
    CmdRes digiwinRateListAdd(CmdReq cmdReq)
        throws ApiException, NumberFormatException, IOException;
}
