package com.digiwin.ecims.ontime.service;

import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.system.enums.WorkOperateTypeEnum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 */
public interface TaskService {

  /**
   * 判断lastupdatetime是否与开始时间一样，如果一样，让lastupdatetime加一秒
   * 
   * @param scheduleType 认物名称
   * @param startDate 排程开始时间
   * @param lastUpdateTime 本次区间内取得的最后更新时间
   */
  public void updateLastUpdateTime(String scheduleType, String startDate, String lastUpdateTime);

  /**
   * 更更新最后更新时间
   * 
   * @param scheduleType 认物名称
   * @param lastUpdateTime 本次区间内取得的最后更新时间
   */
  void updateLastUpdateTime(String scheduleType, String lastUpdateTime);

  /**
   * 取得上次最后更新时间
   * 
   * @param scheduleType 认物名称
   * @return 上次最后更新时间
   */
  String getLastUpdateTime(String scheduleType);

  /**
   * 取得认物排成资讯
   * 
   * @param scheduleType 认物名称
   * @return 任務排程資訊
   */
  TaskScheduleConfig getTaskScheduleConfigInfo(String scheduleType);

  void newTransaction4SaveLastRunTime(String scheduleType, Date lastRunTime);

  void newTransaction4SaveShopIdAndRunType(String scheduleType, String shopId, String runType);

  // /**
  // * 依照传入参数取得本次推送资料的ID列表
  // *
  // * @param params
  // * 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT
  // * checkCol=created/modified/aomsmoddt storeType=0/1/2/3/4/5/A
  // * startDate='yyyy-MM-dd HH:mm:ss' endDate='yyyy-MM-dd HH:mm:ss'
  // *
  // * @return List<String> 要推送资料的ID列表
  // * @author 维杰
  // * @since 2015.10.09
  // */
  // @Deprecated
  // List<String> getSelectPojosIdNormally(HashMap<String, String> params);
  //
  // /**
  // * 依照传入的params参数与ID列表，通过关联原表取得需推送的资料
  // *
  // * @param params
  // * 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT
  // * checkCol=created/modified/aomsmoddt storeType=0/1/2/3/4/5/A
  // * startDate='yyyy-MM-dd HH:mm:ss' endDate='yyyy-MM-dd HH:mm:ss'
  // * @param idList 资料ID列表
  // * @param clazz
  // * T=AomsordT、AomsrefundT、AomditemT
  // * @return List<AomsordT、AomsrefundT、AomditemT>
  // * @author 维杰
  // * @since 2015.10.09
  // */
  // @Deprecated
  // <T> List<T> getSelectPojos(HashMap<String, String> params, List<String> idList, Class<T>
  // clazz);

  /**
   * 依照传入参数取得资料
   * 
   * @param params 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT checkCol=created/modified/aomsmoddt
   *        storeType=0/1/2/3/4/5/A startDate='yyyy-MM-dd HH:mm:ss' endDate='yyyy-MM-dd HH:mm:ss'
   * 
   * @param clazz T=AomsordT、AomsrefundT、AomditemT
   * @return List<AomsordT、AomsrefundT、AomditemT>
   * @throws SecurityException
   * @throws NoSuchFieldException
   */
  <T> List<T> getSelectPojos(Map<String, String> params, Class<T> clazz,
      WorkOperateTypeEnum workOpEnum) throws NoSuchFieldException, SecurityException;


