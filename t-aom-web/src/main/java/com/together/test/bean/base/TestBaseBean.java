package com.together.test.bean.base;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.operamasks.faces.annotation.Accessible;
import org.springframework.web.context.support.WebApplicationContextUtils;



/***
 * 测试Bean 继承基类后，就不能 及时刷新发布（不用重启服务）<p>
 * @author LingMin 
 * @date 2013-9-11<br>
 * @version 1.0<br>
 */
public abstract class TestBaseBean {

	/** 当前会话参数容器 **/
	private HttpSession session = null;
	/**AOM上下文环境**/
	private javax.faces.context.ExternalContext context = null;
	/**JSF上下文环境**/
	private javax.faces.context.FacesContext facesContext = null;
	/** 页面请求对象 **/
	private HttpServletRequest request = null;
	/** 页面响应对象 **/
	private HttpServletResponse response = null;
	/** SPRING上下文环境 **/
	private org.springframework.context.ApplicationContext applicationContext = null;
	/** 日志书写对象 **/
	protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	/**工程相对路径 **/
	@Accessible
	protected java.lang.String contextPath = "";
	/**
	 * 构造函数:初始化相关参数<p>
	 */
	public TestBaseBean() {
		/** 页面状态 **/
		// JSF上下文环境
		facesContext = FacesContext.getCurrentInstance();
		// AOM上下文环境
		context = facesContext.getExternalContext();
		// SESSION会话对象
		session = (javax.servlet.http.HttpSession) context.getSession(false);
		// 页面请求对象
		request = (javax.servlet.http.HttpServletRequest) context.getRequest();
		// 工程相对路径
		contextPath = (java.lang.String) request.getContextPath();
		// 页面响应对象  
		response = (javax.servlet.http.HttpServletResponse) context.getResponse();
		// SPRING上下文环境
		applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext((javax.servlet.ServletContext) context.getContext());
	}
	
	
	public void test(){
		  
	}
	
}
