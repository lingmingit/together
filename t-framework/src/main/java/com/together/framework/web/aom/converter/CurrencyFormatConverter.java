package com.together.framework.web.aom.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.operamasks.faces.annotation.DefineConverter;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.common.number.NumberUtils;
import com.together.framework.web.aom.converter.base.BaseConverter;

/**
 * 基于金额数据的格式化转换器<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
@DefineConverter(id = "currencyFormatConverter")
public class CurrencyFormatConverter extends BaseConverter {
	/**
	 * 页面数据转换为逻辑层数据<p>
	 */
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Object rtnO = null;
		if (StringUtils.isNotEmpty(value)) {
			rtnO = NumberUtils.getBigDecimalFromString(value.replaceAll("￥|,|\\$", ""));
		}
		return rtnO;
	}

	/**
	 * 逻辑层数据转换为页面数据<p>
	 */
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String rtnS = NumberUtils.currencyFortmat("0.0");
		if (CommonUtils.isNotEmptyObject(value) && value instanceof java.math.BigDecimal) {
			rtnS = NumberUtils.currencyFortmat(value.toString());
		}
		return rtnS;
	}
}
