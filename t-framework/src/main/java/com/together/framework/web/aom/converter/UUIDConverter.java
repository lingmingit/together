package com.together.framework.web.aom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.operamasks.faces.annotation.DefineConverter;

import com.together.common.CommonUtils;
import com.together.framework.web.aom.converter.base.BaseConverter;

/**
 * 关键字对象转换器<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@DefineConverter(id = "UUIDConverter")
public class UUIDConverter extends BaseConverter {
	/**
	 * 将界面上的值 转换为 对象<p>
	 */
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2;
	}

	/**
	 * 将后台转换为界面上显示<br>
	 */
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String rtnS = "";
		if (CommonUtils.isNotEmptyObject(arg2) && arg2 instanceof String) {
			rtnS = (String) arg2;
		}
		return rtnS;
	}

}
