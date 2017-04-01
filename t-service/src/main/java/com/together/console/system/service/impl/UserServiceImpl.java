/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.console.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.together.console.system.dao.IUserDao;
import com.together.console.system.service.IUserService;
import com.together.framework.entity.sys.User;
import com.together.framework.service.impl.DataBaseServiceImpl;

/** 
 * 用户信息业务逻辑层操作接口实现类<p>
 * @author LingMin 
 * @date 2014-6-18<br>
 * @version 1.0<br>
 */
@Service("console.system.userService")
public class UserServiceImpl extends DataBaseServiceImpl<User, String> implements IUserService {
	/** 用户信息数据库层操作对象 **/
	protected IUserDao userDao;
	
	/**
	 * 构造函数:初始化数据库层操作对象<p>
	 * @param userDao 初始化数据库层操作对象<br>
	 */
	@Autowired(required=true)
	public UserServiceImpl(IUserDao userDao) {
		super(userDao);
		this.userDao = userDao;
	}
	
	
	/**
	 * 封存指定的用户信息<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	//@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void archiveUser(User user, User oprt) {
		userDao.archiveUser(user, oprt);
	}
	
	/**
	 * 重置指定的用户密码<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	//@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void resetPassword(User user, User oprt) {
		userDao.resetPassword(user, oprt);
	}
	
	/**
	 * 判断当前用户名是否存在<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public boolean isExistenceUserName(String username) {
		return userDao.isExistenceUserName(username);
	}
	
	/**
	 * 删除指定的用户信息<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	//@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void deleteUser(String userKey, User oprter) {
		userDao.deleteUser(userKey, oprter);
	}
	
	/**
	 * 判断当前用户是否管理员<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public boolean isSuperman(String userKey, String roleNum) {
		return userDao.isSuperman(userKey, roleNum);
	}
	
	/**
	 * 变更当前用户密码信息<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	//@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void changePasswrod(String userKey, String newPsd) {
		userDao.changePasswrod(userKey, newPsd);
	}
	
	
	/**
	 * 根据用户关键字判断输入密码是否合法<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public boolean isRightPasswrod(String userKey, String oldPsd) {
		return userDao.isRightPasswrod(userKey, oldPsd);
	}
	
	/**
	 * 根据指定的用户名与密码判断当前用户是否合法<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public User getLegalLoginUser(String username, String password) {
		return userDao.getLegalLoginUser(username, password);
	}
	
	/**
	 * 判断当前用户密码是否发生改变<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public boolean isPasswordChanged(String userKey, String password) {
		return userDao.isPasswordChanged(userKey, password);
	}
	
	/**
	 * 锁定|解锁指定的用户账户<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	//@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void lockOrUnlockUser(User user, User oprt, boolean locked) {
		userDao.lockOrUnlockUser(user, oprt, locked);
	}
	
	
	/**
	 * 判断指定的用户是否具备操作某项功能的权限<p>
	 */
	//@Cacheable(value="com.hgmk.hibernateCache")
	public boolean isOperateRight(String userKey, String functionName, String actionName) {
		return userDao.isOperateRight(userKey, functionName, actionName);
	}
}