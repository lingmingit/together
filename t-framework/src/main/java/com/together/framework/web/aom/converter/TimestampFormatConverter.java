package com.together.framework.web.aom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.operamasks.faces.annotation.DefineConverter;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.date.DateUtils;
import com.together.framework.web.aom.converter.base.BaseConverter;

/**
 * 日期 格式 转换器 yyyy-MM-dd 24HH:mm:ss<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@DefineConverter(id = "timestampConverter")
public class TimestampFormatConverter extends BaseConverter {
	/**
	 * 将界面上的值 转换为 对象<p>
	 */
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return StringUtils.isNotEmpty(arg2) ? DateUtils.getDateFromSpecifiedString(arg2) : null;
	}

	/**
	 * 将后台值转换为界面上显示<p>
	 */
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String rtnS = "";
		if (CommonUtils.isNotEmptyObject(arg2) && arg2 instanceof java.util.Date) {
			rtnS = DateUtils.formatDateBySpecifiedFormatter((java.util.Date) arg2,"yyyy-MM-dd HH:mm:ss");
		}
		return rtnS;
	}
}
