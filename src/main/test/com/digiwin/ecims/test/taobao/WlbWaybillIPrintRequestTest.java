package com.digiwin.ecims.test.taobao;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.WlbWaybillIPrintRequest;
import com.taobao.api.request.WlbWaybillIPrintRequest.PrintCheckInfo;
import com.taobao.api.request.WlbWaybillIPrintRequest.WaybillApplyPrintCheckRequest;
import com.taobao.api.response.WlbWaybillIPrintResponse;

public class WlbWaybillIPrintRequestTest {

  TaobaoClient client;
  
  @Before
  public void setUp() throws Exception {
    client =
    new DefaultTaobaoClient("http://121.199.161.136:30001/tbapi",
    "12414910",
    "59d0c0c35a75e5b2cb742e6d174642f3",
    Constants.FORMAT_JSON);
  }

  @After
  public void tearDown() throws Exception {}

  @org.junit.Test
  public void test() {
//    fail("Not yet implemented");
     WlbWaybillIPrintRequest req = new WlbWaybillIPrintRequest();
     WaybillApplyPrintCheckRequest obj1 = new WaybillApplyPrintCheckRequest();
     List<PrintCheckInfo> list3 = new ArrayList<PrintCheckInfo>();
     PrintCheckInfo obj4 = new PrintCheckInfo();
     list3.add(obj4);
    
     obj4.setConsigneeName("管宏玫");
     obj4.setWaybillCode("3967990501321");
     com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress consigneeAddress
     = new com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress();
     consigneeAddress.setProvince("江苏省");
     consigneeAddress.setAddressDetail("中央北路168号盛景园3栋3单元1212室");
     obj4.setConsigneeAddress(consigneeAddress);
     obj4.setConsigneePhone("13814007086");
     com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress shippingAddress = new
     com.taobao.api.request.WlbWaybillIPrintRequest.WaybillAddress();
     shippingAddress.setProvince("浙江省");
     shippingAddress.setCity("金华市");
     shippingAddress.setArea("义乌市");
     shippingAddress.setAddressDetail("浙江省义乌市廿三里街道开元北街121号");
     obj4.setShippingAddress(shippingAddress);
     obj4.setRealUserId(2608950843L);
     obj1.setPrintCheckInfoCols(list3);
     obj1.setCpCode("YUNDA");
     req.setWaybillApplyPrintCheckRequest(obj1);
     WlbWaybillIPrintResponse rsp;
    try {
      rsp = client.execute(req,
       "610050424fe414bcceb45286876a2af5fa523676dbe9989479966771");
      System.out.println(rsp.getBody());
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }

}
