package com.digiwin.ecims.platforms.taobao.service.jst;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.taobao.api.ApiException;

import com.digiwin.ecims.platforms.taobao.bean.jst.PostBean;

/**
 * 淘宝、天猫平台聚石塔同步服务
 * @author 维杰
 *
 */
public interface TaobaoJstSyncService {
  
  static final Logger logger = LoggerFactory.getLogger(TaobaoJstSyncService.class);
  
  /**
   * 获取聚石塔单笔资料。
   * @param url
   * @param jstPostBean
   * @param clazz
   * @return
   * @throws ApiException
   * @throws IOException
   */
  <T> T saveWithGetDetail(String url, PostBean jstPostBean, Class<T> clazz) 
      throws ApiException, IOException;
  
  /**
   * 批量获取并保存聚石塔资料，按时间排序.<br>
   * @param url
   * @param jstPostBean
   * @param clazz
   * @return 修改时间最大的一笔资料
   * @throws ApiException
   * @throws IOException
   */
  <T> T saveWithGetList(String url, PostBean jstPostBean, Class<T> clazz) 
      throws ApiException, IOException;
  
  /**
   * 批量获取并保存聚石塔资料，按时间、资料ID排序.<br>
   * 通常用在一秒内数量特别大时取资料回来，避免遗漏.<br>
   * @param url
   * @param jstPostBean
   * @param clazz
   * @return 修改时间最大的一笔资料
   * @throws ApiException
   * @throws IOException
   */
  <T> T saveWithGetListById(String url, PostBean jstPostBean, Class<T> clazz) 
      throws ApiException, IOException;
  
  /**
   * 根据自定义聚石塔API，取得最新一笔资料的更新时间。
   * @param url
   * @param jstPostData 聚石塔API参数.资料排序：按jdp_modified栏位升序
   * @return 最新一笔资料的更新时间
   * @throws ApiException
   * @throws IOException
   */
  String getLastDataUpdateTimeByApi(String url, PostBean jstPostData)
      throws ApiException, IOException;

  /**
   * 取得区间内资料的笔数
   * @param url
   * @param jstPostData 聚石塔参数
   * @return 区间内资料笔数
   * @throws IOException
   */
  Long getCount(String url, PostBean jstPostData)
      throws IOException;
  
  /**
   * 取得区间内资料的笔数.<br>
   * 通常用于一秒内含有许多资料时使用.<br>
   * @param url
   * @param jstPostData 聚石塔参数
   * @return 区间内资料笔数
   * @throws IOException
   */
  Long getCountById(String url, PostBean jstPostData)
      throws IOException;
  
//  /**
//   * 保存从聚石塔取回的业务资料，并返回修改时间最大的一笔资料
//   * 
//   * @param jstPostData 聚石塔API参数
//   * @param api API名称
//   * @return 修改时间最大的一笔资料
//   * @throws ApiException
//   * @throws ClientProtocolException
//   * @throws IOException
//   */
//  <T> T newTransaction4SaveByApi(PostBean jstPostData, String api, Class<T> clazz)
//      throws ApiException, IOException, NullPointerException;

//  /**
//   * 保存从聚石塔取回的业务资料，并返回修改时间最大的一笔资料
//   * 
//   * @param jstPostData 聚石塔API参数
//   * @param api API名称
//   * @return 修改时间最大的一笔资料
//   * @throws ApiException
//   * @throws ClientProtocolException
//   * @throws IOException
//   */
//  Object newTransaction4SaveByApi(PostBean jstPostData, String api)
//      throws ApiException, ClientProtocolException, IOException, NullPointerException;
  
}
