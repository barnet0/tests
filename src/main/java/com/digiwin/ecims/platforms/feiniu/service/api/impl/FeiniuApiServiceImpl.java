package com.digiwin.ecims.platforms.feiniu.service.api.impl;

import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.refund.MyFeiniuRefundGetDetailInfoResponse;
import com.digiwin.ecims.platforms.feiniu.bean.reponse.returngoods.MyFeiniuOrderReverseDetailGetResponse;
import com.digiwin.ecims.platforms.feiniu.bean.request.refund.MyFeiniuRefundGetDetailInfoRequest;
import com.digiwin.ecims.platforms.feiniu.bean.request.returngoods.MyFeiniuOrderReverseDetailGetRequest;
import com.digiwin.ecims.platforms.feiniu.service.api.FeiniuApiService;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuClientUtil;
import com.digiwin.ecims.platforms.feiniu.util.FeiniuCommonTool;
import com.digiwin.ecims.platforms.feiniu.util.translator.AomsitemTTranslator;
import com.digiwin.ecims.platforms.feiniu.util.translator.AomsordTTranslator;
import com.digiwin.ecims.platforms.feiniu.util.translator.AomsrefundTTranslator;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.feiniu.open.api.sdk.FnClient;
import com.feiniu.open.api.sdk.request.item.*;
import com.feiniu.open.api.sdk.request.order.*;
import com.feiniu.open.api.sdk.request.refund.RefundsRreceiveNeedGetListRequest;
import com.feiniu.open.api.sdk.request.returngoods.OrderReverseDetailGetRequest;
import com.feiniu.open.api.sdk.request.returngoods.OrderReverseGetRequest;
import com.feiniu.open.api.sdk.response.item.*;
import com.feiniu.open.api.sdk.response.order.*;
import com.feiniu.open.api.sdk.response.refund.RefundsRreceiveNeedGetListResponse;
import com.feiniu.open.api.sdk.response.returngoods.OrderReverseDetailGetResponse;
import com.feiniu.open.api.sdk.response.returngoods.OrderReverseGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zaregoto on 2017/1/26.
 */
@Service
public class FeiniuApiServiceImpl implements FeiniuApiService {

    @Autowired
    private ParamSystemService paramSystemService;

    @Autowired
    private AomsShopService aomsShopService;

    @Override public CmdRes execute(CmdReq cmdReq) throws Exception {
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

    @Override public CmdRes digiwinOrderDetailGet(CmdReq cmdReq) throws Exception {
        // 取得授权
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得参数
        String orderId = cmdReq.getParams().get("id");

        TradeSoldGetOrderDetailResponse response =
            fnTradesSoldGetOrderDetail(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                orderId);

        boolean success = response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && response.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(success + "", response.getMsg()),
            success ? new AomsordTTranslator(response).doTranslate(cmdReq.getStoreid()) : null);
    }

    @Override public CmdRes digiwinShippingSend(CmdReq cmdReq) throws Exception {
        // 取得授权
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得参数
        // 物流公司ID
        String expcompno = cmdReq.getParams().get("expcompno");
        // 运单号
        String expressno = cmdReq.getParams().get("expressno");
        // 订单号
        String orderId = cmdReq.getParams().get("oid");

        OrderDelivergoodsResponse response = fnOrderDeliverGoods(
            esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
            orderId, expressno, expcompno);

        boolean success = response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && response.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        return new CmdRes(cmdReq, success,
            success ?
                null : new ResponseError(success + "", response.getMsg()),
            null);
    }

    @Override public CmdRes digiwinInventoryUpdate(CmdReq cmdReq) throws Exception {
        // 取得授权
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得参数
        String numId = cmdReq.getParams().get("numid");
        String skuId = cmdReq.getParams().get("skuid");
        // 商品在ERP料号
        String outerId = cmdReq.getParams().get("outerid");
        // 数量
        String quantity = cmdReq.getParams().get("num");

        ItemSkuUpdateResponse response = fnItemSkuUpdate(
            esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
            skuId, StringTool.EMPTY, quantity, StringTool.EMPTY);

        boolean success = response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && response.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        return new CmdRes(cmdReq, success,
            success ?
                null : new ResponseError(success + "", response.getMsg()),
            null);
    }

