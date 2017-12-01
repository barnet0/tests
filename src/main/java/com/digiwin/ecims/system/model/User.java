package com.digiwin.ecims.system.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "t_user")
@DynamicUpdate(true)
public class User {
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	@Column(name="user_real_name")
	private String userRealName;
	@Column(name="user_name")
	private String userName;
	@Column(name="user_pwd")
	private String userPwd;
	@Column(name="user_remark")
	private String userRemark;
	@Column(name="user_date")
	private Date userDate;
	@Column(name="user_last_ip")
	private String userLastIp;
	@Column(name="user_last_time")
	private String userLastTime;
	
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserRemark() {
		return userRemark;
	}
	public void setUserRemark(String userRemark) {
		this.userRemark = userRemark;
	}
	public Date getUserDate() {
		return userDate;
	}
	public void setUserDate(Date userDate) {
		this.userDate = userDate;
	}
	public String getUserLastIp() {
		return userLastIp;
	}
	public void setUserLastIp(String userLastIp) {
		this.userLastIp = userLastIp;
	}
	public String getUserLastTime() {
		return userLastTime;
	}
	public void setUserLastTime(String userLastTime) {
		this.userLastTime = userLastTime;
	} 
	
	
}
