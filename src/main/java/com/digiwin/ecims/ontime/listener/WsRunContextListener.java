package com.digiwin.ecims.ontime.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.digiwin.ecims.ontime.service.impl.SchedulerFactoryDynamic;


/**
 * 随服务启动
 * (1) 初始化缓存  cacheOnTimerBO
 * (2) 读取数据库的系统参数配置  Session管理模式  Servlet 或者 JBossCache 
 * @author aibozeng
 *
 */

public class WsRunContextListener implements ServletContextListener{

	private static final Logger logger = LoggerFactory.getLogger(WsRunContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		if(ctx==null){
			logger.warn("ws contextDestroyed:ApplicationContext ctx is null");
			return;
		}
		SchedulerFactoryDynamic schedulerFactoryDynamic = (SchedulerFactoryDynamic)ctx.getBean("schedulerFactoryIntf");
		if(schedulerFactoryDynamic!=null){
			schedulerFactoryDynamic.stopScheduler();
		}
//		SchedulerFactoryDynamic schedulerFactoryDynamic2 = (SchedulerFactoryDynamic)ctx.getBean("schedulerFactoryDynamic2");
//		if(schedulerFactoryDynamic2!=null){
//			schedulerFactoryDynamic2.stopScheduler();
//		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		if(ctx==null){
			logger.error("ws contextInitialized :ApplicationContext ctx is null");
			return;
		}

    	
    	//2011-03-30 aibozeng
		logger.info("ws:定时调度器开始。。。。。");
		SchedulerFactoryDynamic schedulerFactoryDynamic = (SchedulerFactoryDynamic)ctx.getBean("schedulerFactoryIntf");
		if(schedulerFactoryDynamic!=null){
			schedulerFactoryDynamic.initOnTimeTask();
			schedulerFactoryDynamic.startScheduler();
			logger.info("ws:定时调度器1启动结束");    	
		} else{
			logger.info("ws:定时调度器1找不到");    	
		}
//		SchedulerFactoryDynamic schedulerFactoryDynamic2 = (SchedulerFactoryDynamic)ctx.getBean("schedulerFactoryDynamic2");
//		if(schedulerFactoryDynamic2!=null){
//			schedulerFactoryDynamic2.initOnTimeTask();
//			schedulerFactoryDynamic2.startScheduler();
//			logger.info("ws:定时调度器2启动结束");    	
//		} else{
//			logger.info("ws:定时调度器2找不到");    	
//		}
	}
}
