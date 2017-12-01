package com.digiwin.ecims.platforms.baidu.bean.merchant.client.api;

import com.fasterxml.jackson.core.type.TypeReference;

import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfo.GetItemInfoRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfo.GetItemInfoResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos.GetItemInfosRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.getiteminfos.GetItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.grouping.GroupingRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.grouping.GroupingResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.off.OffRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.off.OffResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos.QueryItemInfosRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.queryiteminfos.QueryItemInfosResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.UpdateStockRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.UpdateStockResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.batch.BatchUpdateStockRequest;
import com.digiwin.ecims.platforms.baidu.bean.merchant.api.item.updatestock.batch.BatchUpdateStockResponse;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Request;
import com.digiwin.ecims.platforms.baidu.bean.merchant.client.model.Response;
import com.digiwin.ecims.core.util.JsonUtil;

public class MyItemService extends MyAbstractApiService {

  private String url;

  public void setUrl(String url) {
    this.url = url;
  }

  public MyItemService(String url) {
    super();
    this.url = url;
  }

  public MyItemService() {}

  /**
   * 获取商品列表
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<QueryItemInfosResponse> queryItemInfos(Request<QueryItemInfosRequest> air)
      throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<QueryItemInfosResponse>>() {});
  }

  /**
   * 获取商品信息
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<GetItemInfoResponse> getItemInfo(Request<GetItemInfoRequest> air)
      throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<GetItemInfoResponse>>() {});
  }

  /**
   * 批量获取商品信息
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<GetItemInfosResponse> getItemInfos(Request<GetItemInfosRequest> air)
      throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<GetItemInfosResponse>>() {});
  }

  /**
   * 商品上架
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<GroupingResponse> grouping(Request<GroupingRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<GroupingResponse>>() {});
  }

  /**
   * 商品下架
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<OffResponse> off(Request<OffRequest> air) throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<OffResponse>>() {});
  }

  /**
   * 更新库存
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<UpdateStockResponse> updateStock(Request<UpdateStockRequest> air)
      throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<UpdateStockResponse>>() {});
  }

  /**
   * 批量更新库存
   * 
   * @param air
   * @return
   * @throws Exception
   */
  public Response<BatchUpdateStockResponse> batchUpdateStock(Request<BatchUpdateStockRequest> air)
      throws Exception {
    return JsonUtil.jsonToObject(postTo(this.url, air),
        new TypeReference<Response<BatchUpdateStockResponse>>() {});
  }
}
