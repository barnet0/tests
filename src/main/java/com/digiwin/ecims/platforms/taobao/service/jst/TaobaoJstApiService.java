package com.digiwin.ecims.platforms.taobao.service.jst;

import java.io.IOException;

import com.digiwin.ecims.platforms.taobao.bean.jst.PostBean;

/**
 * 淘宝聚石塔资料转发程序通讯接口
 * @author 维杰
 *
 */
public interface TaobaoJstApiService {

  /**
   * 从聚石塔获取响应数据
   * @param jstPostData
   * @param api
   * @return
   * @throws IOException
   */
  String getResponseFromJst(PostBean jstPostData, String api)
      throws IOException;
  
  Long getCountByApi(PostBean jstPostData, String api)
      throws IOException;
}
