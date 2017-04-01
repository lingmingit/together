/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.service;

import javax.faces.model.SelectItem;

import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.sys.User;
import com.together.framework.service.base.ICoreBaseService;

/** 
 * 基础资料信息业务逻辑层操作接口类<p>
 * @author LingMin 
 * @date 2014-6-18<br>
 * @version 1.0<br>
 */
public interface IDataBaseService <E extends AbstractDataBase, ID extends java.io.Serializable> extends ICoreBaseService<E, ID>{

	/**
	 * 【启用】按钮业务逻辑层监听函数<p>
	 * @param entity 数据记录<br>
	 * @param oprter 操作者<br>
	 */
	public void enable(E entity, User oprter);
	
	/**
	 * 【禁用】按钮业务逻辑层监听函数<p>
	 * @param entity 数据记录<br>
	 * @param oprter 操作者<br>
	 */
	public void disable(E entity, User oprter);
	
	/**
	 * 根据数据记录状态获取满足条件的数据记录集合<p>
	 * @param status 数据记录状态<br>
	 * @return 数据记录集合<br>
	 */
	public java.util.List<E> getListForStatus(Boolean status);
	
	/**
	 * 根据数据状态获取满足条件的下拉列表信息集合，指定显示值字段<p>
	 * @param status 数据状态<br>
	 * @param disField 显示字段<br>
	 * @return 下拉列表信息集合<br>
	 */
	public SelectItem[] getSelectItemForStatus(Boolean status, String disField);
	
	/**
	 * 根据数据记录状态获取满足条件的数据记录集合，并将其组装为HashMap对象返回<p>
	 * @param status 数据记录状态<br>
	 * @param disField 下拉显示字段名<br>
	 * @return 数据记录集合<br>
	 */
	public java.util.Map<String, String> getHashMapForStatus(Boolean status, String disField);
}
