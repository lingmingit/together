package com.together.framework.web.aom.list;

import org.operamasks.faces.annotation.Action;

import com.together.framework.service.base.ICoreBaseService;
import com.together.framework.web.aom.base.ICoreBaseUI;

/**
 * 序时薄界面实现核心接口类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
public interface ICoreListUI<E extends java.io.Serializable , ID extends java.io.Serializable> extends ICoreBaseUI {
	/**
	 * 【重置】按钮监听函数<p>
	 */
	@Action
	public void resetAction();
	
	/**
	 * 【查询】按钮监听函数<p>
	 */
	@Action
	public void searchAction();
	
	/**
	 * 获取绑定实体对象的数组<p>
	 * @return 实体对象数组<br>
	 */
	public E[] getEntity_Array();
	
	/**
	 * 重置页面查询条件<p>
	 */
	public void resetCondition();
	
	/**
	 * 收集页面查询条件<p>
	 */
	public void collectionCondition();
	
	/**
	 * 分录行单击事件监听函数<p>
	 */
	@Action
	public void datagrid_row_single_onclick();
	
	/**
	 * 分录行双击事件监听函数<p>
	 */
	@Action
	public void datagrid_row_double_onclick();
	
	/**
	 * 获取业务逻辑层操作对象<p>
	 * @return 业务逻辑层操作对象<br>
	 */
	public ICoreBaseService<E, ID> getCurrentService();
}
