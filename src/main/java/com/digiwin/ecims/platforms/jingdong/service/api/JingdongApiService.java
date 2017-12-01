package com.digiwin.ecims.platforms.jingdong.service.api;

import java.io.IOException;
import java.util.Date;

import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.domain.Ware;
import com.jd.open.api.sdk.response.afsservice.AfsserviceFreightmessageGetResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceReceivetaskGetResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceRefundinfoGetResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceServicedetailListResponse;
import com.jd.open.api.sdk.response.afsservice.AfsserviceServiceinfoGetResponse;
import com.jd.open.api.sdk.response.etms.EtmsWaybillSendResponse;
import com.jd.open.api.sdk.response.etms.EtmsWaybillcodeGetResponse;
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

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.platforms.jingdong.bean.response.afsservice.MyAfsserviceServicedetailListResponse;

public interface JingdongApiService extends EcImsApiService {

  /*
   * ****************************************
   * *        JingdongApiEncapsulate        *
   * ****************************************
   * 京东API简易封装
   */
  
  
  /**
   * 360buy.order.search 订单检索
   * http://jos.jd.com/api/detail.htm?apiName=360buy.order.search&id=393
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param startDate
   * @param endDate
   * @param orderState
   * @param page
   * @param pageSize
   * @param optionalFields
   * @param sortType 排序方式，默认升序,1是降序,其它数字都是升序 
   * @param dateType 查询时间类型，默认按修改时间查询。 1为按订单创建时间查询；其它数字为按订单（订单状态、修改运单号）修改时间 
   * @return
   * @throws JdException
   * @throws IOException
   */
   OrderSearchResponse jingdongOrderSearch(
      String appKey, String appSecret, String accessToken, 
      String startDate, String endDate, 
      String orderState, 
      Integer page, Integer pageSize, 
      String optionalFields, String sortType, String dateType) 
          throws JdException, IOException;

  /**
   * 360buy.order.get 获取单个订单
   * http://jos.jd.com/api/detail.htm?apiName=360buy.order.get&id=403
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderId
   * @param optionalFields
   * @param orderState
   * @return
   * @throws JdException
   * @throws IOException
   */
   OrderGetResponse jingdongOrderGet(
      String appKey, String appSecret, String accessToken, 
      String orderId, String optionalFields, String orderState) 
          throws JdException, IOException;
  
  /**
   * 360buy.order.sop.outstorage 订单SOP出库
   * http://jos.jd.com/api/detail.htm?apiName=360buy.order.sop.outstorage&id=411
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param logisticsId 物流公司ID(只可通过获取商家物流公司接口获得),多个物流公司以|分隔 
   * @param waybill 运单号(当厂家自送时运单号可为空，不同物流公司的运单号用|分隔，如果同一物流公司有多个运单号，则用英文逗号分隔) 
   * @param orderId
   * @param tradeNo
   * @return
   * @throws JdException
   * @throws IOException
   */
   OrderSopOutstorageResponse jingdongOrderSopOutstorage(
      String appKey, String appSecret, String accessToken, 
      String logisticsId, String waybill, String orderId, String tradeNo) 
          throws JdException, IOException;

  
  /**
   * jingdong.pop.afs.soa.refundapply.queryPageList 退款审核单列表查询
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.pop.afs.soa.refundapply.queryPageList&id=925
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param ids 批量传入退款单id，格式为'id,id'，传入id数不能超过pageSize 
   * @param status 退款申请单状态 0、未审核 1、审核通过2、审核不通过 3、京东财务审核通过 4、京东财务审核不通过 5、人工审核通过 6、拦截并退款 7、青龙拦截成功 8、青龙拦截失败 9、强制关单并退款。不传是查询全部状态 
   * @param orderId
   * @param buyerId 客户帐号 
   * @param buyerName 客户姓名 
   * @param applyTimeStart 申请日期（start）
   * @param applyTimeEnd 申请日期（end）
   * @param checkTimeStart 审核日期(start) 
   * @param checkTimeEnd 审核日期(end) 
   * @param pageIndex
   * @param pageSize
   * @return
   * @throws JdException
   * @throws IOException
   */
   PopAfsSoaRefundapplyQueryPageListResponse jingdongRefundApplyQueryPageList(
      String appKey, String appSecret, String accessToken, 
      String ids, Long status, 
      String orderId, String buyerId,
      String buyerName,
      String applyTimeStart, String applyTimeEnd, 
      String checkTimeStart, String checkTimeEnd, 
      Integer pageIndex, Integer pageSize) 
          throws JdException, IOException;
  
  /**
   * jingdong.pop.afs.soa.refundapply.queryById 根据Id查询退款审核单
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.pop.afs.soa.refundapply.queryById&id=922
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param id 退款单id 
   * @return
   * @throws JdException
   * @throws IOException
   */
   PopAfsSoaRefundapplyQueryByIdResponse jingdongRefundApplyQueryById(
      String appKey, String appSecret, String accessToken,
      Long id) 
          throws JdException, IOException;
  
