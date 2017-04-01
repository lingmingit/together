package com.together.console.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.together.console.system.dao.IUserGroupDao;
import com.together.framework.dao.jpa.impl.TreeDataDaoImpl;
import com.together.framework.entity.sys.UserGroup;

/**
 * 用户分组信息数据库层操作接口实现类<p>
 * @author LingMin 
 * @date 2014-07-23<br>
 * @version 1.0<br>
 */
@Repository("com.together.console.system.userGroupDao")
public class UserGroupDaoImpl extends TreeDataDaoImpl<UserGroup, String, IUserGroupDao> implements IUserGroupDao {
}
