package com.digiwin.ecims.test.taobao;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.WaybillApplyCancelRequest;
import com.taobao.api.request.WlbWaybillICancelRequest;
import com.taobao.api.response.WlbWaybillICancelResponse;

public class WlbWaybillICancelTest {

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
    WlbWaybillICancelRequest req = new WlbWaybillICancelRequest();
    WaybillApplyCancelRequest obj1 = new WaybillApplyCancelRequest();
    obj1.setRealUserId(2608950843L);
    List<String> orderList = new ArrayList<String>();
    orderList.add("2105915870302313");
    orderList.add("2105915870312313");
    obj1.setTradeOrderList(orderList);
    obj1.setCpCode("EYB");
    obj1.setWaybillCode("9706191681612");
    req.setWaybillApplyCancelRequest(obj1);
    WlbWaybillICancelResponse rsp;
    try {
      rsp = client.execute(req,
      "610050424fe414bcceb45286876a2af5fa523676dbe9989479966771");
      System.out.println(rsp.getBody());
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }

}
