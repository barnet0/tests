package com.digiwin.ecims.system.dao;

import java.util.List;

import com.digiwin.ecims.system.bean.PageBean;
import com.digiwin.ecims.system.model.User;

public interface UserDAO{
	/**
	 * 依据用户名与密码进行查询
	 * @param username
	 * @param password
	 */
	public List<User> findByUsernameAndPassword(String username, String password);
	/**
	 * 查询所有
	 * @return
	 */
	public PageBean findAll(String start, String limit);
	/**
	 * 按条件查询
	 * @param userBean
	 * @return
	 */
	public PageBean findByCondition(User userBean, String start, String limit);
	/**
	 * 按id删除
	 * @param userId
	 * @return
	 */
	public boolean deleteByIds(String[] userIds);
	/**
	 * 保存或修改
	 * @param user
	 * @return
	 */
	public boolean saveOrUpdate(Object user);
	/**
	 * 按id查询
	 * @param userId
	 * @return
	 */
	public User findById(int userId);
}
