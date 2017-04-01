/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.console.system.dao.impl;


import org.springframework.stereotype.Repository;

import com.together.common.CommonUtils;
import com.together.common.security.EncryptUtils;
import com.together.console.system.dao.IUserDao;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.dao.jpa.impl.DataBaseDaoImpl;
import com.together.framework.entity.sys.User;

/** 
 * 户信息数据库层操作对象接口实现类<p>
 * @author LingMin 
 * @date 2014-6-18<br>
 * @version 1.0<br>
 */
@Repository("com.together.console.system.userDao")
public class UserDaoImpl extends DataBaseDaoImpl<User, String, IUserDao> implements IUserDao {

	/**
	 * 封存指定的用户信息<p>
	 */
	public void archiveUser(User user, User oprt) {
		user.setModifier(oprt);
		user.setIsArchive(Boolean.TRUE);
		user.setModifyTime(new java.util.Date());
		update(user);
	}
	
	/**
	 * 重置指定的用户密码<p>
	 */
	public void resetPassword(User user, User oprt) {
		user.setModifier(oprt);
		user.setModifyTime(new java.util.Date());
		//user.setPassword(EncryptUtils.md5Encode("123456"));
		update(user);
	}
	
	/**
	 * 判断当前用户名是否存在<p>
	 */
	public boolean isExistenceUserName(String username) {
		boolean rtnB = false;
		return rtnB;
	}
	
	/**
	 * 删除用户信息<p>
	 */
	public void deleteUser(String userKey, User oprter) {
		User user = get(userKey);
		if (CommonUtils.isNotEmptyObject(user)) {
			user.setIsDelete(Boolean.TRUE);
			user.setModifier(oprter);
			user.setModifyTime(new java.util.Date());
			update(user);
		}
	}
	
	/**
	 * 判断当前用户是否管理员<p>
	 */
	public boolean isSuperman(String userKey, String roleNum) {
		return false;
	}
	
	/**
	 * 变更当前用户密码信息<p>
	 * @param userKey 用户关键字<br>
	 * @param oldPsd  旧  密 码<br>
	 * @param newPsd  新 密 码<br>
	 */
	public void changePasswrod(String userKey, String newPsd) {
	}
	
	
	/**
	 * 根据用户关键字判断输入密码是否合法<p>
	 */
	public boolean isRightPasswrod(String userKey, String oldPsd) {
		boolean rtnB = true;
		return rtnB;
	}
	
	/**
	 * 根据指定的用户名与密码判断当前用户是否合法<p>
	 */
	public User getLegalLoginUser(String username, String password) {
		User rtnO = null;
		
		String jpql = " from User where  isLocked = 0 and isArchive = 0 and isDelete = 0  and  isEnable = 1 and   numbers = ? and password = ? ";
		// 执行数据库查询  isLocked = 0 and isArchive = 0 and isDelete = 0  and
		java.util.List<Object> rtnList = this.findByJPQL(jpql, new Object[]{username , EncryptUtils.md5Encode(password)});
		if (CommonUtils.isNotEmptyList(rtnList)) {
			rtnO = (User)rtnList.get(0);
		}
		return rtnO;
	}
	
	/**
	 * 判断当前用户密码是否发生改变<p>
	 */
	public boolean isPasswordChanged(String userKey, String password) {
		boolean rtnB = true;
		return rtnB;
	}
	
	/**
	 * 锁定|解锁指定的用户账户<p>
	 */
	public void lockOrUnlockUser(User user, User oprt, boolean locked) {
		user.setModifier(oprt);
		user.setIsLocked(locked);
		user.setModifyTime(new java.util.Date());
		update(user);
	}
	
	
	
	/**
	 * 判断指定的用户是否具备操作某项功能的权限<p>
	 */
	public boolean isOperateRight(String userKey, String functionName, String actionName) {
		boolean rtnB = false;
		return rtnB;
	}


}
