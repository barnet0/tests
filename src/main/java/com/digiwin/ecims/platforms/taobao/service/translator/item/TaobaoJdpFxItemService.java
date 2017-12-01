package com.digiwin.ecims.platforms.taobao.service.translator.item;

import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.domain.FenxiaoProduct;
import com.taobao.api.response.FenxiaoProductsGetResponse;

import com.digiwin.ecims.core.model.AomsitemT;

public interface TaobaoJdpFxItemService {

  List<AomsitemT> parseResponseToAomsitemT(
      FenxiaoProductsGetResponse fenxiaoProductsGetResponse, String storeId) 
          throws NullPointerException, ApiException;
  
  List<AomsitemT> parseFenxiaoProductToAomsitemT(FenxiaoProduct fenxiaoProduct,
      String storeId) throws ApiException, NullPointerException;
}
