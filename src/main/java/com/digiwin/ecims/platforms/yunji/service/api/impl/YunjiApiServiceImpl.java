package com.digiwin.ecims.platforms.yunji.service.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.core.api.EcImsApiService.EcImsApiEnum;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.kaola.bean.response.SubErrors;
import com.digiwin.ecims.platforms.kaola.bean.response.order.LogisticsCompanyDeliverResponse;
import com.digiwin.ecims.platforms.yunji.bean.YunjiClient;
import com.digiwin.ecims.platforms.yunji.bean.request.logistic.LogisticRequest;
import com.digiwin.ecims.platforms.yunji.bean.request.order.OrderDetailRequest;
import com.digiwin.ecims.platforms.yunji.bean.request.order.OrderListRequest;
import com.digiwin.ecims.platforms.yunji.bean.request.refund.OrderRefundDetailRequest;
import com.digiwin.ecims.platforms.yunji.bean.request.refund.OrderRefundListRequest;
import com.digiwin.ecims.platforms.yunji.bean.response.logistic.LogisticResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderDetailResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.order.OrderListResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundDetailResponse;
import com.digiwin.ecims.platforms.yunji.bean.response.refund.OrderRefundListResponse;
import com.digiwin.ecims.platforms.yunji.service.api.YunjiApiService;
import com.digiwin.ecims.platforms.yunji.util.YunjiClientUtil;
import com.digiwin.ecims.platforms.yunji.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.yunji.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;

@Service
public class YunjiApiServiceImpl implements YunjiApiService {

	private static final Logger logger = LoggerFactory.getLogger(YunjiApiServiceImpl.class);

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

	    OrderDetailResponse response = yunjiOrderDetail(esv.getAppKey(), esv.getAppSecret(), "1.0",orderId );

	    //boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;
	    boolean success = response.getCode() == 0;
	    logger.info("response buyercommont:" +response.getBuyerComment() + "---- success:" + success);
	    
	    
		  return new CmdRes(cmdReq, success,
			        success ? null : new ResponseError(response.getCode() + "", response.getDesc()),
			            success ? new AomsordTTranslator(response)
			                .doTranslate(cmdReq.getStoreid()) : null);
	}

	@Override
	public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
	    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());


	    LogisticResponse response = yunjiLogisticsDeliver(esv.getAppKey(), esv.getAppSecret(), "1.0",cmdReq);

	    boolean success = (response.getCode() == 0);

	    if (success) {
	    	return new CmdRes(cmdReq, success,new ResponseError("0","success"), null);	    	 
		}
	    else {
		    return new CmdRes(cmdReq, success,new ResponseError(response.getCode() + "", response.getDesc()), null);
		}	
	}

	@Override
	public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
		 // 取得授权
	    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

	    // 取得参数
	    String orderId = cmdReq.getParams().get("id");
	    String refundId = cmdReq.getParams().get("refundId");

	    OrderRefundDetailResponse response = yunjiOrderRefundDetail(esv.getAppKey(), esv.getAppSecret(), "1.0",refundId,orderId );

	    //boolean success = response.getResult() == KaolaCommonTool.RESPONSE_SUCCESS_CODE;
	    boolean success = response.getCode() == 0;
	    
	    
		  return new CmdRes(cmdReq, success,
			        success ? null : new ResponseError(response.getCode() + "", response.getDesc()),
			            success ? new AomsrefundTTranslator(response)
			                .doTranslate(cmdReq.getStoreid()) : null);
	}

	@Override
	public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinItemDelisting(CmdReq cmdreq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderListResponse yunjiOrderList(String appKey, String appSecret,String version, String status, String startTime,
			String endTime, Integer pageNo, Integer pageSize, Integer queryType) throws Exception {
		YunjiClient client = YunjiClientUtil.getInstance().getYunjiClient(
				paramSystemService.getEcApiUrl(EcApiUrlEnum.Yunji_API), appKey, appSecret,version);
			OrderListRequest request = new OrderListRequest();
			request.setStatus(status);
			if (StringTool.isNotEmpty(startTime)) {
			  request.setStartTime(startTime);
			}
			if (StringTool.isNotEmpty(endTime)) {
			  request.setEndTime(endTime);
			}
			request.setPageNo(pageSize);
			request.setPageSize(pageNo);
			
	
			
			request.setQueryType(queryType);
			//add by test
			logger.info("\r\n endTime:" + request.getEndTime());

			OrderListResponse response = client.execute(request);

			
			
			return response;
	}
	@Override
	public OrderDetailResponse yunjiOrderDetail(String appKey, String appSecret, String version, String orderId) throws Exception{
		YunjiClient client = YunjiClientUtil.getInstance().getYunjiClient(
				paramSystemService.getEcApiUrl(EcApiUrlEnum.Yunji_API), appKey, appSecret,version);
			OrderDetailRequest request = new OrderDetailRequest();
			request.setOrderId(orderId);

			OrderDetailResponse response = client.execute(request);

			return response;
	}

	@Override
	public OrderRefundListResponse yunjiOrderRefundList(String appKey, String appSecret, String version, String status,
			String startTime, String endTime, Integer pageNo, Integer pageSize, Integer queryType) throws Exception {
		YunjiClient client = YunjiClientUtil.getInstance().getYunjiClient(
				paramSystemService.getEcApiUrl(EcApiUrlEnum.Yunji_API), appKey, appSecret,version);
			OrderRefundListRequest request = new OrderRefundListRequest();
			request.setReturnStatus(status);
			if (StringTool.isNotEmpty(startTime)) {
			  request.setStartTime(startTime);
			}
			if (StringTool.isNotEmpty(endTime)) {
			  request.setEndTime(endTime);
			}
			request.setPageNo(pageNo);
			request.setPageSize(pageSize);
			request.setQueryType(queryType);
			//add by test
			logger.info("\r\n endTime:" + request.getEndTime());

			OrderRefundListResponse response = client.execute(request);

			return response;
	}

	@Override
	public OrderRefundDetailResponse yunjiOrderRefundDetail(String appKey, String appSecret, String version,
			String refundId, String orderId) throws Exception {
		YunjiClient client = YunjiClientUtil.getInstance().getYunjiClient(
				paramSystemService.getEcApiUrl(EcApiUrlEnum.Yunji_API), appKey, appSecret,version);
			OrderRefundDetailRequest request = new OrderRefundDetailRequest();
			request.setOrderId(orderId);
			request.setRefundId(refundId);

			OrderRefundDetailResponse response = client.execute(request);

			return response;
	}
	
	
	/**
	 * 说明：  发货回传
	 * @param appKey
	 * @param appSecret
	 * @param version
	 * @param cmdReq
	 * @return
	 * @throws Exception
	 */
	public LogisticResponse yunjiLogisticsDeliver(String appKey, String appSecret, String version, CmdReq cmdReq)throws Exception{
		YunjiClient client = YunjiClientUtil.getInstance().getYunjiClient(
				paramSystemService.getEcApiUrl(EcApiUrlEnum.Yunji_API), appKey, appSecret,version);
		LogisticRequest request = new LogisticRequest();
			request.setOrderId(cmdReq.getParams().get("orderId"));
			request.setStatus(Integer.parseInt(cmdReq.getParams().get("status")));
			request.setLogisticsList(cmdReq.getLogisticlist());
			logger.info("orderId:" + cmdReq.getParams().get("orderId"));
			logger.info("status:" + cmdReq.getParams().get("status"));
			LogisticResponse response = client.execute(request);

			return response;
	}

}
