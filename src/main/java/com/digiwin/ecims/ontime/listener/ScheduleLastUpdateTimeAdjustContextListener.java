package com.digiwin.ecims.ontime.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.digiwin.ecims.ontime.service.ParamSystemService;
import com.digiwin.ecims.ontime.service.ScheduleLastUpdateTimeAdjustService;

/**
 * Application Lifecycle Listener implementation class ScheduleLastUpdateTimeAdjustContextListener
 *
 * @author 维杰
 * @since 2015.09.07
 */
@WebListener
public class ScheduleLastUpdateTimeAdjustContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleLastUpdateTimeAdjustContextListener.class);
	
    /**
     * Default constructor. 
     */
    public ScheduleLastUpdateTimeAdjustContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
    	
    	if (ctx == null) {
    		logger.error("systemParam contextInitialized :ApplicationContext ctx is null");
			return;
    	}
    	logger.info("系统启动,修改并提前各排程在task_schedule_config中的lastUpdateTime");
    	ParamSystemService paramSystemService = (ParamSystemService)ctx.getBean(ParamSystemService.class);
    	String timeAhead = "";
    	if (paramSystemService != null) {
    		timeAhead = (String) paramSystemService.getSysParamByKey("TimeAhead");
        	if (timeAhead == null || timeAhead.trim().length() == 0) {
        		timeAhead = "30";
        		logger.info("系统参数无法读取,从默认配置中读取排程提前时间.提前时间为:{}分钟", timeAhead);
        	} else {
        		logger.info("系统参数读取成功.提前时间为:{}分钟", timeAhead);
        	}
    	} else {
    		timeAhead = "30";
    		logger.info("系统参数无法读取,从默认配置中读取排程提前时间.提前时间为:{}分钟", timeAhead);
    	}

    	ScheduleLastUpdateTimeAdjustService scheduleAdjustService = 
    			(ScheduleLastUpdateTimeAdjustService)ctx.getBean(ScheduleLastUpdateTimeAdjustService.class);
    	int affectedRows = scheduleAdjustService.updateLastUpdateTime(timeAhead);
    	if (affectedRows > 0) {
    		logger.info("系统启动,排程lastUpdateTime修改成功");
    	} else {
    		logger.info("系统启动,排程lastUpdateTime修改失败!返回值为{}", affectedRows);
    	}
    	
    }
	
}
