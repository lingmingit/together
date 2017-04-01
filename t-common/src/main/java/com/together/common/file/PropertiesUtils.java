package com.together.common.file;

import java.io.IOException;
import java.io.InputStream;

import com.together.common.CommonUtils;
import com.together.common.StringUtils;



/**
 * 基于Properties文件的通用操作工具类<p>
 * @author LingMin 
 * @date 2014-07-21<br>
 * @version 1.0<br>
 */
public final class PropertiesUtils {
	/**
	 * 构造函数<p>
	 */
	private PropertiesUtils() {}
	
	
	
	
	/***
	 * 获取配置文件信息<p>
	 * 通过流方式 获取属性配置文件，主要应用于jar中的属性文件 
	 * @param fullPackagePath 包目录如：com/test/sys/
	 * @param fileName 文件名称 *.properties
	 * @param key 配置文件key名称
	 * @return  String 返回key 对应value值<p>
	 * 
	 */
	public static String getInputStreamPropertiesValue(String fullPackagePath , String fileName , String key){
		String value = null;
		InputStream inputStream = null;
		try {
			inputStream = PropertiesUtils.class.getResourceAsStream(fullPackagePath.concat(fileName));
			java.util.Properties properties = new java.util.Properties();
			properties.load(inputStream);
			value = PropertiesUtils.getAttributeByKey(properties , key);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(CommonUtils.isNotEmptyObject(inputStream)){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	/**
	 * 根据文件路径加载资源配置文件<p>
	 * @param path 文件路径<br>
	 * @return Properties文档对象<br>
	 */
	public static java.util.Properties load(String path) {
		java.util.Properties props =null;
		java.io.InputStream input = null;
		if (StringUtils.isNotEmpty(path)) {
			try {
				props = new java.util.Properties();
				input = new java.io.FileInputStream(FileUtils.getCurrentOSFilePath(path));
				props.load(input);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (input != null) FileUtils.closeIO(input);
			}
		}
		return props;
	}
	
	/**
	 * 记载CLASS路径下的配置文件<p>
	 * @param clz 字节码类<br>
	 * @param path 路径<br>
	 * @return 配置文件对象<br>
	 */
	public static java.util.Properties load(String className, String name) {
		java.util.Properties props = null;
		java.io.InputStream input = null;
		try {
			if (StringUtils.isNotEmpty(className) && StringUtils.isNotEmpty(name)) {
				props = new java.util.Properties();
				Class<?> clz = Class.forName(className);
				input = clz.getResourceAsStream("/".concat(clz.getPackage().getName()).replace(".", "/").concat("/").concat(name).concat(".properties"));
				props.load(input);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			FileUtils.closeIO(input);
		}
		return props;
	}
	
	/**
	 * 保存Properties对象入指定的文件中<p>
	 * @param props 文档对象<br>
	 * @param path 文档路径<br>
	 * @param comment 注释信息<br>
	 */
	public static void save(java.util.Properties props, String path, String comment) {
		java.io.OutputStream output = null;
		if (CommonUtils.isNotEmptyObject(props) && StringUtils.isNotEmpty(path)) {
			try {
				path = FileUtils.getCurrentOSFilePath(path);
				if (!FileUtils.isFileExist(path))
					FileUtils.createFileByPath(path);
				output = new java.io.FileOutputStream(path);
				props.store(output, comment);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				FileUtils.closeIO(output);
			}
		}
	}
	
	/**
	 * 根据关键字获取对应的属性值<p>
	 * @param props 文档对象<br>
	 * @param key 关键字<br>
	 * @return 属性值<br>
	 */
	public static String getAttributeByKey(java.util.Properties props, String key) {
		return CommonUtils.isNotEmptyObject(props) && props.containsKey(key) ? props.getProperty(key) : "";
	}
	
	/**
	 * 移除指定属性及值<p>
	 * @param props 文档对象<br>
	 * @param key 关键字<br>
	 */
	public static void removeAttributeByKey(java.util.Properties props, String key) {
		if (props.containsKey(key)) props.remove(key);
	}
	
	/**
	 * 向指定的Properties文件中增加属性及值<p>
	 * @param props 文档对象<br>
	 * @param key 关键字<br>
	 * @param value 属性值<br>
	 */
	public static void addAttribute(java.util.Properties props, String key, String value) {
		if (CommonUtils.isNotEmptyObject(props) && StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) props.put(key, value);
	}
	
	/**
	 * 根据关键字获取对应的Boolean类型属性值<p>
	 * @param props 文档对象<br>
	 * @param key 关键字<br>
	 * @return Boolean属性值<br>
	 */
	public static boolean getBooleanAttributeByKey(java.util.Properties props, String key) {
		return CommonUtils.getBooleanFromString(PropertiesUtils.getAttributeByKey(props, key));
	}
	
	
}
