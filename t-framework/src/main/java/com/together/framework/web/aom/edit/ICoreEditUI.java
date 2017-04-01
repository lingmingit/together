/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.web.aom.edit;

import org.operamasks.faces.annotation.Action;

import com.together.framework.service.base.ICoreBaseService;
import com.together.framework.web.aom.base.ICoreBaseUI;



/** 
 * 编辑界面后台核心litBean基类 接口方法<p>
 * @author LingMin 
 * @date 2014-7-21<br>
 * @version 1.0<br>
 */
public interface ICoreEditUI <E extends java.io.Serializable, ID extends java.io.Serializable> extends ICoreBaseUI {

	/**
	 * 加载页面数据<p>
	 */
	public void loadField();
	
	/**
	 * 收集页面数据<p>
	 */
	public void storeField();
	
	/**
	 * 【编辑】按钮监听函数<p>
	 */
	@Action
	public void editAction();
	
	/**
	 * 【保存】按钮监听函数<p>
	 */
	@Action
	public void saveAction();
	
	/**
	 * 【退出】按钮监听函数<p>
	 */
	@Action
	public void exitAction();
	
	/**
	 * 【新增】按钮监听函数<p>
	 */
	@Action
	public void addNewAction();
	
	/**
	 * 【删除】按钮监听函数<p>
	 */
	@Action
	public void deleteAction();
	
	/**
	 * 获取编辑界面的URL<p>
	 * @return 编辑界面的URL<br>
	 */
	public String getEditURL();
	
	/**
	 * 页面状态【新增】时初始化页面数据<p>
	 */
	public void initalPageData();
	
	/**
	 * 判断输入数据的合法性<p>
	 * @return true:合法  false:非法<br>
	 */
	public boolean verifyInput();
	
	/**
	 * 判断是否允许进行新增操作<p>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyAddNew();
	
	/**
	 * 初始化页面控件信息<p>
	 */
	public void initPageComponent();
	
	/**
	 * 获取标准功能的URL路径<p>
	 * @return URL路径<br>
	 */
	public String getCommonPageURL();
	
	/**
	 * 页面状态【查询】时锁定页面控件<p>
	 */
	public void lockComponentForView();
	
	/**
	 * 判断是否允许执行编辑操作<p>
	 * @param record 指定数据记录<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyEdit(E record);
	
	/**
	 * 判断是否允许执行删除操作<p>
	 * @param record 指定数据记录<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyDelete(E record);
	
	/**
	 * 页面状态【审核】时解锁页面控件<p>
	 */
	public void unLockComponentForAudit();
	
	/**
	 * 执行业务逻辑层保存数据操作<p>
	 * @param record 数据信息记录<br>
	 */
	public void processSaveRecord(E record);
	
	/**
	 * 执行业务逻辑层删除数据操作<p>
	 * @param record 数据信息记录<br>
	 */
	public void processDeleteRecord(E record);
	
	/**
	 * 复制新增数据保存后相关操作函数<p>
	 * @param record 数据信息记录<br>
	 */
	public void processChangeForCopyAdd(E record);
	
	/**
	 * 执行分录数据业务逻辑层保存操作<p>
	 * @param parent 数据信息记录<br>
	 */
	public void processSaveEntryRecord(E parent);
	
	/**
	 * 初始化复制新增状态下的页面数据<p>
	 * @param record 页面数据对象<br>
	 */
	public void initalPageDataForCopyAdd(E record);
	
	/**
	 * 获取业务逻辑层操作对象<p>
	 * @return 业务逻辑层操作对象<br>
	 */
	public ICoreBaseService<E, ID> getCurrentService();
}
