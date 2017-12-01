package com.digiwin.ecims.ontime.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.digiwin.ecims.core.exception.GenericBusinessException;
import com.digiwin.ecims.ontime.enumvar.TaskExceTypeEnum;
import com.digiwin.ecims.ontime.enumvar.TaskGroupTypeEnum;
import com.digiwin.ecims.ontime.enumvar.TaskRunStatusEnum;
import com.digiwin.ecims.ontime.model.LoginUser;
import com.digiwin.ecims.ontime.model.OntimeTask;
import com.digiwin.ecims.ontime.service.OnTimeTaskBusiJob;
import com.digiwin.ecims.ontime.service.OnTimeTaskCheckBusiJob;
import com.digiwin.ecims.ontime.service.OnTimeTaskService;
import com.digiwin.ecims.ontime.service.SchedulerFactoryIntf;
import com.digiwin.ecims.ontime.util.InetAddressTool;

/**
 * 功能：从数据库中读取定时任务,动态设置 SchedulerFactoryBean.triggers列表
 * 
 * 在Spring配置文件进行配置该 SchedulerFactoryDynamic （单例）， 只需要注入: group , onTimeTaskService
 * 
 * 如： 所在的运行环境配置：
 * <bean id="schedulerFactoryDynamic" class= "dcms.ebridge.web.core.quartz.SchedulerFactoryDynamic"
 * scope="singleton"> <property name="quartzPropertiesFileName"><value>quartz4Platform.properties
 * </value></property> <property name="group"><value>1</value></property>
 * <property name="onTimeTaskService" ref="onTimeTaskService" /> </bean>
 * 
 * @author aibozeng
 *
 */

public class SchedulerFactoryDynamic implements ApplicationContextAware, SchedulerFactoryIntf {
  private static final Logger logger = LoggerFactory.getLogger(SchedulerFactoryDynamic.class);
  /**
   * 环境分组
   */
  private String group = TaskGroupTypeEnum.Platform.getCode();

  /**
   * 运行调度器的服务器的IP地址
   */
  private String runIP = null;

  /**
   * Quartz 作业类, JVM下多少个 StdSchedulerFactory, 也许就应该至少多少个 job
   */
  private String jobClassName = "JobExecuter1";

  /**
   * 根据 Spring配置文件的 jobClassName 来加载
   */
  private Class jobClass;

  /**
   * 读取定时任务列表的数据库EJB
   */
  @Autowired
  private OnTimeTaskService onTimeTaskService;

  /**
   * Spring的上下文
   */
  private ApplicationContext applicationContext;

  // 得到可用客户端处理调度程序的工厂
  private SchedulerFactory schedulerFactory = null;
  private Scheduler scheduler = null;

  /**
   * 同一个JVM里，不同的WEB应用，必须定义不同的实例名称
   */
  private String quartzPropertiesFileName = "quartz4Platform.properties";

