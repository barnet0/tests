package com.digiwin.ecims.platforms.yhd.service.api;

import com.yhd.response.logistics.LogisticsOrderShipmentsUpdateResponse;
import com.yhd.response.order.OrderDetailGetResponse;
import com.yhd.response.order.OrdersDetailGetResponse;
import com.yhd.response.order.OrdersGetResponse;
import com.yhd.response.order.OrdersRefundAbnormalGetResponse;
import com.yhd.response.product.GeneralProductsSearchResponse;
import com.yhd.response.product.ProductShelvesdownUpdateResponse;
import com.yhd.response.product.ProductShelvesupUpdateResponse;
import com.yhd.response.product.ProductStockUpdateResponse;
import com.yhd.response.product.ProductsPriceGetResponse;
import com.yhd.response.product.ProductsStockGetResponse;
import com.yhd.response.product.ProductsStockUpdateResponse;
import com.yhd.response.product.SerialChildproductsGetResponse;
import com.yhd.response.product.SerialProductAttributeGetResponse;
import com.yhd.response.product.SerialProductGetResponse;
import com.yhd.response.product.SerialProductsSearchResponse;
import com.yhd.response.product.SerialProductsStockUpdateResponse;
import com.yhd.response.refund.RefundDetailGetResponse;
import com.yhd.response.refund.RefundGetResponse;

import com.digiwin.ecims.core.api.EcImsApiService;

public interface YhdApiService extends EcImsApiService {

  /*
   * ****************************************
   * *           YhdApiEncapsulate          *
   * ****************************************
   * 一号店API简易封装
   */
  
  
  /**
   * yhd.orders.get 获取订单列表 
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.orders.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderStatusList
   * @param dateType 日期类型(1：订单生成日期，2：订单付款日期，3：订单发货日期，4：订单收货日期，5：订单更新日期)
   * @param startTime
   * @param endTime
   * @param curPage
   * @param pageRows
   * @return
   * @throws Exception
   */
  OrdersGetResponse yhdOrdersGet(String appKey, String appSecret, String accessToken,
      String orderStatusList, Integer dateType, String startTime, String endTime, Integer curPage,
      Integer pageRows) throws Exception;

  /**
   * yhd.orders.detail.get 批量获取订单详情
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.orders.detail.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderCodeList 订单编码列表（逗号分隔）,最大长度为50
   * @return
   * @throws Exception
   */
  OrdersDetailGetResponse yhdOrdersDetailGet(String appKey, String appSecret, String accessToken,
      String orderCodeList) throws Exception;

  /**
   * yhd.order.detail.get 获取单个订单详情
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.order.detail.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderCode 订单编码
   * @return
   * @throws Exception
   */
  OrderDetailGetResponse yhdOrderDetailGet(String appKey, String appSecret, String accessToken,
      String orderCode) throws Exception;
  
  /**
   * yhd.refund.get 获取退货列表
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.refund.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderCode
   * @param productId 产品ID
   * @param refundStatus 退货状态。(1000:全部；10:待审核；30:待退货；40:审批通过；110:待顾客寄回；120:待确认退款或确认换货；140:待完成换货；70:退换货完成；100:已关闭；130:客服仲裁)
   * @param startTime
   * @param endTime
   * @param curPage
   * @param pageRows
   * @param dateType 时间类型 :1、申请时间 2、更新时间
   * @param operateType 退换货类型（默认查询退货）：0、退货 1、换货 1000：全部
   * @return
   * @throws Exception
   */
  RefundGetResponse yhdRefundGet(String appKey, String appSecret, String accessToken,
      String orderCode, Long productId, Integer refundStatus, 
      String startTime, String endTime, Integer curPage, Integer pageRows, 
      Integer dateType, Integer operateType) throws Exception;

  /**
   * yhd.refund.detail.get 获取单个退货详情
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.refund.detail.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param refundCode 退货单号
   * @return
   * @throws Exception
   */
  RefundDetailGetResponse yhdRefundDetailGet(String appKey, String appSecret, String accessToken,
      String refundCode) throws Exception;

  /**
   * yhd.orders.refund.abnormal.get 异常订单退款查询接口
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.orders.refund.abnormal.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param refundOrderCode
   * @param orderCode
   * @param refundCode
   * @param refundStatus
   * @param receiverPhone
   * @param startTime
   * @param endTime
   * @param dateType 1表示申请，2表示批准（当dateType=1,startTime表示申请开始时间,endTime表示申请结束时间；dateType=2，startTime表示批准开始时间,endTime表示批准结束时间）
   * @param curPage
   * @param pageRows
   * @return
   * @throws Exception
   */
  OrdersRefundAbnormalGetResponse yhdOrdersRefundAbnormalGet(
      String appKey, String appSecret, String accessToken,
      String refundOrderCode, String orderCode, String refundCode, String refundStatus,
      String receiverPhone, 
      String startTime, String endTime,
      Integer dateType, 
      Integer curPage, Integer pageRows) throws Exception;

