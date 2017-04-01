package com.together.framework.web.aom.converter.base;

import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 基于AOM的页面转换器基类<p>
 * @author LingMin 
 * @date 2014-10-31<br>
 * @version 1.0<br>
 */
public abstract class BaseConverter implements Converter {

	/** 上下文环境 **/
	private WebApplicationContext applicationContext;
	
	/**
	 * 构造函数:初始化上下文环境<p>
	 */
	public BaseConverter() {
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
	}
	
	/**
	 * 根据BEAN配置名与CLASS名获取SPRING中的BEAN对象<p>
	 * @param BEAN配置名<br>
	 * @return BEAN对象<br>
	 */
	public Object getSpringBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据BEAN的CLASS对象获取SPRING中的BEAN对象<p>
	 * @param beanClass BEAN的CLASS对象<br>
	 * @return BEAN对象<br>
	 */
	public Object getSpringBean(Class<?> beanClass) {
		return BeanFactoryUtils.beanOfTypeIncludingAncestors(applicationContext, beanClass);
	}
}
