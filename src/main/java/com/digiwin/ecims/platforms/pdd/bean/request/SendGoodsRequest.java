package com.digiwin.ecims.platforms.pdd.bean.request;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.pdd.bean.response.SendGoodsResponse;

public class SendGoodsRequest extends PddBaseRequest<SendGoodsResponse> {

  private String orderSN;
  
  private String sndStyle;
  
  private String consignTime;
  
  private String billID;
  
  /**
   * @return the orderSN
   */
  public String getOrderSN() {
    return orderSN;
  }

  /**
   * @param orderSN the orderSN to set
   */
  public void setOrderSN(String orderSN) {
    this.orderSN = orderSN;
  }

  /**
   * @return the sndStyle
   */
  public String getSndStyle() {
    return sndStyle;
  }

  /**
   * @param sndStyle the sndStyle to set
   */
  public void setSndStyle(String sndStyle) {
    this.sndStyle = sndStyle;
  }

  /**
   * @return the consignTime
   */
  public String getConsignTime() {
    return consignTime;
  }

  /**
   * @param consignTime the consignTime to set
   */
  public void setConsignTime(String consignTime) {
    this.consignTime = consignTime;
  }

  /**
   * @return the billID
   */
  public String getBillID() {
    return billID;
  }

  /**
   * @param billID the billID to set
   */
  public void setBillID(String billID) {
    this.billID = billID;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("orderSN", getOrderSN());
    apiParams.put("sndStyle", getSndStyle());
    apiParams.put("consignTime", getConsignTime());
    apiParams.put("billID", getBillID());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "mSndGoods";
  }

  @Override
  public Class<SendGoodsResponse> getResponseClass() {
    return SendGoodsResponse.class;
  }

}
