package com.digiwin.ecims.platforms.kaola.service.api;

import java.math.BigDecimal;

import com.digiwin.ecims.core.api.EcImsApiService;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemGetResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSearchResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSkuUpdateResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemUpdateDelistingResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemUpdateListingResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.order.LogisticsCompanyDeliverResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderSearchResponse;


public interface KaolaApiService extends EcImsApiService {

	/*
	   * ****************************************
	   * *            KaolaApiEncapsulate         *
	   * ****************************************
	   * 考拉API简易封装
	   */
	/**
	 * 修改网易考拉商品库存
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param key
	 * @param stock
	 * @return
	 * @throws Exception
	 */
	ItemSkuUpdateResponse kaolaSkuStockUpdate(String appKey, String appSecret, String accessToken, String key,
			Integer stock) throws Exception;

	/**
	 * 修改网易考拉商品价格
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param key
	 * @param sale_price
	 * @return
	 * @throws Exception
	 */
	ItemSkuUpdateResponse kaolaSkuSalePriceUpdate(String appKey, String appSecret, String accessToken, String key,
			BigDecimal sale_price) throws Exception;

	/**
	 * 根据商品id获取单个商品的详细信息接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param key
	 * @return
	 * @throws Exception
	 */
	ItemGetResponse kaolaGetItem(String appKey, String appSecret, String accessToken, String key) throws Exception;

	/**
	 * 根据状态查询商品信息接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param item_edit_status:商品的状态1待提交审核,2审核中,3审核未通过,4待上架(审核已通过),5在售,6下架,8强制下架(确认要哪种)
	 * @param dataType
	 *            1-商品创建时间；2-商品修改时间
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	ItemSearchResponse kaolaItemSearch(String appKey, String appSecret, String accessToken, Integer item_edit_status,
			Integer dataType, String beginTime, String endTime, Integer page, Integer pageSize) throws Exception;

	/**
	 * 商品上架
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	ItemUpdateListingResponse ItemUpdateListing(String appKey, String appSecret, String accessToken, String key_list)
			throws Exception;

	/**
	 * 商品下架
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param key_list
	 * @return
	 * @throws Exception
	 */
	ItemUpdateDelistingResponse ItemUpdateDelisting(String appKey, String appSecret, String accessToken,
			String key_list) throws Exception;
			
	/**
	 * 订单信息详情接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param orderStatus:订单状态1(已付款)、2(已发货)、3(已签收)、5(取消待确认)、6(已取消)
	 * @param beginTime
	 * @param endTime
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	OrderSearchResponse kaolaOrderSearch(
			  String appKey, String appSecret, String accessToken, 
		      Integer orderStatus, String beginTime, String endTime, 
		      Integer page, Integer pageSize) throws Exception;

	/**
	 * 根据订单号查询订单详情接口
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param orderSN 
	 * @return
	 * @throws Exception
	 */
	OrderGetResponse kaolaOrderGet(String appKey, String appSecret, String accessToken, String orderSN)
			throws Exception;
	/**
	 * 商家发货通知
	 * 
	 * @param appKey
	 * @param appSecret
	 * @param accessToken
	 * @param orderSN
	 * @param express_company_code
	 * @param express_no
	 * @param sku_info
	 * @return
	 * @throws Exception
	 */
	
	LogisticsCompanyDeliverResponse kaolaLogisticsDeliver(String appKey, String appSecret, String accessToken, String orderSN,
			String express_company_code, String express_no, String sku_info) throws Exception;
}


	

