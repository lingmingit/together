/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.service.impl;

import javax.faces.model.SelectItem;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.framework.dao.jpa.IDataBaseDao;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.sys.User;
import com.together.framework.service.IDataBaseService;
import com.together.framework.service.base.impl.CoreBaseServiceImpl;

/** 
 * 基础资料信息业务逻辑层操作接口实现类<p>
 * @author LingMin 
 * @date 2014-6-18<br>
 * @version 1.0<br>
 */
public class DataBaseServiceImpl<E extends AbstractDataBase, ID extends java.io.Serializable> extends CoreBaseServiceImpl<E, ID> implements IDataBaseService<E, ID> {
	/** 基础资料信息数据库层操作对象 **/
	protected IDataBaseDao<E, ID> dataBaseDao;
	
	/**
	 * 构造函数:初始化数据库层操作对象<p>
	 * @param baseDataDao 数据库层操作对象<br>
	 */
	public DataBaseServiceImpl(IDataBaseDao<E, ID> dataBaseDao) {
		super(dataBaseDao);
		this.dataBaseDao = dataBaseDao;
	}
	
	/**
	 * 【启用】按钮业务逻辑层监听函数<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	//@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void enable(E entity, User oprter) {
		dataBaseDao.enable(entity, oprter);
	}
	
	/**
	 * 【禁用】按钮业务逻辑层监听函数<p>
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	//@CacheEvict(value="com.hgmk.hibernateCache",allEntries=true)
	public void disable(E entity, User oprter) {
		dataBaseDao.disable(entity, oprter);
	}

	/**
	 * 根据数据记录状态获取满足条件的数据记录集合<p>
	 */
	public java.util.List<E> getListForStatus(Boolean status) {
		return dataBaseDao.getListForStatus(status);
	}
	
	/**
	 * 根据数据状态获取满足条件的下拉列表信息集合，指定显示值字段<p>
	 */
	public SelectItem[] getSelectItemForStatus(Boolean status, String disField) {
		// 初始化返回值
		java.util.List<SelectItem> itemList = null; 
		// 根据条件查询数据库
		if (StringUtils.isNotEmpty(disField)) {
			java.util.List<E> rsList = getListForStatus(status);
			if (CommonUtils.isNotEmptyList(rsList)) {
				itemList = new java.util.ArrayList<SelectItem>();
				for (E record : rsList) {
					String id = (String) ReflectionUtils.invokeMethod(record, "getId", null, null);
					String name = (String) ReflectionUtils.invokeMethod(
						record, "get".concat(StringUtils.firstCharToUpperCase(disField)), null, null
					);
					itemList.add(new SelectItem(id, StringUtils.getLegalString(name), null));
				}
			}
		}
		return CommonUtils.isNotEmptyList(itemList) ? itemList.toArray(new SelectItem[0]) : null;
	}

	/**
	 * 根据数据记录状态获取满足条件的数据记录集合，并将其组装为HashMap对象返回<p>
	 */
	public java.util.Map<String, String> getHashMapForStatus(Boolean status, String disField) {
		java.util.HashMap<String, String> rtnHM = null;
		// 根据数据记录状态获取满足条件的数据记录集合
		java.util.List<E> resultList = dataBaseDao.getListForStatus(status);
		if (CommonUtils.isNotEmptyList(resultList)) {
			rtnHM = new java.util.HashMap<String, String>();
			for (E record : resultList) {
				String id = (String) ReflectionUtils.invokeMethod(record, "getId", null, null);
				String name = (String) ReflectionUtils.invokeMethod(
					record, "get".concat(StringUtils.firstCharToUpperCase(disField)), null, null
				);
				rtnHM.put(id, StringUtils.getLegalString(name));
			}
		}
		return rtnHM;
	}
}