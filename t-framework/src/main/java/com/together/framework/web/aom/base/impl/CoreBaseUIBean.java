/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.web.aom.base.impl;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.annotation.BeforeRender;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.Inject;
import org.operamasks.faces.annotation.LocalString;
import org.operamasks.faces.user.ajax.PartialUpdateManager;
import org.operamasks.faces.user.util.Browser;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.together.common.StringUtils;
import com.together.common.file.FileUtils;
import com.together.common.file.PropertiesUtils;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.dao.enums.DatabaseEnum;
import com.together.framework.entity.sys.User;
import com.together.framework.web.aom.base.ICoreBaseUI;

/** 
 * AOM 基础核心liteBean基类<p>
 * @author LingMin 
 * @date 2014-6-27<br>
 * @version 1.0<br>
 */
@SuppressWarnings("deprecation")
public abstract class CoreBaseUIBean implements ICoreBaseUI , java.io.Serializable {
	
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = -8295938757103846679L;

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
	
	/***局部更新 管理**/
	@Inject
	protected PartialUpdateManager partialUpdate;
	/** 页面状态 **/
	@Bind
	private String pageStatus;
	
	/** 资源文件路径  basename  默认 src目录下，可以增加目录结构，如：resource.LocalStrings **/
	@LocalString(basename="LocalStrings")
	protected java.util.Map<String, String> messages;
	
	
	/**
	 * 构造函数:初始化相关参数<p>
	 */
	public CoreBaseUIBean() {
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
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext((javax.servlet.ServletContext) context.getContext());
	}
	
	/**
	 * 初始化下拉列表信息<p>
	 */
	public void initSelectItemList() { }
	

