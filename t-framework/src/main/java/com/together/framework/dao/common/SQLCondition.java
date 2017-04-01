package com.together.framework.dao.common;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;


/**
 * 基于SQL的配置参数信息类<p>
 * @author LingMin 
 * @date 2014-06-17<br>
 * @version 1.0<br>
 */
public class SQLCondition implements java.io.Serializable {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 4484163698730361526L;
	/** 查询条件映射字符串 **/
	private String maskString = "";
	/** 实体对象别名 **/
	private String entityAlias = "A";
	/** 原味JPQL语句 **/
	private String originalHQL = "";
	/** 分组字段容器 **/
	private java.util.HashSet<String> groups = null;
	/** 排序收集容器 **/
	private java.util.List<OrderParameter> orders = null;
	/** 查询条件收集容器 **/
	private java.util.List<ConditionParameter> conditions = null;
	
	/**
	 * 构造函数<p>
	 */
	public SQLCondition() {}
	
	/**
	 * 追加不进行转换的JPQL语句<p>
	 * @param originalHQL 原味JPQL语句<br>
	 */
	public void put(String originalHQL) {
		this.originalHQL = originalHQL;
	}
	
	/**
	 * 收集排序信息<p>
	 * @param order 排序对象<br>
	 */
	public void put(OrderParameter order) {
		if (CommonUtils.isEmptyObject(orders))
			orders = new java.util.ArrayList<OrderParameter>();
		if (CommonUtils.isNotExistenceList(orders, order))
			orders.add(order);
	}
	
	/**
	 * 收集查询条件信息<p>
	 * @param param 查询条件对象<br>
	 */
	public void put(ConditionParameter param) {
		if (CommonUtils.isEmptyObject(conditions))
			conditions = new java.util.ArrayList<ConditionParameter>();
		if (CommonUtils.isExistenceList(conditions, param)) {
			for (Object temp : conditions) {
				if (temp.equals(param)) {
					conditions.remove(temp);
					break;
				}
			}
		}
		conditions.add(param);
	}
	
	/**
	 * 删除排序信息<p>
	 * @param order 排序对象<br>
	 */
	public void remove(OrderParameter order) {
		// 删除指定的排序对象
		if (CommonUtils.isNotEmptyList(orders) && CommonUtils.isExistenceList(orders, order)) {
			orders.remove(order);
		}
	}
	
	/**
	 * 删除查询条件信息<p>
	 * @param param 查询条件信息<br>
	 */
	public void remove(ConditionParameter param) {
		// 删除指定的排序对象
		if (CommonUtils.isNotEmptyList(conditions) && CommonUtils.isExistenceList(conditions, param)) {
			conditions.remove(param);
		}
	}
	
	/**
	 * 收集分组字段信息<p>
	 * @param field 分组字段<br>
	 */
	public void putGroupField(String field) {
		if (CommonUtils.isEmptyObject(groups)) {
			groups = new java.util.HashSet<String>();
		}
		groups.add(field);
	}
	
	/**
	 * SQL的配置参数信息进行合并<p>
	 * @param condition SQL的配置参数信息<br>
	 */
	public SQLCondition merge(SQLCondition condition) {
		// 融合排序信息
		java.util.List<OrderParameter> orderList = condition.getOrders();
		if (CommonUtils.isNotEmptyList(orderList)) {
			for (int i = 0; i < orderList.size(); i ++) {
				put(orderList.get(i));
			}
		}
		// 融合查询条件
		java.util.List<ConditionParameter> conditionList = condition.getConditions();
		if (CommonUtils.isNotEmptyList(conditionList)) {
			for (int j = 0; j < conditionList.size(); j ++) {
				put(conditionList.get(j));
			}
		}
		// 融合分组信息
		java.util.HashSet<String> container = condition.getGroupField();
		if (CommonUtils.isNotEmptySet(container)) {
			java.util.Iterator<String> iterator = container.iterator();
			while(iterator.hasNext()) {
				putGroupField(iterator.next());
			}
		}
		// 融合原味SQL语句
		if (StringUtils.isNotEmpty(condition.originalHQL) && this.originalHQL.indexOf(condition.originalHQL) < 0) {
			this.originalHQL = this.originalHQL.concat(condition.originalHQL);
		}
		return this;
	}
	
	/**
	 * 获取分组信息<p>
	 * @return 分组信息<br>
	 */
	public java.util.HashSet<String> getGroupField() {
		return groups;
	}
	
	/**
	 * 获取排序的信息<p>
	 * @return 排序信息<br>
	 */
	public java.util.List<OrderParameter> getOrders() {
		return orders;
	}

	/**
	 * 获取查询条件信息<p>
	 * @return 查询对象<br>
	 */
	public java.util.List<ConditionParameter> getConditions() {
		return conditions;
	}

	/**
	 * 清空SQL的配置参数信息<p>
	 */
	public void clear() {
		this.maskString = "";
		this.originalHQL = "";
		this.entityAlias = "A";
		if (CommonUtils.isNotEmptyObject(orders)) orders.clear();
		if (CommonUtils.isNotEmptyObject(groups)) groups.clear();
		if (CommonUtils.isNotEmptyObject(conditions)) conditions.clear();
	}
	