  /**
   * jingdong.afsservice.receivetask.get 获取待收货服务单信息
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.afsservice.receivetask.get&id=319
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param afsserviceId
   * @param pageNumber
   * @param pageSize
   * @param customerPin
   * @param orderId
   * @param afsApplyTimeBegin 申请时间begin
   * @param afsApplyTimeEnd 申请时间end
   * @return
   * @throws JdException
   * @throws IOException
   */
   AfsserviceReceivetaskGetResponse jingdongAfsserviceReceivetaskGet(
      String appKey, String appSecret, String accessToken, 
      Integer afsserviceId, 
      Integer pageNumber, Integer pageSize, 
      String customerPin, 
      Long orderId, 
      Date afsApplyTimeBegin, Date afsApplyTimeEnd) 
          throws JdException, IOException;
  
  /**
   * jingdong.afsservice.servicedetail.list 获取服务单详情
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.afsservice.servicedetail.list&id=326
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param afsServiceId
   * @return
   * @throws JdException
   * @throws IOException
   */
  @Deprecated
   AfsserviceServicedetailListResponse jingdongAfsserviceServicedetailList(
      String appKey, String appSecret, String accessToken,
      Integer afsServiceId) 
          throws JdException, IOException;
  
  /**
   * jingdong.afsservice.servicedetail.list 获取服务单详情<br>
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.afsservice.servicedetail.list&id=326<br>
   * 借由自己实现服务单的相关类来完成同步，解决wareid的实际长度超出Integer范围的问题，导致运行时异常。现已改为Long
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param afsServiceId
   * @return
   * @throws JdException
   * @throws IOException
   */
   MyAfsserviceServicedetailListResponse myJingdongAfsserviceServicedetailList(
      String appKey, String appSecret, String accessToken,
      Long afsServiceId) 
          throws JdException, IOException;
  
  /**
   * jingdong.afsservice.freightmessage.get 获取发运信息
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.afsservice.freightmessage.get&id=327
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param afsserviceId
   * @return
   * @throws JdException
   * @throws IOException
   */
   AfsserviceFreightmessageGetResponse jindongAfsserviceFreightMessageGet(
      String appKey, String appSecret, String accessToken,
      Integer afsserviceId) 
          throws JdException, IOException;
  
  /**
   * jingdong.afsservice.refundinfo.get 获取退款信息
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.afsservice.refundinfo.get&id=324
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param afsserviceId
   * @return
   * @throws JdException
   * @throws IOException
   */
   AfsserviceRefundinfoGetResponse jingdongAfsserviceRefundInfoGet(
      String appKey, String appSecret, String accessToken,
      Integer afsserviceId) 
          throws JdException, IOException;
  
   AfsserviceServiceinfoGetResponse jingdongAfsserviceInfoGet(
      String appKey, String appSecret, String accessToken,
      Long afsserviceId) 
          throws JdException, IOException;
  
  /**
   * 360buy.ware.update.listing 商品上架
   * http://jos.jd.com/api/detail.htm?apiName=360buy.ware.update.listing&id=118
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param wareId 商品ID
   * @param tradeNo
   * @return
   * @throws JdException
   * @throws IOException
   */
   WareUpdateListingResponse jingdongWareUpdateListing(
      String appKey, String appSecret, String accessToken,
      String wareId, String tradeNo) 
          throws JdException, IOException;
  
  /**
   * 360buy.ware.update.delisting 商品下架
   * http://jos.jd.com/api/detail.htm?apiName=360buy.ware.update.delisting&id=121
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param wareId 商品ID
   * @param tradeNo
   * @return
   * @throws JdException
   * @throws IOException
   */
   WareUpdateDelistingResponse jingdongWareUpdateDelisting(
      String appKey, String appSecret, String accessToken,
      String wareId, String tradeNo) 
          throws JdException, IOException;
  
  /**
   * 360buy.ware.get 根据商品ID查询单个商品的详细信息
   * http://jos.jd.com/api/detail.htm?apiName=360buy.ware.get&id=108
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param wareId 商品ID
   * @param fields
   * @return
   * @throws JdException
   * @throws IOException
   */
   WareGetResponse jingdongWareGet(
      String appKey, String appSecret, String accessToken,
      String wareId, String fields) 
          throws JdException, IOException;
   
   
   //addby cs 20170411
   /**
    * jingdong.ware.price.get 根据SKU ID获取商品价格信息
    * http://jos.jd.com/api/detail.htm?apiName=jingdong.ware.price.get&id=386
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param skuId
	 * @return
	 * @throws JdException
	 * @throws IOException
	 */
   	WarePriceGetResponse jingdongWarePriceGet(
	  String appKey, String appSecret, String accessToken,
	  String skuId)
   			throws JdException, IOException;
  
