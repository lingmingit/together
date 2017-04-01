/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.dao.jpa;

import com.together.framework.dao.jpa.base.ICoreBaseDao;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.sys.User;

/** 
 * 基础资料信息数据库层操作接口类<p>
 * @author LingMin 
 * @date 2014-6-18<br>
 * @version 1.0<br>
 */
public interface IDataBaseDao<E extends AbstractDataBase, ID extends java.io.Serializable> extends ICoreBaseDao<E, ID> {

	
	/**
	 * 【启用】按钮数据库层监听函数<p>
	 * @param entity 实体对象<br>
	 * @param oprter 操作者<br>
	 */
	public void enable(E entity, User oprter);
	
	/**
	 * 【禁用】按钮数据库层监听函数<p>
	 * @param entity  实体对象<br>
	 * @param oprter 操作者<br>
	 */
	public void disable(E entity, User oprter);
	
	/**
	 * 根据数据记录状态获取满足条件的数据记录集合<p>
	 * @param status 数据记录状态<br>
	 * @return 数据记录集合<br>
	 */
	public java.util.List<E> getListForStatus(Boolean status);
}