  // /**
  // * 若一秒内资料超过maxPushData，则采用此方法，取得一秒內的資料ID列表
  // * @param params
  // * @return
  // * @author 维杰
  // * @since 2015.10.09
  // */
  // @Deprecated
  // List<String> getSelectPojosIdWhenDataMoreThanSettingInOneSecond(HashMap<String, String>
  // params);
  //
  // /**
  // * 若一秒内资料超过maxPushData，则采用此方法，取得一秒內的資料
  // *
  // * @param params
  // * 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT
  // * checkCol=created/modified/aomsmoddt
  // * id=Aomsordt.id/AomsrefundT.id/AomditemT.id
  // * storeType=0/1/2/3/4/5/A startDate='yyyy-MM-dd HH:mm:ss'
  // * endDate='yyyy-MM-dd HH:mm:ss'
  // * @param idList 要推送资料的ID列表
  // * @param clazz
  // * T=AomsordT、AomsrefundT、AomditemT
  // * @return List<AomsordT、AomsrefundT、AomditemT>
  // * @author 维杰
  // * @since 2015.10.10
  // */
  // @Deprecated
  // <T> List<T> getSelectPojosById(HashMap<String, String> params, List<String> idList, Class<T>
  // clazz);

  /**
   * 若一秒内资料超过maxPushData，则采用此方法，取得一秒內的資料
   * 
   * @param params 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT checkCol=created/modified/aomsmoddt
   *        id=Aomsordt.id/AomsrefundT.id/AomditemT.id storeType=0/1/2/3/4/5/A startDate='yyyy-MM-dd
   *        HH:mm:ss' endDate='yyyy-MM-dd HH:mm:ss'
   * 
   * @param clazz T=AomsordT、AomsrefundT、AomditemT
   * @return List<AomsordT、AomsrefundT、AomditemT>
   * @throws SecurityException
   * @throws NoSuchFieldException
   */
  <T> List<T> getSelectPojosById(Map<String, String> params, Class<T> clazz,
      WorkOperateTypeEnum workOpEnum) throws NoSuchFieldException, SecurityException;

  /**
   * 只依照單頭ID获取单个資料
   * 
   * @param params 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT checkCol=created/modified/aomsmoddt
   *        id=Aomsordt.id/AomsrefundT.id/AomditemT.id storeType=0/1/2/3/4/5/A startDate='yyyy-MM-dd
   *        HH:mm:ss' endDate='yyyy-MM-dd HH:mm:ss'
   * 
   * @param clazz T=AomsordT、AomsrefundT、AomditemT
   * @return List<AomsordT、AomsrefundT、AomditemT>
   */
  <T> List<T> getSelectPojoById(Map<String, String> params, Class<T> clazz);

  /**
   * 取得區間內資料筆數
   * 
   * @param params 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT checkCol=created/modified/aomsmoddt
   *        storeType=0/1/2/3/4/5/A startDate='yyyy-MM-dd HH:mm:ss' endDate='yyyy-MM-dd HH:mm:ss'
   * 
   * @param clazz T=AomsordT、AomsrefundT、AomditemT
   * @return List<AomsordT、AomsrefundT、AomditemT>
   */
  long getSelectPojoCount(Map<String, String> params);

  /**
   * 若一秒内资料超过maxPushData，取得區間內資料筆數
   * 
   * @param params 搜寻用的参数 pojo=Aomsordt/AomsrefundT/AomditemT checkCol=created/modified/aomsmoddt
   *        id=Aomsordt.id/AomsrefundT.id/AomditemT.id storeType=0/1/2/3/4/5/A startDate='yyyy-MM-dd
   *        HH:mm:ss' endDate='yyyy-MM-dd HH:mm:ss'
   * 
   * @param clazz T=AomsordT、AomsrefundT、AomditemT
   * @return List<AomsordT、AomsrefundT、AomditemT>
   */
  long getSelectPojoCountById(Map<String, String> params);

  void saveTaskTaskScheduleConfig(TaskScheduleConfig taskScheduleConfig);

  public <T> String newTransaction4Save(Date modiDate, List<T> objList);

  public <T> void newTransaction4Save(List<T> objList);

  /**
   * 存檔或更新
   * 
   * @param objList 資料
   * @param isClean 是否將session中的物件清空(解決a different object with the same identifier value was already
   *        associated with the session錯誤)
   */
  public <T> void newTransaction4Save(List<T> objList, boolean isClean);

  /**
   * 按hql查询
   * 
   * @param hql
   * @return
   */
  public <T> List<T> executeQueryByHql(String hql);

}
