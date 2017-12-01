package com.digiwin.ecims.platforms.taobao.service.translator.refund;

import java.io.IOException;
import java.util.List;

import com.taobao.api.ApiException;
import com.taobao.api.response.RefundGetResponse;

import com.digiwin.ecims.core.model.AomsrefundT;
import com.digiwin.ecims.platforms.taobao.model.TaobaoRes;

public interface TaobaoJdpTbRefundService {

  /**
   * 将TaoboaRes转换为List<AomsrefundT>
   * 
   * @param taobaoRes 聚石塔回传物件
   * @param storeId
   * @return List<AomsrefundT>
   * @throws ApiException
   */
  List<AomsrefundT> parseTaobaoResToAomsrefundT(TaobaoRes taobaoRes, String storeId) 
      throws ApiException;

  /**
   * 将refundGetResponse对象转换成中台的退单
   * 
   * @param refundGetResponse refundGetResponse实例
   * @param storeId
   * @return 中台退单List
   * @throws ApiException
   * @throws IOException
   */
  List<AomsrefundT> parseResponseToAomsrefundT(RefundGetResponse refundGetResponse, String storeId);

}
