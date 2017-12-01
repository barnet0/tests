package com.digiwin.ecims.ontime.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.digiwin.ecims.platforms.taobao.service.area.StandardAreaService;
import com.digiwin.ecims.ontime.service.CheckUpdateService;
import com.digiwin.ecims.ontime.service.ParamSystemService;


/**
 * 随服务启动
 * (1) 初始化缓存  cacheOnTimerBO
 * (2) 读取数据库的系统参数配置  Session管理模式  Servlet 或者 JBossCache 
 * @author aibozeng
 *
 */

public class SysParamContextListener implements ServletContextListener{
	
	private static final Logger logger = LoggerFactory.getLogger(SysParamContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		if(ctx==null){
			logger.error("systemParam contextInitialized :ApplicationContext ctx is null");
			return;
		}
		logger.info("systemParam:系统参数开始加载。。。。。");
		ParamSystemService initSystemParam = (ParamSystemService)ctx.getBean(ParamSystemService.class);
		
		boolean isSuccess = initSystemParam.initParamToCache();
		if(!isSuccess){
			logger.error("systemParam:系统参数加载失败");
		}
		logger.info("systemParam:系统参数加载完毕。。。。。");
		
		logger.info("systemParam:标准省市区参数开始加载。。。。。");
		StandardAreaService initAreaData = (StandardAreaService)ctx.getBean(StandardAreaService.class);
		isSuccess = initAreaData.saveAreaToCache();
		if (!isSuccess) {
			logger.error("systemParam:标准省市区参数加载失败");
		}
		logger.info("systemParam:标准省市区参数加载完毕。。。。。");
		
		//清空停機前所記載的資訊. add by xavier on 20150909
		logger.info("checkUpdateParam:準備清空。。。。。");
		CheckUpdateService checkUpdateParam = (CheckUpdateService)ctx.getBean(CheckUpdateService.class);
		if(!checkUpdateParam.initParamToCache()) {
			logger.error("checkUpdateParam:清空失败");
		}
		logger.info("checkUpdateParam:準備清空完畢。。。。。");
	}
}
