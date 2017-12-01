package com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos;

import java.util.List;

public class QueryItemInfosResponseData {

  private List<Long> itemIds;

  private int pageSize;

  private int totalRecords;

  private int code;

  private String msg;

  private int pageIndex;

  public void setItemIds(List<Long> itemIds) {
    this.itemIds = itemIds;
  }

  public List<Long> getItemIds() {
    return this.itemIds;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public int getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }


}
