package com.together.framework.web.aom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.operamasks.faces.annotation.DefineConverter;

import com.together.common.CommonUtils;
import com.together.common.number.NumberUtils;

/**
 * BigDecima小数金额 转换器<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@DefineConverter(id = "bigDecimalConverter")
public class BigDecimalConverter implements Converter{
	/**
	 * 将界面上的值 转换为 对象<p>
	 */
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		return NumberUtils.getBigDecimalForCustomizePattern("###,##0.00######", NumberUtils.getLegalNumeralString(value));
	}

	/**
	 * 将后台转换为界面上显示<br>
	 */
	public String getAsString(FacesContext context, UIComponent component,Object value) {
		String rtnS = "";
		if (CommonUtils.isNotEmptyObject(value)) {
			if (value instanceof String) {
				rtnS = NumberUtils.customizeNumberFormatForString("###,##0.00######", NumberUtils.getLegalNumeralString((String)value));
			} else if (value instanceof java.math.BigDecimal) {
				rtnS = NumberUtils.customizeNumberFormatForBigDecimal("###,##0.00######", (java.math.BigDecimal) value);
			}
		}
		return rtnS;
	}
}