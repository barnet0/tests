package com.digiwin.ecims.system.service.impl;
/**
 * @author lizhi
 * @date 2015-07-22
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiwin.ecims.system.bean.PageBean;
import com.digiwin.ecims.system.dao.UserDAO;
import com.digiwin.ecims.system.model.User;
import com.digiwin.ecims.system.service.UserService;
import com.digiwin.ecims.system.util.StaticValue;
import net.sf.json.JSONArray;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDAO userDAO;

	
	public List<User> validateUser(String username, String password) {		
		List<User> users = userDAO.findByUsernameAndPassword(username, password);
		return users;
	}
	
	
	public String list(String start, String limit) {
		PageBean pageBean = userDAO.findAll(start, limit);
		String json = JSONArray.fromObject(pageBean.getRoot()).toString();
		return "{totalProperty:"+pageBean.getTotalProperty()+",root:"+json+"}";
	}
	
	
	public String getUserByCondition(User userBean, String start, String limit) {
		PageBean pageBean = userDAO.findByCondition(userBean, start, limit);
		String json = JSONArray.fromObject(pageBean.getRoot()).toString();
		return "{totalProperty:"+pageBean.getTotalProperty()+",root:"+json+"}";
	}
	
	public String deleteByIds(String ids) {
		String[] userIds = ids.split(",");
		if(userDAO.deleteByIds(userIds)){
			return StaticValue.DELETE_SUCCESS;
		}
		return StaticValue.DELETE_FAILURE;
	}
	
	
	public String save(User user) {
		if(userDAO.saveOrUpdate(user)){
			return StaticValue.SAVE_SUCCESS;
		}
		return StaticValue.SAVE_FAILURE;
	}
	
	
	public String getById(String userId) {
		User users = userDAO.findById(Integer.parseInt(userId));
		return JSONArray.fromObject(users).toString();
	}
	
	
	public void updateIPAndTimeById(Integer userId, String userLastIp,
			String userLastTime) {
		User user = userDAO.findById(userId);
		if(user != null){
			user.setUserLastIp(userLastIp);
			user.setUserLastTime(userLastTime);
			userDAO.saveOrUpdate(user);
		}
	}

	/**follow is getter and setter**/
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
