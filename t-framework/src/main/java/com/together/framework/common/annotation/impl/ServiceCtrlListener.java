package com.together.framework.common.annotation.impl;


import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.operamasks.faces.annotation.EventListener;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.framework.common.annotation.IServiceCtrl;

/**
 * 创建ManagedBean监听器:动态注入业务逻辑层对象<p>
 * 2014-06-19 测试，该类不在web项目中，监听器不起作用，原因暂时不明确
 * @author LingMin 
 * @date 2014-06-17<br> 
 * @version 1.0<br>
 */
//@ManagedBean(scope=ManagedBeanScope.APPLICATION)
public class ServiceCtrlListener {
	/** SPRING上下文环境 **/
	private ApplicationContext appContext = null;
	
	/**
	 * AOM表现层Bean创建时的监听函数<p>
	 * @param beanName Bean名称<br>
	 * @param beanScope Bean作用域<br>
	 * @param bean Bean对象<br>
	 */
	@EventListener({"org.operamasks.faces.MANAGED_BEAN_CREATED"})
	public void beanCreated(String beanName, String beanScope, Object bean) {
		// 初始化上下文环境
		if (CommonUtils.isEmptyObject(appContext))
			appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
				(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()
			);
		// 注入业务逻辑层操作对象
		if (CommonUtils.isNotEmptyObject(bean)) {
			java.lang.reflect.Field[] fields = bean.getClass().getDeclaredFields();
			if (CommonUtils.isNotEmptyObjectArray(fields)) {
				java.util.Set<java.lang.reflect.Field> fieldSet = new java.util.HashSet<java.lang.reflect.Field>();
				for (int i = 0; i < fields.length; i ++) {
					java.lang.reflect.Field perField = fields[i];
					if (perField.isAnnotationPresent(IServiceCtrl.class)) {
						fieldSet.add(perField);
					}
				}
				// 实施注入
				if (CommonUtils.isNotEmptySet(fieldSet)) {
					for (java.lang.reflect.Field per : fieldSet) {
						per.setAccessible(true);
						String serviceName = per.getAnnotation(IServiceCtrl.class).serviceName();
						if (StringUtils.isNotEmpty(serviceName)) {
							Object service = appContext.getBean(serviceName);
							if (CommonUtils.isNotEmptyObject(service)) {
								try {
									per.set(bean, service);
								} catch (Exception ex) {
									ex.printStackTrace();
								}
							}
						}
						per.setAccessible(false);
					}
				}
			}
		}
	}
	
	/**
	 * AOM表现层Bean销毁时的监听函数<p>
	 * @param beanName Bean名称<br>
	 * @param beanScope Bean作用域<br>
	 * @param bean Bean对象<br>
	 */
	@EventListener({"org.operamasks.faces.MANAGED_BEAN_DESTROYED"})
	public void beanDestoryed(String beanName, String beanScope, Object bean) {
		// 初始化上下文环境
		if (CommonUtils.isEmptyObject(appContext))
			appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
				(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()
			);
		// 注入业务逻辑层操作对象
		if (CommonUtils.isNotEmptyObject(bean)) {
			java.lang.reflect.Field[] fields = bean.getClass().getDeclaredFields();
			if (CommonUtils.isNotEmptyObjectArray(fields)) {
				java.util.Set<java.lang.reflect.Field> fieldSet = new java.util.HashSet<java.lang.reflect.Field>();
				for (int i = 0; i < fields.length; i ++) {
					java.lang.reflect.Field perField = fields[i];
					if (perField.isAnnotationPresent(IServiceCtrl.class)) {
						fieldSet.add(perField);
					}
				}
				// 销毁注入对象
				if (CommonUtils.isNotEmptySet(fieldSet)) {
					for (java.lang.reflect.Field per : fieldSet) {
						per.setAccessible(true);
						try {
							per.set(bean, null);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						per.setAccessible(false);
					}
				}
			}
		}
	}
}
