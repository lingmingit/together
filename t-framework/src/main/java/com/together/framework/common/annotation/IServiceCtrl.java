package com.together.framework.common.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 定义注入注释 Service 远程业务接口<p>
 * @author LingMin @date 2014-06-17<br>
 * @version 1.0<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface IServiceCtrl {
	/**
	 * 获取业务逻辑层控制对象名<p>
	 * @return 逻辑层控制对象名<br>
	 */
	public abstract String serviceName();
}

