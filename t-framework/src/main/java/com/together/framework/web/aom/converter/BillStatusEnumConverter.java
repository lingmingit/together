package com.together.framework.web.aom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.operamasks.faces.annotation.DefineConverter;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.common.enums.EnumUtils;
import com.together.framework.dao.enums.BillStatusEnum;

/**
 * 单据状态对象转换器<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@DefineConverter(id = "billStatusEnumConverter")
public class BillStatusEnumConverter implements Converter {
	/**
	 * 将界面上的值 转换为 对象<p>
	 */
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Object rtnO = null;
		if (StringUtils.isNotEmpty(arg2)) {
			rtnO = ((BillStatusEnum) EnumUtils.getAliasEnum(
				BillStatusEnum.ADD_NEW_STATUS, arg2)
			);
		}
		return rtnO;
	}

	/**
	 * 将后台转换为界面上显示<br>
	 */
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String rtnS = "";
		if (CommonUtils.isNotEmptyObject(arg2) && arg2 instanceof String) {
			rtnS = ((BillStatusEnum) EnumUtils.getValueEnum(
				BillStatusEnum.ADD_NEW_STATUS, (String) arg2)
			).getAlias();
		}
		return rtnS;
	}
}
