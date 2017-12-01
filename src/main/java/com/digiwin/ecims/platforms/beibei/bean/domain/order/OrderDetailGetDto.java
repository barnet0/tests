package com.digiwin.ecims.platforms.beibei.bean.domain.order;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import com.digiwin.ecims.platforms.beibei.bean.domain.order.base.AbstractOrderDto;
import com.digiwin.ecims.platforms.beibei.bean.domain.order.base.AbstractOrderItemDto;

/**
 * for 订单详情API返回数据
 * @author zaregoto
 *
 */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class OrderDetailGetDto extends AbstractOrderDto {

  private String user;

  private String channelName;

  private String channelInfo;

  private Object memberCard;

  private String memberCardImgs;

  private Double forexTotalFee;

  private Double forexPayment;

  private Double forexOriginFee;

  private Double forexShippingFee;

  private Double orderForex;

  private String codeTs;

  private Double taxFee;

  private Double forexTaxFee;

  private Double tariffFee;

  private Double forexTariffFee;

  private Double addedvalueFee;

  private Double forexAddedvalueFee;

  private Double consumpFee;

  private Double forexConsumpFee;

  private String orderType;

  private String tradeWaterNo;

  private String partnerId;

  private List<OrderDetailGetItemDto> item;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getChannelInfo() {
    return channelInfo;
  }

  public void setChannelInfo(String channelInfo) {
    this.channelInfo = channelInfo;
  }

  public Object getMemberCard() {
    return memberCard;
  }

  public void setMemberCard(Object memberCard) {
    this.memberCard = memberCard;
  }

  public String getMemberCardImgs() {
    return memberCardImgs;
  }

  public void setMemberCardImgs(String memberCardImgs) {
    this.memberCardImgs = memberCardImgs;
  }

  public Double getForexTotalFee() {
    return forexTotalFee;
  }

  public void setForexTotalFee(Double forexTotalFee) {
    this.forexTotalFee = forexTotalFee;
  }

  public Double getForexPayment() {
    return forexPayment;
  }

  public void setForexPayment(Double forexPayment) {
    this.forexPayment = forexPayment;
  }

  public Double getForexOriginFee() {
    return forexOriginFee;
  }

  public void setForexOriginFee(Double forexOriginFee) {
    this.forexOriginFee = forexOriginFee;
  }

  public Double getForexShippingFee() {
    return forexShippingFee;
  }

  public void setForexShippingFee(Double forexShippingFee) {
    this.forexShippingFee = forexShippingFee;
  }

  public Double getOrderForex() {
    return orderForex;
  }

  public void setOrderForex(Double orderForex) {
    this.orderForex = orderForex;
  }

  public String getCodeTs() {
    return codeTs;
  }

  public void setCodeTs(String codeTs) {
    this.codeTs = codeTs;
  }

  public Double getTaxFee() {
    return taxFee;
  }

  public void setTaxFee(Double taxFee) {
    this.taxFee = taxFee;
  }

  public Double getForexTaxFee() {
    return forexTaxFee;
  }

  public void setForexTaxFee(Double forexTaxFee) {
    this.forexTaxFee = forexTaxFee;
  }

  public Double getTariffFee() {
    return tariffFee;
  }

  public void setTariffFee(Double tariffFee) {
    this.tariffFee = tariffFee;
  }

  public Double getForexTariffFee() {
    return forexTariffFee;
  }

  public void setForexTariffFee(Double forexTariffFee) {
    this.forexTariffFee = forexTariffFee;
  }

  public Double getAddedvalueFee() {
    return addedvalueFee;
  }

  public void setAddedvalueFee(Double addedvalueFee) {
    this.addedvalueFee = addedvalueFee;
  }

  public Double getForexAddedvalueFee() {
    return forexAddedvalueFee;
  }

  public void setForexAddedvalueFee(Double forexAddedvalueFee) {
    this.forexAddedvalueFee = forexAddedvalueFee;
  }

  public Double getConsumpFee() {
    return consumpFee;
  }

  public void setConsumpFee(Double consumpFee) {
    this.consumpFee = consumpFee;
  }

  public Double getForexConsumpFee() {
    return forexConsumpFee;
  }

  public void setForexConsumpFee(Double forexConsumpFee) {
    this.forexConsumpFee = forexConsumpFee;
  }

  public String getOrderType() {
    return orderType;
  }

  public void setOrderType(String orderType) {
    this.orderType = orderType;
  }

  public String getTradeWaterNo() {
    return tradeWaterNo;
  }

  public void setTradeWaterNo(String tradeWaterNo) {
    this.tradeWaterNo = tradeWaterNo;
  }

  public String getPartnerId() {
    return partnerId;
  }

  public void setPartnerId(String partnerId) {
    this.partnerId = partnerId;
  }

  public List<OrderDetailGetItemDto> getItem() {
    return item;
  }

  public void setItem(List<OrderDetailGetItemDto> item) {
    this.item = item;
  }

  public OrderDetailGetDto() {
  }

  @Override
  public List<? extends AbstractOrderItemDto> getItems() {
    return getItem();
  }
  
}
