/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2013, all rights reserved.
 * @author avic-LM
 * @version $Id: TestBean.java,v 1.1 2013/09/29 01:37:02 lingmin Exp $
 * @since 1.0
 * 
 */
package com.together.test.bean;

import java.util.List;

import javax.annotation.Resource;

import org.operamasks.faces.annotation.Action;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.Label;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.annotation.Required;

import com.together.console.system.service.IUserService;
import com.together.framework.common.annotation.IServiceCtrl;

/** 
 * 测试AOM 配置是否成功 后台逻辑控制Bean<p>
 * @author LingMin 
 * @date 2013-9-11<br>
 * @version 1.0<br>
 */
@ManagedBean(name="test.testUIBean", scope=ManagedBeanScope.REQUEST)
public class TestBean {

	 @Bind
    @Label("请输入您的名字：")
    @Required(message = "输入不能为空")
    private String name;
    
    @Bind
    private String result;
    
    /** SPRING上下文环境 **/
    @Resource
	private org.springframework.context.ApplicationContext applicationContext = null;
    
	/** 用户信息业务逻辑层操作对象 **/
	//@Inject(value="com.together.userService")
	//@ManagedProperty("#{com.together.userService}")
	//@Autowired
	//@Autowired(required=true)
	//@Resource(name="com.together.userService")
    //@ManagedProperty(value="#{com.together.userService}")
  //  @ManagedProperty("#{com.together.userService}")
   // @Resource(name="com.together.userService")
    @IServiceCtrl(serviceName="com.together.userService")
	protected IUserService userLogicBean;
    
    /***
     * <p>
     * @return <p>
     * String
     */
    @Action
    private String sayHello() {
    	// 初始化上下文环境
//		if (CommonUtils.isEmptyObject(applicationContext))
//			applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(
//				(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()
//			);
//		userLogicBean = (IUserService)applicationContext.getBean("com.together.userService");
    	List list = userLogicBean.list(null);
    	System.out.println("list="+list);
        if ("duke".equalsIgnoreCase(name)) {
            return "view:sameName";
        } else {
            result = "您好，" + name + "！欢迎使用OperaMasks！";
            return null;
        }
    }

}