  /**
   * yhd.general.products.search 查询普通产品信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.general.products.search
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param canShow
   * @param canSale 上下架状态0：下架，1：上架
   * @param curPage
   * @param pageRows
   * @param productCname
   * @param productIdList 产品Id列表(最多productId个数为100,与outerIdList二选一，两个都填，默认为prodcuctIdList)
   * @param outerIdList outerId列表(最多outerId个数为100，与productIdList二选一，两个都填，默认为prodcuctIdList)
   * @param verifyFlg 审核状态[1.未审核(新增未审核,编辑待审核);2.审核通过(审核通过);3.审核失败(审核未通过,图片审核失败,文描审核失败)]
   * @param categoryId
   * @param categoryType 产品类别类型（0:1号店类别,1:商家自定义类别,默认为0）
   * @param brandId
   * @param productCodeList 产品编码列表（逗号分隔）与productIdList、outerIdList三选一,最大长度为100,优先级最低
   * @param updateStartTime
   * @param updateEndTime
   * @return
   * @throws Exception
   */
  GeneralProductsSearchResponse yhdGeneralProductsSearch(
      String appKey, String appSecret, String accessToken,
      Integer canShow, Integer canSale, Integer curPage, Integer pageRows,
      String productCname, String productIdList, String outerIdList, 
      Integer verifyFlg, Long categoryId, Integer categoryType, Long brandId,
      String productCodeList, String updateStartTime, String updateEndTime) throws Exception;

  /**
   * yhd.serial.products.search 查询系列产品信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.serial.products.search
   * modify by mowj 20151209. 一号店API更新，已经可以用更新时间进行过滤，然而返回的资料没有更新时间，所以使用endDate作为下一次的lastUpdateTime
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param canShow
   * @param canSale 上下架状态0：下架，1：上架
   * @param curPage
   * @param pageRows
   * @param productCname
   * @param productIdList 产品Id列表(最多productId个数为100,与outerIdList二选一，两个都填，默认为prodcuctIdList)
   * @param outerIdList outerId列表(最多outerId个数为100，与productIdList二选一，两个都填，默认为prodcuctIdList)
   * @param verifyFlg 审核状态[1.未审核(新增未审核,编辑待审核);2.审核通过(审核通过);3.审核失败(审核未通过,图片审核失败,文描审核失败)]
   * @param categoryId
   * @param categoryType 产品类别类型（0:1号店类别,1:商家自定义类别,默认为0）
   * @param brandId
   * @param productCodeList 产品编码列表（逗号分隔）与productIdList、outerIdList三选一,最大长度为100,优先级最低
   * @param updateStartTime
   * @param updateEndTime
   * @return
   * @throws Exception
   */
  SerialProductsSearchResponse yhdSerialProductsSearch(
      String appKey, String appSecret, String accessToken, 
      Integer canShow, Integer canSale, Integer curPage, Integer pageRows,
      String productCname, String productIdList, String outerIdList, 
      Integer verifyFlg, Long categoryId, Integer categoryType, Long brandId,
      String productCodeList, String updateStartTime, String updateEndTime) throws Exception;

  /**
   * yhd.serial.product.get 获取单个系列产品的子品信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.serial.product.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productId
   * @param outerId
   * @param brandId
   * @param productCode
   * @return
   * @throws Exception
   */
  SerialProductGetResponse yhdSerialProductGet(
      String appKey, String appSecret, String accessToken,
      Long productId, String outerId, Long brandId, String productCode) throws Exception;

  /**
   * yhd.serial.product.attribute.get 获取商品系列属性
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.serial.product.attribute.get
   *
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productId 一号店（虚品或子品的）产品ID，与outerId二选一
   * @param outerId （虚品或子品的）外部产品ID，与productId二选一
   * @return
   * @throws Exception
   */
  SerialProductAttributeGetResponse yhdSerialProductAttributeGet(
      String appKey, String appSecret, String accessToken,
      Long productId, String outerId) throws Exception;

  /**
   * yhd.products.price.get 批量获取产品价格信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.products.price.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productIdList
   * @param outerIdList
   * @return
   * @throws Exception
   */
  ProductsPriceGetResponse yhdProductsPriceGet(
      String appKey, String appSecret, String accessToken,
      String productIdList, String outerIdList) throws Exception;

