package com.digiwin.ecims.platforms.kaola.service.api.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhgate.open.client.SubError;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.kaola.bean.KaolaClient;
import com.digiwin.ecims.platforms.kaola.bean.request.item.ItemGetRequest;
import com.digiwin.ecims.platforms.kaola.bean.request.item.ItemSearchRequest;
import com.digiwin.ecims.platforms.kaola.bean.request.item.ItemSkuSalePriceUpdateRequest;
import com.digiwin.ecims.platforms.kaola.bean.request.item.ItemSkuStockUpdateRequest;
import com.digiwin.ecims.platforms.kaola.bean.request.item.ItemUpdateDelistingRequest;
import com.digiwin.ecims.platforms.kaola.bean.request.item.ItemUpdateListingRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.SubErrors;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemGetResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSearchResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemSkuUpdateResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemUpdateDelistingResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.item.ItemUpdateListingResponse;
import com.digiwin.ecims.platforms.kaola.bean.request.order.LogisticsCompanyDeliverRequest;
import com.digiwin.ecims.platforms.kaola.bean.request.order.LogisticsCompanyGet;
import com.digiwin.ecims.platforms.kaola.bean.request.order.OrderGetRequest;
import com.digiwin.ecims.platforms.kaola.bean.request.order.OrderSearchRequest;
import com.digiwin.ecims.platforms.kaola.bean.response.order.LogisticsCompanyDeliverResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderGetResponse;
import com.digiwin.ecims.platforms.kaola.bean.response.order.OrderSearchResponse;
import com.digiwin.ecims.platforms.kaola.service.api.KaolaApiService;
import com.digiwin.ecims.platforms.kaola.util.KaolaClientUtil;
import com.digiwin.ecims.platforms.kaola.util.KaolaCommonTool;
import com.digiwin.ecims.platforms.kaola.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.kaola.util.translator.AomsordTTranslator;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.jd.open.api.sdk.domain.jzt_kc.DspAdKCAreaService.ArrayList;
import com.mysql.fabric.xmlrpc.base.Array;

import vipapis.delivery.JitDeliveryServiceHelper.importDeliveryDetail_args;

@Service
public class KaolaApiServiceImpl implements KaolaApiService {
	private static final Logger logger = LoggerFactory.getLogger(KaolaApiServiceImpl.class);

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
		case DIGIWIN_KAOLA_EXPRESSINF_GET:
		   cmdRes = digiwinKaolaExpressInfGet(cmdReq);
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

