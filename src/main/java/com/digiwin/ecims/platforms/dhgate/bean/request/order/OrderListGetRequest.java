package com.digiwin.ecims.platforms.dhgate.bean.request.order;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderListGetResponse;

/**
 * 订单列表请求类
 * 
 * @author 维杰
 *
 */
public class OrderListGetRequest extends DhgateBaseRequest<OrderListGetResponse> {

  // 可选 买家昵称 示例值：zhangsan
  private String buyerNickName;
  // 可选 运单号 示例值：1Z68A9X70467731838
  private String deliveryNo;
  // 必须 结束时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String endDate;
  // 可选 产品编号 产品号在页面详情的URL中即可查看到；示例值：180366586
  private String itemCode;
  // 可选 订单号 卖家后台能查看；示例值：1455953150
  private String orderNo;
  // 可选 订单状态列表
  // 111000,订单取消;101003,等待买家付款;102001,买家已付款，等待平台确认;103001,等待发货;105001,买家申请退款，等待协商结果;105002,退款协议已达成;105003,部分退款后，等待发货;105004,买家取消退款申请;103002,已部分发货;101009,等待买家确认收货;106001,退款/退货协商中，等待协议达成;106002,买家投诉到平台;106003,协议已达成，执行中;102006,人工确认收货;102007,超过预定期限，自动确认收货;102111,交易成功;111111,交易关闭；示例值：101009,101003,多种状态时用英文逗号分隔，默认为空，查询所有订单
  private String orderStatus;
  // 必须 第几页 示例值：10,表示第10页
  private Integer pageNo;
  // 必须 每页记录数 示例值：10,表示每页10条记录
  private Integer pageSize;
  // 必须 查询时间类型 按哪种时间类型查询订单列表，值：1、下单时间 ,2、付款时间,仅支持此两种时间类型查询
  private String querytimeType;
  // 必须 开始时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String startDate;

  public String getBuyerNickName() {
    return buyerNickName;
  }

  public void setBuyerNickName(String buyerNickName) {
    this.buyerNickName = buyerNickName;
  }

  public String getDeliveryNo() {
    return deliveryNo;
  }

  public void setDeliveryNo(String deliveryNo) {
    this.deliveryNo = deliveryNo;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getItemCode() {
    return itemCode;
  }

  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getQuerytimeType() {
    return querytimeType;
  }

  public void setQuerytimeType(String querytimeType) {
    this.querytimeType = querytimeType;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public OrderListGetRequest() {
    super();
  }

  @Override
  public Class<OrderListGetResponse> getResponseClass() {
    return OrderListGetResponse.class;
  }


}
