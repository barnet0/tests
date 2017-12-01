package com.digiwin.ecims.platforms.aliexpress.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderByIdResponse;

public class FindOrderByIdRequest extends AliExpressBaseRequest<FindOrderByIdResponse> {

//  是   订单Id    30110902195804
  private Long orderId;  
  
//  否   暂不支持。需要返回的订单对象字段。多个字段用“,”分隔。如果想返回整个子对象，该字段不设值。 目前支持以下字段：id,gmtCreate,orderStatus,sellerOperatorAliid,sellerOperatorLoginId,paymentType ,initOderAmount,orderAmount,escrowFee
  private String fieldList;        
  
//  否   暂不支持。扩展信息目前支持纠纷信息，放款信息，物流信息，买方信息和退款信息，分别对应二进制位1,2,3,4,5。例如，只查询纠纷信息和物流信息，extInfoBitFlag=10100；查询全部extInfoBitFlag=11111
  private Integer extInfoBitFlag;   
  
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public String getFieldList() {
    return fieldList;
  }

  public void setFieldList(String fieldList) {
    this.fieldList = fieldList;
  }

  public Integer getExtInfoBitFlag() {
    return extInfoBitFlag;
  }

  public void setExtInfoBitFlag(Integer extInfoBitFlag) {
    this.extInfoBitFlag = extInfoBitFlag;
  }

  @Override
  protected String getApiName() {
    return "api.findOrderById";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getOrderId() != null) {
      apiParams.put("orderId", getOrderId() + "");
    }
    if (getFieldList() != null) {
      apiParams.put("fieldList", getFieldList());
    }
    if (getExtInfoBitFlag() != null) {
      apiParams.put("extInfoBitFlag", getExtInfoBitFlag() + "");
    }
    return apiParams;
  }

  @Override
  public Class<FindOrderByIdResponse> getResponseClass() {
    return FindOrderByIdResponse.class;
  }

}
