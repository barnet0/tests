package com.digiwin.ecims.ontime.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;

import com.digiwin.ecims.ontime.model.TaskScheduleConfig;
import com.digiwin.ecims.system.enums.CheckColEnum;
import com.digiwin.ecims.system.enums.WorkOperateTypeEnum;


/**
 * 推送服务基础接口，提供推送执行方法。参考现行OnTimeTaskBusiJob接口，保持现有定时任务接口的架构。
 * 推送方法timeOutExecute以及它的多个重载通过不同平台的具体实现类来调用
 * 
 * @author 维杰
 * @since 2015.10.10
 */
public interface BasePostService {

  /**
   * 默认的推送排程执行方法。 使用SystemWorkOperateTypeEnum=IS_SCHEDULE,param.checkcol=aomsmoddt,
   * startDate=taskScheduleConfig.getLastUpdateTime,endDate=new Date()作为参数
   * 
   * @param <T>
   * @param taskScheduleConfig 排程设定实例
   * @param clazz Model类型
   * @return 推送的资料单头数目
   * @throws Exception
   * @author 维杰
   * @since 2015.10.12
   */
  public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz)
      throws Exception;

  /**
   * 重载的推送排程执行方法。使用操作类型与资料过滤条件栏位作为附加参数
   * 
   * @param <T>
   * @param taskScheduleConfig 排程设定实例
   * @param clazz Model类型
   * @param workOpEnum 操作的类型（包括同步与推送）：有Manually(手动执行)、Schedule(普通排程定时执行)、CheckSchedule(检查排程定时执行)
   * @param checkColEnum 推送资料时的过滤条件栏位
   * @return 推送的资料单头数目
   * @throws Exception
   * @author 维杰
   * @since 2015.10.11
   */
  public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz,
      WorkOperateTypeEnum workOpEnum, CheckColEnum checkColEnum) throws Exception;

  /**
   * 重载的推送方法。使用操作类型、资料过滤条件栏位、时间范围起点与时间范围终点作为附加参数
   * 
   * @param taskScheduleConfig 排程设定实例
   * @param clazz Model类型
   * @param workOpEnum 操作的类型（包括同步与推送）：有Manually(手动执行)、Schedule(普通排程定时执行)、CheckSchedule(检查排程定时执行)
   * @param checkColEnum 推送资料时的过滤条件栏位
   * @param startDate 推送资料的时间范围起点
   * @param endDate 推送资料的时间范围截止点
   * @return 推送的资料单头数目
   * @throws Exception
   * @author 维杰
   * @since 2015.10.12
   */
  public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz,
      WorkOperateTypeEnum workOpEnum, CheckColEnum checkColEnum, String startDate, String endDate)
          throws Exception;

  /**
   * 重载的推送方法。使用操作类型、资料过滤条件栏位、时间范围起点与时间范围终点作为附加参数
   * 
   * @param taskScheduleConfig 排程设定实例
   * @param clazz Model类型
   * @param workOpEnum 操作的类型（包括同步与推送）：有Manually(手动执行)、Schedule(普通排程定时执行)、CheckSchedule(检查排程定时执行)
   * @param checkColEnum 推送资料时的过滤条件栏位
   * @param startDate 推送资料的时间范围起点
   * @param endDate 推送资料的时间范围截止点
   * @param storeId 店铺ID
   * @return 推送的资料单头数目
   * @throws Exception
   * @author 维杰
   * @since 2015.10.14
   */
  public <T> long timeOutExecute(TaskScheduleConfig taskScheduleConfig, Class<T> clazz,
      WorkOperateTypeEnum workOpEnum, CheckColEnum checkColEnum, String startDate, String endDate,
      String storeId) throws Exception;

  /**
   * 根据资料ID来推送。
   * 
   * @param taskScheduleConfig 排程设定实例
   * @param id 资料的ID
   * @param clazz Model类型
   * @return
   * @throws Exception
   * @author 维杰
   * @since 2015.10.15
   */
  public <T> boolean timeOutExecuteById(TaskScheduleConfig taskScheduleConfig, String id,
      Class<T> clazz) throws Exception;

  /**
   * 执行推送排程的所有线程
   * 
   * @param taskScheduleConfig 排程设定
   * @param dataList 资料List
   * @param Clazz 资料类型
   * @throws IOException
   * @throws RejectedExecutionException
   * @throws NullPointerException
   * @throws InterruptedException
   */
  public <T> void doPost(TaskScheduleConfig taskScheduleConfig, List<T> dataList, Class<T> clazz)
      throws IOException, RejectedExecutionException, NullPointerException, InterruptedException,
      ExecutionException;

  /**
   * 重新推送失败的推送
   * 
   * @param taskScheduleConfig
   * @param dataList
   * @param Clazz
   * @throws IOException
   * @throws RejectedExecutionException
   * @throws NullPointerException
   * @throws InterruptedException
   */
  public <T> void doRePost(TaskScheduleConfig taskScheduleConfig, List<T> dataList, Class<T> clazz)
      throws IOException, RejectedExecutionException, NullPointerException, InterruptedException;
}
