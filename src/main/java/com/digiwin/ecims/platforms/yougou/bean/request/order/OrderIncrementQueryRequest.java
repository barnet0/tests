package com.digiwin.ecims.platforms.yougou.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.yougou.bean.request.base.YougouBaseRequest;
import com.digiwin.ecims.platforms.yougou.bean.response.order.OrderIncrementQueryResponse;

/**
 * @author 维杰
 *
 */
public class OrderIncrementQueryRequest extends YougouBaseRequest<OrderIncrementQueryResponse> {

//  订单最后修改开始时间(时间跨度不能大于一天)。格式:yyyy-MM-dd HH:mm:ss
  private String startModified;
  
//  订单最后修改结束时间，必须大于开始时间(时间跨度不能大于一天)，格式:yyyy-MM-dd HH:mm:ss。
  private String endModified;
  
//  销售订单状态(1＝待发货、2＝已完成、3＝申请拦截、4=异常订单) 关于申请拦截状态的订单，该类型订单也可以继续发货。如果同意被优购拦截，需要调用yougou.order.intercept.update回写状态
  private String orderStatus;
  
//  指定页码。有效取值范围：>=1
  private Integer pageIndex;
  
//  是 指定页大小。有效取值范围：1至50
  private Integer pageSize;
  
//      子订单号
  private String orderSubNo;
  
//  物流公司编码
  private String logisticsCode;
  
//  收货人姓名
  private String consigneeName;
  
//  支付方式
  private String payment;

  public String getStartModified() {
    return startModified;
  }

  public void setStartModified(String startModified) {
    this.startModified = startModified;
  }

  public String getEndModified() {
    return endModified;
  }

  public void setEndModified(String endModified) {
    this.endModified = endModified;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getOrderSubNo() {
    return orderSubNo;
  }

  public void setOrderSubNo(String orderSubNo) {
    this.orderSubNo = orderSubNo;
  }

  public String getLogisticsCode() {
    return logisticsCode;
  }

  public void setLogisticsCode(String logisticsCode) {
    this.logisticsCode = logisticsCode;
  }

  public String getConsigneeName() {
    return consigneeName;
  }

  public void setConsigneeName(String consigneeName) {
    this.consigneeName = consigneeName;
  }

  public String getPayment() {
    return payment;
  }

  public void setPayment(String payment) {
    this.payment = payment;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("start_modified", getStartModified());
    apiParams.put("end_modified", getEndModified());
    apiParams.put("order_status", getOrderStatus());
    apiParams.put("page_index", getPageIndex() + "");
    apiParams.put("page_size", getPageSize() + "");
    apiParams.put("order_sub_no", getOrderSubNo());
    apiParams.put("logistics_code", getLogisticsCode());
    apiParams.put("consignee_name", getConsigneeName());
    apiParams.put("payment", getPayment());

    return apiParams;
  }

  @Override
  public String getApiMethodName() {
    return "yougou.order.increment.query";
  }

  @Override
  public Class<OrderIncrementQueryResponse> getResponseClass() {
    return OrderIncrementQueryResponse.class;
  }

}
