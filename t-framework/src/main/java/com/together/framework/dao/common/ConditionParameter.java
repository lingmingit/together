package com.together.framework.dao.common;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.dao.enums.CompareTypeEnum;

/**
 * 查询条件的基本元素对象类<p>
 * @author LingMin 
 * @date 2014-06-17<br>
 * @version 1.0<br>
 */
public class ConditionParameter implements java.io.Serializable {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = -5285008264755290672L;
	/** 字段名 **/
	private String field;
	/** 条件值 **/
	private Object value;
	/** 实体别名 **/
	private String entityAlias = "A";
	/** 比较类型 **/
	private CompareTypeEnum compareType;
	
	/**
	 * 构造函数:初始化相关参数<p>
	 * @param field 字段名<br>
	 * @param value 条件值<br>
	 * @param compareType 比较类型<br>
	 */
	public ConditionParameter(String field, Object value, CompareTypeEnum compareType) {
		this.field = getJPQLField(field);
		this.value = value;
		this.compareType = compareType;
	}

	/**
	 * 获取字段名<p>
	 * @return 字段名<br>
	 */
	public String getField() {
		return entityAlias.concat(".").concat(field);
	}

	/**
	 * 设置字段名<p>
	 * @param field 字段名<br>
	 */
	public void setField(String field) {
		this.field = getJPQLField(field);
	}

	/**
	 * 获取条件值<p>
	 * @return 条件值<br>
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置条件值<p>
	 * @param value 条件值<br>
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 获取比较类型<p>
	 * @return 比较类型<br>
	 */
	public CompareTypeEnum getCompareType() {
		return compareType;
	}

