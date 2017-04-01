package com.together.framework.web.aom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.operamasks.faces.annotation.DefineConverter;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.common.enums.EnumUtils;
import com.together.framework.web.enums.PageStatusEnum;

/**
 * 页面状态转换器实现类<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@DefineConverter(id = "pageStatusConverter")
public class PageStatusEnumConverter implements Converter {
	/**
	 * 将界面上的值 转换为 对象<p>
	 */
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Object rtnO = PageStatusEnum.PAGE_EXCEPTION;
		if(StringUtils.isNotEmpty(arg2)) {
			rtnO = EnumUtils.getValueEnum(PageStatusEnum.PAGE_ADDNEW, arg2);
		}
		return rtnO;
	}

	/**
	 * 将后台值转换为界面上显示<p>
	 */
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String rtnS = PageStatusEnum.PAGE_EXCEPTION.getValue();
		if (CommonUtils.isNotEmptyObject(arg2) || arg2 instanceof PageStatusEnum) {
			rtnS = ((PageStatusEnum) arg2).getValue();
		}
		return rtnS;
	}
}
