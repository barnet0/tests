package com.digiwin.ecims.platforms.pdd2.service.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.pdd2.bean.Pdd2Client;
import com.digiwin.ecims.platforms.pdd2.bean.request.SendGoodsRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.item.GetGoodsInfoRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.item.GetGoodsRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.item.GetSkuStockRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.item.SynSkuStockRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.order.CheckOrdersAfterSaleRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.order.OrderIncrementSearchRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.order.OrderSearchRequest;
import com.digiwin.ecims.platforms.pdd2.bean.request.refund.RefundGetRequest;
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
import com.digiwin.ecims.platforms.pdd2.service.api.Pdd2ApiService;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2ClientUtil;
import com.digiwin.ecims.platforms.pdd2.util.Pdd2CommonTool;
import com.digiwin.ecims.platforms.pdd2.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.pdd2.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class Pdd2ApiServiceImpl implements Pdd2ApiService {

	private static final Logger logger = LoggerFactory.getLogger(Pdd2ApiServiceImpl.class);

	@Autowired
	private ParamSystemService paramSystemService; // add by mowj 20150928

	@Autowired
	private AomsShopService aomsShopService;

	@Override
	public CmdRes execute(CmdReq cmdReq) throws Exception {
		String api = cmdReq.getApi();

		CmdRes cmdRes = null;
		switch (EcImsApiEnum.parse(api)) {
		case DIGIWIN_ORDER_DETAIL_GET:
			cmdRes = digiwinOrderDetailGet(cmdReq);
			break;
		case DIGIWIN_REFUND_GET:
			cmdRes = digiwinRefundGet(cmdReq);
			break;
		case DIGIWIN_ITEM_DETAIL_GET:
			cmdRes = digiwinItemDetailGet(cmdReq);
			break;
		case DIGIWIN_ITEM_LISTING:
			cmdRes = digiwinItemListing(cmdReq);
			break;
		case DIGIWIN_ITEM_DELISTING:
			cmdRes = digiwinItemDelisting(cmdReq);
			break;
		case DIGIWIN_ITEM_UPDATE:
			cmdRes = digiwinItemUpdate(cmdReq);
			break;
		case DIGIWIN_INVENTORY_UPDATE:
			cmdRes = digiwinInventoryUpdate(cmdReq);
			break;
		case DIGIWIN_INVENTORY_BATCH_UPDATE:
			cmdRes = digiwinInventoryBatchUpdate(cmdReq);
			break;
		case DIGIWIN_SHIPPING_SEND:
			cmdRes = digiwinShippingSend(cmdReq);
			break;
		case DIGIWIN_PDD_SKUSTOCK_GET:
			cmdRes = digiwinSkuStockGet(cmdReq);
			break;
		case DIGIWIN_PDD_REFUNDSTATUS_CHECK:
			cmdRes = digiwinRefundStatusCheck(cmdReq);
			break;
		default:
			cmdRes = new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
			break;
		}

		return cmdRes;
	}

	@Override
	public CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception {
		// 取得授权
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		String orderId = cmdReq.getParams().get("id");

		OrderInfoGetResponse response = pddOrderGet(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), orderId);

		boolean success = response.getError_code() == Pdd2CommonTool.RESPONSE_DEFAULT_CODE;

		return new CmdRes(cmdReq, success,
				success ? null : new ResponseError(response.getError_code() + "", response.getError_msg()),
				success ? new AomsordTTranslator(response).doTranslate(cmdReq.getStoreid()) : null);
	}

	@Override
	public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
		// 取得授权
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		// 物流公司ID
		String logisticsId = cmdReq.getParams().get("expcompno");
		// 运单号
		String tracking_number = cmdReq.getParams().get("expressno");
		// 订单号
		String orderId = cmdReq.getParams().get("oid");

		SendGoodsResponse response = pddSndGoods(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), orderId,
				logisticsId, tracking_number);

		boolean success = response.getIs_success()== Pdd2CommonTool.RESPONSE_SUCCESS_CODE;
		return new CmdRes(cmdReq, success,
				success ? null: new ResponseError(response.getError_code() + "", response.getError_msg()),
				null);
	}

	@Override
	public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
		// 取得授权
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		// 商品sku id
		String skuId = cmdReq.getParams().get("skuid");
		// 商品在ERP料号
		String outerId = cmdReq.getParams().get("outerid");
		// 数量
		String quantity = cmdReq.getParams().get("num");

		SynSkuStockResponse response = pddSynSkuStock(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), skuId,
				outerId, Long.parseLong(quantity));

		boolean success = response.getIs_success() == Pdd2CommonTool.RESPONSE_SUCCESS_CODE;
		return new CmdRes(cmdReq, success,
				success ? null: new ResponseError(response.getError_code() + "", response.getError_msg()),
				null);
	}

	@Override
	public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
		throw new UnsupportedOperationException("_034");
	}

	// mod by cjp 20170509 pdd指提供批量的方式,所以不用给订单中心提供接口。
	@Override
	public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
		throw new UnsupportedOperationException("_034");
	}

	@Override
	public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
		throw new UnsupportedOperationException("_034");
	}

	@Override
	public CmdRes digiwinItemDelisting(CmdReq cmdreq) throws Exception {
		throw new UnsupportedOperationException("_034");
	}

	@Override
	public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
		// 取得授权
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得API所需参数
		// 此处需要ERP传入ERP的料号，并在拼多多平台上进行映射维护
		String numid = cmdReq.getParams().get("numid");

		GetGoodsInfoResponse response = pddGetGoodsInfo(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
				numid);
		boolean success = response.getError_code() == Pdd2CommonTool.RESPONSE_DEFAULT_CODE;

		return new CmdRes(cmdReq, success,
				success ? null : new ResponseError(response.getError_code() + "", response.getError_msg()),
				success ? new AomsitemTTranslator(response).doTranslate(cmdReq.getStoreid()) : null);
	}

	@Override
	public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
		throw new UnsupportedOperationException("_034");
	}

	@Override
	public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
		throw new UnsupportedOperationException("_034");
	}

	// 获取SKU库存
	public CmdRes digiwinSkuStockGet(CmdReq cmdReq) throws Exception {
		// 取得授权
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
		// 商品sku id
		String skuIds = cmdReq.getParams().get("skuid");
		// 商品在ERP料号
		String outerIds = cmdReq.getParams().get("outerid");
		GetSkuStockResponse response = pddGetSkuStock(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), skuIds,
				outerIds);
		boolean success = response.getError_code() == Pdd2CommonTool.RESPONSE_DEFAULT_CODE;
		//boolean success = response.getIs_success() == Pdd2CommonTool.RESPONSE_SUCCESS_CODE;
		return new CmdRes(cmdReq, success,
				success ? null : new ResponseError(response.getError_code() + "", response.getError_msg()),
				success ? response.getSku_stock_list() : null);
	}

	public CmdRes digiwinRefundStatusCheck(CmdReq cmdReq) throws Exception {
			// 取得授权
			ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
			// 商品sku id
			String order_sns = cmdReq.getParams().get("oid");
			// 商品在ERP料号
			CheckOrdersAfterSaleResponse response = pddCheckOrdersAfterSale(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), order_sns);
		    boolean success = response.getError_code() == Pdd2CommonTool.RESPONSE_DEFAULT_CODE;
			
			return new CmdRes(cmdReq, success,
					success ? null : new ResponseError(response.getError_code() + "", response.getError_msg()),
					success ? response.getOrder_sns_exists_refund() : null);
		
	}
	
	@Override
	public OrderSearchResponse pddOrderSearch(String appKey, String appSecret, String accessToken, Integer orderStatus,
			Integer page, Integer pageSize) throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		OrderSearchRequest request = new OrderSearchRequest();
		request.setOrderStatus(orderStatus);
		/*
		 * if (StringTool.isNotEmpty(beginTime)) {
		 * request.setBeginTime(beginTime); } if
		 * (StringTool.isNotEmpty(endTime)) { request.setEndTime(endTime); }
		 */
		request.setPage(page);
		request.setPageSize(pageSize);

		OrderSearchResponse response = client.execute(request);

		return response;
	}

	@Override
	public OrderIncrementSearchResponse pddOrderIncrementSearch(String appKey, String appSecret, String accessToken,
			Integer IsLuckyFlag, Integer OrderStatus, Integer RefundStatus, String start_updated_at,
			String end_updated_at, Integer page, Integer pageSize) throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		OrderIncrementSearchRequest request = new OrderIncrementSearchRequest();
		request.setIs_lucky_flag(IsLuckyFlag);
		request.setOrder_status(OrderStatus);
		request.setRefund_status(RefundStatus);
		request.setStart_updated_at(start_updated_at);
		request.setEnd_updated_at(end_updated_at);

		if (StringTool.isNotEmpty(page)) {
			request.setPage(page);
		}
		if (StringTool.isNotEmpty(pageSize)) {
			request.setPage_size(pageSize);
		}

		OrderIncrementSearchResponse response = client.execute(request);

		return response;
	}

	@Override
	public OrderInfoGetResponse pddOrderGet(String appKey, String appSecret, String accessToken, String orderSN)
			throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		OrderGetRequest request = new OrderGetRequest();
		request.setOrderSN(orderSN);

		OrderInfoGetResponse response = client.execute(request);

		return response;
	}

	@Override
	public SendGoodsResponse pddSndGoods(String appKey, String appSecret, String accessToken, String orderSN,
			String logistics_id, String tracking_number) throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		SendGoodsRequest request = new SendGoodsRequest();
		request.setOrderSN(orderSN);
		request.setLogistics_id(logistics_id);
		request.setTracking_number(tracking_number);

		SendGoodsResponse response = client.execute(request);

		return response;
	}

	@Override
	public SynSkuStockResponse pddSynSkuStock(String appKey, String appSecret, String accessToken, String pddSkuId,
			String outerSkuIds, Long quantity) throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		SynSkuStockRequest request = new SynSkuStockRequest();
		if (StringTool.isNotEmpty(pddSkuId)) {
			request.setSkuId(pddSkuId);
		}
		if (StringTool.isNotEmpty(outerSkuIds)) {
			request.setOuterIds(outerSkuIds);
		}
		request.setQuantity(quantity);

		SynSkuStockResponse response = client.execute(request);

		return response;
	}

	@Override
	public GetGoodsInfoResponse pddGetGoodsInfo(String appKey, String appSecret, String accessToken, String goods_id)
			throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		GetGoodsInfoRequest request = new GetGoodsInfoRequest();

		request.setGoods_id(goods_id);

		GetGoodsInfoResponse response = client.execute(request);

		return response;
	}

	/**
	 * by cjp
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
	@Override
	public RefundGetResponse pddRefundGet(String appKey, String appSecret, String accessToken, String afterSalesType,
			String afterSalesStatus, String startUpdatedAt, String endUpdatedAt, Integer page, Integer pageSize)
			throws Exception {

		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		RefundGetRequest request = new RefundGetRequest();
		request.setAfter_sales_type(afterSalesType);
		request.setAfter_sales_status(afterSalesStatus);
		request.setStart_updated_at(startUpdatedAt);
		request.setEnd_updated_at(endUpdatedAt);
		// request.setConsignTime(System.currentTimeMillis() + "");
		request.setPage(page);
		request.setPage_size(pageSize);

		RefundGetResponse response = client.execute(request);

		return response;
	}

	@Override
	public GetSkuStockResponse pddGetSkuStock(String appKey, String appSecret, String accessToken, String pddSkuIds,
			String outerSkuIds) throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		GetSkuStockRequest request = new GetSkuStockRequest();
		request.setPddSkuIds(pddSkuIds);
		request.setOuterSkuIds(outerSkuIds);

		GetSkuStockResponse response = client.execute(request);

		return response;
	}

	@Override
	public GetGoodsResponse pddGetGoods(String appKey, String appSecret, String accessToken, String outerID,
			Integer isOnsale, String goodsName, Integer page, Integer pageSize) throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		GetGoodsRequest request = new GetGoodsRequest();
		if (StringTool.isNotEmpty(isOnsale)) {
			request.setIs_onsale(isOnsale.toString());
		}
		if (StringTool.isNotEmpty(outerID)) {
			request.setOuterID(outerID);
		}
		if (StringTool.isNotEmpty(goodsName)) {
			request.setGoodsName(goodsName);
		}
		request.setPage(page);
		request.setPageSize(pageSize);

		GetGoodsResponse response = client.execute(request);

		return response;
	}

	@Override
	public CheckOrdersAfterSaleResponse pddCheckOrdersAfterSale(String appKey, String appSecret, String accessToken,
			String order_sns) throws Exception {
		Pdd2Client client = Pdd2ClientUtil.getInstance()
				.getPdd2Client(paramSystemService.getEcApiUrl(EcApiUrlEnum.PDD2_API), appKey, appSecret);
		CheckOrdersAfterSaleRequest request = new CheckOrdersAfterSaleRequest();
		request.setOrderSNs(order_sns);

		CheckOrdersAfterSaleResponse response = client.execute(request);

		return response;
	}
}
