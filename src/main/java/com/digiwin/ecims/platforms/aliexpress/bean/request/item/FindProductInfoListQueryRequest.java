package com.digiwin.ecims.platforms.aliexpress.bean.request.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digiwin.ecims.platforms.aliexpress.bean.request.base.AliExpressBaseRequest;
import com.digiwin.ecims.platforms.aliexpress.bean.response.item.FindProductInfoListQueryResponse;

public class FindProductInfoListQueryRequest extends AliExpressBaseRequest<FindProductInfoListQueryResponse> {

//  是   商品业务状态，目前提供4种，输入参数分别是：上架:onSelling ；下架:offline ；审核中:auditing ；审核不通过:editingRequired。    onSelling
  private String productStatusType;
  
//  否   商品标题模糊搜索字段。只支持半角英数字，长度不超过128。   knew odd
  private String subject;
  
//  否   商品分组搜索字段。输入商品分组id(groupId). 124
  private Integer groupId;
  
//  否   商品下架原因：expire_offline(过期下架)，user_offline(用户手工下架)、violate_offline(违规下架)、punish_offline(处罚下架)、degrade_offline(降级下架)、industry_offline(行业准入未续约下架)   expire_offline
  private String wsDisplay;
  
//  否   商品的剩余有效期。如果想查3天之内即将下架的商品，则offLineTime值为3。   7
  private Integer offLineTime;
  
//  否   商品id搜索字段。   123
  private Long productId;
  
//  否   待排除的产品ID列表。 [123,456]
  private List<Long> exceptedProductIds;
  
//  否   每页查询商品数量。输入值小于100，默认20。 30
  private Integer pageSize;
  
//  否   需要商品的当前页数。默认第一页。    2
  private Integer currentPage;
  
  public String getProductStatusType() {
    return productStatusType;
  }

  public void setProductStatusType(String productStatusType) {
    this.productStatusType = productStatusType;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

  public String getWsDisplay() {
    return wsDisplay;
  }

  public void setWsDisplay(String wsDisplay) {
    this.wsDisplay = wsDisplay;
  }

  public Integer getOffLineTime() {
    return offLineTime;
  }

  public void setOffLineTime(Integer offLineTime) {
    this.offLineTime = offLineTime;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public List<Long> getExceptedProductIds() {
    return exceptedProductIds;
  }

  public void setExceptedProductIds(List<Long> exceptedProductIds) {
    this.exceptedProductIds = exceptedProductIds;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  @Override
  protected String getApiName() {
    return "api.findProductInfoListQuery";
  }

  @Override
  public Map<String, String> getApiParams() {
    Map<String, String> apiParams = new HashMap<String, String>();
    if (getProductStatusType() != null) {
      apiParams.put("productStatusType", getProductStatusType());
    }
    if (getSubject() != null) {
      apiParams.put("subject", getSubject());
    }
    if (getGroupId() != null) {
      apiParams.put("groupId", getGroupId() + "");
    }
    if (getWsDisplay() != null) {
      apiParams.put("wsDisplay", getWsDisplay());
    }
    if (getOffLineTime() != null) {
      apiParams.put("offLineTime", getOffLineTime() + "");
    }
    if (getProductId() != null) {
      apiParams.put("productId", getProductId() + "");
    }
    if (getExceptedProductIds() != null) {
      // TODO
//      apiParams.put("exceptedProductIds", get);
    }
    if (getPageSize() != null) {
      apiParams.put("pageSize", getPageSize() + "");
    }
    if (getCurrentPage() != null) {
      apiParams.put("currentPage", getCurrentPage() + "");
    }
    
    return apiParams;
  }

  @Override
  public Class<FindProductInfoListQueryResponse> getResponseClass() {
    return FindProductInfoListQueryResponse.class;
  }

}
