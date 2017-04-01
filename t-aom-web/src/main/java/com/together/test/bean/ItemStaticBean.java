/**
 * &lt;p&gt;
 * copyright &amp;copy; together 2013, all rights reserved.
 * @author avic-LM
 * @version $Id: ItemStaticBean.java,v 1.1 2013/09/29 01:37:02 lingmin Exp $
 * @since 1.0
 * 
 */
package com.together.test.bean;

import java.util.ArrayList;
import java.util.List;

import org.operamasks.faces.annotation.Action;
import org.operamasks.faces.annotation.BeforeRender;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.user.util.Browser;

import com.together.test.bean.base.TestBaseBean;

/** 
 * 测试aom 3.2新控件版本是否配置成功<p>
 * @author LingMin 
 * @date 2013-9-11<br>
 * @version 1.0<br>
 */
@ManagedBean(name="test.itemStaticBean", scope=ManagedBeanScope.REQUEST)
public class ItemStaticBean extends  TestBaseBean{

	
	@Bind(id="itemSelector",attribute="value")
    private List<String> selectedValues;
    
    @BeforeRender
    public void beforeRender(boolean isPostback){
        if(!isPostback){
            List<String> ls=new ArrayList<String>();
            ls.add("1");//1广东
            ls.add("10");//10青海
            ls.add("12");//12上海
            selectedValues=ls;
        }
    }
    
    @Action
    public void showResult(){
        Browser.alert("你选择的值是："+selectedValues.toString());
    }
    @Action
    public void changeValue(){
            List<String> ls=new ArrayList<String>();
            ls.add("2");//2黑龙江
            ls.add("3");//3北京
            ls.add("4");//4湖北
            selectedValues=ls;
    }

}
