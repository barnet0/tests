package com.digiwin.ecims.platforms.taobao.service.translator.trade;

import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.response.FenxiaoOrdersGetResponse;

import com.digiwin.ecims.core.model.AomsordT;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;

public interface TaobaoJdpFxTradeService {

  /**
   * 将TaobaoRes转换成List<AomsordT>
   * 
   * @param taobaoRes 从聚石塔获取的资料
   * @param storeId
   * @return 中台订单List
   * @throws IOException
   * @throws ApiException
   */
  List<AomsordT> parseTaobaoResToAomsordT(TaobaoRes taobaoRes, String storeId) 
      throws ApiException;

  /**
   * 将FenxiaoOrdersGetResponse对象转换成中台的订单
   * 
   * @param fullinfoGetResponse FenxiaoOrdersGetResponse实例
   * @param storeId
   * @return 中台订单List
   * @throws ApiException
   * @throws IOException
   */
  List<AomsordT> parseResponseToAomsordT(FenxiaoOrdersGetResponse fenxiaoOrdersGetResponse, String storeId)
      throws ApiException;
}
