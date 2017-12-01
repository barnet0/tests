package com.digiwin.ecims.platforms.taobao.service.translator.item;

import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.domain.Item;
import com.taobao.api.response.ItemSellerGetResponse;

import com.digiwin.ecims.core.model.AomsitemT;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;

public interface TaobaoJdpTbItemService {

  /**
   * 将从聚石塔取回的响应结果转换为中台商品类型aomsitemt
   * @param taobaoRes
   * @param storeId
   * @return 中台商品List
   * @throws ApiException
   */
  List<AomsitemT> parseTaobaoResToAomsitemT(TaobaoRes taobaoRes, String storeId) 
      throws ApiException;

  /**
   * 将ItemSellerGetResponse类型转换为中台商品类型aomsitemt
   * @param itemSellerGetResponse
   * @param storeId
   * @return 中台商品List
   * @throws ApiException
   */
  List<AomsitemT> parseItemGetResponseToAomsitemT(
      ItemSellerGetResponse itemSellerGetResponse, String storeId) ;
  
  /**
   * 将Item类型转换为中台商品类型aomsitemt
   * @param item
   * @param storeId
   * @return 中台商品List
   * @throws ApiException
   */
  List<AomsitemT> parseItemToAomsitemT(Item item, String storeId) ;
}