  /**
   * yhd.products.stock.get 批量获取产品库存信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.products.stock.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productIdList
   * @param outerIdList
   * @param wareHouseId
   * @return
   * @throws Exception
   */
  ProductsStockGetResponse yhdProductsStockGet(
      String appKey, String appSecret, String accessToken, 
      String productIdList, String outerIdList, Long wareHouseId) throws Exception;
  
  /**
   * yhd.product.stock.update 更新单个产品库存信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.product.stock.update
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productId 1号店产品ID,与outerId二选一.存在非法字符默认为空(productId优先)
   * @param outerId 外部产品ID,与productId二选一
   * @param virtualStockNum 虚拟库存。库存值小于对应类别库存最大值。 请从yhd.category.products.get获取中，查询对应类别的库存最大值
   * @param warehouseId 仓库ID（不传则是默认仓库）
   * @param updateType 更新类型，默认为1（1：全量更新，2：冻结库存上增量更新，3：库存上增量更新）
   * @return
   * @throws Exception
   */
  ProductStockUpdateResponse yhdProductStockUpdate(
      String appKey, String appSecret, String accessToken,
      Long productId, String outerId, Integer virtualStockNum, 
      Long warehouseId, Integer updateType) throws Exception;
  
  /**
   * yhd.products.stock.update 批量更新普通产品库存信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.products.stock.update
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param updateType 更新类型，默认为1（1：全量更新，2：增量更新）
   * @param productStockList
   * @param outerStockList
   * @return
   * @throws Exception
   */
  ProductsStockUpdateResponse yhdProductsStockUpdate(
      String appKey, String appSecret, String accessToken, 
      Integer updateType, String productStockList, String outerStockList) throws Exception;

  /**
   * yhd.serial.products.stock.update 批量更新系列产品库存信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.serial.products.stock.update
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productId 系列产品Id，与outerId二选一
   * @param outerId 外部产品Id，与productId二选一
   * @param updateType 更新类型，默认为1（1：全量更新，2：增量更新）
   * @param productStockList
   * @param outerStockList
   * @return
   * @throws Exception
   */
  SerialProductsStockUpdateResponse yhdSerialProductsStockUpdate(
      String appKey, String appSecret, String accessToken, 
      Long productId, String outerId, Integer updateType, 
      String productStockList, String outerStockList) throws Exception;

  /**
   * yhd.serial.childproducts.get 批量查询系列子品信息
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.serial.childproducts.get
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productIdList 1号店产品ID列表（逗号分隔）与outerIdList、productCodeList三选一,最大长度为100，优先级最高
   * @param outerIdList 外部产品ID列表（逗号分隔）与productIdList、productCodeList三选一,最大长度为100，每个最多30字符，优先级次之
   * @param productCodeList 产品编码列表（逗号分隔）与productIdList、outerIdList三选一,最大长度为100,每个最多30字符，优先级最低
   * @return
   * @throws Exception
   */
  SerialChildproductsGetResponse yhdSerialChildProductsGet(
      String appKey, String appSecret, String accessToken, 
      String productIdList, String outerIdList, String productCodeList) throws Exception;


  /**
   * yhd.logistics.order.shipments.update 订单发货(更新订单物流信息)
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.logistics.order.shipments.update
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param orderCode 订单号(订单编码)
   * @param deliverySupplierId 配送商ID(从获取物流信息接口中获取)
   * @param expressNbr 运单号(快递编号)
   * @param cartonList
   * @param deliveryItemNumberList
   * @return
   * @throws Exception
   */
  LogisticsOrderShipmentsUpdateResponse yhdLogisticsOrderShipmentsUpdate(
      String appKey, String appSecret, String accessToken,
      String orderCode, Long deliverySupplierId, String expressNbr, String cartonList, 
      String deliveryItemNumberList) throws Exception;
  
  /**
   * yhd.product.shelvesup.update 上架单个产品
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.product.shelvesup.update
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productId
   * @param outerId
   * @return
   * @throws Exception
   */
  ProductShelvesupUpdateResponse yhdProductShelvesupUpdate(
      String appKey, String appSecret, String accessToken,
      Long productId, String outerId) throws Exception;
  
  /**
   * yhd.product.shelvesdown.update 下架单个产品
   * http://open.yhd.com/doc2/apiDetail.do?apiName=yhd.product.shelvesdown.update
   * 
   * @param appKey
   * @param appSecret
   * @param accessToken
   * @param productId
   * @param outerId
   * @return
   * @throws Exception
   */
  ProductShelvesdownUpdateResponse yhdProductShelvesdownUpdate(
      String appKey, String appSecret, String accessToken,
      Long productId, String outerId) throws Exception;
}
