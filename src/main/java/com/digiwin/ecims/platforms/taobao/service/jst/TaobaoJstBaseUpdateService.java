package com.digiwin.ecims.platforms.taobao.service.jst;

import com.digiwin.ecims.platforms.taobao.bean.jst.api.AbstractTaobaoJstApi;

/**
 * 此接口定义了中台电商接口与聚石塔Service交互获取资料的方法
 * @author 维杰
 *
 */
public interface TaobaoJstBaseUpdateService {

  /**
   * 执行与聚石塔Service交互获取资料
   * @param <T>
   * @param taobaoJstSyncService
   * @param scheduleTypeNamePrefix 排程名称前缀
   * @param clazz 业务类型。可以是{@link AomsordT},{@link AomsrefundT},{@link AomsitemT}的其中一种
   * @param getListApi 获得资料列表API枚举。参见{@link AbstractTaobaoJstApi}
   * @param getListByIdApi 根据资料ID排序获取资料API枚举。参见{@link AbstractTaobaoJstApi}
   * @param getCountApi 获得资料数量API枚举。参见{@link AbstractTaobaoJstApi}
   * @param getCountByIdApi 根据资料ID排序获取资料数量API枚举。参见{@link AbstractTaobaoJstApi}
   * @param storeType 平台编号
   * @return
   * @throws Exception
   */
  public <T> boolean timeOutExecute(
      TaobaoJstSyncService taobaoJstSyncService,
      String scheduleTypeNamePrefix, Class<T> clazz, 
      AbstractTaobaoJstApi getListApi, 
      AbstractTaobaoJstApi getListByIdApi, 
      AbstractTaobaoJstApi getCountApi, 
      AbstractTaobaoJstApi getCountByIdApi, String storeType) throws Exception;
  
}