    @Override public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq) throws Exception {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        String numId = cmdReq.getParams().get("numid");
        String skuIds = cmdReq.getParams().get("skuids");
        String outerIds = cmdReq.getParams().get("outerids");
        String nums = cmdReq.getParams().get("nums");

        ItemSkuBatchUpdateResponse response = fnItemSkuBatchUpdate(
            esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
            skuIds, nums, StringTool.EMPTY);

        boolean success = response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && response.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        return new CmdRes(cmdReq, success,
            success ?
                null : new ResponseError(success + "", response.getMsg()),
            null);
    }

    @Override public CmdRes digiwinRefundGet(CmdReq cmdReq) throws Exception {
        // 取得授权
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得参数-退款单号
        String refundId = cmdReq.getParams().get("id");

        MyFeiniuRefundGetDetailInfoResponse refundDetailResponse = fnRefundGetDetailInfo(
            esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
            refundId);

        boolean success1 = refundDetailResponse.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && refundDetailResponse.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        RefundsRreceiveNeedGetListResponse listResponse =
            fnRefundsReceiveNeedGetList(
                esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                FeiniuCommonTool.MIN_PAGE_NO,
                FeiniuCommonTool.DEFAULT_PAGE_SIZE,
                refundId, StringTool.EMPTY, StringTool.EMPTY,
                StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY, StringTool.EMPTY,
                StringTool.EMPTY);

        boolean success2 = listResponse.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && listResponse.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        boolean refundSuccess = success1 && success2;

        // 退款单
        if (refundSuccess) {
            return new CmdRes(cmdReq, refundSuccess,
                refundSuccess ? null
                    : new ResponseError(refundSuccess + "", refundDetailResponse.getMsg()),
                refundSuccess ? new AomsrefundTTranslator(
                    listResponse.getData().getPageVoList().get(0), refundDetailResponse)
                    .doTranslate(cmdReq.getStoreid()) : null);
        } else {
            // 退货单
            MyFeiniuOrderReverseDetailGetResponse orderReverseDetailGetResponse =
                fnOrderReverseDetailGet(
                    esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                    refundId);

            boolean returnSuccess = refundDetailResponse.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
                && refundDetailResponse.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

            return new CmdRes(cmdReq, returnSuccess,
                returnSuccess ? null
                    : new ResponseError(returnSuccess + "", orderReverseDetailGetResponse.getMsg()),
                returnSuccess ? new AomsrefundTTranslator(null, orderReverseDetailGetResponse)
                    .doTranslate(cmdReq.getStoreid()) : null);
        }
    }

    @Override public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
        // 取得授权
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得参数
        String goodsId = cmdReq.getParams().get("numid");

        ItemUpdateListingResponse response = fnItemUpdateListing(
            esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
            goodsId);

        boolean success = response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && response.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getCode(), response.getMsg()),
            null);
    }

    @Override public CmdRes digiwinItemDelisting(CmdReq cmdreq) throws Exception {
        // 取得授权
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdreq.getStoreid());

        // 取得参数
        String goodsId = cmdreq.getParams().get("numid");

        ItemUpdateDellistingResponse response = fnItemUpdateDellisting(
            esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
            goodsId);

        boolean success = response.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && response.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        return new CmdRes(cmdreq, success,
            success ? null : new ResponseError(response.getCode(), response.getMsg()),
            null);
    }

    @Override public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
        // 取得授权
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得API所需参数
        String goodsId = cmdReq.getParams().get("numid");

        ItemGetResponse detailResponse = fnItemGet(
            esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
            FeiniuCommonTool.ITEM_FIELDS, goodsId);

        boolean success1 = detailResponse.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && detailResponse.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        ItemInventoryGetResponse listResponse =
            fnItemInventoryGet(
                esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                StringTool.EMPTY, StringTool.EMPTY, goodsId, StringTool.EMPTY,
                FeiniuCommonTool.MIN_PAGE_NO,
                FeiniuCommonTool.MIN_PAGE_SIZE);

        boolean success2 = listResponse.getCode().equals(FeiniuCommonTool.RESPONSE_SUCCESS_CODE)
            && listResponse.getMsg().equals(FeiniuCommonTool.RESPONSE_SUCCESS_MSG);

        boolean success = success1 && success2;

        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(success + "", detailResponse.getMsg()),
            //success ? new AomsitemTTranslator(listResponse.getData(), detailResponse)  //mark by xiaohb 20170828
            success ? new AomsitemTTranslator(listResponse.getData().getList(), detailResponse)
                .doTranslate(cmdReq.getStoreid()) : null);
    }

    @Override public CmdRes digiwinItemUpdate(CmdReq cmdReq) throws Exception {
        throw new UnsupportedOperationException("_034");
    }

    @Override public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
        throw new UnsupportedOperationException("_034");
    }

    @Override
    public TradeSoldGetResponse fnTradesSoldGet(
        String appKey, String appSecret, String accessToken,
        String orderType, String dateStart, String dateEnd,
        String dateType, Long currPage, Long pageCount) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        TradeSoldGetRequest request = new TradeSoldGetRequest();
        if (StringTool.isNotEmpty(orderType)) {
            request.setOrderType(orderType);
        }
        request.setDateStart(dateStart);
        request.setDateEnd(dateEnd);
        request.setDateType(dateType);
        request.setCurrPage(currPage + "");
        request.setPageCount(pageCount + "");

        TradeSoldGetResponse response = client.execute(request);

        return response;
    }

    @Override public TradeFullinfoGetResponse fnTradeFullInfoGet(
        String appKey, String appSecret, String accessToken,
        String ogsSeq) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        TradeFullinfoGetRequest request = new TradeFullinfoGetRequest();
        if (StringTool.isNotEmpty(ogsSeq)) {
            request.setOgsSeq(ogsSeq);
        }

        TradeFullinfoGetResponse response = client.execute(request);

        return response;
    }

    @Override public TradeSoldGetOrderDetailResponse fnTradesSoldGetOrderDetail(
        String appKey, String appSecret, String accessToken,
        String ogNo) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        TradeSoldGetOrderDetailRequest request = new TradeSoldGetOrderDetailRequest();
        if (StringTool.isNotEmpty(ogNo)) {
            request.setOgNo(ogNo);
        }

        TradeSoldGetOrderDetailResponse response = client.execute(request);

        return response;
    }

    @Override
    public OrderDeliverByOgNoResponse fnOrderDeliverByOgNo(
        String appKey, String appSecret, String accessToken,
        String expressId, String expressNo, String ogNo) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        OrderDeliverByOgNoRequest request = new OrderDeliverByOgNoRequest();
        request.setExpressId(expressId);
        request.setExpressNo(expressNo);
        request.setOgNo(ogNo);

        OrderDeliverByOgNoResponse response = client.execute(request);

        return response;
    }

    @Override public OrderDelivergoodsResponse fnOrderDeliverGoods(
        String appKey, String appSecret, String accessToken,
        String ogsSeq, String expressNo, String expressId) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        OrderDelivergoodsRequest request = new OrderDelivergoodsRequest();
        request.setOgsSeq(ogsSeq);
        request.setExpressNo(expressNo);
        request.setExpressId(expressId);

        OrderDelivergoodsResponse response = client.execute(request);

        return response;
    }

    @Override public RefundsRreceiveNeedGetListResponse fnRefundsReceiveNeedGetList(
        String appKey, String appSecret, String accessToken,
        Long currPage, Long pageCount,
        String rssSeq,
        String receiveName, String receivePhone,
        String submitDateStart, String submitDateEnd,
        String skuId, String name,
        String dateType) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        RefundsRreceiveNeedGetListRequest request = new RefundsRreceiveNeedGetListRequest();
        request.setCurrPage(currPage + "");
        request.setPageCount(pageCount + "");
        if (StringTool.isNotEmpty(rssSeq)) {
            request.setRssSeq(rssSeq);
        }
        if (StringTool.isNotEmpty(receiveName)) {
            request.setReceiveName(receiveName);
        }
        if (StringTool.isNotEmpty(receivePhone)) {
            request.setReceivePhone(receivePhone);
        }
        if (StringTool.isNotEmpty(submitDateStart)) {
            request.setSubmitDateStart(submitDateStart);
        }
        if (StringTool.isNotEmpty(submitDateEnd)) {
            request.setSubmitDateEnd(submitDateEnd);
        }
        if (StringTool.isNotEmpty(skuId)) {
            request.setSkuId(skuId);
        }
        if (StringTool.isNotEmpty(name)) {
            request.setName(name);
        }
        if (StringTool.isNotEmpty(dateType)) {
            request.setDateType(dateType);
        }

        RefundsRreceiveNeedGetListResponse response = client.execute(request);

        return response;
    }

    @Override
    public MyFeiniuRefundGetDetailInfoResponse fnRefundGetDetailInfo(
        String appKey, String appSecret, String accessToken,
        String rssSeq) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        MyFeiniuRefundGetDetailInfoRequest request = new MyFeiniuRefundGetDetailInfoRequest();
        request.setRssSeq(rssSeq);

        MyFeiniuRefundGetDetailInfoResponse response = client.execute(request);

        return response;
    }

    @Override public OrderReverseGetResponse fnOrderReverseGet(
        String appKey, String appSecret, String accessToken,
        String dateType, String startDate, String endDate,
        String returnStatus,
        String skuId,
        Long currPage, Long pageCount,
        String isDesc) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        OrderReverseGetRequest request = new OrderReverseGetRequest();
        request.setDateType(dateType);
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        if (StringTool.isNotEmpty(returnStatus)) {
            request.setReturnStatus(returnStatus);
        }
        if (StringTool.isNotEmpty(skuId)) {
            request.setSkuId(skuId);
        }
        request.setCurrPage(currPage + "");
        request.setPageCount(pageCount + "");
        if (StringTool.isNotEmpty(isDesc)) {
            request.setIsDesc(isDesc);
        }

        OrderReverseGetResponse response = client.execute(request);

        return response;
    }

    @Override
    public MyFeiniuOrderReverseDetailGetResponse fnOrderReverseDetailGet(
        String appKey, String appSecret, String accessToken,
        String rssSeq) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        MyFeiniuOrderReverseDetailGetRequest request = new MyFeiniuOrderReverseDetailGetRequest();
        request.setRssSeq(rssSeq);

        MyFeiniuOrderReverseDetailGetResponse response = client.execute(request);

        return response;
    }

    @Override public ItemInventoryGetResponse fnItemInventoryGet(
        String appKey, String appSecret, String accessToken,
        String status, String title, String merchantCodeStr, String goodsId,
        Long curPage, Long pageRows) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        ItemInventoryGetRequest request = new ItemInventoryGetRequest();
        if (StringTool.isNotEmpty(status)) {
            request.setStatus(status);
        }
        if (StringTool.isNotEmpty(title)) {
            request.setTitle(title);
        }
        if (StringTool.isNotEmpty(merchantCodeStr)) {
            request.setMerchantCodeStr(merchantCodeStr);
        }
        if (StringTool.isNotEmpty(goodsId)) {
            request.setGoodsId(goodsId);
        }
        request.setCurPage(curPage + "");
        request.setPageRows(pageRows + "");

        ItemInventoryGetResponse response = client.execute(request);

        return response;
    }

    @Override public ItemGetResponse fnItemGet(
        String appKey, String appSecret, String accessToken,
        String fields, String goodsId) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        ItemGetRequest request = new ItemGetRequest();
        request.setFields(fields);
        if (StringTool.isNotEmpty(goodsId)) {
            request.setGoodsId(goodsId);
        }

        ItemGetResponse response = client.execute(request);

        return response;
    }

    @Override public ItemSkuUpdateResponse fnItemSkuUpdate(
        String appKey, String appSecret, String accessToken,
        String skuId, String price, String stock, String warehouseCode)
        throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        ItemSkuUpdateRequest request = new ItemSkuUpdateRequest();
        request.setSkuId(skuId);
        if (StringTool.isNotEmpty(price)) {
            request.setPrice(price);
        }
        if (StringTool.isNotEmpty(stock)) {
            request.setStock(stock);
        }
        if (StringTool.isNotEmpty(warehouseCode)) {
            request.setWarehouseCode(warehouseCode);
        }

        ItemSkuUpdateResponse response = client.execute(request);

        return response;
    }

    @Override
    public ItemSkuBatchUpdateResponse fnItemSkuBatchUpdate(
        String appKey, String appSecret, String accessToken,
        String skuIds, String stocks, String warehouseCodes) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        ItemSkuBatchUpdateRequest request = new ItemSkuBatchUpdateRequest();
        request.setSkuIds(skuIds);
        request.setStocks(stocks);
        if (StringTool.isNotEmpty(warehouseCodes)) {
            request.setWarehouseCodes(warehouseCodes);
        }

        ItemSkuBatchUpdateResponse response = client.execute(request);

        return response;
    }

    @Override public ItemUpdateListingResponse fnItemUpdateListing(
        String appKey, String appSecret, String accessToken,
        String goodsId) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        ItemUpdateListingRequest request = new ItemUpdateListingRequest();
        request.setGoodsId(goodsId);

        ItemUpdateListingResponse response = client.execute(request);

        return response;
    }

    @Override
    public ItemUpdateDellistingResponse fnItemUpdateDellisting(String appKey, String appSecret,
        String accessToken, String goodsId) throws Exception {
        FnClient client = FeiniuClientUtil.getInstance().getFnClient(
            paramSystemService.getEcApiUrl(EcApiUrlEnum.FEINIU_API),
            appKey, appSecret, accessToken);
        ItemUpdateDellistingRequest request = new ItemUpdateDellistingRequest();
        request.setGoodsId(goodsId);

        ItemUpdateDellistingResponse response = client.execute(request);

        return response;
    }
}
