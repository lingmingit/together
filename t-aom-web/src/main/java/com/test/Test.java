package com.test;

import com.together.common.TestCommon;


/**
 * <p>
 * @author LingMin 
 * @date 2013-9-11<br>
 * @version 1.0<br>
 */
public class Test {

	/**名称**/
	private String name;
	
	public String getTestCommon(){
		return TestCommon.CommonName;
	}

	/**
	 * 获取名称<p>
	 * @return  name  名称<br>
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称<p>
	 * @param  name  名称<br>
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
