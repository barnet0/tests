package com.digiwin.ecims.core.bean.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.digiwin.ecims.platforms.taobao.bean.request.EcimsWaybillIGetOrdersParam;
import com.digiwin.ecims.platforms.yunji.bean.domain.logistic.LogisticList;
import com.digiwin.ecims.platforms.taobao.bean.request.EcimsTaobaoWaybillIPrintReqParam;

public class CmdReq {
  private String api; // 指令名稱
  private String ecno; // 目標電商平台
  private Map<String, String> params; // 指令所需的參數
  private String storeid; // 商店ID
  private List<? extends LinkedHashMap<String, String>> itemlist; // 當當所需的指令所需的參數

  private String[] prodcodelist; // suning 訂單發貨所需的特殊欄位 // modi by mowj 20150813
                                 // 为支援淘宝拆单发货，借用原来苏宁的属性：suboidlist
  private String[] suboidlist;

  // add by mowj 20160601 for 菜鸟物流申请面单号请求参数
  private List<EcimsWaybillIGetOrdersParam> orderlist;
  
  // add by mowj 20160601 for 菜鸟物流面单打印前确认
  private List<EcimsTaobaoWaybillIPrintReqParam> printlist;
  
  // add by mowj 20160602 for 菜鸟物流面单取消
  private List<String> oidlist;
  
  // add by xiaohb 20170704 for 云集平台发货确认
  private List<LogisticList> logisticlist;
  
//  private List<E>
  /**
   * default constructor
   */
  public CmdReq() {
    params = new HashMap<String, String>();
    itemlist = new ArrayList<>();
  }

  /**
   * constructor
   * 
   * @param _api API名稱
   * @param _ecno 電商平台代碼
   * @param _params API所需參數
   * @param _storeId 商店ID
   */
  public CmdReq(String _api, String _ecno, Map<String, String> _params, String _storeId,
      List<? extends LinkedHashMap<String, String>> _itemlist) {
    setApi(_api);
    setEcno(_ecno);
    setParams(_params);
    setStoreid(_storeId);
    setItemlist(_itemlist);
  }

  public String getApi() {
    return api;
  }

  public void setApi(String api) {
    this.api = (api == null) ? null : api.trim();
  }

  public String getEcno() {
    return ecno;
  }

  public void setEcno(String ecno) {
    this.ecno = (ecno == null) ? null : ecno.trim();
  }

  public Map<String, String> getParams() {
    return params;
  }

  public void setParams(Map<String, String> params) {
    this.params = params;

    if (this.params != null) {
      List<String> list = new ArrayList<String>(params.values());
      for (String param : list) {
        param = param.trim();
      }
    }
  }

  public String getStoreid() {
    return storeid;
  }

  public void setStoreid(String storeid) {
    this.storeid = (storeid == null) ? null : storeid.trim();
  }

  public List<? extends LinkedHashMap<String, String>> getItemlist() {
    return itemlist;
  }

  public void setItemlist(List<? extends LinkedHashMap<String, String>> itemlist) {
    this.itemlist = itemlist;
  }

  public String[] getSuboidlist() {
    return suboidlist;
  }

  public void setSuboidlist(String[] suboidlist) {
    this.suboidlist = suboidlist;
  }

  public String[] getProdcodelist() {
    return prodcodelist;
  }

  public void setProdcodelist(String[] prodcodelist) {
    this.prodcodelist = prodcodelist;
  }

  public List<EcimsWaybillIGetOrdersParam> getOrderlist() {
    return orderlist;
  }

  public void setOrderlist(List<EcimsWaybillIGetOrdersParam> orderlist) {
    this.orderlist = orderlist;
  }

  public List<EcimsTaobaoWaybillIPrintReqParam> getPrintlist() {
    return printlist;
  }

  public void setPrintlist(List<EcimsTaobaoWaybillIPrintReqParam> printlist) {
    this.printlist = printlist;
  }

  public List<String> getOidlist() {
    return oidlist;
  }

  public void setOidlist(List<String> oidlist) {
    this.oidlist = oidlist;
  }

  public List<LogisticList> getLogisticlist() {
	return logisticlist;
}

public void setLogisticlist(List<LogisticList> logisticlist) {
	this.logisticlist = logisticlist;
}

@Override
  public String toString() {
    String str = null;
    try {
      str = new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return str;
  }
}
