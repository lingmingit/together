package com.together.framework.common.enums;


import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.common.enums.base.CoreBaseEnum;



/**
 * 枚举对象的通用工具类<p>
 * @author LingMin @date 2014-06-17<br>
 * @version 1.0<br>
 */
public final class EnumUtils {
	/**
	 * 构造函数<p>
	 */
	private EnumUtils() {}
	
	/***
	 * 验证当前枚举value值 是否在排除excludeValues 数组之内
	 * @param value 排除枚举value值
	 * @param excludeValues 排除value 值数组方法
	 * @return true 是排除vlaue之内，false 不在
	 */
	private static boolean isExcludeValue(String value , String ...excludeValues){
		boolean flag = false;
		if(CommonUtils.isEmptyObjectArray(excludeValues)){
			return false;
		}
		for(String excludeValue : excludeValues){
			if(value.equals(excludeValue)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	/**
	 * 将枚举对象转换为下拉列表数组<p>
	 * @param enums 枚举对象<br>
	 * @return AOM下拉列表<br>
	 */
	public static javax.faces.model.SelectItem[] getSelectItemList(CoreBaseEnum<?, ?> perEnum , String ...excludeValues){
		javax.faces.model.SelectItem[] selectItems = null;
		if(CommonUtils.isNotEmptyObject(perEnum)){
			CoreBaseEnum<?, ?>[] enums = (CoreBaseEnum<?, ?>[]) perEnum.getEnums();
			int excludeLength = 0;
			if(CommonUtils.isNotEmptyObjectArray(excludeValues)){
				excludeLength = excludeValues.length;
			}
			selectItems = new javax.faces.model.SelectItem[enums.length - excludeLength];
			int i = 0 ;
		 	for(CoreBaseEnum<?, ?> per : enums){
		 		//验证当前枚举value值 是否在排除excludeValues 数组之内
		 		boolean flag = EnumUtils.isExcludeValue((String)per.getValue(), excludeValues);
		 		if(!flag){
		 			 selectItems[i] = new javax.faces.model.SelectItem(per.getValue(), per.getAlias());
			   		 i++;
		 		}
		 	}
		}
		return selectItems;
	}
	
	/**
	 * 将枚举对象转换为下拉列表数组<p>
	 * @param enums 枚举对象<br>
	 * @return AOM下拉列表<br>
	 */
	public static javax.faces.model.SelectItem[] getSelectItemList(CoreBaseEnum<?, ?> perEnum){
		/*javax.faces.model.SelectItem[] selectItems = null;
		if(CommonUtils.isNotEmptyObject(perEnum)){
			CoreBaseEnum<?, ?>[] enums = (CoreBaseEnum<?, ?>[]) perEnum.getEnums();
			selectItems = new javax.faces.model.SelectItem[enums.length];
			int i = 0 ;
		 	for(CoreBaseEnum<?, ?> per : enums){
		   		 selectItems[i] = new javax.faces.model.SelectItem(per.getValue(), per.getAlias());
		   		 i++;
		 	}
		}
		return selectItems;*/
		return EnumUtils.getSelectItemList(perEnum, null);
	}
	
	/**
	 * 根据枚举对象真实值获取枚举对象<p>
	 * @param enums 枚举对象<br>
	 * @param value 真实值<br>
	 * @return 枚举对象<br>
	 */
	public static CoreBaseEnum<?, ?> getValueEnum(CoreBaseEnum<?, ?> perEnum, String value) {
		CoreBaseEnum<?, ?> rtnEnum = null;
		// 判断条件是否满足
		if(StringUtils.isNotEmpty(value) && CommonUtils.isNotEmptyObject(perEnum)){
		 	for(CoreBaseEnum<?, ?> per : (CoreBaseEnum<?, ?>[]) perEnum.getEnums()){
		   		 if(value.equalsIgnoreCase((String) per.getValue())){
		   			rtnEnum = per;
	   		 		break;
		   		 }
		 	 }
		}
	   	return rtnEnum;
	}
	
	/**
	 * 根据枚举对象显示值获取枚举对象<p>
	 * @param enums 枚举对象<br>
	 * @param alias 显示值<br>
	 * @return 枚举对象<br>
	 */
	public static CoreBaseEnum<?, ?> getAliasEnum(CoreBaseEnum<?, ?> perEnum , String alias){
		CoreBaseEnum<?, ?> temp = null;
		// 判断条件是否满足
		if(StringUtils.isNotEmpty(alias) && CommonUtils.isNotEmptyObject(perEnum)){
		 	for(CoreBaseEnum<?, ?> per : (CoreBaseEnum<?, ?>[]) perEnum.getEnums()){
		   		 if(per.getAlias().equalsIgnoreCase(alias)){
	   		 		temp = per;
	   		 		break;
		   		 }
		 	 }
		}
	   	return temp;
	}
	
	/**
	 * 将枚举对象转换为HASHMAP对象<p>
	 * @param perEnum 枚举对象<br>
	 * @return HASHMAP对象<br>
	 */
	public static java.util.HashMap<String, String> getHashMapFromEnums(CoreBaseEnum<?, ?> perEnum) {
		java.util.HashMap<String, String> enumMap = null;
		if (CommonUtils.isNotEmptyObject(perEnum)) {
			enumMap = new java.util.HashMap<String, String>();
			Enum<?>[] enumList = perEnum.getEnums();
			for (Enum<?> per : enumList) {
				CoreBaseEnum<?, ?> temp = (CoreBaseEnum<?, ?>) per;
				enumMap.put(temp.getValue().toString(), temp.getAlias());
			}
		}
		return enumMap;
	}
	
	
	public static void main(String[] args) {
	}
}
