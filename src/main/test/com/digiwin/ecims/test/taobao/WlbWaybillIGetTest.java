package com.digiwin.ecims.test.taobao;

import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.WlbWaybillIGetRequest;
import com.taobao.api.request.WlbWaybillIGetRequest.PackageItem;
import com.taobao.api.request.WlbWaybillIGetRequest.TradeOrderInfo;
import com.taobao.api.request.WlbWaybillIGetRequest.WaybillAddress;
import com.taobao.api.request.WlbWaybillIGetRequest.WaybillApplyNewRequest;
import com.taobao.api.response.WlbWaybillIGetResponse;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class WlbWaybillIGetTest {

    TaobaoClient client;

    @Before public void setUp() throws Exception {
        client = new DefaultTaobaoClient("http://121.199.161.136:30001/tbapi", "12414910",
            "59d0c0c35a75e5b2cb742e6d174642f3", Constants.FORMAT_JSON);
    }

    @After public void tearDown() throws Exception {
    }

    @org.junit.Test public void test() {
        // fail("Not yet implemented");
        WlbWaybillIGetRequest request = new WlbWaybillIGetRequest();
        WaybillApplyNewRequest applyNewRequest = new WaybillApplyNewRequest();
        applyNewRequest.setCpCode("YTO");
        WaybillAddress shippingAddress = new WaybillAddress();
        shippingAddress.setProvince("浙江省");
        shippingAddress.setCity("金华市");
        shippingAddress.setArea("义乌市");
        shippingAddress.setAddressDetail("浙江省义乌市廿三里街道开元北街121号");
        applyNewRequest.setShippingAddress(shippingAddress);

        List<TradeOrderInfo> tradeOrderInfos = new ArrayList<TradeOrderInfo>();

        TradeOrderInfo tradeOrderInfo = new TradeOrderInfo();
        tradeOrderInfo.setConsigneeName("李蓉");
        tradeOrderInfo.setOrderChannelsType("TM");
        List<String> oidList = new ArrayList<String>();
        oidList.add("2027892657806404");
        oidList.add("2027892657816404");
        tradeOrderInfo.setTradeOrderList(oidList);
        tradeOrderInfo.setConsigneePhone("18192233102");

        WaybillAddress consigneeAddress = new WaybillAddress();
        consigneeAddress.setProvince("陕西省");
        consigneeAddress.setAddressDetail("陕西省西安市其它区高新技术产业开发区科技路37号海星城市广场A座1509室");

        tradeOrderInfo.setConsigneeAddress(consigneeAddress);
        List<PackageItem> packageItems = new ArrayList<PackageItem>();

        // 添加订单的商品信息
        PackageItem packageItem = new PackageItem();
        packageItem.setItemName("3114560568391");
        packageItem.setCount(1L);
        packageItems.add(packageItem);

        PackageItem packageItem2 = new PackageItem();
        packageItem2.setItemName("3114501297115");
        packageItem2.setCount(1L);
        packageItems.add(packageItem2);

        tradeOrderInfo.setPackageItems(packageItems);
        tradeOrderInfo.setProductType("STANDARD_EXPRESS");
        tradeOrderInfo.setRealUserId(2608950843L);

        tradeOrderInfos.add(tradeOrderInfo);

        applyNewRequest.setTradeOrderInfoCols(tradeOrderInfos);
        request.setWaybillApplyNewRequest(applyNewRequest);

        WlbWaybillIGetResponse response;
        try {
            response =
                client.execute(request, "610050424fe414bcceb45286876a2af5fa523676dbe9989479966771");
            System.out.println(response.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
