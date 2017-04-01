/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2014, all rights reserved.
 * @author LM
 * @version $Id$
 * @since 1.0
 * 
 */
package com.together.framework.web.aom.list;

import org.operamasks.faces.annotation.Action;

import com.together.framework.entity.AbstractDataBase;

/** 
 * 基础资料信息列表界面实现接口类<p>
 * @author LingMin 
 * @date 2014-7-23<br>
 * @version 1.0<br>
 */
public interface IDataBaseListUI<E extends AbstractDataBase, ID extends java.io.Serializable> extends IBaseListUI<E, ID> {
	/**
	 * 【启用】按钮监听函数<p>
	 */
	@Action
	public void enableAction();
	
	/**
	 * 【禁用】按钮监听函数<p>
	 */
	@Action
	public void disableAction();
	
	/**
	 * 判断是否允许执行启用操作<p>
	 * @param record 当前数据记录<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyEnable(E record);
	
	/**
	 * 判断是否允许执行禁用操作<p>
	 * @param record 当前数据记录<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyDisable(E record);
	
	/**
	 * 执行业务逻辑层启用数据操作<p>
	 * @param record 当前数据记录<br>
	 */
	public void processEnableRecord(E record);
	
	/**
	 * 执行业务逻辑层禁用数据操作<p>
	 * @param record 当前数据记录<br>
	 */
	public void processDisableRecord(E record);
	
	/**
	 * 启用数据记录之前的数据合法性验证<p>
	 * @param record 当前数据记录<br>
	 * @return true:合法 false:非法<br>
	 */
	public boolean dataValidateForEnable(E record);
	
	/**
	 * 根据数据记录的系统标识判断是否具有操作权限<p>
	 * @param record 数据记录信息<br>
	 * @param oprtName 按钮配置名称<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean checkSystemRecordRight(E record, String oprtName);
	
	/**
	 * 根据数据记录的状态判断是否具有操作权限<p>
	 * @param record 数据记录信息<br>
	 * @param status 数据记录状态<br>
	 * @param oprtName 按钮配置名称<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean checkStatusRecordRight(E record, Boolean status, String oprtName);
}
