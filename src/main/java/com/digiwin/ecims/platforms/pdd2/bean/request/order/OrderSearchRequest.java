package com.digiwin.ecims.platforms.pdd2.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.core.util.DateTimeTool;
import com.digiwin.ecims.platforms.pdd2.bean.request.Pdd2BaseRequest;
import com.digiwin.ecims.platforms.pdd2.bean.response.order.OrderSearchResponse;

public class OrderSearchRequest extends Pdd2BaseRequest<OrderSearchResponse> {

  private Integer order_status;
  
  
  /**
   *  用于查询返回每页的订单数量，默认100
   */
  private Integer page_size;
  
  /**
   * 用于查询要查询的页码 默认0，页码从0开始
   */
  private Integer page;

  /**
   * @return the orderStatus
   */
  public Integer getOrderStatus() {
    return order_status;
  }

  /**
   * @param orderStatus the orderStatus to set
   */
  public void setOrderStatus(Integer orderStatus) {
    this.order_status = orderStatus;
  }

  /**
   * @return the pageSize
   */
  public Integer getPageSize() {
    return page_size;
  }

  /**
   * @param pageSize the pageSize to set
   */
  public void setPageSize(Integer pageSize) {
    this.page_size = pageSize;
  }

  /**
   * @return the page
   */
  public Integer getPage() {
    return page;
  }

  /**
   * @param page the page to set
   */
  public void setPage(Integer page) {
    this.page = page;
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    apiParams.put("order_status", getOrderStatus() + "");
    if(page_size!=null)
    apiParams.put("page_size", getPageSize().toString());
    if(page!=null)
    apiParams.put("page", getPage().toString());

    return apiParams;
  }

  @Override
  public String getMType() {
    return "pdd.order.number.list.get";
  }

  @Override
  public Class<OrderSearchResponse> getResponseClass() {
    return OrderSearchResponse.class;
  }

}
