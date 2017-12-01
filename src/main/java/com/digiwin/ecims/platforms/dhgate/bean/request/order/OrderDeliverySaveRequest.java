package com.digiwin.ecims.platforms.dhgate.bean.request.order;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDeliverySaveResponse;

/**
 * 上传运单号接口请求类
 * 
 * @author 维杰
 *
 */
public class OrderDeliverySaveRequest extends DhgateBaseRequest<OrderDeliverySaveResponse> {

  // 必须 运单号 对应UPS的运单号；示例值：1Z68A9X70467731838
  private String deliveryNo;
  // 可选 运单备注 备注信息，不允许有中文，备注长度不大于1000，可选
  private String deliveryRemark;
  // 必须 发货状态 1、全部发货，2、部分发货
  private String deliveryState;
  // 必须 订单号 不要和订单ID混淆，订单号没有十六进制字符；示例值：1330312162
  private String orderNo;
  // 必须 运输方式 运输方式可以通过接口dh.shipping.types.get获取；示例值：UPS
  private String shippingType;

  public String getDeliveryNo() {
    return deliveryNo;
  }

  public void setDeliveryNo(String deliveryNo) {
    this.deliveryNo = deliveryNo;
  }

  public String getDeliveryRemark() {
    return deliveryRemark;
  }

  public void setDeliveryRemark(String deliveryRemark) {
    this.deliveryRemark = deliveryRemark;
  }

  public String getDeliveryState() {
    return deliveryState;
  }

  public void setDeliveryState(String deliveryState) {
    this.deliveryState = deliveryState;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getShippingType() {
    return shippingType;
  }

  public void setShippingType(String shippingType) {
    this.shippingType = shippingType;
  }

  public OrderDeliverySaveRequest() {
    super();
  }

  @Override
  public Class<OrderDeliverySaveResponse> getResponseClass() {
    return OrderDeliverySaveResponse.class;
  }


}
