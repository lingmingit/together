package com.together.framework.web.aom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.operamasks.faces.annotation.DefineConverter;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.date.DateUtils;
import com.together.framework.web.aom.converter.base.BaseConverter;

/**
 * 日期 格式 转换器 yyyy-MM-dd<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@DefineConverter(id = "dateFormatConverter")
public class DateFormatConverter extends BaseConverter {
	/**
	 * 将界面上的值 转换为 对象<p>
	 */
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		return StringUtils.isNotEmpty(value) ? DateUtils.getDateFromSpecifiedString(value) : null;
	}

	/**
	 * 将后台值转换为界面上显示<p>
	 */
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		return CommonUtils.isNotEmptyObject(value) && value instanceof java.util.Date ?
			DateUtils.formatDateBySpecifiedFormatter((java.util.Date)value, "yyyy-MM-dd") : "";
	}
}