  public SchedulerFactoryDynamic() {}

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#initScheduler()
   */
  @Override
  @SuppressWarnings("unchecked")
  public void initScheduler() {
    try {
      if (schedulerFactory == null || scheduler == null || scheduler.isShutdown()) {
        try {
          schedulerFactory = new StdSchedulerFactory(quartzPropertiesFileName);
          scheduler = schedulerFactory.getScheduler();
          // jobClass =
          // JobExecuter.class.getClassLoader().loadClass(jobClassName);
          // // java.lang.ClassNotFoundException: JobExecuter1
          if (group.equals("1")) {
            jobClass = JobExecuter1.class;
          } else {
            jobClass = JobExecuter2.class;
          }
        } catch (SchedulerException e) {
          e.printStackTrace();
          logger.error(e.getMessage());
        }
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
      logger.error(e.getMessage());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#initOnTimeTask()
   */
  @Override
  public void initOnTimeTask() {
    // 获取当前服务器的ip
    runIP = InetAddressTool.getLocalIpv4();
    logger.info("IP地址:{}", runIP);
    // 声明一个list的task列表
    List<OntimeTask> tasks = null;
    try {
      tasks = onTimeTaskService.findAllActiveTaskByGroupAndIP(new LoginUser(), group, runIP);
      if (tasks == null) {
        return;
      }
      // 初始化scheduler运行容器，使用SchedulerFactory创建Scheduler实例
      initScheduler();
      if (scheduler == null) {
        logger.warn("Quartz Schedule init fail.");
        return;
      }
      // 循环task任务实体，
      for (OntimeTask taskDb : tasks) {
        logger.info("find activeTask:{},{}", taskDb.getCode(), taskDb.getName());
        // 过滤不符合条件的task任务
        if (taskDb.getCronVal() == null || taskDb.getCronVal().length() <= 0) {
          continue;
        }

        // 根据数据库定义的名称，取出任务真正执行对象实例
        /**
         * 定时任务业务执行者的接口 在 OntimeTask实体里声明的 @Service,@EJB 都必须实现该接口
         */
        OnTimeTaskBusiJob busiJob = null;
        try {
          // 如果是spring的service执行方式
          if (TaskExceTypeEnum.SpringService.getCode().equals(taskDb.getExceType())) {
            // spring service
            busiJob = (OnTimeTaskBusiJob) applicationContext.getBean(taskDb.getExceName());
          }
          /*// 如果是SpringService4ECFX，表示是淘宝等各个平台分销专用
          else if (TaskExceTypeEnum.SpringService4ECFX.getCode().equals(taskDb.getExceType())) {
            ECFXBusiJobExecService ecfxJob =
                applicationContext.getBean(ECFXBusiJobExecService.class);
            ecfxJob.setScheduleType(taskDb.getCode());
            ecfxJob.setFxEcType(taskDb.getExceName());
            busiJob = ecfxJob;
          }*/ else if (TaskExceTypeEnum.CheckData.getCode().equals(taskDb.getExceType())) {
            // OnTimeTaskCheckBusiJob ecfxJob =
            // applicationContext.getBean(OnTimeTaskCheckBusiJob.class); // mark by mowj 20151110
            /*
             * 使用上方方法会导致：org.springframework.beans.factory.NoUniqueBeanDefinitionException 原因：
             * DataCheckScheduleServiceImpl和RefundDataCheckScheduleServiceImpl两个类，
             * 都实现了OnTimeTaskCheckBusiJob接口。
             * 所以在使用applicationContext.getBean方法时，如果使用OnTimeTaskCheckBusiJob这个类来获取bean，
             * 会获取到两个bean，无法唯一确认与区分，所以会无法加载。 这边需要参考“如果是spring的service执行方式”中的方法，使用bean的id去获取bean对象。
             */
            OnTimeTaskCheckBusiJob checkBusiJob =
                (OnTimeTaskCheckBusiJob) applicationContext.getBean(taskDb.getExceName()); // add by
                                                                                           // mowj
                                                                                           // 20151110
//            checkBusiJob.setScheduleType(taskDb.getCode());
            busiJob = checkBusiJob;
          } /*else if (TaskExceTypeEnum.EJB3.getCode().equals(taskDb.getExceType())) {
            // ejb
            busiJob = (OnTimeTaskBusiJob) applicationContext.getBean(taskDb.getExceName());
          } else if (TaskExceTypeEnum.SQL.getCode().equals(taskDb.getExceType())) {
            // SQL
            JdbcJobExecService jdbcJob =
                (JdbcJobExecService) applicationContext.getBean(JdbcJobExecService.class);
            jdbcJob.setSql(taskDb.getExceName());
            busiJob = jdbcJob;
          } */else {
            // 其他不支持
            continue;
          }
        } catch (NoSuchBeanDefinitionException nsbde) {
        }
        if (busiJob == null) {
          logger.error("busiJob:{} not found.", taskDb.getExceName());
          continue;
        }

        // 针对每一个task,构造每一个触发器（必须是新实例，不能重用） 定时规则表达式
        // 触发器必须有 name(唯一性) , groupName(本系统仅一个)
        CronTrigger trigger = null;
        try {
          // taskDb.getDataSource()可认为是任务的group
          // 一下方法已经过时，depreciation，使用新的方式获取trigger
          // trigger = new
          // CronTriggerImpl(taskDb.getCode(),taskDb.getDataSource(),taskDb.getCronVal());
          CronScheduleBuilder scheduleBuilder =
              CronScheduleBuilder.cronSchedule(taskDb.getCronVal());
          trigger =
              TriggerBuilder.newTrigger().withIdentity(taskDb.getCode(), taskDb.getDataSource())
                  .withSchedule(scheduleBuilder).build();
        } catch (Exception e) {
          logger.error("job:{} error cron: {}", taskDb.getExceName(), taskDb.getCronVal());
          continue;
        }

        // 通过 触发器的 Map 传递真正的任务执行者,当然也可以放置一些其他参数
        // trigger.getJobDataMap().put(JobExecuter.busiJobkeyName,job);
        // 2011-06-27 aibozeng : 不再通过 JobDataMap 传递 真正执行业务的对象,而是通过
        // JobDetailImpl

        // 定时执行者的 一些基本信息 -- Quartz需要的
        JobDetailImpl jobDetail =
            new JobDetailImpl(taskDb.getCode(), taskDb.getDataSource(), jobClass);
        jobDetail.setDescription(taskDb.getName());
        jobDetail.setBusiJob(busiJob);

        // 将准备好的定时规则和执行者放入计划管理器中
        // logger.debug(sched);//只有一个实例
        scheduler.scheduleJob(jobDetail, trigger);// 可以放入多个触发器和对应的作业
        logger.debug("add one trigger:{}, cron={}", taskDb.getCode(), taskDb.getCronVal());
      }
      // 最后统一运行--由web Context 启动
      // sched.start();
    } catch (GenericBusinessException e) {
      e.printStackTrace();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  ///////////////////////////////////////////// 以下是管理接口,提供给 定时作业的WEB管理调用
  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#findAllSchedulers()
   */
  @Override
  public Collection<Scheduler> findAllSchedulers() {
    initScheduler();
    try {
      return schedulerFactory.getAllSchedulers();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#findAllTrigger()
   */
  @Override
  public List<Trigger> findAllTrigger() {
    List<Trigger> triggers = new ArrayList<Trigger>();
    Collection<Scheduler> schedulerColl = findAllSchedulers();
    if (schedulerColl.isEmpty()) {
      return triggers;
    }
    Iterator<Scheduler> scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      Set<TriggerKey> triggerKeySet;
      try {
        for (String gName : sche.getTriggerGroupNames()) {
          GroupMatcher<TriggerKey> keyMatcher = GroupMatcher.groupEquals(gName);
          triggerKeySet = sche.getTriggerKeys(keyMatcher);
          Iterator<TriggerKey> triggerKeyIter = triggerKeySet.iterator();
          while (triggerKeyIter.hasNext()) {
            TriggerKey trigerKey = triggerKeyIter.next();
            triggers.add(sche.getTrigger(trigerKey));
          }
        }
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return triggers;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#pauseTrigger(java.lang. String,
   * java.lang.String)
   */
  @Override
  public boolean pauseTrigger(String triggerName, String triggerGroupName) {
    boolean retB = false;
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return retB;
    }
    Iterator scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        TriggerKey trigerKey = new TriggerKey(triggerName, triggerGroupName);
        sche.pauseTrigger(trigerKey);
        retB = true;
        break;
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return retB;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#pauseTask(dcms.ebridge.
   * esb.entity.OntimeTask)
   */
  @Override
  public boolean pauseTask(OntimeTask ontimeTask) {
    return pauseTrigger(ontimeTask.getCode(), ontimeTask.getDataSource());
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#startTrigger(java.lang. String,
   * java.lang.String)
   */
  @Override
  public boolean startTrigger(String triggerName, String triggerGroupName) {
    logger.debug("startTrigger:" + triggerName + "," + triggerGroupName);
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return false;
    }
    Iterator scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        TriggerKey trigerKey = new TriggerKey(triggerName, triggerGroupName);
        sche.resumeTrigger(trigerKey);
        break;
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return true;
  }

  @Override
  public boolean deleteTrigger(String triggerName, String triggerGroupName) {
    logger.debug("startTrigger:" + triggerName + "," + triggerGroupName);
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return false;
    }
    Iterator scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        JobKey jobKey = JobKey.jobKey(triggerName, triggerGroupName);
        sche.deleteJob(jobKey);
        break;
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#resumeTask(dcms.ebridge.
   * esb.entity.OntimeTask)
   */
  @Override
  public boolean resumeTask(OntimeTask ontimeTask) {
    return startTrigger(ontimeTask.getCode(), ontimeTask.getDataSource());
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#callJob(java.lang. String,
   * java.lang.String)
   */
  @Override
  public boolean callJob(String jobName, String jobGroup) throws Exception {
    boolean executeRet = false;
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return executeRet;
    }
    Iterator scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetailImpl detail = (JobDetailImpl) sche.getJobDetail(jobKey);
        OnTimeTaskBusiJob job = (OnTimeTaskBusiJob) detail.getBusiJob();
        if (job != null) {
          job.timeOutExecute();
          executeRet = true;
        }
        break;
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return executeRet;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#callJob(dcms.ebridge.esb
   * .entity.OntimeTask)
   */
  @Override
  public boolean callJob(OntimeTask ontimeTask) throws Exception {
    // return callJob(ontimeTask.getCode(),ontimeTask.getDataSource());
    // 根据数据库定义的名称，取出任务真正执行对象实例
    OnTimeTaskBusiJob busiJob = null;
    String taskCode = ontimeTask.getCode();
    try {
      if (TaskExceTypeEnum.SpringService.getCode().equals(ontimeTask.getExceType())) {
        // spring service
        busiJob = (OnTimeTaskBusiJob) applicationContext.getBean(ontimeTask.getExceName());
      }
/*      // 如果是SpringService4ECFX，表示是淘宝等各个平台分销专用
      else if (TaskExceTypeEnum.SpringService4ECFX.getCode().equals(ontimeTask.getExceType())) {
        ECFXBusiJobExecService ecfxJob = applicationContext.getBean(ECFXBusiJobExecService.class);
        ecfxJob.setScheduleType(ontimeTask.getCode());
        ecfxJob.setFxEcType(ontimeTask.getExceName());
        busiJob = ecfxJob;
      }*/ else if (TaskExceTypeEnum.CheckData.getCode().equals(ontimeTask.getExceType())) {
        // OnTimeTaskCheckBusiJob ecfxJob =
        // applicationContext.getBean(OnTimeTaskCheckBusiJob.class); // mark by mowj 20151110
        // add by mowj 20151110
        OnTimeTaskCheckBusiJob checkBusiJob =
            (OnTimeTaskCheckBusiJob) applicationContext.getBean(ontimeTask.getExceName()); 
        busiJob = checkBusiJob;
      }/* else if (TaskExceTypeEnum.EJB3.getCode().equals(ontimeTask.getExceType())) {
        // ejb
        busiJob = (OnTimeTaskBusiJob) applicationContext.getBean(ontimeTask.getExceName());
      } else if (TaskExceTypeEnum.SQL.getCode().equals(ontimeTask.getExceType())) {
        // SQL
        JdbcJobExecService jdbcJob =
            (JdbcJobExecService) applicationContext.getBean(JdbcJobExecService.class);
        jdbcJob.setSql(ontimeTask.getExceName());
        busiJob = jdbcJob;
      } */else {
        // 其他不支持
      }
    } catch (NoSuchBeanDefinitionException nsbde) {
      return false;
    }
    if (busiJob == null) {
      logger.debug("busiJob:{} not found.", ontimeTask.getExceName());
      return false;
    } else {
//      busiJob.timeOutExecute();
      busiJob.timeOutExecute(taskCode);
      return true;
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#findTriggerStatus(dcms.
   * ebridge.esb.entity.OntimeTask)
   */
  @Override
  public String findTriggerStatus(OntimeTask ontimeTask) {
    String statusRet = TaskRunStatusEnum.NO_LOAD.getCode();
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return statusRet;
    }
    Iterator scheIter = schedulerColl.iterator();
    TriggerState triggerState = Trigger.TriggerState.NONE;
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        TriggerKey trigerKey = new TriggerKey(ontimeTask.getCode(), ontimeTask.getDataSource());
        triggerState = sche.getTriggerState(trigerKey);
        if (triggerState == Trigger.TriggerState.NONE) {
          // 继续查找
          continue;
        }
        break;
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    if (triggerState == Trigger.TriggerState.PAUSED) {
      statusRet = TaskRunStatusEnum.PAUSED.getCode();
    } else if (triggerState == TriggerState.NONE) {
      statusRet = TaskRunStatusEnum.NO_LOAD.getCode();
    } else {
      statusRet = TaskRunStatusEnum.RUNNING.getCode();
    }
    return statusRet;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#startScheduler()
   */
  @Override
  public boolean startScheduler() {
    try {
      if (scheduler.isInStandbyMode()) {
        // 待机状态才启动
        scheduler.start();
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#startAllScheduler()
   */
  @Override
  public boolean startAllScheduler() {
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return false;
    }
    Iterator scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        if (sche.isInStandbyMode()) {
          // 待机状态才启动
          sche.start();
        }
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#stopScheduler()
   */
  @Override
  public boolean stopScheduler() {
    try {
      if (scheduler == null) {
        return true;
      }
      if (!scheduler.isInStandbyMode()) {
        // 待机
        scheduler.standby();
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#stopAllScheduler()
   */
  @Override
  public boolean stopAllScheduler() {
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return false;
    }
    Iterator scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        if (!sche.isInStandbyMode()) {
          // 待机
          sche.standby();
        }
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#shutdownScheduler()
   */
  @Override
  public boolean shutdownScheduler() {
    try {
      // 立即停止所有作业
      if (!scheduler.isShutdown()) {
        logger.debug("scheduler.shutdown(false);");
        scheduler.shutdown(false);
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#shutdownAllScheduler()
   */
  @Override
  public boolean shutdownAllScheduler() {
    Collection schedulerColl = findAllSchedulers();
    if (schedulerColl == null || schedulerColl.isEmpty()) {
      return false;
    }
    Iterator scheIter = schedulerColl.iterator();
    while (scheIter.hasNext()) {
      Scheduler sche = (Scheduler) scheIter.next();
      try {
        // 立即停止所有作业
        if (!sche.isShutdown()) {
          sche.shutdown(false);
        }
      } catch (SchedulerException e) {
        e.printStackTrace();
        continue;
      }
    }
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#checkCronExpression(java .lang.String)
   */
  @Override
  public boolean checkCronExpression(String cronStr) {
    return CronExpression.isValidExpression(cronStr);
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#getGroup()
   */
  @Override
  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public OnTimeTaskService getonTimeTaskService() {
    return onTimeTaskService;
  }

  public void setonTimeTaskService(OnTimeTaskService onTimeTaskService) {
    this.onTimeTaskService = onTimeTaskService;
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  public SchedulerFactory getSchedulerFactory() {
    return schedulerFactory;
  }

  public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
    this.schedulerFactory = schedulerFactory;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf# getQuartzPropertiesFileName()
   */
  @Override
  public String getQuartzPropertiesFileName() {
    return quartzPropertiesFileName;
  }

  public void setQuartzPropertiesFileName(String quartzPropertiesFileName) {
    this.quartzPropertiesFileName = quartzPropertiesFileName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see dcms.ebridge.ontime.service.SchedulerFactoryIntf#getJobClassName()
   */
  @Override
  public String getJobClassName() {
    return jobClassName;
  }

  public void setJobClassName(String jobClassName) {
    this.jobClassName = jobClassName;
  }

  public String getRunIP() {
    if (this.runIP == null) {
      this.runIP = InetAddressTool.getLocalIpv4(); // add by xavier on 20151110
    }
    return this.runIP;
    // return runIP;
  }

  public void setRunIP(String runIP) {
    this.runIP = runIP;
  }

  // @Override
  // public void afterPropertiesSet() throws Exception {
  // initOnTimeTask();
  // }

}
