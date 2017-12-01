package com.digiwin.ecims.platforms.dhgate.bean.request.order;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeOpenListResponse;

/**
 * 获取seller纠纷开启订单列表接口请求类
 * 
 * @author 维杰
 *
 */
public class OrderDisputeOpenListRequest extends DhgateBaseRequest<OrderDisputeOpenListResponse> {

  // 可选 买家昵称 示例值：zhangsan
  private String buyerNickName;
  // 可选 纠纷类型 1、协议纠纷，2、平台纠纷，3、售后纠纷，默认全部纠纷中订单
  private String disputeType;
  // 可选 订单号 卖家后台能查看；示例值：1455953150
  private String orderNo;
  // 必须 第几页 示例值：10,表示第10页
  private Integer pageNo;
  // 必须 每页记录数 示例值：10,表示每页10条记录
  private Integer pageSize;

  public String getBuyerNickName() {
    return buyerNickName;
  }

  public void setBuyerNickName(String buyerNickName) {
    this.buyerNickName = buyerNickName;
  }

  public String getDisputeType() {
    return disputeType;
  }

  public void setDisputeType(String disputeType) {
    this.disputeType = disputeType;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
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

  public OrderDisputeOpenListRequest() {
    super();
  }

  @Override
  public Class<OrderDisputeOpenListResponse> getResponseClass() {
    return OrderDisputeOpenListResponse.class;
  }


}
