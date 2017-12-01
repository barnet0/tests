package com.digiwin.ecims.ontime.service;

import java.util.Collection;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.Trigger;

import com.digiwin.ecims.ontime.model.OntimeTask;

public interface SchedulerFactoryIntf {

	@SuppressWarnings("unchecked")
	public abstract void initScheduler();

	/**
	 * 初始化定时任务
	 * (1)根据分组条件，从数据库找出定时任务列表
	 * (2)构造定时作业
	 * 注：暂不启动,由web context listener 启动
	 */
	public abstract void initOnTimeTask();

	///////////////////////////////////////////// 以下是管理接口,提供给 定时作业的WEB管理调用
	/**
	 * 取出调度器里所有调度器 --- 在同一个JVM里的
	 * 本系统应该只有一个
	 */
	public abstract Collection<Scheduler> findAllSchedulers();

	/**
	 * 取出调度器里所有触发器.应该等于 T_OntimeTask 的有效状态的列表
	 * @return
	 */
	public abstract List<Trigger> findAllTrigger();

	/**
	 * 暂停某个触发器==定时作业
	 * @param triggerName
	 * @param triggerGroupName
	 * @return
	 */
	public abstract boolean pauseTrigger(String triggerName,
			String triggerGroupName);

	public abstract boolean pauseTask(OntimeTask ontimeTask);

	/**
	 * 重新启动某个触发器==定时作业 , 并不是立即调用job。
	 * @param triggerName
	 * @param triggerGroupName
	 * @return
	 */
	public abstract boolean startTrigger(String triggerName,
			String triggerGroupName);

	public abstract boolean resumeTask(OntimeTask ontimeTask);

	/**
	 * 立即调用某个作业 
	 * 从调度器里直接调用    数据库配置不可用，调度器不加载，不能调用
	 * @param taskCode  = jobGroup , dataSource  =jobGroup
	 * @return
	 */
	public abstract boolean callJob(String jobName, String jobGroup)throws Exception;

	/**
	 * 立即调用某个作业  数据库配置不可用，调度器不加载，也能调用
	 * 直接调用 Service, 不考虑调度器状态
	 * @param ontimeTask
	 * @return
	 */
	public abstract boolean callJob(OntimeTask ontimeTask)throws Exception;

	public abstract String findTriggerStatus(OntimeTask ontimeTask);

	/**
	 * 启动本环境的调度器
	 * @return
	 */
	public abstract boolean startScheduler();

	/**
	 * 启动所有调度器 --- 慎用
	 * @return
	 */
	public abstract boolean startAllScheduler();

	/**
	 * 置调度器为待机
	 * @return
	 */
	public abstract boolean stopScheduler();

	/**
	 * 置所有调度器为待机
	 * @return
	 */
	public abstract boolean stopAllScheduler();

	/**
	 * 停止调度器,释放内存对象
	 * @return
	 */
	public abstract boolean shutdownScheduler();

	/**
	 * 停止所有调度器,释放内存对象
	 * @return
	 */
	public abstract boolean shutdownAllScheduler();

	public abstract boolean checkCronExpression(String cronStr);

	public abstract String getGroup();

	public abstract String getQuartzPropertiesFileName();

	public abstract String getJobClassName();
	
	public abstract String getRunIP();
	public boolean deleteTrigger(String triggerName, String triggerGroupName);

}
