package com.together.console;

import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;

import com.together.console.system.service.IUserService;
import com.together.framework.common.annotation.IServiceCtrl;


/**
 * 管理后台登录界面实现类<p>
 * @author LingMin 
 * @date 2014-11-04<br>
 * @version 1.0<br>
 */
@ManagedBean(name="console.login2Bean", scope=ManagedBeanScope.REQUEST)
public class Login2UIBean extends LoginUIBean {

	 /** 用户信息业务逻辑层操作对象 **/
	@IServiceCtrl(serviceName="console.system.userService")
	protected IUserService userService;
	
	
	
	
	/**
	 * 获取用户信息业务逻辑层操作对象<p>
	 * @return  userService  用户信息业务逻辑层操作对象<br>
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * 设置用户信息业务逻辑层操作对象<p>
	 * @param  userService  用户信息业务逻辑层操作对象<br>
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
