package com.digiwin.ecims.platforms.aliexpress.bean.request.order;

import java.util.HashMap;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.order.FindOrderListQueryResponse;

public class FindOrderListQueryRequest extends AliExpressBaseRequest<FindOrderListQueryResponse> {

  // 是 当前页码 1
  private Integer page;

  // 是 每页个数，最大50 50
  private Integer pageSize;

  // 否 订单创建时间起始值，格式: mm/dd/yyyy hh:mm:ss,如10/08/2013 00:00:00 倘若时间维度未精确到时分秒，故该时间条件筛选不许生效。 10/01/2015
  // 00:00:00
  private String createDateStart;

  // 否 订单创建时间结束值，格式: mm/dd/yyyy hh:mm:ss,如10/08/2013 00:00:00 倘若时间维度未精确到时分秒，故该时间条件筛选不许生效。 10/07/2015
  // 00:00:00
  private String createDateEnd;

  // 否 订单状态： PLACE_ORDER_SUCCESS:等待买家付款; IN_CANCEL:买家申请取消; WAIT_SELLER_SEND_GOODS:等待您发货;
  // SELLER_PART_SEND_GOODS:部分发货; WAIT_BUYER_ACCEPT_GOODS:等待买家收货; FUND_PROCESSING:买卖家达成一致，资金处理中；
  // IN_ISSUE:含纠纷中的订单; IN_FROZEN:冻结中的订单; WAIT_SELLER_EXAMINE_MONEY:等待您确认金额;
  // RISK_CONTROL:订单处于风控24小时中，从买家在线支付完成后开始，持续24小时。 以上状态查询可分别做单独查询，不传订单状态查询订单信息不包含（FINISH，已结束订单状态）
  // FINISH:已结束的订单，需单独查询。 PLACE_ORDER_SUCCESS
  private String orderStatus;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getCreateDateStart() {
    return createDateStart;
  }

  public void setCreateDateStart(String createDateStart) {
    this.createDateStart = createDateStart;
  }

  public String getCreateDateEnd() {
    return createDateEnd;
  }

  public void setCreateDateEnd(String createDateEnd) {
    this.createDateEnd = createDateEnd;
  }

  public String getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
  }

  @Override
  protected String getApiName() {
    return "api.findOrderListQuery";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getPage() != null) {
      apiParams.put("page", getPage() + "");
    }
    if (getPageSize() != null) {
      apiParams.put("pageSize", getPageSize() + "");
    }
    if (getCreateDateStart() != null) {
      apiParams.put("createDateStart", getCreateDateStart());
    }
    if (getCreateDateEnd() != null) {
      apiParams.put("createDateEnd", getCreateDateEnd());
    }
    if (getOrderStatus() != null) {
      apiParams.put("orderStatus", getOrderStatus());
    }

    return apiParams;
  }

  @Override
  public Class<FindOrderListQueryResponse> getResponseClass() {
    return FindOrderListQueryResponse.class;
  }


}
