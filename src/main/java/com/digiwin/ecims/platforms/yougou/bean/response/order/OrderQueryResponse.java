package com.digiwin.ecims.platforms.yougou.bean.response.order;

import java.util.List;

import com.digiwin.ecims.platforms.yougou.bean.domain.order.OrderQuery;
import com.digiwin.ecims.platforms.yougou.bean.response.base.YougouQueryResponse;

/**
 * @author 维杰
 *
 */
public class OrderQueryResponse extends YougouQueryResponse<OrderQuery> {

  private String message;

  private Integer pageSize;

  private Integer pageIndex;

  private List<OrderQuery> items;

  private String code;

  private Integer totalCount;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public List<OrderQuery> getItems() {
    return items;
  }

  public void setItems(List<OrderQuery> items) {
    this.items = items;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }


}
