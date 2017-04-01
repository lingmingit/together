package com.together.framework.web.aom.list.impl;

import org.operamasks.faces.annotation.Accessible;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.SaveState;
import org.operamasks.faces.component.grid.CheckboxSelectionModel;
import org.operamasks.faces.component.grid.GridSelectionModel;
import org.operamasks.faces.component.grid.impl.UIDataGrid;
import org.operamasks.faces.component.grid.provider.GridDataProvider;
import org.operamasks.faces.component.widget.UIForm;

import com.together.common.CommonUtils;
import com.together.framework.dao.common.SQLCondition;
import com.together.framework.web.aom.base.impl.CoreBaseUIBean;
import com.together.framework.web.aom.list.ICoreListUI;

/**
 * 序时薄界面实现核心litBean 接口实现类<p>
 * @author LingMin 
 * @date 2014-07-22<br>
 * @version 1.0<br>
 */
@SuppressWarnings("unchecked")
public abstract class CoreListUIBean<E extends java.io.Serializable , ID extends java.io.Serializable> extends CoreBaseUIBean implements ICoreListUI<E, ID> {
	/** 系统生成默认版本编号 **/
	private static final long serialVersionUID = 5743044502182412935L;
	/** 分页显示数据记录数 **/
	@Accessible
	protected int pageSize;
	/** 界面的Form对象 **/
	@Bind
	protected UIForm listForm;
	/** 列表界面列表控件对象 **/
	@Accessible
	private UIDataGrid listDataGrid;
	/** 数据列高度 **/
	@Accessible
	protected Integer dataGridHeight;
	/** 分录行数据选择模式 **/
	@Accessible
	protected GridSelectionModel checkbox;
	/** 列表界面过滤条件收集容器 **/
	@SaveState
	private SQLCondition condition = new SQLCondition();
	/** 列表界面默认过滤条件收集容器 **/
	private SQLCondition defaultCondition = new SQLCondition();
	
	/**
	 * 【重置】按钮监听函数<p>
	 */
	public void resetAction() {
		getCondition().clear();
		resetCondition();
		refreshDataGridList();
	}
	
	/**
	 * 【查询】按钮监听函数<p>
	 */
	public void searchAction() {
		getCondition().clear();
		collectionCondition();
		refreshDataGridList();
	}
	
	/**
	 * 页面渲染之前调用的函数<p>
	 */
	public void beforePageOnLoad() {
		super.beforePageOnLoad();
		// 初始化选择控件
		checkbox = new CheckboxSelectionModel();
		// 初始化分录的高度
		dataGridHeight = getContentDisplayArea() - getCurrentBottomHeight()
				- getCurrentToolAreaHeight() - getConditionAreaheight();
		// 56:分录工具栏高度  15:分录滚动条高度  25:分录标题高度
		pageSize = (dataGridHeight - (26 + 15 + 25)) / getCurrentDataGridRowHeight();
	}
	
	/**
	 * 设置默认的过滤条件<p>
	 */
	public void setDefaultCondition() {
		getDefaultCondition().clear();
	}
	
	/**
	 * 刷新当前页面列表控件<p>
	 */
	public void refreshDataGridList() {
		if (CommonUtils.isNotEmptyObject(getListDataGrid()))
			getListDataGrid().reload();
	}
	
	/**
	 * 获取分录选择行数据<p>
	 * @return 分录选择行数据容器<br>
	 */
	public E[] getSelectedRowsData() {
		return (E[]) getListDataGrid().getSelectedValues();
	}
	
	/**
	 * 验证是否选择了数据行<p>
	 * @return true:已选择 false:未选择<br>
	 */
	public boolean checkRowSelected() {
		return CommonUtils.isNotEmptyObjectArray(getListDataGrid().getSelectedValues());
	}
	
	/**
	 * 查询列表显示数据记录的总数量<p>
	 * @return 列表显示数据记录的总数量<br>
	 */
	protected int getDataGridTotalCount() {
		setDefaultCondition();
		getCondition().merge(getDefaultCondition());
		return getCurrentService().count(condition, getFunctionName());
	}
	
