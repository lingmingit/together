package com.together.console;

import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;

import com.together.framework.web.aom.base.impl.CoreBaseUIBean;



/** 
 * 主页 后台逻辑 liteBean<p>
 * @author LingMin 
 * @date 2014-11-03<br>
 * @version 1.0<br>
 */
@ManagedBean(name="console.indexBean", scope=ManagedBeanScope.REQUEST)
public class IndexBean extends CoreBaseUIBean {

	/**
	 * 获取功能名称<p>
	 * @return 功能名称<br>
	 */
	public String getFunctionName() {
		return null;
	}

}
