package com.digiwin.ecims.platforms.aliexpress.bean.response.item;

import java.util.List;

import com.digiwin.ecims.platforms.aliexpress.bean.domain.item.AeopAEProductDisplaySampleDTO;
import com.digiwin.ecims.platforms.aliexpress.bean.response.base.AliExpressBaseResponse;

public class FindProductInfoListQueryResponse extends AliExpressBaseResponse {

  // 是 商品基本信息列表
  private List<AeopAEProductDisplaySampleDTO> aeopAEProductDisplayDTOList;

  // 是 总页数 100
  private Integer totalPage;

  // 是 当前页 10
  private Integer currentPage;

  // 是 总商品数 1021
  private Integer productCount;

  // 是 接口调用结果 true
  private Boolean success;

  public List<AeopAEProductDisplaySampleDTO> getAeopAEProductDisplayDTOList() {
    return aeopAEProductDisplayDTOList;
  }

  public void setAeopAEProductDisplayDTOList(
      List<AeopAEProductDisplaySampleDTO> aeopAEProductDisplayDTOList) {
    this.aeopAEProductDisplayDTOList = aeopAEProductDisplayDTOList;
  }

  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getProductCount() {
    return productCount;
  }

  public void setProductCount(Integer productCount) {
    this.productCount = productCount;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

}
