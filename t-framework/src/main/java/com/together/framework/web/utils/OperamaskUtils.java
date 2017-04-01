package com.together.framework.web.utils;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.model.SelectItem;

import org.operamasks.faces.component.form.impl.UICheckBox;
import org.operamasks.faces.component.form.impl.UICombo;
import org.operamasks.faces.component.form.impl.UINumberField;
import org.operamasks.faces.component.form.impl.UIRadioGroup;
import org.operamasks.faces.component.form.impl.UITextField;
import org.operamasks.faces.component.grid.impl.UIEditDataGrid;
import org.operamasks.faces.component.widget.UIButton;

import com.together.common.CommonUtils;
import com.together.common.ReflectionUtils;
import com.together.common.StringUtils;
import com.together.common.date.DateUtils;
import com.together.common.number.NumberUtils;

/**
 * 基于金蝶OperaMasks中间件的工具类<p>
 * @author LingMin 
 * @date 2014-10-22<br>
 * @version 1.0<br>
 */
public final class OperamaskUtils {
	/**
	 * 构造函数<p>
	 */
	private OperamaskUtils() {}
	
	/**
	 * 将Boolean类型值转换为字符串<p>
	 * @param bool Boolean类型值<br>
	 * @return 字符串<br>
	 */
	public static String getStringFromBoolean(Boolean bool) {
		return CommonUtils.isNotEmptyObject(bool) && bool ? "true" : "false";
	}
	
	/**
	 * 获取指定控件的真实值<p>
	 * @param component 控件对象<br>
	 * @return 控件值<br>
	 */
	public static Object getComponentValue(UIComponent component) {
		java.lang.Object rtnO = null;
		// 合法性验证
		if (CommonUtils.isNotEmptyObject(component) && component instanceof UIInput) {
			rtnO = ((UIInput) component).getSubmittedValue();
			if (CommonUtils.isEmptyObject(rtnO)) {
				rtnO = ((UIInput) component).getValue();
			}
		}
		return rtnO;
	}
	
	/**
	 * 设置控件值<p>
	 * @param component 控件对象<br>
	 * @param value 控件值<br>
	 */
	public static void setComponentValue(UIComponent component, Object value) {
		// 合法性判断
		if (CommonUtils.isNotEmptyObject(component)) {
			if (component instanceof UIInput) {
				if (component instanceof UICombo) {
					component.repaint();
				}
				UIInput inputComp = (UIInput) component;
				if (value instanceof String) {
					inputComp.setValue(value);
					inputComp.setSubmittedValue(value);
				} else if (value instanceof java.math.BigDecimal) {
					String temp = NumberUtils.customizeNumberFormatForBigDecimal(
						"###,###.00######", (java.math.BigDecimal) value
					);
					inputComp.setValue(temp);
					inputComp.setSubmittedValue(temp);
				} else if (value instanceof java.util.Date) {
					String temp = DateUtils.formatDateBySpecifiedFormatter(
						(java.util.Date)value, "yyyy-MM-dd"
					);
					inputComp.setValue(temp);
					inputComp.setSubmittedValue(temp);
				} else if (value instanceof Boolean) {
					inputComp.setValue((Boolean)value);//setValue方法应该保持原类型，否则再次获取该组件的值时，就会变成转行后的类型
					inputComp.setSubmittedValue(getStringFromBoolean((Boolean)value));
				} else if (value instanceof Integer) {
					inputComp.setValue(value.toString());
					inputComp.setSubmittedValue(value.toString());
				} else {
					inputComp.setValue(null);
					inputComp.setSubmittedValue(null);
				}
			}
		}
	}
	
	/**
	 * 将指定的控件重置<p>
	 * @param component 控件对象<br>
	 */
	public static void resetComponent(UIComponent component, boolean isNumComponet) {
		// 判断控件对象的合法性
		if (CommonUtils.isNotEmptyObject(component) && component instanceof UIInput) {
			UIInput temp = (UIInput) component;
			if (!isNumComponet) {
				temp.setSubmittedValue(null);
			} else {
				temp.setSubmittedValue(NumberUtils.customizeNumberFormatForBigDecimal(
					"###,##0.00##", NumberUtils.ZERO_BIGDECIMAL)
				);
			}
			temp.repaint();
		}
	}
	
	/**
	 * 隐藏指定控件下的所有按钮控件<p>
	 * @param component 指定控件<br>
	 * @param isHidden 是否隐藏<br>
	 */
	public static void hiddenOrUnHiddenRootComponentUIButton(UIComponent component, Boolean isHidden) {
		if (CommonUtils.isNotEmptyObject(component)) {
			java.util.List<UIComponent> components = component.getChildren();
			if (CommonUtils.isNotEmptyList(components)) {
				UIComponent temp = null;
				for (int i = 0; i < components.size(); i ++) {
					temp = components.get(i);
					hiddenOrUnHiddenRootComponentUIButton(temp, isHidden);
				}
			} else {
				if (component instanceof UIButton) {
					((UIButton)component).setStyle(isHidden ? "display:none;" : "");
				}
			}
		}
	}
	
