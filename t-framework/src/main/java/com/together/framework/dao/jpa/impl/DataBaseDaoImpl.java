package com.together.framework.dao.jpa.impl;

import com.together.common.CommonUtils;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.common.OrderParameter;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.dao.enums.OrderTypeEnum;
import com.together.framework.dao.jpa.IDataBaseDao;
import com.together.framework.dao.jpa.base.impl.CoreBaseDaoImpl;
import com.together.framework.entity.AbstractDataBase;
import com.together.framework.entity.sys.User;


/***
 * 基础资料信息数据库层操作接口实现类<p>
 * @author LingMin 
 * @date 2014-6-18<br>
 * @version 1.0<br>
 */
public class DataBaseDaoImpl<E extends AbstractDataBase, ID extends java.io.Serializable , DAOImpl extends IDataBaseDao<E, ID>> extends CoreBaseDaoImpl<E, ID, DAOImpl> implements IDataBaseDao<E, ID> {

	
	/**
	 * 【启用】按钮数据库层监听函数<p>
	 */
	public void enable(E entity, User oprter) {
		entity.setModifier(oprter);
		entity.setIsEnable(Boolean.TRUE);
		entity.setModifyTime(new java.util.Date());
		this.update(entity);
	}
	
	/**
	 * 【禁用】按钮数据库层监听函数<p>
	 */
	public void disable(E entity, User oprter) {
		entity.setModifier(oprter);
		entity.setIsEnable(Boolean.FALSE);
		entity.setModifyTime(new java.util.Date());
		this.update(entity);
	}
	
	/**
	 * 根据数据记录状态获取满足条件的数据记录集合<p>
	 */
	public java.util.List<E> getListForStatus(Boolean status) {
		SQLCondition condition = new SQLCondition();
		// 设置排序条件
		condition.put(new OrderParameter("numbers", OrderTypeEnum.ORDER_ASC));
		// 组装查询条件
		if (CommonUtils.isNotEmptyObject(status)) {
			condition.put(new ConditionParameter("isEnable", CommonUtils
					.getStringFromBoolean(status), CompareTypeEnum.COMPARE_EQUEAL));
		}
		return list(condition);
	}
}
