package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos;

import java.util.List;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.base.BaiduBaseRequest;

public class QueryItemInfosRequest extends BaiduBaseRequest {

  private List<Integer> categoryIds;
  
  private int pageIndex;
  
  private int pageSize;
  
  public List<Integer> getCategoryIds() {
    return categoryIds;
  }

  public void setCategoryIds(List<Integer> categoryIds) {
    this.categoryIds = categoryIds;
  }

  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  @Override
  public String getUrlPath() {
    return "ItemService/queryItemInfos";
  }

}