	/**
	 * 获取列表界面默认过滤条件收集容器<p>
	 * @return 列表界面默认过滤条件收集容器<br>
	 */
	public SQLCondition getDefaultCondition() {
		return defaultCondition;
	}
	
	/**
	 * 执行操作之前判断是否已经选中数据记录<p>
	 * @param oprtName 操作名称<br>
	 */
	public boolean checkRowSelectedForOperate(String oprtName) {
		boolean rtnB = checkRowSelected();
		if (!rtnB) {
			//putErrorMessage("EMS0004", "EMS0004", getCustomizeMessage(oprtName));
		}
		return rtnB;
	}
	
	/**
	 * 分录行单击事件监听函数<p>
	 */
	public void datagrid_row_single_onclick() { }

	/**
	 * 分录行双击事件监听函数<p>
	 */
	public void datagrid_row_double_onclick() { }
	
	/**
	 * 查询列表每页显示的数据集合<p>
	 * @param start 开始记录索引<br>
	 * @param limit 每页显示数据行数<br>
	 * @return 数据集合<br>
	 */
	protected Object[] getDataGridPageList(int start, int limit) {
		setDefaultCondition();
		getCondition().merge(getDefaultCondition());
		return getCurrentService().list(start, limit, condition).toArray();
	}
	
	/**
	 * 根据关键字集合查询对应的数据记录<p>
	 */
	public Object[] getElementsByObject(String[] ids) {
		return CommonUtils.isNotEmptyObjectArray(ids) ? this.getCurrentService().getEntityByKeys((ID[]) ids).toArray(getEntity_Array()) : null;
	}
	
	/**
	 * 定义列表界面列表控件的数据适配器<p>
	 */
	@Bind(id = "listDataGrid", attribute = "dataProvider")
	protected GridDataProvider gridDataProvider = new GridDataProvider() {
		/** 系统生成默认版本编号 **/
		private static final long serialVersionUID = -6349200111554357337L;
		
		/**
		 * 获取显示数据记录的总行数<p>
		 */
		public int getTotalCount() {
			return getDataGridTotalCount();
		}

		/**
		 * 获取每页显示的数据记录<p>
		 */
		public Object[] getElements() {
			return getDataGridPageList(start, limit);
		}
		
		/**
		 * 根据关键字集合查询对应的数据记录<p>
		 */
		public Object[] getElementsById(String[] ids) {
			return getElementsByObject(ids);
		}
	};
	
	/**
	 * 获取当前浏览器的高度<p>
	 */
	public int getContentDisplayArea() {
		// 80:标头部分高度  28:功能显示栏高度
		//return getCurrentIEHeight() - (90 + 28);
		return 0;
	}
	
	/**
	 * 获取网站底部的高度<p>
	 * @return 网站底部的高度<br>
	 */
	public int getCurrentBottomHeight() {
		return 0;
	}
	
	/**
	 * 设置条件区域的高度<p>
	 * @return 查询区域高度<br>
	 */
	public int getConditionAreaheight() {
		return 80;
	}
	
	/**
	 * 获取工具栏的高度<p>
	 * @return 工具栏的高度<br>
	 */
	public int getCurrentToolAreaHeight() {
		return 26;
	}
	
	/**
	 * 获取分录行高度<p>
	 * @return 分录行高度<br>
	 */
	public int getCurrentDataGridRowHeight() {
		return 21;
	}
	
	/**
	 * 获取列表界面过滤条件收集容器<p>
	 * @return 列表界面过滤条件收集容器<br>
	 */
	public SQLCondition getCondition() {
		return condition;
	}
	
	/**
	 * 获取列表界面列表控件对象<p>
	 * @return 列表界面列表控件对象<br>
	 */
	public UIDataGrid getListDataGrid() {
		return listDataGrid;
	}

	/**
	 * 设置列表界面列表控件对象<p>
	 * @param listDataGrid 列表界面列表控件对象<br>
	 */
	public void setListDataGrid(UIDataGrid listDataGrid) {
		this.listDataGrid = listDataGrid;
	}
}
