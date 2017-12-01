package com.digiwin.ecims.test.taobao;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.WaybillDetailQueryRequest;
import com.taobao.api.request.WlbWaybillIQuerydetailRequest;
import com.taobao.api.response.WlbWaybillIQuerydetailResponse;

public class WlbWaybillIQuerydetailTest {

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
    WlbWaybillIQuerydetailRequest req = new WlbWaybillIQuerydetailRequest();
    WaybillDetailQueryRequest obj1 = new WaybillDetailQueryRequest();
    List<String> waybillCodes = new ArrayList<String>();
  //  waybillCodes.add("882177535569694240");
  //  waybillCodes.add("882177535567371018");
  //  waybillCodes.add("882177535568533634");
  //  waybillCodes.add("882177535565654846");
  //  waybillCodes.add("882164639519583139");
  //  waybillCodes.add("882156457389765957");
  //  waybillCodes.add("882164595255331614");
  //  waybillCodes.add("882164595256795250");
  //  waybillCodes.add("882164595257159896");
  //  waybillCodes.add("882177535566917472");
  //  waybillCodes.add("88220727593092757");
  //  waybillCodes.add("882222589552694240");
  //  obj1.setWaybillCodes(waybillCodes);
    obj1.setCpCode("EYB");
  //  obj1.setQueryBy(0L);
    obj1.setQueryBy(1L);
    List<String> tradeOrders = new ArrayList<String>();
  //  tradeOrders.add("2027892657806404");
  //  tradeOrders.add("2027892657816404");
  //  tradeOrders.add("1647889391588461");
  //  tradeOrders.add("1642027797834256");
  //  tradeOrders.add("2021495702772919");
  //  tradeOrders.add("2027892657806404");
  //  tradeOrders.add("2043108827026107");
    tradeOrders.add("2105915870302313");
    tradeOrders.add("2105915870312313");
    obj1.setTradeOrderList(tradeOrders);
    
    req.setWaybillDetailQueryRequest(obj1);
    WlbWaybillIQuerydetailResponse rsp;
    try {
      rsp = client.execute(req, 
          "610050424fe414bcceb45286876a2af5fa523676dbe9989479966771");
      System.out.println(rsp.getBody());
    } catch (ApiException e) {
      e.printStackTrace();
    }
  }

}
