package com.together.console.system.dao;

import com.together.framework.dao.jpa.ITreeDataDao;
import com.together.framework.entity.sys.UserGroup;


/**
 * 用户分组信息数据库层操作接口类<p>
 * @author LingMin 
 * @date 2014-07-23<br>
 * @version 1.0<br>
 */
public interface IUserGroupDao extends ITreeDataDao<UserGroup, String> {
}
