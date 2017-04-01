package com.together.console.system.bean;

import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;

import com.together.console.system.service.IUserGroupService;
import com.together.framework.common.annotation.IServiceCtrl;
import com.together.framework.entity.sys.UserGroup;
import com.together.framework.service.ITreeDataService;
import com.together.framework.service.base.ICoreBaseService;
import com.together.framework.web.aom.edit.impl.TreeGroupEditUIBean;

/**
 * 用户分组信息编辑界面实现类<p>
 * @author LingMin 
 * @date 2014-07-23<br>
 * @version 1.0<br>
 */
@ManagedBean(name="console.system.userGroupEditUIBean", scope=ManagedBeanScope.REQUEST)
public class UserGroupEditUIBean extends TreeGroupEditUIBean<UserGroup, String> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 4412419771507719545L;
	/** 用户分组信息业务逻辑层操作对象 **/
	@IServiceCtrl(serviceName="console.system.userGroupService")
	protected IUserGroupService userGroupService;
	
	/**
	 * 构造函数:初始化相关参数<p>
	 */
	public UserGroupEditUIBean() {
//		setHasUsedNumRule(true);
//		setNumberRuler(new NumberRuler("UG", 4, Boolean.FALSE));
	}

	/**
	 * 获取编辑界面的URL路径<p>
	 */
	public String getEditURL() {
		return "/console/system/userGroupEditUI.faces";
	}

	/**
	 * 获取功能名称<p>
	 */
	public String getFunctionName() {
		return "console.system.user";
	}

	/**
	 * 获取业务逻辑层操作对象<p>
	 */
	public ICoreBaseService<UserGroup, String> getCurrentService() {
		return userGroupService;
	}
	
	/**
	 * 获取分组信息业务逻辑层操作对象<p>
	 * @return 分组信息业务逻辑层操作对象<br>
	 */
	public ITreeDataService<UserGroup, String> getTreeGroupService(){
		return userGroupService;
	}
}
