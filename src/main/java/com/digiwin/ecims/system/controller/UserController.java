package com.digiwin.ecims.system.controller;
/**
 * @author sux
 * @date 2011-01-09
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digiwin.ecims.core.util.ConditionValidate;
import com.digiwin.ecims.core.util.MD5;
import com.digiwin.ecims.system.model.User;
import com.digiwin.ecims.system.service.UserService;
import com.digiwin.ecims.system.util.CurrentDate;

@Controller
@RequestMapping("/system")
public class UserController{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	/**
	 * 
	 */
	@Autowired
	private UserService userService;
	
	/**
	 * 首页
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/index.do")
	public String index(ModelMap map,HttpServletRequest request,HttpServletResponse response){
		return "index";
	}
	
	/**
	 * first.jsp
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/first.do")
	public String first(ModelMap map,HttpServletRequest request,HttpServletResponse response){
		return "first";
	}
	/**
	 * userInfo.jsp
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/userInfo.do")
	public String userInfo(ModelMap map,HttpServletRequest request,HttpServletResponse response){
		return "system/userInfo";
	}
	
	/**
	 * userInfo.jsp
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/userPwdUpdate.do")
	public String userPwdUpdate(ModelMap map,HttpServletRequest request,HttpServletResponse response){
		return "system/update";
	}
	
	/**
	 * 登录
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login.do")
	public String login(ModelMap map,HttpServletRequest request,HttpServletResponse response,String username,String password,String validateCode){
		User user = (User) request.getSession().getAttribute("user");
		if(user != null){
			return "main";
		}
		
		String validateCode2 = (String)request.getSession().getAttribute("validateCode");
		//上线时要注释掉
//		username="admin";
//		password="123456";
//		validateCode = validateCode2==null?"":validateCode2;
		List<User> users = userService.validateUser(username==null?"":username.trim(),new MD5().complie(password==null?"":password.trim()));
		if(users == null || users.size() == 0){
			map.put("actionmessage", "用户名或密码错误");
			return "redirect:/index.jsp";
		}else{
			//this.addActionMessage("用户名或密码错误");
			if(!validateCode.trim().equalsIgnoreCase(validateCode2==null?"":validateCode2.trim())){
				//this.addActionMessage("验证码不正确");
				map.put("actionmessage", "验证码不正确");
				return "redirect:/index.jsp";
			}
		}
		request.getSession().setAttribute("user", users.get(0));
		logger.info("{} login sucess!", users.get(0).getUserName());
		map.put("user", users.get(0));
		userService.updateIPAndTimeById(users.get(0).getUserId(), request.getRemoteAddr(),CurrentDate.getStringDateAndTime());
		return "main";
	}
	
	@RequestMapping(value="/exit.do")
	public String exit(ModelMap map,HttpServletRequest request,HttpServletResponse response){
		User user = (User) request.getSession().getAttribute("user");
		if(null != user)
		logger.info("{} exited!", user.getUserName());
		request.getSession().removeAttribute("user");
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="/list.do")
	@ResponseBody
	public String list(ModelMap map,HttpServletRequest request,
			HttpServletResponse response,
			String condition,String conditionValue,String start,String limit){
		String userJson = null;
		if(!ConditionValidate.isEmpty(condition)){
			User userBean = new User();
			if("userName".equals(condition)){
				userBean.setUserName(conditionValue);
			}
			if("userRealName".equals(condition)){
				userBean.setUserRealName(conditionValue);
			}
			userJson = userService.getUserByCondition(userBean, start, limit);
		}else{
			userJson = userService.list(start, limit);
		}
		return userJson;
	}
	
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public String delete(ModelMap map,HttpServletRequest request,HttpServletResponse response,String ids){
		String msg = userService.deleteByIds(ids);
		return "{success: true, msg: '"+msg+"'}";
	}
	
	@RequestMapping(value="/saveorupdate.do")
	@ResponseBody
	public String save(ModelMap map,String jsonUser,HttpServletRequest request,HttpServletResponse response){
		JSONObject userJsonObject = JSONObject.fromObject(jsonUser);
		User user = new User();
		String userName = userJsonObject.getString("user.userName");
		user.setUserName(userName);
		String userRealName = userJsonObject.getString("user.userRealName");
		user.setUserRealName(userRealName);
		String userRemark = userJsonObject.getString("user.userRemark");
		user.setUserRemark(userRemark);
		
		
		Integer userId = userJsonObject.getInt("user.userId");
		if(userId != null && userId != 0){
			user.setUserId(userId);
			
			String userPwd = userJsonObject.getString("user.userPwd");
			user.setUserPwd(userPwd);
			
			String resetPwd = userJsonObject.getString("resetPwd");
			if(resetPwd != null && "1".equals(resetPwd)){
				user.setUserPwd(new MD5().complie("123456"));
			}
		}else{
			user.setUserPwd(new MD5().complie("123456"));//默认密码为123456
		}
		user.setUserDate(CurrentDate.getDate());
		
		String msg = userService.save(user);
		return "{success: true, msg: '"+msg+"'}";
	}
	
	@RequestMapping(value="/intoUpdate.do")
	@ResponseBody
	public String intoUpdate(ModelMap map,HttpServletRequest request,HttpServletResponse response,String userId){
		String userJson = userService.getById(userId);
		return userJson;
	}
	
	@RequestMapping(value="/updatePwd.do")
	@ResponseBody
	public String updatePwd(ModelMap map,HttpServletRequest request,HttpServletResponse response,String userPwd){
		User oldUser = (User)request.getSession().getAttribute("user");
		
		oldUser.setUserName(oldUser.getUserName());
		oldUser.setUserPwd(new MD5().complie(userPwd));
		String msg = userService.save(oldUser);
		return "{success: true, msg: '"+msg+"'}";
	}
	
	@RequestMapping(value="/validatePwd.do")
	@ResponseBody
	public String validatePwd(ModelMap map,HttpServletRequest request,HttpServletResponse response,String oldPassword){
		User user = (User) request.getSession().getAttribute("user");
		boolean msg = false;
		if(new MD5().complie(oldPassword).equals(user.getUserPwd())){
			msg = true;
		}
		return "{success: true, msg: "+msg+"}";
	}
	
//	/**
//	 * 获取当前登录人的工号
//	 */
//	public void ajaxGetEmpId(){
//		User user = (User)ActionContext.getContext().getSession().get("user");
//		this.out(user.getEmployee().getEmpId());
//	}
//	/**
//	 * 获取当前登录人的工号
//	 */
//	public void ajaxGetUser(){
//		User user = (User)ActionContext.getContext().getSession().get("user");
//		String json = JSONObject.fromObject(user).toString();
//		this.out(json);
//	}
//	/**
//	 * 获取当前登录人的id
//	 */
//	public void ajaxGetUserId(){
//		User user = (User)ActionContext.getContext().getSession().get("user");
//		this.out(String.valueOf(user.getUserId()));
//	}
//	/**
//	 * 获取当前登录人的员工姓名
//	 */
//	public void ajaxGetEmpName(){
//		User user = (User)ActionContext.getContext().getSession().get("user");
//		this.out(user.getEmployee().getEmpName());
//	}
//	/**
//	 * 获取当前登录人的登录名称username
//	 */
//	public void ajaxGetUserName(){
//		User user = (User)ActionContext.getContext().getSession().get("user");
//		this.out(user.getUserName());
//	}
	
	/**follow is getter and setter method**/
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args){
		System.out.println(new MD5().complie("123456"));
	}
}
