package com.digiwin.ecims.core.annotation;

import com.digiwin.ecims.core.model.LogInfoT;
import com.digiwin.ecims.system.enums.LogInfoBizTypeEnum;
import com.digiwin.ecims.system.model.User;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.Date;

/**
 * 切点类
 *
 * @author tiangai
 * @version 1.0
 * @since 2014-08-05 Pm 20:35
 */
@Aspect @Component public class SystemLogAspect {
    // 注入Service用于把日志保存数据库
    //	@Resource
    //	private LogService logService;
    // 本地异常日志记录对象
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    // Service层切点
    @Pointcut("@annotation(com.digiwin.ecims.core.annotation.SystemServiceLog)")
    public void serviceAspect() {

    }

    // Controller层切点
    @Pointcut("@annotation(com.digiwin.ecims.core.annotation.SystemControllerLog)")
    public void controllerAspect() {

    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()") public void doBefore(JoinPoint joinPoint) {

        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 读取session中的用户
        //User user = (User) session.getAttribute("");
        User user = new User();
        user.setUserName("测试用户");
        // 请求的IP
        String ip = request.getRemoteAddr();
        try {
            // *========控制台输出=========*//
            System.out.println("=====前置通知开始=====");
            System.out.println(
                "请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint
                    .getSignature().getName() + "()"));
            System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));
            System.out.println("请求人:" + user.getUserName());
            System.out.println("请求IP:" + ip);
            // *========数据库日志=========*//
            LogInfoT logInfoT = new LogInfoT();
            logInfoT.setIpAddress("192.168.58.200");
            logInfoT.setCallMethod("http://192.168.58.200/web/ws/r/cws_oms_port");
            logInfoT.setBusinessType(LogInfoBizTypeEnum.ECI_PUSH.getValueString());
            logInfoT.setReqType("taskSchedule");
            logInfoT.setResSize(BigInteger.valueOf(1));
            logInfoT.setIsSuccess(false);
            logInfoT.setReqTime(new Date());
            logInfoT.setResTime(new Date());
            logInfoT.setResCode("fail");
            logInfoT.setResMsg("");
            logInfoT.setErrBillType("aomsordT");
            logInfoT.setErrBillId("");
            logInfoT.setPushLimits(0);
            logInfoT.setFinalStatus("0");
            // 保存数据库
            //logService.add(log);
            System.out.println("=====前置通知结束=====");
        } catch (Exception e) {
            // 记录本地异常日志
            logger.error("==前置通知异常=={}", e.getMessage());
        }
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e") public void doAfterThrowing(
        JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        // 读取session中的用户
        //		User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
        User user = new User();
        user.setUserName("测试用户");
        // 获取请求ip
        String ip = request.getRemoteAddr();
        // 获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                params += JSONObject.fromObject(joinPoint.getArgs()[i]).toString() + ";";
            }
        }
        try {
      /* ========控制台输出========= */
            System.out.println("=====异常通知开始=====");
            System.out.println("异常代码:" + e.getClass().getName());
            System.out.println("异常信息:" + e.getMessage());
            System.out.println(
                "异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint
                    .getSignature().getName() + "()"));
            System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));
            System.out.println("请求人:" + user.getUserName());
            System.out.println("请求IP:" + ip);
            System.out.println("请求参数:" + params);
			/* ==========数据库日志========= */
            LogInfoT logInfoT = new LogInfoT();
            logInfoT.setIpAddress("192.168.58.200");
            logInfoT.setCallMethod("http://192.168.58.200/web/ws/r/cws_oms_port");
            logInfoT.setBusinessType(LogInfoBizTypeEnum.ECI_PUSH.getValueString());
            logInfoT.setReqType("taskSchedule");
            logInfoT.setResSize(BigInteger.valueOf(1));
            logInfoT.setIsSuccess(false);
            logInfoT.setReqTime(new Date());
            logInfoT.setResTime(new Date());
            logInfoT.setResCode("fail");
            logInfoT.setResMsg("");
            logInfoT.setErrBillType("aomsordT");
            logInfoT.setErrBillId("");
            logInfoT.setPushLimits(0);
            logInfoT.setFinalStatus("0");
            // 保存数据库
            //logService.add(log);
            System.out.println("=====异常通知结束=====");
        } catch (Exception ex) {
            // 记录本地异常日志
            logger.error("==异常通知异常=={}", ex.getMessage());
        }
		/* ==========记录本地异常日志========== */
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", e.getMessage());

    }

    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method
                        .getAnnotation(com.digiwin.ecims.core.annotation.SystemServiceLog.class)
                        .description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method
                        .getAnnotation(com.digiwin.ecims.core.annotation.SystemControllerLog.class)
                        .description();
                    break;
                }
            }
        }
        return description;
    }
}
