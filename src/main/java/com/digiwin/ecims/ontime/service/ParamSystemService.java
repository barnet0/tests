package com.digiwin.ecims.ontime.service;

import java.util.List;

import com.digiwin.ecims.ontime.model.SystemParam;
import com.digiwin.ecims.system.enums.EcApiUrlEnum;
import com.digiwin.ecims.system.enums.EcApiValidListEnum;

/**
 * 系统参数的服务类
 * 
 * @author hopelee
 *
 */
public interface ParamSystemService {
  /**
   * 查找所有的系统参数
   * 
   * @return
   */
  public List<SystemParam> findAllSysParam();

  /**
   * 初始化系统参数
   * 
   * @return
   */
  public boolean initParamToCache();

  /**
   * 通过key获取系统参数
   * 
   * @param key
   * @return
   */
  public Object getSysParamByKey(String key);

  /**
   * 获取系统参数中设定的各平台的API调用URL
   * 
   * @param urlEnum
   * @return
   * @author zaregoto
   */
  public String getEcApiUrl(EcApiUrlEnum urlEnum);

  /**
   * 查找API是否在可用列表中
   * @param apiName 平台API名称
   * @param ecApiValidListEnum 列表参数key值
   * @return
   */
  public boolean isApiInValidList(String apiName, EcApiValidListEnum ecApiValidListEnum);
}