	    OrderGetResponse response = kaolaOrderGet(
	        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), orderId);

	    //boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;
	    boolean success = response.getResult() == null;
	    logger.info("response result:" + response.getResult() + "---- success:" + success);
	    
		  return new CmdRes(cmdReq, success,
			        success ? null : new ResponseError(response.getCode() + "", response.getMsg()),
			            success ? new AomsordTTranslator(response)
			                .doTranslate(cmdReq.getStoreid()) : null);
	  }

	  @Override
	  public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
		    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		    // 取得参数
		    // 物流公司ID
		    String logisticsId = cmdReq.getParams().get("expcompno");
		    // 运单号
		    String waybill = cmdReq.getParams().get("expressno");
		    // 订单号
		    String orderId = cmdReq.getParams().get("oid"); 

		    LogisticsCompanyDeliverResponse response = kaolaLogisticsDeliver(
		        esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), 
		        orderId, logisticsId, waybill, null);

		    boolean success = (response.getCode() == null);

		    if (success) {
		    	String modifyTime = response.getModify_time();
		    	String ordId = response.getOrder_id();
		    	return new CmdRes(cmdReq, success,new ResponseError(ordId + "",modifyTime), null);	    	 
			}
		    else {
		    	SubErrors subError = response.getSubErrors().get(0);
		    	String code = subError.getCode();
		    	String msg = subError.getMsg();
			    return new CmdRes(cmdReq, success,new ResponseError(code + "", msg), null);
			}	    
	  }

	@Override
	public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
		// 取得授权
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		// 商品sku id
		String key = cmdReq.getParams().get("skuid");
		// 数量
		int stock = Integer.parseInt(cmdReq.getParams().get("num"));
		

		ItemSkuUpdateResponse response = kaolaSkuStockUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
				key, stock);

		boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;

		if (success || response.getSubErrors() == null) {
			return new CmdRes(cmdReq, success,
					success ? null : new ResponseError(response.getResult() + "", response.getModify_Time()), null);
		} else {
			return new CmdRes(cmdReq, success,
					success ? null
							: new ResponseError(response.getSubErrors().get(0).getCode() + "",
									response.getSubErrors().get(0).getMsg()),
					null);
		}
	}

	@Override
	public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		String keyList = cmdReq.getParams().get("numid");

		ItemUpdateListingResponse response = ItemUpdateListing(esv.getAppKey(), esv.getAppSecret(),
				esv.getAccessToken(), keyList);

		boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;
		if (success || response.getSubErrors() == null) {
			return new CmdRes(cmdReq, success,
					success ? null : new ResponseError(response.getResult() + "", response.getListing_time()), null);
		} else {
			return new CmdRes(cmdReq, success,
					success ? null
							: new ResponseError(response.getSubErrors().get(0).getCode() + "",
									response.getSubErrors().get(0).getMsg()),
					null);
		}
	}

	@Override
	public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		String keyList = cmdReq.getParams().get("numid");

		ItemUpdateDelistingResponse response = ItemUpdateDelisting(esv.getAppKey(), esv.getAppSecret(),
				esv.getAccessToken(), keyList);

		boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;
		if (success || response.getSubErrors() == null) {
			return new CmdRes(cmdReq, success,
					success ? null : new ResponseError(response.getResult() + "", response.getDeListing_time()), null);
		} else {
			return new CmdRes(cmdReq, success,
					success ? null
							: new ResponseError(response.getSubErrors().get(0).getCode() + "",
									response.getSubErrors().get(0).getMsg()),
					null);
		}
	}

	@Override
	public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
		// 取得授权
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		String key = cmdReq.getParams().get("numid");

		ItemGetResponse response = kaolaGetItem(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), key);

		//boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;
		boolean success = response.getCode() == null;

		  return new CmdRes(cmdReq, success,
			        success ? null : new ResponseError(response.getResult() + "", response.getCode()),
			            success ? new AomsitemTTranslator(response)
			                .doTranslate(cmdReq.getStoreid(), null) : null);
	}

	@Override
	public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
		// 取得授权
				ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

				// 取得参数
				// 商品sku id
				String key = cmdReq.getParams().get("skuid");
				// 数量
				int stock = Integer.parseInt(cmdReq.getParams().get("salePrice"));
				

				ItemSkuUpdateResponse response = kaolaSkuStockUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
						key, stock);

				boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;

				if (success || response.getSubErrors() == null) {
					return new CmdRes(cmdReq, success,
							success ? null : new ResponseError(response.getResult() + "", response.getModify_Time()), null);
				} else {
					return new CmdRes(cmdReq, success,
							success ? null
									: new ResponseError(response.getSubErrors().get(0).getCode() + "",
											response.getSubErrors().get(0).getMsg()),
							null);
				}
	}

	@Override
	public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemSkuUpdateResponse kaolaSkuStockUpdate(String appKey, String appSecret, String accessToken, String key,
			Integer stock) throws Exception {
		KaolaClient client = KaolaClientUtil.getInstance()
				.getKaolaClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
		ItemSkuStockUpdateRequest request = new ItemSkuStockUpdateRequest();
		if (StringTool.isNotEmpty(key)) {
			request.setKey(key);
		}
		if (StringTool.isNotEmpty(stock)) {
			request.setStock(stock);
		}
		ItemSkuUpdateResponse response = client.execute(request);

		return response;
	}

	@Override
	public ItemSkuUpdateResponse kaolaSkuSalePriceUpdate(String appKey, String appSecret, String accessToken,
			String key, BigDecimal sale_price) throws Exception {
		KaolaClient client = KaolaClientUtil.getInstance()
				.getKaolaClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
		ItemSkuSalePriceUpdateRequest request = new ItemSkuSalePriceUpdateRequest();
		if (StringTool.isNotEmpty(key)) {
			request.setKey(key);
		}

		request.setSale_price(sale_price);

		ItemSkuUpdateResponse response = client.execute(request);

		return response;
	}

	@Override
	public ItemGetResponse kaolaGetItem(String appKey, String appSecret, String accessToken, String key)
			throws Exception {
		KaolaClient client = KaolaClientUtil.getInstance()
				.getKaolaClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
		ItemGetRequest request = new ItemGetRequest();
		request.setKey(key);
		ItemGetResponse response = client.execute(request);

		return response;
	}

	@Override
	public ItemSearchResponse kaolaItemSearch(String appKey, String appSecret, String accessToken,
			Integer item_edit_status, Integer dataType, String beginTime, String endTime, Integer page,
			Integer pageSize) throws Exception {
		KaolaClient client = KaolaClientUtil.getInstance()
				.getKaolaClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
		ItemSearchRequest request = new ItemSearchRequest();

		request.setItem_edit_status(item_edit_status);

		if (StringTool.isNotEmpty(beginTime)) {
			request.setStart_time(beginTime);
		}
		if (StringTool.isNotEmpty(endTime)) {
			request.setEnd_time(endTime);
		}
		request.setPage_no(page);
		request.setPage_size(pageSize);
		ItemSearchResponse response = client.execute(request);

		return response;
	}

	@Override
	public ItemUpdateListingResponse ItemUpdateListing(String appKey, String appSecret, String accessToken,
			String key_list) throws Exception {
		KaolaClient client = KaolaClientUtil.getInstance()
				.getKaolaClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
		ItemUpdateListingRequest request = new ItemUpdateListingRequest();
		request.setKey_list(key_list);

		ItemUpdateListingResponse response = client.execute(request);

		return response;
	}

	@Override
	public ItemUpdateDelistingResponse ItemUpdateDelisting(String appKey, String appSecret, String accessToken,
			String key_list) throws Exception {
		KaolaClient client = KaolaClientUtil.getInstance()
				.getKaolaClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
		ItemUpdateDelistingRequest request = new ItemUpdateDelistingRequest();
		request.setKey_list(key_list);

		ItemUpdateDelistingResponse response = client.execute(request);

		return response;
	}
	
	@Override
	public  OrderSearchResponse kaolaOrderSearch(String appKey, String appSecret, String accessToken,
	  Integer orderStatus, String beginTime, String endTime, Integer page, Integer pageSize)
	  throws Exception {
	KaolaClient client = KaolaClientUtil.getInstance().getKaolaClient(
		paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret,accessToken);
	OrderSearchRequest request = new OrderSearchRequest();
	request.setOrder_status(orderStatus);
	if (StringTool.isNotEmpty(beginTime)) {
	  request.setStart_time(beginTime);
	}
	if (StringTool.isNotEmpty(endTime)) {
	  request.setEnd_time(endTime);
	}
	request.setPage_no(page);
	request.setPage_size(pageSize);

	OrderSearchResponse response = client.execute(request);

	return response;
	}
	  @Override
	  public OrderGetResponse kaolaOrderGet(String appKey, String appSecret, String accessToken,
	      String orderSN) throws Exception {
	    KaolaClient client = KaolaClientUtil.getInstance().getKaolaClient(
	        paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
	    OrderGetRequest request = new OrderGetRequest();
	    request.setOrder_id(orderSN);
	    
	    OrderGetResponse response = client.execute(request);
	    

	    logger.info("转发结果：buyer_account ----" + response.getOrder().getBuyer_account());


	    return response;
	  }
	  @Override
	  public LogisticsCompanyDeliverResponse kaolaLogisticsDeliver(String appKey, String appSecret, String accessToken,
	      String orderSN,String express_company_code, String express_no, String sku_info) throws Exception {
	    KaolaClient client = KaolaClientUtil.getInstance().getKaolaClient(
	        paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), appKey, appSecret, accessToken);
	    LogisticsCompanyDeliverRequest request = new LogisticsCompanyDeliverRequest();
	    request.setOrder_id(orderSN);
	    request.setExpress_company_code(express_company_code);
	    request.setExpress_no(express_no);
	    request.setSku_info(sku_info);
	    
	    LogisticsCompanyDeliverResponse response = client.execute(request);
	    
	    return response;
	  }
	public CmdRes digiwinKaolaExpressInfGet(CmdReq cmdReq) throws Exception {
		ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

		// 取得参数
		
		KaolaClient client = KaolaClientUtil.getInstance()
				.getKaolaClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.Kaola_API), esv.getAppKey(), esv.getAppSecret(),
						esv.getAccessToken());
		LogisticsCompanyGet request = new LogisticsCompanyGet();

		client.execute(request);
        return null;
	}	
}
