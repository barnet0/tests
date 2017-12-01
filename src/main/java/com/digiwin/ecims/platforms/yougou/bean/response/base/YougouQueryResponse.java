package com.digiwin.ecims.platforms.yougou.bean.response.base;

import java.util.List;


/**
 * 查询类型API的请求类
 * 
 * @author 维杰
 * @param <T>
 *
 */
public class YougouQueryResponse<T> extends YougouBaseResponse {

  private String message;
  
  private List<T> items;

  private Integer pageIndex;

  private Integer pageSize;

  private Integer totalCount;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
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

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public YougouQueryResponse() {
    super();
  }


}