	/**
	 * 锁定|解锁指定控件下的所有控件<p>
	 * @param component 指定控件<br>
	 * @param repaint   重绘标志<br>
	 * @param isLocked true:锁定 false:解锁<br>
	 */
	public static void lockOrUnLockRootComponent(UIComponent component, boolean repaint, boolean isLocked) {
		if (CommonUtils.isNotEmptyObject(component)) {
			java.util.List<UIComponent> components = component.getChildren();
			if (CommonUtils.isNotEmptyList(components)) {
				if (component instanceof UIRadioGroup) {
					lockOrUnLockComponent(component, repaint, isLocked);
				} else if (component instanceof UIEditDataGrid) {
					lockOrUnLockComponent(component, repaint, isLocked);
				} else if (component instanceof UICombo) {
					lockOrUnLockComponent(component, repaint, isLocked);
				} else {
					UIComponent temp = null;
					for (int i = 0; i < components.size(); i ++) {
						temp = components.get(i);
						lockOrUnLockRootComponent(temp, repaint, isLocked);
					}
				}
			} else {
				lockOrUnLockComponent(component, repaint, isLocked);
			}
		}
	}
	
	/**
	 * 将HASHMAP容器中的数据对象转换为下拉列表信息<p>
	 * @param map Map对象<br>
	 * @param keyField 真实值字段名<br>
	 * @param labelField 显示值字段名<br>
	 * @return 下拉列表信息<br>
	 */
	@SuppressWarnings("rawtypes")
	public static SelectItem[] getSelectItemFromHashMap(java.util.Map<String, Object> map, String keyField, String labelField) {
		SelectItem[] rtnArray = null;
		// 合法性判断
		if (CommonUtils.isNotEmptyHashMap(map) && StringUtils.isNotEmpty(keyField) && StringUtils.isNotEmpty(labelField)) {
			java.util.List<SelectItem> list = new java.util.ArrayList<SelectItem>();
			java.util.Iterator iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				Object obj = map.get(iterator.next());
				list.add(new SelectItem(ReflectionUtils.getFieldValue(obj, keyField), StringUtils.getLegalString((String)ReflectionUtils.getFieldValue(obj, labelField))));
			}
			rtnArray = CommonUtils.isNotEmptyList(list) ? list.toArray(new javax.faces.model.SelectItem[0]) : rtnArray;
		}
		return rtnArray;
	}
	
	/**
	 * 根据标志位锁定相关控件<p>
	 * @param component 容器控件名称<br>
	 * @param repaint   重绘标志<br>
	 * @param isLocked true:锁定  false:解锁<br>
	 */
	public static void lockOrUnLockComponent(UIComponent component, boolean repaint, boolean isLocked) {
		if (component instanceof UITextField) {
			if (component instanceof UICombo) {
				UICombo temp = (UICombo) component;
				temp.setDisabled(isLocked);
				temp.setAlwaysSubmit(true);
				if (repaint) temp.repaint();
			} else {
				UITextField temp = (UITextField) component;
				temp.setReadOnly(isLocked);
				temp.setAlwaysSubmit(true);
			}
		} else if (component instanceof UIButton) {
			UIButton temp = (UIButton) component;
			temp.setAlwaysSubmit(true);
			temp.setDisabled(isLocked);
		} else if (component instanceof UICheckBox) {
			UICheckBox temp = (UICheckBox) component;
			temp.setReadOnly(isLocked);
			temp.setAlwaysSubmit(true);
		} else if (component instanceof UIRadioGroup) {
			UIRadioGroup temp = (UIRadioGroup) component;
			temp.setDisabled(isLocked);
			if (repaint) temp.repaint();
		} else if (component instanceof UIEditDataGrid) {
			UIEditDataGrid temp = (UIEditDataGrid) component;
			temp.setReadOnly(isLocked);
		} else if (component instanceof UINumberField) {
			UINumberField temp = (UINumberField) component;
			temp.setReadOnly(isLocked);
			temp.setAlwaysSubmit(true);
		}
	}
	
	/**
	 * 隐藏指定控件容器下的按钮控件<p>
	 * @param toolbar 控件容器ID<br>
	 * @param button 按钮控件ID<br>
	 */
	public static void hiddenUIToolButton(org.operamasks.faces.component.widget.UIToolBar toolbar, String button) {
		UIButton tempBtn = OperamaskUtils.getUIToolButtonFromComponent(toolbar, button);
		if (CommonUtils.isNotEmptyObject(tempBtn)) {
			tempBtn.setStyle("display:none;");
		}
	}
	
	/**
	 * 获取某控件容器下的按钮控件<p>
	 * @param toolbar 控件容器ID<br>
	 * @param button 按钮控件ID<br>
	 * @return 按钮控件对象<br>
	 */
	public static UIButton getUIToolButtonFromComponent(org.operamasks.faces.component.widget.UIToolBar toolbar, String button){
		UIButton rtnBtn = null;
		UIComponent uiComponent = toolbar.findComponent(button);
		if(uiComponent instanceof UIButton){
			rtnBtn = (UIButton)uiComponent;
		}
		return rtnBtn;
	}
	
	/**
	 * 在指定的控件下查询指定控件关键字的控件<p>
	 * @param component 控件对象<br>
	 * @param componentid 查询控件关键字<br>
	 * @return 控件对象<br>
	 */
	public static UIComponent findUIComponent(UIComponent component, String componentid) {
		UIComponent rtnComp = null;
		if (CommonUtils.isNotEmptyObject(component)) {
			rtnComp = component.findComponent(componentid);
			if (CommonUtils.isEmptyObject(rtnComp)) {
				java.util.List<UIComponent> children = component.getChildren();
				if (CommonUtils.isNotEmptyList(children)) {
					for (int i = 0; i < children.size(); i ++) {
						rtnComp = findUIComponent(children.get(i), componentid);
					}
				}
			}
		}
		return rtnComp;
	}
}
