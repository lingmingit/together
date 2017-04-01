package com.together.framework.web.aom.list;

import org.operamasks.faces.annotation.Action;



/**
 * 基于序时薄界面的基本实现接口类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public interface IBaseListUI<E extends java.io.Serializable, ID extends java.io.Serializable> extends ICoreListUI<E, ID> {
	/**
	 * 【编辑】按钮监听函数<p>
	 */
	@Action
	public void editAction();
	
	/**
	 * 【查看】按钮监听函数<p>
	 */
	@Action
	public void viewAction();
	
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
	 * 验证是否允许执行新增操作<p>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyAddNew();
	
	/**
	 * 获取标准URL的路径<p>
	 * @return URL路径<br>
	 */
	public String getStandardPageURL();
	
	/**
	 * 判断是否允许执行编辑操作<p>
	 * @param record 指定数据记录<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyEdit(E record);
	
	/**
	 * 获取编辑界面的显示名称<p>
	 * @return 编辑界面的显示名称<br>
	 */
	public String getEditUIDisplayName();
	
	/**
	 * 判断是否允许执行删除操作<p>
	 * @param record 指定数据记录<br>
	 * @return true:允许 false:禁止<br>
	 */
	public boolean verifyDelete(E record);
	
	/**
	 * 执行业务逻辑层删除数据操作<p>
	 * @param record 数据信息记录<br>
	 */
	public void processDeleteRecord(E record);
	
	/**
	 * 执行操作之前判断是否已经选中数据记录<p>
	 * @param oprtName 操作配置名称<br>
	 * @return true:未选择 false:未选择<br>
	 */
	public boolean checkRowSelectedForOperate(String oprtName);
}