  /**
   * 360buy.wares.list.get 批量获取商品信息
   * http://jos.jd.com/api/detail.htm?apiName=360buy.wares.list.get&id=109
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param wareIds 商品的id，用逗号分隔，最多不能超过10个 
   * @param fields 
   * @return
   * @throws JdException
   * @throws IOException
   */
   WareListResponse jingdongWaresListGet(
      String appKey, String appSecret, String accessToken,
      String wareIds, String fields) 
          throws JdException, IOException;
  
  /**
   * 360buy.wares.search 检索商品信息
   * http://jos.jd.com/api/detail.htm?apiName=360buy.wares.search&id=100
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param cid 类目id
   * @param startPrice 最小价格（分） 
   * @param endPrice 最大价格（分）
   * @param page
   * @param pageSize
   * @param title 商品名称 
   * @param orderBy 排序栏位（默认onlineTime ）(offlineTime,onlineTime) 
   * @param startTime 起始创建时间 
   * @param endTime 结束创建时间 
   * @param startModified 起始的修改时间 
   * @param endModified 结束的修改时间 
   * @param wareStatus 1:在售;2:待售 
   * @param fields
   * @param parentShopCategoryId
   * @param shopCategoryId
   * @param itenNum 商品货号 
   * @return
   * @throws JdException
   * @throws IOException
   */
   WareInfoByInfoSearchResponse jingdongWaresSearch(
      String appKey, String appSecret, String accessToken,
      String cid, String startPrice, String endPrice, 
      Integer page, Integer pageSize, 
      String title, String orderBy,
      String startTime, String endTime,
      String startModified, String endModified,
      String wareStatus, String fields, 
      String parentShopCategoryId, String shopCategoryId, String itenNum) 
          throws JdException, IOException;
  
  /**
   * 360buy.sku.stock.update 修改SKU库存信息
   * http://jos.jd.com/api/detail.htm?apiName=360buy.sku.stock.update&id=117
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param skuId
   * @param outerId
   * @param quantity
   * @param tradeNo
   * @param storeId 分区库存ID。若使用分区库存则为必填项，且为数字；若不使用分区库存则可不填 
   * @return
   * @throws JdException
   * @throws IOException
   */
   WareSkuStockUpdateResponse jingdongSkuStockUpdate(
      String appKey, String appSecret, String accessToken,
      String skuId, String outerId, String quantity, 
      String tradeNo, String storeId) 
          throws JdException, IOException;
  
  
  /**
   * jingdong.etms.waybillcode.get 获取京东快递运单号
   * http://jos.jd.com/api/detail.htm?apiName=jingdong.etms.waybillcode.get&id=246
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param preNum 获取运单号数量（最大100） 
   * @param customerCode 商家编码（区分英文大小写） 此商家编码需由商家向京东快递运营人员（与商家签订京东快递合同的人）索取。
   * @param orderType 运单类型。(普通外单：0，O2O外单：1)默认为0 
   * @return
   * @throws JdException
   * @throws IOException
   */
   EtmsWaybillcodeGetResponse jingdongEtmsWaybillcodeGet(
      String appKey, String appSecret, String accessToken,
      String preNum, String customerCode, Integer orderType) 
          throws JdException, IOException;
  
   EtmsWaybillSendResponse jingdongEtmsWaybillSend(
      String appKey, String appSecret, String accessToken,
      String deliveryId, String salPlat, String customerCode, String orderId, 
      String thrOrderId, Integer selfPrintwayBill, String pickMethod, String packageRequired, 
      String senderName, String senderAddress, String senderTel, String senderMobil, String senderPostcode,
      String receiverName, String receiverAddress, String receiverProvince, String receiverCity,
      String receiverCounty, String receiverTown, Integer receiverProinceId, Integer receiverCityId,
      Integer receiverCountyId, Integer receiverTownId, Integer siteType, Integer siteId,
      String siteName, String receiverTel, String receiverMobile, String receiverPostcode,
      Integer packageCount, Double weight, Double volumnLong, Double volumnWidth, Double volumnHeight,
      Double volumn, String description, Integer collectionValue, Double collectionMoney, Integer guaranteeValue,
      Double guaranteeValueAmount, Integer signReturn, Integer aging, Integer transType, String remark,
      Integer goodsType, Integer orderType, String shopCode, String orderSendTime, String warehouseCode,
      String extendField1, String extendField2, String extendField3, 
      Integer extendField4, Integer extendField5) 
          throws JdException, IOException;
  
   CmdRes digiwinJdExpressnoGet(CmdReq cmdReq) throws Exception;

   CmdRes digiwinJdWaybillSend(CmdReq cmdReq) throws Exception;

}