	/**
	 * 设置比较类型<p>
	 * @param compareType 比较类型<br>
	 */
	public void setCompareType(CompareTypeEnum compareType) {
		this.compareType = compareType;
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof ConditionParameter))
			return false;
		final ConditionParameter param = (ConditionParameter) other;
		if (!param.getField().equals(getField())) return false;
		if (!param.getCompareType().equals(this.getCompareType())) return false;
		return true;
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public int hashCode() {
		int hashcode = super.hashCode();
		// 字段名
		if (StringUtils.isNotEmpty(field)) {
			hashcode += field.hashCode();
		}
		// 字段值
		if (CommonUtils.isNotEmptyObject(value)) {
			hashcode += value.hashCode();
		}
		// 比较类型
		if (CommonUtils.isNotEmptyObject(compareType)) {
			hashcode += compareType.hashCode();
		}
		return hashcode;
	}
	
	/**
	 * 重写父类方法<p>
	 */
	public String toString() {
		if (CompareTypeEnum.COMPARE_EQUEAL.equals(compareType) || CompareTypeEnum.COMPARE_NOT_EQUEAL.equals(compareType)) {
			String param = (String)value;
			if (StringUtils.isNotEmpty(param)) {
				if (param.indexOf("TO_DATE") >= 0 || param.indexOf("STR_TO_DATE") >= 0) {
					return getField().concat(" ").concat(compareType.getValue()).concat(value.toString().trim());
				} else {
					return getField().concat(" ").concat(compareType.getValue()).concat(" '").concat(value.toString().trim()).concat("'");
				}
			}
		} else if (CompareTypeEnum.COMPARE_LESS.equals(compareType) || CompareTypeEnum.COMPARE_MORE.equals(compareType)
				|| CompareTypeEnum.COMPARE_LESS_EQUEAL.equals(compareType) || CompareTypeEnum.COMPARE_MORE_EQUEAL.equals(compareType)) {
			return getField().concat(" ").concat(compareType.getValue()).concat(" ").concat(value.toString().trim());
		} else if (CompareTypeEnum.COMPARE_LIKE.equals(compareType)) {
			return getField().concat(" LIKE '%").concat(value.toString().trim()).concat("%'");
		} else if (CompareTypeEnum.COMPARE_LEFT_LIKE.equals(compareType)) {
			return getField().concat(" LIKE '%").concat(value.toString().trim()).concat("'");
		} else if (CompareTypeEnum.COMPARE_RIGHT_LIKE.equals(compareType)) {
			return getField().concat(" LIKE '").concat(value.toString().trim()).concat("%'");
		} else if (CompareTypeEnum.COMPARE_NOT_IN.equals(compareType) || CompareTypeEnum.COMPARE_IN.equals(compareType) && value instanceof Object[]) {
			StringBuffer rtnBuf = new StringBuffer();
			Object[] tempArray = (Object[]) value;
			if (CommonUtils.isNotEmptyObjectArray(tempArray)) {
				rtnBuf.append(field).append(" ").append(compareType.getValue()).append(" (");
				for (int i = 0; i < tempArray.length; i ++) {
					String condition = CommonUtils.isNotEmptyObject(tempArray[i]) ? (String) tempArray[i] : "XXXX";
					if (i == 0) {
						rtnBuf.append("'").append(condition).append("'");
					} else {
						rtnBuf.append(" ,").append("'").append(condition).append("'");
					}
				}
				rtnBuf.append(")");
			}
			return rtnBuf.toString();
		} else if (CompareTypeEnum.COMPARE_IS_NULL.equals(compareType)) {
			return getField().concat(" ").concat(compareType.getValue());
		}
		return "";
	}
	
	/**
	 * 重载父类方法<p>
	 * @param entityAlias 实体对象别名<br>
	 * @return 查询条件字符串<br>
	 */
	public String toString(String entityAlias) {
		this.entityAlias = entityAlias;
		if (CompareTypeEnum.COMPARE_EQUEAL.equals(compareType) || CompareTypeEnum.COMPARE_NOT_EQUEAL.equals(compareType)) {
			String param = (String)value;
			if (StringUtils.isNotEmpty(param)) {
				if (param.indexOf("TO_DATE") >= 0 || param.indexOf("STR_TO_DATE") >= 0) {
					return getField().concat(" ").concat(compareType.getValue()).concat(value.toString().trim());
				} else {
					return getField().concat(" ").concat(compareType.getValue()).concat(" '").concat(value.toString().trim()).concat("'");
				}
			}
		} else if (CompareTypeEnum.COMPARE_LESS.equals(compareType) || CompareTypeEnum.COMPARE_MORE.equals(compareType)
				|| CompareTypeEnum.COMPARE_LESS_EQUEAL.equals(compareType) || CompareTypeEnum.COMPARE_MORE_EQUEAL.equals(compareType)) {
			return getField().concat(" ").concat(compareType.getValue()).concat(" ").concat(value.toString().trim());
		} else if (CompareTypeEnum.COMPARE_LIKE.equals(compareType)) {
			return getField().concat(" LIKE '%").concat(value.toString().trim()).concat("%'");
		} else if (CompareTypeEnum.COMPARE_LEFT_LIKE.equals(compareType)) {
			return getField().concat(" LIKE '%").concat(value.toString().trim()).concat("'");
		} else if (CompareTypeEnum.COMPARE_RIGHT_LIKE.equals(compareType)) {
			return getField().concat(" LIKE '").concat(value.toString().trim()).concat("%'");
		} else if (CompareTypeEnum.COMPARE_NOT_IN.equals(compareType) || CompareTypeEnum.COMPARE_IN.equals(compareType) && value instanceof Object[]) {
			StringBuffer rtnBuf = new StringBuffer();
			Object[] tempArray = (Object[]) value;
			if (CommonUtils.isNotEmptyObjectArray(tempArray)) {
				rtnBuf.append(getField()).append(" ").append(compareType.getValue()).append(" (");
				for (int i = 0; i < tempArray.length; i ++) {
					String condition = CommonUtils.isNotEmptyObject(tempArray[i]) ? (String) tempArray[i] : "XXXX";
					if (i == 0) {
						rtnBuf.append("'").append(condition).append("'");
					} else {
						rtnBuf.append(" ,").append("'").append(condition).append("'");
					}
				}
				rtnBuf.append(")");
			}
			return rtnBuf.toString();
		} else if (CompareTypeEnum.COMPARE_IS_NULL.equals(compareType)) {
			return getField().concat(" ").concat(compareType.getValue());
		}
		return "";
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
			} else {
				rtnStr = field;
			}
		}
		return rtnStr;
	}
}
