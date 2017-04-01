package com.together.framework.entity.sys;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.together.framework.entity.AbstractTreeData;


/**
 * 用户分组实体信息类<p>
 * @author LingMin 
 * @date 2014-07-23<br>
 * @version 1.0<br>
 */
@Entity @Table(name = "t_sys_usergroup")
public class UserGroup extends AbstractTreeData {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 7665772087139250607L;
	/** 上级菜单 **/
	@OneToOne @JoinColumn
	private UserGroup parent;
	
	/**
	 * 获取菜单信息<p>
	 * @return 菜单信息<br>
	 */
	public UserGroup getParent() {
		return parent;
	}
	
	/**
	 * 设置菜单信息<p>
	 * @param parent 菜单信息<br>
	 */
	public void setParent(UserGroup parent) {
		this.parent = parent;
	}
}
