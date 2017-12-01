package com.digiwin.ecims.platforms.taobao.service.api.impl;

import com.digiwin.ecims.core.bean.area.AreaNode;
import com.digiwin.ecims.core.bean.area.AreaResponse;
import com.digiwin.ecims.core.bean.request.CmdReq;
import com.digiwin.ecims.core.bean.response.CmdRes;
import com.digiwin.ecims.core.bean.response.ResponseError;
import com.digiwin.ecims.core.dao.BaseDAO;
import com.digiwin.ecims.core.model.AomsshopT;
import com.digiwin.ecims.core.service.AomsShopService;
import com.digiwin.ecims.core.service.impl.AomsShopServiceImpl.ESVerification;
import com.digiwin.ecims.core.util.ErrorMessageBox;
import com.digiwin.ecims.core.util.JsonUtil;
import com.digiwin.ecims.core.util.StringTool;
import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.platforms.taobao.bean.request.EcimsTaobaoWaybillIPrintReqParam;
import com.digiwin.ecims.platforms.taobao.bean.request.EcimsWaybillIGetOrdersParam;
import com.digiwin.ecims.platforms.taobao.bean.response.cainiao.waybillget.CainiaoWaybillGetPrintData;
import com.digiwin.ecims.platforms.taobao.bean.response.cmd.returnvalue.EcimsLogisticsTraceSearchRv;
import com.digiwin.ecims.platforms.taobao.bean.response.cmd.returnvalue.EcimsWaybillGetRv;
import com.digiwin.ecims.platforms.taobao.bean.response.cmd.returnvalue.EcimsWaybillIPrintRv;
import com.digiwin.ecims.platforms.taobao.enums.CainiaoParamEnum;
import com.digiwin.ecims.platforms.taobao.service.api.TaobaoApiService;
import com.digiwin.ecims.platforms.taobao.service.translator.item.TaobaoJdpFxItemService;
import com.digiwin.ecims.platforms.taobao.service.translator.item.TaobaoJdpTbItemService;
import com.digiwin.ecims.platforms.taobao.service.translator.refund.TaobaoJdpFxRefundService;
import com.digiwin.ecims.platforms.taobao.service.translator.refund.TaobaoJdpTbRefundService;
import com.digiwin.ecims.platforms.taobao.service.translator.trade.TaobaoJdpFxTradeService;
import com.digiwin.ecims.platforms.taobao.service.translator.trade.TaobaoJdpTbTradeService;
import com.digiwin.ecims.platforms.taobao.util.TaobaoClientUtil;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool.FxProductStatus;
import com.digiwin.ecims.platforms.taobao.util.TaobaoCommonTool.TaobaoTradeRateRole;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.taobao.api.ApiException;
import com.taobao.api.AutoRetryClusterTaobaoClient;
import com.taobao.api.Constants;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.*;
import com.taobao.api.request.*;
import com.taobao.api.request.CainiaoWaybillIiGetRequest.WaybillCloudPrintApplyNewRequest;
import com.taobao.api.request.WlbWaybillIPrintRequest.PrintCheckInfo;
import com.taobao.api.request.WlbWaybillIPrintRequest.WaybillApplyPrintCheckRequest;
import com.taobao.api.response.*;
import com.taobao.top.schema.exception.TopSchemaException;
import com.taobao.top.schema.factory.SchemaReader;
import com.taobao.top.schema.factory.SchemaWriter;
import com.taobao.top.schema.field.*;
import com.taobao.top.schema.label.LabelGroup;
import com.taobao.top.schema.option.Option;
import com.taobao.top.schema.value.ComplexValue;
import com.taobao.top.schema.value.Value;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service public class TaobaoApiServiceImpl implements TaobaoApiService {

    private static final Logger logger = LoggerFactory.getLogger(TaobaoApiServiceImpl.class);

    @Autowired private ParamSystemService paramSystemService;

    @Autowired private TaobaoJdpTbTradeService tableJdpTbTrade;

    @Autowired private TaobaoJdpFxTradeService tableJdpFxTrade;

    @Autowired private TaobaoJdpTbRefundService tableJdpTbRefund;

    @Autowired private TaobaoJdpFxRefundService tableJdpFxRefund;

    @Autowired private TaobaoJdpTbItemService tableJdpTbItem;

    @Autowired private TaobaoJdpFxItemService tableJdpFxItem;

    @Autowired private AomsShopService aomsShopService;

    @Autowired private BaseDAO baseDAO;

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
            case DIGIWIN_LOGISTICS_AREAS_GET:
                cmdRes = digiwinLogisticsAreasGet(cmdReq);
                break;
            case DIGIWIN_WLB_WAYBILL_I_GET:
                cmdRes = digiwinWlbWaybillIGet(cmdReq);
                break;
            case DIGIWIN_WLB_WAYBILL_I_UPDATE:
                cmdRes = digiwinWlbWaybillIUpdate(cmdReq);
                break;
            case DIGIWIN_WLB_WAYBILL_I_CANCEL:
                cmdRes = digiwinWlbWaybillICancel(cmdReq);
                break;
            //      case DIGIWIN_WLB_WAYBILL_I_PRINT:
            //        cmdRes = digiwinWlbWaybillIPrint(cmdReq);
            //        break;
            case DIGIWIN_ONLINE_SHIPPING_SEND:
                cmdRes = digiwinOnlineShippingSend(cmdReq);
                break;
            case DIGIWIN_LOGISTICS_TRACE_SEARCH:
                cmdRes = digiwinLogisticsTraceSearch(cmdReq);
                break;
            case DIGIWIN_RATE_ADD:
                cmdRes = digiwinRateAdd(cmdReq);
                break;
            case DIGIWIN_RATE_LIST_ADD:
                cmdRes = digiwinRateListAdd(cmdReq);
                break;
            default:
                cmdRes =
                    new CmdRes(cmdReq, false, new ResponseError("034", ErrorMessageBox._034), null);
                break;
        }

        return cmdRes;
    }

    @Override public CmdRes digiwinOrderDetailGet(CmdReq cmdReq)
        throws ClientProtocolException, IOException, ApiException {
        String ecNo = cmdReq.getEcno();
        if (ecNo.equals(TaobaoCommonTool.STORE_TYPE)) {
            return taobaoTradeFullInfoGet(cmdReq);
        } else if (ecNo.equals(TaobaoCommonTool.STORE_TYPE_FX)) {
            return taobaoFenxiaoOrderGet(cmdReq);
        } else {
            throw new IllegalArgumentException("_32");
        }
    }

    @Override public CmdRes digiwinRefundGet(CmdReq cmdReq)
        throws ClientProtocolException, IOException, ApiException {
        Map<String, String> params = cmdReq.getParams();

        if (params.get("type").equals(REFUND_TYPE_NORMAL)) {// 一般退貨單
            return taobaoRefundGet(cmdReq);
        } else if (params.get("type").equals(REFUND_TYPE_TBFX)) {// 分銷退貨單
            return taobaoFenxiaoRefundGet(cmdReq);
        } else {
            throw new IllegalArgumentException("_39");
        }
    }

    @Override public CmdRes digiwinItemDetailGet(CmdReq cmdReq) throws Exception {
        String ecNo = cmdReq.getEcno();
        if (ecNo.equals(TaobaoCommonTool.STORE_TYPE)) {
            return taobaoItemGet(cmdReq);
        } else if (ecNo.equals(TaobaoCommonTool.STORE_TYPE_FX)) {
            return taobaoFenxiaoProductsGet(cmdReq);
        } else {
            throw new IllegalArgumentException("_32");
        }
    }

    @Override public CmdRes digiwinItemListing(CmdReq cmdReq) throws Exception {
        String ecNo = cmdReq.getEcno();
        if (ecNo.equals(TaobaoCommonTool.STORE_TYPE)) {
            return taobaoItemUpdateListing(cmdReq);
        } else if (ecNo.equals(TaobaoCommonTool.STORE_TYPE_FX)) {
            return taobaoFenxiaoItemUpdateListing(cmdReq);
        } else {
            throw new IllegalArgumentException("_32");
        }
    }

    @Override public CmdRes digiwinItemDelisting(CmdReq cmdReq) throws Exception {
        String ecNo = cmdReq.getEcno();
        if (ecNo.equals(TaobaoCommonTool.STORE_TYPE)) {
            return taobaoItemUpdateDelisting(cmdReq);
        } else if (ecNo.equals(TaobaoCommonTool.STORE_TYPE_FX)) {
            return taobaoFenxiaoItemUpdateDelisting(cmdReq);
        } else {
            throw new IllegalArgumentException("_32");
        }
    }

    /**
     * 商品信息更新
     *
     * @param cmdReq
     * @return
     * @throws NumberFormatException
     * @throws ApiException
     * @throws TopSchemaException
     * @author 维杰
     * @since 2015.10.08
     */
    @Override public CmdRes digiwinItemUpdate(CmdReq cmdReq)
        throws NumberFormatException, ApiException, TopSchemaException {
        CmdRes cmdRes = null;
        // 获取卖家用户信息，调用不同的方法
        // 如果为B,是商城卖家,调用天猫的方法;
        // 如果为C,是淘宝卖家,调用淘宝的方法.
        AomsshopT aomsshopT = aomsShopService.getStoreByStoreId(cmdReq.getStoreid());
        String userType = aomsshopT.getAomsshop011();
        User user = new User();
        // User user = taobaoUserSellerGet(cmdReq);
        switch (userType) {
            case TaobaoCommonTool.B_SELLER_TYPE_IN_DB:
                user.setType("B");
                break;
            case TaobaoCommonTool.C_SELLER_TYPE_IN_DB:
                user.setType("C");
                break;
            case TaobaoCommonTool.FX_SELLER_TYPE_IN_DB:
                break;
            default:
                break;
        }
        switch (user.getType()) {
            case "B":
                cmdRes = tmallItemUpdate(cmdReq);
                break;
            case "C":
                // cmdRes = new CmdRes(cmdReq, false, new ResponseError(
                // "011", "淘宝(C卖家)商品更新API建设中..."
                // ), null);
                cmdRes = taobaoItemUpdate(cmdReq);
                break;
            default:
                break;
        }
        // if (user != null) {
        // switch (user.getType()) {
        // case "B":
        // cmdRes = tmallItemUpdate(cmdReq);
        // break;
        // case "C":
        //// cmdRes = new CmdRes(cmdReq, false, new ResponseError(
        //// "011", "淘宝(C卖家)商品更新API建设中..."
        //// ), null);
        // cmdRes = taobaoItemUpdate(cmdReq);
        // break;
        // default:
        // break;
        // }
        // } else {
        // cmdRes = new CmdRes(cmdReq, false, new ResponseError(
        // "011", "请检查店铺ID与店铺授权资料是否有效."
        // ), null);
        // }

        return cmdRes;
    }

    @Override public CmdRes digiwinInventoryUpdate(CmdReq cmdReq)
        throws ApiException, IOException, NumberFormatException {
        String ecNo = cmdReq.getEcno();
        if (ecNo.equals(TaobaoCommonTool.STORE_TYPE)) {
            return taobaoItemQuantityUpdate(cmdReq);
        } else if (ecNo.equals(TaobaoCommonTool.STORE_TYPE_FX)) {
            return taobaoFenxiaoProductUpdate(cmdReq);
        } else {
            throw new IllegalArgumentException("_32");
        }
    }

    @Override public CmdRes digiwinInventoryBatchUpdate(CmdReq cmdReq)
        throws NumberFormatException, IOException, ApiException {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        String numId = cmdReq.getParams().get("numid");
        String skuIds = cmdReq.getParams().get("skuids");
        String outerIds = cmdReq.getParams().get("outerids");
        String nums = cmdReq.getParams().get("nums");

    /*
     * mark by mowj 20151222 与水星张志伟沟通后，发现有这种情况“ 在ERP有可能一个宝贝只更新一个SKU，或者一个宝贝只有一个SKU且需要更新，
     * 同时，另外的宝贝有多个SKU需要更新库存， 那么，如果对逗号做严格限制的话，不太符合实际业务的情形。 于是商量后，决定去除逗号的限制。 if
     * (skuIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) <= 0 ||
     * outerIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) <= 0 ||
     * nums.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) <= 0 ||
     * skuIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) == skuIds.length() - 1 ||
     * outerIds.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) == outerIds.length() - 1 ||
     * nums.indexOf(CMD_INVENTORY_UPDATE_DELIMITER) == nums.length() - 1) { return new
     * CmdRes(cmdReq, false, new ResponseError("039", ErrorMessageBox._039), null); }
     */
        String[] skuIdArray = skuIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
        String[] outerIdArray = outerIds.split(CMD_INVENTORY_UPDATE_DELIMITER);
        String[] numArray = nums.split(CMD_INVENTORY_UPDATE_DELIMITER);

        int skuIdCount = skuIdArray.length;
        int outerIdCount = outerIdArray.length;
        int numCount = numArray.length;

        if (skuIdCount != outerIdCount || skuIdCount != numCount || outerIdCount != numCount) {
            return new CmdRes(cmdReq, false, new ResponseError("039", ErrorMessageBox._039), null);
        }

        StringBuilder listStringBuilder = new StringBuilder();
        String skuidQuantities = "";

        // 准备productStockList
        for (int i = 0; i < skuIdCount; i++) {
            listStringBuilder.append(skuIdArray[i])
                .append(TaobaoCommonTool.SKU_QUANTITY_UPDATE_INNER_DELIMITER).append(numArray[i]);
            if (i + 1 == skuIdCount) {

            } else {
                listStringBuilder.append(TaobaoCommonTool.SKU_QUANTITY_UPDATE_OUTER_DELIMITER);
            }
        }
        skuidQuantities = listStringBuilder.toString();

        // 清除StringBuffer
        listStringBuilder.setLength(0);

        SkusQuantityUpdateResponse response =
            taobaoSkusQuantityUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                Long.parseLong(numId), TaobaoCommonTool.INVENTORY_FULL_UPDATE_TYPE,
                skuidQuantities);

        boolean isSuccess = (response.getItem() != null);
        return new CmdRes(cmdReq, isSuccess,
            isSuccess ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            null);
    }

    @Override public CmdRes digiwinShippingSend(CmdReq cmdReq)
        throws ApiException, NumberFormatException, IOException {
        return taobaoLogisticsOfflineSend(cmdReq);
    }

    @Override public CmdRes digiwinLogisticsAreasGet(CmdReq cmdReq) throws ApiException {
        return getAreaNodeTree(cmdReq);
    }

    @Override public CmdRes digiwinWlbWaybillIGet(CmdReq cmdReq)
        throws ApiException, NumberFormatException {
        // 菜鸟物流面单申请，需要请订单中心告知申请菜鸟物流服务的店铺ID，这样才能找到对应的access_token进行调用
        // 而cmd中的storeid则是表示参数中订单所属的店铺ID，这里只需要获取店铺在平台 的id即可
        // 取得订单所属店铺授权
        ESVerification userShopEsv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Map<String, String> params = cmdReq.getParams();
        // 取得申请菜鸟物流服务的店铺授权
        ESVerification applyShopEsv =
            aomsShopService.getAuthorizationByStoreId(params.get("applystoreid"));

        String cpCode = params.get("cpcode");
        String sendProvince = params.get("sendprovince");
        String sendCity = params.get("sendcity");
        String sendArea = params.get("sendarea");
        String sendAddress = params.get("sendaddress");
        String senderName = params.get("sendername");
        String senderPhone = params.get("senderphone");
        String senderMobile = params.get("sendermobile");
        String productCode = params.get("expressprodtype");

        com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto senderInfo =
            new com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto();
        com.taobao.api.request.CainiaoWaybillIiGetRequest.AddressDto senderAddressInfo =
            new com.taobao.api.request.CainiaoWaybillIiGetRequest.AddressDto();

        senderInfo.setName(senderName);
        senderInfo.setPhone(senderPhone);
        senderInfo.setMobile(senderMobile);
        senderInfo.setAddress(senderAddressInfo);

        senderAddressInfo.setProvince(sendProvince);
        senderAddressInfo.setCity(sendCity);
        senderAddressInfo.setDistrict(sendArea);
        senderAddressInfo.setDetail(sendAddress);

        List<com.taobao.api.request.CainiaoWaybillIiGetRequest.TradeOrderInfoDto> tradeOrderInfos =
            new ArrayList<com.taobao.api.request.CainiaoWaybillIiGetRequest.TradeOrderInfoDto>();

        // 获取需要提交给菜鸟物流的订单信息
        List<EcimsWaybillIGetOrdersParam> orders = cmdReq.getOrderlist();
        for (int i = 0; i < orders.size(); i++) {
            EcimsWaybillIGetOrdersParam ordersParam = orders.get(i);

            com.taobao.api.request.CainiaoWaybillIiGetRequest.TradeOrderInfoDto tradeInfo =
                new com.taobao.api.request.CainiaoWaybillIiGetRequest.TradeOrderInfoDto();

            com.taobao.api.request.CainiaoWaybillIiGetRequest.OrderInfoDto orderInfo =
                new com.taobao.api.request.CainiaoWaybillIiGetRequest.OrderInfoDto();
            com.taobao.api.request.CainiaoWaybillIiGetRequest.PackageInfoDto packageInfo =
                new com.taobao.api.request.CainiaoWaybillIiGetRequest.PackageInfoDto();
            com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto userInfo =
                new com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto();
            com.taobao.api.request.CainiaoWaybillIiGetRequest.AddressDto userAddressInfo =
                new com.taobao.api.request.CainiaoWaybillIiGetRequest.AddressDto();

            // 对tradeinfo处理
            tradeInfo.setObjectId((i + 1) + "");

            tradeInfo.setOrderInfo(orderInfo);
            tradeInfo.setPackageInfo(packageInfo);
            tradeInfo.setRecipient(userInfo);

            tradeInfo.setTemplateUrl((String) paramSystemService
                .getSysParamByKey(CainiaoParamEnum.getKeyByCpcode(cpCode)));
            tradeInfo.setUserId(Long.parseLong(userShopEsv.getUserStoreId()));

            // // 对orderinfo处理
            orderInfo.setOrderChannelsType(ordersParam.getOrdChannel());
            orderInfo.setTradeOrderList(ordersParam.getSubOidList());
            // // 对packageinfo处理
            packageInfo.setId(ordersParam.getPackageId());
            // // // 对packageinfo中的Item处理
            List<com.taobao.api.request.CainiaoWaybillIiGetRequest.Item> packageItems =
                new ArrayList<com.taobao.api.request.CainiaoWaybillIiGetRequest.Item>();
            packageInfo.setItems(packageItems);
            for (Map<String, String> itemMap : ordersParam.getItemList()) {
                com.taobao.api.request.CainiaoWaybillIiGetRequest.Item packageItem =
                    new com.taobao.api.request.CainiaoWaybillIiGetRequest.Item();
                packageItem.setName(itemMap.get("itemname"));
                // 防止读取到2.000000这种值，所以先使用double获取，再转换成long
                packageItem.setCount((long) (Double.parseDouble(itemMap.get("cnt"))));

                packageItems.add(packageItem);
            }
            // // 对userinfo处理
            userInfo.setAddress(userAddressInfo);
            userAddressInfo.setProvince(ordersParam.getRcvProvince());
            userAddressInfo.setDetail(ordersParam.getRcvAddress());
            userInfo.setName(ordersParam.getRcvName());
            userInfo.setPhone(ordersParam.getRcvPhone());
            userInfo.setMobile(ordersParam.getRcvMobile());

            tradeOrderInfos.add(tradeInfo);
        }

        com.taobao.api.response.CainiaoWaybillIiGetResponse response =
            cainiaoWaybillIiGet(applyShopEsv.getAppKey(), applyShopEsv.getAppSecret(),
                applyShopEsv.getAccessToken(), cpCode, productCode, senderInfo, tradeOrderInfos);
        logger.info("cainiaoWaybillIiGetResponse: {}", response.getBody());

        List<EcimsWaybillGetRv> newWaybillValues = new ArrayList<EcimsWaybillGetRv>();
        boolean success = response.getModules() == null ? false : true;

        if (success) {
            for (int i = 0; i < response.getModules().size(); i++) {
                com.taobao.api.response.CainiaoWaybillIiGetResponse.WaybillCloudPrintResponse
                    printResponse = response.getModules().get(i);

                CainiaoWaybillGetPrintData printData = JsonUtil
                    .jsonToObject(printResponse.getPrintData(), CainiaoWaybillGetPrintData.class);

                EcimsWaybillGetRv wayBillReturnValue = new EcimsWaybillGetRv();
                wayBillReturnValue.setWaybillCode(printResponse.getWaybillCode());
                wayBillReturnValue
                    .setShortAddress(printData.getData().getRoutingInfo().getSortation().getName());
                wayBillReturnValue.setPackCentCode(
                    printData.getData().getRoutingInfo().getConsolidation().getCode());
                wayBillReturnValue.setPackCentName(
                    printData.getData().getRoutingInfo().getConsolidation().getName());
                wayBillReturnValue.setRcvName(printData.getData().getRecipient().getName());
                wayBillReturnValue.setRcvPhone(printData.getData().getRecipient().getPhone());
                wayBillReturnValue.setRcvMobile(printData.getData().getRecipient().getMobile());
                wayBillReturnValue.setOid(orders.get(i).getOid()); // 此处取请求参数中同一位置的orderlist的oid
                wayBillReturnValue.setSubOidList(orders.get(i).getSubOidList());

                newWaybillValues.add(wayBillReturnValue);
            }
        }

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null,
            newWaybillValues);

    }

    @Override public CmdRes digiwinWlbWaybillIUpdate(CmdReq cmdReq) throws ApiException {
        // 取得订单所属店铺授权
        //    ESVerification userShopEsv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Map<String, String> params = cmdReq.getParams();
        // 取得申请菜鸟物流服务的店铺授权
        ESVerification applyShopEsv =
            aomsShopService.getAuthorizationByStoreId(params.get("applystoreid"));

        String cpCode = params.get("cpcode");
        String waybillCode = params.get("waybillcode");
        String senderName = params.get("sendername");
        String senderPhone = params.get("senderphone");
        String senderMobile = params.get("sendermobile");
        String rcvName = params.get("rcvname");
        String rcvPhone = params.get("rcvphone");
        String rcvMobile = params.get("rcvmobile");
        String rcvProvince = params.get("rcvprovince");
        String rcvCity = params.get("rcvcity");
        String rcvArea = params.get("rcvarea");
        String rcvAddress = params.get("rcvaddress");
        List<? extends Map<String, String>> itemList = cmdReq.getItemlist();

        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.PackageInfoDto packageInfo =
            new com.taobao.api.request.CainiaoWaybillIiUpdateRequest.PackageInfoDto();

        List<com.taobao.api.request.CainiaoWaybillIiUpdateRequest.Item> packageItems =
            new ArrayList<com.taobao.api.request.CainiaoWaybillIiUpdateRequest.Item>();
        packageInfo.setItems(packageItems);

        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.UserInfoDto recipient =
            new com.taobao.api.request.CainiaoWaybillIiUpdateRequest.UserInfoDto();
        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.AddressDto recipientAddress =
            new com.taobao.api.request.CainiaoWaybillIiUpdateRequest.AddressDto();
        recipient.setAddress(recipientAddress);

        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.UserInfoDto sender =
            new com.taobao.api.request.CainiaoWaybillIiUpdateRequest.UserInfoDto();

        for (Map<String, String> itemMap : itemList) {
            com.taobao.api.request.CainiaoWaybillIiUpdateRequest.Item packageItem =
                new com.taobao.api.request.CainiaoWaybillIiUpdateRequest.Item();
            packageItem.setName(itemMap.get("itemname"));
            packageItem.setCount((long) (Double.parseDouble(itemMap.get("cnt"))));

            packageItems.add(packageItem);
        }

        recipientAddress.setProvince(rcvProvince);
        recipientAddress.setCity(rcvCity);
        recipientAddress.setDistrict(rcvArea);
        recipientAddress.setDetail(rcvAddress);

        recipient.setMobile(rcvMobile);
        recipient.setName(rcvName);
        recipient.setPhone(rcvPhone);

        sender.setMobile(senderMobile);
        sender.setName(senderName);
        sender.setPhone(senderPhone);

        com.taobao.api.response.CainiaoWaybillIiUpdateResponse response =
            cainiaoWaybillIiUpdate(applyShopEsv.getAppKey(), applyShopEsv.getAppSecret(),
                applyShopEsv.getAccessToken(), cpCode, null, packageInfo, recipient, sender,
                (String) paramSystemService
                    .getSysParamByKey(CainiaoParamEnum.getKeyByCpcode(cpCode)), waybillCode, null);

        boolean success = response.getSubCode() != null;

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null, null);
    }

    @Override public CmdRes digiwinWlbWaybillICancel(CmdReq cmdReq) throws ApiException {
        // 取得订单所属店铺授权
        //    ESVerification userShopEsv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Map<String, String> params = cmdReq.getParams();
        // 取得申请菜鸟物流服务的店铺授权
        ESVerification applyShopEsv =
            aomsShopService.getAuthorizationByStoreId(params.get("applystoreid"));

        String cpCode = params.get("cpcode");
        String waybillCode = params.get("waybillcode");

        com.taobao.api.response.CainiaoWaybillIiCancelResponse response =
            cainiaoWaybillIiCancel(applyShopEsv.getAppKey(), applyShopEsv.getAppSecret(),
                applyShopEsv.getAccessToken(), cpCode, waybillCode);

        boolean success = response.isSuccess();

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null, null);
    }

    @Override public CmdRes digiwinWlbWaybillIPrint(CmdReq cmdReq) throws ApiException {
        // 取得订单所属店铺授权
        ESVerification userShopEsv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Map<String, String> params = cmdReq.getParams();
        // 取得申请菜鸟物流服务的店铺授权
        ESVerification applyShopEsv =
            aomsShopService.getAuthorizationByStoreId(params.get("applystoreid"));

        String cpCode = params.get("cpcode");
        List<EcimsTaobaoWaybillIPrintReqParam> printList = cmdReq.getPrintlist();

        List<PrintCheckInfo> printCheckInfos = new ArrayList<PrintCheckInfo>();
        for (EcimsTaobaoWaybillIPrintReqParam printReqParam : printList) {
            PrintCheckInfo printCheckInfo = new PrintCheckInfo();
            printCheckInfo.setConsigneeName(printReqParam.getRcvName());
            printCheckInfo.setWaybillCode(printReqParam.getWaybillCode());
            printCheckInfo.setShortAddress(printReqParam.getShortAddress());
            printCheckInfo.setConsigneePhone(printReqParam.getRcvPhone());

            com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress shippingAddress =
                new com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress();
            shippingAddress.setProvince(printReqParam.getSendProvince());
            shippingAddress.setCity(printReqParam.getSendCity());
            shippingAddress.setArea(printReqParam.getSendArea());
            shippingAddress.setAddressDetail(printReqParam.getSendAddress());

            printCheckInfo.setShippingAddress(shippingAddress);

            com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress consigneeAddress =
                new com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress();
            consigneeAddress.setProvince(printReqParam.getRcvProvince());
            consigneeAddress.setAddressDetail(printReqParam.getRcvAddress());

            printCheckInfo.setConsigneeAddress(consigneeAddress);
            printCheckInfo.setConsigneePhone(printReqParam.getRcvPhone());
            printCheckInfo.setRealUserId(Long.parseLong(userShopEsv.getUserStoreId()));

            printCheckInfos.add(printCheckInfo);
        }
        WlbWaybillIPrintResponse response =
            taobaoWlbWaybillIPrint(applyShopEsv.getAppKey(), applyShopEsv.getAppSecret(),
                applyShopEsv.getAccessToken(), cpCode, printCheckInfos);
        List<EcimsWaybillIPrintRv> newWaybillValues = new ArrayList<EcimsWaybillIPrintRv>();
        boolean success = response.getWaybillApplyPrintCheckInfos() == null ? false : true;

        if (success) {
            for (WaybillApplyPrintCheckInfo printCheckInfo : response
                .getWaybillApplyPrintCheckInfos()) {
                EcimsWaybillIPrintRv wayBillReturnValue = new EcimsWaybillIPrintRv();
                wayBillReturnValue.setWaybillCode(printCheckInfo.getWaybillCode());
                wayBillReturnValue.setPrintCnt(printCheckInfo.getPrintQuantity() + "");
                wayBillReturnValue.setNoticeId(printCheckInfo.getNoticeCode());
                wayBillReturnValue.setNoticeMsg(printCheckInfo.getNoticeMessage());

                newWaybillValues.add(wayBillReturnValue);
            }
        }

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null,
            newWaybillValues);
    }

    @Override public CmdRes digiwinOnlineShippingSend(CmdReq cmdReq)
        throws ApiException, NumberFormatException {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        Map<String, String> params = cmdReq.getParams();

        String oid = params.get("oid");
        String expressNo = params.get("expressno");
        String expCompNo = params.get("expcompno");
        String subOids = "";
        if (cmdReq.getSuboidlist() != null && cmdReq.getSuboidlist().length > 0) {
            for (String subOid : cmdReq.getSuboidlist()) {
                subOids += subOid + ",";
            }
            subOids = subOids.substring(0, subOids.length() - 1);
        }
        boolean isSplit = !"".equals(subOids);

        LogisticsOnlineSendResponse response =
            taobaoLogisticsOnlineSend(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                Long.parseLong(oid), isSplit, expressNo, expCompNo, subOids);

        boolean success = response.getErrorCode() == null;

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null, null);
    }

    @Override public CmdRes digiwinLogisticsTraceSearch(CmdReq cmdReq)
        throws ApiException, NumberFormatException, IOException {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        Map<String, String> params = cmdReq.getParams();

        Long oid = Long.parseLong(params.get("oid"));
        String subOidList = params.get("suboidlist");
        boolean isSpilt = false;
        if (StringTool.isNotEmpty(subOidList)) {
            isSpilt = true;
        }

        LogisticsTraceSearchResponse response =
            taobaoLogisticsTraceSearch(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                oid, esv.getUserSellerNick(), isSpilt ? 1L : 0L, subOidList);

        boolean success = response.getErrorCode() == null;

        List<EcimsLogisticsTraceSearchRv> returnValues =
            new ArrayList<EcimsLogisticsTraceSearchRv>();
        if (success) {
            for (TransitStepInfo transitInfo : response.getTraceList()) {
                EcimsLogisticsTraceSearchRv traceRv = new EcimsLogisticsTraceSearchRv();
                traceRv.setAction(transitInfo.getAction());
                traceRv.setDesc(transitInfo.getStatusDesc());
                traceRv.setTime(transitInfo.getStatusTime());

                returnValues.add(traceRv);
            }
        }

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null,
            returnValues);
    }

    @Override public CmdRes digiwinRateAdd(CmdReq cmdReq)
        throws ApiException, NumberFormatException, IOException {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        Map<String, String> params = cmdReq.getParams();

        String oid = params.get("oid");
        String suboid = params.get("suboid") == null ? StringTool.EMPTY : params.get("suboid");
        String result = params.get("result");
        String content = params.get("content");

        TraderateAddResponse response =
            taobaoTradeRateAdd(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                Long.parseLong(oid), Long.parseLong(suboid), result, content);

        boolean success = response.getErrorCode() == null;

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null, null);
    }

    @Override public CmdRes digiwinRateListAdd(CmdReq cmdReq)
        throws ApiException, NumberFormatException, IOException {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        Map<String, String> params = cmdReq.getParams();

        String oid = params.get("oid");
        String result = params.get("result");
        String content = params.get("content");

        TraderateListAddResponse response =
            taobaoTradeRateListAdd(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                Long.parseLong(oid), result, content);

        boolean success = response.getErrorCode() == null;

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null, null);
    }

    /**
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ApiException
     */
    private CmdRes taobaoTradeFullInfoGet(CmdReq cmdReq) throws IOException, ApiException {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        String storeId = cmdReq.getStoreid();

        // 取參數
        String tradeId = cmdReq.getParams().get("id");

        TradeFullinfoGetResponse response =
            taobaoTradeFullinfoGet(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                TaobaoCommonTool.API_ORDER_FIELDS, Long.parseLong(tradeId));

        boolean success = response.getErrorCode() == null;
        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            success ?
                tableJdpTbTrade.parseTradeFullinfoGetResponseToAomsordT(response, storeId) :
                null);
    }

    /**
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ApiException
     */
    private CmdRes taobaoRefundGet(CmdReq cmdReq)
        throws ClientProtocolException, IOException, ApiException, NumberFormatException {
        // 取得授權資料
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        String storeId = cmdReq.getStoreid();

        // 取參數
        String refundId = cmdReq.getParams().get("id");

        RefundGetResponse response =
            taobaoRefundGet(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                TaobaoCommonTool.API_REFUND_FIELDS, Long.parseLong(refundId));
        boolean success = response.getErrorCode() == null;
        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            success ? tableJdpTbRefund.parseResponseToAomsrefundT(response, storeId) : null);

    }

    /**
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ApiException
     */
    private CmdRes taobaoItemGet(CmdReq cmdReq) throws ApiException, IOException {
        // 取得授權資料
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        String storeId = cmdReq.getStoreid();

        // 取參數
        String numIid = cmdReq.getParams().get("numid");

        ItemSellerGetResponse response =
            taobaoItemSellerGet(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                TaobaoCommonTool.API_ITEM_FIELDS, Long.parseLong(numIid));
        boolean success = response.getErrorCode() == null;
        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getErrorCode(), response.getMsg()),
            success ? tableJdpTbItem.parseItemGetResponseToAomsitemT(response, storeId) : null);
    }

    /**
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ApiException
     */
    private CmdRes taobaoItemUpdateListing(CmdReq cmdReq)
        throws ApiException, IOException, NumberFormatException {
        // 取得授權資料
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得API所需參數
        Long numIid = Long.parseLong(cmdReq.getParams().get("numid"));
        Long num = Long.parseLong(cmdReq.getParams().get("num"));

        // 調用API
        ItemUpdateListingResponse response =
            taobaoItemUpdateListing(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                numIid, num);

        boolean isSuccess = (response.getItem() != null);
        return new CmdRes(cmdReq, isSuccess,
            isSuccess ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            null);
    }

    /**
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ApiException
     */
    private CmdRes taobaoItemUpdateDelisting(CmdReq cmdReq)
        throws ApiException, IOException, NumberFormatException {
        // 取得授權資料
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得API所需參數
        Long numIid = Long.parseLong(cmdReq.getParams().get("numid"));

        // 調用API
        ItemUpdateDelistingResponse response =
            taobaoItemUpdateDelisting(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                numIid);

        boolean isSuccess = (response.getItem() != null);
        return new CmdRes(cmdReq, isSuccess,
            isSuccess ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            null);
    }

    /**
     * 使用淘宝的接口：taobao.item.update进行商品参数的更新
     * <p>
     * 2015.10.19更新：原来 taobao.item.update接口被停用.需要使用一组新的API.
     * <ul>
     * 新API以及调用顺序：
     * <li>1.taobao.item.update.schema.get
     * <li>2.taobao.item.schema.update
     * <p>
     * 2015.10.26更新
     * 原来taobao.item.update接口依然可用，用回旧接口
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     * @throws NumberFormatException
     * @throws TopSchemaException
     * @author 维杰
     * @since 2015.10.08
     */
    private CmdRes taobaoItemUpdate(CmdReq cmdReq)
        throws ApiException, NumberFormatException, TopSchemaException {
        // 取得授權資料
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取得API所需參數
        Long numIid = Long.parseLong(cmdReq.getParams().get("numid"));
        String picPath = cmdReq.getParams().get("picpath");

        ItemUpdateResponse response =
            taobaoItemUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), numIid,
                picPath);

        boolean isSuccess = (response.getItem() != null);
        return new CmdRes(cmdReq, isSuccess,
            isSuccess ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            null);

    }

    /**
     * 从淘宝图片的绝对URL抽取出相对URL。
     * 绝对URL类似这样：https://img.alicdn.com/imgextra/i2/675786499/TB2.emHiXXXXXaAXpXXXXXXXXXX_!!675786499.
     * jpg 相对URL类似这样：i2/675786499/TB2.emHiXXXXXaAXpXXXXXXXXXX_!!675786499.jpg
     *
     * @param absolutePicPath
     * @return 相对URL。如果URL中无imgextra字样，则返回绝对 URL
     * @author 维杰
     */
    private String extractRelativePicPath(String absolutePicPath) {
        String result = "";
        int index = absolutePicPath.indexOf(TaobaoCommonTool.PIC_RELATIVE_PATH_DELIMITER);
        if (index > 0) {
            result = absolutePicPath
                .substring(index + TaobaoCommonTool.PIC_RELATIVE_PATH_DELIMITER.length() + 1);
        } else {
            result = absolutePicPath;
        }
        return result;
    }

    /**
     * 编辑天猫商品.原来 taobao.item.update接口对天猫店铺被停用,需要使用一组新的API.
     * <p>
     * 使用时只对需要修改的字段添加对应的方法来处理，其他字段返回默认值.
     * <ul>
     * 新API以及调用顺序：
     * <li>1.tmall.item.update.schema.get 获取天猫商品编辑规则
     * <li>2.tmall.item.schema.update 根据规则编辑商品
     *
     * @return
     * @throws TopSchemaException
     * @throws ApiException
     * @author 维杰
     */
    private CmdRes tmallItemUpdate(CmdReq cmdReq) throws TopSchemaException, ApiException {
        // 获取天猫商品编辑规则(Schema)
        TmallItemUpdateSchemaGetResponse schemaGetResponse = tmallItemUpdateSchemaGet(cmdReq);
        String schema = schemaGetResponse.getUpdateItemResult();
        // 获取Schema的List
        List<Field> oldFieldList = SchemaReader.readXmlForList(schema);
        // 构造新的List,只处理需要处理的id,其余保留默认值回传
        List<Field> newFieldList = new ArrayList<Field>();
        for (Field field : oldFieldList) {
            if (field.getId().equals("item_images")) {
                String picPath = "";
                if (cmdReq.getParams().get("picpath") != null) {
                    picPath = cmdReq.getParams().get("picpath");
                    tmallItemUpdateMajorImageUrl(field, newFieldList, picPath);
                }
            } else if (field.getId().equals("item_wireless_images")) {
                String wirelessPicPath = "";
                if (cmdReq.getParams().get("wirelesspicpath") != null) {
                    wirelessPicPath = cmdReq.getParams().get("wirelesspicpath");
                    tmallItemUpdateWirelessMajorImageUrl(field, newFieldList, wirelessPicPath);
                }
            } else if (field.getId().equals("white_bg_image")) {
                String whiteBgPicPath = "";
                if (cmdReq.getParams().get("whitebgpicpath") != null) {
                    whiteBgPicPath = cmdReq.getParams().get("whitebgpicpath");
                    tmallItemUpdateWhiteBgImageUrl(field, newFieldList, whiteBgPicPath);
                }
            } else {
                if (field instanceof InputField) {
                    InputField newField = new InputField();
                    newField.setId(field.getId());
                    newField.setName(field.getName());
                    newField.setProperties(field.getProperties());
                    newField.setRules(field.getRules());

                    String value = ((InputField) field).getDefaultValue();
                    newField.setValue(value);

                    newFieldList.add(newField);
                } else if (field instanceof MultiInputField) {
                    MultiInputField newField = new MultiInputField();
                    newField.setId(field.getId());
                    newField.setName(field.getName());
                    newField.setProperties(field.getProperties());
                    newField.setRules(field.getRules());

                    List<String> defaultValues = new ArrayList<String>();
                    for (String string : ((MultiInputField) field).getDefaultValues()) {
                        defaultValues.add(string);
                    }
                    newField.setValues(defaultValues);

                    newFieldList.add(newField);
                } else if (field instanceof SingleCheckField) {
                    SingleCheckField newField = new SingleCheckField();
                    newField.setId(field.getId());
                    newField.setName(field.getName());

                    List<Option> oldOptions = ((SingleCheckField) field).getOptions();
                    List<Option> newOptions = new ArrayList<Option>();
                    for (Option option : oldOptions) {
                        newOptions.add(option);
                    }
                    newField.setOptions(newOptions);

                    newField.setProperties(field.getProperties());
                    newField.setRules(field.getRules());
                    newField.setValue(((SingleCheckField) field).getDefaultValue());

                    newFieldList.add(newField);
                } else if (field instanceof MultiCheckField) {
                    MultiCheckField newField = new MultiCheckField();
                    newField.setId(field.getId());
                    newField.setName(field.getName());

                    List<Option> oldOptions = ((MultiCheckField) field).getOptions();
                    List<Option> newOptions = new ArrayList<Option>();
                    for (Option option : oldOptions) {
                        newOptions.add(option);
                    }
                    newField.setOptions(newOptions);

                    newField.setProperties(field.getProperties());
                    newField.setRules(field.getRules());

                    List<Value> defaultValues = new ArrayList<Value>();
                    for (String string : ((MultiCheckField) field).getDefaultValues()) {
                        defaultValues.add(new Value(string));
                    }
                    newField.setValues(defaultValues);

                    newFieldList.add(newField);
                } else if (field instanceof ComplexField) {
                    ComplexField newField = new ComplexField();
                    newField.setId(field.getId());
                    newField.setName(field.getName());
                    newField.setProperties(field.getProperties());
                    newField.setRules(field.getRules());

                    ComplexValue oldDefaultComplexValue =
                        ((ComplexField) field).getDefaultComplexValue();
                    newField.setComplexValue(oldDefaultComplexValue);

                    newFieldList.add(newField);
                } else if (field instanceof MultiComplexField) {
                    MultiComplexField newField = new MultiComplexField();
                    newField.setId(field.getId());
                    newField.setName(field.getName());
                    newField.setProperties(field.getProperties());
                    newField.setRules(field.getRules());

                    List<ComplexValue> oldComplexValues =
                        ((MultiComplexField) field).getDefaultComplexValues();
                    List<ComplexValue> complexValues = new ArrayList<ComplexValue>();
                    for (ComplexValue oldComplexValue : oldComplexValues) {
                        complexValues.add(oldComplexValue);
                    }
                    newField.setComplexValues(complexValues);

                    newFieldList.add(newField);
                } else if (field instanceof LabelField) {
                    LabelField newField = new LabelField();
                    newField.setId(field.getId());
                    newField.setName(field.getName());
                    newField.setProperties(field.getProperties());
                    newField.setRules(field.getRules());

                    LabelGroup oldLabelGroup = ((LabelField) field).getLabelGroup();
                    newField.setLabelGroup(oldLabelGroup);

                    newFieldList.add(newField);
                } else {
                    ;
                }
            }
        } // end-for

        // 准备XML数据，并更新到天猫平台
        String xmlData = SchemaWriter.writeParamXmlString(newFieldList);
        TmallItemSchemaUpdateResponse updateResponse = tmallItemSchemaUpdate(cmdReq, xmlData);

        boolean isSuccess = (updateResponse.getUpdateItemResult() != null);

        return new CmdRes(cmdReq, isSuccess, isSuccess ?
            null :
            new ResponseError(updateResponse.getSubCode(), updateResponse.getSubMsg()), null);
    }

  /*
   * ==============================自定义商品信息更新区域============================== = =
   * =================================================================================
   */

    /**
     * 按照天猫商品编辑Schema，修改商品主图URL
     *
     * @param currentField 当前Schema的Field
     * @param newFieldList 新Schema的List<com.taobao.top.schema.field.Field>
     * @param picPath      主图的URL
     */
    private void tmallItemUpdateMajorImageUrl(Field currentField, List<Field> newFieldList,
        String picPath) {
        ComplexField oldField = (ComplexField) currentField;
        ComplexField picField = new ComplexField();
        picField.setId(oldField.getId());
        picField.setName(oldField.getName());
        picField.setProperties(oldField.getProperties());
        picField.setRules(oldField.getRules());

        ComplexValue imageValues = new ComplexValue();
        imageValues = oldField.getDefaultComplexValue();
        imageValues.setInputFieldValue("item_image_0", picPath);

        picField.setComplexValue(oldField.getDefaultComplexValue());
        newFieldList.add(picField);
    }

  /*
   * ================================================================================= = =
   * ==============================自定义商品信息更新区域==============================
   */

    /**
     * 按照天猫商品编辑Schema，修改商品无线端主图URL
     *
     * @param currentField
     * @param newFieldList
     * @param wirelessPicPath
     */
    private void tmallItemUpdateWirelessMajorImageUrl(Field currentField, List<Field> newFieldList,
        String wirelessPicPath) {
        ComplexField oldField = (ComplexField) currentField;
        ComplexField picField = new ComplexField();
        picField.setId(oldField.getId());
        picField.setName(oldField.getName());
        picField.setProperties(oldField.getProperties());
        picField.setRules(oldField.getRules());

        ComplexValue imageValues = new ComplexValue();
        imageValues = oldField.getDefaultComplexValue();
        imageValues.setInputFieldValue("item_wireless_images_1", wirelessPicPath);

        picField.setComplexValue(oldField.getDefaultComplexValue());
        newFieldList.add(picField);

    }

  /*
   * ==============================自定义商品信息更新区域============================== = =
   * =================================================================================
   */

    private void tmallItemUpdateWhiteBgImageUrl(Field currentField, List<Field> newFieldList,
        String whiteBgPicPath) {
        InputField oldField = (InputField) currentField;
        InputField picField = new InputField();
        picField.setId(oldField.getId());
        picField.setName(oldField.getName());
        picField.setProperties(oldField.getProperties());
        picField.setRules(oldField.getRules());

        picField.setValue(whiteBgPicPath);
        newFieldList.add(picField);

    }

    /**
     * 天猫编辑商品规则(Schema)获取.使用的API:tmall.item.update.schema.get
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     * @author 维杰
     * @since 2015.10.19
     */
    private TmallItemUpdateSchemaGetResponse tmallItemUpdateSchemaGet(CmdReq cmdReq)
        throws ApiException, NumberFormatException {
        // 取得授權資料
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        TaobaoClient client =
            new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
        Long numIid = 0l;
        if (cmdReq.getParams().get("numid") != null) {
            numIid = Long.valueOf(cmdReq.getParams().get("numid"));
        }
        // 获取Schema
        TmallItemUpdateSchemaGetRequest schemaGetRequest = new TmallItemUpdateSchemaGetRequest();
        schemaGetRequest.setItemId(numIid);
        TmallItemUpdateSchemaGetResponse response =
            client.execute(schemaGetRequest, esv.getAccessToken());

        return response;
    }

    /**
     * 天猫根据规则编辑商品.使用的API:tmall.item.schema.update
     *
     * @param cmdReq
     * @param xmlData
     * @return
     * @throws ApiException
     * @author 维杰
     * @since 2015.10.19
     */
    private TmallItemSchemaUpdateResponse tmallItemSchemaUpdate(CmdReq cmdReq, String xmlData)
        throws ApiException {
        // 取得授權資料
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        TaobaoClient client =
            new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
        TmallItemSchemaUpdateRequest updateRequest = new TmallItemSchemaUpdateRequest();
        updateRequest.setItemId(Long.parseLong(cmdReq.getParams().get("numid")));
        updateRequest.setXmlData(xmlData);
        TmallItemSchemaUpdateResponse updateResponse =
            client.execute(updateRequest, esv.getAccessToken());

        return updateResponse;
    }

    /**
     * 使用淘宝API：taobao.item.quantity.update修改宝贝的库存
     * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.1998342952.32.YVQKN8&path=cid:4-apiId:
     * 10591
     *
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ApiException
     */
    private CmdRes taobaoItemQuantityUpdate(CmdReq cmdReq) throws ApiException, IOException {
        Map<String, String> params = cmdReq.getParams();

        // 取得參數
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Long numId = Long.parseLong(params.get("numid")); // 商品在電商平台的ID
        Long skuId = Long.parseLong(params.get("skuid"));
        String outerId = params.get("outerid"); // 產品在ERP的料號
        Long num = new Double(params.get("num")).longValue(); // 數量

        // 調用API
        ItemQuantityUpdateResponse itemQuantityUpdateResponse =
            taobaoItemQuantityUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                numId, skuId, outerId, num, TaobaoCommonTool.INVENTORY_FULL_UPDATE_TYPE);

        Item item = itemQuantityUpdateResponse.getItem();

        return new CmdRes(cmdReq, !(item == null), item == null ?
            new ResponseError(itemQuantityUpdateResponse.getSubCode(),
                itemQuantityUpdateResponse.getSubMsg()) :
            null, null);
    }

    //  private SkusQuantityUpdateResponse taobaoSkusQuantityUpdate(String appKey, String appSecret,
    //      String accessToken, long numId, long updateType, String skuidQuantities) throws ApiException {
    //    TaobaoClient client =
    //        new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    //            appKey, appSecret, Constants.FORMAT_JSON);
    //    SkusQuantityUpdateRequest request = new SkusQuantityUpdateRequest();
    //    request.setNumIid(numId);
    //    request.setType(TaobaoCommonTool.INVENTORY_FULL_UPDATE_TYPE); // 1为全量更新，2为增量更新
    //    request.setSkuidQuantities(skuidQuantities);
    //    SkusQuantityUpdateResponse response = client.execute(request, accessToken);
    //
    //    return response;
    //  }

    /**
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ApiException
     */
    private CmdRes taobaoFenxiaoOrderGet(CmdReq cmdReq)
        throws IOException, ApiException, NumberFormatException {
        // 取得授權資料
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        String storeId = cmdReq.getStoreid();

        // 取參數
        String fxId = cmdReq.getParams().get("id");

        FenxiaoOrdersGetResponse response =
            taobaoFenxiaoOrdersGet(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), null,
                null, null, null, null, null, Long.parseLong(fxId),
                TaobaoCommonTool.API_FX_ORDER_FIELDS);

        boolean success = response.getErrorCode() == null;

        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            success ? tableJdpFxTrade.parseResponseToAomsordT(response, storeId) : null);
    }

    /**
     * 使用淘宝API：taobao.fenxiao.refund.get查询采购单退款信息
     * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.1998342952.61.qkEIPW&path=cid:15-apiId:
     * 21873
     *
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ApiException
     */
    private CmdRes taobaoFenxiaoRefundGet(CmdReq cmdReq)
        throws ClientProtocolException, IOException, ApiException, NumberFormatException {
        // 取得授權資料
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
        String storeId = cmdReq.getStoreid();

        // 取參數
        String subOrderId = cmdReq.getParams().get("id");

        // 調用API
        FenxiaoRefundGetResponse response =
            taobaoFenxiaoRefundGet(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                Long.parseLong(subOrderId));

        boolean success = response.getErrorCode() == null;

        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            success ? tableJdpFxRefund.parseResponseToAomsrefundT(response, storeId) : null);

    }

    /**
     * 使用淘宝API：taobao.fenxiao.products.get获取单个分销商品的详情
     * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.1998342952.5.ux3MmB&path=cid:15-apiId:
     * 328
     *
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ApiException
     * @throws IOException
     */
    private CmdRes taobaoFenxiaoProductsGet(CmdReq cmdReq) throws ApiException, IOException {
        // 取得授權
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        // 取參數
        String pid = cmdReq.getParams().get("numid");

        // 調用API
        //    TaobaoClient client =
        //        new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
        //            esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
        //    FenxiaoProductsGetRequest req = new FenxiaoProductsGetRequest();
        //    req.setPids(pid);
        //    req.setFields(TaobaoCommonTool.API_FX_ITEM_FIELDS);
        //    FenxiaoProductsGetResponse response = client.execute(req, esv.getAccessToken());
        FenxiaoProductsGetResponse response =
            taobaoFenxiaoProductsGet(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(), pid,
                TaobaoCommonTool.API_FX_ITEM_FIELDS, null, null, null, null);

        boolean success = response.getErrorCode() == null;
        return new CmdRes(cmdReq, success,
            success ? null : new ResponseError(response.getErrorCode(), response.getMsg()),
            success ?
                tableJdpFxItem.parseResponseToAomsitemT(response, cmdReq.getStoreid()) :
                null);
    }


    /**
     * 更新淘宝分销产品信息：taobao.fenxiao.product.update
     * http://open.taobao.com/doc2/apiDetail?spm=0.0.0.0.j2KJEp&apiId=326
     *
     * @param cmdReq
     * @return
     * @throws ApiException
     * @author 维杰
     * @since 2015.10.29
     */
    private CmdRes taobaoFenxiaoProductUpdate(CmdReq cmdReq)
        throws ApiException, NumberFormatException, IOException {
        Map<String, String> params = cmdReq.getParams();

        // 取得參數
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        String pIdString = "";
        String skuIdString = "";
        String outerIdString = "";
        String numString = "";

        if (params.get("numid") != null) {
            pIdString = params.get("numid");
        }
        if (params.get("skuid") != null) {
            skuIdString = params.get("skuid");
        }
        if (params.get("outerid") != null) {
            outerIdString = params.get("outerid");
        }
        if (params.get("num") != null) {
            numString = params.get("num");
        }

        Long pId = 0L;
        if (pIdString.length() > 0) {
            pId = Long.parseLong(params.get("numid")); // 商品在電商平台的ID
        }

        // 調用API
        FenxiaoProductUpdateResponse response =
            taobaoFenxiaoProductUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                pId, null, null, skuIdString, outerIdString, numString);

        boolean success = response.getModified() == null ? false : true;

        return new CmdRes(cmdReq, success,
            !success ? new ResponseError(response.getSubCode(), response.getSubMsg()) : null, null);
    }

    private CmdRes taobaoFenxiaoItemUpdateListing(CmdReq cmdReq)
        throws ApiException, IOException, NumberFormatException {

        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Long numIid = Long.parseLong(cmdReq.getParams().get("numid"));
        Long num = Long.parseLong(cmdReq.getParams().get("num"));

        FenxiaoProductUpdateResponse response =
            taobaoFenxiaoProductUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                numIid, num, FxProductStatus.LISTING.getStatus(), null, null, null);

        boolean isSuccess = (response.getPid() != null);
        return new CmdRes(cmdReq, isSuccess,
            isSuccess ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            null);
    }

    private CmdRes taobaoFenxiaoItemUpdateDelisting(CmdReq cmdReq)
        throws ApiException, IOException, NumberFormatException {
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Long numIid = Long.parseLong(cmdReq.getParams().get("numid"));

        FenxiaoProductUpdateResponse response =
            taobaoFenxiaoProductUpdate(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                numIid, null, FxProductStatus.DELISTING.getStatus(), null, null, null);

        boolean isSuccess = (response.getPid() != null);
        return new CmdRes(cmdReq, isSuccess,
            isSuccess ? null : new ResponseError(response.getSubCode(), response.getSubMsg()),
            null);
    }

    /**
     * taobao.logistics.offline.send (自己联系物流（线下物流）发货)
     * http://open.taobao.com/doc2/apiDetail.htm?apiId=10690
     *
     * @param cmdReq ERP请求JSON实例
     * @return
     * @throws ApiException
     */
    private CmdRes taobaoLogisticsOfflineSend(CmdReq cmdReq) throws ApiException, IOException {
        Map<String, String> params = cmdReq.getParams();

        // 取得參數
        ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());

        Long oid = Long.parseLong(params.get("oid"));
        String expressno = params.get("expressno");
        String expcompno = params.get("expcompno");

        // 支持淘宝拆单发货
        String subOids = "";
        if (cmdReq.getSuboidlist() != null && cmdReq.getSuboidlist().length > 0) {
            for (String subOid : cmdReq.getSuboidlist()) {
                subOids += subOid + ",";
            }
            subOids = subOids.substring(0, subOids.length() - 1);
        }
        boolean needSplit = !"".equals(subOids);

        // 調用SDK
        LogisticsOfflineSendResponse logisticsOfflineSendResponse =
            taobaoLogisticsOfflineSend(esv.getAppKey(), esv.getAppSecret(), esv.getAccessToken(),
                oid, expressno, expcompno, subOids, needSplit);
        Shipping shipping = logisticsOfflineSendResponse.getShipping();

        return new CmdRes(cmdReq, !(shipping == null), shipping == null ?
            new ResponseError(logisticsOfflineSendResponse.getSubCode(),
                logisticsOfflineSendResponse.getSubMsg()) :
            null, null);
    }

    @Override public List<Area> taobaoLogisticsAreasGet() throws ApiException {
        @SuppressWarnings("unchecked") List<Object[]> result = baseDAO.executeNativeSQL(
            "SELECT aomsshop004,aomsshop005 FROM aomsshop_t WHERE aomsshop003='0' LIMIT 1");
        if (result != null && result.size() > 0) {
            TaobaoClient client = new AutoRetryClusterTaobaoClient(
                paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API), (String) (result.get(0)[0]),
                (String) (result.get(0)[1]), Constants.FORMAT_JSON);
            AreasGetRequest req = new AreasGetRequest();
            req.setFields("id,name,parent_id,type,zip");
            AreasGetResponse res;
            res = client.execute(req);
            if (res != null && res.getMsg() == null && res.getAreas() != null) {
                List<Area> resultAreas = res.getAreas();
                return resultAreas;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取淘宝省市区，并以树的结构进行存储，最终返回遍历节点的结果
     *
     * @param request
     * @return
     * @throws ApiException
     */
    private CmdRes getAreaNodeTree(CmdReq request) throws ApiException {
        CmdRes cmdRes = new CmdRes();

        try {
            List<Area> areas = taobaoLogisticsAreasGet();
            if (areas != null) {
                AreaNode root = new AreaNode();
                root.setParentId(new Long(-1));

                // 添加国家
                for (Area area : areas) {
                    // if (area.getType() == 1 && area.getName().equals("中国"))
                    // mark by mowj 20150718 需要返回所有国家
                    // unmark by mowj 20150916 只需要返回中国
                    {
                        if (area.getType() == 1) {
                            AreaNode newNode = new AreaNode();
                            newNode.setRootNode(root);
                            newNode.setId(area.getId());
                            newNode.setName(area.getName());
                            newNode.setParentId(area.getParentId());
                            // newNode.setType(area.getType());
                            newNode.setZip(area.getZip());

                            root.addChild(newNode);
                            break;
                            // mark by mowj 20150718 需要返回所有国家
                            // unmark by mowj 20150916 只需要返回中国
                        }
                    }
                }

                // 添加省
                for (Area area : areas) {
                    if (area.getType() == 2 && !area.getName().equals("海外"))
                    // mark by mowj 20150718 需要返回所有国家
                    // unmark by mowj 20150916 只需要返回中国
                    {
                        if (area.getType() == 2) {
                            AreaNode newNode = new AreaNode();
                            newNode.setRootNode(root);
                            newNode.setId(area.getId());
                            newNode.setName(area.getName());
                            newNode.setParentId(area.getParentId());
                            // newNode.setType(area.getType());
                            newNode.setZip(area.getZip());

                            AreaNode parentNode = AreaNode.findChild(root, area.getParentId());
                            if (parentNode != null) {
                                parentNode.addChild(newNode);
                            }

                        }
                    }
                }

                // 添加市
                for (Area area : areas) {
                    if (area.getType() == 3) {
                        AreaNode newNode = new AreaNode();
                        newNode.setRootNode(root);
                        newNode.setId(area.getId());
                        newNode.setName(area.getName());
                        newNode.setParentId(area.getParentId());
                        // newNode.setType(area.getType());
                        newNode.setZip(area.getZip());

                        AreaNode parentNode = AreaNode.findChild(root, area.getParentId());
                        if (parentNode != null) {
                            parentNode.addChild(newNode);
                        }
                    }
                }

                // 添加区/县
                for (Area area : areas) {
                    if (area.getType() == 4) {
                        AreaNode newNode = new AreaNode();
                        newNode.setRootNode(root);
                        newNode.setId(area.getId());
                        newNode.setName(area.getName());
                        newNode.setParentId(area.getParentId());
                        // newNode.setType(area.getType());
                        newNode.setZip(area.getZip());

                        AreaNode parentNode = AreaNode.findChild(root, area.getParentId());
                        if (parentNode != null) {
                            parentNode.addChild(newNode);
                        }
                    }
                }

                // AreaNode.printChildsAsTree(root,"├─", 1);
                // ObjectMapper mapper = new ObjectMapper();
                // mapper.setSerializationInclusion(Include.NON_EMPTY);
                // mapper.setSerializationInclusion(Include.NON_NULL);
                // System.out.println(mapper.writeValueAsString(root.getChilds().get(0)));
                // AreaNode.printChildsAsTable(root);
                List<AreaResponse> areaList = new ArrayList<AreaResponse>();
                AreaNode.getAreaList(root, areaList);

                // System.out.println(mapper.writeValueAsString(areaList));
                // System.out.println(AreaNode.calculateTotalNodesCount(root));

                cmdRes.setReturnValue(areaList);
                cmdRes.setApi(request.getApi());
                cmdRes.setEcno(request.getEcno());
                cmdRes.setStoreid(request.getStoreid());
                cmdRes.setParams(request.getParams());

            } else {
                cmdRes.setSuccess(false);
                // cmdRes.setError(new ResponseError("035",
                // ErrorMessageBox._035));
            }
        } catch (ApiException e) {
            cmdRes.setSuccess(false);
            cmdRes.setError(new ResponseError("999", e.getMessage()));
            e.printStackTrace();
            throw e;
        }
        return cmdRes;
    }

    //  private WlbWaybillIGetResponse taobaoWlbWaybillIGet(String appKey, String appSecret,
    //      String accessToken, String cpCode, WaybillAddress sendAddress,
    //      List<TradeOrderInfo> tradeOrderInfos) throws ApiException {
    //    TaobaoClient client =
    //        new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    //            appKey, appSecret, Constants.FORMAT_JSON);
    //    WlbWaybillIGetRequest request = new WlbWaybillIGetRequest();
    //    WaybillApplyNewRequest applyNewRequest = new WaybillApplyNewRequest();
    //    applyNewRequest.setCpCode(cpCode);
    //    applyNewRequest.setShippingAddress(sendAddress);
    //    applyNewRequest.setTradeOrderInfoCols(tradeOrderInfos);
    //    request.setWaybillApplyNewRequest(applyNewRequest);
    //    WlbWaybillIGetResponse response = client.execute(request, accessToken);
    //
    //    return response;
    //  }

    //  private WlbWaybillIFullupdateResponse taobaoWlbWaybillIFullUpdate(String appKey, String appSecret,
    //      String accessToken, String cpCode, String waybillCode, String productType,
    //      String orderChannelsType, Long realUserId, String consigneeProvince,
    //      String consigneeDetailAddress, String consigneeName, String consigneePhone,
    //      List<String> tradeOrderList, List<com.taobao.api.domain.PackageItem> packageItems)
    //          throws ApiException {
    //    TaobaoClient client =
    //        new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    //            appKey, appSecret, Constants.FORMAT_JSON);
    //    WlbWaybillIFullupdateRequest request = new WlbWaybillIFullupdateRequest();
    //    WaybillApplyFullUpdateRequest updateRequest = new WaybillApplyFullUpdateRequest();
    //    updateRequest.setCpCode(cpCode);
    //    updateRequest.setWaybillCode(waybillCode);
    //    updateRequest.setProductType(productType);
    //    updateRequest.setOrderChannelsType(orderChannelsType);
    //    updateRequest.setRealUserId(realUserId);
    //
    //    com.taobao.api.domain.WaybillAddress consigneeAddress =
    //        new com.taobao.api.domain.WaybillAddress();
    //    consigneeAddress.setProvince(consigneeProvince);
    //    consigneeAddress.setAddressDetail(consigneeDetailAddress);
    //
    //    updateRequest.setConsigneeAddress(consigneeAddress);
    //    updateRequest.setConsigneeName(consigneeName);
    //    updateRequest.setConsigneePhone(consigneePhone);
    //    updateRequest.setTradeOrderList(tradeOrderList);
    //    updateRequest.setPackageItems(packageItems);
    //
    //    request.setWaybillApplyFullUpdateRequest(updateRequest);
    //
    //    WlbWaybillIFullupdateResponse response = client.execute(request, accessToken);
    //
    //    return response;
    //  }
    //
    //  private WlbWaybillICancelResponse taobaoWlbWaybillICancel(String appKey, String appSecret,
    //      String accessToken, String cpCode, String waybillCode, List<String> tradeOrderList)
    //          throws ApiException {
    //    TaobaoClient client =
    //        new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    //            appKey, appSecret, Constants.FORMAT_JSON);
    //    WlbWaybillICancelRequest request = new WlbWaybillICancelRequest();
    //    WaybillApplyCancelRequest cancelRequest = new WaybillApplyCancelRequest();
    //    cancelRequest.setTradeOrderList(tradeOrderList);
    //    cancelRequest.setWaybillCode(waybillCode);
    //    cancelRequest.setCpCode(cpCode);
    //
    //    request.setWaybillApplyCancelRequest(cancelRequest);
    //
    //    WlbWaybillICancelResponse response = client.execute(request, accessToken);
    //
    //    return response;
    //  }

    private WlbWaybillIPrintResponse taobaoWlbWaybillIPrint(String appKey, String appSecret,
        String accessToken, String cpCode, List<PrintCheckInfo> printCheckInfos)
        throws ApiException {
        TaobaoClient client =
            new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret, Constants.FORMAT_JSON);
        WlbWaybillIPrintRequest request = new WlbWaybillIPrintRequest();
        WaybillApplyPrintCheckRequest printCheckRequest = new WaybillApplyPrintCheckRequest();
        printCheckRequest.setCpCode(cpCode);
        printCheckRequest.setPrintCheckInfoCols(printCheckInfos);

        request.setWaybillApplyPrintCheckRequest(printCheckRequest);

        WlbWaybillIPrintResponse response = client.execute(request, accessToken);

        return response;
    }

    private com.taobao.api.response.CainiaoWaybillIiGetResponse cainiaoWaybillIiGet(String appKey,
        String appSecret, String accessToken, String cpCode, String productCode,
        com.taobao.api.request.CainiaoWaybillIiGetRequest.UserInfoDto senderInfo,
        List<com.taobao.api.request.CainiaoWaybillIiGetRequest.TradeOrderInfoDto> tradeOrderInfoDtos)
        throws ApiException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        com.taobao.api.request.CainiaoWaybillIiGetRequest request =
            new com.taobao.api.request.CainiaoWaybillIiGetRequest();
        WaybillCloudPrintApplyNewRequest applyNewRequest = new WaybillCloudPrintApplyNewRequest();
        applyNewRequest.setCpCode(cpCode);
        if (StringTool.isNotEmpty(productCode)) {
            applyNewRequest.setProductCode(productCode);
        }
        applyNewRequest.setSender(senderInfo);
        applyNewRequest.setTradeOrderInfoDtos(tradeOrderInfoDtos);

        request.setParamWaybillCloudPrintApplyNewRequest(applyNewRequest);

        com.taobao.api.response.CainiaoWaybillIiGetResponse response =
            client.execute(request, accessToken);

        return response;
    }

    private com.taobao.api.response.CainiaoWaybillIiUpdateResponse cainiaoWaybillIiUpdate(
        String appKey, String appSecret, String accessToken, String cpCode,
        String logisticsServices,
        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.PackageInfoDto packageInfo,
        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.UserInfoDto recipient,
        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.UserInfoDto sender, String templateUrl,
        String waybillCode, String objectId) throws ApiException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        com.taobao.api.request.CainiaoWaybillIiUpdateRequest request =
            new com.taobao.api.request.CainiaoWaybillIiUpdateRequest();
        com.taobao.api.request.CainiaoWaybillIiUpdateRequest.WaybillCloudPrintUpdateRequest
            updateRequest =
            new com.taobao.api.request.CainiaoWaybillIiUpdateRequest.WaybillCloudPrintUpdateRequest();
        request.setParamWaybillCloudPrintUpdateRequest(updateRequest);

        updateRequest.setCpCode(cpCode);
        if (StringTool.isNotEmpty(logisticsServices)) {
            updateRequest.setLogisticsServices(logisticsServices);
        }
        if (packageInfo != null) {
            updateRequest.setPackageInfo(packageInfo);
        }
        if (recipient != null) {
            updateRequest.setRecipient(recipient);
        }
        if (sender != null) {
            updateRequest.setSender(sender);
        }
        if (StringTool.isNotEmpty(templateUrl)) {
            updateRequest.setTemplateUrl(templateUrl);
        }
        updateRequest.setWaybillCode(waybillCode);
        if (StringTool.isNotEmpty(objectId)) {
            updateRequest.setObjectId(objectId);
        }

        com.taobao.api.response.CainiaoWaybillIiUpdateResponse response =
            client.execute(request, accessToken);

        return response;
    }

    private com.taobao.api.response.CainiaoWaybillIiCancelResponse cainiaoWaybillIiCancel(
        String appKey, String appSecret, String accessToken, String cpCode, String waybillCode)
        throws ApiException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        com.taobao.api.request.CainiaoWaybillIiCancelRequest request =
            new com.taobao.api.request.CainiaoWaybillIiCancelRequest();
        request.setCpCode(cpCode);
        request.setWaybillCode(waybillCode);

        com.taobao.api.response.CainiaoWaybillIiCancelResponse response =
            client.execute(request, accessToken);

        return response;
    }



    //  /**
    //   * 调用淘宝API：taobao.user.seller.get获取卖家身份相关信息
    //   *
    //   * @param client
    //   * @param esvVerification
    //   * @return 如果有响应，则返回com.taobao.api.domain.User对象;否则返回NULL
    //   * @throws ApiException
    //   */
    //  private User taobaoUserSellerGet(CmdReq cmdReq) throws ApiException {
    //    // 取得授權資料
    //    ESVerification esv = aomsShopService.getAuthorizationByStoreId(cmdReq.getStoreid());
    //    // 准备调用 淘宝客户端
    //    TaobaoClient client =
    //        new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    //            esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
    //    UserSellerGetRequest request = new UserSellerGetRequest();
    //    request.setFields("type");
    //    UserSellerGetResponse response = client.execute(request, esv.getAccessToken());
    //    if (response != null) {
    //      return response.getUser();
    //    } else {
    //      return null;
    //    }
    //  }

    @Override public CmdRes digiwinPictureExternalUpload(CmdReq cmdReq) throws Exception {
        throw new UnsupportedOperationException("_034");
    }

    // /**
    // * 获取当前会话用户出售中的非分銷商品列表<br>
    // * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.0.0.FppmTN&path=categoryId:4-apiId:18
    // *
    // * @param esv DB中保存的授权信息
    // * @param startModifiedDate 修改开始时间
    // * @param endModifiedDate 修改结束时间
    // * @param pageNo 页码
    // * @param pageSize 页面大小
    // * @return ItemInventoryGetResponse
    // * @throws ApiException
    // * @author Sen.shen & 维杰
    // */
    // @Override
    // public ItemsInventoryGetResponse taobaoItemsInventoryGet(ESVerification esv, String fields,
    // String startModifiedDate, String endModifiedDate, Long pageNo, Long pageSize)
    // throws ApiException {
    //
    // TaobaoClient client =
    // new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    // esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
    // ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
    // // mark by mowj 20150810 start
    // // req.setFields("approve_status,num_iid,title,nick,"
    // // + "type,cid,pic_url,num,props,valid_thru,"
    // // + "list_time,price,has_discount,has_invoice,"
    // // + "has_warranty,has_showcase,modified,"
    // // + "delist_time,postage_id,seller_cids,outer_id");
    // // mark by mowj 20150810 end
    // req.setFields(fields); // add by mowj 20150810
    // //
    // 修改的原因：因为这个只能获取部分字段，详情需要使用num_iid调用另外的API，且需要modified作为中台的lastUpdateTime,所以这里只需要取num_iid与modified即可
    // req.setStartModified(DateTimeTool.parse(startModifiedDate));
    // req.setEndModified(DateTimeTool.parse(endModifiedDate));
    // req.setPageNo(pageNo);// 默認 1 pageNo * pageSize > 100000 會報錯
    // req.setPageSize(pageSize);// 默認 40 最大 200 pageNo * pageSize > 20000 會報錯
    // return client.execute(req, esv.getAccessToken());
    // }
    //
    // /**
    // * 获取当前会话用户库存中的非分销商品列表 <br>
    // *
    // http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.0.0.FppmTN&path=categoryId:4-apiId:162
    // *
    // * @param esv DB中保存的授权信息
    // * @param startModifiedDate 修改开始时间
    // * @param endModifiedDate 修改结束时间
    // * @param pageNo 页码
    // * @param pageSize 页面大小
    // * @return ItemsInventoryGetResponse
    // * @throws ApiException
    // * @author Sen.shen & 维杰
    // */
    // @Override
    // public ItemsInventoryGetResponse taobaoItemInventoryGet(ESVerification esv, String fields,
    // String startModifiedDate, String endModifiedDate, Long pageNo, Long pageSize)
    // throws ApiException {
    //
    // TaobaoClient client =
    // new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    // esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
    // ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
    // // mark by mowj 20150810 start
    // // req.setFields("approve_status,num_iid,title,nick,type,"
    // // + "cid,pic_url,num,props,valid_thru,list_time,"
    // // + "price,has_discount,has_invoice,has_warranty,"
    // // + "has_showcase,modified,delist_time,postage_id,"
    // // + "seller_cids,outer_id");
    // // mark by mowj 20150810 end
    // req.setFields(fields); // add by mowj 20150810
    // //
    // 修改的原因：因为这个只能获取部分字段，详情需要使用num_iid调用另外的API，且需要modified作为中台的lastUpdateTime,所以这里只需要取num_iid与modified即可
    // req.setStartModified(DateTimeTool.parse(startModifiedDate));
    // req.setEndModified(DateTimeTool.parse(endModifiedDate));
    // req.setPageNo(pageNo);// 1 ~ 101 超過報錯
    // req.setPageSize(pageSize);// 默認 40 最大 200 pageNo * pageSize > 20000 會報錯
    // return client.execute(req, esv.getAccessToken());
    // }

    // /**
    // * 批量获取非分销商品详细信息<br>
    // * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.0.0.FppmTN&path=categoryId:4-apiId:
    // * 24626
    // *
    // * @param esv DB中保存的授权信息
    // * @param idList 商品编码列表，以","分隔。最多20个
    // * @return ItemsSellerListGetResponse
    // * @throws ApiException
    // * @author Sen.shen & 维杰
    // */
    // private ItemsSellerListGetResponse taobaoItemsSellerListGet(ESVerification esv, String idList)
    // throws ApiException {
    // TaobaoClient client =
    // new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    // esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
    // ItemsSellerListGetRequest req = new ItemsSellerListGetRequest();
    // req.setFields("num_iid,outer_id,title,approve_status,modified,"
    // + "detail_url,pic_url,created,list_time,delist_time,sku");
    // req.setNumIids(idList);
    // return client.execute(req, esv.getAccessToken());
    // }
    //
    // private List<AomsitemT> doParseTranslate(ESVerification esv, String numiidArr, String storeId)
    // throws ApiException {
    // ItemsSellerListGetResponse response = taobaoItemsSellerListGet(esv, numiidArr);
    // List<AomsitemT> aomsitemTList = new ArrayList<AomsitemT>();
    // for (Item item : response.getItems()) {
    // List<AomsitemT> itemList = tableJdpTbItem.parseItemToAomsitemT(item);
    // aomsitemTList.addAll(itemList);
    // }
    // System.out.println(storeId + "：" + aomsitemTList.size());
    // return aomsitemTList;
    // }

    @Override public TradesSoldGetResponse taobaoTradesSoldGet(String appKey, String appSecret,
        String accessToken, String fields, Date startCreated, Date endCreated, Long pageNo,
        Long pageSize, Boolean useHasNext) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        TradesSoldGetRequest request = new TradesSoldGetRequest();
        request.setFields(fields);
        if (StringTool.isNotEmpty(startCreated)) {
            request.setStartCreated(startCreated);
        }
        if (StringTool.isNotEmpty(endCreated)) {
            request.setEndCreated(endCreated);
        }
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        if (StringTool.isNotEmpty(useHasNext)) {
            request.setUseHasNext(useHasNext);
        }

        TradesSoldGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override public TradesSoldIncrementGetResponse taobaoTradesSoldIncrementGet(String appKey,
        String appSecret, String accessToken, String fields, Date startModified, Date endModified,
        Long pageNo, Long pageSize, Boolean useHasNext) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        TradesSoldIncrementGetRequest request = new TradesSoldIncrementGetRequest();
        request.setFields(fields);
        request.setStartModified(startModified);
        request.setEndModified(endModified);
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        if (StringTool.isNotEmpty(useHasNext)) {
            request.setUseHasNext(useHasNext);
        }

        TradesSoldIncrementGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override public TradesSoldIncrementvGetResponse taobaoTradesSoldIncrementvGet(String appKey,
        String appSecret, String accessToken, String fields, Date startCreate, Date endCreate,
        Long pageNo, Long pageSize, Boolean useHasNext) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        TradesSoldIncrementvGetRequest request = new TradesSoldIncrementvGetRequest();
        request.setFields(fields);
        request.setStartCreate(startCreate);
        request.setEndCreate(endCreate);
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        if (StringTool.isNotEmpty(useHasNext)) {
            request.setUseHasNext(useHasNext);
        }

        TradesSoldIncrementvGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public TradeFullinfoGetResponse taobaoTradeFullinfoGet(String appKey, String appSecret,
        String accessToken, String fields, Long tid) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        TradeFullinfoGetRequest request = new TradeFullinfoGetRequest();
        request.setFields(TaobaoCommonTool.API_ORDER_FIELDS);
        request.setTid(tid);

        TradeFullinfoGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public RefundsReceiveGetResponse taobaoRefundsReceiveGet(String appKey, String appSecret,
        String accessToken, String fields, Date startModified, Date endModified, Long pageNo,
        Long pageSize, Boolean useHasNext) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        RefundsReceiveGetRequest request = new RefundsReceiveGetRequest();
        request.setFields(fields);
        if (StringTool.isNotEmpty(startModified)) {
            request.setStartModified(startModified);
        }
        if (StringTool.isNotEmpty(endModified)) {
            request.setEndModified(endModified);
        }
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        if (StringTool.isNotEmpty(useHasNext)) {
            request.setUseHasNext(useHasNext);
        }

        RefundsReceiveGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public RefundGetResponse taobaoRefundGet(String appKey, String appSecret, String accessToken,
        String fields, Long refundId) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        RefundGetRequest request = new RefundGetRequest();
        request.setFields(fields);
        request.setRefundId(refundId);

        RefundGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override public ItemsOnsaleGetResponse taobaoItemsOnsaleGet(String appKey, String appSecret,
        String accessToken, String fields, String orderBy, Date startModified, Date endModified,
        Long pageNo, Long pageSize) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        request.setFields(fields);
        if (StringTool.isNotEmpty(orderBy)) {
            request.setOrderBy(orderBy);
        }
        if (StringTool.isNotEmpty(startModified)) {
            request.setStartModified(startModified);
        }
        if (StringTool.isNotEmpty(endModified)) {
            request.setEndModified(endModified);
        }
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }

        ItemsOnsaleGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public ItemsInventoryGetResponse taobaoItemsInventoryGet(String appKey, String appSecret,
        String accessToken, String fields, String orderBy, Date startModified, Date endModified,
        Long pageNo, Long pageSize) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemsInventoryGetRequest request = new ItemsInventoryGetRequest();
        request.setFields(fields);
        if (StringTool.isNotEmpty(orderBy)) {
            request.setOrderBy(orderBy);
        }
        if (StringTool.isNotEmpty(startModified)) {
            request.setStartModified(startModified);
        }
        if (StringTool.isNotEmpty(endModified)) {
            request.setEndModified(endModified);
        }
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        ItemsInventoryGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override public ItemSellerGetResponse taobaoItemSellerGet(String appKey, String appSecret,
        String accessToken, String fields, Long numIid) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemSellerGetRequest request = new ItemSellerGetRequest();
        request.setFields(fields);
        request.setNumIid(numIid);

        ItemSellerGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public ItemsSellerListGetResponse taobaoItemsSellerListGet(String appKey, String appSecret,
        String accessToken, String fields, String numIids) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemsSellerListGetRequest request = new ItemsSellerListGetRequest();
        request.setFields(fields);
        request.setNumIids(numIids);

        ItemsSellerListGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public ItemQuantityUpdateResponse taobaoItemQuantityUpdate(String appKey, String appSecret,
        String accessToken, Long numIid, Long skuId, String outerId, Long quantity, Long updateType)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemQuantityUpdateRequest request = new ItemQuantityUpdateRequest();
        request.setNumIid(numIid);
        if (StringTool.isNotEmpty(skuId)) {
            request.setSkuId(skuId);
        }
        if (StringTool.isNotEmpty(outerId)) {
            request.setOuterId(outerId);
        }
        request.setQuantity(quantity);
        if (StringTool.isNotEmpty(updateType)) {
            request.setType(updateType);
        }

        ItemQuantityUpdateResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public SkusQuantityUpdateResponse taobaoSkusQuantityUpdate(String appKey, String appSecret,
        String accessToken, Long numIid, Long updateType, String skuidQuantities)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        SkusQuantityUpdateRequest request = new SkusQuantityUpdateRequest();
        request.setNumIid(numIid);
        if (StringTool.isNotEmpty(updateType)) {
            request.setType(updateType);
        }
        if (StringTool.isNotEmpty(skuidQuantities)) {
            request.setSkuidQuantities(skuidQuantities);
        }
        // req.setOuteridQuantities("123:1;234:2;345:3");

        SkusQuantityUpdateResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public ItemUpdateListingResponse taobaoItemUpdateListing(String appKey, String appSecret,
        String accessToken, Long numIid, Long num) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemUpdateListingRequest request = new ItemUpdateListingRequest();
        request.setNumIid(numIid);
        request.setNum(num);

        ItemUpdateListingResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public ItemUpdateDelistingResponse taobaoItemUpdateDelisting(String appKey, String appSecret,
        String accessToken, Long numIid) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemUpdateDelistingRequest request = new ItemUpdateDelistingRequest();
        request.setNumIid(numIid);

        ItemUpdateDelistingResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public FenxiaoOrdersGetResponse taobaoFenxiaoOrdersGet(String appKey, String appSecret,
        String accessToken, String status, Date startCreated, Date endCreated, String timeType,
        Long pageNo, Long pageSize, Long purchaseOrderId, String fields)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        FenxiaoOrdersGetRequest request = new FenxiaoOrdersGetRequest();
        if (StringTool.isNotEmpty(status)) {
            request.setStatus(status);
        }
        if (StringTool.isNotEmpty(startCreated)) {
            request.setStartCreated(startCreated);
        }
        if (StringTool.isNotEmpty(endCreated)) {
            request.setEndCreated(endCreated);
        }
        if (StringTool.isNotEmpty(timeType)) {
            request.setTimeType(timeType);
        }
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        if (StringTool.isNotEmpty(purchaseOrderId)) {
            request.setPurchaseOrderId(purchaseOrderId);
        }
        if (StringTool.isNotEmpty(fields)) {
            request.setFields(fields);
        }
        // req.setTcOrderId(237064190580986L);

        FenxiaoOrdersGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public FenxiaoRefundQueryResponse taobaoFenxiaoRefundQuery(String appKey, String appSecret,
        String accessToken, Date startDate, Date endDate, Long page, Long pageSize)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        FenxiaoRefundQueryRequest request = new FenxiaoRefundQueryRequest();
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        if (StringTool.isNotEmpty(page)) {
            request.setPageNo(page);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        request.setQuerySellerRefund(false); // 不关心下游买家信息

        FenxiaoRefundQueryResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public FenxiaoRefundGetResponse taobaoFenxiaoRefundGet(String appKey, String appSecret,
        String accessToken, Long subOrderId) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        FenxiaoRefundGetRequest request = new FenxiaoRefundGetRequest();
        request.setSubOrderId(subOrderId);
        request.setQuerySellerRefund(false); // 不关心下游买家信息

        FenxiaoRefundGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public FenxiaoProductsGetResponse taobaoFenxiaoProductsGet(String appKey, String appSecret,
        String accessToken, String pids, String fields, Date startModified, Date endModified,
        Long pageNo, Long pageSize) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        FenxiaoProductsGetRequest request = new FenxiaoProductsGetRequest();
        if (StringTool.isNotEmpty(pids)) {
            request.setPids(pids);
        }
        if (StringTool.isNotEmpty(fields)) {
            request.setFields(fields);
        }
        if (StringTool.isNotEmpty(startModified)) {
            request.setStartModified(startModified);
        }
        if (StringTool.isNotEmpty(endModified)) {
            request.setEndModified(endModified);
        }
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }

        FenxiaoProductsGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public ItemUpdateResponse taobaoItemUpdate(String appKey, String appSecret, String accessToken,
        Long numIid, String picPath) throws ApiException {
        // 調用API
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        ItemUpdateRequest request = new ItemUpdateRequest();
        request.setNumIid(numIid);
        request.setPicPath(extractRelativePicPath(picPath));
        ItemUpdateResponse response = client.execute(request, accessToken);
        return response;
    }

    @Override
    public FenxiaoProductUpdateResponse taobaoFenxiaoProductUpdate(String appKey, String appSecret,
        String accessToken, Long pid, Long quantity, String status, String skuIds,
        String skuOuterIds, String skuQuantitys) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        FenxiaoProductUpdateRequest request = new FenxiaoProductUpdateRequest();
        request.setPid(pid);
        if (StringTool.isNotEmpty(quantity)) {
            request.setQuantity(quantity);
        }
        if (StringTool.isNotEmpty(status)) {
            request.setStatus(status);
        }
        if (StringTool.isNotEmpty(skuIds)) {
            request.setSkuIds(skuIds);
        }
        if (StringTool.isNotEmpty(skuOuterIds)) {
            request.setSkuOuterIds(skuOuterIds);
        }
        if (StringTool.isNotEmpty(skuQuantitys)) {
            request.setSkuQuantitys(skuQuantitys);
        }

        FenxiaoProductUpdateResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public LogisticsOfflineSendResponse taobaoLogisticsOfflineSend(String appKey, String appSecret,
        String accessToken, Long tid, String outSid, String companyCode, String subTids,
        boolean isSplit) throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        LogisticsOfflineSendRequest logisticsOfflineSendRequest = new LogisticsOfflineSendRequest();
        logisticsOfflineSendRequest.setTid(tid);
        logisticsOfflineSendRequest.setOutSid(outSid);
        logisticsOfflineSendRequest.setCompanyCode(companyCode);

        logisticsOfflineSendRequest.setIsSplit(isSplit ? 1l : 0l);
        if (isSplit) {
            logisticsOfflineSendRequest.setSubTid(subTids);
        }

        LogisticsOfflineSendResponse logisticsOfflineSendResponse =
            client.execute(logisticsOfflineSendRequest, accessToken);
        return logisticsOfflineSendResponse;
    }

    @Override
    public LogisticsOnlineSendResponse taobaoLogisticsOnlineSend(String appKey, String appSecret,
        String accessToken, Long tid, boolean isSplit, String outSid, String companyCode,
        String subTids) throws ApiException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        LogisticsOnlineSendRequest request = new LogisticsOnlineSendRequest();
        request.setTid(tid);
        request.setIsSplit(isSplit ? 1L : 0L);
        if (StringTool.isNotEmpty(outSid)) {
            request.setOutSid(outSid);
        }
        request.setCompanyCode(companyCode);
        if (isSplit) {
            request.setSubTid(subTids);
        }
        LogisticsOnlineSendResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override public TraderatesGetResponse taobaoTraderatesGet(String appKey, String appSecret,
        String accessToken, String fields, String rateType, String role, String result, Long pageNo,
        Long pageSize, Date startDate, Date endDate, Long tid, Boolean useHasNext, Long numIid)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        TraderatesGetRequest request = new TraderatesGetRequest();
        request.setFields(fields);
        request.setRateType(rateType);
        request.setRole(role);
        if (StringTool.isNotEmpty(result)) {
            request.setResult(result);
        }
        if (StringTool.isNotEmpty(pageNo)) {
            request.setPageNo(pageNo);
        }
        if (StringTool.isNotEmpty(pageSize)) {
            request.setPageSize(pageSize);
        }
        if (StringTool.isNotEmpty(startDate)) {
            request.setStartDate(startDate);
        }
        if (StringTool.isNotEmpty(endDate)) {
            request.setEndDate(endDate);
        }
        if (StringTool.isNotEmpty(tid)) {
            request.setTid(tid);
        }
        if (StringTool.isNotEmpty(useHasNext)) {
            request.setUseHasNext(useHasNext);
        }
        if (StringTool.isNotEmpty(numIid)) {
            request.setNumIid(numIid);
        }

        TraderatesGetResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public LogisticsTraceSearchResponse taobaoLogisticsTraceSearch(String appKey, String appSecret,
        String accessToken, Long tid, String sellerNick, Long isSplit, String subTids)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        LogisticsTraceSearchRequest request = new LogisticsTraceSearchRequest();
        request.setTid(tid);
        request.setSellerNick(sellerNick);
        if (StringTool.isNotEmpty(isSplit)) {
            request.setIsSplit(isSplit);
        }
        if (StringTool.isNotEmpty(subTids)) {
            request.setSubTid(subTids);
        }

        LogisticsTraceSearchResponse response = client.execute(request);

        return response;
    }

    @Override public TraderateAddResponse taobaoTradeRateAdd(String appKey, String appSecret,
        String accessToken, Long tid, Long oid, String result, String content)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        TraderateAddRequest request = new TraderateAddRequest();
        request.setTid(tid);
        if (oid != null) {
            request.setOid(oid);
        }
        request.setResult(result);
        request.setRole(TaobaoTradeRateRole.SELLER.getRole());
        if (StringTool.isNotEmpty(content)) {
            request.setContent(content);
        }

        TraderateAddResponse response = client.execute(request, accessToken);

        return response;
    }

    @Override
    public TraderateListAddResponse taobaoTradeRateListAdd(String appKey, String appSecret,
        String accessToken, Long tid, String result, String content)
        throws ApiException, IOException {
        TaobaoClient client = TaobaoClientUtil.getInstance()
            .getMyAutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
                appKey, appSecret);
        TraderateListAddRequest request = new TraderateListAddRequest();
        request.setTid(tid);
        request.setResult(result);
        request.setRole(TaobaoTradeRateRole.SELLER.getRole());
        if (StringTool.isNotEmpty(content)) {
            request.setContent(content);
        }

        TraderateListAddResponse response = client.execute(request, accessToken);

        return response;
    }



    // /**
    // * 获取当前会话用户出售中的非分銷商品列表<br>
    // * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.0.0.FppmTN&path=categoryId:4-apiId:18
    // *
    // * @param esv DB中保存的授权信息
    // * @param startModifiedDate 修改开始时间
    // * @param endModifiedDate 修改结束时间
    // * @param pageNo 页码
    // * @param pageSize 页面大小
    // * @return ItemInventoryGetResponse
    // * @throws ApiException
    // * @author Sen.shen & 维杰
    // */
    // @Override
    // public ItemsInventoryGetResponse taobaoItemsInventoryGet(ESVerification esv, String fields,
    // String startModifiedDate, String endModifiedDate, Long pageNo, Long pageSize)
    // throws ApiException {
    //
    // TaobaoClient client =
    // new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    // esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
    // ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
    // // mark by mowj 20150810 start
    // // req.setFields("approve_status,num_iid,title,nick,"
    // // + "type,cid,pic_url,num,props,valid_thru,"
    // // + "list_time,price,has_discount,has_invoice,"
    // // + "has_warranty,has_showcase,modified,"
    // // + "delist_time,postage_id,seller_cids,outer_id");
    // // mark by mowj 20150810 end
    // req.setFields(fields); // add by mowj 20150810
    // //
    // 修改的原因：因为这个只能获取部分字段，详情需要使用num_iid调用另外的API，且需要modified作为中台的lastUpdateTime,所以这里只需要取num_iid与modified即可
    // req.setStartModified(DateTimeTool.parse(startModifiedDate));
    // req.setEndModified(DateTimeTool.parse(endModifiedDate));
    // req.setPageNo(pageNo);// 默認 1 pageNo * pageSize > 100000 會報錯
    // req.setPageSize(pageSize);// 默認 40 最大 200 pageNo * pageSize > 20000 會報錯
    // return client.execute(req, esv.getAccessToken());
    // }
    //
    // /**
    // * 获取当前会话用户库存中的非分销商品列表 <br>
    // *
    // http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.0.0.FppmTN&path=categoryId:4-apiId:162
    // *
    // * @param esv DB中保存的授权信息
    // * @param startModifiedDate 修改开始时间
    // * @param endModifiedDate 修改结束时间
    // * @param pageNo 页码
    // * @param pageSize 页面大小
    // * @return ItemsInventoryGetResponse
    // * @throws ApiException
    // * @author Sen.shen & 维杰
    // */
    // @Override
    // public ItemsInventoryGetResponse taobaoItemInventoryGet(ESVerification esv, String fields,
    // String startModifiedDate, String endModifiedDate, Long pageNo, Long pageSize)
    // throws ApiException {
    //
    // TaobaoClient client =
    // new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    // esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
    // ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
    // // mark by mowj 20150810 start
    // // req.setFields("approve_status,num_iid,title,nick,type,"
    // // + "cid,pic_url,num,props,valid_thru,list_time,"
    // // + "price,has_discount,has_invoice,has_warranty,"
    // // + "has_showcase,modified,delist_time,postage_id,"
    // // + "seller_cids,outer_id");
    // // mark by mowj 20150810 end
    // req.setFields(fields); // add by mowj 20150810
    // //
    // 修改的原因：因为这个只能获取部分字段，详情需要使用num_iid调用另外的API，且需要modified作为中台的lastUpdateTime,所以这里只需要取num_iid与modified即可
    // req.setStartModified(DateTimeTool.parse(startModifiedDate));
    // req.setEndModified(DateTimeTool.parse(endModifiedDate));
    // req.setPageNo(pageNo);// 1 ~ 101 超過報錯
    // req.setPageSize(pageSize);// 默認 40 最大 200 pageNo * pageSize > 20000 會報錯
    // return client.execute(req, esv.getAccessToken());
    // }

    // /**
    // * 批量获取非分销商品详细信息<br>
    // * http://open.taobao.com/apidoc/api.htm?spm=a219a.7386789.0.0.FppmTN&path=categoryId:4-apiId:
    // * 24626
    // *
    // * @param esv DB中保存的授权信息
    // * @param idList 商品编码列表，以","分隔。最多20个
    // * @return ItemsSellerListGetResponse
    // * @throws ApiException
    // * @author Sen.shen & 维杰
    // */
    // private ItemsSellerListGetResponse taobaoItemsSellerListGet(ESVerification esv, String idList)
    // throws ApiException {
    // TaobaoClient client =
    // new AutoRetryClusterTaobaoClient(paramSystemService.getEcApiUrl(EcApiUrlEnum.TOP_API),
    // esv.getAppKey(), esv.getAppSecret(), Constants.FORMAT_JSON);
    // ItemsSellerListGetRequest req = new ItemsSellerListGetRequest();
    // req.setFields("num_iid,outer_id,title,approve_status,modified,"
    // + "detail_url,pic_url,created,list_time,delist_time,sku");
    // req.setNumIids(idList);
    // return client.execute(req, esv.getAccessToken());
    // }
    //
    // private List<AomsitemT> doParseTranslate(ESVerification esv, String numiidArr, String storeId)
    // throws ApiException {
    // ItemsSellerListGetResponse response = taobaoItemsSellerListGet(esv, numiidArr);
    // List<AomsitemT> aomsitemTList = new ArrayList<AomsitemT>();
    // for (Item item : response.getItems()) {
    // List<AomsitemT> itemList = tableJdpTbItem.parseItemToAomsitemT(item);
    // aomsitemTList.addAll(itemList);
    // }
    // System.out.println(storeId + "：" + aomsitemTList.size());
    // return aomsitemTList;
    // }

}
