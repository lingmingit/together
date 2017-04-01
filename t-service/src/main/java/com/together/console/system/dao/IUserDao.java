/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.console.system.dao;


import com.together.framework.dao.jpa.IDataBaseDao;
import com.together.framework.entity.sys.User;

/** <p>
 * @author LingMin 
 * @date 2014-6-18<br>
 * @version 1.0<br>
 */
public interface IUserDao  extends IDataBaseDao<User, String> {
	/**
	 * 封存指定的用户信息<p>
	 * @param user 用户信息<br>
	 * @param oprt 操作者信息<br>
	 */
	public void archiveUser(User user, User oprt);
	
	/**
	 * 重置指定的用户密码<p>
	 * @param user 用户信息<br>
	 * @param oprt 操作者信息<br>
	 */
	public void resetPassword(User user, User oprt);

	/**
	 * 删除指定的用户信息<p>
	 * @param userKey 用户关键字<br>
	 * @param oprter 操作者信息<br>
	 */
	public void deleteUser(String userKey, User oprter);
	
	/**
	 * 判断当前用户名是否存在<p>
	 * @param username 用户名<br>
	 * @return true:存在 false:消失<br>
	 */
	public boolean isExistenceUserName(String username);
	
	
	/**
	 * 判断当前用户是否管理员<p>
	 * @param userKey 用户关键字<br>
	 * @param roleNum 管理员角色编码<br>
	 * @return true:是 false:否<br>
	 */
	public boolean isSuperman(String userKey, String roleNum);
	
	/**
	 * 变更当前用户密码信息<p>
	 * @param userKey 用户关键字<br>
	 * @param oldPsd  旧  密 码<br>
	 * @param newPsd  新 密 码<br>
	 */
	public void changePasswrod(String userKey, String newPsd);
	
	
	/**
	 * 根据用户关键字判断输入密码是否合法<p>
	 * @param userKey 用户关键字<br>
	 * @param oldPsd  密码字符串<br>
	 * @return true:合法 false:非法<br>
	 */
	public boolean isRightPasswrod(String userKey, String oldPsd);
	
	/**
	 * 根据指定的用户名与密码判断当前用户是否合法<p>
	 * @param username 用户名<br>
	 * @param password 密    码<br>
	 * @return 用户信息<br>
	 */
	public User getLegalLoginUser(String username, String password);
	
	
	/**
	 * 判断当前用户密码是否发生改变<p>
	 * @param userKey 用户关键字<br>
	 * @param password 用户密码<br>
	 * @return true:已变 false:未变<br>
	 */
	public boolean isPasswordChanged(String userKey, String password);
	
	/**
	 * 锁定|解锁指定的用户账户<p>
	 * @param user 用户信息<br>
	 * @param oprt 操作者信息<br>
	 * @param locked true:锁定 false:解锁<br>
	 */
	public void lockOrUnlockUser(User user, User oprt, boolean locked);
	
	/**
	 * 获取用户的角色信息<p>
	 * @param userKey 用户关键字<br>
	 * @param isMain 主要角色<br>
	 * @return 角色信息<br>
	 */
	//public java.util.List<Role> getRoleList(String userKey, Boolean isMain);
	
	/**
	 * 判断指定的用户是否具备操作某项功能的权限<p>
	 * @param userKey 用户关键字<br>
	 * @param functionName 功能名称<br>
	 * @param actionName 操作名称<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean isOperateRight(String userKey, String functionName, String actionName);
}