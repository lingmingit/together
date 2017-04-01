/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.web.aom.base;

/** 
 * 抽象AOM 基础核心litBean基类 接口方法<p>
 * @author LingMin 
 * @date 2014-6-27<br>
 * @version 1.0<br>
 */
public interface ICoreBaseUI {

	
	/**
	 * 页面渲染滞后调用的函数<p>
	 */
	public void afterPageOnLoad();
	
	/**
	 * 页面渲染之前调用的函数<p>
	 */
	public void beforePageOnLoad();
	
	/**
	 * 获取功能名称<p>
	 * @return 功能名称<br>
	 */
	public String getFunctionName();
	
	/**
	 * 初始化下拉列表信息<p>
	 */
	public void initSelectItemList();
	
	/**
	 * 初始化页面控件信息<p>
	 */
	public void initPageComponent();
	
//	/**
//	 * 获取当前系统登录浏览器的宽度<p>
//	 * @return 浏览器的宽度<br>
//	 */
//	public int getCurrentIEwidth();
//	
//	/**
//	 * 获取当前系统登录浏览器的高度<p>
//	 * @return 浏览器的高度<br>
//	 */
//	public int getCurrentIEHeight();
	
	/**
	 * 根据信息键值获取AOM的配置信息<p>
	 * @param key 配置信息键值<br>
	 * @return 配置信息<br>
	 */
	public String getAomMessage(String key);
}
