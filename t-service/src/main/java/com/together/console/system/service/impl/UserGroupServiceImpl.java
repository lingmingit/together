package com.together.console.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.together.console.system.dao.IUserGroupDao;
import com.together.console.system.service.IUserGroupService;
import com.together.framework.entity.sys.UserGroup;
import com.together.framework.service.impl.TreeDataServiceImpl;

/**
 * 用户分组信息业务逻辑层操作接口实现类<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@Service("console.system.userGroupService")
public class UserGroupServiceImpl extends TreeDataServiceImpl<UserGroup, String> implements IUserGroupService {
	/** 用户分组信息数据库层操作对象 **/
	protected IUserGroupDao userGroupDao;
	
	/**
	 * 构造函数:初始化用户分组信息数据库层操作对象<p>
	 * @param userGroupDao 用户分组信息数据库层操作对象<br>
	 */
	@Autowired(required=true)
	public UserGroupServiceImpl(IUserGroupDao userGroupDao) {
		super(userGroupDao);
		this.userGroupDao = userGroupDao;
	}
}
