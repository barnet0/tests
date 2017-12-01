package com.digiwin.ecims.ontime.service;


/**
 * 定时任务业务执行者的接口
 * 
 * 在 OntimeTask实体里声明的 @Service,@EJB 都必须实现该接口
 * 
 * @author aibozeng
 *
 */

public interface OnTimeTaskBusiJob {

  /**
   * 根据定时规则,时间点到达,执行此方法
   * @param args 参数
   * @return
   * @throws Exception
   */
  boolean timeOutExecute(String... args) throws Exception;

}

