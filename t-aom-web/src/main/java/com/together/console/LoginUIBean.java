package com.together.console;

import java.text.MessageFormat;

import javax.faces.application.FacesMessage;

import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.annotation.Action;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.annotation.ManagedProperty;
import org.operamasks.faces.annotation.SaveState;
import org.operamasks.faces.component.widget.UIDrawImage;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;
import com.together.console.system.service.IUserService;
import com.together.framework.common.annotation.IServiceCtrl;
import com.together.framework.constants.FrameworkConstants;
import com.together.framework.entity.sys.User;
import com.together.framework.web.aom.base.impl.CoreBaseUIBean;
import com.together.framework.web.aom.bean.DrawVerifyCode;


/**
 * 管理后台登录界面实现类<p>
 * @author LingMin 
 * @date 2014-11-03<br>
 * @version 1.0<br>
 */
@ManagedBean(name="console.loginBean", scope=ManagedBeanScope.REQUEST)
public class LoginUIBean extends CoreBaseUIBean {


	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 7009874377419890487L;
	/** 登录账号 **/
	@Bind
	protected String userName = "admin";
	/** 登录密码 **/
	@Bind
	protected String password = "123456";
	/** 验证码 **/
	@Bind
	protected String authCode;
	/** 系统名称 **/
	@Accessible @SaveState
	protected String system_name;

	@ManagedProperty("#{drawVerifyCode}")
	protected DrawVerifyCode drawVerifyCode;
	 @Bind(id="codeImage" , attribute="binding")
	 protected UIDrawImage codeImage;//验证码图片对象 //该绑定不生效 不知道什么原因
	 
	 /** 用户信息业务逻辑层操作对象 **/
	@IServiceCtrl(serviceName="console.system.userService")
	protected IUserService userService;
	
	
	/**
	 * 初始化页面<p>
	 */
	public void pageOnLoad() {
		if (StringUtils.isEmpty(system_name)) {
			system_name = getSystemConfigParam("SYSTEM_NAME");
		}
		
	}
	
	  /**
     * 绑定页面id为validateImg的DrawImage组件的onclick事件， 实现验证码图片重新刷新
     */
    @Action
    public void changeVerifyCode() {
    	//ConversationContext.getCurrentInstance().put("resp", "");
        codeImage.refresh();
    }

	
	/**
	 * 登录当前系统<p>
	 */
	@Action
	public String login() {
		// 输入数据合法性验证
		if (!verifyInput()) {
			return null;
		}
		// 判断密码是否错误
		User user = this.getUserService().getLegalLoginUser(userName, password);
		if(CommonUtils.isEmptyObject(user) || CommonUtils.isEmptyObject(user.getId())){
			String errorStr = messages.get("login_error");
			this.getFacesContext().addMessage("userName",new FacesMessage(FacesMessage.SEVERITY_ERROR, errorStr , errorStr));
			return null;
		}
		
		return "view:index";
	}
	
	/**
	 * 注册合法用户<p>
	 */
	@Action(id="registerLink")
	public void register() {
		
	}
	
	/**
	 * 验证输入数据的合法性<p>
	 * @return true:合法 false:非法<>
	 */
	public boolean verifyInput() {
		boolean rtnB = true;
		
		// 用户名
		if (StringUtils.isEmpty(userName)) {
			 rtnB = rtnB ? false : rtnB;
			String errorStr = MessageFormat.format(messages.get("login_notNull"), "用户名" );
			//获取资源文件
			this.getFacesContext().addMessage("userName",new FacesMessage(FacesMessage.SEVERITY_ERROR, errorStr , errorStr));
			return rtnB;
		} 
		// 密    码
		if (StringUtils.isEmpty(password)) {
			rtnB = rtnB ? false : rtnB;
			String errorStr = MessageFormat.format(messages.get("login_notNull"), "密码" );
			this.getFacesContext().addMessage("password",new FacesMessage(FacesMessage.SEVERITY_ERROR, errorStr, errorStr));
			return rtnB;
		} 
		//验证码
		if(StringUtils.isEmpty(authCode)){
			rtnB = rtnB ? false : rtnB;
			String errorStr = MessageFormat.format(messages.get("login_notNull"), "验证码" );
			this.getFacesContext().addMessage("password",new FacesMessage(FacesMessage.SEVERITY_ERROR, errorStr, errorStr));
			return rtnB;
		}
   	
		//获取验证码
		String verifyCodeSesssion = (String)this.getSession().getAttribute(FrameworkConstants.VALIDATECODE_KEY);
   		logger.info("verifyCodeSesssion="+verifyCodeSesssion);
		if(!verifyCodeSesssion.equalsIgnoreCase(authCode)){
			rtnB = rtnB ? false : rtnB;
			//throw new FacesException("验证码错误");
			String errorStr = messages.get("login_verify_error");
			this.getFacesContext().addMessage("verifyCode",new FacesMessage(FacesMessage.SEVERITY_ERROR, errorStr, errorStr));
			return rtnB;
		}
		return rtnB;
	}

	/**
	 * 获取功能的名称<p>
	 */
	public String getFunctionName() {
		return "console.loginBean";
	}
	
	 /**
     * 界面上获取的资源文件 key ，通过传递 参数方式
     * @return
     */
    @Bind
    public String getUserNameRMessage(){
    	String errorStr = MessageFormat.format(messages.get("login_notNull"), "用户名" );
    	return errorStr;
    }
    /**
     * 界面上获取的资源文件 key ，通过传递 参数方式
     * @return
     */
    @Bind
    public String getPasswordRMessage(){
    	String errorStr = MessageFormat.format(messages.get("login_notNull"), "密码" );
    	return errorStr;
    }
    
    /**
     * 界面上获取的资源文件 key ，通过传递 参数方式
     * @return
     */
    @Bind
    public String getVerifyCodeRMessage(){
    	String errorStr = MessageFormat.format(messages.get("login_notNull"), "验证码" );
    	return errorStr;
    }

	/**
	 * 获取用户信息业务逻辑层操作对象<p>
	 * @return  userService  用户信息业务逻辑层操作对象<br>
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * 设置用户信息业务逻辑层操作对象<p>
	 * @param  userService  用户信息业务逻辑层操作对象<br>
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

    
    
    
}
