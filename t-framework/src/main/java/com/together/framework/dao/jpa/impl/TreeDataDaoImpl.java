package com.together.framework.dao.jpa.impl;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.dao.common.ConditionParameter;
import com.together.framework.dao.common.OrderParameter;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.dao.enums.CompareTypeEnum;
import com.together.framework.dao.enums.OrderTypeEnum;
import com.together.framework.dao.jpa.ITreeDataDao;
import com.together.framework.entity.AbstractTreeData;


/**
 * 树型基础资料数据库层操作接口类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public class TreeDataDaoImpl<E extends AbstractTreeData, ID extends java.io.Serializable, DAOImpl extends ITreeDataDao<E, ID>> extends DataBaseDaoImpl<E, ID, DAOImpl> implements ITreeDataDao<E, ID> {
	/**
	 * 根据结点关键字查询该结点下叶子结点信息<p>
	 */
	public java.util.List<E> getChildrenNodeList(final ID parentKey, final String mappedField) {
		// 合法性验证
		SQLCondition condition = new SQLCondition();
		// 组装查询条件
		if (StringUtils.isEmpty((String) parentKey)) {
			condition.put(new ConditionParameter((StringUtils
					.isNotEmpty(mappedField) ? mappedField : "parent")
					.concat(".id"), null, CompareTypeEnum.COMPARE_IS_NULL));
		} else {
			condition.put(new ConditionParameter((StringUtils
					.isNotEmpty(mappedField) ? mappedField : "parent")
					.concat(".id"), parentKey, CompareTypeEnum.COMPARE_EQUEAL));
		}
		// 设置排序字段
		condition.put(new OrderParameter("numbers", OrderTypeEnum.ORDER_ASC));
		// 执行数据库查询
		return list(condition);
	}
	
	/**
	 * 根据结点关键字与状态信息查询该结点下所有叶子结点信息，包含叶子结点下的叶子结点<p>
	 */
	public java.util.List<E> getDeepestStatusChildrenNodeList(final ID parentKey, final Boolean status, final boolean hasCurrentNode) {
		SQLCondition condition = new SQLCondition();
		// 根据结点关键字组装查询条件
		if (StringUtils.isNotEmpty((String) parentKey)) {
			E entry = get(parentKey);
			if (CommonUtils.isNotEmptyObject(entry)) {
				// 获取结点长编码
				String longNum = entry.getLongNumber();
				if (StringUtils.isNotEmpty(longNum)) {
					// 根据是否包含当前结点标志构造查询条件
					if (hasCurrentNode) {
						condition.put(new ConditionParameter("longNumber", longNum, CompareTypeEnum.COMPARE_RIGHT_LIKE));
					} else {
						condition.put(new ConditionParameter("longNumber",
							longNum.concat(FrameworkConstants.TREE_LONGNUM_SEPARATOR), CompareTypeEnum.COMPARE_RIGHT_LIKE)
						);
					}
				} else {
					if (!hasCurrentNode) {
						condition.put(new ConditionParameter("id", parentKey, CompareTypeEnum.COMPARE_NOT_EQUEAL));
					}
				}
			}
		}
		// 根据结点状态组装查询条件
		if (CommonUtils.isNotEmptyObject(status)) {
			condition.put(
				new ConditionParameter("isEnable", CommonUtils .getStringFromBoolean(status), CompareTypeEnum.COMPARE_EQUEAL)
			);
		}
		// 设置排序字段
		condition.put(new OrderParameter("numbers", OrderTypeEnum.ORDER_ASC));
		// 执行数据库查询
		return list(condition);
	}
}