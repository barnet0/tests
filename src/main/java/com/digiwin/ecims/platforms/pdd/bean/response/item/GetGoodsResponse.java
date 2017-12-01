package com.digiwin.ecims.platforms.pdd.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.pdd.bean.domain.item.Item;
import com.digiwin.ecims.platforms.pdd.bean.response.PddBaseResponse;

public class GetGoodsResponse extends PddBaseResponse {

  private Integer totalCount;
  
  private List<Item> goodList;

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public List<Item> getGoodList() {
    return goodList;
  }

  public void setGoodList(List<Item> goodList) {
    this.goodList = goodList;
  }
  
}
