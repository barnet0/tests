package com.digiwin.ecims.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * WebUtil 负责Cookie/Session等的管理。通常，所有对Cookie/Session的操作都通过此类来完成。 <br>
 * Session最终是采用 Servlet容器来管理，或者采用 JBoss Cache，由此控制。
 * 
 * 
 * @version 1.0
 */
public class WebUtil {

	/***
	 * Cookie约定的名称 
	 */
	//PropertiesUtil propertiesUtil = PropertiesUtil.createPropertiesUtil("sso.properties");
	public static String tokenPName = "MToken";//propertiesUtil.getProperty("cookieTokenName");
	public static String uIdPName = "MUID";// t_user.id
	public static String subUserIdPName = "MSubUserId";//如：employee.id , member.id等
	public static String userIdPName = "MUserId";// t_user.userId==loginName
	public static String niceNamePName = "MNiceName";// t_user.name
	public static int maxAge = 60*60;//Integer.valueOf(propertiesUtil.getProperty("cookieage"));
	public static String path = "/";
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getObject(HttpSession session, String name) {
		return (T) session.getAttribute(name);
	}
	
	/**
	 * 根据名字从HttpSession中获取一个对象
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            Session中对象的名字
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObject(HttpServletRequest request, String name) {
		return (T) request.getSession().getAttribute(name);
	}

	/**
	 * 根据给定的name将一个对象保存到 HttpSession中
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param name
	 *            Session中对象的名字
	 * @param object
	 *            需要保存到Session的对象
	 */
	public static <T> void putObject(HttpServletRequest request, String name, T object) {
		request.getSession().setAttribute(name, object);
	}

	/**
	 * 将Session置为无效状态，通常在注销时调用
	 * 
	 * @param request
	 *            HttpServletRequest
	 */
	public static void invalidateSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}

	/**
	 * 获取URI的路径,如路径为http://www.example.com/example/user.do?method=add, 得到的值为"/example/user.do"
	 * 
	 * @param request
	 * @return String
	 */
	public static String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}

	/**
	 * 获取不包含应用名字的URI的路径, 并去掉最前面的"/", <br>
	 * 如路径为http://localhost:8080/appName/user/list.do, 得到的值为"user/list.do",其中appNames为应用的名字
	 * 
	 * @param request
	 * @return String
	 */
	public static String getNoAppNamedRequestURI(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		String realUri = uri.replace(contextPath, "");
		while (realUri.startsWith("/")) {
			realUri = realUri.substring(1);
		}
		realUri = realUri.replaceAll("/+", "/");
		return realUri;
	}

	/**
	 * 获取应用的根目录
	 * 
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		if (contextPath.equals("/")) {
			return "";
		}
		return contextPath;
	}
	
	/**
	 * 获取不含端口号的域名。如：www.my.com:8080,返回 .my.com:8080
	 * @param request
	 * @return
	 */
	public static String getDomain(HttpServletRequest request){
		String domain = request.getHeader("host");
		if(domain==null){
			return "localhost";
		}
		//if(domain.indexOf(':')>0){
		//	domain = domain.substring(0,domain.indexOf(':'));
		//}
		if(domain.indexOf('.')>0){
			domain = domain.substring(domain.indexOf('.'));
		}		
		return domain;
	}

	/**
	 * 获取完整请求路径(含内容路径及请求参数)
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURIWithParam(HttpServletRequest request) {
		return getRequestURI(request) + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
	}

	/**
	 * 添加cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie的名称
	 * @param value
	 *            cookie的值
	 * @param maxAge
	 *            cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 
	 *            如果值为0,cookie将随浏览器关闭而清除)
	 *            如果值为-1,永不过期
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		if(maxAge>0)
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie的值
	 * 
	 * @param request
	 * @param name
	 *            cookie的名称
	 * @return
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(name)) {
					return cookies[i].getValue();
				}
			}
		}
		return null;
	}

	/**
	 * sso.properties ------ 已经被去掉，改放 SystemParam
	 * sso登出清除cookie
	 * @param request
	 * @param response
	 */
	@Deprecated	
	public static void clearCookieSSO(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		try {
			for (int i = 0; i < cookies.length; i++) {
				if (tokenPName.equals(cookies[i].getName())){
					cookies[i].setValue(null);
					response.addCookie(cookies[i]);
					//只需要去掉token
					break;
				}
			}
		} catch (Exception ex) {
			System.out.println("清空Cookies发生异常！");
		}
	}		
}
