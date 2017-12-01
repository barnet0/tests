package com.digiwin.ecims.ontime.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 放置于 Session的标识当前登录用户的信息。这是一个泛指。
 * @see LoginEmployee 针对企业自身职员
 * @see LoginMember   针对网上会员
 * 
 * @author aibozeng
 */

@SuppressWarnings("serial")
public class LoginUser implements Serializable {

	public LoginUser() {
	}

	public LoginUser(Long id) {
		this.id = id;
	}
	
	private Long id = 1L; // 用户表 T_USER.id 的主键
	
	private Long subUserId = 1L;//具体用户类型的id,如：employee.id , member.id , otherEmpl.id 等

	//private String userType = UserTypeEnum.Admin.getCode(); // 管理员、职员、客户员工、物流商员工等 // CommonCD.SubUserType
	private String userType = "1"; // 管理员、职员、客户员工、物流商员工等 // CommonCD.SubUserType
	
	private String roleAssignType = "1"; //角色指派类型 CommonCD.RoleAssignType

	/**
	 * 登录名
	 */
	private String userId = "admin"; // 登录帐号 T_USER_ALIAS.loginName  , 一个用户可能有多个登录名

	/**
	 * 用户名称
	 */
	private String userNm = "测试用户"; // 用户昵称 ,可以随意变的
	
	/**
	 * 人的编号，一旦录入系统，不能轻易改变的，如：员工编码、会员卡号等
	 */
	private String userCode = ""; 
	
	/**
	 * 部门编码 T_DEPARTMENT.dept_code, 工作流分配任务用
	 */
	private String deptCode = "";

	private String email;

	private String passwd; // 可以不存放该值

	private String clientIp; // 访问来源 ip

	private Date loginDate = new Date(); // 登录日期

	private String msg = ""; // 提示信息

	private String isNeedChangePwd="N";//是否需要修改密码
	//private String changePwdDesc ; //需要修改密码的原因 --放在 msg 字段

	private String logonToken; // 2010-12-26 登录令牌

	// 当前菜单的ID
	private String currentFuncId;

	/**
	 * 表明用户当前访问的模块
	 */
	private String moduleCode;

	private String webId; // ???
	
	private String currentUrl = "";
	
	private Long applicationId = 0L;

	private Date accessDate = new Date(); // 最后访问日期
	
	/**
	 * 2010-03-12 aibo zeng 表明是否已经登录 Session中可以放入一个 new User() , 如果 logined==false，表明没有登录
	 */
	private boolean logined = false;
	
	//0:标准样式
	private String styleName = "default";

	/**
	 * 存储登录用户拥有的授权菜单的code和authCode
	 */
	private Map<String,Long> funcAuthCodes= new HashMap<String,Long>();
	
	/**
	 * 交易圈ID, 为空或者0表示不控制
	 * >0,需要在各种查询语句加入过滤
	 */
	private Long ownerId = 0L;

	/**
	 * 是否是超级管理员
	 * @return
	 */
	public boolean isAdmin(){
		//return UserTypeEnum.Admin.getCode().equals(userType);
		return true;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public boolean isLogined() {
		return logined;
	}

	public void setLogined(boolean logined) {
		this.logined = logined;
	}


	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getName() {

		return userNm;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getWebId() {
		return webId;
	}

	public void setWebId(String webId) {
		this.webId = webId;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static final String COOKIE_SEPARATOR = "^";

	public String toString() {
		StringBuilder sb = new StringBuilder(100);
		sb.append(this.getId()).append(COOKIE_SEPARATOR).append(this.getUserId()).append(COOKIE_SEPARATOR).append(this.getUserNm())
				.append(COOKIE_SEPARATOR).append(this.getUserType()).append(COOKIE_SEPARATOR).append(this.getModuleCode())
				.append(COOKIE_SEPARATOR).append(this.getWebId())
				.append(COOKIE_SEPARATOR).append("userType=" + this.getUserType())
                .append(COOKIE_SEPARATOR).append("logonToken=" + getLogonToken())
				.append(COOKIE_SEPARATOR).append("isLogin=" + isLogined());
		return sb.toString();
	}

	public String getLogonToken() {
		return logonToken;
	}

	public void setLogonToken(String logonToken) {
		this.logonToken = logonToken;
	}

	public String getCurrentFuncId() {
		return currentFuncId;
	}

	public void setCurrentFuncId(String currentFuncId) {
		this.currentFuncId = currentFuncId;
	}

	public String getIsNeedChangePwd() {
		return isNeedChangePwd;
	}

	public void setIsNeedChangePwd(String isNeedChangePwd) {
		this.isNeedChangePwd = isNeedChangePwd;
	}

	public Map<String, Long> getFuncAuthCodes() {
		return funcAuthCodes;
	}

	public void setFuncAuthCodes(Map<String, Long> funcAuthCodes) {
		this.funcAuthCodes = funcAuthCodes;
	}

	/**
	 * 根据菜单编码，获取功能点的授权码
	 * @param funcCode
	 * @return
	 */
	public long getAuthCodeByFuncCode(String funcCode) {
		if (getFuncAuthCodes() == null || getFuncAuthCodes().isEmpty()) {
			return 0L;
		}		
		if(!getFuncAuthCodes().containsKey(funcCode)){
			return -1L;
		}
		return getFuncAuthCodes().get(funcCode);
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getRoleAssignType() {
		return roleAssignType;
	}

	public void setRoleAssignType(String roleAssignType) {
		this.roleAssignType = roleAssignType;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getSubUserId() {
		return subUserId;
	}

	public void setSubUserId(Long subUserId) {
		this.subUserId = subUserId;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	
	
}

