package com.digiwin.ecims.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.digiwin.ecims.core.service.LoginfoOperateService;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	LoginfoOperateService interceptorService;

//	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

//	private LogInfoT logInfo=null;
	
	/**
	 * 請求進來時會先跑這方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object target) throws Exception {
//		User oldUser = (User)request.getSession().getAttribute("user");
//		if(oldUser == null){
//			response.sendRedirect("/index.jsp");
//		}
//		
		return false;
	}

	/**
	 * 整個回應完後會到這方法
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

}