	/**
	 * 设置查询条件映射字符串<p>
	 * @param mask 查询条件映射字符串<br>
	 */
	public void setMaskString(String mask) {
		this.maskString = mask;
	}
	
	/**
	 * 获取查询条件<p>
	 * @return 查询条件<br>
	 */
	public String getSearchCondition() {
		StringBuffer condition = new StringBuffer(" ");
		// 组装查询条件
		condition.append(entityAlias);
		if (StringUtils.isNotEmpty(maskString)) {
			if (CommonUtils.isNotEmptyList(conditions)) {
				condition.append(" WHERE ");
				for (int i = 0; i < conditions.size(); i ++) {
					String tempIndex = "#".concat(String.valueOf(i + 1));
					if (maskString.indexOf(tempIndex) >= 0) {
						maskString = StringUtils.replaceAll(maskString, tempIndex, conditions.get(i).toString(entityAlias));
					} else {
						maskString = maskString.concat("AND ").concat(conditions.get(i).toString(entityAlias)).concat(" ");
					}
				}
				condition.append(maskString);
			}
		} else {
			if (CommonUtils.isNotEmptyList(conditions)) {
				condition.append(" WHERE ");
				for (int j = 0; j < conditions.size(); j ++) {
					if (j == 0) {
						condition.append(conditions.get(j).toString(entityAlias)).append(" ");
					} else {
						condition.append("AND ").append(conditions.get(j).toString(entityAlias)).append(" ");
					}
				}
			}
		}
		// 组装原味JPQL语句
		if (CommonUtils.isNotEmptyList(conditions)) {
			if(StringUtils.isNotEmpty(originalHQL)) {
				condition.append(" AND ").append(StringUtils.replaceAll(originalHQL, "@EntityAilias", entityAlias));
			}
		} else {
			if(StringUtils.isNotEmpty(originalHQL)) {
				condition.append(" WHERE ").append(StringUtils.replaceAll(originalHQL, "@EntityAilias", entityAlias));
			}
		}
		return condition.toString();
	}
	
	/**
	 * 获取实体对象别名<p>
	 * @return 实体对象别名<br>
	 */
	public String getEntityAlias() {
		return entityAlias;
	}

	/**
	 * 设置实体对象别名<p>
	 * @param entityAlias 实体对象别名<br>
	 */
	public void setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SQLCondition))
			return false;
		// 属性比较
		if (!CommonUtils.isNotEmptyObject(obj) && obj.toString().equals(toString())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public int hashCode() {
		return toString().hashCode();
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public String toString() {
		StringBuffer condition = new StringBuffer(" ");
		// 组装查询条件
		condition.append(entityAlias);
		// 组装查询条件
		if (StringUtils.isNotEmpty(maskString)) {
			if (CommonUtils.isNotEmptyList(conditions)) {
				condition.append(" WHERE ");
				for (int i = 0; i < conditions.size(); i ++) {
					String tempIndex = "#".concat(String.valueOf(i + 1));
					if (maskString.indexOf(tempIndex) >= 0) {
						maskString = StringUtils.replaceAll(maskString, tempIndex, conditions.get(i).toString(entityAlias));
					} else {
						maskString = maskString.concat("AND ").concat(conditions.get(i).toString(entityAlias)).concat(" ");
					}
				}
				condition.append(maskString);
			}
		} else {
			if (CommonUtils.isNotEmptyList(conditions)) {
				condition.append(" WHERE ");
				for (int j = 0; j < conditions.size(); j ++) {
					if (j == 0) {
						condition.append(conditions.get(j).toString(entityAlias)).append(" ");
					} else {
						condition.append("AND ").append(conditions.get(j).toString(entityAlias)).append(" ");
					}
				}
			}
		}
		// 组装原味JPQL语句
		if (CommonUtils.isNotEmptyList(conditions)) {
			if(StringUtils.isNotEmpty(originalHQL)) {
				condition.append(" AND ").append(StringUtils.replaceAll(originalHQL, "@EntityAilias", entityAlias));
			}
		} else {
			if(StringUtils.isNotEmpty(originalHQL)) {
				condition.append(" WHERE ").append(StringUtils.replaceAll(originalHQL, "@EntityAilias", entityAlias));
			}
		}
		// 组装分组信息
		if (CommonUtils.isNotEmptySet(groups)) {
			java.util.Iterator<String> iterator = groups.iterator();
			boolean first = true;
			while(iterator.hasNext()) {
				if (first) {
					condition.append(" group by ").append(entityAlias.concat(".").concat(iterator.next().toString()));
					first = false;
				} else {
					condition.append(", ").append(entityAlias.concat(".").concat(iterator.next().toString())).append(" ");
				}
			}
		}
		// 组装排序信息
		if (CommonUtils.isNotEmptyList(orders)) {
			for (int k = 0; k < orders.size(); k ++) {
				if (k == 0) {
					condition.append(" ORDER BY ").append(orders.get(k).toString(entityAlias));
				} else {
					condition.append(", ").append(orders.get(k).toString(entityAlias));
				}
			}
		}
		return condition.toString();
	}
}
