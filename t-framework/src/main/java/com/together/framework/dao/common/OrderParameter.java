package com.together.framework.dao.common;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.dao.enums.OrderTypeEnum;


/**
 * 排序功能基本元素对象类<p>
 * @author LingMin 
 * @date 2014-06-17<br>
 * @version 1.0<br>
 */
public class OrderParameter implements java.io.Serializable {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = -3893761098846495739L;
	/** 字段名 **/
	private String field;
	/** 排序类型 **/
	private OrderTypeEnum orderType;
	
	/**
	 * 构造函数:初始化相关参数<p>
	 * @param field 字段名<br>
	 * @param orderType 排序类型<br>
	 */
	public OrderParameter(String field, OrderTypeEnum orderType) {
		this.field = getJPQLField(field);
		this.orderType = orderType;
	}

	/**
	 * 获取字段名<p>
	 * @return 字段名<br>
	 */
	public String getField() {
		return field;
	}

	/**
	 * 设置字段名<p>
	 * @param field 字段名<br>
	 */
	public void setField(String field) {
		this.field = getJPQLField(field);
	}

	/**
	 * 获取排序类型<p>
	 * @return 排序类型<br>
	 */
	public OrderTypeEnum getOrderType() {
		return orderType;
	}

	/**
	 * 设置排序类型<p>
	 * @param orderType 排序类型<br>
	 */
	public void setOrderType(OrderTypeEnum orderType) {
		this.orderType = orderType;
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof OrderParameter))
			return false;
		final OrderParameter order = (OrderParameter) other;
		if (!order.getField().equals(getField())) return false;
		return true;
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public String toString() {
		return field.concat(" ").concat(orderType.getValue());
	}
	
	/**
	 * 重载父类方法<p>
	 */
	public int hashCode() {
		int hashCode = super.hashCode();
		// 字段名
		if (StringUtils.isNotEmpty(field)) {
			hashCode += field.hashCode();
		}
		// 排序类型
		if (CommonUtils.isNotEmptyObject(orderType)) {
			hashCode += orderType.hashCode();
		}
		return hashCode;
	}
	
	/**
	 * 重载父类方法，以别名的形式输入排序语句<p>
	 * @param entityAlias 实体对象别名<br>
	 * @return 将对象转换为字符串<br>
	 */
	public String toString(String entityAlias) {
		return entityAlias.concat(".").concat(field).concat(" ").concat(orderType.getValue());
	}
	
	/**
	 * 获取JPQL字段信息<p>
	 * @param field 字段名<br>
	 * @return 字段名<br>
	 */
	private String getJPQLField(String field) {
		String rtnStr = "";
		if (StringUtils.isNotEmpty(field)) {
			int index = field.indexOf(".");
			if (index >= 0) {
				rtnStr = field.substring(0, index + 1).concat(field.substring(index + 1, field.length()));
			}
			rtnStr = field;
		}
		return rtnStr;
	}
}