	/**
	 * 渲染页面前进行一些值的初始化<p>
	 * @param isPostBack 是否Post方式请求页面<br>
	 */
	@BeforeRender
	protected void beforeRender(boolean isPostBack){
		if(!isPostBack){
			try {
				// 页面渲染之前调用
				beforePageOnLoad();
				// 页面渲染调用函数
				pageOnLoad();
				// 页面渲染滞后调用
				afterPageOnLoad();
			} catch (Exception ex) {
				logger.error(ex);
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化页面<p>
	 */
	public void pageOnLoad() {
		// 初始化下拉控件
		initSelectItemList();
		// 初始化页面控件
		initPageComponent();
	}
	
	/**
	 * 页面渲染之前调用的函数<p>
	 */
	public void beforePageOnLoad() {
	}
	
	/**
	 * 页面渲染滞后调用的函数<p>
	 */
	public void afterPageOnLoad() { }
	
	/**
	 * 初始化页面控件信息<p>
	 */
	public void initPageComponent() {
		// 查询数据库的权限信息隐藏页面按钮
	}
	
	
	
	/**
	 * 页面跳转功能<p>
	 * @param url 页面路径<br>
	 */
	public void forwardURL(String url) {
		Browser.execClientScript(new StringBuffer("window.location.href='")
				.append(url).append("'").toString());
	}
	
	
	
	/**
	 * 获取当前登录的合法用户Id<p>
	 * @return 当前登录用户Id<br>
	 */
	public String getCurrentSessionUserId(){
		// 查询当前登录用户信息
		return (String) this.session.getAttribute(FrameworkConstants.CURRENT_SESSION_USER_KEY);
	}
	
	/**
	 * 获取当前登录的合法用户信息<p>
	 * @return 当前登录用户信息<br>
	 */
	public User getCurrentSessionUser() {
		User rtnO = null;
		// 查询当前登录用户信息
		String user_key = this.getCurrentSessionUserId();
		// 合法性验证
		if (StringUtils.isNotEmpty(user_key)) {
			//rtnO = getUserLogicBean().get(user_key);
		}
		return rtnO;
	}
	
	
	/**
	 * 根据BEAN配置名获取SPRING中的BEAN对象<p>
	 */
	public Object getSpringBean(String beanName) {
		return getApplicationContext().getBean(beanName);
	}
	
	/**
	 * 根据BEAN的CLASS对象获取SPRING中的BEAN对象<p>
	 * @param beanClass BEAN的CLASS对象<br>
	 * @return BEAN对象<br>
	 */
	public Object getSpringBean(Class<?> beanClass) {
		return BeanFactoryUtils.beanOfTypeIncludingAncestors(getApplicationContext(), beanClass);
	}
	
	
	/**
	 * 获取当前系统的数据库类型<p>
	 * @return 数据库类型 <br>
	 */
	public DatabaseEnum getCurrentDatabase() {
		DatabaseEnum database = DatabaseEnum.DATABASE_MYSQL;
		// 获取系统配置参数
//		String data_type = null;//getSystemConfigParam("DATABASE_TYPE");
//		if (StringUtils.isNotEmpty(data_type)) {
//			database = (DatabaseEnum) EnumUtils.getValueEnum(DatabaseEnum.DATABASE_ORACLE, data_type);
//		}
		return database;
	}
	
	
	/**
	 * 获取泛型参数对象的Class对象<p>
	 * @param 泛型参数序列<br>
	 * @return 实体对象的Class对象<br>
	 */
	public Class<?> getEntityModelClass(int index) {
		return (Class<?>) ((java.lang.reflect.ParameterizedType) this
				.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	
	/**
	 * 获取系统配置参数信息<p>
	 */
	public String getPropertiesValue(String fullPackagePath , String fileName  , String key) {
		return PropertiesUtils.getInputStreamPropertiesValue(fullPackagePath , fileName , key);
	}
	
	/**
	 * 获取请求对象REQUEST中的参数值<p>
	 * @param key 参数索引<br>
	 * @return 参数值<br>
	 */
	public Object getRequestParameter(String key) {
		return getRequest().getParameter(key);
	}
	
	/**
	 * 根据指定的索引值从SESSION会话中获取对应的真实值<p>
	 * @param key 索引值<br>
	 * @return 真实值<br>
	 */
	public Object getSessionAttribute(String key) {
		return getSession().getAttribute(getFunctionName().concat(".").concat(key));
	}
	
	
	/**
	 * 删除SESSION会话中指定索引值的真实值<p>
	 * @param key 索引值<br>
	 */
	public void removeSessionAttribute(String key) {
		if (StringUtils.isNotEmpty(key)) {
			setSessionAttribute(key, null);
			getSession().removeAttribute(getFunctionName().concat(".").concat(key));
		}
	}
	/**
	 * 向SESSION会话中设值<p>
	 * @param key 索引值<br>
	 * @param value 真实值<br>
	 */
	public void setSessionAttribute(String key, Object value) {
		if (StringUtils.isNotEmpty(key)) {
			getSession().setAttribute(getFunctionName().concat(".").concat(key), value);
		}
	}
	/**
	 * 检验是否具有操作某功能的权限<p>
	 */
	public boolean checkPermission(String actionName) {
		boolean rtnB = true;
		return rtnB;
	}

	/**
	 * 将URL转换为JSF标准URL路径<p>
	 * @param url 路径字符串<br>
	 * @return JSF标准URL路径<br>
	 */
	public String getFacesContextURL(String url) {
		return facesContext.getApplication().getViewHandler().getResourceURL(facesContext, url);
	}

	
	/**
	 * 根据指定的时间长度异步显示前台信息<p>
	 * @param divid DIV索引值<br>
	 */
	public void displayClientMessageTime(String divid) {
//		StringBuffer script = new StringBuffer("displayClientMessage('")
//			.append(divid).append("', ")
//			.append(getSystemConfigParam("client_message_time")).append(")");
//		Browser.execClientScript(script.toString());
	}
	
	/**
	 * 增加错误信息到前台界面<p>
	 * @param mesKey 错误信息索引值<br>
	 * @param errMsg 错误信息真实值<br>
	 */
	public void putErrorMessage(String mesKey, String errMsg) {
//		// 判断错误信息容器是否存在
//		if (CommonUtils.isEmptyObject(errMap)) {
//			errMap = new java.util.HashMap<String, String>();
//		}
//		// 向容器中添加错误信息并保存到会话中
//		if (StringUtils.isNotEmpty(mesKey) && StringUtils.isNotEmpty(errMsg)) {
//			errMap.put(mesKey, errMsg);
//			setGlobalSessionAttribute(FrameworkConstants.SESSION_ERROR_MAP, errMap);
//		}
	}
	
	/**
	 * 增加错误信息到前台显示<p>
	 * @param mesKey 错误信息索引值<br>
	 * @param errCode 错误信息编码<br>
	 * @param replace 替换字段信息<br>
	 */
	public void putErrorMessage(String mesKey, String errCode, String replace) {
		
	}
	
	/**
	 * 获取项目部署路径下的绝对路径<p>
	 * @param path 相对路径<br>
	 */
	public String getAbsolutePath(String path) {
		return request.getRealPath(java.io.File.separator).concat(FileUtils.getCurrentOSFilePath(path));
	}
	
	/**
	 * 获取系统配置参数信息<p>
	 */
	public String getSystemConfigParam(String key) {
		return "";
	}
	
	/**
	 * 获取自定义的提示信息<p>
	 */
	public String getCustomizeMessage(String key) {
		return "";
	}
	
	
	/**
	 * 根据信息键值获取AOM的配置信息<p>
	 */
	public String getAomMessage(String key) {
		return StringUtils.getLegalString(messages.get(key));
	}
	/**
	 * 获取页面状态<p>
	 * @return  pageStatus  页面状态<br>
	 */
	public String getPageStatus() {
		return pageStatus;
	}



	/**
	 * 设置页面状态<p>
	 * @param  pageStatus  页面状态<br>
	 */
	public void setPageStatus(String pageStatus) {
		this.pageStatus = pageStatus;
	}



	/**
	 * 获取当前会话参数容器<p>
	 * @return  session  当前会话参数容器<br>
	 */
	public HttpSession getSession() {
		return session;
	}



	/**
	 * 设置当前会话参数容器<p>
	 * @param  session  当前会话参数容器<br>
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}



	/**
	 * 获取AOM上下文环境<p>
	 * @return  context  AOM上下文环境<br>
	 */
	public javax.faces.context.ExternalContext getContext() {
		return context;
	}



	/**
	 * 设置AOM上下文环境<p>
	 * @param  context  AOM上下文环境<br>
	 */
	public void setContext(javax.faces.context.ExternalContext context) {
		this.context = context;
	}



	/**
	 * 获取JSF上下文环境<p>
	 * @return  facesContext  JSF上下文环境<br>
	 */
	public javax.faces.context.FacesContext getFacesContext() {
		return facesContext;
	}



	/**
	 * 设置JSF上下文环境<p>
	 * @param  facesContext  JSF上下文环境<br>
	 */
	public void setFacesContext(javax.faces.context.FacesContext facesContext) {
		this.facesContext = facesContext;
	}



	/**
	 * 获取页面请求对象<p>
	 * @return  request  页面请求对象<br>
	 */
	public HttpServletRequest getRequest() {
		return request;
	}



	/**
	 * 设置页面请求对象<p>
	 * @param  request  页面请求对象<br>
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}



	/**
	 * 获取页面响应对象<p>
	 * @return  response  页面响应对象<br>
	 */
	public HttpServletResponse getResponse() {
		return response;
	}



	/**
	 * 设置页面响应对象<p>
	 * @param  response  页面响应对象<br>
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}



	/**
	 * 获取工程相对路径<p>
	 * @return  contextPath  工程相对路径<br>
	 */
	public java.lang.String getContextPath() {
		return contextPath;
	}



	/**
	 * 设置工程相对路径<p>
	 * @param  contextPath  工程相对路径<br>
	 */
	public void setContextPath(java.lang.String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * 获取SPRING上下文环境<p>
	 * @return  applicationContext  SPRING上下文环境<br>
	 */
	public org.springframework.context.ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 设置SPRING上下文环境<p>
	 * @param  applicationContext  SPRING上下文环境<br>
	 */
	public void setApplicationContext(
			org.springframework.context.ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	
	
	
	
}
