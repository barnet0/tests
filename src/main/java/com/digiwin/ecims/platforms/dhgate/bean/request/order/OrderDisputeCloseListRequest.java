package com.digiwin.ecims.platforms.dhgate.bean.request.order;

import com.digiwin.ecims.platforms.dhgate.bean.request.base.DhgateBaseRequest;
import com.digiwin.ecims.platforms.dhgate.bean.response.order.OrderDisputeCloseListResponse;

/**
 * 获取seller纠纷关闭订单列表接口请求类
 * 
 * @author 维杰
 *
 */
public class OrderDisputeCloseListRequest extends DhgateBaseRequest<OrderDisputeCloseListResponse> {

  // 可选 买家昵称 示例值：zhangsan
  private String buyerNickName;
  // 必须 纠纷关闭结束时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String endDate;
  // 可选 订单号 卖家后台能查看；示例值：1455953150
  private String orderNo;
  // 必须 第几页 示例值：10,表示第10页
  private Integer pageNo;
  // 必须 每页记录数 示例值：10,表示每页10条记录
  private Integer pageSize;
  // 必须 纠纷关闭开始时间 日期格式：yyyy-MM-dd HH:mm:ss,精确到秒；示例值：2014-01-12 18:20:21
  private String startDate;

  public String getBuyerNickName() {
    return buyerNickName;
  }

  public void setBuyerNickName(String buyerNickName) {
    this.buyerNickName = buyerNickName;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
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

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public OrderDisputeCloseListRequest() {
    super();
  }

  @Override
  public Class<OrderDisputeCloseListResponse> getResponseClass() {
    return OrderDisputeCloseListResponse.class;
  }


}
